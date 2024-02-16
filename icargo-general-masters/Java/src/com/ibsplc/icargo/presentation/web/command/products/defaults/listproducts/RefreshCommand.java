
package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;


/**
 * @author A-2135
 *
 */
public class RefreshCommand  extends BaseCommand {
	

    private static final String REFRESH_SUCCESS = "refresh_success";
    
    private static final String REFRESH_FAILURE = "refresh_failure";
    
    private Log log = LogFactory.getLogger("RefreshCommand");
    
    private static final String LIST_PROD_MODE= "listproductmode";
    
    /*
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.log(Log.INFO,"\n\n\nInside RefreshCommand ");
    	ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		setFormDetails(listProductForm,session);
		
		//Added by Kirupakaran to list again on closing the MaintainProduct Screen
		
		
		if(session.getProductFilterVO() != null){
			//Added  By A-5174 for bug ICRD-23719 starts here
			ProductFilterVO productDetails=session.getProductFilterVO();
			productDetails.setTotalRecords(-1);
			session.setProductFilterVO(productDetails);
			//Added  By A-5174 for bug ICRD-23719 ends  here
			try{
				ProductDefaultsDelegate productDefaultsDelegate =
					new ProductDefaultsDelegate();
				Page<ProductVO>  productVO =  productDefaultsDelegate.
				findProducts(session.getProductFilterVO(),session.getProductFilterVO().getDisplayPage());
				if(productVO==null || productVO.size()==0){

					ErrorVO error = null;
					error = new ErrorVO("products.defaults.noproductsfound");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					if(errors!=null && errors.size()>0){
						session.setPageProductVO(null);
						invocationContext.addAllError(errors);
						invocationContext.target = REFRESH_FAILURE;
						return;
					}
				}

				Collection<OneTimeVO> oneTimeVOs =session.getStatus();
				Collection<OneTimeVO> oneTimeVOForProductCategory = session.getProductCategories();
				if(productVO!=null){
					for(ProductVO prdVO : productVO){

						if(oneTimeVOs!=null){
							prdVO.setStatus(
									findOneTimeDescription(
											oneTimeVOs,prdVO.getStatus()));
							if(oneTimeVOForProductCategory != null){
								prdVO.setProductCategory(findOneTimeDescriptionForProductCtg(oneTimeVOForProductCategory,prdVO.getProductCategory()));
							}
						}

					}
				}

			
				session.setTotalRecords(productVO.getTotalRecordCount()); //Added by A-5174 for bug ICRD-23719 
				session.setPageProductVO(productVO);
			}catch (BusinessDelegateException businessDelegateException) {
				
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}

			
		}
	
		
		//ends
		listProductForm.setScreenMode(LIST_PROD_MODE);
		invocationContext.target = REFRESH_SUCCESS;
	}
	/**
	 * Function to set form details from the session 
	 * @param listProductForm
	 * @param session
	 */	
		
		
    private void setFormDetails(ListProductForm listProductForm,ListProductSessionInterface session){
		ProductFilterVO productFilterVO = session.getProductFilterVO();
		log.log(Log.INFO, "\n\n\nsession.getProductFilterVO()", session.getProductFilterVO());
		if(productFilterVO!= null){
			log.log(Log.INFO, "\n\n\nproductFilterVO is not null",
					productFilterVO);
			listProductForm.setProductName(productFilterVO.getProductName());
			log
					.log(
							Log.INFO,
							"\n\n\n!!!!!!!!!!!!!productFilterVO.getProductName()!!!!!!!!!!!!",
							productFilterVO.getProductName());
			listProductForm.setStatus(productFilterVO.getStatus());
			listProductForm.setTransportMode(productFilterVO.getTransportMode());
			listProductForm.setPriority(productFilterVO.getPriority());
			listProductForm.setProductScc(productFilterVO.getScc());
			listProductForm.setRateDefined(productFilterVO.getIsRateDefined());
			if(productFilterVO.getFromDate()!=null){
			listProductForm.setFromDate(TimeConvertor.toStringFormat(productFilterVO.getFromDate(),TimeConvertor.CALENDAR_DATE_FORMAT));
			}
			if(productFilterVO.getToDate()!=null){
			listProductForm.setToDate(TimeConvertor.toStringFormat(productFilterVO.getToDate(),TimeConvertor.CALENDAR_DATE_FORMAT));
			}
			listProductForm.setProductCategory(productFilterVO.getProductCategory());
		}
	}
    
	/**
	 * This method will the dstatus escription
	 * corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(
			Collection<OneTimeVO> oneTimeVOs, String status){
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
