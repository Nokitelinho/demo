/**
 *	Java file	: 	com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa.USPSResditReceiptService.java
 *
 *	Created by	:	A-7540
 *	Created on	:	08-May-2019
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa.USPSResditReceiptResponse;

/**
 * @author A-7540
 *
 */
@Path("/")
public interface USPSResditReceiptService {
	    @GET
	    @Consumes({"application/json"})
	    @Produces({"application/json"})
	    USPSResditReceiptResponse  getResditInfofromUSPS(@QueryParam("mailbagId") String mailbagId);

}
