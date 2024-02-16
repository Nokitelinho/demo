package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class MailMRDVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private Collection<HandoverVO> handoverVO;
	private HashMap<String, Collection<HandoverVO>> handovers;
	private Collection<ErrorVO> handoverErrors;
	private HashMap<String, String> mailBags;
}
