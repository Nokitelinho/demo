package com.ibsplc.neoicargo.stock.service;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.model.AWBDocumentValidationModel;
import com.ibsplc.neoicargo.stock.model.AirlineValidationModel;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.model.DocumentValidationModel;
import com.ibsplc.neoicargo.stock.model.MonitorStockModel;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.model.StockAgentModel;
import com.ibsplc.neoicargo.stock.model.StockAgentNeoModel;
import com.ibsplc.neoicargo.stock.model.StockAllocationModel;
import com.ibsplc.neoicargo.stock.model.StockDetailsModel;
import com.ibsplc.neoicargo.stock.model.StockHolderDetailsModel;
import com.ibsplc.neoicargo.stock.model.StockHolderLovModel;
import com.ibsplc.neoicargo.stock.model.StockHolderModel;
import com.ibsplc.neoicargo.stock.model.StockHolderPriorityModel;
import com.ibsplc.neoicargo.stock.model.StockRangeHistoryModel;
import com.ibsplc.neoicargo.stock.model.StockRangeModel;
import com.ibsplc.neoicargo.stock.model.StockRequestApproveModel;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.DocumentFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.RangeFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockAgentFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockDetailsFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockHolderFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockHolderLovFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRangeFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRequestFilterModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.Collection;
import java.util.List;

public interface StockService {

  DocumentValidation findNextDocumentNumber(DocumentFilter documentFilter) throws BusinessException;

  DocumentValidation validateStock(String prefix, String mstdocnum, DocumentFilter documentFilter);

  StockHolderModel findStockHolderDetails(String companyCode, String stockHolderCode);

  String findApproverCode(
      String companyCode,
      String stockHolderCode,
      // Integer airlineId,
      String docType,
      String docSubType);

  StockAllocationModel allocateStock(StockAllocationModel stockAllocationModel);

  MonitorStockModel findMonitoringStockHolderDetails(StockFilterModel stockFilterModel);

  void createHistory(StockAllocationModel model, String status);

  List<StockHolderPriorityModel> findPriorities(String companyCode, List<String> stockHolderCodes);

  List<StockHolderPriorityModel> findStockHolderTypes(String companyCode);

  void validateStockHolders(String companyCode, List<String> stockHolderCodes)
      throws StockBusinessException;

  void findStockHolderTypeCode(StockRequestModel stockRequestModel) throws BusinessException;

  void deleteStock(List<RangeModel> rangesModel) throws BusinessException;

  Page<MonitorStockModel> monitorStock(StockFilterModel stockFilterModel);

  void saveStockHolderDetails(StockHolderModel stockHolderModel);

  StockRangeModel viewRange(StockFilterModel stockFilterModel);

  List<RangeModel> findRanges(RangeFilterModel rangeFilterModel);

  Page<StockRequestModel> findStockRequests(
      int displayPage, StockRequestFilterModel stockRequestFilterModel);

  Page<StockHolderLovModel> findStockHolderLov(StockHolderLovFilterModel stockHolderLovFilterModel);

  void rejectStockRequests(List<StockRequestModel> stockRequestsModel);

  Page<RangeModel> findAvailableRanges(StockFilterModel stockFilterModel);

  void approveStockRequests(StockRequestApproveModel stockRequestApproveModel)
      throws BusinessException;

  String saveStockRequestDetails(StockRequestModel stockRequestModel) throws BusinessException;

  void checkStock(String companyCode, String stockHolderCode, String docType, String docSubType)
      throws BusinessException;

  void saveStockAgentMappings(List<StockAgentModel> stockAgentModels) throws BusinessException;

  Page<StockAgentModel> findStockAgentMappings(StockAgentFilterModel stockAgentFilterModel);

  StockRequestModel findStockRequestDetails(StockRequestFilterModel stockRequestFilterModel);

  int findTotalNoOfDocuments(StockFilterModel stockFilterModel);

  BlacklistStockModel validateStockForVoiding(BlacklistStockModel blacklistStockModel);

  void cancelStockRequest(StockRequestModel stockRequestModel) throws StockBusinessException;

  Page<BlacklistStockModel> findBlacklistedStock(
      StockFilterModel stockFilterModel, int displayPage);

  StockRequestModel findDocumentDetails(
      String companyCode, int airlineIdentifier, String documentNumber);

  AirlineValidationModel validateNumericCode(String companyCode, String shipmentPrefix)
      throws BusinessException;

  Boolean checkForBlacklistedDocument(String companyCode, String doctype, String documentNumber);

  Page<StockRangeHistoryModel> findStockRangeHistoryForPage(
      StockRangeFilterModel stockRangeFilterModel);

  List<StockRangeHistoryModel> findStockRangeHistory(StockRangeFilterModel stockRangeFilterModel);

  void blacklistStock(BlacklistStockModel blacklistStockModel) throws BusinessException;

  Boolean isStockDetailsExists(DocumentFilterModel documentFilterModel);

  void validateAgentForStockHolder(AWBDocumentValidationModel documentValidationModel)
      throws StockBusinessException;

  String findAutoPopulateSubtype(DocumentFilterModel documentFilterModel)
      throws StockBusinessException;

  Page<StockHolderDetailsModel> findStockHolders(StockHolderFilterModel filterModel);

  void saveStockUtilisation(StockAllocationModel stockAllocationModel, String status);

  StockDetailsModel findCustomerStockDetails(StockDetailsFilterModel stockDetailsFilterModel)
      throws StockBusinessException;

  void returnDocumentToStock(StockAllocationModel stockAllocationModel);

  Collection<RangeModel> findAWBStockDetailsForPrint(StockFilterModel stockFilterModel)
      throws StockBusinessException;

  DocumentValidationModel validateDocument(DocumentFilterModel documentFilterModel)
      throws BusinessException;

  void deleteStockHolder(StockHolderDetailsModel stockHolderdetailsModel);

  List<StockAgentNeoModel> getStockAgentMappings(StockAgentFilterModel stockAgentFilterModel);

  void deleteDocumentFromStock(DocumentFilterModel documentFilterModel) throws BusinessException;
}
