package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * @author A-5526
 */
@Setter
@Getter
@Embeddable
public class MLDMessageMasterPK implements Serializable {
	private String companyCode;
	private int serialNumber;
}
