// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.cache;

/**
 * The IOAuth2CacheHelper interface.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.cache
 * @copyright Copyright © 2019
 */
public interface IOAuth2CacheHelper {

    public boolean isValidStorageType();

    public String put(String key, String value);

    public String put(String key, String value, long expiry);

    public void remove(String key);

    public String get(String key);

}
