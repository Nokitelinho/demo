package com.ibsplc.neoicargo.cca.service;

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
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = BusinessException.class)
public interface CcaService {

    CCAMasterData getCCADetails(CcaDataFilter ccaDataFilter);

    CcaValidationData saveCCA(CCAMasterData ccaMasterData);

    List<CCAMasterData> getCCAList(CcaDataFilter ccaDataFilter);

    List<RelatedCCAData> getRelatedCCA(String shipmentPrefix, String masterDocumentNumber);

    BulkActionData deleteCCA(CcaDataFilterList filtersData);

    BulkActionData authorizeCCA(CcaDataFilterList filtersData);

    CCAMasterData reRateCCA(String ratingParameter, CCAMasterData ccaMasterData);

    CCAPrintModel generateCCAPrint(CCAPrintFilterModel reportFilterModel);

    void updateCcaStatusInvoiced(CCAFilterVO ccaFilterVO);

    List<AvailableReasonCodeData> getAvailableReasonCodes();

    CcaListViewPageInfo getCCAListView(CCAListViewFilterData ccaListViewFilterData);

    void updateCcaMasterAttachments(AttachmentsData attachmentsData);

    CcaNumbersPage getCCANumbers(CcaSelectFilter ccaSelectFilter);

    CcaAssigneesPage getCcaAssignees(CcaSelectFilter ccaSelectFilter);

    CcaValidationData updateCcaAssignee(CcaAssigneeData ccaAssigneeData) throws CcaBusinessException;

    CCAMasterData reCalculateCCATaxes(CCAMasterData ccaMasterData);

    CcaValidationData saveAutoCCA(CCAMasterVO ccaMasterVO);

    CcaCassValidationDataResponse validateCassIndicator(CcaCassValidationDataRequest ccaCassValidationDataRequest);

    void updateExistingCCAForAwbVoidedEvent(AwbVoidedEvent awbVoidedEvent);

    NetValuesData getNetValues(CCAMasterData ccaMasterData);
    CCAMasterData getCcaReferenceNumber(CCAMasterData ccaMasterData);
}
