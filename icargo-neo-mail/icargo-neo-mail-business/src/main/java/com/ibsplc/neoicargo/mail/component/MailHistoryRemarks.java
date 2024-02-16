package com.ibsplc.neoicargo.mail.component;


import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.vo.MailHistoryRemarksVO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

@Setter
@Getter
@Slf4j
@Entity
@IdClass(MailHistoryRemarksPK.class)
@SequenceGenerator(name = "MALHISNOTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALHISNOT_SEQ")
@Table(name = "MALHISNOT")
public class MailHistoryRemarks extends BaseEntity implements Serializable {
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;
	@Column(name = "RMK")
	private String remarks;
	@Column(name = "USRNAM")
	private String userName;
	//TODO: Verify in Neo
	//@Temporal(TemporalType.TIMESTAMP)--- removed timstamp to fix compilation
	@Column(name = "RMKDAT")
	private LocalDateTime remarkDate;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALHISNOTSEQ")
	@Column(name = "RMKSERNUM")
	private long serialNumber;
	@Id
	@Transient
	private String companyCode;

	public MailHistoryRemarks(MailHistoryRemarksVO mailHistoryRemarksVO) {

		populatePK(mailHistoryRemarksVO);
		populateAttributes(mailHistoryRemarksVO);
	}
	
	private void populatePK(MailHistoryRemarksVO mailHistoryRemarksVO){
		this.setCompanyCode(mailHistoryRemarksVO.getCompanyCode());
	}
	
	public void populateAttributes(MailHistoryRemarksVO mailHistoryRemarksVO) {
		setRemarks(mailHistoryRemarksVO.getRemark());
		setUserName(mailHistoryRemarksVO.getUserName());
		setMailSequenceNumber(mailHistoryRemarksVO.getMailSequenceNumber());
		setRemarkDate(mailHistoryRemarksVO.getRemarkDate().toLocalDateTime());
	}
	
}
