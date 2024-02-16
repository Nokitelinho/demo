package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailResditMessageDetailsVO extends AbstractVO{
	
	private List<String> selectedResdits=Collections.emptyList();
	private Collection<MailbagVO> mailbagVO;
	private Collection<MailResditAddressVO> mailResditAddressVO;
	
	
	
	public Collection<MailbagVO> getMailbagVO() {
		return mailbagVO;
	}
	public void setMailbagVO(Collection<MailbagVO> mailbagVO) {
		this.mailbagVO = mailbagVO;
	}
	public Collection<MailResditAddressVO> getMailResditAddressVO() {
		return mailResditAddressVO;
	}
	public void setMailResditAddressVO(Collection<MailResditAddressVO> mailResditAddressVO) {
		this.mailResditAddressVO = mailResditAddressVO;
	}
	public List<String> getSelectedResdits() {
		return new ArrayList<>(selectedResdits);
	}
	public void setSelectedResdits(List<String> selectedResdits) {
		selectedResdits = new ArrayList<>(selectedResdits);
	    this.selectedResdits = Collections.unmodifiableList(selectedResdits);
	}
	
	

}
