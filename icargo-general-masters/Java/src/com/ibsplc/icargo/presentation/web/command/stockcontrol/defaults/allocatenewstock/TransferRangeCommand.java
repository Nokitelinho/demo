/*
 * TransferRangeCommand.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatenewstock;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateNewStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateNewStockSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1927
 *
 */

public class TransferRangeCommand extends BaseCommand {

	private static final String BLANKSPACE = "";

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("TransferCommand","execute");
		AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm) invocationContext.screenModel;
		AllocateNewStockSession session = (AllocateNewStockSession)
									getScreenSession("stockcontrol.defaults","stockcontrol.defaults.allocatenewstock");


		ArrayList<RangeVO> rangeVOList =  new ArrayList<RangeVO>(session.getAvailableRangeVos());


		String[] stockRangeFrom=allocateNewStockForm.getStockRangeFrom();
		String[] stockRangeTo=allocateNewStockForm.getStockRangeTo();
		String[] availableRangeNo=allocateNewStockForm.getAvailableRangeNo();
		String[] hiddenOpFlag = allocateNewStockForm.getHiddenOpFlag();

		int iterator = 0;

		if(session.getAllocatedRangeVos()!=null){
			Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>(session.getAllocatedRangeVos());
			Collection<RangeVO> newRangeVOs = new ArrayList<RangeVO>();

			if(rangeVOs!=null){
				for(RangeVO tempRange:rangeVOs){
					log.log(Log.ALL, "Hidden op flag is ", hiddenOpFlag,
							iterator);
					if(!OPERATION_FLAG_DELETE.equalsIgnoreCase(hiddenOpFlag[iterator])){                //added by a2434 to avoid deleted rows
						tempRange.setStartRange(stockRangeFrom[iterator]);
						tempRange.setEndRange(stockRangeTo[iterator]);
						if(stockRangeFrom[iterator] != null && stockRangeFrom[iterator].trim().length()>0
								&& stockRangeTo[iterator] != null && stockRangeTo[iterator].trim().length()>0) {
							newRangeVOs.add(tempRange);
						}
						iterator++;
					}
				}
				session.setAllocatedRangeVos(newRangeVOs);
			}
		}
		ArrayList<RangeVO> rangeAllocVOs = new ArrayList<RangeVO>();
		ArrayList<RangeVO> rangeAvailVOs = new ArrayList<RangeVO>();

		if(session.getAllocatedRangeVos()!=null){
			 rangeAllocVOs = new ArrayList<RangeVO>(session.getAllocatedRangeVos());
		}
		if(session.getAvailableRangeVos()!=null){
			 rangeAvailVOs = new ArrayList<RangeVO>(session.getAvailableRangeVos());
		}


		if(rangeVOList!=null && availableRangeNo!=null){

			Collection<RangeVO> rangesToDelete = new ArrayList<RangeVO>();
			for(int i=0; i<availableRangeNo.length; i++){
					RangeVO vo = rangeVOList.get(Integer.parseInt(availableRangeNo[i]));
					rangeAllocVOs.add(vo);
					RangeVO rangevo = rangeAvailVOs.get(Integer.parseInt(availableRangeNo[i]));
					rangesToDelete.add(rangevo);
					//rangeAvailVOs.remove(Integer.parseInt(availableRangeNo[i]));
					
			}
			//Added by A-7364 as part of ICRD-320779 starts
			if(rangesToDelete!=null && rangesToDelete.size()>0){
				for(RangeVO rangeVO : rangesToDelete){
					rangeAvailVOs.remove(rangeVO);
				}
			}
			//Added by A-7364 as part of ICRD-320779 ends
//			rangeAvailVOs.removeAll(rangesToDelete);//commented by A-7364 as part of ICRD-320779
			//Added to have empty row
			RangeVO rangeStock = new RangeVO();

			rangeStock.setOperationFlag(OPERATION_FLAG_INSERT);
			rangeStock.setStartRange(BLANKSPACE);
			rangeStock.setEndRange(BLANKSPACE);
			rangeStock.setNumberOfDocuments(0);
			rangeAllocVOs.add(rangeStock);
			//ends
			session.setAvailableRangeVos(rangeAvailVOs);
			session.setAllocatedRangeVos(rangeAllocVOs);
		}
		if(allocateNewStockForm.getAllocatedTotalnoofDocs()==null||allocateNewStockForm.getAllocatedTotalnoofDocs().isEmpty()){
			allocateNewStockForm.setAllocatedTotalnoofDocs(allocateNewStockForm.getAvailableTotalnoofDocs());
		}else{
			int allocatedTotalNumber= Integer.parseInt(allocateNewStockForm.getAllocatedTotalnoofDocs());
			int availabeTotalNumber= Integer.parseInt(allocateNewStockForm.getAvailableTotalnoofDocs());
			int sum= allocatedTotalNumber+availabeTotalNumber;
			allocateNewStockForm.setAllocatedTotalnoofDocs(Integer.toString(sum));
		}
		log.exiting("TransferRangesCommand","execute");
		invocationContext.target = "screenload_success";
	}
}

