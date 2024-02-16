package com.ibsplc.neoicargo.cca.component.feature.report;

import com.ibsplc.neoicargo.cca.component.feature.getavailablereasoncodes.GetAvailableReasonCodesFeature;
import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.mapper.CcaPrintMapper;
import com.ibsplc.neoicargo.cca.modal.AvailableReasonCodeData;
import com.ibsplc.neoicargo.cca.modal.CCAPrintModel;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CCAPrintVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAPrintFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.report.constant.OutputFormat;
import com.ibsplc.neoicargo.framework.report.constant.OutputType;
import com.ibsplc.neoicargo.framework.report.exception.ReportException;
import com.ibsplc.neoicargo.framework.report.jasper.JasperReportController;
import com.ibsplc.neoicargo.framework.report.vo.InputInfo;
import com.ibsplc.neoicargo.framework.report.vo.OutputInfo;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;

@Slf4j
@Component
@FeatureConfigSource("cca/ccaprint")
@AllArgsConstructor
public class CCAPrintFeature extends AbstractFeature<CCAPrintFilterVO> {

    private static final String CCA_001 = "CCA001";
    private static final String TAXES = "Taxes";
    private static final String PDF_FILE_EXTENSION = ".pdf";

    private final CcaDao ccaDao;
    private final CcaPrintMapper ccaPrintMapper;
    private final JasperReportController jasperReportController;
    private final GetAvailableReasonCodesFeature getAvailableReasonCodesFeature;
    private final AirlineWebComponent airlineWebComponent;

    @Override
    protected CCAPrintModel perform(CCAPrintFilterVO reportFilterVO) throws BusinessException {
        log.info("CcaReportFeature invoked");
        final var ccaDataFilter = generateCcaDataFilter(reportFilterVO);
        try {
            var params = generateParams(ccaDataFilter);
            byte[] bytes = jasperReportController.generateReport(getInput(params), getOutput());

            log.info("Successfully completed the export");
            return createCcaPrintModel(reportFilterVO, bytes);
        } catch (ReportException e) {
            throw new BusinessException(e);
        }
    }

    @NotNull
    private OutputInfo getOutput() {
        var output = new OutputInfo();
        output.setOutputType(OutputType.VIEW_TYPE);
        output.setOutputFormat(OutputFormat.DOC_PDF);
        return output;
    }

    @NotNull
    private InputInfo getInput(Map<String, Object> params) {
        var input = new InputInfo();
        input.setReportId(CCA_001);
        input.setDetails(Collections.singletonList(CCA_001));
        input.setParameters(params);
        input.setDynamicReport(true);
        return input;
    }

    private CCAPrintModel createCcaPrintModel(CCAPrintFilterVO reportFilterVO, byte[] returnBytes) {
        var reportVO = new CCAPrintVO();
        reportVO.setGeneratedReport(returnBytes);
        reportVO.setFileName(reportFilterVO.getReportName() + PDF_FILE_EXTENSION);
        return ccaPrintMapper.constructCCAPrintModel(reportVO);
    }

    @NotNull
    private CcaDataFilter generateCcaDataFilter(CCAPrintFilterVO reportFilterVO) {
        var ccaDataFilter = new CcaDataFilter();
        ccaDataFilter.setCompanyCode(reportFilterVO.getCompanyCode());
        ccaDataFilter.setShipmentPrefix(reportFilterVO.getShipmentPrefix());
        ccaDataFilter.setMasterDocumentNumber(reportFilterVO.getMasterDocumentNumber());
        ccaDataFilter.setCcaReferenceNumber(reportFilterVO.getCcaReferenceNumber());
        return ccaDataFilter;
    }

    @NotNull
    private Map<String, Object> generateParams(CcaDataFilter ccaDataFilter) throws CcaBusinessException {
        var ccaMasterVO = Optional.ofNullable(ccaDao.getCCADetails(ccaDataFilter))
                .orElseThrow(() -> new CcaBusinessException((constructErrorVO(CcaErrors.NEO_CCA_014.getErrorCode(),
                        CcaErrors.NEO_CCA_014.getErrorMessage(), ErrorType.ERROR))));
        setAwb(ccaMasterVO);
        var params = new HashMap<String, Object>();
        putCcaMasterParams(ccaMasterVO, params);
        putParamsFromAwb(params, ccaMasterVO.getOriginalShipmentVO(), "origin");
        putParamsFromAwb(params, ccaMasterVO.getRevisedShipmentVO(), "revised");
        return params;
    }

    private void setAwb(CCAMasterVO ccaMasterVO) {
        ccaMasterVO.getShipmentDetailVOs().forEach(s -> {
            if (CCA_RECORD_TYPE_ORIGINAL.equals(s.getRecordType())) {
                ccaMasterVO.setOriginalShipmentVO(s);
            } else {
                ccaMasterVO.setRevisedShipmentVO(s);
            }
        });
    }

    private void putCcaMasterParams(CCAMasterVO ccaMaster, Map<String, Object> params) {
        //TODO Fill fields after implementation.
        params.put("indicator", null);
        params.put("ccaReferenceNumber", ccaMaster.getCcaReferenceNumber());
        params.put("agentCode", ccaMaster.getOriginalShipmentVO().getAgentCode());
        params.put("airlineCode", getAlphaAirlineCode(ccaMaster.getShipmentPrefix()));
        params.put("reportingPeriod", null);
        params.put("masterDocumentNumber", ccaMaster.getShipmentPrefix() + "-" + ccaMaster.getMasterDocumentNumber());
        params.put("origin", ccaMaster.getRevisedShipmentVO().getOrigin());
        params.put("destination", ccaMaster.getRevisedShipmentVO().getDestination());
        params.put("dateOfAWBIssue", Optional.ofNullable(ccaMaster.getCcaIssueDate())
                .map(date -> date.format(DateTimeFormatter.ofPattern("dd-MMM-yy")))
                .orElse(null));
        params.put("reasonForCCA", getCcaReason(ccaMaster));
        params.put("ccaRemarks", ccaMaster.getCcaRemarks());
        params.put("weightUnit", displayUnitName(ccaMaster.getOriginalShipmentVO().getUnitOfMeasure().getWeight()));
    }

    private String getAlphaAirlineCode(String shipmentPrefix) {
        try {
            var airlineModel = airlineWebComponent.validateNumericCode(shipmentPrefix);
            return airlineModel == null
                    ? StringUtils.EMPTY
                    : airlineModel.getAlphaCode();
        } catch (BusinessException | HttpClientErrorException e) {
            log.warn("Cannot receive alpha airline code for [{}] shipment prefix", shipmentPrefix, e);
            return StringUtils.EMPTY;
        }
    }

    private String getCcaReason(CCAMasterVO ccaMaster) {
        var ccaReasonCodes = Set.of(ccaMaster.getCcaReason().split(","));
        return  getAvailableReasonCodesFeature.perform().stream()
                .filter(data -> ccaReasonCodes.contains(data.getParameterCode()))
                .map(AvailableReasonCodeData::getParameterDescription)
                .collect(Collectors.joining(", "));
    }

    private void putParamsFromAwb(Map<String, Object> params, CcaAwbVO awb, String prefix) {
        params.put(prefix + "GrossWeight", getWeight(awb.getWeight()));
        params.put(prefix + "AdjustedWeight", getWeight(awb.getAdjustedWeight()));
        params.put(prefix + "ChargeableWeight", getWeight(awb.getChargeableWeight()));
        params.put(prefix + "VolumeWeight", getWeight(awb.getVolumetricWeight()));
        params.put(prefix + "Origin", awb.getOrigin());
        params.put(prefix + "Destination", awb.getDestination());
        params.put(prefix + "AgentName", awb.getAgentName());
        params.put(prefix + "AgentIATACode", awb.getAgentIataCode());
        params.put(prefix + "Currency", awb.getCurrency());
        params.put(prefix + awb.getPayType() + "WeightCharge", awb.getWeightCharge());
        params.put(prefix + awb.getPayType() + "ValuationCharge", awb.getValuationCharge());
        setAwbTaxAmount(params, prefix, awb);
        params.put(prefix + awb.getPayType() + "Commission", awb.getCommissionAmount());
        params.put(prefix + awb.getPayType() + "Discount", awb.getDiscountAmount());
        params.put(prefix + awb.getAwbOtherChargePaymentType() + "TotalNonAwbCharges", awb.getTotalNonAWBCharge());
        var ppType = "PP".equals(awb.getAwbOtherChargePaymentType());
        params.put(
                prefix + awb.getAwbOtherChargePaymentType() + "TotalOtherChargesDueAgent",
                ppType ? awb.getTotalDueAgtPPDChg() : awb.getTotalDueAgtCCFChg());
        params.put(
                prefix + awb.getAwbOtherChargePaymentType() + "TotalOtherChargesDueCarrier",
                ppType ? awb.getTotalDueCarPPDChg() : awb.getTotalDueCarCCFChg());
    }

    private String getWeight(Quantity<Weight> weight) {
        String unitName = weight.getDisplayUnit().getName();
        if ("K".equals(unitName)) {
            return String.format("%s kg", weight.getDisplayValue().setScale(1, RoundingMode.HALF_UP));
        } else if ("L".equals(unitName)) {
            return String.format("%s lbs", weight.getValue().toBigInteger());
        } else {
            return String.format("%s ton", weight.getValue().toBigInteger());
        }
    }

    private String displayUnitName(String weightUnit) {
        if ("K".equals(weightUnit)) {
            return "\"KG\"";
        } else if ("L".equals(weightUnit)) {
            return "\"Pound\"";
        } else {
            return "\"Ton\"";
        }
    }

    private void setAwbTaxAmount(Map<String, Object> params, String prefix, CcaAwbVO awb) {
        var payType = "" + awb.getPayType().charAt(0) + awb.getAwbOtherChargePaymentType().charAt(1);
        if ("PP".equals(payType)) {
            params.put(prefix + payType + TAXES, awb.getAwbPPTaxAmount());
        } else if ("CC".equals(payType)) {
            params.put(prefix + payType + TAXES, awb.getAwbCCTaxAmount());
        }
        if ("PC".equals(payType) || "CP".equals(payType)) {
            params.put(prefix + "PP" + TAXES, awb.getAwbPPTaxAmount());
            params.put(prefix + "CC" + TAXES, awb.getAwbCCTaxAmount());
        }
    }
}
