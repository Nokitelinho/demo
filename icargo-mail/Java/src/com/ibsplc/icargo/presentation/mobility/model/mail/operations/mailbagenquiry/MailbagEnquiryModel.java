
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.model.tgc.defaults.aa.kiosk.CustomerIdDetailsModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-5444	:	02-Jan-2019	:	Draft
 */


package com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailbagenquiry;

import java.io.Serializable;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.common.Mailbag;

public class MailbagEnquiryModel implements Serializable{
	

	private MailbagEnquiryFilter mailbagEnquiryFilter;

	private Mailbag mailbag;
	
	public MailbagEnquiryFilter getMailbagEnquiryFilter() {
		return mailbagEnquiryFilter;
	}
	public void setMailbagEnquiryFilter(MailbagEnquiryFilter mailbagEnquiryFilter) {
		this.mailbagEnquiryFilter = mailbagEnquiryFilter;
	}
	public Mailbag getMailbag() {
		return mailbag;
	}
	public void setMailbag(Mailbag mailbag) {
		this.mailbag = mailbag;
	}

}
