// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.utilities;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Provisioning;

/**
 * The Configuration class for this project for loading attributes from LDAP
 * @author Zimbra API Team
 * @package com.zimbra.oauth.utilities
 * @copyright Copyright © 2018
 */
public class LdapConfiguration extends Configuration {

    /**
     * @param appName
     * @throws ServiceException
     */
    protected LdapConfiguration(String appName) {
        super(appName);
    }


    /**
     * Get the associated value with the key.
     *
     * @param key A key to lookup
     * @appName the social account name
     * @acct the user account for which datasource is being setup
     * @return A value for a given key for given social account and user account
     *
     */
    @Override
    public String getString(String key, String appName, Account account) {
        return getConfig(key, appName, account);
    }

    /**
     * @param oauth related config key
     * @param appName client app name
     * @param acct the relevant account
     * @return value for the associated key
     */
    public String getConfig(String key, String appName, Account acct) {

        String value = null;
        ZimbraLog.extensions.debug("App name is:%s", appName);
        // determine which credential to acquire when fetching from consumer credentials
        Integer credentialPosition = null;
        if (key.endsWith(OAuth2ConfigConstants.OAUTH_CLIENT_ID.getValue())) {
            credentialPosition = 0;
        } else if (key.endsWith(OAuth2ConfigConstants.OAUTH_CLIENT_SECRET.getValue())) {
            credentialPosition = 1;
        } else if (key.endsWith(OAuth2ConfigConstants.OAUTH_VERIFICATION_TOKEN.getValue())) {
            credentialPosition = 2;
        }

        if (credentialPosition != null) {
            final String[] registeredOAuth2Clients = loadConfiguration(acct,
                Provisioning.A_zimbraOAuthConsumerCredentials, appName);

            if (registeredOAuth2Clients != null && registeredOAuth2Clients.length != 0) {
                // {consumer-id}:{secret}:[{verification-token}:]{consumer-app-name}
                for (final String consumer : registeredOAuth2Clients) {
                    final String s[] = consumer.split(":");
                    // skip this client if the credentials are not
                    // properly formatted to what the client expects
                    if (credentialPosition >= (s.length - 1)) {
                        continue;
                    }
                    // ensure the client is correct and return the credential value
                    if (s.length >= 3 && s[s.length - 1].equals(appName)) {
                        value = s[credentialPosition];
                        break;
                    }
                }
            }
        } else if (key.endsWith(OAuth2ConfigConstants.OAUTH_CLIENT_REDIRECT_URI.getValue())) {
            final String[] registeredOAuth2RedirectUrls = loadConfiguration(acct,
                Provisioning.A_zimbraOAuthConsumerRedirectUri, appName);
            if (registeredOAuth2RedirectUrls != null
                && registeredOAuth2RedirectUrls.length != 0) {
                // {redirectURI}:{consumer-app-name} (the redirect uri can contain ":")
                for (final String consumer : registeredOAuth2RedirectUrls) {
                    final int index = consumer.lastIndexOf(':');
                    if (index != -1) {
                        final String temp = consumer.substring(index+1);
                        if (temp.equals(appName)) {
                            value = consumer.substring(0, index);
                            break;
                        }
                    }
                }
            }
        } else if (key.endsWith(OAuth2ConfigConstants.OAUTH_SCOPE.getValue())) {
            final String[] registeredOAuth2APIScope = loadConfiguration(acct,
                Provisioning.A_zimbraOAuthConsumerAPIScope, appName);

            if (registeredOAuth2APIScope != null && registeredOAuth2APIScope.length != 0) {
                for (final String scope : registeredOAuth2APIScope) {
                    final int index = scope.lastIndexOf(':');
                    if (index != -1) {
                        final String temp = scope.substring(index+1);
                        if (temp.equals(appName)) {
                            value = scope.substring(0, index);
                            break;
                        }
                    }
                }
            }
        } else {
            value = getString(key, null);
        }
        if (key.endsWith(OAuth2ConfigConstants.OAUTH_CLIENT_SECRET.getValue())) {
            ZimbraLog.extensions.trace("Requested : %s  and value is: %s ", key, "****");
        } else {
            ZimbraLog.extensions.trace("Requested : %s  and value is: %s ", key, value);
        }
        return value;
    }

    /**
     * Retrieve first instance of specified configuration for the client.<br>
     * Client is not validated against LC handlers.
     *
     * @param key The config key
     * @param appName The client
     * @param account The account to search by
     * @return The first instance of the configuration
     */
    public static String getFirstConfig(String key, String appName, Account account) {
        final String[] registeredOAuth2Clients = loadConfiguration(account, key, appName);
        String rawConfig = null;
        if (registeredOAuth2Clients != null) {
            rawConfig = Arrays.stream(registeredOAuth2Clients)
                .filter(c -> StringUtils.endsWith(c, appName))
                .findFirst()
                .orElse(null);
        }
        return rawConfig;
    }

    /**
     *
     * @acct the user account for which datasource is being setup
     * @param key the ldap config key
     * @param appName the social app
     * @return the attributes values
     */
    private static String[] loadConfiguration(Account acct, String key, String appName) {
        String [] values = null;
        ZimbraLog.extensions.debug("Loading configuration: %s for: %s", key, acct.getName());
        try {
            values = Provisioning.getInstance().getDomain(acct).getMultiAttr(key);
            final String temp = StringUtils.join(values);
            if (values == null || values.length == 0 || !temp.contains(appName)) {
                ZimbraLog.extensions.trace("Config:%s does not exist at domain level", key);
                values = Provisioning.getInstance().getConfig()
                    .getMultiAttr(key);
            }
        } catch (final ServiceException e) {
            ZimbraLog.extensions.info("Error loading configuration : %s for : %s", key, acct.getName());
            ZimbraLog.extensions.debug(e);
        }
        ZimbraLog.extensions.debug("Configuration is: %s", StringUtils.join(values));
        return values;
    }

   /**
    * Loads a single configuration by name (no extension).<br>
    * Creates a Configuration and caches the Configuration.
    *
    * @param name Name of the client
    * @return Configuration object
    * @throws ServiceException If there are issues
    */
   public static Configuration buildConfiguration(String name) throws ServiceException {
       Configuration config = null;

       // try to find config in cache
       if (name != null) {
           config = configCache.get(name);
       }

       // if the config is empty, try to load it
       if (config == null) {
           // validate the client
           if (!isValidClient(name)) {
               throw ServiceException.UNSUPPORTED();
           }
           config = new LdapConfiguration(name);
       }
       configCache.put(name, config);
       return config;
   }

}
