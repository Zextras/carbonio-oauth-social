// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.handlers;

import java.util.List;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Account;
import com.zimbra.oauth.models.OAuthInfo;

/**
 * The IOAuth2ProxyHandler class.<br>
 * Interface for OAuth proxy operations in this project.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.handlers
 * @copyright Copyright © 2019
 */
public interface IOAuth2ProxyHandler {

    /**
     * Returns a map of headers to set before proxying a request.
     *
     * @param oauthInfo Contains info to determine which headers are needed
     * @return Map of headers to set before proxying
     * @throws ServiceException If there are issues determining the endpoint
     */
    public Map<String, String> headers(OAuthInfo oauthInfo) throws ServiceException;

    /**
     * @param client The request client (may contain relevant data for path comparison)
     * @param method The request method
     * @param extraHeaders Contains authorization header
     * @param target The target to check
     * @param body The request body
     * @param account The account to acquire configuration by access level
     * @return True if the specified request is allowed
     */
    public boolean isProxyRequestAllowed(String client, String method,
        Map<String, String> extraHeaders, String target, byte[] body, Account account);

    /**
     * @return A list of keys expected by headers method
     */
    public List<String> getHeadersParamKeys();
}
