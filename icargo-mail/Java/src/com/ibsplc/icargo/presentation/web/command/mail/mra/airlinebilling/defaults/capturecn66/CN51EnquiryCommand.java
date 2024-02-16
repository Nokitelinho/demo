/*
 * CN51EnquiryCommand.java Created on Jan 27, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2408
 *
 */
public class CN51EnquiryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ListCommand");

	private static final String CLASS_NAME = "Cn51Enquiry";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
	private static final String MODULENAME = "mailtracking.mra.airlinebilling";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	
	//private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	//private static final String BILLINGTYPE_ONETIME = "mailtracking.mra.billingtype";
	//private static final String STATUS_ONETIME = "mailtracking.mra.despatchstatus";
	private static final String PARENT_ID="mailtracking.mra.airlinebilling.defaults.capturecn66";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULENAME, SCREENID);
		/*ApplicationSessionImpl applicationSession = getApplicationSession();
		  LogonAttributes logonAttributes = applicationSession.getLogonVO();
		  SharedDefaultsDelegate sharedDefaultsDelegate = new
		  SharedDefaultsDelegate(); 
		  Map<String, Collection<OneTimeVO>>
		  oneTimeValues = null; 
		  Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		  try {
		 
		  oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
		  logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
		   } catch (BusinessDelegateException businessDelegateException) {
			  log.log(Log.FINE, "*****in the exception");
//printStackTrrace()(); 
			  errors = handleDelegateException(businessDelegateException);
		  }
		   captureCN51Session .setOneTimeValues( (HashMap<String,
		  Collection<OneTimeVO>>) oneTimeValues);*/
		   session.setPresentScreenStatus(form.getScreenStatus());
		AirlineCN51FilterVO cn51filterVO=new AirlineCN51FilterVO();
		AirlineCN66DetailsFilterVO cn66filterVO=null;
		cn66filterVO=session.getCn66FilterVO(); 
		if(cn66filterVO!=null){
		cn51filterVO.setCompanyCode(cn66filterVO.getCompanyCode());
		cn51filterVO.setInvoiceReferenceNumber(cn66filterVO.getInvoiceRefNumber());
		cn51filterVO.setIataClearancePeriod(cn66filterVO.getClearancePeriod());
		cn51filterVO.setAirlineIdentifier(cn66filterVO.getAirlineId());
		cn51filterVO.setAirlineCode(cn66filterVO.getAirlineCode());
		cn51filterVO.setCarriageStationFrom(cn66filterVO.getCarriageFrom());
		cn51filterVO.setCarriageStationTo(cn66filterVO.getCarriageTo());
		cn51filterVO.setCategoryCode(cn66filterVO.getCategory());
		cn51filterVO.setInterlineBillingType(cn66filterVO.getInterlineBillingType());
	}
		captureCN51Session.setParentId(PARENT_ID);
		captureCN51Session.setFilterDetails(cn51filterVO);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * 
	 * @return Collection<String>
	 */
	/*private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(CATEGORY_ONETIME);
		parameterTypes.add(BILLINGTYPE_ONETIME);
		parameterTypes.add(STATUS_ONETIME);
		return parameterTypes;
	}*/
}