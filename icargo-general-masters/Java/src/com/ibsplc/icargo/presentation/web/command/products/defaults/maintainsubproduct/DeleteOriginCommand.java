package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;

/**
 * 
 * @author A-1754
 *
 */
public class DeleteOriginCommand extends BaseCommand {
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {	
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainSubProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		String[]originChecked =  maintainSubProductForm.getOriginCheck();
		Collection<RestrictionStationVO> allStation = session.getStationVOs();
		Collection<RestrictionStationVO> newStationSet = new ArrayList<RestrictionStationVO>(
										session.getStationVOs());
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
								allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);  
								newStationSet.add(allVO);
							}
						}//end of if
					}//end of for
				
				}	
		}
		if(newStationSet.size()==0){
			session.setStationVOs(null);
		}else{
			session.setStationVOs(newStationSet);
		}
	
		invocationContext.target = "screenload_success";
		
	}

}
