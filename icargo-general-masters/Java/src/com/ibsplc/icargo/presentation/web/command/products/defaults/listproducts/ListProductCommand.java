package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;

import java.util.ArrayList;
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
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * ListProductCommand class is for listing the product
 * @author a-1870
 *
 */
public class ListProductCommand extends BaseCommand {
	
	/**
	 * Log
	 */
	private Log log = LogFactory.getLogger("Products");
	private static final String LIST_PROD_MODE= "listproductmode";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListProductSessionInterface session = getScreenSession(
				"product.defaults","products.defaults.listproducts");
		
		ListProductForm listProductForm = (
				ListProductForm)invocationContext.screenModel;
		session.setPageProductVO(null);
		ProductFilterVO prdFilterVOs=getSearchDetails(listProductForm);
		session.setFilterDetails(prdFilterVOs);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(listProductForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "list_failure";
			return;
		}
		int displayPage=Integer.parseInt(listProductForm.getDisplayPage());
		//added by A-5201 for CR ICRD-22065 starts		
		if(!"YES".equals(listProductForm.getCountTotalFlag())&& session.getTotalRecords().intValue() != 0){
			prdFilterVOs.setTotalRecords(session.getTotalRecords().intValue());
		}else{
			prdFilterVOs.setTotalRecords(-1);
		}						
		//added by A-5201 for CR ICRD-22065 end

		try{
			ProductDefaultsDelegate productDefaultsDelegate =
				new ProductDefaultsDelegate();
			//Added by A-5220 for ICRD-34755 starts
			session.setProductFilterVO(prdFilterVOs);
			//Added by A-5220 for ICRD-34755 ends
			Page<ProductVO>  productVO =  productDefaultsDelegate.
			findProducts(prdFilterVOs,displayPage);
			if(productVO==null || productVO.size()==0){

				ErrorVO error = null;
				error = new ErrorVO("products.defaults.noproductsfound");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				if(errors!=null && errors.size()>0){
					session.setPageProductVO(null);
					invocationContext.addAllError(errors);
					invocationContext.target = "list_failure";
					return;
				}
			}

			Collection<OneTimeVO> oneTimeVOs =session.getStatus(); 
			Collection<OneTimeVO> oneTimeVO =session.getPriority();
			Collection<OneTimeVO> productCategories =session.getProductCategories();
			session.setTotalRecords(productVO.getTotalRecordCount()); //added by A-5201 for CR ICRD-22065
			if(productVO!=null){
				for(ProductVO prdVO : productVO){

					if(oneTimeVOs!=null){
						prdVO.setStatus(
								findOneTimeDescription(
										oneTimeVOs,prdVO.getStatus()));
						prdVO.setProductCategory(
								findOneTimeDescriptionForProductCtg(
										productCategories,prdVO.getProductCategory()));

					}

				}
			}


			session.setPageProductVO(productVO);
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			
			errors = handleDelegateException(businessDelegateException);
		}
		listProductForm.setScreenMode(LIST_PROD_MODE);
		invocationContext.target ="list_success";
	}


	


	private String findOneTimeDescriptionForProductCtg(
			Collection<OneTimeVO> productCategoryOnetimes, String productCategory) {
		StringBuilder productcategory  = new StringBuilder();
		if(productCategory != null && productCategory.length() > 0){
			String productcodes [] = productCategory.split(",");
			if(productcodes != null &&productcodes.length > 0){
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
		return null;
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
		productFilterVOs.setScc(upper(listProductForm.getProductScc()));
		productFilterVOs.setProductCategory(listProductForm.getProductCategory());//Added for ICRD-166985 by A-5117
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

		return productFilterVOs;
	}
	/**
	 * This method will the status description
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
 * @param form
 * @return Collection<ErrorVO>
 */
	private Collection<ErrorVO> validateForm(ListProductForm form){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if(!"".equals(form.getFromDate()) &&! "".equals(form.getToDate())){
		log.log(log.FINER,"\n\n*******startdategreaterthancurrentdate********");
		if(!form.getFromDate().equals(form.getToDate())){
			log.log(log.FINER,"\n\n*****inside 1******");
			if (!DateUtilities.isLessThan(form.getFromDate(), form.getToDate(),
					"dd-MMM-yyyy")) {
				log.log(log.FINER,"\n\n*****inside 2******");
				Object[] obj = { "Date" };
				error = new ErrorVO(
					"products.defaults.startdategreaterthancurrentdate", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		}
		}
		return errors;

	}
	
	/**
     * 
     * @param input
     * @return
     */
		private String upper(String input){

			if(input!=null){
				return input.trim().toUpperCase();
			}else{
				return "";
			}
	  }
}
