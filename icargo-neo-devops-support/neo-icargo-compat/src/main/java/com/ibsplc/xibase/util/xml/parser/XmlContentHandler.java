
package com.ibsplc.xibase.util.xml.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;

/**
 * <b><code>ServerContentHandler</code></b> extends the
 *   <code>DefaultHandler</code> and defines callback
 *   behavior for the SAX callbacks associated with an XML
 *   document's content.
 */
public class XmlContentHandler extends DefaultHandler {

    private Map<String, Object> propertyMap;
    private String currentElement;

   
    public Map<String, Object> getServices(){
		return propertyMap;
	}

   
    /**
     * <p>
     * This indicates the start of a Document parse - this precedes
     *   all callbacks in all SAX Handlers with the sole exception
     *   of <code>{@link #setDocumentLocator}</code>.
     * </p>
     *
     * @throws <code>SAXException</code> when things go wrong
     */
    public void startDocument() throws SAXException {
        //serverPropertiessMap = new HashMap();
        propertyMap = new java.util.HashMap<String, Object>();        
    }

    /**
     * <p>
     * This indicates the end of a Document parse - this occurs after
     *   all callbacks in all SAX Handlers.</code>.
     * </p>
     *
     * @throws <code>SAXException</code> when things go wrong
     */
    public void endDocument() throws SAXException {
    }


    /**
     * <p>
     * This reports the occurrence of an actual element.  It will include
     *   the element's attributes, with the exception of XML vocabulary
     *   specific attributes, such as
     *   <code>xmlns:[namespace prefix]</code> and
     *   <code>xsi:schemaLocation</code>.
     * </p>
     *
     * @param namespaceURI <code>String</code> namespace URI this element
     *                     is associated with, or an empty
     *                     <code>String</code>
     * @param localName <code>String</code> name of element (with no
     *                  namespace prefix, if one is present)
     * @param rawName <code>String</code> XML 1.0 version of element name:
     *                [namespace prefix]:[localName]
     * @param atts <code>Attributes</code> list for this element
     * @throws <code>SAXException</code> when things go wrong
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes atts)
        throws SAXException {
			doStartElement(rawName,atts);

    }

    /**
	 * <p>
	 * This method will do the necessary actions to be done when the start of an element
	 *  is reached.
	 * </p>
	 *
	 * @param rawName Value of an element.
     */
    private void doStartElement(String rawName,Attributes atts)
    												throws SAXException{

		if (rawName.equals("map")) {
			currentElement = "";
		}
		else if (rawName.equals("map-item")){
			currentElement = "";
			String name = atts.getValue(0);
			String value  = atts.getValue(1);
			propertyMap.put(name,value);
		}else
			throw new SAXException("Element " + rawName + " cannot be handled by XMLContentHandler");			
	}

	/**
	 * <p>
	 * This method will do the necessary actions to be done when the end of an element
	 *  is reached.
	 * </p>
	 *
	 * @param rawName Value of an element.
     */
    private void doEndElement(String rawName){

	}

    /**
     * <p>
     * Indicates the end of an element
     *   (<code>&lt;/[element name]&gt;</code>) is reached.  Note that
     *   the parser does not distinguish between empty
     *   elements and non-empty elements, so this will occur uniformly.
     * </p>
     *
     * @param namespaceURI <code>String</code> URI of namespace this
     *                     element is associated with
     * @param localName <code>String</code> name of element without prefix
     * @param rawName <code>String</code> name of element in XML 1.0 form
     * @throws <code>SAXException</code> when things go wrong
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName)
					        throws SAXException {
        doEndElement(rawName);
    }


}

