/*
 * ScreenLoadFlightReconcilationCommand.java Created on JULY 24, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.FlightReconciliationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.FlightReconciliationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3817
 *
 */
public class ScreenLoadFlightReconcilationCommand extends BaseCommand{
	private static final String SCREENID = "mailtracking.defaults.flightreconcilation";

	private static final String PARAMETER_KEY_REC_EXC = "mailtracking.defaults.reconcileexceptions";

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREENID_SEARCH = "mailtracking.defaults.searchflight";

	private static final String PARAMETER_KEY_REC_STA = "mailtracking.defaults.reconcilestatus";
	
   private static final String SCREENLOAD_SUCCESS="screenload_success";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		FlightReconciliationSession flightReconciliationSession =(FlightReconciliationSession)getScreenSession(MODULE_NAME, SCREENID);
		SearchFlightSession searchFlightSession=(SearchFlightSession)getScreenSession(MODULE_NAME, SCREENID_SEARCH);
		FlightReconciliationForm flightReconciliationForm=(FlightReconciliationForm)invocationContext.screenModel;
		Collection<MailReconciliationVO>mailReconciliationVOs=null;
		boolean infoflag=true;
		boolean acceptListflag=true;
		Collection<ErrorVO>errorVOs=null;
		ErrorVO errorVO=null;
		String port=null;
		String companyCode= getApplicationSession().getLogonVO().getCompanyCode();
		OperationalFlightVO operationalFlightVO=null;
		  if("ON".equals(flightReconciliationForm.getListflag())){
			  operationalFlightVO=flightReconciliationSession.getOperationalFlightVO();
		  }
		  else{
			  String selectedRow=flightReconciliationForm.getSelectedRow();
				 operationalFlightVO=(OperationalFlightVO)(searchFlightSession.getOperationalFlightVOs()).get(Integer.parseInt(selectedRow));
				flightReconciliationSession.setOperationalFlightVO(operationalFlightVO);
				operationalFlightVO.setDirection(flightReconciliationForm.getFromScreen());				 
				
				if(flightReconciliationForm.getPort()!=null && flightReconciliationForm.getPort().trim().length()>0){
					port=flightReconciliationForm.getPort();
				}
				else{
					port=getApplicationSession().getLogonVO().getAirportCode();
				}
				operationalFlightVO.setAirportCode(port);
				operationalFlightVO.setDirection(flightReconciliationForm.getFromScreen());
		  }	
		try{
			mailReconciliationVOs=new MailTrackingDefaultsDelegate().findMailsForReconciliation(operationalFlightVO);
		}
		catch (BusinessDelegateException businessDelegateException) {
			infoflag=false;
		}
		if((mailReconciliationVOs==null ||mailReconciliationVOs.size()<1)&&(infoflag)  ){
			   if((MailConstantsVO.OPERATION_OUTBOUND.equals(flightReconciliationForm.getFromScreen())) &&  (!MailConstantsVO.EXC_RECONCILE.equals(operationalFlightVO.getMailStatus())) ){
					try{
						operationalFlightVO=new MailTrackingDefaultsDelegate().updateOperationalFlightVO(operationalFlightVO);
						flightReconciliationSession.setOperationalFlightVO(operationalFlightVO);
					}
					catch (BusinessDelegateException businessDelegateException) {
						log.log(Log.FINE,  "BusinessDelegateException");
					}
			   }	
			errorVO=new ErrorVO("mailtracking.defaults.flightreconciliation.reconsiled");
			errorVOs=new ArrayList<ErrorVO>();
			errorVOs.add(errorVO);
			//invocationContext.addAllError(errorVOs);
			flightReconciliationForm.setFinaliseBtnFlag("YEF");
			
			
		}
		else{
			flightReconciliationForm.setFinaliseBtnFlag("N");
		}
		if(mailReconciliationVOs!=null &&mailReconciliationVOs.size()>0){
			 for(MailReconciliationVO reconciliationVO:mailReconciliationVOs){
				if(!MailConstantsVO.EXC_ACCEPTED.equals(reconciliationVO.getMailStatus())) {
					acceptListflag=false;
				}
			 }
			 if((acceptListflag) && (!MailConstantsVO.EXC_RECONCILE.equals(operationalFlightVO.getMailStatus())) &&(MailConstantsVO.OPERATION_OUTBOUND.equals(flightReconciliationForm.getFromScreen()))){
				 
				 try{
						operationalFlightVO=new MailTrackingDefaultsDelegate().updateOperationalFlightVO(operationalFlightVO);
						log.log(Log.INFO, "operationalFlightVO",
								operationalFlightVO);
						log
								.log(
										Log.INFO,
										"flightReconciliationSession.getOperationalFlightVO()",
										flightReconciliationSession.getOperationalFlightVO());
						flightReconciliationSession.setOperationalFlightVO(operationalFlightVO);					
						
					}
					catch (BusinessDelegateException businessDelegateException) {
						log.log(Log.FINE,  "BusinessDelegateException");
					}
					
			 }
			 if(acceptListflag){
			 flightReconciliationForm.setFinaliseBtnFlag("YNF");
			 }
	 }
		
		
		flightReconciliationSession.setMailReconciliationVOs(mailReconciliationVOs);
		flightReconciliationSession.setOneTimeVOs(getOnetimes(companyCode));
		flightReconciliationForm.setListflag("OFF");
		populateFlightMessage(operationalFlightVO, flightReconciliationForm);
		invocationContext.target=SCREENLOAD_SUCCESS;
		
	}
	/**
	 * The method to obtain the onetime values. The method will call the
	 * sharedDefaults delegate and returns the map of requested onetimes
	 * 
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOnetimes(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		Collection<ErrorVO> errors = null;
		Collection<String>keyparameters=new ArrayList<String>();
		keyparameters.add(PARAMETER_KEY_REC_EXC);
		keyparameters.add(PARAMETER_KEY_REC_STA);
		try {
			oneTimeMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, keyparameters);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeMap;
	}
	/**
	 * 
	 * @param operationalFlightVO
	 * @param flightReconciliationForm
	 */
    private void populateFlightMessage(OperationalFlightVO operationalFlightVO,FlightReconciliationForm flightReconciliationForm ){
    	StringBuilder stringBuilder=new StringBuilder();
    	stringBuilder.append("Flight Exceptions for ");
    	stringBuilder.append(operationalFlightVO.getCarrierCode());
    	stringBuilder.append(" ");
    	stringBuilder.append(operationalFlightVO.getFlightNumber());
    	if(MailConstantsVO.OPERATION_INBOUND.equals(flightReconciliationForm.getFromScreen())){
    		stringBuilder.append(" Arrival at ");
    	}
    	if(MailConstantsVO.OPERATION_OUTBOUND.equals(flightReconciliationForm.getFromScreen())){
    		stringBuilder.append(" Departure at ");
    	}
    	stringBuilder.append(flightReconciliationForm.getPort());
    	stringBuilder.append(" on ");
    	stringBuilder.append(operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat());
    	stringBuilder.append(" ");
    	stringBuilder.append(operationalFlightVO.getFlightDate().toDisplayTimeOnlyFormat(true));
    	flightReconciliationForm.setFlightMessage(stringBuilder.toString());
    }
}
