package com.ibsplc.icargo.business.mail.mra;

import java.util.HashMap;
import com.ibsplc.icargo.business.businessframework.businessevaluator.defaults.vo.BusinessEvaluatorFilterVO;
import com.ibsplc.icargo.business.businessframework.businessevaluator.defaults.vo.BusinessEvaluatorOutputVO;
import com.ibsplc.icargo.business.businessframework.businessevaluator.defaults.vo.BusinessEvaluatorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.proxy.BusinessEvaluatorDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailMRAHelper {
	private static Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String MOD_NAM = "mail";
	private static final String SUB_MOD_NAM = "mra";
	
	public static String evaluateCCAWorkflowAssignee(CCAdetailsVO detailsVO) {
		
		String assignee = "";
		if (detailsVO != null) {
				BusinessEvaluatorFilterVO businessEvaluatorFilterVO = new BusinessEvaluatorFilterVO();
				int a=0;
				HashMap<String, Object> mcaMapforWrkflw = new HashMap<String, Object>();
				
				BusinessEvaluatorVO businessEvaluatorVO = null;
				LogonAttributes logonAttributes = null;
				try {
					logonAttributes = ContextUtils.getSecurityContext()
							.getLogonAttributesVO();
				} catch (SystemException e) {
					log.log(Log.FINE, "evaluateCCAWorkflowAssigneeexception");
				}
				LocalDate currentDate = new LocalDate(
						logonAttributes.getStationCode(), Location.ARP, true);
				// setting the filter values to call
				// evaluateBusinessRules(businessValidationFilterVO) method
				businessEvaluatorFilterVO.setRuleType("MCAWRKFLW");  //instead of hardcoding, get the value from system parameter			
				businessEvaluatorFilterVO.setEffectiveDate(currentDate);
				businessEvaluatorFilterVO.setCompanyCode(detailsVO.getCompanyCode());
				businessEvaluatorFilterVO.setStatus(String
						.valueOf(BusinessEvaluatorFilterVO.CONFIG_STATUS_ACTIVE));
				mcaMapforWrkflw.put("MRAMCA", detailsVO);
				businessEvaluatorFilterVO.setTransactionCode("SAVEMCA");
				businessEvaluatorFilterVO.setContextObjectMap(mcaMapforWrkflw);
				try {
					businessEvaluatorVO = new BusinessEvaluatorDefaultsProxy()
							.evaluateBusinessRules(businessEvaluatorFilterVO);
				} catch (ProxyException pe) {
					log.log(Log.FINE, "evaluateMCAWorkflowAssigneeexception");
				} catch (SystemException e) {
					log.log(Log.FINE, "evaluateMCAWorkflowAssigneeexception");
				}
				assignee = findAssignee(businessEvaluatorVO);
			}
			return assignee;
		}
		private static String findAssignee(BusinessEvaluatorVO businessEvaluatorVO) {
			StringBuilder assigneRolgrp = new StringBuilder();
			if (businessEvaluatorVO != null) {
				if (businessEvaluatorVO.getEvaluatedRules() != null
						&& businessEvaluatorVO.getEvaluatedRules().length() > 0) {
					if (businessEvaluatorVO.getValiadationOutputs() != null) {
						for (BusinessEvaluatorOutputVO businessEvaluatorOutputVO : businessEvaluatorVO
								.getValiadationOutputs()) {
							if (businessEvaluatorOutputVO != null
									&& businessEvaluatorOutputVO.getOutputMap() != null) {
								assigneRolgrp
										.append((String) businessEvaluatorOutputVO
												.getOutputMap().get("Assignee"));
							}
							return assigneRolgrp.toString();
						}
					}
				}
			}
			return assigneRolgrp.toString();
		}
		public static CCAdetailsVO populateNetValue(CCAdetailsVO detailsVO) {
			
			if(detailsVO.getRevNetAmount() != null ){
				detailsVO.setNetValue(detailsVO.getRevNetAmount().getAmount());
			}
			return 	detailsVO;
		}
		public static CCAdetailsVO populateNetValueBase(CCAdetailsVO detailsVO) throws SystemException{
			/*double netAmount = 0.0;
			Money netAmt;
			try {
				netAmt = CurrencyHelper.getMoney(detailsVO.getContCurCode());
				netAmount=detailsVO.getRevChgGrossWeight().getAmount()+detailsVO.getOtherRevChgGrossWgt().getAmount()+ detailsVO.getRevTax() - detailsVO.getRevTds();
				netAmt.setAmount(netAmount);
				detailsVO.setNetAmountBase(netAmt.getAmount());
			} catch (CurrencyException excep) {
				log.log(Log.FINE,"Inside CurrencyException.. ");
			}
			if(detailsVO.getNetAmount() != null ){
				
				detailsVO.setNetAmountBase(detailsVO.getNetAmount().getAmount());
			}
			return 	detailsVO;
		}*/
			//Added as part of ICRD-341591 starts
			CurrencyConvertorVO currencyConvertorVO = new CurrencyConvertorVO();
		    currencyConvertorVO.setCompanyCode(detailsVO.getCompanyCode());
		    currencyConvertorVO.setRatePickUpDate(detailsVO.getIssueDat());
		    currencyConvertorVO.setFromCurrencyCode(detailsVO.getCurCode());
		    currencyConvertorVO.setToCurrencyCode("USD");
		    currencyConvertorVO.setRatingBasisType("F");
		    double exchangeRate = 0.0D;
		    //Added as part of ICRD-342939
		    if(currencyConvertorVO.getCompanyCode()!=null && currencyConvertorVO.getRatePickUpDate()!=null && currencyConvertorVO.getFromCurrencyCode()!=null){
		    if (!currencyConvertorVO.getFromCurrencyCode().equals(
		    	      currencyConvertorVO.getToCurrencyCode())){
		    	      try {
		    	        exchangeRate = new SharedCurrencyProxy()
		    	          .findConversionRate(currencyConvertorVO).doubleValue();
		    	      } catch (ProxyException localProxyException) {
		    	        
		    	    	  throw new SystemException ( " ");
		    	      }
		    }
		    	    if(detailsVO.getCurCode().equals("USD"))
		    	    	detailsVO.setNetAmountBase(detailsVO.getNetValue());
		    	    else {
		    	    	if(exchangeRate!=0.0 && detailsVO.getNetValue()!=0.0)
		    	    		
		    	    		detailsVO.setNetAmountBase(detailsVO.getNetValue()*exchangeRate);	
		    	    }
		    }
		    //Added as part of ICRD-342939
		    else
		    	detailsVO.setNetAmountBase(detailsVO.getNetValue());	
		    	    return detailsVO;
		    	    ////Added as part of ICRD-341591 ends
		}
}