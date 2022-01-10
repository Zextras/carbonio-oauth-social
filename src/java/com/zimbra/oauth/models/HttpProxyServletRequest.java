// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.models;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.common.collect.Sets;

/**
 * The HttpProxyServletRequest class.<br>
 * Wrapper for HttpServletRequest to set extra headers.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.models
 * @copyright Copyright © 2019
 */
public class HttpProxyServletRequest extends HttpServletRequestWrapper {

    protected final Map<String, String> extraHeaders = new HashMap<String, String>();

    public HttpProxyServletRequest(HttpServletRequest req) {
        super(req);
    }

    public void setAll(Map<String, String> headers) {
        extraHeaders.putAll(headers);
    }

    public void setHeader(String name, String value) {
        extraHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        final String value = extraHeaders.get(name);
        if (value != null) {
            return value;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        final Set<String> headerNames = Sets.newHashSet(extraHeaders.keySet());
        final Enumeration<String> headers = super.getHeaderNames();
        while (headers.hasMoreElements()) {
            final String name = headers.nextElement();
            // don't add null value headers from the raw request
            if (super.getHeader(name) != null) {
                headerNames.add(name);
            }
        }
        return Collections.enumeration(headerNames);
    }
}
