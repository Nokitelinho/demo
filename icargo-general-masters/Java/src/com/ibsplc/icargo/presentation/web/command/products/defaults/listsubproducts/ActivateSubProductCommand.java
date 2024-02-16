package com.ibsplc.icargo.presentation.web.command.products.defaults.listsubproducts;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListSubProductSessionInterface;

import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
/**
 * ActivateSubProductCommand is for activating a subProduct
 * @author A-1870
 *
 */
public class ActivateSubProductCommand extends BaseCommand{
	private static final String ACTIVE = "Active";
	private static final String NEW = "New";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
		Page<SubProductVO> activate =session.getPageSubProductVO();
		Collection<SubProductVO> subProductVOList=new ArrayList<SubProductVO>();
		String[] chk=listSubProductForm.getCheckBox();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(listSubProductForm);
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = "subactivate_failure";
			return;
		}
		String productCode="";
		try{
		for(int i=0;i<chk.length;i++){
			for(SubProductVO vo:activate){
				if(vo!=null){
					if(vo.getSubProductCode().equals(chk[i])){
						vo.setStatus("A");
						listSubProductForm.setMode("A");
						productCode = vo.getProductCode();
						vo.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
						subProductVOList.add(vo);
					}

				}
			}
			ProductVO productVO = new ProductVO();
			productVO.setProductCode(productCode);
			productVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			productVO.setSubProducts(subProductVOList);
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			productDefaultsDelegate.updateSubProductStatus(productVO);

		}
		}catch (BusinessDelegateException businessDelegateException) {
			
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}


		   invocationContext.target ="subactivate_success";


  }


private Collection<ErrorVO> validateForm(ListSubProductForm listSubProductForm){

        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		Page<SubProductVO> pageSubProductVO =session.getPageSubProductVO();
		String[] chk=listSubProductForm.getCheckBox();
		if(chk.length==1){
			for(SubProductVO subProductVo:pageSubProductVO){
				if(chk[0].equals(subProductVo.getSubProductCode())){
					if(ACTIVE.equals(subProductVo.getStatus())){
						     error = new ErrorVO("products.defaults.cannotactivatesubproduct");
						     error.setErrorDisplayType(ErrorDisplayType.ERROR);
						     errors.add(error);
					}
					else if(NEW.equals(subProductVo.getStatus())){
							 error = new ErrorVO("products.defaults.cannotactivatesubproductofstatusnew");
							 error.setErrorDisplayType(ErrorDisplayType.ERROR);
							 errors.add(error);
                    }
					break;
				}

			 }
		}else{
				 error = new ErrorVO("products.defaults.cannotactivatesubproductmorethanone");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 errors.add(error);
	    }

			        return errors;
}



}
