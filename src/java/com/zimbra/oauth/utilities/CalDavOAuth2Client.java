// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.utilities;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

import com.zimbra.common.httpclient.HttpClientUtil;
import com.zimbra.cs.dav.DavContext.Depth;
import com.zimbra.cs.dav.client.CalDavClient;

/**
 * The CalDavOAuth2Client class.<br>
 * Used to refresh OAuth2 access token for CalDav import.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.utilities
 * @copyright Copyright © 2018
 */
public class CalDavOAuth2Client extends CalDavClient {

    /**
     * Constructor.
     *
     * @param baseUrl The url to initialize with
     */
    public CalDavOAuth2Client(String baseUrl) {
        super(baseUrl);
    }

    @Override
    protected HttpResponse executeMethod(HttpRequestBase m, Depth d, String bodyForLogging) throws IOException, HttpException {
        final HttpClient client = mClient.build();
        m.setHeader("User-Agent", mUserAgent);
        String depth = "0";
        switch (d) {
        case one:
            depth = "1";
            break;
        case infinity:
            depth = "infinity";
            break;
        case zero:
            break;
        default:
            break;
        }
        final String authorizationHeader = String.format("Bearer %s", accessToken);
        m.setHeader(OAuth2HttpConstants.HEADER_AUTHORIZATION.getValue(), authorizationHeader);
        m.setHeader("Depth", depth);
        logRequestInfo(m, bodyForLogging);
        final HttpResponse response = HttpClientUtil.executeMethod(client, m);
        logResponseInfo(response);
        return response;
    }
}
