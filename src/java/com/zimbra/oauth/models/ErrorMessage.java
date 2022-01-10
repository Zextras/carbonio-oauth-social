// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The ErrorMessage class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.models
 * @copyright Copyright © 2019
 */
@XmlRootElement
public class ErrorMessage {

    /**
     * Error code.
     */
    protected String code;

    /**
     * Error message.
     */
    protected String message;

    /**
     * @param code The error code
     */
    public ErrorMessage(String code) {
        this(code, null);
    }

    /**
     * @param code The error code
     * @param message The error message
     */
    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The error message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
