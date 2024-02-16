package com.ibsplc.neoicargo.stock.inttest;

import static org.assertj.core.api.Assertions.assertThat;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.repository.StockUtilisationRepository;
import java.io.IOException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

public class AwbCreatedEventListenerIT extends StockApiBase {

  @Autowired StubTrigger trigger;
  @Autowired private StockUtilisationRepository repository;

  @Test
  public void contextLoads()
      throws JSONException, IOException, InterruptedException, BusinessException {
    trigger.trigger("produceAwbCreatedEventForStock");
    var rngUtlizd = repository.find("AV", "VALIDATE", "AWB", "S", 1176, "4540544");
    assertThat(rngUtlizd.get().getStatus()).isEqualTo("U");
  }
}
