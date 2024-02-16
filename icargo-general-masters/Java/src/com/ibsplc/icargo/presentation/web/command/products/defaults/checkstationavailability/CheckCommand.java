package com.ibsplc.icargo.presentation.web.command.products.defaults.checkstationavailability;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.CheckAvailabilityForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class CheckCommand extends BaseCommand{
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
			getScreenSession(
					"product.defaults","products.defaults.listproducts");
		CheckAvailabilityForm checkAvailabilityForm =
			(CheckAvailabilityForm)invocationContext.screenModel;
		String productCode=checkAvailabilityForm.getCode();
		log
				.log(
						Log.FINE,
						"\n\n\n\n**********************productCode from client---------",
						productCode);
		/*String productName=findProductName(session,productCode);
		log.log(Log.FINE,
				"\n\n\n\n**********************productName from client---------"
				+productName);*/
		String productName = checkAvailabilityForm.getName();
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		Collection<ErrorVO> errorVos=new ArrayList<ErrorVO>();
		errors = validateForm(checkAvailabilityForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "check_success";
			return;
		}
		StationAvailabilityFilterVO filterVo=findDetails(checkAvailabilityForm);
		log
				.log(
						Log.FINE,
						"\n\n\n\n**********************filterVo from server-----------",
						filterVo);
		try{
			Collection<String> station=new ArrayList<String>();
						Collection<String> commodity=new ArrayList<String>();
			station.add(upper(filterVo.getOrigin()));
			station.add(upper(filterVo.getDestination()));
			commodity.add(upper(filterVo.getCommodity()));

			new AreaDelegate().validateAirportCodes(
					getApplicationSession().getLogonVO().getCompanyCode(),station);

			new CommodityDelegate().validateCommodityCodes(
					getApplicationSession().getLogonVO().getCompanyCode(),
					commodity);
			ProductDefaultsDelegate productDefaultsDelegate =
				new ProductDefaultsDelegate();
			boolean checkResult=productDefaultsDelegate.
			checkStationAvailability(filterVo);

			if(checkResult){
				log.log(Log.FINE,
						"\n\n\n\n***************error true----------",
						checkResult);
				ErrorVO error = null;
				Object[] obj = {productName};
				error = new ErrorVO("products.defaults.checktrue", obj);
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target="check_failure";

			}else{
				log.log(Log.FINE,
						"\n\n\n\n****************error false-----------",
						checkResult);
				ErrorVO error = null;
				Object[] obj = {productName};
				error = new ErrorVO("products.defaults.checknottrue", obj);
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target="check_failure";
			}

		}catch(BusinessDelegateException businessDelegateException){
     		
			businessDelegateException.getMessage();
			errorVos = handleDelegateException(businessDelegateException);
		}
		if (errorVos != null && errorVos.size() > 0) {
			invocationContext.addAllError(errorVos);
			invocationContext.target = "check_success";
		}
		invocationContext.target="check_success";
	}

	/**
	 *
	 * @param form
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(CheckAvailabilityForm form){
		log.log(Log.FINE,"\n\n\n -----inside-validateForm---->" );
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if("".equals(form.getOrigin())){
			log.log(Log.FINE,"\n\n\n -----inside-2 error---->" );
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.origincannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		if("".equals(form.getDestination())){
			log.log(Log.FINE,"\n\n\n -----inside-3 error---->" );
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.destinationcannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		if("".equals(form.getCommodity())){
			log.log(Log.FINE,"\n\n\n -----inside-1 error---->" );
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.commoditycannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}


		return errors;
	}
	/**
	 *
	 * @param checkAvailabilityForm
	 * @return StationAvailabilityFilterVO
	 */
	private StationAvailabilityFilterVO
	    findDetails(CheckAvailabilityForm checkAvailabilityForm){
		StationAvailabilityFilterVO availabilityFilterVO=
			new StationAvailabilityFilterVO();
		if(checkAvailabilityForm.getCommodity()!=null &&
				checkAvailabilityForm.getCommodity().trim().length()>0) {
			availabilityFilterVO.setCommodity(
					checkAvailabilityForm.getCommodity().trim().toUpperCase());
		}
		availabilityFilterVO.setCompanyCode(
				getApplicationSession().getLogonVO().getCompanyCode());
		availabilityFilterVO.setProductCode(checkAvailabilityForm.getCode());
		if(checkAvailabilityForm.getOrigin()!=null &&
				checkAvailabilityForm.getOrigin().trim().length()>0) {
			availabilityFilterVO.setOrigin(
					checkAvailabilityForm.getOrigin().trim().toUpperCase());
		}
		if(checkAvailabilityForm.getDestination()!=null &&
				checkAvailabilityForm.getDestination().trim().length()>0) {
			availabilityFilterVO.setDestination(
				checkAvailabilityForm.getDestination().trim().toUpperCase());
		}
		return availabilityFilterVO;
	}
	/**
	 *
	 * @param session
	 * @param code
	 * @return String
	 */
	/*private String findProductName(ListProductSessionInterface session,
			 String code){
		String name="";
		if(!"".equals(code)){
		Page<ProductVO> page=session.getPageProductCatalogueList();
		for(ProductVO vo:page){
			if((vo.getProductCode()).equals(code)){
				name=vo.getProductName();
			}
		}
		}
		return name;

	}*/
	/**
	 *
	 * @param input
	 * @return String
	 */
	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}
}
