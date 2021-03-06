// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.handlers.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.oauth.handlers.IOAuth2Handler;
import com.zimbra.oauth.models.OAuthInfo;
import com.zimbra.oauth.utilities.Configuration;
import com.zimbra.oauth.utilities.OAuth2Constants;
import com.zimbra.soap.admin.type.DataSourceType;

/**
 * The OutlookOAuth2Handler class.<br>
 * Outlook OAuth operations handler.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.handlers.impl
 * @copyright Copyright © 2018
 */
public class OutlookOAuth2Handler extends OAuth2Handler implements IOAuth2Handler {

    /**
     * Contains error constants used in this implementation.
     */
    protected enum OutlookErrorConstants {

        /**
         * Invalid request error from Outlook.<br>
         * Protocol error, such as a missing required parameter.
         */
        RESPONSE_ERROR_INVALID_REQUEST("invalid_request"),

        /**
         * Unauthorized client error from Outlook.<br>
         * The client application is not permitted to request an authorization
         * code.
         */
        RESPONSE_ERROR_UNAUTHORIZED_CLIENT("unauthorized_client"),

        /**
         * Access denied error from Outlook.<br>
         * Resource owner denied consent.
         */
        RESPONSE_ERROR_ACCESS_DENIED("access_denied"),

        /**
         * Server error, error from Outlook.<br>
         * The server encountered an unexpected error.
         */
        RESPONSE_ERROR_SERVER_ERROR("server_error"),

        /**
         * Temporarily unavailable error from Outlook.<br>
         * The server is temporarily too busy to handle the request.
         */
        RESPONSE_ERROR_TEMPORARILY_UNAVAILABLE("temporarily_unavailable"),

        /**
         * Invalid resource error from Outlook.<br>
         * The target resource is invalid because it does not exist, Azure AD
         * cannot find it, or it is not correctly configured.
         */
        RESPONSE_ERROR_INVALID_RESOURCE("invalid_resource"),

        /**
         * Unsupported response type error from Outlook.<br>
         * The authorization server does not support the response type in the
         * request.
         */
        RESPONSE_ERROR_RESPONSE_TYPE("unsupported_response_type"),

        /**
         * Default error.
         */
        DEFAULT_ERROR("DEFAULT_ERROR");

        /**
         * The value of this enum.
         */
        private String constant;

        /**
         * @return The enum value
         */
        public String getValue() {
            return constant;
        }

        /**
         * @param constant The enum value to set
         */
        private OutlookErrorConstants(String constant) {
            this.constant = constant;
        }

        /**
         * ValueOf wrapper for constants.
         *
         * @param code The code to check for
         * @return Enum instance
         */
        protected static OutlookErrorConstants fromString(String code) {
            for (final OutlookErrorConstants t : OutlookErrorConstants.values()) {
                if (StringUtils.equals(t.getValue(), code)) {
                    return t;
                }
            }
            return DEFAULT_ERROR;
        }

    }

    /**
     * Contains contact constants used in this implementation.
     */
    protected enum OutlookContactConstants {
        CONTACTS_URI("https://outlook.office.com/api/v2.0/me/contacts?$select=EmailAddresses,GivenName,MiddleName,Surname,Nickname,JobTitle,CompanyName,Department,OfficeLocation,HomePhones,BusinessPhones,MobilePhone1,PersonalNotes,HomeAddress,BusinessAddress,OtherAddress,ImAddresses,Birthday"),
        CONTACTS_FOLDER_URI("https://outlook.office.com/api/v2.0/me/ContactFolders"),
        CONTACTS_PHOTO_URI_TEMPLATE("https://outlook.office.com/api/v2.0/me/contacts('%s')/photo/$value"),
        CONTACTS_PAGE_SIZE("100"),
        CONTACTS_IMAGE_NAME("outlook-profile-image"),
        CONTACT_ID("OutlookId"),
        CONTACT_BIRTHDAY_FORMAT("yyyy-MM-dd'T'HH:mm:ss'Z'");

        /**
         * The value of this enum.
         */
        private String constant;

        /**
         * @return The enum value
         */
        public String getValue() {
            return constant;
        }

        /**
         * @param constant The enum value to set
         */
        private OutlookContactConstants(String constant) {
            this.constant = constant;
        }
    }

    /**
     * Contains oauth2 constants used in this implementation.
     */
    protected enum OutlookOAuth2Constants {

        /**
         * The authorize endpoint for Outlook.
         */
        AUTHORIZE_URI_TEMPLATE("https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=%s&redirect_uri=%s&response_type=%s&scope=%s"),

        /**
         * The profile endpoint for Outlook.
         */
        PROFILE_URI("https://www.outlookapis.com/auth/userinfo.email"),

        /**
         * The authenticate endpoint for Outlook.
         */
        AUTHENTICATE_URI("https://login.microsoftonline.com/common/oauth2/v2.0/token"),

        /**
         * The relay key for Outlook.
         */
        RELAY_KEY("state"),

        /**
         * The scope required for Outlook.
         */
        REQUIRED_SCOPES("openid+email+offline_access"),

        /**
         * The scope delimiter for Outlook.
         */
        SCOPE_DELIMITER("+"),

        /**
         * The implementation name.
         */
        CLIENT_NAME("outlook"),

        /**
         * The implementation host.
         */
        HOST_OUTLOOK("microsoftonline.com");

        /**
         * The value of this enum.
         */
        private String constant;

        /**
         * @return The enum value
         */
        public String getValue() {
            return constant;
        }

        /**
         * @param constant The enum value to set
         */
        private OutlookOAuth2Constants(String constant) {
            this.constant = constant;
        }
    }

    /**
     * Constructs an OutlookOAuth2Handler object.
     *
     * @param config For accessing configured properties
     */
    public OutlookOAuth2Handler(Configuration config) {
        super(config, OutlookOAuth2Constants.CLIENT_NAME.getValue(),
            OutlookOAuth2Constants.HOST_OUTLOOK.getValue());
        authenticateUri = OutlookOAuth2Constants.AUTHENTICATE_URI.getValue();
        authorizeUriTemplate = OutlookOAuth2Constants.AUTHORIZE_URI_TEMPLATE.getValue();
        requiredScopes = OutlookOAuth2Constants.REQUIRED_SCOPES.getValue();
        scopeDelimiter = OutlookOAuth2Constants.SCOPE_DELIMITER.getValue();
        relayKey = OutlookOAuth2Constants.RELAY_KEY.getValue();
        dataSource.addImportClass(DataSourceType.oauth2contact.name(),
            OutlookContactsImport.class.getCanonicalName());
    }

    /**
     * Validates that the token response has no errors, and contains the
     * requested access information.
     *
     * @param response The json token response
     * @throws ServiceException<br>
     *             FORBIDDEN If the social service rejects request as
     *             `access_denied`.<br>
     *             OPERATION_DENIED If the refresh token was deemed invalid, or
     *             incorrect redirect uri.<br>
     *             If the client id or client secret are incorrect.<br>
     *             PARSE_ERROR If response from Outlook has no errors, but the
     *             access info is missing.<br>
     *             PERM_DENIED If the refresh token or code is expired, or for
     *             general rejection.<br>
     *             PROXY_ERROR If the social service is unable to respond
     *             normally.
     */
    @Override
    protected void validateTokenResponse(JsonNode response) throws ServiceException {
        // check for errors
        if (response.has("error")) {
            final String error = response.get("error").asText();
            final JsonNode errorMsg = response.get("error_description");
            switch (OutlookErrorConstants.fromString(error)) {
            case RESPONSE_ERROR_INVALID_REQUEST:
                ZimbraLog.extensions.warn("Invalid token request parameters: " + errorMsg);
                throw ServiceException
                    .OPERATION_DENIED("The token request parameters are invalid.");
            case RESPONSE_ERROR_UNAUTHORIZED_CLIENT:
                ZimbraLog.extensions.warn(
                    "The specified client details provided to the social service are invalid: "
                        + errorMsg);
                throw ServiceException.OPERATION_DENIED(
                    "The specified client details provided to the social service are invalid.");
            case RESPONSE_ERROR_ACCESS_DENIED:
                ZimbraLog.extensions
                    .info("User did not provide authorization for this service: " + errorMsg);
                throw ServiceException
                    .FORBIDDEN("User did not provide authorization for this service.");
            case RESPONSE_ERROR_SERVER_ERROR:
            case RESPONSE_ERROR_TEMPORARILY_UNAVAILABLE:
                ZimbraLog.extensions
                    .debug("There was an issue with the remote social service: " + errorMsg);
                throw ServiceException
                    .PROXY_ERROR("There was an issue with the remote social service.", null);
            case RESPONSE_ERROR_INVALID_RESOURCE:
                ZimbraLog.extensions.debug("Invalid resource: " + errorMsg);
                throw ServiceException.PERM_DENIED("The specified resource is invalid.");
            case RESPONSE_ERROR_RESPONSE_TYPE:
                ZimbraLog.extensions.info("Requested response type is not supported: " + errorMsg);
                throw ServiceException.OPERATION_DENIED(
                    "Requested response type is not supported by the social service.");
            default:
                ZimbraLog.extensions
                    .warn("Unexpected error while trying to authenticate the user: " + errorMsg);
                throw ServiceException.PERM_DENIED("Unable to authenticate the user.");
            }
        }

        // ensure the tokens we requested are present
        if (!response.has("access_token") || !response.has("refresh_token")) {
            throw ServiceException.PARSE_ERROR("Unexpected response from mail server.", null);
        }
    }

    @Override
    protected JsonNode getToken(OAuthInfo authInfo, String basicToken) throws ServiceException {
        final String datasourceType = authInfo.getParam(typeKey);
        final Map<String, String> headers = new HashMap<String, String>();
        // see https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow
        // outlook requires scope to be added to token requests with the introduction of graph api scopes
        if (OAuth2Constants.DEFAULT_PROXY_TYPE.getValue().equals(datasourceType)) {
            final String scope = buildScopeString(authInfo.getAccount(), datasourceType)
                // scope delimiter varies depending on scopes. old contact: plus. noop: comma
                // at the time of this writing microsoft docs incorrectly specify space delimiter
                .replace("+", ",");
            ZimbraLog.extensions.debug("Adding %s's scope: %s to get token request.", client, scope);
            headers.put("scope", scope);
        }
        return getTokenRequest(authInfo, basicToken, headers);
    }
}
