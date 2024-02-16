/*
 * SaveCommand.java Created on Mar 27, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class SaveCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";

	private static final String SCREENLOAD_SUCCESS ="screenload_success";
	private static final String SCREENLOAD_FAILURE="screenload_failure";
	private static final String CLASS_NAME = "Save Command";
	private static final String BLANK="";
	private static final String NO_DATA_SAVE="mailtracking.mra.gpabilling.msg.err.nodataforsave";
	private static final String SETTLED_STATUS="S";
	private static final String PENDING_STATUS="P";
	private static final String SCREENLOAD_STATUS="screenload";
	private static final String CHECKSELECTEDINVOICE= "checkselectedinvoice";
	private static final String DELETED= "deleteFlag";

	private static final String FROMAVAILABLEPOPUP="fromavailablepopup";
	private static final String TRUE="true";


	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	//Added by A-5116 for ICRD-17510
	@Override
	public boolean breakOnInvocationFailure() {
		return true;
	}
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException{
		Log log = LogFactory.getLogger("MRA_GPABILLING");
		log.entering(CLASS_NAME, "execute");
		InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;		
		Collection<GPASettlementVO> gpaSettlementVOs = new ArrayList<GPASettlementVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		GPASettlementVO gpaSettlementVO = new GPASettlementVO();		
		Collection<InvoiceSettlementVO> selectedInvoiceSettlementVOs = null;
		Collection<InvoiceSettlementVO> invoiceSettlementVOs = null;
		ErrorVO errorVO=null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		if(session.getGPASettlementVO()!=null&&session.getGPASettlementVO().size()>0){			
			log.log(Log.FINE, "GPA SettlementVO from session--->" +session.getGPASettlementVO());
			selectedInvoiceSettlementVOs = session.getGPASettlementVO().iterator().next().getInvoiceSettlementVOs();
			log.log(Log.FINE, "Selected Invoice settlementVO---->" +selectedInvoiceSettlementVOs);
			for(GPASettlementVO gpasVo: session.getGPASettlementVO()){
				if(gpasVo.getSettlementDetailsVOs()==null || gpasVo.getSettlementDetailsVOs().size()<=0){
					gpaSettlementVO=populateGPASettlementVO(form);	
				}
				else{
					gpaSettlementVO.setGpaCode(gpasVo.getGpaCode());
					gpaSettlementVO.setSettlementId(gpasVo.getSettlementId());	
					gpaSettlementVO.setSettlementSequenceNumber(gpasVo.getSettlementSequenceNumber());
					gpaSettlementVO.setCompanyCode(gpasVo.getCompanyCode());
					gpaSettlementVO.setSettlementCurrency(gpasVo.getSettlementCurrency());
					gpaSettlementVO.setSettlementId(gpasVo.getSettlementId());
					gpaSettlementVO.setSettlementDate(gpasVo.getSettlementDate());
					gpaSettlementVO.setOperationFlag(GPASettlementVO.OPERATION_FLAG_UPDATE);
					gpaSettlementVO.setLastUpdatedTime(new LocalDate((LocalDate.NO_STATION),Location.NONE,false) );
					gpaSettlementVO.setLastUpdatedUser(logonAttributes.getUserId());					
				}


			}
		}
		else{
			gpaSettlementVO=populateGPASettlementVO(form);				
		}
		//to populate selected invoices from screen starts
		String[] selectedRow = form.getCheck();		
		int row = 0;
		if(selectedInvoiceSettlementVOs!=null && selectedInvoiceSettlementVOs.size()>0){
			if(selectedRow!=null){
				invoiceSettlementVOs=new ArrayList<InvoiceSettlementVO>();
				for(InvoiceSettlementVO invoiceSettlementVO: selectedInvoiceSettlementVOs){
					for (int j = 0; j < (selectedRow.length)-1; j++) {
						if (row == Integer.parseInt(selectedRow[j])) {
							invoiceSettlementVO.setIsDeleted(false);
							invoiceSettlementVOs.add(invoiceSettlementVO);
						}
					}
					row++;

				}
			}

		}
		//to populate selected invoices from screen ends
		Collection<SettlementDetailsVO> settlementDetailsVOs = populateSettlementDetailsVO(form,session,gpaSettlementVO);
		//validation for deletion of chqeue details

		//validating form
		Collection<ErrorVO> errorVo = new ArrayList<ErrorVO>();	

		errorVo=validateForm(form);

		if(errorVo!=null && errorVo.size()>0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errorVo);
			invocationContext.target=SCREENLOAD_FAILURE;
			return;
		}
		gpaSettlementVO.setSettlementDetailsVOs(settlementDetailsVOs);
		if(invoiceSettlementVOs!=null){
			gpaSettlementVO.setInvoiceSettlementVOs(invoiceSettlementVOs);
		}
		gpaSettlementVOs.add(gpaSettlementVO);

		if((DELETED).equals(form.getCheckFlag())){
			form.setCheckFlag("");
			errors=validateChequeDeletion(form);
		}
		if(errors!=null && errors.size()>0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target=SCREENLOAD_FAILURE;
			return;
		}
		try{
			new MailTrackingMRADelegate().saveSettlementDetails(gpaSettlementVOs);

		}
		catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);

		}
		if(errors!=null && errors.size()>0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target=SCREENLOAD_FAILURE;
			return;
		}else{
			session.removeInvoiceSettlementVOs();
			session.removeInvoiceSettlementHistoryVOs();
			session.setGPASettlementVO(null);
			session.setGPASettlementVOs(null);
			form.setGpaCodeFilter(BLANK);
			form.setInvRefNumberFilter(BLANK);
			form.setFromDate(BLANK);
			form.setToDate(BLANK);
			form.setSettlementStatusFilter(BLANK);
			form.setSettlementReferenceNumber(BLANK);
			form.setSettleCurrency(BLANK);
			form.setSettlementDate(BLANK);
			form.setInvoiceStatusFilter(BLANK);
			form.setScreenStatus(SCREENLOAD_STATUS);
			form.setGpaNameFilter(BLANK);	
			session.setFromSave(TRUE);

		}

		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 * 
	 * @param settlementDetailsVO
	 * @return
	 */
	private Collection<ErrorVO> validateForm(InvoiceSettlementForm invoiceSettlementForm) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		for(int j=0;j<invoiceSettlementForm.getStlOpFlag().length-1;j++){
			if(invoiceSettlementForm.getChequeNumber()[j]==null ||invoiceSettlementForm.getChequeNumber()[j].trim().length()<=0){
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequenumbermandatory"));
			}
			if(invoiceSettlementForm.getChequeDate()[j]==null ||invoiceSettlementForm.getChequeDate()[j].trim().length()<=0){
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequedatemandatory"));
			}
			if(invoiceSettlementForm.getChequeAmount()[j]==null ||invoiceSettlementForm.getChequeAmount()[j].trim().length()<=0){
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequeamountmandatory"));
			}
			//			if(invoiceSettlementForm.getBankName()[j]==null ||invoiceSettlementForm.getBankName()[j].trim().length()<=0){
			//				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequebankmandatory"));
			//			}
			//			if(invoiceSettlementForm.getBranchName()[j]==null ||invoiceSettlementForm.getBranchName()[j].trim().length()<=0){
			//				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequebranchmandatory"));
			//			}

		}
		if(invoiceSettlementForm.getSettleCurrency()==null ||invoiceSettlementForm.getSettleCurrency().trim().length()<=0){
			errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.settlecurrmandatory"));
		}
		if(invoiceSettlementForm.getSettlementDate()==null ||invoiceSettlementForm.getSettlementDate().trim().length()<=0){
			errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.settlementdatemandatory"));
		}
		if(invoiceSettlementForm.getSettlementDate()!=null && invoiceSettlementForm.getSettlementDate().trim().length()>0){
			LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			LocalDate settlementDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			settlementDate.setDate(invoiceSettlementForm.getSettlementDate());
			if(settlementDate.isGreaterThan(localDate)){
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.settlementDateCannotbefuture"));
			}
		}
		return errors;
	}
	/**
	 * 
	 * @param form
	 * @return
	 */
	private GPASettlementVO populateGPASettlementVO(InvoiceSettlementForm form) {
		GPASettlementVO gpaSettlementVO= new GPASettlementVO();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		gpaSettlementVO.setSettlementId(form.getSettlementReferenceNumber());
		gpaSettlementVO.setCompanyCode(logonAttributes.getCompanyCode());
		gpaSettlementVO.setGpaCode(form.getGpaCodeFilter());
		if(form.getSettlementDate()!= null && form.getSettlementDate().trim().length()>0){
			gpaSettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getSettlementDate()));	
		}    			
		if(form.getSettleCurrency()!= null && form.getSettleCurrency().trim().length()>0){
			gpaSettlementVO.setSettlementCurrency(form.getSettleCurrency());
		}
		gpaSettlementVO.setLastUpdatedUser(logonAttributes.getUserId());
		gpaSettlementVO.setLastUpdatedTime(logonAttributes.getLoginTime());
		gpaSettlementVO.setOperationFlag(GPASettlementVO.OPERATION_FLAG_INSERT);
		gpaSettlementVO.setInvoiceRefNumber(form.getInvRefNumberFilter());
		return gpaSettlementVO;
	}

	/**
	 * 
	 * @param form
	 * @param session 
	 * @param gpaSettlementVO 
	 * @return
	 */
	private Collection<SettlementDetailsVO> populateSettlementDetailsVO(
			InvoiceSettlementForm form, InvoiceSettlementSession session, GPASettlementVO gpaSettlementVO) {
		List <SettlementDetailsVO> settlementDetailsVOsInSession= null;	
		SettlementDetailsVO settlementDetailsVO = null;				
		Collection<SettlementDetailsVO> settlementDetailsVOs = new ArrayList<SettlementDetailsVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String[] chequeNo = form.getChequeNumber();
		String[] chequeDate = form.getChequeDate();
		String[] bank = form.getBankName();
		String[] branch = form.getBranchName();
		String[] chequeAmount = form.getChequeAmount();
		String[] remarks = form.getChequeRemarks();
		String [] opFlg= form.getStlOpFlag();
		String deleted = form.getDeleteArray();
		String[] isDeleteArray = deleted.split(",");		
		double currChequeAmt=0.0;
		if(session.getGPASettlementVO()!=null){
			settlementDetailsVOsInSession= (List<SettlementDetailsVO>)session.getGPASettlementVO().iterator().next().
			getSettlementDetailsVOs();
		}
		if(opFlg!=null){
			for(int j=0;j<opFlg.length-1;j++){
				if(("I").equals(opFlg[j])){
					settlementDetailsVO= new SettlementDetailsVO();
					settlementDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					settlementDetailsVO.setGpaCode(gpaSettlementVO.getGpaCode());

					settlementDetailsVO.setOperationFlag(opFlg[j]);		
					if(("true").equals(isDeleteArray[j])) {						
						settlementDetailsVO.setIsDeleted("Y");					
					}else {
						settlementDetailsVO.setIsDeleted("N");
					}
					settlementDetailsVO.setSettlementId(form.getSettlementReferenceNumber());

					settlementDetailsVO.setChequeNumber(chequeNo[j]);
					if(chequeDate[j]!=null && chequeDate[j].trim().length()>0){
						settlementDetailsVO.setChequeDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(chequeDate[j]));
					}					
					settlementDetailsVO.setChequeBank(bank[j]);
					settlementDetailsVO.setChequeBranch(branch[j]);
					settlementDetailsVO.setChequeCurrency(form.getSettleCurrency());
					settlementDetailsVO.setRemarks(remarks[j]);
					//settlementDetailsVO.setIsAccounted("Y");
					if(chequeAmount[j]!=null&&chequeAmount[j].trim().length()>0){
						currChequeAmt= Double.parseDouble(chequeAmount[j]);
					}
					try{
						if(form.getSettleCurrency()!= null && form.getSettleCurrency().trim().length()>0){
							Money currAmt=CurrencyHelper.getMoney(form.getSettleCurrency());
							currAmt.setAmount(currChequeAmt);			
							settlementDetailsVO.setChequeAmount(currAmt);
						}				
					}
					catch(CurrencyException e){
						e.getErrorCode();
					}			
					settlementDetailsVOs.add(settlementDetailsVO);

				}

				else if(settlementDetailsVOsInSession!=null && settlementDetailsVOsInSession.size()>0){
				
					if("U".equals(opFlg[j])){
						settlementDetailsVO=settlementDetailsVOsInSession.get(j);
						if(("true").equals(isDeleteArray[j])) {							
							settlementDetailsVO.setIsDeleted("Y");					
							settlementDetailsVO.setIsAccounted("N");
							form.setCheckFlag(CHECKSELECTEDINVOICE);	

							settlementDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							settlementDetailsVO.setGpaCode(gpaSettlementVO.getGpaCode());
							settlementDetailsVOs.add(settlementDetailsVO);
						}
					}
					else if("D".equals(opFlg[j])){
						form.setCheckFlag(DELETED);	
					}

				}
			}

		}
		gpaSettlementVO.setChequeNumber(settlementDetailsVO.getChequeNumber());
		return settlementDetailsVOs;
	}


	/**
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateChequeDeletion(InvoiceSettlementForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();		
		errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.cannotdeletecheque"));


		return errors;
	}


}