package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Embeddable
public class USPSPostalCalendarPK implements Serializable {
	private String companyCode;
	private String gpacod;
	private String filterCalender;
	private long serialNumber;

}
