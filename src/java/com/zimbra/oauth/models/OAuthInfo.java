// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.models;

import java.util.Map;

import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AuthToken;

/**
 * The OAuthInfo class.<br>
 * Contains OAuth related data.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.models
 * @copyright Copyright © 2018
 */
public class OAuthInfo {

    /**
     * A client id.
     */
    protected String clientId;

    /**
     * A client secret.
     */
    protected String clientSecret;

    /**
     * A client redirect uri.
     */
    protected String clientRedirectUri;

    /**
     * An access token.
     */
    protected String accessToken;

    /**
     * A refresh token.
     */
    protected String refreshToken;

    /**
     * A Zimbra auth token.
     */
    protected AuthToken zmAuthToken;

    /**
     * A username.
     */
    protected String username;

    /**
     * A token url.
     */
    protected String tokenUrl;

    /**
     * A map of parameters.
     */
    protected Map<String, String> params;

    /**
     * User account
     */
    protected Account account;

    /**
     * Constructor.
     *
     * @param params A map of parameters.
     */
    public OAuthInfo(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Get the clientId.
     *
     * @return clientId A client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Set the clientId.
     *
     * @param clientId A client id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Get the client secret.
     *
     * @return clientSecret A client secret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Set the client secret.
     *
     * @param clientSecret A client secret
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * Get the client redirect uri.
     *
     * @return clientRedirectUri A client redirect uri
     */
    public String getClientRedirectUri() {
        return clientRedirectUri;
    }

    /**
     * Set the client redirect uri.
     *
     * @param clientRedirectUri A client redirect uri
     */
    public void setClientRedirectUri(String clientRedirectUri) {
        this.clientRedirectUri = clientRedirectUri;
    }

    /**
     * Get a parameter value.
     *
     * @param key A key value
     * @return The value associated with the provided key parameter
     */
    public String getParam(String key) {
        return params.get(key);
    }

    /**
     * Get the param map.
     *
     * @return The param map
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Set the parameters.
     *
     * @param params The parameters to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Get the access token.
     *
     * @return accessToken The access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Set the access token.
     *
     * @param An access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Get the refresh token.
     *
     * @return The refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Set the refresh token.
     *
     * @param refreshToken A refresh token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Get the Zimbra auth token.
     *
     * @return The Zimbra auth token
     */
    public AuthToken getZmAuthToken() {
        return zmAuthToken;
    }

    /**
     * Set the Zimbra auth token.
     *
     * @param zmAuthToken A Zimbra auth token
     */
    public void setZmAuthToken(AuthToken zmAuthToken) {
        this.zmAuthToken = zmAuthToken;
    }

    /**
     * Get the username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     *
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the token url.
     *
     * @return The token url
     */
    public String getTokenUrl() {
        return tokenUrl;
    }

    /**
     * Set the token url.
     *
     * @param tokenUrl A token url
     */
    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    /**
     * Get the user account
     * @return the user account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Set the user account
     * @param account user account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return String.format(
            "OAuthInfo [clientId=%s, clientSecret=%s, clientRedirectUri=%s, accessToken=%s, refreshToken=%s, zmAuthToken=%s, username=%s, tokenUrl=%s, account=%s]",
            clientId, "***", clientRedirectUri, accessToken, refreshToken, zmAuthToken,
            username, tokenUrl, account);
    }


}
