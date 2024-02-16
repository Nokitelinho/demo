package com.ibsplc.icargo.business.mail.operations.vo;





import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * Webservice errors.
 * @author A-3109
 *
 */
public class MailWebserviceErrorVO extends AbstractVO {
	
		
	private String companyCode;
	
	private String hhtVersion;
	
	private String scanningPort;
	
	private int messagePartId;
	
	private String mailBagId;
	
	private String errorCode;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getHhtVersion() {
		return hhtVersion;
	}

	public void setHhtVersion(String hhtVersion) {
		this.hhtVersion = hhtVersion;
	}

	public String getScanningPort() {
		return scanningPort;
	}

	public void setScanningPort(String scanningPort) {
		this.scanningPort = scanningPort;
	}

	public int getMessagePartId() {
		return messagePartId;
	}

	public void setMessagePartId(int messagePartId) {
		this.messagePartId = messagePartId;
	}

	public String getMailBagId() {
		return mailBagId;
	}

	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}	
	
	
}
