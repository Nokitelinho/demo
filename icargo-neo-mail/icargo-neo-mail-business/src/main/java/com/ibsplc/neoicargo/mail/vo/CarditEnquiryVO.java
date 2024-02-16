package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class CarditEnquiryVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String searchMode;
	private String unsendResditEvent;
	private String flightType;
	private String resditEventPort;
	private String resditEventCode;
	private OperationalFlightVO operationalFlightVo;
	private Collection<ContainerVO> containerVos;
	private Collection<MailbagVO> mailbagVos;
	private Collection<DespatchDetailsVO> despatchDetailVos;
	private Collection<ConsignmentDocumentVO> consignmentDocumentVos;
}
