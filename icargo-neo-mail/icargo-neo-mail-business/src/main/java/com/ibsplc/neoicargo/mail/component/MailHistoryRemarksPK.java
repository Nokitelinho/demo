package com.ibsplc.neoicargo.mail.component;

import javax.persistence.Embeddable;
import java.io.Serializable;



@Embeddable
public class MailHistoryRemarksPK implements Serializable{
	
	
	private long serialNumber;
	private String companyCode;
}
