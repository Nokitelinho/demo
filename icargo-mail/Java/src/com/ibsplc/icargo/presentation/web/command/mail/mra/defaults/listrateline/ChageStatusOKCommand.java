/*
 * ChageStatusOKCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author A-2270
 */
public class ChageStatusOKCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ChangeStatusCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";

	private static final String STATUS_NEW = "N";

	private static final String STATUS_ACT = "A";

	private static final String STATUS_INACT = "I";

	private static final String STATUS_EXP = "E";

	private static final String STATUS_CNCLD = "C";

	private static final String CHGSTSOK_SUCCESS = "statusok_success";

	private static final String CHGSTSOK_FAILURE = "statusok_failure";

	// private static final String KEY_NOT_VALID_NEW =
	// "mailtracking.mra.defaults.listupurate.notvalidfornew";

	// private static final String KEY_NOT_VALID_EXP =
	// "mailtracking.mra.defaults.listupurate.notvalidforexpired";

	// private static final String KEY_NOT_VALID_CNCL =
	// "mailtracking.mra.defaults.listupurate.notvalidforcancelled";

	private static final String KEY_IDENT_CNCL = "mailtracking.mra.defaults.listupurate.identicalcancel";

	private static final String KEY_IDEN_ACT = "mailtracking.mra.defaults.listupurate.identicalactive";

	private static final String KEY_IDEN_INACT = "mailtracking.mra.defaults.listupurate.identicalinactive";

	//
	private static final String KEY_EXPTOACT = "mailtracking.mra.defaults.listupurate.expiredtoactive";

	private static final String KEY_EXPTOINACT = "mailtracking.mra.defaults.listupurate.expiredtoinactive";

	private static final String KEY_EXPTOCNCLD = "mailtracking.mra.defaults.listupurate.expiredtocancelled";

	private static final String KEY_IDEN_EXP = "mailtracking.mra.defaults.listupurate.identicalexpired";

	//
	private static final String KEY_CNCLTOACT = "mailtracking.mra.defaults.listupurate.cancelledtoactive";

	private static final String KEY_CNCLTOINACT = "mailtracking.mra.defaults.listupurate.cancelledtoinactive";

	//
	private static final String KEY_NEWTOINACT = "mailtracking.mra.defaults.listupurate.newtoinactive";

	private static final String KEY_NEWTOCNCL = "mailtracking.mra.defaults.listupurate.newtocancelled";

	private static final String KEY_NEWTOEXP = "mailtracking.mra.defaults.listupurate.newtoexpired";

	private static final String KEY_IDEN_NEW = "mailtracking.mra.defaults.listupurate.newtonew";

	//

	// private static final String KEY_CHANGESTATUS_IDT =
	// "mailtracking.mra.defaults.listupurate.changestatussame";

	// private static final String KEY_NO_VALID_RATECARDS =
	// "mailtracking.mra.defaults.viewupurate.norecords";

	private static final String RATELINE_EXISTS = "mailtracking.mra.defaults.ratelinesexist";

	private static final String RATEEXISTERROR = "mailtracking.mra.defaults.ratelinesexist";

	/**
	 * Method implementing changing of ratecard status
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		boolean newFlag = false;
		boolean expFlag = false;
		boolean cndFlag = false;
		boolean actFlag = false;
		boolean inactFlag = false;

		String currentStatus = null;

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> finErrors = new ArrayList<ErrorVO>();

		MaintainUPURateCardForm maintainUPURateCardForm = (MaintainUPURateCardForm) invocationContext.screenModel;

		// ListUPURateLineForm listUPURateLineForm =
		// (ListUPURateLineForm)invocationContext.screenModel;

		ListUPURateLineSession listUPURateLineSession = (ListUPURateLineSession) getScreenSession(
				MODULE_NAME, SCREENID);

		listUPURateLineSession.removeErrorVOs();

		String toStatus = maintainUPURateCardForm.getPopupStatus();
		Page<RateLineVO> selRateLineVOs = listUPURateLineSession
				.getSelectedRateLineVOs();
		log.log(Log.INFO,
				"/n/n********in maintainUPURateCardForm --popupstatus********",
				maintainUPURateCardForm.getPopupStatus());
		for (RateLineVO rateLineVO : selRateLineVOs) {

			currentStatus = rateLineVO.getRatelineStatus();
			if (STATUS_ACT.equals(currentStatus) && !actFlag) {
				if (STATUS_ACT.equals(toStatus)) {
					log.log(Log.INFO,
							"trying to make active to Active Again!!!");
					actFlag = true;
					errors.add(new ErrorVO(KEY_IDEN_ACT));
				}
			} else if (STATUS_NEW.equals(currentStatus) && !newFlag) {
				if (STATUS_INACT.equals(toStatus)) {
					log.log(Log.INFO,
							"/n/n******** KEY_NOT_VALID_NEW *to INACTIVE");
					newFlag = true;
					errors.add(new ErrorVO(KEY_NEWTOINACT));
				} else if (STATUS_CNCLD.equals(toStatus)) {
					log
							.log(Log.INFO,
									"/n/n******** KEY_NOT_VALID_NEW ********to cancelled");
					newFlag = true;
					errors.add(new ErrorVO(KEY_NEWTOCNCL));
				} else if (STATUS_EXP.equals(toStatus)) {
					log
							.log(Log.INFO,
									"/n/n******** KEY_NOT_VALID_NEW ********to expired");
					newFlag = true;
					errors.add(new ErrorVO(KEY_NEWTOEXP));
				} else if (STATUS_NEW.equals(toStatus)) {
					newFlag = true;
					errors.add(new ErrorVO(KEY_IDEN_NEW));
				}
			} else if (STATUS_CNCLD.equals(currentStatus) && !cndFlag) {
				log
						.log(Log.INFO,
								"trying to make an Invalid Status Change for a rateLine of Status>>>.Cancelled");
				if (STATUS_ACT.equals(toStatus)) {
					log
							.log(Log.INFO,
									"trying to make an Invalid Status Change>cancelled to Active");
					cndFlag = true;
					errors.add(new ErrorVO(KEY_CNCLTOACT));
				} else if (STATUS_INACT.equals(toStatus)) {
					log.log(Log.INFO,
							"trying to change cncl  to inact again!!!!");
					cndFlag = true;
					errors.add(new ErrorVO(KEY_CNCLTOINACT));
				} else if (STATUS_CNCLD.equals(toStatus)) {
					log.log(Log.INFO,
							"trying to change cncl  to cncl again!!!!");
					cndFlag = true;
					errors.add(new ErrorVO(KEY_IDENT_CNCL));
				}
			} else if (STATUS_EXP.equals(currentStatus) && !expFlag) {
				if (STATUS_ACT.equals(toStatus)) {
					log
							.log(Log.INFO,
									"trying to change status of an Expired RateLine to Active!!!!");
					expFlag = true;
					errors.add(new ErrorVO(KEY_EXPTOACT));
				} else if (STATUS_CNCLD.equals(toStatus)) {
					log
							.log(Log.INFO,
									"trying to change status of an Expired RateLine to Cancelled!!!!");
					expFlag = true;
					errors.add(new ErrorVO(KEY_EXPTOCNCLD));
				} else if (STATUS_INACT.equals(toStatus)) {
					log
							.log(Log.INFO,
									"trying to change status of an Expired RateLine to Inactive!!!!");
					expFlag = true;
					errors.add(new ErrorVO(KEY_EXPTOINACT));
				} else if (STATUS_EXP.equals(toStatus)) {
					log
							.log(Log.INFO,
									"trying to change status of an Expired RateLine to Expired!!!!");
					expFlag = true;
					errors.add(new ErrorVO(KEY_IDEN_EXP));
				}
			} else if (STATUS_INACT.equals(currentStatus) && !inactFlag) {
				if (STATUS_INACT.equals(toStatus)) {
					log
							.log(Log.INFO,
									"trying to change status frm Inactive to inactive!!!!");
					inactFlag = true;
					errors.add(new ErrorVO(KEY_IDEN_INACT));
				}
			}
		}

		// listUPURateLineSession.setRateLineFilterVO(populateFilterVo(listUPURateLineForm));

		// boolean exptoact = false;
		// boolean exptocnd = false;
		// boolean exptoinact = false;
		// KEY_IDEN_EXP

		if (errors == null || errors.size() <= 0) {

			RateLineVO rateLineVOSave = null;
			Collection<RateLineVO> rateLineVoForSave = new ArrayList<RateLineVO>();
			log.log(Log.INFO, "inside errors NULL after validation in client");
			try {
				for (RateLineVO rateLineVO : selRateLineVOs) {

					rateLineVOSave = new RateLineVO();
					// try {
					BeanHelper.copyProperties(rateLineVOSave, rateLineVO);
					rateLineVOSave.setRatelineStatus(toStatus);
					rateLineVoForSave.add(rateLineVOSave);
					// } catch (SystemException e) {

					// }
					// rateLineVO.setRatelineStatus(toStatus);
				}
			} catch (SystemException e) {
				log.log(Log.FINE,  "Sys.Excptn ");
			}

			try {

				new MailTrackingMRADelegate()
						.saveRatelineStatus(rateLineVoForSave);

			} catch (BusinessDelegateException e) {
				log.log(Log.INFO, "inside catch of ratelineException>>Caught");
				errors = handleDelegateException(e);
			}
			if (errors != null && errors.size() > 0) {
				log.log(Log.INFO, "inside if in command");

				for (ErrorVO error : errors) {
					RateLineErrorVO[] rateLineErrorVos = new RateLineErrorVO[error
							.getErrorData().length];
					if (RATELINE_EXISTS.equals(error.getErrorCode())) {
						log
								.log(Log.INFO,
										"/n/n inside>>RATELINE_EXISTS.equals(error.getErrorCode()in command class");
						// for(Object o : error.getErrorData()) {
						// System.out.println(o.toString());
						// }

						// try{
						System.arraycopy(error.getErrorData(), 0,
								rateLineErrorVos, 0,
								error.getErrorData().length);
						// rateLineErrorVos = (RateLineErrorVO[])
						// (error.getErrorData());
						// System.out.println(rateLineErrorVos);
						// log.log(Log.INFO,"rateLineErrorVos"+rateLineErrorVos);
						for (RateLineErrorVO rateLineErrorVO : rateLineErrorVos) {
							// log.log(Log.INFO,"printing the
							// errorVOS>>>>>>>>>"+rateLineErrorVO);
							// log.log(Log.INFO,"NewRateCardID>>>>>>>>>"+rateLineErrorVO.getNewRateCardID());
							// log.log(Log.INFO,"origin>>>>>>>>>"+rateLineErrorVO.getOrigin());
							finErrors
									.add(new ErrorVO(
											RATEEXISTERROR,
											new String[] {
													rateLineErrorVO
															.getNewRateCardID(),
													rateLineErrorVO
															.getCurrentRateCardID(),
													rateLineErrorVO.getOrigin(),
													rateLineErrorVO
															.getDestination(),
													rateLineErrorVO
															.getCurrentValidityStartDate()
															.toDisplayDateOnlyFormat()
															.toString(),
													rateLineErrorVO
															.getCurrentValidityEndDate()
															.toDisplayDateOnlyFormat()
															.toString() }));
						}
						// }catch(ClassCastException castExcp){
						// log.log(Log.INFO,"inside Class Cast Exception");
//printStackTrrace()();
						//
						//
						// }
						log
								.log(Log.INFO,
										"before adding errors to the invocationContext ");

					}
				}

			} else {
				for (RateLineVO rateLineVO : selRateLineVOs) {
					log.log(Log.INFO, "\n********HERE IN LOOOP********");
					rateLineVO.setRatelineStatus(toStatus);
				}
			}
			invocationContext.addAllError(finErrors);
			invocationContext.target = CHGSTSOK_FAILURE;

		} else {
			log.log(Log.INFO,
					"\n********ERRORS AFTER SAVE IN COMMAND CLASS********");
			invocationContext.addAllError(errors);
			listUPURateLineSession.setErrorVOs((ArrayList<ErrorVO>) errors);
			log.log(Log.INFO, "/n/n******** errors.size()**",
					listUPURateLineSession.getErrorVOs().size());
			invocationContext.target = CHGSTSOK_SUCCESS;
		}
		log.log(Log.INFO, "/n/n******** details vos size********",
				selRateLineVOs.size());
		// resetFlags();
		log.exiting(CLASS_NAME, "execute");

		return;

	}
	/*
	 * private RateLineFilterVO populateFilterVo(ListUPURateLineForm
	 * listUPURateLineForm){ RateLineFilterVO rateLineFilterVO = new
	 * RateLineFilterVO();
	 * 
	 * rateLineFilterVO.setCompanyCode(listUPURateLineForm.getCompanyCode());
	 * rateLineFilterVO.setRatelineStatus(listUPURateLineForm.getStatus());
	 * rateLineFilterVO.setStartDate(convertToDate(listUPURateLineForm.getFromDate()));
	 * rateLineFilterVO.setEndDate(convertToDate(listUPURateLineForm.getToDate()));
	 * rateLineFilterVO.setRateCardID(listUPURateLineForm.getRateCardID());
	 * rateLineFilterVO.setOrigin(listUPURateLineForm.getOrigin());
	 * rateLineFilterVO.setDestination(listUPURateLineForm.getDestination());
	 * 
	 * return rateLineFilterVO; }
	 * 
	 */

	/**
	 * 
	 * @param date
	 * @return LocalDate
	 */
	/*
	 * private LocalDate convertToDate(String date){
	 * 
	 * if(date!=null && !date.equals("")){
	 * 
	 * return(new LocalDate (LocalDate.NO_STATION,Location.NONE,false).setDate(
	 * date )); } return null; }
	 */

	/**
	 * Checks the validity of change for the sotatus
	 * 
	 * @param currentStatus
	 * @param toStatus
	 * @param rateCardId
	 * @return ErrorVO
	 */
	// private ErrorVO checkValidity(String rateCardId,
	// String currentStatus, String toStatus) {
	//
	// if(STATUS_NEW.equals(currentStatus) ){
	//
	// if(STATUS_INACT.equals(toStatus) || STATUS_CNCLD.equals(toStatus)){
	// log.log(Log.INFO,"/n/n******** KEY_NOT_VALID_NEW ********"+toStatus);
	// return new ErrorVO(KEY_NOT_VALID_NEW);
	// }
	// }else if(STATUS_CNCLD.equals(currentStatus)){
	// log.log(Log.INFO,"trying to make an Invalid Status Change for a rateLine
	// of Status>>>.Cancelled");
	// if(STATUS_ACT.equals(toStatus)
	// || STATUS_CNCLD.equals(toStatus)
	// || STATUS_INACT.equals(toStatus)){
	//
	// return new ErrorVO(KEY_NOT_VALID_CNCL, new String[]{rateCardId});
	//			}
	//		}else if(STATUS_EXP.equals(currentStatus)){
	//
	//			if(STATUS_ACT.equals(toStatus)
	//					|| STATUS_CNCLD.equals(toStatus)
	//					|| STATUS_INACT.equals(toStatus)){
	//
	//				return new ErrorVO(KEY_NOT_VALID_EXP, new String[]{rateCardId});
	//			}
	//		}
	//
	//		return null;
	//	}
}
