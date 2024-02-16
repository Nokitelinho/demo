package com.ibsplc.icargo.presentation.web.command.products.defaults.listfeedback;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListFeedbackForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * ListCommand is for list action of ListFeedback
 * @author a-1870
 *
 */
public class ListCommand extends BaseCommand{
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListProductSessionInterface session =
		getScreenSession( "product.defaults","products.defaults.listproducts");
		ListFeedbackForm listFeedbackForm =
			(ListFeedbackForm)invocationContext.screenModel;
		Collection<ErrorVO> errorVos=new ArrayList<ErrorVO>();
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		errors = validateForm(listFeedbackForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "list_failure";
			return;
		}
		ProductFeedbackFilterVO productFeedbackFilterVO=
			getSearchDetails(listFeedbackForm);
		
		//added by A-5201 for CR ICRD-22065 starts		
		if(!"YES".equals(listFeedbackForm.getCountTotalFlag())&& session.getTotalRecords().intValue() != 0){
			productFeedbackFilterVO.setTotalRecords(session.getTotalRecords().intValue());
		}else{
			productFeedbackFilterVO.setTotalRecords(-1);
		}						
		//added by A-5201 for CR ICRD-22065 end
		
		log.log(Log.FINE,
				"\n\n\n -----productFeedbackFilterVO from client----->",
				productFeedbackFilterVO);
		int displayPage=Integer.parseInt(listFeedbackForm.getDisplayPage());
		try{
			ProductDefaultsDelegate productDefaultsDelegate =
				new ProductDefaultsDelegate();
			Page<ProductFeedbackVO> pageProductFeedbackVO =
				productDefaultsDelegate.
				listProductFeedback(productFeedbackFilterVO,displayPage);
			log.log(Log.FINE,
					"\n\n\n -----ProductFeedbackVO from server----->",
					pageProductFeedbackVO);
			if(pageProductFeedbackVO==null || pageProductFeedbackVO.size()==0){
				ErrorVO error = null;
				error = new ErrorVO("products.defaults.noresultsfound");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				if(errors!=null && errors.size()>0){
					listFeedbackForm.setPageProductFeedback(null);
					invocationContext.addAllError(errors);
					invocationContext.target = "list_failure";
					return;
				}
			}
			if(pageProductFeedbackVO!=null){
			   for(ProductFeedbackVO vo:pageProductFeedbackVO){
				String date=vo.getFeedbackDate().toDisplayDateOnlyFormat();
				vo.setDate(date);
			   }
			}
			listFeedbackForm.setPageProductFeedback(pageProductFeedbackVO);
			session.setTotalRecords(pageProductFeedbackVO.getTotalRecordCount()); //added by A-5201 for CR ICRD-22065
			session.setPageProductFeedbackVO(pageProductFeedbackVO);
		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			errorVos = handleDelegateException(businessDelegateException);
		}
		invocationContext.target="list_success";
	}
	/**
	 *
	 * @param listFeedbackForm
	 * @return ProductFeedbackFilterVO
	 */
	private ProductFeedbackFilterVO
	getSearchDetails(ListFeedbackForm listFeedbackForm)
	{
		ProductFeedbackFilterVO productFeedbackFilterVO =
			new ProductFeedbackFilterVO();

		if(listFeedbackForm.getToDate()!= null &&
				listFeedbackForm.getToDate().trim().length()!=0){
			LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			productFeedbackFilterVO.setEndDate(
					to.setDate(listFeedbackForm.getToDate()));
		}
		if(listFeedbackForm.getFromDate()!= null &&
				listFeedbackForm.getFromDate().trim().length()!=0 ){
			LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			productFeedbackFilterVO.setStartDate(
				from.setDate(listFeedbackForm.getFromDate()));
		}
		log.log(Log.FINE,
				"\n\n\n -----listFeedbackForm.getProductName()----->",
				listFeedbackForm.getProductName());
		productFeedbackFilterVO.setProductCode(
			listFeedbackForm.getProductName());
			productFeedbackFilterVO.setCompanyCode(
				getApplicationSession().getLogonVO().getCompanyCode());
			
		return productFeedbackFilterVO;
	}
	/**
	 *
	 * @param form
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ListFeedbackForm form){
		log.log(Log.FINE,"\n\n\n -----inside-validateForm---->" );
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(!"".equals(form.getFromDate()) &&! "".equals(form.getToDate())){

		if(!form.getFromDate().equals(form.getToDate())){
			log.log(Log.FINE,"\n\n\n -----inside-error---->" );

			ErrorVO error = null;
			if (!DateUtilities.isLessThan(form.getFromDate(), form.getToDate(),
			"dd-MMM-yyyy")) {
				Object[] obj = { "Date" };
				error = new ErrorVO(
						"products.defaults.startdategreaterthancurrentdate",
						obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		}
		return errors;
	}
}
