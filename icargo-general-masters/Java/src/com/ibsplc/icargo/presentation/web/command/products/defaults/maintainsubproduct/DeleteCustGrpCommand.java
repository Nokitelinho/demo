package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;

/**
 * @author A-1870 
 * 
 */
public class DeleteCustGrpCommand extends BaseCommand {

	  /**
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {	
		MaintainSubProductForm maintainProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainSubProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		String[]custGroupChecked =  maintainProductForm.getCustGroupCheck();
		Collection<RestrictionCustomerGroupVO> allCustGroup = session.getCustGroupVOs();
		Collection<RestrictionCustomerGroupVO> newCustGruopSet = new ArrayList<RestrictionCustomerGroupVO>(
															session.getCustGroupVOs());
		Iterator iterator = allCustGroup.iterator();
			while(iterator.hasNext()){
				RestrictionCustomerGroupVO allVO =(RestrictionCustomerGroupVO)iterator.next();
				for(int i=0;i<custGroupChecked.length;i++){
				if(custGroupChecked[i].equals(allVO.getCustomerGroup())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the customer grp added just now
						newCustGruopSet.remove(allVO);
					}else{ //Updating the customer grp of the serivce already present in DB
						newCustGruopSet.remove(allVO);
						allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE); 
						newCustGruopSet.add(allVO);
					}
				}
			}
				
		}
		if(newCustGruopSet.size()==0){
			session.setCustGroupVOs(null);
		}else{
			session.setCustGroupVOs(newCustGruopSet);
		}
		invocationContext.target = "screenload_success";
		
	}

}
