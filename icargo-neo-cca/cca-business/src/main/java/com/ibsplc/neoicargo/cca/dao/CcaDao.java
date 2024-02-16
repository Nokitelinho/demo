package com.ibsplc.neoicargo.cca.dao;

import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaListViewVO;
import com.ibsplc.neoicargo.cca.vo.CcaNumbersNodeVO;
import com.ibsplc.neoicargo.cca.vo.GetCcaListMasterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface CcaDao {

    CCAMasterVO getCCADetails(CcaDataFilter ccaDataFilter);

    void saveCCA(CcaMaster ccaMaster);

    void saveAllCCAs(List<CcaMaster> ccaMasters);

    List<CCAMasterVO> getRelatedCCA(CcaDataFilter ccaDataFilter);

    Optional<CcaMaster> findCCAMaster(CCAFilterVO ccaFilterVO);

    Set<GetCcaListMasterVO> getCCAList(List<CcaDataFilter> ccaDataFilters);

    Collection<CcaMaster> deleteCCA(List<Long> ccaSerialNumbers);

    List<CcaMaster> findCcaMastersByCcaSerialNumbers(List<Long> ccaSerialNumbers);

    void updateExportAndImportBillingStatus(CCAFilterVO ccaFilterVO);

    Page<CcaListViewVO> findCcaListViewVO(CCAListViewFilterVO ccaListViewFilterVO, Pageable pageable,
                                          int maxResult, int firstResult);

    void updateCcaMasterAttachments(AttachmentsData attachmentsData);

    void updateCcaMasterAssignee(CcaAssigneeData ccaAssigneeData, String companyCode) throws CcaBusinessException;

    List<CcaNumbersNodeVO> getCCANumbers(CcaSelectFilter ccaSelectFilter, String companyCode);

    void updateExistingCCAForAwbVoidedEvent(AwbVoidedEvent awbVoidedEvent);
}
