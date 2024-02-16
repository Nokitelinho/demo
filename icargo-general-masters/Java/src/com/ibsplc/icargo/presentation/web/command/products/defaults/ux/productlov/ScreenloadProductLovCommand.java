package com.ibsplc.icargo.presentation.web.command.products.defaults.ux.productlov;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.products.defaults.vo.ProductLovFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ux.ProductLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * 
 * @author a-1870
 * 
 */
public class ScreenloadProductLovCommand extends BaseCommand {
	// private static final String COMPANY_CODE = "AV";
	/**
	 * Log
	 */
	private Log log = LogFactory.getLogger("Products");

	private static final String BOOKING = "B";

	private static final String TRUE = "Y";
	
	private static final String EMPTY = "";
	private static final String CAPTURE_AWB = "OPR026";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ProductLovForm productLovForm = (ProductLovForm) invocationContext.screenModel;
		ListProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.listproducts");
		// productLovForm.setEndDate("");
		// productLovForm.setStartDate("");

		if (productLovForm.getBookingDate() != null
				&& productLovForm.getBookingDate().trim().length() > 0) {
			if (productLovForm.getEndDate() != null
					&& productLovForm.getEndDate().trim().length() == 0) {
				productLovForm.setEndDate(productLovForm.getBookingDate());
			}
		}
		if (productLovForm.getBookingDate() != null
				&& productLovForm.getBookingDate().trim().length() > 0) {
			if (productLovForm.getStartDate() != null
					&& productLovForm.getStartDate().trim().length() == 0) {
				productLovForm.setStartDate(productLovForm.getBookingDate());
			}
		}
		int displayPage=1;
		ProductLovFilterVO productLovFilterVO = getSearchDetails(productLovForm);
		if(EMPTY.equals(productLovFilterVO.getProductName())&& productLovFilterVO.getProductName().length()==0){
		displayPage= Integer.parseInt(productLovForm.getDisplayPage());
		}

		HashMap indexMap = null;
		HashMap finalMap = null;
		if (session.getIndexMap() != null) {
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {

			indexMap = new HashMap();
			indexMap.put("1", "1");
		}

		int nAbsoluteIndex = 0;

		String strAbsoluteIndex = (String) indexMap.get(productLovForm
				.getDisplayPage());

		if (strAbsoluteIndex != null) {

			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		productLovFilterVO.setPageNumber(displayPage);
		productLovFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		productLovFilterVO.setDefaultPageSize(Integer.parseInt(productLovForm.getDefaultPageSize()));
		log
				.log(
						Log.FINE,
						"\n\n\n\n--------------------productLovFilterVO sent to server-----------",
						productLovFilterVO);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(productLovForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			return;
		}

		try {
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			Page<ProductLovVO> page = productDefaultsDelegate.findProductLov(
					productLovFilterVO, displayPage);
			// productLovForm.setStartDate(productLovForm.getStartDate());
			// productLovForm.setEndDate(productLovForm.getEndDate());
			productLovForm.setPageProductLov(page);
			finalMap = indexMap;
			if (productLovForm.getPageProductLov() != null) {
				finalMap = buildIndexMap(indexMap, productLovForm
						.getPageProductLov());
				session.setIndexMap(finalMap);
			}

		} catch (BusinessDelegateException businessDelegateException) {
			// To be reviewed call handleException
			businessDelegateException.getMessage();
		}
		invocationContext.target = "screenload_success";
	}

	/**
	 * 
	 * @param productLovForm
	 * @return ProductLovFilterVO
	 */
	private ProductLovFilterVO getSearchDetails(ProductLovForm productLovForm) {
		ProductLovFilterVO productLovFilterVO = new ProductLovFilterVO();
		
		// Modified below if condition for Bug fix ICRD-21095 by Ameer Basha. (added || CAPTURE_AWB.equals(productLovForm.getSourceScreen()) )
		if (BOOKING.equals(productLovForm.getSourceScreen()) || CAPTURE_AWB.equals(productLovForm.getSourceScreen())) {
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			productLovFilterVO.setCurrentDate(currentDate);
			productLovFilterVO.setIsProductNotExpired(true);
		}

		if (productLovForm.getEndDate() != null
				&& productLovForm.getEndDate().trim().length() != 0) {
			LocalDate from = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			productLovFilterVO.setEndDate(from.setDate(productLovForm
					.getEndDate()));
		}

		if (productLovForm.getStartDate() != null
				&& productLovForm.getStartDate().trim().length() != 0) {
			LocalDate to = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			productLovFilterVO.setStartDate(to.setDate(productLovForm
					.getStartDate()));
		}
		if (productLovForm.getActiveProducts() != null
				&& productLovForm.getActiveProducts().trim().length() > 0
				&& productLovForm.getActiveProducts().equalsIgnoreCase(TRUE)) {
			productLovFilterVO.setActive(true);
		}
		productLovFilterVO.setProductName(productLovForm.getProductName());
		productLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());

		return productLovFilterVO;
	}

	/**
	 * 
	 * @param form
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ProductLovForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if (!"".equals(form.getStartDate()) && !"".equals(form.getEndDate())) {
			log.log(log.FINER,
					"\n\n*******startdategreaterthancurrentdate********");
			if (!form.getStartDate().equals(form.getEndDate())) {
				log.log(log.FINER, "\n\n*****inside 1******");
				if (!DateUtilities.isLessThan(form.getStartDate(), form
						.getEndDate(), "dd-MMM-yyyy")) {
					log.log(log.FINER, "\n\n*****inside 2******");
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

	/**
	 * This method set the final index map value for pagination
	 * 
	 * @param existingMap
	 * @param page
	 * @return
	 */
	private HashMap buildIndexMap(HashMap existingMap, Page page) {
		HashMap finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));

		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}

		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}
}
