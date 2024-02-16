/*
 * DamagedDSNVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			JUN 30, 2016				   A-5991			First Draft
 */
public class DamagedDSNVO extends AbstractVO {

    private String companyCode;
    
    private String dsn;
    
    private String originExchangeOffice;
    
    private String destinationExchangeOffice;
    
    private String mailClass;
    
    private int year;
    
    private String consignmentNumber;
    
    private int consignmentSequenceNumber;
    
    private String paCode;
    
    private int damageSequenceNumber;
    
    private int carrierId;
    
    private String flightNumber;
    
    private long flightSequenceNumber;
    
    private int segmentSerialNumber;
    
    private String airportCode;
    
    private int acceptedBags;
    
    //private double acceptedWeight;
    private Measure acceptedWeight;//added by A-7371
    
    private int receivedBags;
    
    //private double receivedWeight;
    private Measure receivedWeight;//added by A-7371
    
    private int returnedBags;
    
    //private double returnedWeight;
    private Measure returnedWeight;//added by A-7371
    
    private int latestReturnedBags;
    
    //private double latestReturnedWeight; 
    private Measure latestReturnedWeight;//added by A-7371
    
    private String ownAirlineCode;
           
    /**
     *  Whether full or partial
     */
    private String returnStatus;
    
    private String lastUpdateUser;
    
    private String containerType;
    
    private String containerNumber;
    
    private String pou;
    
    private Collection<DamagedDSNDetailVO> damagedDsnDetailVOs;
    
    private LocalDate flightDate;
    
    private String carrierCode;
    
    private String operationType ;
    
    private String mailCategoryCode;
    
    private String mailSubclass; 
    
    private String remarks; 
    
    private LocalDate lastUpdateTime;
/**
 * 
 * @return acceptedWeight
 */
    public Measure getAcceptedWeight() {
		return acceptedWeight;
	}
/**
 * 
 * @param acceptedWeight
 */
	public void setAcceptedWeight(Measure acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}
/**
 * 
 * @return receivedWeight
 */
	public Measure getReceivedWeight() {
		return receivedWeight;
	}
/**
 * 
 * @param receivedWeight
 */
	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
/**
 * 
 * @return returnedWeight
 */
	public Measure getReturnedWeight() {
		return returnedWeight;
	}
/**
 * 
 * @param returnedWeight
 */
	public void setReturnedWeight(Measure returnedWeight) {
		this.returnedWeight = returnedWeight;
	}
/**
 * 
 * @return latestReturnedWeight
 */
	public Measure getLatestReturnedWeight() {
		return latestReturnedWeight;
	}
/**
 * 
 * @param latestReturnedWeight
 */
	public void setLatestReturnedWeight(Measure latestReturnedWeight) {
		this.latestReturnedWeight = latestReturnedWeight;
	}

    public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the damagedDsnDetailVOs.
	 */
	public Collection<DamagedDSNDetailVO> getDamagedDsnDetailVOs() {
		return damagedDsnDetailVOs;
	}

	/**
	 * @param damagedDsnDetailVOs The damagedDsnDetailVOs to set.
	 */
	public void setDamagedDsnDetailVOs(
			Collection<DamagedDSNDetailVO> damagedDsnDetailVOs) {
		this.damagedDsnDetailVOs = damagedDsnDetailVOs;
	}

	/**
     * @return Returns the acceptedBags.
     */
    public int getAcceptedBags() {
        return acceptedBags;
    }

    /**
     * @param acceptedBags The acceptedBags to set.
     */
    public void setAcceptedBags(int acceptedBags) {
        this.acceptedBags = acceptedBags;
    }

    /**
     * @return Returns the acceptedWeight.
     */
   /* public double getAcceptedWeight() {
        return acceptedWeight;
    }

    *//**
     * @param acceptedWeight The acceptedWeight to set.
     *//*
    public void setAcceptedWeight(double acceptedWeight) {
        this.acceptedWeight = acceptedWeight;
    }*/

    /**
     * @return Returns the airportCode.
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * @param airportCode The airportCode to set.
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * @return Returns the carrierId.
     */
    public int getCarrierId() {
        return carrierId;
    }

    /**
     * @param carrierId The carrierId to set.
     */
    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * @return Returns the consignmentNumber.
     */
    public String getConsignmentNumber() {
        return consignmentNumber;
    }

    /**
     * @param consignmentNumber The consignmentNumber to set.
     */
    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }

    /**
     * @return Returns the damageSequenceNumber.
     */
    public int getDamageSequenceNumber() {
        return damageSequenceNumber;
    }

    /**
     * @param damageSequenceNumber The damageSequenceNumber to set.
     */
    public void setDamageSequenceNumber(int damageSequenceNumber) {
        this.damageSequenceNumber = damageSequenceNumber;
    }

    /**
     * @return Returns the damageStatus.
     */
    public String getReturnStatus() {
        return returnStatus;
    }

    /**
     * @param damageStatus The damageStatus to set.
     */
    public void setReturnStatus(String damageStatus) {
        this.returnStatus = damageStatus;
    }

    /**
     * @return Returns the destinationExchangeOffice.
     */
    public String getDestinationExchangeOffice() {
        return destinationExchangeOffice;
    }

    /**
     * @param destinationExchangeOffice The destinationExchangeOffice to set.
     */
    public void setDestinationExchangeOffice(String destinationExchangeOffice) {
        this.destinationExchangeOffice = destinationExchangeOffice;
    }

    /**
     * @return Returns the dsn.
     */
    public String getDsn() {
        return dsn;
    }

    /**
     * @param dsn The dsn to set.
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    /**
     * @return Returns the flightNumber.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * @param flightNumber The flightNumber to set.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return Returns the flightSequenceNumber.
     */
    public long getFlightSequenceNumber() {
        return flightSequenceNumber;
    }

    /**
     * @param flightSequenceNumber The flightSequenceNumber to set.
     */
    public void setFlightSequenceNumber(long flightSequenceNumber) {
        this.flightSequenceNumber = flightSequenceNumber;
    }

    /**
     * @return Returns the lastUpdateUser.
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * @return Returns the mailClass.
     */
    public String getMailClass() {
        return mailClass;
    }

    /**
     * @param mailClass The mailClass to set.
     */
    public void setMailClass(String mailClass) {
        this.mailClass = mailClass;
    }

    /**
     * @return Returns the originExchangeOffice.
     */
    public String getOriginExchangeOffice() {
        return originExchangeOffice;
    }

    /**
     * @param originExchangeOffice The originExchangeOffice to set.
     */
    public void setOriginExchangeOffice(String originExchangeOffice) {
        this.originExchangeOffice = originExchangeOffice;
    }

    /**
     * @return Returns the returnedBags.
     */
    public int getReturnedBags() {
        return returnedBags;
    }

    /**
     * @param returnedBags The returnedBags to set.
     */
    public void setReturnedBags(int returnedBags) {
        this.returnedBags = returnedBags;
    }

    /**
     * @return Returns the returnedWeight.
     */
    /*public double getReturnedWeight() {
        return returnedWeight;
    }

    *//**
     * @param returnedWeight The returnedWeight to set.
     *//*
    public void setReturnedWeight(double returnedWeight) {
        this.returnedWeight = returnedWeight;
    }
*/
    /**
     * @return Returns the segmentSerialNumber.
     */
    public int getSegmentSerialNumber() {
        return segmentSerialNumber;
    }

    /**
     * @param segmentSerialNumber The segmentSerialNumber to set.
     */
    public void setSegmentSerialNumber(int segmentSerialNumber) {
        this.segmentSerialNumber = segmentSerialNumber;
    }

    /**
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return Returns the consignmentSequenceNumber.
     */
    public int getConsignmentSequenceNumber() {
        return consignmentSequenceNumber;
    }

    /**
     * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
     */
    public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
        this.consignmentSequenceNumber = consignmentSequenceNumber;
    }

    /**
     * @return Returns the paCode.
     */
    public String getPaCode() {
        return paCode;
    }

    /**
     * @param paCode The paCode to set.
     */
    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    /**
     * @return Returns the containerNumber.
     */
    public String getContainerNumber() {
        return containerNumber;
    }

    /**
     * @param containerNumber The containerNumber to set.
     */
    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    /**
     * @return Returns the containerType.
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * @param containerType The containerType to set.
     */
    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    /**
     * @return Returns the pou.
     */
    public String getPou() {
        return pou;
    }

    /**
     * @param pou The pou to set.
     */
    public void setPou(String pou) {
        this.pou = pou;
    }

    /**
     * @return Returns the latestReturnedBags.
     */
    public int getLatestReturnedBags() {
        return latestReturnedBags;
    }

    /**
     * @param latestReturnedBags The latestReturnedBags to set.
     */
    public void setLatestReturnedBags(int latestReturnedBags) {
        this.latestReturnedBags = latestReturnedBags;
    }

    /**
     * @return Returns the latestReturnedWeight.
     */
    /*public double getLatestReturnedWeight() {
        return latestReturnedWeight;
    }

    *//**
     * @param latestReturnedWeight The latestReturnedWeight to set.
     *//*
    public void setLatestReturnedWeight(double latestReturnedWeight) {
        this.latestReturnedWeight = latestReturnedWeight;
    }
*/
    /**
     * @return Returns the carrierCode.
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * @param carrierCode The carrierCode to set.
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    /**
     * @return Returns the flightDate.
     */
    public LocalDate getFlightDate() {
        return flightDate;
    }

    /**
     * @param flightDate The flightDate to set.
     */
    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

	/**
	 * @return Returns the ownAirlineCode.
	 */
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}

	/**
	 * @param ownAirlineCode The ownAirlineCode to set.
	 */
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}

    /**
     * @return Returns the receivedBags.
     */
    public final int getReceivedBags() {
        return receivedBags;
    }

    /**
     * @param receivedBags The receivedBags to set.
     */
    public final void setReceivedBags(int receivedBags) {
        this.receivedBags = receivedBags;
    }

    /**
     * @return Returns the receivedWeight.
     */
    /*public final double getReceivedWeight() {
        return receivedWeight;
    }

    *//**
     * @param receivedWeight The receivedWeight to set.
     *//*
    public final void setReceivedWeight(double receivedWeight) {
        this.receivedWeight = receivedWeight;
    }
*/
    /**
     * @return Returns the operationType.
     */
    public final String getOperationType() {
        return operationType;
    }

    /**
     * @param operationType The operationType to set.
     */
    public final void setOperationType(String operationType) {
        this.operationType = operationType;
    }

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
    
}
