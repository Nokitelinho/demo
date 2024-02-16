package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.icargo.business.capacity.mailbidding.vo.CGRResponseVO;
import com.ibsplc.neoicargo.mail.vo.CarditVO;

/** 
 * TODO Add the purpose of this class
 * @author A-5991
 */
@Setter
@Getter
public class EDIInterchangeVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String sequenceNum;
	private String interchangeCtrlReference;
	/** 
	* Syntax id of this interchange
	*/
	private String interchangeSyntaxId;
	/** 
	* Syntax version
	*/
	private int interchangeSyntaxVer;
	/** 
	* Recipient idr
	*/
	private String recipientId;
	/** 
	* Sender id
	*/
	private String senderId;
	/** 
	* Interchange preparation date
	*/
	private ZonedDateTime preparationDate;
	/** 
	* Interchange control count
	*/
	private int interchangeControlCnt;
	private long messageSequence;
	private String stationCode;
	private Collection<CarditVO> carditMessages;
	private Collection<CGRResponseVO> cgrResponseVOs;
	private Collection<String> errorCodes;
}
