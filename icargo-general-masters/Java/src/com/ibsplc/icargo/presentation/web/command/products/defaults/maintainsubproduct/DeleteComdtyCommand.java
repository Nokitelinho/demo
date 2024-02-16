package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;


import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * The Command Class to delete from commodity table
 * @author A-1754
 *
 */
public class DeleteComdtyCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DeleteComdtyCommand","execute");
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainSubProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		String[]cmdtyChecked =  maintainSubProductForm.getCommodityCheck();
		Collection<RestrictionCommodityVO> allComdty = session.getCommodityVOs();
		Collection<RestrictionCommodityVO> newCmdtySet = 
			new ArrayList<RestrictionCommodityVO>(session.getCommodityVOs());
		Iterator iterator = allComdty.iterator();
			while(iterator.hasNext()){
				RestrictionCommodityVO allVO =(RestrictionCommodityVO)iterator.next();
				for(int i=0;i<cmdtyChecked.length;i++){
				if(cmdtyChecked[i].equals(allVO.getCommodity())){
					if(ProductVO.OPERATION_FLAG_INSERT.equals(allVO.getOperationFlag())){ // Removing the cmdty added just now
						newCmdtySet.remove(allVO);
					}else{ //Updating the cmdty of the serivce already present in DB
						newCmdtySet.remove(allVO);
						allVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE); 
						newCmdtySet.add(allVO);
					}
				}
			}
				
		}
		if(newCmdtySet.size()==0){
			//session.setProductCommodityVOs(null);
			//newly added
			session.setCommodityVOs(null);
		}else{
			//session.setProductCommodityVOs(newCmdtySet);
			//newly added
			session.setCommodityVOs(newCmdtySet);
		}	
		invocationContext.target = "screenload_success";
		log.exiting("DeleteComdtyCommand","execute");
		
	}

}
