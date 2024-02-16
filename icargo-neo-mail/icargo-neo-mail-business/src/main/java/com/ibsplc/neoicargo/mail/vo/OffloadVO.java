package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class OffloadVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String pol;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private String userCode;
	private String offloadType;
	private Collection<ContainerVO> offloadContainers;
	private Page<DespatchDetailsVO> offloadDSNs;
	private Page<MailbagVO> offloadMailbags;
	private Page<ContainerVO> offloadContainerDetails;
	private boolean isDepartureOverride;
	private boolean fltClosureChkNotReq;
	private boolean isRemove;

	/** 
	* @param isDepartureOverride The isDepartureOverride to set.
	*/
	public void setDepartureOverride(boolean isDepartureOverride) {
		this.isDepartureOverride = isDepartureOverride;
	}

	public void setRemove(boolean isRemove) {
		this.isRemove = isRemove;
	}
}
