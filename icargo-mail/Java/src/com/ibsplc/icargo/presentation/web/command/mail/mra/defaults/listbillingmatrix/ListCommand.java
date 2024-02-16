/*
 * ListCommand.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;

/**
 * @author A-2280
 *
 */
public class ListCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";

	private static final String MANDATORY_ANY = "mailtracking.mra.defaults.listbillingmatrix.err.mandatory";

	private static final String LIST_FAILURE = "list_failure";

	private static final String NO_RESULTS = "mailtracking.mra.defaults.listbillingmatrix.err.noresults";

	private static final String LIST_SUCCESS = "list_success";

	private static final String FRM_DATE_GREATER = "mailtracking.mra.defaults.listbillingmatrix.err.fromdateisgreater";
	
	private static final String PA_INVALID = "mailtracking.mra.defaults.listbillingmatrix.err.invalidpoacode";
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
		ListBillingMatrixForm listBillingMatrixForm=(ListBillingMatrixForm)invocationContext.screenModel;
		ListBillingMatrixSession listBillingMatrixSession=getScreenSession(MODULE_NAME,SCREENID);
		
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		handleListing(logonAttributes,listBillingMatrixForm,listBillingMatrixSession,invocationContext);
		log.exiting(CLASS_NAME,"execute");
		
		
	}
	/**
	 * 
	 * @param logonAttributes
	 * @param listBillingMatrixForm
	 * @param listBillingMatrixSession
	 * @param invocationContext
	 */
	private void handleListing(LogonAttributes logonAttributes, ListBillingMatrixForm listBillingMatrixForm,
			ListBillingMatrixSession listBillingMatrixSession, InvocationContext invocationContext) {
		log.entering(CLASS_NAME,"handleListing");
		Collection<ErrorVO> errors=null;
		if(listBillingMatrixForm.getFromPage()==null 
				|| listBillingMatrixForm.getFromPage().trim().length()==0){
		errors=validateForm(listBillingMatrixForm);
		}
		if(errors!=null && errors.size()>0){
			log.log(Log.INFO,"Inside errors present--validation failed");
			invocationContext.addAllError(errors);
			listBillingMatrixForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target=LIST_FAILURE;
			return;
		}else{
			BillingMatrixFilterVO billingMatrixFilterVO=null;
			if(listBillingMatrixForm.getFromPage()!=null 
					&& listBillingMatrixForm.getFromPage().trim().length()>0){
				billingMatrixFilterVO=listBillingMatrixSession.getBillingMatrixFilterVO();
				listBillingMatrixForm.setFromPage("");
				setFormFields(billingMatrixFilterVO,listBillingMatrixForm);
			}else{
				billingMatrixFilterVO=populateFilterVO(listBillingMatrixForm,listBillingMatrixSession,logonAttributes);
			}
			
			
		   int flag=0;
		
			log.log(Log.INFO, "\n\nfilterVO passsing to Server",
					billingMatrixFilterVO);
			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			Page<BillingMatrixVO> billingMatrixVOs=null;
			//Added by A-5218 to enable Last Link in Pagination to start
			if(ListBillingMatrixForm.PAGINATION_MODE_FROM_FILTER.equals(listBillingMatrixForm.getPaginationMode())){
				billingMatrixFilterVO.setTotalRecordCount(-1);
			}
		    else{
		    	billingMatrixFilterVO.setTotalRecordCount(listBillingMatrixSession.getTotalRecords().intValue());
		    }
			//Added by A-5218 to enable Last Link in Pagination to end
			try {
				billingMatrixVOs=delegate.findAllBillingMatrix(billingMatrixFilterVO);
			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}
			if(billingMatrixVOs!=null && billingMatrixVOs.size()>0){
				listBillingMatrixSession.setBillingMatrixVOs(billingMatrixVOs);
				//Added by A-5218 to enable Last Link in Pagination to start
				listBillingMatrixSession.setListDisplayPages(billingMatrixVOs);
				listBillingMatrixSession.setTotalRecords(billingMatrixVOs.getTotalRecordCount());
				//Added by A-5218 to enable Last Link in Pagination to end
			}
			else{
				ErrorVO err=new ErrorVO(NO_RESULTS);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
				listBillingMatrixForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.addAllError(errors);
				invocationContext.target=LIST_FAILURE;
				flag++;
			}
			if(flag==0){
			listBillingMatrixForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			}
			invocationContext.target=LIST_SUCCESS;
			log.exiting(CLASS_NAME,"handleListing");
		}
		
	}
	/**
	 * @author A-2280
	 * @param billingMatrixFilterVO
	 * @param listBillingMatrixForm
	 * @param logonAttributes 
	 * @param listBillingMatrixSession 
	 * @return
	 */
	private BillingMatrixFilterVO populateFilterVO( ListBillingMatrixForm listBillingMatrixForm, ListBillingMatrixSession listBillingMatrixSession, LogonAttributes logonAttributes) {
		BillingMatrixFilterVO billingMatrixFilterVO=new BillingMatrixFilterVO();
		billingMatrixFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(listBillingMatrixForm.getBillingMatrixId()!=null 
				&& listBillingMatrixForm.getBillingMatrixId().trim().length()>0){
			billingMatrixFilterVO.setBillingMatrixId(listBillingMatrixForm.getBillingMatrixId().toUpperCase());
			
		}
		if(listBillingMatrixForm.getPoaCode()!=null 
				&& listBillingMatrixForm.getPoaCode().trim().length()>0){
			billingMatrixFilterVO.setPoaCode(listBillingMatrixForm.getPoaCode().toUpperCase());
		}
		if(listBillingMatrixForm.getAirlineCode()!=null 
				&& listBillingMatrixForm.getAirlineCode().trim().length()>0){
			billingMatrixFilterVO.setAirlineCode(listBillingMatrixForm.getAirlineCode().toUpperCase());
			
		}
		if(listBillingMatrixForm.getStatus()!=null 
				&& listBillingMatrixForm.getStatus().trim().length()>0){
			billingMatrixFilterVO.setStatus(listBillingMatrixForm.getStatus());
		}
		if(listBillingMatrixForm.getValidFrom()!=null 
				&& listBillingMatrixForm.getValidFrom().trim().length()>0){
			billingMatrixFilterVO.setValidFrom(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,false).setDate(listBillingMatrixForm.getValidFrom()));
		}
		if(listBillingMatrixForm.getValidTo()!=null && listBillingMatrixForm.getValidTo().trim().length()>0){
			billingMatrixFilterVO.setValidTo(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,false).setDate(listBillingMatrixForm.getValidTo()));
		}
		int pageNumber=Integer.parseInt(listBillingMatrixForm.getDisplayPage());
		if(pageNumber==1){
			listBillingMatrixSession.removeBillingMatrixVOs();
		}
		billingMatrixFilterVO.setPageNumber(pageNumber);
		listBillingMatrixSession.setBillingMatrixFilterVO(billingMatrixFilterVO);
		return billingMatrixFilterVO;
	}
	/**
	 * @author A-2280
	 * @param billingMatrixFilterVO
	 * @param listBillingMatrixForm
	 */
	private void setFormFields(BillingMatrixFilterVO billingMatrixFilterVO, ListBillingMatrixForm listBillingMatrixForm) {
		if(billingMatrixFilterVO!=null){
		listBillingMatrixForm.setBillingMatrixId(billingMatrixFilterVO.getBillingMatrixId());
		listBillingMatrixForm.setAirlineCode(billingMatrixFilterVO.getAirlineCode());
		listBillingMatrixForm.setPoaCode(billingMatrixFilterVO.getPoaCode());
		listBillingMatrixForm.setDisplayPage("1");
		listBillingMatrixForm.setLastPageNum("0");
		}
		
	}
	/**
	 * 
	 * @param listBillingMatrixForm
	 * @param errors
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ListBillingMatrixForm listBillingMatrixForm) {
		log.entering(CLASS_NAME,"validateForm");
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		if((listBillingMatrixForm.getBillingMatrixId()==null || listBillingMatrixForm.getBillingMatrixId().trim().length()==0)
				&& (listBillingMatrixForm.getPoaCode()==null || listBillingMatrixForm.getPoaCode().trim().length()==0)
				&& (listBillingMatrixForm.getStatus()==null || listBillingMatrixForm.getStatus().trim().length()==0)
				&& (listBillingMatrixForm.getAirlineCode()==null || listBillingMatrixForm.getAirlineCode().trim().length()==0)
				&& (listBillingMatrixForm.getValidFrom()==null ||listBillingMatrixForm.getValidFrom().trim().length()==0)
				&& (listBillingMatrixForm.getValidTo()==null || listBillingMatrixForm.getValidTo().trim().length()==0)){
			log.log(Log.INFO,"\n\nInside mandatory");
			ErrorVO err=new ErrorVO(MANDATORY_ANY);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
			return errors;
		}
		if(listBillingMatrixForm.getValidFrom()!=null && listBillingMatrixForm.getValidFrom().trim().length()>0){
			if(listBillingMatrixForm.getValidTo()!=null && listBillingMatrixForm.getValidTo().trim().length()>0){
				LocalDate frmDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(listBillingMatrixForm.getValidFrom());
				LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(listBillingMatrixForm.getValidTo());
				if(frmDate.isGreaterThan(toDate)){
					log.log(Log.INFO,"indide date validation");
					ErrorVO err=new ErrorVO(FRM_DATE_GREATER);
					errors.add(err);
					return errors;
					
				}
			}
		}
		//Added by A-9998 to display invalid PA code to start
		if(listBillingMatrixForm.getPoaCode() !=null && listBillingMatrixForm.getPoaCode().trim().length()>0){
			try{
    			PostalAdministrationVO paVO =new MailTrackingMRADelegate().findPACode(getApplicationSession().getLogonVO().getCompanyCode(),listBillingMatrixForm.getPoaCode().trim());
    		 if(paVO == null){
				 ErrorVO err = new ErrorVO(PA_INVALID);
				 err.setErrorDisplayType(ErrorDisplayType.ERROR);
				 errors.add(err);
				 log.log(Log.INFO, "Error", err.getErrorCode());
			 }
    		 return errors;
    		}
    		catch(BusinessDelegateException businessDelegateException){
    			errors.addAll(handleDelegateException(businessDelegateException));
    		}
		}
		//Added by A-9998 to display invalid PA code to start
		log.exiting(CLASS_NAME,"validateForm");
		return errors;
	}
	
	
	

}
