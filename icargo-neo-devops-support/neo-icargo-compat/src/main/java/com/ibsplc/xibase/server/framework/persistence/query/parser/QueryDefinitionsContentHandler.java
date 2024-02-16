/*
 * @(#) QueryDefinitionsContentHandler.java 1.0 Apr 27, 2005
 * Copyright 2005 -2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query.parser;

import com.ibsplc.xibase.server.framework.persistence.query.QueryMappings;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

/**
 * A {@link DefaultHandler} for the SAX parser
 * while parsing query definition files.
 * <p>
 * Returns a HashMap of procedure and query sources.
 *
 * @author A-1456
 */

/*
 * Revision History
 * Revision         Date                Author          Description
 * 1.0              Apr 27, 2005        Binu K          First draft
 */

public class QueryDefinitionsContentHandler extends DefaultHandler {

    private StringBuilder buffer;

    private HashMap<String, String> qryMappings;

    private HashMap<String, String> proMappings;

    private boolean processCharacterData;

    private String queryName;

    private Types queryType;

    /**
     * Get Query/Procedure mappings based on the type
     */
    public HashMap<String, String> getMappings(Types type) {
        switch (type) {
            case NATIVE:
                return qryMappings;
            case PROCEDURE:
                return proMappings;
        }
        return null;
    }

    private void createProcMap() {
        if (proMappings == null) {
            proMappings = new HashMap<String, String>();
        }
    }

    private void createQryMap() {
        if (qryMappings == null) {
            qryMappings = new HashMap<String, String>();
        }
    }

    private void createMap(Types type) {
        switch (type) {
            case NATIVE:
                createQryMap();
                break;
            case PROCEDURE:
                createProcMap();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (processCharacterData) {
            buffer.append(ch, start, length);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.endDocument();
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        processCharacterData = false;
        String element = qName.trim();
        if (QueryMappings.QUERY_SOURCE.equals(element)) {
            popMaps(queryType, queryName, buffer.toString().trim());
        }
    }

    private void popMaps(Types type, String name, String query) {
        switch (type) {
            case NATIVE:
                qryMappings.put(name, query);
                break;
            case PROCEDURE:
                proMappings.put(name, query);
        }
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        super.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        super.fatalError(e);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        String element = qName.trim();
        String type = null;
        if (QueryMappings.QUERY_SOURCE.equals(element)) {
            type = attributes.getValue(QueryMappings.TYPE_ATTRIBUTE);
            queryType = Types.valueOf(type);
            queryName = attributes.getValue(QueryMappings.NAME_ATTRIBUTE);
            createMap(queryType);
            buffer = null;
            buffer = new StringBuilder();
            processCharacterData = true;
        }
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        super.warning(e);
    }

}
