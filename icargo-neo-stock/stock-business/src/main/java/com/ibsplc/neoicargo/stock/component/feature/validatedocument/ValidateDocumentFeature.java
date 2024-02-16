package com.ibsplc.neoicargo.stock.component.feature.validatedocument;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_004;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_020;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_023;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_024;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_025;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_026;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_027;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_028;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_029;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_030;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_031;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_AWB;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_COURIER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_EBT;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_INV;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_INSTOCK;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STRING_START;
import static com.ibsplc.neoicargo.stock.util.StockConstant.SUBSTRING_COUNT;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.stock.component.feature.checkforblacklisteddocument.CheckForBlacklistedDocumentFeature;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.vo.AWBDocumentValidationVO;
import com.ibsplc.neoicargo.stock.vo.AgentDetailVO;
import com.ibsplc.neoicargo.stock.vo.DocumentValidationVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateDocumentFeature {

  private final ContextUtil contextUtil;
  private final StockDao stockDao;
  private final StockAgentRepository stockAgentRepository;
  private final AirlineWebComponent airlineWebComponent;
  private final StockHolderRepository stockHolderRepository;
  private final StockMapper stockMapper;
  private final StockHolderMapper stockHolderMapper;

  private final CheckForBlacklistedDocumentFeature feature;

  private final CustomerComponent customerComponent;

  public DocumentValidationVO perform(DocumentFilterVO documentFilterVO) throws BusinessException {
    checkForBlacklistedDocument(documentFilterVO);
    if (DOC_TYP_AWB.equals(documentFilterVO.getDocumentType())) {
      log.info("Document Type is AWB. So, calling awbValidation");
      return validateAWB(documentFilterVO);
    } else if (DOC_TYP_COURIER.equals(documentFilterVO.getDocumentType())) {
      log.info("Document Type is COU. So, calling courierValidation");
      return validateCourier(documentFilterVO);
    } else if (DOC_TYP_EBT.equals(documentFilterVO.getDocumentType())) {
      log.info("Document Type is EBT. So, calling ebtValidation");
      return validateEBT(documentFilterVO);
    } else if (DOC_TYP_INV.equals(documentFilterVO.getDocumentType())) {
      log.info("Document Type is INV. So, calling InvoiceValidation");
      return validateInvoice(documentFilterVO);
    } else {
      log.error("UNIDENTIFIED DOC_TYPE. So, returning NULL :");
      return null;
    }
  }

  private void checkForBlacklistedDocument(DocumentFilterVO documentFilterVO)
      throws StockBusinessException {
    boolean documentBlackListed =
        feature.perform(
            contextUtil.callerLoginProfile().getCompanyCode(),
            documentFilterVO.getDocumentType(),
            documentFilterVO.getDocumentNumber());
    if (documentBlackListed) {
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_020.getErrorCode(),
              NEO_STOCK_020.getErrorMessage(),
              ERROR,
              new String[] {documentFilterVO.getDocumentNumber()}));
    }
  }

  private DocumentValidationVO validateAWB(DocumentFilterVO docFilterVO) throws BusinessException {
    String stockHolderCode = null;
    String stockHolderType = null;

    String companyCode = contextUtil.callerLoginProfile().getCompanyCode();
    String documentNumber = docFilterVO.getDocumentNumber();
    int airlineId = docFilterVO.getAirlineIdentifier();
    String agentCode = docFilterVO.getStockOwner();
    String documentSubType = docFilterVO.getDocumentSubType();

    AWBDocumentValidationVO awbValidationVO = new AWBDocumentValidationVO();

    if (Objects.isNull(airlineId) || airlineId == 0) {
      airlineId = findAirlineIdentifier(docFilterVO.getPrefix());
    }
    if (StringUtils.isBlank(documentSubType)) {
      documentSubType = findSubTypeForDocument(companyCode, airlineId, DOC_TYP_AWB, documentNumber);
    }

    if (agentCode != null) {
      checkAwbExistsInAnyStock(
          companyCode, airlineId, DOC_TYP_AWB, documentNumber, SUBSTRING_COUNT);
      stockHolderCode = findStockHolderCodeForAgent(companyCode, agentCode);
      if (StringUtils.isBlank(stockHolderCode)) {
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_024.getErrorCode(),
                NEO_STOCK_024.getErrorMessage(),
                ERROR,
                new String[] {agentCode}));
      } else {
        List<AgentDetailVO> agentDetails = validateAgents(Arrays.asList(agentCode));
        awbValidationVO.setAgentDetails(agentDetails);
      }

      checkDocumentAvailable(companyCode, airlineId, stockHolderCode, documentNumber);

    } else {
      stockHolderCode =
          checkAwbExistsInAnyStock(
              companyCode, airlineId, DOC_TYP_AWB, documentNumber, SUBSTRING_COUNT);
      awbValidationVO.setStatus(STATUS_INSTOCK);
      StockHolderVO stkHolderVO = stockDao.findStockHolderDetails(companyCode, stockHolderCode);
      List<String> agentCodes = null;
      if (Objects.nonNull(stkHolderVO)) {
        stockHolderType = stkHolderVO.getStockHolderType().name();
        agentCodes = findAgentsForStockHolder(companyCode, stkHolderVO.getStockHolderCode());
      } else {
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_004.getErrorCode(),
                NEO_STOCK_004.getErrorMessage(),
                ERROR,
                new String[] {stockHolderCode}));
      }

      if (Objects.nonNull(agentCodes) && !agentCodes.isEmpty()) {
        List<AgentDetailVO> agentDetails = validateAgents(agentCodes);
        awbValidationVO.setAgentDetails(agentDetails);
      }
    }

    awbValidationVO.setStockHolderCode(stockHolderCode);
    awbValidationVO.setDocumentType(DOC_TYP_AWB);
    awbValidationVO.setDocumentNumber(documentNumber);
    awbValidationVO.setStockHolderType(stockHolderType);
    awbValidationVO.setDocumentSubType(documentSubType);

    log.info("returning AWBValidationVO :{}", awbValidationVO);

    return awbValidationVO;
  }

  public int findAirlineIdentifier(String prefix) throws BusinessException {
    var airlineModel = airlineWebComponent.validateNumericCode(prefix);
    if (Objects.nonNull(airlineModel)) {
      return airlineModel.getAirlineIdentifier();
    }
    return 0;
  }

  private DocumentValidationVO validateCourier(DocumentFilterVO documentFilterVO)
      throws BusinessException {
    String companyCode = contextUtil.callerLoginProfile().getCompanyCode();
    String documentNumber = documentFilterVO.getDocumentNumber();
    int airlineId = documentFilterVO.getAirlineIdentifier();
    String agentCode = documentFilterVO.getStockOwner();
    if (Objects.isNull(airlineId) || airlineId == 0) {
      airlineId = findAirlineIdentifier(documentFilterVO.getPrefix());
    }

    AWBDocumentValidationVO docValidationVO = new AWBDocumentValidationVO();
    long mstDocNumber = toLong(documentNumber);
    String stockHolderCode =
        checkDocumentExistsInAnyStock(companyCode, airlineId, DOC_TYP_COURIER, mstDocNumber);
    if (StringUtils.isBlank(stockHolderCode)) {
      log.error("No stock contains the Courier ", documentNumber);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_028.getErrorCode(),
              NEO_STOCK_028.getErrorMessage(),
              ERROR,
              new String[] {documentNumber}));
    }
    if (!stockHolderCode.equals(agentCode)) {
      log.error("Courier stock NOT Available ", documentNumber);
      log.error("Courier stock NOT Available with ", agentCode);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_029.getErrorCode(),
              NEO_STOCK_029.getErrorMessage(),
              ERROR,
              new String[] {documentNumber, stockHolderCode}));
    }
    return docValidationVO;
  }

  private DocumentValidationVO validateEBT(DocumentFilterVO documentFilterVO)
      throws BusinessException {
    String companyCode = contextUtil.callerLoginProfile().getCompanyCode();
    String documentNumber = documentFilterVO.getDocumentNumber();
    int airlineId = documentFilterVO.getAirlineIdentifier();
    String agentCode = documentFilterVO.getStockOwner();
    if (Objects.isNull(airlineId) || airlineId == 0) {
      airlineId = findAirlineIdentifier(documentFilterVO.getPrefix());
    }
    AWBDocumentValidationVO docValidationVO = new AWBDocumentValidationVO();
    long mstDocNumber = toLong(documentNumber);
    String stockHolderCode =
        checkDocumentExistsInAnyStock(companyCode, airlineId, DOC_TYP_EBT, mstDocNumber);
    if (StringUtils.isBlank(stockHolderCode)) {
      log.error("No stock contains the EBT ", documentNumber);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_030.getErrorCode(),
              NEO_STOCK_030.getErrorMessage(),
              ERROR,
              new String[] {documentNumber}));
    }

    if (!stockHolderCode.equals(agentCode)) {
      log.error("EBT stock NOT Available ", documentNumber);
      log.error("EBT stock NOT Available with ", agentCode);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_031.getErrorCode(),
              NEO_STOCK_031.getErrorMessage(),
              ERROR,
              new String[] {documentNumber, stockHolderCode}));
    }

    return docValidationVO;
  }

  private DocumentValidationVO validateInvoice(DocumentFilterVO documentFilterVO)
      throws BusinessException {
    int airlineId = documentFilterVO.getAirlineIdentifier();
    if (Objects.isNull(airlineId) || airlineId == 0) {
      airlineId = findAirlineIdentifier(documentFilterVO.getPrefix());
    }
    AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO();
    String stockHolderCode =
        checkAwbExistsInAnyStock(
            documentFilterVO.getCompanyCode(),
            airlineId,
            documentFilterVO.getDocumentType(),
            documentFilterVO.getDocumentNumber(),
            documentFilterVO.getDocumentNumber().length());
    documentValidationVO.setStockHolderCode(stockHolderCode);
    return documentValidationVO;
  }

  public String findSubTypeForDocument(
      String companyCode, int airlineId, String documentType, String documentNumber) {
    long mstDocNumber = 0;
    if (DOC_TYP_AWB.equals(documentType)) {
      mstDocNumber = toLong(documentNumber.substring(STRING_START, SUBSTRING_COUNT));
    } else {
      mstDocNumber = toLong(documentNumber);
    }
    RangeVO rangeVO =
        stockDao.findStockRangeDetails(companyCode, airlineId, documentType, mstDocNumber);
    return Objects.nonNull(rangeVO) ? rangeVO.getDocumentSubType() : null;
  }

  private List<String> findAgentsForStockHolder(String companyCode, String stockHolderCode) {
    return stockDao.findAgentsForStockHolder(companyCode, stockHolderCode);
  }

  private void checkDocumentAvailable(
      String companyCode, int airlineId, String stockHolderCode, String documentNumber)
      throws StockBusinessException {
    var stkHolder = findStockHolderDetails(companyCode, stockHolderCode);
    if (stkHolder == null) {
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_004.getErrorCode(),
              NEO_STOCK_004.getErrorMessage(),
              ERROR,
              new String[] {stockHolderCode}));
    }

    if (CollectionUtils.isEmpty(stkHolder.getStock())) {
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_025.getErrorCode(),
              NEO_STOCK_025.getErrorMessage(),
              ERROR,
              new String[] {stockHolderCode}));
    }

    checkForAWBStock(airlineId, stkHolder);

    try {

      checkAWBRangeExists(
          airlineId, toLong(documentNumber.substring(STRING_START, SUBSTRING_COUNT)), stkHolder);
    } catch (StockBusinessException e) {
      log.error("StockBusinessException", e);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_027.getErrorCode(),
              NEO_STOCK_027.getErrorMessage(),
              ERROR,
              new String[] {stockHolderCode, documentNumber}));
    }
  }

  private StockHolderVO findStockHolderDetails(String companyCode, String stockHolderCode) {
    StockHolderVO stockHolderVO = null;
    Collection<StockVO> stockVOs = new ArrayList<>();
    var stockHolderOptional =
        stockHolderRepository.findByCompanyCodeAndStockHolderCode(companyCode, stockHolderCode);
    if (stockHolderOptional.isPresent()) {
      var stkHldr = stockHolderOptional.get();
      stockHolderVO = stockHolderOptional.map(stockHolderMapper::mapEntityToVo).orElse(null);
      if (Objects.nonNull(stockHolderVO)) {
        for (Stock stock : stkHldr.getStock()) {
          stockVOs.add(stockMapper.mapEntityToVoWithRanges(stock));
        }
        stockHolderVO.setStock(stockVOs);
      }
    }
    return stockHolderVO;
  }

  public void checkForAWBStock(int airlineId, StockHolderVO stkHolder)
      throws StockBusinessException {
    boolean hasAWBStock = false;
    for (StockVO stk : stkHolder.getStock()) {
      if (stk.getDocumentType().equals(DOC_TYP_AWB) && stk.getAirlineIdentifier() == airlineId) {
        hasAWBStock = true;
        break;
      }
    }
    if (!hasAWBStock) {
      log.error("No AWB Stock found for stockholder ", stkHolder.getStockHolderCode());
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_026.getErrorCode(),
              NEO_STOCK_026.getErrorMessage(),
              ERROR,
              new String[] {stkHolder.getStockHolderCode()}));
    }
  }

  public void checkAWBRangeExists(int airlineId, long documentNumber, StockHolderVO stkHolder)
      throws StockBusinessException {
    if (stkHolder.getStock() == null) {
      return;
    }
    for (StockVO stock : stkHolder.getStock()) {
      if (stock.getDocumentType().equals(DOC_TYP_AWB)
          && stock.getAirlineIdentifier() == airlineId
          && checkDocumentExists(documentNumber, stock)) {
        return;
      }
    }
    log.error("AWB document number not existing ");
    throw new StockBusinessException(constructErrorVO(NEO_STOCK_027, ERROR));
  }

  public boolean checkDocumentExists(long documentNumber, StockVO stock) {
    for (RangeVO range : CollectionUtils.emptyIfNull(stock.getRanges())) {
      if (range.getAsciiStartRange() <= documentNumber
          && range.getAsciiEndRange() >= documentNumber) {
        return true;
      }
    }
    return false;
  }

  private List<AgentDetailVO> validateAgents(List<String> agentCodes)
      throws CustomerBusinessException {
    var agentDetailVOs = new ArrayList<AgentDetailVO>();
    for (String agentCode : agentCodes) {
      CustomerModel model = new CustomerModel();
      model.setAgentCode(agentCode);
      var customerModel = customerComponent.validateAgent(model);
      if (Objects.nonNull(customerModel)) {
        AgentDetailVO detailVO = new AgentDetailVO();
        detailVO.setAgentCode(customerModel.getAgentCode());
        detailVO.setAgentName(customerModel.getCustomerName());
        detailVO.setCustomerCode(customerModel.getCustomerCode());
        agentDetailVOs.add(detailVO);
      }
    }
    return agentDetailVOs;
  }

  private String findStockHolderCodeForAgent(String companyCode, String agentCode) {
    Optional<StockAgent> stkAgentMapping =
        stockAgentRepository.findByCompanyCodeAndAgentCode(companyCode, agentCode);
    if (stkAgentMapping.isPresent()) {
      return stkAgentMapping.get().getStockHolderCode();
    }
    return null;
  }

  public String checkAwbExistsInAnyStock(
      String companyCode,
      int airlineId,
      String documentType,
      String documentNumber,
      int lengthofFormat)
      throws StockBusinessException {
    long mstDocNumber = 0;
    if (DOC_TYP_AWB.equals(documentType) && documentNumber.length() >= lengthofFormat) {
      mstDocNumber = toLong(documentNumber.substring(STRING_START, lengthofFormat));
    } else if ("ORDINO".equals(documentType) && documentNumber.length() >= lengthofFormat) {
      mstDocNumber = toLong(documentNumber.substring(STRING_START, lengthofFormat));
    } else {
      mstDocNumber = toLong(documentNumber);
    }
    String stockHolderCode =
        checkDocumentExistsInAnyStock(companyCode, airlineId, documentType, mstDocNumber);
    if (StringUtils.isBlank(stockHolderCode)) {
      if (DOC_TYP_AWB.equals(documentType)) {
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_023.getErrorCode(),
                NEO_STOCK_023.getErrorMessage(),
                ERROR,
                new String[] {documentNumber}));
      } else {
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_003.getErrorCode(),
                NEO_STOCK_003.getErrorMessage(),
                ERROR,
                new String[] {documentNumber}));
      }
    }
    return stockHolderCode;
  }

  private String checkDocumentExistsInAnyStock(
      String companyCode, int airlineId, String documentType, long mstDocNumber) {
    RangeVO rangeVO =
        stockDao.findStockRangeDetails(companyCode, airlineId, documentType, mstDocNumber);
    return Objects.nonNull(rangeVO) ? rangeVO.getStockHolderCode() : null;
  }
}
