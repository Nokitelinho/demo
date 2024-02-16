/*
 * CloseCommand.java Created on Sep 9, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.editrange;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.EditRangeForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.EditRangeSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author A-1927
 *
 */
public class CloseCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {


    		log.entering("CloseCommand","execute");
    		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
    		EditRangeForm editRangeForm = (EditRangeForm) invocationContext.screenModel;
			EditRangeSession session = (EditRangeSession)
							getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.editrange");
			 AllocateStockSession allocateSession= (AllocateStockSession)getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			Collection<ErrorVO> errors = null;
			Collection<RangeVO> rangeVOs =session.getAllocatedRangeVos();
			//Modifed A-1927 for new Add/Delete operation starts
			String[] allocRangeFrom=editRangeForm.getStockRangeFrom();
			String[] allocRangeTo=editRangeForm.getStockRangeTo();
			String[] allocNumDocs=editRangeForm.getNoOfDocs();
			String[] hiddenOpFlag=editRangeForm.getHiddenOpFlag();
			//Modified by A-5807 for ICRD-76030
			DocumentTypeDelegate documentTypeDelegate=new DocumentTypeDelegate();
			for(int i=0;i<allocRangeFrom.length-1;i++){
				DocumentVO documentVO=new DocumentVO();
		        documentVO.setDocumentType(editRangeForm.getDocType());
		        documentVO.setCompanyCode(logonAttributes.getCompanyCode());
		        documentVO.setDocumentSubType(editRangeForm.getSubType());
		        SharedRangeVO sharedRangeVO=new SharedRangeVO();
		        sharedRangeVO.setFromrange(allocRangeFrom[i]);
		        sharedRangeVO.setToRange(allocRangeTo[i]);
		        Collection<SharedRangeVO> sharedRangeVOs=new ArrayList<SharedRangeVO>();
		        sharedRangeVOs.add(sharedRangeVO);
		        documentVO.setRange(sharedRangeVOs);
		        try {
					documentTypeDelegate.validateRange(documentVO);
				} catch (BusinessDelegateException e) {
					ErrorVO error = null;
					error = new ErrorVO("shared.document.invalidalphanumericrange");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
					errorVOs.add(error);
					invocationContext.addAllError(errorVOs);
					invocationContext.target = "screenload_failure";
					return;
				}
			}
			int iterator=0;
			Collection<RangeVO> rangesForSave = new ArrayList<RangeVO>();
			if(rangeVOs != null && rangeVOs.size()>0){
				for(RangeVO tempRange:rangeVOs){
					if(allocRangeFrom[iterator] != null && allocRangeFrom[iterator].trim().length()>0 &&
							allocRangeTo[iterator] != null && allocRangeTo[iterator].trim().length()>0 &&
							hiddenOpFlag[iterator] != null && hiddenOpFlag[iterator].trim().length()>0){
						if(hiddenOpFlag[iterator].equalsIgnoreCase(OPERATION_FLAG_UPDATE)){
							tempRange.setOperationFlag(OPERATION_FLAG_UPDATE);
							tempRange.setStartRange(allocRangeFrom[iterator]);
							tempRange.setEndRange(allocRangeTo[iterator]);
							tempRange.setNumberOfDocuments(Long.valueOf(allocNumDocs[iterator]));
							rangesForSave.add(tempRange);
						}
						if(hiddenOpFlag[iterator].equalsIgnoreCase(OPERATION_FLAG_DELETE)){
							tempRange.setOperationFlag(OPERATION_FLAG_DELETE);
							//rangesForSave.add(tempRange);
						}
					}
					iterator++;
				}
			}
			RangeVO rangeVoForInsert = null;
			if(hiddenOpFlag != null && hiddenOpFlag.length>0){
				iterator = 0;
				for(String operationFlag : hiddenOpFlag){
					if(operationFlag.equalsIgnoreCase(OPERATION_FLAG_INSERT)){
						rangeVoForInsert = new RangeVO();
						rangeVoForInsert.setOperationFlag(OPERATION_FLAG_INSERT);
						rangeVoForInsert.setStartRange(allocRangeFrom[iterator]);
						rangeVoForInsert.setEndRange(allocRangeTo[iterator]);
						//added as a part of icrd-219881
						rangeVoForInsert.setNumberOfDocuments(Long.valueOf(allocNumDocs[iterator]));
						rangesForSave.add(rangeVoForInsert);
					}
					iterator++;
				}
			}
			//Modifed A-1927 for new Add/Delete operation ends
			session.setAllocatedRangeVos(rangesForSave);
			allocateSession.setRangeVO(rangesForSave);

			log.log(Log.FINE,"\n\n\n...........Before validate form...." );
			errors = validateForm(editRangeForm,session);
			log.log(Log.FINE, "\n\n\n...........After validate form....",
					errors.size());
			if (errors != null && errors.size() > 0) {
				editRangeForm.setIsValidRange("N");
			    log.log(Log.FINE,"\n\n\n...........Inside errors............");
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
			}
			else{
				log.log(Log.FINE,"\n\n\n.......... errors else............");
				editRangeForm.setIsValidRange("Y");
			}
			log.exiting("CloseCommand","execute");
			invocationContext.target = "screenload_success";

    }

    /** To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	private  long findLong(String range){
			log.log(Log.FINE,"...........Entering ascii conversion.......");
			char[] sArray=range.toCharArray();
			long base=1;
			long sNumber=0;
			for(int i=sArray.length-1;i>=0;i--){
				sNumber+=base*calculateBase(sArray[i]);
				int count=sArray[i];
				if (count>57) {
					base*=26;
				} else {
					base*=10;
				}
			}
			return sNumber;
	}

	private  long calculateBase(char str){
			long formatStr=str;
			long base=0;
			try{
				base=Integer.parseInt(""+str);
			}catch(NumberFormatException numberFormatException){
				base=formatStr-65;
			}
			return base;
	}

	/** To find the difference between range from and range to value
	 *
	 * @param rangeFrom Range from value
	 * @param rangeTo Range to value
	 * @return Difference between the ranges
	 */
	private int difference(String rangeFrom,String rangeTo){

		long difference=findLong(rangeTo)-findLong(rangeFrom);
		difference++;
		return (int)difference;
	}

    private Collection<ErrorVO> validateForm(EditRangeForm form,
				EditRangeSession session) {

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			boolean isCheckFromNull=false;
			boolean isCheckToNull=false;
			boolean isCheckDuplicate=false;
			boolean isCheckFlow=false;
			boolean isCheckTo=false;
			boolean isCheckOverlap=false;
			boolean isCheckRange=false;
			String[] allocRangeFrom=form.getStockRangeFrom();
			String[] allocRangeTo=form.getStockRangeTo();
			//log.log(Log.FINE,"\n\n\n...........Inside validate form..allocRangeFrom.........."+allocRangeFrom);
			//log.log(Log.FINE,"\n\n\n...........Inside validate form....allocRangeTo........"+allocRangeTo);
			if( allocRangeFrom!=null && allocRangeTo!=null){
				for(int i=0;i<(allocRangeFrom.length-1);i++){
					for(int j=i+1;j<allocRangeFrom.length;j++){
						log
								.log(
										Log.FINE,
										"\n\n\n...........Inside loop.......allocRangeFrom[i].....",
										allocRangeFrom, i,
										"\n allocRangeFrom[j]", allocRangeFrom,
										j, "\n allocRangeTo[i]", allocRangeTo,
										i, "\n allocRangeTo[j]", allocRangeTo,
										j);
						if("".equals(allocRangeFrom[i]) || "".equals(allocRangeTo[i]) || "".equals(allocRangeFrom[j]) || "".equals(allocRangeTo[j])) {
							break;
						} else{
							log.log(Log.FINE,"\n\n\n...........Inside else............");
							if(findLong(allocRangeFrom[i])==findLong(allocRangeFrom[j])
								&&	findLong(allocRangeTo[i])==findLong(allocRangeTo[j])){

									isCheckDuplicate=true;
									break;
							}

							if(findLong(allocRangeTo[i])==findLong(allocRangeFrom[j])){

									isCheckFlow=true;
									break;
							}

							if(findLong(allocRangeTo[i])==findLong(allocRangeTo[j])){

									isCheckTo=true;
									break;
							}
							/*if(findLong(allocRangeFrom[i])<=findLong(allocRangeFrom[j])
									&& findLong(allocRangeTo[i])>findLong(allocRangeTo[j])){

									checkOverlap=true;
									break;
							}*/

							if(	  (
						        	findLong(allocRangeFrom[i])==
							        	findLong(allocRangeFrom[j])
							        	) ||((
						        	findLong(allocRangeFrom[i])<
						        	findLong(allocRangeFrom[j])
						        	)
						           &&
						           (
						        	findLong(allocRangeTo[i])>
						        	findLong(allocRangeFrom[j])
						        	))
						           ||((
						    		   findLong(allocRangeTo[j])<
						    		   findLong(allocRangeTo[i])
						    		   )
						           &&(
						    		   findLong(allocRangeFrom[i])<
						    		   findLong(allocRangeTo[j]))
						           	)
						        	){

								   isCheckOverlap=true;
						           break;
						         }
						}


					}
				}
				for(int i=0;i<allocRangeFrom.length;i++){

					if(!((allocRangeFrom[i]==null || allocRangeFrom[i].trim().length()==0) &&
							(allocRangeTo[i]==null || allocRangeTo[i].trim().length()==0))){
						if(allocRangeFrom[i]==null || allocRangeFrom[i].trim().length()==0){
							isCheckFromNull=true;
							break;
						}else if(allocRangeTo[i]==null || allocRangeTo[i].trim().length()==0){
							isCheckToNull=true;
							break;
						}

					}
					if(findLong(allocRangeFrom[i])>findLong(allocRangeTo[i])){

						isCheckRange=true;
						break;
					}
				}
				log.log(Log.FINE,
						"\n\n\n...........flags........isCheckDuplicate....",
						isCheckDuplicate, "\n  isCheckFlow", isCheckFlow,
						"\n isCheckTo", isCheckTo, "\n isCheckOverlap",
						isCheckOverlap, "\n isCheckFromNull", isCheckFromNull,
						"\n isCheckRange", isCheckRange);
				if(isCheckFromNull){
							Object[] obj = { "Allocated-Range-From" };
							error = new ErrorVO("stockcontrol.defaults.plsenterallocatedrngfrmvalintab", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							return errors;
				}

				if(isCheckToNull){
					Object[] obj = { "Allocated-Range-To" };
					error = new ErrorVO("stockcontrol.defaults.editrange.err.plzenterrangeto", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					return errors;
				}

				if(isCheckDuplicate){
							Object[] obj = { "Allocated-Range-Duplicate" };
							error = new ErrorVO("stockcontrol.defaults.duplirngcannotbeentered", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							return errors;
				}

				if(isCheckFlow){
							Object[] obj = { "Allocated-Range-Flow" };
							error = new ErrorVO("stockcontrol.defaults.cntrngcannotbeentered", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							return errors;
				}

				if(isCheckTo){
							Object[] obj = { "Allocated-Range-To" };
							error = new ErrorVO("stockcontrol.defaults.endrngcannotbesame", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							return errors;
				}

				if(isCheckOverlap){
							Object[] obj = { "Allocated-Range-Overlap" };
							error = new ErrorVO("stockcontrol.defaults.overlapingrngcannotbeentered", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							return errors;
				}
				if(isCheckRange){
							Object[] obj = { "Range From Exceeds" };
							error = new ErrorVO("SKCM_099", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							return errors;
				}
			}




		return errors;
	}


}
