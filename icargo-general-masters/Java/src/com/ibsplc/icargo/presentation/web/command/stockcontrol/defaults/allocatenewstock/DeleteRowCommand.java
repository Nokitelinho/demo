/*
 * DeleteRowCommand.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatenewstock;

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

public class DeleteRowCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("DeleteRowCommand","execute");
		AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm) invocationContext.screenModel;
		AllocateNewStockSession session = (AllocateNewStockSession)
									getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatenewstock");


		ArrayList<RangeVO> rangeVOList = (ArrayList<RangeVO>)session.getAllocatedRangeVos();
		String[] stockRangeFrom=allocateNewStockForm.getStockRangeFrom();
		String[] stockRangeTo=allocateNewStockForm.getStockRangeTo();
		String[] noOfDocs=allocateNewStockForm.getNoOfDocs();
		String[] allocatedRangeNo=allocateNewStockForm.getAllocatedRangeNo();
		int iterator=0;

		Collection<RangeVO> rangeVOs = session.getAllocatedRangeVos();
		
		if(rangeVOs!=null){

			for(RangeVO tempRange:rangeVOs){
					tempRange.setStartRange(stockRangeFrom[iterator]);
					tempRange.setEndRange(stockRangeTo[iterator]);
					tempRange.setNumberOfDocuments(findLong(noOfDocs[iterator]));
					iterator++;
			}
			session.setAllocatedRangeVos(rangeVOs);
		}
		ArrayList<RangeVO> rangeVONewList = new ArrayList<RangeVO>(session.getAllocatedRangeVos());


			if(rangeVOList!=null && allocatedRangeNo!=null){
					for(int i=0; i<allocatedRangeNo.length; i++){
					       	RangeVO vo = rangeVOList.get(Integer.parseInt(allocatedRangeNo[i]));
							rangeVONewList.remove(vo);
					}
				session.setAllocatedRangeVos(rangeVONewList);
			}
	    log.exiting("DeleteRowCommand","execute");
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
}

