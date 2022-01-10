// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.cache.ephemeral;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.zimbra.common.localconfig.LC;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.ephemeral.EphemeralInput;
import com.zimbra.cs.ephemeral.EphemeralInput.RelativeExpiration;
import com.zimbra.cs.ephemeral.EphemeralKey;
import com.zimbra.cs.ephemeral.EphemeralStore;
import com.zimbra.oauth.cache.IOAuth2CacheHelper;
import com.zimbra.oauth.utilities.OAuth2Constants;

/**
 * The OAuth2CacheUtilities class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.cache.ephemeral
 * @copyright Copyright © 2019
 */
public class OAuth2EphemeralCacheHelper implements IOAuth2CacheHelper {

    /**
     * Cache client instance.
     */
    protected EphemeralStore client;

    public OAuth2EphemeralCacheHelper() {
        try {
            client = EphemeralStore.getFactory().getNewStore();
            if (client != null) {
                client.setAttributeEncoder(new OAuth2AttributeEncoder());
            }
        } catch (final ServiceException e) {
            ZimbraLog.extensions.errorQuietly("Unable to load ephemeral store for oauth.", e);
        }
    }

    @Override
    public boolean isValidStorageType() {
        if (client == null) {
            return false;
        }
        // if redis service details exist assume this is zimbra x. ephemeral is handled with default setup
        final String redisServiceUri = LC.get("redis_service_uri");
        if (!StringUtils.isEmpty(redisServiceUri)) {
            return true;
        }
        try {
            // `ssdb` allows ssdb, redis use for formless storage. ensure we're only using this for caching
            final String backendUrl = Provisioning.getInstance().getConfig().getEphemeralBackendURL();
            return StringUtils.startsWith(backendUrl, OAuth2Constants.CACHE_BACKEND_URL_PREFIX.getValue());
        } catch (final ServiceException e) {
            ZimbraLog.extensions.errorQuietly("Unable to load ephemeral store for oauth.", e);
        }
        return false;
    }

    @Override
    public String put(String key, String value) {
        final EphemeralInput input = new EphemeralInput(new EphemeralKey(key), value);
        try {
            client.set(input, new OAuth2EphemeralLocation());
        } catch (final ServiceException e) {
            ZimbraLog.extensions.errorQuietly("Failed write to ephemeral store.", e);
        }
        return value;
    }

    @Override
    public String put(String key, String value, long expiry) {
        final EphemeralInput input = new EphemeralInput(new EphemeralKey(key), value,
            new RelativeExpiration(expiry, TimeUnit.SECONDS));
        try {
            client.set(input, new OAuth2EphemeralLocation());
        } catch (final ServiceException e) {
            ZimbraLog.extensions.errorQuietly("Failed write to ephemeral store.", e);
        }
        return value;
    }

    @Override
    public void remove(String key) {
        try {
            client.delete(new EphemeralKey(key), null, new OAuth2EphemeralLocation());
        } catch (final ServiceException e) {
            ZimbraLog.extensions.errorQuietly("Failed removal from ephemeral store.", e);
        }
    }

    @Override
    public String get(String key) {
        String result = null;
        try {
            result = client.get(new EphemeralKey(key), new OAuth2EphemeralLocation()).getValue();
        } catch (final ServiceException e) {
            ZimbraLog.extensions.errorQuietly("Failed to read from ephemeral store.", e);
        }
        return result;
    }

}
