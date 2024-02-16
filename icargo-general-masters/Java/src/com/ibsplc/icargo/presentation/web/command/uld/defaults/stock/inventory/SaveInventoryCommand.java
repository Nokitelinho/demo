/*
 * SaveInventoryCommand.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.inventory;
/**
 * @author a-2883
 */
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDInventoryDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.ULDInventorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.InventoryULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2883
 *
 */
public class SaveInventoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULDMANAGEMENT");
	private static final String SCREEN_ID = 
			"uld.defaults.stock.inventoryuld";
	private static final String MODULE_NAME = "uld.defaults";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("SaveInventoryCommand", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDInventorySession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		InventoryULDForm form = 
			(InventoryULDForm)invocationContext.screenModel;
			ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
			if(session.getListInventoryULDDetails() != null){
				Collection<InventoryULDVO> vos = new ArrayList<InventoryULDVO>(session.getListInventoryULDDetails());
				Collection<InventoryULDVO> updatedVOs = new ArrayList<InventoryULDVO>();
				Collection<ULDInventoryDetailsVO> add = new ArrayList<ULDInventoryDetailsVO>();
				InventoryULDVO finalVOs  =  null;
				log.log(Log.FINE, " \n VOSize", vos.size());
				log.log(Log.FINE, " \n VODetails", vos);
				try {
					for(InventoryULDVO currentVOs : vos){
						finalVOs = new InventoryULDVO();
						if(OPERATION_FLAG_UPDATE.equals(currentVOs.getOpFlag())){
							log.log(Log.FINE, " \n InsideUpdate");
							//delegate.updateInventoryDetails(currentVOs);
							updatedVOs.add(currentVOs);
						}else if(OPERATION_FLAG_INSERT.equals(currentVOs.getOpFlag())){
							for(ULDInventoryDetailsVO vo : currentVOs.getUldInventoryDetailsVOs()){
								log.log(Log.FINE, " \n InsertSize", currentVOs.getUldInventoryDetailsVOs().size());
								if(OPERATION_FLAG_INSERT.equals(vo.getOpFlag())){
									add.add(vo);
								}
							}
							finalVOs.setUldInventoryDetailsVOs(add);
							log.log(Log.FINE, " \n afterInsertVOsAdd", vos);
							if(finalVOs != null && finalVOs.getUldInventoryDetailsVOs().size() > 0){
								//delegate.updateInventoryDetails(currentVOs);
								updatedVOs.add(currentVOs);
							}
						}
					}
					if(updatedVOs !=null && updatedVOs.size() > 0){
						delegate.updateInventoryDetails(updatedVOs);
					}
					
				} catch (BusinessDelegateException ex) {
					errors = handleDelegateException(ex);		
				}
			}
		
		if(errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target = "failure";
		}else{
			session.setDisplayInventoryDetails(null);
			session.setListInventoryULDDetails(null);
			form.setFromDate("");
			form.setToDate("");
			form.setUldType("");
			form.setAirportCode("");
			ErrorVO errorVO = new ErrorVO("uld.defaults.stock.inventory.inventorydetailssaved");
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = "success";
		}
		
		
	}
	
	 /**
     * method for updating the index map
     * @author a-2883
     * @param existingMap
     * @param page
     * @return HashMap<String,String>
     */
    private HashMap<String,String> buildIndexMap(HashMap<String,String> existingMap, Page<CustomerGroupVO> page) {
        HashMap<String,String> finalMap = existingMap;
        String indexPage = String.valueOf((page.getPageNumber()+1));

        boolean isPageExits = false;
        Set<Map.Entry<String, String>> set = existingMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            String pageNum = entry.getKey();
            if (pageNum.equals(indexPage)) {
                isPageExits = true;
            }
        }

        if (!isPageExits) {
            finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
        }
        return finalMap;
    }
	
}
