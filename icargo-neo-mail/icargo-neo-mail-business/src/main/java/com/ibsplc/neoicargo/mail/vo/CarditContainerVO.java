package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class CarditContainerVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* Equipment qualifier code
	*/
	private String equipmentQualifier;
	/** 
	* Code list responsible agency coded
	*/
	private String codeListResponsibleAgency;
	/** 
	* Equipment size and type, identification
	*/
	private String containerType;
	/** 
	* Code list responsible agency , coded
	*/
	private String typeCodeListResponsibleAgency;
	/** 
	* Measurement dimensions, coded-Unit net weight
	*/
	private String measurementDimension;
	/** 
	* container weight If value is null default value is set as -1
	*/
	private Quantity containerWeight;
	/** 
	* Container seal number
	*/
	private String sealNumber;
	private String containerNumber;
	/** 
	* Container Journey Identifier
	*/
	private String containerJourneyIdentifier;
	/** 
	* The CDTTYP : CarditType (Message Function) 
	*/
	private String carditType;
	/** 
	* last update user
	*/
	private String lastUpdateUser;
}
