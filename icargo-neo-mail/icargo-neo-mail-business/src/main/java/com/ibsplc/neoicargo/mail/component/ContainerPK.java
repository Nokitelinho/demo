package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Embeddable
public class ContainerPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The containerNumber
	*/
	private String containerNumber;
	/** 
	* The assignmentPort
	*/
	private String assignmentPort;
	/** 
	* The carrierId
	*/
	private int carrierId;
	/** 
	* The flightNumber
	*/
	private String flightNumber;
	/** 
	* The flightSequenceNumber
	*/
	private long flightSequenceNumber;
	/** 
	* The legSerialNumber
	*/
	private int legSerialNumber;

}
