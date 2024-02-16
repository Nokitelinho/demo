package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "stockcontrol", submodule = "defaults", name = "stockcontrolDefaultsEProxy")
public interface StockcontrolDefaultsEProxy {
	DocumentValidationVO validateDocument(DocumentFilterVO documentFilterVO);

	DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documentFilterVO);

	String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO);

	Page<StockAgentVO> findStockAgentMappings(StockAgentFilterVO stockAgentFilterVO);

	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO);
}
