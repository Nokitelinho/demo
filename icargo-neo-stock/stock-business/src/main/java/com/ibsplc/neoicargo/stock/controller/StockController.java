package com.ibsplc.neoicargo.stock.controller;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.StockWebApi;
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
import com.ibsplc.neoicargo.stock.service.StockService;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockController implements StockWebApi {

  private final StockService stockService;

  @Override
  public DocumentValidation findNextDocumentNumber(DocumentFilter documentFilter)
      throws BusinessException {
    return stockService.findNextDocumentNumber(documentFilter);
  }

  @Override
  public DocumentValidation validateStock(
      String prefix, String mstdocnum, DocumentFilter documentFilter) {
    return stockService.validateStock(prefix, mstdocnum, documentFilter);
  }

  @Override
  public StockHolderModel findStockHolderDetails(String companyCode, String stockHolderCode) {
    return stockService.findStockHolderDetails(companyCode, stockHolderCode);
  }

  @Override
  public String findApproverCode(
      String companyCode,
      String stockHolderCode,
      // Integer airlineId,
      String docType,
      String docSubType) {
    return stockService.findApproverCode(companyCode, stockHolderCode, docType, docSubType);
  }

  @Override
  public MonitorStockModel findMonitoringStockHolderDetails(StockFilterModel stockFilterModel) {
    return stockService.findMonitoringStockHolderDetails(stockFilterModel);
  }

  @Override
  public void createHistory(StockAllocationModel model, String status) {
    stockService.createHistory(model, status);
  }

  @Override
  public StockAllocationModel allocateStock(StockAllocationModel stockAllocationModel) {
    return stockService.allocateStock(stockAllocationModel);
  }

  @Override
  public List<StockHolderPriorityModel> findPriorities(
      String companyCode, List<String> stockHolderCodes) {
    return stockService.findPriorities(companyCode, stockHolderCodes);
  }

  @Override
  public List<StockHolderPriorityModel> findStockHolderTypes(String companyCode) {
    return stockService.findStockHolderTypes(companyCode);
  }

  @Override
  public void validateStockHolders(String companyCode, List<String> stockHolderCodes)
      throws StockBusinessException {
    stockService.validateStockHolders(companyCode, stockHolderCodes);
  }

  @Override
  public void validateStockHolderTypeCode(StockRequestModel stockRequestModel)
      throws BusinessException {
    stockService.findStockHolderTypeCode(stockRequestModel);
  }

  @Override
  public void deleteStock(List<RangeModel> rangesModel) throws BusinessException {
    stockService.deleteStock(rangesModel);
  }

  @Override
  public Page<MonitorStockModel> monitorStock(StockFilterModel stockFilterModel) {
    return stockService.monitorStock(stockFilterModel);
  }

  @Override
  public void saveStockHolderDetails(StockHolderModel stockHolderModel) {
    stockService.saveStockHolderDetails(stockHolderModel);
  }

  @Override
  public StockRangeModel viewRange(StockFilterModel stockFilterModel) {
    return stockService.viewRange(stockFilterModel);
  }

  @Override
  public Page<StockRequestModel> findStockRequests(
      StockRequestFilterModel stockRequestFilterModel, int displayPage) {
    return stockService.findStockRequests(displayPage, stockRequestFilterModel);
  }

  @Override
  public Page<StockHolderLovModel> findStockHolderLov(
      StockHolderLovFilterModel stockHolderLovFilterModel, int pageNumber) {
    pageNumber = Math.max(pageNumber, 1);
    stockHolderLovFilterModel.setPageNumber(pageNumber);
    return stockService.findStockHolderLov(stockHolderLovFilterModel);
  }

  @Override
  public List<RangeModel> findRanges(RangeFilterModel rangeFilterModel) {
    return stockService.findRanges(rangeFilterModel);
  }

  @Override
  public void rejectStockRequests(List<StockRequestModel> stockRequestsModel) {
    stockService.rejectStockRequests(stockRequestsModel);
  }

  @Override
  public Page<RangeModel> findAvailableRanges(StockFilterModel stockFilterModel) {
    return stockService.findAvailableRanges(stockFilterModel);
  }

  @Override
  public void approveStockRequests(StockRequestApproveModel stockRequestApproveModel)
      throws BusinessException {
    stockService.approveStockRequests(stockRequestApproveModel);
  }

  @Override
  public String saveStockRequestDetails(StockRequestModel stockRequestModel)
      throws BusinessException {
    return stockService.saveStockRequestDetails(stockRequestModel);
  }

  @Override
  public void checkStock(
      String companyCode, String stockHolderCode, String docType, String docSubType)
      throws BusinessException {
    stockService.checkStock(companyCode, stockHolderCode, docType, docSubType);
  }

  @Override
  public void saveStockAgentMappings(List<StockAgentModel> stockAgentModels)
      throws BusinessException {
    stockService.saveStockAgentMappings(stockAgentModels);
  }

  @Override
  public Page<StockAgentModel> findStockAgentMappings(StockAgentFilterModel stockAgentFilterModel) {
    return stockService.findStockAgentMappings(stockAgentFilterModel);
  }

  @Override
  public StockRequestModel findStockRequestDetails(
      StockRequestFilterModel stockRequestFilterModel) {
    return stockService.findStockRequestDetails(stockRequestFilterModel);
  }

  @Override
  public int findTotalNoOfDocuments(StockFilterModel stockFilterModel) {
    return stockService.findTotalNoOfDocuments(stockFilterModel);
  }

  @Override
  public BlacklistStockModel validateStockForVoiding(BlacklistStockModel blacklistStockModel) {
    return stockService.validateStockForVoiding(blacklistStockModel);
  }

  @Override
  public void cancelStockRequest(StockRequestModel stockRequestModel)
      throws StockBusinessException {
    stockService.cancelStockRequest(stockRequestModel);
  }

  @Override
  public Page<BlacklistStockModel> findBlacklistedStock(
      StockFilterModel stockFilterModel, int displayPage) {
    return stockService.findBlacklistedStock(stockFilterModel, displayPage);
  }

  @Override
  public StockRequestModel findDocumentDetails(
      String companyCode, int airlineIdentifier, String documentNumber) {
    return stockService.findDocumentDetails(companyCode, airlineIdentifier, documentNumber);
  }

  @Override
  public AirlineValidationModel validateNumericCode(String companyCode, String shipmentPrefix)
      throws BusinessException {
    return stockService.validateNumericCode(companyCode, shipmentPrefix);
  }

  @Override
  public Boolean checkForBlacklistedDocument(
      String companyCode, String doctype, String documentNumber) {
    return stockService.checkForBlacklistedDocument(companyCode, doctype, documentNumber);
  }

  @Override
  public Page<StockRangeHistoryModel> findStockRangeHistoryForPage(
      StockRangeFilterModel stockRangeFilterModel) {
    return stockService.findStockRangeHistoryForPage(stockRangeFilterModel);
  }

  @Override
  public List<StockRangeHistoryModel> findStockRangeHistory(
      StockRangeFilterModel stockRangeFilterModel) {
    return stockService.findStockRangeHistory(stockRangeFilterModel);
  }

  @Override
  public void blacklistStock(BlacklistStockModel blacklistStockModel) throws BusinessException {
    stockService.blacklistStock(blacklistStockModel);
  }

  @Override
  public Boolean isStockDetailsExists(DocumentFilterModel documentFilterModel) {
    return stockService.isStockDetailsExists(documentFilterModel);
  }

  @Override
  public void validateAgentForStockHolder(AWBDocumentValidationModel documentValidationModel)
      throws StockBusinessException {
    stockService.validateAgentForStockHolder(documentValidationModel);
  }

  @Override
  public String findAutoPopulateSubtype(DocumentFilterModel documentFilterModel)
      throws StockBusinessException {
    return stockService.findAutoPopulateSubtype(documentFilterModel);
  }

  @Override
  public Page<StockHolderDetailsModel> findStockHolders(StockHolderFilterModel filterModel) {
    return stockService.findStockHolders(filterModel);
  }

  @Override
  public void saveStockUtilisation(StockAllocationModel stockAllocationModel, String status) {
    stockService.saveStockUtilisation(stockAllocationModel, status);
  }

  @Override
  public StockDetailsModel findCustomerStockDetails(StockDetailsFilterModel stockDetailsFilterModel)
      throws StockBusinessException {
    return stockService.findCustomerStockDetails(stockDetailsFilterModel);
  }

  @Override
  public void returnDocumentToStock(StockAllocationModel stockAllocationModel) {
    stockService.returnDocumentToStock(stockAllocationModel);
  }

  @Override
  public Collection<RangeModel> findAWBStockDetailsForPrint(StockFilterModel stockFilterModel)
      throws StockBusinessException {
    return stockService.findAWBStockDetailsForPrint(stockFilterModel);
  }

  @Override
  public DocumentValidationModel validateDocument(DocumentFilterModel documentFilterModel)
      throws BusinessException {
    return stockService.validateDocument(documentFilterModel);
  }

  @Override
  public void deleteStockHolder(StockHolderDetailsModel stockHolderdetailsModel) {
    stockService.deleteStockHolder(stockHolderdetailsModel);
  }

  @Override
  public List<StockAgentNeoModel> getStockAgentMappings(
      StockAgentFilterModel stockAgentFilterModel) {
    return stockService.getStockAgentMappings(stockAgentFilterModel);
  }

  @Override
  public void deleteDocumentFromStock(DocumentFilterModel documentFilterModel)
      throws BusinessException {
    stockService.deleteDocumentFromStock(documentFilterModel);
  }
}
