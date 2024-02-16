package com.ibsplc.neoicargo.cca.controller;

import com.ibsplc.neoicargo.cca.CCAAWBWebAPI;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.AttachmentsInfo;
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
import com.ibsplc.neoicargo.cca.service.CcaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CcaController implements CCAAWBWebAPI {

    private final CcaService ccaService;

    @Override
    public CCAMasterData getCCADetails(CcaDataFilter ccaDataFilter) {
        return ccaService.getCCADetails(ccaDataFilter);
    }

    @Override
    public CcaValidationData saveCCA(CCAMasterData ccaMasterData) {
        return ccaService.saveCCA(ccaMasterData);
    }

    @Override
    public List<RelatedCCAData> getRelatedCCA(String shipmentPrefix, String masterDocumentNumber) {
        return ccaService.getRelatedCCA(shipmentPrefix, masterDocumentNumber);
    }

    @Override
    public List<CCAMasterData> getCCAList(CcaDataFilter ccaDataFilter) {
        return ccaService.getCCAList(ccaDataFilter);
    }

    @Override
    public BulkActionData deleteCCA(CcaDataFilterList ccaDataFilterList) {
        return ccaService.deleteCCA(ccaDataFilterList);
    }

    @Override
    public BulkActionData authorizeCCA(CcaDataFilterList ccaDataFilterList) {
        return ccaService.authorizeCCA(ccaDataFilterList);
    }

    @Override
    public CCAMasterData reRateCCA(String ratingParameter, CCAMasterData ccaMasterData) {
        return ccaService.reRateCCA(ratingParameter, ccaMasterData);
    }

    @Override
    public CCAPrintModel generateCCAPrint(CCAPrintFilterModel reportFilterModel) {
        return ccaService.generateCCAPrint(reportFilterModel);
    }

    @Override
    public List<AvailableReasonCodeData> getAvailableReasonCodes() {
        return ccaService.getAvailableReasonCodes();
    }

    @Override
    public CcaListViewPageInfo getCCAListView(CCAListViewFilterData ccaListViewFilterData) {
        return ccaService.getCCAListView(ccaListViewFilterData);
    }

    @Override
    public AttachmentsInfo updateCcaMasterAttachments(AttachmentsData attachmentsData) {
        ccaService.updateCcaMasterAttachments(attachmentsData);
        return new AttachmentsInfo(true);
    }

    @Override
    public CcaNumbersPage getCCANumbers(CcaSelectFilter ccaSelectFilter) {
        return ccaService.getCCANumbers(ccaSelectFilter);
    }

    @Override
    public CcaAssigneesPage getCcaAssignees(CcaSelectFilter ccaSelectFilter) {
        return ccaService.getCcaAssignees(ccaSelectFilter);
    }

    public CcaValidationData updateCcaAssignee(CcaAssigneeData ccaAssigneeData) throws CcaBusinessException {
        return ccaService.updateCcaAssignee(ccaAssigneeData);
    }

    @Override
    public CCAMasterData reCalculateCCATaxes(CCAMasterData ccaMasterData) {
        return ccaService.reCalculateCCATaxes(ccaMasterData);
    }

    @Override
    public CcaCassValidationDataResponse validateCassIndicator(CcaCassValidationDataRequest ccaCassValidationData) {
        return ccaService.validateCassIndicator(ccaCassValidationData);
    }

    @Override
    public NetValuesData getNetValues(CCAMasterData ccaMasterData) {
        return ccaService.getNetValues(ccaMasterData);
    }

    @Override
    public CCAMasterData getCcaReferenceNumber(CCAMasterData ccaMasterData) {
        return ccaService.getCcaReferenceNumber(ccaMasterData);
    }
}
