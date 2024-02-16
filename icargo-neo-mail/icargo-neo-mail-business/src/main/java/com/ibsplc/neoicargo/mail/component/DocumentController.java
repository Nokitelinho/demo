package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.formatter.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.ebl.ICargoDomainTypeSupport;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload.SaveConsignmentUploadFeature;
import com.ibsplc.neoicargo.mail.component.proxy.MailMRAProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** 
 * @author a-1883
 */
@Component
@Slf4j
public class DocumentController {
	@Autowired
	private Quantities quantities;
	@Autowired
	private MailMRAProxy mailtrackingMRAProxy;
	@Autowired
	private SharedAreaProxy sharedAreaProxy;
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	@Autowired
	private ContextUtil contextUtil;

	private static final String CONSIGNMENT_REPORT_ID = "RPTOPR046";
	private static final String AV7_REPORT_ID = "RPTMTK052";
	private static final String HYPHEN = "-";
	private static final String IMPORT_TRIGGERPOINT = "mailtracking.mra.triggerforimport";
	private static final String CONSIGNMENTCAPTURE = "C";
	private static final String CSGDOCNUM_GEN_KEY = "CSGDOCNUM_GEN_KEY";
	private static final String IMPORTTRIGGER_DELIVERY = "D";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";
	private static final String MODULENAME = "mail.operations";
	private static final String DOCUMENT_CONTROLLER = "DocumentController";

	/** 
	* @author a-1883
	* @param consignmentDocumentVO
	* @return Integer
	* @throws SystemException
	* @throws MailbagAlreadyAcceptedException
	* @throws InvalidMailTagFormatException
	* @throws DuplicateDSNException
	* @throws DuplicateMailBagsException 
	*/
	public Integer saveConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO)
			throws MailbagAlreadyAcceptedException, InvalidMailTagFormatException, DuplicateDSNException,
			DuplicateMailBagsException {
		log.debug("DocumentController" + " : " + "saveConsignmentDocument" + " Entering");
		Integer consignmentSeqNumber = null;
		String triggerPoint = "";
		log.debug("" + " OPERATION FLAG :" + " " + consignmentDocumentVO.getOperationFlag());
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonVO = contextUtil.callerLoginProfile();
		consignmentDocumentVO.setLastUpdateUser(logonVO.getUserId());
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		try {
			triggerPoint = mailController.findSystemParameterValue(IMPORT_TRIGGERPOINT);
		} finally {
		}
		validateMailBags((Collection<MailInConsignmentVO>) consignmentDocumentVO.getMailInConsignmentVOs(),
				consignmentDocumentVO.isScanned());
		checkDSNForAcceptedMailAndDespatch(consignmentDocumentVO);
		mailController.saveConsignmentDetails(consignmentDocumentVO);
		if (ConsignmentDocumentVO.OPERATION_FLAG_INSERT.equals(consignmentDocumentVO.getOperationFlag())) {
			if (consignmentDocumentVO.getMailInConsignmentVOs() != null
					&& !consignmentDocumentVO.getMailInConsignmentVOs().isEmpty()) {
				for (MailInConsignmentVO mailvo : consignmentDocumentVO.getMailInConsignmentVOs()) {
					if (mailvo.getMailSource() == null) {
						mailvo.setMailSource(MailConstantsVO.CARDIT_PROCESS);
					}
					if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailvo.getOperationFlag())) {
						double actualVolume = 0.0;
						double stationVolume = 0.0;
						String commodityCode = "";
						String stationVolumeUnit = "";
						CommodityValidationVO commodityValidationVO = null;
						try {
							commodityCode = mailController
									.findSystemParameterValue("mailtracking.defaults.booking.commodity");
							commodityValidationVO = mailController.validateCommodity(mailvo.getCompanyCode(),
									commodityCode, consignmentDocumentVO.getPaCode());
						} finally {
						}
						if (commodityValidationVO != null && commodityValidationVO.getDensityFactor() != 0) {
							Map stationParameters = null;
							String stationCode = logonVO.getStationCode();
							Collection<String> parameterCodes = new ArrayList<String>();
							parameterCodes.add(STNPAR_DEFUNIT_VOL);
							try {
								stationParameters =neoMastersServiceUtils.findStationParametersByCode(logonVO.getCompanyCode(),
									stationCode, parameterCodes);
							} catch (BusinessException e) {
								throw new RuntimeException(e);
							}

							stationVolumeUnit = (String) stationParameters.get(STNPAR_DEFUNIT_VOL);
							if (mailvo.getStatedWeight() != null) {
								double weightInKg = unitConvertion(UnitConstants.MAIL_WGT,
										mailvo.getStatedWeight().getUnit().getName(),
										UnitConstants.WEIGHT_UNIT_KILOGRAM,
										mailvo.getStatedWeight().getValue().doubleValue());
								actualVolume = (weightInKg / (commodityValidationVO.getDensityFactor()));
								stationVolume = unitConvertion(UnitConstants.VOLUME,
										UnitConstants.VOLUME_UNIT_CUBIC_METERS, stationVolumeUnit, actualVolume);
								log.info("" + "inside volume calculation for mailbags***:>>>" + " " + actualVolume);
								if (stationVolume < 0.01) {
									stationVolume = 0.01;
								}
							}
						}
						if (stationVolumeUnit != null) {
							mailvo.setVolume(quantities.getQuantity(Quantities.VOLUME,
									BigDecimal.valueOf(stationVolume), BigDecimal.valueOf(0.0), stationVolumeUnit));
						}
					}
				}
			}
			ConsignmentDocument consignmentDocument = new ConsignmentDocument(consignmentDocumentVO);
			consignmentSeqNumber = Integer
					.valueOf(consignmentDocument.getConsignmentSequenceNumber());
			consignmentDocumentVO.setConsignmentSequenceNumber(consignmentSeqNumber.intValue());
		} else if (ConsignmentDocumentVO.OPERATION_FLAG_UPDATE.equals(consignmentDocumentVO.getOperationFlag())) {
			ConsignmentDocument consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
			consignmentDocument.update(consignmentDocumentVO);
			modifyChildren(consignmentDocumentVO, consignmentDocument);
			consignmentSeqNumber = Integer
					.valueOf(consignmentDocument.getConsignmentSequenceNumber());
		} else if (ConsignmentDocumentVO.OPERATION_FLAG_DELETE.equals(consignmentDocumentVO.getOperationFlag())) {
			ConsignmentDocument consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
			log.error(new StringBuilder("This code executed from delete consignment command_2, csgdocnum ")
					.append(consignmentDocumentVO.getConsignmentNumber()).append(" CSGSEQNUM ")
					.append(consignmentDocumentVO.getConsignmentSequenceNumber()).toString());
			if (consignmentDocument != null) {
				checkDespatchesAlreadyAccepted(
						(Collection<MailInConsignmentVO>) consignmentDocumentVO.getMailInConsignmentVOs());
				if (consignmentDocument.getRoutingsInConsignments() != null
						&& consignmentDocument.getRoutingsInConsignments().size() > 0) {
					RoutingInConsignment routingInConsignment = null;
					Iterator<RoutingInConsignment> routingInConsignmentIterator = consignmentDocument
							.getRoutingsInConsignments().iterator();
					while (routingInConsignmentIterator.hasNext()) {
						routingInConsignment = routingInConsignmentIterator.next();
						routingInConsignment.remove();
						routingInConsignmentIterator.remove();
					}
				}
				if (consignmentDocument.getMailsInConsignments() != null
						&& consignmentDocument.getMailsInConsignments().size() > 0) {
					MailInConsignment mailInConsignment = null;
					Iterator<MailInConsignment> mailInConsignmentIterator = consignmentDocument.getMailsInConsignments()
							.iterator();
					while (mailInConsignmentIterator.hasNext()) {
						mailInConsignment = mailInConsignmentIterator.next();
						mailInConsignment.remove();
						mailInConsignmentIterator.remove();
					}
				}
				consignmentDocument.remove();
			}
		}
		if (isMraImportRequired(consignmentDocumentVO, triggerPoint)) {
			importConsignmentDataToMra(consignmentDocumentVO);
		}
		log.debug("DocumentController" + " : " + "saveConsignmentDocument" + " Exiting");
		return consignmentSeqNumber;
	}

	public boolean isMraImportRequired(ConsignmentDocumentVO consignmentDocumentVO, String triggerPoint) {
		boolean isMraImportRequired = false;
		if ((triggerPoint.contains(CONSIGNMENTCAPTURE) || triggerPoint.contains(IMPORTTRIGGER_DELIVERY))
				&& (consignmentDocumentVO.getMailInConsignmentVOs() != null
						&& !consignmentDocumentVO.getMailInConsignmentVOs().isEmpty())) {
			Mailbag mailbag = null;
			boolean isMailInMRA = false;
			MailbagPK mailbagPk = new MailbagPK();
			for (MailInConsignmentVO mailVO : consignmentDocumentVO.getMailInConsignmentVOs()) {
				mailbagPk.setCompanyCode(mailVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
				try {
					mailbag = Mailbag.find(mailbagPk);
				} catch (FinderException finderException) {
					log.debug("" + "FinderException" + " " + finderException);
				}
				if (mailbag != null) {
					try {
						isMailInMRA = mailtrackingMRAProxy.isMailbagInMRA(mailbagPk.getCompanyCode(),
								mailbagPk.getMailSequenceNumber());
					} catch (BusinessException e) {
						e.printStackTrace();
					} finally {
					}
					if (isMailInMRA || (mailbag.getScanWavedFlag() != null
							&& MailConstantsVO.FLAG_YES.equals(mailbag.getScanWavedFlag()))) {
						isMraImportRequired = true;
						break;
					}
				}
			}
		}
		return isMraImportRequired;
	}

	/** 
	* Method		:	DocumentController.importConsignmentDataToMra Added by 	:	A-4809 on Nov 19, 2018 Used for 	: Parameters	:	@param consignmentDocumentVO  Return type	: 	void
	*/
	public void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO) {
		mailtrackingMRAProxy.importConsignmentDataToMra(consignmentDocumentVO);
	}

	/** 
	* To Check if DSN is present in Accepted Mailbag/Despatch Level 
	* @param consignmentDocumentVO
	* @throws DuplicateDSNException
	* @throws SystemException 
	*/
	private void checkDSNForAcceptedMailAndDespatch(ConsignmentDocumentVO consignmentDocumentVO)
			throws DuplicateDSNException {
		Page<MailInConsignmentVO> newMailsTosaveVOs = consignmentDocumentVO.getMailInConsignmentVOs();
		Collection<MailInConsignmentVO> newMailsToInsert = new ArrayList<MailInConsignmentVO>();
		if (newMailsTosaveVOs != null && newMailsTosaveVOs.size() > 0) {
			for (MailInConsignmentVO mailInConsignmentVO : newMailsTosaveVOs) {
				log.debug("" + " Operation FLAG : " + " " + mailInConsignmentVO.getOperationFlag());
				if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
					newMailsToInsert.add(mailInConsignmentVO);
				}
			}
		}
		DSNVO dsnVO = null;
		String inpk = "";
		int err = 0;
		if (newMailsToInsert != null && newMailsToInsert.size() > 0) {
			for (MailInConsignmentVO newMailTosave : newMailsToInsert) {
				dsnVO = new DSNVO();
				dsnVO.setCompanyCode(newMailTosave.getCompanyCode());
				dsnVO.setOriginExchangeOffice(newMailTosave.getOriginExchangeOffice());
				dsnVO.setDestinationExchangeOffice(newMailTosave.getDestinationExchangeOffice());
				dsnVO.setMailCategoryCode(newMailTosave.getMailCategoryCode());
				dsnVO.setMailSubclass(newMailTosave.getMailSubclass());
				dsnVO.setYear(newMailTosave.getYear());
				dsnVO.setDsn(newMailTosave.getDsn());
				if (newMailTosave.getReceptacleSerialNumber() == null) {
					dsnVO.setPltEnableFlag("N");
				} else {
					if (("").equals(newMailTosave.getReceptacleSerialNumber().trim())) {
						dsnVO.setPltEnableFlag("N");
					} else {
						dsnVO.setPltEnableFlag("Y");
					}
				}
				String pltFlag = findMailType(dsnVO);
				log.debug("" + "!!!!!!!!!!!!!!!!pltFlag" + " " + pltFlag);
				if (pltFlag != null && pltFlag.trim().length() > 0) {
					if (!"M".equals(pltFlag)) {
						if ("".equals(inpk)) {
							inpk = dsnVO.getOriginExchangeOffice() + HYPHEN + dsnVO.getDestinationExchangeOffice()
									+ HYPHEN + dsnVO.getMailCategoryCode() + HYPHEN + dsnVO.getMailSubclass() + HYPHEN
									+ dsnVO.getDsn() + HYPHEN + dsnVO.getYear();
						} else {
							String ipk = dsnVO.getOriginExchangeOffice() + HYPHEN + dsnVO.getDestinationExchangeOffice()
									+ HYPHEN + dsnVO.getMailCategoryCode() + HYPHEN + dsnVO.getMailSubclass() + HYPHEN
									+ dsnVO.getDsn() + HYPHEN + dsnVO.getYear();
							inpk = new StringBuilder().append(inpk).append(" , ").append(ipk).toString();
						}
						err = 1;
					}
				}
				if (err == 1) {
					log.debug("" + "!!!!!!!!!!!!!!!!inpk" + " " + inpk);
					throw new DuplicateDSNException(DuplicateDSNException.DSN_IN_MAILBAG_DESPATCH,
							new Object[] { inpk });
				}
			}
		}
	}

	/** 
	* @param consignmentDocument
	* @throws DuplicateDSNException 
	*/
	private void checkDSNExistanceInConsignment(Collection<MailInConsignmentVO> mailInConsignmentVOs,
			ConsignmentDocument consignmentDocument) throws DuplicateDSNException {
		Set<MailInConsignment> existingMailInConsignments = consignmentDocument.getMailsInConsignments();
		for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
			log.debug("" + " Operation FLAG : " + " " + mailInConsignmentVO.getOperationFlag());
			if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
				int error = 0;
				String newMailKey = new StringBuilder().append(mailInConsignmentVO.getOriginExchangeOffice())
						.append(MailConstantsVO.SEPARATOR).append(mailInConsignmentVO.getDestinationExchangeOffice())
						.append(MailConstantsVO.SEPARATOR).append(mailInConsignmentVO.getMailCategoryCode())
						.append(MailConstantsVO.SEPARATOR).append(mailInConsignmentVO.getMailSubclass())
						.append(MailConstantsVO.SEPARATOR).append(mailInConsignmentVO.getDsn())
						.append(MailConstantsVO.SEPARATOR).append(mailInConsignmentVO.getYear()).toString();
				for (MailInConsignment mailInCsg : existingMailInConsignments) {
					String existingMailKey = new StringBuilder()
							.append(mailInCsg.getCompanyCode())
							.append(MailConstantsVO.SEPARATOR)
							.append(mailInCsg.getConsignmentNumber())
							.append(MailConstantsVO.SEPARATOR)
							.append(mailInCsg.getConsignmentSequenceNumber())
							.append(MailConstantsVO.SEPARATOR)
							.append(mailInCsg.getMailSequenceNumber())
							.append(MailConstantsVO.SEPARATOR).append(mailInCsg.getPaCode())
							.toString();
					if (newMailKey.equalsIgnoreCase(existingMailKey)) {
						if (mailInConsignmentVO.getReceptacleSerialNumber() == null
								|| (mailInConsignmentVO.getReceptacleSerialNumber() != null
										&& mailInConsignmentVO.getReceptacleSerialNumber().trim().length() == 0)) {
							error = 2;
							error = 2;
						}
						break;
					}
				}
				if (error == 1) {
					log.error("-----DuplicateDSNException----Similar Despatch already Exist in consignment---");
					log.error("" + "-----mailInConsignmentVO---" + " " + mailInConsignmentVO);
					throw new DuplicateDSNException(DuplicateDSNException.DSN_IN_DESPATCH);
				} else if (error == 2) {
					log.debug("" + "----DSN EXIST FOR ANOTHER MAILBAG----" + " " + newMailKey);
					throw new DuplicateDSNException(DuplicateDSNException.DSN_IN_MAILBAG_DESPATCH,
							new Object[] { newMailKey });
				}
			}
		}
	}

	/** 
	* TODO Purpose May 26, 2008 , A-3251
	* @param dsnVO
	*/
	public String findMailType(DSNVO dsnVO) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(dsnVO.getCompanyCode());
		String mailId = MailOperationsVOConverter.createMailBag(dsnVO);
		mailbagVO.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailId, dsnVO.getCompanyCode()));
		mailbagVO.setMailbagId(mailId);
		return new Mailbag().findMailType(mailbagVO);
	}

	/** 
	* @author a-1883
	* @param mailInConsignmentVOs
	* @throws SystemException
	* @throws InvalidMailTagFormatException
	*/
	private void validateMailBags(Collection<MailInConsignmentVO> mailInConsignmentVOs, boolean isScanned)
			throws InvalidMailTagFormatException {
		log.debug("DocumentController" + " : " + "validateMailBags" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
			Collection<DSNVO> dSNVOs = new ArrayList<DSNVO>();
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
					if (mailInConsignmentVO.getReceptacleSerialNumber() != null) {
						MailbagVO mailbagVO = new MailbagVO();
						mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
						mailbagVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
						mailbagVO.setDoe(mailInConsignmentVO.getDestinationExchangeOffice());
						mailbagVO.setOoe(mailInConsignmentVO.getOriginExchangeOffice());
						mailbagVO.setMailSubclass(mailInConsignmentVO.getMailSubclass());
						mailbagVO.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
						mailbagVO.setMailbagId(mailInConsignmentVO.getMailId());
						mailbagVO.setDespatchSerialNumber(mailInConsignmentVO.getDsn());
						mailbagVO.setMailClass(mailInConsignmentVO.getMailClass());
						mailbagVO.setYear(mailInConsignmentVO.getYear());
						mailbagVO.setUldNumber(mailInConsignmentVO.getUldNumber());
						mailbagVO.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
						mailbagVO.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
						mailbagVO = mailController.constructOriginDestinationDetails(mailbagVO);
						mailbagVOs.add(mailbagVO);
					} else {
						DSNVO dSNVO = new DSNVO();
						dSNVO.setOperationFlag(DSNVO.OPERATION_FLAG_INSERT);
						dSNVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
						dSNVO.setDestinationExchangeOffice(mailInConsignmentVO.getDestinationExchangeOffice());
						dSNVO.setOriginExchangeOffice(mailInConsignmentVO.getOriginExchangeOffice());
						dSNVO.setDsn(mailInConsignmentVO.getDsn());
						dSNVO.setMailClass(mailInConsignmentVO.getMailClass());
						dSNVO.setMailSubclass(mailInConsignmentVO.getMailSubclass());
						dSNVO.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
						dSNVO.setYear(mailInConsignmentVO.getYear());
						dSNVOs.add(dSNVO);
					}
				}
			}
			if (mailbagVOs.size() > 0) {
				log.debug(" Validating Mailbags ");
				if (isScanned) {
					mailController.validateScannedMailDetails(mailbagVOs);
				} else {
					mailController.validateMailBags(mailbagVOs);
				}
			}
			if (dSNVOs.size() > 0) {
				log.debug(" Validating DSNs ");
			}
		}
		log.debug("DocumentController" + " : " + "validateMailBags" + " Entering");
	}

	/** 
	* @author a-1883 This method performs Insert / Update / Delete operations
	* @param consignmentDocumentVO
	* @param consignmentDocument
	* @throws SystemException
	* @throws MailbagAlreadyAcceptedException
	* @throws DuplicateDSNException 
	* @throws DuplicateMailBagsException 
	*/
	private void modifyChildren(ConsignmentDocumentVO consignmentDocumentVO, ConsignmentDocument consignmentDocument)
			throws MailbagAlreadyAcceptedException, DuplicateDSNException, DuplicateMailBagsException {
		log.debug("DocumentController" + " : " + "modifyChildren" + " Entering");
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = consignmentDocumentVO.getRoutingInConsignmentVOs();
		Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
		if (routingInConsignmentVOs != null && routingInConsignmentVOs.size() > 0) {
			modifyRoutingInConsignment(routingInConsignmentVOs, consignmentDocument);
		}
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			checkDSNExistanceInConsignment((Collection<MailInConsignmentVO>) mailInConsignmentVOs, consignmentDocument);
			modifyMailInConsignment((Collection<MailInConsignmentVO>) mailInConsignmentVOs, consignmentDocument);
		}
		log.debug("DocumentController" + " : " + "modifyChildren" + " Exiting");
	}

	/** 
	* @author A-1883
	* @param routingInConsignmentVOs
	* @param consignmentDocument
	* @throws SystemException
	*/
	private void modifyRoutingInConsignment(Collection<RoutingInConsignmentVO> routingInConsignmentVOs,
			ConsignmentDocument consignmentDocument) {
		log.debug("DocumentController" + " : " + "modifyRoutingInConsignment" + " Entering");
		RoutingInConsignment routingInConsignment = null;
		for (RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs) {
			log.debug("" + " Operation FLAG : " + " " + routingInConsignmentVO.getOperationFlag());
			if (RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())) {
				routingInConsignmentVO.setConsignmentSequenceNumber(
						consignmentDocument.getConsignmentSequenceNumber());
				routingInConsignment = new RoutingInConsignment(routingInConsignmentVO);
				consignmentDocument.getRoutingsInConsignments().add(routingInConsignment);
			} else if (RoutingInConsignmentVO.OPERATION_FLAG_DELETE.equals(routingInConsignmentVO.getOperationFlag())) {
				routingInConsignment = RoutingInConsignment.find(routingInConsignmentVO);
				routingInConsignment.remove();
				consignmentDocument.getRoutingsInConsignments().remove(routingInConsignment);
			} else if (RoutingInConsignmentVO.OPERATION_FLAG_UPDATE.equals(routingInConsignmentVO.getOperationFlag())) {
				routingInConsignment = RoutingInConsignment.find(routingInConsignmentVO);
				routingInConsignment.update(routingInConsignmentVO);
			} else {
			}
		}
		log.debug("DocumentController" + " : " + "modifyRoutingInConsignment" + " Exiting");
	}

	/** 
	* @author A-1883
	* @param mailInConsignmentVOs
	* @param consignmentDocument
	* @throws SystemException
	* @throws MailbagAlreadyAcceptedException
	* @throws DuplicateDSNException 
	* @throws DuplicateMailBagsException 
	*/
	private void modifyMailInConsignment(Collection<MailInConsignmentVO> mailInConsignmentVOs,
			ConsignmentDocument consignmentDocument)
			throws MailbagAlreadyAcceptedException, DuplicateDSNException, DuplicateMailBagsException {
		log.debug("DocumentController" + " : " + "modifyMailInConsignment" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		boolean isFirstTime = true;
		int carrierId = 0;
		MailInConsignment mailInConsignment = null;
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = null;
		HashMap<String, Long> flightSeqNumMap = new HashMap<>();
		Collection<RoutingInConsignment> routingInConsignments = consignmentDocument.getRoutingsInConsignments();
		if (routingInConsignments != null && routingInConsignments.size() > 0) {
			for (RoutingInConsignment routingInConsignment : routingInConsignments) {
				carrierId = routingInConsignment.getOnwardCarrierId();
				routingInConsignmentVO = new RoutingInConsignmentVO();
				routingInConsignmentVO.setOnwardFlightNumber(routingInConsignment.getOnwardFlightNumber());
				routingInConsignmentVO.setOnwardCarrierCode(routingInConsignment.getOnwardCarrierCode());
				if (routingInConsignment.getOnwardFlightDate() != null) {
					routingInConsignmentVO.setOnwardFlightDate(
							localDateUtil.getLocalDateTime(routingInConsignment.getOnwardFlightDate(), null));
				}
				routingInConsignmentVO.setOnwardCarrierSeqNum(routingInConsignment.getOnwardCarrierSeqNum());
				routingInConsignmentVO.setPou(routingInConsignment.getPou());
				routingInConsignmentVO.setPol(routingInConsignment.getPol());
				routingInConsignmentVOs.add(routingInConsignmentVO);
			}
		}
		for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
			log.debug("" + " Operation FLAG : " + " " + mailInConsignmentVO.getOperationFlag());
			if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
				modifyExistingMailInConsignment(mailInConsignmentVO);
				mailInConsignmentVO.setConsignmentSequenceNumber(
						consignmentDocument.getConsignmentSequenceNumber());
				log.error("-----INSERTING NEW MAIL----");
				mailInConsignmentVO
						.setConsignmentNumber(consignmentDocument.getConsignmentNumber());
				mailInConsignmentVO.setConsignmentSequenceNumber(
						consignmentDocument.getConsignmentSequenceNumber());
				mailInConsignmentVO.setConsignmentDate(
						localDateUtil.getLocalDateTime(consignmentDocument.getConsignmentDate(), null));
				mailInConsignmentVO.setAirportCode(consignmentDocument.getAirportCode());
				mailInConsignmentVO.setCarrierId(carrierId);
				mailController.updateMailBagConsignmentDetails(mailInConsignmentVO, routingInConsignmentVOs,
						flightSeqNumMap);
				mailInConsignment = new MailInConsignment(mailInConsignmentVO);
				if (mailInConsignmentVO.getMailSource() == null) {
					mailInConsignmentVO.setMailSource(MailConstantsVO.CARDIT_PROCESS);
				}
				consignmentDocument.getMailsInConsignments().add(mailInConsignment);
				consignmentDocument
						.setStatedBags(consignmentDocument.getStatedBags() + mailInConsignment.getStatedBags());
				consignmentDocument.setStatedWeight(consignmentDocument.getStatedWeight());
			} else if (MailInConsignmentVO.OPERATION_FLAG_DELETE.equals(mailInConsignmentVO.getOperationFlag())) {
				if (isFirstTime) {
					checkDespatchesAlreadyAccepted(mailInConsignmentVOs);
					checkMailbagAccepted(mailInConsignmentVOs);
					isFirstTime = false;
					log.debug(" Mailbags has not accepted Yet ");
				}
				try {
					mailInConsignmentVO.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(
							mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
					mailInConsignment = MailInConsignment.find(mailInConsignmentVO);
				} catch (FinderException finderException) {
					log.error("  Finder Exception ");
					throw new SystemException(finderException.getErrorCode(), finderException.getMessage(),
							finderException);
				}
				consignmentDocument
						.setStatedBags(consignmentDocument.getStatedBags() - mailInConsignment.getStatedBags());
				consignmentDocument.setStatedWeight(consignmentDocument.getStatedWeight());
				mailInConsignment.remove();
				consignmentDocument.getMailsInConsignments().remove(mailInConsignment);
			} else if (MailInConsignmentVO.OPERATION_FLAG_UPDATE.equals(mailInConsignmentVO.getOperationFlag())) {
				modifyExistingMailInConsignment(mailInConsignmentVO);
				try {
					mailInConsignment = MailInConsignment.find(mailInConsignmentVO);
				} catch (FinderException finderException) {
					log.error("  Finder Exception ");
					new MailInConsignment(mailInConsignmentVO);
				}
				if (mailInConsignment != null) {
					consignmentDocument.setStatedBags(consignmentDocument.getStatedBags()
							+ (mailInConsignmentVO.getStatedBags() - mailInConsignment.getStatedBags()));
					consignmentDocument.setStatedWeight(consignmentDocument.getStatedWeight()
							+ (mailInConsignmentVO.getStatedWeight().getValue().doubleValue()));
					if (mailInConsignmentVO.getMailSource() == null) {
						mailInConsignmentVO.setMailSource(MailConstantsVO.CARDIT_PROCESS);
					}
					mailInConsignment.update(mailInConsignmentVO);
				}
				MailbagVO mailbagToUpdate = new MailbagVO();
				mailbagToUpdate.setCompanyCode(mailInConsignmentVO.getCompanyCode());
				mailbagToUpdate.setMailbagId(mailInConsignmentVO.getMailId());
				mailbagToUpdate.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
				mailbagToUpdate.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
				if (mailInConsignmentVO.getMailServiceLevel() != null
						&& !mailInConsignmentVO.getMailServiceLevel().isEmpty()) {
					mailbagToUpdate.setMailServiceLevel(mailInConsignmentVO.getMailServiceLevel());
				} else {
					mailbagToUpdate.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
					mailbagToUpdate.setMailClass(mailInConsignmentVO.getMailClass());
					mailbagToUpdate.setMailSubclass(mailInConsignmentVO.getMailSubclass());
					mailbagToUpdate.setPaCode(mailInConsignmentVO.getPaCode());
					mailbagToUpdate.setMailServiceLevel(mailController.findMailServiceLevel(mailbagToUpdate));
				}
				mailbagToUpdate.setOrigin(mailInConsignmentVO.getMailOrigin());
				mailbagToUpdate.setDestination(mailInConsignmentVO.getMailDestination());
				mailbagToUpdate.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
				mailbagToUpdate.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
				mailbagToUpdate.setPaBuiltFlag(mailInConsignmentVO.getPaBuiltFlag());
				mailbagToUpdate.setWeight(mailInConsignmentVO.getStatedWeight());
				mailbagToUpdate.setMailSource(mailInConsignmentVO.getMailSource());
				mailbagToUpdate.setSecurityStatusCode(consignmentDocument.getSecurityStatusCode());
				new Mailbag().updateMailbagForConsignment(mailbagToUpdate);
			} else {
			}
		}
		log.debug("DocumentController" + " : " + "modifyMailInConsignment" + " Exiting");
	}

	/** 
	* @author a-1883
	* @param mailInConsignmentVOs
	* @throws SystemException
	*/
	private void checkMailbagAccepted(Collection<MailInConsignmentVO> mailInConsignmentVOs) {
		log.debug("DocumentController" + " : " + "checkMailbagAccepted" + " Entering");
		Collection<Mailbag> mailbags = new MailController().checkMailbagAccepted(mailInConsignmentVOs);
		log.debug("" + "Mailbags :" + " " + mailbags);
		if (mailbags != null && mailbags.size() > 0) {
			log.debug(" Going to set consignment details of all already accepted mail bags as Null ");
			for (Mailbag mailbag : mailbags) {
				mailbag.setConsignmentNumber(null);
				mailbag.setConsignmentSequenceNumber(0);
			}
		}
		log.debug("DocumentController" + " : " + "checkMailbagAccepted" + " Exiting");
	}

	/** 
	* @author a-1883 Find Consignment . If it does not exist , create fromhere. Else update MailInConsignment and toal stated bags and weight
	* @param consignmentDocumentVO
	* @return int
	* @throws SystemException
	* @throws MailbagAlreadyAcceptedException
	* @throws DuplicateMailBagsException 
	*/
	public int saveConsignmentForAcceptance(ConsignmentDocumentVO consignmentDocumentVO)
			throws MailbagAlreadyAcceptedException, DuplicateMailBagsException {
		log.debug("DocumentController" + " : " + "saveConsignmentForAcceptance" + " Entering");
		int consignmentSeqNumber = -1;
		ConsignmentDocumentVO documentVO = new ConsignmentDocument()
				.checkConsignmentDocumentExists(consignmentDocumentVO);
		log.debug("" + " ConsignmentDocumentVO : " + " " + documentVO);
		if (documentVO != null) {
			log.debug(" Consignment Document is Existing ");
			ConsignmentDocument consignmentDocument = ConsignmentDocument.find(documentVO);
			consignmentSeqNumber = documentVO.getConsignmentSequenceNumber();
			Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
			if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
				for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
					log.debug("" + " Consignment seq no. Fom acceptance side  : " + " "
							+ mailInConsignmentVO.getConsignmentSequenceNumber());
					mailInConsignmentVO.setConsignmentSequenceNumber(
							consignmentDocument.getConsignmentSequenceNumber());
					log.debug("" + " Consignment seq no. from Consignment entity : " + " "
							+ mailInConsignmentVO.getConsignmentSequenceNumber());
					mailInConsignmentVO
							.setMailSequenceNumber(ConsignmentDocument.findMailSequenceNumber(mailInConsignmentVO));
					log.debug("" + "  MailSequenceNumber got is " + " " + mailInConsignmentVO.getMailSequenceNumber());
					findCreateMailInConsignment(mailInConsignmentVO, consignmentDocument, consignmentSeqNumber,
							consignmentDocumentVO);
				}
			}
		} else {
			log.debug(" Consignment Document is not Existing ");
			consignmentDocumentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			try {
				consignmentSeqNumber = saveConsignmentDocument(consignmentDocumentVO).intValue();
			} catch (InvalidMailTagFormatException invalidMailTagFormatException) {
				log.error(" InvalidMailTagFormatException ");
			} catch (DuplicateDSNException duplicateDSNException) {
				log.error(" DuplicateDSNException ");
			}
		}
		log.debug("DocumentController" + " : " + "saveConsignmentForAcceptance" + " Exiting");
		return consignmentSeqNumber;
	}

	/** 
	* @author a-1883
	* @param mailInConsignmentVO
	* @param consignmentDocument
	* @param consignmentSeqNumber
	* @param consignmentDocumentVO
	* @throws SystemException
	*/
	private void findCreateMailInConsignment(MailInConsignmentVO mailInConsignmentVO,
			ConsignmentDocument consignmentDocument, int consignmentSeqNumber,
			ConsignmentDocumentVO consignmentDocumentVO) {
		log.debug("DocumentController" + " : " + "findMailInConsignment" + " Entering");
		MailInConsignment mailInConsignment = null;
		try {
			mailInConsignment = MailInConsignment.find(mailInConsignmentVO);
			log.debug("" + "  Mail In Consignment is  Existing " + " " + mailInConsignmentVO);
		} catch (FinderException finderException) {
			log.debug("" + "  Mail In Consignment is Not Existing " + " " + mailInConsignmentVO);
			mailInConsignmentVO.setConsignmentSequenceNumber(consignmentSeqNumber);
			mailInConsignment = new MailInConsignment(mailInConsignmentVO);
			consignmentDocument.getMailsInConsignments().add(mailInConsignment);
		}
	}

	/** 
	* @author A-2037
	* @param companyCode
	* @param mailId
	* @param airportCode
	* @return
	* @throws SystemException
	*/
	public MailInConsignmentVO findConsignmentDetailsForMailbag(String companyCode, String mailId, String airportCode) {
		log.debug("DocumentController" + " : " + "findConsignmentDetailsForMailbag" + " Entering");
		return ConsignmentDocument.findConsignmentDetailsForMailbag(companyCode, mailId, airportCode);
	}

	/** 
	* This method checks whether any mails already accepted (calls while deleting mails from consignments )
	* @author a-1883
	* @param mailInConsignmentVOs
	* @throws SystemException
	* @throws MailbagAlreadyAcceptedException
	*/
	private void checkDespatchesAlreadyAccepted(Collection<MailInConsignmentVO> mailInConsignmentVOs)
			throws MailbagAlreadyAcceptedException {
		log.debug("DocumentController" + " : " + "checkMailsAlreadyAccepted" + " Entering");
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			log.debug("" + "Count of VOs" + " " + mailInConsignmentVOs.size());
			Collection<ErrorVO> errorVOs = null;
			String companyCode = null;
			StringBuilder errorString = null;
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				if (mailInConsignmentVO.getMailId() == null) {
					String mail = null;
					StringBuilder sb = new StringBuilder();
					mail = sb.append(mailInConsignmentVO.getCompanyCode())
							.append(mailInConsignmentVO.getConsignmentNumber())
							.append(mailInConsignmentVO.getConsignmentSequenceNumber())
							.append(mailInConsignmentVO.getDestinationExchangeOffice())
							.append(mailInConsignmentVO.getOriginExchangeOffice())
							.append(mailInConsignmentVO.getPaCode()).append(mailInConsignmentVO.getDsn())
							.append(mailInConsignmentVO.getMailSubclass())
							.append(mailInConsignmentVO.getMailCategoryCode()).append(mailInConsignmentVO.getYear())
							.toString();
					mailInConsignmentVO.setMailId(mail);
				}
				if (mailInConsignmentVO.getOperationFlag() != null
						&& !(MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag()))
						&& !(MailInConsignmentVO.OPERATION_FLAG_UPDATE
								.equals(mailInConsignmentVO.getOperationFlag()))) {
					log.debug("" + " CompanyCode :" + " " + companyCode);
					if (companyCode != null && companyCode.trim().length() > 0) {
						if (errorString == null) {
							errorString = new StringBuilder();
						} else {
							errorString.append(",");
						}
						errorString.append(createErrorString(mailInConsignmentVO));
					}
				}
			}
			if (errorString != null && errorString.length() > 0) {
				log.debug(" Mails Already Accepted ");
				Object[] errorData = new Object[1];
				errorData[0] = errorString.toString();
				ErrorVO errorVO = new ErrorVO(MailbagAlreadyAcceptedException.MAIL_ALREADY_ACCEPTED, errorData);
				MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException = new MailbagAlreadyAcceptedException();
				if (errorVOs == null) {
					errorVOs = new ArrayList<ErrorVO>();
				}
				errorVOs.add(errorVO);
				mailbagAlreadyAcceptedException.addErrors(errorVOs);
				throw mailbagAlreadyAcceptedException;
			}
			log.debug(" Mails Not Accepted Yet");
		}
		log.debug("DocumentController" + " : " + "checkMailsAlreadyAccepted" + " Exiting");
	}

	/** 
	* This method creates Error String for Mails accepted
	* @author a-1883
	* @param mailInConsignmentVO
	* @return String
	* @throws SystemException
	*/
	private String createErrorString(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("DocumentController" + " : " + "createErrorString" + " Entering");
		return new StringBuilder().append(mailInConsignmentVO.getOriginExchangeOffice())
				.append(mailInConsignmentVO.getDestinationExchangeOffice())
				.append(mailInConsignmentVO.getMailSubclass()).append(mailInConsignmentVO.getMailCategoryCode())
				.append(mailInConsignmentVO.getYear()).append(mailInConsignmentVO.getDsn()).toString();
	}

	/** 
	* @author A-8353
	* @param unitType
	* @param fromUnit
	* @param toUnit
	* @param fromValue
	* @return
	*/
	//TODO: Method to be corrected in NEO
	public double unitConvertion(String unitType, String fromUnit, String toUnit, double fromValue) {
		UnitConversionNewVO unitConversionVO = null;
		double convertedValue = 0;
		UnitFormatter unitFormatter = ContextUtil.getInstance().getBean(ICargoDomainTypeSupport.class).unitFormatter();
		try {
			unitConversionVO =unitFormatter.getUnitConversionForToUnit(unitType, fromUnit, toUnit, fromValue);
		} catch (UnitException e) {
			throw new RuntimeException(e);
		}
		if (unitConversionVO != null) {
			convertedValue = unitConversionVO.getToValue();
		}
		return convertedValue;

	}

	/** 
	* Method		:	DocumentController.modifyExistingMailbagInConsignment Added by 	:	A-6245 on 12-Oct-2020 Used for 	:	Modify Consignment tables if same mailbag present in another consignment Parameters	:	@param consignmentDocumentVO  Return type	: 	void
	*/
	public void modifyExistingMailInConsignment(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("DocumentController" + " : " + "modifyExistingMailInConsignment" + " Entering");
		if (mailInConsignmentVO.getMailSource() != null
				&& !MailConstantsVO.CARDIT_PROCESS.equalsIgnoreCase(mailInConsignmentVO.getMailSource())) {
			MailInConsignmentVO existingMailInConsignmentVO = null;
			double statedWeight = 0.0;
			try {
				existingMailInConsignmentVO = MailInConsignment.findConsignmentDetailsForMailbag(
						mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getMailId(), null);
			} finally {
			}
			if (mailInConsignmentVO.getStatedWeight() != null) {
				statedWeight = mailInConsignmentVO.getStatedWeight().getValue().doubleValue();
			}
			if (existingMailInConsignmentVO != null) {
				ConsignmentDocument consignmentDocument = null;
				MailInConsignment mailInConsignment = null;
				ConsignmentDocumentVO consignmentDocumentVO = null;
				try {
					mailInConsignment = MailInConsignment.find(existingMailInConsignmentVO);
					consignmentDocumentVO = new ConsignmentDocumentVO();
					consignmentDocumentVO.setCompanyCode(mailInConsignment.getCompanyCode());
					consignmentDocumentVO
							.setConsignmentNumber(mailInConsignment.getConsignmentNumber());
					consignmentDocumentVO.setConsignmentSequenceNumber(
							mailInConsignment.getConsignmentSequenceNumber());
					consignmentDocumentVO.setPaCode(mailInConsignment.getPaCode());
					consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
					consignmentDocument.setStatedBags(consignmentDocument.getStatedBags() - 1);
					consignmentDocument.setStatedWeight(consignmentDocument.getStatedWeight() - statedWeight);
					mailInConsignment.remove();
					consignmentDocument.getMailsInConsignments().remove(mailInConsignment);
				} catch (FinderException e) {
					log.debug("Consignment Not present, no need to modify");
				}
			}
		}
		log.debug("DocumentController" + " : " + "modifyExistingMailInConsignment" + " Exiting");
	}
	/**
	 * @author A-3227
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 * @throws InvalidMailTagFormatException
	 * @throws DuplicateDSNException
	 * @throws DuplicateMailBagsException
	 */
	public void saveConsignmentForManifestedDSN(ConsignmentDocumentVO consignmentDocumentVO)
			throws MailbagAlreadyAcceptedException, InvalidMailTagFormatException, DuplicateDSNException,
			DuplicateMailBagsException {
		log.debug("DocumentController" + " : " + "saveConsignmentForManifestedDSN" + " Entering");
		ArrayList<MailInConsignmentVO> mailInCondignmentVOs = null;
		Page<MailInConsignmentVO> mailbagVOs = consignmentDocumentVO.getMailInConsignmentVOs();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			mailInCondignmentVOs = new ArrayList<MailInConsignmentVO>();
			for (MailInConsignmentVO mailInConsignment : mailbagVOs) {
				MailInConsignmentVO assignedVO = null;
				if (mailInConsignment.getMailId() != null) {
					assignedVO = new DocumentController().findConsignmentDetailsForMailbag(
							mailInConsignment.getCompanyCode(), mailInConsignment.getMailId(), null);
				}
				if (assignedVO == null) {
					mailInCondignmentVOs.add(mailInConsignment);
				}
			}
			if (mailInCondignmentVOs != null && mailInCondignmentVOs.size() > 0) {
				Page<MailInConsignmentVO> mailInConsignmentVOs = new Page<MailInConsignmentVO>(
						(ArrayList<MailInConsignmentVO>) mailInCondignmentVOs, 0, 0, 0, 0, 0, false);
				consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);
			}
		}
		saveConsignmentDocument(consignmentDocumentVO);
		log.debug("DocumentController" + " : " + "saveConsignmentForManifestedDSN" + " Exiting");
	}
	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 */
	public ConsignmentDocumentVO findConsignmentDocumentDetails(ConsignmentFilterVO consignmentFilterVO) {
		log.debug("DocumentController" + " : " + "findConsignmentDocumentDetails" + " Entering");
		return ConsignmentDocument.findConsignmentDocumentDetails(consignmentFilterVO);
	}

	/**
	 * This method deletes Consignment document details and its childs
	 * @author a-1883
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 */
	public void deleteConsignmentDocumentDetails(ConsignmentDocumentVO consignmentDocumentVO)
			throws MailbagAlreadyAcceptedException {
		log.debug("DocumentController" + " : " + "deleteConsignmentDocumentDetails" + " Entering");
		log.error(new StringBuilder("This code executed from delete consignment command_1, csgdocnum ")
				.append(consignmentDocumentVO.getConsignmentNumber()).append(" CSGSEQNUM ")
				.append(consignmentDocumentVO.getConsignmentSequenceNumber()).toString());
		ConsignmentDocument consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
		checkDespatchesAlreadyAccepted(consignmentDocumentVO.getMailInConsignmentVOs());
		consignmentDocument.remove();
		checkMailbagAccepted(consignmentDocumentVO.getMailInConsignmentVOs());
		log.debug("DocumentController" + " : " + "deleteConsignmentDocumentDetails" + " Exiting");
	}
	/**
	 * @author A-3227  - FEB 10, 2009
	 * @param companyCode
	 * @param despatchDetailsVOs
	 * @return The Collection Of Despatches with wrong CSG DTLS
	 * @throws SystemException
	 */
	public Collection<DespatchDetailsVO> validateConsignmentDetails(String companyCode,
																	Collection<DespatchDetailsVO> despatchDetailsVOs) {
		Collection<DespatchDetailsVO> despatchesWithWrongCsgDtls = new ArrayList<DespatchDetailsVO>();
		for (DespatchDetailsVO despatchDetailVO : despatchDetailsVOs) {
			DespatchDetailsVO despDtlVO = ConsignmentDocument.findConsignmentDetailsForDespatch(companyCode,
					despatchDetailVO);
			if (despDtlVO != null) {
				if (despatchDetailVO.getConsignmentNumber().equals(despDtlVO.getConsignmentNumber())
						&& despatchDetailVO.getPaCode().equals(despDtlVO.getPaCode())
						&& despatchDetailVO.getDsn().equals(despDtlVO.getDsn())
						&& despatchDetailVO.getOriginOfficeOfExchange().equals(despDtlVO.getOriginOfficeOfExchange())
						&& despatchDetailVO.getDestinationOfficeOfExchange()
						.equals(despDtlVO.getDestinationOfficeOfExchange())
						&& despatchDetailVO.getMailCategoryCode().equals(despDtlVO.getMailCategoryCode())
						&& despatchDetailVO.getMailSubclass().equals(despDtlVO.getMailSubclass())
						&& despatchDetailVO.getYear() == despDtlVO.getYear()) {
					if (!despatchDetailVO.getConsignmentDate()
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))
							.equals(despDtlVO.getConsignmentDate()
									.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)))
							|| despatchDetailVO.getStatedBags() != despDtlVO.getStatedBags()
							|| despatchDetailVO.getStatedWeight() != despDtlVO.getStatedWeight()) {
						despatchesWithWrongCsgDtls.add(despatchDetailVO);
					}
				}
			}
		}
		return despatchesWithWrongCsgDtls;
	}

	/**
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @return
	 * @throws SystemException
	 */
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException {
		log.debug("DocumentController" + " : " + "processMailDataFromExcel" + " Entering");
		String processStatus = null;
		Collection<ConsignmentDocumentVO> documentVOs = null;
		processStatus = new MailController().processMailDataFromExcel(fileUploadFilterVO);
		if ("OK".equals(processStatus)) {
			documentVOs = new MailController().fetchMailDataForOfflineUpload(fileUploadFilterVO.getCompanyCode(),
					fileUploadFilterVO.getFileType());
			if (null != documentVOs) {
				for (ConsignmentDocumentVO consgnVO : documentVOs) {
					if (processStatus.equals("NOTOK")) {
						break;
					}
					for (MailInConsignmentVO mailVO : consgnVO.getMailInConsignment()) {
						if ((mailVO.getMailId() != null && mailVO.getMailId().equals(mailVO.getDsn()))
								&& (mailVO.getConsignmentDate() != null
								&& mailVO.getConsignmentDate().equals(mailVO.getReqDeliveryTime()))) {
							fileUploadFilterVO.setStatus("Mailbag already exist in system");
							saveFileUploadError(fileUploadFilterVO);
							processStatus = "NOTOK";
							break;
						}
					}
				}
				for (ConsignmentDocumentVO consgnVO : documentVOs) {
					if (processStatus.equals("NOTOK")) {
						break;
					}
					if (processStatus.equals("OK")) {
						constructConsignmentDocumentVO(consgnVO);
						try {
							saveConsignmentDocument(consgnVO);
						} catch (MailbagAlreadyAcceptedException | InvalidMailTagFormatException | DuplicateDSNException
								| DuplicateMailBagsException e) {
							processStatus = "NOTOK";
							fileUploadFilterVO.setStatus(e.getMessage());
							saveFileUploadError(fileUploadFilterVO);
							break;
						}
					}
				}
			}
		}
		new MailController().removeDataFromTempTable(fileUploadFilterVO);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.insertHistoryDetailsForExcelUpload(documentVOs);
		return processStatus;
	}

	/**
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 */
	private void saveFileUploadError(FileUploadFilterVO fileUploadFilterVO) {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		FileUploadErrorLogVO errorLogVO = new FileUploadErrorLogVO();
		Collection<FileUploadErrorLogVO> errorLogVOs = new ArrayList<FileUploadErrorLogVO>();
		errorLogVO.setCompanyCode(fileUploadFilterVO.getCompanyCode());
		errorLogVO.setProcessIdentifier(fileUploadFilterVO.getProcessIdentifier());
		errorLogVO.setFileName(fileUploadFilterVO.getFileName());
		errorLogVO.setFileType(fileUploadFilterVO.getFileType());
		errorLogVO.setLineNumber(0);
		errorLogVO.setLineContent(fileUploadFilterVO.getFileName());
		errorLogVO.setErrorCode(fileUploadFilterVO.getStatus());
		errorLogVO.setUpdatedUser(logonAttributes.getUserId());
		ZonedDateTime date = localDateUtil.getLocalDate(null, false);
		errorLogVO.setUpdatedTime(LocalDateMapper.toLocalDate(date));
		errorLogVOs.add(errorLogVO);
		if (!errorLogVOs.isEmpty()) {
			sharedDefaultsProxy.saveFileUploadExceptions(errorLogVOs);
		}
	}

	/**
	 * @author A-7794
	 * @throws SystemException
	 */
	public void constructConsignmentDocumentVO(ConsignmentDocumentVO documentVO) {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		AirportVO airportVO = sharedAreaProxy.findAirportDetails(documentVO.getCompanyCode(),
				logonAttributes.getAirportCode());
		log.debug("" + "AIRPORT VO" + " " + airportVO);
		String id = new StringBuilder().append(airportVO.getCountryCode()).append(airportVO.getCityCode()).toString();
		//TODO: TO correct
		String key = null;//KeyUtils.getKey();
		String str = "";
		double totalwgt = 0.0;
		int count = 0;
		for (int i = 0; i < (7 - key.length()); i++) {
			if (count == 0) {
				str = "0";
				count = 1;
			} else {
				str = new StringBuilder().append(str).append("0").toString();
			}
		}
		String conDocNo = new StringBuilder().append(id).append("S").append(str).append(key).toString();
		documentVO.setConsignmentNumber(conDocNo);
		documentVO.setOperation("O");
		documentVO.setAirportCode(logonAttributes.getAirportCode());
		Collection<RoutingInConsignmentVO> routingVOs = documentVO.getRoutingInConsignmentVOs();
		for (MailInConsignmentVO mailVO : documentVO.getMailInConsignment()) {
			double systemWt = 0.0;
			if (routingVOs == null) {
				routingVOs = new ArrayList<RoutingInConsignmentVO>();
				RoutingInConsignmentVO routingVO = new RoutingInConsignmentVO();
				routingVO.setPol(mailVO.getMailOrigin());
				routingVO.setPou(mailVO.getMailDestination());
				routingVO.setCompanyCode(mailVO.getCompanyCode());
				routingVO.setOnwardCarrierCode(documentVO.getCarrierCode());
				routingVO.setConsignmentNumber(conDocNo);
				routingVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
				routingVO.setConsignmentSequenceNumber(documentVO.getConsignmentSequenceNumber());
				routingVO.setOriginOfficeOfExchange(mailVO.getDsn().substring(0, 6));
				routingVO.setDestinationOfficeOfExchange(mailVO.getDsn().substring(6, 12));
				routingVO.setOnwardFlightNumber("0000");
				routingVO.setOnwardCarrierSeqNum(MailConstantsVO.DESTN_FLT);
				routingVO.setRoutingSerialNumber(MailConstantsVO.ZERO);
				routingVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
				routingVO.setNoOfPieces(1);
				routingVO.setPaCode(documentVO.getPaCode());
				if (mailVO.getReqDeliveryTime() != null) {
					routingVO.setOnwardFlightDate(mailVO.getReqDeliveryTime());
				}
				routingVOs.add(routingVO);
				documentVO.setRoutingInConsignmentVOs(routingVOs);
				documentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			} else {
				for (RoutingInConsignmentVO vo : routingVOs) {
					vo.setPol(mailVO.getMailOrigin());
					vo.setPou(mailVO.getMailDestination());
					vo.setCompanyCode(mailVO.getCompanyCode());
					vo.setConsignmentNumber(conDocNo);
					documentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
				}
			}
			mailVO.setMailSource("FILE");
			mailVO.setConsignmentNumber(conDocNo);
			mailVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			mailVO.setMailId(mailVO.getDsn());
			if (mailVO.getMailId() != null) {
				mailVO.setDsn(mailVO.getMailId().substring(16, 20));
				mailVO.setYear(Integer.parseInt(mailVO.getMailId().substring(15, 16)));
				mailVO.setMailSubclass(mailVO.getMailId().substring(13, 15));
				mailVO.setReceptacleSerialNumber(mailVO.getMailId().substring(20, 23));
				mailVO.setHighestNumberedReceptacle(mailVO.getMailId().substring(23, 24));
				mailVO.setRegisteredOrInsuredIndicator(mailVO.getMailId().substring(24, 25));
				mailVO.setStatedBags(1);
				mailVO.setMailClass(mailVO.getMailId().substring(13, 14));
				systemWt = (Double.parseDouble(mailVO.getMailId().substring(25, 29))) / 10;
			}
			if (mailVO.getReqDeliveryTime() != null) {
				mailVO.setConsignmentDate(mailVO.getReqDeliveryTime());
			}
			//TODO: hardcoding of weight to be corrected
			Quantity wgt = quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(systemWt),
					BigDecimal.valueOf(0.0), "K");
			mailVO.setStatedWeight(wgt);
			totalwgt = totalwgt + systemWt;
		}
		documentVO.setStatedWeight(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(totalwgt)));
		Page<MailInConsignmentVO> mailconsgnVos = new Page<MailInConsignmentVO>(
				(ArrayList<MailInConsignmentVO>) documentVO.getMailInConsignment(), 0, 0, 0, 0, 0, false);
		documentVO.setMailInConsignmentVOs(mailconsgnVos);
	}
	public Integer updateConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO)
			throws MailbagAlreadyAcceptedException, InvalidMailTagFormatException, DuplicateDSNException,
			DuplicateMailBagsException {
		log.debug("DocumentController" + " : " + "updateConsignmentDocument" + " Entering");
		Integer consignmentSeqNumber = null;
		String triggerPoint = "";
		String consignmentDate = null;
		String latestConsignmentDate = null;
		log.debug("" + " OPERATION FLAG :" + " " + consignmentDocumentVO.getOperationFlag());
		LoginProfile logonVO = contextUtil.callerLoginProfile();
		consignmentDocumentVO.setLastUpdateUser(logonVO.getUserId());
		try {
			triggerPoint = new MailController().findSystemParameterValue(IMPORT_TRIGGERPOINT);
		} finally {
		}
		validateMailBags((Collection<MailInConsignmentVO>) consignmentDocumentVO.getMailInConsignmentVOs(),
				consignmentDocumentVO.isScanned());
		checkDSNForAcceptedMailAndDespatch(consignmentDocumentVO);
		ContextUtil.getInstance().getBean(MailController.class).saveConsignmentDetails(consignmentDocumentVO);
		if (consignmentDocumentVO.getMailInConsignmentVOs() != null
				&& consignmentDocumentVO.getMailInConsignmentVOs().size() > 0) {
			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
			consignmentFilterVO.setPaCode(consignmentDocumentVO.getPaCode());
			consignmentFilterVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
			consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
			ConsignmentDocumentVO consignmentDocumentVOTofind = new DocumentController()
					.findConsignmentDocumentDetails(consignmentFilterVO);
			if (consignmentDocumentVOTofind != null) {
				consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_NO);
				consignmentFilterVO.setPageNumber(0);
				consignmentDocumentVOTofind = new DocumentController()
						.findConsignmentDocumentDetails(consignmentFilterVO);
				ConsignmentDocument consignmentDocument = ConsignmentDocument.find(consignmentDocumentVOTofind);
				if (consignmentDocument != null) {
					if (consignmentDocument.getConsignmentDate() != null
							&& consignmentDocumentVO.getConsignmentDate() != null) {
						consignmentDate = localDateUtil.getLocalDateTime(consignmentDocument.getConsignmentDate(), null)
								.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT));
						latestConsignmentDate = consignmentDocumentVO.getConsignmentDate()
								.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT));
						PostalAdministrationVO postalAdministrationVO = new MailController().findPACode(
								consignmentDocument.getCompanyCode(),
								consignmentDocument.getPaCode());
						consignmentDocument
								.setConsignmentDate(consignmentDocumentVO.getConsignmentDate().toLocalDateTime());
						if (consignmentDocument.getMailsInConsignments() != null
								&& !consignmentDocument.getMailsInConsignments().isEmpty()) {
							consignmentDocument.getMailsInConsignments().forEach(mailInCsg -> {
								MailbagVO mailbagVO = new MailbagVO();
								mailbagVO.setCompanyCode(mailInCsg.getCompanyCode());
								mailbagVO.setMailSequenceNumber(
										mailInCsg.getMailSequenceNumber());
								mailbagVO.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());
								try {
									new Mailbag().updateDespatchDate(mailbagVO,
											postalAdministrationVO.getDupMailbagPeriod());
								}  catch (SystemException e) {
									log.debug("" + "SystemException" + " " + e);
								}
							});
						}
					}
					for (MailInConsignmentVO mailInConsignmentVO : consignmentDocumentVO.getMailInConsignmentVOs()) {
						mailInConsignmentVO.setConsignmentSequenceNumber(
								consignmentDocument.getConsignmentSequenceNumber());
						mailInConsignmentVO.setPaCode(consignmentDocument.getPaCode());
					}
					modifyMailInConsignment(consignmentDocumentVO.getMailInConsignmentVOs(), consignmentDocument);
				}
			}
		}
		if (isMraImportRequired(consignmentDocumentVO, triggerPoint)) {
			importConsignmentDataToMra(consignmentDocumentVO);
		}
		log.debug("DocumentController" + " : " + "updateConsignmentDocument" + " Exiting");
		return consignmentSeqNumber;
	}
	/**
	 * Method		:	DocumentController.saveUploadedConsignmentData Added by 	:	A-6245 on 22-Dec-2020 Used for 	:	IASCB-81526 Parameters	:	@param consignmentDocumentVOs Parameters	:	@throws SystemException Parameters	:	@throws MailTrackingBusinessException  Return type	: 	void
	 */
	public void saveUploadedConsignmentData(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
			throws MailOperationsBusinessException {
		SaveConsignmentUploadFeature saveConsignmentUploadFeature = ContextUtil.getInstance()
				.getBean(SaveConsignmentUploadFeature.class);
		for (ConsignmentDocumentVO consignmentDocumentVO : consignmentDocumentVOs) {
			saveConsignmentUploadFeature.execute(consignmentDocumentVO);
		}
	}
	 public ConsignmentDocumentVO generateConsignmentSummaryReportDtls(ConsignmentFilterVO consignmentFilterVO) {
		return ConsignmentDocument.generateConsignmentSummaryReport(consignmentFilterVO);
	}


	public ConsignmentDocumentVO generateConsignmentSecurityReportDtls(ConsignmentFilterVO filterVO) {
		var logonAttributes = contextUtil.callerLoginProfile();
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setAirportCode(logonAttributes.getAirportCode());

		return ConsignmentDocument.generateConsignmentSecurityReportDtls(filterVO);
	}
	public ConsignmentDocumentVO generateConsignmentReportDtls(ConsignmentFilterVO consignmentFilterVO) {
		return ConsignmentDocument.generateConsignmentReport(consignmentFilterVO);
	}
	private static MailOperationsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());
		}
	}
	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentReportDtls(ConsignmentFilterVO consignmentFilterVO) {
		log.debug(DOCUMENT_CONTROLLER, "generateCN46ConsignmentReport");
		return constructDAO().generateCN46ConsignmentReport(consignmentFilterVO);
	}

	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReportDtls(ConsignmentFilterVO consignmentFilterVO) {
		return constructDAO().generateCN46ConsignmentSummaryReport(consignmentFilterVO);
	}
}
