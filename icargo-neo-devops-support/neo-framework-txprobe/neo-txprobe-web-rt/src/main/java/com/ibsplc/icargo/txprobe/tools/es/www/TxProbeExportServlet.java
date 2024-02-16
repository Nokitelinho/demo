/*
 * TxProbeExportServlet.java Created on 30-Dec-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es.www;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			30-Dec-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@WebServlet(urlPatterns = "/txprobe/export-web/*", name = "TxProbe Web Export", asyncSupported = true)
@VaadinServletConfiguration(ui = TxProbeExportWeb.class, productionMode = true, closeIdleSessions = true)
public class TxProbeExportServlet extends VaadinServlet{
	
	private static final long serialVersionUID = 453179535990301426L;

	
}
