package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.masters.customer.CustomerWebAPI;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor.StockAgentPersistor;
import com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor.StockHolderDetailsPersistor;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FeatureConfigSource("stock/savestockholderdetails")
@RequiredArgsConstructor
public class SaveStockHolderDetailsFeature extends AbstractFeature<StockHolderVO> {
  private final StockAgentPersistor stockAgentPersistor;
  private final StockHolderDetailsPersistor stockHolderDetailsPersistor;
  private final CustomerWebAPI customerWebAPI;

  public Void perform(StockHolderVO stockHolderVO) throws BusinessException {
    log.info("Invoke SaveStockHolderDetailsFeature");
    if (StockHolderVO.OPERATION_FLAG_INSERT.equals(stockHolderVO.getOperationFlag())) {
      saveStockAgent(stockHolderVO);
      stockHolderDetailsPersistor.save(stockHolderVO);
    }
    if (StockHolderVO.OPERATION_FLAG_UPDATE.equals(stockHolderVO.getOperationFlag())) {
      stockHolderDetailsPersistor.update(stockHolderVO);
    }
    return null;
  }

  private void saveStockAgent(StockHolderVO stockHolderVO) throws StockBusinessException {
    if (StockHolderType.A.equals(stockHolderVO.getStockHolderType())
        && customerWebAPI.validateAgent(getCustomermodel(stockHolderVO.getStockHolderCode()))
            != null) {
      log.info("Save stock agent");
      stockAgentPersistor.save(stockHolderVO);
    }
  }

  private CustomerModel getCustomermodel(String stockHolderCode) {
    CustomerModel customermodel = new CustomerModel();

    customermodel.setAgentCode(stockHolderCode);
    return customermodel;
  }
}
