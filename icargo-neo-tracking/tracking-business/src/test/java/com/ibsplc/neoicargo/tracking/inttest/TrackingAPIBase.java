package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;

import javax.ws.rs.client.WebTarget;

@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.CLASSPATH, ids = {
        "com.ibsplc.icargo:ebl-nbridge-base-rt:+:stubs"})
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
public class TrackingAPIBase {

    @LocalServerPort
    int port;

    @Autowired
    protected WebTarget webTarget;
}
