// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zimbra.oauth.utilities.OAuth2Constants;

/**
 * The ResponseMeta class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.models
 * @copyright Copyright © 2019
 */
@XmlRootElement
public class ResponseMeta {

    protected final String api = OAuth2Constants.API_NAME.getValue();

    /**
     * HTTP Status.
     */
    @JsonIgnore
    protected int status;

    /**
     * Constructor.
     *
     * @param status The status to set
     */
    public ResponseMeta(int status) {
        this.status = status;
    }

    public String getApi() {
        return api;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
