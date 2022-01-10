// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.cache.ephemeral;

import com.zimbra.cs.ephemeral.EphemeralInput;
import com.zimbra.cs.ephemeral.EphemeralLocation;
import com.zimbra.cs.ephemeral.ValueEncoder;

/**
 * The OAuth2ValueEncoder class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.cache.ephemeral
 * @copyright Copyright © 2019
 * @see SSDBValueEncoder
 */
public class OAuth2ValueEncoder extends ValueEncoder {

    @Override
    public String encodeValue(EphemeralInput input, EphemeralLocation target) {
        if(input == null || input.getValue() == null) {
            return null;
        }
        return input.getValue().toString();
    }

}

