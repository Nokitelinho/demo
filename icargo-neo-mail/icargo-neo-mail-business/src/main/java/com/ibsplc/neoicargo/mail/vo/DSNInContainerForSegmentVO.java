package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class DSNInContainerForSegmentVO extends AbstractVO {
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
	private Collection<DSNInConsignmentForContainerSegmentVO> dsnInConsignments;
	private String ubrNumber;
	private double mailrate;
	private String currencyCode;
}
