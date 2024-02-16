package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.MailUploadController;
import com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.ValidateMailBagDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.RecoDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailSubClassVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component("embargoValidator")
public class EmbargoValidator extends Validator<ScannedMailDetailsVO> {
    @Autowired
    private NeoMastersServiceUtils neoMastersServiceUtils;
    @Autowired
    private MailController mailController;
    @Autowired
    private MailUploadController mailuploadController;
    @Autowired
    MailTrackingDefaultsBI mailMasterApi;
    @Autowired
    private ScannerValidationUtils scannerValidationUtils;
    @Autowired
    private LocalDate localDateUtil;

    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        RecoDefaultsProxy recoDefaultsProxy = ContextUtil.getInstance().getBean(RecoDefaultsProxy.class);
        //String embargoValidationRequired = neoMastersServiceUtils.findSystemParameterValue(ValidateMailBagDetailsFeatureConstants.EMBARGO_VALIDATION_REQUIRED);
        String embargoValidationRequired = mailuploadController.findSystemParameterValue(ValidateMailBagDetailsFeatureConstants.EMBARGO_VALIDATION_REQUIRED);
        if (!MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
                &&(scannedMailDetailsVO.getMailDetails()!=null &&!scannedMailDetailsVO.getMailDetails().isEmpty())
                && MailConstantsVO.FLAG_YES.equals(embargoValidationRequired)
                && (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
                || MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
                || MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())
                || MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
                || MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint()))) {
            Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
            Collection<EmbargoDetailsVO> embargoDetails = null;
            Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();
            boolean isEmbargoValidationRequired = true;
            EmbargoFilterVO embargoFilterVO = constructEmbargoFilterVO(scannedMailDetailsVO);
            try {
                isEmbargoValidationRequired = recoDefaultsProxy.checkAnyEmbargoExists(embargoFilterVO);
            } finally {
            }
            if (isEmbargoValidationRequired && mailBagVOs != null && mailBagVOs.size() > 0) {
                for (MailbagVO mailBagvo : mailBagVOs) {
                    if (mailBagvo.getMailbagId() != null && (mailBagvo.getMailbagId().trim().length() == 29
                            || (mailuploadController.isValidMailtag(mailBagvo.getMailbagId().trim().length())))) {
                        shipmentDetailsVOs = populateShipmentDetailsVO(mailBagvo, scannedMailDetailsVO.getCompanyCode(),
                                scannedMailDetailsVO);
                        if (shipmentDetailsVOs != null && shipmentDetailsVOs.size() > 0) {
                            log.info("" + "shipmentDetailsVOs" + " " + shipmentDetailsVOs);
                            try {
                                embargoDetails = mailController.checkEmbargoForMail(shipmentDetailsVOs);
                            } catch (Exception e) {
                                log.debug("Exception Caught");
                                scannerValidationUtils.constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
                            }
                            if (embargoDetails != null && embargoDetails.size() > 0) {
                                log.info("" + "VO is" + " " + embargoDetails);
                                for (EmbargoDetailsVO embargoDetailsVO : embargoDetails) {
                                    if ("W".equals(embargoDetailsVO.getEmbargoLevel())
                                            && !MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())) {
                                        scannerValidationUtils.constructAndroidException(MailConstantsVO.EMBARGO_VALIDATION,
                                                embargoDetailsVO.getEmbargoDescription(), scannedMailDetailsVO);
                                    } else if ("E".equals(embargoDetailsVO.getEmbargoLevel())) {
                                        scannerValidationUtils.constructAndRaiseException(embargoDetailsVO.getEmbargoDescription(),
                                                embargoDetailsVO.getEmbargoDescription(), scannedMailDetailsVO);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private EmbargoFilterVO constructEmbargoFilterVO(ScannedMailDetailsVO scannedMailDetailsVO) {
        EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
        embargoFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
        if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
            embargoFilterVO.setApplicableTransactions("MALARR");
        } else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
            embargoFilterVO.setApplicableTransactions("MALACP");
        } else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
            embargoFilterVO.setApplicableTransactions("MALTRA");
        }
        return embargoFilterVO;
    }
    private Collection<ShipmentDetailsVO> populateShipmentDetailsVO(MailbagVO mailbagVOToSave, String companyCode,
                                                                    ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException {
        SharedAreaProxy sharedAreaProxy = ContextUtil.getInstance().getBean(SharedAreaProxy.class);
        log.debug("MailUploadController" + " : " + "populateShipmentDetailsVO" + " Entering");
        log.info("" + "populateShipmentDetailsVO" + " " + mailbagVOToSave);
        ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
        Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
        Map<String, Collection<String>> detailsMap = new HashMap<String, Collection<String>>();
        String commodityCode = "";
        Set<String> flightNumberOrg = new HashSet<String>();
        Set<String> flightNumberDst = new HashSet<String>();
        Set<String> flightNumberVia = new HashSet<String>();
        Set<String> carrierOrg = new HashSet<String>();
        Set<String> carrierDst = new HashSet<String>();
        Set<String> carrierVia = new HashSet<String>();
        if (mailbagVOToSave.getOoe() != null && mailbagVOToSave.getDoe() != null) {
            shipmentDetailsVO.setCompanyCode(companyCode);
            shipmentDetailsVO.setOoe(mailbagVOToSave.getOoe());
            shipmentDetailsVO.setDoe(mailbagVOToSave.getDoe());
            Collection<String> officeOfExchanges = new ArrayList<String>();
            String ooe = null;
            String doe = null;
            String origin = null;
            String destination = null;
            String orgCountry = null;
            String desCountry = null;
            ooe = mailbagVOToSave.getOoe();
            doe = mailbagVOToSave.getDoe();
            officeOfExchanges.add(ooe);
            officeOfExchanges.add(doe);
            Collection<String> airportCodes = new ArrayList<String>();
            Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
            AirportValidationVO orgAirportValidationVO = null;
            AirportValidationVO desAirportValidationVO = null;
            origin = mailbagVOToSave.getOrigin();
            destination = mailbagVOToSave.getDestination();
            airportCodes.add(origin);
            airportCodes.add(destination);
            try {
                countryCodeMap = neoMastersServiceUtils.validateAirportCodes(companyCode, airportCodes);
            } catch(AirportBusinessException e){
                e.printStackTrace();
            }
            finally {
            }
            if (countryCodeMap != null) {
                orgAirportValidationVO = countryCodeMap.get(origin);
                desAirportValidationVO = countryCodeMap.get(destination);
                if (orgAirportValidationVO != null) {
                    orgCountry = orgAirportValidationVO.getCountryCode();
                }
                if (desAirportValidationVO != null) {
                    desCountry = desAirportValidationVO.getCountryCode();
                }
            }
            if (origin != null && orgCountry != null) {
                shipmentDetailsVO.setOrgStation(origin);
                shipmentDetailsVO.setOrgCountry(orgCountry);
            }
            if (destination != null && desCountry != null) {
                shipmentDetailsVO.setDstStation(destination);
                shipmentDetailsVO.setDstCountry(desCountry);
            }
            shipmentDetailsVO.setShipmentID(mailbagVOToSave.getMailbagId());
            String orgPaCod = null;
            String dstPaCod = null;
            String subClassGrp = null;
            Collection<MailSubClassVO> mailSubClassVOs = null;
            Collection<String> mailclass = new ArrayList<String>();
            Collection<String> mailsubclass = new ArrayList<String>();
            Collection<String> mailcat = new ArrayList<String>();
            Collection<String> mailsubclassGrp = new ArrayList<String>();
            Collection<String> commodity = new ArrayList<String>();
            try {
                orgPaCod = mailbagVOToSave.getPaCode() != null && mailbagVOToSave.getPaCode().trim().length() > 0
                        ? mailbagVOToSave.getPaCode()
                        : mailController.findPAForOfficeOfExchange(companyCode, mailbagVOToSave.getOoe());
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
            try {
                dstPaCod = mailController.findPAForOfficeOfExchange(companyCode, mailbagVOToSave.getDoe());
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            shipmentDetailsVO.setOrgPaCod(orgPaCod);
            shipmentDetailsVO.setDstPaCod(dstPaCod);
            String mailBagId = mailbagVOToSave.getMailbagId();
            StringBuilder flightNumber = new StringBuilder();
            flightNumber.append(scannedMailDetailsVO.getCarrierCode());
            if (scannedMailDetailsVO.getFlightNumber() != null) {
                flightNumber.append("~").append(scannedMailDetailsVO.getFlightNumber());
            }
            flightNumberOrg.add(flightNumber.toString());
            flightNumberVia.add(flightNumber.toString());
            flightNumberDst.add(flightNumber.toString());
            carrierOrg.add(scannedMailDetailsVO.getCarrierCode());
            carrierDst.add(scannedMailDetailsVO.getCarrierCode());
            carrierVia.add(scannedMailDetailsVO.getCarrierCode());
            detailsMap.put("CARORG", carrierOrg);
            detailsMap.put("CARVIA", carrierVia);
            detailsMap.put("CARDST", carrierDst);
            detailsMap.put("FLTNUMORG", flightNumberOrg);
            detailsMap.put("FLTNUMVIA", flightNumberVia);
            detailsMap.put("FLTNUMDST", flightNumberDst);
            if (mailBagId != null && mailBagId.trim().length() > 0) {
                if (mailBagId.length() == 29) {
                    mailcat.add(mailBagId.substring(12, 13));
                    mailsubclass.add(mailBagId.substring(13, 15));
                    mailclass.add(mailBagId.substring(13, 15).substring(0, 1));
                    detailsMap.put("MALCLS", mailclass);
                    detailsMap.put("MALSUBCLS", mailsubclass);
                    detailsMap.put("MALCAT", mailcat);
                    try {
                        mailSubClassVOs = mailController.findMailSubClassCodes(companyCode,
                                mailBagId.substring(13, 15));
                    } catch (Exception e) {
                        log.debug(e.getMessage());
                    }
                    if (mailSubClassVOs != null && mailSubClassVOs.size() > 0) {
                        subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
                        if (subClassGrp != null) {
                            mailsubclassGrp.add(subClassGrp);
                            detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
                        }
                    }
                } else if (mailuploadController.isValidMailtag(mailBagId.length())) {
                    mailcat.add(mailbagVOToSave.getMailCategoryCode());
                    mailsubclass.add(mailbagVOToSave.getMailSubclass());
                    mailclass.add(mailbagVOToSave.getMailClass());
                    detailsMap.put("MALCLS", mailclass);
                    detailsMap.put("MALSUBCLS", mailsubclass);
                    detailsMap.put("MALCAT", mailcat);
                    try {
                        mailSubClassVOs = mailController.findMailSubClassCodes(companyCode,
                                mailbagVOToSave.getMailSubclass());
                    } catch (Exception e) {
                        log.debug(e.getMessage());
                    }
                    if (mailSubClassVOs != null && mailSubClassVOs.size() > 0) {
                        subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
                        if (subClassGrp != null) {
                            mailsubclassGrp.add(subClassGrp);
                            detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
                        }
                    }
                }
                CommodityValidationVO commodityValidationVO = null;
                try {
                    //commodityCode = neoMastersServiceUtils.findSystemParameterValue(ValidateMailBagDetailsFeatureConstants.DEFAULTCOMMODITYCODE_SYSPARAM);
                    commodityCode = mailuploadController.findSystemParameterValue(ValidateMailBagDetailsFeatureConstants.DEFAULTCOMMODITYCODE_SYSPARAM);
                    commodityValidationVO = mailController.validateCommodity(mailbagVOToSave.getCompanyCode(), commodityCode,
                            orgPaCod);
                } finally {
                }
                if (commodityValidationVO != null)
                    commodity.add(commodityCode);
                detailsMap.put("COM", commodity);
                shipmentDetailsVO.setMap(detailsMap);
            }
            if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
                shipmentDetailsVO.setApplicableTransaction("MALARR");
            } else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
                shipmentDetailsVO.setApplicableTransaction("MALACP");
            } else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
                shipmentDetailsVO.setApplicableTransaction("MALTRA");
            }
            localDateUtil.getLocalDate(LocalDateMapper.toLocalDate(mailbagVOToSave.getScannedDate(), false));
            shipmentDetailsVO.setShipmentDate(LocalDateMapper.toLocalDate(mailbagVOToSave.getScannedDate(), false));
            shipmentDetailsVOs.add(shipmentDetailsVO);
            log.info("" + "populateShipmentDetailsVO" + " " + shipmentDetailsVOs);
            return shipmentDetailsVOs;
        }
        return null;
    }
}