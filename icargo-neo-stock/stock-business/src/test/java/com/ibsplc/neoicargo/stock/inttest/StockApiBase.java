package com.ibsplc.neoicargo.stock.inttest;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.NeoiCargoIDToken;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.util.function.Supplier;
import javax.ws.rs.client.WebTarget;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.CLASSPATH,
    ids = {
      "com.ibsplc.icargo:ebl-nbridge-base-rt:+:stubs",
      "com.ibsplc.neoicargo:neo-masters-business:+:stubs",
      "com.ibsplc.neoicargo:neo-awb-business:+:stubs",
      "com.ibsplc.neoicargo:neo-booking-business:+:stubs"
    })
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "av")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
public class StockApiBase {

  @LocalServerPort int port;

  @Autowired protected WebTarget webTarget;

  @Autowired private Supplier<LoginProfile> loginProfileSupplier;

  @Autowired private Converter<LoginProfile, NeoiCargoIDToken> loginProfileConverter;

  @Autowired private ContextUtil contextUtil;

  @BeforeEach
  public void setAuthenticationContext() {
    var neoiCargoIDToken = loginProfileConverter.convert(loginProfileSupplier.get());
    SecurityContextHolder.getContext().setAuthentication(neoiCargoIDToken);
    contextUtil.removeTxContext();
  }
}
