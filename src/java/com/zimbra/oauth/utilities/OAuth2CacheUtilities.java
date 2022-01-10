// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.utilities;

import com.zimbra.oauth.cache.IOAuth2CacheHelper;
import com.zimbra.oauth.cache.OAuth2NoopCacheHelper;
import com.zimbra.oauth.cache.ephemeral.OAuth2EphemeralCacheHelper;

/**
 * The OAuth2CacheUtilities class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.utilities
 * @copyright Copyright © 2019
 */
public class OAuth2CacheUtilities {

    protected static IOAuth2CacheHelper helper = loadCacheHelper();

    public static boolean isValidStorageType() {
        return helper.isValidStorageType();
    }

    public static String put(String key, String value) {
        return helper.put(key, value);
    }

    public static String put(String key, String value, long expiry) {
        return helper.put(key, value, expiry);
    }

    public static void remove(String key) {
        helper.remove(key);
    }

    public static String get(String key) {
        return helper.get(key);
    }

    public static String buildAccountKey(String accountId, String key) {
        return String.format("{%s}-%s", accountId, key);
    }

    protected static IOAuth2CacheHelper loadCacheHelper() {
        final IOAuth2CacheHelper cacheHelper = new OAuth2EphemeralCacheHelper();
        // if storage type is not valid, we can't use the ephemeral cache
        if (cacheHelper.isValidStorageType()) {
            return cacheHelper;
        }
        return new OAuth2NoopCacheHelper();
    }
}
