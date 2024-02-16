/**
 * GenerateMailImportManifestCommand.java Created on June 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.arrivemail.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ArriveDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-4810
 *
 */
public class GenerateMailImportManifestCommand extends AbstractPrintCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String REPORT_ID = "RPRMTK080";
	private static final String ACTION = "generateMailImportManifest";	
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String PRODUCT_NAME = "mailtracking";
	private static final String SUBPRODUCT_NAME = "defaults";
	private static final String RESOURCE_BUNDLE = "arrivingMailResources";
	
	private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";
	private static final String INBOUND = "I";
	/**
	 * @param  invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		
		 log.entering("GenerateMailImportManifestCommand","execute");
		 
		 ArriveDispatchForm arriveDispatchForm = 
			 (ArriveDispatchForm)invocationcontext.screenModel;
		 ArriveDispatchSession arriveDispatchSession = 
			 getScreenSession(MODULE_NAME,SCREEN_ID);
		 LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		 FlightValidationVO flightValidationVO = new FlightValidationVO();
		 flightValidationVO =arriveDispatchSession.getFlightValidationVO();
		 //
		 Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		 Collection<String> fieldTypes = new ArrayList<String>();
		 fieldTypes.add(CATEGORY);
		 try {
				oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			Map<String, Collection<OneTimeVO>> oneTimMap = (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
//
		 OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		 operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		 operationalFlightVO.setPou(logonAttributes.getAirportCode());
		 operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
		 operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		 operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		 operationalFlightVO.setFlightDate(flightValidationVO.getSta());
		 operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		 operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		 operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
		 operationalFlightVO.setDirection(INBOUND);
		 operationalFlightVO.setPol(flightValidationVO.getLegOrigin());

		 MailArrivalFilterVO mailArrivalFilterVO = arriveDispatchSession.getMailArrivalFilterVO();

		 if(mailArrivalFilterVO==null){
			 mailArrivalFilterVO = constructMailArrivalFilterVO(operationalFlightVO);

		 }
		 
		    ReportSpec reportSpec = getReportSpec();				
			reportSpec.setReportId(REPORT_ID);
			if(oneTimMap.get(CATEGORY)!=null){
			reportSpec.addExtraInfo(oneTimMap.get(CATEGORY)); 
			}
			reportSpec.setProductCode(PRODUCT_NAME);
			reportSpec.setSubProductCode(SUBPRODUCT_NAME);
			reportSpec.setPreview(true);
			reportSpec.setHttpServerBase(invocationcontext.httpServerBase);
			reportSpec.addFilterValue(mailArrivalFilterVO);
			reportSpec.setResourceBundle(RESOURCE_BUNDLE);
			//
			reportSpec.addParameter(mailArrivalFilterVO);
			reportSpec.setAction(ACTION);
			generateReport();
		 			
			
			
		 invocationcontext.target = getTargetPage();
		 log.exiting("GenerateMailImportManifest","execute");
	}
	
	
	private MailArrivalFilterVO constructMailArrivalFilterVO(
			 OperationalFlightVO operationalFlightVO) {
		 MailArrivalFilterVO filterVO = new MailArrivalFilterVO();
		 filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		 filterVO.setCarrierId(operationalFlightVO.getCarrierId());
		 filterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		 filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		 filterVO.setFlightSequenceNumber(
				 operationalFlightVO.getFlightSequenceNumber());
		 filterVO.setFlightDate(operationalFlightVO.getFlightDate());
		 filterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		 filterVO.setPou(operationalFlightVO.getPou());
		 filterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
		 return filterVO;
	}
}


