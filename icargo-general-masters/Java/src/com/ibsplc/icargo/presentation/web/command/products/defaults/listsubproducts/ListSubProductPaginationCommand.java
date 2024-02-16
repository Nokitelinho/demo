package com.ibsplc.icargo.presentation.web.command.products.defaults.listsubproducts;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
/**
 * ListSubProductPaginationCommand
 * A-1870
 */
public class ListSubProductPaginationCommand extends BaseCommand{
	private static final String LIST = "LIST";
	private static final String NAVIGATION = "NAVIGATION";
	//private static final String COMPANY_CODE = "AV";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
		ProductFilterVO prdFilterVO = getSearchDetails(listSubProductForm,session);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		int displayPage=Integer.parseInt(listSubProductForm.getDisplayPage());
		try{
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			Page<SubProductVO>  pageSubroductVO =  productDefaultsDelegate.findSubProducts(prdFilterVO,displayPage);
			if(pageSubroductVO!=null){
				session.setTotalRecordsCount(pageSubroductVO.getTotalRecordCount());
			}
			Collection<OneTimeVO> oneTimeVOs =session.getStatus();
			if(pageSubroductVO!=null){
				for(SubProductVO subprdVO : pageSubroductVO){
					if(oneTimeVOs!=null){
						subprdVO.setStatus(findOneTimeDescription(oneTimeVOs,subprdVO.getStatus()));
					}
				}
			}
			session.setPageSubProductVO(pageSubroductVO);
		}catch (BusinessDelegateException businessDelegateException) {
			
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		invocationContext.target = "subproductlist_success";
	}
	/**
	 * creating the searching vo
	 * @param listSubProductForm
	 * @return ProductFilterVO
	 */
	private ProductFilterVO getSearchDetails(ListSubProductForm listSubProductForm,ListSubProductSessionInterface session)
	{
		ProductFilterVO productFilterVO = new ProductFilterVO();	

		productFilterVO.setProductCode(listSubProductForm.getProductCode());
		productFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		productFilterVO.setStatus(listSubProductForm.getStatus());

		productFilterVO.setTransportMode(listSubProductForm.getTransportMode());
		productFilterVO.setPriority(listSubProductForm.getPriority());
		productFilterVO.setScc(listSubProductForm.getProductScc());	
		if(LIST.equalsIgnoreCase(listSubProductForm.getNavigationMode())){//added by a-5505 for the bug ICRD-124986
			productFilterVO.setTotalRecordsCount(-1);
		}else if(NAVIGATION.equalsIgnoreCase(listSubProductForm.getNavigationMode())){
			productFilterVO.setTotalRecordsCount(session.getTotalRecordsCount());
		}
		productFilterVO.setPageNumber(Integer.parseInt(listSubProductForm.getDisplayPage()));

		return productFilterVO;
	}

	/**
	 * This method will the dstatus escription
	 * corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		for (OneTimeVO oneTimeVO:oneTimeVOs){
			if(status.equals(oneTimeVO.getFieldValue())){
				return oneTimeVO.getFieldDescription();
			}
		}
		return null;
	}

}
