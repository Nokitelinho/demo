
	/*
	 * ClearCommand.java Created on Aug 6,2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.manualproration;

	import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ManualProrationForm;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author A-3229
	 * Command class for clearing manualproration screen  
	 *
	 * Revision History
	 *
	 * Version      Date           Author          		    Description
	 *
	 *  0.1         Aug 6,2008     A-3229					Initial draft
	 */
	public class ClearCommand extends BaseCommand {
		/**
		 * Logger and the file name
		 */
		
		private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS MANUAL PRORATION");
		
		private static final String CLASS_NAME = "ClearCommand";
		
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";
		
		
		private static final String CLEAR_SUCCESS = "clear_success";
		

		/**
		 * Execute method
		 *
		 * @param invocationContext InvocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
												throws CommandInvocationException {
			log.entering(CLASS_NAME, "execute");
			
			ManualProrationSession  manualProrationSession = 
				(ManualProrationSession)getScreenSession(MODULE_NAME, SCREEN_ID);
			ManualProrationForm manualProrationForm=(ManualProrationForm)invocationContext.screenModel;
			manualProrationSession.removeProrationFilterVO();
			manualProrationSession.removeProrationDetailVOs();
			manualProrationSession.removeProrationDetailsVO();
			manualProrationSession.removePrimaryProrationVOs();
			manualProrationSession.removeSecondaryProrationVOs();
			manualProrationForm.setDespatchSerialNumber("");
			manualProrationForm.setConsigneeDocumentNumber("");
			manualProrationForm.setFlightCarrierIdentifier("");
			manualProrationForm.setFlightNumber("");
			manualProrationForm.setFlightDate("");
			manualProrationForm.setOriginExchangeOffice("");
			manualProrationForm.setDestinationExchangeOffice("");
			manualProrationForm.setPostalAuthorityCode("");
			manualProrationForm.setPostalAuthorityName("");
			manualProrationForm.setTotalWeight("");
			manualProrationForm.setMailCategoryCode("");
			manualProrationForm.setMailSubclass("");
			
			manualProrationForm.setTotalInBasForPri("");
			manualProrationForm.setTotalInBasForSec("");
			manualProrationForm.setTotalInCurForPri("");
			manualProrationForm.setTotalInCurForSec("");
			manualProrationForm.setTotalInSdrForPri("");
			manualProrationForm.setTotalInSdrForSec("");
			manualProrationForm.setTotalInUsdForPri("");
			manualProrationForm.setTotalInUsdForSec("");
			manualProrationForm.setTotalInBasForPrimary("");
			manualProrationForm.setTotalInBasForSecon("");
			manualProrationForm.setTotalInCurForPrimary("");
			manualProrationForm.setTotalInCurForSecon("");
			manualProrationForm.setTotalInSdrForPrimary("");
			manualProrationForm.setTotalInSdrForSecon("");
			manualProrationForm.setTotalInUsdForPrimary("");
			manualProrationForm.setTotalInUsdForSecon("");
			manualProrationForm.setPrimaryWeight("");
			manualProrationForm.setSecondaryWeight("");
			manualProrationForm.setCarrierCodeForSec(null);
			manualProrationForm.setCarrierCodeForPri(null);
			manualProrationForm.setProrateFlag("");
			manualProrationForm.setFromScreen("");
			manualProrationSession.setOneTimeVOs(null);
			manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			
			
			invocationContext.target = CLEAR_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
		}
	}

