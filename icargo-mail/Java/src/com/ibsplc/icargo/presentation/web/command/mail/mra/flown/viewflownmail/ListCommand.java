/*
 * ListCommand.java Created on Feb 13, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO;;

/**
 * @author a-2449
 *
 */
public class ListCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA flown");
	private static final String ACTION_SUCCESS = "list_success";
	private static final String ACTION_FAILURE = "list_failure";
	private static final String CLASS_NAME = "ListCommand";
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREENID = "mra.flown.viewflownmail";
	
	private static final String NO_RESULTS_FOUND = "mra.flown.msg.err.noresultsfound";
	private static final String SYS_PARA_ACC_ENTRY="cra.accounting.isaccountingenabled";
	private static final String WEIGHT_UNIT_ONETIME="mail.mra.defaults.weightunit"; // added by A-9002
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");		
		ViewFlownMailSession session = 
			(ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREENID);
		ViewFlownMailForm form =
			(ViewFlownMailForm)invocationContext.screenModel;
		
		FlightValidationVO flightValidationVO = session.getFlightDetails();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<MailBagForFlownSegmentVO> mailBagForFlownSegmentVOs = new ArrayList<MailBagForFlownSegmentVO>();
		Collection<MailBagForFlownSegmentVO> mailBagForFlownSegmentVOs2 = new ArrayList<MailBagForFlownSegmentVO>();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs = session.getSegmentDetails();
		FlownMailFilterVO filterVO = new FlownMailFilterVO();
		FlownMailSegmentVO flownMailSegmentVO = new FlownMailSegmentVO();
		ErrorVO error = null;
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		
		if("Y".equals(form.getFromExceptionScreenFlag())){
			log.log(1,"From AssignExceptionsScreen...");
			filterVO = session.getListFilterDetails();
			form.setCarrierCode(filterVO.getFlightCarrierCode());
			String flightDate = filterVO.getFlightDate().toDisplayDateOnlyFormat();
			form.setFlightDate(flightDate);
			Integer segSerialNo = null;
			segSerialNo = filterVO.getSegmentSerialNumber();
			form.setSegment(segSerialNo.toString());
			log.log(Log.INFO, "segment---->", filterVO.getFlightSegment());
			form.setFromExceptionScreenFlag("");
			form.setListSegmentsFlag("Y");
//			 code for acc entry sys para starts
			Collection<String> systemParameterCodes = new ArrayList<String>();

			systemParameterCodes.add(SYS_PARA_ACC_ENTRY);

			Map<String, String> systemParameters = null;

			try {

				systemParameters = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);

			} catch (BusinessDelegateException e) {
				e.getMessage();
				invocationContext.addAllError(handleDelegateException(e));
			}

			String accountingEnabled = (systemParameters.get(SYS_PARA_ACC_ENTRY));
			log.log(Log.INFO, "IS acc enabled--->", accountingEnabled);
			if("N".equals(accountingEnabled)){
				form.setAccEntryFlag("N");
			}else{
				form.setAccEntryFlag("Y");
			}
//			code for acc entry sys para ends
			if(flownMailSegmentVOs.size() == 1){
				form.setOneSegmentFlag("Y");
			}
		}
		else{
			String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
			filterVO.setCompanyCode(companyCode);
			filterVO.setFlightCarrierCode(form.getCarrierCode());
			filterVO.setFlightNumber(form.getFlightNumber());
			
			log.log(Log.INFO, "flight date from form------->", form.getFlightDate());
			LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			flightDate.setDate(form.getFlightDate());
			filterVO.setFlightDate(flightDate);
			
			
			filterVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
			filterVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
			
			if(flownMailSegmentVOs.size() == 1){
				log.log(1,"<-------Only one segment present------>");
				FlownMailSegmentVO segmentVO = 
					((ArrayList<FlownMailSegmentVO>)flownMailSegmentVOs).get(0);
				log.log(Log.INFO, "Segment details before listing----->",
						segmentVO);
				filterVO.setSegmentSerialNumber(segmentVO.getSegmentSerialNumber());
				form.setListSegmentsFlag("Y");
				form.setOneSegmentFlag("Y");
				log.log(Log.INFO, "ListSegmentsFlag----->", form.getListSegmentsFlag());
				log.log(Log.INFO, "OneSegmentFLag------>", form.getOneSegmentFlag());
			}
			else{
				filterVO.setSegmentSerialNumber(Integer.parseInt(form.getSegment()));
			}
		}
		
		/*
		 * Delegate Call
		 */
		try{
			flownMailSegmentVO = new MailTrackingMRADelegate().findFlownMails(filterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
			
		}
		
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());
			
        	mailBagForFlownSegmentVOs = flownMailSegmentVO.getSegmentMailBags();
        	
            if(mailBagForFlownSegmentVOs.size() >0 ){
				
            	for (MailBagForFlownSegmentVO mailBagForFlownSegmentVO : mailBagForFlownSegmentVOs) {            		            
            	
			  	  if (mailBagForFlownSegmentVO.getDisplayWgtUnit() != null  && oneTimeValues != null && !oneTimeValues.isEmpty() && oneTimeValues.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeValues.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
					 for (OneTimeVO oneTimeVO : oneTimeValues.get(WEIGHT_UNIT_ONETIME)) {
						if (oneTimeVO.getFieldValue().equals(mailBagForFlownSegmentVO.getDisplayWgtUnit()) ) {																								
							mailBagForFlownSegmentVO.setDisplayWgtUnit(oneTimeVO.getFieldDescription());
							mailBagForFlownSegmentVOs2.add(mailBagForFlownSegmentVO);
						}
					 }
				  }else {
					        mailBagForFlownSegmentVOs2.add(mailBagForFlownSegmentVO);
				  }
            	}
            	flownMailSegmentVO.setSegmentMailBags(mailBagForFlownSegmentVOs2) ;	 
			}
            
		  
            
		log.log(Log.INFO, "FlownMailSegmentVO from command Class------>",
				flownMailSegmentVO);
		session.setListFilterDetails(filterVO);
		session.setFlownMailSegmentVO(flownMailSegmentVO);
		
		if((flownMailSegmentVO.getSegmentDSNs() == null || flownMailSegmentVO.getSegmentDSNs().size() == 0)
				&& (flownMailSegmentVO.getSegmentMailBags() == null || flownMailSegmentVO.getSegmentMailBags().size()== 0)){
			form.setListFlag("");
			error = new ErrorVO(NO_RESULTS_FOUND);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		
		}
		else{
			form.setListFlag("Y");
		}
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			
			invocationContext.target = ACTION_FAILURE;
			return;
		}
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
	}
	
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();

		oneTimeList.add(WEIGHT_UNIT_ONETIME);

		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}
	
}
