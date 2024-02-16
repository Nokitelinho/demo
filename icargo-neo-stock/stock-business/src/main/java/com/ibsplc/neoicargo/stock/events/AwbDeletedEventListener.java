package com.ibsplc.neoicargo.stock.events;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_AWB;
import static com.ibsplc.neoicargo.stock.util.StockConstant.SUBSTRING_COUNT;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.model.StockAllocationModel;
import com.ibsplc.neoicargo.stock.service.StockService;
import com.ibsplc.neoicargo.stock.util.StockUtil;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("awbDeletedEventListener")
@RequiredArgsConstructor
@EnableBinding(AwbEventsSink.class)
public class AwbDeletedEventListener {

  private final StockUtil stockUtil;
  private final ContextUtil contextUtil;
  private final StockService stockService;

  @StreamListener(
      target = AwbEventsSink.AWB_EVENT_CHANNEL_INPUT,
      condition = "headers['EVENT_TYPE']=='AWBDeletedEvent'")
  public void handleAwbEvent(@Payload AWBDeletedEvent awbData) throws BusinessException {
    log.info(
        "handleAwbDeletedEvent : {}-{}",
        awbData.getShipmentPrefix(),
        awbData.getMasterDocumentNumber());

    String companyCode = contextUtil.callerLoginProfile().getCompanyCode();
    int airlineIdentifier = stockUtil.findAirlineIdentifier(awbData.getShipmentPrefix());
    String docSubType =
        stockUtil.findSubTypeForDocument(
            companyCode, airlineIdentifier, DOC_TYP_AWB, awbData.getMasterDocumentNumber());
    String stockHolderCode =
        stockUtil.checkAwbExistsInAnyStock(
            companyCode,
            airlineIdentifier,
            DOC_TYP_AWB,
            awbData.getMasterDocumentNumber(),
            SUBSTRING_COUNT);

    var stockAllocationModel = new StockAllocationModel();
    var rangeModel = new RangeModel();
    var ranges = new ArrayList<RangeModel>();
    rangeModel.setStartRange(awbData.getMasterDocumentNumber());
    ranges.add(rangeModel);

    stockAllocationModel.setCompanyCode(companyCode);
    stockAllocationModel.setStockHolderCode(stockHolderCode);
    stockAllocationModel.setDocumentType(DOC_TYP_AWB);
    stockAllocationModel.setDocumentSubType(docSubType);
    stockAllocationModel.setAirlineIdentifier(airlineIdentifier);
    stockAllocationModel.setRanges(ranges);

    stockService.returnDocumentToStock(stockAllocationModel);
    log.info("AwbDeletedEventListener.handleAwbEvent ends");
  }
}
