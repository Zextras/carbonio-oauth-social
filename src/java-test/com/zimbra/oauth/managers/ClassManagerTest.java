// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.managers;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.matches;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.zimbra.common.service.ServiceException;
import com.zimbra.oauth.handlers.IOAuth2Handler;
import com.zimbra.oauth.utilities.Configuration;
import com.zimbra.oauth.utilities.LdapConfiguration;
import com.zimbra.oauth.utilities.OAuth2ConfigConstants;

/**
 * Test class for {@link ClassManager}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ClassManager.class, LdapConfiguration.class })
public class ClassManagerTest {

    /**
     * Mock config for testing.
     */
    protected Configuration mockConfig;

    /**
     * Handler cache map for testing.
     */
    protected Map<String, IOAuth2Handler> handlerCacheMap;

    /**
     * Test client.
     */
    protected final String client = "yahoo";

    /**
     * Setup for tests.
     *
     * @throws Exception If there are issues mocking
     */
    @Before
    public void setUp() throws Exception {
        PowerMock.mockStatic(LdapConfiguration.class);

        // set the handler cache for reference during tests
        handlerCacheMap = new HashMap<String, IOAuth2Handler>();
        Whitebox.setInternalState(ClassManager.class, "handlersCache", handlerCacheMap);

        mockConfig = EasyMock.createMock(LdapConfiguration.class);
    }

    /**
     * Test method for {@link ClassManager#getHandler}<br>
     * Validates that a client handler is created and cached.
     *
     * @throws Exception If there are issues testing
     */
    @Test
    public void testGetHandler() throws Exception {
        LdapConfiguration.buildConfiguration(anyObject(String.class));
        PowerMock.expectLastCall().andReturn(mockConfig);
        expect(mockConfig.getString(matches(OAuth2ConfigConstants.LC_HANDLER_CLASS_PREFIX.getValue() + client)))
            .andReturn("com.zimbra.oauth.handlers.impl.YahooOAuth2Handler");

        PowerMock.replay(LdapConfiguration.class);
        replay(mockConfig);

        ClassManager.getHandler(client);

        PowerMock.verify(LdapConfiguration.class);
        verify(mockConfig);
        assertEquals(1, handlerCacheMap.size());
        assertNotNull(handlerCacheMap.get(client));
    }

    /**
     * Test method for {@link ClassManager#getHandler}<br>
     * Validates that creating a handler for a bad client fails as expected.
     *
     * @throws Exception If there are issues testing
     */
    @Test
    public void testGetHandlerBadClient() throws Exception {
        final String badClient = "not-a-client";
        LdapConfiguration.buildConfiguration(matches(badClient));
        PowerMock.expectLastCall().andThrow(ServiceException.UNSUPPORTED());

        PowerMock.replay(LdapConfiguration.class);

        try {
            ClassManager.getHandler(badClient);
        } catch (final ServiceException e) {
            PowerMock.verify(LdapConfiguration.class);
            assertEquals(ServiceException.UNSUPPORTED, e.getCode());
            return;
        }
        fail("Expected exception to be thrown for bad client name.");
    }

    /**
     * Test method for {@link ClassManager#getHandler}<br>
     * Validates that a client handler is created and cached.
     *
     * @throws Exception If there are issues testing
     */
    @Test
    public void testGetProxyHandler() throws Exception {
        assertNotNull(ClassManager.getProxyHandler("static-basic-jira-zx"));
        assertNotNull(ClassManager.getProxyHandler("static-basic-jira-ufb"));
        assertNotNull(ClassManager.getProxyHandler("static-basic-jira-10005"));
    }

    /**
     * Test method for {@link ClassManager#getPrefix}<br>
     * Validates that the client is retrieved without suffix data.
     *
     * @throws Exception If there are issues testing
     */
    @Test
    public void testGetPrefix() {
        assertEquals("static-basic-jira", ClassManager.getPrefix("static-basic-jira-ufb"));
        assertEquals("static-basic-jira", ClassManager.getPrefix("static-basic-jira-10005"));
        assertEquals("static-basic-jira", ClassManager.getPrefix("static-basic-jira-10501"));
        assertEquals("static-basic-jira", ClassManager.getPrefix("static-basic-jira-ufb-10501"));
        assertEquals("static-basic-jira", ClassManager.getPrefix("static-basic-jira-zx"));
        assertEquals("static-basic-jira", ClassManager.getPrefix("static-basic-jira"));
        assertEquals("basic-jira", ClassManager.getPrefix("basic-jira"));
    }
}
