/**
 * ScreenLoadCommand.java Created on Aug 31, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.editrange;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.EditRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.EditRangeForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author A-1927
 *
 * This is the form class that represents the Screen Load Command
 * for Edit Range
 */


public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadCommand","execute");
		 EditRangeForm editRangeForm = (EditRangeForm) invocationContext.screenModel;
 		EditRangeSession session = (EditRangeSession)
											getScreenSession("stockcontrol.defaults","stockcontrol.defaults.editrange");
		AllocateStockSession allocateSession= (AllocateStockSession)getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
		boolean isValid=true;
		boolean isTrue=true;
		editRangeForm.setIsValidRange("");
		Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
		RangeVO rangeStock =new RangeVO();
		
		Collection<RangeVO> avaliableRangeVOs = session.getAvailableRangeVos();		
		

		rangeStock.setOperationFlag(OPERATION_FLAG_INSERT);
		rangeStock.setStartRange("");
		rangeStock.setEndRange("");
		rangeStock.setNumberOfDocuments(0);

		
		session.removeAllAttributes();

		Page<StockRequestVO> pageStockRequestVO=allocateSession.getPageStockRequestVO();
		Collection<StockRequestVO> stockRequestVo=new ArrayList<StockRequestVO>();
		allocateSession.setCheck(editRangeForm.getReferenceNo());
		for(StockRequestVO stockRequestVO:pageStockRequestVO){
					if(stockRequestVO!=null){
						if(stockRequestVO.getRequestRefNumber().equals(editRangeForm.getReferenceNo())){

								if("New".equals(stockRequestVO.getStatus())) {
									isValid=false;
								}
						}
					}
				}

		if(isValid=isTrue){

				for(StockRequestVO stockRequestVO:pageStockRequestVO){
					if(stockRequestVO!=null){
						if(stockRequestVO.getRequestRefNumber().equals(editRangeForm.getReferenceNo())){
								editRangeForm.setDocType(stockRequestVO.getDocumentType());
								editRangeForm.setSubType(stockRequestVO.getDocumentSubType());
								editRangeForm.setApprovedStock(String.valueOf(stockRequestVO.getApprovedStock()));
								editRangeForm.setManual(stockRequestVO.isManual());
								editRangeForm.setStockHolderCode(stockRequestVO.getStockHolderCode());

						}
					}
				}

				
				 Collection<RangeVO> rangeAllocVOs=new ArrayList<RangeVO>();
				 Collection<RangeVO> rangeAvailVOs=allocateSession.getRangeVO();
				 //ADDED FOR ICRD-219881
				if(rangeAvailVOs!=null){
				 List<RangeVO> rangeList=new ArrayList<>(rangeAvailVOs);
				 if(editRangeForm.getAvailableRangeNo()!=null){
				 for (String i : editRangeForm.getAvailableRangeNo()){
					 rangeAllocVOs.add(rangeList.get(Integer.parseInt(i)));
				 		}
				 	}
				}
				else if (avaliableRangeVOs != null && !avaliableRangeVOs.isEmpty()) {
				List<RangeVO> rangeList = new ArrayList<>(avaliableRangeVOs);
				if (editRangeForm.getAvailableRangeNo() != null) {
					for (String i : editRangeForm.getAvailableRangeNo()) {
						rangeAllocVOs.add(rangeList.get(Integer.parseInt(i)));
					}
				}
			}
				
				
				//rangeAllocVOs.add(rangeStock);
				session.setAllocatedRangeVos(rangeAllocVOs);
				session.setAvailableRangeVos(rangeAvailVOs);
				session.setStockControlFor(allocateSession.getData());
				//session.setStockControlFor(editRangeForm.getStockControlFor());
				log.exiting("AddRowCommand","execute");
				invocationContext.target = "screenload_success";
		}
		else{

				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				Object[] obj = { "StatusNew" };
				error = new ErrorVO("stockcontrol.defaults.rngwithnewstatuscannotbeedited", obj);
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;

			}

		}

}
