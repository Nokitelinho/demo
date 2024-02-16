package com.ibsplc.icargo.business.mail.operations.webservices.lh.mapper.stockretrieval;

import static org.mockito.Mockito.spy;

import org.junit.Test;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

public class StockMessageHeaderTest extends AbstractFeatureTest {
	private StockMessageHeader spy;
	@Override
	public void setup() throws Exception {
		spy = spy(new StockMessageHeader());
	}
	
	@Test
	public void map_messageheader_from_paramater() {
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setShipmentPrefix("123");
		documentFilterVO.setDocumentNumber("2345678");
		spy.messageHeader();
	}

}
