package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "reco", submodule = "defaults", name = "recoDefaultsEProxy")
public interface RecoDefaultsEProxy {
	Collection<EmbargoDetailsVO> checkForEmbargo(Collection<ShipmentDetailsVO> shipmentDetailsVos);

	boolean checkAnyEmbargoExists(EmbargoFilterVO embargoFilterVO);
}
