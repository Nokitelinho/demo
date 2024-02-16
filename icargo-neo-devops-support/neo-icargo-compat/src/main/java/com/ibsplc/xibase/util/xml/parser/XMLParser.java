

package com.ibsplc.xibase.util.xml.parser;

import com.ibsplc.ibase.servicelocator.exception.ParserException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.Map;


/**
 * This class handles the parsing of the ServerProperties.xml
 * @author A-2394 - Acts a <code>SAXParserFactory</code> registry
 */
public class XMLParser {

	/** A singleton registry for SAX Parser Factories */
	private static SAXParserFactory SPF_NON_VALIDATING_FACTORY = createSAXParserFactory(false, false);
	private static SAXParserFactory SPF_VALIDATING_FACTORY = createSAXParserFactory(true, false);
	private static SAXParserFactory SPF_NS_AWARE_FACTORY = createSAXParserFactory(false, true);
	private static SAXParserFactory SPF_NS_AWARE_VALIDATING_FACTORY = createSAXParserFactory(true, true);
	
	/** Singleton Registry for <code>DocumentBuilderFactories</code> */
	private static DocumentBuilderFactory DBF_NON_VALIDATING_FACTORY = createDOMBuilderFactory(false, false);
	private static DocumentBuilderFactory DBF_VALIDATING_FACTORY = createDOMBuilderFactory(true, false);
	private static DocumentBuilderFactory DBF_NS_AWARE_FACTORY = createDOMBuilderFactory(false, true);
	private static DocumentBuilderFactory DBF_NS_AWARE_VALIDATING_FACTORY = createDOMBuilderFactory(true, true);
	
	/**
	 * @author A-2394
	 * @param validating
	 * @param nsAware
	 * @return
	 */
	static final synchronized SAXParserFactory createSAXParserFactory(boolean validating, boolean nsAware){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		if(validating)
			factory.setValidating(validating);
		if(nsAware){
			factory.setNamespaceAware(nsAware);
		}
		return factory;
	}
	
	/**
	 * @author A-2394
	 * @param validating
	 * @param nsAware
	 * @return
	 */
	static final synchronized DocumentBuilderFactory createDOMBuilderFactory(boolean validating, boolean nsAware){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		if(validating)
			factory.setValidating(validating);
		if(nsAware){
			factory.setNamespaceAware(nsAware);
		}
		return factory;
	}
	
	/**
	 * Method to provide a cached SAXParserFactory
	 * @author A-2394
	 * @param validating
	 * @param nsAware
	 * @return - SAXParserFactory
	 */
	public static SAXParserFactory getSAXParserFactory(boolean validating, boolean nsAware){
		int decision = ( validating ? 1 : 0 ) + ( nsAware ? 4 : 2 );
		switch( decision ){
		   case 2 : return SPF_NON_VALIDATING_FACTORY;
		   case 3 : return SPF_VALIDATING_FACTORY;
		   case 4 : return SPF_NS_AWARE_FACTORY;
		   case 5 : return SPF_NS_AWARE_VALIDATING_FACTORY;
		}
		throw new IllegalStateException("no mans land !");
	}
	
	/**
	 * Method to provide a cached DocumentBuilderFactory
	 * @author A-2394
	 * @param validating
	 * @param nsAware
	 * @return - DocumentBuilderFactory
	 */
	public static DocumentBuilderFactory getDOMBuilderFactory(boolean validating, boolean nsAware){
		int decision = ( validating ? 1 : 0 ) + ( nsAware ? 4 : 2 );
		switch( decision ){
		   case 2 : return DBF_NON_VALIDATING_FACTORY;
		   case 3 : return DBF_VALIDATING_FACTORY;
		   case 4 : return DBF_NS_AWARE_FACTORY;
		   case 5 : return DBF_NS_AWARE_VALIDATING_FACTORY;
		}
		throw new IllegalStateException("no mans land !");
	}
	
	/**
	 * This method will return a collection of  key value pair server names and the properties associated with each server.
	 * @param url String representing the url of the xml file
	 * @return Map key value pair of server name and properties associated with each server.
	 */
	public Map<String, Object> parse(String content) throws ParserException {

  		try {
			SAXParserFactory fact = getSAXParserFactory(false, false);
			SAXParser sp = fact.newSAXParser();
	
			XmlContentHandler contentHandler = new XmlContentHandler();
   			XMLErrorHandler errorHandler = new XMLErrorHandler();
			XMLReader reader = sp.getXMLReader();
			reader.setContentHandler(contentHandler);
			 // Register the error handler
			reader.setErrorHandler(errorHandler);
	
			// Turn off validation
			reader.setFeature("http://xml.org/sax/features/validation",false);
	
	
			// Turn off namespace awareness
			reader.setFeature("http://xml.org/sax/features/namespaces",false);
		   // Parse the document
			reader.parse(new InputSource(new StringReader(content)));
		   //sp.parse(url,handler);
			return contentHandler.getServices();  	
	  	} catch (Exception e) {
	    	e.printStackTrace();
	    	throw new ParserException(e.getMessage());
	  	}
	}
}