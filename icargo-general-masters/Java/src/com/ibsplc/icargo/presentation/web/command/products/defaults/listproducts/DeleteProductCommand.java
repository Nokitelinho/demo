package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
/**
 * DeleteProductCommand class is for deleting a product
 * @author a-1870
 *
 */
public class DeleteProductCommand extends BaseCommand{
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		Collection<ErrorVO> errors = null;
		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		Page<ProductVO> pageProductVO =session.getPageProductVO();
		//changes for bug icrd-5160 starts
		Page<ProductVO> pageProductVOToMove = new Page<ProductVO>(new ArrayList<ProductVO>(), 0, 0, 0, 0, 0, false);
		//changes for bug icrd-5160 ends
		String[] chk=listProductForm.getCheckBox();
		 try{
		for(int i=0;i<chk.length;i++){
			for(ProductVO productVO:pageProductVO){
				if(productVO!=null){
					if(productVO.getProductCode().equals(chk[i])){						
						ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
						productDefaultsDelegate.deleteProduct(productVO);	
						//changes for bug icrd-5160 starts
						pageProductVOToMove.add(productVO);
					 }            
				}
			}
		}
		if(pageProductVO != null && pageProductVO.size() >0  && pageProductVOToMove != null && pageProductVOToMove.size() >0){
			pageProductVO.removeAll(pageProductVOToMove);
			//changes for bug icrd-5160 ends	
		}
		}catch(BusinessDelegateException businessDelegateException){
			 errors=handleDelegateException(businessDelegateException);
		 }
		 if (errors != null && errors.size() > 0) {
				
				invocationContext.addAllError(errors);
				invocationContext.target = "delete_failure";
			}else{
		invocationContext.target = "deleteproduct_success";
	}
        
	}
	
}
