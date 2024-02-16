/**
 * 
 */
package com.ibsplc.neoicargo.lh.mail.component.feature.savescreeningdetails.enrichers;

import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.AdminUserProxy;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author A-10556
 *
 */
@Slf4j
@Component("SaveScreeningDetailsEnricherLH")
public class SaveScreeningDetailsEnricherLH extends Enricher<ScannedMailDetailsVO> {

	@Autowired
	private AdminUserProxy adminUserProxy;

	@Override
	public void enrich(ScannedMailDetailsVO scannedMailDetailsVO)  {
		if ((MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
				|| (MailConstantsVO.CONTAINER_STATUS_TRANSFER.equals(scannedMailDetailsVO.getContainerProcessPoint())
				&& !MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())))
				&& scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			for (MailbagVO mailbagVo : scannedMailDetailsVO.getMailDetails()) {
				try {
					addSecurityDetails(scannedMailDetailsVO, mailbagVo);
				} catch (FinderException e) {
					log.error(e.getErrorCode());
				}
			}
		}

	}

	private void addSecurityDetails(ScannedMailDetailsVO scannedMailDetailsVO, MailbagVO mailbagVo)
			throws FinderException {
		String mailBagId = mailbagVo.getMailbagId() != null && mailbagVo.getMailbagId().trim().length() > 0
				? mailbagVo.getMailbagId()
				: null;
		String companyCode = scannedMailDetailsVO.getCompanyCode();
		long malseqnum = 0;
		try {
			malseqnum = mailbagVo.getMailSequenceNumber() == 0
					? Mailbag.findMailBagSequenceNumberFromMailIdr(mailBagId, companyCode)
					: mailbagVo.getMailSequenceNumber();
		} finally {
		}
		if (scannedMailDetailsVO.isScreeningPresent()) {
			findMailbag(scannedMailDetailsVO, companyCode, malseqnum);
		}
		saveScreeningValues(scannedMailDetailsVO, mailBagId, companyCode, malseqnum);
	}
	private void saveScreeningValues(ScannedMailDetailsVO scannedMailDetailsVO, String mailBagId, String companyCode,
									 long seqnum) {
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		int raCount = 0;
		UserVO userVO = null;
		String raid = null;
		String country = null;
		if (scannedMailDetailsVO.getConsignmentScreeningVos() != null) {
			consignmentScreeningVos = scannedMailDetailsVO.getConsignmentScreeningVos();
		}
		try {
			raCount = Mailbag.findScreeningDetails(mailBagId, companyCode);
			userVO = adminUserProxy.findUserDetails(companyCode, scannedMailDetailsVO.getScannedUser());
			if (userVO != null) {
				raid = userVO.getUserParameterVOs().stream()
						.filter(param -> param.getParameterCode().equals("admin.user.raid"))
						.map(parval -> parval.getParameterValue()).collect(Collectors.toList()).get(0);
				country = userVO.getUserParameterVOs().stream()
						.filter(param -> param.getParameterCode().equals("admin.user.country"))
						.map(parval -> parval.getParameterValue()).collect(Collectors.toList()).get(0);
			}
			if (raCount == 0) {
				if (scannedMailDetailsVO.isScreeningPresent()) {
					consignmentScreeningVO.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
					consignmentScreeningVO.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
					consignmentScreeningVO.setCompanyCode(companyCode);
					consignmentScreeningVO.setSource(MailConstantsVO.SOURCE_HHT);
					consignmentScreeningVO.setAgentType(MailConstantsVO.RA_ISSUING);
					consignmentScreeningVO.setAgentID(raid);
					consignmentScreeningVO.setIsoCountryCode(country);
					consignmentScreeningVO.setScreeningLocation(scannedMailDetailsVO.getAirportCode());
					consignmentScreeningVos.add(consignmentScreeningVO);
				}
			}
			consignmentScreeningVos.forEach(consignmentScreeningVo -> consignmentScreeningVo.setMalseqnum(seqnum));
			new MailController().saveSecurityDetails(consignmentScreeningVos);
		} catch (BusinessException e) {
			log.info("Exception :", e);
		}
	}

	private void findMailbag(ScannedMailDetailsVO scannedMailDetailsVO, String companyCode, long seqnum)
			throws FinderException {
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = null;
		mailBagPK.setCompanyCode(companyCode);
		mailBagPK.setMailSequenceNumber(seqnum);
		mailBag = Mailbag.find(mailBagPK);
		if (mailBag != null && scannedMailDetailsVO.isScreeningPresent()) {
			mailBag.setSecurityStatusCode(MailConstantsVO.SECURITY_STATUS_CODE_SPX);
		}
	}
}
