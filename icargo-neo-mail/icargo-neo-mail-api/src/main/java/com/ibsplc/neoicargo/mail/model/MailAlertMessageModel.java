package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailAlertMessageModel extends BaseModel {
	private String flightnum;
	private LocalDate departureDate;
	private String deptport;
	private String route;
	private Collection<ContainerDetailsModel> condatails;
	private String airlinecode;
	private Collection<MessageDespatchDetailsVO> messageDespatchDetailsVOs;
	private Collection<String> stations;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
