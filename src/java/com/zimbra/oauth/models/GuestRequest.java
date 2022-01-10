// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.models;

import java.util.Collections;
import java.util.Map;

/**
 * The GuestRequest class.<br>
 * Contains request headers and body.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.models
 * @copyright Copyright © 2019
 */
public class GuestRequest {

    /**
     * Relevant request headers.
     */
    private Map<String, String> headers;

    /**
     * Request body params.
     */
    private Map<String, Object> body;

    public GuestRequest() {
        this(Collections.emptyMap(), Collections.emptyMap());
    }

    public GuestRequest(Map<String, String> headers, Map<String, Object> body) {
        this.headers = headers;
        this.body = body;
    }

    /**
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * @return the body
     */
    public Map<String, Object> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

}
