package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.math.BigDecimal;
import java.math.MathContext;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class PopulateNetValueAjaxCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MRA DEFAULTS");
    private static final String AJAX_SUCCESS="populate_success";    
    private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";
    private static final String MODULE_NAME = "mailtracking.mra.defaults";  
   
    public void execute(InvocationContext invocationContext)
                throws CommandInvocationException {    	
    	MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm)invocationContext.screenModel;
		MaintainCCASession maintainCCASession = 
			(MaintainCCASession) getScreenSession(MODULE_NAME, MAINTAINCCA_SCREEN);		
		String revChgGrossWeight=maintainCCAForm.getRevChgGrossWeight();
		String otherRevChgGrossWgt=maintainCCAForm.getOtherRevChgGrossWgt();
		String otherChgGrossWgt=maintainCCAForm.getOtherChgGrossWgt();
		String revTax=maintainCCAForm.getRevTax();		
		String newRevTax=revTax.replace(",","");
		String revRate=maintainCCAForm.getRevisedRate();
		CCAdetailsVO cCAdetailsVO=maintainCCASession.getCCAdetailsVO();		
		double revAmount =0;
		double orgAmount=0;
		double revSurAmount =0;
		double orgSurAmount=0;
		double revTaxD=0.0;
		double taxD=0;
		double weight =0;
		if(!"listbillingentries".equals(maintainCCAForm.getFromScreen())&&!"listbillingentriesuxopenPopUp".equals(maintainCCAForm.getFromScreen())){
		if (cCAdetailsVO != null && revChgGrossWeight.length() <= 12) {
			taxD = cCAdetailsVO.getTax();
			orgAmount = cCAdetailsVO.getChgGrossWeight().getAmount();  
			orgSurAmount=cCAdetailsVO.getOtherChgGrossWgt().getAmount(); 
			if (revChgGrossWeight.trim().length() > 0) {
				log.log(Log.FINE, "null value", revChgGrossWeight);
				revAmount = Double.parseDouble(revChgGrossWeight.replace(",", ""));
			}
			if(otherRevChgGrossWgt.trim().length()>0){
				revSurAmount=Double.parseDouble(otherRevChgGrossWgt.replace(",", ""));
			}
			if (newRevTax != null) {
				revTaxD = Double.parseDouble(newRevTax);
			}
			
			//Added by A-6991 for ICRD-208114
			BigDecimal revtaxsum = new BigDecimal(0);
			BigDecimal othrchgsum = new BigDecimal(0);
			BigDecimal diffAmount = new BigDecimal(0);
			revtaxsum =	new BigDecimal(revAmount + revTaxD + revSurAmount);
			othrchgsum =new BigDecimal(orgAmount + taxD+orgSurAmount);
			diffAmount =revtaxsum.subtract(othrchgsum, MathContext.DECIMAL32);
			
			//double diffAmount = (revAmount + revTaxD + revSurAmount) - (orgAmount + taxD+orgSurAmount);
			log.log(Log.FINE, "difference amnt calculated---->", diffAmount);
			Money revNetAmount = null;
			Money revSurNetAmount = null;
			Money differenceAmount = null;
			
			try {
				revNetAmount = CurrencyHelper.getMoney(
						maintainCCAForm.getRevCurCode().toUpperCase());
				revNetAmount.setAmount(revAmount);
			} catch (CurrencyException e) {		
				log.log(Log.FINE, e.getMessage());
			}
			try {
				revSurNetAmount = CurrencyHelper.getMoney(
						maintainCCAForm.getRevCurCode().toUpperCase());
				revSurNetAmount.setAmount(revSurAmount);
			} catch (CurrencyException e) {	
				log.log(Log.FINE, e.getMessage());
			}
			try {
				differenceAmount = CurrencyHelper.getMoney(
						maintainCCAForm.getRevCurCode().toUpperCase());
				differenceAmount.setAmount(diffAmount.doubleValue());
			} catch (CurrencyException e) {		
				log.log(Log.FINE, e.getMessage());
			}
			if (maintainCCAForm.getRevGrossWeight() != null) {
				weight = Double.parseDouble(maintainCCAForm.getRevGrossWeight());
			}
			cCAdetailsVO.setDifferenceAmount(differenceAmount);
			cCAdetailsVO.setOtherRevChgGrossWgt(revSurNetAmount);
			
			if(revRate!=null){//added for icrd-CRD-278828
				Double.parseDouble(revRate);
				if((Double.parseDouble(revRate)!=0 && Double.parseDouble(revRate)!=cCAdetailsVO.getRate() )|| (weight !=cCAdetailsVO.getGrossWeight())) {//Modified by a-7871 for ICRD-283739			
				if(!(cCAdetailsVO.getDisplayWeightUnit().toUpperCase()).equals(cCAdetailsVO.getRateLineWeightUnit().toUpperCase())){
					double weightInConvertedUnit=unitConvertion(UnitConstants.MAIL_WGT,cCAdetailsVO.getDisplayWeightUnit().toUpperCase(),cCAdetailsVO.getRateLineWeightUnit().toUpperCase(),cCAdetailsVO.getGrossWeight());
					revNetAmount.setAmount(Double.parseDouble(revRate)*weightInConvertedUnit);
					
					revtaxsum =	new BigDecimal(revNetAmount.getAmount() + revTaxD + revSurAmount);
					diffAmount =revtaxsum.subtract(othrchgsum, MathContext.DECIMAL32);
					differenceAmount.setAmount(diffAmount.doubleValue());
									   
					//Added by A-8527 for ICRD-346934 starts	
				}else{
					revNetAmount.setAmount(Double.parseDouble(revRate)*weight);
					revtaxsum =	new BigDecimal(revNetAmount.getAmount() + revTaxD + revSurAmount);
					diffAmount =revtaxsum.subtract(othrchgsum, MathContext.DECIMAL32);
					differenceAmount.setAmount(diffAmount.doubleValue());	
					//Added by A-8527 for ICRD-346934 ends
				}
				}
			}
			cCAdetailsVO.setRevChgGrossWeight(revNetAmount);//modified for icrd-CRD-278828
			cCAdetailsVO.setRevisedRate(Double.parseDouble(revRate));//added for icrd-CRD-278828
			cCAdetailsVO.setRevGrossWeight(weight);
			cCAdetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
			cCAdetailsVO.setRevContCurCode(maintainCCAForm.getRevCurCode());
			maintainCCASession.setCCAdetailsVO(cCAdetailsVO);
			
			maintainCCAForm.setRevisedChargeGrossWeignt(revChgGrossWeight);;
		}}
		else {
			cCAdetailsVO.setRevisedRate(Double.parseDouble(maintainCCAForm.getRevisedRate()));
			maintainCCASession.setCCAdetailsVO(cCAdetailsVO);
		}
			
        invocationContext.target=AJAX_SUCCESS;
    }   
/**
 * 
 * @param unitType
 * @param fromUnit
 * @param toUnit
 * @param fromValue
 * @return Added by A-8331 
 */
	private double unitConvertion(String unitType,String fromUnit,String toUnit,double fromValue) {
		UnitConversionNewVO unitConversionVO = null;
		try {
			unitConversionVO = UnitFormatter.getUnitConversionForToUnit(unitType, fromUnit, toUnit, fromValue);
		} catch (UnitException e) {
			
			log.log(Log.FINE, e.getMessage());
		}
		double convertedValue = unitConversionVO.getToValue();
		return convertedValue;
	}
	   

}