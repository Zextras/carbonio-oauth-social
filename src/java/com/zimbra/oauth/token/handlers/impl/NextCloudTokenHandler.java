package com.zimbra.oauth.token.handlers.impl;

// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.StringUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.oauth.models.OAuthInfo;
import com.zimbra.oauth.token.utilities.LdapOauthHelper;
import com.zimbra.oauth.token.utilities.OAuth2HandlerUtilities;
import com.zimbra.oauth.token.utilities.OAuth2TokenConstants;
import com.zimbra.oauth.utilities.OAuth2CacheUtilities;
import com.zimbra.oauth.utilities.OAuth2Constants;
import com.zimbra.oauth.utilities.OAuth2DataSource;
import com.zimbra.oauth.utilities.OAuth2Utilities;
/**
 * @author zimbra
 *
 */
public class NextCloudTokenHandler {

    /**
     *
     */
    private static final int TOKEN_EXPIRATION_TIME = 60 *60 *1000;

    /**
     * Retrieves the NextCloud users accessToken.
     *
     * @return accessToken A live access token
     * @throws ServiceException If there are issues
     */
    public static String refreshAccessToken(Account acct, String client) throws ServiceException {

        String dataSourceName = OAuth2Constants.DEFAULT_PROXY_TYPE.getValue() + "-"
                            + OAuth2TokenConstants.NEXTCLOUD_CLIENT_NAME.getValue();

        ZimbraLog.extensions.debug("No access token found, refreshing for account: %s", acct.getId());;

        DataSource dataSource = acct.getAllDataSources().stream().
            filter(dataS -> dataS.getName().endsWith(dataSourceName)).findAny().get();


        if (dataSource == null) {
            throw ServiceException.NOT_FOUND("No relevant datasource found");
        }
        final OAuthInfo oauthInfo = new OAuthInfo(new HashMap<String, String>());
        final String refreshToken = OAuth2DataSource.getRefreshToken(dataSource);

        String value = com.zimbra.oauth.token.utilities.LdapOauthHelper.getFirstConfig(Provisioning.A_zimbraOAuthConsumerCredentials,
                client, acct) ;
        String indVa [] = value.split(":");
        final String clientId = indVa[0];
        final String clientSecret = indVa[1];
        value = LdapOauthHelper.getFirstConfig(Provisioning.A_zimbraOAuthConsumerRedirectUri,
            client, acct) ;
        String redirecUri = value.substring(0, value.lastIndexOf(":"));
        final String clientRedirectUri = redirecUri;

        String nextCloudUrl = null;
        value = LdapOauthHelper.getFirstConfig(Provisioning.A_zimbraOAuthConsumerAPIScope, client + "_noop", acct);
        try {
           nextCloudUrl = value.substring(0, value.lastIndexOf(":"));
        } catch (Exception e) {
           throw ServiceException.FAILURE("Failed to get nextCloudUrl from zimbraOAuthConsumerAPIScope", null);
        }

        if (StringUtil.isNullOrEmpty(clientId) || StringUtil.isNullOrEmpty(clientSecret)
            || StringUtil.isNullOrEmpty(clientRedirectUri)) {
            throw ServiceException.FAILURE("Required config(id, secret and redirectUri) parameters are not provided.", null);
        }
        // set client specific properties
        oauthInfo.setRefreshToken(refreshToken);
        oauthInfo.setClientId(clientId);
        oauthInfo.setClientSecret(clientSecret);
        oauthInfo.setClientRedirectUri(clientRedirectUri);
        oauthInfo.setTokenUrl(nextCloudUrl +  OAuth2TokenConstants.NEXTCLOUD_AUTHENTICATE_URI.getValue());

      ZimbraLog.extensions.debug("Fetching access credentials for access token.");
      JsonNode credentials = null;
      credentials = OAuth2HandlerUtilities.getTokenRequest(oauthInfo,
            OAuth2Utilities.encodeBasicHeader(clientId, clientSecret));

      String accessToken = credentials.get("access_token").asText();
      String  newRefreshToken =     credentials.get("refresh_token").asText();
      Map<String, Object>  attrs = new HashMap<String, Object>();


      attrs.put(Provisioning.A_zimbraDataSourceOAuthRefreshToken, newRefreshToken);
      Provisioning prov = Provisioning.getInstance();
      prov.modifyDataSource(acct, dataSource.getId(), attrs);
      oauthInfo.setRefreshToken(newRefreshToken);

      OAuth2CacheUtilities.put(acct.getId(), newRefreshToken, System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);
      return accessToken;

    }

}
