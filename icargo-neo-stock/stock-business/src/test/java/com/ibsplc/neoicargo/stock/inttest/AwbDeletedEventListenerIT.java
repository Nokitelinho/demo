package com.ibsplc.neoicargo.stock.inttest;

import com.ibsplc.neoicargo.stock.dao.repository.StockUtilisationRepository;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

@Slf4j
public class AwbDeletedEventListenerIT extends StockApiBase {

  @Autowired StubTrigger trigger;

  @Autowired StockUtilisationRepository stockUtilisationRepository;

  @Test
  public void contextLoads()
      throws JSONException, IOException, InterruptedException, BusinessException {

    var result = stockUtilisationRepository.find("AV", "STKHLD5", "AWB", "S", 1134, "5200123");
    Assertions.assertEquals("U", result.get().getStatus());
    trigger.trigger("publishAwbDeletedEventForStock");
    var result1 = stockUtilisationRepository.find("AV", "STKHLD5", "AWB", "S", 1134, "5200123");
    Assertions.assertEquals("D", result1.get().getStatus());
  }
}
