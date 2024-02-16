/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.tk.MRADefaultsTKController.java
 *
 *	Created by	:	a-4809
 *	Created on	:	Oct 21, 2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.tk;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.DSNRouting;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.tk.MRADefaultsTKController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-4809	:	Oct 17, 2014	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class MRADefaultsTKController {

	private Log log = LogFactory.getLogger("MRA:DEFAULTS:TK");
	private static final String Class_Name = "MRADefaultsTKController";

	/**
	 * 	Method		:	MRADefaultsTKController.saveDSNRoutingDetails
	 *	Added by 	:	a-4809 on Oct 17, 2014
	 * 	Used for 	:	ICRD-68924 SAP CR TK specific flow
	 *	Parameters	:	@param dsnRoutingVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void saveDSNRoutingDetails(Collection<DSNRoutingVO> dsnRoutingVOs) throws SystemException{
		log.entering(Class_Name, "saveDSNRoutingDetails");
		log.log(Log.INFO, "dsnRoutingVOs in cntrlr-->", dsnRoutingVOs);
		int count=1;
		for(DSNRoutingVO dsnRoutingVO:dsnRoutingVOs){
			DSNRouting dsnRouting = null;
			if (OPERATION_FLAG_UPDATE.equals(dsnRoutingVO
					.getOperationFlag())) {
				try {
					//Modified as part of MRA revamp activity
					dsnRouting = DSNRouting.find(dsnRoutingVO.getCompanyCode(),dsnRoutingVO.getMailSequenceNumber(),dsnRoutingVO.getRoutingSerialNumber());
				} catch (FinderException finderException) {
					log
					.log(Log.SEVERE,
							"FINDER EXCEPTION OCCURED IN FINDING DSNRouting Entity");
					throw new SystemException(finderException.getErrorCode());
				}
				if (dsnRouting != null) {
					dsnRouting.remove();
				}
			}
			else if (OPERATION_FLAG_DELETE.equals(dsnRoutingVO
					.getOperationFlag())) {
				try {
					//Modified as part of MRA revamp activity
					dsnRouting = DSNRouting.find(dsnRoutingVO.getCompanyCode(),dsnRoutingVO.getMailSequenceNumber(),dsnRoutingVO.getRoutingSerialNumber());
				} catch (FinderException finderException) {
					log
					.log(Log.SEVERE,
							"FINDER EXCEPTION OCCURED IN FINDING DSNRouting Entity");
					throw new SystemException(finderException.getErrorCode());
				}
				if (dsnRouting != null) {
					dsnRouting.remove();
				}
			}
		}
		/**
		 * Method updateFlightSegment to update the flight segment details for 
		 * ICRD-68924 SAP CR.Last updated time of the flight segment need to be
		 * stamped.TK specific change.
		 * Value updated in CRAFLTSEG table while updating dsn routing
		 */
		DSNRouting.updateFlightSegment(dsnRoutingVOs.iterator().next());
		for(DSNRoutingVO dsnRoutingVO:dsnRoutingVOs){

			if (OPERATION_FLAG_INSERT.equals(dsnRoutingVO
					.getOperationFlag())||OPERATION_FLAG_UPDATE.equals(dsnRoutingVO
							.getOperationFlag())) {

				dsnRoutingVO.setRoutingSerialNumber(count);

				new DSNRouting(dsnRoutingVO);

			}
			count++;
		}
		log.exiting(Class_Name, "saveDSNRoutingDetails");

	}

}
