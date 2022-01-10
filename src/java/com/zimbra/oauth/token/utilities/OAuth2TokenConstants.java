package com.zimbra.oauth.token.utilities;

// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

/**
 * @author zimbra
 *
 */
public enum OAuth2TokenConstants {

    NEXTCLOUD_CLIENT_NAME("nextcloud"),
    NEXTCLOUD_AUTHENTICATE_URI("/apps/oauth2/api/v1/token"),
    NEXTCLOUD_HOST_NEXTCLOUD("nextcloud_dummy_host");


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
    private OAuth2TokenConstants(String constant) {
        this.constant = constant;
    }
}
