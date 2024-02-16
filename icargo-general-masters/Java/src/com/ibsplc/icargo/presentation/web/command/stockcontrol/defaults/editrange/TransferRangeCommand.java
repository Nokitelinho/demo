/**
 * TransferRangeCommand.java Created on Sep 13, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.editrange;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.EditRangeForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.EditRangeSession;
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

		log.entering("TransferRangesCommand","execute");
		EditRangeForm editRangeForm = (EditRangeForm) invocationContext.screenModel;
		EditRangeSession session = (EditRangeSession)
									getScreenSession("stockcontrol.defaults","stockcontrol.defaults.editrange");


		 ArrayList<RangeVO> rangeVOList =  new ArrayList<RangeVO>(session.getAvailableRangeVos());
		String[] stockRangeFrom=editRangeForm.getStockRangeFrom();
		String[] stockRangeTo=editRangeForm.getStockRangeTo();
		String[] availableRangeNo=editRangeForm.getAvailableRangeNo();
		String[] hiddenOpFlag = editRangeForm.getHiddenOpFlag();
		//System.out.println("------------check length-----------"+availableRangeNo.length);
		int iterator = 0;  

		if(session.getAllocatedRangeVos()!=null){
			Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>(session.getAllocatedRangeVos());
			Collection<RangeVO> newRangeVOs = new ArrayList<RangeVO>();

			if(rangeVOs!=null){
				for(RangeVO tempRange:rangeVOs){
					log.log(Log.ALL, "Hidden op flag is ", hiddenOpFlag,
							iterator);
					if(!OPERATION_FLAG_DELETE.equalsIgnoreCase(hiddenOpFlag[iterator])){                //added by a2434 to avoid deleted rows	tempRange.setStartRange(stockRangeFrom[iterator]);
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

			for(int i=0; i<availableRangeNo.length; i++){
			        //System.out.println("value of chk"+i+"'''"+availableRangeNo[i]);
					RangeVO vo = rangeVOList.get(Integer.parseInt(availableRangeNo[i]));
					//System.out.println("------------Received VO 4 transfer----------"+vo);
					if(rangeAllocVOs!=null){
						rangeAllocVOs.add(vo);
					}
					if(rangeAvailVOs!=null && rangeAvailVOs.contains(vo)){
						rangeAvailVOs.remove(vo);
					}
			}
			//Added to have empty row
			/*RangeVO rangeStock = new RangeVO();

			rangeStock.setOperationFlag(OPERATION_FLAG_INSERT);
			rangeStock.setStartRange(BLANKSPACE);
			rangeStock.setEndRange(BLANKSPACE);
			rangeStock.setNumberOfDocuments(0);
			rangeAllocVOs.add(rangeStock);*/
			//ends
			//System.out.println("----------Checked Values--------"+rangeAllocVOs);
			session.setAvailableRangeVos(rangeAvailVOs);
			session.setAllocatedRangeVos(rangeAllocVOs);
		}
		log.exiting("ListRangesCommand","execute");
		invocationContext.target = "screenload_success";
	}
}

