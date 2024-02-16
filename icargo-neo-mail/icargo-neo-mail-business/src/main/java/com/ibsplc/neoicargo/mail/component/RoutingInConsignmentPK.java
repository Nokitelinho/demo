package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Embeddable
public class RoutingInConsignmentPK implements Serializable {
	/** 
	* companyCode
	*/
	private String companyCode;
	/** 
	* consignmentNumber
	*/
	private String consignmentNumber;
	/** 
	* paCode
	*/
	private String paCode;
	/** 
	* consignmentSequenceNumber
	*/
	private int consignmentSequenceNumber;
	/** 
	* routingSerialNumber - key based generation 
	*/
	private int routingSerialNumber;

}
