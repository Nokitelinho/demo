package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Embeddable
public class ConsignmentDocumentPK implements Serializable {
	/** 
	* Company Code
	*/
	private String companyCode;
	/** 
	* Consignment Number
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

}
