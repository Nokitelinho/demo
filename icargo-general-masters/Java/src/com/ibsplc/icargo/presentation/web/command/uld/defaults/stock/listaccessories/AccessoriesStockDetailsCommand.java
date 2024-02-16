/*
 * AccessoriesStockDetailsCommand.java Created on Jan 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.ListAccessriesStockSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for getting the details of a selected row
 *
 * @author A-1940
 */
public class AccessoriesStockDetailsCommand extends BaseCommand {

	private static final String DETAILS_SUCCESS = "details_success";
	private static final String DETAILS_FAILURE = "details_error";
    private static final String SCREEN_ID = 
    				"uld.defaults.stock.listaccessoriesstock";
	private static final String MODULE_NAME = "uld.defaults";
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		ListAccessoriesStockForm listAccessoriesStockForm = 
					(ListAccessoriesStockForm)invocationContext.screenModel;
		ListAccessriesStockSessionImpl listAccessriesStockSessionImpl = 
		(ListAccessriesStockSessionImpl)getScreenSession(MODULE_NAME,SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("AccessoriesStockDetailsCommand","execute");
		String chkbox[] = listAccessoriesStockForm.getSelect();
		Page<AccessoriesStockConfigVO> accessoriesStockConfigVOs = 
			listAccessriesStockSessionImpl.getAccessoriesStockConfigVOs();
		ArrayList<AccessoriesStockConfigVO> pg =
								new ArrayList<AccessoriesStockConfigVO>();
		AccessoriesStockConfigVO vo = null;
		if(chkbox != null && chkbox.length > 0){
			for(int i=0;i<chkbox.length;i++){
				int check = Integer.parseInt(chkbox[i]);
				vo = new AccessoriesStockConfigVO();
				vo = accessoriesStockConfigVOs.get(check);
				pg.add(vo);
			}
			listAccessriesStockSessionImpl.setAccessoriesStockConfigVOColl(pg);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = DETAILS_SUCCESS;
		}else{
			
			ErrorVO error = new ErrorVO
						("uld.defaults.listaccessorystock.norowsselected");
    		error.setErrorDisplayType(ERROR);
    		errors.add(error);
		}
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = DETAILS_FAILURE;
		}
		log.exiting("CloseAccessoriesStockCommand","execute");
    }
}
