package com.ibsplc.icargo.business.mail.mra.gpareporting;


import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 * 
 * @author A-7371
 *
 */
@SequenceKeyGenerator(name="ID_GEN",sequence="MALMRAGPAINCMSGMST_SEQ")
@Embeddable
public class MailInvoicMessageMasterPK implements Serializable {
	
		
	    private String companyCode;     
	    private long serialNumber; 

	    
		public MailInvoicMessageMasterPK() {
			super();
		}

		public MailInvoicMessageMasterPK(String companyCode, long serialNumber) {
			this.companyCode = companyCode;
			this.serialNumber = serialNumber;
		}

		public boolean equals(Object other) {
			return (other != null) && ((hashCode() == other.hashCode()));
		}
	   
	    
		public int hashCode() {

			return new StringBuffer(companyCode).
					
					append(serialNumber).
					toString().hashCode();
		}

		@KeyCondition(column = "CMPCOD")
		public String getCompanyCode() {
			return companyCode;
		}


		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}

		@Key(generator = "ID_GEN", startAt = "1" )
		public long getSerialNumber() {
			return serialNumber;
		}


		public void setSerialNumber(long serialNumber) {
			this.serialNumber = serialNumber;
		}

		
		public String toString() {
			StringBuilder sbul = new StringBuilder(205);
			sbul.append("InvoicMessageMasterPK [ ");
			sbul.append("companyCode '").append(this.companyCode);

			sbul.append("serialNumber '").append(this.serialNumber);
			sbul.append("' ]");
			return sbul.toString();
		}



}
