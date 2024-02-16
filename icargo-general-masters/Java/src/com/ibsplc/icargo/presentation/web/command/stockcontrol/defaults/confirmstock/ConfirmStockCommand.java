/*
 * AllocateStockCommand.java Created on Mar 05, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.confirmstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4443
 *
 */
public class ConfirmStockCommand extends BaseCommand {
	private static final String NEW = "New";
	private Log log = LogFactory.getLogger("STOCK CONTROL");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	ConfirmStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.confirmstock");
    	ConfirmStockForm confirmStockForm=(ConfirmStockForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();  
		ErrorVO errorVO=null;                                                                       
		String checkedRows=null;
		String[] row=null;
		if(confirmStockForm.getCheckBox()!=null){
			checkedRows=confirmStockForm.getCheckBox()[0];
			if(checkedRows.contains(",")){
				row=checkedRows.split(",");
			}else{
				row=new String[1];
				row[0]=checkedRows;                                        
			}
		}   
        //String row[] = confirmStockForm.getCheckBox();
        
        int size = row.length;
        int count = 0;   
       TransitStockVO transitStockVO = null;
        Collection<TransitStockVO> transitStockVOs = null;
        Collection<TransitStockVO> confirmedVOs = new ArrayList<TransitStockVO>();
        Collection transitStockDetails = new ArrayList();
        transitStockVOs = session.getTransitStockVOs();
        for(int i = 0; i < size; i++){
            if(transitStockVOs != null)
            {
                int idx = Integer.parseInt(row[i]);
                transitStockVO = (TransitStockVO)((ArrayList)transitStockVOs).get(idx);
                confirmedVOs.add(transitStockVO);
            }

        log.log(5, (new StringBuilder()).append("<<<<<<<<<<------Selected filterVO------>>>>>>>>>").append(transitStockVO).toString());
        
        transitStockDetails.add(transitStockVO);
        }
        session.setSelectedTransitStockVOs(transitStockDetails);
        transitStockVO.setAirlineIdentifier(getAirlineIdentifier(confirmStockForm.getAwbPrefix()));
        StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
        try {
        	transitStockVOs =  stockControlDefaultsDelegate.confirmStockRange(confirmedVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE,"BusinessDelegateException");
		}
		
			errorVO=new ErrorVO("stockcontrol.defaults.confirmStock.confirmationsucessful");
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(errorVO);
		    invocationContext.addAllError(errors);
		
		session.setTransitStockVOs(null);
		session.setSelectedTransitStockVOs(null);
		invocationContext.target = "confirm_success";
    }
    private int getAirlineIdentifier(String awbPrefix) {
		String[] tokens=awbPrefix.split("-");
		if(tokens.length>2){
			return Integer.parseInt(tokens[2]);
		}
		return getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
	}

}
