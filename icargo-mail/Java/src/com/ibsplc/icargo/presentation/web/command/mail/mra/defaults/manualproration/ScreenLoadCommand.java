/*
 * ScreenLoadCommand.java Created on June 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.manualproration;


import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ManualProrationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ManualProration ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	
    	ManualProrationForm form=(ManualProrationForm)invocationContext.screenModel;
    	setDefault(form);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	ManualProrationSession session=getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	//Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		session.setWeightRoundingVO(unitRoundingVO);		
		setUnitComponent(logonAttributes.getStationCode(),session);
		
    	
    	session.setProrationDetailsVO(null);
    	session.setProrationDetailVOs(null);
    	session.setPrimaryProrationVOs(null);
    	session.setSecondaryProrationVOs(null);
    	session.setProrationFilterVO(null);
    	form.setFlightDate(null);
    	form.setFlightCarrierIdentifier("");
    	
    	
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

	  /**
	   * method to set default value
	   */
  
		  private void setDefault(ManualProrationForm form){
		  	form.setTotalWtForPri("0.0");
		  	form.setTotalWtForSecon("0.0");
		  	
		  	form.setTotalInBasForPri("0.0");
		  	form.setTotalInBasForSec("0.0");
		  	form.setTotalInUsdForPri("0.0");
		  	form.setTotalInUsdForSec("0.0");
		  	form.setTotalInSdrForPri("0.0");
		  	form.setTotalInSdrForSec("0.0");
		  	form.setTotalInCurForPri("0.0");
		  	form.setTotalInCurForSec("0.0");
		  	
			}
		  
		  /**
			 * A-3251
			 * @param stationCode
			 * @param mailAcceptanceSession
			 * @return 
			 */
			private void setUnitComponent(String stationCode,
					ManualProrationSession session){
				UnitRoundingVO unitRoundingVO = null;
				try{
					log.log(Log.FINE, "station code is ----------->>",
							stationCode);
					unitRoundingVO = UnitFormatter.getStationDefaultUnit(
							stationCode, UnitConstants.WEIGHT);			
					log.log(Log.FINE, "unit vo for wt--in session---",
							unitRoundingVO);
					session.setWeightRoundingVO(unitRoundingVO);	
				   }catch(UnitException unitException) {
						unitException.getErrorCode();
				   }
				
			}

}
