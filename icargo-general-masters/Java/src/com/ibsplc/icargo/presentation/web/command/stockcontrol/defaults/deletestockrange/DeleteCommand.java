/*
 * DeleteCommand.java Created on July 12, 2017
 *
 * Copyright  IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.deletestockrange;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.DeleteStockRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DeleteStockRangeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DeleteCommand extends BaseCommand{

	private static final String SUCCESS = "delete_success";
	private static final String FAILURE = "delete_failure";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		DeleteStockRangeForm form = (DeleteStockRangeForm) invocationContext.screenModel;
		DeleteStockRangeSession sessionDeleteStockRange = (DeleteStockRangeSession) getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.deletestockrange"); 
		Collection<ErrorVO> errorVOs=null;
		Collection<RangeVO> rangeVOs=sessionDeleteStockRange.getCollectionRangeVO();
		Collection<RangeVO> selectedRangeVOs=new ArrayList<RangeVO>();
		if(rangeVOs!=null && rangeVOs.size()>0){
			String selectedRowIds=form.getSelectedRowIds();
			String[] index=selectedRowIds.split(" ");
			for(int i=0;i<index.length;i++){
				selectedRangeVOs.add(((ArrayList<RangeVO>)rangeVOs).get(Integer.parseInt(index[i])));
			}
		}
		StockControlDefaultsDelegate delegate=new StockControlDefaultsDelegate();
		try {
			delegate.deleteStock(selectedRangeVOs);
		} catch (BusinessDelegateException e) {
			errorVOs=handleDelegateException(e);
			
		}
		if(errorVOs!=null && errorVOs.size()>0){
			invocationContext.addAllError(errorVOs);
			invocationContext.target=FAILURE;
			return;
		}
		form.setMode("Y");
		invocationContext.target=SUCCESS;
		return;
	}
}
