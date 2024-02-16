package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
@Setter
@Getter
@Embeddable
public class CarditHandoverPK implements Serializable {
	private String companyCode;
	private String carditKey;
	private int handoverSerialNumber;

}
