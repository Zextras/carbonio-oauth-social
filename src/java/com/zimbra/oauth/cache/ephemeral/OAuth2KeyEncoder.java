// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.cache.ephemeral;

import com.zimbra.cs.ephemeral.EphemeralKey;
import com.zimbra.cs.ephemeral.EphemeralLocation;
import com.zimbra.cs.ephemeral.KeyEncoder;

/**
 * The OAuth2KeyEncoder class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.cache.ephemeral
 * @copyright Copyright © 2019
 */
public class OAuth2KeyEncoder extends KeyEncoder {

    @Override
    public String encodeKey(EphemeralKey key, EphemeralLocation target) {
        return key.getKey();
    }

}
