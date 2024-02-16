/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.SaveCommand.java
 *
 *	Created by	:	A-7871
 *	Created on	:	23-May-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.SaveCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7871	:	23-May-2018	:	Draft
 */
public class SaveCommand extends BaseCommand{
	
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
	private static final String CLASS_NAME = "Save Command";
	private static final String BLANK="";
	private static final String CHECKSELECTEDINVOICE= "checkselectedinvoice";
	private static final String SCREENLOAD_STATUS="screenload";
	private static final String SCREENLOAD_SUCCESS ="screenload_success";
	private static final String SCREENLOAD_FAILURE="screenload_failure";
    private static final String CHEQUE_DETAILS_PARAMETER = "mailtracking.mra.gpabilling.ChequeDetailsMandatory";
    private static final String SAVE_SUCCESS="mailtracking.mra.gpabilling.msg.err.savesucess"; 
    private static final String OVERRIDEROUNDING = "mailtracking.mra.overrideroundingvalue";
	Log log = LogFactory.getLogger("MRA_GPABILLING");
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
	
		log.entering(CLASS_NAME, "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ErrorVO errorVO=null;
		String chequeDetailsMandatory;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		InvoiceSettlementMailbagSession session=(InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		Page<InvoiceSettlementVO> selectedInvoiceSettlementVOs = null;
		InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationContext.screenModel;
		Collection<GPASettlementVO> gpaSettlementVOs=new ArrayList<GPASettlementVO>();
		GPASettlementVO gpaSettlementVO = new GPASettlementVO();	//set from form
		//Collection<InvoiceSettlementVO> selectedInvoiceSettlementVOs = null;
		Collection<InvoiceSettlementVO> invoiceSettlementVOs = null;
		InvoiceSettlementFilterVO invoiceSettlementFilterVO = new InvoiceSettlementFilterVO();
		invoiceSettlementFilterVO=session.getInvoiceSettlementFilterVO();
		gpaSettlementVO.setOverrideRounding(session.getSystemparametres().get(OVERRIDEROUNDING));
		if(session.getSelectedGPASettlementVO()!=null&&session.getSelectedGPASettlementVO().size()>0){
			log.log(Log.FINE, "GPA SettlementVO from session--->" +session.getSelectedGPASettlementVO());
			selectedInvoiceSettlementVOs = session.getSelectedGPASettlementVO().iterator().next().getInvoiceSettlementVO();
			log.log(Log.FINE, "Selected Invoice settlementVO---->" +selectedInvoiceSettlementVOs);
			for(GPASettlementVO gpasVo: session.getSelectedGPASettlementVO()){
				if(gpasVo.getSettlementDetailsVOs()==null || gpasVo.getSettlementDetailsVOs().size()<=0){
					gpaSettlementVO=populateGPASettlementVO(form,session);
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
			gpaSettlementVO=populateGPASettlementVO(form,session);				
		}
		//to populate selected invoices from screen starts
		//String[] selectedRow = form.getCheck();		
		//int row = 0;
		//to populate selected invoices from screen starts
		
		//String[] selectedRow = form.getCheck();
		String[] selectedRow = form.getChequeNumberFilter().split("-");	
		if(selectedRow[0].equals("ALL"))
		{
			if( session.getSystemparametres().get(CHEQUE_DETAILS_PARAMETER).equals("N"))
			{
			       selectedRow=new String[form.getCurrentSettlingAmount().length];
                   for(int i=0;i<form.getCurrentSettlingAmount().length;i++)
                   {
                          //System.out.println(selectedRow[i]);
                          selectedRow[i]=String.valueOf(i);
                          //System.out.println("seell"+selectedRow[i]);
                   }


				
		}else
			selectedRow=null;
		}
		if(selectedInvoiceSettlementVOs!=null && selectedInvoiceSettlementVOs.size()>0){
			if(selectedRow!=null){
				invoiceSettlementVOs=new ArrayList<InvoiceSettlementVO>();
				//for(InvoiceSettlementVO invoiceSettlementVO: selectedInvoiceSettlementVOs){
				for (String index : selectedRow) {
							//if(form.getCurrentSettlingAmount()[Integer.parseInt(index)]!=null && form.getCurrentSettlingAmount()[Integer.parseInt(index)].length()!=0 && Double.parseDouble(form.getCurrentSettlingAmount()[Integer.parseInt(index)])>0){
								for(InvoiceSettlementVO invoiceSettlementVO: selectedInvoiceSettlementVOs){
									if(form.getMailbagIdtable()[Integer.parseInt(index)]!=null){
								if(invoiceSettlementVO.getMailbagID().equals(form.getMailbagIdtable()[Integer.parseInt(index)]))	{
									if(invoiceSettlementVO.getSettlementCurrencyCode()!=null){
									Money amount=null;
									try {
										amount = CurrencyHelper.getMoney(invoiceSettlementVO.getSettlementCurrencyCode());
										amount.setAmount(Double.parseDouble(form.getCurrentSettlingAmount()[Integer.parseInt(index)]));
										invoiceSettlementVO.setCurrentSettlingAmount(amount);
										//Added by A-8399 as part of ICRD-305647
										if(form.getCaseClosedArray()[Integer.parseInt(index)].equals("true")){
										invoiceSettlementVO.setCaseClosed("Y");
										gpaSettlementVO.setSettlementChequeNumber("DUMMY");
										}else{
										invoiceSettlementVO.setCaseClosed("N");
										}
									} catch (CurrencyException e) {
										// TODO Auto-generated catch block
										log.log(Log.FINE,"Inside CurrencyException.. ");
									}
									invoiceSettlementVO.setIsDeleted(false);
									invoiceSettlementVO.setIndex(Integer.parseInt(index));
									invoiceSettlementVOs.add(invoiceSettlementVO);
									break;
									}
								}
								}
								}
								
							
							
					}

				//}
		//	}

		}
		}
		
		//to populate selected invoices from screen ends
		
		//validation for deletion of chqeue details

		//validating form
		Collection<ErrorVO> errorVo = new ArrayList<ErrorVO>();	
		Collection<SettlementDetailsVO> settlementDetailsVOs =null;
		
		chequeDetailsMandatory=session.getSystemparametres().get(CHEQUE_DETAILS_PARAMETER);
		if(chequeDetailsMandatory.equals("Y"))
		{
		errorVo=validateForm(form);
		gpaSettlementVO.setUpdateFlag("Y");
		
		settlementDetailsVOs = populateSettlementDetailsVO(form,session,gpaSettlementVO,selectedRow);
		
		
		}
		else
		{
			 settlementDetailsVOs =	populateDummySettelementDetails(form, session, gpaSettlementVO,selectedRow);
				String errorFlg=null;
				for(SettlementDetailsVO vo:settlementDetailsVOs){
					
						if(vo.getChequeAmount().getAmount()<=0){		
							errorFlg="Y";
							
						
						}
						
					
					}
				if("Y".equals(errorFlg)){
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.invalidchequeamt"));
				errorVo.addAll(errors);
				}
			
		}
		if(errorVo!=null && errorVo.size()>0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errorVo);
			invocationContext.target=SCREENLOAD_FAILURE; 
			return;
		}
		gpaSettlementVO.setSettlementDetailsVOs(settlementDetailsVOs);
	//	List<String> chequeNumbers = new ArrayList<>();//A-8331
	//	String chqNumbers;
		
		//chequeNumbers.add(settlementDetailsVO.getCh)
		if(invoiceSettlementVOs!=null){
			gpaSettlementVO.setInvoiceSettlementVOs(invoiceSettlementVOs);
		}
		gpaSettlementVO.setInvoiceSettlementVO(selectedInvoiceSettlementVOs);
		gpaSettlementVOs.add(gpaSettlementVO);
		String stlchqnums=null;
		/*for(SettlementDetailsVO settlements:settlementDetailsVOs)
		{
			stlchqnums=settlements.getSettlementChequeNumbers();
		}*/
		/*if(null==(gpaSettlementVO.getSettlementChequeNumber()))
		{
			gpaSettlementVO.setSettlementChequeNumber(stlchqnums);
		}*/
		gpaSettlementVO.setFrmScreen("MRA076");
		
		/*if((DELETED).equals(form.getCheckFlag())){
			form.setCheckFlag("");
			errors=validateChequeDeletion(form);
		}*/
		if(errors!=null && errors.size()>0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target=SCREENLOAD_FAILURE;
			return;
		}
		try{
			new MailTrackingMRADelegate().saveSettlementsAtMailbagLevel(gpaSettlementVOs);

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
			//session.removeInvoiceSettlementVOs();
			//session.removeInvoiceSettlementHistoryVOs();
			//session.setGPASettlementVO(null);
			//session.setGPASettlementVOs(null);
			form.setGpaCodeFilter(BLANK);
			session.setInvoiceSettlementFilterVO(null);
			form.setGpaCodeFilter(BLANK);
			session.setInvoiceSettlementHistoryVO(null);
			session.setSelectedGPASettlementVO(null);
		//form.setInvRefNumberFilter(BLANK);
			//form(BLANK);
			//form.setToDate(BLANK);
		//	form.setSettlementStatusFilter(BLANK);
			errors.add(new ErrorVO(SAVE_SUCCESS));
			form.setSettlementReferenceNumber(BLANK);
			form.setSettlementDate(null);
			session.setSelectedGPASettlementVO(null);
			form.setSettlementCurrency(BLANK);
			//form.setSettlementDate(BLANK);
			//form.setInvoiceStatusFilter(BLANK);
			form.setScreenStatusFlag(SCREENLOAD_STATUS);
			form.setActionFlag("SAVE");
			//form.setGpaNameFilter(BLANK);	
			//session.setFromSave(TRUE);
			if(errors!=null && errors.size()>0)
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_SUCCESS;
			log.exiting(CLASS_NAME, "execute");

		}

		
	}
	
	/**
	 * 
	 * @param form
	 * @return
	 */
	private GPASettlementVO populateGPASettlementVO(InvoiceSettlementMailbagForm form, InvoiceSettlementMailbagSession session) {
		GPASettlementVO gpaSettlementVO= new GPASettlementVO();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		gpaSettlementVO.setSettlementId(session.getInvoiceSettlementFilterVO().getSettlementReferenceNumber());
		gpaSettlementVO.setCompanyCode(logonAttributes.getCompanyCode());
		gpaSettlementVO.setGpaCode(session.getInvoiceSettlementFilterVO().getGpaCode());
		if(session.getInvoiceSettlementFilterVO().getSettlementDate()!= null && session.getInvoiceSettlementFilterVO().getSettlementDate().length()>0){
			gpaSettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(session.getInvoiceSettlementFilterVO().getSettlementDate()));	
		}    			
		if(session.getInvoiceSettlementFilterVO().getSettlementCurrency()!= null){
			gpaSettlementVO.setSettlementCurrency(session.getInvoiceSettlementFilterVO().getSettlementCurrency());
		}
		gpaSettlementVO.setLastUpdatedUser(logonAttributes.getUserId());
		gpaSettlementVO.setLastUpdatedTime(logonAttributes.getLoginTime());
		gpaSettlementVO.setOperationFlag(GPASettlementVO.OPERATION_FLAG_INSERT);
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
			InvoiceSettlementMailbagForm form, InvoiceSettlementMailbagSession session, GPASettlementVO gpaSettlementVO,String[] selectedRow) {
		List <SettlementDetailsVO> settlementDetailsVOsInSession= null;	
	//	List<String> chequeNumbers = new ArrayList<>();//A-8331
	//	Collection<String> chequeNumbers= new ArrayList<>();
	//	StringBuilder  ChequeNumbers = new StringBuilder ();
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
		String deleted =  form.getDeleteArray();
		String[] isDeleteArray = deleted.split(",");		
		double currChequeAmt=0.0;
		
		if(session.getSelectedGPASettlementVO()!=null){
			settlementDetailsVOsInSession= (List<SettlementDetailsVO>)session.getSelectedGPASettlementVO().iterator().next().
			getSettlementDetailsVOs();
		}
		int index=0;
		if(opFlg!=null){
			for(int j=0;j<opFlg.length-1;j++){
			
					
				if(("I").equals(opFlg[j])){
							//for(int i=0;i<Integer.parseInt(form.getTemplateIndex());i++){
								int i=index;
							//	int m=i;
					settlementDetailsVO= new SettlementDetailsVO();
					settlementDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					settlementDetailsVO.setGpaCode(gpaSettlementVO.getGpaCode());
					if(settlementDetailsVOsInSession.size()>0){
					settlementDetailsVO.setSettlementSequenceNumber(settlementDetailsVOsInSession.iterator().next().getSettlementSequenceNumber());
					}
					if("OK".equals(form.getActionFlag())&& gpaSettlementVO.getUpdateFlag().equals("Y") && selectedRow==null){
						settlementDetailsVO.setStlFlag("Y");
						 for (SettlementDetailsVO stlvo : settlementDetailsVOsInSession) {
				                if (stlvo.getSettlementId().equals(settlementDetailsVO.getSettlementId())) {
				                  //settlementDetailsVO.setSerialNumber(stlvo.getSerialNumber());
				                  settlementDetailsVO.setSettlementSequenceNumber(stlvo.getSettlementSequenceNumber());
				                }
				              }
					}
					else if("CREATE".equals(form.getActionFlag())&& gpaSettlementVO.getUpdateFlag().equals("Y") && selectedRow==null){
						settlementDetailsVO.setStlFlag("N");
					}
                   
					settlementDetailsVO.setOperationFlag(opFlg[j]);		
					if(("true").equals(isDeleteArray[j])) {						
						settlementDetailsVO.setIsDeleted("Y");		
					}else {
						settlementDetailsVO.setIsDeleted("N");
					}
					settlementDetailsVO.setSettlementId(session.getInvoiceSettlementFilterVO().getSettlementReferenceNumber());
					
					settlementDetailsVO.setChequeNumber(chequeNo[i]);
					

			       /* if (chequeNo != null && index==0) {
			   
			                for(int n=0;n<chequeNo.length-1;n++){
			                    if(ChequeNumbers.length() > 0){
			                    	ChequeNumbers.append("-");
			                    }
			                    ChequeNumbers.append(chequeNo[m]);
			                 
			                    m++;
			                }
			         
			        }*/
			 //       ChequeNumbers.append(chequeNo[i]);
			        
			    //    settlementDetailsVO.setSettlementChequeNumbers(ChequeNumbers.toString());
					if(chequeDate[i]!=null && chequeDate[i].trim().length()>0){
						settlementDetailsVO.setChequeDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(chequeDate[i]));
					}					
					settlementDetailsVO.setChequeBank(bank[i]);
					settlementDetailsVO.setChequeBranch(branch[i]);
					settlementDetailsVO.setChequeCurrency(session.getInvoiceSettlementFilterVO().getSettlementCurrency());
					settlementDetailsVO.setRemarks(remarks[i]);
					//settlementDetailsVO.setIsAccounted("Y");
					if(chequeAmount[i]!=null&&chequeAmount[i].trim().length()>0){
						currChequeAmt= Double.parseDouble(chequeAmount[i]);
					}
					try{
						if(session.getInvoiceSettlementFilterVO().getSettlementCurrency()!= null && session.getInvoiceSettlementFilterVO().getSettlementCurrency().trim().length()>0){
							Money currAmt=CurrencyHelper.getMoney(session.getInvoiceSettlementFilterVO().getSettlementCurrency());
							currAmt.setAmount(currChequeAmt);			
							settlementDetailsVO.setChequeAmount(currAmt);
						}				
					}
					catch(CurrencyException e){
						e.getErrorCode();
					}			
					settlementDetailsVOs.add(settlementDetailsVO);
					index++;
							//}
						} 
				else if(settlementDetailsVOsInSession!=null && settlementDetailsVOsInSession.size()>0 && j+1<=settlementDetailsVOsInSession.size()){
					
					settlementDetailsVO=settlementDetailsVOsInSession.get(j);
					
					if("U".equals(opFlg[j])){
						if("OK".equals(form.getActionFlag())){
							settlementDetailsVO.setStlFlag("Y");
							/*settlementDetailsVO.setSerialNumber(stlvo.getSerialNumber());
			                  settlementDetailsVO.setSettlementSequenceNumber(stlvo.getSettlementSequenceNumber());*/
						}
						else if("CREATE".equals(form.getActionFlag())){
							settlementDetailsVO.setStlFlag("N");
						}
						settlementDetailsVO.setOperationFlag(opFlg[j]);	
						if(("true").equals(isDeleteArray[j])&& !"Y".equals(settlementDetailsVO.getIsDeleted())) {							
							settlementDetailsVO.setIsDeleted("Y");					
							settlementDetailsVO.setIsAccounted("N");
							//form.setCheckFlag(CHECKSELECTEDINVOICE);	

							settlementDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							settlementDetailsVO.setGpaCode(gpaSettlementVO.getGpaCode());
							
							settlementDetailsVO.setSettlementChequeNumbers(settlementDetailsVO.getChequeNumber());
						
							settlementDetailsVOs.add(settlementDetailsVO);
							
						}
					}
					else if("D".equals(opFlg[j])){
						//form.setCheckFlag(DELETED);	
					}
					
				/*	else if("N".equals (opFlg[j]))
					{
						settlementDetailsVO=settlementDetailsVOsInSession.get(j);
						settlementDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
						settlementDetailsVO.setGpaCode(gpaSettlementVO.getGpaCode());
						if("OK".equals(form.getActionFlag())){
							settlementDetailsVO.setStlFlag("Y");
						}
						settlementDetailsVO.setOperationFlag(opFlg[j]);
						settlementDetailsVOs.add(settlementDetailsVO);
							
					}	*/
					
						
						
						

				}
				settlementDetailsVO.setFromScreen("MRA076");	
			}

		}

		return settlementDetailsVOs;
	}
	
	
	/**
	 * 
	 * @param settlementDetailsVO
	 * @return
	 */
	private Collection<ErrorVO> validateForm(InvoiceSettlementMailbagForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		int index=0;
		boolean isChequeExist=false;
		for(int j=0;j<form.getStlOpFlag().length-1;j++){
			
			
			if("I".equals(form.getStlOpFlag()[j])||"N".equals(form.getStlOpFlag()[j])||"U".equals(form.getStlOpFlag()[j])){
				isChequeExist=true;
			}
			
			if("I".equals(form.getStlOpFlag()[j])){
				
				int i=index;
			if(form.getChequeNumber()[i]==null ||  form.getChequeNumber()[i].trim().length()<=0){			//Modified by A-8399 as part of ICRD-302177
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequenumbermandatory"));
			}
			if(form.getChequeDate()[i]==null || form.getChequeDate()[i].trim().length()<=0){				//Modified by A-8399 as part of ICRD-302177
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequedatemandatory"));
			}
			if(form.getChequeAmount()[i]==null || form.getChequeAmount()[i].trim().length()<=0){			//Modified by A-8399 as part of ICRD-302177
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequeamountmandatory"));
			}
			if(Double.parseDouble(form.getChequeAmount()[i])<=0){			//Modified by A-8399 as part of ICRD-302177
				errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.invalidchequeamt"));
			}
			
			index++;
			}
			
		}
		if(!isChequeExist){
			errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.chequenumbermandatory"));
		}
		return errors;
	}
	
	
	private Collection<SettlementDetailsVO> populateDummySettelementDetails(
			InvoiceSettlementMailbagForm form, InvoiceSettlementMailbagSession session, GPASettlementVO gpaSettlementVO,String[] selectedRow)
	{
		Collection<InvoiceSettlementVO> invoiceSettlementVOs = null;
		Collection<SettlementDetailsVO> settlementDetailsVOs = new ArrayList<SettlementDetailsVO>();
		List <SettlementDetailsVO> settlementDetailsVOsInSession= null;	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		LocalDate currentTime = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		if(session.getSelectedGPASettlementVO()!=null){
			settlementDetailsVOsInSession= (List<SettlementDetailsVO>)session.getSelectedGPASettlementVO().iterator().next().
			getSettlementDetailsVOs();
		}
		//int selectedRow = form.getCurrentSettlingAmount().length;
			if(selectedRow!=null){
				invoiceSettlementVOs=new ArrayList<InvoiceSettlementVO>();
				for (String index : selectedRow) {
					if(form.getCurrentSettlingAmount()[Integer.parseInt(index)]!=null ){ //Modified by A-8399 as part of ICRD-305647
								Money amount=null;
								try {
									amount = CurrencyHelper.getMoney(session.getInvoiceSettlementFilterVO().getSettlementCurrency());
									amount.setAmount(Double.parseDouble(form.getCurrentSettlingAmount()[Integer.parseInt(index)]));
									SettlementDetailsVO settlementDetailsVO = new SettlementDetailsVO();				
									settlementDetailsVO.setChequeAmount(amount);
									settlementDetailsVO.setChequeBank("0000");
									settlementDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
									settlementDetailsVO.setGpaCode(gpaSettlementVO.getGpaCode());
									settlementDetailsVO.setChequeBranch("0000");
									settlementDetailsVO.setChequeDate(currentTime);
									settlementDetailsVO.setChequeNumber("0000");
									settlementDetailsVO.setIsDeleted("N");
									settlementDetailsVO.setSettlementChequeNumbers("0000");
									settlementDetailsVO.setIndex(Integer.parseInt(index));
									settlementDetailsVO.setSettlementId(session.getInvoiceSettlementFilterVO().getSettlementReferenceNumber());
									settlementDetailsVO.setChequeCurrency(session.getInvoiceSettlementFilterVO().getSettlementCurrency());
									settlementDetailsVO.setIsAccounted("Y");
									if(settlementDetailsVOsInSession.size()>0){
										settlementDetailsVO.setSettlementSequenceNumber(settlementDetailsVOsInSession.iterator().next().getSettlementSequenceNumber());
										
										settlementDetailsVO.setSerialNumber(settlementDetailsVOsInSession.get(Integer.parseInt(index)).getSerialNumber());
										}
									settlementDetailsVOs.add(settlementDetailsVO);
									
				
									
								} catch (CurrencyException e) {
									// TODO Auto-generated catch block
									log.log(Log.FINE,"Inside CurrencyException.. ");
								}
							}
							}	
						
					

				}
			
			return settlementDetailsVOs;
			}

		
	

}
