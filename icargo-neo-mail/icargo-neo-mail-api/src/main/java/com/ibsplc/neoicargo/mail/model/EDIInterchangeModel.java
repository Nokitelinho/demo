package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.business.capacity.mailbidding.vo.CGRResponseVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class EDIInterchangeModel extends BaseModel {
	private String companyCode;
	private String sequenceNum;
	private String interchangeCtrlReference;
	private String interchangeSyntaxId;
	private int interchangeSyntaxVer;
	private String recipientId;
	private String senderId;
	private LocalDate preparationDate;
	private int interchangeControlCnt;
	private long messageSequence;
	private String stationCode;
	private Collection<CarditVO> carditMessages;
	private Collection<CGRResponseVO> cgrResponseVOs;
	private Collection<String> errorCodes;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
