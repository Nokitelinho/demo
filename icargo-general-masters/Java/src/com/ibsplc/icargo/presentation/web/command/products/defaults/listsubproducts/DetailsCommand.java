
package com.ibsplc.icargo.presentation.web.command.products.defaults.listsubproducts;




import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2135
 *
 */
public class DetailsCommand  extends BaseCommand {

    private Log log = LogFactory.getLogger("DetailsCommand");
	private static final String BLANK = "";
    /*
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
		ProductFilterVO prdFilterVO = new ProductFilterVO();
		prdFilterVO = getSearchDetails(listSubProductForm);
		//if ( session.getProductFilterVODetails()!=null ) {
		if ( prdFilterVO!=null ) {
			session.setProductFilterVODetails(prdFilterVO);

    	}
		invocationContext.target = "details_success";
		
    }
   
    private ProductFilterVO getSearchDetails(ListSubProductForm listSubProductForm)
	{
    	
    	LocalDate startDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	
		ProductFilterVO productFilterVO = new ProductFilterVO();
		log.log(Log.FINE, "listSubProductForm.getProductCode()",
				listSubProductForm.getProductCode());
		productFilterVO.setProductCode(listSubProductForm.getProductCode());
		productFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		log.log(Log.FINE, "listSubProductForm.getStatus()", listSubProductForm.getStatus());
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
		productFilterVO.setProductName(upper(listSubProductForm.getProductName()));
		if(listSubProductForm.getStartDate()!= null && !BLANK.equals(listSubProductForm.getStartDate())){
			productFilterVO.setFromDate(startDate.setDate(listSubProductForm.getStartDate()));}
		if(listSubProductForm.getEndDate()!= null && !BLANK.equals(listSubProductForm.getEndDate())){
			productFilterVO.setToDate(endDate.setDate(listSubProductForm.getEndDate()));}
		return productFilterVO;
	
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
