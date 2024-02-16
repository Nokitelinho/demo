package com.ibsplc.icargo.presentation.web.command.products.defaults.listsubproducts;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
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
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.log.Log;
/**
 * ListSubProductCommand is for listing the subproducts
 * @author A=1870
 *
 */
public class ListSubProductCommand extends BaseCommand{
	//private static final String COMPANY_CODE = "AV";
	private Log log = LogFactory.getLogger("ListSubProductCommand");
	private static final String BLANK = "";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
	//	ProductFilterVO prdFilterVO = getSearchDetails(listSubProductForm);
	//	int displayPage=Integer.parseInt(listSubProductForm.getDisplayPage());
	//	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		//Added now
		ProductFilterVO prdFilterVO = new ProductFilterVO();
		int displayPage;
		if ( session.getProductFilterVODetails()!=null && ("listsubproductmode").equals(listSubProductForm.getFromListSubproduct())) {
			 displayPage = 1 ;
			prdFilterVO = session.getProductFilterVODetails();
			log.log(Log.FINE, "session.getProductFilterVODetails()", session.getProductFilterVODetails());		
		}else{
		 prdFilterVO = getSearchDetails(listSubProductForm);
		 log.log(Log.FINE, "prdFilterVO", prdFilterVO);
		session.setProductFilterVODetails(prdFilterVO);
		 log.log(Log.FINE, "session.getProductFilterVODetails()", session.getProductFilterVODetails());
		displayPage=Integer.parseInt(listSubProductForm.getDisplayPage());
		}
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		
		
		
		
		try{
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			Page<SubProductVO>  pageSubroductVO =  productDefaultsDelegate.findSubProducts(prdFilterVO,displayPage);
			if(pageSubroductVO==null || pageSubroductVO.size()==0){
				ErrorVO error = null;
				error = new ErrorVO("products.defaults.nosubproductsfound");
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				if(errors!=null && errors.size()>0){
					session.setPageSubProductVO(null);
					invocationContext.addAllError(errors);
					invocationContext.target = "list_failure";
					return;
				}
			}
			Collection<OneTimeVO> oneTimeVOs =session.getStatus();
			Collection<OneTimeVO>  oneTimeVO=session.getPriority();
			if(pageSubroductVO!=null){
				for(SubProductVO subprdVO : pageSubroductVO){
					if(oneTimeVOs!=null){
						subprdVO.setStatus(findOneTimeDescription(oneTimeVOs,subprdVO.getStatus()));
					}if(oneTimeVO!=null){
						subprdVO.setProductPriority(findOneTimeDescription(oneTimeVO,subprdVO.getProductPriority()));
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
	private ProductFilterVO getSearchDetails(ListSubProductForm listSubProductForm)
	{
		
		//Added now
    	LocalDate startDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	
    	
		ProductFilterVO productFilterVO = new ProductFilterVO();
		log.log(Log.FINE, "listSubProductForm.getProductCode()",
				listSubProductForm.getProductCode());
		productFilterVO.setProductCode(listSubProductForm.getProductCode());
		log.log(Log.FINE, "listSubProductForm.getStatus()", listSubProductForm.getStatus());
		productFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		productFilterVO.setStatus(listSubProductForm.getStatus());
		log.log(Log.FINE, "listSubProductForm.getTransportMode()",
				listSubProductForm.getTransportMode());
		productFilterVO.setTransportMode(listSubProductForm.getTransportMode());
		log.log(Log.FINE, "listSubProductForm.getPriority()",
				listSubProductForm.getPriority());
		productFilterVO.setPriority(listSubProductForm.getPriority());
		log.log(Log.FINE, "listSubProductForm.getProductScc()",
				listSubProductForm.getProductScc());
		productFilterVO.setScc(upper(listSubProductForm.getProductScc()));
		
		//added now
		productFilterVO.setProductName(upper(listSubProductForm.getProductName()));
		if(listSubProductForm.getStartDate()!= null && !BLANK.equals(listSubProductForm.getStartDate())){
			productFilterVO.setFromDate(startDate.setDate(listSubProductForm.getStartDate()));}
		if(listSubProductForm.getEndDate()!= null && !BLANK.equals(listSubProductForm.getEndDate())){
			productFilterVO.setToDate(endDate.setDate(listSubProductForm.getEndDate()));}

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
