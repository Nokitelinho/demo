/*
 * BlacklistStockCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.blackliststock;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.BlackListStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1952
 *
 */
public class BlacklistStockCommand extends BaseCommand {

	//Added by A-1927 @ NCA on 02-Aug-2007 for NCA Bug Fix starts

	private static final String AWB = "AWB";

	private static final String S = "S";

	//Added by A-1927 @ NCA on 02-Aug-2007 for NCA Bug Fix ends

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("BlacklistStockCommand","execute");
		boolean isFromNull=false;
		BlackListStockForm form = (BlackListStockForm)invocationContext.screenModel;
		BlackListStockSession session =(BlackListStockSession) getScreenSession( "stockcontrol.defaults",
		                                             "stockcontrol.defaults.blackliststock");
		
//		--------	 Added by a2434 for depleting AWB before Blacklisting-----------
		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
		stockDepleteFilterVO.setAirlineId(getAirlineIdentifier(form.getAwbPrefix()));
		stockDepleteFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		stockDepleteFilterVO.setDocumentSubType(form.getSubType());
		stockDepleteFilterVO.setDocumentType(form.getDocType());
		
//		--------	 Added by a2434 for doc format validation --------------
		DocumentVO documentVO = new DocumentVO();
		if(!isFromNull){
			Collection<SharedRangeVO> newSharedRangeVOs = new ArrayList<SharedRangeVO>();
			SharedRangeVO sharedRangeVO = new SharedRangeVO();
			sharedRangeVO.setFromrange(form.getRangeFrom());                                   
			sharedRangeVO.setToRange(form.getRangeTo());				
			newSharedRangeVOs.add(sharedRangeVO);
			documentVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			documentVO.setDocumentSubType(form.getSubType());
			documentVO.setDocumentType(form.getDocType());
			documentVO.setRange(newSharedRangeVOs);
		}
//		--------	 Added by a2434 for doc format validation ends--------------
        BlacklistStockVO blacklistStockVO = new BlacklistStockVO();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        Collection<ErrorVO> errorVos= null;
        Collection<ErrorVO> errors = null;
        errors = validateForm(form,session,isFromNull);
		if (errors != null && errors.size() > 0) {
			 invocationContext.addAllError(errors);
			 invocationContext.target = "screenload_success";
			 return;
		}
        try{
			//Calendar lastUpdateDate =Calendar.getInstance(logonAttributes.getTimeZone());
			blacklistStockVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			blacklistStockVO.setLastUpdateUser(logonAttributes.getUserId());
			blacklistStockVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			blacklistStockVO.setAirlineIdentifier(getAirlineIdentifier(form.getAwbPrefix()));
			blacklistStockVO.setDocumentSubType(form.getSubType());
			blacklistStockVO.setDocumentType(form.getDocType());
			if("".equals(form.getRangeFrom())){
				blacklistStockVO.setStockHolderCode(form.getStockHolderCode());
			}else{
				blacklistStockVO.setRangeFrom(form.getRangeFrom());
				blacklistStockVO.setRangeTo(form.getRangeTo());
			}
			blacklistStockVO.setRemarks(form.getRemarks());
			
			blacklistStockVO.setStationCode(getApplicationSession().getLogonVO().getStationCode());
			LocalDate blackListDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			String blackListDateStr = blackListDate.toDisplayFormat(CALENDAR_DATE_FORMAT);
			blacklistStockVO.setBlacklistDate(blackListDate.setDate(blackListDateStr));
			blacklistStockVO.setActionCode(BlacklistStockVO.ACTION_BLACKLIST);
			session.setBlacklistStockVO(blacklistStockVO);
			log
					.log(
							Log.FINE,
							"\n\n.....................blacklistStockVO...............> ",
							blacklistStockVO);
			//        	--------	 Added by a2434 for doc format validation if doc range is specified--------------
	        if(!isFromNull && form.getRangeFrom()!=null && form.getRangeFrom().trim().length()>0 && form.getRangeTo()!=null && form.getRangeTo().trim().length()>0){
	        	log
						.log(
								Log.FINE,
								"\n\n.....................blacklistStockVO...............>",
								documentVO);
				DocumentTypeDelegate documentTypeDelegate = new DocumentTypeDelegate();
	        	documentTypeDelegate.validateRange(documentVO);
        	}
//        	--------	 Added by a2434 for doc format validation ends--------------
	        
//        	--------Added by a2434 for depleting AWB before Blacklisting--------------	        
	      //Commented by A-5153 for CRQ_ICRD-38007
	        //new StockControlDefaultsDelegate().autoStockDeplete(stockDepleteFilterVO);
			new StockControlDefaultsDelegate().blacklistStock(session.getBlacklistStockVO());

    	}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
			for(ErrorVO errorVO:errorVos){
				log.log(Log.FINE, "\n\n.......errorVO", errorVO.getErrorCode());
			}
		}
		if (errorVos != null && errorVos.size() > 0) {
		log.log(Log.FINE,"\n\n......errorVos != null && errorVos.size() > 0");
	     invocationContext.addAllError(errorVos);
	     invocationContext.target = "screenload_success";
	     return;
   }
   if(errorVos==null || errorVos.size()==0){
		errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		Object[] obj = { "Blacklist" };
		error = new ErrorVO("blackliststock.blacklistsuccess", obj);
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		errors.add(error);
		invocationContext.addAllError(errors);

	}

	if("popup".equals(session.getMode())){
		log.log(Log.FINE,"\n\n.....................popup...............> ");
		//Modified by A-1927 @ NRT on 02-Aug-2007for NCA Bug Fix starts
		form.setDocType(AWB);
		form.setSubType(S);
		//Modified by A-1927 @ NRT on 02-Aug-2007for NCA Bug Fix ends
		form.setRemarks("");
		form.setRangeFrom("");
		form.setRangeTo("");
		form.setStockHolderCode("");
        invocationContext.target = "screenloadpopup_success";
	}
	//Modified by A-1927 @ NRT on 02-Aug-2007for NCA Bug Fix starts
	form.setDocType(AWB);
	form.setSubType(S);
	//Modified by A-1927 @ NRT on 02-Aug-2007for NCA Bug Fix ends
	form.setRemarks("");
	form.setRangeFrom("");
	form.setRangeTo("");
	form.setStockHolderCode("");
	invocationContext.target = "screenload_success";

  }
  private int getAirlineIdentifier(String awbPrefix) {
	  int airlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier(); 
	  if(awbPrefix!=null && awbPrefix.trim().length()>0){
		  String[] tokens=awbPrefix.split("-");
		  return (tokens!=null && tokens.length>2)?Integer.parseInt(tokens[2]):airlineIdentifier;
	  }
		return airlineIdentifier;
	}
private Collection<ErrorVO> validateForm(BlackListStockForm form,
         BlackListStockSession session,boolean isFromNull) {

	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	ErrorVO error = null;

	String rangeFrom=form.getRangeFrom();
	String rangeTo=form.getRangeTo();
	String stockHolderCode=form.getStockHolderCode();

	if("".equals(stockHolderCode)&&"".equals(rangeFrom)&&"".equals(rangeTo)){
		Object[] obj = { "Mandatory" };
		error = new ErrorVO("stockcontrol.defaults.plsenterValues", obj);
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
		return errors;
	}
	if("".equals(rangeFrom)&&!"".equals(rangeTo)){
		Object[] obj = { "Mandatory" };
		error = new ErrorVO("stockcontrol.defaults.plsenterallocatedrngproper", obj);
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
	}
	if(!"".equals(rangeFrom)&&"".equals(rangeTo)){
		Object[] obj = { "Mandatory" };
		error = new ErrorVO("stockcontrol.defaults.plsenterallocatedrngproper", obj);
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
	}
	if(!"".equals(stockHolderCode)){
		if("".equals(rangeFrom)||"".equals(rangeTo)){
			isFromNull=true;
		}
		return errors;
	}
	if("".equals(rangeFrom)){
		isFromNull=true;
    }
    if("".equals(rangeTo)){
	    isFromNull=true;
    }
	if(isFromNull){
		Object[] obj = { "Mandatory" };
		error = new ErrorVO("stockcontrol.defaults.plsenterallocatedrngfrmvalintab", obj);
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
	}
	if(form.isPartnerAirline() && (form.getAwbPrefix()==null || form.getAwbPrefix().trim().length()==0)){
		error=new ErrorVO("stockcontrol.defaults.allocatestock.partnerairline.ismandatory");
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
	}

	return errors;
	}



}
