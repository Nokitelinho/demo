package com.ibsplc.neoicargo.stock.component.feature.findautopopulatesubtypefeature;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_021;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("findAutoPopulateSubtypeFeature")
@Slf4j
@RequiredArgsConstructor
public class FindAutoPopulateSubtypeFeature {

  private final StockDao stockDao;

  public String perform(DocumentFilterVO documentFilterVO) throws StockBusinessException {
    String stockHolderCode = null;
    stockHolderCode =
        findStockHolderCodeForAgent(
            documentFilterVO.getCompanyCode(), documentFilterVO.getStockOwner());
    log.info("STOCKHOLDERCODE : {}", stockHolderCode);
    if (stockHolderCode == null || ("").equals(stockHolderCode.trim())) {
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_021, ERROR));
    }
    documentFilterVO.setStockHolderCode(stockHolderCode);
    return findAutoPopulateSubtype(documentFilterVO);
  }

  public String findStockHolderCodeForAgent(String companyCode, String agentCode) {
    StockAgentVO stkAgentMapping = stockDao.findStockAgent(companyCode, agentCode);
    return Objects.nonNull(stkAgentMapping) ? stkAgentMapping.getStockHolderCode() : null;
  }

  public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO) {
    String subtype = null;
    subtype = stockDao.findAutoPopulateSubtype(documentFilterVO);
    log.info("SUBTYPE : {}", subtype);
    return subtype;
  }
}
