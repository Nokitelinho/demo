package com.ibsplc.neoicargo.stock.service;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.notation.BusinessService;
import com.ibsplc.neoicargo.stock.component.StockComponent;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.MonitorStockHolderDetailsMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("stockService")
@BusinessService
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

  private final StockComponent stockComponent;
  private final MonitorStockHolderDetailsMapper monitorStockHolderDetailsMapper;

  @Override
  public DocumentValidation findNextDocumentNumber(DocumentFilter documentFilter)
      throws BusinessException {
    return stockComponent.findNextDocumentNumber(documentFilter);
  }

  @Override
  public DocumentValidation validateStock(
      String prefix, String mstdocnum, DocumentFilter documentFilter) {
    return stockComponent.validateStock(prefix, mstdocnum, documentFilter);
  }

  @Override
  public StockHolderModel findStockHolderDetails(String companyCode, String stockHolderCode) {
    return stockComponent.findStockHolderDetails(companyCode, stockHolderCode);
  }

  @Override
  public String findApproverCode(
      String companyCode,
      String stockHolderCode,
      // Integer airlineId,
      String docType,
      String docSubType) {
    return stockComponent.findApproverCode(companyCode, stockHolderCode, docType, docSubType);
  }

  @Override
  public MonitorStockModel findMonitoringStockHolderDetails(StockFilterModel stockFilterModel) {
    var monitorStockVO = stockComponent.findMonitoringStockHolderDetails(stockFilterModel);

    return monitorStockHolderDetailsMapper.mapVoToModel(monitorStockVO);
  }

  @Override
  public void createHistory(StockAllocationModel model, String status) {
    stockComponent.createHistory(model, status);
  }

  @Override
  public Page<MonitorStockModel> monitorStock(StockFilterModel stockFilterModel) {
    return stockComponent.monitorStock(stockFilterModel);
  }

  @Override
  public StockAllocationModel allocateStock(StockAllocationModel stockAllocationModel) {
    return stockComponent.allocateStock(stockAllocationModel);
  }

  @Override
  public List<StockHolderPriorityModel> findStockHolderTypes(String companyCode) {
    return stockComponent.findStockHolderTypes(companyCode);
  }

  @Override
  public List<StockHolderPriorityModel> findPriorities(
      String companyCode, List<String> stockHolderCodes) {
    return stockComponent.findPriorities(companyCode, stockHolderCodes);
  }

  @Override
  public void validateStockHolders(String companyCode, List<String> stockHolderCodes)
      throws StockBusinessException {
    stockComponent.validateStockHolders(companyCode, stockHolderCodes);
  }

  @Override
  public void findStockHolderTypeCode(StockRequestModel stockRequestModel)
      throws BusinessException {
    stockComponent.findStockHolderTypeCode(stockRequestModel);
  }

  @Override
  public void deleteStock(List<RangeModel> rangesModel) throws BusinessException {
    stockComponent.deleteStock(rangesModel);
  }

  @Override
  public void saveStockHolderDetails(StockHolderModel stockHolderModel) {
    stockComponent.saveStockHolderDetails(stockHolderModel);
  }

  @Override
  public StockRangeModel viewRange(StockFilterModel stockFilterModel) {
    return stockComponent.viewRange(stockFilterModel);
  }

  @Override
  public Page<StockRequestModel> findStockRequests(
      int displayPage, StockRequestFilterModel stockRequestFilterModel) {
    return stockComponent.findStockRequests(displayPage, stockRequestFilterModel);
  }

  @Override
  public Page<StockHolderLovModel> findStockHolderLov(
      StockHolderLovFilterModel stockHolderLovFilterModel) {
    return stockComponent.findStockHolderLov(stockHolderLovFilterModel);
  }

  @Override
  public String saveStockRequestDetails(StockRequestModel stockRequestModel)
      throws BusinessException {
    return stockComponent.saveStockRequestDetails(stockRequestModel);
  }

  @Override
  public List<RangeModel> findRanges(RangeFilterModel rangeFilterModel) {
    return stockComponent.findRanges(rangeFilterModel);
  }

  @Override
  public void rejectStockRequests(List<StockRequestModel> stockRequestsModel) {
    stockComponent.rejectStockRequests(stockRequestsModel);
  }

  @Override
  public Page<RangeModel> findAvailableRanges(StockFilterModel stockFilterModel) {
    return stockComponent.findAvailableRanges(stockFilterModel);
  }

  @Override
  public void approveStockRequests(StockRequestApproveModel stockRequestApproveModel) {
    stockComponent.approveStockRequests(stockRequestApproveModel);
  }

  @Override
  public void checkStock(
      String companyCode, String stockHolderCode, String docType, String docSubType)
      throws BusinessException {
    stockComponent.checkStock(companyCode, stockHolderCode, docType, docSubType);
  }

  @Override
  public void saveStockAgentMappings(List<StockAgentModel> stockAgentModels)
      throws BusinessException {
    stockComponent.saveStockAgentMappings(stockAgentModels);
  }

  @Override
  public Page<StockAgentModel> findStockAgentMappings(StockAgentFilterModel stockAgentFilterModel) {
    return stockComponent.findStockAgentMappings(stockAgentFilterModel);
  }

  @Override
  public StockRequestModel findStockRequestDetails(
      StockRequestFilterModel stockRequestFilterModel) {
    return stockComponent.findStockRequestDetails(stockRequestFilterModel);
  }

  @Override
  public int findTotalNoOfDocuments(StockFilterModel stockFilterModel) {
    return stockComponent.findTotalNoOfDocuments(stockFilterModel);
  }

  @Override
  public BlacklistStockModel validateStockForVoiding(BlacklistStockModel blacklistStockModel) {
    return stockComponent.validateStockForVoiding(blacklistStockModel);
  }

  @Override
  public void cancelStockRequest(StockRequestModel stockRequestModel)
      throws StockBusinessException {
    stockComponent.cancelStockRequest(stockRequestModel);
  }

  @Override
  public Page<BlacklistStockModel> findBlacklistedStock(
      StockFilterModel stockFilterModel, int displayPage) {
    return stockComponent.findBlacklistedStock(stockFilterModel, displayPage);
  }

  @Override
  public StockRequestModel findDocumentDetails(
      String companyCode, int airlineIdentifier, String documentNumber) {
    return stockComponent.findDocumentDetails(companyCode, airlineIdentifier, documentNumber);
  }

  @Override
  public AirlineValidationModel validateNumericCode(String companyCode, String shipmentPrefix)
      throws BusinessException {
    return stockComponent.validateNumericCode(companyCode, shipmentPrefix);
  }

  @Override
  public Boolean checkForBlacklistedDocument(
      String companyCode, String doctype, String documentNumber) {
    return stockComponent.checkForBlacklistedDocument(companyCode, doctype, documentNumber);
  }

  @Override
  public Page<StockRangeHistoryModel> findStockRangeHistoryForPage(
      StockRangeFilterModel stockRangeFilterModel) {
    return stockComponent.findStockRangeHistoryForPage(stockRangeFilterModel);
  }

  @Override
  public List<StockRangeHistoryModel> findStockRangeHistory(
      StockRangeFilterModel stockRangeFilterModel) {
    return stockComponent.findStockRangeHistory(stockRangeFilterModel);
  }

  @Override
  public void blacklistStock(BlacklistStockModel blacklistStockModel) throws BusinessException {
    stockComponent.blacklistStock(blacklistStockModel);
  }

  @Override
  public Boolean isStockDetailsExists(DocumentFilterModel documentFilterModel) {
    return stockComponent.isStockDetailsExists(documentFilterModel);
  }

  @Override
  public void validateAgentForStockHolder(AWBDocumentValidationModel documentValidationModel)
      throws StockBusinessException {
    stockComponent.validateAgentForStockHolder(documentValidationModel);
  }

  @Override
  public String findAutoPopulateSubtype(DocumentFilterModel documentFilterModel)
      throws StockBusinessException {
    return stockComponent.findAutoPopulateSubtype(documentFilterModel);
  }

  @Override
  public Page<StockHolderDetailsModel> findStockHolders(StockHolderFilterModel filterModel) {
    return stockComponent.findStockHolders(filterModel);
  }

  @Override
  public void saveStockUtilisation(StockAllocationModel stockAllocationModel, String status) {
    stockComponent.saveStockUtilisation(stockAllocationModel, status);
  }

  @Override
  public StockDetailsModel findCustomerStockDetails(StockDetailsFilterModel stockDetailsFilterModel)
      throws StockBusinessException {
    return stockComponent.findCustomerStockDetails(stockDetailsFilterModel);
  }

  @Override
  public void returnDocumentToStock(StockAllocationModel stockAllocationModel) {
    stockComponent.returnDocumentToStock(stockAllocationModel);
  }

  @Override
  public Collection<RangeModel> findAWBStockDetailsForPrint(StockFilterModel stockFilterModel)
      throws StockBusinessException {
    return stockComponent.findAWBStockDetailsForPrint(stockFilterModel);
  }

  @Override
  public DocumentValidationModel validateDocument(DocumentFilterModel documentFilterModel)
      throws BusinessException {
    return stockComponent.validateDocument(documentFilterModel);
  }

  @Override
  public void deleteStockHolder(StockHolderDetailsModel deleteStockHolder) {
    stockComponent.deleteStockHolder(deleteStockHolder);
  }

  @Override
  public List<StockAgentNeoModel> getStockAgentMappings(
      StockAgentFilterModel stockAgentFilterModel) {
    return stockComponent.getStockAgentMappings(stockAgentFilterModel);
  }

  @Override
  public void deleteDocumentFromStock(DocumentFilterModel documentFilterModel)
      throws BusinessException {
    stockComponent.deleteDocumentFromStock(documentFilterModel);
  }
}
