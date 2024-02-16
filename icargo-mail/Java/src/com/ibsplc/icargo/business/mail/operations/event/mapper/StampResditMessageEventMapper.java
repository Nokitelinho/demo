package com.ibsplc.icargo.business.mail.operations.event.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.context.ContextAware;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;

@BeanConverterRegistry("mail.operations.stampResditMessageEventMapper")
public class StampResditMessageEventMapper implements ContextAware {
	
	private static final String MAIL_OPERATIONS_STAMP_LOST_RESDIT_EVENT = "MAIL_OPERATIONS_STAMP_LOST_RESDIT_EVENT";
	private static final String MAIL_OPERATIONS = "MAIL OPERATIONS";
	private static final Log LOGGER = LogFactory.getLogger(MAIL_OPERATIONS);
	private static final String MAIL_OPERATIONS_STAMP_FOUND_RESDIT_EVENT = "MAIL_OPERATIONS_STAMP_FOUND_RESDIT_EVENT";
	
	@BeanConversion(from = OperationalFlightVO.class, to = MailResditVO.class, toType = ElementType.LIST, group = {MAIL_OPERATIONS_STAMP_LOST_RESDIT_EVENT})
	public Collection<MailResditVO> mapLostResditDetailsToPayload(OperationalFlightVO operationalFlightVO)
			throws SystemException {
		Collection<MailbagVO> lostMailbags =null;
		Collection<ContainerDetailsVO> containerDetailsVOs;
		try {
			containerDetailsVOs = Proxy.getInstance().get(MailOperationsProxy.class).findArrivalDetailsForReleasingMails(operationalFlightVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}
		if (Objects.nonNull(containerDetailsVOs) && !containerDetailsVOs.isEmpty()) {
			lostMailbags = containerDetailsVOs
					.stream()
					.flatMap(containerDetailsVO -> containerDetailsVO.getMailDetails().stream())
					.filter(mailbagVO -> MailConstantsVO.FLAG_NO.equals(mailbagVO.getArrivedFlag()))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		
		return constructMailResditVOs(lostMailbags,StampResditFeatureConstants.RESDIT_LOST);

	}
	
	@BeanConversion(from = MailArrivalVO.class, to = MailResditVO.class, toType = ElementType.LIST, group = {
			MAIL_OPERATIONS_STAMP_FOUND_RESDIT_EVENT })
	public Collection<MailResditVO> mapFoundResditDetailsToPayload(MailArrivalVO mailArrivalVO) throws SystemException {
		Collection<MailbagVO> foundMailbags = null;
		Collection<MailResditVO> mailResditVOs = null;
		if(mailArrivalVO.isFoundResditSent()){
				return mailResditVOs;
		}
		try {
			foundMailbags = Proxy.getInstance().get(MailOperationsProxy.class).getFoundArrivalMailBags(mailArrivalVO);
		} catch (ProxyException e) {

			throw new SystemException(e.getMessage(), e);
		}

		return constructMailResditVOs(foundMailbags, StampResditFeatureConstants.RESDIT_FOUND);

	}

	private Collection<MailResditVO> constructMailResditVOs(Collection<MailbagVO> mailbagVOs, String eventCode) {
		Collection<MailResditVO> mailResditVOs = null;
		if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
			mailResditVOs = mailbagVOs.stream().map(mailbagVO -> constructMailResditVO(mailbagVO, eventCode))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return mailResditVOs;
	}

	private MailResditVO constructMailResditVO(MailbagVO mailbagVO, String eventCode) {
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailbagId());
		mailResditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mailResditVO.setEventAirport(mailbagVO.getScannedPort());
		mailResditVO.setEventCode(eventCode);
		mailResditVO.setCarrierId(mailbagVO.getCarrierId());
		mailResditVO.setFlightNumber(mailbagVO.getFlightNumber());
		mailResditVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		mailResditVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
		mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber(mailbagVO.getContainerNumber());
		mailResditVO.setEventDate(getEventDate(mailbagVO));
		String mailboxId =null;
		try {
			mailboxId = Proxy.getInstance().get(MailOperationsProxy.class).findMailboxIdFromConfig(mailbagVO);
			mailResditVO.setMailboxID(mailboxId);
		} catch (SystemException ex) {
			ErrorVO errorVO = new ErrorVO(SystemException.UNEXPECTED_DB_ERROR);
			ex.addError(errorVO);
			LOGGER.log(Log.FINE,ex);
		} catch (ProxyException ex) {
			LOGGER.log(Log.FINE, "ProxyException in findMailboxIdFromConfig",ex);
		}
		return mailResditVO;
	}

	private LocalDate getEventDate(MailbagVO mailbagVO) {
		LocalDate eventDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
			if(Objects.nonNull(mailbagVO.getResditEventDate())){
				eventDate = mailbagVO.getResditEventDate();
			}else if(Objects.nonNull(mailbagVO.getScannedDate())){
				eventDate = mailbagVO.getScannedDate();
			}else if (isNotNullAndEmpty(mailbagVO.getScannedPort())){
				eventDate = new LocalDate(mailbagVO.getScannedPort(),Location.ARP,true);
			}
		return eventDate;
	}

	private boolean isNotNullAndEmpty(String s) {
		return Objects.nonNull(s) && !s.isEmpty();
	}

}
