/*
 * ModifyULDStockSetUpCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;


/**
 * @author A-2105
 *
 */
public class ModifyULDStockSetUpCommand  extends BaseCommand {

      private static final String MODULE = "uld.defaults";
		private static final String SCREENID ="uld.defaults.maintainuldstock";
		private static final String CREATE_SUCCESS = "create_success";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDStockForm maintainuldstockform = (MaintainULDStockForm) invocationContext.screenModel;
		String rows =  maintainuldstockform.getSelectedRows();
		Collection<ErrorVO> errors = null;
		errors = new ArrayList<ErrorVO>();
		errors = (Collection<ErrorVO>)validateRows(maintainuldstockform, rows);
		if(errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			maintainuldstockform.setValidateStatus("failed");
		}
		else {
		maintainuldstockform.setValidateStatus("success");
		}
		invocationContext.target = CREATE_SUCCESS;
    }
/**
 * 
 * @param maintainuldstockform
 * @param rows
 * @return
 */
    public Collection<ErrorVO> validateRows(MaintainULDStockForm maintainuldstockform, String rows) {
    	
    	ErrorVO error = null;
    	Collection<ErrorVO> errors = null;
    	errors = new ArrayList<ErrorVO>();
    	if("".equals(rows)) {	    	
			Object[] obj = { "ULDStockStockConfigVO" };
			error = new ErrorVO("uld.defaults.norowselected", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);			
    	}
    	else {
    		String[] contents = rows.split(",");    		
    		if(contents.length >1) {
    			Object[] obj = { "ULDStockStockConfigVO" };
    			error = new ErrorVO("uld.defaults.selectonerow", obj);
    			error.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(error);
    		}
    	}
		return errors;
    }

}
