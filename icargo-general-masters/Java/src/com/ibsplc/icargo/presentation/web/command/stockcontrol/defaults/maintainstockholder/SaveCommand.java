/*
 * SaveCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;

import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1870
 *
 */
public class SaveCommand extends BaseCommand {

	//Added by A-1927 @ NRT on 02-Aug-2007 for NCA Bug Fix starts

	private static final String AWB = "AWB";

	private static final String S = "S";

	private static final String CONTROL_PRIVILEGE_ALL = "All";

	//Added by A-1927 @ NRT on 02-Aug-2007 for NCA Bug Fix ends
	private static final String SAVE_MODE = "save";
	private static final String OPERATION_FLAG_INSERT = "I";

	private static final String OPERATION_FLAG_UPDATE = "U";
	private static final String OPERATION_FLAG_DELETE = "D";
	private Log log = LogFactory.getLogger("STOCK CONTROL SQLDAO");
	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)  
	throws CommandInvocationException {
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
		MaintainStockHolderForm maintainStockHolderForm=(MaintainStockHolderForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(maintainStockHolderForm,session);

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "save_failure";
			return;
		}
		Collection<StockVO> stockvos=session.getStockVO();
		if(stockvos!=null){
			Collection<ErrorVO> invalidApprovers = null;
			ArrayList<String> approverCodes = new ArrayList<String>();

			for(StockVO vo :stockvos){
				if(!OPERATION_FLAG_DELETE.equals(vo.getOperationFlag())){

					approverCodes.add(vo.getStockApproverCode());

				}
			}

			/*  ArrayList<String> otherApproverCodes = new ArrayList<String>(approverCodes);
			 ArrayList<String> newApproverCodes = new ArrayList<String>(approverCodes);
			 int length=approverCodes.size();
			 String approve = null;
			 for(int i=0;i< length;i++){
			 boolean isPresent = false;
			 for(int j=i+1;j< length;j++){
			 approve=otherApproverCodes.get(i);
			 if(approve.equals(newApproverCodes.get(j))){
			 isPresent = true;

			 }
			 if(isPresent){
			 newApproverCodes.remove(approve);
			 }

			 }

			 }


			 System.out.println("***********new approver codes********************"+newApproverCodes);*/

			String stockHolderType=maintainStockHolderForm.getStockHolderType();

			Collection<StockHolderPriorityVO> stockHolderpriorityVos=session.getPrioritizedStockHolders();
			for(StockHolderPriorityVO stockHolderPriorityVO:stockHolderpriorityVos){
				if(stockHolderType.equals(stockHolderPriorityVO.getStockHolderType())){
				   if(stockHolderPriorityVO.getPriority()>1){
						try{
							new StockControlDefaultsDelegate().validateStockHolders(
									getApplicationSession().getLogonVO().getCompanyCode(),approverCodes);
							Collection<StockHolderPriorityVO> codePriors=new StockControlDefaultsDelegate().
							findPriorities(getApplicationSession().getLogonVO().getCompanyCode(),approverCodes);
							if(codePriors!=null){
							for(StockHolderPriorityVO priorityvo:codePriors){
								if((stockHolderPriorityVO.getPriority()<=priorityvo.getPriority())){
									log.log(Log.FINE,"\n\n-------------inside mismatch priority --->");
									Object[] obj = { "stockHolder" };
									ErrorVO error = new ErrorVO("stockcontrol.defaults.cannotproceed", obj);
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									invocationContext.addAllError(errors);
									invocationContext.target = "save_failure";
									return;
								}
							}
						}


						}catch(BusinessDelegateException businessDelegateException){
							log.log(Log.FINE,"--------------------inside exception handling  --->");
							errors = handleDelegateException(businessDelegateException);
							/*Object[] obj = { "stockHolderCode" };
							ErrorVO error = new ErrorVO("stockcontrol.defaults.invalidstockholder", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);*/
							invocationContext.addAllError(errors);
							invocationContext.target = "save_failure";
							return;
						}
			       }
				}
		    }

		}

		StockHolderVO stockHolderVO=new StockHolderVO();
		StockHolderVO stkHolderVO = session.getStockHolderVO();
		stockHolderVO.setStockHolderType(maintainStockHolderForm.getStockHolderType());
		stockHolderVO.setStockHolderCode(upper(maintainStockHolderForm.getStockHolderCode()));
		stockHolderVO.setStockHolderName(maintainStockHolderForm.getStockHolderName());
		stockHolderVO.setControlPrivilege(maintainStockHolderForm.getControlPrivilege());
		stockHolderVO.setCompanyCode(logonAttributes.getCompanyCode());
		stockHolderVO.setStockHolderContactDetails(maintainStockHolderForm.getContact());
		log.log(Log.FINE, "--------------------contact details  --->",
				maintainStockHolderForm.getContact());
		stockHolderVO.setStock(session.getStockVO());

		if (SAVE_MODE.equals(session.getMode())){
			stockHolderVO.setOperationFlag(OPERATION_FLAG_INSERT);
			stockHolderVO.setLastUpdateUser(logonAttributes.getUserId());
			stockHolderVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));

		}else{
			stockHolderVO.setOperationFlag(OPERATION_FLAG_UPDATE);
			if(stkHolderVO != null){
				stockHolderVO.setLastUpdateUser(stkHolderVO.getLastUpdateUser());
				stockHolderVO.setLastUpdateTime(stkHolderVO.getLastUpdateTime());
			}
		}
		try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			stockControlDefaultsDelegate.saveStockHolderDetails(stockHolderVO);


		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
		}

		if (errors != null && errors.size() > 0) {

			invocationContext.addAllError(errors);
			invocationContext.target = "save_failure";
		}
		else{
			ErrorVO error = null;
			Object[] obj = { "Save" };
			error = new ErrorVO("stockcontrol.defaults.saveddetailssuccessfully", obj);// Saved Successfully
			error.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "save_success";
			//Added by A-7364 as part of ICRD-217145 starts
			MessageInboxSession messageInboxSession = 
					(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
			if(messageInboxSession.getMessageDetails() != null){
				invocationContext.target = "savestockholder_success";
			}
			//Added by A-7364 as part of ICRD-217145 ends
			session.setStockVO(null);
			//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug fix starts
			maintainStockHolderForm.setControlPrivilege(CONTROL_PRIVILEGE_ALL);
			//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug fix ends
			maintainStockHolderForm.setStockHolderName("");
			maintainStockHolderForm.setStockHolderCode("");
			maintainStockHolderForm.setStockHolderType("");
			maintainStockHolderForm.setContact("");
			StockVO stockVO=new StockVO();
			stockVO.setReorderLevel(0);
			stockVO.setReorderQuantity(0);

			//Added for Auto processing
			stockVO.setAutoprocessQuantity(0);

			stockVO.setRemarks("");
			//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug fix starts
			stockVO.setDocumentSubType(S);
			stockVO.setDocumentType(AWB);
			//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug fix ends
			stockVO.setStockApproverCode("");
			stockVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
			stockVO.setReorderAlertFlag(false);
			stockVO.setAutoRequestFlag(false);
			stockVO.setAutoPopulateFlag(false);
			stockVO.setOperationFlag(OPERATION_FLAG_INSERT);
			Collection<StockVO> stockVo=new ArrayList<StockVO>();
			stockVo.add(stockVO);
			session.setStockVO(stockVo);
			session.setMode(SAVE_MODE);
			return;
		}

	}

	private Collection<ErrorVO> validateForm(MaintainStockHolderForm form,
			MaintainStockHolderSession session) {


		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isapprover=false;
		boolean isdoctype=false;
		boolean isdocsubtype=false;
		boolean isAutoprocessquantity = false;

		if("".equals(form.getStockHolderType().trim())){
			Object[] obj = { "stockHolderType" };
			error = new ErrorVO("stockcontrol.defaults.stkhldtypeismandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if("".equals(form.getStockHolderCode().trim())){
			Object[] obj = { "stockHolderCode" };
			error = new ErrorVO("stockcontrol.defaults.plsenterfrmdate", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if("".equals(form.getStockHolderName().trim())){
			Object[] obj = { "stockHolderName" };
			error = new ErrorVO("stockcontrol.defaults.plsentertodate", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if("".equals(form.getControlPrivilege().trim())){
			Object[] obj = { "controlPrivilege" };
			error = new ErrorVO("stockcontrol.defaults.ctrlprivilegemandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		ArrayList<StockVO> stockVO=(ArrayList<StockVO>)session.getStockVO();
		if (stockVO != null) {
		 StockVO firstVO = null;
		 StockVO secondVO = null;

		 ArrayList<StockVO> stockList = new ArrayList<StockVO>(stockVO);
		 int size=stockList.size();

		 outer:
		 for (int i = 0; i < size; i++) {
			 firstVO = (StockVO) stockVO
			 .get(i);
			 if(firstVO.getOperationFlag()!=null){
				 if(!OPERATION_FLAG_DELETE.equalsIgnoreCase(firstVO.getOperationFlag())){
					 for (int j = i + 1; j < size; j++) {
						 secondVO = (StockVO) stockList
						 .get(j);
						 if(!OPERATION_FLAG_DELETE.equalsIgnoreCase(secondVO.getOperationFlag())){
							 if (firstVO.getDocumentType().equals(secondVO.getDocumentType())) {
								 if (firstVO.getDocumentSubType().equals(secondVO.getDocumentSubType())
									&& firstVO.getAirlineIdentifier()==secondVO.getAirlineIdentifier()) {
								 Object[] obj = { "type" };
								 error = new ErrorVO("stockcontrol.defaults.cantselectsamedoctypeandsubtypecombination", obj);
								 error.setErrorDisplayType(ErrorDisplayType.ERROR);
								 errors.add(error);
								 break outer;
								 }
								 
								 if (!firstVO.getDocumentSubType().equals(secondVO.getDocumentSubType())
										&& (firstVO.isAutoPopulateFlag() && secondVO.isAutoPopulateFlag())) {
									 error = new ErrorVO("stockcontrol.defaults.cantselectautopopulateflagcombination");
									 error.setErrorDisplayType(ErrorDisplayType.ERROR);
									 errors.add(error);
									 break outer;
				
								 }
							 }
						 }
					 }
				 }
			 }
		 }

		 }
		if(stockVO!=null){
			for(StockVO stockVo:stockVO){
				String approver=stockVo.getStockApproverCode();
				String docType= stockVo.getDocumentType();
				String docSubType=stockVo.getDocumentSubType();
				String stockHolderType=form.getStockHolderType();
				Collection<StockHolderPriorityVO> stockHolderpriorityVos=session.getPrioritizedStockHolders();
				for(StockHolderPriorityVO stockHolderPriorityVO:stockHolderpriorityVos){
					if(stockHolderType.equals(stockHolderPriorityVO.getStockHolderType())){
						long priority= stockHolderPriorityVO.getPriority();
						if(priority>1){
							log
									.log(
											Log.FINE,
											"\n\n----------approver--------------------------- --->",
											approver);
							if((approver==null || approver.trim().length()==0)&&  !(isapprover)){
								Object[] obj = { "approver" };
								error = new ErrorVO("stockcontrol.defaults.approvercodemandatory", obj);
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								isapprover=true;

							}
						}else{
							log
									.log(
											Log.FINE,
											"\n\n----------approver-2-------------------------- --->",
											approver);
							if((approver!=null && approver.trim().length()!=0 )&&  !(isapprover)){
								log.log(Log.FINE,"\n\n----------inside approver!=null || approver.trim().length()!=0 )&&  !(isapprover --->");
								Object[] obj = { "app" };
								error = new ErrorVO("stockcontrol.defaults.approvercodenotneeded", obj);
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								isapprover=true;
							}
						}
					}
				}
				if(("".equals(docType.trim())) && !(isdoctype)){

					Object[] obj = { "type" };
					error = new ErrorVO("stockcontrol.defaults.doctypemandatory", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					isdoctype=true;

				}
				if(("".equals(docSubType.trim())) && !(isdocsubtype)){
					Object[] obj = { "subtype" };
					error = new ErrorVO("stockcontrol.defaults.docsubtypemandatory", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					isdocsubtype=true;

				}
				//Code commented for ICRD-233124
				/*if(stockVo.isAutoRequestFlag() && stockVo.getAutoprocessQuantity()==0 && !isAutoprocessquantity){
					Object[] obj = { "autoprocessquantity" };
					error = new ErrorVO("stockcontrol.defaults.autoprocessquantitymandatory", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					isAutoprocessquantity=true;
				}*/


			}

		}
	return errors;
	}

	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}
}
