/*
 * SaveAttachGroupCodeCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.listcustomergroup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.ListCustomerGroupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class SaveAttachGroupCodeCommand  extends BaseCommand {
	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("SaveAttachGroupCodeCommand");
	private static final String SCREENID = "customermanagement.defaults.listcustomergroup";
    private static final String MODULE = "customermanagement.defaults";
    private static final String SAVE_SUCCESS = "save_success";
    private static final String SAVE_FAILURE = "save_failure";
    private static final String BLANK = "";
/**
 *@param  invocationContext
 *@throws CommandInvocationException
 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ListCustomerGroupForm actionForm =
			(ListCustomerGroupForm) invocationContext.screenModel;
		ListCustomerGroupSession session =
    		getScreenSession(MODULE, SCREENID);

        CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();

        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        Collection<CustomerGroupLoyaltyProgrammeVO> customerGroupLoyaltyProgrammeVOs =
        	new ArrayList<CustomerGroupLoyaltyProgrammeVO>();
	        ArrayList<CustomerGroupLoyaltyProgrammeVO> CustomerGroupLoyaltyProgrammeVOsFromSession =
					session.getCustomerGroupLoyaltyProgrammeVOs()!= null ?
					new ArrayList<CustomerGroupLoyaltyProgrammeVO>(session.getCustomerGroupLoyaltyProgrammeVOs()):
					new ArrayList<CustomerGroupLoyaltyProgrammeVO>();
			//validation



			
			if(CustomerGroupLoyaltyProgrammeVOsFromSession != null && CustomerGroupLoyaltyProgrammeVOsFromSession.size()>0){
				String[] loyaltyPgm = actionForm.getLoyaltyPgm();
		 		String[] pgmFromDate = actionForm.getPgmFromDate();
		 		String[] pgmToDate = actionForm.getPgmToDate();
		 		String[] attachFromDate = actionForm.getAttachFromDate();
		 		String[] attachToDate = actionForm.getAttachToDate();

		 		int index = 0;
	    		for(CustomerGroupLoyaltyProgrammeVO vo : CustomerGroupLoyaltyProgrammeVOsFromSession){

	    			if((vo.getOperationalFlag()!=null &&
	    		    		!vo.getOperationalFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
	    		    			|| vo.getOperationalFlag()==null){
	    				log.log(Log.FINE, "vo.getLoyaltyProgramCode()", vo.getLoyaltyProgramCode());
						log.log(Log.FINE, "vo.getFromDate()", vo.getFromDate());
						log.log(Log.FINE, "vo.getToDate()", vo.getToDate());
						if(
			    				(BLANK.equals(vo.getLoyaltyProgramCode()) && !BLANK.equals(loyaltyPgm[index])) ||

		    					(!BLANK.equals(vo.getLoyaltyProgramCode()) && !vo.getLoyaltyProgramCode().equals(loyaltyPgm[index])) ||

			    				(vo.getFromDate()== null && !BLANK.equals(attachFromDate[index])) ||

			    				(vo.getFromDate()!= null && !vo.getFromDate().toDisplayFormat(true).equals(attachFromDate[index])) || //Modified By A-5374

			    				(vo.getToDate()== null && !BLANK.equals(attachToDate[index])) ||

			    				(vo.getToDate()!= null && !vo.getToDate().toDisplayFormat(true).equals(attachToDate[index]))){ //Modified By A-5374
			    				if(vo.getOperationalFlag()==null){
			    					vo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			    				}
			    			}
			    			if(loyaltyPgm[index] != null && loyaltyPgm[index].trim().length() != 0){
			    				vo.setLoyaltyProgramCode(loyaltyPgm[index].toUpperCase());
			    			}
			    			if(attachFromDate[index] != null && attachFromDate[index].trim().length() != 0){
			    				LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setFromDate(fromDate.setDate(attachFromDate[index]));
			    			}
			    			if(attachToDate[index] != null && attachToDate[index].trim().length() != 0){
			    				LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setToDate(toDate.setDate(attachToDate[index]));
			    			}
			    			if(pgmFromDate[index] != null && pgmFromDate[index].trim().length() != 0){
			    				LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setLoyaltyFromDate(fromDate.setDate(pgmFromDate[index]));
			    			}
			    			if(pgmToDate[index] != null && pgmToDate[index].trim().length() != 0){
			    				LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setLoyaltyToDate(toDate.setDate(pgmToDate[index]));
			    			}
	    			}
	    			String groupCode  = actionForm.getGroupCodeAttach();
	    			log.log(Log.FINE, "***********groupCode*************",
							groupCode);
					vo.setGroupCode(groupCode);
	    			customerGroupLoyaltyProgrammeVOs.add(vo);
	    			index++;
	    		}
	      	}
			log.log(Log.FINE, "\n\n\n\n Collection from session ---> ",
					CustomerGroupLoyaltyProgrammeVOsFromSession);
			errors = validateDateFields(CustomerGroupLoyaltyProgrammeVOsFromSession);
			if(errors != null && errors.size() > 0) {
				log.log(Log.FINE, "exception");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}

			log.log(Log.FINE, "\n\n\n\n Collection from session ---> ",
					CustomerGroupLoyaltyProgrammeVOsFromSession);
			errors = validateLoyaltyProgramme(CustomerGroupLoyaltyProgrammeVOsFromSession,actionForm);
			if(errors != null && errors.size() > 0) {
				log.log(Log.FINE, "exception");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
	     	log.log(Log.FINE,
					"\n\n\n\n customerGroupLoyaltyProgrammeVOs ---> ",
					customerGroupLoyaltyProgrammeVOs);
			session.setCustomerGroupLoyaltyProgrammeVOs(customerGroupLoyaltyProgrammeVOs);
	        log.log(Log.FINE,
					"collection before setting to delegate ---->>>>>>>>>",
					customerGroupLoyaltyProgrammeVOs);
			try {
	        	delegate.saveGroupLoyaltyPgm(customerGroupLoyaltyProgrammeVOs);
			} catch (BusinessDelegateException e) {
				// To be reviewed Auto-generated catch block
//printStackTrrace()();
				errors = handleDelegateException(e);
			}
			if(errors != null && errors.size()>0){
				log.log(Log.FINE,"%%%%%%inside errors%%%%%%%%%");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_SUCCESS;
				return;

				}
			session.removeCustomerGroupLoyaltyProgrammeVOs();
			actionForm.setStatusFlag("afterAttach");
			invocationContext.target = SAVE_SUCCESS;
    }
 /**
  * 
  * @param col
  * @return
  */
    public Collection<ErrorVO> validateDateFields(Collection<CustomerGroupLoyaltyProgrammeVO> col) {
			log.log(log.FINE,"!!!!!!!!!!!!!!!!!!!!!!! INSIDE VALIDATE FUN.");
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			log.log(Log.FINE, "col---->>>>", col);
			for(CustomerGroupLoyaltyProgrammeVO vo:col){
				log.log(Log.FINE, "vo.getFromDate()---->>>>", vo.getFromDate());
				log.log(Log.FINE, "vo.getToDate()---->>>>", vo.getToDate());
				log.log(Log.FINE, "vo.getLoyaltyFromDate()---->>>>", vo.getLoyaltyFromDate());
				log.log(Log.FINE, "vo.getLoyaltyToDate()---->>>>", vo.getLoyaltyToDate());
				if((vo.getOperationalFlag()!=null &&
    		    		!vo.getOperationalFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
    		    			|| vo.getOperationalFlag()==null){

					if(!(
							(vo.getFromDate().equals(vo.getLoyaltyFromDate()))&&
							((vo.getToDate().equals(vo.getLoyaltyToDate()))) &&
							((vo.getLoyaltyFromDate().equals(vo.getLoyaltyToDate()))))){

					if(!((vo.getLoyaltyToDate().equals(vo.getFromDate())) && (vo.getFromDate().equals(vo.getToDate())))){
						if(!((vo.getLoyaltyFromDate().equals(vo.getFromDate())) && (vo.getFromDate().equals(vo.getToDate())))){
					if((vo.getFromDate().equals(vo.getLoyaltyFromDate())) && (vo.getToDate().equals(vo.getLoyaltyToDate()))){
						if(!(vo.getFromDate().isLesserThan(vo.getLoyaltyToDate()))||
								!(vo.getToDate().isGreaterThan(vo.getLoyaltyFromDate())))
								{
					log.log(Log.FINE,"********VALIDATION-0");
						error = new ErrorVO("customermanagement.defaults.maintaingroupcode.msg.err.daterangealreadyexists");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					}
					else if((vo.getFromDate().equals(vo.getLoyaltyFromDate()))){
						if(!(vo.getFromDate().isLesserThan(vo.getLoyaltyToDate()))||
								!(vo.getToDate().isGreaterThan(vo.getLoyaltyFromDate())) ||
								!(vo.getToDate().isLesserThan(vo.getLoyaltyToDate())))
								{
					log.log(Log.FINE,"********VALIDATION-1");
						error = new ErrorVO("customermanagement.defaults.maintaingroupcode.msg.err.daterangealreadyexists");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					}
					else if((vo.getToDate().equals(vo.getLoyaltyToDate()))){
						if(!(vo.getFromDate().isLesserThan(vo.getLoyaltyToDate()))||
								!(vo.getToDate().isGreaterThan(vo.getLoyaltyFromDate()))  ||
								!(vo.getFromDate().isGreaterThan(vo.getLoyaltyFromDate()))
								){
							log.log(Log.FINE,"********VALIDATION-2");
						error = new ErrorVO("customermanagement.defaults.maintaingroupcode.msg.err.daterangealreadyexists");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					}
					else if(!(vo.getFromDate().isLesserThan(vo.getLoyaltyToDate()))||
							!(vo.getToDate().isGreaterThan(vo.getLoyaltyFromDate())) ||
							!(vo.getToDate().isLesserThan(vo.getLoyaltyToDate())) ||
							!(vo.getFromDate().isGreaterThan(vo.getLoyaltyFromDate()))
							){
						log.log(Log.FINE,"********VALIDATION-3");
					error = new ErrorVO("customermanagement.defaults.maintaingroupcode.msg.err.daterangealreadyexists");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
					else if(!(vo.getFromDate().equals(vo.getToDate()))){
						 if(!(vo.getFromDate().isLesserThan(vo.getToDate()))){
							 // if(vo.getToDate().before(vo.getFromDate()))
							log.log(Log.FINE,"********VALIDATION-4");
							error = new ErrorVO("customermanagement.defaults.maintaingroupcode.msg.err.fromdateshouldbelessthantodate");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
						}
				}
					}

					}
				}

				}
			}
			return errors;
   }
/**
 * 
 * @param col
 * @param actionForm
 * @return
 */
    public Collection<ErrorVO> validateLoyaltyProgramme(Collection<CustomerGroupLoyaltyProgrammeVO> col,
    		ListCustomerGroupForm actionForm) {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		log.log(Log.FINE,"!!!!!!!!!!!!!!!!!!!!!!! INSIDE VALIDATE FUN.");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		log.log(Log.FINE, "col---->>>>", col);
		if(col != null && col.size()>0){
			String[] code = actionForm.getLoyaltyPgm();
			String[] fromDate = actionForm.getAttachFromDate();
			String[] toDate = actionForm.getAttachToDate();
			int size =  code.length;

			for(int i=0;i<size;i++){
				int index = 0;
				for(CustomerGroupLoyaltyProgrammeVO vo:col){
					if(index!=i){
						log.log(Log.FINE, "------------i -------------", i);
						log.log(Log.FINE, "actionForm.getOperationalFlag()[i]",
								actionForm.getOperationalFlag(), i);
					if(!AbstractVO.OPERATION_FLAG_DELETE.equals(actionForm.getOperationalFlag()[i]))	{
					if((vo.getOperationalFlag()!=null &&
				    		!vo.getOperationalFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
				    			|| vo.getOperationalFlag()==null){

						if(vo.getFromDate()!=null){
							log.log(Log.FINE, "code[i]---->>>>", code, i);
							log.log(Log.FINE, "fromDate[i]---->>>>", fromDate,
									i);
							log.log(Log.FINE, "toDate[i]---->>>>", toDate, i);
							log.log(Log.FINE, "vo.getFromDate()---->>>>", vo.getFromDate().toDisplayDateOnlyFormat());
							log.log(Log.FINE, "vo.getToDate()---->>>>", vo.getToDate().toDisplayDateOnlyFormat());
							LocalDate fromDateForm = (new LocalDate(applicationSession.getLogonVO().getStationCode(),Location.STN,false)).setDate(fromDate[i]);
							LocalDate toDateForm = (new LocalDate(applicationSession.getLogonVO().getStationCode(),Location.STN,false)).setDate(toDate[i]);
							if((code[i].equals(vo.getLoyaltyProgramCode())) &&
									(fromDate[i].equalsIgnoreCase(vo.getFromDate().toDisplayDateOnlyFormat())) &&
									(toDate[i].equalsIgnoreCase(vo.getToDate().toDisplayDateOnlyFormat()))){
								log.log(Log.FINE,"ERROR-------------1");
								error = new ErrorVO("customermanagement.defaults.maintaingroupcode.msg.err.loyaltyprgmforthtdaterangealreadyexits");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								return errors;
							}
							if((code[i].equals(vo.getLoyaltyProgramCode()))){
								log.log(Log.FINE, "validation", vo.getFromDate().isGreaterThan(toDateForm));
								log.log(Log.FINE, "validation", vo.getToDate().isLesserThan(fromDateForm));
								if(!(vo.getFromDate().isGreaterThan(toDateForm))&&
												!(vo.getToDate().isLesserThan(fromDateForm))){

									log.log(Log.FINE,"ERROR-------------2");
									error = new ErrorVO("customermanagement.defaults.maintaingroupcode.msg.err.loyaltyprgmforthtdaterangealreadyexits");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									return errors;
								}




							}



						}
					}
					}
				}
					index++;
				}
			}
		}


		return errors;
    }
}

