/*
 * ULDForSegment.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;





import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRouteForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
//import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralRuleConfigDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-5991
 * Entity saving info regarding uld/bulk assignments for a segment.
 * Details of containers for BULK  will be saved in a separate entity.
 *
 *      AssignedFlight
 *             |
 *             |
 *             AssignedFlightSegment
 *                          |
 *                          |
 *                          +--ULDForSegment
 *                                      |
 *                                      |
 *                                      +--DSNInULDForSegment
 *                                      |               |
 *                                      |               |
 *                                      |               +--MailbagInULDForSegmentVO
 *                                      |               |
 *                                      |               |
 *                                      |               +--DSNInContainerForSegmentVO
 *                                      |
 *                                      +--OnwardRouting
 *
 */
@Entity
@Table(name = "MALULDSEG")
public class ULDForSegment {

    private Log log =LogFactory.getLogger("MAIL_OPERATIONS");

    private ULDForSegmentPK uldForSegmentPk;

    private int numberOfBags;
    private double weight;
    private String remarks;

    private int receivedBags;

    private double receivedWeight;
    private String warehouseCode;

    private String locationCode;


    /*
     * Set<OnwardRouteForSegment>
     */
    private Set<OnwardRouteForSegment> onwardRoutes;

    private static final String PRODUCT_NAME = "mail.operations";

    private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA="mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String CLOSE_OUT_OFFSET = "mail.operations.USPSCloseoutoffsettime"; //Added by A-8464 for ICRD-240360 

    private String lastUpdateUser;

    private Calendar lastUpdateTime;

    private String transferFromCarrier;

    private String transferToCarrier;

    private String releasedFlag;
    private Set<MailbagInULDForSegment> mailBagInULDForSegments;


    /**
     * @return Returns the bags.
     */
    @Column(name="BAGCNT")
    public int getNumberOfBags() {
        return numberOfBags;
    }
    /**
     * @param bags The bags to set.
     */
    public void setNumberOfBags(int bags) {
        this.numberOfBags = bags;
    }


    /**
     * @return Returns the mailBagInULDForSegments.
     */
    @OneToMany
    @JoinColumns( {
        @JoinColumn(name="CMPCOD", referencedColumnName="CMPCOD", insertable=false, updatable=false),
        @JoinColumn(name="FLTCARIDR", referencedColumnName="FLTCARIDR", insertable=false, updatable=false),
        @JoinColumn(name="FLTNUM", referencedColumnName="FLTNUM",insertable=false, updatable=false),
        @JoinColumn(name="FLTSEQNUM", referencedColumnName="FLTSEQNUM",insertable=false, updatable=false),
        @JoinColumn(name="SEGSERNUM", referencedColumnName="SEGSERNUM", insertable=false, updatable=false),
        @JoinColumn(name="ULDNUM", referencedColumnName="ULDNUM", insertable=false, updatable=false)
    })
    public Set<MailbagInULDForSegment> getMailBagInULDForSegments() {
  		return mailBagInULDForSegments;
  	}
  	public void setMailBagInULDForSegments(
  			Set<MailbagInULDForSegment> mailBagInULDForSegments) {
  		this.mailBagInULDForSegments = mailBagInULDForSegments;
  	}

    /**
     * @return Returns the remarks.
     *
     */
    @Column(name="RMK")
    public String getRemarks() {
        return remarks;
    }
	/**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * @return Returns the uldForSegmentPk.
     *
     */
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
        @AttributeOverride(name="uldNumber", column=@Column(name="ULDNUM")),
        @AttributeOverride(name="carrierId", column=@Column(name="FLTCARIDR")),
        @AttributeOverride(name="flightNumber", column=@Column(name="FLTNUM")),
        @AttributeOverride(name="flightSequenceNumber", column=@Column(name="FLTSEQNUM")),
        @AttributeOverride(name="segmentSerialNumber", column=@Column(name="SEGSERNUM"))} )
    public ULDForSegmentPK getUldForSegmentPk() {
        return uldForSegmentPk;
    }
    /**
     * @param uldForSegmentPk The uldForSegmentPk to set.
     */
    public void setUldForSegmentPk(ULDForSegmentPK uldForSegmentPk) {
        this.uldForSegmentPk = uldForSegmentPk;
    }
    /**
     * @return Returns the weight.
     *
     */
    @Column(name="BAGWGT")
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
     * @return Returns the onwardRoutes.
     */
    @OneToMany
    @JoinColumns( {
        @JoinColumn(name="CMPCOD", referencedColumnName="CMPCOD",  insertable=false, updatable=false),
        @JoinColumn(name="FLTCARIDR",  referencedColumnName="FLTCARIDR", insertable=false, updatable=false),
        @JoinColumn(name="FLTNUM", referencedColumnName="FLTNUM", insertable=false, updatable=false),
        @JoinColumn(name="FLTSEQNUM",referencedColumnName="FLTSEQNUM", insertable=false, updatable=false),
        @JoinColumn(name="SEGSERNUM", referencedColumnName="SEGSERNUM", insertable=false, updatable=false),
        @JoinColumn(name="ULDNUM", referencedColumnName="ULDNUM",insertable=false, updatable=false)
    })
    public Set<OnwardRouteForSegment> getOnwardRoutes() {
        return onwardRoutes;
    }
    /**
     * @param onwardRoutes The onwardRoutes to set.
     */
    public void setOnwardRoutes(Set<OnwardRouteForSegment> onwardRoutes) {
        this.onwardRoutes = onwardRoutes;
    }


    /**
     * @return Returns the locationCode.
     */
    @Column(name="LOCCOD")
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * @param locationCode The locationCode to set.
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * @return Returns the warehouseCode.
     */
    @Column(name="WHSCOD")
    public String getWarehouseCode() {
        return warehouseCode;
    }

    /**
     * @param warehouseCode The warehouseCode to set.
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }




    /**
     * @return Returns the lastUpdateTime.
     */
    @Version
    @Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime The lastUpdateTime to set.
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * @return Returns the lastUpdateUser.
     */
    @Column(name="LSTUPDUSR")
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
	 * @return Returns the receivedBags.
	 */
    @Column(name="RCVBAG")
	public int getReceivedBags() {
		return receivedBags;
	}

	/**
	 * @param receivedBags The receivedBags to set.
	 */
	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}

	/**
	 * @return Returns the receivedWeight.
	 */
	@Column(name="RCVWGT")
	public double getReceivedWeight() {
		return receivedWeight;
	}

	/**
	 * @param receivedWeight The receivedWeight to set.
	 */
	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
	}






	/**
	 * @return Returns the transferFromCarrier.
	 */
	@Column(name="FRMCARCOD")
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}
	/**
	 * @param transferFromCarrier The transferFromCarrier to set.
	 */
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}
	/**
	 * @return Returns the transferToCarrier.
	 */
	@Column(name="TRFCARCOD")
	public String getTransferToCarrier() {
		return transferToCarrier;
	}
	/**
	 * @param transferToCarrier The transferToCarrier to set.
	 */
	public void setTransferToCarrier(String transferToCarrier) {
		this.transferToCarrier = transferToCarrier;
	}


	/**
	 * @return the releasedFlag
	 */
	@Column(name="RELFLG")
	public String getReleasedFlag() {
		return releasedFlag;
	}
	/**
	 * @param releasedFlag the releasedFlag to set
	 */
	public void setReleasedFlag(String releasedFlag) {
		this.releasedFlag = releasedFlag;
	}



	/**
     * The Constructor
     *
     */
    public ULDForSegment() {
    }
    /**
     *@author A-5991
     * @param uldForSegmentVO
     * @throws SystemException
     */
    public  ULDForSegment(ULDForSegmentVO uldForSegmentVO)
        throws SystemException {
        populatePK(uldForSegmentVO);
        populateAttributes(uldForSegmentVO);
        try {
            PersistenceController.getEntityManager().persist(this);
        } catch(CreateException createException) {
            throw new SystemException(
                    createException.getMessage(), createException);
        }
        if(uldForSegmentVO.getMailbagInULDForSegmentVOs()!=null){
        populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
        }

    }

    /**
     * @author A-5991
     * Populate the attribuete vaules
     * @param uldForSegmentVO
     */
    private void populateAttributes(ULDForSegmentVO uldForSegmentVO) {
       this.numberOfBags = uldForSegmentVO.getNoOfBags();
       //this.weight = uldForSegmentVO.getTotalWeight();
       if(uldForSegmentVO.getTotalWeight()!=null){
       this.weight = uldForSegmentVO.getTotalWeight().getRoundedSystemValue();//added by A-7371
       }
       this.receivedBags=uldForSegmentVO.getReceivedBags();
       //this.receivedWeight=	uldForSegmentVO.getReceivedWeight();
       if(uldForSegmentVO.getReceivedWeight()!=null){
       this.receivedWeight=	uldForSegmentVO.getReceivedWeight().getRoundedSystemValue();//added by A-7371
       }
       setNumberOfBags(uldForSegmentVO.getNoOfBags());
       //setWeight(uldForSegmentVO.getTotalWeight());
       if(uldForSegmentVO.getTotalWeight()!=null){
       setWeight(uldForSegmentVO.getTotalWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
       }
       setWarehouseCode(uldForSegmentVO.getWarehouseCode());
       setLocationCode(uldForSegmentVO.getLocationCode());
       setRemarks(uldForSegmentVO.getRemarks());
       setLastUpdateUser(uldForSegmentVO.getLastUpdateUser());
       setTransferFromCarrier(uldForSegmentVO.getTransferFromCarrier());
       setTransferToCarrier(uldForSegmentVO.getTransferToCarrier());
       if(uldForSegmentVO.getReleasedFlag()!=null){
    	   setReleasedFlag(uldForSegmentVO.getReleasedFlag());
       }else{
    	   setReleasedFlag(MailConstantsVO.FLAG_NO);
       }
    }

    /**
     * @author A-5991
     * Populates the pK values
     * @param uldForSegmentVO
     */
    private void populatePK(ULDForSegmentVO uldForSegmentVO) {
        uldForSegmentPk = new ULDForSegmentPK();
        uldForSegmentPk.setCompanyCode(   uldForSegmentVO.getCompanyCode());
        uldForSegmentPk.setCarrierId(   uldForSegmentVO.getCarrierId());
        uldForSegmentPk.setFlightNumber(   uldForSegmentVO.getFlightNumber());
        uldForSegmentPk.setFlightSequenceNumber(
            uldForSegmentVO.getFlightSequenceNumber());
        uldForSegmentPk.setSegmentSerialNumber(
            uldForSegmentVO.getSegmentSerialNumber());
        uldForSegmentPk.setUldNumber(   uldForSegmentVO.getUldNumber());
    }


    /**
     * @author A-5991
     * This method is used to find the Instance of the Entity
     * @param uldForSegmentPK
     * @return
     * @throws SystemException
     * @throws FinderException
     */
    public static ULDForSegment find(ULDForSegmentPK uldForSegmentPK)
        throws SystemException, FinderException {
        return PersistenceController.getEntityManager().find(
                ULDForSegment.class, uldForSegmentPK);
    }


    /**
     *  A-1739
     * @param mailAcceptanceVO TODO
     * @param containerDetailsVO
     * @param isBulkContainer
     * @param dsnForSegMap
     * @throws SystemException
     */
    public void saveAcceptanceDetails(
            MailAcceptanceVO mailAcceptanceVO, ContainerDetailsVO containerDetailsVO)
        throws SystemException {
    	log.entering("ULDForSegment", "saveAcceptanceDetails");
    	boolean isScanned = mailAcceptanceVO.isScanned();
        Collection<MailbagVO>mailbagVOs=new ArrayList<MailbagVO>();
        Collection<DespatchDetailsVO>despatchDetailsVOs=containerDetailsVO.getDesptachDetailsVOs();
        //IASCB-48967 beg
        OperationalFlightVO operationalFlightVO =null;
        MailController mailController = new MailController();
        LocalDate GHTtime=null;        
        operationalFlightVO=mailController.constructOpFlightFromAcp(mailAcceptanceVO);
        operationalFlightVO.setPou(containerDetailsVO.getPou());
        GHTtime= findGHTForMailbags(operationalFlightVO);
        mailAcceptanceVO.setGHTtime(GHTtime);
        //IASCB-48967 end
        	if(containerDetailsVO.getMailDetails()!=null){
        		mailbagVOs.addAll(containerDetailsVO.getMailDetails());
        	}
            if(despatchDetailsVOs!=null){
            	for(DespatchDetailsVO despatchDetailsVO:despatchDetailsVOs){

            		mailbagVOs.add(MailtrackingDefaultsVOConverter.convertToMailBagVO(despatchDetailsVO));
            	}
            }
            if(mailbagVOs.size()>0){
            	addMailbags(mailbagVOs,mailAcceptanceVO);//IASCB-48967
            }

        log.exiting("ULDForSegment", "saveAcceptanceDetails");
    }






       /**
        * @author A-5991
        * @param mailbagVO
        * @return
        * @throws SystemException
        */

       public MailbagInULDForSegmentPK constructMailbagInULDForSegmentPK(MailbagVO mailbagVO)
    		   throws SystemException{

    	   MailbagInULDForSegmentPK mailbagInULDForSegmentPK=new MailbagInULDForSegmentPK();
    	   uldForSegmentPk = getUldForSegmentPk();
    	   mailbagInULDForSegmentPK.setCarrierId(    uldForSegmentPk.getCarrierId());
    	   mailbagInULDForSegmentPK.setCompanyCode(uldForSegmentPk.getCompanyCode());
    	   mailbagInULDForSegmentPK.setFlightNumber(   uldForSegmentPk.getFlightNumber());
    	   mailbagInULDForSegmentPK.setFlightSequenceNumber( uldForSegmentPk.getFlightSequenceNumber());
    	   mailbagInULDForSegmentPK.setSegmentSerialNumber( uldForSegmentPk.getSegmentSerialNumber());
    	   mailbagInULDForSegmentPK.setUldNumber( uldForSegmentPk.getUldNumber());
    	   mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());//added by A-8353 for ICRD-230449
    	   return mailbagInULDForSegmentPK;
       }

       /**
        * @author A-5991
        * @param mailBagId
        * @param companyCode
        * @return
        * @throws SystemException
        */
       public long findMailSequenceNumber(String mailBagId,String companyCode)
    		   throws SystemException{

    	   return constructDAO().findMailSequenceNumber(mailBagId, companyCode);

       }

       /**
       *@author A-5991
       * @return
       * @throws SystemException
       */
      private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
          try {
              return MailTrackingDefaultsDAO.class.cast(
                      PersistenceController.getEntityManager().getQueryDAO(
                      PRODUCT_NAME));
          } catch (PersistenceException ex) {
              throw new SystemException(ex.getMessage(), ex);
          }
      }




      /**
       * This method adds mailbags to a DSN during acceptance
       * @param mailbagVOs
       * @throws SystemException
       */
      public void addMailbags(Collection<MailbagVO> mailbagVOs,MailAcceptanceVO mailAcceptanceVO)
              throws SystemException {
          log.entering("DSNInULDForSegment", "addMailbagsToDSN");
          if(mailbagVOs.size() > 0) {
              Collection<MailbagInULDForSegmentVO> mailbagInULDVOs =
                  new ArrayList<MailbagInULDForSegmentVO>();
              Collection<MailbagVO> mailbagsAdded = new ArrayList<MailbagVO>();

              for(MailbagVO mailbagVO : mailbagVOs) {
                  if(OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
                	  mailbagVO.setGhttim(mailAcceptanceVO.getGHTtime());//IASCB-48967
                //added as part of IASCB-59355 by A-7815
              	  if(mailAcceptanceVO.isFromDeviationList()) {
              		 MailbagInULDForSegment mailbagInULDForSeg = null;
              		 try {
						mailbagInULDForSeg = findMailbagInULD(constructMailbagInULDForSegmentPK(mailbagVO));
					} catch (FinderException e) { 
						log.log(Log.SEVERE, e.getMessage());
					}
              		if(mailbagInULDForSeg!=null) {
              			mailbagInULDForSeg.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
              			if( MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType()) && mailbagVO.getContainerNumber()!=null && mailbagVO.getContainerNumber().trim().length()>0) {
              			  mailbagInULDForSeg.setContainerNumber(mailbagVO.getContainerNumber());
              			}
              			mailbagInULDForSeg.setAcceptedDate(mailbagVO.getScannedDate());
              			mailbagInULDForSeg.setAcceptedBags(1);	
              			mailbagInULDForSeg.setAcceptedWeight(mailbagVO.getWeight()!=null ?mailbagVO.getWeight().getSystemValue() :0.0);
              			mailbagInULDForSeg.setAcceptedUser(mailAcceptanceVO.getAcceptedUser());
              		 } else {
              			  mailbagInULDVOs.add(constructMailbagForSegment(mailbagVO, false));
              		 }
              	  }else {
              		  mailbagInULDVOs.add(constructMailbagForSegment(mailbagVO, false));
              	  }
                     mailbagsAdded.add(mailbagVO);
                  } else if(OPERATION_FLAG_UPDATE.equals(
                          mailbagVO.getOperationalFlag())) {

                      MailbagInULDForSegment mailbagInULDForSeg = null;
  					try {
  						mailbagInULDForSeg = findMailbagInULD(constructMailbagInULDForSegmentPK(mailbagVO));
  					} catch (FinderException e) {
  						log.log(Log.FINE, e.getMessage());
  					}

                      if(mailbagInULDForSeg!=null){
                  		if(mailbagVO.getDamageFlag()!=null){
                  			 mailbagInULDForSeg.setDamageFlag(
       	                            mailbagVO.getDamageFlag());
            				}
                  		//Added as part of bug ICRD-274236 by A-5526 starts
                  		if(mailbagVO.getSealNumber()!=null){
                  		mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getSealNumber());
                  		}
                  		else{
                  			mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
                  		}
                  	//Added as part of bug ICRD-274236 by A-5526 ends
  	                    //NCACR aded trsfrcarr and editable scandate
  	                    mailbagInULDForSeg.setTransferFromCarrier(
  	                    		mailbagVO.getTransferFromCarrier());
  	                    mailbagInULDForSeg.setScannedDate(
  	                    		mailbagVO.getScannedDate());
  	            		/*if(mailbagVO.getSealNumber() != null &&
  	            				mailbagVO.getSealNumber().trim().length() > 0) {
  							mailbagInULDForSeg.setSealNumber(mailbagVO.getSealNumber());
  						}*/
  	            		if(mailbagVO.getArrivalSealNumber() != null &&
  	            				mailbagVO.getArrivalSealNumber().trim().length() > 0) {
  							mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
                      /*
                       * Added By Karthick V to include the status  for importing the Data
                       * from  the Flown Flights that could be used  be used for the
                       * Mail Revenue Accounting ...
                       * Intially it will be N
                       * Once  MRA Has Imported  the Data the Status will Be 'I'
                       * Once the Update takes place against the Despatch it will be 'U'
                       * Even if the MailBag is Updated  the mrastatus will still remain 'N'
                       */
  						}
                      } else{
                          throw new SystemException("Mailbag not found for update");
                      }
                  }
              }
              populateMailbagDetails(mailbagInULDVOs);
          }
          log.exiting("DSNInULDForSegment", "addMailbagsToDSN");
      }


      /**
       * @author A-5991
       * @param mailbagVO
       * @param isArrival
       * @return
     * @throws SystemException
       */
      private MailbagInULDForSegmentVO constructMailbagForSegment(
              MailbagVO mailbagVO, boolean isArrival) throws SystemException {
          MailbagInULDForSegmentVO mailbagInULDForSegmentVO =
              new MailbagInULDForSegmentVO();
          mailbagInULDForSegmentVO.setMailId(mailbagVO.getMailbagId());
          mailbagInULDForSegmentVO.setDamageFlag(mailbagVO.getDamageFlag());
          mailbagInULDForSegmentVO.setMailSequenceNumber(
        		  findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
          mailbagInULDForSegmentVO.setContainerNumber(
                  mailbagVO.getContainerNumber());
          mailbagInULDForSegmentVO.setScannedDate(
                  mailbagVO.getScannedDate());
          mailbagInULDForSegmentVO.setScannedPort(mailbagVO.getScannedPort());
          mailbagInULDForSegmentVO.setWeight(mailbagVO.getWeight());
          mailbagInULDForSegmentVO.setMailClass(mailbagVO.getMailClass());
          if(!isArrival) {
          	mailbagInULDForSegmentVO.setAcceptanceFlag(
          		MailConstantsVO.FLAG_YES);

          }
          if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())){
        	  mailbagInULDForSegmentVO.setArrivalFlag(MailConstantsVO.FLAG_YES);
          }
          if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())){
        	  mailbagInULDForSegmentVO.setDeliveredFlag(MailConstantsVO.FLAG_YES);
          }
          mailbagInULDForSegmentVO.setArrivalFlag(mailbagVO.getArrivedFlag());
          mailbagInULDForSegmentVO.setDeliveredStatus(
          		mailbagVO.getDeliveredFlag());
          if(mailbagVO.isDeliveryStatusForAutoArrival())// Added by A-8164 for ICRD-333412
        	  mailbagInULDForSegmentVO.setDeliveredStatus(MailConstantsVO.FLAG_YES);
          mailbagInULDForSegmentVO.setTransferFromCarrier(
          		mailbagVO.getTransferFromCarrier());

  		if(mailbagVO.getSealNumber() != null &&
  				mailbagVO.getSealNumber().trim().length() > 0) {
  			mailbagInULDForSegmentVO.setSealNumber(mailbagVO.getSealNumber());
  		}
  		if(mailbagVO.getArrivalSealNumber() != null &&
  				mailbagVO.getArrivalSealNumber().trim().length() > 0) {
  			mailbagInULDForSegmentVO.setArrivalSealNumber(mailbagVO.getArrivalSealNumber());
  		}
          /*
           * Added By Karthick V as the part of the BUg Fix ..While saving mail Axp from Peak
           * the control docnum has to be captured ..added to include the  same ..
           *
           */
          mailbagInULDForSegmentVO.setControlDocumentNumber(mailbagVO.getControlDocumentNumber());


          /*
           * Added By Karthick V to include the status  for importing the Data
           * from  the Flown Flights that could be used  be used for the
           * Mail Revenue Accounting ...
           * Intially it will be N
           * Once  MRA Has Imported  the Data the Status will Be 'I'
           * Once the Update takes place against the Despatch it will be 'U'
           *
           */
          mailbagInULDForSegmentVO.setMraStatus(MailConstantsVO.MRA_STATUS_NEW);
          if(mailbagVO.getMailSequenceNumber()>0){
          mailbagInULDForSegmentVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
          }
          if(mailbagVO.getGhttim() != null){
        	  mailbagInULDForSegmentVO.setGhttim(mailbagVO.getGhttim());
          }
          if(isArrival&&MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())) {
        	  mailbagInULDForSegmentVO.setIsFromTruck(mailbagVO.getIsFromTruck());
			}       
          return mailbagInULDForSegmentVO;
      }



      /**
       * @author A-5991
       * Finds a mailbag in this ULD
       * @param mailbagPK
       * @return the mailbag entity
       * @throws SystemException
       * @throws FinderException
       */
      public static MailbagInULDForSegment findMailbagInULD(
              MailbagInULDForSegmentPK mailbagPK)
      throws SystemException, FinderException {
    	  MailbagInULDForSegment mailInULDSeg = null;
    	  try{
    		  mailInULDSeg= MailbagInULDForSegment.find(mailbagPK);
    	  }catch(Exception exception){
    		  if(mailbagPK.getUldNumber() != null &&
    			  mailbagPK.getUldNumber().contains(MailConstantsVO.CONST_BULK)){
    			  mailbagPK.setUldNumber(MailConstantsVO.CONST_BULK);
    			  mailInULDSeg= MailbagInULDForSegment.find(mailbagPK);
    		  }
    	  }
    	  return mailInULDSeg;
      }


      /**
       *@author A-5991
       * @param mailbags
       * @throws SystemException
       */
      public void populateMailbagDetails(
              Collection<MailbagInULDForSegmentVO> mailbags)
          throws SystemException {
    	  uldForSegmentPk=getUldForSegmentPk();
          if(mailbags != null && mailbags.size() > 0) {
              if(mailBagInULDForSegments == null) {
            	  mailBagInULDForSegments = new HashSet<MailbagInULDForSegment>();
              }
              if(mailbags != null && !mailbags.isEmpty()){
              for(MailbagInULDForSegmentVO mailbag : mailbags) {
            	  mailBagInULDForSegments.add(
                          new MailbagInULDForSegment(uldForSegmentPk, mailbag));
              }
          }
      }
      }

      /**
       *	A-1739
       * @param onwardRoutingForSegmentVOs
       * @throws SystemException
       */
      public void updateOnwardRoutes(
              Collection<OnwardRouteForSegmentVO> onwardRoutingForSegmentVOs)
          throws SystemException {
          if(getOnwardRoutes() == null || onwardRoutes.size() == 0) {
          	//this will happen while acceptance update for first time
              populateOnwardRouteDetails(onwardRoutingForSegmentVOs);
          } else {
          	//for every modification in route thru assign container
              for(OnwardRouteForSegmentVO onwardRouteForSegmentVO : onwardRoutingForSegmentVOs) {
              	if(OPERATION_FLAG_INSERT.equals(
              			onwardRouteForSegmentVO.getOperationFlag())) {
              		new OnwardRouteForSegment(getUldForSegmentPk(),
              				onwardRouteForSegmentVO);
              	} else {
  	                OnwardRouteForSegment onwardRouteForSeg =
  	                    findOnwardRouteForSegment(onwardRoutes,
  	                            onwardRouteForSegmentVO.getRoutingSerialNumber());
  	                if(OPERATION_FLAG_DELETE.equals(
  	                		onwardRouteForSegmentVO.getOperationFlag())) {
  	                	onwardRouteForSeg.remove();
  	                } else {
  	                	onwardRouteForSeg.update(onwardRouteForSegmentVO);
  	                }
              	}
              }
          }
      }


      /**
      *
      * @param onwardRoutings
      * @throws SystemException
      */
     private void populateOnwardRouteDetails(
             Collection<OnwardRouteForSegmentVO> onwardRoutings)
     throws SystemException {
         uldForSegmentPk = getUldForSegmentPk();
         if(onwardRoutings != null && onwardRoutings.size() > 0) {
             if(onwardRoutes == null) {
                 onwardRoutes = new HashSet<OnwardRouteForSegment>();
             }
             for(OnwardRouteForSegmentVO onwardRouteForSegmentVO : onwardRoutings) {
                 onwardRoutes.add(
                         new OnwardRouteForSegment(
                                 uldForSegmentPk, onwardRouteForSegmentVO));
             }
         }
     }


     /**
     *
     * @param onwardRoutesForSeg
     * @param routingSerialNumber
     * @return
     */
     private OnwardRouteForSegment findOnwardRouteForSegment(
             Set<OnwardRouteForSegment> onwardRoutesForSeg,
             int routingSerialNumber) {
         for(OnwardRouteForSegment onwardRouteForSeg : onwardRoutesForSeg) {
             if(onwardRouteForSeg.getOnwardRouteForSegmentPk().getRoutingSerialNumber() ==
                     routingSerialNumber) {
                 return onwardRouteForSeg;
             }
         }
         return null;
     }

     /**
      * This method removes the entity
      *@throws SystemException
      */
     public void remove() throws SystemException {
         removeChildren();
         try {
             PersistenceController.getEntityManager().remove(this);
         } catch(RemoveException removeException) {
             throw new SystemException(removeException.getMessage(),
                     removeException);
         }
     }

     /**
      * This method is used to remove the Instance of the Children
      * @throws SystemException
      */
     private void removeChildren() throws SystemException {
         Collection<MailbagInULDForSegment> mailbagInULDForSegments =
             getMailBagInULDForSegments();
         if(mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0) {
             for(MailbagInULDForSegment mailBagSegment : mailbagInULDForSegments) {
            	 mailBagSegment.remove();
             }
         }
         //remove onwardroutes
         Collection<OnwardRouteForSegment> onwardRoutings = getOnwardRoutes();
         if(onwardRoutings != null && onwardRoutings.size() > 0) {
             for(OnwardRouteForSegment onwardRouting : onwardRoutings) {
                 onwardRouting.remove();
             }
         }
     }

     /**
      * @author A-1739
      * This method is used to reassignMailToFlight
      * @param mailbagsToAdd
      * @param dsnForSegmentMap
      * @param toContainerVO
      * @param dsnAtAirportMap
      * @throws SystemException
      */
     public void reassignMailToFlight(Collection<MailbagVO> mailbagsToAdd,
             ContainerVO toContainerVO) throws SystemException {
         log.entering("ULDForSegment", "reassignMailToFlight");
         if(mailbagsToAdd != null && mailbagsToAdd.size() >0) {
        	 //IASCB-48967 beg
        	 OperationalFlightVO operationalFlightVO =null;
             MailController mailController = new MailController();
             LocalDate GHTtime=null;
             
             operationalFlightVO=mailController.constructOpFlightFromContainer(toContainerVO,true);
             GHTtime = findGHTForMailbags(operationalFlightVO);
        	 //IASCB-48967 end
             if(getMailBagInULDForSegments() == null) {
                 mailBagInULDForSegments= new HashSet<MailbagInULDForSegment>();
             }
             uldForSegmentPk = getUldForSegmentPk();

             for(MailbagVO mailbagVO : mailbagsToAdd) {

                 /*
                  *Added by Karthick V
                  * Like when  we reassign mails To Flight now  it is persisted  the
                  * Accepatance Flag as "Y" for the Reassigned mail
                  * otherwise the MailBagEnquiry wont
                  * retrieve results when we reassign mails to Destination and list
                  * with status Accepted....
                  * since we Query with MAALULDSEG.ACPSTA='Y'..

                  */
            	 mailbagVO.setGhttim(GHTtime);//IASCB-48967
                 mailbagVO.setAcceptanceFlag("Y");
                 mailbagVO.setArrivedFlag("N");
                 mailbagVO.setDeliveredFlag("N");
                 mailbagVO.setMraStatus(MailConstantsVO.FLAG_NO);//Added as part of ICRD-134389

                 mailBagInULDForSegments.add(
                         new MailbagInULDForSegment(uldForSegmentPk,
                                 constructMailbagInULD(mailbagVO, toContainerVO)));
             }



         }
         log.exiting("ULDForSegment", "reassignMailToFlight");
     }

     /**
      *	A-1739
      * @param mailbagVO
      * @param toContainerVO
      * @return
      */
     private MailbagInULDForSegmentVO constructMailbagInULD(
             MailbagVO mailbagVO, ContainerVO toContainerVO) {
         MailbagInULDForSegmentVO mailbagInULDForSeg =
             new MailbagInULDForSegmentVO();
         mailbagInULDForSeg.setMailId(mailbagVO.getMailbagId());
         mailbagInULDForSeg.setAcceptanceFlag(mailbagVO.getAcceptanceFlag());
         mailbagInULDForSeg.setContainerNumber(toContainerVO.getContainerNumber());
         mailbagInULDForSeg.setDamageFlag(mailbagVO.getDamageFlag());

         mailbagInULDForSeg.setScannedDate(mailbagVO.getScannedDate());
         mailbagInULDForSeg.setScannedPort(mailbagVO.getScannedPort());
         mailbagInULDForSeg.setWeight(mailbagVO.getWeight());
         mailbagInULDForSeg.setArrivalFlag(mailbagVO.getArrivedFlag());
         mailbagInULDForSeg.setDeliveredStatus(mailbagVO.getDeliveredFlag());
         mailbagInULDForSeg.setMailClass(mailbagVO.getMailClass());
         if(mailbagVO.getMailSequenceNumber()>0){
        	 mailbagInULDForSeg.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
         }else if(mailbagVO.getMailbagId()!=null){
        	 try {
				mailbagInULDForSeg.setMailSequenceNumber(
					   findMailSequenceNumber(mailbagVO.getMailbagId(),uldForSegmentPk.getCompanyCode()));
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "PersistenceException Caught");
			}
         }
         /*
          * Added By Karthick V as the Transfer From carrier  has to be set from the
          * ReassignedMail Bag Details
          */
         mailbagInULDForSeg.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
         mailbagInULDForSeg.setMraStatus(mailbagVO.getMraStatus());//Added as part of ICRD-134389
         if(mailbagVO.getGhttim()!=null){//IASCB-48967
        	 mailbagInULDForSeg.setGhttim(mailbagVO.getGhttim());
         }
         
         return mailbagInULDForSeg;
     }



     /**
     *
     * @return
     */
    public ULDForSegmentVO retrieveVO() {
        ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
        ULDForSegmentPK uldForSegPk = getUldForSegmentPk();
        uldForSegmentVO.setCompanyCode(uldForSegPk.getCompanyCode());
        uldForSegmentVO.setCarrierId(uldForSegPk.getCarrierId());
        uldForSegmentVO.setFlightNumber(uldForSegPk.getFlightNumber());
        uldForSegmentVO.setFlightSequenceNumber(
                uldForSegPk.getFlightSequenceNumber());
        uldForSegmentVO.setSegmentSerialNumber(
                uldForSegPk.getSegmentSerialNumber());
        uldForSegmentVO.setUldNumber(
                uldForSegPk.getUldNumber());
        uldForSegmentVO.setNoOfBags(getNumberOfBags());
        //uldForSegmentVO.setTotalWeight(getWeight());
        uldForSegmentVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,getWeight()));//added by A-7371
        uldForSegmentVO.setRemarks(getRemarks());
        uldForSegmentVO.setWarehouseCode(getWarehouseCode());
        uldForSegmentVO.setLocationCode(getLocationCode());
        uldForSegmentVO.setTransferFromCarrier(getTransferFromCarrier());
        uldForSegmentVO.setTransferToCarrier(getTransferToCarrier());


        //get dsnInULDs
        Collection<MailbagInULDForSegment> mailbagInULDForSegments = getMailBagInULDForSegments();
        if(mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0) {
            Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs =
                new ArrayList<MailbagInULDForSegmentVO>();
            for(MailbagInULDForSegment mailbagInULDForSegment : mailbagInULDForSegments) {
            	mailbagInULDForSegmentVOs.add(mailbagInULDForSegment.retrieveVO());
            }
            uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagInULDForSegmentVOs);
        }

        //get Onward routing
        Collection<OnwardRouteForSegment> onwardRoutings = getOnwardRoutes();
        if(onwardRoutings != null && onwardRoutings.size() > 0) {
            Collection<OnwardRouteForSegmentVO> onwardRouteVOs =
                new ArrayList<OnwardRouteForSegmentVO>();
            for(OnwardRouteForSegment onwardRouting : onwardRoutings) {
                onwardRouteVOs.add(onwardRouting.retrieveVO());
            }
            uldForSegmentVO.setOnwardRoutings(onwardRouteVOs);
        }

        return uldForSegmentVO;
    }



    /**
     * This method detaches a DSN from the assigned dest/flight
     * to the dest/flight to which it has to be assined. Since a DSN can
     * span multiple BULK containers, a DSN is completely detached only if
     * it is in a single container only. In the case where a ULD spans
     * multiple containers a replica of this DSN in
     * returned with only that container and the mailbags in them.
     *  A-1739
     * @param containerNumber
     * @return
     * @throws SystemException
     */
//    public  Collection<DSNInULDForSegmentVO> reassignBulkContainer(String containerNumber)
//            throws SystemException {
//        log.entering("ULDForSEgment", "findAndRemoveContainer");
//
//        Collection<DSNInULDForSegmentVO> dsnsToReassign =
//            new ArrayList<DSNInULDForSegmentVO>();
//        Collection<MailbagInULDForSegment> mailbagsInULD =
//        		getMailBagInULDForSegments();
//            Collection<MailbagInULDForSegmentVO> mailbagsToMove =
//                new ArrayList<MailbagInULDForSegmentVO>();
//            DSNInULDForSegmentVO dsnInULDForSegToMove =new DSNInULDForSegmentVO();
//            if(mailbagsInULD != null && mailbagsInULD.size () > 0) {
//                for(MailbagInULDForSegment mailbagInULD : mailbagsInULD) {
//                    if(containerNumber.equals(mailbagInULD.getContainerNumber())) {
//                        mailbagsToMove.add(mailbagInULD.retrieveVO());
//                        mailbagInULD.remove();
//                    }
//                }
//            }
//            dsnInULDForSegToMove.setMailBags(mailbagsToMove);
//            dsnsToReassign.add(dsnInULDForSegToMove);
//        log.exiting("ULDForSEgment", "findAndRemoveContainer");
//        return dsnsToReassign;
//    }
    public  Collection<MailbagInULDForSegmentVO> reassignBulkContainer(String containerNumber)
            throws SystemException {
        log.entering("ULDForSEgment", "findAndRemoveContainer");

Collection<MailbagInULDForSegmentVO> dsnsToReassign =
                new ArrayList<MailbagInULDForSegmentVO>();

Set<MailbagInULDForSegment> dsnInULDForSegs =
    getMailBagInULDForSegments();

Collection<MailbagInULDForSegment> dsnsToDeassign =
    new ArrayList<MailbagInULDForSegment>();



for(MailbagInULDForSegment dsnInULDForSeg : dsnInULDForSegs) {
	 if(containerNumber.equals(dsnInULDForSeg.getContainerNumber())) {
		 dsnsToReassign.add(dsnInULDForSeg.retrieveVO());
		 dsnInULDForSeg.remove();
     }
}



if(dsnsToDeassign.size() > 0) {
    dsnInULDForSegs.removeAll(dsnsToDeassign);
}
        log.exiting("ULDForSEgment", "findAndRemoveContainer");
        return dsnsToReassign;
    }


    /**
    *
    * @param dsnsToReassign
    * @throws SystemException
    */
    public void assignBulkContainer( Collection<MailbagInULDForSegmentVO> dsnInULDForSegmentVOs)
    throws SystemException {
    		ULDForSegmentPK  uldForSegmentPK=getUldForSegmentPk();
    	  if(getMailBagInULDForSegments() == null) {
              mailBagInULDForSegments = new HashSet<MailbagInULDForSegment>();
          }
    	  if(dsnInULDForSegmentVOs!=null && dsnInULDForSegmentVOs.size()>0){
    		  for(MailbagInULDForSegmentVO dsnInULDForSegmentVO:dsnInULDForSegmentVOs){
    			  //Collection<MailbagInULDForSegmentVO>mailbagSegToReassign=dsnInULDForSegmentVO.getMailBags();
    			  if(dsnInULDForSegmentVO!=null ){
    				 // for(MailbagInULDForSegmentVO mailbagVO : mailbagSegToReassign) {
    		        	  mailBagInULDForSegments.add(
    		                      new MailbagInULDForSegment(uldForSegmentPK, dsnInULDForSegmentVO));
    		          //}
    			  }
    		  }
    	  }

    }


    /**
     * @author a-1936
     * This method is used to reassign the mails From the Flight to Destiantion
     *
     *  The Block does the following
     *  1.Group the Mailbags based on differentDSns say
     *  D1-U1-F1-seg1
     *  D2-U1-F1-seg1
     *  D3-U1-F1-seg1
     *
     *  2.Group the DSNS to update the DSNS at segment  finally from AssignedFlightSegment
     *    say
     *    D1-F1-seg1
     *    D2-F1-seg1
     *    D3-F1-seg1
     *    D1-F2-seg1
     *    D2-F1-seg2
     * @param mailbagVOs
     * @param dsnForSegmentMap
     * @param isInbound TODO
     * @throws SystemException
     */
    public void reassignMailFromFlight(Collection<MailbagVO> mailbagVOs,
    		    boolean isInbound)
        throws SystemException {

        Map<MailbagInULDForSegmentPK, Collection<MailbagVO>> mailbagMap =
            groupDSNMailbags(mailbagVOs);

        MailbagInULDForSegment mailbagInULDForSegment = null;
          for(Map.Entry<MailbagInULDForSegmentPK, Collection<MailbagVO>> dsnMailbag : mailbagMap.entrySet()) {
        	  MailbagInULDForSegmentPK mailbagInULDForSegmentPK = dsnMailbag.getKey();
        	  mailbagInULDForSegment = findDSNInULDForSeg(mailbagInULDForSegmentPK);
              if(mailbagInULDForSegment == null) {
            	  throw new SystemException("NO mail bags FOR SEG ");
              }

              Collection<MailbagVO> mailbags = dsnMailbag.getValue();
              int noOfbags = mailbags.size();
              double bagWeight = calculateWeightofbags(mailbags);

              if(isInbound) {
            	  mailbagInULDForSegment.setRecievedBags(
            			  mailbagInULDForSegment.getRecievedBags() - noOfbags);
            	  mailbagInULDForSegment.setRecievedWeight(
            			  mailbagInULDForSegment.getRecievedWeight() - bagWeight);
              } else {
            	  mailbagInULDForSegment.setAcceptedBags(
            			  mailbagInULDForSegment.getAcceptedBags() - noOfbags);
            	  mailbagInULDForSegment.setAcceptedWeight(
            			  mailbagInULDForSegment.getAcceptedWeight() - bagWeight);
              }

              mailbagInULDForSegment.reassignMailFromFlight(mailbags, isInbound);
              /*if (mailbagInULDForSegment.getAcceptedBags() == 0
            		  && mailbagInULDForSegment.getRecievedBags() == 0) {*A-8061 commneted for negetive pcs weight issue/
            	 /* updateDSNForSegmentMap( mailbagInULDForSegment,
                          0, 0, 0, 0, isInbound);*/
            	  log.log(Log.FINE, " removing bulk container because wt 0");
              //Added for icrd-128801.To remove the dsnuldforseg from the collection
            	  this.mailBagInULDForSegments.remove(mailbagInULDForSegment);
            	  mailbagInULDForSegment.remove();
              //}
          }
    }



    /**
     *	A-1739
     * @param mailbags
     * @return
     */
    private double calculateWeightofbags(Collection<MailbagVO> mailbags) {
        double totalWeight = 0;
        for(MailbagVO mailbagVO : mailbags) {
            totalWeight += mailbagVO.getWeight().getSystemValue();//added by A-7371
        }
        return totalWeight;
    }

    /**
     *	A-1739
     * @param dsnInULDForSegments2
     * @param dsnULDPK
     * @return
     */
    private MailbagInULDForSegment findDSNInULDForSeg(MailbagInULDForSegmentPK dsnULDPK) {
    	mailBagInULDForSegments = getMailBagInULDForSegments();
        if(mailBagInULDForSegments != null) {
        	log.log(Log.FINE, "dsnInULDForSegments.size", mailBagInULDForSegments.size());
    		for(MailbagInULDForSegment mailbagInULDForSegment : mailBagInULDForSegments) {
            	if(mailbagInULDForSegment.getMailbagInULDForSegmentPK().equals(dsnULDPK)){
                    return mailbagInULDForSegment;
                }
            }
        }
        return null;
    }
    /**
     *	A-1739
     * @param mailbagVOs
     * @return
     */
    private Map<MailbagInULDForSegmentPK, Collection<MailbagVO>> groupDSNMailbags(
            Collection<MailbagVO> mailbagVOs) {
        Map<MailbagInULDForSegmentPK, Collection<MailbagVO>> dsnForULDSegMap =
        new HashMap<MailbagInULDForSegmentPK, Collection<MailbagVO>>();
        for(MailbagVO mailbagVo :mailbagVOs){
        	if(mailbagVo.getMailSequenceNumber()==0 && mailbagVo.getMailbagId()!=null){
        		try {
					mailbagVo.setMailSequenceNumber(findMailSequenceNumber(mailbagVo.getMailbagId(),mailbagVo.getCompanyCode()));
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "SystemException Caught");
				}
        	}
        	MailbagInULDForSegmentPK dsnForULDSegmentPK =
                  constructDSNInULDForSegPKFromMail(mailbagVo);
              Collection<MailbagVO> mailbagsOfDSN =
                  dsnForULDSegMap.get(dsnForULDSegmentPK);
              if(mailbagsOfDSN == null){
                 mailbagsOfDSN = new ArrayList<MailbagVO>();
                dsnForULDSegMap.put(dsnForULDSegmentPK, mailbagsOfDSN);
           }
             mailbagsOfDSN.add(mailbagVo);
         }
        return dsnForULDSegMap;
    }



    /**
     * @author A-1936
     * This method is used to reassign the DsNs From Flight
     * @param despatchDetailVos
     * @param dsnForSegmentMap
     * @param isInbound TODO
     * @throws SystemException
     */
    public  void  reassignDSNsFromFlight( Collection<DespatchDetailsVO> despatchDetailVos,
    		boolean isInbound)  throws SystemException{
    	  log.entering("ULDForSegment","reassignDSNFromFlight");

    		if (despatchDetailVos != null && despatchDetailVos.size() > 0) {
    			for (DespatchDetailsVO despatchDetailsVO : despatchDetailVos) {
    				MailbagVO mailbagVO  = constructMailbagInULDAtAirportvoFromDespatch(despatchDetailsVO);
    				Collection<MailbagVO> mailbagVOs=findMailBagForDespatch(mailbagVO);
    				if(mailbagVOs!=null && mailbagVOs.size()>0){
    					for(MailbagVO mailbagvo : mailbagVOs ){
    					MailbagInULDForSegmentPK mailbagInULDForSegmentPK = constructMailbagInULDAtAirportPKFromDespatch(mailbagvo);
    					MailbagInULDForSegment mailbagInULDForSegment = null;
    				try {
    					mailbagInULDForSegment = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
    				} catch (FinderException ex) {
    					log.log(Log.FINE, ex.getMessage());
    				}

    				if(mailbagInULDForSegment!=null){
    					mailbagInULDForSegment.remove();
    				}
    			}
    		}
    			}

    		}
    	 log.exiting("ULDForSegment","reassignDSNFromFlight");

    }
	/**
	 * @author  This method is used to construct the
	 *         MailbagInULDAtAirportPK
	 * @param mailbagVo
	 * @return
	 * @throws SystemException
	 */
	private MailbagVO constructMailbagInULDAtAirportvoFromDespatch(
			DespatchDetailsVO despatchDetailsVO) throws SystemException {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getUldForSegmentPk()
				.getCompanyCode());
		mailbagVO
				.setUldNumber(getUldForSegmentPk().getUldNumber());
		mailbagVO.setScannedPort(despatchDetailsVO
				.getAirportCode());
		mailbagVO
				.setCarrierId(getUldForSegmentPk().getCarrierId());
		mailbagVO.setDespatchId(
				createDespatchBag(despatchDetailsVO));
		// Added to Include the DSN PK

		return mailbagVO;

	}
	public static String createDespatchBag(DespatchDetailsVO despatchDetailsVO) {
		StringBuilder dsnid = new StringBuilder();
		dsnid.append(despatchDetailsVO.getOriginOfficeOfExchange())
				.append(despatchDetailsVO.getDestinationOfficeOfExchange())
				.append(despatchDetailsVO.getMailCategoryCode())
				.append(despatchDetailsVO.getMailSubclass())
				.append(despatchDetailsVO.getYear())
				.append(despatchDetailsVO.getDsn());
		return dsnid.toString();
	}

	/**
	 * @author A-5991
	 * @param mailBagId
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO)
			throws SystemException {
		Collection<MailbagVO> mailbagVos=null;
		try {
			mailbagVos= constructDAO().findMailBagForDespatch(mailbagVO);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return mailbagVos;

	}
    /**
     * @author a-1936
     * This method is used to construct the MailbagInULDAtAirportPK
     * @param mailbagVo
     * @return
     * @throws SystemException
     */
    private MailbagInULDForSegmentPK  constructMailbagInULDAtAirportPKFromDespatch(MailbagVO mailbagvo) throws SystemException{

    	MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
    	mailbagInULDForSegmentPK.setCompanyCode(getUldForSegmentPk().getCompanyCode() );
    	mailbagInULDForSegmentPK.setUldNumber(getUldForSegmentPk().getUldNumber());
    	mailbagInULDForSegmentPK.setFlightNumber(getUldForSegmentPk().getFlightNumber());
    	 mailbagInULDForSegmentPK.setCarrierId(getUldForSegmentPk().getCarrierId());
    	 mailbagInULDForSegmentPK.setFlightSequenceNumber(getUldForSegmentPk().getFlightSequenceNumber());
    	 mailbagInULDForSegmentPK.setSegmentSerialNumber(getUldForSegmentPk().getSegmentSerialNumber());
    	 mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
    	 //Added to Include the DSN PK

    	 return  mailbagInULDForSegmentPK;

    	 }

    public static String createMailBag(DespatchDetailsVO despatchDetailsVO){
    	StringBuilder mailBagid=new StringBuilder();
    	mailBagid.append(despatchDetailsVO.getOriginOfficeOfExchange())
    	.append(despatchDetailsVO.getDestinationOfficeOfExchange())
    	.append(despatchDetailsVO.getMailCategoryCode())
    	.append(despatchDetailsVO.getMailSubclass())
    	.append(despatchDetailsVO.getYear())
    	.append(despatchDetailsVO.getDsn());
    	return mailBagid.toString();
    }

    /**
     * @author a-1936
     * This method is used to construct the DSNInULDForSegmentPK from mailBagVO..
     * @param mailbagVO
     * @return
     */
    private MailbagInULDForSegmentPK constructDSNInULDForSegPKFromMail(
            MailbagVO mailbagVO){
    	MailbagInULDForSegmentPK dsnInULDForSegmentPK =
            new MailbagInULDForSegmentPK();
        uldForSegmentPk = getUldForSegmentPk();
         dsnInULDForSegmentPK.setCompanyCode(
             uldForSegmentPk.getCompanyCode());
         dsnInULDForSegmentPK.setCarrierId(   uldForSegmentPk.getCarrierId());
         dsnInULDForSegmentPK.setFlightNumber(   uldForSegmentPk.getFlightNumber());
         dsnInULDForSegmentPK.setFlightSequenceNumber(
             uldForSegmentPk.getFlightSequenceNumber());
         dsnInULDForSegmentPK.setSegmentSerialNumber(
             uldForSegmentPk.getSegmentSerialNumber());
         dsnInULDForSegmentPK.setUldNumber(   uldForSegmentPk.getUldNumber());
         dsnInULDForSegmentPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
    /*     dsnInULDForSegmentPK.setDsn(
        	 		mailbagVO.getDespatchSerialNumber());
         dsnInULDForSegmentPK.setOriginOfficeOfExchange(
        	mailbagVO.getOoe());
         dsnInULDForSegmentPK.setDestinationOfficeOfExchange(
        	mailbagVO.getDoe());
         //Added to include the DSN PK
         dsnInULDForSegmentPK.setMailSubclass(   mailbagVO.getMailSubclass());
         dsnInULDForSegmentPK.setMailCategoryCode(   mailbagVO.getMailCategoryCode());
         dsnInULDForSegmentPK.setYear(   mailbagVO.getYear());*/

         return dsnInULDForSegmentPK;
    }

    /**
     * @param dSNInULDForSegmentVOs
     * @param dsnSegmentMap
     * @throws SystemException
     */
	public void saveOutboundDetailsForTransfer(
			Collection<DSNInULDForSegmentVO> dSNInULDForSegmentVOs,ContainerVO containerVO)throws SystemException {
		log.entering("ULDForSegment", "saveOutboundDetailsForTransfer");
		int bags = 0;
		double totalWgt = 0;

		if(dSNInULDForSegmentVOs != null && dSNInULDForSegmentVOs.size()>0){
		for (DSNInULDForSegmentVO dSNInULDForSegmentVO : dSNInULDForSegmentVOs) {
		Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs=dSNInULDForSegmentVO.getMailBags();
			if (mailbagInULDForSegmentVOs != null
					&& mailbagInULDForSegmentVOs.size() > 0) {
				for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : mailbagInULDForSegmentVOs) {
					mailbagInULDForSegmentVO.setGhttim(containerVO.getGHTtime());//IASCB-48967
					MailbagInULDForSegmentPK mailbagInULDForSegmentPK= constructMailbagInULDPK(uldForSegmentPk, mailbagInULDForSegmentVO);
					if (findMailbagInULDForSeg(mailbagInULDForSegmentPK) == null) {
						new MailbagInULDForSegment(uldForSegmentPk,
								mailbagInULDForSegmentVO);
					}
				}
			}
		}
		}

		log.exiting("ULDForSegment", "saveOutboundDetailsForTransfer");
	}


/**
 * @author A-5991
 * @param mailbagInULDForSegmentPK
 * @return
 */
private MailbagInULDForSegment findMailbagInULDForSeg(MailbagInULDForSegmentPK mailbagInULDForSegmentPK){
	if(mailBagInULDForSegments != null && mailBagInULDForSegments.size() > 0) {
		for(MailbagInULDForSegment mailbagInULD : mailBagInULDForSegments) {
			if(mailbagInULD.getMailbagInULDForSegmentPK().equals(
					mailbagInULDForSegmentPK)) {
				return mailbagInULD;
			}
		}
	}
	return null;
}

/**
 * @author A-5991
 * @param uldForSegmentPK
 * @param mailbagInULDForSegmentVO
 * @return
 * @throws SystemException
 */
private MailbagInULDForSegmentPK constructMailbagInULDPK(
        ULDForSegmentPK uldForSegmentPK,
        MailbagInULDForSegmentVO mailbagInULDForSegmentVO) throws SystemException {
    MailbagInULDForSegmentPK mailbagInULDForSegmentPK =
        new MailbagInULDForSegmentPK();
    mailbagInULDForSegmentPK.setCompanyCode(   uldForSegmentPK.getCompanyCode());
    mailbagInULDForSegmentPK.setCarrierId(   uldForSegmentPK.getCarrierId());
    mailbagInULDForSegmentPK.setFlightNumber(   uldForSegmentPK.getFlightNumber());
    mailbagInULDForSegmentPK.setFlightSequenceNumber(
        uldForSegmentPK.getFlightSequenceNumber());
    mailbagInULDForSegmentPK.setSegmentSerialNumber(
        uldForSegmentPK.getSegmentSerialNumber());
    mailbagInULDForSegmentPK.setUldNumber(   uldForSegmentPK.getUldNumber());
    mailbagInULDForSegmentPK.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagInULDForSegmentVO.getMailId(), uldForSegmentPK.getCompanyCode()));
    return mailbagInULDForSegmentPK;
}


/**
 * @author a-1883
 * @param dSNForSegmentMap
 * @param dSNMap
 * @param isArrived
 * @param toFlightVO TODO
 * @throws SystemException
 */
public void saveArrivalDetailsForTransfer(
        Map<MailbagPK,MailbagVO> mailMap,boolean isArrived,
        OperationalFlightVO toFlightVO) throws SystemException {
    log.entering("ULDForSegment","saveArrivalDetailsForTransfer");
    Collection<MailbagInULDForSegment> mailbagInULDForSegments =
        this.getMailBagInULDForSegments();
   if( mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0 ){
        for(MailbagInULDForSegment mailbagInULDForSegment:
        	mailbagInULDForSegments){


	         MailbagPK mailbagPK = constructMailbagPKFromSeg(mailbagInULDForSegment);
	         MailbagVO mailbagVO = mailMap.get(mailbagPK);
	         if(mailbagVO == null){
	        	 mailbagVO = constructMailBagVOFrmSeg(mailbagInULDForSegment);
	        	 mailMap.put(mailbagPK,mailbagVO);
	          }
	         mailbagInULDForSegment.setArrivalFlag(MailConstantsVO.FLAG_YES);
	         if(!isArrived){
	             mailbagInULDForSegment.setTransferFlag(MailConstantsVO.FLAG_YES);
	         } 
	             mailbagInULDForSegment.setTransferToCarrier(
	            		 toFlightVO.getCarrierCode());
	             mailbagInULDForSegment.setRecievedBags(1);
	             mailbagInULDForSegment.setRecievedWeight(mailbagInULDForSegment.getAcceptedWeight());
        }
    }
    log.exiting("ULDForSegment","saveArrivalDetailsForTransfer");
}

	/**
	 * @author A-5991
	 * @param mailbagInULDForSegment
	 * @return
	 */
	private MailbagPK constructMailbagPKFromSeg(MailbagInULDForSegment mailbagInULDForSegment){
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode(mailbagInULDForSegment.getMailbagInULDForSegmentPK().getCompanyCode());
		mailbagPK.setMailSequenceNumber(mailbagInULDForSegment.getMailbagInULDForSegmentPK().getMailSequenceNumber());
		return mailbagPK;

	}


	private MailbagVO constructMailBagVOFrmSeg(MailbagInULDForSegment mailbagInULDForSegment){
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setCompanyCode(mailbagInULDForSegment.getMailbagInULDForSegmentPK().getCompanyCode());
		mailbagVO.setMailSequenceNumber(mailbagInULDForSegment.getMailbagInULDForSegmentPK().getMailSequenceNumber());
		return mailbagVO;
	}



    /**
     * updateUldAcquittalStatus
     * @throws SystemException
     */
    public void updateUldAcquittalStatus()
    throws SystemException{
    	log.entering("ULDForSegment", "updateUldAcquittalStatus");
    	mailBagInULDForSegments = getMailBagInULDForSegments();
    	boolean acquitULD = false;
    	if(mailBagInULDForSegments != null && mailBagInULDForSegments.size() > 0){
    		for(MailbagInULDForSegment mailbagInULDForSegment : mailBagInULDForSegments){
    			if(MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegment.getArrivalFlag())){
    				acquitULD = true;
    				break;
    			}
    		}
    	}
    	//Case to handle empty ULD's starts
    	if(getNumberOfBags()==0&&getWeight()==0.0
    			&&getReceivedBags()==0&&getReceivedWeight()==0.0){
    		acquitULD=true;
    	}
    	//Case to handle empty ULD's ends
    	if(acquitULD && !MailConstantsVO.FLAG_YES.equals(getReleasedFlag())){
    		setReleasedFlag(MailConstantsVO.FLAG_YES);
    		log.log(Log.FINE, "ALL DSNs ARRIVED & ULD SUCCESSFULLY RELEASED FROM FLIGHT. !!!!!!!");
    	}

    	log.exiting("ULDForSegment", "updateUldAcquittalStatus");
    }



	/**
     * @param mailbagVOs
     * @param dSNForSegmentMap
     * @param dsnAtAirportMap
	 * @param containerVO
     * @throws SystemException
     */
       public void saveMailArrivalDetailsForTransfer(
               Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) throws SystemException {
           log.entering("DSNInULDForSegment", "saveMailArrivalDetailsForTransfer");
           MailbagInULDForSegment mailbagInULDForSegment =null;
           try{
               for(MailbagVO mailbagVO :mailbagVOs){
   	            MailbagInULDForSegmentPK mailbagInULDForSegmentPK =
   	            		constructMailbagInULDForSegmentPK(mailbagVO);
   	             mailbagInULDForSegment = MailbagInULDForSegment.
   	                         find(mailbagInULDForSegmentPK);
   	             mailbagInULDForSegment.setArrivalFlag(MailConstantsVO.FLAG_YES);
   	             mailbagInULDForSegment.setTransferFlag(MailConstantsVO.FLAG_YES);
   	             mailbagInULDForSegment.setTransferToCarrier(
   	            		containerVO.getCarrierCode());
   	             mailbagInULDForSegment.setRecievedBags(1);
   	             if(mailbagVO.getWeight()!=null){//added by A-7371
   	             mailbagInULDForSegment.setRecievedWeight(mailbagVO.getWeight().getSystemValue());
   	             }
               }
           } catch(FinderException finderException) {
           throw new SystemException("Mailbag in Dsn not found", finderException);
           }
           log.exiting("DSNInULDForSegment", "saveMailArrivalDetailsForTransfer");
       }


       /**
        * @param mailbagVOs
        * @param containerVO
        * @param dsnSegmentMap
        * @throws SystemException
        */
        public void saveOutboundMailDetailsForTransfer(Collection<MailbagVO>
            mailbagVOs,ContainerVO containerVO)
        throws SystemException {
        	//IASCB-48967 beg
        	OperationalFlightVO operationalFlightVO =null;
            MailController mailController = new MailController();
            LocalDate GHTtime=null;
            operationalFlightVO=mailController.constructOpFlightFromContainer(containerVO,true);
            GHTtime=findGHTForMailbags(operationalFlightVO);
        	//IASCB-48967 end
        	for(MailbagVO mailbagVO:mailbagVOs){
        		
        		mailbagVO.setGhttim(GHTtime);//IASCB-48967
         	   MailbagInULDForSegmentVO mailbagULDSegVO =
         		   constructMailbagInULD(mailbagVO, containerVO);
         	   mailbagULDSegVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
         	   mailbagULDSegVO.setArrivalFlag(MailConstantsVO.FLAG_NO);
         	   mailbagULDSegVO.setDeliveredStatus(MailConstantsVO.FLAG_NO);
         	   mailbagULDSegVO.setTransferFlag(MailConstantsVO.FLAG_NO);
         	   mailbagULDSegVO.setMraStatus(MailConstantsVO.FLAG_NO);//Added as part of ICRD-134389
         	    if(mailbagVO.getTransferFromCarrier()!=null&&
                      mailbagVO.getTransferFromCarrier().trim().length()>0){
         		 mailbagULDSegVO.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
                  }
                  else{
               	   if(!containerVO.isFoundTransfer()){
               		mailbagULDSegVO.setTransferFromCarrier(mailbagVO.getCarrierCode()); 
               	   }
                  }
         	  boolean uldSegDtlFound = false;
			if (mailbagVO.isFromDeviationList()) {
				// to handle found arrival/delivery with flight details, entry will be exists in
				// MALULDSEGDTL
				// added as part of IASCB-82531
				MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
				mailbagInULDForSegmentPK.setCompanyCode(uldForSegmentPk.getCompanyCode());
				mailbagInULDForSegmentPK.setCarrierId(uldForSegmentPk.getCarrierId());
				mailbagInULDForSegmentPK.setFlightNumber(uldForSegmentPk.getFlightNumber());
				mailbagInULDForSegmentPK.setFlightSequenceNumber(uldForSegmentPk.getFlightSequenceNumber());
				mailbagInULDForSegmentPK.setSegmentSerialNumber(uldForSegmentPk.getSegmentSerialNumber());
				mailbagInULDForSegmentPK.setUldNumber(uldForSegmentPk.getUldNumber());
				mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagULDSegVO.getMailSequenceNumber());
				try {
					MailbagInULDForSegment foundUldSeg = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
					if (foundUldSeg != null) {
						uldSegDtlFound = true;
						foundUldSeg.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
						foundUldSeg.setAcceptedDate(mailbagVO.getScannedDate());
						foundUldSeg.setAcceptedBags(1);
						foundUldSeg.setAcceptedWeight(
								mailbagVO.getWeight() != null ? mailbagVO.getWeight().getSystemValue() : 0.0);
						foundUldSeg.setAcceptedUser(mailbagVO.getScannedUser());
						if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())
								&& mailbagVO.getContainerNumber() != null
								&& mailbagVO.getContainerNumber().trim().length() > 0) {
							foundUldSeg.setContainerNumber(mailbagVO.getContainerNumber());
						}
					}
				} catch (FinderException e) {
					uldSegDtlFound = false;
				}
			}
         	if(!uldSegDtlFound) {
                new MailbagInULDForSegment(uldForSegmentPk,
                        mailbagULDSegVO);
         	   }
            }


            log.exiting("DSNInULDForSegment","saveOutboundMailDetailsForTransfer");

           }


      //Added by A-5945 for ICRD-118205 starts (For updating entries in MTKDSNULDSEG)
        /**
         *	A-5945
         * @param Collection<MailbagVO>
         * @return
         */
       /* public void undoArriveContainer(ContainerDetailsVO containerdetailsvo,Collection<MailbagVO> mailbagsforUndoArrival) throws SystemException{
        	 log.entering("ULDForSegment", "undoArriveContainer");
        	 DSNInULDForSegment dsnInULDForSegment = null;
        	 for(MailbagVO mailbag : mailbagsforUndoArrival){
        		 if( MailConstantsVO.FLAG_YES.equals(mailbag.getUndoArrivalFlag())){
        			 DSNInULDForSegmentPK dsnForULDSegmentPK =
        	                 constructDSNInULDForSegPKFromMail(mailbag);
        			 try{
        			 dsnInULDForSegment =  DSNInULDForSegment.find(dsnForULDSegmentPK);
        			 }catch(FinderException e){
        			 }
        			 if(dsnInULDForSegment!=null){
        				 //Either container or DSN is selected . Hence updating MTKDSNULDSEG
        				//Modified the code  as part of bug ICRD-149146 by A-5526
        					if("DSN".equals(containerdetailsvo.getUndoArrivalFlag()) || "CON".equals(containerdetailsvo.getUndoArrivalFlag())){
        				 dsnInULDForSegment.setReceivedBags(
          	                      dsnInULDForSegment.getReceivedBags() - 1);
                   		 dsnInULDForSegment.setReceivedWeight(
          	                      dsnInULDForSegment.getReceivedWeight() - mailbag.getWeight());
        			 }
        			 }
        			 if (dsnInULDForSegment.getAcceptedBags() == 0 && dsnInULDForSegment.getReceivedBags()==0) {
                    	 dsnInULDForSegment.remove();//found DSN,hence removing
                   	  log.log(Log.FINE, " removing found dsn ");
                     }
        		 }
        	 }
        	 dsnInULDForSegment.undoArriveContainer(mailbagsforUndoArrival);
        	 log.exiting("ULDForSegment", "undoArriveContainer");
        }      */
        //Added by A-5945 for ICRD-118205 ends
        /**
         * @author A-1739
         * @param containerDetailsVO
         * @param dsnForSegmentMap
         * @param mailArrivalVO TODO
         * @throws SystemException
         * @throws DuplicateMailBagsException
         */
            public void saveArrivalDetails(ContainerDetailsVO containerDetailsVO,
                                        MailArrivalVO mailArrivalVO,
        			   Map<String,Collection<MailbagVO>>         mailBagsMapForInventory,
        			   Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory)
                    throws SystemException, DuplicateMailBagsException {
                log.entering("ULDForSegment", "saveArrivalDetails");
                boolean isScanned = mailArrivalVO.isScanned();
                Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
                Collection<MailbagVO> mailbagVOs=containerDetailsVO.getMailDetails();
                Collection<MailbagVO> updatedMailbagVOs=new ArrayList<MailbagVO>();
                if(mailbagVOs!= null && mailbagVOs.size()>0){
                	for(MailbagVO mailbagVO:mailbagVOs){
                    	/*mailbagVO.setFlightNumber(mailArrivalVO.getFlightNumber());
                    	mailbagVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
                    	mailbagVO.setCarrierId(mailArrivalVO.getCarrierId());
                    	mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
                    	mailbagVO.setDespatchId(createDespatchBag(mailbagVO));
                    	mailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
                    	if(mailArrivalVO.getScanDate()!=null){
                    		mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
                    	}*/
            		if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())
            			||MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())){
                    	mailbagVO.setFlightNumber(mailArrivalVO.getFlightNumber());
                    	mailbagVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
                    	mailbagVO.setCarrierId(mailArrivalVO.getCarrierId());
                    	mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
                    	mailbagVO.setDespatchId(createDespatchBag(mailbagVO));
                    	mailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
                    	mailbagVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());//Added for ICRD-243421
                    	if(MailConstantsVO.FLAG_YES.equals(mailArrivalVO.getPaBuiltFlag())){
                    	mailbagVO.setPaBuiltFlag(mailArrivalVO.getPaBuiltFlag());
                    	}
                    	//Added by A-7540 as part of ICRD-265287 for stamping PACODE in MALMST table
                    	
                    	String paCode = null;
						String companyCode = mailbagVO.getCompanyCode();
						String originOfFiceExchange = mailbagVO.getOoe();
						if(mailbagVO.getPaCode() == null){
							try{
								paCode = new MailController().findPAForOfficeOfExchange(companyCode,originOfFiceExchange);
							}catch (SystemException e) {
								
							}
							
							mailbagVO.setPaCode(paCode);
						}
                    	if(mailArrivalVO.getScanDate()!=null){
                    		mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
                    	}
                    	if(MailConstantsVO.FLAG_YES.equals(mailArrivalVO.getIsFromTruck())) {
                    	   mailbagVO.setIsFromTruck(mailArrivalVO.getIsFromTruck());
                    	}
            			updatedMailbagVOs.add(mailbagVO);
            			}
                	}
                	saveArrivedMailbags(updatedMailbagVOs,mailBagsMapForInventory);
                }
                log.exiting("ULDForSegment", "saveArrivalDetails");
            }


        	public static String createDespatchBag(MailbagVO mailbagVO) {
				StringBuilder dsnid = new StringBuilder();
				dsnid.append(mailbagVO.getOoe())
						.append(mailbagVO.getDoe())
						.append(mailbagVO.getMailCategoryCode())
						.append(mailbagVO.getMailSubclass())
						.append(mailbagVO.getYear())
						.append(mailbagVO.getDespatchSerialNumber());
				return dsnid.toString();
			}

    		/**
    		 * @author A-1936
    		 * Added By Karthick V as the part of the Nca Mail Tracking CR
    		 * This method is used to mark the Inventory Mail Bags to be  mail Bag as Delivered .
    		 * @param mailBags
    		 * @throws SystemException
    		 */
    	    public  void deliverMailBagsFromInventory(Collection<MailbagVO> mailBags)
    		 throws SystemException{
    	    	 MailbagInULDForSegment mailbagInULDForSegment = null;
    	    	 for(MailbagVO mailbagVO:mailBags){
    	    		 mailbagInULDForSegment=findMailBagsInUldForSegment(mailbagVO);
    	    		 mailbagInULDForSegment.setDeliveredStatus(MailConstantsVO.FLAG_YES);
    	    	 }

    	    }

    	    /**
    	     * @author A-1936
    	     * Addedd By Karthick V as the part of the NCA Mail Tracking Cr
    	     * This methos is used to return the MailBaginUld Instance
    	     * @param mailBag
    	     * @return
    	     */
    	    private MailbagInULDForSegment findMailBagsInUldForSegment (MailbagVO mailBag){
    	    	MailbagInULDForSegment  mailBaginUld= null;
    	    	for(MailbagInULDForSegment mailInUld : getMailBagInULDForSegments()){
    	    	 if(mailInUld.getMailbagInULDForSegmentPK().getMailSequenceNumber()==(mailBag.getMailSequenceNumber())
    	    			 && mailInUld.getMailbagInULDForSegmentPK().getCompanyCode().equals(mailBag.getCompanyCode())){
    	    		 mailBaginUld=mailInUld;
    	    	 }
    	    	}
    	    	return mailBaginUld;
    	     }



    	    /**
    		 * @author a-1883
    		 * @param mailbagVOs
    		 * @throws SystemException
    		 */
    		public void updateDamageDetails(Collection<MailbagVO> mailbagVOs)
    				throws SystemException {
    			log.entering("ULDForSegment", "updateDamageDetails");
    			for (MailbagVO mailbagVO : mailbagVOs) {
    				updateMailbagInULDForSegment(mailbagVO);
    			}
    			log.exiting("ULDForSegment", "updateDamageDetails");
    		}

    		/**
    		 * @author a-1883
    		 * @param mailbagVO
    		 * @throws SystemException
    		 */
    		private void updateMailbagInULDForSegment(MailbagVO mailbagVO)
    				throws SystemException {

    			log.entering("DSNInULDForSegment", "updateMailbagInULDForSegment");
    			MailbagInULDForSegmentPK mailbagInULDForSegmentPk = new MailbagInULDForSegmentPK();
    			mailbagInULDForSegmentPk.setCarrierId(mailbagVO.getCarrierId());
    			mailbagInULDForSegmentPk.setCompanyCode(mailbagVO.getCompanyCode());
    			mailbagInULDForSegmentPk.setFlightNumber(mailbagVO.getFlightNumber());
    			mailbagInULDForSegmentPk.setFlightSequenceNumber(mailbagVO
    					.getFlightSequenceNumber());
    			mailbagInULDForSegmentPk.setMailSequenceNumber(findMailSequenceNumber(
    					mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
    			mailbagInULDForSegmentPk.setSegmentSerialNumber(mailbagVO
    					.getSegmentSerialNumber());
    			// Added By Karthick V to construct the BULK_POU formation based on the
    			// Container Type ..
    			mailbagInULDForSegmentPk.setUldNumber(mailbagVO.getContainerNumber());
    			if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
    				log.log(Log.INFO, "THE MAL BAG IS  ASSOCIATED WITH A BARROW");
    				/*mailbagInULDForSegmentPk
    						.setUldNumber(constructBulkULDNumber(mailbagVO
    								.getFinalDestination()));*/
    				/*
    				 * The below If check mailbagVO.isFromReturnPopUp() is added by Manish
    				 * as part of ICRD-196881
    				 */
    				if(mailbagVO.isFromReturnPopUp()){
    					String uldNum = new StringBuilder().append(MailConstantsVO.CONST_BULK)
    					.append(MailConstantsVO.SEPARATOR).append(mailbagVO.getPou()).toString();
    					mailbagInULDForSegmentPk.setUldNumber(uldNum);
    				}else
    				{
    				mailbagInULDForSegmentPk.setUldNumber(mailbagVO.getUldNumber());
    				}
    				log.log(Log.INFO, "THE MAL BAG IS  ASSOCIATED WITH A BARROW",
    						mailbagInULDForSegmentPk.getUldNumber());
    			}

    			try {
    				MailbagInULDForSegment mailbagInULDForSegment = MailbagInULDForSegment
    						.find(mailbagInULDForSegmentPk);
    				mailbagInULDForSegment.setDamageFlag(MailbagVO.FLAG_YES);
    			} catch (FinderException finderException) {
    				log.log(Log.SEVERE, " MailbagInULDForSegment Not existing ");
    				throw new SystemException(finderException.getMessage(),
    						finderException);
    			}
    			log.exiting("DSNInULDForSegment", "updateMailbagInULDForSegment");

    		}

    		/**
    		 * A-1739
    		 *
    		 * @param pou
    		 * @return
    		 */
    		private String constructBulkULDNumber(String pou) {
    			return new StringBuilder().append(MailConstantsVO.CONST_BULK)
    					.append(MailConstantsVO.SEPARATOR).append(pou).toString();
    		}



    	    /**
    	     * @author a-1936
    	     * This method is used to find the Containers and the assocaiated dsns in the Flight..
    	     * @param operationalFlightVo
    	     * @return
    	     * @throws SystemException
    	     */
    	    public static  MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo)
    		   throws SystemException{
    	    	Log logger = LogFactory.getLogger("Mail Tracking Defaults");
    	    	logger.entering("UldForSegment", "findContainersInFlight");
    	    	try{
    	    		return constructDAO().findContainersInFlightForManifest(operationalFlightVo);
    	    	}catch(PersistenceException ex ){
    	    		throw new SystemException(ex.getMessage());
    	    	}
    	    }

    	    /**
    	     * @author a-1936
    	     * This method  is used to  fetch the Mail Bags and the  Despacthes in the Contianer for a Flight
    	     * @param containers
    	     * @return
    	     * @throws SystemException
    	     */

    	 public static    Collection<ContainerDetailsVO> findMailbagsInContainerForManifest(Collection<ContainerDetailsVO> containers)
    		  throws SystemException{
    		 Log loggerlocal = LogFactory.getLogger("Mail Tracking Defaults");
    		 loggerlocal.entering("UldForSegment", "findContainersInFlight");
    	 	try{
    	 		return constructDAO().findMailbagsInContainerForManifest(containers);
    	 	}catch(PersistenceException ex ){
    	 		throw new SystemException(ex.getMessage());
    	 	}
    	 }



 	    /**
 	     *	A-1739
 	     * @param opFlightVO
 	     * @return
 	     * @throws SystemException
 	     * @throws PersistenceException
 	     */
 	    public static Collection<ContainerDetailsVO> findArrivedContainers(
 	    		MailArrivalFilterVO mailArrivalFilterVO)
 	    throws SystemException {
 	        try {
 	            return constructDAO().findArrivedContainers(mailArrivalFilterVO);
 	        } catch(PersistenceException exception ) {
 	            throw new SystemException(exception.getMessage(), exception);
 	        }
 	    }


 	    /**
 	     * @author a-1936
 	     * Added By Karthick V ..
 	     * @param mailBagsMapForInventory
 	     * @param mailBag
 	     */
 	    private void   addMailBagToInventoryMap(
 	    		Map<String,Collection<MailbagVO>>     mailBagsMapForInventory,MailbagVO mailBag){
 	    	log.entering("DSNINULD", "addMailBagToInventoryMap");

 	    	String containerKey = null;
 	    	if(mailBag.getContainerForInventory()!=null){
 	    		containerKey = new StringBuilder().append(
 	    			mailBag.getContainerForInventory()).
 	    			append(MailConstantsVO.ARPULD_KEYSEP).
 	    			append(mailBag.getContainerTypeAtAirport()).
 	    			append(MailConstantsVO.ARPULD_KEYSEP).
 	    			append(mailBag.getPaBuiltFlag()).toString();
 	    	} else {

 	    		if(mailBag.getContainerType()==null || mailBag.getContainerType().length()==0){
 					if(getUldForSegmentPk().getUldNumber().startsWith("BULK")){
 						mailBag.setContainerType(MailConstantsVO.BULK_TYPE);
 					}
 					else{
 						mailBag.setContainerType(MailConstantsVO.ULD_TYPE);
 					}
 				}

 	    		if(MailConstantsVO.BULK_TYPE.equals(mailBag.getContainerType())) {
 					String container = MailConstantsVO.CONST_BULK_ARR_ARP
 					.concat(MailConstantsVO.SEPARATOR)
 					.concat(mailBag.getCarrierCode());
 	    			containerKey = new StringBuilder().append(container).
 											append(MailConstantsVO.ARPULD_KEYSEP).
 											append(mailBag.getContainerType()).toString();
 	    		} else {
 		    		containerKey = new StringBuilder().append(mailBag.getContainerNumber()!= null ? mailBag.getContainerNumber() :getUldForSegmentPk().getUldNumber() ).
 					append(MailConstantsVO.ARPULD_KEYSEP).
 					append(mailBag.getContainerType()).
 					append(MailConstantsVO.ARPULD_KEYSEP).toString();
 	    		}
 	    	}
 	    	Collection<MailbagVO> mailBags=mailBagsMapForInventory.get(containerKey);
 	    	if(mailBags==null){
 	    		mailBags= new ArrayList<MailbagVO>();
 	    		mailBagsMapForInventory.put(containerKey, mailBags);
 	    	}
 	    	mailBags.add(mailBag);
 	    	log.exiting("DSNINULD", "addMailBagToInventoryMap");
 	    }


 	/**
 	 * @author A-5991
 	 * @param mailbagVOs
 	 * @param mailBagsMapForInventory
 	 * @throws SystemException
 	 * @throws DuplicateMailBagsException
 	 */
 	    public void saveArrivedMailbags(Collection<MailbagVO> mailbagVOs,
 				   Map<String,Collection<MailbagVO>>     mailBagsMapForInventory)
 	    throws SystemException, DuplicateMailBagsException {
 	        log.entering("DSNInULDForSegment", "saveArrivedMailbags");
 	        if(mailbagVOs.size() > 0) {
 	            Collection<MailbagInULDForSegmentVO> mailbagInULDVOs =
 	                new ArrayList<MailbagInULDForSegmentVO>();

 	            for(MailbagVO mailbagVO : mailbagVOs) {
 	            	/*
 	            	 * Added By Karthick V ..
 	            	 * For all the  MailBags that has Been Arrived and not yet delivered or Transferred ..
 	            	 * These MailBags has to be added to the Inventory
 	            	 *
 	            	 *
 	            	 */
 	            	
	 	            // Added for ICRD-255189 starts
	 	   			mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
	 	   			//Added for ICRD-255189 ends
 	   			
 	            	if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag()) &&
 	            			!(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag()) ||
 	            					MailConstantsVO.FLAG_YES.equals(mailbagVO.getTransferFlag()))){
 	            		addMailBagToInventoryMap(mailBagsMapForInventory,mailbagVO);
 	            	}
 	            	MailbagPK mailbagPK=constructMailbagPK(mailbagVO);
             		Mailbag mailbag=null;
             		try {
						mailbag=Mailbag.findMailbagDetails(mailbagPK);/*A-9619 changed to specific as part of IASCB-55196*/
					} catch (FinderException e1) {
						// TODO Auto-generated catch block

					}
             	
 	                    //Modified for icrd-126626
 	                    MailbagInULDForSegment mailbagInULDForSeg = null;
 						try {
 							mailbagInULDForSeg = findMailbagInULD(constructMailbagPKFrmMail(mailbagVO));
 						} catch (FinderException e) {
					if (mailbag!=null && mailbag.getPou() !=null && MailConstantsVO.BULK_TYPE.equals(mailbag.getContainerType())
							&& (mailbagVO.getUldNumber() == null || mailbagVO.getUldNumber().isEmpty())) {
						mailbagVO.setUldNumber("BULK-" + mailbag.getPou());
						try {
							mailbagInULDForSeg = findMailbagInULD(constructMailbagPKFrmMail(mailbagVO));
							mailbagVO.setUldNumber(null);       
						} catch (FinderException e1) {
							log.log(Log.SEVERE, e1);
						}
					}
 						}

 	                    /*
 	                     * Added By Karthick V to stamp the Damages for the Mail Bag
 	                     *
 	                     *
 	                     */

 	                    log
 								.log(Log.FINE, " To find The Damage Flag is ",
 										mailbagVO);
 						if(mailbagInULDForSeg!=null &&
 								(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDamageFlag())
 								||MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
 								||MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag()))){
 	                    mailbagInULDForSeg.setDamageFlag(mailbagVO.getDamageFlag());
 	                    //damageflag alone to be update
 	                    mailbagInULDForSeg.setArrivalFlag(
 	                            mailbagVO.getArrivedFlag());
 	                    mailbagInULDForSeg.setDeliveredStatus(
 	                        mailbagVO.getDeliveredFlag());
 	                    if(mailbagVO.isDeliveryStatusForAutoArrival())// Added by A-8164 for ICRD-333412
 	                    	mailbagInULDForSeg.setDeliveredStatus(MailConstantsVO.FLAG_YES);  
 	                    if(!MailConstantsVO.IMPORT.equals(mailbagInULDForSeg.getMraStatus())){
 	                    mailbagInULDForSeg.setMraStatus(MailConstantsVO.FLAG_NO);
 	                    }

 	                    /** START - added by roopak for bug : 12358
 	                     * to stamp the correct scanned date.*/
 	                    if(mailbagVO.getScannedDate()!=null){
 	                    mailbagInULDForSeg.setScannedDate(mailbagVO.getScannedDate());
 	                    }
 	                    if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())){
 	                    	mailbagInULDForSeg.setRecievedBags(1);
 	                    	if(mailbagVO.getWeight()!=null){//added by A-7371
 	                    	mailbagInULDForSeg.setRecievedWeight(mailbagVO.getWeight().getSystemValue());
 	                    	}
 	                    }

 	                    if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())){
 	                    	mailbagInULDForSeg.setDeliveredBags(1);
 	                    	if(mailbagVO.getWeight()!=null){//added by A-7371
 	                    	mailbagInULDForSeg.setDeliveredWeight(mailbagVO.getWeight().getSystemValue());
 	                    	}
 	                    }

 	                    /*
 	                     * Seal Number
 	                     */
 	            		/*if(mailbagVO.getSealNumber() != null &&
 	            				mailbagVO.getSealNumber().trim().length() > 0) {
 							mailbagInULDForSeg.setSealNumber(mailbagVO.getSealNumber());
 						}*/
 	            		if(mailbagVO.getArrivalSealNumber() != null &&
 	            				mailbagVO.getArrivalSealNumber().trim().length() > 0) {
 							mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
 						}

 	                    /** END - added by roopak for bug : 12358*/
 	                    } else {
 	                    	if(OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {


 	                    		   boolean isDuplicate = false;
 	                    			if (OPERATION_FLAG_INSERT.equals(
 	       								mailbagVO.getOperationalFlag())) {
 	                    				if(getUldForSegmentPk()!=null){
 	                    				mailbagVO.setSegmentSerialNumber(getUldForSegmentPk().getSegmentSerialNumber());
 	                    				}
 	       								if(mailbag!=null) {
 	       									if(!(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) && MailConstantsVO.OPERATION_INBOUND.equals(
 	       											mailbag.getOperationalStatus()) &&
 	       											mailbag.getScannedPort().equals(
 	       														mailbagVO.getScannedPort())) {
 	       									// Added by A-8353 for ICRD-230449 starts
 	       			                        isDuplicate = new MailController().checkForDuplicateMailbag(mailbagVO.getCompanyCode(),mailbagVO.getPaCode(),mailbag);	
 	       			                     // Added by A-8353 for ICRD-230449 ends
 	       									/*	throw new DuplicateMailBagsException(
 	       												DuplicateMailBagsException.// commented by A-8353 for ICRD-230449
 	       												DUPLICATEMAILBAGS_EXCEPTION,
 	       												new Object[] { mailbagVO.getMailbagId() });*/
 	       			                   if(mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber()) && !isDuplicate){
 	       										throw new DuplicateMailBagsException(
 	       												DuplicateMailBagsException.
 	       												DUPLICATEMAILBAGS_EXCEPTION,
 	       			            			    new Object[] {mailbagVO.getMailbagId()});
 	       									}
 	       									}
 	       									
 	       									
 	       									else{
 	       									mailbagVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
 	 	       								  mailbagInULDVOs.add(
 	 	       	                            		constructMailbagForSegment(mailbagVO, true));
 	       									}
 	       								}
 	       							if(mailbag == null || isDuplicate){
 	       								//Added for ICRD-243469 starts
 	       								String paCode = null;
 										String companyCode = mailbagVO.getCompanyCode();
 										String originOfFiceExchange = mailbagVO.getOoe();
 										if(mailbagVO.getPaCode() == null){
 											try{
 												paCode = new MailController().findPAForOfficeOfExchange(companyCode,originOfFiceExchange);
 											}catch (SystemException e) {
 												log.log(Log.SEVERE, " PACode Not found ");
 											}
 											
 											mailbagVO.setPaCode(paCode);
 										}
 										/*String serviceLevel = null;
 										serviceLevel = findMailServiceLevel(mailbagVO);
 										
 										if(serviceLevel!=null){
 											mailbagVO.setMailServiceLevel(serviceLevel);
 										}*/
 								     mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());	// Added by A-8353 for ICRD-230449
 	       								//Added for ICRD-243469 ends
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
 									
 									mailbag = Mailbag.getInstance().persistMailbag(mailbagVO);/*A-9619 as part of IASCB-55196*/
 	                    			mailbagVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
 	       							//Added by A-7540 for ICRD-331539 starts
 	                    			if((mailbagVO.getAcceptanceFlag() != null || MailConstantsVO.FLAG_NO.equals(mailbagVO.getAcceptanceFlag()))
											&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())){	 
										OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
										operationalFlightVo.setCompanyCode(mailbagVO.getCompanyCode());
										operationalFlightVo.setCarrierId(mailbagVO.getCarrierId());
										operationalFlightVo.setFlightNumber(mailbagVO.getFlightNumber());
										operationalFlightVo.setFlightSequenceNumber(mailbagVO
												.getFlightSequenceNumber());
										operationalFlightVo.setPol(mailbagVO.getPol());
										operationalFlightVo.setPou(mailbagVO.getPou());
										mailbagVO.setPou(mailbagVO.getPou());
										operationalFlightVo.setAirportCode(mailbagVO.getScannedPort());
										operationalFlightVo.setLegSerialNumber(mailbagVO.getLegSerialNumber());
										LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,mailbagVO.getFlightDate(),true);
										operationalFlightVo.setFlightDate(flightDate);
										operationalFlightVo.setCarrierCode(mailbagVO.getCarrierCode());
										 Collection<FlightValidationVO> flightValidationVOs = null;
									      flightValidationVOs = new FlightOperationsProxy()
					                    .validateFlightForAirport(MailtrackingDefaultsVOConverter.constructFlightFilterVOForContainer(mailbagVO));
									      if(flightValidationVOs != null){
									      for (FlightValidationVO flightValidationVO : flightValidationVOs) {
					 			            if (flightValidationVO.getFlightSequenceNumber() == mailbagVO
									                    .getFlightSequenceNumber()) {
									            	if(flightValidationVO!=null && flightValidationVO.getAta()!=null){
									            	 operationalFlightVo.setArrivaltime(flightValidationVO.getAta());
									            	 updateGHTForMailbags(operationalFlightVo, mailbagVO);
									            	}
									            }
									        }	
									      }
 	                    		}
 	                    			//Added by A-7540 for ICRD-331539 ends

 	                    			MailbagInULDForSegmentVO  mailbagInULD = constructMailbagForSegment(mailbagVO, true);
 	                    			if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())) {
 	                    				mailbagInULD.setIsFromTruck(mailbagVO.getIsFromTruck());
 	                    			}
 	       							mailbagInULDVOs.add(mailbagInULD);
 	       								}
 	       						}




 	                        } else if(OPERATION_FLAG_UPDATE.equals(
 	                                mailbagVO.getOperationalFlag())) {
 	                        	/*if(mailbagInULDForSeg==null){//Commented for ICRD-329802
 	                        	throw new SystemException("Mailbag not found for update" +
 	                                mailbagVO.getMailbagId());
 	                        	}*/
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
 	   							mailbagVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
								if (mailbagVO.getMailRemarks() == null
										|| mailbagVO.getMailRemarks().trim().isEmpty()) {
									mailbagVO.setMailRemarks(mailbag.getMailRemarks());
								}
 	   							mailbag.updateArrivalDetails(mailbagVO);
 	   							}catch(Exception exception){
 	   								log.log(Log.FINE,"Exception in MailController at initiateArrivalForFlights for Offline *Flight* with Mailbag "+mailbagVO);
 	   								continue;
 	   							}

 	   							/*
 	   							 * Updating Consignment Details for mailbag
 	   							 */
 	   							/*DocumentController docController = new DocumentController();
 	   							MailInConsignmentVO mailInConsignmentVO = null;
 	   							mailInConsignmentVO = docController
 	   									.findConsignmentDetailsForMailbag(mailbagVO
 	   											.getCompanyCode(), mailbagVO
 	   											.getMailbagId(),null);*/
 	   						MailInConsignmentVO mailInConsignmentVO = mailbagVO.getMailConsignmentVO();
 	   							if (mailInConsignmentVO != null) {
 	   							//	mailbag.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
 	   							//	mailbag.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
 	   							//	mailbag.setPaCode(mailInConsignmentVO.getPaCode());
 	   							}

 	                    		mailbagVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());


 	                            //updateFlightDetails of Mailbag
 	                            updateFlightDetailsofNewMailbag(mailbagVO);
	   						}

                        //Added by A-7371 as part of ICRD-240340

 	   						//A-8061 commented for ICRD-277436 begin
 	   						
 	   						if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())){ 
 	   					try {
 	   						if(mailbagInULDForSeg!=null){ //Changed by A-8164 for ICRD-319194 
 	   							if(new MailController().isUSPSMailbag(mailbagVO)){
 	   							updatemailperformanceDetails(mailbagVO,mailbagInULDForSeg,mailbag);     
 	   							}
 	   						}
							//Modified as part of bug ICRD-304515 by A-5526
								
						} catch (PersistenceException e) {
							// TODO Auto-generated catch block
							log.log(log.SEVERE, e.getMessage());
						}
 	           }


 	   			Transaction tx = null;
				boolean success = false;
 	   					try {
	 						TransactionProvider tm = PersistenceController
	 								.getTransactionProvider();
	 						tx = tm.getNewTransaction(false);						
	 				    success=true;
	 					}finally {
	 						if(success){ 
	 							tx.commit();    
						}
					}


 	   					//A-8061 commented for ICRD-277436 end ,please uncomment and use for dev purpose dont check in the changes before closing MSMs of ICRD-212544.
 		            }
 	           populateMailbagDetails(mailbagInULDVOs);
 	           // Code added by Manish for IASCB-65099 start
 	          for(MailbagVO mailbagVO : mailbagVOs) {
 	        	  if(OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag()) && 
 	        			  MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag()) &&
 	        			  	new MailController().isUSPSMailbag(mailbagVO)){ 
 	        		 	MailbagPK mailbagPK=constructMailbagPK(mailbagVO);
 	        		 	Mailbag mailbag=null;
 	        		 	MailbagInULDForSegment mailbagInULDForSeg = null;
 	   					try {
 	   						mailbag=Mailbag.find(mailbagPK);
 	   						mailbagInULDForSeg = findMailbagInULD(constructMailbagPKFrmMail(mailbagVO));
 	   						if(mailbag != null && mailbagInULDForSeg != null){  
   								updatemailperformanceDetails(mailbagVO,mailbagInULDForSeg,mailbag);     
 	   						}

						} catch (Exception e) {
							log.log(Log.SEVERE,"exception raised", e  );
							
						}
 	        	  	}
 	          }
 	          //Code added by Manish for IASCB-65099 start end
 	        }

 	        log.exiting("DSNInULDForSegment", "saveArrivedMailbags");
 	    }


 	    /**
 	     *	A-1739
 	     * @param mailbagVO
 	     * @return
 	     * 
 	     */
 	    private MailbagInULDForSegmentPK constructMailbagPKFrmMail(
 	            MailbagVO mailbagVO){
 	    
 	        MailbagInULDForSegmentPK mailbagInULDPK = new MailbagInULDForSegmentPK();

 	        mailbagInULDPK.setCompanyCode(mailbagVO.getCompanyCode());
 	        mailbagInULDPK.setCarrierId(mailbagVO.getCarrierId());
 	        mailbagInULDPK.setFlightNumber(mailbagVO.getFlightNumber());
 	        mailbagInULDPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
 	        mailbagInULDPK.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
 	        if(mailbagVO.getUldNumber()!=null){
 	        	mailbagInULDPK.setUldNumber(mailbagVO.getUldNumber());
 	        }

 	        else{
 	        	mailbagInULDPK.setUldNumber(mailbagVO.getContainerNumber());
 	        }
 	        if(mailbagVO.getMailSequenceNumber()<1){
 	        	try {
						mailbagInULDPK.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
					} catch (SystemException e) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE, "SystemException Caught");
					}
 	        }
 	        else{
 	        	mailbagInULDPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
 	        }
 	        return mailbagInULDPK;
 	    }



 		private void updateFlightDetailsofNewMailbag(MailbagVO mailbagVO) {
 			uldForSegmentPk = getUldForSegmentPk();
 			mailbagVO.setCompanyCode(uldForSegmentPk.getCompanyCode());
 			mailbagVO.setCarrierId(uldForSegmentPk.getCarrierId());
 			mailbagVO.setFlightNumber(uldForSegmentPk.getFlightNumber());
 			mailbagVO.setFlightSequenceNumber(
 					uldForSegmentPk.getFlightSequenceNumber());
 			mailbagVO.setFlightNumber(uldForSegmentPk.getFlightNumber());
 			mailbagVO.setSegmentSerialNumber(
 					uldForSegmentPk.getSegmentSerialNumber());
 		}

 		public MailbagPK constructMailbagPK(MailbagVO  mailbagVO) throws SystemException{
 			MailbagPK  mailbagPK=new MailbagPK();
 			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
 			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber()>0?mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
 			return mailbagPK;
 		}


 		 /**
 		  @author a-1936
 			 * Added By Karthick V as the  part of  the Air NewZealand CR...
 			 * This method is used to find all  the DSNs and the mail bags Required For the Impport Manifest Report..
 		  * @param operationalFlightVo
 		  * @return
 		  */
 			public static  MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo)
 			 throws SystemException{
 				 Log loger = LogFactory.getLogger("Mail Tracking Defaults");
 				 loger.entering("UldForSegment", "findDSNsForImportManifestReport");
 			 	try{
 			 		return constructDAO().findImportManifestDetails(operationalFlightVo);
 			 	}catch(PersistenceException ex ){
 			 		throw new SystemException(ex.getMessage());
 			 	}
 			}
 		   /**
 		    * @author A-1883
 		    * @param containerDetailsVO
 		    * @param shipmentValidationVO
 		    * @param shipmentPrefix
 		    * @throws SystemException
 		    */
 		        public static void attachAWBForDSN(ContainerDetailsVO containerDetailsVO,
 		        		ShipmentValidationVO shipmentValidationVO,String shipmentPrefix)
 		        		throws SystemException {
 		        	//Collection<DSNVO> dSNVOs = containerDetailsVO.getDsnVOs();
 		        	Collection<MailbagVO>mailbagVOs = containerDetailsVO.getMailDetails();
 		     		if(mailbagVOs != null && mailbagVOs.size() > 0 ){
 		     		for(MailbagVO mailbagVO:mailbagVOs){
/* 		     			DSNInULDForSegmentVO dSNInULDForSegmentVO = new DSNInULDForSegmentVO();
 		     			dSNInULDForSegmentVO.setStatedBags(dSNVO.getBags());
 		     			dSNInULDForSegmentVO.setStatedWeight(dSNVO.getWeight());*/
 		     			MailbagInULDForSegmentPK mailbagInULDForSegmentPK  = new MailbagInULDForSegmentPK();
 		     			mailbagInULDForSegmentPK.setCompanyCode(mailbagVO.getCarrierCode());
 		     			mailbagInULDForSegmentPK.setCarrierId(mailbagVO.getCarrierId());
 		     			mailbagInULDForSegmentPK.setFlightNumber(mailbagVO.getFlightNumber());
 		     			mailbagInULDForSegmentPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
 		     			mailbagInULDForSegmentPK.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
 		     			mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
 		     			mailbagInULDForSegmentPK.setUldNumber(mailbagVO.getUldNumber());
 		     			updateAWBDetails(mailbagInULDForSegmentPK,shipmentValidationVO,shipmentPrefix,mailbagVO.getLastUpdateTime());
 		     		}
 		         }
 		        }
 		        /**
 		         * @author A-1883
 		         * @param dSNInULDForSegmentPK
 		         * @param shipmentValidationVO
 		         * @param shipmentPrefix
 		         * @throws SystemException
 		         */
 		        private static void updateAWBDetails(MailbagInULDForSegmentPK mailbagInULDForSegmentPK,
 		        		ShipmentValidationVO  shipmentValidationVO,String shipmentPrefix,LocalDate lastUpdateTime )
 		        throws SystemException {
 		        	try{
 		        		MailbagInULDForSegment mailbagInULDForSegment = MailbagInULDForSegment.
 		     		        find(mailbagInULDForSegmentPK);
 		     		/*
 		     		 * Added By Karthick V as the part of Optimsitic Locking Mechanism
 		     		 *
 		     		 *
 		     		 */
 		        		mailbagInULDForSegment.setLastUpdateTime(lastUpdateTime);
 		        		mailbagInULDForSegment.updateAWBDetails(shipmentValidationVO,shipmentPrefix);
 		        	}catch(FinderException finderException){
 		        		throw new SystemException(finderException.getErrorCode());
 		        	}
 		        }
 		   	/**
 		   	 * @author a-1936
 		   	 * This method is used to find out the other DSns for the Same AWb excluding the One
 		   	 * passed as the Filter
 		   	 * @param dsnVo
 		   	 * @param containerDetailsVO
 		   	 * @return
 		   	 * @throws SystemException
 		   	 */
 		   	public static  Collection<DespatchDetailsVO> findOtherDSNsForSameAWB(DSNVO dsnVo
 		   			 ,ContainerDetailsVO containerDetailsVO)
 		   			 throws SystemException{
 		   		return MailbagInULDForSegment.findOtherDSNsForSameAWB(dsnVo,containerDetailsVO);
 		   	}
 	       /**
 	         *
 	         * @param dsnVOs
 	         * @param shipmentValidationVO
 	         * @param operationalFlightVO
 	         * @param shipmentPrefix
 	         * @throws SystemException
 	         */
 	        public static void autoAttachAWBDetailsForDSN(Collection<DSNVO> dsnVOs,
 	        		ShipmentValidationVO shipmentValidationVO,
 	        		OperationalFlightVO operationalFlightVO,String shipmentPrefix)
 	        		throws SystemException {
 	     		for(DSNVO dSNVO:dsnVOs){
 	     			DSNInULDForSegmentVO dSNInULDForSegmentVO = new DSNInULDForSegmentVO();
 	     			dSNInULDForSegmentVO.setStatedBags(dSNVO.getBags());
 	     			dSNInULDForSegmentVO.setStatedWeight(dSNVO.getWeight());
		     			MailbagInULDForSegmentPK mailbagInULDForSegmentPK  = new MailbagInULDForSegmentPK();
		     			mailbagInULDForSegmentPK.setCompanyCode(dSNVO.getCarrierCode());
		     			mailbagInULDForSegmentPK.setCarrierId(dSNVO.getCarrierId());
		     			mailbagInULDForSegmentPK.setFlightNumber(dSNVO.getFlightNumber());
		     			mailbagInULDForSegmentPK.setFlightSequenceNumber(dSNVO.getFlightSequenceNumber());
		     			mailbagInULDForSegmentPK.setSegmentSerialNumber(dSNVO.getSegmentSerialNumber());
		     			mailbagInULDForSegmentPK.setMailSequenceNumber(dSNVO.getMailSequenceNumber());
		     			mailbagInULDForSegmentPK.setUldNumber(dSNVO.getContainerNumber());
		     			updateAWBDetails(mailbagInULDForSegmentPK,shipmentValidationVO,shipmentPrefix,dSNVO.getDsnUldSegLastUpdateTime());
 			 	}
 			}

	 	       /**
	 	        * @author a-1936
	 	        * This method  is used to  fetch the Mail Bags and the  Despacthes in the Contianer for a Flight
	 	        * @param containers
	 	        * @return
	 	        * @throws SystemException
	 	        */

	 	      public static Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
	 	      	  throws SystemException{
	 	      	 Log loggerlocal = LogFactory.getLogger("Mail Operations");
	 	      	 loggerlocal.entering("UldForSegment", "findContainersInFlight");
	 	      	try{
	 	      		return constructDAO().findMailbagsInContainerForImportManifest(containers);
	 	      	}catch(PersistenceException ex ){
	 	      		throw new SystemException(ex.getMessage());
	 	      	}
	 	      }
					
					
					
					
						
						
							
						
						
						
						
						
						
						
				
				private String findSystemParameterValue(String syspar)
						throws SystemException {
					String sysparValue = null;
					ArrayList<String> systemParameters = new ArrayList<String>();
					systemParameters.add(syspar);
					HashMap<String, String> systemParameterMap = new SharedDefaultsProxy()
							.findSystemParameterByCodes(systemParameters);
					log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
					if (systemParameterMap != null) {
						sysparValue = systemParameterMap.get(syspar);
					}
					return sysparValue;
				}
					
				/**
				 * @author A-7371
				 * @param mailbag
				 * @param mailbagInULDForSeg
				 * @throws SystemException 
				 * @throws PersistenceException 
				 */
				 public void updatemailperformanceDetails(MailbagVO mailbagVO, MailbagInULDForSegment mailbagInULDForSeg, Mailbag mailbag) throws PersistenceException, SystemException {
				
							updateServiceResponsiveness(mailbagVO,mailbagInULDForSeg);
							
							String paCode_int =null;
							try {
								paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
							} catch (SystemException e) {
							}
							if(mailbag.getPaCode().equals(paCode_int)){
							updateTransportationWindowStatus(mailbag, mailbagVO);//Added by A-8464 for ICRD-240360 
							}							
							//Added for ICRD-243421 starts
							if(!(MailConstantsVO.FLAG_YES.equals(mailbagVO.getOnTimeDelivery()) && MailConstantsVO.FLAG_YES.equals(mailbagVO.getScanningWavedFlag()))){
							updateOnTimeDelivery(mailbagVO,mailbagInULDForSeg,mailbag);
							}
							//Added for ICRD-243421 ends
					}
				private void updateServiceResponsiveness(MailbagVO mailbagVO, MailbagInULDForSegment mailbagInULDForSeg) throws PersistenceException, SystemException {
					
					String paCode_dom =null; 
					paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
					Timestamp staDate=(mailbagVO.getFlightNumber()!=null && mailbagVO.getFlightSequenceNumber()>0)?fetchSegmentSTA(mailbagVO):null; 
					String serviceResponsiveIndicator="X";
					if(mailbagVO.getServiceResponsive()!=null){
						serviceResponsiveIndicator=mailbagVO.getServiceResponsive();
					}else{
					
					try{
					serviceResponsiveIndicator=findServiceResponsiveIndicator(mailbagVO);//ICRD-331440
					}catch(Exception exception){
						log.log(Log.FINE,exception);
					}
					mailbagVO.setServiceResponsive(serviceResponsiveIndicator);
					}
					/*String handOverTime=fetchHandlingConfiguration(mailbagVO);
					String rdtOffset=fetchRDTOffset(mailbagVO,paCode_dom);
					String[] rdtOffsetTime;
					LocalDate staDateLocal=(staDate!=null)?new LocalDate(mailbagVO.getScannedPort(),Location.ARP,staDate):null;*/
					
					if(mailbagInULDForSeg!=null && paCode_dom.equals(mailbagVO.getPaCode())){
						mailbagInULDForSeg.setServiceResponsive(MailConstantsVO.MALTYP_DOMESTIC);
					}else{
					if(mailbagInULDForSeg!=null)
					mailbagInULDForSeg.setServiceResponsive(serviceResponsiveIndicator);
					//Service responsive logic moved to CRG 	
					/*if(rdtOffset!=null && rdtOffset.trim().length()>0){
					rdtOffsetTime=rdtOffset.split("-");
					//LocalDate rdtOffsetTimeLocal = new LocalDate(mailbagVO.getScannedPort(),ARP,false);
					//adding rdt offset with sta 
					staDateLocal.addMinutes(Integer.parseInt(rdtOffsetTime[1]));
					LocalDate handOverTimeLocal = new LocalDate(mailbagVO.getScannedPort(),Location.ARP,true);
					if(handOverTime!=null && handOverTime.trim().length()>0){
					handOverTimeLocal.setTime(handOverTime +":00");  
					
					if(staDateLocal.isGreaterThan(handOverTimeLocal)){
						mailbagInULDForSeg.setServiceResponsive(MailConstantsVO.FLAG_NO);
					}else
					{
						mailbagInULDForSeg.setServiceResponsive(MailConstantsVO.FLAG_YES);
					}
					}
					
					}*/
					
					}
					
				}
				private Timestamp fetchSegmentSTA(MailbagVO mailbagVO) throws PersistenceException, SystemException {
			    	   return constructDAO().fetchSegmentSTA(mailbagVO);

				}
				private String findServiceResponsiveIndicator(MailbagVO mailbagVO) throws PersistenceException, SystemException {
					
			      	MailbagPK mailbagPK=constructMailbagPK(mailbagVO);
             		Mailbag mailbag=null;
             		try {
						mailbag=Mailbag.find(mailbagPK);
						mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true));
						mailbagVO.setMailServiceLevel(mailbag.getMailServiceLevel());
						mailbagVO.setPaCode(mailbag.getPaCode());
						mailbagVO.setOrigin(mailbag.getOrigin());
						mailbagVO.setDestination(mailbag.getDestination());
						
					} catch (FinderException e1) {
						log.log(Log.FINE, "mailbag not found");
					}
		
			    	   return constructDAO().findServiceResponsiveIndicator(mailbagVO);

				}
				private String fetchHandlingConfiguration(MailbagVO mailbagVO) throws PersistenceException, SystemException {
			    	   return constructDAO().fetchHandlingConfiguration(mailbagVO);

				}
				private String fetchRDTOffset(MailbagVO mailbagVO,String paCode_dom) throws PersistenceException, SystemException {
					
			    	   return constructDAO().fetchRDTOffset(mailbagVO,paCode_dom);

				}	 
				private void updateOnTimeDelivery(MailbagVO mailbagVO, MailbagInULDForSegment mailbagInULDForSeg,Mailbag mailbag) throws PersistenceException, SystemException {
					String domPaCode =null; 
					domPaCode = findSystemParameterValue(USPS_DOMESTIC_PA);
					Timestamp staDate=(mailbagVO.getFlightNumber()!=null && mailbagVO.getFlightSequenceNumber()>0)?fetchSegmentSTA(mailbagVO):null; 
							 
                    String serviceResponsiveIndicator = "X";
					if(mailbagInULDForSeg!=null && mailbagInULDForSeg.getServiceResponsive()!=null){
						serviceResponsiveIndicator= mailbagInULDForSeg.getServiceResponsive();
					}else{
														
						serviceResponsiveIndicator=	mailbagVO.getServiceResponsive();
					}
					String rdtOffset=fetchRDTOffset(mailbagVO,domPaCode);
					String[] rdtOffsetTime;
					LocalDate RDTLocal= staDate!=null?new LocalDate(mailbagVO.getScannedPort(),Location.ARP,staDate)
                     :new LocalDate(mailbagVO.getScannedPort(), Location.ARP, true).setDateAndTime(mailbagVO.getScannedDate().toDisplayFormat());
					//Added by A-7540 for IASCB-33480
					if(mailbag.getReqDeliveryTime()!=null){
						mailbagVO.setReqDeliveryTime((new LocalDate(mailbagVO.getScannedPort(), Location.ARP, mailbag.getReqDeliveryTime(), true)));	
					}
					if(rdtOffset!=null && rdtOffset.trim().length()>0){
						rdtOffsetTime=rdtOffset.split("-");
					//adding rdt offset with sta 
					if(domPaCode.equals(mailbagVO.getPaCode())){//if mailbag is domestic
						RDTLocal.addDays(Integer.parseInt(rdtOffsetTime[0]));
						//Modified by A-8672 for ICRD-340598 starts here
						String rdt = RDTLocal.toDisplayDateOnlyFormat();
						RDTLocal = new LocalDate(mailbagVO.getScannedPort(), Location.ARP, true);
						RDTLocal.setDate(rdt);
						//Modified by A-8672 for ICRD-340598 ends here
						RDTLocal.addMinutes(Integer.parseInt(rdtOffsetTime[1]));	
					}else{//international mailbag
						RDTLocal.addMinutes(Integer.parseInt(rdtOffsetTime[1]));
					}
					}
					//Modified By A-6986 for ICRD-337702 starts 
					//ICRD-338169 if mailbag is delivered at co-terminus airport 	
					//commented as even for coterminus, date check for ontime caculation has to be done
					/*if(mailbagVO.getScannedPort()!=null && !mailbagVO.getScannedPort().equals(mailbag.getDestination())){
						mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
					
					}else*/
					if(serviceResponsiveIndicator!= null &&
							serviceResponsiveIndicator.length() > 0 &&
							(MailConstantsVO.FLAG_YES.equals(serviceResponsiveIndicator)) ){
						//Modified the code for on time delivery for IASCB-33480 starts
						//If REQDLVTIM is not present,then we will consider STA+offset config for ONTIMDLVFLG
						if(mailbag.getReqDeliveryTime()!=null){
							if((mailbagVO.getScannedDate().isLesserThan(mailbagVO.getReqDeliveryTime()) ||
									mailbagVO.getScannedDate().equals(mailbagVO.getReqDeliveryTime()))&&
									((mailbag.getMetTransWindow()!= null) && 
									(mailbag.getMetTransWindow().length()>0) &&
									MailConstantsVO.FLAG_YES.equals(mailbag.getMetTransWindow()))){
						
										mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
							}else {
								mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
							}
						}
						else{
							if((mailbagVO.getScannedDate().isLesserThan(RDTLocal) ||
									mailbagVO.getScannedDate().equals(RDTLocal))&&
									((mailbag.getMetTransWindow()!= null) && 
									(mailbag.getMetTransWindow().length()>0) &&
									MailConstantsVO.FLAG_YES.equals(mailbag.getMetTransWindow()))){
								
										mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
							}else {
								mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
							}
						}
						//Modified the code for on time delivery for IASCB-33480 ends
					}else if(serviceResponsiveIndicator!= null 
							&& serviceResponsiveIndicator.length() > 0 
							&& (MailConstantsVO.MALTYP_DOMESTIC.equals(serviceResponsiveIndicator) 
								|| MailConstantsVO.FLAG_NO.equals(serviceResponsiveIndicator))){
						//Added Domestic  and Req DLV time check for ICRD-340442 
						//Modified the code for on time delivery for IASCB-33480 starts						
						if (mailbag.getReqDeliveryTime()!= null){
								 if(mailbagVO.getScannedDate().isLesserThan(mailbagVO.getReqDeliveryTime())
										|| mailbagVO.getScannedDate().equals(mailbagVO.getReqDeliveryTime())) {
							mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
						} else {
							mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
						}
					  }	
						else{
							if(mailbagVO.getScannedDate().isLesserThan(RDTLocal)
									|| mailbagVO.getScannedDate().equals(RDTLocal)) {
							mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
						} else {
							mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
						}
						
						}
						//Modified the code for on time delivery for IASCB-33480 ends
					}//else if(serviceResponsiveIndicator!= null 
						//	&& serviceResponsiveIndicator.length() > 0 
						//	&& (MailConstantsVO.FLAG_OTHERS.equals(serviceResponsiveIndicator))){
						//mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);// Modified by A-8527 for ICRD-346837
					//}
					
					
					
					
				}	 
	 			
			/**
			 * 	
			 * 	Method		:	ULDForSegment.findArrivedContainersForInbound
			 *	Added by 	:	A-8164 on 29-Dec-2018
			 * 	Used for 	:
			 *	Parameters	:	@param mailArrivalFilterVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws SystemException 
			 *	Return type	: 	Collection<ContainerDetailsVO>
			 */
			public static Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws SystemException {
				return constructDAO().findArrivedContainersForInbound(mailArrivalFilterVO);
			}
			
			/**
			 * 
			 * 	Method		:	ULDForSegment.findArrivedMailbagsForInbound
			 *	Added by 	:	A-8164 on 29-Dec-2018
			 * 	Used for 	:
			 *	Parameters	:	@param mailArrivalFilterVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws SystemException 
			 *	Return type	: 	Collection<ContainerDetailsVO>
			 */
			public static Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws SystemException {
				return constructDAO().findArrivedMailbagsForInbound(mailArrivalFilterVO);
			}
			
			/**
			 * 
			 * 	Method		:	ULDForSegment.findArrivedDsnsForInbound
			 *	Added by 	:	A-8164 on 29-Dec-2018
			 * 	Used for 	:
			 *	Parameters	:	@param mailArrivalFilterVO
			 *	Parameters	:	@return
			 *	Parameters	:	@throws SystemException 
			 *	Return type	: 	Collection<DSNVO>
			 */
			public static Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
					throws SystemException {
				return constructDAO().findArrivedDsnsForInbound(mailArrivalFilterVO);
			}
	/**
	 * @author A-8464
	 * @param mailbag
	 * @param mailbagVo
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	private void updateTransportationWindowStatus(Mailbag mailbag,MailbagVO mailbagVo) throws PersistenceException, SystemException {
	
		log.entering("ULDForSegment", "updateTransportationWindowStatus");	
		
		mailbagVo.setMailServiceLevel(mailbag.getMailServiceLevel());
		
		LocalDate transportationSrvWindowEndTime = null;
		if(mailbag.getTransWindowEndTime()==null){			
			transportationSrvWindowEndTime = new MailController().calculateTransportServiceWindowEndTime(mailbagVo);
		}else{
			transportationSrvWindowEndTime= new LocalDate(mailbag.getDestination(), Location.ARP,mailbag.getTransWindowEndTime(), true);
		}
		
		
		
		
		/*//Closeout date - received in cardit from PA
		Timestamp closeOutDate=findCloseoutDate(mailbagVo.getMailbagId());
		log.log(Log.INFO, "Close out date for mail bag "+mailbagVo.getMailbagId()+" is : "+closeOutDate);
		
		//Closeout offset - system parameter in minutes
		String closeOutOffset = findSystemParameterValue(CLOSE_OUT_OFFSET);
		if(closeOutOffset == null || closeOutOffset.trim().length()==0 ){
			closeOutOffset="0";
		}
		log.log(Log.INFO, "Close out offset for mail bag "+mailbagVo.getMailbagId()+" is : "+closeOutOffset);
		
		//Service standard = number of days defined by USPS for transporting mails
		int serviceStd = 0;
		boolean exceptionInFindingSrvStd = false;
		try{
			mailbagVo.setMailServiceLevel(mailbag.getMailServiceLevel());
		serviceStd = findServiceStd(mailbagVo);
		if(serviceStd>0){
			serviceStd=serviceStd-1;      
		}
		}
		catch(Exception e){
			exceptionInFindingSrvStd = true;
		}
		log.log(Log.INFO, "Service standard for mail bag "+mailbagVo.getMailbagId()+" is : "+serviceStd);
		
		
		 * Transportation service window is the no: of days within which the
		 * carrier should transport and deliver the mail at the destination.
		 * Transportation service window = closeoutdate + closeoutoffset + servicestd
		 * 
		 * 	Met Transportation Service Window
		 *	Yes: If the mailbag was delivered within the transportation service window
		 *	No: If the mailbag was NOT delivered within the transportation service window
		 *	Blank: System cannot find the value as either the service standard of the product and the OD pair OR HT is not defined for the destination airport
		 * 
		 
		if(closeOutDate == null || exceptionInFindingSrvStd){

			mailbag.setMetTransWindow("");
		}
		
		else {*/
/*
			LocalDate transportationSrvWindowEndTime = new LocalDate(mailbagVo.getScannedPort(), Location.ARP,
					closeOutDate);
			transportationSrvWindowEndTime.addDays(serviceStd);
			transportationSrvWindowEndTime.addMinutes(Integer.parseInt(closeOutOffset));
			log.log(Log.INFO, "TransportationSrvWindowEndTime for mail bag "+mailbagVo.getMailbagId()+" is : "+transportationSrvWindowEndTime);*/
			
			// Persisting the value of transportation window end time
			// (DLVDAT+SRVSTD+Offset) in MALMST.TRPSRVTIM
			mailbag.setTransWindowEndTime(transportationSrvWindowEndTime);

			// Finding metTransportationWindow, and persisiting
			if (transportationSrvWindowEndTime!=null && (mailbagVo.getScannedDate().isLesserThan(transportationSrvWindowEndTime)
					|| mailbagVo.getScannedDate().equals(transportationSrvWindowEndTime))) {
				mailbag.setMetTransWindow("Y");
				//Added by A-7540
				mailbagVo.setMetTransWindow(MailConstantsVO.FLAG_YES);
			} else if (transportationSrvWindowEndTime!=null && mailbagVo.getScannedDate().isGreaterThan(transportationSrvWindowEndTime)) {
				mailbag.setMetTransWindow("N");
			} else {
				mailbag.setMetTransWindow("");
			}

		 log.exiting("ULDForSegment", "updateTransportationWindowStatus");
	}
	/**
	 * @author A-8464
	 * @param mailbagId
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private Timestamp findCloseoutDate(String mailbagId) throws PersistenceException, SystemException {
 	   return constructDAO().findCloseoutDate(mailbagId);

	}
	/**
	 * @author A-8464
	 * @param mailbagVo
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private int findServiceStd(MailbagVO mailbagVo) throws PersistenceException, SystemException {
	 	   return constructDAO().findServiceStandard(mailbagVo);

	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailbag
	 * @return
	 * @throws SystemException
	 * @throws DuplicateMailBagsException
	 */
/*	private boolean checkForDuplicateMailbag(MailbagVO mailbagVO,Mailbag mailbag) throws SystemException, DuplicateMailBagsException {
        PostalAdministrationVO postalAdministrationVO = PostalAdministration.findPACode(mailbagVO.getCompanyCode(), mailbagVO.getPaCode());
        LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
        LocalDate dspDate = new LocalDate(
                    LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);

        long seconds=currentDate.findDifference(dspDate);
        float days=seconds/86400000;
        if((days)<= postalAdministrationVO.getDupMailbagPeriod()){
        	return false;
        }
        return true;
  }*/
/**
 * 
 * 	Method		:	ULDForSegment.findGHTForMailbags
 *	Added by 	:	A-8061 on 28-Apr-2020
 * 	Used for 	:	IASCB-48967
 *	Parameters	:	@param operationalFlightVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	LocalDate
 */
public LocalDate findGHTForMailbags(OperationalFlightVO operationalFlightVO) throws SystemException{
	
	 FlightValidationVO flightValidationVO = null;
	 LocalDate GHTtime=null;
	 MailbagVO mailbagVO=new MailbagVO();
	 MailController mailController = new MailController();
	 flightValidationVO=mailController.validateOperationalFlight(operationalFlightVO,true);
     
     if(flightValidationVO!=null){
     operationalFlightVO.setArrivaltime(flightValidationVO.getAta()!=null?flightValidationVO.getAta():flightValidationVO.getSta());
     mailbagVO.setPou(operationalFlightVO.getPou());
     mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_OUTBOUND);
     updateGHTForMailbags(operationalFlightVO, mailbagVO);
     GHTtime=mailbagVO.getGhttim();
     }
     return GHTtime;
		
	}
/**
 * @author A-7540
 * @param operationalFlightVO
 * @param mailbagVO
 */
public void updateGHTForMailbags(OperationalFlightVO operationalFlightVO,MailbagVO mailbagVO){
	LocalDate ghttim =null;
	if (operationalFlightVO != null && operationalFlightVO.getFlightDate()!=null) {
        GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
        Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = null;
    Collection<MailbagVO> mailbags = null;
    LocalDate arrtime = null;
    LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,operationalFlightVO.getFlightDate(),false);

	generalTimeMappingFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());   
	generalTimeMappingFilterVO.setCarrierId(operationalFlightVO.getCarrierId());
	generalTimeMappingFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
	generalTimeMappingFilterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
        if (operationalFlightVO.getPou() != null) {
		generalTimeMappingFilterVO.setPou(operationalFlightVO.getPou());
	}
	
	generalTimeMappingFilterVO.setPol(operationalFlightVO.getPol());
			generalTimeMappingFilterVO
					.setAirportCode(MailConstantsVO.OPERATION_OUTBOUND.equals(mailbagVO.getOperationalFlag())
							? generalTimeMappingFilterVO.getPou() : operationalFlightVO.getAirportCode());
	generalTimeMappingFilterVO.setConfigurationType("MHT");		
           try {
			generalConfigurationMasterVOs = new SharedDefaultsProxy().findGeneralConfigurationDetails(generalTimeMappingFilterVO);
		} catch (SystemException e) {			
			e.getMessage();
		}
          //  arrtime = getLocalDate(generalTimeMappingFilterVO.getAirportCode(), Location.ARP, false);
           if (operationalFlightVO.getArrivaltime() != null) {
            arrtime = operationalFlightVO.getArrivaltime();

             } 
	if (generalConfigurationMasterVOs != null && !generalConfigurationMasterVOs.isEmpty()) {
	//arrtime=new LocalDate(generalTimeMappingFilterVO.getAirportCode(),Location.ARP,false);  

            for (GeneralConfigurationMasterVO general : generalConfigurationMasterVOs) {
                String parvalmin = null;
                String parvalhr = null;
                int min = 0;
                int hour = 0;
	
            if((flightDate.isGreaterThan(general.getStartDate())
                     && flightDate.isLesserThan(general.getEndDate()))
                  || flightDate.equals(general.getStartDate())
                  || flightDate.equals(general.getEndDate()))  {
            	if(mailbagVO.getPou().equals(general.getAirportCode())){
                    Collection<GeneralRuleConfigDetailsVO> time = general.getTimeDetails();
                    for (GeneralRuleConfigDetailsVO offset : time) {
                    	
                        if (offset.getParameterCode().equals("Min")) {
                            parvalmin = offset.getParameterValue();
					
                            min = Integer.parseInt(parvalmin);

                        }

                        if (offset.getParameterCode().equals("Hrs")) {
                            parvalhr = offset.getParameterValue();
                            hour = Integer.parseInt(parvalhr);
				}
                    }
		
       
			arrtime.addHours(hour);
			arrtime.addMinutes(min);
                    ghttim = arrtime;
            	}            
            } 
         }
        }
	
      else{
            ghttim = arrtime;
          }
	     mailbagVO.setGhttim(ghttim);
      }
    } 
 
}




