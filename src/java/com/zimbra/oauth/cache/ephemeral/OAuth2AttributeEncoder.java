// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.cache.ephemeral;

import com.zimbra.cs.ephemeral.AttributeEncoder;
import com.zimbra.cs.ephemeral.EphemeralKey;
import com.zimbra.cs.ephemeral.EphemeralKeyValuePair;

/**
 * The OAuth2AttributeEncoder class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.cache.ephemeral
 * @copyright Copyright © 2019
 * @see SSDBAttributeEncoder
 */
public class OAuth2AttributeEncoder extends AttributeEncoder {

    public OAuth2AttributeEncoder() {
        setKeyEncoder(new OAuth2KeyEncoder());
        setValueEncoder(new OAuth2ValueEncoder());
    }

    @Override
    public EphemeralKeyValuePair decode(String key, String value) {
        return new EphemeralKeyValuePair(new EphemeralKey(key), value);
    }

}
