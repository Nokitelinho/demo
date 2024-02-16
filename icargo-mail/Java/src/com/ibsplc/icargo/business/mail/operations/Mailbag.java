/*
 * Mailbag.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.*;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import javax.persistence.*;
import java.util.*;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.DESTN_FLT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

/**
 * @author A-5991
 *
 * Entity storing mailbag details.
 * *
 */
@Entity
@Table(name = "MALMST_VW_BASE")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Mailbag {

	private static final String MODULE_NAME = "mail.operations";
	
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA="mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String ASSIGNED_FROM_DEV_PANEL = "Asg. from Dev.Panel";
	private static final String ATTACHED_TO_AWB= " was attached with AWB ";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	private static final String MLDMSGSENDINGREQRD="mail.operations.mldsendingrequired";
	 private StringBuilder errorString = new StringBuilder();
    private MailbagPK mailbagPK;
	private static final String NO="N";
    /*
     * Last Digit of year
     */

    private String receptacleSerialNumber;
    private String registeredOrInsuredIndicator;
    private String highestNumberedReceptacle;
    private double weight;

    private Calendar despatchDate;
    private String scannedUser;

    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private String uldNumber;

    // private Set<MailbagHistory> mailbagHistories;
     private String pou;
     private Calendar scannedDate;
     private String scannedPort;
     private String latestStatus;

	private String irregularityflag;
	private String orginOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategory;
	private String mailSubClass;
	private int year;
	private String despatchSerialNumber;

     private String operationalStatus;

     private String containerType;

     private String consignmentNumber;
     private String lastUpdateUser;

 	 private Calendar lastUpdateTime;

 	 private Calendar purgingCloseDate;

     private String paCode;

     private double volume;


     private int consignmentSequenceNumber;

     private Set<DamagedMailbag> damagedMailbags;

     private String damageFlag;

     private String mailClass;

     private String bellyCartId;

     private String mailCompanyCode;
 	 private String mailIdr;
 	 private String dsnIdr;

	private Calendar consignmentDate;
 	private String mailType;
 	private Calendar reqDeliveryTime;//Added as part of ICRD-214795

    private String mailRemarks;
	//added by a-7779 for icrd-192536 starts
 	//private String ubrNumber;
 	 private int documentOwnerId;
 	 private String masterDocumentNumber;
 	 private int dupliacteNumber;
 	 private int sequenceNumber;
 	private String shipmentPrefix;
 	private String displayUnit;//added by A-7371 for ICRD-234941
 	private double displayValue;
 	private String mailServiceLevel;//Added for ICRD-243469
 	private String selnum; //Added by A-8236 for ICRD-262947
 	private String onTimeDelivery;//Added for ICRD-243421
	private String origin;
 	private String destination;
 
 	private String metTransWindow; //Added by A-8464 for ICRD-240360 
 	private Calendar transWindowEndTime; //Added by A-8464 for ICRD-240360 
 	private String mailbagSource; // Added by A-4809 for IASCB-137
 	private String scanWavedFlag;//Added by A-7794 as part of ICRD-232299
 	private double actualWeight; //Added by A-8672 for ICRD-255039
 	private String displayVolUnit;//added by A-8353 for ICRD-274933
 	//Added by A-7540 
 	private String rfdFlag;
 	//Added by A-8672 for ICRD-255039 starts
 	//added by a-7779 for ICRD-326752 starts
 	private String actualWeightDisplayUnit;
 	private double actualWeightDisplayValue;
 	//added by a-7779 for ICRD-326752 ends
 	//Added by a-7929 as part of IASCB-28260
 	private String contractIDNumber;
 	private String paBuiltFlag;
    private Calendar latestAcceptanceTime;

	private String acceptanceAirportCode;
	private Calendar acceptanceScanDate; 
	private String acceptancePostalContainerNumber;
	private Calendar firstScanDate;
	private String firstScanPort;
	private String securityStatusCode;
 	
 	@Column(name="CTRNUM")
 	public String getContractIDNumber() {
		return contractIDNumber;
	}
	public void setContractIDNumber(String contractIDNumber) {
		this.contractIDNumber = contractIDNumber;
	}
 	
 	 @Column(name="ACTWGT")
 	 public double getActualWeight() {
 		return actualWeight;
 	}
 	public void setActualWeight(double actualWeight) {
 		this.actualWeight = actualWeight;
 	}
 	//Added by A-8672 for ICRD-255039 ends
 	
	@Column(name="DSPWGTUNT")
 	public String getDisplayUnit() {
		return displayUnit;
	}
	public void setDisplayUnit(String displayUnit) {
		this.displayUnit = displayUnit;
	}
	@Column(name="DSPWGT")
	public double getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(double displayValue) {
		this.displayValue = displayValue;
	}
	


 	 /*@Column(name="UBRNUM")
     public String getUbrNumber() {
		return ubrNumber;
	}
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}*/
	@Column(name="DOCOWRIDR")
	public int getDocumentOwnerId() {
		return documentOwnerId;
	}
	public void setDocumentOwnerId(int documentOwnerId) {
		this.documentOwnerId = documentOwnerId;
	}
	@Column(name="MSTDOCNUM")
	 @Audit(name="masterDocumentNumber")
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	@Column(name="DUPNUM")
	public int getDupliacteNumber() {
		return dupliacteNumber;
	}
	public void setDupliacteNumber(int dupliacteNumber) {
		this.dupliacteNumber = dupliacteNumber;
	}
	@Column(name="SEQNUM")
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	@Column(name="SHPPFX")
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	//added by a-7779 for icrd-192536 ends


     /**
	 * 	Getter for mailRemarks 
	 *	Added by : a-7540 on 18-Jul-2017
	 * 	Used for :ICRD-197419 for new field 'remarks'
	 */
    @Column(name="MALRMK")
	public String getMailRemarks() {
		return mailRemarks;
	}
	/**
	 *  @param mailRemarks the mailRemarks to set
	 * 	Setter for mailRemarks 
	 *	Added by : a-7540 on 18-Jul-2017
	 * 	Used for :ICRD-197419 for new field 'remarks'
	 */
	public void setMailRemarks(String mailRemarks) {
		this.mailRemarks = mailRemarks;
	}
     private Log log = LogFactory.getLogger("MAIL_OPERATIONS");
     private static final String COUNTRY_CACHE="COUNTRY";
     private static final String CITY_CACHE="CITY";
     private static final String EXCHANGE_CACHE="EXCHANGE";
     private static final String ERROR_CACHE="ERROR";
     private static final String SUBCLS_CACHE="MailSubClass";
     private static final String CITYPAIR_CACHE="CITYPAIRDESTINATION";
     private static final Logger errPgExceptionLogger = ExtendedLogManager.getLogger("MAILHHTERR");
	 private static final Log LOGGER = LogFactory.getLogger("MailbagEntityClass");

     private static final String ORIGINEXCHANGE_ERR="OOE";

     private static final String DESTINATIONEXCHANGE_ERR="DOE";

 	private static final String MTK_INB_ONLINEFLT_CLOSURE="MTK_INB_ONLINEFLT_CLOSURE";
     private static final String ORIGIN_COUNTRY_ERR="COUNTRYORIGIN";

     private static final String DESTN_COUNTRY_ERR="COUNTRYDESTINATION";

     private static final String ORIGIN_CITY_ERR = "CITYORIGIN";

     private static final String DESTN_CITY_ERR="CITYDESTINATION";


     private static final String ORIGIN_PAIR_ERR="CITYPAIRORIGIN";

     private static final String DESTN_PAIR_ERR="CITYPAIRDESTINATION";


	private static final String ORIGIN_EXG_PA_ERR = "ORGPANOTPRESENT";

	private static final String MTK_IMP_FLT="MTK_IMP_FLT";

     private String intFlg;



	 @Column(name="MALTYP")
    public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	/**
     * @return Returns the carrierId.
     *
     */
    @Column(name="FLTCARIDR")
    @Audit(name="carrierId")
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
     * @return Returns the despatchDate.
     *
     */
    @Column(name="DSPDAT")

	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getDespatchDate() {
        return despatchDate;
    }
    /**
     * @param despatchDate The despatchDate to set.
     */
    public void setDespatchDate(Calendar despatchDate) {
        this.despatchDate = despatchDate;
    }

    /**
     * @return Returns the flightNumber.
     *
     */
    @Column(name="FLTNUM")
    @Audit(name="flightNumber")
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
     *
     */
    @Column(name="FLTSEQNUM")
    @Audit(name="flightSequenceNumber")
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
     * @return Returns the highestNumberedReceptacle.
     *
     */
    @Column(name="HSN")
    public String getHighestNumberedReceptacle() {
        return highestNumberedReceptacle;
    }
    /**
     * @param highestNumberedReceptacle The highestNumberedReceptacle to set.
     */
    public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
        this.highestNumberedReceptacle = highestNumberedReceptacle;
    }
    /**
     * @return Returns the mailbagHistories.
     *
     *   */
   /* @OneToMany
    @JoinColumns( {
    @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
    @JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM", insertable=false, updatable=false)})
    public Set<MailbagHistory> getMailbagHistories() {
      //  return mailbagHistories;
    	return null;
    }*/
    /**
     * @param mailbagHistories The mailbagHistories to set.
     */
   /* public void setMailbagHistories(Set<MailbagHistory> mailbagHistories) {
        //this.mailbagHistories = mailbagHistories;
    }*/
    /**
     * @return Returns the mailbagPK.
     *
     */
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
        @AttributeOverride(name="mailSequenceNumber", column=@Column(name="MALSEQNUM"))} )
    public MailbagPK getMailbagPK() {
        return mailbagPK;
    }
    /**
     * @param mailbagPK The mailbagPK to set.
     */
    public void setMailbagPK(MailbagPK mailbagPK) {
        this.mailbagPK = mailbagPK;
    }
    /**
     * @return Returns the mailCategoryCode.
     *
     */


    /**
     * @return Returns the receptacleSerialNumber.
     *
     */
    @Column(name="RSN")
    public String getReceptacleSerialNumber() {
        return receptacleSerialNumber;
    }
    /**
     * @param receptacleSerialNumber The receptacleSerialNumber to set.
     */
    public void setReceptacleSerialNumber(String receptacleSerialNumber) {
        this.receptacleSerialNumber = receptacleSerialNumber;
    }
    /**
     * @return Returns the registeredOrInsuredIndicator.
     *
     */
    @Column(name="REGIND")
    public String getRegisteredOrInsuredIndicator() {
        return registeredOrInsuredIndicator;
    }
    /**
     * @param registeredOrInsuredIndicator The registeredOrInsuredIndicator to set.
     */
    public void setRegisteredOrInsuredIndicator(
            String registeredOrInsuredIndicator) {
        this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
    }
    /**
     * @return Returns the scannedUser.
     *
     */
    @Column(name="SCNUSR")
    @Audit(name="scannedUser")
    public String getScannedUser() {
        return scannedUser;
    }
    /**
     * @param scannedUser The scannedUser to set.
     */
    public void setScannedUser(String scannedUser) {
        this.scannedUser = scannedUser;
    }
    /**
     * @return Returns the segmentSerialNumber.
     *
     */
    @Column(name="SEGSERNUM")
    @Audit(name="segmentSerialNumber")
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
     * @return Returns the uldNumber.
     *
     */
    @Column(name="CONNUM")
    public String getUldNumber() {
        return uldNumber;
    }
    /**
     * @param uldNumber The uldNumber to set.
     */
    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }
    /**
     * @return Returns the weight.
     *
     */
    @Column(name="WGT")
    public double getWeight() {
        return weight;
    }
    /**
     * @param weight The weight to set.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }


    /**
     * @return Returns the pou.
     *
     *
     */
    @Column(name="POU")
    @Audit(name="pou")
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
     * @return Returns the scannedDate.
     *
     */
    @Column(name="SCNDAT")
    @Audit(name="scannedDate")

	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getScannedDate() {
        return scannedDate;
    }
    /**
     * @param scannedDate The scannedDate to set.
     */
    public void setScannedDate(Calendar scannedDate) {
        this.scannedDate = scannedDate;
    }
    /**
     * @return Returns the scannedPort.
     *
     */
    @Column(name="SCNPRT")
    @Audit(name="scannedPort")
    public String getScannedPort() {
        return scannedPort;
    }
    /**
     * @param scannedPort The scannedPort to set.
     */
    public void setScannedPort(String scannedPort) {
        this.scannedPort = scannedPort;
    }

    /**
     * @return Returns the latestStatus.
     *
     */
    @Column(name="MALSTA")
    @Audit(name="latestStatus")
    public String getLatestStatus() {
        return latestStatus;
    }
    /**
     * @param latestStatus The latestStatus to set.
     */
    public void setLatestStatus(String latestStatus) {
        this.latestStatus = latestStatus;
    }




  /**
   * @author A-5991
   * This method id used to find the instance of the Entity
   * @param mailbagPK
   * @return
   * @throws FinderException
   * @throws SystemException
   */
  public static Mailbag find(MailbagPK mailbagPK)
      throws FinderException, SystemException {
      return PersistenceController.getEntityManager().find(Mailbag.getInstance().getClass(), mailbagPK);
  }
    /**
     * @return Returns the containerType.
     */
     @Column(name="CONTYP")
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
     * @return Returns the operatingStatus.
     */
    @Column(name="OPRSTA")
    public String getOperationalStatus() {
        return operationalStatus;
    }
    /**
     * @param operatingStatus The operatingStatus to set.
     */
    public void setOperationalStatus(String operatingStatus) {
        this.operationalStatus = operatingStatus;
    }


    /**
     * @return Returns the consignmentNumber.
     */
    @Column(name="CSGDOCNUM")
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
     * @return Returns the consignmentSequenceNumber.
     */
    @Column(name="CSGSEQNUM")
    public int getConsignmentSequenceNumber() {
        return consignmentSequenceNumber;
    }
    /**
     * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
     */
    public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
        this.consignmentSequenceNumber =consignmentSequenceNumber;
    }
    /**
     * @return Returns the paCode.
     */
    @Column(name="POACOD")
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
     * @return Returns the damagedMailbags.
     */
    @OneToMany
    @JoinColumns( {
     @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
      @JoinColumn(name="MALSEQNUM", referencedColumnName="MALSEQNUM", insertable=false, updatable=false)})
    public Set<DamagedMailbag> getDamagedMailbags() {
        return damagedMailbags;
    }
    /**
     * @param damagedMailbags The damagedMailbags to set.
     */
    public void setDamagedMailbags(Set<DamagedMailbag> damagedMailbags) {
        this.damagedMailbags = damagedMailbags;
    }




    /**
     * @return Returns the damageFlag.
     */
    @Column(name="DMGFLG")
    public String getDamageFlag() {
        return damageFlag;
    }
    /**
     * @param damageFlag The damageFlag to set.
     */
    public void setDamageFlag(String damageFlag) {
        this.damageFlag = damageFlag;
    }



	/**
	 * @return Returns the mailClass.
	 */
	@Column(name="MALCLS")
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}





	 @Version
	 @Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "VOL")
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}

	/**
	 * @param mailCompanyCode the mailCompanyCode to set
	 */
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	/**
	 * @return the mailCompanyCode
	 */
	@Column(name="MALCMPCOD")
	 @Audit(name="MailCompanyCode")
	public String getMailCompanyCode() {
		return mailCompanyCode;
	}

	@Column(name = "MALIDR")
	@Audit(name="Mailbag ID")
	public String getMailIdr() {
		return mailIdr;
	}
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}
	@Column(name = "DSNIDR")
 	public String getDsnIdr() {
		return dsnIdr;
	}
	public void setDsnIdr(String dsnIdr) {
		this.dsnIdr = dsnIdr;
	}


	/**
	 * @return the orginOfficeOfExchange
	 */
	@Column(name = "ORGEXGOFC")
	public String getOrginOfficeOfExchange() {
		return orginOfficeOfExchange;
	}
	/**
	 * @param orginOfficeOfExchange the orginOfficeOfExchange to set
	 */
	public void setOrginOfficeOfExchange(String orginOfficeOfExchange) {
		this.orginOfficeOfExchange = orginOfficeOfExchange;
	}
	/**
	 * @return the destinationOfficeOfExchange
	 */
	@Column(name = "DSTEXGOFC")
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}
	/**
	 * @param destinationOfficeOfExchange the destinationOfficeOfExchange to set
	 */
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}
	/**
	 * @return the mailCategory
	 */
	@Column(name = "MALCTG")
	public String getMailCategory() {
		return mailCategory;
	}
	/**
	 * @param mailCategory the mailCategory to set
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	/**
	 * @return the mailSubClass
	 */
	@Column(name = "MALSUBCLS")
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 * @return the year
	 */
	@Column(name = "YER")
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the despatchSerialNumber
	 */
	@Column(name = "DSN")
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}
	/**
	 * @param despatchSerialNumber the despatchSerialNumber to set
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return the purgingCloseDate
	 */
	@Column(name = "PRGCLSDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getPurgingCloseDate() {
		return purgingCloseDate;
	}
	/**
	 * @param purgingCloseDate the purgingCloseDate to set
	 */
	public void setPurgingCloseDate(Calendar purgingCloseDate) {
		this.purgingCloseDate = purgingCloseDate;
	}
	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
    @Column(name="REQDLVTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(Calendar reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	/**
	 * @return the mailServiceLevel
	 */
	 @Column(name="MALSRVLVL")
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}
	 
	//Added by A-8236 for ICRD-262947
	 /**
	 * @return the selnum
	 */
	@Column(name="SELNUM")
	 public String getSelnum() {
		 return selnum;
	 }
	 /**
	 * @param selnum - to set selnum
	 */
	public void setSelnum(String selnum) {
		 this.selnum = selnum;
	 }
	/**
	 * @return the onTimeDelivery
	 */
	@Column(name="ONNTIMDLVFLG")
	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}
	/**
	 * @param onTimeDelivery the onTimeDelivery to set
	 */
	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	 }
	/**
	 * @param mailServiceLevel the mailServiceLevel to set
	 */
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}
	
	/**
	 * @return origin field
	 */
	@Column(name="ORGCOD")
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return destination
	 */
	@Column(name="DSTCOD")
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return metTransWindow 
	 */
	@Column(name="METTRPSRVFLG")
	public String getMetTransWindow() {
		return metTransWindow; 
	}
	/**
	 * @param metTransWindow :  Y or N ,whether mailbag has
	 * 							met transportation window
	 */
	public void setMetTransWindow(String metTransWindow) {
		this.metTransWindow = metTransWindow;
	}
	/**
	 * @return transWindowEndTime 
	 */
	@Column(name="TRPSRVENDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getTransWindowEndTime() {
		return transWindowEndTime;
	}
	/**
	 * @param transWindowEndTime :  Transportation service window end time
	 */
	public void setTransWindowEndTime(Calendar transWindowEndTime) {
		this.transWindowEndTime = transWindowEndTime;
	}
	/**
	 * 	Method		:	Mailbag.getMailbagSource
	 *	Added by 	:	A-4809 on Nov 22, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	@Column(name="MALSRC")
	public String getMailbagSource() {
		return mailbagSource;
	}
	public void setMailbagSource(String mailbagSource) {
		this.mailbagSource = mailbagSource;
	}
	/***
	 * @author A-7794
	 * @return
	 */
	@Column(name="SCNWVDFLG")
	public String getScanWavedFlag() {
		return scanWavedFlag;
	}
	public void setScanWavedFlag(String scanWavedFlag) {
		this.scanWavedFlag = scanWavedFlag;
	}  
   /**
	 *Added by:A-8353 for ICRD-274933 
	 * @return displayVolUnit
	 */
	@Column(name="VOLUNT")
	public String getDisplayVolUnit() {
		return displayVolUnit;
	}
	/**
	 * 
	 * @param displayVolUnit
	 */
	public void setDisplayVolUnit(String displayVolUnit) {
		this.displayVolUnit = displayVolUnit;
     }
	/**
	 * @return the rfdFlag
	 */
 	@Column(name="RFDFLG")
	public String getRfdFlag() {
		return rfdFlag;
	}
	/**
	 * @param rfdFlag the rfdFlag to set
	 */
	public void setRfdFlag(String rfdFlag) {
		this.rfdFlag = rfdFlag;
	}
	@Column(name = "POAFLG")
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	/**
	 * @param paBuiltFlag
	 *            The paBuiltFlag to set.
	 */
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
    @Column(name="LATACTTIM")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLatestAcceptanceTime() {
        return latestAcceptanceTime;
    }

    public void setLatestAcceptanceTime(Calendar latestAcceptanceTime) {
        this.latestAcceptanceTime = latestAcceptanceTime;
    }
    @Column(name="ACPARPCOD")
	public String getAcceptanceAirportCode() {
		return acceptanceAirportCode;
	}
	public void setAcceptanceAirportCode(String acceptanceAirportCode) {
		this.acceptanceAirportCode = acceptanceAirportCode;
	}
	@Column(name="ACPSCNDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getAcceptanceScanDate() {
		return acceptanceScanDate;
	}
	public void setAcceptanceScanDate(Calendar acceptanceScanDate) {
		this.acceptanceScanDate = acceptanceScanDate;
	}
	@Column(name="ACPPOACONNUM")
	public String getAcceptancePostalContainerNumber() {
		return acceptancePostalContainerNumber;
	}
	public void setAcceptancePostalContainerNumber(String acceptancePostalContainerNumber) {
		this.acceptancePostalContainerNumber = acceptancePostalContainerNumber;
	}
	@Column(name="FSTSCNDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFirstScanDate() {
		return firstScanDate;
	}
	public void setFirstScanDate(Calendar firstScanDate) {
		this.firstScanDate = firstScanDate;
	}
	@Column(name="FSTSCNARP")
	public String getFirstScanPort() {
		return firstScanPort;
	}
	public void setFirstScanPort(String firstScanPort) {
		this.firstScanPort = firstScanPort;
	}
	@Column(name="SECSTACOD")
	public String getSecurityStatusCode() {
		return securityStatusCode;
	}
	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}
	/**
	 * @author A-5991
	 * @param mailbagvo
	 * @throws SystemException
	 */
	public void update(MailbagVO mailbagvo)throws SystemException {
		log.entering("Mailbag","updateIrpFlag");
		//setIrregularityflag(MailConstantsVO.FLAG_NO);
		setLastUpdateTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		setLastUpdateUser(mailbagvo.getScannedUser());
		log.exiting("Mailbag","updateIrpFlag");
	}


	/**
	 * @author A-5991
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		if(this.getDamagedMailbags()!=null){
			for(DamagedMailbag damaged : this.getDamagedMailbags()){
				damaged.remove();
			}
		}
		
		Collection<MailbagHistoryVO>  mailbagHistoryVOs =findMailbagHistories(this.mailbagPK.getCompanyCode(),"",this.mailbagPK.getMailSequenceNumber());
		if (mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {
			for (MailbagHistoryVO mailbagHistory : mailbagHistoryVOs) {
				MailbagHistoryPK mailbaghistorypk =MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
				MailbagHistory mailhistory = null;
				 try {
                     mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
                     mailhistory.remove();
                 } catch (FinderException e) {
					 log.log(Log.SEVERE, "Finder Exception Caught");
				}
			}
		}
		
//		if(this.getMailbagHistories()!=null){IASCB-46569
//			for(MailbagHistory history : this.getMailbagHistories()){
//				history.remove();
//			}
//		}

		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
}


	 public Mailbag() {
	    }

	    public Mailbag(MailbagVO mailbagVO) throws SystemException {
	    	
	    	populateMailbag(mailbagVO);
	          try {
	              PersistenceController.getEntityManager().persist(this);
	          } catch(CreateException createException) {
	        	  log.log(Log.SEVERE,"Mailbag - SystemException"+createException);
	              throw new SystemException(createException.getMessage(),
	                      createException);
	          }
	          populateChildren(mailbagVO);
	    }
	    
	    /**
	     * @author A-9619 for resolving sonarlint issue Constructors should only call non-overridable methods (squid:S1699)
	     * @param mailbagVO
	     * @throws SystemException
	     */
	    private void populateMailbag(MailbagVO mailbagVO) throws SystemException {
	    	  populatePk(mailbagVO);
	          populateAttributes(mailbagVO);
	    }
	    
	    /*A-9619 as part of IASCB-55196*/
	    public Mailbag persistMailbag(MailbagVO mailbagVO) throws SystemException {
	    	  populatePk(mailbagVO);
	    	  populateAttributes(mailbagVO);/*A-9619 as part of IASCB-55196*/
	          try {
	              PersistenceController.getEntityManager().persist(this);
	          } catch(CreateException createException) {
	              throw new SystemException(createException.getMessage(),
	                      createException);
	          }
	          populateChildren(mailbagVO);
	          return this;
	    }



		public void updateULDTransferDetails(
				OperationalFlightVO operationalFlightVO, int toFlightSegSerNum)
			throws SystemException {
			log.entering("Mailbag", "updateULDTransferDetails");
			LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
			String currScannedPort = getScannedPort();
			setScannedPort(operationalFlightVO.getPol());
			setScannedUser(operationalFlightVO.getOperator());
			if(operationalFlightVO.getOperationTime()!=null){
			   setScannedDate(
	        		 new LocalDate(getScannedPort(), Location.ARP, operationalFlightVO.getOperationTime().toCalendar(),true));
			}else{
			   setScannedDate(new LocalDate(getScannedPort(),Location.ARP,true));
			}
			//check if already arrived else create history for arrival befor acceptance hsitroy
			boolean[] eventStats = checkIfHistoryExists(null,
				MailConstantsVO.MAIL_STATUS_ARRIVED,
				MailConstantsVO.MAIL_STATUS_DELIVERED);
			boolean isArrived = eventStats[0];
			boolean isDelivered = eventStats[1];
			if(!isArrived && !isDelivered) {
	            /*MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(null);
	            mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);*/

	            /*MailbagVO mailbagVO = constructMailbagVO();
	            //find prev flight details
	            mailbagVO =
	            	MailbagHistory.findLatestFlightDetailsOfMailbag(mailbagVO);
	            mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
	            mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
	            //Added for ICRD-156218 starts
	            if(operationalFlightVO.isScanned()){
	            mailbagHistoryVO.setMailSource(MailConstantsVO.SCAN);
	            }else{
	            mailbagHistoryVO.setMailSource("MTK008");
	            }
	            //Added for ICRD-156218 ends
	            insertHistoryDetails(mailbagHistoryVO);*/
			}

			if(!isDelivered) {
				if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(getLatestStatus()) && operationalFlightVO.getPol().equals(currScannedPort)){
					setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
					setScannedUser(getLastUpdateUser());
					setMailbagSource(getLatestStatus());
				}
				else{
					if(operationalFlightVO.isTransferStatus())
					{
				        setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);     
					}
					else
					{
		        setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);     
					}
		        String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT"); 
		        if("MTK064".equals(triggeringPoint)) {
		        	setMailbagSource(triggeringPoint);
		        } else {
		        setMailbagSource(getLatestStatus());
		        }
		        //ICRD-341173 
				/*if (operationalFlightVO.getCarrierCode() != null
						&& !"".equals(operationalFlightVO.getCarrierCode().trim())
						&& !logonAttributes.getOwnAirlineCode().equals(operationalFlightVO.getCarrierCode())) {
					setOnTimeDelivery(MailConstantsVO.FLAG_YES);
				}*/
		        MailbagVO newMailbagVO = new MailbagVO();
		        newMailbagVO.setCompanyCode(getMailbagPK().getCompanyCode());
		        newMailbagVO.setPaCode(getPaCode());
				if(new MailController().isUSPSMailbag(newMailbagVO)){
					setOnTimeDelivery(MailConstantsVO.FLAG_NO);
				}else{
					setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
				}
				}

		        setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				setCarrierId(operationalFlightVO.getCarrierId());
		        setFlightNumber(operationalFlightVO.getFlightNumber());
		        setFlightSequenceNumber(
		        		operationalFlightVO.getFlightSequenceNumber());
		        setSegmentSerialNumber(toFlightSegSerNum);
		        setPou(operationalFlightVO.getPou());
		       //Added as part of ASCB-42188
		        String routingDetails = null;
		        MailbagVO mailbagVO = new MailbagVO();
		        if(getConsignmentNumber()!=null){
		        	mailbagVO.setCompanyCode(getMailbagPK().getCompanyCode());
			        mailbagVO.setPaCode(getPaCode());
			        mailbagVO.setConsignmentNumber(getConsignmentNumber());
			        mailbagVO.setDestination(getDestination());
					routingDetails = constructDAO().findRoutingDetailsForConsignment(mailbagVO);
		        }
			if (routingDetails != null && getReqDeliveryTime() != null) {
				setReqDeliveryTime(getReqDeliveryTime());
			} else {
				LocalDate reqDlvTim=null;				
				try {
					MailbagVO mailbagTransferVO = new MailbagVO();
					mailbagTransferVO.setDestination(getDestination());
					mailbagTransferVO.setPaCode(getPaCode());
					mailbagTransferVO.setCompanyCode(operationalFlightVO.getCompanyCode());
					mailbagTransferVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					mailbagTransferVO.setFlightDate(operationalFlightVO.getFlightDate());
					mailbagTransferVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					reqDlvTim = calculateRDT(mailbagTransferVO);
				} catch (SystemException e) {
					e.getMessage();
				}
			
				setReqDeliveryTime(reqDlvTim);
			}
		       


		        //insert history for outbound
		       /* MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(null);
		        mailbagHistoryVO.setFlightDate(operationalFlightVO.getFlightDate());
		        mailbagHistoryVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		      //temporary code for identify the root cause of bug ICRD-160796  starts
		        if(getSegmentSerialNumber()==0 && getFlightNumber()!=null && !"-1".equals(getFlightNumber())){
		        	String mailSource=mailbagHistoryVO.getMailSource();
		        	if(mailSource!=null && !mailSource.isEmpty()){
		        		mailSource=mailSource.concat("UUTD");
		        	}
		        	  mailbagHistoryVO.setMailSource(mailSource);
		        }
		    	//temporary code for bug ICRD-160796 by A-5526 as issue is not getting replicated ends

		        insertHistoryDetails(mailbagHistoryVO);*/
			}
	        log.entering("Mailbag", "updateULDTransferDetails");
		}



		/**
		 * TODO Purpose
		 * Oct 11, 2006, a-1739
		 * @return
		 */
		/*private MailbagVO constructMailbagVO() {
			MailbagVO mailbagVO  = new MailbagVO();
			mailbagPK = getMailbagPK();
			mailbagVO.setCompanyCode(mailbagPK.getCompanyCode());
			mailbagVO.setMailSequenceNumber(mailbagPK.getMailSequenceNumber());
			mailbagVO.setMailbagId(getMailIdr());
			mailbagVO.setDespatchSerialNumber(getDespatchSerialNumber());
			mailbagVO.setOoe(getOrginOfficeOfExchange());
			mailbagVO.setDoe(getDestinationOfficeOfExchange());
			mailbagVO.setMailClass(getMailClass());
			mailbagVO.setMailSubclass(getMailSubClass());
			mailbagVO.setMailCategoryCode(getMailCategory());
			mailbagVO.setYear(getYear());
			return mailbagVO;
		}*/


	    /**
	     * This method checks whether History entry exists or not.
	     * If exists for Arrived/Delivered status updates it's Scan date.
	     * @param mailbagVO
	     * @param isAlreadyArrived
	     * @param isDelivered
	     * @return
	     * @throws SystemException 
	     */
    private boolean[] checkIfHistoryExists(MailbagVO mailbagVO, String... events) throws SystemException{
    	//Collection<MailbagHistory> existingMailbagHistories = getMailbagHistories();//IASCB-46569
    	Collection<MailbagHistoryVO> existingMailbagHistories=findMailbagHistories(getMailbagPK().getCompanyCode(),"",getMailbagPK().getMailSequenceNumber());
    	
    	boolean[] evtStats = new boolean[events.length];
    	int idx = 0;
    	String airport = getScannedPort();
    	if(mailbagVO != null) {
    		airport = mailbagVO.getScannedPort();
    	}
    	if(existingMailbagHistories != null && existingMailbagHistories.size() >0){
    		for(MailbagHistoryVO mailbagHistory:existingMailbagHistories){
    			for(String mailEvent : events) {
	    			if(mailbagHistory.getScannedPort().equals(airport) &&
	    					mailbagHistory.getMailStatus().
								equals(mailEvent)){
	    					 evtStats[idx++] = true;
	    					 break;
	    				}
	    			}
    			}
    		}
    	return evtStats;
    	}


	    /**
	     *
	     * @param mailbagVO
	     * @throws SystemException
	     */
	    private void populateChildren(MailbagVO mailbagVO) throws SystemException {
	      /* Collection<MailbagHistoryVO> mailbagHistoryVOs =
	           mailbagVO.getMailbagHistories();
	       ppulateHistoryDetails(mailbagHistoryVOs); */   


	       Collection<DamagedMailbagVO> damageVOs =
	           mailbagVO.getDamagedMailbags();
	       //Added for icrd-129225
	       //For Found mailbags history stamping and damage population will be handled in UpdateArrivaldetails Method
	       //Modified as part of bug ICRD-129225 by A-5526
	       if(!MailConstantsVO.FLAG_NO.equals(mailbagVO.getAcceptanceFlag())){
	       if(damageVOs != null && damageVOs.size() > 0) {
	           populateDamageDetails(damageVOs);

	          /* MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(mailbagVO);
	           mailHistoryVO.setMailStatus(
	                   MailConstantsVO.MAIL_STATUS_DAMAGED);
	           insertHistoryDetails(mailHistoryVO);*/      

		       }
	       }


	    }


	    /**
	     *	A-1739
	     * @param mailbagHistoryVOs
	     * @throws SystemException
	     */
	  /*  public void populateHistoryDetails(Collection<MailbagHistoryVO> mailbagHistoryVOs)//commented for IASCB-46569
	    throws SystemException {
	        if(mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {
	            if(getMailbagHistories() == null) {
	                mailbagHistories = new HashSet<MailbagHistory>();
	            }
	            for(MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
	                mailbagHistories.add(new MailbagHistory(
	                        getMailbagPK(), mailbagHistoryVO));
	            }
	        }

	    }*/


		/**
		 *
		 * 	Method		:	Mailbag.constructDamagedMailbagPK
		 *	Added by 	:	A-4803 on 21-May-2015
		 * 	Used for 	:	constructing damage mail bag pk
		 *	Parameters	:	@param damageMailbagVO
		 *	Parameters	:	@return
		 *	Return type	: 	DamagedMailbagPK
		 */
		private DamagedMailbagPK constructDamagedMailbagPK(DamagedMailbagVO damageMailbagVO) {
			DamagedMailbagPK damageMailbagPK = new DamagedMailbagPK();
			damageMailbagPK.setCompanyCode(mailbagPK.getCompanyCode());
			damageMailbagPK.setMailSequenceNumber(mailbagPK.getMailSequenceNumber());
			damageMailbagPK.setAirportCode(damageMailbagVO.getAirportCode());
			damageMailbagPK.setDamageCode(damageMailbagVO.getDamageCode());
			return damageMailbagPK;
		}

	    /**
	     *	A-1739
	     * @param damageVOs
	     * @throws SystemException
	     */
	    private void populateDamageDetails(Collection<DamagedMailbagVO> damageVOs)
	    throws SystemException {
	        if(getDamagedMailbags() == null) {
	            damagedMailbags = new HashSet<DamagedMailbag>();
	        }
	        mailbagPK = getMailbagPK();
	        for(DamagedMailbagVO damagedMailbagVO : damageVOs) {
	          damagedMailbagVO.setMailClass(getMailClass());

				try {
					DamagedMailbag.find(constructDamagedMailbagPK(damagedMailbagVO));
					log.log(Log.INFO, "Damage details already saved");
				} catch (FinderException exception) {
					damagedMailbags.add(new DamagedMailbag(mailbagPK, damagedMailbagVO));
				}
			}
		}
	    private static Log staticLogger(){
	    	return LogFactory.getLogger(MODULE_NAME);
	    }
	    /**
	     *	A-5991
	     * @param dsnPK
	     * @param mailbagVO
	     */
	    private void populatePk(MailbagVO mailbagVO) throws SystemException {
	        mailbagPK = new MailbagPK();
	        mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());

	    }
	    /**
	     *	A-5991
	     * @param mailbagVO
	     */
	    public void populateAttributes(MailbagVO mailbagVO) {
	        setCarrierId(mailbagVO.getCarrierId());
	        if(mailbagVO.getConsignmentDate()!=null){
	        setDespatchDate(mailbagVO.getConsignmentDate().toCalendar());//added by A-8353 for ICRD-2304499
	        }
	        setFlightNumber(mailbagVO.getFlightNumber());
	        setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
	        setHighestNumberedReceptacle(mailbagVO.getHighestNumberedReceptacle());
	        setLatestStatus(mailbagVO.getLatestStatus());
	         setMailCompanyCode(mailbagVO.getMailCompanyCode());
	        setPou(mailbagVO.getPou());
	        setReceptacleSerialNumber(mailbagVO.getReceptacleSerialNumber());
	        setRegisteredOrInsuredIndicator(mailbagVO.getRegisteredOrInsuredIndicator());
	        if(mailbagVO.getScannedDate()!= null){
	        setScannedDate(mailbagVO.getScannedDate().toCalendar());
	        }
	        setScannedPort(mailbagVO.getScannedPort());
	        setScannedUser(mailbagVO.getScannedUser());
	        setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
	        setMailIdr(mailbagVO.getMailbagId());
	        setDsnIdr(mailbagVO.getDespatchId());
	       setUldNumber(mailbagVO.getUldNumber());
	        setContainerType(mailbagVO.getContainerType());
	        if(mailbagVO.getWeight() != null){
	        setWeight(mailbagVO.getWeight().getSystemValue());//added by A-7371
	        }
	        setOperationalStatus(mailbagVO.getOperationalStatus());
	        setConsignmentNumber(mailbagVO.getConsignmentNumber());
	        setConsignmentSequenceNumber(mailbagVO.getConsignmentSequenceNumber());
	        setPaCode(mailbagVO.getPaCode());
	     
	        if(mailbagVO.getPaBuiltFlag()!=null&&mailbagVO.getPaBuiltFlag().trim().length()>0){
	        	setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
			}else{
				setPaBuiltFlag("N");
			}
	        if(mailbagVO.getDamageFlag()!=null){
	        setDamageFlag(mailbagVO.getDamageFlag());
	        }else{
	        	 setDamageFlag(MailbagVO.FLAG_NO);
	        }
	        setMailClass(mailbagVO.getMailClass());
	        setMailCategory(mailbagVO.getMailCategoryCode());
	        setMailSubClass(mailbagVO.getMailSubclass());
	        setOrginOfficeOfExchange(mailbagVO.getOoe());
	        setDestinationOfficeOfExchange(mailbagVO.getDoe());
	        setDespatchSerialNumber(mailbagVO.getDespatchSerialNumber());
	        setYear(mailbagVO.getYear());
            //Added as a part of ICRD-197419 
	        setMailRemarks(mailbagVO.getMailRemarks());
	        if(mailbagVO.isDespatch()){
	        	setMailType("D");
	        }else{
	        	setMailType("M");
	        }
	         //setMailCompanyCode(mailbagVO.getMailCompanyCode()) ;
	        if(mailbagVO.getDamageFlag()!=null && mailbagVO.getDamageFlag().equals(MailConstantsVO.FLAG_YES))
	        {
	        	setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
	        }
			if (mailbagVO.getFlightSequenceNumber() > 0
					&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus())) {
				setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			}
	        /*
	         * Added By Karthick as the part of the Optimistic Concurrency
	         *
	         */
	            setLastUpdateUser(mailbagVO.getScannedUser());
	         log.log(Log.FINE, "VOLUME", mailbagVO.getVolume());
        		  		 try {
        		 calculateMailbagVolume(mailbagVO);
				} catch (SystemException e) {
					log.log(Log.FINE,"System Exception",e);
				}        		 
	      if (mailbagVO.getVolume() != null) {
			setVolume(mailbagVO.getVolume().getDisplayValue());
		} 
	       //Added by A-7540 for IASCB-27218 starts
			if(mailbagVO.getReqDeliveryTime()!=null){
					setReqDeliveryTime(mailbagVO.getReqDeliveryTime().toCalendar());
				}
			else{
				LocalDate reqDlvTim=null;				
				try {
					reqDlvTim = calculateRDT(mailbagVO);
				} catch (SystemException e) {
					e.getMessage();
				}
			
				setReqDeliveryTime(reqDlvTim);
			}
			//Added by A-7540 for IASCB-27218 ends
			//Added by A-7540 for IASCB-27218 ends
			//added by A-7371 for ICRD-234919
			if(mailbagVO.getWeight() != null){
			setDisplayValue(mailbagVO.getWeight().getDisplayValue());   
			setDisplayUnit(mailbagVO.getWeight().getDisplayUnit());//added by A-8353 for ICRD ICRD-274933
			}
			//Modified by A-7540 starts
			if(mailbagVO.getMailServiceLevel() != null && !mailbagVO.getMailServiceLevel().isEmpty() && 
					getMailServiceLevel()==null){
				setMailServiceLevel(mailbagVO.getMailServiceLevel()); 
			}
			else{
			String serviceLevel = null;
			try {
				serviceLevel = new MailController().findMailServiceLevel(mailbagVO);
			} catch (SystemException e) {
				e.getMessage();
			}
			if(serviceLevel!=null && !serviceLevel.isEmpty()){
				mailbagVO.setMailServiceLevel(serviceLevel);
				setMailServiceLevel(serviceLevel);
			}
	    }
			//Modified by A-7540 ends
			setSelnum(mailbagVO.getSealNumber());//Added by A-8236 for ICRD-262947
			setOnTimeDelivery(MailConstantsVO.FLAG_NO);//Added for ICD-243421
			setOrigin(mailbagVO.getOrigin());
			setDestination(mailbagVO.getDestination());
			
			if(mailbagVO.getOnTimeDelivery()!=null){
			setOnTimeDelivery(mailbagVO.getOnTimeDelivery());	
			}else{
			setOnTimeDelivery("NA");//Modified by A-7540 for ICRD-323499
			}
			
			
			setOrigin(mailbagVO.getOrigin());
			setDestination(mailbagVO.getDestination());
			setMailbagSource(mailbagVO.getMailbagDataSource());
			setScanWavedFlag(mailbagVO.getScanningWavedFlag());//Added by A-7794 as part of ICRD-232299	  
			  if(mailbagVO.getVolume()!=null){  
            setDisplayVolUnit(mailbagVO.getVolume().getDisplayUnit());//added by A-8353 for  ICRD-274933
	       }
			//Added by A-7540
			if(mailbagVO.getRfdFlag() != null){
				setRfdFlag(mailbagVO.getRfdFlag());
			}
			else{
				setRfdFlag(MailConstantsVO.FLAG_NO);
			}
			setContractIDNumber(mailbagVO.getContractIDNumber());  //Added by A-7929 as part of IASCB-28260
			if(mailbagVO.getTransWindowEndTime()==null){
				String paCode_int =null;
				try {
					paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
				} catch (SystemException e) {
				}
				if (mailbagVO.getPaCode()!=null){
				if(mailbagVO.getPaCode().equals(paCode_int)){
			LocalDate transportServiceWindowEndTime = null;
			try {
				transportServiceWindowEndTime = new MailController().calculateTransportServiceWindowEndTime(mailbagVO);
			} catch (SystemException e) {
			}
			if(transportServiceWindowEndTime!=null)
			{
				setTransWindowEndTime(transportServiceWindowEndTime.toCalendar());
				}
				}}
			}else{
				setTransWindowEndTime(mailbagVO.getTransWindowEndTime());
			}
			setLatestAcceptanceTime(mailbagVO.getLatestAcceptanceTime()!=null?mailbagVO.getLatestAcceptanceTime().toCalendar():null);
			setAcceptanceAirportCode(mailbagVO.getAcceptanceAirportCode());
			setAcceptanceScanDate(mailbagVO.getAcceptanceScanDate()!=null?mailbagVO.getAcceptanceScanDate().toCalendar():null);
			setAcceptancePostalContainerNumber(mailbagVO.getAcceptancePostalContainerNumber());
			if(mailbagVO.getWeight() != null && mailbagVO.getWeight().getSystemValue()==0) {
			try {
			 Measure actualWeight=new MailController().calculateActualWeightForZeroWeightMailbags(mailbagVO);
			
			 if(actualWeight!=null) {
				 setActualWeight(actualWeight.getSystemValue());
				 setActualWeightDisplayValue(actualWeight.getDisplayValue());
				 setActualWeightDisplayUnit(actualWeight.getDisplayUnit());
			 }
			}
			 catch (SystemException e) {
				}
	     }
			if(!MailConstantsVO.MAIL_STATUS_NEW.equals(mailbagVO.getLatestStatus())){
				if(mailbagVO.getScannedDate() != null){
			setFirstScanDate(mailbagVO.getScannedDate().toCalendar());
				}
	        setFirstScanPort(mailbagVO.getScannedPort());
			}
			setSecurityStatusCode(mailbagVO.getSecurityStatusCode());
	     }


	    /**
	     * @author A-5991
	     * This method returns the Instance of the DAO
	     * @return
	     * @throws SystemException
	     */
	    private static MailTrackingDefaultsDAO constructDAO()
	    throws SystemException {
	    	try {
	    		EntityManager em = PersistenceController.getEntityManager();
	    		return MailTrackingDefaultsDAO.class.cast(em.
	    				getQueryDAO(MODULE_NAME));
	    	}
	    	catch(PersistenceException persistenceException) {
	    		throw new SystemException(persistenceException.getErrorCode());
	    	}
	    }

	    /**
	     * @author A-5991
	     * @param MailIdr
	     * @param companyCode
	     * @return
	     * @throws SystemException
	     */
	    public static long findMailBagSequenceNumberFromMailIdr(String mailIdr,String companyCode) throws SystemException{
	    	return constructDAO().findMailSequenceNumber(mailIdr, companyCode);
	    }

	    /**
	     * @author A-10504
	     * @param mailBagId
	     * @param companyCode
	     * @return
	     * @throws SystemException
	     */
	    public static int findScreeningDetails(String mailBagId,String companyCode)
	    		throws SystemException{
	    	return constructDAO().findScreeningDetails(mailBagId, companyCode);
	    }

	    /**
	     * @author A-1739
	     * @param mailbagVO
	     * @throws SystemException
	     */
		public void updateAcceptanceFlightDetails(MailbagVO mailbagVO) throws SystemException {
			log.entering("Mailbag", "updateAcceptanceFlightDetails");
			 if(mailbagVO.isFromDeviationList()){
				// mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				 return;
 		}
			 LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
			if(mailbagVO.isMailUpdateFlag()){
			setScannedDate(mailbagVO.getScannedDate().toCalendar());
			setCarrierId(mailbagVO.getCarrierId());
			setFlightNumber(mailbagVO.getFlightNumber());
			setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			setPou(mailbagVO.getPou());
			setUldNumber(mailbagVO.getContainerNumber());
			setContainerType(mailbagVO.getContainerType());
			setOperationalStatus(mailbagVO.getOperationalStatus());
			setScannedPort(mailbagVO.getScannedPort());
			setLastUpdateTime(
					mailbagVO.getLastUpdateTime());
			setLastUpdateUser(logonAttributes.getUserId());//Added by A-8527 for IASCB-52472
			setScannedDate(mailbagVO.getScannedDate());
       				 calculateMailbagVolume(mailbagVO);
			if(mailbagVO.getVolume()!=null) {
			setVolume(mailbagVO.getVolume()
					.getDisplayValue());
			}
			//Added by A-7540 as a part of ICRD-197419
			setMailRemarks(mailbagVO.getMailRemarks());
			 //Added by A-8527 for ICRD-346864 starts
			if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())){
			setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
			}
			//Added by A-5945 for ICRD-119569 starts
			setPaCode(mailbagVO.getPaCode());
			if(mailbagVO.getMailbagSource()!=null){//IASCB-41447
				setMailbagSource(mailbagVO.getMailSource());
			}
			if(getFirstScanDate()==null&&getFirstScanPort()==null){
			setFirstScanDate(mailbagVO.getScannedDate());	
			setFirstScanPort(mailbagVO.getScannedPort());
			}
			
			
				if(mailbagVO.getMailCompanyCode()!=null && !mailbagVO.getMailCompanyCode().equals(getMailCompanyCode())&&
						mailbagVO.getMailCompanyCode().trim().length()>0){
					MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
					mailbagAuditVO = (MailbagAuditVO)AuditUtils.populateAuditDetails(mailbagAuditVO,this,false);
	       setMailCompanyCode(mailbagVO.getMailCompanyCode());
					mailbagAuditVO = (MailbagAuditVO)AuditUtils.populateAuditDetails(mailbagAuditVO,this,false);
					mailbagAuditVO.setActionCode("UPDATEMAL");
					mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
					mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
					mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
					mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
					mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
					mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
					mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
					mailbagAuditVO.setYear(mailbagVO.getYear());
					
					mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
					StringBuffer additionalInfo = new StringBuffer();
					additionalInfo.append("Company code ").append("updated for mailbag	").append(mailbagVO.getMailbagId());
					mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
					AuditUtils.performAudit(mailbagAuditVO);
				}
			else// Added by A-5945 for ICRD-119569 ends
	       {
	       setMailCompanyCode(mailbagVO.getMailCompanyCode());
	       }
			if (OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
				Collection<MailbagHistoryVO> mailbagHistoryVOs = mailbagVO.getMailbagHistories();

			/*	if (mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {
					populateHistoryDetails(mailbagHistoryVOs);
				} else {
					insertHistoryDetails(constructMailHistoryVO(mailbagVO));
				}*/
					if(mailbagVO.getFlightSequenceNumber()!=MailConstantsVO.DESTN_FLT && "CARDIT".equals(mailbagVO.getFromPanel())){
						setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
					}
					else if (getLatestStatus() != null) {
					if (getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)) {
						mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
						setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
					} else if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(getLatestStatus()) ||
							getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED) ||
							getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)) {
						mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
					}else if(MailConstantsVO.MAIL_STATUS_NEW.equals(getLatestStatus()) || MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(getLatestStatus()) || MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(getLatestStatus())){
						if(mailbagVO.getFlightSequenceNumber() != DESTN_FLT && MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus())) {
							setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						} else {
						setLatestStatus(mailbagVO.getLatestStatus());
						}
					}
					//insertHistoryDetails(constructMailHistoryVO(mailbagVO));
				} else {
					setLatestStatus(mailbagVO.getLatestStatus());
				}
			
				setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			} else if (OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
				MailbagHistory mailbagHistory = findMailbagHistoryForEvent(mailbagVO);
				//Uncommented for ICRD-140584
				if (mailbagHistory != null) {
					mailbagHistory.setScanDate(mailbagVO.getScannedDate());
				}
				updateAxpResditEventTimes(mailbagVO);
			}
			}
			log.exiting("Mailbag", "updateAcceptanceFlightDetails");
		}



	    /**
	     *	A-1739
	     * @param mailbagVO
	     * @param damagedMailbagVO
	     * @return
	     */
	    public MailbagHistoryVO constructMailHistoryVO(MailbagVO mailbagVO) {
	    	MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
	    	mailbagHistoryVO.setMailStatus(getLatestStatus());
	    	mailbagHistoryVO.setScannedPort(getScannedPort());
	    	mailbagHistoryVO.setScanUser(getScannedUser());

	    	//mailbagHistoryVO.setScanDate(
	    			//new LocalDate(getScannedPort(), Location.ARP, getScannedDate(), true));
	    	mailbagHistoryVO.setScanDate(
	                 new LocalDate(mailbagVO.getScannedPort(), Location.ARP, true));
	    	mailbagHistoryVO.setCarrierId(getCarrierId());
	    	mailbagHistoryVO.setFlightNumber(getFlightNumber());
	    	mailbagHistoryVO.setFlightSequenceNumber(getFlightSequenceNumber());
	    	//Added by A-5945 for ICRD-96316  starts
	    	if(mailbagVO!=null && MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getLatestStatus())&& mailbagVO.getOperationalFlag()!=null &&(MailConstantsVO.OPERATION_FLAG_INSERT).equals(mailbagVO.getOperationalFlag() )){
	    		mailbagHistoryVO.setContainerNumber(mailbagVO.getContainerNumber());
	    	}else { //Added by A-5945 for ICRD-96316  ends
	    		mailbagHistoryVO.setContainerNumber(getUldNumber());
	    	}
	    	mailbagHistoryVO.setSegmentSerialNumber(getSegmentSerialNumber());
	    	if(mailbagVO != null) {
	    		 if((mailbagVO.getScannedDate()!=null)){
	    			mailbagHistoryVO.setScanDate(
	     			new LocalDate(getScannedPort(), Location.ARP, mailbagVO.getScannedDate(), true));
	    		 }
	    		mailbagHistoryVO.setScanUser(mailbagVO.getScannedUser());
	    		mailbagHistoryVO.setMailSource(mailbagVO.getMailSource());//Added for ICRD-156218
	    		mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
	    		mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
	    	}

	    	mailbagHistoryVO.setContainerType(getContainerType());
	    	mailbagHistoryVO.setPou(getPou());
	    	//temporary code for identify the root cause of bug ICRD-160796  starts
	     if( MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(getLatestStatus())){

	         if(getSegmentSerialNumber()==0 && getFlightNumber()!=null && !"-1".equals(getFlightNumber())){
	         	String mailSource=mailbagHistoryVO.getMailSource();
	         	if(mailSource!=null && !mailSource.isEmpty()){
	         		mailSource=mailSource.concat("CMH");
	         	}
	         	  mailbagHistoryVO.setMailSource(mailSource);
	         	 mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
	         }

	     }
	   //temporary code for bug ICRD-160796 by A-5526 as issue is not getting replicated ends

	    	if(getFlightSequenceNumber() > 0 && mailbagVO != null) {
	    		mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
	    		mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
	    		mailbagHistoryVO.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
	    		log.log(Log.FINE, "##########mailbagVO.getPaBuiltFlag()####",
						mailbagVO.getPaBuiltFlag());
	    	}
	    	mailbagHistoryVO.setMailClass(getMailClass());
	    	return mailbagHistoryVO;
	    }


		/**
	     * @author A-1739
	     * @param mailbagHistoryVO
	     * @throws SystemException
	     */
	    public void insertHistoryDetails(MailbagHistoryVO mailbagHistoryVO ) throws SystemException {
	    	
	    	String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT"); 
	    	
	    	if(mailbagHistoryVO.getMailSource()!=null && mailbagHistoryVO.getMailSource().startsWith(MailConstantsVO.SCAN+":")){
	    		mailbagHistoryVO.setMailSource(mailbagHistoryVO.getMailSource().replace(MailConstantsVO.SCAN+":", ""));
	    	}else{
	    		mailbagHistoryVO.setMailSource(triggeringPoint);
	    	}
	    	
	        /*
	         * Added By Karthick V
	         * In case of Reassign tO Flight Persist the Details in MailHistory also.
	         *
	         */
	            new MailbagHistory(getMailbagPK(),mailbagHistoryVO);
	       /* if(getMailbagHistories()==null){
	         setMailbagHistories(new HashSet<MailbagHistory>());
	         }
	        getMailbagHistories().add(mailbagHistory);*/

	    }


	    /**
		 * May 23, 2007, a-1739
		 * @param mailbagVO
		 * @return
	     * @throws SystemException 
		 */
		public MailbagHistory findMailbagHistoryForEvent(MailbagVO mailbagVO) throws SystemException {
			
			Collection<MailbagHistoryVO>  mailbagHistoryVOs =findMailbagHistories(mailbagVO.getCompanyCode(),mailbagVO.getMailbagId(),mailbagVO.getMailSequenceNumber());
			//mailbagHistories = getMailbagHistories();IASCB-46569

			if (mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {

				for (MailbagHistoryVO mailbagHistory : mailbagHistoryVOs) {

					if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(
							mailbagHistory.getMailStatus()) ||
							MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(
									mailbagHistory.getMailStatus()) ||
									MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(
											mailbagHistory.getMailStatus()) ||
											MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(
													mailbagHistory.getMailStatus()) ||
													MailConstantsVO.MAIL_STATUS_ARRIVED.equals(
													mailbagHistory.getMailStatus()) ||
													MailConstantsVO.MAIL_STATUS_DELIVERED.equals(
													mailbagHistory.getMailStatus())) {
					if(mailbagHistory.getFlightNumber()!=null &&mailbagHistory.getContainerNumber()!= null){
						if (mailbagHistory.getScannedPort().equals(mailbagVO.getScannedPort()) &&
								mailbagHistory.getCarrierId() == mailbagVO.getCarrierId() &&
								mailbagHistory.getFlightNumber().equals(mailbagVO.getFlightNumber()) &&
								mailbagHistory.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber() &&
								mailbagHistory.getSegmentSerialNumber() == mailbagVO.getSegmentSerialNumber() &&
								mailbagHistory.getContainerNumber().equals(mailbagVO.getContainerNumber())) {
							
							MailbagHistoryPK mailbaghistorypk =MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
							MailbagHistory mailhistory = null;
							 try {
                                 mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
                             } catch (FinderException e) {
								 log.log(Log.SEVERE, "Finder Exception Caught");
							}
							return mailhistory;
						}
						}
					}
				}
			}
			return null;
		}



		/**
		 * May 23, 2007, a-1739
		 * @param mailbagVO
		 * @throws SystemException
		 */
		private void updateAxpResditEventTimes(MailbagVO mailbagVO)
		throws SystemException {
			Collection<MailbagVO> mail = new ArrayList<MailbagVO>();
			String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
			if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
			log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
			new ResditController().updateResditEventTimes(mailbagVO,
					MailConstantsVO.TXN_ACP);
		}
		}


		private static String findSystemParameterValue(String syspar)
				throws SystemException {
					String sysparValue = null;
					ArrayList<String> systemParameters = new ArrayList<String>();
					systemParameters.add(syspar);
					HashMap<String, String> systemParameterMap =  Proxy.getInstance().get(SharedDefaultsProxy.class)
							.findSystemParameterByCodes(systemParameters);
					//log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
					if (systemParameterMap != null) {
						sysparValue = systemParameterMap.get(syspar);
					}
					return sysparValue;
				}


		  /**
	     *	A-1739
	     * @param mailbagVO
	     * @throws SystemException
	     */
	    public void updateAcceptanceDamage(MailbagVO mailbagVO)
	    throws SystemException {
	    	log.entering("Mailbag", "updateAcceptanceDamage");
	    	if(!MailbagVO.FLAG_YES.equals(this.getDamageFlag())){
	    	if(mailbagVO.getDamageFlag()!=null){
	        setDamageFlag(mailbagVO.getDamageFlag());
	    	}

	        //added check for icrd-85496 by a-4810.
	        if(mailbagVO.getDamageFlag()!=null && (MailConstantsVO.FLAG_YES).equals(mailbagVO.getDamageFlag())){
			 setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
	        }
	    	}
	        setScannedUser(mailbagVO.getScannedUser());
	        Collection<DamagedMailbagVO> damageMails =
	            mailbagVO.getDamagedMailbags();
				/*if(mailbagVO.getDamageFlag().equals(MailConstantsVO.FLAG_YES))
				{
				 MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagVO);
				 insertHistoryDetails(mailbagHistoryVO);
				}*/

	        if(damageMails != null && damageMails.size() > 0) {
	            updateMailbagDamage(mailbagVO);
	        }
	        log.exiting("Mailbag", "updateAcceptanceDamage");
	    }


	    /**
	     * @author A-5991
	     * @param mailbagVO
	     * @throws SystemException
	     */
	    private void updateMailbagDamage(MailbagVO mailbagVO)
	            throws SystemException {

	        Collection<DamagedMailbagVO> damagedMailbagVOs =
	            mailbagVO.getDamagedMailbags();
	        
	        if(getDamagedMailbags() == null) {
	            damagedMailbags = new HashSet<DamagedMailbag>();
	        }
	        for(DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
	        	log.log(Log.INFO, "DAMAGE MAIL-updateMailbagDamage",
						damagedMailbagVO);
				DamagedMailbag dmgMail =
	                findDamagedMailbagInSet(damagedMailbagVO);
	        	 damagedMailbagVO.setMailClass(getMailClass());

	            if(dmgMail == null) {
	            	// Added as part of Bug ICRD-129209 starts
	            	setDamageFlag(MailConstantsVO.FLAG_YES);
	                setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
	                setScannedUser(mailbagVO.getScannedUser());
	             // Added as part of Bug ICRD-129209 ends
	                damagedMailbags.add(
	                        new DamagedMailbag(mailbagPK,damagedMailbagVO));

	               /* MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(mailbagVO);
	                mailHistoryVO.setMailStatus(
	                        MailConstantsVO.MAIL_STATUS_DAMAGED);
	                if((damagedMailbagVO.getPaCode()!=null && damagedMailbagVO.getPaCode().trim().length()>0 ) && (mailbagVO.getPaCode()!=null && mailbagVO.getPaCode().trim().length()>0))
	                {
	                	mailHistoryVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
	                }*/

	                /*
	                 * Added By Karthick V  as the part of the Air New Zealand Mail Tracking Bugh Fix
	                 * Actually a Kind of Work around to set the Carrier Code
	                 * Better to set the Carrier Code here as the actual ConstructMailHistoryVO() method doesnt require any changes ..
	                 * mailHistoryVO.getCarrierCode() will be null only in case of damage captured from Carrier else ConstructMailHistoryVO()
	                 * already sets that carrierCode for flight
	                 */
	                /* if(mailHistoryVO.getCarrierCode()==null){
	                	  log.log(Log.FINE, "setting the Carrier Code", mailbagVO.getCarrierCode());
						mailHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
	                 }


	                insertHistoryDetails(mailHistoryVO);*/

	            } else {
	                dmgMail.update(damagedMailbagVO);
	            }
	        }

	    }


	    /**
	     *	A-1739
	     * @param damagedMailbags2
	     * @param damagedMailbagVO
	     * @return
	     */
	    public DamagedMailbag findDamagedMailbagInSet(
	            DamagedMailbagVO damagedMailbagVO) {
	        for(DamagedMailbag damagedMailbag : damagedMailbags) {
	            if(damagedMailbag.getDamagedMailbagPK().getAirportCode().
	                    equals(damagedMailbagVO.getAirportCode()) &&
	                    damagedMailbag.getDamagedMailbagPK().getDamageCode().
	                    equals(damagedMailbagVO.getDamageCode())) {
	                return damagedMailbag;
	            }
	        }
	        return null;
	    }


	    /**
	        * @author A-5991
	        * @param mailBagId
	        * @param companyCode
	        * @return
	        * @throws SystemException
	        */
	       public static long findMailSequenceNumber(String mailBagId,String companyCode)
	    		   throws SystemException{

	    	   return constructDAO().findMailSequenceNumber(mailBagId, companyCode);

	       }

	       /**
	   	 * @author A-2553
	   	 * @param companyCode
	   	 * @param mailbagId
	   	 * @param opFltVO
	   	 * @return
	   	 * @throws SystemException
	   	 */
	   	public static MailbagVO findExistingMailbags(String companyCode,long mailbagId)
	   		throws SystemException{
	   		try{
	   			return constructDAO().findExistingMailbags(companyCode,mailbagId);
	   		}
	   		catch(PersistenceException persistenceException){
	   			throw new SystemException(persistenceException.getErrorCode());
	   		}
	   	}

	   	/**
	     * This method is used to validate the MailTagFormat
	     * @param mailbagVos
	     * @param isUploadHHT will not throw exp if true
	     * @return
	     * @throws SystemException
	     * @throws InvalidMailTagFormatException
	     */
	     public  boolean  validateMailBags(Collection<MailbagVO> mailbagVos,
	    		 boolean isUploadHHT)
	          throws SystemException,InvalidMailTagFormatException {
	    	  HashMap<String,Collection<String>> hashMap=null;
	    	  Map<String,Map<String,CityVO>> cityMaps=
	    		  new HashMap<String,Map<String,CityVO>>();
	    	  String originOfficeExchange=null;
	    	  String destinationOfficeExchange=null;
	    	  String mailSubClass=null;
	    	  log.entering("MailBag","ValidateMailBag");
	    	 if(mailbagVos!=null && mailbagVos.size()>0){
	    	   for(MailbagVO mailbagVo:mailbagVos){
	    		    log.log(Log.FINE, "The Operational Flag From MailBagVo",
							mailbagVo.getOperationalFlag());
					if(MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVo.getOperationalFlag())){
	    		      if(hashMap==null){
						  log.log(Log.INFO,"Will be entered only once");
						  hashMap=new HashMap<String,Collection<String>>();
						  hashMap.put(COUNTRY_CACHE,new ArrayList<String>());
						  hashMap.put(CITY_CACHE,new ArrayList<String>());
						  hashMap.put(EXCHANGE_CACHE,new ArrayList<String>());
						  hashMap.put(ERROR_CACHE,new ArrayList<String>());
						  hashMap.put(SUBCLS_CACHE,new ArrayList<String>());
						  hashMap.put(CITYPAIR_CACHE,new ArrayList<String>());
					   }
	    		      /*
	    		       * Added By  Karthick V..
	    		       * Assuming  after the first iteration if the MailBag Id is invalid and
	    		       * if some errors are collected in the ERROR_CACHE
	    		       * these has to be removed when we are about to commence the next Itertaion which means
	    		       * the  Previous  Iterations Error Values has to be removed from the CACHE
	    		       * which has no signifiacnce with the subsequent Iterations as the Errors collected in
	    		       *  these CACHE corresponds to the a Single MailBag .so Once the Errors are collected
	    		       * for a MailBag the Cache has to be removed Before this reference is being passed to the
	    		       * Second MailBag  during the Iteration...
	    		       * Note this has been done Only for the Scanned MailBags where as in Case of  the
	    		       * MailBags being  not Scanned,Each  MailBag from the Iteration will throw an
	    		       * Exception to the client stating the Error Description ..
	    		       *
	    		       * Where as in  case of the Scanned Mail Bags if at all any errors are there with '
	    		       * the Validation of a MailBag that Particular MailBag will be returned to the Client with
	    		       * the Error Description ....Only ValidMailBags will be considered and the all the other
	    		       * invalid MailBags will be returned to the client with the corresponding error associated with it
	    		       *
	    		       */
	    		      if(isUploadHHT && hashMap.get(ERROR_CACHE)!=null
	    		    		   && hashMap.get(ERROR_CACHE).size()>0){
	    		    	   hashMap.put(ERROR_CACHE, new ArrayList<String>());
	    		       }


	    	           originOfficeExchange=mailbagVo.getOoe();
	   	    	       destinationOfficeExchange=mailbagVo.getDoe();
	   	    	       mailSubClass=mailbagVo.getMailSubclass();
	   	               if(originOfficeExchange!=null &&
	   	            		   !hashMap.get(EXCHANGE_CACHE).contains(originOfficeExchange)){

			             log.log(Log.INFO,"A valid Origin OE is not there in Map");
			             validateOfficeExchange(mailbagVo.getCompanyCode()
			             ,originOfficeExchange,hashMap,cityMaps,true);
		               }
	   	            if(!(hashMap.get(ERROR_CACHE).size()==0)){
	   	         	errorString.append(" The mailbag id : ");
		        	errorString.append(mailbagVo.getMailbagId());
		        	errorString.append(" : ");
		        	errorString.append(mailbagVo.getActionMode());
		        	String finalerrorString = errorString.toString();

					errPgExceptionLogger.info(finalerrorString);
	   	            }
	 		           if(destinationOfficeExchange!=null && !hashMap.get(EXCHANGE_CACHE).contains(destinationOfficeExchange)){

		    	    	   log.log(Log.INFO,"A valid Destination OE is not there in Map");
		    	    		validateOfficeExchange(mailbagVo.getCompanyCode(),
		    	    				destinationOfficeExchange,hashMap,cityMaps,false);
		 		        }
	 		          if(!(hashMap.get(ERROR_CACHE).size()==0)){
	 		   	         	errorString.append(" The mailbag id : ");
	 			        	errorString.append(mailbagVo.getMailbagId());
	 			        	errorString.append(" : ");
	 			        	errorString.append(mailbagVo.getActionMode());
	 			        	String finalerrorString = errorString.toString();

	 						errPgExceptionLogger.info(finalerrorString);
	 		   	            }
	 		           if(mailSubClass!=null){
	    			       validateSubClass(mailbagVo.getCompanyCode(),mailSubClass,hashMap);
	    	             }

	    		        if(!(hashMap.get(ERROR_CACHE).size()==0)){

	    		        	errorString.append(" The mailbag id : ");
	    		        	errorString.append(mailbagVo.getMailbagId());
	    		        	errorString.append(" : ");
	    		        	errorString.append(mailbagVo.getActionMode());
	    		        	String finalerrorString = errorString.toString();

	    					errPgExceptionLogger.info(finalerrorString);
	    			          String str= createErrors(mailbagVo,hashMap.get(ERROR_CACHE));
	    			          if(isUploadHHT) {
	    			        	  /**
	    			        	   * Commented for Bug 77995
	    			        	   */
//	    			        	  mailbagVo.setErrorType(MailConstantsVO.EXCEPT_WARN);
	    			        	  mailbagVo.setErrorType(MailConstantsVO.EXCEPT_FATAL);
	    			        	  mailbagVo.setErrorDescription(str);
	    			          } else {
	    			        	  throw  new InvalidMailTagFormatException(InvalidMailTagFormatException
	    					    		  .INVALID_MAILFORMAT,new Object[]{str});
	    			          }
				        	}
	                   }
	    	         }
	    	       }
	    	return true;
	     }



	     /**
		  * @author A-1936
		  * This method is used to validateOfficeExchange
		  * @param companyCode
		  * @param officeExchange
		  * @param hashMap
		  * @param cityMaps
		  * @param isOrigin
		  * @throws SystemException
		  */
		public    void  validateOfficeExchange(String companyCode,
				String officeExchange,HashMap<String,Collection<String>>  hashMap,
				Map<String,Map<String,CityVO>> cityMaps,
				boolean isOrigin)throws SystemException{
				//THIS IS DONE A FIX FOR BUG 35546
				//NEED TO CHANGE : ELSE THIS WILL AFFECT PERFORMANCE
			    HashMap<String, String> cityOEMap = null;
			    errorString=new StringBuilder();
			    Collection<String> officeOfExchanges = new ArrayList<String>();
			    officeOfExchanges.add(officeExchange);
			    cityOEMap = new MailController().findCityForOfficeOfExchange(companyCode,officeOfExchanges);
			    String  country = officeExchange.substring(0,2);
			    String city= null;
			    if(cityOEMap !=null) {
			    	city = cityOEMap.get(officeExchange);
			    }
			    //END
			    Map<String,CityVO> cityMap=null;
			    boolean  isValidCountry=true;
			    OfficeOfExchangeVO officeOfExchangeVO = null;
			    boolean  isValidCity=true;
			    boolean isValidExchange=true;
			    boolean isValidCityCountryPair=true;
			    boolean isValidPoaCode = true;
			    if(!hashMap.get(EXCHANGE_CACHE).contains(officeExchange) &&
			    		officeExchange.trim().length()==6){
			    	log.entering("MailBag","validateOfficeExchange");
			    	officeOfExchangeVO = new MailController().validateOfficeOfExchange(
			    			companyCode,officeExchange);
			    	if(officeOfExchangeVO != null) {
			    		isValidExchange = true;
			    		if(officeOfExchangeVO.getPoaCode() == null) {
			    			isValidPoaCode = false;
			    		}
			    	} else {
			    		isValidExchange = false;
			    	}
		          }
			      if(!isValidExchange) {
		     if (!hashMap.get(COUNTRY_CACHE).contains(country)) {
				isValidCountry = validateCountry(companyCode, country);
			}

			if (!hashMap.get(CITY_CACHE).contains(city)) {
				cityMap = validateCity(companyCode, city, cityMap);
				if (cityMap == null) {
					log.log(Log.INFO, "CITY MAP NULL");
					isValidCity = false;
				}
			} else if (officeExchange.trim().length() == 5) {
				cityMap = cityMaps.get(city);
				log.log(Log.FINE, "The Constructed City Map is ", cityMap);
			}

			if (!hashMap.get(CITYPAIR_CACHE).contains(officeExchange) &&
					officeExchange.trim().length() == 5) {
				if (isValidCity) {
					String countryCode = cityMap.get(city).getCountryCode();
					if (!country.equals(countryCode)) {
						log.log(Log.FINE, "The Country Code", countryCode);
						isValidCityCountryPair = false;
					}
				} else {
					isValidCityCountryPair = false;
				}
			}


			    if(isValidCountry){
			    	 if(!hashMap.get(COUNTRY_CACHE).contains(country)){
				    	  hashMap.get(COUNTRY_CACHE).add(country);
				       }
			    }else{
			        if(isOrigin){
				      hashMap.get(ERROR_CACHE).add(ORIGIN_COUNTRY_ERR);

						errorString.append(officeExchange);
						errorString.append(" is an invalid Orgin Office Of exchage ");


		            }else{

							errorString.append(officeExchange);
							errorString.append(" is an invalid Destination Office Of exchage ");


		    	      hashMap.get(ERROR_CACHE).add(DESTN_COUNTRY_ERR);
		            }
			   }

			    if(isValidCity){
			        if(!hashMap.get(CITY_CACHE).contains(city)){
			    	       hashMap.get(CITY_CACHE).add(city);
			        }
			        if(cityMaps.get(city)==null ){
			        	 cityMaps.put(city,cityMap);
			        }
			     }else{
			    	if(isOrigin){

							errorString.append(officeExchange);
							errorString.append(" - Invalid Orgin Office Of exchage ");


			    		 hashMap.get(ERROR_CACHE).add(ORIGIN_CITY_ERR);
			    	}else{

							errorString.append(officeExchange);
							errorString.append("- Invalid Destination Office Of exchage ");


			    		 hashMap.get(ERROR_CACHE).add(DESTN_CITY_ERR);
			    	}
			    }
			      }

			    if(officeExchange.trim().length()==5){
			        if(isValidCityCountryPair){
			    	   if(!hashMap.get(CITYPAIR_CACHE).contains(officeExchange)){
			    		   hashMap.get(CITYPAIR_CACHE).add(officeExchange);
			    	   }
			        }else{
			    	    if(isOrigin){

							errorString.append(officeExchange);

							errorString.append(" - Invalid Office Of exchage ");


			    		   hashMap.get(ERROR_CACHE).add(ORIGIN_PAIR_ERR);
			    	    }else{

							errorString.append(officeExchange);

							errorString.append(" - Invalid Office Of exchage  ");


			    		   hashMap.get(ERROR_CACHE).add(DESTN_PAIR_ERR);
			    	    }
			    	  }

			     }else{
			    	  if(isValidExchange){
				    	   if(!hashMap.get(EXCHANGE_CACHE).contains(officeExchange)){
				    	       hashMap.get(EXCHANGE_CACHE).add(officeExchange);
				           }
			    	   }else{
			    	        if(isOrigin){

			    		      hashMap.get(ERROR_CACHE).add(ORIGINEXCHANGE_ERR);
			    	       }else{

			    		     hashMap.get(ERROR_CACHE).add(DESTINATIONEXCHANGE_ERR);
			    	       }
			           }
			     }

				    if(!isValidPoaCode) {
				    	if(isOrigin) {
				    		hashMap.get(ERROR_CACHE).add(ORIGIN_EXG_PA_ERR);
				    	}
				    }

		 }


		 /**
		  * @author a-1936
		  * This method is used to validate the MailSubClass
		  * @param companyCode
		  * @param subclass
		  * @param hashMap
		  * @throws SystemException
		  */
		 private    void validateSubClass(String companyCode,String subclass,
				 HashMap<String,Collection<String>> hashMap)
		    throws SystemException{
			 log.entering("MailBag","validateSubClass");
			  boolean isValidSubClass= true;
			  errorString=new StringBuilder();
		      if(!hashMap.get(SUBCLS_CACHE).contains(subclass)){
				 isValidSubClass =MailSubClass.validateMailSubClass(companyCode,subclass);
			   }
			   if(isValidSubClass){
				  if(!hashMap.get(SUBCLS_CACHE).contains(subclass)){
				      hashMap.get(SUBCLS_CACHE).add(subclass);
				  }
			   }else{
				   hashMap.get(ERROR_CACHE).add(SUBCLS_CACHE);

					errorString.append(subclass);
					errorString.append(" is an Invalid subclass");

			   }
		 }


		 /**
		   * @author a-1936
		   * @author A-3227
		   * This method is used to create an appended String tat has be sent to the Client
		   * along with the Rxception when the MailTagValidation Fails
		   * @param mailbagVo
		   * @param errors
		   * @return
		   */
		  public String  createErrors(MailbagVO mailbagVo ,Collection<String> errors){
			 log.entering("INSIDE THE CREATE ERRORS","createErrors");
			  StringBuilder builder =null;
			  String error="";
			  HashMap<String, String> cityOEMap = null;
			  Collection<String> officeOfExchanges = new ArrayList<String>();
			  String cityOrigin = null;
			  String cityDestination = null;
			  String countryOrigin = mailbagVo.getOoe().substring(0,2);
			  String countryDestination = mailbagVo.getDoe().substring(0,2);
			  if(mailbagVo.getOoe() !=null && mailbagVo.getOoe().length() > 0) {
				  officeOfExchanges.add(mailbagVo.getOoe());
			  }
			  if(mailbagVo.getDoe() !=null && mailbagVo.getDoe().length() > 0) {
				  officeOfExchanges.add(mailbagVo.getDoe());
			  }
			  try {
				cityOEMap = new MailController().findCityForOfficeOfExchange(mailbagVo.getCompanyCode(),officeOfExchanges);
			  } catch (SystemException e) {
				log.log(Log.FINE, "------findCityForOfficeOfExchange--------Failed----- ");
				log
						.log(
								Log.FINE,
								"------findCityForOfficeOfExchange--mailbagVo.getCompanyCode()-----",
								mailbagVo.getCompanyCode());
				log.log(Log.FINE,
						"------findCityForOfficeOfExchange--officeOfExchanges-----",
						officeOfExchanges);
			  }
			  if(cityOEMap != null && cityOEMap.size() > 0) {
				  cityOrigin = cityOEMap.get(mailbagVo.getOoe());
				  cityDestination = cityOEMap.get(mailbagVo.getDoe());
			  }
			  log.log(Log.FINE, "------cityOrigin-------", cityOrigin);
			log.log(Log.FINE, "--------cityDestination-----", cityDestination);
			if(errors!=null && errors.size()>0){
				   builder = new StringBuilder();
				    for(String errorStr :errors){
					   if(errorStr.equals(ORIGIN_CITY_ERR)){
						   if(cityOrigin!=null){
						   builder.append("City code ").append(cityOrigin).append("\n");
						   }
				       }else if(errorStr.equals(DESTN_CITY_ERR)){
				    	   if(cityDestination!=null){
				       	   builder.append("City code ").append(cityDestination).append("\n");
				    	   }
				       }else if(errorStr.equals(ORIGIN_COUNTRY_ERR)){
				       	   builder.append("Country code ").append(countryOrigin).append("\n");
				       }else if(errorStr.equals(DESTN_COUNTRY_ERR)){
				     	   builder.append("Country code ").append(countryDestination).append("\n");
				       }else if(errorStr.equals(ORIGINEXCHANGE_ERR)){
				           builder.append("Origin OE ").append( mailbagVo.getOoe()).append("\n");
				       }else if(errorStr.equals(DESTINATIONEXCHANGE_ERR)){
				    	   builder.append("Destination OE ").append( mailbagVo.getDoe()).append("\n");
				       }else if(errorStr.equals(ORIGIN_PAIR_ERR)){
				           builder.append("City-Country pair").append(" ").append( mailbagVo.getOoe()).append("\n");
				       }else if(errorStr.equals(DESTN_PAIR_ERR )){
				    	   builder.append("City-Country pair").append(" ").append( mailbagVo.getDoe()).append("\n");
				       }
				       else if(errorStr.equals(SUBCLS_CACHE)){
				    	   builder.append(" Subclass code ").append( mailbagVo.getMailSubclass()).append("\n");
				       } else if(errorStr.equals(ORIGIN_EXG_PA_ERR)) {
				    	   builder.append(" Origin PA Code ").append("\n");
				       }
					}
				    builder.append(" is invalid for ")
				         .append(mailbagVo.getMailbagId());
				         if(mailbagVo.getUldNumber()!=null && mailbagVo.getUldNumber().trim().length()>0){
				        	 builder.append(" in ")
				             .append(mailbagVo.getUldNumber());
				         }

				         error=  builder.toString();
			      }
			     log.log(Log.FINE, "The Error created is ", error);
			return error;
		  }

			/**
			 *@author a-1936
			 * This method is used to validate the Country
			 * @param companyCode
			 * @param country
			 * @return
			 * @throws SystemException
			 */
			private    boolean validateCountry(String companyCode,String country)
			   throws SystemException{
				log.entering("MailBag","validateCountry");
				boolean isValidCountry=true;
				    Collection<String> countryCodes= new ArrayList<String>();
				    countryCodes.add(country);
				  try{
					 Proxy.getInstance().get(SharedAreaProxy.class)
							  .validateCountryCodes(companyCode,countryCodes);
				   }catch(SharedProxyException ex){
					   log.log(Log.INFO,"<<<<<<<<<<<<THE PROXY EXCEPTION CAUGHT >>>>>>>>>>>>>");
					   isValidCountry=false;
				   }
			     return isValidCountry;
			   }

			/**
			 * @author A-1936
			 * This method is used to validate the City
			 * @param companyCode
			 * @param city
			 * @param cityMap
			 * @return
			 * @throws SystemException
			 */

			 private    Map<String,CityVO> validateCity(String companyCode,String city,Map<String,CityVO> cityMap)
			  throws SystemException{
				   log.entering("MailBag","validateCity");
				   Collection<String> cityCodes= new ArrayList<String>();
				   Map<String,CityVO> cityMapFromProxy=null;
				   cityCodes.add(city);
				  try{
					  cityMapFromProxy=  Proxy.getInstance().get(SharedAreaProxy.class).validateCityCodes(companyCode,cityCodes);

				   }catch(SharedProxyException ex){
					   log.log(Log.INFO,"<<<<<<<<<<<<THE PROXY EXCEPTION CAUGHT >>>>>>>>>>>>>");

				   }
			     return cityMapFromProxy;
			  }




			    /**
				 * @author A-2037
				 * This method is used to find the History of a Mailbag
				 * @param companyCode
				 * @param mailbagId
				 * @return
				 * @throws SystemException
				 */
			    public static Collection<MailbagHistoryVO> findMailbagHistories
			    (String companyCode,String mailBagId,long mailSequenceNumber) throws SystemException{  /*modified by A-8149 for ICRD-248207*/
			    	Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<MailbagHistoryVO>();
			    	try{
			    		String mldMsgGenerateFlag = NO;
						mailbagHistoryVOs.addAll(constructDAO().findMailbagHistories(companyCode, mailBagId, mailSequenceNumber,mldMsgGenerateFlag));
			    	}
			    	catch(PersistenceException persistenceException){
			    		throw new SystemException(persistenceException.getErrorCode());
			    	}
			       return mailbagHistoryVOs;
			    }

			    
			    // Added for IASCB-174718
			    public static Collection<MailHistoryRemarksVO> findMailbagNotes
			    (String mailBagId) throws SystemException{
			    	Collection<MailHistoryRemarksVO>  mailHistoryRemarksVOs = new ArrayList<>();
			    	try{
			    		mailHistoryRemarksVOs.addAll(constructDAO().findMailbagNotes(mailBagId));
			    	}
			    	catch(PersistenceException persistenceException){
//			    		
			    		 throw new SystemException(persistenceException.getMessage(),persistenceException);
			    	}
			       return mailHistoryRemarksVOs;
			    }
			    public static Collection<MailbagHistoryVO> findMailStatusDetails
			    (MailbagEnquiryFilterVO mailbagEnquiryFilterVO) throws SystemException{
			    	Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<MailbagHistoryVO>();
			    	MailbagHistoryVO mailbagHistoryVO = null;
			    	try{
			    		   mailbagHistoryVOs.addAll(constructDAO().findMailStatusDetails(mailbagEnquiryFilterVO));
			    	}
			    	catch(PersistenceException persistenceException){
			    		throw new SystemException(persistenceException.getErrorCode());
			    	}
			       return mailbagHistoryVOs;
			    }
			     /**
				 * @author a-1936 This method is used to validate the MailSubClass
				 * @param companyCode
				 * @param mailSubClass
				 * @return
				 * @throws SystemException
				 */
				public static boolean validateMailSubClass(String companyCode,
						String mailSubClass) throws SystemException {
					Log logger = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
					logger.entering("MAILSUBCLASS", "VALIDATEMAILSUBCLASS");
					try {
						return constructDAO().validateMailSubClass(companyCode,
								mailSubClass);
					} catch (PersistenceException persistenceException) {
						persistenceException.getErrorCode();
						throw new SystemException(persistenceException.getErrorCode());
					}
				}




			    	/**
			    	 * TODO Purpose
			    	 * Oct 6, 2006, a-1739
			    	 * @param mailbagVO
			    	 * @return
			    	 * @throws SystemException
			    	 */
			    	public static MailbagVO findMailbagDetailsForUpload(MailbagVO mailbagVO)
			    	throws SystemException {
			    		try {
			    			return constructDAO().findMailbagDetailsForUpload(mailbagVO);
			    		} catch(PersistenceException exception) {
			    			throw new SystemException(exception.getMessage(), exception);
			    		}
			    	}

			    	/**
					 * @author A-1885
					 * @param mailUploadVO
					 * @return
					 * @throws SystemException
					 */
					public static Collection<MailUploadVO> findMailBagandContainers(MailUploadVO mailUploadVO)
					throws SystemException{
						try{
							return constructDAO().findMailbagAndContainer(mailUploadVO);
						}catch(PersistenceException persistenceException){
							throw new SystemException(persistenceException.getErrorCode());
						}
				}






					/**
					 * @author a-1936 This method is used to construct the MailkBagPK
					 * @param mailbagVO
					 * @return
					 * @throws SystemException
					 */
					private static MailbagPK constructMailbagPK(MailbagVO mailbagVO) throws SystemException {
						MailbagPK mailbagPK = new MailbagPK();
						mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
						if(mailbagVO.getMailSequenceNumber()>0) {
							mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						} else {
						    mailbagPK.setMailSequenceNumber(findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
						}
						return mailbagPK;
					}






				    /**
				     *	A-1739
				     * @param toFlightVO
				     * @param containerVO
				     * @param toFlightSegmentSerialNum
				     * @throws SystemException
				     */
				    public void updateFlightForReassign(OperationalFlightVO toFlightVO,
				            ContainerVO containerVO, int toFlightSegmentSerialNum,boolean isRDTUpdateReq)
				        throws SystemException {

				    	LogonAttributes logonAttributes = null;
						try {
							logonAttributes = ContextUtils.getSecurityContext()
									.getLogonAttributesVO();
						} catch (SystemException e) {
							log.log(Log.SEVERE, "SystemException Caught");
						}


				        setCarrierId(toFlightVO.getCarrierId());
				        setFlightNumber(toFlightVO.getFlightNumber());
				        setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
				        setSegmentSerialNumber(toFlightSegmentSerialNum);
				        setScannedPort(containerVO.getAssignedPort());
				        if(toFlightVO.getFlightSequenceNumber() > 0) {
				        	setPou(toFlightVO.getPou());
				        } else {
				        	setPou(containerVO.getFinalDestination());
				        }
				        //Added for icrd-94800	 by A-4810
				        if(containerVO.getScannedDate() != null){
				        	setScannedDate(containerVO.getScannedDate());
				        }
				        else {
				        setScannedDate(
				                new LocalDate(containerVO.getAssignedPort(), Location.ARP,true));
				        }
			/*	        if(containerVO.getLastUpdateTime()!=null){
				        	setLastUpdateTime(containerVO.getLastUpdateTime());
				        }else{*/
				        	setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				        	//Added by A-8527 for IASCB-52472
				        	setLastUpdateUser(logonAttributes.getUserId());
				        //}

				        if(containerVO.isOffload() ) {
							setLatestStatus(MailConstantsVO.MAIL_STATUS_OFFLOADED);
							//setIrregularityflag(MailConstantsVO.FLAG_YES);
				        } else {
				        	if(containerVO.isHandoverReceived()){
				            setLatestStatus(
							                MailConstantsVO.MAIL_STATUS_HNDRCV);
				        	}
				        	else {
                             if (!containerVO.isRemove()){
					            setLatestStatus(
					                MailConstantsVO.MAIL_STATUS_ASSIGNED);
					         }
                           }
				        }
				        setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);

				        if(containerVO.getAssignedUser() != null){
				        	setScannedUser(containerVO.getAssignedUser());
				        }else{
				        	//Modified as part of Bug ICRD-144099 by A-5526
				        	setScannedUser(logonAttributes.getUserId());
				        }
				        if(containerVO.getMailSource()!=null) {
                            setMailbagSource(containerVO.getMailSource());
                        }
				        
				        setMailRemarks(containerVO.getRemarks());
				        if(containerVO.isUldTobarrow()){
				        setContainerType("B");
				        }
				        if(containerVO.isBarrowToUld()){
					        setContainerType("U");
					        }
				       /* MailbagHistoryVO historyVO =
				            constructMailHistVOForReassign(toFlightVO, containerVO);
				        insertHistoryDetails(historyVO);*/
				      //Added by A-8672 as part of IASCB-42757 starts.............
//				        String routingDetails = null;
//				        if(getConsignmentNumber()!=null){
//				        	MailbagVO mailbagVO = new MailbagVO();
//					        mailbagVO.setCompanyCode(getMailbagPK().getCompanyCode());
//					        mailbagVO.setPaCode(getPaCode());
//					        mailbagVO.setConsignmentNumber(getConsignmentNumber());
//					        mailbagVO.setDestination(getDestination());
//							routingDetails = findRoutingDetailsForConsignment(mailbagVO);
//				        }
				        if(!isRDTUpdateReq && getReqDeliveryTime()!=null){
								setReqDeliveryTime(getReqDeliveryTime());
							}
						    else{
							LocalDate reqDlvTim=null;				
							try {
								MailbagVO mailbagTransferVO = new MailbagVO();
								mailbagTransferVO.setDestination(getDestination());
								mailbagTransferVO.setPaCode(getPaCode());
								mailbagTransferVO.setCompanyCode(getMailbagPK().getCompanyCode());
								mailbagTransferVO.setFlightNumber(toFlightVO.getFlightNumber());
								mailbagTransferVO.setFlightDate(toFlightVO.getFlightDate());
								mailbagTransferVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
								reqDlvTim = calculateRDT(mailbagTransferVO);
							} catch (SystemException e) {
								e.getMessage();
							}

							setReqDeliveryTime(reqDlvTim);
					        }
						//Added by A-8672 as part of IASCB-42757 ends.............
				    }

				    	/**
				    	 * 
				    	 * 	Method		:	Mailbag.findRoutingDetailsForConsignment
				    	 *	Added by 	:	A-8061 on 22-May-2020
				    	 * 	Used for 	:
				    	 *	Parameters	:	@param mailbagVO
				    	 *	Parameters	:	@return
				    	 *	Parameters	:	@throws SystemException 
				    	 *	Return type	: 	String
				    	 */
				    	public static String findRoutingDetailsForConsignment(MailbagVO mailbagVO)
					     	throws SystemException{
					 			return constructDAO().findRoutingDetailsForConsignment(mailbagVO);
					     }


				    /**
				     * @author A-1739
				     *
				     * @param toFlightVO
				     * @param containerVO
				     * @return
				     */
				    public MailbagHistoryVO constructMailHistVOForReassign(
				            OperationalFlightVO toFlightVO, ContainerVO containerVO, MailbagVO mailbag) {  MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
				            mailbagHistoryVO.setMailClass(mailbag.getMailClass());
				            LogonAttributes logonAttributes = null;
							try {
								logonAttributes = ContextUtils.getSecurityContext()
										.getLogonAttributesVO();
							} catch (SystemException e) {
								log.log(Log.SEVERE, "SystemException Caught");
							}
				        if(containerVO.isOffload()) {
				            mailbagHistoryVO.setCarrierId(containerVO.getCarrierId());
				            //mailbagHistoryVO.setFlightNumber(containerVO.getFlightNumber()); Commented by A-6991 for ICRD-209424
				            //mailbagHistoryVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				            //added as part of IASCB-65084
				            mailbagHistoryVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				            mailbagHistoryVO.setCarrierCode(containerVO.getCarrierCode());
				            mailbagHistoryVO.setFlightDate(containerVO.getFlightDate());
				            mailbagHistoryVO.setPou(containerVO.getPou());
				            mailbagHistoryVO.setFlightNumber(containerVO.getFlightNumber());
				        } else {
				            mailbagHistoryVO.setCarrierId(toFlightVO.getCarrierId());
				            mailbagHistoryVO.setFlightNumber(toFlightVO.getFlightNumber());
				            mailbagHistoryVO.setFlightSequenceNumber(
				                    toFlightVO.getFlightSequenceNumber());
				            mailbagHistoryVO.setSegmentSerialNumber(getSegmentSerialNumber());
				            mailbagHistoryVO.setCarrierCode(toFlightVO.getCarrierCode());
				            mailbagHistoryVO.setFlightDate(toFlightVO.getFlightDate());
				            if(toFlightVO.getFlightSequenceNumber()>0){
				            mailbagHistoryVO.setPou(toFlightVO.getPou());
				            }
				            
				        }
				        mailbagHistoryVO.setContainerType(containerVO.getType());
				        if(containerVO.isUldTobarrow()){
			            	mailbagHistoryVO.setContainerType("B");
			            }
				        if(containerVO.isBarrowToUld()){
			            	mailbagHistoryVO.setContainerType("U");
			            }
				            mailbagHistoryVO.setScannedPort(containerVO.getAssignedPort());
				        mailbagHistoryVO.setPaBuiltFlag(containerVO.getPaBuiltFlag());
				         //Added for icrd-94800 by A-4810
				        if(containerVO.getScannedDate() != null) {
				        	mailbagHistoryVO.setScanDate(containerVO.getScannedDate());
				        }
				        else
				        {
				        mailbagHistoryVO.setScanDate(
				                    new LocalDate(containerVO.getAssignedPort(), Location.ARP, true));
				            }
				            mailbagHistoryVO.setContainerNumber(containerVO.getContainerNumber());
				            
				            if(containerVO.isOffload()) {
				            	mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_OFFLOADED);
								
					        } else {
					        	if(containerVO.isHandoverReceived()){
					        		mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV); 
					        		mailbagHistoryVO
									.setAdditionalInfo(MailConstantsVO.HNDOVR_CARRIER+containerVO.getCarrierCode());
					        	}
					        	else{
					        	mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED); 
					        	}
					        }
				                  
				            //mailbagHistoryVO.setPou(containerVO.getPou());    //Commented by A-6991 for ICRD-
				            if(containerVO.getAssignedUser() != null){
				            	mailbagHistoryVO.setScanUser(containerVO.getAssignedUser());        
					        }else{  
					        	
				            mailbagHistoryVO.setScanUser(logonAttributes.getUserId());      
					        }
				                
				        mailbagHistoryVO.setMailSource(containerVO.getMailSource());//Added for ICRD-156218
				        mailbagHistoryVO.setMessageVersion(containerVO.getMessageVersion());
				    	if(MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())){
							mailbagHistoryVO.setAdditionalInfo(MailConstantsVO.PA_BUILT_ADD_INFO);
						}
				        mailbagHistoryVO.setScreeningUser(containerVO.getScreeningUser());//Added by A-9498 for IASCB-44577
                        return mailbagHistoryVO;
				            }


				    /**
				      * @author A-2553
				      * @param dsnVO
				      * @return
				      * @throws SystemException
				      */
				     public static String findMailType(MailbagVO mailbagVO)
				     	throws SystemException{
				    	 try {
				 			return constructDAO().findMailType(mailbagVO);
				 		} catch (PersistenceException persistenceException) {
				 			throw new SystemException(persistenceException.getErrorCode());
				 		}
				     }

				 	public MailbagVO retrieveVO() {
						MailbagVO mailbagVO  = new MailbagVO();
						mailbagPK = getMailbagPK();
						mailbagVO.setCompanyCode(mailbagPK.getCompanyCode());
						mailbagVO.setMailSequenceNumber(mailbagPK.getMailSequenceNumber());
						mailbagVO.setMailbagId(getMailIdr());
						mailbagVO.setDespatchId(getDsnIdr());
						mailbagVO.setDespatchSerialNumber(getDespatchSerialNumber());
						mailbagVO.setOoe(getOrginOfficeOfExchange());
						mailbagVO.setDoe(getDestinationOfficeOfExchange());
						mailbagVO.setMailSubclass(getMailSubClass());
						mailbagVO.setMailCategoryCode(getMailCategory());
						mailbagVO.setYear(getYear());
						mailbagVO.setCarrierId(getCarrierId());
						mailbagVO.setFlightNumber(getFlightNumber());
						mailbagVO.setFlightSequenceNumber(getFlightSequenceNumber());
						mailbagVO.setHighestNumberedReceptacle(getHighestNumberedReceptacle());
						mailbagVO.setLatestStatus(getLatestStatus());
                        //Added as a part of ICRD-197419
						mailbagVO.setMailRemarks(getMailRemarks());
						mailbagVO.setPou(getPou());
						mailbagVO.setReceptacleSerialNumber(getReceptacleSerialNumber());
						mailbagVO.setRegisteredOrInsuredIndicator(getRegisteredOrInsuredIndicator());
						mailbagVO.setScannedDate(
								new LocalDate(getScannedPort(), Location.ARP, getScannedDate(), true));
						mailbagVO.setScannedPort(getScannedPort());
						mailbagVO.setScannedUser(getScannedUser());
						mailbagVO.setSegmentSerialNumber(getSegmentSerialNumber());
					//	mailbagVO.setUldNumber(getUldNumber());
						//mailbagVO.setContainerType(getContainerType());
						Measure strWt=new Measure(UnitConstants.MAIL_WGT,getWeight());
						mailbagVO.setWeight(strWt);//modified by A-7371
						mailbagVO.setOperationalStatus(getOperationalStatus());
					//	mailbagVO.setConsignmentNumber(getConsignmentNumber());
					//	mailbagVO.setConsignmentSequenceNumber(getConsignmentSequenceNumber());
						mailbagVO.setPaCode(getPaCode());
						mailbagVO.setDamageFlag(getDamageFlag());
				        mailbagVO.setMailClass(getMailClass());
				        //Added by A-8527 for IASCB-56563 starts
				        mailbagVO.setOrigin(getOrigin());
				        mailbagVO.setDestination(getDestination());
				        //Added by A-8527 for IASCB-56563 Ends
				        if(getLastUpdateTime() != null) { //incase same session retrieveVO happens
				        	mailbagVO.setLastUpdateTime(
				        		new LocalDate(LocalDate.NO_STATION, Location.NONE, getLastUpdateTime(), true));
				        }
				        mailbagVO.setLastUpdateUser(getLastUpdateUser());

				      return mailbagVO;
					}





/**
	 * This method is used to update the mailbag details in case of
	 * reassigning from Flight to Destination
	 * Set MalSta as OFL in case of OFFLOAD
	 *
	 * @param mailbagVo
	 * @param toDestinationVo
	 * @throws SystemException
	 */
	public void  updateDestinationReassignDetails(MailbagVO mailbagVo ,
				ContainerVO toDestinationVo) throws SystemException {

			log.entering("MailBag", "updateDestinationDetails");

			log.log(Log.FINE,
					"The MAIL BAG VO IS FOUND TO BE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",
					mailbagVo);
			log.log(Log.FINE,
					"The MAIL BAG VO IS FOUND TO BE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",
					mailbagVo.getLatestStatus());
			MailbagHistoryVO mailbagHistoryVO = constructMailbagHistoryForDestn(
					mailbagVo, toDestinationVo);
			LogonAttributes logonAttributes = ContextUtils
					.getSecurityContext().getLogonAttributesVO();
			/*if (mailbagVo.isIsoffload()) {
				mailbagHistoryVO
						.setMailStatus(MailConstantsVO.MAIL_STATUS_OFFLOADED);
				mailbagHistoryVO.setCarrierId(getCarrierId());
				mailbagHistoryVO.setFlightNumber(getFlightNumber());
				mailbagHistoryVO.setFlightSequenceNumber(getFlightSequenceNumber());
				mailbagHistoryVO.setContainerNumber(toDestinationVo
						.getContainerNumber());
				mailbagHistoryVO.setSegmentSerialNumber(getSegmentSerialNumber());
				mailbagHistoryVO.setFlightDate(mailbagVo.getFlightDate());

			}*/
			// added to set the status of mailbag to Offloaded when offload is done
			setLatestStatus((mailbagVo.isIsoffload()) ? MailConstantsVO.MAIL_STATUS_OFFLOADED
					: MailConstantsVO.MAIL_STATUS_ASSIGNED);
			// no need to update master details if assign from deviational panel
			if(toDestinationVo.isFromDeviationList()) {
				return ;
			}
			// no need to update master details if remove from inbound station
			if(mailbagVo.isIsoffload() && mailbagVo.isRemove()) {
				return ;
			}
			// Added for BUG_ICRD-128704_MiniS_08Oct2015 starts
			if (mailbagVo.getMailCompanyCode() != null
					&& mailbagVo.getMailCompanyCode().trim().length() > 0) {
				setMailCompanyCode(mailbagVo.getMailCompanyCode());
			}
			// Added for BUG_ICRD-128704_MiniS_08Oct2015 ends
			// Changed as part of bug ICRD-139962
			if (mailbagVo.getMailCompanyCode() != null
					&& mailbagVo.getMailCompanyCode().trim().length() > 0) {
				// Added by A-5945 for ICRD-119569 starts
				if (mailbagVo.getMailCompanyCode() != null
						&& !mailbagVo.getMailCompanyCode().equals(
								getMailCompanyCode())) {
					MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
							MailbagAuditVO.MOD_NAM,
							MailbagAuditVO.SUB_MOD_OPERATIONS,
							MailbagAuditVO.ENTITY_MAILBAG);
					mailbagAuditVO = (MailbagAuditVO) AuditUtils
							.populateAuditDetails(mailbagAuditVO, this, false);
					setMailCompanyCode(mailbagVo.getMailCompanyCode());
					mailbagAuditVO = (MailbagAuditVO) AuditUtils
							.populateAuditDetails(mailbagAuditVO, this, false);
					mailbagAuditVO.setActionCode("UPDATEMAL");
					mailbagAuditVO.setCompanyCode(mailbagVo.getCompanyCode());
					mailbagAuditVO.setMailbagId(mailbagVo.getMailbagId());
					mailbagAuditVO.setDsn(mailbagVo.getDespatchSerialNumber());
					mailbagAuditVO.setOriginExchangeOffice(mailbagVo.getOoe());
					mailbagAuditVO.setDestinationExchangeOffice(mailbagVo.getDoe());
					mailbagAuditVO.setMailSubclass(mailbagVo.getMailSubclass());
					mailbagAuditVO.setMailCategoryCode(mailbagVo
							.getMailCategoryCode());
					mailbagAuditVO.setYear(mailbagVo.getYear());
					
					mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
					StringBuffer additionalInfo = new StringBuffer();
					additionalInfo.append("Company code ")
							.append("updated for mailbag	")
							.append(mailbagVo.getMailbagId());
					mailbagAuditVO.setAdditionalInformation(additionalInfo
							.toString());
					AuditUtils.performAudit(mailbagAuditVO);
				} else
					// Added by A-5945 for ICRD-119569 ends
					{
					setMailCompanyCode(mailbagVo.getMailCompanyCode());
					}
			}
			setCarrierId(toDestinationVo.getCarrierId());
			setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			
			setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			setUldNumber(toDestinationVo.getContainerNumber());
			log.log(Log.FINE, "THE CONTAINER TYPE FROM DESTINATION VO IS ",
					toDestinationVo.getType());
			setContainerType(toDestinationVo.getType());
			setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
			setScannedDate(new LocalDate(toDestinationVo.getAssignedPort(),
					Location.ARP, true).toCalendar());
			setPou(toDestinationVo.getFinalDestination());
			// Added by A-5526 as part of Bug ICRD-74483 starts
			setScannedUser(mailbagVo.getScannedUser());
			//Added by A-8527 for IASCB-52472
        	setLastUpdateUser(logonAttributes.getUserId());
			// Added by A-5526 as part of Bug ICRD-74483 ends
			/*MailbagHistory mailbagHistory = new MailbagHistory(getMailbagPK(),
					mailbagHistoryVO);
			if (getMailbagHistories() == null) {
				setMailbagHistories(new HashSet<MailbagHistory>());
			}
			getMailbagHistories().add(mailbagHistory);*/
		}
    /**
     * @author A-1936
     * This methodis used to construct the MailBagHistoryVo
     * @param mailbagVo
     * @param toDestinationVo
     * @return
     * @throws SystemException 
     */
    public   MailbagHistoryVO constructMailbagHistoryForDestn(MailbagVO mailbagVo,ContainerVO toDestinationVo) throws SystemException{log.log(Log.FINE,
				"<<<<<<<<<<<<<<constructMailbagHistoryForDestn>>>>>>>>",
				toDestinationVo);
	Mailbag mailbag=null;
	try {
		 mailbag=find(createMailbagPK(mailbagVo.getCompanyCode(), mailbagVo));
	} catch (FinderException e) {
		mailbag=null;
	} 
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
	mailbagHistoryVO.setCompanyCode(mailbag.getMailbagPK().getCompanyCode());
    	mailbagHistoryVO.setPaBuiltFlag(toDestinationVo.getPaBuiltFlag());
	/*mailbagHistoryVO.setDestinationExchangeOffice(mailbag.getMailbagPK().getDestinationExchangeOffice());
	mailbagHistoryVO.setOriginExchangeOffice(mailbag.getMailbagPK().getOriginExchangeOffice());
	mailbagHistoryVO.setDsn(mailbag.getMailbagPK().getDsn());
	mailbagHistoryVO.setMailbagId(mailbag.getMailbagPK().getMailbagId());
	mailbagHistoryVO.setMailSubclass(mailbag.getMailbagPK().getMailSubclass());
    mailbagHistoryVO.setMailClass(mailbag.getMailClass());
	mailbagHistoryVO.setMailCategoryCode(mailbag.getMailbagPK().getMailCategoryCode());
	mailbagHistoryVO.setYear(mailbag.getMailbagPK().getYear());*/
    	mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
    	mailbagHistoryVO.setCarrierId(toDestinationVo.getCarrierId());
		mailbagHistoryVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		mailbagHistoryVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		mailbagHistoryVO.setContainerNumber(toDestinationVo.getContainerNumber());
		mailbagHistoryVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
		if(mailbagVo.getScannedDate() != null ){
			mailbagHistoryVO.setScanDate(mailbagVo.getScannedDate());
		}
		else {
		mailbagHistoryVO.setScanDate(
            new LocalDate(toDestinationVo.getAssignedPort(),
                Location.ARP, true));
		}
		mailbagHistoryVO.setScannedPort(mailbagVo.getScannedPort());
		mailbagHistoryVO.setScanUser(mailbagVo.getScannedUser());
		mailbagHistoryVO.setCarrierCode(toDestinationVo.getCarrierCode());
		mailbagHistoryVO.setContainerType(toDestinationVo.getType());
		if(mailbagHistoryVO.getFlightSequenceNumber()>0){  
		  mailbagHistoryVO.setPou(toDestinationVo.getFinalDestination()); 
		}
		mailbagHistoryVO.setMailSource(mailbagVo.getMailSource());//Added for ICRD-156218
		mailbagHistoryVO.setMessageVersion(mailbagVo.getMessageVersion());
		if(MailConstantsVO.FLAG_YES.equals(toDestinationVo.getPaBuiltFlag())){
			mailbagHistoryVO.setAdditionalInfo(MailConstantsVO.PA_BUILT_ADD_INFO);
		}
	return mailbagHistoryVO;}
	/**
     *	A-1739
     * @param mailbagVO
     * @param toContainerVO
     * @throws SystemException
     */
	public void updateFlightReassignDetails(MailbagVO mailbagVO,
			ContainerVO toContainerVO,boolean isRDTUpdateReq) throws SystemException {
		log.entering("Mailbag", "updateFlightReassignDetails");
		log.log(Log.FINE, "getSts", getLatestStatus());
		//log.log(Log.FINE, "getIrregularityflag", getIrregularityflag());
		log.log(Log.FINE, "mailbagVO.getBellyCartId()",
				mailbagVO.getBellyCartId());
/*		if (getLatestStatus().equalsIgnoreCase(
				MailConstantsVO.MAIL_STATUS_OFFLOADED)
				&& ("Y".equalsIgnoreCase(getIrregularityflag()))) {
			setIrregularityflag(MailConstantsVO.FLAG_NO);
		}*/
		//if reassign from deviational panel , no need to update master
		if(toContainerVO.isFromDeviationList()) {
			return ;
		}
		setScannedDate(mailbagVO.getScannedDate().toCalendar());
		setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
		setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		setScannedUser((mailbagVO.getScannedUser()));
		//setBellyCartId(mailbagVO.getBellyCartId());
		if (mailbagVO.getMailCompanyCode() != null
				&& mailbagVO.getMailCompanyCode().trim().length() > 0) {
			// Added by A-5945 for ICRD-119569 starts
			// Changed as part of bug ICRD-139962
			if (mailbagVO.getMailCompanyCode() != null
					&& !mailbagVO.getMailCompanyCode().equals(
							getMailCompanyCode())) {
				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
						MailbagAuditVO.MOD_NAM,
						MailbagAuditVO.SUB_MOD_OPERATIONS,
						MailbagAuditVO.ENTITY_MAILBAG);
				mailbagAuditVO = (MailbagAuditVO) AuditUtils
						.populateAuditDetails(mailbagAuditVO, this, false);
				setMailCompanyCode(mailbagVO.getMailCompanyCode());
				mailbagAuditVO = (MailbagAuditVO) AuditUtils
						.populateAuditDetails(mailbagAuditVO, this, false);
				mailbagAuditVO.setActionCode("UPDATEMAL");
				mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
				mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
				mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
				mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
				mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
				mailbagAuditVO.setMailCategoryCode(mailbagVO
						.getMailCategoryCode());
				mailbagAuditVO.setYear(mailbagVO.getYear());
				LogonAttributes logonAttributes = ContextUtils
						.getSecurityContext().getLogonAttributesVO();
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append("Company code ")
						.append("updated for mailbag	")
						.append(mailbagVO.getMailbagId());
				mailbagAuditVO.setAdditionalInformation(additionalInfo
						.toString());
				AuditUtils.performAudit(mailbagAuditVO);
			} else
				// Added by A-5945 for ICRD-119569 ends
				{
				setMailCompanyCode(mailbagVO.getMailCompanyCode());
				}
		}
		// Added by BUG_ICRD-128704_MiniS_08Oct2015 ends
		if (mailbagVO.getMailCompanyCode() != null
				&& mailbagVO.getMailCompanyCode().trim().length() > 0) {
			setMailCompanyCode(mailbagVO.getMailCompanyCode());
		}
		// added by BUG_ICRD-128704_MiniS_08Oct2015 ends
		if (toContainerVO == null) {
			setCarrierId(mailbagVO.getCarrierId());
			setFlightNumber(mailbagVO.getFlightNumber());
			setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			setPou(mailbagVO.getPou());
			setUldNumber(mailbagVO.getContainerNumber());
			setContainerType(mailbagVO.getContainerType());
		} else {
			setCarrierId(toContainerVO.getCarrierId());
			setFlightNumber(toContainerVO.getFlightNumber());
			setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			setPou(toContainerVO.getPou());
			setUldNumber(toContainerVO.getContainerNumber());
			setContainerType(toContainerVO.getType());
		}
		/*Collection<MailbagHistoryVO> mailbagHistoryVOs = mailbagVO
				.getMailbagHistories();
		if (mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {
			for(MailbagHistoryVO mailbagHistoryVO:mailbagHistoryVOs){


				mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			}
			populateHistoryDetails(mailbagHistoryVOs);
		} else {
			insertHistoryDetails(constructFltReassignHistoryVO(mailbagVO,
					toContainerVO));
		}*/
		//Added by A-8672 as part of IASCB-42757 starts.............
//		String routingDetails = null;
//		if(mailbagVO.getConsignmentNumber()!=null)
//		routingDetails = constructDAO().findRoutingDetailsForConsignment(mailbagVO);
		//Added by A-8672 as part of IASCB-42757 ends.............
		//Added by A-7929 as part of IASCB-42678 starts.............
//		 if(routingDetails!=null && getReqDeliveryTime()!=null){
			if(!isRDTUpdateReq){
				setReqDeliveryTime(getReqDeliveryTime());
			}
		    else{
			LocalDate reqDlvTim=null;				
			try {
				MailbagVO mailbagTransferVO = new MailbagVO();
				mailbagTransferVO.setDestination(mailbagVO.getDestination());
				mailbagTransferVO.setPaCode(mailbagVO.getPaCode());
				mailbagTransferVO.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagTransferVO.setFlightNumber(toContainerVO.getFlightNumber());
				mailbagTransferVO.setFlightDate(toContainerVO.getFlightDate());
				mailbagTransferVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
				reqDlvTim = calculateRDT(mailbagTransferVO);
			} catch (SystemException e) {
				e.getMessage();
			}

			setReqDeliveryTime(reqDlvTim);
	        }
		//Added by A-7929 as part of IASCB-42678 ends.............
	}
	   /**
     * @author A-1936
     * This method is used to constructMailbagHistoryVOForFlight
     * @param mailbagVO
     * @param toDestinationVo
     * @return
     */
    public   MailbagHistoryVO constructFltReassignHistoryVO(
MailbagVO mailbagVO,
			ContainerVO toDestinationVo) {log
		.log(
				Log.INFO,
				"THE CONTAINER VO in constructMailbagHistoryVOForFlight  IS >>>>>>>>>>>>",
				toDestinationVo);
log
		.log(
				Log.INFO,
				"THE MailBagVO VO  in constructMailbagHistoryVOForFlight IS >>>>>>>>>>>>",
				toDestinationVo);
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		// mailbagHistoryVO.setMailStatus(getLatestStatus());
		mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
mailbagHistoryVO.setScannedPort(mailbagVO.getScannedPort());
mailbagHistoryVO.setScanUser(mailbagVO.getScannedUser());
mailbagHistoryVO.setMailClass(mailbagVO.getMailClass());
		if (toDestinationVo != null) {
			mailbagHistoryVO.setPaBuiltFlag(toDestinationVo.getPaBuiltFlag());
			mailbagHistoryVO.setCarrierId(toDestinationVo.getCarrierId());
			mailbagHistoryVO.setFlightNumber(toDestinationVo.getFlightNumber());
    mailbagHistoryVO.setFlightSequenceNumber(toDestinationVo.getFlightSequenceNumber());
    mailbagHistoryVO.setContainerNumber(toDestinationVo.getContainerNumber());
    mailbagHistoryVO.setSegmentSerialNumber(toDestinationVo.getSegmentSerialNumber());
    if(mailbagVO.getScreeningUser()!= null){
    	mailbagHistoryVO.setScreeningUser(mailbagVO.getScreeningUser());
    }
    if(mailbagVO.getScannedDate()!=null){ 
    mailbagHistoryVO.setScanDate(
     			new LocalDate(mailbagVO.getScannedPort(), Location.ARP, mailbagVO.getScannedDate(), true));   
    	
    }else{  
    	
    mailbagHistoryVO.setScanDate(
        new LocalDate(mailbagVO.getScannedPort(),Location.ARP, true));   //Chganged by A-5945 for ICRD-112229
    }
			mailbagHistoryVO.setCarrierCode(toDestinationVo.getCarrierCode());
			mailbagHistoryVO.setContainerType(toDestinationVo.getType());
			mailbagHistoryVO.setPou(toDestinationVo.getPou());
			mailbagHistoryVO.setFlightDate(toDestinationVo.getFlightDate());
	mailbagHistoryVO.setMailSource(toDestinationVo.getMailSource());////Added for ICRD-156218
	mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());

		} else {
    mailbagHistoryVO.setCarrierId(mailbagVO.getCarrierId());     
    mailbagHistoryVO.setFlightNumber(mailbagVO.getFlightNumber());
    mailbagHistoryVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
    mailbagHistoryVO.setContainerNumber(mailbagVO.getUldNumber());
    mailbagHistoryVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
    if(mailbagVO.getScannedDate()!=null){
    mailbagHistoryVO.setScanDate(
     			new LocalDate(mailbagVO.getScannedPort(), Location.ARP, mailbagVO.getScannedDate(), true));
    }else{
    mailbagHistoryVO.setScanDate(
        new LocalDate(mailbagVO.getScannedPort(),Location.ARP, true));
    }
    mailbagHistoryVO.setContainerType(mailbagVO.getContainerType());
    mailbagHistoryVO.setPou(mailbagVO.getPou());
			if (mailbagVO != null) {
				mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
				mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
        mailbagHistoryVO.setMailSource(mailbagVO.getMailSource());//Added for ICRD-156218
        mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
    } 
    /*else {
				mailbagHistoryVO.setFlightDate(null);
				mailbagHistoryVO.setCarrierCode(null);
    }*/
}
//mailbagHistoryVO.setScanDate(mailbagVO.getScannedDate());     
		if(MailConstantsVO.FLAG_YES.equals(toDestinationVo.getPaBuiltFlag())){
			mailbagHistoryVO.setAdditionalInfo(MailConstantsVO.PA_BUILT_ADD_INFO);
		}
		log.log(Log.INFO, "THE MAILBAGHISTORY VO IS >>>>>>>>>>>>",
				mailbagHistoryVO);
return mailbagHistoryVO;}

	/**
	 * @author A-1936
	 * This method is used to updateMailbags Details
	 * @param flightAssignedMails
	 * @param toContainerVO
	 * @throws SystemException
	 */
	public void updateReassignedMailbags(Collection<MailbagVO> flightAssignedMails,
			ContainerVO toContainerVO) throws SystemException {
		if (flightAssignedMails != null && flightAssignedMails.size() > 0) {
			Mailbag mailbag = null; 
			String routingDetails = null;
			HashMap<String,String> routingCache= new HashMap<String,String>();
			boolean isRDTUpdateReq=false;
			StringBuilder routingKey=null;
			
			for (MailbagVO mailbagVo : flightAssignedMails) {
				/*// Added by A-8353 for ICRD-230449 starts   
				MailbagPK mailbagPK = new MailbagPK();
				mailbagPK.setCompanyCode(mailbagVo.getCompanyCode());
				mailbagPK.setMailSequenceNumber(mailbagVo.getMailSequenceNumber());
				// Added by A-8353 for ICRD-230449 ends   */				
				try {
					log.log(Log.INFO, "updateMailBags Called");
					mailbag = Mailbag.findMailbagDetails(constructMailbagPK(mailbagVo));// Added by A-8353 for ICRD-346501  
					/*
					 * Added By Karthick V as the part of the Optimistic Locking
					 */
					  
						mailbag.setLastUpdateTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
					mailbag.setScreeningUser(mailbagVo);/*A-9619 as part of IASCB-55196*/
						mailbag.setMailbagSource(mailbagVo.getMailSource());

				} catch (FinderException ex) {
					log.log(Log.INFO, "DATA INCONSISTENT");
					throw new SystemException(ex.getMessage(), ex);
				}
				if (toContainerVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					log.log(Log.INFO,
							" calling >>>>>>>>>>>>>>updateDestinationDetails");
					mailbag.updateDestinationReassignDetails(mailbagVo,
							toContainerVO);
					mailbag.updatePrimaryAcceptanceDetails(mailbagVo);
				} else {
					log.log(Log.INFO,
							" calling >>>>>>>>>>>>>>updateFlightDetails");
					
					if (mailbagVo.getDestination() == null) {
						mailbagVo.setDestination(mailbag.getDestination());
					}
					if (mailbagVo.getPaCode() == null) {
						mailbagVo.setPaCode(mailbag.getPaCode());
					}
					isRDTUpdateReq=true;
					if(mailbagVo.getConsignmentNumber()!=null){
						routingKey=new StringBuilder();
						routingKey.append(mailbagVo.getPaCode()).append(mailbagVo.getConsignmentNumber()).append(mailbagVo.getDestination());
						if(!routingCache.containsKey(routingKey.toString())){
						routingDetails = findRoutingDetailsForConsignment(mailbagVo);
						if(routingDetails!=null){
							routingCache.put(routingKey.toString(), routingDetails);
							isRDTUpdateReq=false;
						}
						}else{
							isRDTUpdateReq=false;
						}
					}
					mailbag.updateFlightReassignDetails(mailbagVo,
							toContainerVO,isRDTUpdateReq);
					mailbag.updatePrimaryAcceptanceDetails(mailbagVo);
				}

			}
		}

	}

	/**
	 * @author A-1739
	 * @param mailbagPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static Mailbag findMailbag(MailbagPK mailbagPK)
			throws FinderException, SystemException {
		return Mailbag.find(mailbagPK);
	}

	/**
	 * Updates the returndetails in mailbag master and DSNAtAirport
	 * A-1739
	 * @param dsnVO
	 * @param isScanned 
	 * @throws SystemException
	 * @throws DuplicateMailBagsException 
	 */
	public void updateReturnedMailbags(Collection<MailbagVO> mailbagsToReturn, boolean isScanned)
			throws SystemException, DuplicateMailBagsException {
		int totalbags = 0;
		int flightAcpbags = 0;
		double flightAcpWt = 0;
		int destAcpbags = 0;
		double destAcpWt = 0;
		double totalWeight = 0;
		String airportCode = null;

		boolean isInbound = false;

		for (MailbagVO mailbagVO : mailbagsToReturn) {
			totalbags=0;
			totalWeight=0;
			
		
			// Added for ICRD-255189 starts
			mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
			//Added for ICRD-255189 ends
			
			if (MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO
					.getOperationalStatus())) {
				isInbound = true;
			}

			Mailbag mailbag = null;
			boolean isNew = false;
			try {
				 // Added by A-8353 for ICRD-230449 starts
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(   mailbagVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mailbag = findMailbagDetails(mailbagPk);  //IASCB-55196 changed to specific mailbag
				// Added by A-8353 for ICRD-230449 ends
			} catch (FinderException ex) {
				if (isScanned) {
					// new maiblags may be returned while scanning. but not
					// possible
					// from PC
							//Added by A-8353 for ICRD-230449 starts
				              // checkForDuplicateMailbag(mailbagVO,mailbag);
				             // Added by A-8353 for ICRD-230449 ends
					isNew = true;
					// workaround to avoid dmg from getting inserted first
					Collection<DamagedMailbagVO> dmgMails = mailbagVO
							.getDamagedMailbags();
					mailbagVO.setDamagedMailbags(null);

					//Added for ICRD-243469 starts
					/*String serviceLevel = null;
					serviceLevel = findMailServiceLevel(mailbagVO);
					
					if(serviceLevel!=null){
						mailbagVO.setMailServiceLevel(serviceLevel);
					}*///Added for ICRD-243469 ends
					 mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());	// Added by A-8353 for ICRD-230449	
					//Added by A-7794 as part of ICRD-232299
					String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
					if(scanWaved!= null){
						mailbagVO.setScanningWavedFlag(scanWaved);
					}
					
					//ICRD-341146 Begin 
					if(new MailController().isUSPSMailbag(mailbagVO)){
						mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
					}else{
						mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
					}
					//ICRD-341146 End
					
					
					mailbagVO.setMailbagDataSource(mailbagVO.getLatestStatus());
					MailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
					mailbag = new Mailbag(mailbagVO);

					mailbagVO.setDamagedMailbags(dmgMails);
				} else {
					throw new SystemException(ex.getMessage(), ex);
				}
			}

			if (mailbag != null) {
			/*	if(mailbagVO.getLastUpdateTime()!=null){
				mailbag.setLastUpdateTime(mailbagVO.getLastUpdateTime());
				}*/
				//else{
					mailbag.setLastUpdateTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		      //  }

				if (isNew) {
					// insert history for acceptance
					mailbag.updateAcceptanceFlightDetails(mailbagVO);
				} else {
					// update dsnarp for this mailbag only if already existing
					totalWeight += mailbagVO.getWeight().getSystemValue();
					totalbags++;
					if (mailbagVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
						destAcpbags++;
						destAcpWt += mailbagVO.getWeight().getSystemValue();
					} else {
						flightAcpbags++;
						flightAcpWt += mailbagVO.getWeight().getSystemValue();
					}
				}
				// MailbagAuditVO mailBagAuditVo =
				// new MailbagAuditVO(MailbagVO.MODULE,
				// MailbagVO.SUBMODULE, MailbagVO.ENTITY);
				// mailBagAuditVo = (MailbagAuditVO)
				// AuditUtils.populateAuditDetails(
				// mailBagAuditVo, mailbag, false);
				// mailBagAuditVo.setActionCode(MailConstantsVO.AUDIT_MAILRTN);
				mailbag.updateReturnDetails(mailbagVO);
				// performMailbagAudit(mailBagAuditVo, mailbag,
				// AuditVO.UPDATE_ACTION, "Mailbag Returned");
			}

			airportCode = mailbagVO.getScannedPort();


		MailbagInULDAtAirportVO mailbagInULDAtAirportVO = new MailbagInULDAtAirportVO();
		mailbagInULDAtAirportVO.setAirportCode(airportCode);
		mailbagInULDAtAirportVO.setComapnyCode(mailbagVO.getCompanyCode());
		mailbagInULDAtAirportVO.setCarrierId(mailbagVO.getCarrierId());
		if(MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())){
			mailbagInULDAtAirportVO.setUldNumber(new StringBuilder().append("BULK").append("-").append(mailbagVO.getPou()).toString());
		}
		else{
			mailbagInULDAtAirportVO.setUldNumber(mailbagVO.getUldNumber());
		}


		
			mailbagInULDAtAirportVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mailbagInULDAtAirportVO.setAcceptedBags(totalbags);
		//mailbagInULDAtAirportVO.setAcceptedWgt(totalWeight);
		mailbagInULDAtAirportVO.setAcceptedWgt(new Measure(UnitConstants.MAIL_WGT,totalWeight));//added by A-7371
		updateMailbagInULDAtAirportReturnDetails(mailbagInULDAtAirportVO);
		log.exiting("DSN", "updateReturnedMailbags");
	}
	}



    private static void updateMailbagInULDAtAirportReturnDetails(
    		MailbagInULDAtAirportVO dsnAtAirportVO) throws SystemException {
    	try {
			MailbagInULDAtAirport mailbagInULDAtAirport=MailbagInULDAtAirport.find(constructMailbagInULDAtAirportPK(dsnAtAirportVO));
			//mailbagInULDAtAirport.updateReturnDetails(dsnAtAirportVO);
			mailbagInULDAtAirport.remove();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();   
		}


	}
	private static MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(
			MailbagInULDAtAirportVO dsnAtAirportVO) {

		MailbagInULDAtAirportPK mailbagInULDAtAirportPK=new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(dsnAtAirportVO.getComapnyCode());
		mailbagInULDAtAirportPK.setCarrierId(dsnAtAirportVO.getCarrierId());
		mailbagInULDAtAirportPK.setAirportCode(dsnAtAirportVO.getAirportCode());
		mailbagInULDAtAirportPK.setMailSequenceNumber(dsnAtAirportVO.getMailSequenceNumber());
		mailbagInULDAtAirportPK.setUldNumber(dsnAtAirportVO.getUldNumber());


		return mailbagInULDAtAirportPK;
	}
    /**
     *
     * @param mailbagVO
     * @throws SystemException
     */
    public void updateReturnDetails(MailbagVO mailbagVO) throws SystemException {
    	log.entering("Mailbag", "updateReturnDetails");
    	/*
    	 * In order to save the History Information for the Damage First and then Proceed with the
    	 * Returned  Status ..
    	 * Added By Karthick V as the  part of the Air NewZealand Mail Tracking  Bug Fix
    	 */
    	Collection<DamagedMailbagVO> damagedMailbagVOs =
             mailbagVO.getDamagedMailbags();
         if(damagedMailbagVOs != null && damagedMailbagVOs.size() > 0) {
             setDamageFlag(MailConstantsVO.FLAG_YES);

             updateMailbagDamage(mailbagVO);

         }

    	setCarrierId(MailConstantsVO.ZERO);
        setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
        setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
        setSegmentSerialNumber(MailConstantsVO.ZERO);
        
      //  setUldNumber(null);
      //  setContainerType(null);
        setPou(null);
        setLatestStatus(MailConstantsVO.MAIL_STATUS_RETURNED);
        setScannedPort(mailbagVO.getScannedPort());
        setScannedUser(mailbagVO.getScannedUser());
        setScannedDate(
                new LocalDate(mailbagVO.getScannedPort(),
                    Location.ARP, true).toCalendar());

        if(mailbagVO.getMailSource()!=null){
        setMailbagSource(mailbagVO.getMailSource());
        }
        else{
        setMailbagSource(MailConstantsVO.MAIL_STATUS_RETURNED);
        }
        
        /*MailbagHistoryVO mailHistoryVO = constructMailHistoryVO(null);
        mailHistoryVO.setMailSource(mailbagVO.getMailSource());//Added for ICRD-156218
        insertHistoryDetails(mailHistoryVO
                );*/
        log.exiting("Mailbag", "updateReturnDetails");
    }

	/**
	 * TODO Purpose
	 * Oct 11, 2006, a-1739
	 * @param mailbagVOs
	 * @param toContainerVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailbagVO> updateMailbagsForTransfer(
			Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO)
		throws SystemException {
		log.entering("DSN", "updateMailbagsForTransfer");
		Collection<MailbagVO> mailbagsInDSN = new ArrayList<MailbagVO>();
		for(MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			try {//Added by A-8353 for ICRD-230449 starts 
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mailbag = Mailbag.find(mailbagPK);
				// Added by A-8353 for ICRD-230449 ends
			} catch(FinderException exception) {
				log.log(Log.FINE,"Exception in MailController at closeInboundFlightForMailOperation for Online *Flight* ");
				continue;
			}catch(Exception exception) {
				log.log(Log.FINE,"Exception in MailController at closeInboundFlightForMailOperation for Online *Flight* ");
				continue;
			}

			/* Auditing */
//			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
//					MailbagVO.MODULE, MailbagVO.SUBMODULE,
//					MailbagVO.ENTITY);
//        	mailbagAuditVO.setActionCode(MailbagAuditVO.TRANSFER_ACTION);
//			collectMailbagTransferDtlsForAudit(mailbagAuditVO,mailbag,toContainerVO);
			/*
			 * Added By Karthick V as the Part of Optimistic Locking
			 *
			 *
			 */
		/*	if(mailbagVO.getLastUpdateTime()!=null){
			mailbag.setLastUpdateTime(mailbagVO.getLastUpdateTime());
			}else{*/
				//mailbag.setLastUpdateTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				if(mailbagVO.getDestination()==null){
				mailbagVO.setDestination(mailbag.getDestination());
				}
				if(mailbagVO.getPaCode()==null){
					mailbagVO.setPaCode(mailbag.getPaCode());
				}
			//}
			if (toContainerVO.getSegmentSerialNumber() != 0) {
				mailbagVO.setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			}
			mailbag.updateDetailsForTransfer(toContainerVO, mailbagVO);

			/*
			 * Added By Karthick V as the part of the Handed Over Resdit Bug Fix
			 * where the mail Bag Information Passed must be the latest and also set
			 * the CarrierCode as the one to which it was actually Transfeered ..
			 */
			 //should happen after the  details are updated
			 MailbagVO mailbagAfterTransfer= mailbag.retrieveVO();
			 mailbagAfterTransfer.setCarrierCode(toContainerVO.getCarrierCode());
			 mailbagAfterTransfer.setVolume(mailbagVO.getVolume());
			 mailbagsInDSN.add(mailbagAfterTransfer);
			 mailbagVO.setPaBuiltFlag(toContainerVO.getPaBuiltFlag());
//			 AuditUtils.performAudit(mailbagAuditVO);
		}
		log.exiting("DSN", "updateMailbagsForTransfer");
		return mailbagsInDSN;
	}


	/**
	 * TODO Purpose
	 * Oct 11, 2006, a-1739
	 * @param toContainerVO
	 * @param mailbagVO2
	 * @throws SystemException
	 */
	public void updateDetailsForTransfer(ContainerVO toContainerVO,
			MailbagVO mailbagToUpdate) throws SystemException {log.entering("Mailbag", "updateDetailsForTransfer");
			
			LogonAttributes logonAttributes=null;
		if(mailbagToUpdate.isFromDeviationList()){
				 return;
		 }	
		setScannedPort(toContainerVO.getAssignedPort());
		logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		 
		/*if(toContainerVO.getOperationTime() != null) {
			//from inventory
			setScannedDate(toContainerVO.getOperationTime());
			setScannedUser(toContainerVO.getAssignedUser());
		} else {*/
			setScannedUser(mailbagToUpdate.getScannedUser());
		if("WS".equals(mailbagToUpdate.getMailSource()) && mailbagToUpdate.getScannedDate() != null){
			setScannedDate(mailbagToUpdate.getScannedDate());
		}else{	
		setScannedDate(
        		new LocalDate(getScannedPort(), Location.ARP, true).toCalendar());
		}		
if(mailbagToUpdate.getMailCompanyCode()!=null && mailbagToUpdate.getMailCompanyCode().trim().length()>0){
	//Added by A-5945 for ICRD-119569 starts

			if(!mailbagToUpdate.getMailCompanyCode().equals(getMailCompanyCode())){
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
			mailbagAuditVO = (MailbagAuditVO)AuditUtils.populateAuditDetails(mailbagAuditVO,this,false);
		 setMailCompanyCode(mailbagToUpdate.getMailCompanyCode());
			mailbagAuditVO = (MailbagAuditVO)AuditUtils.populateAuditDetails(mailbagAuditVO,this,false);
			mailbagAuditVO.setActionCode("UPDATEMAL");
			mailbagAuditVO.setCompanyCode(mailbagToUpdate.getCompanyCode());
			mailbagAuditVO.setMailbagId(mailbagToUpdate.getMailbagId());
			mailbagAuditVO.setDsn(mailbagToUpdate.getDespatchSerialNumber());
			mailbagAuditVO.setOriginExchangeOffice(mailbagToUpdate.getOoe());
			mailbagAuditVO.setDestinationExchangeOffice(mailbagToUpdate.getDoe());
			mailbagAuditVO.setMailSubclass(mailbagToUpdate.getMailSubclass());
			mailbagAuditVO.setMailCategoryCode(mailbagToUpdate.getMailCategoryCode());
			mailbagAuditVO.setYear(mailbagToUpdate.getYear());

			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
			StringBuffer additionalInfo = new StringBuffer();
			additionalInfo.append("Company code ").append("updated for mailbag	").append(mailbagToUpdate.getMailbagId());
			mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
			AuditUtils.performAudit(mailbagAuditVO);

	}else//Added by A-5945 for ICRD-119569 ends
		 {
		 setMailCompanyCode(mailbagToUpdate.getMailCompanyCode());
		 }
}
		//check if already arrived else create history for arrival befor acceptance hsitroy
		boolean[] eventStats = checkIfHistoryExists(null,
			MailConstantsVO.MAIL_STATUS_ARRIVED,
			MailConstantsVO.MAIL_STATUS_DELIVERED);
		boolean isArrived = eventStats[0];
		boolean isDelivered = eventStats[1];
		if(!isArrived && !isDelivered) {
	          /*  MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(null);
	            mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);*/

	           /* MailbagVO mailbagVO = constructMailbagVO();
            //find prev flight details
            mailbagVO =
	            	MailbagHistory.findLatestFlightDetailsOfMailbag(mailbagVO);*/
            //Modified for ICRD-156218
	           /* if(!MTK_IMP_FLT.equals(mailbagToUpdate.getMailSource())&&!MTK_INB_ONLINEFLT_CLOSURE.equals(mailbagToUpdate.getMailSource())){
            mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
            }
            else {
            mailbagHistoryVO.setFlightDate(mailbagToUpdate.getFlightDate());
            }
            mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
            mailbagHistoryVO.setMailSource(mailbagToUpdate.getMailSource());//Added for ICRD-156218
	            insertHistoryDetails(mailbagHistoryVO);*/
		}
        if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagToUpdate.getMailStatus())){  
        setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
        }
        else if(MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbagToUpdate.getMailStatus())){
        	setLatestStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);	
        }
        else{
        setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);	
        }
        if(mailbagToUpdate.getMailSource()!=null){
        	setMailbagSource(mailbagToUpdate.getMailSource());
        }else{
        setMailbagSource(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
        }
        
        if(toContainerVO.getCarrierId()==0){
        	setCarrierId(mailbagToUpdate.getCarrierId());
        }else{
		setCarrierId(toContainerVO.getCarrierId());
        }
        
        if(toContainerVO.getFlightNumber()==null){
         setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
         setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
         setSegmentSerialNumber(MailConstantsVO.ZERO);
        }else{
        setFlightNumber(toContainerVO.getFlightNumber());
        setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
        setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
        }
        
        setPou(toContainerVO.getPou()==null?toContainerVO.getFinalDestination():toContainerVO.getPou());
        setUldNumber(toContainerVO.getContainerNumber());
        setContainerType(toContainerVO.getType());
    	setMailRemarks(mailbagToUpdate.getMailRemarks());
        //setVolume(mailbagToUpdate.getVolume());
   		 	  	calculateMailbagVolume(mailbagToUpdate); 	   		 
	if (mailbagToUpdate.getVolume() != null) {
		setVolume(mailbagToUpdate.getVolume().getDisplayValue());
        }
    	setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);

//      insert history for outbound
	      /*  MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(null);
        mailbagHistoryVO.setFlightDate(toContainerVO.getFlightDate());
        mailbagHistoryVO.setCarrierCode(toContainerVO.getCarrierCode());
        mailbagHistoryVO.setMailSource(mailbagToUpdate.getMailSource());//Added for ICRD-156218
    	//temporary code for identify the root cause of bug ICRD-160796  starts
	        if(getSegmentSerialNumber()==0 && getFlightNumber()!=null && getFlightNumber()!="-1"){
        	String mailSource=mailbagHistoryVO.getMailSource();
        	if(mailSource!=null && !mailSource.isEmpty()){
        		mailSource=mailSource.concat("UDFT");
        	}
        	  mailbagHistoryVO.setMailSource(mailSource);
        }
    	//temporary code for bug ICRD-160796 by A-5526 as issue is not getting replicated ends
	        insertHistoryDetails(mailbagHistoryVO);*/
        
        //ICRD-341173 
		/*if (toContainerVO.getCarrierCode() != null && !"".equals(toContainerVO.getCarrierCode().trim())
				&& !logonAttributes.getOwnAirlineCode().equals(toContainerVO.getCarrierCode())) {
			setOnTimeDelivery(MailConstantsVO.FLAG_YES);
		}*/
		 
		if(new MailController().isUSPSMailbag(mailbagToUpdate)){
			setOnTimeDelivery(MailConstantsVO.FLAG_NO);
		}else{
			setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
		}
        String routingDetails = null;
		if(mailbagToUpdate.getConsignmentNumber()!=null)
		routingDetails = constructDAO().findRoutingDetailsForConsignment(mailbagToUpdate);
		
        if(routingDetails!=null && getReqDeliveryTime()!=null){
			setReqDeliveryTime(getReqDeliveryTime());
		}
	    else if(getReqDeliveryTime()==null && toContainerVO.getFlightSequenceNumber()>0){    
		LocalDate reqDlvTim=null;				
		try {
			MailbagVO mailbagTransferVO = new MailbagVO();
			mailbagTransferVO.setDestination(mailbagToUpdate.getDestination());
			mailbagTransferVO.setPaCode(mailbagToUpdate.getPaCode());
			mailbagTransferVO.setCompanyCode(mailbagToUpdate.getCompanyCode());
			mailbagTransferVO.setFlightNumber(toContainerVO.getFlightNumber());
			mailbagTransferVO.setFlightDate(toContainerVO.getFlightDate());
			mailbagTransferVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			mailbagTransferVO.setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			reqDlvTim = calculateRDT(mailbagTransferVO);
		} catch (SystemException e) {
			e.getMessage();
		}
	
		setReqDeliveryTime(reqDlvTim);
        }
        setPaCode(mailbagToUpdate.getPaCode());
        updatePrimaryAcceptanceDetails(mailbagToUpdate);
        if(getFirstScanDate()==null&&getFirstScanPort()==null){
			setFirstScanDate(getScannedDate());	
			setFirstScanPort(getScannedPort());
			}
			log.exiting("Mailbag", "updateDetailsForTransfer");}

	/**
	 * @author a-1883
	 * @param mailInConsignmentVO
	 * @return String
	 * @throws SystemException
	 */
	public String checkMailAccepted(MailInConsignmentVO mailInConsignmentVO)
			throws SystemException {
		Log findLog = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
		findLog.entering("DSN", "checkMailAccepted");
		try {
			return constructDAO().checkMailAccepted(mailInConsignmentVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 *
	 * @param opFltVo
	 * @return
	 * @throws SystemException
	 */
	public static ContainerAssignmentVO findContainerDetailsForMRD(
			OperationalFlightVO opFltVo, String mailBag) throws SystemException{
		try{
			return constructDAO().findContainerDetailsForMRD(opFltVo,mailBag);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


	/**
	 *
	 * 	Method		:	Mailbag.validateMailBagsForUPL
	 *	Added by 	:	A-4803 on 24-Nov-2014
	 * 	Used for 	:	validating mail bags for MLD UPL
	 *	Parameters	:	@param flightValidationVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<String>
	 */
	public static Collection<String> validateMailBagsForUPL(FlightValidationVO flightValidationVO) throws
	SystemException {

		try {
			return constructDAO().validateMailBagsForUPL(flightValidationVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

/**
     *	A-1739
     * @param mailbagVO
     * @throws SystemException
     */
    public void updateArrivalDetails(MailbagVO mailbagVO) throws SystemException {
        log.entering("Mailbag", "updateArrivalDetails");

        /*
         * To handle arrival after a RETURN
         */
        
        if(mailbagVO.isFromDeviationList()){
			 return;
        }
        
        if(getCarrierId() == 0) {
	        setCarrierId(mailbagVO.getCarrierId());
	        setFlightNumber(mailbagVO.getFlightNumber());
	        setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
	        setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
	        if(MailConstantsVO.MAIL_STATUS_NEW.equals(getLatestStatus())){
	        	 setUldNumber(mailbagVO.getContainerNumber());
	  	      setContainerType(mailbagVO.getContainerType());
	        }
	        //setUldNumber(mailbagVO.getContainerNumber());
	      //  setContainerType(mailbagVO.getContainerType());
	        if(null == getPou()){
	        	setPou(mailbagVO.getPou());
	        }
        }else{
        	log.log(Log.FINE, "if carrierID !=0 then update flight details");
	        setCarrierId(mailbagVO.getCarrierId());
	        setFlightNumber(mailbagVO.getFlightNumber());
	        setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
	        setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
	        setUldNumber(mailbagVO.getContainerNumber());
	        setContainerType(mailbagVO.getContainerType());
	        if(null == getPou()){
	        	setPou(mailbagVO.getPou());
        }
        }
        
        
        /**
         * Else part added by A-4809 to update the latest flight details while
         * arrival in mtkmalmst table
         */
        /*
         * boolean to identify whether histories already present
         * if index 0 is true then arrival already logged
         * if index 1 is true then delv already logged
         */
        boolean[] arrDlv = updateIfHistoryExists(mailbagVO);

        boolean isAlreadyArrived = arrDlv[0];
        boolean isAlreadyDelivered = arrDlv[1];
        //Added for Bug ICRD-97039 by A-5526 starts
        boolean isAlreadyDamaged = arrDlv[2];
        //Added for Bug ICRD-97039 by A-5526 ends
        /*
         * Go for updates only if already NOT arrived/delivered
         */
        if(!isAlreadyArrived || !isAlreadyDelivered) {
        	boolean canUpdate = false;

			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {

				if (!isAlreadyArrived && !isAlreadyDelivered) {
				 //Commented for icrd-129225  as latest status not getting updated properly in MTKMALMST
					/*if (!(mailbagVO.getDamageFlag() != null && MailConstantsVO.FLAG_YES.equals(
							mailbagVO.getDamageFlag()))) {*/
						setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
						if(mailbagVO.getMailSource()!=null){
						setMailbagSource(mailbagVO.getMailSource());
						}else{
							setMailbagSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
						}
					//}
					setMailCompanyCode(mailbagVO.getMailCompanyCode());
					canUpdate = true;
				}
			}

        	if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
        		if(!isAlreadyDelivered) {
        			setMailCompanyCode(mailbagVO.getMailCompanyCode());
        			setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
        			if(mailbagVO.getMailSource()!=null){
        			setMailbagSource(mailbagVO.getMailSource());	
        			}else{
        			setMailbagSource(MailConstantsVO.MAIL_STATUS_DELIVERED);
        			}
        			if(mailbagVO.isNeedDestUpdOnDlv()){
        		      setDestination(mailbagVO.getDestination());
        		      setDestinationOfficeOfExchange(mailbagVO.getDoe());
        			}
        			canUpdate = true;
        		}
        	}

        	/*
        	 * Either udpated in current transaction or already made inbound
        	 */
        	if(canUpdate || MailConstantsVO.OPERATION_INBOUND.equals(
        			getOperationalStatus())) {
                if(!MailConstantsVO.CHANGE_SCAN_TIME.equals(mailbagVO.getScreen())){
        		if(mailbagVO.getScannedDate()!=null){
        		setScannedDate(mailbagVO.getScannedDate());
        		}
                }else{
                	performChangeScannedTimeAudit(mailbagVO);//Added For ICRD-140584
        		}
        		if(mailbagVO.getScannedUser()!=null){
        		setScannedUser(mailbagVO.getScannedUser());
        		}
        		if(mailbagVO.getScannedPort()!=null){
        		setScannedPort(mailbagVO.getScannedPort());
        		}
        		setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
        	} else {
        		//no update..Master has transfer details now..
        	}
        	mailbagVO.setLatestStatus(getLatestStatus());//Added by A-5945 for ICRD-96316
        	setMailRemarks(mailbagVO.getMailRemarks());//Added by A-7540 for ICRD-197419

        	/*MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(mailbagVO);

        	if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag()) &&
        			!isAlreadyArrived) {
        		mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
        		insertHistoryDetails(mailbagHistoryVO);
        	}*/


        	 //modified for icrd-129225  as damage needs to be captured for nondelivered non arrived mailbags also
			if (mailbagVO.getDamageFlag() != null
					&& mailbagVO.getDamageFlag().equals(
							MailConstantsVO.FLAG_YES)&&!isAlreadyDelivered) {
				boolean newDamage=false;
					Collection<DamagedMailbagVO> damageVOs = mailbagVO.getDamagedMailbags();
				if (damageVOs != null && damageVOs.size() > 0) {

					for(DamagedMailbagVO damagedMailbagVO:damageVOs){
						if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(damagedMailbagVO.getOperationFlag())){
							populateDamageDetails(damageVOs);
							newDamage=true;
						}
					}

				}
				if(newDamage){
              setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
              if(mailbagVO.getDamageFlag()!=null){
              setDamageFlag(mailbagVO.getDamageFlag());
              }else{
            	  setDamageFlag(MailbagVO.FLAG_NO);
              }
     		/* MailbagHistoryVO mailbagHistryVO = constructMailHistoryVO(mailbagVO);
     		 insertHistoryDetails(mailbagHistryVO);*/
				}


     		}


        	if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag()) &&
        			!isAlreadyDelivered) {
        		//Added for Bug ICRD-97039 by A-5526 starts
        		  setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
        		  
        		if(mailbagVO.getMailSource()!=null){
        			setMailbagSource(mailbagVO.getMailSource());	
            	}else{
            		setMailbagSource(MailConstantsVO.MAIL_STATUS_DELIVERED);
            	}
        		  //Added for Bug ICRD-97039 by A-5526 ends
        		/*mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
        		insertHistoryDetails(mailbagHistoryVO);*/
        	}


        	/*
        	 * Done for ANZ Bug 50683
        	 */
        	if(OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
        		// Commented for Bug ICRD-96616 by A-5526 starts
//        		MailbagHistory mailbagHistory = findMailbagHistoryForEvent(mailbagVO);
//        		if(mailbagHistory != null) {
//        			mailbagHistory.setScanDate(mailbagVO.getScannedDate());
//        		}
        		// Commented for Bug ICRD-96616 by A-5526 ends
        		if(mailbagVO.isResditRequired())
            	{
            	updateArrResditEventTimes(mailbagVO);
            	}
        		
        		
        	}
        } else if(isAlreadyDelivered || isAlreadyArrived){
        	//Added as part of BUG ICRD-111287 by A-5526 starts
			if (mailbagVO.getDamageFlag() != null
					&& mailbagVO.getDamageFlag().equals(
							MailConstantsVO.FLAG_YES)) {
				boolean newDamage=false;

				Collection<DamagedMailbagVO> damageVOs = mailbagVO
						.getDamagedMailbags();

				if (damageVOs != null && damageVOs.size() > 0) {
					for(DamagedMailbagVO damagedMailbagVO:damageVOs){
						if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(damagedMailbagVO.getOperationFlag())){
					populateDamageDetails(damageVOs);
					newDamage=true;
						}
				}
				}
				if(newDamage){
				setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
				setDamageFlag(mailbagVO.getDamageFlag());
				/*MailbagHistoryVO mailbagHistryVO = constructMailHistoryVO(mailbagVO);
				insertHistoryDetails(mailbagHistryVO);*/
				}
			}//Added as part of BUG ICRD-111287 by A-5526 ends
			if(mailbagVO.isResditRequired()){
        	updateArrResditEventTimes(mailbagVO);
			}
            if(!MailConstantsVO.CHANGE_SCAN_TIME.equals(mailbagVO.getScreen())){
        		if(mailbagVO.getScannedDate()!=null){
        		setScannedDate(mailbagVO.getScannedDate());
        		}
            }else{
            	performChangeScannedTimeAudit(mailbagVO);
        }
        }
        if(getFirstScanDate()==null&&getFirstScanPort()==null
                && mailbagVO.getScannedPort()!=null&&mailbagVO.getScannedDate()!=null){
       			setFirstScanDate(getScannedDate());	
       			setFirstScanPort(getScannedPort());    
        }
        if(mailbagVO.getDeliveredFlag()!=null && mailbagVO.getDeliveredFlag().equals("Y") && getIntFlg()!=null && getIntFlg().equals("Y")){
        	setIntFlg("N");
        }
        log.exiting("Mailbag", "updateArrivalDetails");
    }
    /**
     * This method checks whether History entry exists or not.
     * If exists for Arrived/Delivered status updates it's Scan date.
     * @param mailbagVO
     * @param isAlreadyArrived
     * @param isDelivered
     * @return
     * @throws SystemException 
     */
    public boolean[] updateIfHistoryExists(MailbagVO mailbagVO) throws SystemException{
    	//Collection<MailbagHistory> existingMailbagHistories = getMailbagHistories();//IASCB-46569
    	Collection<MailbagHistoryVO> existingMailbagHistories = findMailbagHistories(getMailbagPK().getCompanyCode(),"", getMailbagPK().getMailSequenceNumber());
    	
    	
    	 //Modified for Bug ICRD-97039 by A-5526 starts
    	boolean[] arrDlv = new boolean[3];
    	 //Added for Bug ICRD-97039 by A-5526 ends
    	if(existingMailbagHistories != null && existingMailbagHistories.size() >0){
    		for(MailbagHistoryVO mailbagHistory:existingMailbagHistories){
    			if(mailbagHistory.getScannedPort().equals(
    					mailbagVO.getScannedPort())) {
					if(mailbagHistory.getMailStatus().
							equals(MailConstantsVO.MAIL_STATUS_ARRIVED)){
							if(MailConstantsVO.CHANGE_SCAN_TIME.equals(mailbagVO.getScreen())&&//Added For ICRD-140584
							!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())){
								
						MailbagHistoryPK mailbaghistorypk =MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
								MailbagHistory mailhistory = null;
								 try {
		                             mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
		                             mailhistory.setScanDate(mailbagVO.getScannedDate());
		                             LocalDate utcDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
			                    	 GMTDate gmt =  utcDate.toGMTDate(); 
		                             mailhistory.setUtcScanDate(gmt.toCalendar());
		                             mailhistory.setLastUpdateUser(mailbagVO.getLastUpdateUser());
		                             mailhistory.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		                             
		                         } catch (FinderException e) {
									 log.log(Log.SEVERE, "Finder Exception Caught");
								}
							}
    					 arrDlv[0] = true;
    				}
    				if(mailbagHistory.getMailStatus().
							equals(MailConstantsVO.MAIL_STATUS_DELIVERED)){
    					MailbagHistoryPK mailbaghistorypk =MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
						MailbagHistory mailhistory = null;
						 try {
                             mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
                             mailhistory.setScanDate(mailbagVO.getScannedDate());
                             mailhistory.setUtcScanDate(mailbagVO.getScannedDate().toGMTDate());
                             mailhistory.setLastUpdateUser(mailbagVO.getLastUpdateUser());
                             mailhistory.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
                         } catch (FinderException e) {
							 log.log(Log.SEVERE, "Finder Exception Caught");
						}

    					arrDlv[1] = true;
    				}
    			}
    				 //Added for Bug ICRD-97039 by A-5526 starts
    				if(mailbagHistory.getMailStatus().
							equals(MailConstantsVO.MAIL_STATUS_DAMAGED)){
    					    arrDlv[2] = true;
    				}
    				 //Added for Bug ICRD-97039 by A-5526 ends
    		}
    	}
    	return arrDlv;
    }
    /**
     * @param mailbagVO
     * @throws SystemException
     */
    private void updateArrResditEventTimes(MailbagVO mailbagVO)
	throws SystemException {
    	/*String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
			log.log(Log.FINE, "Resdit Enabled ", resditEnabled);*/
    	MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.updateResditEventTimes(mailbagVO);
	//}
	}

    /**
     *	A-1739
     * @param mailbagVO
     * @throws SystemException
     */
    public void updateDamageDetails(MailbagVO mailbagVO)
    throws SystemException {

        updateMailbagDamage(mailbagVO);


    }

	/**
	 * @author a-1936
	 * This method is used to find out the MailBags for the
	 * a Particular Destination-category-Container-Carrier-currentAirport
	 * @param mailInInventoryListVo
	 * @return
	 * @throws SystemException
	 */
	public  static Collection<MailbagVO> findMailBagsForInventory(MailInInventoryListVO  mailInInventoryListVo)
	 throws SystemException{
		 try{
			 return constructDAO().findMailBagsForInventory(mailInInventoryListVo);
		 }catch(PersistenceException ex){
			throw new SystemException(ex.getMessage(),ex);
		}
	}



	/**
	 * @author a-1936
	 * This method is used to find out the MailBags for the
	 * a Particular Destination-category-Container-Carrier-currentAirport
	 * @param mailInInventoryListVo
	 * @return
	 * @throws SystemException
	 */
	public  static Collection<MailbagVO> findMailsForDeliveryFromInventory(MailInInventoryListVO  mailInInventoryListVo)
	 throws SystemException{
		 try{
			 return constructDAO().findMailsForDeliveryFromInventory(mailInInventoryListVo);
		 }catch(PersistenceException ex){
			throw new SystemException(ex.getMessage(),ex);
		}
	}



	 /**
	  * @author A-1936
	  * This method is used to deliver the Mail Bags  that are in the Inventory
	  * Insert the History Details For the Same .
	  * @param mailBagVo
	  * @throws SystemException
	  */
	 public void updateMailbagForDeliveryFromInventory(MailbagVO mailBagVo)
	   throws SystemException{
		      setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
			  setOperationalStatus(
			  MailConstantsVO.OPERATION_INBOUND);
			  if((mailBagVo.getScannedDate()!=null) && (MailConstantsVO.MLD.equals(
					  mailBagVo.getMailSource()))){
				  setScannedDate(mailBagVo.getScannedDate());
			  }else{
			  setScannedDate(new LocalDate(getScannedPort(),Location.ARP,true));
			  }
			  setScannedUser(mailBagVo.getScannedUser());
			  setScannedPort(mailBagVo.getScannedPort());
		        setCarrierId(0);
		        setFlightNumber(null);
		        setFlightSequenceNumber(0);
		        setSegmentSerialNumber(0);
		      //  setUldNumber(null);
		        //setContainerType(null);
		        setPou(null);
		      /*MailbagHistoryVO mailbagHistoryVO = constructMailHistoryVO(null);
		      mailbagHistoryVO.setPaBuiltFlag(mailBagVo.getPaBuiltFlag());
		      mailbagHistoryVO.setMailSource(mailBagVo.getMailSource());//Added for ICRD-156218
		      if(mailBagVo.getScannedDate()!=null){
		    	  mailbagHistoryVO.setScanDate(mailBagVo.getScannedDate());
			  }
		      insertHistoryDetails(mailbagHistoryVO);*/

	 }


	 /**
		 * @author A-1739
		 * @param dsnVO
		 * @throws SystemException
		 * @throws DuplicateMailBagsException
		 */
		public void saveArrivalDetails(DSNVO dsnVO)
		throws SystemException, DuplicateMailBagsException {
			log.entering("DSN", "saveArrivalDetails");


			
			
			Collection<MailbagVO> mailbagVOs = dsnVO.getMailbags();
			if (mailbagVOs != null && mailbagVOs.size() > 0) {


				for (MailbagVO mailbagVO : mailbagVOs) {
					
					// Added for ICRD-255189 starts
					mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
					//Added for ICRD-255189 ends
					
					//Added for ICRD-243469 starts
					/*String serviceLevel = null;
					serviceLevel = findMailServiceLevel(mailbagVO);
					
					if(serviceLevel!=null){
						mailbagVO.setMailServiceLevel(serviceLevel);
					}*/
					//Added for ICRD-243469 ends
					
					
					if (mailbagVO.getOperationalFlag() != null) {
						 boolean isNew = false;
						 Mailbag mailbag = null;
						//Modified for ICRD-126626
						 try {
							mailbag = findMailbag(constructMailbagPK(mailbagVO));
						} catch (FinderException e) {
							log.log(Log.SEVERE, "Finder Exception Caught");
						 }
						 if (mailbag==null) {
							  isNew = true;
						 }
						 boolean isDuplicate = false;
						if (OPERATION_FLAG_INSERT.equals(
								mailbagVO.getOperationalFlag())) {
								if(!isNew) {
									if(!(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) && MailConstantsVO.OPERATION_INBOUND.equals(
											mailbag.getOperationalStatus()) &&
											mailbag.getScannedPort().equals(
														mailbagVO.getScannedPort())) {
										// Added by A-8353 for ICRD-230449 starts
				                        isDuplicate = new MailController().checkForDuplicateMailbag(mailbagVO.getCompanyCode(),mailbagVO.getPaCode(),mailbag);	
				                     // Added by A-8353 for ICRD-230449 ends
									}
								} 
								if(isNew||isDuplicate){
									//Added by A-7794 as part of ICRD-232299
									String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
									if(scanWaved != null){
										mailbagVO.setScanningWavedFlag(scanWaved);
									}
									
									//ICRD-341146 Begin 
									if(new MailController().isUSPSMailbag(mailbagVO)){
										mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
									}else{
										mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
									}
									//ICRD-341146 End
									MailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
									mailbag = new Mailbag(mailbagVO);
									// mailbags.add(mailbag);
								}
						}
						/*
						 * Added By Karthick V
						 * Under  the Assunption that if the lastUpdateTime is not there in the MailBag
						 * Its a New MailBag being Arrived at the Port (Found MailBag ) so simple Insert happens
						 * else the MailBag is  actually getting Updated.
						 */
						if(mailbagVO.getLastUpdateTime()!=null && ! OPERATION_FLAG_INSERT.equals(
								mailbagVO.getOperationalFlag())){
							mailbag.setLastUpdateTime(mailbagVO.getLastUpdateTime().toCalendar());
						}
						if(mailbag != null){
							try{
							mailbag.updateArrivalDetails(mailbagVO);
							}catch(Exception exception){
								log.log(Log.FINE,"Exception in MailController at initiateArrivalForFlights for Offline *Flight* with Mailbag "+mailbagVO);
								continue;
							}

							/*
							 * Updating Consignment Details for mailbag
							 */
							DocumentController docController = new DocumentController();
							MailInConsignmentVO mailInConsignmentVO = null;
							mailInConsignmentVO = docController
									.findConsignmentDetailsForMailbag(mailbagVO
											.getCompanyCode(), mailbagVO
											.getMailbagId(),null);
							if (mailInConsignmentVO != null) {
							//	mailbag.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
							//	mailbag.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
							//	mailbag.setPaCode(mailInConsignmentVO.getPaCode());
							}
						}
//						if(isNew) {
//							//audit for created mailbag
//							performMailbagAudit(mailbagAuditVO, mailbag,
//									AuditVO.CREATE_ACTION, null);
//						} else {
//							//audit for updated mailbag
//							performMailbagAudit(mailbagAuditVO, mailbag,
//									AuditVO.UPDATE_ACTION, null);
//						}
					}
				}
			}
			log.exiting("DSN", "saveArrivalDetails");
		}

		/**
		 * @author a-1936
		 * This method is used to find all the MailBags from the closed Flight
		 * @param operationalFlightVo
		 * @return
		 */
		 public   static  Collection<String> findMailBagsInClosedFlight(OperationalFlightVO
				 operationalFlightVo)throws SystemException{
			 Log logger = LogFactory.getLogger("MailTracking_Defaults");
			 logger.entering("Mailbag","findMailBagsInClosedFlight");
			 try{
					return constructDAO().findMailBagsInClosedFlight(operationalFlightVo);
				}catch(PersistenceException ex){
					throw new SystemException(ex.getMessage(),ex);
				}
				}

		    /**
		     * @author a-1936
		     * This method is used to find the mailbags
		     * @param mailbagEnquiryFilterVO
		     * @param pageNumber
		     * @return
		     * @throws SystemException
		     */
		    public  static  Page<MailbagVO> findMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
		            int pageNumber) throws SystemException{

		    	 if(mailbagEnquiryFilterVO.isInventory()&&
		    			 mailbagEnquiryFilterVO.getDestinationCity()!=null && mailbagEnquiryFilterVO.getDestinationCity().trim().length()>0
		    			 && mailbagEnquiryFilterVO.getMailCategoryCode()!=null && mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0 ){
		    		 createDestintaionCategoryForInventory(mailbagEnquiryFilterVO);
		    	 }
		    	 try{
		    		return constructDAO().findMailbags(mailbagEnquiryFilterVO,pageNumber);
		    	 }
		    	 catch(PersistenceException persistenceException){
		    		throw new SystemException(persistenceException.getErrorCode());
		    	 }

		    }

		    /**
		     * @author a-1936
		     * This method is used to create the appended String of
		     * Destination's and Category's tat could be used as the
		     * Part of the (IN) parameter in the Query of the Find MailBags ..
		     * Say From the Client the Destination Citys may be SIN,SIN,TRV
		     * should be converted to the Form Of 'SIN','TRV'
		     * Note:Duplicates also should be avoided
		     * Say From the Client the Categorys  may be A,A,U
		     * should be converted to the Form Of 'A','U'
		     * Note:Duplicates also should be avoided
		     *@param mailbagEnquiryFilterVO
		     */
		    private  static void  createDestintaionCategoryForInventory(MailbagEnquiryFilterVO
		    		mailbagEnquiryFilterVO){
		     String[] destinationCity=mailbagEnquiryFilterVO.getDestinationCity().split(",");
		     String[]  category=mailbagEnquiryFilterVO.getMailCategoryCode().split(",");
		     String  createdDestination=null;
		     String  createdCategory=null;
		     StringBuilder destinationBuilder=null;
		     StringBuilder categoryBuilder=null;
		     Collection<String> destinationCollection=null;
		     Collection<String> categoryCollection=null;
		     /*
		      * Does the Logic Required for the Destination City's
		      *
		      */
		     if(destinationCity!=null && destinationCity.length>0){
		    	 destinationBuilder=new StringBuilder();
		    	 if(destinationCity.length==1){
		    		 createdDestination=destinationBuilder.append("'").append(destinationCity[0]).append("'").toString();
		    	 }else{
		    		 destinationCollection=new ArrayList<String>();
		    		 for(String dest:destinationCity){
		    			 if(!destinationCollection.contains(dest)){
		    				 destinationBuilder.append("'").append(dest).append("'").append(",");
		    				 destinationCollection.add(dest);
		    			 }
		    	     }
		    		 createdDestination=destinationBuilder.substring(0,destinationBuilder.length()-1);
		    	 }
		     }
		     /*
		      * Does the Logic Required for the Category's..
		      *
		      */
		     if(category!=null && category.length>0){
		    	 categoryBuilder=new StringBuilder();
		    	 if(category.length==1){

		    		 if(!MailConstantsVO.MIL_MAL_CAT.equals(mailbagEnquiryFilterVO.getMailCategoryCode())){
		    			 createdCategory=categoryBuilder.append("'").append(category[0]).append("'").toString();
		    		 }else{
		    			 createdCategory=categoryBuilder.append(category[0]).toString();
		    		 }
		    	 }else{
		    		 categoryCollection=new ArrayList<String>();
		    		 for(String ctg:category){
		    			 if(!categoryCollection.contains(ctg)){
		    				 categoryBuilder.append("'").append(ctg).append("'").append(",");
		    				 categoryCollection.add(ctg);
		    			 }


		    	     }
		    		 createdCategory=categoryBuilder.substring(0,categoryBuilder.length()-1);
		    	 }
		     }
		     mailbagEnquiryFilterVO.setDestinationCity(createdDestination);
		     mailbagEnquiryFilterVO.setMailCategoryCode(createdCategory);
		     }


		 /**
		*  @author a-1936
		*  This  method is used to find out the MailDetais  For  all MailBags for which  Resdits
		*  are  not sent  and  having  the Search Mode as
		*  Despatch..
		*  @param despatchDetailVos
		*  @param unsentResditEvent
		*  @return
		*  @throws SystemException
		*/
		public static Collection<MailbagVO> findMailDetailsForDespatches(Collection<DespatchDetailsVO> despatchDetailVos,String unsentResditEvent)
		  throws SystemException{
			try{
			return constructDAO().findMailDetailsForDespatches(despatchDetailVos,unsentResditEvent);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getMessage(),ex);
		}
		}
		/**
		 * @author a-1936
		 *
		 * This  method is used to find out the MailDetais  For  all MailBags for which  Resdits
		  * are not sent and having the Search Mode as
		  * Document..
		 * @param consignmentDocumentVos
		 * @param unsentResditEvent
		 * @return
		 * @throws SystemException
		 */
		public static Collection<MailbagVO> findMailDetailsForDocument(Collection<ConsignmentDocumentVO> consignmentDocumentVos,String unsentResditEvent)
		 throws SystemException{
			try{
			 return constructDAO().findMailDetailsForDocument(consignmentDocumentVos,unsentResditEvent);
		 }catch(PersistenceException ex){
			throw new SystemException(ex.getMessage(),ex);
		}
		}
	    /**
		 * @author A-2037
		 * This method is used to find the Damaged Mailbag Details
		 * @param companyCode
		 * @param mailbagId
		 * @return
		 * @throws SystemException
		 */
		public static Collection<DamagedMailbagVO> findMailbagDamages(String
				companyCode,String mailbagId) throws SystemException{
			try{
				return constructDAO().findMailbagDamages(companyCode,mailbagId);
			}
			catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());

			}
		}

		/**
		 * @author A-2553
		 * @param mailStatusFilterVO
		 * @return
		 * @throws SystemException
		 */
		public static Collection<MailStatusVO> generateMailStatusReport(MailStatusFilterVO mailStatusFilterVO)
			throws SystemException{
			try{
				return constructDAO().generateMailStatusReport(mailStatusFilterVO);
			}
			catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());

			}
		}

		     /**
		      * @author A-2553
		      * @param companyCode
		      * @param mailbagId
		      * @param opFltVO
		      * @return
		      * @throws SystemException
		      */
		     public static Collection<DespatchDetailsVO> findDespatchesOnDSN(DSNVO dsnVO,String mode)
		     	throws SystemException{
		    	 try {
		  			return constructDAO().findDespatchesOnDSN(dsnVO,mode);
		  		} catch (PersistenceException persistenceException) {
		  			throw new SystemException(persistenceException.getErrorCode());
		  		}
		     }

		 	/**
		 	 * @author A-3227 RENO K ABRAHAM
		 	 * @param damageMailReportFilterVO
		 	 * @return
		 	 * @throws SystemException
		 	 */
		 	public static Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO
		 			damageMailFilterVO)throws SystemException{
		 		try{
		 			return constructDAO().findDamageMailReport(damageMailFilterVO);
		 		}
		 		catch(PersistenceException persistenceException){
		 			throw new SystemException(persistenceException.getErrorCode());

		 		}
		 	}

	    /**
		 * @author A-2553
		 * @param mailbagVOs
		 * @throws SystemException
		 */
		public static ArrayList<MailbagVO> generateMailTag(ArrayList<MailbagVO> mailbagVOs)throws SystemException{
			try{
				return constructDAO().findMailTagDetails(mailbagVOs);//Modified by a-7871 for ICRD-108366
			}catch(PersistenceException ex){
				throw new SystemException(ex.getMessage(),ex);
			}
		}

	/**
		 * TODO Purpose
		 * Sep 26, 2006, a-1739
		 * @param resditEventVO
		 * @param receptacleVO
		 * @return
		 * @throws SystemException
		 */
		private MailbagPK constructMailbagPKForResdit(ResditEventVO resditEventVO, ReceptacleInformationVO receptacleVO) throws SystemException {
			MailbagPK mailPK = new MailbagPK();
			mailPK.setCompanyCode(   resditEventVO.getCompanyCode());
			if(receptacleVO.getMailSequenceNumber()>0) {
				mailPK.setMailSequenceNumber(receptacleVO.getMailSequenceNumber());
			} else {
			String mailbagId = receptacleVO.getReceptacleID();
			mailPK.setMailSequenceNumber(findMailBagSequenceNumberFromMailIdr(mailbagId, resditEventVO.getCompanyCode()));
			}
			return mailPK;
		}

		/**
		 * Create history for all mailbags which had RESDITs flagged
		 * Sep 26, 2006, a-1739
		 * @param resditEventVO
		 * @param consignVO
		 * @throws SystemException
		 */
	  //Modified as part of Bug ICRD-155226 by A-5526
		public void insertMailbagHistoryForResdit(ResditEventVO resditEventVO, ConsignmentInformationVO consignVO,ReceptacleInformationVO receptacleVO) throws
		SystemException {
			log.entering("Mailbag", "insertMailbagHistoryForResdit");
			GMTDate eventTime = consignVO.getEventDate();
			Collection<TransportInformationVO> transportInfoVOs = consignVO.getTransportInformationVOs();
			TransportInformationVO transportVO = null;
			String mailbagId = receptacleVO.getReceptacleID();
			String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT"); 
			if(transportInfoVOs != null && transportInfoVOs.size() > 0) {
				transportVO = ((ArrayList<TransportInformationVO>) transportInfoVOs).get(0);
			}
			//Commented as part of Bug ICRD-155226 by A-5526 starts
			//Collection<ReceptacleInformationVO> receptacleVOs = consignVO.getReceptacleInformationVOs();

			//if(receptacleVOs != null && receptacleVOs.size() > 0) {

			//for(ReceptacleInformationVO receptacleVO : receptacleVOs) {
			//Commented as part of Bug ICRD-155226 by A-5526 ends
				//Added to avoid the string out of bounds issue for belly cart resdits Temp fix
			  if(receptacleVO.getReceptacleID()!= null && receptacleVO.getReceptacleID().trim().length()>0 ){
						MailbagPK mailPK = constructMailbagPKForResdit(resditEventVO, receptacleVO);
						MailbagHistoryVO historyVO = constructMailbagHistoryVOForResdit(resditEventVO, receptacleVO,
								transportVO);
				    	historyVO.setMailSource(triggeringPoint);
				    	//historyVO.setMailClass(mailbagId.substring(13, 15).substring(0,1));
						historyVO.setMessageTime(new LocalDate(resditEventVO.getEventPort(), Location.ARP, true));
						
						if(eventTime!=null&&consignVO.getTransferLocation()!=null&&consignVO.getTransferLocation().length()>0){
							historyVO.setScanDate(new LocalDate(eventTime,consignVO.getTransferLocation(), Location.ARP));
							}else{
								historyVO.setScanDate(
									new LocalDate(resditEventVO.getEventPort(), Location.ARP, true));
						}
//						LocalDate todaysDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
//						historyVO.setMessageTimeUTC(todaysDate);
						StringBuilder sb = new StringBuilder();
						sb.append(MailConstantsVO.SENDER_ID_LEADING_VALUE).append(resditEventVO.getSenderIdentifier())
								.append(MailConstantsVO.SENDER_RECIPIENT_SEPERATOR)
								.append(MailConstantsVO.RECIPIENT_ID_LEADING_VALUE).append(resditEventVO.getRecipientIdentifier());
						historyVO.setAdditionalInfo(sb.toString());
				new MailbagHistory(mailPK, historyVO);
	             }
			//}
			//}
			log.exiting("Mailbag", "insertMailbagHistoryForResdit");

		}


		/**
		 * TODO Purpose
		 * Sep 26, 2006, a-1739
		 * @param resditEventVO
		 * @param receptacleVO
		 * @param transportVO
		 */
		private MailbagHistoryVO constructMailbagHistoryVOForResdit(ResditEventVO resditEventVO,
				ReceptacleInformationVO receptacleVO, TransportInformationVO transportVO) {
			MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
	        mailbagHistoryVO.setMailClass(getMailClass());
			mailbagHistoryVO.setMailStatus(resditEventVO.getResditEventCode());
			mailbagHistoryVO.setScannedPort(resditEventVO.getEventPort());
			//mailbagHistoryVO.setMailSource(MailConstantsVO.MAIL_RESDIT_TRIGGER); Code will be enabled after rebase from TK Hotfix
			
			if(transportVO != null) {
				mailbagHistoryVO.setCarrierCode(transportVO.getCarrierCode());
				mailbagHistoryVO.setCarrierId(transportVO.getCarrierID());
				mailbagHistoryVO.setFlightNumber(transportVO.getFlightNumber());
				mailbagHistoryVO.setFlightSequenceNumber(
						transportVO.getFlightSequenceNumber());
				mailbagHistoryVO.setSegmentSerialNumber(
						transportVO.getSegmentSerialNumber());
				if (((MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(resditEventVO.getResditEventCode()))
						|| (MailConstantsVO.RESDIT_DELIVERED.equals(resditEventVO.getResditEventCode()))
						|| (MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(resditEventVO.getResditEventCode()))
						|| (MailConstantsVO.RESDIT_ARRIVED.equals(resditEventVO.getResditEventCode()))
						|| (MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEventVO.getResditEventCode()))
						|| (MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEventVO.getResditEventCode()))
						|| (MailConstantsVO.RESDIT_FOUND.equals(resditEventVO.getResditEventCode())))
						&& (transportVO.getArrivalTime() != null)) {
					mailbagHistoryVO.setFlightDate(transportVO.getArrivalTime());
				} else {
					mailbagHistoryVO.setFlightDate(transportVO.getDepartureDate());
				}
			}

			return mailbagHistoryVO;
		}
		/**
		 * @param dsnVO
		 * @return
		 * @throws SystemException
		 */
		public static Collection<MailbagVO> findDSNMailbags(DSNVO dsnVO)
				throws SystemException{
			staticLogger().entering(MODULE_NAME, "Ram findDSNMailbags");
			try{
				return constructDAO().findDSNMailbags(dsnVO);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
		}

		/**
		 * 	Method		:	Mailbag.performUploadCorrection
		 *	Added by 	:	A-4809 on Dec 4, 2015
		 * 	Used for 	:
		 *	Parameters	:	@param companyCode
		 *	Return type	: 	void
		 * @throws SystemException
		 */
		public static void performUploadCorrection(String companyCode)
				throws SystemException {
			try{
				 constructDAO().performUploadCorrection(companyCode);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}

		}
		/**
		 * @author A-3227  RENO K ABRAHAM
		 * @param mailHandedOverFilterVO
		 * @return
		 * @throws SystemException
		 */
		public static Collection<MailHandedOverVO> generateMailHandedOverReport(
				MailHandedOverFilterVO mailHandedOverFilterVO)
			throws SystemException{
			try{
				return constructDAO().generateMailHandedOverReport(mailHandedOverFilterVO);
			}
			catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
		}
		/**
		 * 	Method		:	Mailbag.findFlightsForArrival
		 *	Added by 	:	A-4809 on Sep 30, 2015
		 * 	Used for 	:
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemExcepion
		 *	Return type	: 	Collection<OperationalFlightVO>
		 */
		public static Collection<OperationalFlightVO> findFlightsForArrival(String companyCode)
		throws SystemException{
			try{
				return constructDAO().findFlightsForArrival(companyCode);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
		}
		/**
		 * 	Method		:	Mailbag.findFlightsForArrival
		 *	Added by 	:	A-4809 on Sep 30, 2015
		 * 	Used for 	:
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemExcepion
		 *	Return type	: 	Collection<OperationalFlightVO>
		 */
		public static Collection<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO flightVO)
				throws SystemException{
			try{
				return constructDAO().findArrivalDetailsForReleasingMails(flightVO);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
		}
		/**
		 * 	Method		:	Mailbag.findOnlineFlightsAndConatiners
		 *	Added by 	:	A-4809 on Sep 29, 2015
		 * 	Used for 	:
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Return type	: 	Collection<ContainerDetailsVO>
		 */
		public static Collection<MailArrivalVO> findOnlineFlightsAndConatiners(String companyCode)
				throws SystemException{
			try{
				return constructDAO().findOnlineFlightsAndConatiners(companyCode);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
		}
		/**
		 * @author A-1885
		 * @param companyCode
		 * @param time
		 * @return
		 * @throws SystemException
		 */
		public static Collection<OperationalFlightVO> findFlightForMailOperationClosure(
				String companyCode, int time,String airportCode) throws SystemException{
			try{
				return constructDAO().findFlightForMailOperationClosure(companyCode, time, airportCode);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
		}

		/**
	     * This method is ued to  find DSN
	     * @param dSNEnquiryFilterVO
	     * @param pageNumber
	     * @return
	     * @throws SystemException
	     */
	    public static Page<DespatchDetailsVO> findDSNs(
	    		  DSNEnquiryFilterVO dSNEnquiryFilterVO,int pageNumber)
	    		  throws SystemException{
	    	try{
				return constructDAO().findDSNs(dSNEnquiryFilterVO, pageNumber);
			}
			catch(PersistenceException persistenceException){
				persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getErrorCode());
			}
	    }
	    /**
		 * @author A-5249
		 * method: findMailbagsforFlightSegments
		 * to change the assigned flight status to TBA if mailbag present
		 * CR Id: ICRD-84046
		 * @param operationalFlightVO
		 * @param segments
		 * @return boolean
		 * @throws SystemException
		 */
		public static boolean findMailbagsforFlightSegments(OperationalFlightVO operationalFlightVO,
				Collection<FlightSegmentVO> segments,String cancellation) throws SystemException{
			try{
				return constructDAO().findMailbagsforFlightSegments(operationalFlightVO,segments,cancellation);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
	    }
		/**
		 *
		 * @param handoverVO
		 * @return
		 * @throws SystemException
		 */
		public static Collection<OperationalFlightVO> findOperationalFlightForMRD(
					HandoverVO handoverVO) throws SystemException{
				try{
					return constructDAO().findOperationalFlightForMRD(handoverVO);
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());
			}
	    }
		 public static Map<Long,Collection<MailbagHistoryVO>> findMailbagHistoriesMap
		    (String companyCode,long[] malseqnum) throws SystemException{
		    	//Map<Long, Collection<MailbagHistoryVO>> mailCarditHistories =null;
		    	Map<Long, Collection<MailbagHistoryVO>> mailHistories =null;
		    	try{
		    		//mailCarditHistories = constructDAO().findCarditDetailsOfMailbagMap(companyCode,malseqnum);
	    			mailHistories = constructDAO().findMailbagHistoriesMap(companyCode,malseqnum);
	    			/*if(mailHistories != null && !mailHistories.isEmpty()){
	    				if(mailCarditHistories != null && !mailCarditHistories.isEmpty()){
	    					Set<Long> carditKeys = mailCarditHistories.keySet();
	    					Set<Long> mailKeys = mailHistories.keySet();
	    					for(Long mailKey : mailKeys){
	    						for(Long carditKey : carditKeys){
	    							if(carditKey.equals(mailKey)){
	    								mailHistories.get(mailKey).addAll(mailCarditHistories.get(carditKey));
	    							}
	    						}
	    					}
    					}
	    			}*/
		    	}
		    	catch(PersistenceException persistenceException){
		    		throw new SystemException(persistenceException.getErrorCode());
		    	}
		       return mailHistories;
	    }
		 public static Map<Long, MailInConsignmentVO> findAllConsignmentDetailsForMailbag(
				 String companyCode,long[] malseqnum) throws SystemException{
			 Map<Long, MailInConsignmentVO> consignments =null;
			 try{
				 consignments = constructDAO().findAllConsignmentDetailsForMailbag(companyCode, malseqnum);
			 }catch(PersistenceException persistenceException){
		    		throw new SystemException(persistenceException.getErrorCode());
		    	}
			 return consignments;
	    }
		 public MailbagPK createMailbagPK(String companyCode, MailbagVO mailbagVO) throws SystemException {
				MailbagPK mailbagPK = new MailbagPK();
				long mailsequenceNumber;
				if(mailbagVO.getMailSequenceNumber()>0) {
					mailsequenceNumber = mailbagVO.getMailSequenceNumber();
				} else {
				
					mailsequenceNumber = findMailSequenceNumber(mailbagVO.getMailbagId(),companyCode);
				}
				
				mailbagPK.setCompanyCode(companyCode);
				mailbagPK.setMailSequenceNumber(mailsequenceNumber);
				return mailbagPK;
			}
			 public void insertMailbagHistoryDetails(MailbagHistoryVO mailbagHistoryVO, Mailbag mailbag ) throws SystemException {
				 
			    	String triggeringPoint = (String)ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT"); 
			    	
			    	if(mailbagHistoryVO.getMailSource()!=null && mailbagHistoryVO.getMailSource().startsWith(MailConstantsVO.SCAN+":")){
			    		mailbagHistoryVO.setMailSource(mailbagHistoryVO.getMailSource().replace(MailConstantsVO.SCAN+":", ""));
			    	}else if(mailbagHistoryVO.getMailSource()!=null && MailConstantsVO.MAIL_SRC_RESDIT.equals(mailbagHistoryVO.getMailSource())){
			    		mailbagHistoryVO.setMailSource(MailConstantsVO.MAIL_SRC_RESDIT);       
			    	}
			    	//Added by A-8527 for IASCB-58918 starts
			    	else if(mailbagHistoryVO.getMailSource()!=null && MailConstantsVO.MLD.equals(mailbagHistoryVO.getMailSource())){
			    		mailbagHistoryVO.setMailSource(MailConstantsVO.MLD+" "+mailbagHistoryVO.getMessageVersion());
			    	}
			    	//Added by A-8527 for IASCB-58918 ends
			    	else{
			    		mailbagHistoryVO.setMailSource(triggeringPoint);
			    	}
					if (mailbagHistoryVO.isFomDeviationList()
							&& (MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagHistoryVO.getMailStatus())|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagHistoryVO.getMailStatus()))) {
						if (Objects.nonNull(mailbagHistoryVO.getAdditionalInfo())) {
							StringBuilder sb = new StringBuilder(mailbagHistoryVO.getAdditionalInfo());
							sb.append(",").append(ASSIGNED_FROM_DEV_PANEL);
							mailbagHistoryVO.setAdditionalInfo(sb.toString());
						} else {
							mailbagHistoryVO.setAdditionalInfo(ASSIGNED_FROM_DEV_PANEL);
						}
					}
			        /*
			         * Added By Karthick V
			         * In case of Reassign tO Flight Persist the Details in MailHistory also.
			         *
			         */

			         //   new MailbagHistory(mailbag!=null ?mailbag.getMailbagPK():null,mailbagHistoryVO);

			            MailbagHistory.getInstance().persistMailbagHistory(mailbag.getMailbagPK(),mailbagHistoryVO);//A-9619 as part of IASCB-55196

			       /* if(getMailbagHistories()==null){
			         setMailbagHistories(new HashSet<MailbagHistory>());
			         }
			        getMailbagHistories().add(mailbagHistory);*/
				

	    }
			 /**
			  * 
			  * Method		:	Mailbag.findMailbagIdForMailTag
			  *	Added by 	:	a-6245 on 22-Jun-2017
			  * Used for 	:	Finding mailbagid from mail details
			  *	Parameters	:	@param mailbagVO
			  *	Parameters	:	@return
			  *	Parameters	:	@throws SystemException 
			  *	Return type	: 	MailbagVO
			  */
			 public static MailbagVO findMailbagIdForMailTag(MailbagVO mailbagVO)
					 throws SystemException {
				 try{
					 mailbagVO = constructDAO().findMailbagIdForMailTag(mailbagVO);
				 }catch(PersistenceException persistenceException){
			    		throw new SystemException(persistenceException.getErrorCode());
			    	}
				 return mailbagVO;
			 }
			 
			 /**
			  * Method to perform audit during change scan time
			  * Added for ICRD-140584
			  * @param mailbagVO
			  * @throws SystemException
			  */
			 private void performChangeScannedTimeAudit(MailbagVO mailbagVO) throws SystemException{
				 if(mailbagVO.getScannedDate()!=null){
					 if(!mailbagVO.getScannedDate().equals(getScannedDate())){
						 LocalDate oldDate=new LocalDate(getScannedPort(), 
								 Location.ARP, getScannedDate(), true);
							MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
									MailbagAuditVO.MOD_NAM,
									MailbagAuditVO.SUB_MOD_OPERATIONS,
									MailbagAuditVO.ENTITY_MAILBAG);
							mailbagAuditVO = (MailbagAuditVO) AuditUtils
									.populateAuditDetails(mailbagAuditVO, this, false);
							setScannedDate(mailbagVO.getScannedDate());
							mailbagAuditVO = (MailbagAuditVO) AuditUtils
									.populateAuditDetails(mailbagAuditVO, this, false);
							mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_SCNDATTIM_UPDATED);
							mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
							mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
							mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
							mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
							mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
							mailbagAuditVO.setMailCategoryCode(mailbagVO
									.getMailCategoryCode());
							mailbagAuditVO.setYear(mailbagVO.getYear());
							LogonAttributes logonAttributes = ContextUtils
									.getSecurityContext().getLogonAttributesVO();
							mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
							StringBuffer additionalInfo = new StringBuffer();
							additionalInfo.append("Scan date and time of the mail bag ")
									.append(mailbagVO.getMailbagId())
									.append(" was changed from ")
									.append(oldDate.toDisplayFormat())
									.append(" to ")
									.append(mailbagVO.getScannedDate().toDisplayFormat());
							mailbagAuditVO.setAdditionalInformation(additionalInfo
									.toString());
							AuditUtils.performAudit(mailbagAuditVO);
					 }
				 }
			 }
			 /**
			  * Method to fetch mailbag transaction histories only exclude cardit info
			  * @param companyCode
			  * @param mailbagId
			  * @return
			  * @throws SystemException
			  */
			public static Collection<MailbagHistoryVO> findMailbagHistoriesWithoutCarditDetails(
					String companyCode, String mailbagId) throws SystemException{
				Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		    	
		    	try{
		    		String mldMsgGenerateFlag = findSystemParameterValue(MLDMSGSENDINGREQRD);
		    		mailbagHistoryVOs.addAll(constructDAO().findMailbagHistories(companyCode,mailbagId, 0l,mldMsgGenerateFlag));
		    	}
		    	catch(PersistenceException persistenceException){
		    		throw new SystemException(persistenceException.getErrorCode());
		    	}
		       return mailbagHistoryVOs;
	    }
	/**
	 * 
	 * 	Method		:	Mailbag.attachAwbInMailbag
	 *	Added by 	:	a-7779 on 28-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingFlightDetailVOs
	 *	Parameters	:	@param mailbagVOs 
	 *	Return type	: 	void
	 * @throws SystemException 
	 */
	public void attachAwbInMailbag(
			MailBookingDetailVO mailBookingDetailVO) throws SystemException {
		log.entering("Mailbag", "attachAwbInMailbag");
		if (mailBookingDetailVO != null) {
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
				MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS,
				MailbagAuditVO.ENTITY_MAILBAG);
		mailbagAuditVO = (MailbagAuditVO) AuditUtils
				.populateAuditDetails(mailbagAuditVO, this, false);
			this.setMasterDocumentNumber(mailBookingDetailVO.getMasterDocumentNumber())	;
				this.setDocumentOwnerId(mailBookingDetailVO.getOwnerId());
			this.setDupliacteNumber(mailBookingDetailVO.getDuplicateNumber());
			this.setShipmentPrefix(mailBookingDetailVO.getShipmentPrefix());
			this.setSequenceNumber(mailBookingDetailVO.getSequenceNumber());//added by a-7779 for icrd-231589

			if(!"OFL".equals(this.getLatestStatus())){//ICRD-346966
				this.setLatestStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
				this.setFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
				this.setFlightSequenceNumber(mailBookingDetailVO.getBookingFlightSequenceNumber());
				this.setSegmentSerialNumber(mailBookingDetailVO.getSegementserialNumber());
				this.setCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());
			}
				// audit
				mailbagAuditVO = new MailbagAuditVO(
						MailbagAuditVO.MOD_NAM,
						MailbagAuditVO.SUB_MOD_OPERATIONS,
						MailbagAuditVO.ENTITY_MAILBAG);
				mailbagAuditVO = (MailbagAuditVO) AuditUtils
						.populateAuditDetails(mailbagAuditVO, this, false);
				mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_AWB_ATTACHED);
				mailbagAuditVO.setCompanyCode(mailBookingDetailVO
						.getCompanyCode());
				mailbagAuditVO.setStationCode(mailBookingDetailVO
						.getBookingStation());
				LogonAttributes logonAttributes = ContextUtils
						.getSecurityContext().getLogonAttributesVO();
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append("The mailbag ").append(this.getMailIdr())
						.append(ATTACHED_TO_AWB)
						.append(this.getShipmentPrefix())
						.append("-")
						.append(this.getMasterDocumentNumber());
				mailbagAuditVO.setAdditionalInformation(additionalInfo
						.toString());
				mailbagAuditVO.setMailbagId(this.getMailIdr()); 
				mailbagAuditVO.setMailSequenceNumber(mailBookingDetailVO.getMailSequenceNumber());
				AuditUtils.performAudit(mailbagAuditVO);
		
			}
		log.exiting("Mailbag", "attachAwbInMailbag");
	}
	/**
	 * 
	 * 	Method		:	Mailbag.detachAwbInMailbag
	 *	Added by 	:	a-7779 on 29-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	 
	 *	Return type	: 	void
	 * @throws SystemException 
	 */
	public void detachAwbInMailbag(MailbagVO mailbagVO) throws SystemException {
		log.entering("Mailbag","detachAwbInMailbag");
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO( 
				MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS, 
				MailbagAuditVO.ENTITY_MAILBAG);
		mailbagAuditVO = (MailbagAuditVO) AuditUtils
				.populateAuditDetails(mailbagAuditVO, this, false);
		if(MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(this.getLatestStatus())){//ICRD-320167
		this.setLatestStatus(MailConstantsVO.MAIL_STATUS_AWB_CANCELLED);
		}
		this.setMasterDocumentNumber(null);
		this.setDocumentOwnerId(0);
		this.setSequenceNumber(0);
		this.setDupliacteNumber(0);
		this.setShipmentPrefix(null);
		// audit
/*		mailbagAuditVO = new MailbagAuditVO(
				MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS,
				MailbagAuditVO.ENTITY_MAILBAG);*/
		mailbagAuditVO = (MailbagAuditVO) AuditUtils
				.populateAuditDetails(mailbagAuditVO, this, false);
		mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_AWB_DEATTACHED);
		mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagAuditVO.setStationCode(mailbagVO.getStationCode());
		LogonAttributes logonAttributes = ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append("The mailbag ").append(this.getMailIdr())
				.append(" was detached with AWB ")
				.append(mailbagVO.getShipmentPrefix())
				.append("-")
				.append(mailbagVO.getDocumentNumber());
		mailbagAuditVO.setAdditionalInformation(additionalInfo
				.toString());
		mailbagAuditVO.setMailbagId(this.getMailIdr());
		mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		AuditUtils.performAudit(mailbagAuditVO);
		log.exiting("Mailbag","detachAwbInMailbag");
	}
	/**
	 * 
	 * 	Method		:	Mailbag.findAwbAtachedMailbagDetails
	 *	Added by 	:	a-7779 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param ownerId
	 *	Parameters	:	@param masterDocumentNumber2
	 *	Parameters	:	@return 
	 *	Return type	: 	ScannedMailDetailsVO
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	public ScannedMailDetailsVO findAwbAtachedMailbagDetails(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
		try {
			return constructDAO().findAwbAtachedMailbagDetails(shipmentSummaryVO,mailFlightSummaryVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		}
	}
	public static List<MailbagVO> findMailBagsforReassign(ShipmentSummaryVO shipmentSummaryVO,
			MailFlightSummaryVO mailFlightSummaryVO) throws SystemException, PersistenceException {
		return constructDAO().findMailBagsforReassign(shipmentSummaryVO,mailFlightSummaryVO);
	}
	
	/**
	 * 
	 * 	Method		:	Mailbag.findAwbAtachedMailbagDetailsForOffload
	 *	Added by 	:	a-8061 on 30-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param mailFlightSummaryVO
	 *	Parameters	:	@param shipmentSummaryVO
	 *	Parameters	:	@return 
	 *	Return type	: 	ScannedMailDetailsVO
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	public int findAwbPartialOflPcs(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
		try {
			return constructDAO().findAwbPartialOflPcs(shipmentSummaryVO,mailFlightSummaryVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		}
	}
	/**
	 * 
	 * 	Method		:	Mailbag.performAttachAWBDetailsForMailbag
	 *	Added by 	:	U-1267 on Nov 2, 2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param shipmentValidationVO 
	 *	Return type	: 	void
	 */
	public void performAttachAWBDetailsForMailbag(
			MailbagVO mailbagVO,
			ShipmentValidationVO shipmentValidationVO) throws SystemException{
		this.setMasterDocumentNumber(shipmentValidationVO
				.getMasterDocumentNumber());
		this.setDocumentOwnerId(shipmentValidationVO.getOwnerId());
		this.setSequenceNumber(shipmentValidationVO
				.getSequenceNumber());
		this.setDupliacteNumber(shipmentValidationVO
				.getDuplicateNumber());
		this.setShipmentPrefix(shipmentValidationVO
				.getShipmentPrefix());
		MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
				MailbagAuditVO.MOD_NAM,
				MailbagAuditVO.SUB_MOD_OPERATIONS,
				MailbagAuditVO.ENTITY_MAILBAG);
		mailbagAuditVO = (MailbagAuditVO) AuditUtils
				.populateAuditDetails(mailbagAuditVO, this, false);
		mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_AWB_ATTACHED);
		mailbagAuditVO.setCompanyCode(shipmentValidationVO
				.getCompanyCode());
		mailbagAuditVO.setStationCode(shipmentValidationVO
				.getStationCode());
		LogonAttributes logonAttributes = ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append("The mailbag ").append(this.getMailIdr())
				.append(ATTACHED_TO_AWB)
				.append(this.getShipmentPrefix())
				.append("-")
				.append(this.getMasterDocumentNumber());
		mailbagAuditVO.setAdditionalInformation(additionalInfo
				.toString());
		mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
		mailbagAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		AuditUtils.performAudit(mailbagAuditVO);
	}
	/**
	 * @author A-8061
	 * @param carditEnquiryFilterVO
	 * @return
	 */
	public static String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO)throws SystemException {
		try{
			return constructDAO().findGrandTotals(
				carditEnquiryFilterVO);
		}catch(PersistenceException exception){
			throw new SystemException("findGrandTotals");
		}
	}
	
	
		
		
		
		
			
			
				
			
			
			
			
			
			
			
		
	/**
	 * @author A-8061
	 * @param shipmentSummaryVO
	 * @param mailFlightSummaryVO
	 * @return
	 * @throws SystemException
	 */
	
	public ScannedMailDetailsVO findMailbagsForAWB(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException{
		
		try {
			return constructDAO().findMailbagsForAWB(shipmentSummaryVO,mailFlightSummaryVO);
		} catch (SystemException e) {
			throw new SystemException(e.getMessage());
		}
		
		
	}
	/**
	 * @author A-8061
	 * @param shipmentSummaryVO
	 * @param mailFlightSummaryVO
	 * @return
	 */
	public ScannedMailDetailsVO findAWBAttachedMailbags(ShipmentSummaryVO shipmentSummaryVO,
			MailFlightSummaryVO mailFlightSummaryVO) throws SystemException{
		try {
			return constructDAO().findAWBAttachedMailbags(shipmentSummaryVO,mailFlightSummaryVO);
		} catch (SystemException e) {
			throw new SystemException(e.getMessage());
		}
	}
	


	/**
	 * @author A-7929
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException 
	 */
	public static Page<MailbagVO> findAllMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
			int pageNumber) throws SystemException {
		try{
    		return constructDAO().findAllMailbagsForTruckFlight(mailbagEnquiryFilterVO,pageNumber);
    	 }
    	 catch(SystemException e){
    		throw new SystemException((Collection<ErrorVO>) e.getError());
    	 }
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailbag
	 * @return
	 * @throws SystemException
	 * @throws DuplicateMailBagsException
	 */
/*	private static boolean checkForDuplicateMailbag(MailbagVO mailbagVO,Mailbag mailbag) throws SystemException, DuplicateMailBagsException {
	    PostalAdministrationVO postalAdministrationVO = PostalAdministration.findPACode(mailbagVO.getCompanyCode(), mailbagVO.getPaCode());
	    LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
	    LocalDate dspDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);
	    
	    long seconds=currentDate.findDifference(dspDate);
	    float days=seconds/86400000;
	    if((days)<= postalAdministrationVO.getDupMailbagPeriod()){
	    	throw new DuplicateMailBagsException(
	    			DuplicateMailBagsException.
	    			DUPLICATEMAILBAGS_EXCEPTION,
	    			new Object[] {mailbagVO.getMailbagId()});
	    }
	    return true;
	    
	}*/	
	
	/**
	 * @author A-8672
	 * @param MailbagVO
	 * @return
	 * @throws SystemException 
	 */
	public static void saveActualweight(MailbagVO mailbagVO) throws SystemException {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailbag=null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			if(mailbag != null){
				//container.setActualWeight(containerVo.getActualWeight());
				mailbag.setActualWeight(mailbagVO.getActualWeight().getSystemValue());
				mailbag.setActualWeightDisplayValue(mailbagVO.getActualWeight().getDisplayValue());//Added by a-7779 for ICRD-326752
				mailbag.setActualWeightDisplayUnit(mailbagVO.getActualWeight().getDisplayUnit());//Added by a-7779 for ICRD-326752
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	/**
	 * 	Method		:	Mailbag.findDuplicateMailbag
	 *	Added by 	:	A-7531 on 16-May-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param mailBagId
	 *	Parameters	:	@return 
	 *	Return type	: 	ArrayList<MailbagVO>
	 * @throws PersistenceException 
	 */
	public static ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId) throws SystemException{
		try{
    		return constructDAO().findDuplicateMailbag(companyCode,mailBagId);
    	 }
    	 catch(SystemException e){
    		 throw new SystemException(e.getMessage());
    	 }
	
	}
	 @Column(name="ACTWGTDSPUNT")
	public String getActualWeightDisplayUnit() {
		return actualWeightDisplayUnit;
	}
	public void setActualWeightDisplayUnit(String actualWeightDisplayUnit) {
		this.actualWeightDisplayUnit = actualWeightDisplayUnit;
	}
	@Column(name="ACTWGTDSP")
	public double getActualWeightDisplayValue() {
		return actualWeightDisplayValue;
	}
	public void setActualWeightDisplayValue(double actualWeightDisplayValue) {
		this.actualWeightDisplayValue = actualWeightDisplayValue;
	}
	/**
	 * @author A-7540
	 * @param mailbgaVO
	 * @return
	 * @throws SystemException
	 */
	private LocalDate calculateRDT(MailbagVO mailbagVO) throws SystemException{
		String rcpDest = mailbagVO.getDestination();
		LocalDate reqDeliveryDate = null;
		String paCode_int= null;
		String paCode_dom= null;
		Boolean isDestinationPresent = false;
		String[] flightRoutes = null;
		//String flightFinalDestination = null;
		
       
		paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
		if(paCode_dom != null && paCode_dom.equals(mailbagVO.getPaCode())){
			mailbagVO.setReqDeliveryTime(null);
			return null;
		}
		FlightValidationVO flightValidationVO = null; 
        flightValidationVO = validateFlight(mailbagVO);
        
        if(flightValidationVO!=null){
         flightRoutes = flightValidationVO.getFlightRoute().split("-");
         for(int i=0 ;i<flightRoutes.length;i++){
				if(flightRoutes[i].equals(rcpDest)){
					isDestinationPresent =true;
					break;		
				}
			}	
         //flightFinalDestination = flightRoutes[flightRoutes.length-1];
        }
		if(!isDestinationPresent){
			mailbagVO.setReqDeliveryTime(null);
			return null;
		}
		 if(rcpDest!=null)
         	reqDeliveryDate=new LocalDate(rcpDest, Location.ARP, true);
         RdtMasterFilterVO filterVO =new RdtMasterFilterVO();
			filterVO.setAirportCodes(rcpDest);
			 filterVO.setCompanyCode(mailbagVO.getCompanyCode()); 
			 filterVO.setGpaCode(mailbagVO.getPaCode());  
			Collection<MailRdtMasterVO> mailRdtMasterVOs=null;			
				try {
					mailRdtMasterVOs = constructDAO().findRdtMasterDetails(filterVO);
				} catch (PersistenceException e) {
					e.getMessage();
				}
						LocalDate scheduledTimeOfArrival=null;
						LocalDate scheduledTimeOfArrivalCopy=null;
                      
                       if(flightValidationVO!=null){
                       scheduledTimeOfArrival = flightValidationVO.getSta();   
                       }
			if(mailRdtMasterVOs !=null && mailRdtMasterVOs.size() >0){
				for(MailRdtMasterVO mailRdtMasterVO: mailRdtMasterVOs){
					 if(paCode_int.equals(mailbagVO.getPaCode())){						                      
                       if(scheduledTimeOfArrival!=null){
						if(rcpDest!=null)  
						 scheduledTimeOfArrivalCopy=new LocalDate(rcpDest, Location.ARP, true);
								BeanHelper.copyProperties(scheduledTimeOfArrivalCopy,scheduledTimeOfArrival);    
							if(scheduledTimeOfArrivalCopy!=null){
								scheduledTimeOfArrivalCopy.addDays(mailRdtMasterVO.getRdtDay()); 
								scheduledTimeOfArrivalCopy.addMinutes(mailRdtMasterVO.getRdtOffset());
							reqDeliveryDate=scheduledTimeOfArrivalCopy;    
							}
                       }
					}
			}
			}
			else{
				reqDeliveryDate=scheduledTimeOfArrival;
			}
		return reqDeliveryDate;
	}
	/**
	 * 
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException 
	 */
	public static Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws SystemException {
		try{
    		return constructDAO().findDeviationMailbags(mailbagEnquiryFilterVO,pageNumber);
    	 }
    	 catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	 }
	}
	/**
	 * @author A-7540
	 * @param containerDetailsVO
	 * @return
	 * @throws SystemException
	 */
	private FlightValidationVO validateFlight(
            MailbagVO mailbagVO) throws SystemException {
        Collection<FlightValidationVO> flightValidationVOs = null;
        FlightFilterVO flightFilterVO =  new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
        flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
        flightFilterVO.setFlightDate(mailbagVO.getFlightDate());
        //As part of IASCB-56961 STARTS
        flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
        //As part of IASCB-56961 ENDS
        flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
        flightValidationVOs = new FlightOperationsProxy()
        .validateFlightForAirport(flightFilterVO);
        if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
        for (FlightValidationVO flightValidationVO : flightValidationVOs) {
            if (flightValidationVO.getFlightSequenceNumber() == mailbagVO
                    .getFlightSequenceNumber()) {
                return flightValidationVO;
                }
            }
        }
        return null;
	}
	
	/**
	 * @author A-7929
	 * @param MailbagVO
	 * @return
	 * @throws SystemException 
	 */
	public void updateRDT(MailbagVO mailbagVO) throws SystemException {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailbag=null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			if(mailbag != null){
				mailbag.setReqDeliveryTime(mailbagVO.getReqDeliveryTime());
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	/**
	 * 
	 * @param mailbagVO
	 * @throws SystemException
	 */
	public void updateMailbagForConsignment(MailbagVO mailbagVO) throws SystemException {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailbagId(),mailbagVO.getCompanyCode()));
		Mailbag mailbag=null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			if(mailbag != null){
				mailbag.setReqDeliveryTime(mailbagVO.getReqDeliveryTime());
				mailbag.setOrigin(mailbagVO.getOrigin());
				mailbag.setDestination(mailbagVO.getDestination());
				mailbag.setMailServiceLevel(mailbagVO.getMailServiceLevel());
				if (mailbagVO.getConsignmentNumber() != null && !mailbagVO.getConsignmentNumber().isEmpty()
						&& !mailbagVO.getConsignmentNumber().equals(mailbag.getConsignmentNumber())) {
					mailbag.setConsignmentNumber(mailbagVO.getConsignmentNumber());
					mailbag.setConsignmentSequenceNumber(mailbagVO.getConsignmentSequenceNumber());
				}
				if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())){
					mailbag.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
				}
				if (mailbagVO.getWeight() != null && mailbagVO.getWeight().getSystemValue() != mailbag.getWeight()
						&& mailbag.getPaCode().equals(findSystemParameterValue(USPS_DOMESTIC_PA))
						&& MailConstantsVO.CARDIT_PROCESS.equals(mailbagVO.getMailSource())) {
					mailbag.setWeight(mailbagVO.getWeight().getSystemValue());
					mailbag.setDisplayValue(mailbagVO.getWeight().getDisplayValue());
					mailbag.setDisplayUnit(mailbagVO.getWeight().getDisplayUnit());
				}
				
				if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())) {
					new MailController().updatemailperformanceDetails(mailbag);	
				}
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	/**
	 * 
	 * 	Method		:	Mailbag.updateDespatchDate
	 *	Added by 	:	A-8061 on 08-Apr-2020
	 * 	Used for 	:	IASCB-45762
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param duplicatePeriod
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void updateDespatchDate(MailbagVO mailbagVO,int duplicatePeriod) throws SystemException {
		
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailbag=null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			
		     LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		     LocalDate dspDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);
		     long seconds = currentDate.findDifference(dspDate);
		     long days = seconds / 86400000;
		     
		        if (((days) <= duplicatePeriod) || duplicatePeriod==0) {
		        	
					mailbag.setDespatchDate(mailbagVO.getConsignmentDate());
					mailbag.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		        }

		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		
	}
	/**
	 * 
	 * 	Method		:	Mailbag.updatePrimaryAcceptanceDetails
	 *	Added by 	:	A-6245 on 26-Feb-2021
	 * 	Used for 	:	IASCB-96538
	 *	Parameters	:	@param mailbagVO 
	 *	Return type	: 	void
	 */
	public void updatePrimaryAcceptanceDetails(MailbagVO mailbagVO) {
		if ((MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbagVO.getLatestStatus())
				|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO.getLatestStatus())
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus()))
				&& (Objects.isNull(getAcceptanceAirportCode())
						 ||getAcceptanceAirportCode().equals(mailbagVO.getScannedPort()))
				&& (Objects.isNull(getAcceptancePostalContainerNumber()))) {
			if (Objects.nonNull(mailbagVO.getScannedPort())) {
				setAcceptanceAirportCode(mailbagVO.getScannedPort());
				if (Objects.isNull(getAcceptanceScanDate())) {
					if (Objects.nonNull(mailbagVO.getScannedDate())) {
						setAcceptanceScanDate(mailbagVO.getScannedDate().toCalendar());
					} else if (Objects.nonNull(mailbagVO.getFlightDate())) {
						setAcceptanceScanDate(mailbagVO.getFlightDate().toCalendar());
					} else {
						setAcceptanceScanDate(
								new LocalDate(mailbagVO.getScannedPort(), Location.ARP, true).toCalendar());
					}
				}
			} else {
				setAcceptanceAirportCode(getScannedPort());
				setAcceptanceScanDate(getScannedDate());
			}
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())) {
				setAcceptancePostalContainerNumber(mailbagVO.getContainerNumber());
				setPaBuiltFlag(MailConstantsVO.FLAG_YES);
			}
		}
		if ((mailbagVO.isPaContainerNumberUpdate() || mailbagVO.isPaBuiltFlagUpdate())
				&& (getAcceptanceAirportCode().equals(mailbagVO.getScannedPort())
						|| MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO.getTriggerForReImport())
						|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(mailbagVO.getTriggerForReImport()))) {
			mailbagVO.setPreviousPostalContainerNumber(getAcceptancePostalContainerNumber());
			if (Objects.isNull(mailbagVO.getAcceptancePostalContainerNumber())
					|| mailbagVO.getAcceptancePostalContainerNumber().isEmpty()) {
				setPaBuiltFlag(MailConstantsVO.FLAG_NO);
				setAcceptancePostalContainerNumber(null);
			} else {
				setPaBuiltFlag(MailConstantsVO.FLAG_YES);
				setAcceptancePostalContainerNumber(mailbagVO.getAcceptancePostalContainerNumber());
			}
		}
		if(Objects.nonNull(getAcceptanceScanDate())){
			mailbagVO.setAcceptanceScanDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, getAcceptanceScanDate(), true));
		}
	}
	/**
	 * @author A-8353
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public static void updateOriginAndDestinationForMailbag(MailbagVO mailbagVO) throws SystemException {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailbag=null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			if(mailbag != null){
				if(mailbagVO.isOriginUpdate()){
					updateOriginOfMailbag(mailbagVO, mailbag);
				}	
				if(mailbagVO.isDestinationUpdate()){      
				    updateDestinationOfMailbag(mailbagVO, mailbag);
				}
				MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
				mailController.auditMailBagUpdate(mailbagVO, MailbagAuditVO.MAILBAG_ORG_DEST_MODIFIED);
			 }
		   } catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
    }
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailbag
	 * @throws SystemException
	 */
	private static void updateDestinationOfMailbag(MailbagVO mailbagVO, Mailbag mailbag) throws SystemException {
		String officeOfExchange=null;
		boolean isDummyDest=false;
		isDummyDest=new MailController().isValidDestForCarditMissingDomesticMailbag(mailbag.getDestination());
		mailbagVO.setDestination(mailbag.getDestination());
				mailbag.setDestination(mailbagVO.getMailDestination());
		if(isDummyDest){
				officeOfExchange= new MailController().findOfficeOfExchangeForCarditMissingDomMail(mailbagVO.getCompanyCode(),mailbagVO.getMailDestination());
				if(officeOfExchange!=null){
				mailbag.setDestinationOfficeOfExchange(officeOfExchange);
				}
				}
			 }
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailbag
	 * @throws SystemException
	 */
	private static void updateOriginOfMailbag(MailbagVO mailbagVO, Mailbag mailbag) throws SystemException {
		String officeOfExchange=null;
		boolean isDummyOrg=false;
		isDummyOrg=new MailController().isValidDestForCarditMissingDomesticMailbag(mailbag.getOrigin());
		mailbagVO.setOrigin(mailbag.getOrigin());
		mailbag.setOrigin(mailbagVO.getMailOrigin());
		if(isDummyOrg){
		    officeOfExchange= new MailController().findOfficeOfExchangeForCarditMissingDomMail(mailbagVO.getCompanyCode(),mailbagVO.getMailOrigin());
		      if(officeOfExchange!=null){
		         mailbag.setOrginOfficeOfExchange(officeOfExchange);
		     }
	}
	}
	/**
	 * 
	 * 	Method		:	Mailbag.listCarditDsnDetails
	 *	Added by 	:	A-8164 on 04-Sep-2019
	 * 	Used for 	:	List Cardit DSN Details
	 *	Parameters	:	@param dsnEnquiryFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Page<DSNVO>
	 */
	public static Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO)
			throws SystemException{
		try {
			return constructDAO().listCarditDsnDetails(dsnEnquiryFilterVO);
		} catch (SystemException e) {
			throw new SystemException(e.getMessage());
		}
	}
	
	/**
	 * @author A-9619
	 * @return company specific mail controller
	 * @throws SystemException
	 * IASCB-55196
	 */
	public static Mailbag getInstance() throws SystemException {
		return (Mailbag) SpringAdapter.getInstance().getBean("MailbagEntity");

	}
	/**
	 * @author A-9619
	 * @param mailbagPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 * implemented for xaddons IASCB-55196
	 */
	public static Mailbag findMailbagDetails(MailbagPK mailbagPK)
			throws FinderException, SystemException {
		 return PersistenceController.getEntityManager().find(Mailbag.getInstance().getClass(), mailbagPK);
	}
	
	public void setScreeningUser(MailbagVO mailbagVo)
			throws FinderException, SystemException {
		/** Implementation should be done in Client specific entity placed in xaddons specific for DNATA**/
	}

	public static Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO) throws SystemException {
		try {
			return constructDAO().getFoundArrivalMailBags(mailArrivalVO);
		} catch (PersistenceException persistenceException) {

			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	public void attachAwbInMailbagForAddons(
			com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO mailBookingDetailVO) throws SystemException {
		log.entering("Mailbag", "attachAwbInMailbagForAddons");
		if (mailBookingDetailVO != null) {
			if((mailBookingDetailVO.getMasterDocumentNumber()==null || mailBookingDetailVO.getShipmentPrefix()==null) &&
					mailBookingDetailVO.getAwbNumber()!=null){
				mailBookingDetailVO.setMasterDocumentNumber(mailBookingDetailVO.getAwbNumber().split(" ")[1]);
				mailBookingDetailVO.setShipmentPrefix(mailBookingDetailVO.getAwbNumber().split(" ")[0]);
			}   
			
			this.setMasterDocumentNumber(mailBookingDetailVO.getMasterDocumentNumber())	;
			this.setDocumentOwnerId(mailBookingDetailVO.getOwnerId());
			this.setDupliacteNumber(mailBookingDetailVO.getDuplicateNumber());
			this.setShipmentPrefix(mailBookingDetailVO.getShipmentPrefix());
			this.setSequenceNumber(mailBookingDetailVO.getSequenceNumber());

			if(!"OFL".equals(this.getLatestStatus())){
				this.setLatestStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
				this.setFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
				this.setFlightSequenceNumber(mailBookingDetailVO.getBookingFlightSequenceNumber());
				this.setSegmentSerialNumber(mailBookingDetailVO.getSegementserialNumber());
				this.setCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());
			}
				// audit
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
						MailbagAuditVO.MOD_NAM,
						MailbagAuditVO.SUB_MOD_OPERATIONS,
						MailbagAuditVO.ENTITY_MAILBAG);
				mailbagAuditVO = (MailbagAuditVO) AuditUtils
						.populateAuditDetails(mailbagAuditVO, this, false);
				mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_AWB_ATTACHED);
				mailbagAuditVO.setCompanyCode(mailBookingDetailVO
						.getCompanyCode());
				mailbagAuditVO.setStationCode(mailBookingDetailVO
						.getBookingStation());
				LogonAttributes logonAttributes = ContextUtils
						.getSecurityContext().getLogonAttributesVO();
				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				StringBuilder additionalInfo = new StringBuilder();
				additionalInfo.append("The mailbag -").append(this.getMailIdr())
						.append(ATTACHED_TO_AWB)
						.append(this.getShipmentPrefix())
						.append("-")
						.append(this.getMasterDocumentNumber());
				mailbagAuditVO.setAdditionalInformation(additionalInfo
						.toString());
				mailbagAuditVO.setMailbagId(this.getMailIdr()); 
				mailbagAuditVO.setMailSequenceNumber(mailBookingDetailVO.getMailSequenceNumber());
				AuditUtils.performAudit(mailbagAuditVO);
		
			}
		log.exiting("Mailbag", "attachAwbInMailbagForAddons");
	}

	 public static MailbagVO findMailbagDetails(String mailBagId,String companyCode)
  		   throws SystemException{

  	   return constructDAO().findMailbagDetails(mailBagId, companyCode);

     }

    /**
     * @param companyCode
     * @return PostalAdministrationVO
     * @throws SystemException
     * @throws PersistenceException
     * @author 204082
     * Added for IASCB-159276 on 27-Sep-2022
     */
    public Collection<PostalAdministrationVO> getPADetails(String companyCode) throws SystemException {
        Collection<PostalAdministrationVO> paDetails = null;
        try {
            paDetails = constructDAO().getPADetails(companyCode);
        } catch (PersistenceException persistenceException) {
            log.log(Log.SEVERE, persistenceException);
        }
        return paDetails;
    }
	 @Column(name="INTFLG")
		public String getIntFlg() {
			return intFlg;
		}
		public void setIntFlg(String intFlg) {
			this.intFlg = intFlg;
		}

    /**
     * @param mailMasterDataFilterVO
     * @return MailbagDetailsVo
     * @throws SystemException
     * @throws PersistenceException
     * @author 204082
     * Added for IASCB-159267 on 20-Oct-2022
     */
    public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO) throws SystemException {
        Collection<MailbagDetailsVO> mailbagDetailsVOs = null;
        try {
            mailbagDetailsVOs = constructDAO().getMailbagDetails(mailMasterDataFilterVO);
        } catch (PersistenceException persistenceException) {
            log.log(Log.SEVERE, persistenceException);
        }
        return mailbagDetailsVOs;
    }

    /**
     * @param companyCode
     * @return MailSubClassVO
     * @throws SystemException
     * @author 204084
     * Added for IASCB-172483 on 15-Oct-2022
     */
    public Collection<MailSubClassVO> getSubclassDetails(String companyCode) throws SystemException {
        Collection<MailSubClassVO> subclassDetails = null;
        try {
            subclassDetails = constructDAO().getSubclassDetails(companyCode);
        } catch (PersistenceException persistenceException) {
            log.log(Log.SEVERE, persistenceException);
        }
        return subclassDetails;
    }

    /**
     * @param companyCode
     * @return OfficeOfExchangeVO
     * @throws SystemException
     * @throws PersistenceException
     * @author 204083
     * Added for IASCB-172483 on 15-Oct-2022
     */
    public Collection<OfficeOfExchangeVO> getOfficeOfExchangeDetails(String companyCode) throws SystemException {
        Collection<OfficeOfExchangeVO> exchangeOfficeDetails = null;
        try {
            exchangeOfficeDetails = constructDAO().getOfficeOfExchangeDetails(companyCode);
        } catch (PersistenceException persistenceException) {
            log.log(Log.SEVERE, persistenceException);
        }
        return exchangeOfficeDetails;
    }

    /**
     * @param companyCode
     * @param airportCode
     * @return OfficeOfExchangeVO
     * @throws SystemException
     * @author 204082
     * Added for IASCB-164537 on 09-Nov-2022
     */
    public Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode) throws SystemException {
        return constructDAO().getExchangeOfficeDetails(companyCode, airportCode);
    }

    /**
     * @param companyCode
     * @param airportCode
     * @return MailbagVO
     * @throws SystemException
     * @author 204082
     * Added for IASCB-162362 on 14-Nov-2022
     */
    public Collection<MailbagVO> getMailbagDetailsForValidation(String companyCode, String airportCode) throws SystemException {
        return constructDAO().getMailbagDetailsForValidation(companyCode, airportCode);
    }
    

    public void calculateMailbagVolume(MailbagVO mailbagVO) throws SystemException {
    	
		
		String commodityCode = ""; 
		
		
		CommodityValidationVO commodityValidationVO = null;
		try {
			commodityCode = new MailController().findSystemParameterValue("mailtracking.defaults.booking.commodity");
			commodityValidationVO = new MailController().validateCommodity(
					mailbagVO.getCompanyCode(),
					commodityCode,mailbagVO.getPaCode());
		} catch (SystemException e) {
			log.log(Log.FINE, "System exception",e);
		}
		catch(Exception e){
			log.log(Log.FINE, "Exception",e);
		}
		validateCommodity(commodityValidationVO,mailbagVO);
		
    }
    public static void validateCommodity(CommodityValidationVO commodityValidationVO, MailbagVO mailbagVO) throws SystemException {
		if (commodityValidationVO != null
				&& commodityValidationVO.getDensityFactor() >0) {
               if(mailbagVO.getWeight()!=null ){        	  
                /**
                 * Density factor is assumed to be configured in Kg/cbm. So, volume is stamped in cbm in all cases.
                 * Code to be refactored if density factor is unitized. Screens will display value using station unit.
                 */
            	double weightInKg=new DocumentController().unitConvertion(UnitConstants.MAIL_WGT,mailbagVO.getWeight().getSystemUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,mailbagVO.getWeight().getSystemValue());
            	double actualVolume=(weightInKg/(commodityValidationVO.getDensityFactor()));   
                mailbagVO.setVolume(new Measure(UnitConstants.VOLUME, 0.0, actualVolume, UnitConstants.VOLUME_UNIT_CUBIC_METERS));
				}          	  
			}
		}
		
			public static Collection<MailbagHistoryVO> findMailbagHistoriesFromWebScreen(String companyCode, String mailBagId,
			long mailSequenceNumber) throws SystemException {  
    	Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
    	try{
    		String mldMsgGenerateFlag = findSystemParameterValue(MLDMSGSENDINGREQRD);
			mailbagHistoryVOs.addAll(constructDAO().findMailbagHistories(companyCode, mailBagId, mailSequenceNumber,mldMsgGenerateFlag));
    	}
    	catch(PersistenceException persistenceException){
    		LOGGER.log(Log.SEVERE,"findMailbagHistoriesFromWebScreen - PersistenceException: "+persistenceException);
    		throw new SystemException(persistenceException.getErrorCode());
    	}
       return mailbagHistoryVOs;
		}
	}
	  