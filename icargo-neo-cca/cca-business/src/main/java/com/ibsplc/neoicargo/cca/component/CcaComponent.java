package com.ibsplc.neoicargo.cca.component;

import com.ibsplc.neoicargo.cca.component.feature.authorizecca.AuthorizeCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.CalculateCCATaxesFeature;
import com.ibsplc.neoicargo.cca.component.feature.deletecca.DeleteCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.getavailablereasoncodes.GetAvailableReasonCodesFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccaassignees.GetCcaAssigneesFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccalistview.GetCCAListViewFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccanumbers.GetCCANumbersFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccanumbers.GetCCAReferenceNumberFeature;
import com.ibsplc.neoicargo.cca.component.feature.getnetvalues.GetNetValuesFeature;
import com.ibsplc.neoicargo.cca.component.feature.maintainccalist.GetCCADetailsFeature;
import com.ibsplc.neoicargo.cca.component.feature.maintainccalist.GetCCAListFeature;
import com.ibsplc.neoicargo.cca.component.feature.maintainccalist.GetRelatedCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.report.CCAPrintFeature;
import com.ibsplc.neoicargo.cca.component.feature.reratecca.RerateCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.savecca.SaveAutoCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.savecca.SaveCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.updateattachments.UpdateCcaMasterAttachments;
import com.ibsplc.neoicargo.cca.component.feature.updateccaassignee.UpdateCcaAssigneeFeature;
import com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced.CCAUpdateInvoicedFeature;
import com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced.CCAUpdateVoidedFeature;
import com.ibsplc.neoicargo.cca.component.feature.validatecass.ValidateCassIndicatorFeature;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaPrintMapper;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.AvailableReasonCodeData;
import com.ibsplc.neoicargo.cca.modal.BulkActionData;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CCAPrintFilterModel;
import com.ibsplc.neoicargo.cca.modal.CCAPrintModel;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneesPage;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataRequest;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataResponse;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilterList;
import com.ibsplc.neoicargo.cca.modal.CcaListViewPageInfo;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.modal.CcaNumbersPage;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.modal.NetValuesData;
import com.ibsplc.neoicargo.cca.modal.RelatedCCAData;
import com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CcaComponent {

    private final GetCCADetailsFeature getCCADetailsFeature;
    private final SaveCCAFeature saveCCAFeature;
    private final GetRelatedCCAFeature getRelatedCCAFeature;
    private final GetCCAListFeature getCCAListFeature;
    private final ContextUtil contextUtil;
    private final DeleteCCAFeature deleteCCAFeature;
    private final AuthorizeCCAFeature authorizeCCAFeature;
    private final RerateCCAFeature rerateCCAFeature;
    private final CCAPrintFeature ccaPrintFeature;
    private final CcaMasterMapper ccaMasterMapper;
    private final CcaPrintMapper ccaPrintMapper;
    private final GetAvailableReasonCodesFeature getAvailableReasonCodesFeature;
    private final CCAUpdateInvoicedFeature ccaUpdateInvoicedFeature;
    private final GetCCAListViewFeature getCCAListViewFeature;
    private final UpdateCcaMasterAttachments updateCcaMasterAttachments;
    private final GetCCANumbersFeature getCCANumbersFeature;
    private final GetCcaAssigneesFeature getCcaAssigneesFeature;
    private final UpdateCcaAssigneeFeature updateCcaAssigneeFeature;
    private final SaveAutoCCAFeature saveAutoCCAFeature;
    private final CalculateCCATaxesFeature calculateCCATaxesFeature;
    private final ValidateCassIndicatorFeature validateCassIndicatorFeature;
    private final CCAUpdateVoidedFeature ccaUpdateVoidedFeature;
    private final GetNetValuesFeature getNetValuesFeature;
    private final GetCCAReferenceNumberFeature getCCAReferenceNumberFeature;
    public CCAMasterData getCCADetails(CcaDataFilter ccaDataFilter) {
        ccaDataFilter.setCompanyCode(getCompanyCode());
        return getCCADetailsFeature.perform(ccaDataFilter);
    }

    public CcaValidationData saveCCA(CCAMasterData ccaMasterData) {
        final var ccaMasterVO =
                ccaMasterMapper.constructCCAMasterVOFromCCAData(ccaMasterData, ccaMasterData.getUnitOfMeasure());
        ccaMasterVO.setCompanyCode(getCompanyCode());
        if (ccaMasterVO.getRevisedShipmentVO() != null) {
            updatedRevisedShipmentVo(ccaMasterVO);
        }
        final CcaValidationData ccaValidationData = saveCCAFeature.execute(ccaMasterVO);
        if (ccaValidationData != null) {
            setStatusMessage(ccaMasterVO, ccaValidationData);
        }
        return ccaValidationData;
    }

    public List<RelatedCCAData> getRelatedCCA(String shipmentPrefix, String masterDocumentNumber) {
        final var ccaDataFilter = new CcaDataFilter();
        ccaDataFilter.setShipmentPrefix(shipmentPrefix);
        ccaDataFilter.setMasterDocumentNumber(masterDocumentNumber);
        ccaDataFilter.setCompanyCode(getCompanyCode());
        return getRelatedCCAFeature.perform(ccaDataFilter);
    }

    public List<CCAMasterData> getCCAList(CcaDataFilter ccaDataFilter) {
        ccaDataFilter.setCompanyCode(getCompanyCode());
        return getCCAListFeature.perform(ccaDataFilter);
    }

    public BulkActionData deleteCCA(CcaDataFilterList filtersData) {
        var companyCode = getCompanyCode();
        filtersData.getCcaDataFilters()
                .forEach(ccaDataFilter -> ccaDataFilter.setCompanyCode(companyCode));
        return deleteCCAFeature.perform(filtersData);
    }

    public BulkActionData authorizeCCA(CcaDataFilterList filtersData) {
        var companyCode = getCompanyCode();
        filtersData.getCcaDataFilters()
                .forEach(ccaDataFilter -> ccaDataFilter.setCompanyCode(companyCode));
        return authorizeCCAFeature.perform(filtersData);
    }

    public CCAMasterData reRateCCA(String ratingParameter, CCAMasterData ccaMasterData) {
        final var ccaMasterVO =
                ccaMasterMapper.constructCCAMasterVOFromCCAData(ccaMasterData, ccaMasterData.getUnitOfMeasure());
        ccaMasterVO.getRevisedShipmentVO().setRatingParameter(ratingParameter);
        updatedRevisedShipmentVo(ccaMasterVO);
        return rerateCCAFeature.execute(ccaMasterVO);
    }

    public CCAPrintModel generateCCAPrint(CCAPrintFilterModel reportFilterModel) {
        reportFilterModel.setCompanyCode(getCompanyCode());
        return ccaPrintFeature.execute(ccaPrintMapper.constructCCAPrintVO(reportFilterModel));
    }

    public void updateCcaStatusInvoiced(CCAFilterVO ccaFilterVO) {
        ccaUpdateInvoicedFeature.perform(ccaFilterVO);
    }

    public List<AvailableReasonCodeData> getAvailableReasonCodes() {
        return getAvailableReasonCodesFeature.perform();
    }


    public CcaListViewPageInfo getCCAListView(CCAListViewFilterData ccaListViewFilterData) {
        ccaListViewFilterData.setCompanyCode(getCompanyCode());
        return getCCAListViewFeature.execute(
                ccaMasterMapper.constructCCAListViewFilterVO(ccaListViewFilterData)
        );
    }

    public CcaNumbersPage getCCANumbers(CcaSelectFilter ccaSelectFilter) {
        return getCCANumbersFeature.perform(ccaSelectFilter, getCompanyCode());
    }

    public CcaAssigneesPage getCcaAssignees(CcaSelectFilter ccaSelectFilter) {
        return getCcaAssigneesFeature.perform(ccaSelectFilter);
    }

    public CcaValidationData updateCcaAssignee(CcaAssigneeData ccaAssigneeData) throws CcaBusinessException {
        return updateCcaAssigneeFeature.perform(ccaAssigneeData, getCompanyCode());
    }

    public String getCompanyCode() {
        return contextUtil.callerLoginProfile().getCompanyCode();
    }

    private void setStatusMessage(CCAMasterVO ccaMasterVO, CcaValidationData ccaValidationData) {
        final var ccaNumber = ccaValidationData.getCcaReferenceNumber();
        final var status = ccaMasterVO.getCcaStatus();
        if (status != null) {
            ccaValidationData.setStatusMessage(ccaNumber + " " + status.getStatusMessage() + " successfully");
        } else {
            ccaValidationData.setStatusMessage(ccaNumber + " Invalid status");
        }
    }

    public void updateCcaMasterAttachments(AttachmentsData attachmentsData) {
        attachmentsData.setCompanyCode(getCompanyCode());
        updateCcaMasterAttachments.perform(attachmentsData);
    }

    public CCAMasterData reCalculateCCATaxes(CCAMasterData ccaMasterData) {
        final var ccaMasterVO =
                ccaMasterMapper.constructCCAMasterVOFromCCAData(ccaMasterData, ccaMasterData.getUnitOfMeasure());
        calculateCCATaxesFeature.execute(ccaMasterVO);
        return ccaMasterMapper.constructCCAMasterData(ccaMasterVO);
    }

    public CcaValidationData saveAutoCCA(CCAMasterVO ccaMasterVO) {
        final CcaValidationData ccaValidationData = saveAutoCCAFeature.execute(ccaMasterVO);
        if (ccaValidationData != null) {
            setStatusMessage(ccaMasterVO, ccaValidationData);
        }
        return ccaValidationData;
    }

    public CcaCassValidationDataResponse validateCassIndicator(CcaCassValidationDataRequest ccaCassValidationData) {
        final var ccaMasterVO = ccaMasterMapper.createCCAMasterVOFromCcaAwbVO(ccaCassValidationData);
        return validateCassIndicatorFeature.execute(ccaMasterVO);
    }

    public void updateExistingCCAForAwbVoidedEvent(AwbVoidedEvent awbVoidedEvent) {
        ccaUpdateVoidedFeature.perform(awbVoidedEvent);
    }

    private void updatedRevisedShipmentVo(CCAMasterVO ccaMasterVO) {
        ccaMasterVO.getRevisedShipmentVO().setShipmentPrefix(ccaMasterVO.getShipmentPrefix());
        ccaMasterVO.getRevisedShipmentVO().setMasterDocumentNumber(ccaMasterVO.getMasterDocumentNumber());
    }

    public NetValuesData getNetValues(CCAMasterData ccaMasterData) {
        final var ccaMasterVO =
                ccaMasterMapper.constructCCAMasterVOFromCCAData(ccaMasterData, ccaMasterData.getUnitOfMeasure());
        return getNetValuesFeature.execute(ccaMasterVO);
    }

    public CCAMasterData getCcaReferenceNumber(CCAMasterData ccaMasterData) {
        final var ccaMasterVO =
                ccaMasterMapper.constructCCAMasterVOFromCCAData(ccaMasterData, ccaMasterData.getUnitOfMeasure());
        return getCCAReferenceNumberFeature.execute(ccaMasterVO);
    }
}
