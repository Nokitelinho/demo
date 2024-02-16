/*
 * ChageStatusOKCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-1556
 * @author A-2521
 */
public class ChageStatusOKCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ChageStatusOKCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

	private static final String STATUS_NEW = "N";

	private static final String STATUS_ACT = "A";

	private static final String STATUS_INACT = "I";

	private static final String STATUS_EXP = "E";

	private static final String STATUS_CNCLD = "C";

	private static final String CHGSTSOK_SUCCESS = "statusok_success";

	private static final String CHGSTSOK_FAILURE = "statusok_failure";

	private static final String KEY_NOT_VALID_NEW = "mailtracking.mra.defaults.listupuratecard.notvalidfornew";

	private static final String KEY_NOT_VALID_CNCL = "mailtracking.mra.defaults.listupuratecard.notvalidforcancelled";

	private static final String KEY_NOT_VALID_EXP = "mailtracking.mra.defaults.listupuratecard.notvalidforexpired";

	private static final String KEY_CHANGESTATUS_IDT = "mailtracking.mra.defaults.listupuratecard.changestatussame";
//
//	private static final String KEY_NO_VALID_RATECARDS = "mailtracking.mra.defaults.listupuratecard.norecords";

	private static final String RATELINE_EXISTS="mailtracking.mra.defaults.ratelinesexist";

	private static final String BLANK = "";

//	private static boolean NEW_FLAG = false;
//
//	private static boolean EXP_FLAG = false;
//
//	private static boolean CND_FLAG = false;

	/**
	 * Method  implementing changing of ratecard status
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");
		
		boolean newFlag = false;
		boolean expFlag = false;
		boolean cndFlag = false;
		boolean idntFlag = false;
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		MaintainUPURateCardForm maintainUPURateCardForm
		= (MaintainUPURateCardForm)invocationContext.screenModel;

		ListUPURateCardSession listUPURateCardSession =
			(ListUPURateCardSession)getScreenSession(MODULE_NAME, SCREENID);

		String toStatus = maintainUPURateCardForm.getPopupStatus();

		Page<RateCardVO> selRateCardVOs =  listUPURateCardSession.getSelectedRateCardVOs();
		Page<RateCardVO> saveRateCardVOs =  new Page<RateCardVO>(
				new ArrayList<RateCardVO>(),0,0,0,0,0,false);

		listUPURateCardSession.removeErrorVOs();
		
		if(toStatus == null || BLANK.equals(toStatus)){
			
			invocationContext.target = CHGSTSOK_FAILURE;
			return;
		}

		String currentStatus = null; 
		
		for(RateCardVO rateCardVO : selRateCardVOs){

			log.log(Log.INFO, "/n/n******** rateCardVO ********", rateCardVO);
			//err = checkValidity(rateCardVO.getRateCardStatus(), toStatus);
			currentStatus = rateCardVO.getRateCardStatus();
			
			if(toStatus.equals(currentStatus) && !idntFlag){
				
				idntFlag = true;
				errors.add(new ErrorVO(KEY_CHANGESTATUS_IDT));
				
			}else if(STATUS_NEW.equals(currentStatus) && !newFlag ){

				if(STATUS_INACT.equals(toStatus) || STATUS_CNCLD.equals(toStatus)){

					newFlag = true;
					errors.add(new ErrorVO(KEY_NOT_VALID_NEW));
					
				}
			}else if(STATUS_CNCLD.equals(currentStatus) && !cndFlag){

				if(STATUS_ACT.equals(toStatus)
						|| STATUS_CNCLD.equals(toStatus)
						|| STATUS_INACT.equals(toStatus)){

					cndFlag = true;
					errors.add(new ErrorVO(KEY_NOT_VALID_CNCL));
				}
			}else if(STATUS_EXP.equals(currentStatus) && !expFlag){

				if(STATUS_ACT.equals(toStatus)
						|| STATUS_CNCLD.equals(toStatus)
						|| STATUS_INACT.equals(toStatus)){

					expFlag = true;
					errors.add(new ErrorVO(KEY_NOT_VALID_EXP));
				}
			}
			
		}

		if(errors == null || errors.size() <= 0){

			RateCardVO rateCardVOSave = null;

			try {
				
				for(RateCardVO rateCardVO : selRateCardVOs){
					
					rateCardVOSave = new RateCardVO();
										
					BeanHelper.copyProperties(rateCardVOSave, rateCardVO);
										
					rateCardVOSave.setRateCardStatus(toStatus);
					rateCardVOSave.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
					saveRateCardVOs.add(rateCardVOSave);
				}
				
			} catch (SystemException e) {
				e.getMessage();
			}

			try {

				new MailTrackingMRADelegate().changeRateCardStatus(saveRateCardVOs);

				// setting rate card status of ratecard vos to changed status for displaying in screen
				for(RateCardVO rateCardVO : selRateCardVOs){
						rateCardVO.setRateCardStatus(toStatus);
				}

				invocationContext.target = CHGSTSOK_SUCCESS;

			} catch (BusinessDelegateException e) {

				errors = handleDelegateException(e);
				errors = handleErrorMessage(errors);	//handles error data from server
				listUPURateCardSession.setErrorVOs((ArrayList<ErrorVO>) errors);
				invocationContext.target = CHGSTSOK_FAILURE;
			}
		}else{

			listUPURateCardSession.setErrorVOs((ArrayList<ErrorVO>) errors);
			log.log(Log.INFO, "/n/n******** errors.size()**",
					listUPURateCardSession.getErrorVOs().size());
			invocationContext.target = CHGSTSOK_FAILURE;
		}


		log.log(Log.INFO, "/n/n******** details vos size********",
				selRateCardVOs.size());
		log.exiting(CLASS_NAME,"execute");
		return;
	}

	/**
	 * Creates errorvo collection to be displayed in screen
	 *
	 * @param errors
	 * @return finErrors
	 */
	private Collection<ErrorVO> handleErrorMessage(
			Collection<ErrorVO> errors){

		log.entering(CLASS_NAME,"handleErrorMessage");

		Collection<ErrorVO> finErrors = new ArrayList<ErrorVO>();

		if(errors != null && errors.size() > 0){

			for(ErrorVO error:errors){

				RateLineErrorVO[] rateLineErrorVos =
					new RateLineErrorVO[error.getErrorData() == null ? 0: error.getErrorData().length];

				if(RATELINE_EXISTS.equals(error.getErrorCode())){

					System.arraycopy(error.getErrorData(),0,
							rateLineErrorVos,0,error.getErrorData().length);

					for(RateLineErrorVO rateLineErrorVO :rateLineErrorVos){

						log.log(Log.INFO, "printing the errorVOS>>>>>>>>>",
								rateLineErrorVO);
						finErrors.add(new ErrorVO(RATELINE_EXISTS,
								new String[]{rateLineErrorVO.getNewRateCardID(),
								rateLineErrorVO.getCurrentRateCardID(),
								rateLineErrorVO.getOrigin(),
								rateLineErrorVO.getDestination(),
								rateLineErrorVO.getCurrentValidityStartDate().toDisplayFormat(),
								rateLineErrorVO.getCurrentValidityEndDate().toDisplayFormat()}
						));
					}
				}
			}
		}

		log.exiting(CLASS_NAME,"handleErrorMessage");
		return finErrors;
	}
}
