package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * ReloadCommand
 * @author A-1865
 *
 */
public class ReloadCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("PRODUCT DEFAULTS LIST PRODUCTS");
	private static final String RELOAD_SUCCESS = "reload_success";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.log(Log.INFO,"\n\n\nInside Reload command");
		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		setFormDetails(listProductForm,session);
		invocationContext.target = RELOAD_SUCCESS;
	}
	/**
	 * Function to set form details from the session 
	 * @param listProductForm
	 * @param session
	 */
	private void setFormDetails(ListProductForm listProductForm,ListProductSessionInterface session){
		//Modified by A-5220 for ICRD-32647 starts
		/*
		 * Replaced session.getFilterDetails() with session.getProductFilterVO()
		 * because session.getFilterDetails() stores sub product form details
		 * whereas session.getProductFilterVO() stores product form details
		 */
		ProductFilterVO productFilterVO = session.getProductFilterVO();
		//Modified by A-5220 for ICRD-32647 ends
		if(productFilterVO!= null){
			log.log(Log.INFO, "\n\n\nproductFilterVO is not null",
					productFilterVO);
			listProductForm.setProductName(productFilterVO.getProductName());
			listProductForm.setStatus(productFilterVO.getStatus());
			listProductForm.setTransportMode(productFilterVO.getTransportMode());
			listProductForm.setPriority(productFilterVO.getPriority());
			listProductForm.setProductScc(productFilterVO.getScc());
			listProductForm.setProductCategory(productFilterVO.getProductCategory());
			//Added by A-5220 for ICRD-34755 starts
			listProductForm.setRateDefined(productFilterVO.getIsRateDefined());
			//Added by A-5220 for ICRD-34755 ends
			if(productFilterVO.getFromDate()!=null){
			listProductForm.setFromDate(TimeConvertor.toStringFormat(productFilterVO.getFromDate(),TimeConvertor.CALENDAR_DATE_FORMAT));
			}
			if(productFilterVO.getToDate()!=null){
			listProductForm.setToDate(TimeConvertor.toStringFormat(productFilterVO.getToDate(),TimeConvertor.CALENDAR_DATE_FORMAT));
			}
		}
	}

}
