	/*
	 * USPSReportingCommand.java created on Nov 12, 2008
	 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.  
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

    import java.util.ArrayList;
	import java.util.Collection;

	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
    import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
	import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
	import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
	import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
	import com.ibsplc.xibase.server.framework.vo.ErrorVO;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
	/**
	 * 
	 * @author A-3229
	 *
	 */
	public class USPSReportingCommand extends BaseCommand{
		private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

		private static final String CLASS_NAME = "USPSReportingCommand";

		private static final String MODULE_NAME = "mailtracking.mra.defaults";

		private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";

		private static final String LISTUSPS_SUCCESS = "listusps_success";
		
		private static final String NO_RESULTS = "mailtracking.mra.defaults.despatchenquiry.nouspsdetailsfound";
		
		private static final String LISTUSPS_FAILURE = "listusps_failure";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
		
		private static final String PACODE="101";
		private static final String PANAME="US Postal Authority";
		private static final String COUNTRY="US";
		private static final String CURRENCY="USD";
		
		/*
		 *  (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
		 */
		/**
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			log.entering(CLASS_NAME,"execute");
			DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREEN_ID);
			DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
			DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID);
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			
	    	//DSN popup to get pk details
			
			DSNPopUpVO popUpVO=popUpSession.getSelectedDespatchDetails();
			if(popUpVO != null){
			despatchEnquiryForm.setDespatchNum(popUpVO.getBlgBasis());
			despatchEnquiryForm.setDsnFilterDate(popUpVO.getDsnDate());
			despatchEnquiryForm.setPacode(PACODE);
			despatchEnquiryForm.setPaName(PANAME);
			despatchEnquiryForm.setCountry(COUNTRY);
			despatchEnquiryForm.setFinancialYear(popUpVO.getBlgBasis().substring(15,16));
			despatchEnquiryForm.setBillingCurrency(CURRENCY);
			}
			log.log(Log.INFO, "popUpVO---------", popUpVO);
			if((("N").equals(despatchEnquiryForm.getListed()))){
				despatchEnquiryForm.setListed("N");
				invocationContext.target=LISTUSPS_FAILURE;
				return;
			}
			else{
			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			ArrayList<USPSReportingVO> uspsReportingVOs=null;
		
			//server call to get usps details
			try {
				uspsReportingVOs=new ArrayList<USPSReportingVO>(delegate.findUSPSReportingDetails(popUpVO));
				log
						.log(Log.INFO, "uspsReportingVOs---------",
								uspsReportingVOs);
				
			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}
			if(uspsReportingVOs!=null && uspsReportingVOs.size()>0){
			despatchEnqSession.setUSPSReportingDetails(uspsReportingVOs);
			despatchEnquiryForm.setListed("Y");
			log.log(Log.FINE, "!!!inside errors==null");
			invocationContext.target=LISTUSPS_SUCCESS;
			}
			else{
			if(errors!=null){
				log.log(Log.FINE, "!!!inside errors!= null");
				ErrorVO err=new ErrorVO(NO_RESULTS);
				despatchEnqSession.removeAccountingDetails();
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
				invocationContext.addAllError(errors);
				invocationContext.target=LISTUSPS_FAILURE;
				}
				
				
			}		
			}
			log.log(Log.INFO,
					"despatchEnqSession.findUSPSReportingDetails---------",
					despatchEnqSession.getUSPSReportingDetails());
			log.exiting(CLASS_NAME,"execute");
		}
		
	}







