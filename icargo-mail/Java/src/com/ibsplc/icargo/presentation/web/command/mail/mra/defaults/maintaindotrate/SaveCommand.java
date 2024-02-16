/*
 * SaveCommand.java Created on Aug 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaindotrate;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainDOTRateSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainDOTRateForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * 
 */
public class SaveCommand extends BaseCommand {
	/**
	 * String for CLASS_NAME
	 */
	private static final String CLASS_NAME = "save command";

	/**
	 * Log for AddCommand
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING DEFAULTS");

	private static final String BLANK = "";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaindotrate";

	private static final String ACTION_SUCCESS = "screenload_success";

	private static final String SAVE_FAILURE = "screenload_failure";

	private static final String NO_DATA_FOR_SAVE = "mailtracking.mra.defaults.maintaindotrate.nodataforsave";

	private static final String SECTOR_ORIGIN_MANDATORY = "mailtracking.mra.defaults.maintaindotrate.originmandatory";

	private static final String SECTOR_DST_MANDATORY = "mailtracking.mra.defaults.maintaindotrate.dstmandatory";

	private static final String SECTOR_GCM_MANDATORY = "mailtracking.mra.defaults.maintaindotrate.gcmmandatory";

	private static final String SECTOR_RATE_MANDATORY = "mailtracking.mra.defaults.maintaindotrate.ratemandatory";

	private static final String SAVE_SUCCESS = "mailtracking.mra.defaults.maintaindotrate.savesuccess";

	private static final String INVALID_RATE_TRANS = "mailtracking.mra.defaults.maintaindotrate.invalidratefortransborder";

	private static final String INVALID_RATE = "mailtracking.mra.defaults.maintaindotrate.invalidrate";

	/**
	 * Method execute
	 * 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MaintainDOTRateForm maintainDOTRateForm = (MaintainDOTRateForm) invocationContext.screenModel;
		MaintainDOTRateSession session = null;
		session = (MaintainDOTRateSession) getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ErrorVO errorVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ArrayList<MailDOTRateVO> mainDOTRateVOs = new ArrayList<MailDOTRateVO>();
		String[] operationFlags = maintainDOTRateForm.getOperationFlag();
		String[] segOrigin = maintainDOTRateForm.getOriginCode();
		String[] segDest = maintainDOTRateForm.getDestinationCode();
		String[] gcm = maintainDOTRateForm.getCircleMiles();
		String[] regionCode = maintainDOTRateForm.getRegionCode();
		String[] rateCode = maintainDOTRateForm.getRateCode();
		// String[] rateDes=maintainDOTRateForm.getRateDescription();
		String[] lhrate = maintainDOTRateForm.getLineHaulRate();
		String[] thrate = maintainDOTRateForm.getTerminalHandlingRate();
		String[] dotrate = maintainDOTRateForm.getDotRate();
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		boolean hasUpdated = false;
		boolean hasChanged = false;
		ArrayList<MailDOTRateVO> oldMailDOTRateVOs = session
				.getMailDOTRateVOs();
		if (operationFlags != null) {

			int index = 0;

			for (String oprFlag : operationFlags) {
				MailDOTRateVO mailDOTRateVO = new MailDOTRateVO();

				if (OPERATION_FLAG_INSERT.equals(oprFlag)
						|| OPERATION_FLAG_DELETE.equals(oprFlag)) {

					mailDOTRateVO.setOperationFlag(oprFlag);
					hasChanged = true;
				} else if (!("NOOP".equals(oprFlag))) {
					if (!(segOrigin[index].equals(oldMailDOTRateVOs.get(index)
							.getOriginCode()))) {
						log.log(Log.INFO, "change1");
						hasUpdated = true;
					}
					if (!(segDest[index].equals(oldMailDOTRateVOs.get(index)
							.getDestinationCode()))) {
						log.log(Log.INFO, "change2");
						hasUpdated = true;
					}
					if (gcm[index] != null && gcm[index].trim().length() > 0) {
						if (oldMailDOTRateVOs.get(index).getCircleMiles() != Double
								.parseDouble(gcm[index])) {
							log.log(Log.INFO, "change3");
							hasUpdated = true;
						}
					}
					if (!(regionCode[index].equals(oldMailDOTRateVOs.get(index)
							.getRegionCode()))) {
						log.log(Log.INFO, "change4");
						hasUpdated = true;
					}
					if (!(rateCode[index].equals(oldMailDOTRateVOs.get(index)
							.getRateCode()))) {
						log.log(Log.INFO, "change5");
						hasUpdated = true;
					}
					/*
					 * if(!(rateDes[index].equals(oldMailDOTRateVOs.get(index).getRateDescription()))){
					 * hasUpdated=true; }
					 */
					if (lhrate[index] != null
							&& lhrate[index].trim().length() > 0) {
						if (oldMailDOTRateVOs.get(index).getLineHaulRate() != Double
								.parseDouble(lhrate[index])) {
							log.log(Log.INFO, "change6");
							hasUpdated = true;
						}
					}
					
					if (thrate[index] != null
							&& thrate[index].trim().length() > 0) {
						if (oldMailDOTRateVOs.get(index)
								.getTerminalHandlingRate() != Double
								.parseDouble(thrate[index])) {
							log.log(Log.INFO, "change7");
							hasUpdated = true;
						}
					}
					if (dotrate[index] != null
							&& dotrate[index].trim().length() > 0) {
						if (oldMailDOTRateVOs.get(index).getDotRate() != Double
								.parseDouble(dotrate[index])) {
							log.log(Log.INFO, "change8");
							hasUpdated = true;
						}
					}
					if (hasUpdated) {
						mailDOTRateVO.setOperationFlag(OPERATION_FLAG_UPDATE);
					}
				}

				if (!("NOOP".equals(oprFlag))) {
					mailDOTRateVO.setCompanyCode(companyCode);

					if (!(BLANK.equals(segOrigin[index]))) {
						log.log(Log.INFO, "sector origin-->", segOrigin, index);
						mailDOTRateVO.setOriginCode(segOrigin[index]);
					} else {
						log.log(Log.INFO, "sector null origin-->", segOrigin,
								index);
						errorVO = new ErrorVO(SECTOR_ORIGIN_MANDATORY);
						errors.add(errorVO);
					}
					if (!(BLANK.equals(segDest[index]))) {
						mailDOTRateVO.setDestinationCode(segDest[index]);
					} else {
						errorVO = new ErrorVO(SECTOR_DST_MANDATORY);
						errors.add(errorVO);
					}
					if (!(BLANK.equals(gcm[index]))) {
						if (Double.parseDouble(gcm[index]) > 0) {
							mailDOTRateVO.setCircleMiles(Double
									.parseDouble(gcm[index]));
						} else {
							errorVO = new ErrorVO(SECTOR_GCM_MANDATORY);
							errors.add(errorVO);
						}
					} else {
						errorVO = new ErrorVO(SECTOR_GCM_MANDATORY);
						errors.add(errorVO);
					}
					if (!(BLANK.equals(regionCode[index]))) {
						mailDOTRateVO.setRegionCode(regionCode[index]);
					}
					if (!(BLANK.equals(rateCode[index]))) {
						mailDOTRateVO.setRateCode(rateCode[index]);
						mailDOTRateVO.setRateDescription(rateCode[index]);
					} else {
						errorVO = new ErrorVO(SECTOR_RATE_MANDATORY);
						errors.add(errorVO);
					}

					if (!(BLANK.equals(lhrate[index]))) {
						mailDOTRateVO.setLineHaulRate(Double
								.parseDouble(lhrate[index]));
					}
					if (!(BLANK.equals(thrate[index]))) {
						mailDOTRateVO.setTerminalHandlingRate(Double
								.parseDouble(thrate[index]));
					}
					if (!(BLANK.equals(dotrate[index]))) {
						mailDOTRateVO.setDotRate(Double
								.parseDouble(dotrate[index]));
					}

					mainDOTRateVOs.add(mailDOTRateVO);
					++index;

				}

			}
			if (errors != null && errors.size() > 0) {
				session.setMailDOTRateVOs(mainDOTRateVOs);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				log.exiting(CLASS_NAME, "execute");
				return;
			}

			errors = validateVOs(mainDOTRateVOs);

		}
		log.log(Log.INFO, "changed falg --hasUpdated", hasUpdated);
		log.log(Log.INFO, "changed falg --hasChanged", hasChanged);
		if (!(hasUpdated) && !(hasChanged)) {
			errorVO = new ErrorVO(NO_DATA_FOR_SAVE);
			errors.add(errorVO);

		}
		if (errors != null && errors.size() > 0) {
			session.setMailDOTRateVOs(mainDOTRateVOs);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			log.exiting(CLASS_NAME, "execute");
			return;
		} else {
			try {
				log.log(Log.INFO, "vo for save--->", mainDOTRateVOs);
				new MailTrackingMRADelegate()
						.saveDOTRateDetails(mainDOTRateVOs);
				session.setMailDOTRateVOs(null);
				maintainDOTRateForm.setGreatCircleMiles(BLANK);
				maintainDOTRateForm.setRateCodeFilter(BLANK);
				maintainDOTRateForm.setScreenFlag("screenload");
				maintainDOTRateForm.setSectorOriginCode(BLANK);
				maintainDOTRateForm.setSectorDestinationCode(BLANK);
				errorVO = new ErrorVO(SAVE_SUCCESS);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_SUCCESS;
				log.exiting(CLASS_NAME, "execute");
			} catch (BusinessDelegateException e) {
				e.getMessage();
				handleDelegateException(e);
			}
		}
	}

	/**
	 * @param mailVOs
	 * @return
	 */
	private Collection<ErrorVO> validateVOs(Collection<MailDOTRateVO> mailVOs) {
		MaintainDOTRateSession session = null;
		session = (MaintainDOTRateSession) getScreenSession(MODULE_NAME,
				SCREEN_ID);
		Collection<ErrorVO> orgerrors = null;
		Collection<ErrorVO> desterrors = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ArrayList<String> checkorigins = new ArrayList<String>();
		ArrayList<String> checkdests = new ArrayList<String>();
		int index = 0;
		if (mailVOs != null && mailVOs.size() > 0) {
			for (MailDOTRateVO org : mailVOs) {
				if (index != mailVOs.size()
						&& !(OPERATION_FLAG_DELETE.equals(org
								.getOperationFlag()))) {

					if (!checkorigins.contains(org.getOriginCode())) {
						checkorigins.add(org.getOriginCode());
					}
					if (!checkdests.contains(org.getDestinationCode())) {
						checkdests.add(org.getDestinationCode());
					}
					if (org.getRegionCode() != null
							&& org.getRegionCode().trim().length() > 0) {
						if ("B".equals(org.getRegionCode())) {
							if ("PRI".equals(org.getRateCode())
									|| "SAM".equals(org.getRateCode())) {
								errors.add(new ErrorVO(INVALID_RATE_TRANS));
							}
						} else {
							if ("S".equals(org.getRateCode())
									|| "SD".equals(org.getRateCode())
									|| "D".equals(org.getRateCode())) {
								String reg = "";
								Map oneTimeHashMap = session.getOneTimeVOs();
								if (oneTimeHashMap != null) {
									// System.out.println("one times
									// map---->"+oneTimeHashMap);
									Collection<OneTimeVO> regions = (Collection<OneTimeVO>) oneTimeHashMap
											.get("mailtracking.mra.defaults.regioncode");
									// System.out.println("one
									// times---->"+regions);
									if (regions != null && regions.size() > 0) {
										for (OneTimeVO vo : regions) {
											if (vo.getFieldValue().equals(
													org.getRegionCode())) {
												reg = vo.getFieldDescription();
											}
										}
									}
								}
								Object[] errorData = { reg };
								errors
										.add(new ErrorVO(INVALID_RATE,
												errorData));
							}
						}
					}

				}
				++index;
			}
			log.log(Log.INFO, "origin s-->", checkorigins);
			try {
				new AreaDelegate().validateStationCodes(getApplicationSession()
						.getLogonVO().getCompanyCode(), checkorigins);
			} catch (BusinessDelegateException e) {
				orgerrors = handleDelegateException(e);
			}
			if (orgerrors != null && orgerrors.size() > 0) {
				StringBuilder codeArray = new StringBuilder();
				String errorString = "";
				for (ErrorVO error : orgerrors) {
					log.log(Log.INFO, "ErrorVO---->>>", error);
					if (("shared.station.invalidstation").equals(error.getErrorCode())
							) {
						Object[] codes = error.getErrorData();
						for (int count = 0; count < codes.length; count++) {
							if (("").equals(errorString)) {
								errorString = String.valueOf(codes[count]);
								codeArray.append(errorString);
							} else {
								errorString = codeArray.append(",").append(
										String.valueOf(codes[count]))
										.toString();
							}
							log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
							log.log(Log.FINE, "\n\n\nErrorString-->",
									errorString);
						}
						Object[] errorArray = { errorString };
						ErrorVO errorVO = new ErrorVO(
								"mra.airlinebilling.defaults.maintaindotrate.msg.err.invalidorigin",
								errorArray);
						errors.add(errorVO);
					}
				}
			}
			log.log(Log.INFO, "destinations s-->", checkdests);
			try {
				new AreaDelegate().validateStationCodes(getApplicationSession()
						.getLogonVO().getCompanyCode(), checkdests);
			} catch (BusinessDelegateException e) {
				desterrors = handleDelegateException(e);
			}
			if (desterrors != null && desterrors.size() > 0) {
				StringBuilder codeArray = new StringBuilder();
				String errorString = "";
				for (ErrorVO error : desterrors) {
					log.log(Log.INFO, "ErrorVO---->>>", error);
					if (("shared.station.invalidstation").equals(error.getErrorCode()))
							 {
						Object[] codes = error.getErrorData();
						for (int count = 0; count < codes.length; count++) {
							if (("").equals(errorString)) {
								errorString = String.valueOf(codes[count]);
								codeArray.append(errorString);
							} else {
								errorString = codeArray.append(",").append(
										String.valueOf(codes[count]))
										.toString();
							}
							log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
							log.log(Log.FINE, "\n\n\nErrorString-->",
									errorString);
						}
						Object[] errorArray = { errorString };
						ErrorVO errorVO = new ErrorVO(
								"mra.airlinebilling.defaults.maintaindotrate.msg.err.invaliddestination",
								errorArray);
						errors.add(errorVO);
					}
				}
			}

		}
		return errors;
	}

}
