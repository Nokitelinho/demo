/*
* @(#) QueryDefinitionsParser.java 1.0 Apr 27, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query.parser;

import com.ibsplc.xibase.util.xml.parser.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * A SAX parser for parsing the query definition files.
 * 
 */
/*
 * Revision History
 * Revision         Date                Author          Description
 * 1.0              Apr 27, 2005        Binu K          First draft
 */
public class QueryDefinitionsParser {

	static final Logger logger = LoggerFactory.getLogger(QueryDefinitionsParser.class);

	public static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	public static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	public static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
	QueryDefinitionsContentHandler qdch = new QueryDefinitionsContentHandler();

	public HashMap<String, String> getMappings(Types type) {
		return qdch.getMappings(type);
	}

	public void parse(InputStream is) {
		try {
			SAXParserFactory parserFactory = XMLParser.getSAXParserFactory(true, true);
			SAXParser saxParser = parserFactory.newSAXParser();
			saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			//saxParser.setProperty(JAXP_SCHEMA_SOURCE, new File(ConfigFileLocator.getXSDPath(PersistenceConstants.QUERY_MAPPING_FILE_XSD)));

			XMLReader xmlRreader = saxParser.getXMLReader();
			xmlRreader.setContentHandler(qdch);
			xmlRreader.setErrorHandler(qdch);
			xmlRreader.parse(new InputSource(is));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Error parsing query xml", e);
			throw new RuntimeException(e);
		}
	}


}
