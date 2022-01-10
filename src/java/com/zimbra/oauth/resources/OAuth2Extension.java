// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.resources;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.extension.ExtensionDispatcherServlet;
import com.zimbra.cs.extension.ExtensionException;
import com.zimbra.cs.extension.ZimbraExtension;
import com.zimbra.oauth.utilities.OAuth2Constants;

/**
 * The OAuth2Extension class.<br>
 * Registry point for the project's request handler.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.resources
 * @copyright Copyright © 2018
 */
public class OAuth2Extension implements ZimbraExtension {

    @Override
    public void destroy() {
        ExtensionDispatcherServlet.unregister(this);
    }

    @Override
    public String getName() {
        return OAuth2Constants.API_NAME.getValue();
    }

    @Override
    public void init() throws ExtensionException, ServiceException {
        ExtensionDispatcherServlet.register(this, new ZOAuth2Servlet());
        ExtensionDispatcherServlet.register(this, new ZOAuth2ProxyServlet());
    }

}
