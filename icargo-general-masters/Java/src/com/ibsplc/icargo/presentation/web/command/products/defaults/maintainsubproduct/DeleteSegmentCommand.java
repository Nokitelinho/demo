package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
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
public class DeleteSegmentCommand extends BaseCommand {
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
		
		String[] segment =  maintainSubProductForm.getSegmentRowId();
		ArrayList<RestrictionSegmentVO> allSegments = 
					(ArrayList<RestrictionSegmentVO>)session.getSegmentVOs();
		ArrayList<RestrictionSegmentVO> newSegmentSet = new ArrayList<RestrictionSegmentVO>
													(session.getSegmentVOs());
		for(int i=0;i<segment.length;i++){
				int count = Integer.parseInt(segment[i]);
				RestrictionSegmentVO vo = (RestrictionSegmentVO)allSegments.get(count);
					if(ProductVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){ 
						newSegmentSet.remove(vo);
					}else{ //Updating the milestone of the serivce already present in DB
						newSegmentSet.remove(vo);
						vo.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
						newSegmentSet.add(vo);
					}
				}
									
		if(newSegmentSet.size()==0){
			session.setSegmentVOs(null);
		}else{
			session.setSegmentVOs(newSegmentSet);
		}
	
		invocationContext.target = "screenload_success";
		
	}

}
