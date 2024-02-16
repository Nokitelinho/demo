package com.ibsplc.neoicargo.mail.lh.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.ibsplc.neoicargo.mail.component.MailUploadController;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import com.ibsplc.neoicargo.mail.vo.TransferManifestVO;
import com.ibsplc.neoicargo.mail.component.proxy.AdminUserProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
public class LHMailUploadController extends MailUploadController {
	@Autowired
	private AdminUserProxy adminUserProxy;
	@Override
	public void saveScreeningConsginorDetails(Map<String, Object> contTransferMap) {
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsCollection = null;
		TransferManifestVO transferManifestVO = new TransferManifestVO();
		if (contTransferMap != null && contTransferMap.get(MailConstantsVO.CONST_CONTAINER_DETAILS) != null) {
			containerDetailsCollection = (Collection<ContainerDetailsVO>) contTransferMap
					.get(MailConstantsVO.CONST_CONTAINER_DETAILS);
			transferManifestVO.setDsnVOs(new ArrayList<DSNVO>());
			for (ContainerDetailsVO container : containerDetailsCollection) {
				if (container.getDsnVOs() != null && !container.getDsnVOs().isEmpty()) {
					for (DSNVO dsnVO : container.getDsnVOs()) {
						MailbagVO mailbagVO = new MailbagVO();
						mailbagVO.setMailSequenceNumber(dsnVO.getMailSequenceNumber());
						mailbagVO.setMailbagId(dsnVO.getMailbagId());
						scannedMailDetailsVO.setScannedUser(dsnVO.getScannedUser());
						scannedMailDetailsVO.setCompanyCode(dsnVO.getCompanyCode());
						mailbagVOs.add(mailbagVO);
					}
				}
			}
			scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_TRANSFER);
			scannedMailDetailsVO.setMailDetails(mailbagVOs);
			saveScreeningDetails(scannedMailDetailsVO);
			}
	}
}
