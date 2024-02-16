package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * TODO Add the purpose of this class
 * @author A-1739
 */
@Setter
@Getter
@Embeddable
public class CarditTransportationPK implements Serializable {

	private String companyCode;

	private String carditKey;

	private String arrivalPort;

	private long sequenceNumber;
}
