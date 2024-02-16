package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-1883
 */
@Setter
@Getter
@Embeddable
public class ResditEventPK implements Serializable {
	/** 
	* Company Code
	*/
	private String companyCode;
	/** 
	* Consignment Documen tNumber
	*/
	private String consignmentDocumentNumber;
	/** 
	* Event Code
	*/
	private String eventCode;
	/** 
	* Event Port
	*/
	private String eventPort;
	/** 
	* Message Sequence Number
	*/
	private long messageSequenceNumber;

}
