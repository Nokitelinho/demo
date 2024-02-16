
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


public class CoTerminusVO extends AbstractVO {
	
	private String gpaCode;
	private String coAirportCodes; 
	public String getCoAirportCodes() {
		return coAirportCodes;
	}
	public void setCoAirportCodes(String coAirportCodes) {
		this.coAirportCodes = coAirportCodes;
	}
	public String getCoOperationFlag() {
		return coOperationFlag;
	}
	public void setCoOperationFlag(String coOperationFlag) {
		this.coOperationFlag = coOperationFlag;
	}
	private String resditModes;
	private String truckFlag;
	
	private String companyCode;  
	
	private String coOperationFlag;
    private LocalDate lastUpdateTime;
	private String lastUpdateUser;
    private long seqnum;
	
	public long getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(long seqnum) {
		this.seqnum = seqnum;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	
	public String getResditModes() {
		return resditModes;
	}
	public void setResditModes(String resditModes) {
		this.resditModes = resditModes;
	}
	public String getTruckFlag() {
		return truckFlag;
	}
	public void setTruckFlag(String truckFlag) {
		this.truckFlag = truckFlag;
	}
	 
}
