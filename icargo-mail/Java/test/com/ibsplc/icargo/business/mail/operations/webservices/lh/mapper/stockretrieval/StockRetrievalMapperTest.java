package com.ibsplc.icargo.business.mail.operations.webservices.lh.mapper.stockretrieval;

import static org.mockito.Mockito.spy;

import org.junit.Test;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.lhsystems.xsd.esb.AWBStockService.AwbResponse;
import com.lhsystems.xsd.esb.AWBStockService.AwmStockResponse;

public class StockRetrievalMapperTest extends AbstractFeatureTest {
	private StockRetrievalMapper spy;
	@Override
	public void setup() throws Exception {
		spy = spy(new StockRetrievalMapper());
	}
	
	@Test
	public void mapParameters_vo_test() throws SystemException {
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setShipmentPrefix("123");
		documentFilterVO.setDocumentNumber("2345678");
		documentFilterVO.setAwbOrigin("CDG");
		documentFilterVO.setAwbDestination("DXB");
		Object[] parameters = new Object[] { documentFilterVO };
		spy.mapParameters(parameters);
	}
	
	@Test
	public void mapResult_test() throws SystemException {
		AwmStockResponse obj= new AwmStockResponse();
		AwbResponse awbResponse = new AwbResponse();
		awbResponse.setAwbPrefix("123");
		awbResponse.setAwbNumber("2345678");
		obj.setAwbResponse(awbResponse);
		spy.mapResult(obj);
	}
	
	@Test
	public void mapResult_test_whenresponsenull() throws SystemException {
		AwmStockResponse obj= null;
		spy.mapResult(obj);
	}

}

