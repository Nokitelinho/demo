package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * TODO Add the purpose of this class
 * @author A-5991
 */
@Setter
@Getter
public class DSNInContainerAtAirportVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String containerNumber;
	private int statedBags;
	private Quantity statedWeight;
	private int acceptedBags;
	private Quantity acceptedWeight;
	private String remarks;
	private String warehouseCode;
	private String locationCode;
	private String mailClass;
	private Collection<DSNInConsignmentForContainerAtAirportVO> dsnInConsignments;
}
