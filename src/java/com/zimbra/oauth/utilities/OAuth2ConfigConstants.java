// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.utilities;

import com.zimbra.cs.account.Provisioning;

/**
 * The OAuth2ConfigConstants class.<br>
 * OAuth2HttpConstants contains config-related constants used in the project.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.utilities
 * @copyright Copyright © 2018
 */
public enum OAuth2ConfigConstants {

    LC_HANDLER_CLASS_PREFIX("zm_oauth_classes_handlers_"),

    LC_OAUTH_CLIENT_ID_TEMPLATE("zm_oauth_%s_client_id"),
    LC_OAUTH_CLIENT_SECRET_TEMPLATE("zm_oauth_%s_client_secret"),
    LC_OAUTH_CLIENT_REDIRECT_URI_TEMPLATE("zm_oauth_%s_client_redirect_uri"),
    LC_OAUTH_SCOPE_TEMPLATE("zm_oauth_%s_scope"),
    LC_OAUTH_IMPORT_CLASS_TEMPLATE("zm_oauth_%s_import_class"),

    OAUTH_CLIENT_ID("client_id"),
    OAUTH_CLIENT_SECRET("client_secret"),
    OAUTH_VERIFICATION_TOKEN("verification_token"),
    OAUTH_CLIENT_REDIRECT_URI("client_redirect_uri"),
    OAUTH_SCOPE("scope"),
    OAUTH_STATIC_CREDENTIALS(Provisioning.A_zimbraOAuthConsumerCredentials);

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
    private OAuth2ConfigConstants(String constant) {
        this.constant = constant;
    }
}
