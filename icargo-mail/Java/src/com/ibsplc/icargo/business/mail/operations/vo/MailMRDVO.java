/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
/**
 * 
 * @author A-3109
 *
 */
public class MailMRDVO extends AbstractVO{
  
	private String companyCode;
	private Collection<HandoverVO> handoverVO;
	private HashMap<String,Collection<HandoverVO>> handovers;
	private Collection<ErrorVO> handoverErrors;
	private HashMap<String,String> mailBags;
	
	

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * @return the handovers
	 */
	public HashMap<String, Collection<HandoverVO>> getHandovers() {
		return handovers;
	}
	/**
	 * @param handovers the handovers to set
	 */
	public void setHandovers(HashMap<String, Collection<HandoverVO>> handovers) {
		this.handovers = handovers;
	}

	/**
	 * @return the handoverErrors
	 */
	public Collection<ErrorVO> getHandoverErrors() {
		return handoverErrors;
	}
	/**
	 * @param handoverErrors the handoverErrors to set
	 */
	public void setHandoverErrors(Collection<ErrorVO> handoverErrors) {
		this.handoverErrors = handoverErrors;
	}
	/**
	 * @return the mailBags
	 */
	public HashMap<String, String> getMailBags() {
		return mailBags;
	}
	/**
	 * @param mailBags the mailBags to set
	 */
	public void setMailBags(HashMap<String, String> mailBags) {
		this.mailBags = mailBags;
	}
	/**
	 * @return the handoverVO
	 */
	public Collection<HandoverVO> getHandoverVO() {
		return handoverVO;
	}
	/**
	 * @param handoverVO the handoverVO to set
	 */
	public void setHandoverVO(Collection<HandoverVO> handoverVO) {
		this.handoverVO = handoverVO;
	}
	
 
}