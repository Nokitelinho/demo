package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class BookingTimeVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	public static final String MODULE = "mail";
	public static final String SUBMODULE = "operations";
	private String ubrNumber;
	private ZonedDateTime bookingLastUpdateTime;
	private ZonedDateTime bookingFlightDetailLastUpdTime;
}
