// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.cache.ephemeral;

import com.zimbra.cs.ephemeral.EphemeralLocation;

/**
 * The OAuth2EphemeralLocation class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.cache.ephemeral
 * @copyright Copyright © 2019
 */
public class OAuth2EphemeralLocation extends EphemeralLocation {

    @Override
    public String[] getLocation() {
        return new String[0];
    }

}

