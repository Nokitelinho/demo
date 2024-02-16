package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailMRDModel extends BaseModel {
	private String companyCode;
	private Collection<HandoverModel> handoverVO;
	private HashMap<String, Collection<HandoverModel>> handovers;
	private Collection<ErrorVO> handoverErrors;
	private HashMap<String, String> mailBags;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
