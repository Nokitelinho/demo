package com.ibsplc.neoicargo.stock;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.PrivateAPI;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PrivateAPI
public interface StockWebApi {

  @Path("/stock/nextawb")
  @POST
  DocumentValidation findNextDocumentNumber(DocumentFilter documentFilter) throws BusinessException;

  @Path("/stockcheck/{prefix}/{mstdocnum}")
  @POST
  DocumentValidation validateStock(
      @PathParam("prefix") String prefix,
      @PathParam("mstdocnum") String mstdocnum,
      DocumentFilter documentFilter);

  @Path("/stock/findstockholderdetails")
  @POST
  @Consumes("multipart/mixed")
  StockHolderModel findStockHolderDetails(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) String stockHolderCode);

  @Path("/stock/findapprovercode")
  @POST
  @Consumes("multipart/mixed")
  String findApproverCode(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) String stockHolderCode,
      // @Multipart(value = "2", type = MediaType.APPLICATION_JSON) String airlineId,
      @Multipart(value = "2", type = MediaType.APPLICATION_JSON) String docType,
      @Multipart(value = "3", type = MediaType.APPLICATION_JSON) String docSubType);

  @Path("/stock/findmonitoringstockholderdetails")
  @POST
  MonitorStockModel findMonitoringStockHolderDetails(StockFilterModel stockFilterModel);

  @Path("/stock/createhistory/{status}")
  @POST
  void createHistory(StockAllocationModel model, @PathParam("status") String status);

  @Path("/stock/allocatestock")
  @POST
  StockAllocationModel allocateStock(StockAllocationModel stockAllocationModel);

  @Path("/stock/findpriorities")
  @POST
  @Consumes("multipart/mixed")
  List<StockHolderPriorityModel> findPriorities(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) List<String> stockHolderCodes);

  @Path("/stock/findstockholdertypes")
  @POST
  List<StockHolderPriorityModel> findStockHolderTypes(String companyCode);

  @Path("/stock/validatestockholders")
  @POST
  @Consumes("multipart/mixed")
  void validateStockHolders(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) List<String> stockHolderCodes)
      throws StockBusinessException;

  @Path("/stock/validatestockholdertypecode")
  @POST
  void validateStockHolderTypeCode(StockRequestModel stockRequestModel) throws BusinessException;

  @Path("/stock/deletestock")
  @POST
  void deleteStock(List<RangeModel> rangesModel) throws BusinessException;

  @Path("/stock/monitorstock")
  @POST
  Page<MonitorStockModel> monitorStock(StockFilterModel stockFilterModel) throws BusinessException;

  @Path("/stock/savestockholderdetails")
  @POST
  void saveStockHolderDetails(StockHolderModel stockHolderModel);

  @Path("/stock/viewrange")
  @POST
  StockRangeModel viewRange(StockFilterModel stockFilterModel) throws StockBusinessException;

  @Path("/stock/findstockrequests/")
  @POST
  @Consumes("multipart/mixed")
  Page<StockRequestModel> findStockRequests(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON)
          StockRequestFilterModel stockRequestFilterModel,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) int displayPage);

  @Path("/stock/findstockholderlov")
  @POST
  @Consumes("multipart/mixed")
  Page<StockHolderLovModel> findStockHolderLov(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON)
          StockHolderLovFilterModel stockHolderLovFilterModel,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

  @Path("/stock/findranges")
  @POST
  List<RangeModel> findRanges(RangeFilterModel rangeFilterModel);

  @Path("/stock/rejectstockrequests")
  @POST
  void rejectStockRequests(List<StockRequestModel> stockRequestsModel);

  @Path("/stock/findavailableranges")
  @POST
  Page<RangeModel> findAvailableRanges(StockFilterModel stockFilterModel);

  @Path("/stock/approvestockrequests")
  @POST
  void approveStockRequests(StockRequestApproveModel stockRequestApproveModel)
      throws BusinessException;

  @Path("/stock/savestockrequestdetails")
  @POST
  String saveStockRequestDetails(StockRequestModel stockRequestModel) throws BusinessException;

  @Path("/stock/checkstock")
  @POST
  @Consumes("multipart/mixed")
  void checkStock(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) String stockHolderCode,
      @Multipart(value = "2", type = MediaType.APPLICATION_JSON) String docType,
      @Multipart(value = "3", type = MediaType.APPLICATION_JSON) String docSubType)
      throws BusinessException;

  @Path("/stock/savestockagentmappings")
  @POST
  void saveStockAgentMappings(List<StockAgentModel> stockAgentModels) throws BusinessException;

  @Path("/stock/findstockagentmappings")
  @POST
  Page<StockAgentModel> findStockAgentMappings(StockAgentFilterModel stockAgentFilterModel);

  @Path("/stock/findstockrequestdetails")
  @POST
  StockRequestModel findStockRequestDetails(StockRequestFilterModel stockRequestFilterModel);

  @Path("/stock/findtotalnoofdocuments")
  @POST
  int findTotalNoOfDocuments(StockFilterModel stockFilterModel);

  @Path("/stock/validatestockforvoiding")
  @POST
  BlacklistStockModel validateStockForVoiding(BlacklistStockModel blacklistStockModel);

  @Path("/stock/cancelstockrequest")
  @POST
  void cancelStockRequest(StockRequestModel stockRequestModel) throws StockBusinessException;

  @Path("/stock/findblacklistedstock")
  @POST
  @Consumes("multipart/mixed")
  Page<BlacklistStockModel> findBlacklistedStock(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) StockFilterModel stockFilterModel,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) int displayPage);

  @Path("/stock/finddocumentdetails")
  @POST
  @Consumes("multipart/mixed")
  StockRequestModel findDocumentDetails(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) int airlineIdentifier,
      @Multipart(value = "2", type = MediaType.APPLICATION_JSON) String documentNumber);

  @Path("/stock/validatenumericcode")
  @POST
  @Consumes("multipart/mixed")
  AirlineValidationModel validateNumericCode(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) String shipmentPrefix)
      throws BusinessException;

  @Path("/stock/checkforblacklisteddocument")
  @POST
  @Consumes("multipart/mixed")
  Boolean checkForBlacklistedDocument(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) String doctype,
      @Multipart(value = "2", type = MediaType.APPLICATION_JSON) String documentNumber);

  @Path("/stock/findstockrangehistoryforpage")
  @POST
  Page<StockRangeHistoryModel> findStockRangeHistoryForPage(
      StockRangeFilterModel stockRangeFilterModel);

  @Path("/stock/findstockrangehistory")
  @POST
  List<StockRangeHistoryModel> findStockRangeHistory(StockRangeFilterModel stockRangeFilterModel);

  @Path("/stock/blackliststock")
  @POST
  void blacklistStock(BlacklistStockModel blacklistStockModel) throws BusinessException;

  @Path("/stock/isstockdetailsexists")
  @POST
  Boolean isStockDetailsExists(DocumentFilterModel documentFilterModel);

  @Path("/stock/validateagentforstockholder")
  @POST
  void validateAgentForStockHolder(AWBDocumentValidationModel documentValidationModel)
      throws StockBusinessException;

  @Path("/stock/findautopopulatesubtype")
  @POST
  String findAutoPopulateSubtype(DocumentFilterModel documentFilterModel)
      throws StockBusinessException;

  @Path("/stock/findstockholders")
  @POST
  Page<StockHolderDetailsModel> findStockHolders(StockHolderFilterModel filterModel);

  @Path("/stock/savestockutilisation")
  @POST
  @Consumes("multipart/mixed")
  void saveStockUtilisation(
      @Multipart(value = "0", type = MediaType.APPLICATION_JSON)
          StockAllocationModel stockAllocationModel,
      @Multipart(value = "1", type = MediaType.APPLICATION_JSON) String status);

  @Path("/stock/findcustomerstockdetails")
  @POST
  StockDetailsModel findCustomerStockDetails(StockDetailsFilterModel stockDetailsFilterModel)
      throws StockBusinessException;

  @Path("/stock/returndocumenttostock")
  @POST
  void returnDocumentToStock(StockAllocationModel stockAllocationModel);

  @Path("/stock/findawbstockdetailsforprint")
  @POST
  Collection<RangeModel> findAWBStockDetailsForPrint(StockFilterModel stockFilterModel)
      throws StockBusinessException;

  @Path("/stock/validatedocument")
  @POST
  DocumentValidationModel validateDocument(DocumentFilterModel documentFilterModel)
      throws BusinessException;

  @Path("/stock/deletestockholder")
  @POST
  void deleteStockHolder(StockHolderDetailsModel stockHolderdetailsModel);

  @Path("/stock/getstockagentmappings")
  @POST
  List<StockAgentNeoModel> getStockAgentMappings(StockAgentFilterModel stockAgentFilterModel);

  @Path("/stock/deletedocumentfromstock")
  @POST
  void deleteDocumentFromStock(DocumentFilterModel documentFilterModel) throws BusinessException;
}
