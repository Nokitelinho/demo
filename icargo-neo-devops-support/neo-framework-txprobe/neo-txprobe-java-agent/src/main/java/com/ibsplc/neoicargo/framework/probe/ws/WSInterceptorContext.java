/*
 * @(#) WSInterceptorContext.java 1.0 Dec 03, 2010
 * Copyright 2004 -2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.neoicargo.framework.probe.ws;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.apache.cxf.message.Message;

/*
 * Revision History
 * Revision 		Date      		Author			    Description
 * 1.0          Dec 03, 2010 		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Getter
@Setter
public class WSInterceptorContext implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String service;
	private String operation;
	private String port;
	private String soapAction;
	private String faultCode;
	private String faultCodeDesc;
	private boolean isFault;
	private List<Object> xmlTypes;
	
	private String inMessage;
	private String outMessage;	
	
	public WSInterceptorContext(Message message){
		update(message);
	}
	
	public void update(Message message){
		resolveSoapAction(message);
		if(message.getContent(Exception.class) != null){
			Exception fault = message.getContent(Exception.class);
			faultCode = fault.getMessage();
			faultCodeDesc = fault.getLocalizedMessage();
		}
	}
	
	private void resolveSoapAction(Message message){
		if(message.get(Message.WSDL_SERVICE) != null)
			service = message.get(Message.WSDL_SERVICE).toString();
		if(message.get(Message.WSDL_PORT) != null)
			port = message.get(Message.WSDL_PORT).toString();
		if(message.get(Message.WSDL_OPERATION) != null){
			operation = message.get(Message.WSDL_OPERATION).toString();
			// test :)
			if(message.get(Message.WSDL_OPERATION) instanceof javax.xml.namespace.QName){
				javax.xml.namespace.QName qName = (javax.xml.namespace.QName)message.get(Message.WSDL_OPERATION);
				soapAction = qName.getLocalPart();
			}
		}
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public String getFaultCodeDesc() {
		return faultCodeDesc;
	}

	public void setFaultCodeDesc(String faultCodeDesc) {
		this.faultCodeDesc = faultCodeDesc;
	}

	public boolean isFault() {
		return isFault;
	}

	public void setFault(boolean fault) {
		isFault = fault;
	}

	public List<Object> getXmlTypes() {
		return xmlTypes;
	}

	public void setXmlTypes(List<Object> xmlTypes) {
		this.xmlTypes = xmlTypes;
	}

	public String getInMessage() {
		return inMessage;
	}

	public void setInMessage(String inMessage) {
		this.inMessage = inMessage;
	}

	public String getOutMessage() {
		return outMessage;
	}

	public void setOutMessage(String outMessage) {
		this.outMessage = outMessage;
	}
}
