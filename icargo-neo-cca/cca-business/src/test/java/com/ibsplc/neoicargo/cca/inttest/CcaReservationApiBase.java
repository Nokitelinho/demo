package com.ibsplc.neoicargo.cca.inttest;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.NeoiCargoIDToken;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import javax.ws.rs.client.WebTarget;
import java.util.function.Supplier;

@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.CLASSPATH, ids = {
        "com.ibsplc.icargo:ebl-nbridge-base-rt:+:stubs"
})
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "av")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
public class CcaReservationApiBase {

    @LocalServerPort
    protected int port;

	@Autowired
	protected WebTarget webTarget;

	@Autowired
	private Supplier<LoginProfile> loginProfileSupplier;

	@Autowired
	private Converter<LoginProfile, NeoiCargoIDToken> loginProfileConverter;

	@Autowired
	private ContextUtil contextUtil;

	@BeforeEach
	public void setAuthenticationContext() {
		var neoiCargoIDToken = loginProfileConverter.convert(loginProfileSupplier.get());
		SecurityContextHolder.getContext().setAuthentication(neoiCargoIDToken);
		contextUtil.removeTxContext();
	}
}