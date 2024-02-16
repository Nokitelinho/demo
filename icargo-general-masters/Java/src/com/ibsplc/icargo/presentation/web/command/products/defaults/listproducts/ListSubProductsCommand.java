package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * ListSubProductsCommand 
 * @author A-1865
 *
 */
public class ListSubProductsCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("PRODUCT DEFAULTS LIST PRODUCTS");
	private static final String LISTSUBPRODUCT_SUCCESS = "list_subproduct_success";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.log(Log.INFO,"\n\n\n Inside ListSubProducts Command");
		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		ProductFilterVO productFilterVO = new ProductFilterVO();
		productFilterVO = getFormDetails(listProductForm);
		session.setFilterDetails(productFilterVO);
		listProductForm.setButtonStatusFlag("fromListProduct");
		invocationContext.target =LISTSUBPRODUCT_SUCCESS;
	}
	
	private ProductFilterVO getFormDetails(ListProductForm listProductForm){
		ProductFilterVO productFilterVO = new ProductFilterVO();
		log.log(Log.INFO,"\n\n\n Setting form details into session");
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if (listProductForm.getFromDate() != null	&& listProductForm.getFromDate().trim().length() != 0) {
			productFilterVO.setFromDate(fromDate.setDate(listProductForm.getFromDate()));
		}
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if (listProductForm.getToDate() != null	&& listProductForm.getToDate().trim().length() != 0) {
			productFilterVO.setToDate(toDate.setDate(listProductForm.getToDate()));
		}

		productFilterVO.setProductName(listProductForm.getProductName());
		productFilterVO.setStatus(listProductForm.getStatus());
		//productFilterVO.setIsRateDefined(listProductForm.get);
		productFilterVO.setTransportMode(listProductForm.getTransportMode());
		productFilterVO.setPriority(listProductForm.getPriority());
		productFilterVO.setScc(listProductForm.getProductScc());
		
		return productFilterVO;
		
	}

}
