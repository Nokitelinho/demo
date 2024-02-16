/*
 * DeleteOriginCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
/**
 * 
 * @author A-1754
 *
 */
public class DeleteOriginCommand extends BaseCommand {
	
	private static final String	SAVEAS = "saveas";
	
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {	
		MaintainProductForm maintainProductForm= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		String[]originChecked =  maintainProductForm.getOriginCheck();
		Collection<RestrictionStationVO> allStation = session.getProductStationVOs();
		Collection<RestrictionStationVO> newStationSet = new ArrayList<RestrictionStationVO>(
										session.getProductStationVOs());
		Iterator iterator = allStation.iterator();
			while(iterator.hasNext()){
				RestrictionStationVO allVO =(RestrictionStationVO)iterator.next();
				if(allVO.getIsOrigin()){
					for(int i=0;i<originChecked.length;i++){
						if(originChecked[i].equals(allVO.getStation())){
							if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the origin added just now
								newStationSet.remove(allVO);
							}else { //Updating the origin already present in DB
								newStationSet.remove(allVO);
								if(!SAVEAS.equalsIgnoreCase(maintainProductForm.getMode())){
									allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);  
									newStationSet.add(allVO);									
								}
							}
						}//end of if
					}//end of for
				
				}	
		}
		if(newStationSet.size()==0){
			session.setProductStationVOs(null);
		}else{
			session.setProductStationVOs(newStationSet);
		}
	
		invocationContext.target = "screenload_success";
		
	}

}
