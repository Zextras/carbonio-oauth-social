// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.cache;

/**
 * The OAuth2NoopCacheHelper class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.cache
 * @copyright Copyright © 2019
 */
public class OAuth2NoopCacheHelper implements IOAuth2CacheHelper {

    @Override
    public boolean isValidStorageType() {
        return false;
    }

    @Override
    public String put(String key, String value) {
        return value;
    }

    @Override
    public String put(String key, String value, long expiry) {
        return value;
    }

    @Override
    public void remove(String key) {

    }

    @Override
    public String get(String key) {
        return null;
    }

}
