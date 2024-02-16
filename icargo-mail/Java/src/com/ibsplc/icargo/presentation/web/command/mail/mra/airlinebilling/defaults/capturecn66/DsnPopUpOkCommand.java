/*
 * DsnPopUpOkCommand.java Created on Oct-28-2008 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.s
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;
import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3434
 *
 */

public class DsnPopUpOkCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "DsnPopUpOkCommand";

	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String ACTION_SUCCESS = "action_success";
	private static final String DSNPOPUP_MODULE = "mailtracking.mra.defaults";
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	/**
	 * *Oct-28-2008,a-3434
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *             Execute Method for showing Dsn Pop UP
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
	
	log.entering(CLASS_NAME, MODULE_NAME);
	DSNPopUpSession dSNPopUpSession = getScreenSession( DSNPOPUP_MODULE,DSNPOPUP_SCREENID);
	CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
			MODULE_NAME, SCREEN_ID);
	CN66DetailsForm cN66DetailsForm = (CN66DetailsForm) invocationContext.screenModel;
	
	ArrayList<AirlineCN66DetailsVO> airlineCN66DetailsVOsForadding=session.getCn66Details();
	//ArrayList<AirlineCN66DetailsVO> airlineCN66DetailsVOsForadding=new ArrayList<AirlineCN66DetailsVO>();
	DSNPopUpVO dSNPopUpVO=dSNPopUpSession.getSelectedDespatchDetails();
	//AirlineCN66DetailsVO airlineCN66DetailsVO=session.getAirlineCN66DetailsVO();
	AirlineCN66DetailsVO airlineCN66DetailsVO=new AirlineCN66DetailsVO();
	airlineCN66DetailsVO = airlineCN66DetailsVOsForadding.get(airlineCN66DetailsVOsForadding.size()-1);
	log.log(Log.INFO, "dSNPopUpVO ---", dSNPopUpVO);
	log.log(Log.INFO, "airlineCN66DetailsVO-in dsnok end--",
			airlineCN66DetailsVO);
	Integer count=cN66DetailsForm.getCount();
	
	log.log(Log.INFO, "count ---", count);
	//	setting latest vo
	//if(airlineCN66DetailsVOsForadding!=null){
	//for(AirlineCN66DetailsVO airlineCN66DetailsVO: airlineCN66DetailsVOs){
	airlineCN66DetailsVO.setCsgdocnum(dSNPopUpVO.getCsgdocnum());
	airlineCN66DetailsVO.setCsgseqnum(dSNPopUpVO.getCsgseqnum());
	airlineCN66DetailsVO.setPoaCode(dSNPopUpVO.getGpaCode());
	airlineCN66DetailsVO.setDespatchSerialNo(dSNPopUpVO.getDsn());
	airlineCN66DetailsVO.setBillingBasis(dSNPopUpVO.getBlgBasis());
	LocalDate dsnDate=new LocalDate
	(LocalDate.NO_STATION,Location.NONE,false).setDate(dSNPopUpVO.getDsnDate());
	log.log(Log.FINE, "dsnDate**-->>", dsnDate);
	//Added for ICRD-101113
	airlineCN66DetailsVO.setDespatchDate(dsnDate);
	airlineCN66DetailsVO.setReceptacleSerialNo(dSNPopUpVO.getReceptacleSerialNo());
	airlineCN66DetailsVO.setHni(dSNPopUpVO.getHni());
	airlineCN66DetailsVO.setRegInd(dSNPopUpVO.getRegInd());
	
	airlineCN66DetailsVO.setMalSeqNum((dSNPopUpVO.getMailSequenceNumber()));
	if(dSNPopUpVO.getBlgBasis()!=null && dSNPopUpVO.getBlgBasis().length()>20){
		airlineCN66DetailsVO.setDsnIdr(dSNPopUpVO.getBlgBasis().substring(0, 20));
	}
	
	
	log.log(Log.INFO, "airlineCN66DetailsVOsForadding -in dsnok end--",
			airlineCN66DetailsVOsForadding);
	session.setCn66Details(airlineCN66DetailsVOsForadding);
	
	invocationContext.target = ACTION_SUCCESS;
	}

}
