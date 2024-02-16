package com.ibsplc.neoicargo.stock.component.feature.validateagentforstockholder;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_021;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.AWBDocumentValidationVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component("validateAgentForStockHolderFeature")
@Slf4j
@RequiredArgsConstructor
public class ValidateAgentForStockHolderFeature {

  private final StockDao stockDao;

  public void perform(AWBDocumentValidationVO documentValidationVO) throws StockBusinessException {
    String companyCode = null;
    String stkhldrcod = null;
    if (CollectionUtils.isNotEmpty(documentValidationVO.getAgentDetails())) {
      var agentDetails = documentValidationVO.getAgentDetails().iterator().next();
      companyCode = agentDetails.getCompanyCode();
      var agentCode = agentDetails.getAgentCode();
      log.info("AGENTCODE : {}", agentCode);
      stkhldrcod = findStockHolderCodeForAgent(companyCode, agentCode);
      log.info("STOCKHOLDERCODE : {}", stkhldrcod);
    }
    if (stkhldrcod == null) {
      log.info("---------INVALID_STOCKHOLDERFORAGENTDETAILS- STOCKHOLDERCODE IS NULL--------");
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_021, ERROR));
    }

    if (!stkhldrcod.equals(documentValidationVO.getStockHolderCode())) {
      StockHolderVO stockHolder = stockDao.findStockHolderDetails(companyCode, stkhldrcod);
      log.info("STOCK HOLDER : {}", stockHolder);

      if (stockHolder == null) {
        log.info("---------INVALID_STOCKHOLDERFORAGENTDETAILS-STOCKHOLDER_IS_NULL--------");
        throw new StockBusinessException(constructErrorVO(NEO_STOCK_021, ERROR));
      }
      var approver =
          obtainApproverForStockHolder(stockHolder, documentValidationVO.getDocumentType());
      log.info("APPROVER : {}", approver);
      if (approver == null) {
        log.info("---------INVALID_STOCKHOLDERFORAGENTDETAILS-APPROVER_IS_NULL--------");
        throw new StockBusinessException(constructErrorVO(NEO_STOCK_021, ERROR));
      }
    }
  }

  public String findStockHolderCodeForAgent(String companyCode, String agentCode) {
    StockAgentVO stkAgentMapping = stockDao.findStockAgent(companyCode, agentCode);
    return Objects.nonNull(stkAgentMapping) ? stkAgentMapping.getStockHolderCode() : null;
  }

  public String obtainApproverForStockHolder(StockHolderVO stockHolder, String doctyp) {
    for (StockVO stock : CollectionUtils.emptyIfNull(stockHolder.getStock())) {
      if (stock.getDocumentType().equals(doctyp)) {
        return stock.getStockApproverCode();
      }
    }
    return null;
  }
}
