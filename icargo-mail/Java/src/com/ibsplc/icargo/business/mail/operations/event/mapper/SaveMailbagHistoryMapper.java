package com.ibsplc.icargo.business.mail.operations.event.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.context.ContextAware;

@BeanConverterRegistry("mail.operations.saveMailbagHistoryMapper")
public class SaveMailbagHistoryMapper implements ContextAware {

	@BeanConversion(from = ConsignmentDocumentVO.class, to = MailbagHistoryVO.class, toType = ElementType.LIST, group = {
			"MAIL_OPERATIONS_SAVEMAILBAGHISTORY_CONSIGNMENT_UPLOAD_EVENT"})
	public Collection<MailbagHistoryVO> mapConsignmentDocumentVOToMailbagHistoryVOs(
			ConsignmentDocumentVO consignmentDocumentVO) {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<>();
		if (Objects.nonNull(consignmentDocumentVO) && Objects.nonNull(consignmentDocumentVO.getMailInConsignmentVOs())
				&& !consignmentDocumentVO.getMailInConsignmentVOs().isEmpty()) {
			for (MailInConsignmentVO mailInConsignmentVO : consignmentDocumentVO.getMailInConsignmentVOs()) {
				mailbagHistoryVOs
						.add(mapMailInConsignmentVOToMailbagHistoryVO(consignmentDocumentVO, mailInConsignmentVO));
			}
		}
		return mailbagHistoryVOs;
	}

	private MailbagHistoryVO mapMailInConsignmentVOToMailbagHistoryVO(ConsignmentDocumentVO consignmentDocumentVO,
			MailInConsignmentVO mailInConsignmentVO) {
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
		mailbagHistoryVO.setMailStatus(MailConstantsVO.CARDIT_EVENT);
		mailbagHistoryVO.setScanDate(consignmentDocumentVO.getConsignmentDate());
		mailbagHistoryVO.setMailSource(mailInConsignmentVO.getMailSource());
		mailbagHistoryVO.setContainerNumber(mailInConsignmentVO.getUldNumber());
		mailbagHistoryVO.setScanUser(consignmentDocumentVO.getLastUpdateUser());
		mailbagHistoryVO.setContainerType(
				Objects.nonNull(mailInConsignmentVO.getUldNumber()) && !mailInConsignmentVO.getUldNumber().isEmpty()
						? MailConstantsVO.ULD_TYPE : MailConstantsVO.BULK_TYPE);
		mailbagHistoryVO.setScannedPort(consignmentDocumentVO.getAirportCode());
		mailbagHistoryVO.setMailbagId(mailInConsignmentVO.getMailId());
		mailbagHistoryVO.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
		mailbagHistoryVO.setScanDate(consignmentDocumentVO.getConsignmentDate());
		mailbagHistoryVO.setPoacod(mailInConsignmentVO.getPaCode());
		mailbagHistoryVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		mailbagHistoryVO.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());
		mailbagHistoryVO.setMailbagId(mailInConsignmentVO.getMailId());
		// Flight details
		if (Objects.nonNull(consignmentDocumentVO.getRoutingInConsignmentVOs())
				&& !consignmentDocumentVO.getRoutingInConsignmentVOs().isEmpty()) {
			RoutingInConsignmentVO routingInConsignmentVO = consignmentDocumentVO.getRoutingInConsignmentVOs()
					.iterator().next();
			mailbagHistoryVO.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
			mailbagHistoryVO.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
			mailbagHistoryVO.setCarrierId(routingInConsignmentVO.getOnwardCarrierId());
			mailbagHistoryVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
			mailbagHistoryVO.setFlightSequenceNumber(routingInConsignmentVO.getOnwardCarrierSeqNum());
			mailbagHistoryVO.setSegmentSerialNumber(routingInConsignmentVO.getSegmentSerialNumber());
			mailbagHistoryVO.setPou(routingInConsignmentVO.getPou());
		}
		return mailbagHistoryVO;
	}

}
