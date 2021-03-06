// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.handlers;

import java.util.List;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Account;
import com.zimbra.oauth.models.GuestRequest;
import com.zimbra.oauth.models.OAuthInfo;

/**
 * The IOAuth2Handler class.<br>
 * Interface for OAuth operations in this project.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.handlers
 * @copyright Copyright © 2018
 */
public interface IOAuth2Handler {

    /**
     * Returns authorize endpoint for the client.
     *
     * @param params request params filtered with param keys
     * @param account The account to acquire configuration by access level
     * @return The authorize endpoint
     * @throws ServiceException If there are issues determining the
     *             endpoint
     */
    public String authorize(Map<String, String> params, Account acct) throws ServiceException;

    /**
     * Authenticates a user with the endpoint and stores credentials.
     *
     * @param oauthInfo Contains a code provided by authorizing endpoint
     * @return True on success
     * @throws ServiceException If there are issues in this process
     */
    public Boolean authenticate(OAuthInfo oauthInfo) throws ServiceException;

    /**
     * Refreshes a user's token and updates the credentials in oauthInfo.
     *
     * @param oauthInfo Contains the info on which account/token to refresh
     * @return True on success
     * @throws ServiceException If there are issues in this process
     */
    public Boolean refresh(OAuthInfo oauthInfo) throws ServiceException;

    /**
     * Retrieves the client info for a specified client and sets it in oauthInfo
     * params.
     *
     * @param oauthInfo Contains the client info
     * @return True on success
     * @throws ServiceException If there are issues in this process
     */
    public Boolean info(OAuthInfo oauthResponse) throws ServiceException;

    /**
     * Handles events for the client.<br>
     * This method is not provided with an authorized account before execution.
     *
     * @param request Contains client and request info
     * @return True on success
     * @throws ServiceException If there are issues in this process
     */
    public Boolean event(GuestRequest request) throws ServiceException;

    /**
     * Returns a list of keys to expect during authenticate callback.
     *
     * @return List of query param keys
     */
    public List<String> getAuthenticateParamKeys();

    /**
     * Returns a list of keys to expect during authenticate callback.
     *
     * @return List of query param keys
     */
    public List<String> getAuthorizeParamKeys();

    /**
     * Throws an exception if there are invalid params passed in.
     *
     * @param params The authenticate request params
     * @throws ServiceException If any params are invalid
     */
    public void verifyAndSplitAuthenticateParams(Map<String, String> params) throws ServiceException;

    /**
     * Throws an exception if there are invalid params passed in.
     *
     * @param params The authorize request params
     * @throws ServiceException If any params are invalid
     */
    public void verifyAuthorizeParams(Map<String, String> params) throws ServiceException;

    /**
     * Returns the appropriate relay for this client.
     *
     * @param params Map of params to retrieve relay from
     * @return Relay as specified in params, or client default
     */
    public String getRelay(Map<String, String> params);
}
