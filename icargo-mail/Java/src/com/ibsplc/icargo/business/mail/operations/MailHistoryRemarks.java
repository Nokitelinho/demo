package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;


@Entity
@Table(name = "MALHISNOT")
@Staleable
public class MailHistoryRemarks {
	
	private MailHistoryRemarksPK mailHistoryRemarksPK;
	private long mailSequenceNumber;
	private String remarks;
	private String userName;
	private Calendar remarkDate;
	
	
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "RMKSERNUM")) })
	public MailHistoryRemarksPK getMailHistoryRemarksPK() {
		return mailHistoryRemarksPK;
	}
	
	public void setMailHistoryRemarksPK(MailHistoryRemarksPK mailHistoryRemarksPK) {
		this.mailHistoryRemarksPK = mailHistoryRemarksPK;
	}
	
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	@Column(name = "USRNAM")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "MALSEQNUM")
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	
	@Column(name = "RMKDAT")
	@Temporal(TemporalType.TIMESTAMP)			
	public Calendar getRemarkDate() {
		return remarkDate;
	}
	public void setRemarkDate(Calendar remarkDate) {
		this.remarkDate = remarkDate;
	}
	public MailHistoryRemarks() {

	}

	public MailHistoryRemarks(MailHistoryRemarksVO mailHistoryRemarksVO) {
		setMailHistoryRemarksAttributes(mailHistoryRemarksVO);

			

	}
	private void setMailHistoryRemarksAttributes(MailHistoryRemarksVO mailHistoryRemarksVO){
		populatePK(mailHistoryRemarksVO);
		populateAttributes(mailHistoryRemarksVO);
	}
	
	private void populatePK(MailHistoryRemarksVO mailHistoryRemarksVO){
		mailHistoryRemarksPK = new MailHistoryRemarksPK();
		mailHistoryRemarksPK.setCompanyCode(mailHistoryRemarksVO.getCompanyCode());
	}
	
	public void populateAttributes(MailHistoryRemarksVO mailHistoryRemarksVO) {
		setRemarks(mailHistoryRemarksVO.getRemark());
		setUserName(mailHistoryRemarksVO.getUserName());
		setMailSequenceNumber(mailHistoryRemarksVO.getMailSequenceNumber());
		setRemarkDate(mailHistoryRemarksVO.getRemarkDate());
	}
	
}
