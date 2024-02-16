package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
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
public class DeleteMilestoneCommand extends BaseCommand {
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
		String[] milestone =  maintainSubProductForm.getMileStoneRowId();
		ArrayList<ProductEventVO> allMilestone = (ArrayList<ProductEventVO>)session.getProductEventVOs();
		ArrayList<ProductEventVO> newMilestoneSet = new ArrayList<ProductEventVO>
													(session.getProductEventVOs());
		for(int i=0;i<milestone.length;i++){
				int count = Integer.parseInt(milestone[i]);
				ProductEventVO vo = (ProductEventVO)allMilestone.get(count);
					if(ProductVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){ // Removing the milestone added just now
						newMilestoneSet.remove(vo);
					}else{ //Updating the milestone of the serivce already present in DB
						newMilestoneSet.remove(vo);
						vo.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
						newMilestoneSet.add(vo);
					}
				}
									
		if(newMilestoneSet.size()==0){
			session.setProductEventVOs(null);
		}else{
			session.setProductEventVOs(newMilestoneSet);
		}	
		invocationContext.target = "screenload_success";
		
	}

}
