package com.ibsplc.neoicargo.mailmasters.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 
 * @author A-5526
 * 
 */
@Embeddable
@Getter
@Setter
public class MLDConfigurationPK implements Serializable {

	private String airportCode;
	private int carrierIdentifier;
	private String companyCode;

}