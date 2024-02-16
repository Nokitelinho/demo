package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;

@EProductProxy(module = "shared", submodule = "uld", name = "sharedULDEProxy")
public interface SharedULDEProxy {
	ULDValidationVO validateULD(String companyCode, String uldNumber);

	Quantity findULDTareWeight(ULDValidationFilterVO ULDValidationFilterVO);

	Collection<ULDPositionVO> findULDPosition(Collection<ULDPositionFilterVO> filterVOs);
}
