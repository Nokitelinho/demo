package com.ibsplc.neoicargo.cca.dao.impl;

import com.ibsplc.neoicargo.cca.component.feature.savecca.persistor.CcaPersistor;
import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.dao.mybatis.CcaQueryMapper;
import com.ibsplc.neoicargo.cca.dao.repository.CcaMasterRepository;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaListViewVO;
import com.ibsplc.neoicargo.cca.vo.CcaNumbersNodeVO;
import com.ibsplc.neoicargo.cca.vo.GetCcaListMasterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.REJECTED_AS_PART_OF_VOIDING_CCA_REASON;

@Component("ccaDao")
@AllArgsConstructor
public class CcaDaoImpl implements CcaDao {

    protected CcaQueryMapper ccaQueryMapper;
    protected CcaMasterRepository ccaMasterRepository;
    private ContextUtil contextUtil;

    @Override
    @Transactional(readOnly = true)
    public CCAMasterVO getCCADetails(CcaDataFilter ccaDataFilter) {
        return ccaQueryMapper.getCCADetails(ccaDataFilter);
    }

    @Override
    public void saveCCA(CcaMaster ccaMaster) {
        ccaMasterRepository.save(ccaMaster);
    }

    @Override
    public void saveAllCCAs(List<CcaMaster> ccaMasters) {
        ccaMasterRepository.saveAll(ccaMasters);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CCAMasterVO> getRelatedCCA(CcaDataFilter ccaDataFilter) {
        return ccaQueryMapper.getRelatedCCA(ccaDataFilter);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CcaMaster> findCCAMaster(CCAFilterVO ccaFilterVO) {
        final var shipmentPrefix = ccaFilterVO.getShipmentPrefix();
        final var masterDocumentNumber = ccaFilterVO.getMasterDocumentNumber();
        final var ccaRefNumber = ccaFilterVO.getCcaRefNumber();
        return ccaMasterRepository.findByShipmentPrefixAndMasterDocumentNumberAndCcaReferenceNumber(
                shipmentPrefix, masterDocumentNumber, ccaRefNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<GetCcaListMasterVO> getCCAList(List<CcaDataFilter> ccaDataFilters) {
        return ccaQueryMapper.getCCAList(ccaDataFilters);
    }

    @Override
    public Collection<CcaMaster> deleteCCA(List<Long> ccaSerialNumbers) {
        return ccaMasterRepository.deleteByCcaSerialNumberIn(ccaSerialNumbers);
    }

    @Override
    public List<CcaMaster> findCcaMastersByCcaSerialNumbers(List<Long> ccaSerialNumbers) {
        return ccaMasterRepository.findByCcaSerialNumberIn(ccaSerialNumbers);
    }

    @Override
    public void updateExportAndImportBillingStatus(@NotNull final CCAFilterVO ccaFilterVO) {
        final var shipmentPrefix = ccaFilterVO.getShipmentPrefix();
        final var masterDocumentNumber = ccaFilterVO.getMasterDocumentNumber();
        final var ccaRefNumber = ccaFilterVO.getCcaRefNumber();
        final var ccaMaster =
                ccaMasterRepository.findByMasterDocumentNumberAndShipmentPrefixAndCcaReferenceNumber(
                        masterDocumentNumber, shipmentPrefix, ccaRefNumber);
        ccaMaster.ifPresent(master -> {
            master.setExportBillingStatus(ccaFilterVO.getExportBillingStatus());
            master.setImportBillingStatus(ccaFilterVO.getImportBillingStatus());
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CcaListViewVO> findCcaListViewVO(CCAListViewFilterVO ccaListViewFilterVO,
                                                 Pageable updatedPageable, int maxResult, int firstResult) {
        final var ccaMasterListView = ccaQueryMapper.findCcaMasterListView(ccaListViewFilterVO, maxResult, firstResult);
        final var totalElements = ccaQueryMapper.findTotalElements(ccaListViewFilterVO);
        return new PageImpl<>(ccaMasterListView, updatedPageable, totalElements);
    }

    @Override
    public void updateCcaMasterAttachments(AttachmentsData attachmentsData) {
        final var shipmentPrefix = attachmentsData.getShipmentPrefix();
        final var masterDocumentNumber = attachmentsData.getMasterDocumentNumber();
        final var ccaReferenceNumber = attachmentsData.getCcaReferenceNumber();
        final var companyCode = attachmentsData.getCompanyCode();
        final var ccaMaster =
                ccaMasterRepository.findByShipmentPrefixAndMasterDocumentNumberAndCcaReferenceNumberAndCompanyCode(
                        shipmentPrefix, masterDocumentNumber, ccaReferenceNumber, companyCode);
        ccaMaster.ifPresent(mst -> mst.setCcaAttachments(attachmentsData.getCcaAttachments()));
    }

    @Override
    public void updateCcaMasterAssignee(CcaAssigneeData ccaAssigneeData, String companyCode)
            throws CcaBusinessException {
        final var ccaMaster =
                ccaMasterRepository.findByShipmentPrefixAndMasterDocumentNumberAndCcaReferenceNumberAndCompanyCode(
                        ccaAssigneeData.getShipmentPrefix(),
                        ccaAssigneeData.getMasterDocumentNumber(),
                        ccaAssigneeData.getCcaReferenceNumber(),
                        companyCode);
        var ccaMasterToUpdate = ccaMaster.orElseThrow(() ->
                new CcaBusinessException(
                        CcaErrors.constructErrorVO(
                                CcaErrors.NEO_CCA_014.getErrorCode(),
                                CcaErrors.NEO_CCA_014.getErrorMessage(),
                                ErrorType.ERROR)
                )
        );
        ccaMasterToUpdate.setCcaAssignee(ccaAssigneeData.getAssignee());
    }

    @Override
    public List<CcaNumbersNodeVO> getCCANumbers(CcaSelectFilter ccaSelectFilter, String companyCode) {
        return ccaQueryMapper.getCcaNumbers(ccaSelectFilter, companyCode);
    }

    @Override
    public void updateExistingCCAForAwbVoidedEvent(AwbVoidedEvent awbVoidedEvent) {
        ccaMasterRepository.findByShipmentPrefixAndMasterDocumentNumberAndCcaStatusIn(
                awbVoidedEvent.getShipmentPrefix(),
                awbVoidedEvent.getMasterDocumentNumber(),
                Set.of(CcaStatus.N, CcaStatus.I, CcaStatus.C
                )).forEach(master -> {
            master.setCcaStatus(CcaStatus.S);
            master.setCcaReason(REJECTED_AS_PART_OF_VOIDING_CCA_REASON);
            var loginProfile = contextUtil.callerLoginProfile();
            if (master.getCcaWorkflow() == null) {
                master.setCcaWorkflow(new HashSet<>());
            }
            master.getCcaWorkflow().
                    add(
                            CcaPersistor.buildCcaWorkflow(
                                    master,
                                    loginProfile.getUserId(),
                                    Optional.ofNullable(master.getCcaAssignee())
                                            .orElse(CcaPersistor.buildUsername(loginProfile)),
                                    CcaStatus.S
                            )
                    );
        });

    }
}
