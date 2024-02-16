package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-3109
 */
@Setter
@Getter
@Embeddable
public class TransferManifestDSNPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The transferManifestId
	*/
	private String transferManifestId;
	/** 
	* The mailSequenceNumber
	*/
	private long mailSequenceNumber;

}
