/*
 * AllocateStockCommand.java Created on Mar 9, 2012
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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4443
 *
 */
public class SelectedRangeForBlackListCommand extends BaseCommand {
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
		String row[] = confirmStockForm.getCheckBox();
        int size = row.length; 
        int count = 0;
       TransitStockVO transitStockVO = null;                                 
       Collection<TransitStockVO> transitStockDetails = new ArrayList<TransitStockVO>();
        Collection<TransitStockVO> transitStockVOs = null;
        transitStockVOs = session.getTransitStockVOs();
        log.log(Log.FINE, "transitStockVOs...", transitStockVOs.size());
		for(int i = 0; i < size; i++){
            if(transitStockVOs != null)
            {
                int idx = Integer.parseInt(row[i]);
                transitStockVO = (TransitStockVO)((ArrayList)transitStockVOs).get(idx);
            }
      
        log.log(5, (new StringBuilder()).append("<<<<<<<<<<------Selected filterVO------>>>>>>>>>").append(transitStockVO).toString());
        transitStockDetails.add(transitStockVO);
        }
        session.setSelectedTransitStockVOs(transitStockDetails);
        log.log(Log.FINE, "transitStockVOs...", transitStockVOs.size());
		transitStockVO.setAirlineIdentifier(getAirlineIdentifier(confirmStockForm.getAwbPrefix()));
        if("B".equals(confirmStockForm.getBtn())){
        if("N".equals(transitStockVO.getConfirmStatus())){
        	session.setSelectedTransitStockVOs(null);
        	session.setTransitStockVOs(transitStockVOs);   
        	ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.notconfirmedstockscannotblacklist");
        	confirmStockForm.setStatus("N");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = "screenload_failure";
			return;	
        }
        }
        if("B".equals(confirmStockForm.getBtn())){
		invocationContext.target = "screenload_success_blacklist";
        }else if("M".equals(confirmStockForm.getBtn())){
        	confirmStockForm.setPopUpStatus("M");  
        	invocationContext.target = "screenload_success_missing";  
        }
		   }
    private int getAirlineIdentifier(String awbPrefix) {             
		String[] tokens=awbPrefix.split("-");
		if(tokens.length>2){
			return Integer.parseInt(tokens[2]);
		}
		return getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
	}

}
