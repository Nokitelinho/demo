/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.PostalCalendarAuditPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	13-Aug-2020	:	Draft
 */
@KeyTable(table = "SHRENTAUDKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "SHRENTAUDKEY", key = "SHRENTAUD_SEQ")
@Embeddable

public class PostalCalendarAuditPK implements Serializable {
	
	private String companyCode;
	
	private long serialNumber;
	
    private String entityCode;

	/**
	 * @return the companyCode
	 */
	@KeyCondition(column="CMPCOD")
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
	 * 	Getter for entityCode 
	 *	Added by : A-5219 on 22-Aug-2020
	 * 	Used for :
	 */
	@KeyCondition(column="ENTCOD")
	public String getEntityCode() {
		return entityCode;
	}

	/**
	 *  @param entityCode the entityCode to set
	 * 	Setter for entityCode 
	 *	Added by : A-5219 on 22-Aug-2020
	 * 	Used for :
	 */
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	PostalCalendarAuditPK(String companyCode, String entityCode, 
			 long serialNumber){
		this.companyCode = companyCode;
		this.entityCode = entityCode;
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the serialNumber
	 */
	@Key(generator="ID_GEN", startAt="1")
	public long getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public boolean equals(Object obj) {
		if (obj != null) {
			return (hashCode() == obj.hashCode());
		}
		return false;
	}

	/**
	 * @return int
	 */

	public int hashCode() {
		return new StringBuffer(companyCode).append(entityCode)
				.append(serialNumber).toString().hashCode();

	}
	
	public PostalCalendarAuditPK(){
		
	}

}
