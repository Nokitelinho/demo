package com.ibsplc.buildIntegritryTest;

import org.junit.Test;

import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

import com.ibsplc.icargo.codequalitygateway.CodeIntegrityTest;

public class BuildFailureTest extends AbstractFeatureTest {

	@Override
	public void setup() throws Exception {
	}  


	@Test
	public void validate() {
		new CodeIntegrityTest().validate("customermanagement,master,products,reco,stockcontrol,uld,xaddons,POSTGRE_CHECK");
	}


}
