/*
 * OKCommand.java Created on Feb 8, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.copyrate;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyRateSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyRateForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class OKCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MRA DEFAULTS COPYRATE");
	private static final String CLASS_NAME = "OKCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyrate";
	private static final String SCREEN_ID_MAINTAINRATE="mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String CAPTURE="capture";
	private static final String DATES_MANDOTORY = "mailtracking.mra.defaults.copyrate.err.datesmandatory";
	private static final String FROMDATE_GREATER = "mailtracking.mra.defaults.copyrate.err.fromdateisgreater";
	private static final String OK_SUCCESS="ok_success";
	private static final String OK_FAILURE="ok_failure";
	private static final String OPEN = "open";
	private static final String STATUS_NEW="N";
	private static final String RATECARDS_SAME_ERR = "mailtracking.mra.defaults.copyrate.err.ratecardsaresame";

	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		 CopyRateForm copyRateForm=(CopyRateForm)invocationContext.screenModel;
		 CopyRateSession copyRateSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		 MaintainUPURateCardSession
	    	maintainUPURateSession=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID_MAINTAINRATE);
		 LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		 ArrayList<RateLineVO> selectedRateLineVOs=(ArrayList<RateLineVO>)copyRateSession.getSelectedRateLines();
		 Page<RateLineVO> pageRateLines=null;
		 RateCardVO rateCardVO=null;
		 log.log(Log.INFO, "\n\nscreen mode flag-->", copyRateForm.getScreenMode());
		log.log(Log.INFO, "\n\nrate card Id frm Form-->", copyRateForm.getRateCardId());
		log.log(Log.INFO, "\n\nvalid frm  frm Form-->", copyRateForm.getValidFrom());
		log.log(Log.INFO, "\n\nvalid TO  frm Form-->", copyRateForm.getValidFrom());
		RateLineVO selectedrateLineVO=selectedRateLineVOs.get(0);
		 if(selectedrateLineVO.getRateCardID().equalsIgnoreCase(copyRateForm.getRateCardId())){
			 log.log(Log.INFO,"the selected rate card and new Rate card are equal");
			 ErrorVO err=new ErrorVO(RATECARDS_SAME_ERR);
			 err.setErrorDisplayType(ErrorDisplayType.ERROR);
			 invocationContext.addError(err);
			 invocationContext.target=OK_FAILURE;
			 return;
		 }
		 if(CAPTURE.equals(copyRateForm.getScreenMode())){
			 log.log(Log.INFO,"Inside Cpature mode");

			 rateCardVO=new RateCardVO();
			 if((copyRateForm.getValidFrom()==null || copyRateForm.getValidFrom().trim().length()==0)
					 ||(copyRateForm.getValidTo()==null || copyRateForm.getValidTo().trim().length()==0)){
				 log.log(Log.INFO,"DATES mandatory");
				 ErrorVO err=new ErrorVO(DATES_MANDOTORY);

				 err.setErrorDisplayType(ErrorDisplayType.ERROR);
				 invocationContext.addError(err);
				 invocationContext.target=OK_FAILURE;
				 return;

			 }
			LocalDate  frmDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(copyRateForm.getValidFrom());
			LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(copyRateForm.getValidTo());
			 if(frmDate.isGreaterThan(toDate)){
				 log.log(Log.INFO,"From date is greater than TO date");
                ErrorVO err=new ErrorVO(FROMDATE_GREATER);
				 err.setErrorDisplayType(ErrorDisplayType.ERROR);
				 invocationContext.addError(err);
				 invocationContext.target=OK_FAILURE;
				 return;
			 }
			 rateCardVO.setRateCardStatus(STATUS_NEW);
			 rateCardVO.setCompanyCode(logonAttributes.getCompanyCode());
			 rateCardVO.setValidityStartDate(frmDate);
			 rateCardVO.setValidityEndDate(toDate);
			 rateCardVO.setRateCardID(copyRateForm.getRateCardId().toUpperCase());
			 rateCardVO.setOperationFlag(RateCardVO.OPERATION_FLAG_INSERT);
			 calculateRateCardDetails(rateCardVO,selectedRateLineVOs);

			 pageRateLines=new Page<RateLineVO>(selectedRateLineVOs,1,25,
					 selectedRateLineVOs.size(),0,selectedRateLineVOs.size()-1,false);
			 rateCardVO.setRateLineVOss(pageRateLines);
			 maintainUPURateSession.setRateCardDetails(rateCardVO);
			 maintainUPURateSession.setRateLineDetails(pageRateLines);
			 copyRateForm.setScreenFlag(OPEN);




		 }
		 else{
			 log.log(Log.INFO,"Inside ratecard details present");
			 pageRateLines=new Page<RateLineVO>(selectedRateLineVOs,1,25,selectedRateLineVOs.size(),0,selectedRateLineVOs.size()-1,false);
			 
			 rateCardVO=copyRateSession.getRateCardVO();
			 rateCardVO.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
			 calculateRateCardDetails(rateCardVO,selectedRateLineVOs);
			 maintainUPURateSession.setRateCardDetails(rateCardVO);
			 maintainUPURateSession.setRateLineDetails(pageRateLines);
			 copyRateForm.setScreenFlag(OPEN);


		 }
		 invocationContext.target=OK_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}

	/**
	 * This method calculates the ratline values
	 * corresponding to the ratecard Values
	 * @author A-2280
	 * @param rateCardVO
	 * @param selectedRateLineVOs
	 */
	private void calculateRateCardDetails(RateCardVO rateCardVO, ArrayList<RateLineVO> selectedRateLineVOs) {
		log.entering(CLASS_NAME,"calculateRateCardDetails");
		double mdf=rateCardVO.getMailDistanceFactor();
		double catTonKMRefOne=rateCardVO.getCategoryTonKMRefOne();
		double catTonKMRefTwo=rateCardVO.getCategoryTonKMRefTwo();
		double catTonKMRefThree=rateCardVO.getCategoryTonKMRefThree();
		//double rateInSDRForSubclassRefTwo=rateCardVO.getSubclassTonKMRefTwo();
		double exchangeRate=rateCardVO.getExchangeRate();
		if(selectedRateLineVOs!=null && selectedRateLineVOs.size()>0){
			for(RateLineVO rateLineVO:selectedRateLineVOs){
				double iataKM=rateLineVO.getIataKilometre();
				rateLineVO.setMailKilometre(iataKM*mdf);
				rateLineVO.setRateInSDRForCategoryRefOne(iataKM*mdf*(catTonKMRefOne/1000));
				rateLineVO.setRateInSDRForCategoryRefTwo(iataKM*mdf*(catTonKMRefTwo/1000));
				rateLineVO.setRateInSDRForCategoryRefThree(iataKM*mdf*catTonKMRefThree/1000);
				//rateLineVO.setRateInSDRForSubclassRefTwo(iataKM*mdf*(rateInSDRForSubclassRefTwo/1000));
				
				double emssdr=rateLineVO.getRateInSDRForCategoryRefOne();
				
				double salsdr=rateLineVO.getRateInSDRForCategoryRefTwo();
				
				double airmailsdr=rateLineVO.getRateInSDRForCategoryRefThree();
				
				double cpsdr=rateLineVO.getRateInSDRForSubclassRefTwo();
				rateLineVO.setRateInBaseCurrForCategoryRefOne(emssdr*exchangeRate);
				rateLineVO.setRateInBaseCurrForCategoryRefTwo(salsdr*exchangeRate);
				rateLineVO.setRateInBaseCurrForCategoryRefThree(airmailsdr*exchangeRate);
				rateLineVO.setRateInBaseCurrForSubclassRefTwo(cpsdr*exchangeRate);
				rateLineVO.setValidityStartDate(rateCardVO.getValidityStartDate());
				rateLineVO.setValidityEndDate(rateCardVO.getValidityEndDate());
				rateLineVO.setRatelineStatus(STATUS_NEW);
			}
		}
		log.exiting(CLASS_NAME,"calculateRateCardDetails");
		
	}

}
