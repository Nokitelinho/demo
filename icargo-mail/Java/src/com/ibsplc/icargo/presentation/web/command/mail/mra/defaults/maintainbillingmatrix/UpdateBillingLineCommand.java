/*
 * UpdateBillingLineCommand.java Created on Jun 12, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-5255 
 * @version	0.1, Jun 12, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jun 12, 2015 A-5255
 * First draft
 */

public class UpdateBillingLineCommand extends BaseCommand {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String EMPTY_STRING = "";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String MAILCHARGE = "M";
	
	private static final String RATEUPDATEONLYACTION="updateonly";
	
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * 
	 * @author A-5255
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		BillingLineForm form = (BillingLineForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		HashMap<String, BillingLineDetailVO> billingLineDetailVOMap = null;
		billingLineDetailVOMap = session.getBillingLineChargeDetails();
		/**
		 * to Update the session BillingLineChargeDetails
		 */
		updateBillingLineChargeDetails(form, session, billingLineDetailVOMap);
		
		/**
		 * check whether the action is for update only ,else it will get the data from session
		 */
		if(!RATEUPDATEONLYACTION.equals(form.getRateAction())){
		/**
		 * To get the details form session
		 */
		getFromBillingLineChargeDetails(form, session, billingLineDetailVOMap);
		}
		invocationContext.target = SCREEN_SUCCESS;
	}

	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param session
	 * @param billingLineDetailVOMap
	 */
	private void updateBillingLineChargeDetails(BillingLineForm form,
			MaintainBillingMatrixSession session,
			HashMap<String, BillingLineDetailVO> billingLineDetailVOMap) {

		BillingLineDetailVO billingLineDetailVO = null;
		Collection<BillingLineChargeVO> billingLineCharges = null;
		String a="";
		/**
		 * PrevoiusChargeHead is the value selected in 'charge head' drop down in surcharge tab.
		 */
		if (form.getPrevoiusChargeHead() != null
				&& !EMPTY_STRING.equals(form.getPrevoiusChargeHead().trim())) {
			if (!MAILCHARGE.equals(form.getChargeHead())) {
				if (form.getRatingBasisOther() != null
						&& !EMPTY_STRING.equals(form.getRatingBasisOther()
								.trim())) {

					billingLineDetailVO = new BillingLineDetailVO();
					billingLineDetailVO.setChargeType(form
							.getPrevoiusChargeHead());
					billingLineCharges = new ArrayList<BillingLineChargeVO>();
					/**
					 * Check whether the RatingBasis of surcharge= FLATRATE or FLATCHARGE or WEIGTHBREAK. 
					 * And update to session accordingly
					 */
					if (checkAndUpdateFlatRate(form, billingLineCharges,
							billingLineDetailVO)
							|| checkAndUpdateFlatCharge(form,
									billingLineCharges, billingLineDetailVO)
							|| checkAndUpdateWeightBreak(form,
									billingLineCharges, billingLineDetailVO)) {

						if (billingLineCharges.size() > 0) {
							billingLineDetailVO
									.setBillingLineCharges(billingLineCharges);
						}
						/**
						 * setting in to session
						 */
						if (billingLineDetailVOMap == null) {
							billingLineDetailVOMap = new HashMap<String, BillingLineDetailVO>();
							billingLineDetailVOMap.put(
									form.getPrevoiusChargeHead(),
									billingLineDetailVO);
							session.setBillingLineChargeDetails(billingLineDetailVOMap);
						} else if (billingLineDetailVOMap.containsKey(form
								.getPrevoiusChargeHead())) {
							billingLineDetailVOMap.remove(form
									.getPrevoiusChargeHead());
							billingLineDetailVOMap.put(
									form.getPrevoiusChargeHead(),
									billingLineDetailVO);
							session.setBillingLineChargeDetails(billingLineDetailVOMap);
						} else {
							billingLineDetailVOMap.put(
									form.getPrevoiusChargeHead(),
									billingLineDetailVO);
							session.setBillingLineChargeDetails(billingLineDetailVOMap);
						}
					} else if (billingLineDetailVOMap!=null && billingLineDetailVOMap.containsKey(form
							.getPrevoiusChargeHead())) {
						billingLineDetailVOMap.remove(form
								.getPrevoiusChargeHead());
					}
					//Commented as part of Bug ICRD-129490 starts

					/*form.setFlatChargeOther(EMPTY_STRING);
					form.setFlatRateOther(EMPTY_STRING);
					form.setRatingBasisOther(EMPTY_STRING);*/  
					
					//Commented as part of Bug ICRD-129490 ends
				}
			}

		}
	}

	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param billingLineCharges
	 * @param billingLineDetailVO
	 * @return
	 */
	private boolean checkAndUpdateFlatRate(BillingLineForm form,
			Collection<BillingLineChargeVO> billingLineCharges,
			BillingLineDetailVO billingLineDetailVO) {
		if (form.getRatingBasisOther().equals(BillingLineChargeVO.FLATRATE)
				&& form.getFlatRateOther() != null
				&& !EMPTY_STRING.equals(form.getFlatRateOther())) {
			BillingLineChargeVO billingLineChargeVO = new BillingLineChargeVO();
			billingLineChargeVO.setApplicableRateCharge(Double.parseDouble(form
					.getFlatRateOther()));
			billingLineCharges.add(billingLineChargeVO);
			billingLineChargeVO.setRateType(BillingLineChargeVO.RATE);
			billingLineDetailVO.setRatingBasis(BillingLineChargeVO.FLATRATE);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param billingLineCharges
	 * @param billingLineDetailVO
	 * @return
	 */
	private boolean checkAndUpdateFlatCharge(BillingLineForm form,
			Collection<BillingLineChargeVO> billingLineCharges,
			BillingLineDetailVO billingLineDetailVO) {
		if (form.getRatingBasisOther().equals(BillingLineChargeVO.FLATCHARGE)
				&& form.getFlatChargeOther() != null
				&& !EMPTY_STRING.equals(form.getFlatChargeOther())) {
			Money aplRatChg=null;
			BillingLineChargeVO billingLineChargeVO = new BillingLineChargeVO();
			billingLineChargeVO.setApplicableRateCharge(Double.parseDouble(form
					.getFlatChargeOther()));
			//Added as part of ICRD-150833 starts
			try {
				aplRatChg = CurrencyHelper
				.getMoney(form.getCurrency().toUpperCase()); //modified by A-8149 for ICRD-276575
			} catch (CurrencyException e) {
				log.log(Log.FINE, e.getMessage());
			}
			aplRatChg.setAmount(billingLineChargeVO.getApplicableRateCharge());
			billingLineChargeVO.setAplRatChg(aplRatChg);
			//Added as part of ICRD-150833 ends
			billingLineChargeVO.setRateType(BillingLineChargeVO.CHARGE);
			billingLineCharges.add(billingLineChargeVO);
			billingLineDetailVO.setRatingBasis(BillingLineChargeVO.FLATCHARGE);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param billingLineCharges
	 * @param billingLineDetailVO
	 * @return
	 */
	private boolean checkAndUpdateWeightBreak(BillingLineForm form,
			Collection<BillingLineChargeVO> billingLineCharges,
			BillingLineDetailVO billingLineDetailVO) {
		if (form.getRatingBasisOther().equals(BillingLineChargeVO.WEIGTHBREAK)) {
			billingLineDetailVO.setRatingBasis(BillingLineChargeVO.WEIGTHBREAK);

			// isMinRateRequired=false;
			if (!EMPTY_STRING.equals(form.getMinimumChargeOther().trim())) {
				BillingLineChargeVO billingLineChargeVO = new BillingLineChargeVO();
				billingLineChargeVO.setApplicableRateCharge(Double
						.parseDouble(form.getMinimumChargeOther()));
				billingLineChargeVO.setFrmWgt(-1);
				billingLineChargeVO.setRateType(BillingLineChargeVO.CHARGE);
				billingLineCharges.add(billingLineChargeVO);
				// isMinRateRequired=true;
			}
			if (!EMPTY_STRING.equals(form.getNormalRateOther().trim())) {
				BillingLineChargeVO billingLineChargeVO = new BillingLineChargeVO();
				billingLineChargeVO.setApplicableRateCharge(Double
						.parseDouble(form.getNormalRateOther()));
				billingLineChargeVO.setFrmWgt(0);
				billingLineChargeVO.setRateType(BillingLineChargeVO.RATE);
				billingLineCharges.add(billingLineChargeVO);
				// isMinRateRequired=true;

			}
			/*
			 * if(isMinRateRequired){ billingLineChargeVO.setFrmWgt(-1);
			 * billingLineCharges.add(billingLineChargeVO); }
			 */

			if (form.getWbFrmWgtOther() != null
					&& form.getWbFrmWgtOther().length > 0) {
				int i = 0;
				for (String wbFrmWgtOther : form.getWbFrmWgtOther()) {
					if (!EMPTY_STRING.equals(wbFrmWgtOther)) {
						BillingLineChargeVO billingLineChargeVO = new BillingLineChargeVO();
						billingLineChargeVO.setFrmWgt(Double
								.parseDouble(wbFrmWgtOther));
						billingLineChargeVO
								.setRateType(BillingLineChargeVO.RATE);
						billingLineChargeVO
								.setApplicableRateCharge(Double
										.parseDouble(form
												.getWbApplicableRateOther()[i]));
						billingLineChargeVO.setOperationalFlag("I");
						billingLineCharges.add(billingLineChargeVO);
					}
					i++;
				}
			}
			return true;

		}
		return false;
	}
	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param session
	 * @param billingLineDetailVOMap
	 */
	private void getFromBillingLineChargeDetails(BillingLineForm form,
			MaintainBillingMatrixSession session,
			HashMap<String, BillingLineDetailVO> billingLineDetailVOMap) {
		BillingLineDetailVO billingLineDetailVO = null;
		BillingLineChargeVO billingLineChargeVO = null;
		if (form.getChargeHead() != null
				&& !EMPTY_STRING.equals(form.getChargeHead().trim())) {
			if (billingLineDetailVOMap != null
					&& billingLineDetailVOMap.containsKey(form.getChargeHead())) {
				billingLineDetailVO = billingLineDetailVOMap.get(form
						.getChargeHead());
				if (billingLineDetailVO != null) {

					if (billingLineDetailVO.getRatingBasis() != null) {
						form.setRatingBasisOther(billingLineDetailVO
								.getRatingBasis());
						boolean isUpdated = setFlatRateFormSection(form,
								billingLineDetailVO, billingLineChargeVO)
								|| setFlatChargeFormSection(form,
										billingLineDetailVO,
										billingLineChargeVO)
								|| setWeightBreakFormSection(form, session,
										billingLineDetailVO);
					}
				}
			}
		}
	}
	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param billingLineDetailVO
	 * @param billingLineChargeVO
	 * @return
	 */
	private boolean setFlatRateFormSection(BillingLineForm form,
			BillingLineDetailVO billingLineDetailVO,
			BillingLineChargeVO billingLineChargeVO) {
		if (BillingLineChargeVO.FLATRATE.equals(billingLineDetailVO
				.getRatingBasis())) {
			if (billingLineDetailVO.getBillingLineCharges() != null
					&& billingLineDetailVO.getBillingLineCharges().size() > 0) {
				billingLineChargeVO = billingLineDetailVO
						.getBillingLineCharges().iterator().next();
//				form.setFlatRateOther(new BigDecimal(billingLineChargeVO
//						.getApplicableRateCharge()).setScale(4,
//						BigDecimal.ROUND_UNNECESSARY).toPlainString());
				double roundedRate=getScaledValue(billingLineChargeVO.getApplicableRateCharge(),4);
				form.setFlatRateOther(String.valueOf(roundedRate));

			}
			return true;
		}
		return false;
	}
	
	private double getScaledValue(double value, int precision) { 
        java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value); 
        return bigDecimal.setScale(precision, 
                        java.math.BigDecimal.ROUND_HALF_UP).doubleValue(); 
}
	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param billingLineDetailVO
	 * @param billingLineChargeVO
	 * @return
	 */
	private boolean setFlatChargeFormSection(BillingLineForm form,
			BillingLineDetailVO billingLineDetailVO,
			BillingLineChargeVO billingLineChargeVO) {
		if (BillingLineChargeVO.FLATCHARGE.equals(billingLineDetailVO
				.getRatingBasis())) {
			if (billingLineDetailVO.getBillingLineCharges() != null
					&& billingLineDetailVO.getBillingLineCharges().size() > 0) {
				billingLineChargeVO = billingLineDetailVO
						.getBillingLineCharges().iterator().next();
				form.setFlatChargeOther(new BigDecimal(billingLineChargeVO
						.getApplicableRateCharge()).setScale(2,
						BigDecimal.ROUND_UP).toPlainString());
			}
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @author A-5255
	 * @param form
	 * @param session
	 * @param billingLineDetailVO
	 * @return
	 */
	private boolean setWeightBreakFormSection(BillingLineForm form,
			MaintainBillingMatrixSession session,
			BillingLineDetailVO billingLineDetailVO) {
		if (BillingLineChargeVO.WEIGTHBREAK.equals(billingLineDetailVO
				.getRatingBasis())) {
			List<BillingLineChargeVO> billingLineChargeWgtBreakList = new ArrayList<BillingLineChargeVO>();
			if (billingLineDetailVO.getBillingLineCharges() != null
					&& billingLineDetailVO.getBillingLineCharges().size() > 0) {
				// to filter the normal charge and minimum wgt
				ArrayList<BillingLineChargeVO> billingLineChargeVOs = new ArrayList<BillingLineChargeVO>();
				for (BillingLineChargeVO billingLineChargesVO : billingLineDetailVO
						.getBillingLineCharges()) {
					if (billingLineChargesVO.getFrmWgt() != -1
							&& billingLineChargesVO.getFrmWgt() != 0) {
						billingLineChargeVOs.add(billingLineChargesVO);
					} else {
						billingLineChargeWgtBreakList.add(billingLineChargesVO);
					}
				}
				session.setBillingLineSurWeightBreakDetails(billingLineChargeVOs);
			}
			if (billingLineChargeWgtBreakList != null) {
				for (BillingLineChargeVO billingLineChargeWgtBreakVO : billingLineChargeWgtBreakList) {
					if (billingLineChargeWgtBreakVO.getApplicableRateCharge() != 0
							&& billingLineChargeWgtBreakVO.getRateType() != null
							&& "C".equals(billingLineChargeWgtBreakVO
									.getRateType())) {
						form.setMinimumChargeOther(new BigDecimal(
								billingLineChargeWgtBreakVO
										.getApplicableRateCharge()).setScale(2,
								BigDecimal.ROUND_UP).toPlainString());
					}
					if (billingLineChargeWgtBreakVO.getApplicableRateCharge() != 0
							&& billingLineChargeWgtBreakVO.getRateType() != null
							&& "R".equals(billingLineChargeWgtBreakVO
									.getRateType())) {
						/*form.setNormalRateOther(new BigDecimal(
								billingLineChargeWgtBreakVO
										.getApplicableRateCharge()).setScale(4,
								BigDecimal.ROUND_UP).toPlainString());*/
						double roundedWBRate=getScaledValue(billingLineChargeWgtBreakVO
								.getApplicableRateCharge(),4);
						form.setNormalRateOther(String.valueOf(roundedWBRate));
					}
				}
			}
			return true;
		}
		return false;
	}

}
