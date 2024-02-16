package com.ibsplc.neoicargo.cca;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwb;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbChargeDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbPk;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbRateDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CCACustomerDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbDetailDimensions;
import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbRoutingDetails;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.dto.CcaChangedStatusVO;
import com.ibsplc.neoicargo.cca.modal.CCAAWBDetailData;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CCAPrintFilterModel;
import com.ibsplc.neoicargo.cca.modal.CCAWorkflowData;
import com.ibsplc.neoicargo.cca.modal.CcaChargeDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaCustomerDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaNumbersNode;
import com.ibsplc.neoicargo.cca.modal.CcaRateDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.modal.RelatedCCAData;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CCAWorkflowVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import com.ibsplc.neoicargo.cca.vo.CcaNumbersNodeVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingUldVO;
import com.ibsplc.neoicargo.cca.vo.DimensionsVO;
import com.ibsplc.neoicargo.cca.vo.GetCcaListMasterVO;
import com.ibsplc.neoicargo.cca.vo.TaxFilterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEntryType;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditFieldVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEventList;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEventPublisher;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.currency.vo.MoneyVO;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfMeasure;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import org.apache.commons.lang3.tuple.Pair;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_LISTENER_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_MASTER_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_STATUS;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CUSTOMER_TYPE_CC_COLLECTOR;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.IATA_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.MKT_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.PAYMENT_TYPE_CHARGE_COLLECT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.QUALITY_AUDITED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.TRIGGER_QUALITY_AUDIT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.UTC;
import static org.mockito.Mockito.mock;


public final class MockModelsGeneratorUtils {

    private MockModelsGeneratorUtils() {
    }

    public static final String LOCATION_CODE = "TRV";
    public static final String ORIGIN_AIRPORT = "FRA";
    public static final String DESTINATION_AIRPORT = "DXB";
    public static final String COMPANY_CODE = "AV";
    private static final String ICO_ADMIN = "ICOADMIN";

    public static final String SHIPMENT_PREFIX = "134";
    public static final int SHIPMENT_PREFIX_INT_VALUE = 134;
    public static final String MASTER_DOCUMENT_NUMBER = "11001100";
    public static final String CCA_REFERENCE_NUMBER = "CCA000001";

    public static final String CURRENCY_USD = "USD";
    public static final String CCA_AWB_SPOT_RATE_ID = "DXB01";

    public static Quantities QUANTITIES = MockQuantity.performInitialisation(null, null, LOCATION_CODE, null);

    @NotNull
    public static CCAMasterVO getCCAMasterVO(final String masterDocumentNumber,
                                             final String ccaReferenceNumber,
                                             final LocalDate ccaIssueDate) {
        return getCCAMasterVO(masterDocumentNumber, ccaReferenceNumber, ccaIssueDate, CcaStatus.N);

    }

    @NotNull
    public static GetCcaListMasterVO getCcaListMasterVO(final String masterDocumentNumber,
                                                        final String ccaReferenceNumber,
                                                        final LocalDate ccaIssueDate,
                                                        final CcaStatus ccaStatus) {
        final var ccaMasterVO = new GetCcaListMasterVO();
        ccaMasterVO.setMasterDocumentNumber(masterDocumentNumber);
        ccaMasterVO.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMasterVO.setCcaReferenceNumber(ccaReferenceNumber);
        ccaMasterVO.setCcaStatus(ccaStatus);
        ccaMasterVO.setCcaIssueDate(ccaIssueDate);
        return ccaMasterVO;
    }

    @NotNull
    public static CCAMasterVO getCCAMasterVO(final String masterDocumentNumber,
                                             final String ccaReferenceNumber,
                                             final LocalDate ccaIssueDate,
                                             final CcaStatus ccaStatus) {
        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setMasterDocumentNumber(masterDocumentNumber);
        ccaMasterVO.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMasterVO.setCcaReferenceNumber(ccaReferenceNumber);
        ccaMasterVO.setCcaStatus(ccaStatus);
        ccaMasterVO.setCcaIssueDate(ccaIssueDate);
        ccaMasterVO.setUnitOfMeasure(getDefaultUnitsOfMeasure());
        return ccaMasterVO;
    }

    @NotNull
    public static CCAMasterVO getCCAMasterVO(final String masterDocumentNumber,
                                             final String ccaReferenceNumber,
                                             final LocalDate ccaIssueDate,
                                             final Collection<CcaAwbVO> shipmentDetailVOs) {
        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setMasterDocumentNumber(masterDocumentNumber);
        ccaMasterVO.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMasterVO.setCcaReferenceNumber(ccaReferenceNumber);
        ccaMasterVO.setCcaStatus(CcaStatus.N);
        ccaMasterVO.setCcaStatus(CcaStatus.N);
        ccaMasterVO.setCcaIssueDate(ccaIssueDate);
        ccaMasterVO.setShipmentDetailVOs(shipmentDetailVOs);
        ccaMasterVO.setUnitOfMeasure(getDefaultUnitsOfMeasure());
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVOs.stream().filter(ccaAwbVO -> CCA_RECORD_TYPE_REVISED.equals(ccaAwbVO.getRecordType())).findFirst().orElse(null));
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVOs.stream().filter(ccaAwbVO -> CCA_RECORD_TYPE_ORIGINAL.equals(ccaAwbVO.getRecordType())).findFirst().orElse(null));
        return ccaMasterVO;
    }

    @NotNull
    public static CCAMasterVO getWithPaymentTypeCcaMasterVO(String freightPaymentType, String otherChargePaymentType) {
        var customerType = PAYMENT_TYPE_CHARGE_COLLECT.equals(freightPaymentType)
                ? CustomerType.I
                : CustomerType.O;
        var payType = freightPaymentType.substring(0, 1).concat(otherChargePaymentType.substring(0, 1));

        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setPayType(payType);
        revised.setAwbOtherChargePaymentType(otherChargePaymentType);
        revised.setOrigin(ORIGIN_AIRPORT);
        revised.setDestination(DESTINATION_AIRPORT);
        var customerDetail = getCcaCustomerDetailVO(customerType);
        customerDetail.setStationCode(revised.getDestination());
        customerDetail.setCustomerTypeCode(CUSTOMER_TYPE_CC_COLLECTOR);
        revised.setCcaCustomerDetails(Set.of(customerDetail));

        var original = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_ORIGINAL);

        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now(), CcaStatus.N);
        ccaMasterVO.setOriginalShipmentVO(original);
        ccaMasterVO.setRevisedShipmentVO(revised);

        return ccaMasterVO;
    }

    @NotNull
    public static CcaDataFilter getCcaDataFilter(final String shipmentPrefix, final String masterDocumentNumber,
                                                 final String ccaReferenceNumber, final String companyCode) {
        final var ccaDataFilter = new CcaDataFilter();
        ccaDataFilter.setShipmentPrefix(shipmentPrefix);
        ccaDataFilter.setMasterDocumentNumber(masterDocumentNumber);
        ccaDataFilter.setCcaReferenceNumber(ccaReferenceNumber);
        ccaDataFilter.setCompanyCode(companyCode);
        return ccaDataFilter;
    }

    @NotNull
    public static CCAPrintFilterModel getReportFilterModel(final String reportCode,
                                                           final String shipmentPrefix,
                                                           final String masterDocumentNumber) {
        final var reportFilterModel = new CCAPrintFilterModel();
        reportFilterModel.setShipmentPrefix(reportCode);
        reportFilterModel.setMasterDocumentNumber(masterDocumentNumber);
        reportFilterModel.setShipmentPrefix(shipmentPrefix);
        return reportFilterModel;
    }

    @NotNull
    public static CCAFilterVO getCCAFilterVO(final String shipmentPrefix,
                                             final String masterDocumentNumber,
                                             final String setCcaRefNumber) {
        final var ccaFilterVO = new CCAFilterVO();
        ccaFilterVO.setShipmentPrefix(shipmentPrefix);
        ccaFilterVO.setMasterDocumentNumber(masterDocumentNumber);
        ccaFilterVO.setCcaRefNumber(setCcaRefNumber);
        return ccaFilterVO;
    }

    @NotNull
    public static CcaMaster getBasicMockCcaMaster(final String ccaReferenceNumber, final String masterDocumentNumber) {
        return getBasicMockCcaMaster(ccaReferenceNumber, masterDocumentNumber, CcaStatus.A);
    }
    @NotNull
    public static  LocalDateTime getIssueDateTimeInUtc(){
        LocalDateTime time = LocalDateTime.now(ZoneId.of(UTC));
        return time;
    }
    @NotNull
    public static CcaMaster getBasicMockCcaMaster(final String ccaReferenceNumber,
                                                  final String masterDocumentNumber,
                                                  final CcaStatus ccaStatus) {
        final var ccaMaster = new CcaMaster();
        ccaMaster.setCcaReferenceNumber(ccaReferenceNumber);
        ccaMaster.setMasterDocumentNumber(masterDocumentNumber);
        ccaMaster.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMaster.setCcaType(CCA_TYPE_INTERNAL);
        ccaMaster.setCompanyCode(COMPANY_CODE);
        ccaMaster.setCcaStatus(ccaStatus);
        ccaMaster.setCcaIssueDateTimeInUTC(getIssueDateTimeInUtc());
        return ccaMaster;
    }

    @NotNull
    public static CcaMaster getFullMockCcaMaster() {
        final var ccaMaster = getBasicMockCcaMaster("CCA00001", "77803840");

        final var eQuantityFirst = getWeightQuantity(100);
        final var eQuantitySecond = getWeightQuantity(105);

        final var originalAwbPk = getCcaAwbPk(CCA_RECORD_TYPE_ORIGINAL);
        final var revisedAwbPk = getCcaAwbPk(CCA_RECORD_TYPE_REVISED);

        final var originalAwb = getCCAAwb(ccaMaster, eQuantityFirst, getVolumeQuantity(1.6),
                eQuantityFirst, eQuantityFirst, eQuantityFirst, originalAwbPk, 111
        );

        final var revisedAwb = getCCAAwb(ccaMaster, eQuantitySecond, getVolumeQuantity(1.2),
                eQuantitySecond, eQuantitySecond, eQuantitySecond, revisedAwbPk, 222
        );

        ccaMaster.setCcaAwb(Set.of(originalAwb, revisedAwb));
        return ccaMaster;
    }

    @NotNull
    private static CCAAwb getCCAAwb(final CcaMaster ccaMaster, final Quantity<Weight> weight, final Quantity<Volume> volume,
                                    final Quantity<Weight> volumetricWeight, final Quantity<Weight> adjustedWeight,
                                    final Quantity<Weight> chargeableWeight, final CCAAwbPk ccaAwbPk, final double totalNonAWBCharge) {
        final var awb = new CCAAwb();
        awb.setCcaMaster(ccaMaster);
        awb.setWeight(weight.getValue().doubleValue());
        awb.setDisplayWeight(weight.getDisplayValue().doubleValue());
        awb.setVolume(volume.getValue().doubleValue());
        awb.setDisplayVolume(volume.getDisplayValue().doubleValue());
        awb.setVolumetricWeight(volumetricWeight.getValue().doubleValue());
        awb.setDisplayVolumetricWeight(volumetricWeight.getDisplayValue().doubleValue());
        awb.setAdjustedWeight(adjustedWeight.getValue().doubleValue());
        awb.setDisplayAdjustedWeight(adjustedWeight.getDisplayValue().doubleValue());
        awb.setChargeableWeight(chargeableWeight.getValue().doubleValue());
        awb.setDisplayChargeableWeight(chargeableWeight.getDisplayValue().doubleValue());
        awb.setPayType("PP");
        awb.setCcaAwbPk(ccaAwbPk);
        awb.setCompanyCode(COMPANY_CODE);
        awb.setCurrency("EUR");
        awb.setOrigin("CDG");
        awb.setDestination("DXB");
        awb.setPieces(14);
        awb.setTotalNonAWBCharge(totalNonAWBCharge);

        final var eUnitOfQuantity = new EUnitOfQuantity();
        eUnitOfQuantity.setVolume(volume.getDisplayUnit().getSymbol());
        eUnitOfQuantity.setWeight(volumetricWeight.getDisplayUnit().getSymbol());
        awb.setUnitOfQuantity(eUnitOfQuantity);

        awb.setAwbDetails(Set.of(getCCAAwbDetail(awb)));

        final var rateDetail = new CCAAwbRateDetail();
        rateDetail.setCcaawb(awb);
        awb.setAwbRates(Set.of(rateDetail));

        final var orgChgDetail = new CCAAwbChargeDetail();
        orgChgDetail.setCcaawb(awb);
        awb.setAwbCharges(Set.of(orgChgDetail));

        awb.setCcaCustomerDetails(Stream.of(getCcaCustomerDetail(CustomerType.I, awb, null, "inbound "),
                getCcaCustomerDetail(CustomerType.O, awb, "B", "outbound "),
                getCcaCustomerDetail(CustomerType.A, awb, null, "agent ")).collect(Collectors.toSet()));
        return awb;
    }

    @NotNull
    private static CCACustomerDetail getCcaCustomerDetail(CustomerType customerType, CCAAwb awb, String cassIndicator, String cusName) {
        final var ccaCustomerDetail = new CCACustomerDetail();
        ccaCustomerDetail.setCustomerType(customerType);
        ccaCustomerDetail.setCustomerName(cusName + customerType);
        ccaCustomerDetail.setCountryCode("FR" + customerType);
        ccaCustomerDetail.setAccountNumber("90004 " + customerType);
        ccaCustomerDetail.setIataCode("12345678910 " + customerType);
        ccaCustomerDetail.setStationCode("CDG" + customerType);
        ccaCustomerDetail.setCcaawb(awb);
        ccaCustomerDetail.setCassIndicator(cassIndicator);
        return ccaCustomerDetail;
    }

    @NotNull
    private static CCAAwbPk getCcaAwbPk(final String ccaRecordType) {
        final var ccaAwbPk = new CCAAwbPk();
        ccaAwbPk.setRecordType(ccaRecordType);
        return ccaAwbPk;
    }

    @NotNull
    private static CCAAwbDetail getCCAAwbDetail(final CCAAwb ccaawb) {
        final var awbDetail = new CCAAwbDetail();
        awbDetail.setCcaawb(ccaawb);
        final var weightQuantity = getWeightQuantity(101);
        final var weight = weightQuantity.getValue().doubleValue();
        final var displayWeight = weightQuantity.getDisplayValue().doubleValue();
        awbDetail.setWeightOfShipment(weight);
        awbDetail.setDisplayWeightOfShipment(displayWeight);
        final var volumeQuantity = getVolumeQuantity(0.2);
        awbDetail.setVolumeOfShipment(volumeQuantity.getValue().doubleValue());
        awbDetail.setDisplayVolumeOfShipment(volumeQuantity.getDisplayValue().doubleValue());
        awbDetail.setChargeableWeight(weight);
        awbDetail.setDisplayChargeableWeight(displayWeight);
        awbDetail.setVolumetricWeight(weight);
        awbDetail.setDisplayVolumetricWeight(displayWeight);
        awbDetail.setAdjustedWeight(weight);
        awbDetail.setDisplayAdjustedWeight(displayWeight);
        awbDetail.setCcaawb(ccaawb);
        awbDetail.setUnitOfQuantity(ccaawb.getUnitOfQuantity());
        return awbDetail;
    }

    @NotNull
    private static CcaAwbVO getBasicMockCcaAwbVO(final String shipmentPrefix, final String masterDocumentNumber,
                                                 final boolean isQualityAudited, final String qualityAuditStatus,
                                                 final Collection<CcaRatingDetailVO> ratingDetails, final String triggerPoint,
                                                 final Collection<CcaChargeDetailsVO> awbCharges,
                                                 final Collection<CcaRateDetailsVO> awbRates, final String recordType,
                                                 Quantity<Weight> weight, Quantity<Volume> volume,
                                                 Quantity<Weight> adjustedWeight, Quantity<Weight> chargeableWeight) {
        final var ccaAwbVO = new CcaAwbVO();
        ccaAwbVO.setShipmentPrefix(shipmentPrefix);
        ccaAwbVO.setMasterDocumentNumber(masterDocumentNumber);
        ccaAwbVO.setQualityAudited(isQualityAudited);
        ccaAwbVO.setQualityAuditStatus(qualityAuditStatus);
        ccaAwbVO.setRatingDetails(ratingDetails);
        ccaAwbVO.setTriggerPoint(triggerPoint);
        ccaAwbVO.setAwbCharges(awbCharges);
        ccaAwbVO.setAwbRates(awbRates);
        ccaAwbVO.setRecordType(recordType);
        ccaAwbVO.setWeight(weight);
        ccaAwbVO.setAdjustedWeight(adjustedWeight);
        ccaAwbVO.setVolumetricWeight(weight);
        ccaAwbVO.setChargeableWeight(chargeableWeight);
        ccaAwbVO.setVolume(volume);

        final var units = new Units();
        units.setWeight(weight.getDisplayUnit().getSymbol());
        units.setVolume(volume.getDisplayUnit().getSymbol());

        ccaAwbVO.setAgentCode("AIEDXB");
        ccaAwbVO.setInboundCustomerCode("DHLCDG001");
        ccaAwbVO.setOutboundCustomerCode("DHLABR");
        return ccaAwbVO;
    }

    @NotNull
    public static List<CustomerModel> getCustomerModels(String customerCode, String customerName, String stationCode, String countryCode, String accountNumber, String iataCode) {
        final var customerModel = new CustomerModel();
        customerModel.setCustomerCode(customerCode);
        customerModel.setCustomerName(customerName);
        customerModel.setStationCode(stationCode);
        customerModel.setCountryCode(countryCode);
        customerModel.setAccountNumber(accountNumber);
        customerModel.setIataCode(iataCode);
        return List.of(customerModel);
    }

    @NotNull
    public static CcaCustomerDetailVO getCcaCustomerDetailVO(final CustomerType customerType) {
        final var ccaCustomerDetailVO = new CcaCustomerDetailVO();
        ccaCustomerDetailVO.setCustomerType(customerType);
        ccaCustomerDetailVO.setCustomerName("DHL WORLDWIDE INTERNATIONAL");
        ccaCustomerDetailVO.setAccountNumber("423523523523");
        ccaCustomerDetailVO.setCountryCode("FR");
        ccaCustomerDetailVO.setIataCode("1321");
        ccaCustomerDetailVO.setStationCode("WDF");
        return ccaCustomerDetailVO;
    }

    @NotNull
    public static Quantity<Weight> getWeightQuantity(final double systemValue) {
        return QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(systemValue));
    }

    @NotNull
    public static Quantity<Volume> getVolumeQuantity(final double systemValue) {
        return QUANTITIES.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(systemValue));
    }

    @NotNull
    private static Quantity<Volume> getDimensionQuantity(final double systemValue) {
        return QUANTITIES.getQuantity(Quantities.DIMENSION, BigDecimal.valueOf(systemValue));
    }

    public static CCAWorkflowVO getCcaWorkflowVO(CcaStatus ccaStatus) {
        var wf = new CCAWorkflowVO();
        wf.setCcaStatus(ccaStatus);
        wf.setUserName("Mr.Freeze");
        wf.setRequestedDate(LocalDateTime.now());
        return wf;
    }

    public static CCAWorkflowData getCcaWorkflowData(CcaStatus ccaStatus) {
        var wf = new CCAWorkflowData();
        wf.setCcaStatus(ccaStatus);
        wf.setUserName("Mr.Freeze");
        wf.setRequestedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yy - hh:mm")));
        return wf;
    }

    @NotNull
    public static CcaAwbVO getFullMockCcaAwbVO(final String shipmentPrefix,
                                               final String masterDocumentNumber,
                                               final String recordType) {
        final var weight = getWeightQuantity(100.5);
        final var adjustedWeight = getWeightQuantity(20);
        final var chargeableWeight = getWeightQuantity(75);
        final var volume = getVolumeQuantity(0.5);

        final var dimensionsVOS = Set.of(getFullMockDimensions());
        final var ccaRatingUldVOS = Set.of(getFullMockUld());
        final var ratingDetailVO = getCcaRatingDetailVO(weight, weight, volume, weight, weight, "PER", dimensionsVOS, ccaRatingUldVOS);

        var money = getMoney(0.0, CURRENCY_USD);
        final var chargeVO1 = getCcaChargeDetailsVO("PP", "flat charge", money, "FC",
                true, false);

        final var chargeVO2 = getCcaChargeDetailsVO("PP", "flat charge", money, "FD",
                false, true);

        final var rateDetailVO1 = getCcaRateDetailsVO(money, 10.5, "GEN", COMPANY_CODE,
                10.5, 1.0, IATA_TYPE);

        final var rateDetailVO2 = getCcaRateDetailsVO(money, 10.5, "APL", COMPANY_CODE,
                10.5, 1.0, MKT_TYPE);

        var ccaAwbVO = getBasicMockCcaAwbVO(shipmentPrefix, masterDocumentNumber, true, QUALITY_AUDITED,
                Set.of(ratingDetailVO), TRIGGER_QUALITY_AUDIT, Set.of(chargeVO1, chargeVO2),
                Set.of(rateDetailVO1, rateDetailVO2), recordType, weight, volume, adjustedWeight, chargeableWeight);

        ccaAwbVO.setPayType(CcaConstants.PAYMENT_TYPE_PRE_PAID);
        ccaAwbVO.setAwbOtherChargePaymentType(CcaConstants.PAYMENT_TYPE_PRE_PAID);
        ccaAwbVO.setSpotRateId(CCA_AWB_SPOT_RATE_ID);
        ccaAwbVO.setSpotRateStatus(CcaTaxCommissionConstants.SPOT_RATE_VERIFIED_STATUS);
        ccaAwbVO.setChargeableWeight(weight);
        ccaAwbVO.setAdjustedWeight(adjustedWeight);
        ccaAwbVO.setWeight(weight);
        ccaAwbVO.setExportBillingStatus(CcaTaxCommissionConstants.BLG_STA_EXP);
        ccaAwbVO.setHandlingCode("SCC");
        ccaAwbVO.setNetValueImport(getMoney(1.0, "USD"));
        ccaAwbVO.setNetValueExport(getMoney(2.0, "USD"));

        return ccaAwbVO;
    }

    @NotNull
    private static CcaRatingDetailVO getCcaRatingDetailVO(final Quantity<Weight> weightOfShipment, final Quantity<Weight> chargeableWeight,
                                                          final Quantity<Volume> volumeOfShipment,
                                                          final Quantity<Weight> volumetricWeight, final Quantity<Weight> adjustedWeight,
                                                          String commodityCode, Set<DimensionsVO> dimensions, Set<CcaRatingUldVO> ulds) {
        final var ratingDetailVO = new CcaRatingDetailVO();
        ratingDetailVO.setWeightOfShipment(weightOfShipment);
        ratingDetailVO.setChargeableWeight(chargeableWeight);
        ratingDetailVO.setVolumeOfShipment(volumeOfShipment);
        ratingDetailVO.setVolumetricWeight(volumetricWeight);
        ratingDetailVO.setAdjustedWeight(adjustedWeight);
        ratingDetailVO.setCommodityCode(commodityCode);
        ratingDetailVO.setDimensions(dimensions);
        ratingDetailVO.setUlds(ulds);
        return ratingDetailVO;
    }

    @NotNull
    public static CcaRateDetailsVO getCcaRateDetailsVO(final Money charge, final double chargeableWeight,
                                                       final String commodityCode, final String companyCode,
                                                       final double displayChargeableWeight, final double rate,
                                                       final String rateType) {
        final var ccaRateDetailsVO = new CcaRateDetailsVO();
        ccaRateDetailsVO.setCharge(charge);
        ccaRateDetailsVO.setChargeableWeight(chargeableWeight);
        ccaRateDetailsVO.setCommodityCode(commodityCode);
        ccaRateDetailsVO.setCompanyCode(companyCode);
        ccaRateDetailsVO.setDisplayChargeableWeight(displayChargeableWeight);
        ccaRateDetailsVO.setRate(rate);
        ccaRateDetailsVO.setRateType(rateType);
        ccaRateDetailsVO.setRateDetails(getRateClassJson());

        return ccaRateDetailsVO;
    }

    @NotNull
    public static CcaChargeDetailsVO getCcaChargeDetailsVO(final String paymentType, final String chargeHead,
                                                           final Money charge, final String chargeHeadCode,
                                                           final boolean dueCarrier, final boolean dueAgent) {
        final var chargeVO = new CcaChargeDetailsVO();
        chargeVO.setPaymentType(paymentType);
        chargeVO.setChargeHead(chargeHead);
        chargeVO.setCharge(charge);
        chargeVO.setChargeHeadCode(chargeHeadCode);
        chargeVO.setDueCarrier(dueCarrier);
        chargeVO.setDueAgent(dueAgent);
        return chargeVO;
    }

    @NotNull
    public static CcaChargeDetailsVO getCcaChargeWithoutTypeAndValue() {
        return getCcaChargeDetailsVO(
                "PP",
                "Dangerous Charge",
                null,
                "DC",
                false,
                false
        );
    }

    @NotNull
    public static Units getUnitOfMeasure(final String weight, final String volume,
                                         final String length, final String currencyCode) {
        var unitOfMeasure = new Units();
        unitOfMeasure.setWeight(weight);
        unitOfMeasure.setVolume(volume);
        unitOfMeasure.setLength(length);
        unitOfMeasure.setCurrencyCode(currencyCode);
        return unitOfMeasure;
    }

    @NotNull
    public static Units getDefaultUnitsOfMeasure() {
        return getUnitOfMeasure("K", "B", "C", CURRENCY_USD);
    }

    @NotNull
    public static CCAAWBDetailData getCCAAWBDetailData(final String currency, final String payType, final Double weight,
                                                       final Double volume, final Double volumetricWeight,
                                                       final Double adjustedWeight, final Double chargeableWeight, final Double iataRate,
                                                       final Double marketRate, final Double weightCharge, final Double valuationCharge,
                                                       final Double netCharge, final Double discountAmount, final Double commissionAmount,
                                                       final String outboundCustomerCode, final String origin, final String destination,
                                                       final int pieces, String awbOtherChargePaymentType, String inboundCustomerCode, String agentCode) {
        var ccaAWBDetailData = new CCAAWBDetailData();
        ccaAWBDetailData.setCurrency(currency);
        ccaAWBDetailData.setPayType(payType);
        ccaAWBDetailData.setWeight(weight);
        ccaAWBDetailData.setAwbOtherChargePaymentType(awbOtherChargePaymentType);
        ccaAWBDetailData.setWeight(weight);
        ccaAWBDetailData.setVolume(volume);
        ccaAWBDetailData.setVolumetricWeight(volumetricWeight);
        ccaAWBDetailData.setAdjustedWeight(adjustedWeight);
        ccaAWBDetailData.setChargeableWeight(chargeableWeight);
        ccaAWBDetailData.setIataRate(iataRate);
        ccaAWBDetailData.setMarketRate(marketRate);
        ccaAWBDetailData.setWeightCharge(weightCharge);
        ccaAWBDetailData.setValuationCharge(valuationCharge);
        ccaAWBDetailData.setNetCharge(netCharge);
        ccaAWBDetailData.setDiscountAmount(discountAmount);
        ccaAWBDetailData.setCommissionAmount(commissionAmount);
        ccaAWBDetailData.setAgentCode(agentCode);
        ccaAWBDetailData.setInboundCustomerCode(inboundCustomerCode);
        ccaAWBDetailData.setOutboundCustomerCode(outboundCustomerCode);
        ccaAWBDetailData.setOrigin(origin);
        ccaAWBDetailData.setDestination(destination);
        ccaAWBDetailData.setPieces(pieces);
        return ccaAWBDetailData;
    }

    @NotNull
    public static RelatedCCAData getRelatedCCAData(final String masterDocumentNumber, final String shipmentPrefix,
                                                   final String ccaReferenceNumber) {
        final var relatedCCA = new RelatedCCAData();
        relatedCCA.setMasterDocumentNumber(masterDocumentNumber);
        relatedCCA.setShipmentPrefix(shipmentPrefix);
        relatedCCA.setCcaReferenceNumber(ccaReferenceNumber);
        return relatedCCA;
    }

    @NotNull
    public static CcaValidationData getCcaValidationData(final String masterDocumentNumber, final String shipmentPrefix,
                                                         final String ccaReferenceNumber, final String statusMessage) {
        final var ccaValidationData = new CcaValidationData();
        ccaValidationData.setMasterDocumentNumber(masterDocumentNumber);
        ccaValidationData.setShipmentPrefix(shipmentPrefix);
        ccaValidationData.setCcaReferenceNumber(ccaReferenceNumber);
        ccaValidationData.setStatusMessage(statusMessage);
        return ccaValidationData;
    }

    @NotNull
    public static CcaAwbDetailDimensions getFullMockCCAAwbDetailDimensions(final CCAAwbDetail ccaAwbDetail) {
        final var weight = getWeightQuantity(101);
        final var volume = getVolumeQuantity(0.2);
        final var dimension = getDimensionQuantity(101);

        final var units = new EUnitOfMeasure();
        units.setWeight(weight.getDisplayUnit().getSymbol());
        units.setVolume(volume.getDisplayUnit().getSymbol());
        units.setLength(dimension.getDisplayUnit().getSymbol());

        final var ccaAwbDetailDimensions = new CcaAwbDetailDimensions();
        ccaAwbDetailDimensions.setUnitOfMeasure(units);
        ccaAwbDetailDimensions.setPieces(15);
        ccaAwbDetailDimensions.setCcaAwbDetail(ccaAwbDetail);
        ccaAwbDetailDimensions.setWeight(weight.getValue().doubleValue());
        ccaAwbDetailDimensions.setDisplayWeight(weight.getDisplayValue().doubleValue());
        ccaAwbDetailDimensions.setVolume(volume.getValue().doubleValue());
        ccaAwbDetailDimensions.setDisplayVolume(volume.getDisplayValue().doubleValue());
        ccaAwbDetailDimensions.setHeight(dimension.getValue().doubleValue());
        ccaAwbDetailDimensions.setDisplayHeight(dimension.getDisplayValue().doubleValue());
        ccaAwbDetailDimensions.setLength(dimension.getValue().doubleValue());
        ccaAwbDetailDimensions.setDisplayLength(dimension.getDisplayValue().doubleValue());
        ccaAwbDetailDimensions.setWidth(dimension.getValue().doubleValue());
        ccaAwbDetailDimensions.setDisplayWidth(dimension.getDisplayValue().doubleValue());
        return ccaAwbDetailDimensions;
    }

    @NotNull
    public static DimensionsVO getFullMockDimensions() {
        final var dimensionsVO = new DimensionsVO();
        dimensionsVO.setPieces(22);
        return dimensionsVO;
    }

    private static CcaRatingUldVO getFullMockUld() {
        final var weight = getWeightQuantity(100.5);
        final var volume = getVolumeQuantity(0.5);
        final var uldVO = new CcaRatingUldVO();
        uldVO.setNumberOfUld(100);
        uldVO.setVolume(volume);
        uldVO.setWeight(weight);
        return uldVO;
    }

    @NotNull
    private static CcaAwbRoutingDetails getFullMockCCAAwbRoutingDetails(final CCAAwb ccaawb) {
        final var weight = getWeightQuantity(101);

        final var routingDetails = new CcaAwbRoutingDetails();
        routingDetails.setCcaawb(ccaawb);
        routingDetails.setOrigin("or");
        routingDetails.setDestination("de");
        routingDetails.setFlightCarrierCode("AV");
        routingDetails.setWeight(weight.getValue().doubleValue());
        routingDetails.setDisplayWeight(weight.getDisplayValue().doubleValue());
        routingDetails.setDisplayWeightUnit(weight.getDisplayUnit().getSymbol());
        routingDetails.setFlightNumber("fn");
        routingDetails.setFlightDate(LocalDate.now());
        routingDetails.setPieces(456);
        routingDetails.setFirstCarrierCode("AV");
        routingDetails.setSource("src");
        routingDetails.setArgumentType("arg");
        return routingDetails;
    }

    @NotNull
    public static AuditVO getMockCcaAuditVO(CCAMasterVO ccaMasterVO) {
        var auditVO = new AuditVO();

        final var utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
        auditVO.setActionDateTimeUTC(utcNow.toLocalDateTime());
        auditVO.setActionDateTimeStation(utcNow.toLocalDateTime());
        auditVO.setActionDateTime(Date.from(utcNow.toInstant()));

        auditVO.setActionCode("cca Updated");
        auditVO.setActionType(AuditEntryType.UPDATED);
        auditVO.setBusinessObject(ccaMasterVO);
        auditVO.setEntityId("CcaMaster-" + ccaMasterVO.getBusinessId());

        auditVO.setLoginProfile(getLoginProfile());
        auditVO.setLoggedInUser(ICO_ADMIN);

        auditVO.setListener(CCA_AUDIT_EVENT_LISTENER_NAME);
        auditVO.setEventName(CCA_AUDIT_EVENT_NAME);
        auditVO.setAuditGroup(CCA_MASTER_GROUP);

        return auditVO;
    }

    public static LoginProfile getLoginProfile() {
        var profile = new LoginProfile();
        profile.setCompanyCode(COMPANY_CODE);
        profile.setTimeZone("UTC");
        profile.setStationCode("CDG");
        profile.setStation_code("CDG");
        profile.setUserId(ICO_ADMIN);
        profile.setRoleGroupCode(ICO_ADMIN);
        profile.setOwnAirlineCode(COMPANY_CODE);
        profile.setOwnAirlineIdentifier(Integer.parseInt("1" + SHIPMENT_PREFIX));

        return profile;
    }

    @NotNull
    public static AuditEventList getMockCcaAuditListWithoutChanges(String masterNumber, String ccaNumber) {
        var ccaMasterVO = getCCAMasterVO(masterNumber, ccaNumber, LocalDate.now());
        var auditVO = getMockCcaAuditVO(ccaMasterVO);

        return new AuditEventList(
                mock(AuditEventPublisher.class),
                List.of(new AuditEvent(auditVO, CCA_AUDIT_EVENT_NAME)),
                Set.of(CCA_AUDIT_EVENT_NAME),
                Set.of(CCA_AUDIT_EVENT_LISTENER_NAME)
        );
    }

    @NotNull
    public static AuditEventList getMockCcaAuditListWithChangedStatus(CcaChangedStatusVO changedStatusData) {
        var ccaMasterVO = getCCAMasterVO(changedStatusData.getMasterNumber(), changedStatusData.getCcaNumber(), LocalDate.now());
        ccaMasterVO.setCcaStatus(CcaStatus.getCcaStatusByName(changedStatusData.getNewStatus()));
        var auditVO = getMockCcaAuditVO(ccaMasterVO);
        auditVO.setChangeGroupName(CCA_MASTER_GROUP);

        var changedField = new AuditFieldVO();
        changedField.setField(CCA_STATUS);
        changedField.setChangeGroupId(CCA_MASTER_GROUP);
        changedField.setFieldModified(true);
        changedField.setOldValue(changedStatusData.getOldStatus());
        changedField.setNewValue(changedStatusData.getNewStatus());
        auditVO.setAuditFieldVOs(List.of(changedField));

        return new AuditEventList(
                mock(AuditEventPublisher.class),
                List.of(new AuditEvent(auditVO, CCA_AUDIT_EVENT_NAME)),
                Set.of(CCA_AUDIT_EVENT_NAME),
                Set.of(CCA_AUDIT_EVENT_LISTENER_NAME)
        );
    }

    @NotNull
    public static AuditEventList getMockCcaAuditListWithChangedType(String masterNumber, String ccaNumber) {
        var ccaMasterVO = getCCAMasterVO(masterNumber, ccaNumber, LocalDate.now());
        var auditVO = getMockCcaAuditVO(ccaMasterVO);
        auditVO.setChangeGroupName(CCA_MASTER_GROUP);

        var changedField = new AuditFieldVO();
        changedField.setField(CCA_TYPE);
        changedField.setChangeGroupId(CCA_MASTER_GROUP);
        changedField.setFieldModified(true);
        changedField.setOldValue(CCA_TYPE_INTERNAL);
        changedField.setNewValue(CCA_TYPE_ACTUAL);
        auditVO.setAuditFieldVOs(List.of(changedField));

        return new AuditEventList(
                mock(AuditEventPublisher.class),
                List.of(new AuditEvent(auditVO, CCA_AUDIT_EVENT_NAME)),
                Set.of(CCA_AUDIT_EVENT_NAME),
                Set.of(CCA_AUDIT_EVENT_LISTENER_NAME)
        );
    }

    @NotNull
    public static Money getMoney(Double amount, String currencyCode) {
        var moneyVO = new MoneyVO();
        moneyVO.setAlternateCurrencyCode(currencyCode);
        moneyVO.setCurrencyCode(currencyCode);
        moneyVO.setCurrencyName(currencyCode);
        moneyVO.setPrecisionType("R");
        moneyVO.setPrecisionValue(2);
        moneyVO.setWeekCurrencyIndicator("Y");
        moneyVO.setRoundingType("H");
        moneyVO.setRoundingUnit(0.05);

        var money = new Money(moneyVO);
        money.setAmount(BigDecimal.valueOf(amount));

        return money;
    }

    @NotNull
    public static CcaCustomerDetailData getCcaCustomerDetailData() {
        final var ccaCustomerDetailData = new CcaCustomerDetailData();
        ccaCustomerDetailData.setCassIndicator("c");
        ccaCustomerDetailData.setStationCode("S");
        ccaCustomerDetailData.setIataCode("I");
        ccaCustomerDetailData.setSerialNumber(1L);
        ccaCustomerDetailData.setAccountNumber("123");
        ccaCustomerDetailData.setCustomerName("cus name");
        ccaCustomerDetailData.setCountryCode("USA");
        return ccaCustomerDetailData;
    }

    @NotNull
    public static List<CcaNumbersNodeVO> getCcaNumbersDataVO(List<Pair<Long, String>> ccaIdAndNumbers) {
        return ccaIdAndNumbers.stream()
                .map(ccaIdAndNumber -> new CcaNumbersNodeVO(ccaIdAndNumber.getLeft(), ccaIdAndNumber.getRight()))
                .collect(Collectors.toList());
    }

    @NotNull
    public static List<CcaNumbersNode> getCcaNumbersData(List<Pair<Long, String>> ccaIdAndNumbers) {
        return ccaIdAndNumbers.stream()
                .map(ccaIdAndNumber -> new CcaNumbersNode(ccaIdAndNumber.getLeft(), ccaIdAndNumber.getRight()))
                .collect(Collectors.toList());
    }

    @NotNull
    public static TaxFilterVO getTaxFilterVO() {
        var taxFilterVO = new TaxFilterVO();
        taxFilterVO.setCompanyCode(COMPANY_CODE);
        taxFilterVO.setCurrencyCode(CURRENCY_USD);
        taxFilterVO.setCargoType(TaxFilterConstant.CARGOTYPE_CARGO);
        taxFilterVO.setMasterDocumentNumber(MASTER_DOCUMENT_NUMBER);
        taxFilterVO.setShipmentPrefix(SHIPMENT_PREFIX);
        return taxFilterVO;
    }

    @NotNull
    public static CCAMasterData getCcaMasterData() {
        final var ccaAWBDetailData = getCCAAWBDetailData("EUR", "PP", 2508.2, 0.1, 0.1, 0.1, 1575.0,
                0.0, 2508.5, 0.0, 0.0, 148703.88, 0.0,
                0.0, "DHLABRAJ", "CDG", "DXB", 15, "CC", "FFFABRAJ", "AIEDXB");
        ccaAWBDetailData.setRatingDetails(List.of(new CcaDetailData()));
        ccaAWBDetailData.setAwbCharges(List.of(new CcaChargeDetailData()));
        ccaAWBDetailData.setAwbRates(List.of(new CcaRateDetailData()));
        ccaAWBDetailData.setTotalDueAgtCCFChg(12.00);
        ccaAWBDetailData.setTotalOtherCharges(56.36);

        final var ccaMasterData = new CCAMasterData();
        ccaMasterData.setCompanyCode(COMPANY_CODE);
        ccaMasterData.setDocumentOwnerId(1134);
        ccaMasterData.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMasterData.setMasterDocumentNumber("77803840");
        ccaMasterData.setCcaType(CCA_TYPE_INTERNAL);
        ccaMasterData.setCcaStatus(CcaStatus.N);
        ccaMasterData.setOriginalAWBData(ccaAWBDetailData);
        ccaMasterData.setRevisedAWBData(ccaAWBDetailData);
        ccaMasterData.setCcaRemarks("add new CCA");
        ccaMasterData.setCcaIssueDate("10-10-2021");
        return ccaMasterData;
    }

    @NotNull
    public static ObjectNode getRateClassJson() {
        var json = new ObjectMapper().createObjectNode();
        json.put("rate_class", "Q");
        return json;
    }

    public static CcaSelectFilter getCcaSelectFilter(boolean loadFromFilter, String search, List<String> filter) {
        var ccaSelectFilter = new CcaSelectFilter();
        ccaSelectFilter.setFirst(3);
        ccaSelectFilter.setLoadFromFilter(loadFromFilter);
        ccaSelectFilter.setSearch(search);
        ccaSelectFilter.setFilter(filter);
        return ccaSelectFilter;
    }

    @NotNull
    public static CcaCustomerDetailVO getCcaCustomerDetailVO(String customerName, String stationCode, String countryCode,
                                                             String accountNumber, String iataCode, CustomerType customerType) {
        final var ccaCustomerDetailVO = new CcaCustomerDetailVO();
        ccaCustomerDetailVO.setCustomerType(customerType);
        ccaCustomerDetailVO.setCustomerName(customerName);
        ccaCustomerDetailVO.setStationCode(stationCode);
        ccaCustomerDetailVO.setCountryCode(countryCode);
        ccaCustomerDetailVO.setAccountNumber(accountNumber);
        ccaCustomerDetailVO.setIataCode(iataCode);
        return ccaCustomerDetailVO;
    }
}
