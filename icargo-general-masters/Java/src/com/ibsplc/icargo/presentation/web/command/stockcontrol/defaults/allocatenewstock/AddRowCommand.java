/*
 * AddRowCommand.java Created on Sep 20, 2005
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

public class AddRowCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("AddRowCommand","execute");
		AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm) invocationContext.screenModel;
		AllocateNewStockSession session = (AllocateNewStockSession)
							getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatenewstock");


		String[] stockRangeFrom=allocateNewStockForm.getStockRangeFrom();
		String[] stockRangeTo=allocateNewStockForm.getStockRangeTo();
		String[] noOfDocs=allocateNewStockForm.getNoOfDocs();
		int count=0;

		RangeVO rangeStock =new RangeVO();

		rangeStock.setStartRange("");
		rangeStock.setEndRange("");
		rangeStock.setNumberOfDocuments(0);

		if(session.getAllocatedRangeVos()==null){

					log.log(Log.FINE,"...........There are no entries.......");
					Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
					rangeVOs.add(rangeStock);
					session.setAllocatedRangeVos(rangeVOs);

		}

		else{

					log.log(Log.FINE,"...........Else part.......");
					ArrayList<RangeVO> rangeVOs = new ArrayList<RangeVO>(session.getAllocatedRangeVos());
					count=0;

					for(RangeVO tempRange:rangeVOs){
								tempRange.setStartRange(stockRangeFrom[count]);
								tempRange.setEndRange(stockRangeTo[count]);
								tempRange.setNumberOfDocuments(findLong(noOfDocs[count]));
								count++;
					}
					rangeVOs.add(rangeStock);
					session.setAllocatedRangeVos(rangeVOs);
		}
	   log.exiting("AddRowCommand","execute");
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
					int toLong=sArray[i];
					if (toLong>57) {
						base*=26;
					} else {
						base*=10;
					}
				}
				return sNumber;
		}

		private  long calculateBase(char ipChar){
				long varLong=ipChar;
				long base=0;
				try{
					base=Integer.parseInt(""+ipChar);
				}catch(NumberFormatException numberFormatException){
					base=varLong-65;
				}
				return base;
	}

}
