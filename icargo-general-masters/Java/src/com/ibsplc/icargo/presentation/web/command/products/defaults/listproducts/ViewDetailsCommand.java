
package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;



import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2135
 *
 */
public class ViewDetailsCommand  extends BaseCommand {

    private Log log = LogFactory.getLogger("ViewDetailsCommand");

    /*
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		ListProductSessionInterface session = getScreenSession(
						"product.defaults","products.defaults.listproducts");

		ListProductForm listProductForm = (
				ListProductForm)invocationContext.screenModel;

		MaintainProductSessionInterface sessionMaintainProduct = getScreenSession(
					"product.defaults", "products.defaults.maintainproduct");


		ProductFilterVO prdFilterVO = new ProductFilterVO();
		prdFilterVO = getSearchDetails(listProductForm);
		//log.log(Log.INFO,"\n\n\n!!!!!!!!!!!!!session.getProductFilterVO()----->>>>>"+session.getProductFilterVO());
		if ( prdFilterVO!=null ) {
			log.log(Log.INFO, "\n\n\n!!!!!!!!!!!!!prdFilterVO!!!!!!!!!!!!",
					prdFilterVO);
			//	log.log(Log.INFO,"\n\n\n!!!!!!!!!!!!!session.getProductFilterVO()!!!!!!!!!!!!"+session.getProductFilterVO());
			//Added to set get the diplayPage field on closing the Maintain Product Screen
			if(listProductForm.getDisplayPage() != null && listProductForm.getDisplayPage().trim().length()>0){
				prdFilterVO.setDisplayPage(Integer.parseInt(listProductForm.getDisplayPage()));
			}
			//ends
			session.setProductFilterVO(prdFilterVO);
			log
					.log(
							Log.INFO,
							"\n\n\n!!!!!!!!!!!!!session.getProductFilterVO()!!!!!!!!!!!!",
							session.getProductFilterVO());

    	}
		 	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			String stationCode = logonAttributes.getStationCode();
			
			UnitRoundingVO unitRoundingVO = null;
			UnitRoundingVO unitRoundingVolVO = null;
			UnitRoundingVO unitRoundingDimensionsVO = null;//Added by A-7896 as part of ICRD-334236
			try {
				log.log(Log.FINE,"\n\nUnitcode----------->");
				unitRoundingVO = UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.WEIGHT);
				unitRoundingVolVO = UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.VOLUME);
				unitRoundingDimensionsVO=UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.DIMENSION);
				} catch(UnitException unitException) {
				unitException.getErrorCode();
			}
				sessionMaintainProduct.setWeightVO(unitRoundingVO);
				sessionMaintainProduct.setVolumeVO(unitRoundingVolVO);
				sessionMaintainProduct.setDimensionVO(unitRoundingDimensionsVO);
		invocationContext.target = "viewdetails_success";

    }


    private ProductFilterVO getSearchDetails(ListProductForm listProductForm)
    	{
    		ProductFilterVO productFilterVOs = new ProductFilterVO();
    		productFilterVOs.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
    		productFilterVOs.setProductName(listProductForm.getProductName());
    		log
					.log(
							Log.INFO,
							"\n\n\n!!!!!!!!!!!!!listProductForm.getProductName()!!!!!!!!!!!!",
							listProductForm.getProductName());
			productFilterVOs.setStatus(listProductForm.getStatus());
    		productFilterVOs.setIsRateDefined(listProductForm.isRateDefined());
    		productFilterVOs.setTransportMode(listProductForm.getTransportMode());
    		productFilterVOs.setPriority(listProductForm.getPriority());
    		productFilterVOs.setScc(upper(listProductForm.getProductScc()));
    		productFilterVOs.setProductCategory(listProductForm.getProductCategory());
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
