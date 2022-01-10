// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.utilities;

/**
 * The OAuth2Constants class.<br>
 * OAuth2Constants contains application constants used in the project.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.utilities
 * @copyright Copyright © 2018
 */
public enum OAuth2Constants {

    API_NAME("zm-oauth-social"),
    ENCODING("utf-8"),
    DEFAULT_SERVER_PATH("/oauth2"),
    PROXY_SERVER_PATH("/oauth2-proxy"),

    DEFAULT_SUCCESS_REDIRECT("/"),
    DEFAULT_HOST_URI_TEMPLATE("https://%s:443"),
    DEFAULT_OAUTH_FOLDER_TEMPLATE("%s-%s-%s"),

    DATASOURCE_POLLING_INTERVAL("1d"),

    CONTACTS_IMAGE_BUFFER_SIZE("2048"),

    PROPERTIES_NAME_APPLICATION("application"),

    DEFAULT_PROXY_TYPE("noop"),
    TOKEN_CACHE_LIFETIME("1800"),

    CACHE_KEY_PREFIX("zm_oauth_social"),
    CACHE_BACKEND_URL_PREFIX("ssdb");

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
    private OAuth2Constants(String constant) {
        this.constant = constant;
    }
}
