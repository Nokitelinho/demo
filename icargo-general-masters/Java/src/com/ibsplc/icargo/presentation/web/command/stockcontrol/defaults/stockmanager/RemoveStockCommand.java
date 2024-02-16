/*
 * RemoveStockCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockmanager;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockManagerForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1619
 *
 */
public class RemoveStockCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("STOCK MANAGER");

	private static final String REMOVE_SUCCESS = "remove_stock_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID =
		"stockcontrol.defaults.stockmanager";

	private static final String REMOVE_FAILURE = "remove_failure";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		log.entering("RemoveStockCommand", "execute");
		/*
		 * The form for the Stock Manager screen
		 */
		StockManagerForm form = (StockManagerForm)invocationContext.screenModel;

		/*
		 * Obtain the Stock Manager session
		 */
		StockManagerSession session = getScreenSession(MODULE_NAME, SCREEN_ID);

		StockAllocationVO stockAllocationVO = session.getStockAllocationVO();
		Collection<RangeVO> rangeVOsSelected = stockAllocationVO.getRanges();
		log.log(Log.INFO, "Collection retreived from the session---------->",
				rangeVOsSelected);
		ArrayList<RangeVO> arrayRangeVO = new ArrayList<RangeVO>(rangeVOsSelected);
		Collection<RangeVO> rangeVOsToRemoval = new ArrayList<RangeVO>();
		Collection<RangeVO> newRangeVOsToRemoval = new ArrayList<RangeVO>();
		String getString = form.getCheckValueForLog();
		String[] checkVal = getString.split(",");
		log.log(Log.INFO, "checkVal.length ---------->", checkVal.length);
		for(int i=0;i<checkVal.length;i++) {
			RangeVO rangeVO = new RangeVO();
			rangeVO = arrayRangeVO.get(Integer.parseInt(checkVal[i]));
			rangeVOsToRemoval.add(rangeVO);
			RangeVO newRangeVO = new RangeVO();
			try{
				BeanHelper.copyProperties(newRangeVO,rangeVO);
				newRangeVO.setStartRange(null);
				newRangeVO.setEndRange(null);
			}catch(SystemException ex){
				log.log(Log.INFO,"ERROR IN COPY");
			}
			newRangeVOsToRemoval.add(newRangeVO);
		}
		
		//ArrayList<RangeVO> newListRangeVO = new ArrayList<RangeVO>(newRangeVOsToRemoval);
		
		//setting the first VO in the form
		ArrayList<RangeVO> listRange = new ArrayList<RangeVO>(rangeVOsToRemoval);
		RangeVO rangeVO = listRange.get(0);
		log.log(Log.INFO, "First RangeVO set in the form ---------->", rangeVO);
		form.setCurRangeFrom(rangeVO.getStartRange());
		form.setCurRangeTo(rangeVO.getEndRange());
		form.setRemDocumentType(rangeVO.getDocumentType());
		Collection<DocumentVO> docVOCOL = session.getDocumentVO();
		/*for(DocumentVO docvo:docVOCOL) {
    		if(docvo.getDocumentSubTypeDes().trim().equals(oneRange.getDocumentSubType().trim())) {
    			System.out.println("INSIDE DOCSUBTYPE");
    			stockAllocationVO.setDocumentSubType(docvo.getDocumentSubType());        			
    		}        		
    	}*/
		form.setRemDocumentSubType(form.getDocumentSubTypeHidden());
		
		/*
		rangeVO.setStartRange(form.getToRangeFrom());
		log.log(Log.INFO,"form.getToRangeFrom() ---------->"+form.getToRangeFrom());
		rangeVO.setEndRange(form.getToRangeTo());
		log.log(Log.INFO,"form.getToRangeTo() ---------->"+form.getToRangeTo());
		rangeVO.setDocumentType(form.getRemDocumentType());
		rangeVO.setDocumentSubType(form.getRemDocumentSubType());
		rangeVO.setOperationFlag("U");
		newListRangeVO.set(0,rangeVO);
		Collection<RangeVO> newcollRangeVO = new ArrayList<RangeVO>(newListRangeVO);
		log.log(Log.FINE, "updated Collection from the ArrayList ----> "+newcollRangeVO);
		*/

		form.setDisplayPage("1");
		form.setLastPageNum(String.valueOf(listRange.size()));
		form.setTotalRecords(String.valueOf(listRange.size()));
		form.setCurrentPageNum("1");

		if(rangeVOsToRemoval != null) {
			session.setCollForRemoval(rangeVOsToRemoval);
			log.log(Log.INFO, "Collection set in the session---------->",
					rangeVOsToRemoval);
		}
		if(newRangeVOsToRemoval != null) {
			session.setNewCollForRemoval(newRangeVOsToRemoval);
			log.log(Log.INFO,
					"Copied Collection set in the session---------->",
					newRangeVOsToRemoval);
		}

		invocationContext.target = REMOVE_SUCCESS;
		form.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_VIEW);
		log.exiting("RemoveStockCommand", "execute");
    }

}
