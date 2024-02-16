package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;


@KeyTable(table = "MALMRARATLINAUDKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALMRARATLINAUDKEY", key = "RATLINAUD_SEQ")
@Embeddable
public class UPURateAuditPK implements Serializable{

	

	private String companyCode;


    private String rateCardID;

    private int serialNumber;
    
    private int rateLineSerNum;
    
    
    
    @KeyCondition(column="CMPCOD")
    public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@KeyCondition(column="RATCRDCOD")
	public String getRateCardID() {
		return rateCardID;
	}

	public void setRateCardID(String rateCardID) {
		this.rateCardID = rateCardID;
	}
	@Key(generator="ID_GEN", startAt="1")
	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public UPURateAuditPK(String companyCode, String rateCardID,
			int serialNumber, int rateLineSerNum) {
		super();
		this.companyCode = companyCode;
		this.rateCardID = rateCardID;
		this.serialNumber = serialNumber;
		this.rateLineSerNum = rateLineSerNum;
	}

	public UPURateAuditPK() {
	
	}

	/**
	 * 	Getter for rateLineSerNum 
	 *	Added by : A-5219 on 30-Oct-2020
	 * 	Used for :
	 */
	 @KeyCondition(column="RATLINSERNUM")
	public int getRateLineSerNum() {
		return rateLineSerNum;
	}

	/**
	 *  @param rateLineSerNum the rateLineSerNum to set
	 * 	Setter for rateLineSerNum 
	 *	Added by : A-5219 on 30-Oct-2020
	 * 	Used for :
	 */
	public void setRateLineSerNum(int rateLineSerNum) {
		this.rateLineSerNum = rateLineSerNum;
	}
	
	
}
