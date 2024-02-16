package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class ConsignmentScreeningDetailsPK implements Serializable {
	private String companyCode;
	private long serialNumber;

}
