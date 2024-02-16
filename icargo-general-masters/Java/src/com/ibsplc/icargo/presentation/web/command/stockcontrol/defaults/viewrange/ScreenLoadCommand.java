/*
 * ScreenLoadCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.viewrange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ViewRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewRangeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;




/**
 * The command to view ranges
 * @author A-1952
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String TX_COD_MONITOR_STOCK = "MONITOR_STOCK"; 
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";
/**
 * The execute method in BaseCommand
 * @author A-1952
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ViewRangeForm viewRangeForm = (ViewRangeForm) invocationContext.screenModel;
		MonitorStockSession session =
	  		getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
		ViewRangeSession sessionView = (ViewRangeSession)getScreenSession("stockcontrol.defaults",
 											"stockcontrol.defaults.viewrange");
		StockFilterVO stockFilterVO = new StockFilterVO();
		//Collection<MonitorStockVO> collmonitorVO = session.getCollectionMonitorStockVO();
		//log.log(Log.FINE,"\n\n.****View************collmonitorVO********************> ---------" +collmonitorVO);
		String chk=viewRangeForm.getStockHolder();
		MonitorStockVO selectedVO = new MonitorStockVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		MonitorStockVO stockVO = session.getMonitorStockHolderVO();
		Page<MonitorStockVO> monitorStockVO = session.getMonitorStockDetails();
		if(chk != null && chk.trim().length()>0){
			if(stockVO != null && chk.equalsIgnoreCase(stockVO.getStockHolderCode())){
				selectedVO = stockVO;
			}else if(monitorStockVO != null && monitorStockVO.size()>0){
				for(MonitorStockVO stkVO : monitorStockVO){
					if(chk.equalsIgnoreCase(stkVO.getStockHolderCode())){
						selectedVO = stkVO;
						break;
					}
				}
			}
		}
		log
				.log(
						Log.FINE,
						"\n\n.****************.View.......selectedVO*********************> ",
						selectedVO);
		boolean isManual = false;
		viewRangeForm.setStockHolder(selectedVO.getStockHolderCode());
		viewRangeForm.setSubType(selectedVO.getDocumentSubType());
		if(session.getDocumentType()!=null){
		viewRangeForm.setDocType(session.getDocumentType());
		}else{
			viewRangeForm.setDocType(selectedVO.getDocumentType());
			session.setDocumentType(selectedVO.getDocumentType());
		}
		log
				.log(
						Log.FINE,
						"\n\n............View........session.getManual()...............> ",
						session.getManual());
		if("Y".equals(session.getManual())){
			log.log(Log.FINE,"\n\n........View.............Manal Flag true...............> ");
			isManual = true;
		}
		viewRangeForm.setManual(isManual);
		viewRangeForm.setReference(selectedVO.getReference());
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
		String companyCode=logonAttributes.getCompanyCode();
		stockFilterVO.setStockHolderCode(selectedVO.getStockHolderCode());
		stockFilterVO.setCompanyCode(companyCode);
		stockFilterVO.setDocumentSubType(selectedVO.getDocumentSubType());
		if(session.getDocumentType()!=null){
		stockFilterVO.setDocumentType(session.getDocumentType());
		}else{
			stockFilterVO.setDocumentType(selectedVO.getDocumentType());
		}
		stockFilterVO.setViewRange(true);
		stockFilterVO.setManual(viewRangeForm.isManual());
		stockFilterVO.setAirlineIdentifier(getAirlineIdentifier(sessionView.getPartnerAirline()));
		//Privilege check done for ICRD-105821
		//CRQ_ ICRD-105821_Bhaskar_17Apr2015
		TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TX_COD_MONITOR_STOCK);
		if(privilegeNewVO!=null && PRVLG_RUL_STK_HLDR.equals(privilegeNewVO.getPrivilegeCode())&&
				PRVLG_LVL_STKHLD.equals(privilegeNewVO.getLevelType())&&
				privilegeNewVO.getTypeValue()!=null && 
				privilegeNewVO.getTypeValue().trim().length()>0){
			String[] typeValues = privilegeNewVO.getTypeValue().split(",");
			List<String> privilegedStkHldrList = Arrays.asList(typeValues);
			//Approver code
			 String approver = "";
                     try{
                    	 approver =new StockControlDefaultsDelegate().
				findApproverCode(
						companyCode,
				  selectedVO.getStockHolderCode(),session.getDocumentType(),
				  selectedVO.getDocumentSubType());
                   }catch (BusinessDelegateException businessDelegateException) {				
                	   errors.addAll(handleDelegateException(businessDelegateException));
                   	}
              //Approver code end
			if(!privilegedStkHldrList.contains(selectedVO.getStockHolderCode())&&
					!privilegedStkHldrList.contains(approver)){
				ErrorVO error =  new ErrorVO("stockcontrol.defaults.monitornotauthorized");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_success";
				sessionView.setStockRangeVO(null);
				return;
			}
		}
		
		
		StockRangeVO stkRangeVO = viewDetails(stockFilterVO);
		sessionView.setStockRangeVO(stkRangeVO);
		invocationContext.target = "screenload_success";
	}
	private int getAirlineIdentifier(AirlineLovVO partnerAirline) {
		int ownAirlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
		if(partnerAirline!=null){
			int airlineIdentifier=partnerAirline.getAirlineIdentifier(); 				
			
			return (airlineIdentifier>0)?airlineIdentifier:ownAirlineIdentifier;
		} else {
			return ownAirlineIdentifier;
		}
	}
	/**
     * Method to set the view details.
     * @param stockFilterVO
     */
	public StockRangeVO viewDetails(StockFilterVO stockFilterVO){
		StockRangeVO stockRangeVO = null;

			try{

				stockRangeVO  = new StockControlDefaultsDelegate().viewRange(stockFilterVO);
			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}
			return stockRangeVO;

		}
	
	/**
	 * getPrivilegeVO()
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(
			String transactionCode) {
		log.entering("ListMonitorStockCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList=null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) 
			TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {			
			log.log(Log.SEVERE,e.getMessage());
		}
		log.exiting("ListMonitorStockCommand", "getPrivilegeVO");
		if(privilegeList!=null && !privilegeList.isEmpty()){
			return privilegeList.get(0);
		}
		return null;
	}

}
