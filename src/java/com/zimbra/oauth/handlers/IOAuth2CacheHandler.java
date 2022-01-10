// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.handlers;

import com.zimbra.oauth.utilities.OAuth2CacheUtilities;

/**
 * The IOAuth2CacheHandler class.<br>
 * Interface for OAuth handlers that require cache functionality.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.handlers
 * @copyright Copyright © 2019
 */
public interface IOAuth2CacheHandler {

    /**
     * @return True if available cache system can be used by handler for proxy auth requests
     */
    public default boolean isCacheValidForProxy() {
        return OAuth2CacheUtilities.isValidStorageType();
    }

    /**
     * @return True if available cache system can be used by handler for oauth requests
     */
    public default boolean isCacheValidForOAuth() {
        return true;
    }

}
