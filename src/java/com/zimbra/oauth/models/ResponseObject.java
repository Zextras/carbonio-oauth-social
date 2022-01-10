// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The ResponseObject class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.models
 * @copyright Copyright © 2018
 */
@XmlRootElement
public class ResponseObject<E> {

    /**
     * Data of type.
     */
    protected final E data;

    /**
     * Meta instance.
     */
    protected final ResponseMeta _meta;

    /**
     * Constructor.
     *
     * @param data A data object
     */
    public ResponseObject(E data, ResponseMeta meta) {
        this.data = data;
        this._meta = meta;
    }

    /**
     * Get data.
     *
     * @return A data object
     */
    public E getData() {
        return data;
    }

    /**
     * Get the Meta instance.
     *
     * @return The instance of Meta object
     */
    public ResponseMeta get_meta() {
        return _meta;
    }

}
