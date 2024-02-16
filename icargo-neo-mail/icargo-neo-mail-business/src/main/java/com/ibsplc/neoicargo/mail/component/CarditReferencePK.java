package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
@Setter
@Getter
@Embeddable
public class CarditReferencePK implements Serializable {
	private String companyCode;
	private String carditKey;
	private int referrenceSerialNumber;

}
