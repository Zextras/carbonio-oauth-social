// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.oauth.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;

/**
 * The OAuth2Utilities class.
 *
 * @author Zimbra API Team
 * @package com.zimbra.oauth.utilities
 * @copyright Copyright © 2018
 */
public class OAuth2JsonUtilities {

    /**
     * A mapper object that can convert between Java <-> JSON objects.
     */
    private static final ObjectMapper mapper = createDefaultMapper();

    /**
     * Creates a mapper that can convert between Java <-> JSON objects.
     *
     * @return mapper A mapper object
     */
    protected static ObjectMapper createDefaultMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return mapper;
    }

    /**
     * Reads a given string into a json node.<br>
     * Returns null if input is empty.
     *
     * @param jsonString The string to read
     * @return A json node
     * @throws IOException If there are issues parsing the string
     */
    public static JsonNode stringToJson(String jsonString) throws IOException {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        return mapper.readTree(jsonString);
    }

    /**
     * Reads a given string into a json node.<br>
     * Returns null if input is empty.
     *
     * @param jsonString The string to read
     * @return A json node
     * @throws ServiceException If there are issues parsing the string
     */
    public static String objectToJson(Object object) throws ServiceException {
        if (object == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            ZimbraLog.extensions.error("Error writing object as json.", e);
            throw ServiceException.PARSE_ERROR("Error writing output.", e);
        }
    }

    /**
     * Reads an input stream into a map.
     *
     * @param stream The stream to read from
     * @return An instance of Map
     * @throws ServiceException If there are issues parsing
     */
    public static Map<String, Object> streamToMap(InputStream stream) throws ServiceException {
        if (stream == null) {
            return Collections.emptyMap();
        }
        try {
            return mapper.readValue(stream, mapper.getTypeFactory()
                .constructMapType(Map.class, String.class, Object.class));
        } catch (final IOException e) {
            ZimbraLog.extensions.error("Error reading object as json.", e);
            throw ServiceException.PARSE_ERROR("Error reading input.", e);
        }
    }

    /**
     * Reads a string into a map.
     *
     * @param jsonString The string to read from
     * @return An instance of Map
     * @throws ServiceException If there are issues parsing
     */
    public static Map<String, Object> stringToMap(String jsonString) throws ServiceException {
        if (jsonString == null) {
            return Collections.emptyMap();
        }
        try {
            return mapper.readValue(jsonString, mapper.getTypeFactory()
                .constructMapType(Map.class, String.class, Object.class));
        } catch (final IOException e) {
            ZimbraLog.extensions.error("Error reading object as json.", e);
            throw ServiceException.PARSE_ERROR("Error reading input.", e);
        }
    }

    /**
     * Reads a byte array into a map.
     *
     * @param jsonBytes The bytes to read from
     * @return An instance of Map
     * @throws ServiceException If there are issues parsing
     */
    public static Map<String, Object> bytesToMap(byte[] jsonBytes) throws ServiceException {
        if (jsonBytes == null) {
            return Collections.emptyMap();
        }
        try {
            return mapper.readValue(jsonBytes, mapper.getTypeFactory()
                .constructMapType(Map.class, String.class, Object.class));
        } catch (final IOException e) {
            ZimbraLog.extensions.error("Error reading object as json.", e);
            throw ServiceException.PARSE_ERROR("Error reading input.", e);
        }
    }
}
