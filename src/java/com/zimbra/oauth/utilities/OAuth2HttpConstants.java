// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.utilities;


/**
 * The OAuth2HttpConstants class.<br>
 * OAuth2HttpConstants contains http-related constants used in the project.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.utilities
 * @copyright Copyright © 2018
 */
public enum OAuth2HttpConstants {

    HEADER_AUTHORIZATION("Authorization"),
    HEADER_CONTENT_TYPE("Content-Type"),
    HEADER_ACCEPT("Accept"),
    HEADER_LOCATION("Location"),
    HEADER_DISABLE_EXTERNAL_REQUESTS("Disable-External-Requests"),
    HEADER_USER_AGENT("User-Agent"),
    QUERY_ERROR("error"),
    QUERY_ERROR_MSG("error_msg"),
    COOKIE_AUTH_TOKEN("ZM_AUTH_TOKEN"),

    OAUTH2_RELAY_KEY("state"),
    OAUTH2_TYPE_KEY("type"),

    JWT_PARAM_KEY("jwt"),

    PROXY_USER_AGENT("Zimbra/oauth-proxy");

    /**
     * The value of this enum.
     */
    private String constant;

    /**
     * @return The enum value
     */
    public String getValue() {
        return constant;
    }

    /**
     * @param constant The enum value to set
     */
    private OAuth2HttpConstants(String constant) {
        this.constant = constant;
    }

}
