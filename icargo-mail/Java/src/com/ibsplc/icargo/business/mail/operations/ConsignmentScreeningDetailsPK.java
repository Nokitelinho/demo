package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

import javax.persistence.Embeddable;
import java.io.Serializable;

@SequenceKeyGenerator(name="ID_GEN",sequence="MALCSGCSDDTL_SEQ")
@Embeddable
public class ConsignmentScreeningDetailsPK implements Serializable {

    private String companyCode;




    private long serialNumber;

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

    @Override
    public boolean equals(Object other) {
        return (other != null) && ((hashCode() == other.hashCode()));
    }

    @Override
    public int hashCode() {
        return new StringBuffer(companyCode).append(serialNumber).toString().hashCode();
    }
    @Override
    public String toString() {
        StringBuilder sbul = new StringBuilder("ConsignementScreeningDetailsPK [ ")
        .append("companyCode '").append(this.companyCode)
        .append("', serialNumber '").append(this.serialNumber)
        .append("' ]");
        return sbul.toString();
    }

    /**
     * Dummy constructor
     */
    public ConsignmentScreeningDetailsPK() {
    }
}
