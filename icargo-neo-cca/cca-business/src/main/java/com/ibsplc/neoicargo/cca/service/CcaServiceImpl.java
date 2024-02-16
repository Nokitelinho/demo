package com.ibsplc.neoicargo.cca.service;

import com.ibsplc.neoicargo.cca.component.CcaComponent;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
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
import com.ibsplc.neoicargo.framework.core.lang.notation.BusinessService;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ccaService")
@BusinessService
@AllArgsConstructor
public class CcaServiceImpl implements CcaService {

    private final CcaComponent ccaComponent;

    @Override
    public CCAMasterData getCCADetails(CcaDataFilter ccaDataFilter) {
        return ccaComponent.getCCADetails(ccaDataFilter);
    }

    @Override
    public CcaValidationData saveCCA(CCAMasterData ccaMasterData) {
        return ccaComponent.saveCCA(ccaMasterData);
    }

    @Override
    public List<CCAMasterData> getCCAList(CcaDataFilter ccaDataFilter) {
        return ccaComponent.getCCAList(ccaDataFilter);
    }

    @Override
    public List<RelatedCCAData> getRelatedCCA(String shipmentPrefix, String masterDocumentNumber) {
        return ccaComponent.getRelatedCCA(shipmentPrefix, masterDocumentNumber);
    }

    @Override
    public BulkActionData deleteCCA(CcaDataFilterList ccaDataFilters) {
        return ccaComponent.deleteCCA(ccaDataFilters);
    }

    @Override
    public BulkActionData authorizeCCA(CcaDataFilterList filtersData) {
        return ccaComponent.authorizeCCA(filtersData);
    }

    @Override
    public CCAMasterData reRateCCA(String ratingParameter, CCAMasterData ccaMasterData) {
        return ccaComponent.reRateCCA(ratingParameter, ccaMasterData);
    }

    @Override
    public CCAPrintModel generateCCAPrint(CCAPrintFilterModel reportFilterModel) {
        return ccaComponent.generateCCAPrint(reportFilterModel);
    }

    @Override
    public void updateCcaStatusInvoiced(CCAFilterVO ccaFilterVO) {
        ccaComponent.updateCcaStatusInvoiced(ccaFilterVO);
    }

    @Override
    public List<AvailableReasonCodeData> getAvailableReasonCodes() {
        return ccaComponent.getAvailableReasonCodes();
    }

    @Override
    public CcaListViewPageInfo getCCAListView(CCAListViewFilterData ccaListViewFilterData) {
        return ccaComponent.getCCAListView(ccaListViewFilterData);
    }

    @Override
    public void updateCcaMasterAttachments(AttachmentsData attachmentsData) {
        ccaComponent.updateCcaMasterAttachments(attachmentsData);
    }

    @Override
    public CcaNumbersPage getCCANumbers(CcaSelectFilter ccaSelectFilter) {
        return ccaComponent.getCCANumbers(ccaSelectFilter);
    }

    @Override
    public CcaAssigneesPage getCcaAssignees(CcaSelectFilter ccaSelectFilter) {
        return ccaComponent.getCcaAssignees(ccaSelectFilter);
    }

    public CcaValidationData updateCcaAssignee(CcaAssigneeData ccaAssigneeData) throws CcaBusinessException {
        return ccaComponent.updateCcaAssignee(ccaAssigneeData);
    }

    @Override
    public CCAMasterData reCalculateCCATaxes(CCAMasterData ccaMasterData) {
        return ccaComponent.reCalculateCCATaxes(ccaMasterData);
    }

    @Override
    public CcaValidationData saveAutoCCA(CCAMasterVO ccaMasterVO) {
        return ccaComponent.saveAutoCCA(ccaMasterVO);
    }

    @Override
    public CcaCassValidationDataResponse validateCassIndicator(CcaCassValidationDataRequest ccaCassValidationData) {
        return ccaComponent.validateCassIndicator(ccaCassValidationData);
    }

    @Override
    public void updateExistingCCAForAwbVoidedEvent(AwbVoidedEvent awbVoidedEvent) {
        ccaComponent.updateExistingCCAForAwbVoidedEvent(awbVoidedEvent);
    }

    @Override
    public NetValuesData getNetValues(CCAMasterData ccaMasterData) {
        return ccaComponent.getNetValues(ccaMasterData);
    }

    @Override
    public CCAMasterData getCcaReferenceNumber(CCAMasterData ccaMasterData) {
       return  ccaComponent.getCcaReferenceNumber(ccaMasterData);
    }
}
