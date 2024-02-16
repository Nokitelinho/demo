/*
 * Utils.java Created on 20-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.AntPathMatcher;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			20-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class Utils {

    private static AntPathMatcher matcher = new AntPathMatcher("/");

    private static XMLInputFactory INPUT_FACTORY;
    private static JsonFactory JSON_FACTORY;
    private static ObjectMapper DEFAULT_MAPPER;
    static boolean disableSqlFormatting = Boolean.getBoolean("txprobe.export.disableSqlFormatting");

    static {
        INPUT_FACTORY = XMLInputFactory.newFactory();
        INPUT_FACTORY.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        INPUT_FACTORY.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
        INPUT_FACTORY.setProperty(XMLInputFactory.IS_VALIDATING, Boolean.FALSE);

        JSON_FACTORY = new JsonFactory();
        DEFAULT_MAPPER = new ObjectMapper(JSON_FACTORY);
    }

    /**
     * Returns human readable format for byte size.
     *
     * @param bytes
     * @param si
     * @return
     */
    public static String formatBytes(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static boolean shouldReject(String predicate, String[] conditions, boolean path) {
        // if unconditional
        if (conditions == null || conditions.length == 0)
            return false;
        // no predicate
        if (predicate == null)
            return true;
        // unquote it
        if (predicate.startsWith("\"") && predicate.endsWith("\""))
            predicate = predicate.substring(1, predicate.length() - 1);
        boolean present = false;
        if (path) {
            for (int x = 0; x < conditions.length && !present; x++) {
                String pattern = conditions[x];
                present = matcher.match(pattern, predicate);
            }
        } else
            present = Arrays.binarySearch(conditions, predicate) >= 0;
        return !present;
    }

    /**
     * Method to retrieve the element values of the xml, this method is not namespace aware
     *
     * @param source   - the xml string to be parsed
     * @param maxDepth - max traverse depth
     * @param headers  - the element names , the values which is to be parsed
     * @return - <code>java.util.Map</code> of the elementName and elementText
     * @throws XMLStreamException
     */
    public static Map<String, String> parseXmlElements(String source, int maxDepth, String... headers) throws XMLStreamException {
        StringReader sr = new StringReader(source);
        XMLStreamReader reader = INPUT_FACTORY.createXMLStreamReader(sr);
        Map<String, String> answer = new HashMap<>(headers.length);
        Arrays.sort(headers);
        reader.getEventType(); // the start document
        for (int depth = 0; depth < maxDepth && answer.size() < headers.length && reader.hasNext(); ) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT) {
                depth++;
                String localPart = reader.getLocalName();
                if (Arrays.binarySearch(headers, localPart) >= 0 && !answer.containsKey(localPart))
                    answer.put(localPart, reader.getElementText());
            }
        }
        reader.close();
        return answer;
    }

    /**
     * Method to retrieve the element values of the xml, this method is not namespace aware
     *
     * @param xml     - the xml string to be parsed
     * @param headers - the element names , the values which is to be parsed
     * @return - <code>java.util.Map</code> of the elementName and elementText
     * @throws XMLStreamException
     */
    public static Map<String, String> parseXmlElements(String xml, String... headers) throws XMLStreamException {
        return parseXmlElements(xml, Integer.MAX_VALUE, headers);
    }

    /**
     * Method to retrieve fields from the json string.
     *
     * @param source
     * @param fields
     * @return
     * @throws JsonParseException
     * @throws IOException
     */
    public static Map<String, String> parseJsonFields(String source, String... fields)
            throws JsonParseException, IOException {
        JsonParser parser = JSON_FACTORY.createParser(source);
        parser.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        Map<String, String> answer = new HashMap<>(fields.length);
        Arrays.sort(fields);

        for (JsonToken token = parser.nextToken(); answer.size() < fields.length && token != null; token = parser.nextToken()) {
            if (JsonToken.FIELD_NAME == token) {
                String name = parser.getCurrentName();
                if (Arrays.binarySearch(fields, name) >= 0 && !answer.containsKey(name)) {
                    token = parser.nextToken();
                    switch (token) {
                        case END_ARRAY:
                        case END_OBJECT:
                        case FIELD_NAME:
                        case NOT_AVAILABLE:
                        case VALUE_EMBEDDED_OBJECT:
                        case START_OBJECT:
                            break;
                        case START_ARRAY:
                            boolean isFirst = true;
                            StringBuilder sbul = new StringBuilder();
                            for (JsonToken child = parser.nextToken(); child != null && child != JsonToken.END_ARRAY; child = parser.nextToken()) {
                                if (isFirst) {
                                    sbul.append('[');
                                    isFirst = false;
                                } else {
                                    sbul.append(", ");
                                }
                                sbul.append(parser.getValueAsString());
                            }
                            sbul.append(']');
                            answer.put(name, sbul.toString());
                            break;
                        case VALUE_FALSE:
                        case VALUE_NULL:
                        case VALUE_NUMBER_FLOAT:
                        case VALUE_NUMBER_INT:
                        case VALUE_STRING:
                        case VALUE_TRUE:
                            answer.put(name, parser.getValueAsString());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        parser.close();
        return answer;
    }

    /**
     * Method to format Json string objects
     *
     * @param source
     * @return
     * @throws JsonParseException
     * @throws IOException
     */
    public static String formatJson(String source) throws JsonParseException, IOException {
        Object json = DEFAULT_MAPPER.readValue(source, Object.class);
        source = DEFAULT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        return source;
    }

    /**
     * Method to format xml string
     *
     * @param rawXml
     * @return
     * @author a-2394
     */
    public static <T extends CharSequence> CharSequence formalXml(T rawXml) {
        final int TAB_WIDTH = 3;
        int saved = 0, len = rawXml.length(), i1, i;
        boolean atMargin = true;
        int thisIndent = -1, nextIndent = -1, previousIndent = -1;
        StringBuilder cw = new StringBuilder(len * 2);
        // Do XML Formatting
        boolean inXML = false;
        int bufferLen = saved;
        if (len != -1)
            bufferLen += len;
        i1 = 0;
        saved = 0;
        for (; i1 < bufferLen; i1++) {
            // Except when we're at EOF, saved last char
            if ((len != -1) && (i1 + 1 > bufferLen)) {
                saved = 1;
                break;
            }
            thisIndent = -1;
            if ((rawXml.charAt(i1) == '<') && (rawXml.charAt(i1 + 1) != '/')) {
                previousIndent = nextIndent++;
                thisIndent = nextIndent;
                inXML = true;
            }
            if ((rawXml.charAt(i1) == '<') && (rawXml.charAt(i1 + 1) == '/')) {
                if (previousIndent > nextIndent)
                    thisIndent = nextIndent;
                previousIndent = nextIndent--;
                inXML = true;
            }
            if ((rawXml.charAt(i1) == '/') && (rawXml.charAt(i1 + 1) == '>')) {
                previousIndent = nextIndent--;
                inXML = true;
            }
            if (thisIndent != -1) {
                if (thisIndent > 0)
                    cw.append('\n');
                for (i = TAB_WIDTH * thisIndent; i > 0; i--) {
                    cw.append(' ');
                }
            }
            atMargin = ((rawXml.charAt(i1) == '\n') || (rawXml.charAt(i1) == '\r'));
            if (!inXML || !atMargin)
                cw.append(rawXml.charAt(i1));
        }
        return cw;
    }

    /**
     * Method to format sql statements, This method only supports Oracle dialect sql.
     *
     * @param sql
     * @return formatted Sql statement
     * @see <code>-Dtxprobe.export.disableSqlFormatting</code>
     */
    public static String formatSql(String sql) {
        if (disableSqlFormatting)
            return sql;
        return OracleSqlFormatter.formatSql(sql);
    }
}
