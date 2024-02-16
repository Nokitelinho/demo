package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class GPAContractFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* The ModuleName
	*/
	public static final String MODULE = "mail";
	/** 
	* The SubModuleName
	*/
	public static final String SUBMODULE = "operations";
	private String companyCode;
	private String paCode;
	private String origin;
	private String destination;
	private String contractID;
	private String region;
	private int recordsPerPage;
	private int totalRecords;
	private int pageNumber;
}
