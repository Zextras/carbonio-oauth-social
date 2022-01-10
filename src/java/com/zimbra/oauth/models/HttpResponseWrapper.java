// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.models;

import org.apache.http.HttpResponse;

/**
 * The HttpResponseWrapper class.<br>
 * Wrapper for HttpResponse contains entity as byte array.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.models
 * @copyright Copyright © 2019
 */
public class HttpResponseWrapper {

    /**
     * The http response.
     */
    protected HttpResponse response;

    /**
     * The http entity.
     */
    protected byte[] entityBytes;

    /**
     * Creates an instance with response and entity.
     *
     * @param response The response to set
     * @param entityBytes The entity bytes to set
     */
    public HttpResponseWrapper(HttpResponse response, byte[] entityBytes) {
        this.response = response;
        this.entityBytes = entityBytes;
    }

    /**
     * @return the response
     */
    public HttpResponse getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    /**
     * @return the entity bytes
     */
    public byte[] getEntityBytes() {
        return entityBytes;
    }

    /**
     * @param entityBytes the entity to set
     */
    public void setEntityBytes(byte[] entityBytes) {
        this.entityBytes = entityBytes;
    }

}
