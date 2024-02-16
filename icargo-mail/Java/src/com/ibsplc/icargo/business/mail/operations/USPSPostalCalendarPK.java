package com.ibsplc.icargo.business.mail.operations;
import java.io.Serializable;
import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;
@SequenceKeyGenerator(name="ID_GEN", sequence = "MALCALMST_SEQ")
@Embeddable
public class USPSPostalCalendarPK implements Serializable{
	private String companyCode;
	private String gpacod;
	private String filterCalender;
	private long serialNumber;
	@KeyCondition(column="CMPCOD")    
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getGpacod() {
		return gpacod;
	}
	public void setGpacod(String gpacod) {
		this.gpacod = gpacod;
	}
	public String getFilterCalender() {
		return filterCalender;
	}
	public void setFilterCalender(String filterCalender) {
		this.filterCalender = filterCalender;
	}
	@Key(generator="ID_GEN", startAt="1")
	public long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(106);
		sbul.append("USPSPostalCalendarPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', gpacod '").append(this.gpacod);
		sbul.append("', filterCalender '").append(this.filterCalender);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		USPSPostalCalendarPK other = (USPSPostalCalendarPK) obj;
		if (serialNumber != other.serialNumber)
			return false;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (gpacod == null) {
			if (other.gpacod != null)
				return false;
		} else if (!gpacod.equals(other.gpacod))
			return false;
		if (filterCalender == null) {
			if (other.filterCalender != null)
				return false;
		} else if (!filterCalender.equals(other.filterCalender))
			return false;
		return true;
    }
	 public int hashCode() {
		 final int prime = 31;
			int result = 1;
			result = prime * result + (int) (serialNumber ^ (serialNumber >>> 32));
			result = prime * result
					+ ((companyCode == null) ? 0 : companyCode.hashCode());
			result = prime * result + ((gpacod == null) ? 0 : gpacod.hashCode());
			result = prime * result + ((filterCalender == null) ? 0 : filterCalender.hashCode());
			return result;
	    }
}