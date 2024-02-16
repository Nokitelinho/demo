package com.ibsplc.neoicargo.relmgr;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties="spring.flyway.enabled=false")
@AutoConfigureEmbeddedDatabase
class NeoReleaseManagerApplicationTests {

	@Test
	void contextLoads() {
	}

}
