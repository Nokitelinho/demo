package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;

import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * ListProductPaginatingCommand 
 * @author a-1870
 *
 */
public class ListProductPaginationCommand extends BaseCommand {
	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		ProductFilterVO prdFilterVOs=getSearchDetails(listProductForm);
		int displayPage=Integer.parseInt(listProductForm.getDisplayPage());
		//added by A-5174 for BUG ICRD-23719 starts	here	
		if(!ProductFilterVO.FLAG_YES.equals(listProductForm.getCountTotalFlag())&& session.getTotalRecords().intValue() != 0){
			prdFilterVOs.setTotalRecords(session.getTotalRecords().intValue());
		}else{
			prdFilterVOs.setTotalRecords(-1);
		}						
		//added by A-5174 for BUG ICRD-23719 end here
		try{
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			Page<ProductVO>  productVO =  productDefaultsDelegate.findProducts(prdFilterVOs,displayPage);
			Collection<OneTimeVO> oneTimeVOs =session.getStatus();
			Collection<OneTimeVO> oneTimeVOForProductCategory = session.getProductCategories();
			if(productVO!=null){
				session.setTotalRecords(productVO.getTotalRecordCount()); //added by A-5251 for BUG ICRD-25608
				for(ProductVO prdVO : productVO){
					if(oneTimeVOs!=null){
						prdVO.setStatus(findOneTimeDescription(oneTimeVOs,prdVO.getStatus()));
					}
					if(oneTimeVOForProductCategory != null){
						prdVO.setProductCategory(findOneTimeDescriptionForProductCtg(oneTimeVOForProductCategory,prdVO.getProductCategory()));
					}
				}
			}
			session.setPageProductVO(productVO);
		}catch(BusinessDelegateException businessDelegateException){
//			To be reviewed call handleException
			businessDelegateException.getMessage();
		}
		invocationContext.target ="list_success";
	}
	
	
	/**
	 * creating the searching vo
	 * @param listProductForm
	 * @return ProductFilterVO
	 */
	private ProductFilterVO getSearchDetails(ListProductForm listProductForm)
	{
		ProductFilterVO productFilterVOs = new ProductFilterVO();
		productFilterVOs.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		productFilterVOs.setProductName(listProductForm.getProductName());
		productFilterVOs.setStatus(listProductForm.getStatus());
		productFilterVOs.setIsRateDefined(listProductForm.isRateDefined());
		productFilterVOs.setTransportMode(listProductForm.getTransportMode());
		productFilterVOs.setPriority(listProductForm.getPriority());
		productFilterVOs.setScc(listProductForm.getProductScc());
		LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		
		if(listProductForm.getFromDate()!= null && 
				listProductForm.getFromDate().trim().length()!=0){
			productFilterVOs.setFromDate(from.setDate(listProductForm.getFromDate()));
		}
		
		if(listProductForm.getToDate()!= null && 
				listProductForm.getToDate().trim().length()!=0 ){
			LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			productFilterVOs.setToDate(to.setDate(listProductForm.getToDate()));
		}
		productFilterVOs.setProductCategory(listProductForm.getProductCategory());
		
		return productFilterVOs;
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
	/**
	 * 
	 * @param productCategoryOnetimes
	 * @param productCategory
	 * @return
	 */
	private String findOneTimeDescriptionForProductCtg(
			Collection<OneTimeVO> productCategoryOnetimes, String productCategory) {
		StringBuilder productcategory  = new StringBuilder();
		if(productCategory != null && productCategory.length() > 0){
			String productcodes [] = productCategory.split(",");
			if(productcodes != null && productcodes.length > 0){
				for(String category: productcodes){
					if(productCategoryOnetimes != null && productCategoryOnetimes.size() > 0){
						for(OneTimeVO categoryVO: productCategoryOnetimes){
							if(category.equals(categoryVO.getFieldValue())){
								if(productcategory != null && productcategory.length() > 0){
									productcategory.append(",").append(categoryVO.getFieldDescription());
								}else{
									productcategory.append(categoryVO.getFieldDescription());
								}
							}
						}
					}
				}
			}
		}
		if(productcategory != null && productcategory.length() > 0){
			return productcategory.toString();
		}
		return productCategory;
	}
	
}
