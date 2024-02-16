/*
 * AssignedFlightSegment.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
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

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightSegmentVO;
import com.ibsplc.icargo.business.mail.operations.ULDForSegmentPK;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRouteForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.Container;
import com.ibsplc.icargo.business.mail.operations.ContainerPK;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ULDDefaultsProxy;


import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegmentPK;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInULDForSegmentVO;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.ULDForSegment;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationReportVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3109
 *
 *         This class represents each segment of the assigned flight
 *
 *
 */

@Entity
@Table(name = "MALFLTSEG")
@Staleable
public class AssignedFlightSegment {

	private static final String PRODUCT_NAME = "mail.operations";

	private Log log = LogFactory.getLogger("MAIL_operations");

	private AssignedFlightSegmentPK assignedFlightSegmentPK;

	private String pou;

	private String pol;

	private String mraStatus;

	/**
	 * Set<ContainerForSegment> All containers assigned to this segment
	 */
	private Set<ULDForSegment> containersForSegment;

	/**
	 * @return Returns the pol.
	 *
	 */
	@Column(name = "POL")
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the pou.
	 *
	 */
	@Column(name = "POU")
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the mraStatus.
	 */
	@Column(name = "MRASTA")
	public String getMraStatus() {
		return mraStatus;
	}

	/**
	 * @param mraStatus
	 *            The mraStatus to set.
	 */
	public void setMraStatus(String mraStatus) {
		this.mraStatus = mraStatus;
	}

	/**
	 * @return Returns the containersForSegment.
	 *
	 */
	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false),
			@JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
			@JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "SEGSERNUM", referencedColumnName = "SEGSERNUM", insertable = false, updatable = false) })
	public Set<ULDForSegment> getContainersForSegment() {
		return containersForSegment;
	}

	/**
	 * @param containersForSegment
	 *            The containersForSegment to set.
	 */
	public void setContainersForSegment(Set<ULDForSegment> containersForSegment) {
		this.containersForSegment = containersForSegment;
	}

	/**
	 *
	 * @return
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "segmentSerialNumber", column = @Column(name = "SEGSERNUM")) })
	public AssignedFlightSegmentPK getAssignedFlightSegmentPK() {
		return assignedFlightSegmentPK;
	}

	/**
	 * @param assignedFlightSegmentPK
	 *            The assignedFlightSegmentPK to set.
	 */
	public void setAssignedFlightSegmentPK(
			AssignedFlightSegmentPK assignedFlightSegmentPK) {
		this.assignedFlightSegmentPK = assignedFlightSegmentPK;
	}

	public AssignedFlightSegment() {
	}

	public AssignedFlightSegment(AssignedFlightSegmentVO assignedFlightSegmentVO)
			throws SystemException {
		populatePK(assignedFlightSegmentVO);
		populateAttributes(assignedFlightSegmentVO);

		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}

	}

	/**
	 * @author a-3109 This method is used to populate the pk
	 * @param assignedFlightSegmentVO
	 * @throws SystemException
	 */
	private void populatePK(AssignedFlightSegmentVO assignedFlightSegmentVO) {
		log.log(Log.INFO, "INSIDE THE POPULATE SEGMNET PK");

		AssignedFlightSegmentPK segmentPK = new AssignedFlightSegmentPK();
		segmentPK.setCarrierId(assignedFlightSegmentVO.getCarrierId());
		segmentPK.setCompanyCode(assignedFlightSegmentVO.getCompanyCode());
		segmentPK.setFlightNumber(assignedFlightSegmentVO.getFlightNumber());
		segmentPK.setFlightSequenceNumber(assignedFlightSegmentVO
				.getFlightSequenceNumber());
		segmentPK.setSegmentSerialNumber(assignedFlightSegmentVO
				.getSegmentSerialNumber());
		this.setAssignedFlightSegmentPK(segmentPK);
	}

	/**
	 * @author a-3109 This method is used to populate the Attributes other than
	 *         pk into the Entity
	 * @param assignedFlightSegmentVO
	 * @throws SystemException
	 */
	private void populateAttributes(
			AssignedFlightSegmentVO assignedFlightSegmentVO) {
		log.log(Log.INFO, "INSIDE THE POPULATE ATTRIBUTES");
		this.setPol(assignedFlightSegmentVO.getPol());
		this.setPou(assignedFlightSegmentVO.getPou());
		/**
		 * Added By Karthick V to include the status for importing the Data from
		 * the Flown Flights that could be used be used for the Mail Revenue
		 * Accounting
		 */
		if(assignedFlightSegmentVO.getMraStatus()!=null){
		this.setMraStatus(assignedFlightSegmentVO.getMraStatus());
		}else{
			this.setMraStatus(MailConstantsVO.FLAG_NO);
		}

	}

	/**
	 * @author a-3109 This method is used to find the instance of the Entity
	 * @param assignedFlightSegmentPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static AssignedFlightSegment find(
			AssignedFlightSegmentPK assignedFlightSegmentPK)
			throws SystemException, FinderException {

		Log logger = LogFactory.getLogger("MAIL_OPERATIONS");
		logger.log(Log.INFO, "INSIDE THE FINDER METHOD");
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(AssignedFlightSegment.class, assignedFlightSegmentPK);
	}

	/**
	 *
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}


	/**
     * @author A-5991
     * @param mailAcceptanceVO
     * @param containerDetailsForSeg
     * @return
     * @throws SystemException
     */
    public boolean saveFlightAcceptanceDetails(
            MailAcceptanceVO mailAcceptanceVO,
            Collection<ContainerDetailsVO> containerDetailsForSeg)
    throws SystemException {

        boolean hasUpdated = false;
        log.entering("AssignedFlightSegment", "saveULDForSegments");



        log.log(Log.FINE, "Containers of asg flt-> ", containerDetailsForSeg);
		for (ContainerDetailsVO containerDetailsVO : containerDetailsForSeg) {

            if (OPERATION_FLAG_INSERT.equals(containerDetailsVO
                    .getOperationFlag())) {
                insertContainerAcceptanceDetails(containerDetailsVO,
                        mailAcceptanceVO);
                hasUpdated = true;
            } else if (OPERATION_FLAG_UPDATE.equals(containerDetailsVO
                    .getOperationFlag())) {
                updateContainerAcceptanceDetails(containerDetailsVO,
                        mailAcceptanceVO);
                hasUpdated = true;
            }

        }
        log.exiting("AssignedFlightSegment", "saveULDForSegments");
        return hasUpdated;
    }

    /**
     * @author a-5991
     * @param containerDetailsVO
     * @param mailAcceptanceVO
     * @param dsnForSegMap
     * @throws SystemException
     */
    private void insertContainerAcceptanceDetails(
            ContainerDetailsVO containerDetailsVO,
            MailAcceptanceVO mailAcceptanceVO)
    throws SystemException {
        log.entering("AssignedFlightSegment", "insertContainerDetails");
        FlightDetailsVO flightDetailsVO = null;
        Collection<ULDInFlightVO> uldInFlightVos = null;
        ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
        uldForSegmentVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
        uldForSegmentVO.setCarrierId(mailAcceptanceVO.getCarrierId());
        uldForSegmentVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
        uldForSegmentVO.setFlightSequenceNumber(mailAcceptanceVO
                .getFlightSequenceNumber());
		int bags=0;
		double weight=0;
		if(containerDetailsVO.getMailDetails()!=null && containerDetailsVO.getMailDetails().size()>0){

			 for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
				 bags=bags+1;
				 if(mailbagVO.getWeight()!=null){
				 //weight=weight+mailbagVO.getWeight();
				 weight=weight+mailbagVO.getWeight().getRoundedSystemValue();//modified by A-7371
				 }
			 }
		}

        uldForSegmentVO.setSegmentSerialNumber(containerDetailsVO
                .getSegmentSerialNumber());
        uldForSegmentVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
        uldForSegmentVO.setNoOfBags(bags);
        uldForSegmentVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,weight));//added by A-7371

        ULDForSegment uldForSegment = null;

        boolean isBulkContainer = false;
        if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
                .getContainerType())) {
            uldForSegmentVO.setUldNumber(containerDetailsVO
                    .getContainerNumber());
            uldForSegmentVO.setRemarks(containerDetailsVO.getRemarks());
            uldForSegmentVO.setWarehouseCode(containerDetailsVO.getWareHouse());
            uldForSegmentVO.setLocationCode(containerDetailsVO.getLocation());

            uldForSegmentVO.setOnwardRoutings(containerDetailsVO
                    .getOnwardRoutingForSegmentVOs());

            uldForSegmentVO.setTransferFromCarrier(
            		containerDetailsVO.getTransferFromCarrier());
            //Modified by A-4809 as per trace from on site-find need to be done before insert.. Starts
            log.log(Log.FINE, "COnatiner case finding the ULDSegment");
          //Modified for icrd-126626
            try {
				uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
			} catch (FinderException e) {
				 log.log(Log.FINE, "FinderException....");
			}
            log.log(Log.FINE, "ULDsegment after find",uldForSegment);
            if (uldForSegment==null) {
            	 log.log(Log.FINE, "Container ULDSEgment not found, creating....");
            uldForSegment = createULDForSegment(uldForSegmentVO);
          //Added by A-7794 as part of ICRD-208677
            boolean isUMSUpdateNeeded = new MailController().isULDIntegrationEnabled();
            if (isUMSUpdateNeeded) {
            	OperationalFlightVO operationalFlightVo = constructOprFlightFromMailAcp(mailAcceptanceVO);
            	ULDInFlightVO uldFltVo = new ULDInFlightVO();
     			uldFltVo.setUldNumber(containerDetailsVO.getContainerNumber());
     			uldFltVo.setPointOfLading(containerDetailsVO.getPol());
     			uldFltVo.setPointOfUnLading(containerDetailsVO.getPou());
     			uldFltVo.setRemark("Mail ULD Assigned");
            	updateULDForOperationsForContainerAcceptance(operationalFlightVo, uldFltVo);
    		}
            }else{
            	if(uldForSegment.getRemarks()!=null && !uldForSegment.getRemarks().equals(containerDetailsVO.getRemarks())){
            	uldForSegment.setRemarks(containerDetailsVO.getRemarks());
            	}
            	if(uldForSegment.getWarehouseCode()!=null && !uldForSegment.getWarehouseCode().equals(containerDetailsVO.getWareHouse())){
            	uldForSegment.setWarehouseCode(containerDetailsVO.getWareHouse());
            	}
            	if(uldForSegment.getLocationCode()!=null && !uldForSegment.getLocationCode().equals(containerDetailsVO.getLocation())){
            	uldForSegment.setLocationCode(containerDetailsVO.getLocation());
            	}
            	if(uldForSegment.getTransferFromCarrier()!=null && !uldForSegment.getTransferFromCarrier().equals(containerDetailsVO.getTransferFromCarrier())){
            	uldForSegment.setTransferFromCarrier(containerDetailsVO.getTransferFromCarrier());
            	}
            
}
          //Modified by A-4809 as per trace from on site-find need to be done before insert.. Ends
        } else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO
                .getContainerType())) {
            isBulkContainer = true;
            //uldForSegmentVO.setUldNumber(constructBulkULDNumber());
            uldForSegmentVO.setUldNumber(new StringBuilder().append(MailConstantsVO.CONST_BULK).append(
                    MailConstantsVO.SEPARATOR).append(containerDetailsVO.getDestination()).toString());
          //Modified for icrd-126626
             try {
				uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
             if (uldForSegment==null) {
                log.log(Log.FINE, "BULK ULDSEgment not found, creating....");
                uldForSegment = createULDForSegment(uldForSegmentVO);
             }

        }

        uldForSegment.saveAcceptanceDetails(mailAcceptanceVO,containerDetailsVO);

        log.exiting("AssignedFlightSegment", "insertContainerDetails");
    }


    /**
     * @author a-5991
     * @param uldForSegmentPK
     * @return
     * @throws FinderException
     * @throws SystemException
     */
    public static ULDForSegment findULDForSegment(
            ULDForSegmentPK uldForSegmentPK) throws FinderException,
            SystemException {
        return ULDForSegment.find(uldForSegmentPK);
    }


    






/**
     * @author A-5991
     * @param uldForSegmentVO
     * @return
     */
    private ULDForSegmentPK constructULDForSegmentPK(
            ULDForSegmentVO uldForSegmentVO) {
        ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
        uldForSegmentPK.setCompanyCode(   uldForSegmentVO.getCompanyCode());
        uldForSegmentPK.setUldNumber(   uldForSegmentVO.getUldNumber());
        uldForSegmentPK.setCarrierId(   uldForSegmentVO.getCarrierId());
        uldForSegmentPK.setFlightNumber(   uldForSegmentVO.getFlightNumber());
        uldForSegmentPK.setFlightSequenceNumber(   uldForSegmentVO
        .getFlightSequenceNumber());
        uldForSegmentPK.setSegmentSerialNumber(   uldForSegmentVO
        .getSegmentSerialNumber());
        return uldForSegmentPK;
    }



    /**
     * @author a-5991
     * @param uldForSegmentVO
     * @return
     * @throws SystemException
     */
    public ULDForSegment createULDForSegment(ULDForSegmentVO uldForSegmentVO)
    throws SystemException {
        if (getContainersForSegment() == null) {
            containersForSegment = new HashSet<ULDForSegment>();
        }
        ULDForSegment uldForSegment = new ULDForSegment(uldForSegmentVO);
        containersForSegment.add(uldForSegment);
        return uldForSegment;
    }

    /**
     * @author A-5991
     */
    private String constructBulkULDNumber() {
        return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(
                MailConstantsVO.SEPARATOR).append(getPou()).toString();
    }



    /**
     * @author a-1739
     * @param containerDetailsVO
     * @param mailAcceptanceVO
     * @param dsnForSegMap
     * @throws SystemException
     */
    private void updateContainerAcceptanceDetails(
            ContainerDetailsVO containerDetailsVO,
            MailAcceptanceVO mailAcceptanceVO)
    throws SystemException {

        log
        .exiting("AssignedFlightSegment",
        "updateContainerAcceptanceDetails");
		int bags=0;
		double weight=0;
        ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
        uldForSegmentVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
        uldForSegmentVO.setCarrierId(mailAcceptanceVO.getCarrierId());
        uldForSegmentVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
        uldForSegmentVO.setFlightSequenceNumber(mailAcceptanceVO
                .getFlightSequenceNumber());
		if(containerDetailsVO.getMailDetails()!=null && containerDetailsVO.getMailDetails().size()>0){
			 for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
				 bags=bags+1;
				 if(mailbagVO.getWeight()!=null){
					 //weight=weight+mailbagVO.getWeight()
				 weight=weight+mailbagVO.getWeight().getRoundedSystemValue();//added by A-7371
				 }
			 }
		}
        uldForSegmentVO.setNoOfBags(bags);
        //uldForSegmentVO.setTotalWeight(weight);
        uldForSegmentVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,weight));//added by A-7371

        uldForSegmentVO.setSegmentSerialNumber(containerDetailsVO
                .getSegmentSerialNumber());
        uldForSegmentVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
        ULDForSegment uldForSegment = null;

        boolean isBulkContainer = false;
        if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
                .getContainerType())) {
            uldForSegmentVO.setUldNumber(containerDetailsVO
                    .getContainerNumber());
            //Modified for ICRD-126626
                try {
					uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					log.log(Log.FINE, "Finder Exception ULDSEgment not found..");
				}
                if(uldForSegment!=null){
                	if(uldForSegment.getRemarks()!=null && !uldForSegment.getRemarks().equals(containerDetailsVO.getRemarks())){
                uldForSegment.setRemarks(containerDetailsVO.getRemarks());
                	}
                	if(uldForSegment.getWarehouseCode()!=null && !uldForSegment.getWarehouseCode().equals(containerDetailsVO.getWareHouse())){
                uldForSegment.setWarehouseCode(containerDetailsVO
                        .getWareHouse());
                	}
                	
                if(uldForSegment.getLocationCode()!=null && !uldForSegment.getLocationCode().equals(containerDetailsVO.getLocation())){
                uldForSegment.setLocationCode(containerDetailsVO.getLocation());
                }
                uldForSegment.updateOnwardRoutes(containerDetailsVO
                        .getOnwardRoutingForSegmentVOs());
                //Added as part of bug ICRD-144132 by A-5526 starts
                if(mailAcceptanceVO.isScanned() && (containerDetailsVO.getTransferFromCarrier()==null || containerDetailsVO.getTransferFromCarrier().isEmpty())){
                //This is coming from HHT scan .so no need to update the trasnferCarrierCode for container
                }//Added as part of bug ICRD-144132 by A-5526 ends
                else if(uldForSegment.getTransferFromCarrier()!=null && !uldForSegment.getTransferFromCarrier().equals(containerDetailsVO.getTransferFromCarrier())) {
                uldForSegment.setTransferFromCarrier(
                		containerDetailsVO.getTransferFromCarrier());
                }
                else{
                	//Nothing to do.Added to avoid sonar issue
                }
               
                }
                else {
                throw new SystemException("No such ULD for update");
            }

        } else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO
                .getContainerType())) {
            isBulkContainer = true;
            uldForSegmentVO
            .setUldNumber(constructBulkULDNumber());
            //Modified for ICRD-126626
                try {
					uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
                if(uldForSegment!=null)
{

                if (uldForSegment.getWarehouseCode() != null
						&& !uldForSegment.getWarehouseCode().equals(containerDetailsVO.getWareHouse())) {

					uldForSegment.setWarehouseCode(containerDetailsVO.getWareHouse());
				}
			}
                else {
                log.log(Log.FINE, "BULK ULDSEgment not found, creating....");
                throw new SystemException("No such BULK-POU ULD for update");
            }
        }

        uldForSegment.saveAcceptanceDetails(mailAcceptanceVO,
                containerDetailsVO);

        log
        .exiting("AssignedFlightSegment",
        "updateContainerAcceptanceDetails");
    }


	/**
	 * TODO Purpose
	 * Dec 5, 2006, a-1739
	 * @param containerVO
	 * @throws SystemException
	 */
	public void updateULDOnwardRoute(ContainerVO containerVO)
		throws SystemException {
		log.entering("AssignedFlightSegment", "updateULDOnwardRoute");
		ULDForSegment uldForSegment = null;
		//Added as part of IASCB-33408 starts
		if (MailConstantsVO.BULK_TYPE.equals(containerVO .getType())) {
            containerVO
            .setContainerNumber(new  StringBuilder() .append(MailConstantsVO.CONST_BULK).append(
                    MailConstantsVO.SEPARATOR).append(containerVO.getPou()).toString());
		}
		//Added as part of IASCB-33408 ends
		try {
		 uldForSegment =  ULDForSegment.find(constructULDForSegPK(containerVO));
		} catch(FinderException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		Collection<OnwardRouteForSegmentVO> onwardRouteForSegs =
			constructOnwardRoutingForSegments(containerVO.getOnwardRoutings());

		uldForSegment.updateOnwardRoutes(onwardRouteForSegs);
		log.exiting("AssignedFlightSegment", "updateULDOnwardRoute");
	}


	/**
	 * TODO Purpose
	 * Dec 5, 2006, a-1739
	 * @param containerVO
	 * @return
	 */
	private ULDForSegmentPK constructULDForSegPK(ContainerVO containerVO) {
		ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
		uldForSegPK.setCompanyCode(   containerVO.getCompanyCode());
		uldForSegPK.setCarrierId(   containerVO.getCarrierId());
		uldForSegPK.setFlightNumber(  containerVO.getFlightNumber());
		uldForSegPK.setFlightSequenceNumber(
			containerVO.getFlightSequenceNumber());
		uldForSegPK.setSegmentSerialNumber(   containerVO.getSegmentSerialNumber());
		uldForSegPK.setUldNumber(   containerVO.getContainerNumber());
		return uldForSegPK;
	}

	/**
	 * @author A-5991
	 * @param onwardRoutings
	 * @return
	 */
	private Collection<OnwardRouteForSegmentVO> constructOnwardRoutingForSegments(
			Collection<OnwardRoutingVO> onwardRoutings) {
		Collection<OnwardRouteForSegmentVO> onwardRouteForSegmentVOs = new ArrayList<OnwardRouteForSegmentVO>();
		if (onwardRoutings != null && onwardRoutings.size() > 0) {
			for (OnwardRoutingVO onwardRoutingVO : onwardRoutings) {
				OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
				onwardRouteForSegmentVO.setOnwardCarrierCode(onwardRoutingVO
						.getOnwardCarrierCode());
				onwardRouteForSegmentVO.setOnwardFlightNumber(onwardRoutingVO
						.getOnwardFlightNumber());
				onwardRouteForSegmentVO.setOnwardFlightDate(onwardRoutingVO
						.getOnwardFlightDate());
				onwardRouteForSegmentVO.setOnwardCarrierId(onwardRoutingVO
						.getOnwardCarrierId());
				onwardRouteForSegmentVO.setPou(onwardRoutingVO.getPou());
				onwardRouteForSegmentVO.setRoutingSerialNumber(
						onwardRoutingVO.getRoutingSerialNumber());
				onwardRouteForSegmentVO.setOperationFlag(
						onwardRoutingVO.getOperationFlag());
				onwardRouteForSegmentVOs.add(onwardRouteForSegmentVO);
			}
		}
		return onwardRouteForSegmentVOs;
	}




    /**
     * @author a-1739
     * @param mailbags
     * @return
     * @throws SystemException
     */
    public Collection<ContainerDetailsVO> reassignMailFromFlight(
            Collection<MailbagVO> mailbags) throws SystemException {

        log.entering("AssignedFlightSegment", "reassignMailbagsFromFlight");
        Map<ULDForSegmentPK, Collection<MailbagVO>> mailbagsOfULDSeg = groupMailbagsOfULDSeg(mailbags);

        Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();

        ULDForSegment uldForSegment = null;

        boolean isInbound = false;
        /*
         * Sep 29, 2006
         * Check if mailbags are outbound or inbound.
         * Outbound is normal for this method.
         * But this method can be invoked during inbound too since
         *  returning can happen at the inbound port.
         *  If it's inbound don't touch the manifested bags.
         *  Only reduce the received bags at the port
         *  And no uld removal too
         */
        for(MailbagVO mailbagVO : mailbags) {
            if(MailConstantsVO.OPERATION_INBOUND.equals(
                    mailbagVO.getOperationalStatus())) {
                isInbound = true;
            }
            break;
        }

        try {
            for (Map.Entry<ULDForSegmentPK, Collection<MailbagVO>> mailbagsOfULD : mailbagsOfULDSeg
                    .entrySet()) {
                Collection<MailbagVO> mailbagsToRem = mailbagsOfULD.getValue();


                ULDForSegmentPK uldForSegmentPK = mailbagsOfULD.getKey();
                uldForSegment = ULDForSegment.find(uldForSegmentPK);

                //TODO REMOVE THIS CODE
                /*
                 * In order to implement the optimistic locking,LastUpdateTime should be set to ULDForSegment
                 * for testing purpose below code is written.
                 */
              
                uldForSegment.reassignMailFromFlight(mailbagsToRem, isInbound);

                /*
                 *
                 */
               // if(isInbound && uldForSegment.getReceivedBags() == 0) {
                	if(isInbound && (uldForSegment.getMailBagInULDForSegments()==null || uldForSegment.getMailBagInULDForSegments().isEmpty())) {
                	/*
                	 * remove bulk containers with empty manifested bags because
                	 * this can happen only at arrival.
                	 * For ULDs it's possible to accept empty ULDs
                	 * at the origin. So we need to check for the acceptanceflag in the master
                	 */
                	if (uldForSegment.getUldForSegmentPk().getUldNumber()
                            .startsWith(MailConstantsVO.CONST_BULK) &&
                            (uldForSegment.getMailBagInULDForSegments()==null || uldForSegment.getMailBagInULDForSegments().isEmpty())) {
                		this.getContainersForSegment().remove(uldForSegment);
                        uldForSegment.remove();
                        log.log(Log.FINE, "uldforseg removed");
                    } else if (!(uldForSegment.getUldForSegmentPk().getUldNumber()
                            .startsWith(MailConstantsVO.CONST_BULK))){
	                	ContainerDetailsVO containerDetailsVO =
	                		constructULDToReturnForMailbag(
	                				uldForSegmentPK, mailbagsToRem);
	                	if(checkForFoundULD(containerDetailsVO)) {
	                		containersToReturn.add(containerDetailsVO);
	                	}
                    }
                } else  if (!isInbound && uldForSegment.getNumberOfBags() == 0) {
                    if (uldForSegment.getUldForSegmentPk().getUldNumber()
                            .startsWith(MailConstantsVO.CONST_BULK)) {
              // Commented as part of BUG ICRD-113459 by A-5526 starts
                    //	this.getContainersForSegment().remove(uldForSegment);
                    //   uldForSegment.remove();
                        log.log(Log.FINE, "uldforseg removed");
                     // Commented as part of BUG ICRD-113459 by A-5526 ends
                    } else {
                        containersToReturn
                        .add(constructULDToReturnForMailbag(
                                uldForSegmentPK, mailbagsToRem));
                    }
                }
            }
        } catch (FinderException ex) {
            throw new SystemException(ex.getMessage(), ex);
        }

       // updateDSNForSegments(dsnForSegmentMap, false);
        log.exiting("AssignedFlightSegment", "reassignMailbagsFromFlight");
        return containersToReturn;
    }




    /**

     * Checks if this is a found container, i.e., a ULD added at the destination
     * Dec 6, 2006, a-1739
     * @param containerDetailsVO
     * @return
     * @throws SystemException
     */
	private boolean checkForFoundULD(ContainerDetailsVO containerDetailsVO)
		throws SystemException {
		log.entering("AssignedFlightSegment", "checkForFoundContainer");
		ContainerVO containerVO =
			constructContainerVOFromDetails(containerDetailsVO);
		Container container = null;
		try {
			container = Container.find(constructContainerPK(containerVO));
		} catch(FinderException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		return (MailConstantsVO.FLAG_NO.equals(container.getAcceptanceFlag()) &&
				MailConstantsVO.FLAG_YES.equals(container.getArrivedStatus()));
	}



	   /**
     * Copies the details from containerdetailsVO and Finds the legserial number
     * of this container too A-1739
     *
     * @param containerDetailsVO
     * @return
     * @throws SystemException
     */
    private ContainerVO constructContainerVOFromDetails(
            ContainerDetailsVO containerDetailsVO) throws SystemException {
        ContainerVO containerVO = new ContainerVO();
        assignedFlightSegmentPK = getAssignedFlightSegmentPK();

        containerVO.setCompanyCode(assignedFlightSegmentPK.getCompanyCode());
        containerVO.setCarrierId(assignedFlightSegmentPK.getCarrierId());
        containerVO.setFlightNumber(assignedFlightSegmentPK.getFlightNumber());
        containerVO
        .setFlightSequenceNumber(assignedFlightSegmentPK.getFlightSequenceNumber());
        containerVO
        .setSegmentSerialNumber(assignedFlightSegmentPK.getSegmentSerialNumber());

        containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
        containerVO.setType(containerDetailsVO.getContainerType());
        containerVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
        containerVO.setAssignedPort(containerDetailsVO.getPol());
        containerVO.setFinalDestination(containerDetailsVO.getDestination());
        containerVO.setRemarks(containerDetailsVO.getRemarks());
        // not accepted
        containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
        /* START
         * ADDED FOR SB -- ARRIVED
         * The below written code for Setting ArrivedStatus
         * is rewritten,by fetching value from the User.
         */
        //Modified as part of bug ICRD-131199
        containerVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
        /*if(ContainerDetailsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())){
        	 containerVO.setArrivedStatus(containerDetailsVO.getArrivedStatus());
        }else{
        	containerVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
        }*/
        containerVO.setDeliveredStatus(containerDetailsVO.getDeliveredStatus());
        //END
        containerVO.setAssignedDate(new LocalDate(containerDetailsVO.getPou(),
                Location.ARP, true));
        containerVO.setPou(containerDetailsVO.getPou());
        containerVO.setCarrierCode(containerDetailsVO.getCarrierCode());

        if(containerDetailsVO.getLegSerialNumber() == 0) {
          FlightValidationVO flightValidationVO = validateFlightForContainer(containerDetailsVO);
          containerVO.setLegSerialNumber(
              flightValidationVO.getLegSerialNumber());
        } else {
          containerVO.setLegSerialNumber(
              containerDetailsVO.getLegSerialNumber());
        }
        /*
         * Added By RENO
         * FOR M39 (CARDIT_1.2/RESDIT_1.1)
         */
        containerVO.setContainerJnyID(containerDetailsVO.getContainerJnyId());
        containerVO.setShipperBuiltCode(containerDetailsVO.getPaCode());

        /*
         * FOR INTACT
         */
        containerVO.setIntact(containerDetailsVO.getIntact());
        if(containerDetailsVO.getLastUpdateTime()!=null)
        	containerVO.setLastUpdateTime(containerDetailsVO.getLastUpdateTime());
        if(containerDetailsVO.getUldLastUpdateTime()!=null)
        containerVO.setULDLastUpdateTime(containerDetailsVO.getUldLastUpdateTime());    

        return containerVO;
    }

	/**
     * @author a-1739
     * @param uldForSegmentPK
     * @param mailbags
     * @return
     */
    private ContainerDetailsVO constructULDToReturnForMailbag(
            ULDForSegmentPK uldForSegmentPK, Collection<MailbagVO> mailbags) {
        ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
        containerDetailsVO.setCompanyCode(uldForSegmentPK.getCompanyCode());
        containerDetailsVO.setCarrierId(uldForSegmentPK.getCarrierId());
        containerDetailsVO.setFlightNumber(uldForSegmentPK.getFlightNumber());
        containerDetailsVO
        .setFlightSequenceNumber(uldForSegmentPK.getFlightSequenceNumber());
        containerDetailsVO
        .setSegmentSerialNumber(uldForSegmentPK.getSegmentSerialNumber());
        containerDetailsVO.setContainerNumber(uldForSegmentPK.getUldNumber());
        containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);

        for (MailbagVO mailbagVO : mailbags) {
            containerDetailsVO.setDestination(mailbagVO.getFinalDestination());
            containerDetailsVO.setPou(getPou());
            containerDetailsVO.setPol(getPol());
            containerDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
            containerDetailsVO.setFlightDate(mailbagVO.getFlightDate());
            containerDetailsVO.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
            containerDetailsVO.setFlagPAULDResidit(mailbagVO.getFlagPAULDResidit());
            break;
        }
        return containerDetailsVO;
    }

    /**
     * A-1739
     *
     * @param mailbags
     * @return
     * @throws SystemException
     */
    private Map<ULDForSegmentPK, Collection<MailbagVO>> groupMailbagsOfULDSeg(
            Collection<MailbagVO> mailbags) throws SystemException {
        Map<ULDForSegmentPK, Collection<MailbagVO>> mailbagsOfULDMap =
        	new HashMap<ULDForSegmentPK, Collection<MailbagVO>>();
        Collection<MailbagVO> mailbagsOfULD = null;
        for (MailbagVO mailbagVO : mailbags) {
            ULDForSegmentPK uldForSegPK = constructULDForSegPKFromMailbag(mailbagVO);
            mailbagsOfULD = mailbagsOfULDMap.get(uldForSegPK);
            if (mailbagsOfULD == null) {
                mailbagsOfULD = new ArrayList<MailbagVO>();
                mailbagsOfULDMap.put(uldForSegPK, mailbagsOfULD);
            }
            mailbagsOfULD.add(mailbagVO);
        }
        return mailbagsOfULDMap;
    }

    /**
     * A-1739
     *
     * @param mailbagVO
     * @return
     * @throws SystemException
     */
    private ULDForSegmentPK constructULDForSegPKFromMailbag(MailbagVO mailbagVO) throws SystemException {
        log.log(Log.FINE, "THE MAIL BAG VO IS", mailbagVO);
		ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
        assignedFlightSegmentPK = getAssignedFlightSegmentPK();
        uldForSegPK.setCompanyCode(   assignedFlightSegmentPK.getCompanyCode());
        uldForSegPK.setCarrierId(   assignedFlightSegmentPK.getCarrierId());
        uldForSegPK.setFlightNumber(   assignedFlightSegmentPK.getFlightNumber());
        uldForSegPK.setFlightSequenceNumber(   assignedFlightSegmentPK.getFlightSequenceNumber());
        uldForSegPK.setSegmentSerialNumber(   assignedFlightSegmentPK.getSegmentSerialNumber());
        uldForSegPK.setUldNumber(   mailbagVO.getContainerNumber());
        if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
            uldForSegPK.setUldNumber(   constructBulkULDNumber());
        }
/*        uldForSegPK.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr
        				(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));*/
        return uldForSegPK;
    }

    


    /**
     * @author a-1739
     * @param containerVO
     * @return
     */
    private ContainerPK constructContainerPK(ContainerVO containerVO) {
        ContainerPK containerPK = new ContainerPK();
        containerPK.setCompanyCode(   containerVO.getCompanyCode());
        containerPK.setCarrierId(   containerVO.getCarrierId());
        containerPK.setContainerNumber(   containerVO.getContainerNumber());
        containerPK.setAssignmentPort(   containerVO.getAssignedPort());
        containerPK.setFlightNumber(   containerVO.getFlightNumber());
        containerPK.setFlightSequenceNumber(   containerVO
        .getFlightSequenceNumber());
        containerPK.setLegSerialNumber(   containerVO.getLegSerialNumber());
        return containerPK;
    }

    /**
     * A-1739
     *
     * @param containerDetailsVO
     * @return
     * @throws SystemException
     */
    private FlightValidationVO validateFlightForContainer(
            ContainerDetailsVO containerDetailsVO) throws SystemException {
        Collection<FlightValidationVO> flightValidationVOs = null;

        flightValidationVOs = Proxy.getInstance().get(FlightOperationsProxy.class)
        .validateFlightForAirport(constructFlightFilterVOForContainer(containerDetailsVO));

        for (FlightValidationVO flightValidationVO : flightValidationVOs) {
            if (flightValidationVO.getFlightSequenceNumber() == containerDetailsVO
                    .getFlightSequenceNumber()) {
                return flightValidationVO;
            }
        }
        return null;
    }

    /**
     * A-1739
     *
     * @param containerDetailsVO
     * @return
     */
    private FlightFilterVO constructFlightFilterVOForContainer(
            ContainerDetailsVO containerDetailsVO) {
        FlightFilterVO flightFilterVO = new FlightFilterVO();
        flightFilterVO.setCompanyCode(containerDetailsVO.getCompanyCode());
        flightFilterVO.setFlightCarrierId(containerDetailsVO.getCarrierId());
        flightFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
        flightFilterVO.setFlightDate(containerDetailsVO.getFlightDate());
                flightFilterVO.setDirection(FlightFilterVO.INBOUND);
        flightFilterVO.setStation(containerDetailsVO.getPou());
        return flightFilterVO;
    }

    /**
     * @author a-1739
     * @param mailbagsToAdd
     * @param toContainerVO
     * @param dsnAtAirportMap
     * @throws SystemException
     */
    public void reassignMailToFlight(Collection<MailbagVO> mailbagsToAdd,
            ContainerVO toContainerVO)
    throws SystemException {
        log.entering("AssignedFlightSegment", "reassignMailToFlight");

        String uldNumber = toContainerVO.getContainerNumber();
        if (MailConstantsVO.BULK_TYPE.equals(toContainerVO.getType())) {
            uldNumber = constructBulkULDNumber();
        }
        ULDForSegment uldForSegment = findULDForSegmentWithULDNumber(uldNumber);
        if (uldForSegment == null) {
            uldForSegment = createULDForSegment(constructULDForSegmentFromCon(
                    toContainerVO, uldNumber));
            //Added by A-7794 as part of ICRD-208677
            boolean isUMSUpdateNeeded = new MailController().isULDIntegrationEnabled();
            if (isUMSUpdateNeeded && MailConstantsVO.ULD_TYPE.equals(toContainerVO.getType())) {
            	updateULDForOperations(toContainerVO);
                   		}

            if(getContainersForSegment()==null){
            	setContainersForSegment(new HashSet<ULDForSegment>());
            }
            getContainersForSegment().add(uldForSegment);
        }
       
   

        uldForSegment.reassignMailToFlight(mailbagsToAdd,toContainerVO);

       // updateDSNForSegments(dsnSegmentMap, true);

        log.exiting("AssignedFlightSegment", "reassignMailToFlight");
    }



    /**
     * A-1739
     *
     * @param uldNumber
     * @return
     */
    private ULDForSegment findULDForSegmentWithULDNumber(String uldNumber) {
        if (getContainersForSegment() != null) {
            for (ULDForSegment uldForSegment : containersForSegment) {
                if (uldForSegment.getUldForSegmentPk().getUldNumber()
                        .equals(uldNumber)) {
                    return uldForSegment;
                }
            }
        }
        return null;
    }

    /**
     * A-1739
     *
     * @param toContainerVO
     * @param uldNumber
     * @return
     */
    private ULDForSegmentVO constructULDForSegmentFromCon(
            ContainerVO toContainerVO, String uldNumber) {
        AssignedFlightSegmentPK assignedFlightSegPK = getAssignedFlightSegmentPK();
        ULDForSegmentVO uldForSegVO = new ULDForSegmentVO();
        uldForSegVO.setUldNumber(uldNumber);
        uldForSegVO.setCompanyCode(assignedFlightSegPK.getCompanyCode());
        uldForSegVO.setCarrierId(assignedFlightSegPK.getCarrierId());
        uldForSegVO.setFlightNumber(assignedFlightSegPK.getFlightNumber());
        uldForSegVO
        .setFlightSequenceNumber(assignedFlightSegPK.getFlightSequenceNumber());
        uldForSegVO
        .setSegmentSerialNumber(assignedFlightSegPK.getSegmentSerialNumber());
        return uldForSegVO;
    }


	public static int findNumberOfBarrowsPresentinFlightorCarrier(ContainerVO flightAssignedContainerVO) 	throws SystemException{
		try{
			return constructDAO().findNumberOfBarrowsPresentinFlightorCarrier(flightAssignedContainerVO);
		}catch(PersistenceException e){
		throw new SystemException(e.getMessage(), e);
		}

	}

    /**
     * @author a-1739
     * @param uldSegment
     * @throws SystemException
     */
    public void removeULDForSegment(ULDForSegment uldSegment)
    throws SystemException {
        uldSegment.remove();
    }


	/**
	 * @author a-1739
	 * @param uldForSegment
	 * @param containerNumber
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailbagInULDForSegmentVO> reassignBulkContainer(
			ULDForSegment uldForSegment, String containerNumber)
			throws SystemException {

		return uldForSegment.reassignBulkContainer(containerNumber);
	}

    /**
     * @author a-1739
     * @param toULDForSegment
     * @param dsnsToReassign
     * @throws SystemException
     */
    public void assignBulkContainer(ULDForSegment toULDForSegment,
            Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs)
    throws SystemException {
        toULDForSegment.assignBulkContainer(mailbagInULDForSegmentVOs);
    }


    /**
     * @author a-1883
     * @param containerVOs
     * @param uLDForSegmentVOs
     * @throws SystemException
     */
    public void saveOutboundDetailsForTransfer(Collection<ContainerVO>
    containerVOs,Collection<ULDForSegmentVO> uLDForSegmentVOs)throws SystemException {
        log.entering("AssignedFlightSegment", "saveOutboundDetailsForTransfer");


        for(ULDForSegmentVO uldForSegmentVO :uLDForSegmentVOs){
            for(ContainerVO containerVO:containerVOs){
            	String bulkNumber=null;
                if(uldForSegmentVO.getUldNumber().equals(containerVO.
                        getContainerNumber())){
                    ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
                    AssignedFlightSegmentPK assignedFlightSegmentPk = this.
                    getAssignedFlightSegmentPK();
                    uLDForSegmentPK.setCarrierId(   assignedFlightSegmentPk.getCarrierId());
                    uLDForSegmentPK.setCompanyCode(   assignedFlightSegmentPk.getCompanyCode());
                    uLDForSegmentPK.setFlightNumber(   assignedFlightSegmentPk.getFlightNumber());
                    uLDForSegmentPK.setFlightSequenceNumber(
                    	assignedFlightSegmentPk.getFlightSequenceNumber());
                    uLDForSegmentPK.setSegmentSerialNumber(
                    	assignedFlightSegmentPk.getSegmentSerialNumber());
                  //Added by A-8893 for IASCB-38903 starts
                    if(containerVO.isUldTobarrow()){
                    	 bulkNumber = constructBulkULDNumber(
                  			  containerVO.getFinalDestination(),
                  			  containerVO.getCarrierCode());
              			 if(bulkNumber!=null){
              				uLDForSegmentPK.setUldNumber(bulkNumber);
              				}
                    
                    }else{
                    uLDForSegmentPK.setUldNumber(   containerVO.getContainerNumber());
                    }
                  //Added by A-8893 for IASCB-38903 ends
                    ULDForSegment  uldForSegment = null;
                    //Modified for ICRD-126626
                        try {
							uldForSegment =  findULDForSegment(uLDForSegmentPK);
						} catch (FinderException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}

                        if(uldForSegment!=null){
                        //Modified by A-8893 for IASCB-59946 starts
                        if(containerVO.isUldTobarrow()){
                          uldForSegment.populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
                         }
                         else{
                        uldForSegment.saveOutboundDetailsForTransfer(
                            	uldForSegmentVO.getDsnInULDForSegmentVOs(),containerVO);
                         }
                        //Modified by A-8893 for IASCB-59946 ends	
                        uldForSegment.saveOutboundDetailsForTransfer(
                        		uldForSegmentVO.getDsnInULDForSegmentVOs(),containerVO);
                         
/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 *///added by A-7371
                        if(uldForSegment.getTransferFromCarrier()!=null && !uldForSegment.getTransferFromCarrier().equals(containerVO.getCarrierCode())){
                      uldForSegment.setTransferFromCarrier(
                    		  containerVO.getCarrierCode());
                        }

                    }else{
                        modifyULDForSegmentVO(uldForSegmentVO,containerVO);
                      //Added by A-8893 for IASCB-38903 starts
                        if(containerVO.isUldTobarrow()){
                        	 bulkNumber = constructBulkULDNumber(
                      			  containerVO.getFinalDestination(),
                      			  containerVO.getCarrierCode());
                  			 if(bulkNumber!=null){
                  				uldForSegmentVO.setUldNumber(bulkNumber);
                  				}
                        
                        }
                      //Added by A-8893 for IASCB-38903 ends
                        
						if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
								&& !uldForSegmentVO.getMailbagInULDForSegmentVOs().isEmpty()) {
							uldForSegmentVO.getMailbagInULDForSegmentVOs()
									.forEach(mailbagInULDForSegmentVO -> mailbagInULDForSegmentVO
											.setGhttim(containerVO.getGHTtime()));//IASCB-48967
						}
                        
                        uldForSegment = new ULDForSegment(uldForSegmentVO);
                        //Added by A-7794 as part of ICRD-208677
                        if(MailConstantsVO.ULD_TYPE.equals(containerVO
                                .getType())){
                        	boolean isUMSUpdateNeeded = new MailController().isULDIntegrationEnabled();
                        if (isUMSUpdateNeeded && OPERATION_FLAG_INSERT.equals(containerVO
                                .getOperationFlag())) {
                        	updateULDForOperations(containerVO);
                        }
                        }
                      //  uldForSegment.updateDSNForSegmentVOs(dsnSegmentMap);
                        if(getContainersForSegment()==null){
                        	log.log(Log.FINE,"ADDDED  THE CONTAINERS TO THE SEGMENT");
                        	setContainersForSegment(new HashSet<ULDForSegment>());

                        }
                        getContainersForSegment().add(uldForSegment);
                    }

                    break;
                }
            }// Containers Loop
        }

       // updateDSNForSegments(dsnSegmentMap, true);
        log.exiting("AssignedFlightSegment", "saveOutboundDetailsForTransfer");
    }


    /**
     * @author a-1936 This method is used to reassign the DSN from Flight
     * @param despatchDetailsVOs
     * @return
     * @throws SystemException
     */
    public Collection<ContainerDetailsVO> reassignDSNsFromFlight(
            Collection<DespatchDetailsVO> despatchDetailsVOs)
            throws SystemException {
        log.entering("AssignedFlightSegment", "reassignDSNFromFlight");
      /*  Map<DSNForSegmentPK, DSNForSegmentVO> dsnForSegmentMap =
            new HashMap<DSNForSegmentPK, DSNForSegmentVO>();*/
        Map<ULDForSegmentPK, Collection<DespatchDetailsVO>> uldForSegmentMap =
            new HashMap<ULDForSegmentPK, Collection<DespatchDetailsVO>>();


        /*
         * Oct 3, 2006
         * Check if mailbags are outbound or inbound.
         * Outbound is normal for this method.
         * But this method can be invoked during inbound too since
         *  returning can happen at the inbound port.
         *  If it's inbound don't touch the manifested bags.
         *  Only reduce the received bags at the port
         *  And no uld removal too
         */
        boolean isInbound = false;


        for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
            ULDForSegmentPK uldForSegmentPK =
                constructULDForSegmentPK(despatchDetailsVO);
            Collection<DespatchDetailsVO> despatchDetailVos =
                uldForSegmentMap.get(uldForSegmentPK);
            if (despatchDetailVos == null) {
                despatchDetailVos = new ArrayList<DespatchDetailsVO>();
                uldForSegmentMap.put(uldForSegmentPK, despatchDetailVos);
            }
            despatchDetailVos.add(despatchDetailsVO);

            if(MailConstantsVO.OPERATION_INBOUND.equals(
                    despatchDetailsVO.getOperationType())) {
                isInbound = true;
            }
        }

        Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();

        if (uldForSegmentMap != null && uldForSegmentMap.size() > 0) {

            for (ULDForSegmentPK uldForSegmentPK : uldForSegmentMap.keySet()) {
                Collection<DespatchDetailsVO> despatchDetailsFromMap = uldForSegmentMap
                .get(uldForSegmentPK);
                ULDForSegment uldForSegment = null;
                try {
                    uldForSegment = ULDForSegment.find(uldForSegmentPK);
                } catch (FinderException ex) {
                    throw new SystemException(ex.getMessage(), ex);
                }
               

                uldForSegment.reassignDSNsFromFlight(despatchDetailsFromMap,
                         isInbound);

                if (uldForSegment.getNumberOfBags() == 0 && !isInbound) {
                    if (uldForSegment.getUldForSegmentPk().getUldNumber()
                            .startsWith(MailConstantsVO.CONST_BULK)) {
                        uldForSegment.remove();
                    } else {
                        containersToReturn
                        .add(constructULDToReturnForDespatch(
                                uldForSegmentPK, despatchDetailsFromMap));
                    }
                }

            }
        }
       // updateDSNForSegments(dsnForSegmentMap, false);
        log.exiting("AssignedFlightSegment", "reassignDSNsFromFlight");
        return containersToReturn;
    }
    /**
    *
    * @param uldForSegmentVO
    * @param containerVO
    * @throws SystemException
    */
   private void modifyULDForSegmentVO(ULDForSegmentVO uldForSegmentVO ,
           ContainerVO containerVO)throws SystemException {
       log.entering("AssignedFlightSegment", "modifyULDForSegmentVO");
       AssignedFlightSegmentPK assignedFlightSegmentPk = this.
       getAssignedFlightSegmentPK();
       uldForSegmentVO.setCarrierId(assignedFlightSegmentPk.getCarrierId());
       uldForSegmentVO.setCompanyCode(assignedFlightSegmentPk.getCompanyCode());
       uldForSegmentVO.setFlightNumber(assignedFlightSegmentPk.getFlightNumber());
       uldForSegmentVO.setFlightSequenceNumber(
       	assignedFlightSegmentPk.getFlightSequenceNumber());
       uldForSegmentVO.setSegmentSerialNumber(assignedFlightSegmentPk.getSegmentSerialNumber());
       uldForSegmentVO.setRemarks(containerVO.getRemarks());
       uldForSegmentVO.setPou(this.getPou());

       Collection<OnwardRoutingVO>  onwardRoutingVOs =
           containerVO.getOnwardRoutings();
       if(onwardRoutingVOs != null && onwardRoutingVOs.size() > 0 ){
           addNewRoutingDetails(onwardRoutingVOs,uldForSegmentVO);
       }
     //Added by A-8893 for IASCB-38903 starts
       if(containerVO.isUldTobarrow()){
    	  String bulkNumber = constructBulkULDNumber(
    			  containerVO.getFinalDestination(),
    			  containerVO.getCarrierCode());
			 if(bulkNumber!=null){
				 uldForSegmentVO.setUldNumber(bulkNumber);
				}
       }
       else{
           uldForSegmentVO.setUldNumber(containerVO.getContainerNumber());
       }
     //Added by A-8893 for IASCB-38903 ends
   }
   
	private String constructBulkULDNumber(String airport, String carrierCode) {
		/*
		 * This "airport" can be the POU / Destination
		 */
		if (airport != null && airport.trim().length() > 0) {
			return new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(airport)
					.toString();
		} else {
			return MailConstantsVO.CONST_BULK_ARR_ARP.concat(
					MailConstantsVO.SEPARATOR).concat(carrierCode);
		}
	}


   /**
   *
   * @param onwardRoutingVOs
   * @param uLDForSegmentVO
   * @throws SystemException
   */
  private void addNewRoutingDetails(Collection<OnwardRoutingVO> onwardRoutingVOs,
          ULDForSegmentVO uLDForSegmentVO)throws SystemException {
      log.entering("AssignedFlightSegment", "addNewRoutingDetails");
      Collection<OnwardRouteForSegmentVO>  onwardRouteForSegmentVOs =
          new ArrayList<OnwardRouteForSegmentVO>();
      if(uLDForSegmentVO.getOnwardRoutings() != null &&
              uLDForSegmentVO.getOnwardRoutings().size() > 0){
          uLDForSegmentVO.getOnwardRoutings().clear();
      }
      for(OnwardRoutingVO onwardRoutingVO:onwardRoutingVOs){
          OnwardRouteForSegmentVO onwardRouteForSegmentVO =
              new OnwardRouteForSegmentVO();
          onwardRouteForSegmentVO.setOnwardCarrierCode(onwardRoutingVO.
                  getOnwardCarrierCode());
          onwardRouteForSegmentVO.setOnwardCarrierId(onwardRoutingVO.
                  getOnwardCarrierId());
          onwardRouteForSegmentVO.setOnwardFlightDate(onwardRoutingVO.
                  getOnwardFlightDate());
          onwardRouteForSegmentVO.setOnwardFlightNumber(onwardRoutingVO.
                  getOnwardFlightNumber());
          onwardRouteForSegmentVO.setPou(onwardRoutingVO.getPou());
          onwardRouteForSegmentVO.setRoutingSerialNumber(onwardRoutingVO.
                  getRoutingSerialNumber());
          onwardRouteForSegmentVOs.add(onwardRouteForSegmentVO);
      }
      uLDForSegmentVO.setOnwardRoutings(onwardRouteForSegmentVOs);
      log.exiting("AssignedFlightSegment", "addNewRoutingDetails");
  }


    /**
     * This method is used to create the ULDForSegmentPK from the
     * despatchDetailsVO
     *
     * @param despatchDetailsVO
     * @return
     */
    private ULDForSegmentPK constructULDForSegmentPK(
            DespatchDetailsVO despatchDetailsVO) {
        ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
        uldForSegPK.setCompanyCode(   assignedFlightSegmentPK.getCompanyCode());
        uldForSegPK.setCarrierId(   assignedFlightSegmentPK.getCarrierId());
        uldForSegPK.setFlightNumber(   assignedFlightSegmentPK.getFlightNumber());
        uldForSegPK.setFlightSequenceNumber(   assignedFlightSegmentPK.getFlightSequenceNumber());
        uldForSegPK.setSegmentSerialNumber(   assignedFlightSegmentPK.getSegmentSerialNumber());
        uldForSegPK.setUldNumber(   despatchDetailsVO.getContainerNumber());
        if (MailConstantsVO.BULK_TYPE.equals(despatchDetailsVO
                .getContainerType())) {
            uldForSegPK.setUldNumber(   constructBulkULDNumber());
        }
        return uldForSegPK;
    }

  /**
   * @author a-1883
   * @param containerVOs
   * @param dSNMap
   * @param toFlightVO TODO
   * @throws SystemException
   * @return Collection<ULDForSegmentVO>
   */
  public Collection<ULDForSegmentVO> saveArrivalDetailsForTransfer(
  		Collection<ContainerVO> containerVOs,
  		OperationalFlightVO toFlightVO,Map<MailbagPK,MailbagVO> mailMap) throws SystemException {
      log.entering("AssignedFlightSegment", "saveArrivalDetailsForTransfer");
      Container container = null;

      Collection<ULDForSegmentVO> uLDForSegmentVOs =
          new ArrayList<ULDForSegmentVO>();
      for(ContainerVO containerVO:containerVOs){
          ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
          uLDForSegmentPK.setCarrierId(   containerVO.getCarrierId());
          uLDForSegmentPK.setCompanyCode(   containerVO.getCompanyCode());
          uLDForSegmentPK.setFlightNumber(   containerVO.getFlightNumber());
          uLDForSegmentPK.setFlightSequenceNumber(   containerVO.
          getFlightSequenceNumber());
          uLDForSegmentPK.setSegmentSerialNumber(   containerVO.
          getSegmentSerialNumber());
          uLDForSegmentPK.setUldNumber(   containerVO.getContainerNumber());
          ULDForSegment  uldForSegment = null;
          //Modified for ICRD-126626
          try {
				uldForSegment =  findULDForSegment(uLDForSegmentPK);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}


          if(uldForSegment==null){
              throw new SystemException("	NO ULD FOR SEGMENT FOUND ");
          }
          /*
           * Added By Karthick V as the part of the Optimistic Locking..
           *
           */
          
          /**Added by A-4809 for ICRD-84408 Starts
           * No of bags and Weight need to updated
           * Arrival details to be updated when container is transferred.
           */
          
          //Added by A-4809 for ICRD-84408 Ends
          uLDForSegmentVOs.add(
          		updateTransferDetailsForULDSegVO(uldForSegment.retrieveVO(),
          				containerVO));
          /* Updating the Received informations */
          /**
           * Boolean isArrived marked as false - change done by A-4809
           * This was done as part of BUG ICRD-84408
           * Arrival need to be marked while container is transferred
           * Import of data to MRA will not take place without arrival status
           * Received pieces and weight marked in MTKDSNULDSEG and ARRSTA=Y in MTKMALULDSEG
           * These changes occur as part of the boolean change
           */
          boolean isArrived = false ;
          container = findContainer(containerVO);
          /*
           * Added By Karthick V as the part of the Optimistic Locking..
           *
           */
          container.setLastUpdateTime(containerVO.getLastUpdateTime());
          container.setLastUpdateUser(containerVO.getLastUpdateUser());
          
        
          /**
           * commented for bug 82312 ends
           */
          if(!toFlightVO.isTransferOutOperation()){
          uldForSegment.setTransferToCarrier(toFlightVO.getCarrierCode());
          }
          else{
        	  isArrived=true;  
          }
          uldForSegment.saveArrivalDetailsForTransfer(mailMap,isArrived, toFlightVO);
          // added for bug 82312 starts
          uldForSegment.updateUldAcquittalStatus();
          if(MailConstantsVO.FLAG_YES.equals(uldForSegment.getReleasedFlag())){
          	container.setTransitFlag(MailConstantsVO.FLAG_NO);
          	if(toFlightVO.isTransferOutOperation()){   
          		container.setArrivedStatus(MailConstantsVO.FLAG_YES);
          	}
          }
          // added for bug 82312 ends
      }
     // updateDSNForSegments(dSNForSegmentMap,true);
      log.exiting("AssignedFlightSegment", "saveArrivalDetailsForTransfer");
      return uLDForSegmentVOs;
  }



	/**
  *
  * @param containerVO
  * @return
  * @throws SystemException
  */
 private Container findContainer(ContainerVO containerVO)
 throws SystemException{
     log.entering("MailTransfer", "findContainer");
     Container container = null;
     ContainerPK containerPK = new ContainerPK();
     containerPK.setAssignmentPort(   containerVO.getAssignedPort());
     containerPK.setCarrierId(   containerVO.getCarrierId());
     containerPK.setCompanyCode(   containerVO.getCompanyCode());
     containerPK.setContainerNumber(   containerVO.getContainerNumber());
     containerPK.setFlightNumber(   containerVO.getFlightNumber());
     containerPK.setFlightSequenceNumber(   containerVO.getFlightSequenceNumber());
     containerPK.setLegSerialNumber(   containerVO.getLegSerialNumber());
     try {
         container  = Container.find(containerPK);
     }catch(FinderException finderException){
         throw new SystemException(finderException.getErrorCode(),
                 finderException);
     }
     log.exiting("MailTransfer", "findContainer");
     return container ;
 }

	/**
	 * Oct 27, 2006, a-1739
	 * @param containerVO TODO
	 * @param segmentVO
	 * @return
	 * @throws SystemException 
	 */
	private ULDForSegmentVO updateTransferDetailsForULDSegVO(
			ULDForSegmentVO uldForSegmentVO, ContainerVO containerVO) throws SystemException {

		uldForSegmentVO.setTransferFromCarrier(containerVO.getCarrierCode());   
		Collection<DSNInULDForSegmentVO> dsnInULDForSegs =
			uldForSegmentVO.getDsnInULDForSegmentVOs();
		  Collection<MailbagInULDForSegmentVO> mailbagInULDForSegments = uldForSegmentVO.getMailbagInULDForSegmentVOs();
		if(dsnInULDForSegs!=null && dsnInULDForSegs.size()>0){
			for(DSNInULDForSegmentVO dsnInULDForSegmentVO:dsnInULDForSegs){
				Collection<MailbagInULDForSegmentVO>mailbagsInULD=dsnInULDForSegmentVO.getMailBags();
				if(mailbagsInULD != null && mailbagsInULD.size() >  0) {
					Collection<MailbagInULDForSegmentVO> mailbagsToRem =
						new ArrayList<MailbagInULDForSegmentVO>();
					for(MailbagInULDForSegmentVO mailbagInULD : mailbagsInULD) {
						if(MailConstantsVO.FLAG_YES.equals(mailbagInULD.getTransferFlag())||
		                    MailConstantsVO.FLAG_YES.equals(mailbagInULD.getDeliveredStatus())) {
							mailbagsToRem.add(mailbagInULD);
						} else {
		                  mailbagInULD.setArrivalFlag(MailConstantsVO.FLAG_NO);
		                  mailbagInULD.setTransferFlag(MailConstantsVO.FLAG_NO);
		                  mailbagInULD.setDeliveredStatus(MailConstantsVO.FLAG_NO);
		                  mailbagInULD.setTransferFromCarrier(containerVO.getCarrierCode());
						}
						//Added By paulson for stamping scan port for mailbags after transfer
						mailbagInULD.setScannedPort(containerVO.getPou());
						//Added by A-5945 for ICRD-97900
						mailbagInULD.setScannedDate(new LocalDate(containerVO.getPou(),
								Location.ARP, true));
						log.log(Log.FINE,"mailbagInULD.setScannedDate"+mailbagInULD.getScannedDate() );
					}
					mailbagsInULD.removeAll(mailbagsToRem);
				}
			}
		  }
		if(mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0) {
				   LogonAttributes logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
		            for(MailbagInULDForSegmentVO mailbagInULDForSegment : mailbagInULDForSegments) {
		            	mailbagInULDForSegment.setScannedPort(logonVO.getAirportCode());
		         }
		 }
		

      log.log(Log.FINEST, " uldforsgvotosaveob ", uldForSegmentVO);
		return uldForSegmentVO;
	}

	/**
     * @author a-1739
     * @param uldForSegmentPK
     * @param despatchDetails
     * @return
     */
    private ContainerDetailsVO constructULDToReturnForDespatch(
            ULDForSegmentPK uldForSegmentPK,
            Collection<DespatchDetailsVO> despatchDetails) {
        ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
        containerDetailsVO.setCompanyCode(uldForSegmentPK.getCompanyCode());
        containerDetailsVO.setCarrierId(uldForSegmentPK.getCarrierId());
        containerDetailsVO.setFlightNumber(uldForSegmentPK.getFlightNumber());
        containerDetailsVO
        .setFlightSequenceNumber(uldForSegmentPK.getFlightSequenceNumber());
        containerDetailsVO
        .setSegmentSerialNumber(uldForSegmentPK.getSegmentSerialNumber());
        containerDetailsVO.setContainerNumber(uldForSegmentPK.getUldNumber());
        containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);

        for (DespatchDetailsVO despatchDetailsVO : despatchDetails) {
            containerDetailsVO.setDestination(despatchDetailsVO
                    .getDestination());
            containerDetailsVO.setPou(despatchDetailsVO.getPou());
            containerDetailsVO.setPol(despatchDetailsVO.getAirportCode());
            containerDetailsVO.setCarrierCode(despatchDetailsVO
                    .getCarrierCode());
            containerDetailsVO.setFlightDate(despatchDetailsVO.getFlightDate());
            break;
        }
        return containerDetailsVO;
    }
    

    
/**
     * @param mailbagVOs
     * @param containerVO
     * @param dsnAtAirportMap
     * @throws SystemException
     */
    public void saveMailArrivalDetailsForTransfer(Collection<MailbagVO>
    mailbagVOs, ContainerVO containerVO)throws SystemException {
        log.entering("AssignedFlightSegment", "saveMailArrivalDetailsForTransfer");


        /* groups mailbags that are in same uld*/
        Map<ULDForSegmentPK,Collection<MailbagVO>> uldMailbagsMap =
                groupMailbagsOfULDSeg(mailbagVOs);

        for(ULDForSegmentPK uLDForSegmentPK :uldMailbagsMap.keySet()){
            ULDForSegment  uLDForSegment = null;
            Collection<MailbagVO> mailbagsInULD = uldMailbagsMap.get(
                    uLDForSegmentPK);
            try {
                uLDForSegment =  ULDForSegment.find(uLDForSegmentPK);
            }catch(FinderException finderException){
                throw new SystemException(
                		finderException.getErrorCode(), finderException);
            }
           

            uLDForSegment.saveMailArrivalDetailsForTransfer(mailbagsInULD, containerVO);
        }
     //   updateDSNForSegments(dSNForSegmentMap,true);

        log.exiting("AssignedFlightSegment", "saveMailArrivalDetailsForTransfer");
    }



    /**
     * @param containerVO
     * @return
     * @throws SystemException
     */
    private ULDForSegmentVO constructULDForSegmentVO(ContainerVO
            containerVO)throws SystemException {
        ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
        AssignedFlightSegmentPK assignedFlightSegmentPk = this.
        getAssignedFlightSegmentPK();
        uldForSegmentVO.setCarrierId(assignedFlightSegmentPk.getCarrierId());
        uldForSegmentVO.setCompanyCode(assignedFlightSegmentPk.getCompanyCode());
        uldForSegmentVO.setFlightNumber(assignedFlightSegmentPk.getFlightNumber());
        uldForSegmentVO.setFlightSequenceNumber(
        	assignedFlightSegmentPk.getFlightSequenceNumber());
        uldForSegmentVO.setSegmentSerialNumber(
        	assignedFlightSegmentPk.getSegmentSerialNumber());
        uldForSegmentVO.setUldNumber(containerVO.getContainerNumber());
        if(MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
        	uldForSegmentVO.setUldNumber(
        			constructBulkULDNumber());
        } else {
	        uldForSegmentVO.setRemarks(containerVO.getRemarks());
	        uldForSegmentVO.setPou(this.getPou());
	        Collection<OnwardRoutingVO>  onwardRoutingVOs =
	            containerVO.getOnwardRoutings();
	        if(onwardRoutingVOs != null && onwardRoutingVOs.size() > 0 ){
	            addNewRoutingDetails(onwardRoutingVOs,uldForSegmentVO);
	        }
        }
        return uldForSegmentVO;
    }

    /**
     * @param mailbagVOs
     * @param containerVO
     * @throws SystemException
     */
    public void saveOutboundMailsFlightForTransfer(
    	Collection<MailbagVO> mailbagVOs,
    	ContainerVO containerVO)throws SystemException {
        log.entering("AssignedFlightSegment", "saveOutboundMailsFlightForTransfer");
                    ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
                    ULDForSegmentVO uLDForSegmentVO =null;
                    AssignedFlightSegmentPK assignedFlightSegmentPk = this.
                    getAssignedFlightSegmentPK();
                    containerVO.setSegmentSerialNumber(assignedFlightSegmentPk.getSegmentSerialNumber());
                    uLDForSegmentPK.setCarrierId(   assignedFlightSegmentPk.getCarrierId());
                    uLDForSegmentPK.setCompanyCode(   assignedFlightSegmentPk.getCompanyCode());
                    uLDForSegmentPK.setFlightNumber(   assignedFlightSegmentPk.getFlightNumber());
                    uLDForSegmentPK.setFlightSequenceNumber(
                    	assignedFlightSegmentPk.getFlightSequenceNumber());
                    uLDForSegmentPK.setSegmentSerialNumber(
                    		assignedFlightSegmentPk.getSegmentSerialNumber());
                    uLDForSegmentPK.setUldNumber(   containerVO.getContainerNumber());
                    if(MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
                    	uLDForSegmentPK.setUldNumber(
                    		constructBulkULDNumber());
                    }
                    ULDForSegment  uLDForSegment = null;
                    try {
                        uLDForSegment =  ULDForSegment.find(uLDForSegmentPK);
                    }catch(FinderException finderException){
                        uLDForSegmentVO = constructULDForSegmentVO(containerVO);
                        uLDForSegment = new ULDForSegment(uLDForSegmentVO);
                      //Added by A-7794 as part of ICRD-208677
                        if(MailConstantsVO.ULD_TYPE.equals(containerVO
                                .getType())){
                        	boolean isUMSUpdateNeeded = new MailController().isULDIntegrationEnabled();
                        if (isUMSUpdateNeeded && OPERATION_FLAG_INSERT.equals(containerVO
                                .getOperationFlag())) {
                        	updateULDForOperations(containerVO);
                        }
                        }
                    }
                    
                    uLDForSegment.saveOutboundMailDetailsForTransfer(mailbagVOs,
                            containerVO);

		//updateDSNForSegments(dsnSegmentMap, true);
        log.exiting("AssignedFlightSegment", "saveOutboundMailsFlightForTransfer");
    }

   

	/**
     * A-1739
     *
     * @param segmentContainers
     * @param mailArrivalVO
     * @param exceptionMails this collection will be added with the mailbags of the ULD
     * 									which was a new one and caused an assignment exception
     * @return
     * @throws SystemException
     * @throws ContainerAssignmentException
	 * @throws DuplicateMailBagsException
     */
    public boolean saveArrivalDetails(
            Collection<ContainerDetailsVO> segmentContainers,
            MailArrivalVO mailArrivalVO, Collection<MailbagVO> exceptionMails ,
			   Map<String,Collection<MailbagVO>>         mailBagsMapForInventory,
			   Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory) throws SystemException,
            ContainerAssignmentException, DuplicateMailBagsException {
        log.entering("AssignedFlightSegment", "saveArrivalDetails");
        boolean isUpdated = false;
        for (ContainerDetailsVO containerDetailsVO : segmentContainers) {
            if (OPERATION_FLAG_INSERT.equals(containerDetailsVO
                    .getOperationFlag())) {
            	try {
	                isUpdated = true;
	                insertArrivedContainerDetails(containerDetailsVO,
	                         mailArrivalVO,
	         			 mailBagsMapForInventory,
	        			 despatchesMapForInventory);
	                MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagContainerAuditForArrival(mailArrivalVO);
            	} catch(ContainerAssignmentException ex) {
            		if(mailArrivalVO.isScanned()) {
            			if(containerDetailsVO.getMailDetails() != null) {
	            			updateMailbagVOsWithErr(
	            					containerDetailsVO.getMailDetails(),
	            					MailConstantsVO.ERR_MSG_NEW_ULD_ASG);
	            			exceptionMails.addAll(containerDetailsVO.getMailDetails());
            			}
            		} else {
            			throw ex;
            		}
            	}
            } else if (OPERATION_FLAG_UPDATE.equals(containerDetailsVO
                    .getOperationFlag())) {
                isUpdated = true;
                boolean isSaved = updateContainerArrivalDetails(containerDetailsVO,
                         mailArrivalVO,
         			  mailBagsMapForInventory,
        			  despatchesMapForInventory,mailArrivalVO.isScanned());
                MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
				mailController.flagContainerAuditForArrival(mailArrivalVO);
                if(mailArrivalVO.isScanned() && !isSaved) {
                	//for handling bulk from scanner
                	containerDetailsVO.setOperationFlag(OPERATION_FLAG_INSERT);
                	insertArrivedContainerDetails(containerDetailsVO,
	                         mailArrivalVO,
	         			    mailBagsMapForInventory,
	        			    despatchesMapForInventory);
                }
            }
        }
        //updateDSNForSegments(dsnForSegmentMap, true);
        log.exiting("AssignedFlightSegment", "saveArrivalDetails");
        return isUpdated;
    }
    /**
	 * TODO Purpose
	 * Oct 12, 2006, a-1739
	 * @param mailDetails
	 * @param err_msg_new_uld_asg
	 */
	private void updateMailbagVOsWithErr(
			Collection<MailbagVO> mailDetails, String errMsg) {
		log.entering("AssignedFlightSegment", "updateMailbagVOsWithErr");
		if(mailDetails != null && mailDetails.size() > 0) {
			for(MailbagVO mailbagVO : mailDetails) {
				mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
				mailbagVO.setErrorDescription(errMsg);
			}
		}
		log.exiting("AssignedFlightSegment", "updateMailbagVOsWithErr");
	}
	//Added by A-5945 for ICRD-118205 starts for updating entry from MTKULDSEG
		/**
	     * @author a-A-5945
	     * @param Collection<ContainerDetailsVO>
	     * @return
	     */
		public void undoArriveContainer(Collection<ContainerDetailsVO> containerDetailsVOs) throws SystemException{
			 log.entering("AssignedFlightSegment", "undoArriveContainer");
			for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
				Collection<MailbagVO> mailbagsforUndoArrival = containerDetailsVO.getMailDetails() ;
				Collection<MailbagVO> mailbagsforDSNUpadation = new ArrayList<MailbagVO>();
				  ULDForSegment uldForSegment = null;
				  ULDForSegmentPK uldForSegmentPK = 	constructULDForSegmentPK(containerDetailsVO);
				 try{
				 uldForSegment = ULDForSegment.find(uldForSegmentPK);
				 } catch (FinderException e){
					 log.log(Log.FINE, "FinderException....");
				 }
				//Modified the code  as part of bug ICRD-149146 by A-5526
				 if((uldForSegment!=null && uldForSegment.getNumberOfBags()==0 && uldForSegment.getWeight()==0) && "CON".equals(containerDetailsVO.getUndoArrivalFlag()) ){
					 uldForSegment.remove(); //Removing entry for found ulds
				 }else{
					 if(uldForSegment!=null && !(uldForSegment.getNumberOfBags()==0 && uldForSegment.getWeight()==0) && (!MailConstantsVO.FLAG_NO.equals(uldForSegment.getReleasedFlag()) )){
						 uldForSegment.setReleasedFlag(MailConstantsVO.FLAG_NO);
					 }
				 for(MailbagVO mailbag : mailbagsforUndoArrival){
					if(MailConstantsVO.FLAG_YES.equals(mailbag.getUndoArrivalFlag()) ){
						
						mailbagsforDSNUpadation.add(mailbag);
						}
					}
//				 if(mailbagsforDSNUpadation!=null && mailbagsforDSNUpadation.size()>0){
//				 uldForSegment.undoArriveContainer(containerDetailsVO,mailbagsforDSNUpadation);
//				 }
				 }
				 }
			 log.exiting("AssignedFlightSegment", "undoArriveContainer");
			}
		/**
	     * @author a-A-5945
	     * @param ContainerDetailsVO
	     * @return ULDForSegmentPK
	     */
		private ULDForSegmentPK constructULDForSegmentPK(
				ContainerDetailsVO containerDetailsVO) {
	        ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
	        uldForSegmentPK.setCompanyCode(   containerDetailsVO.getCompanyCode());
	        uldForSegmentPK.setUldNumber(   containerDetailsVO.getContainerNumber());
	        uldForSegmentPK.setCarrierId(   containerDetailsVO.getCarrierId());
	        uldForSegmentPK.setFlightNumber(   containerDetailsVO.getFlightNumber());
	        uldForSegmentPK.setFlightSequenceNumber(   containerDetailsVO
	        .getFlightSequenceNumber());
	        uldForSegmentPK.setSegmentSerialNumber(   containerDetailsVO
	        .getSegmentSerialNumber());
	        return uldForSegmentPK;
	    }
		/**
	     * A-1739
	     *
	     * @param containerDetailsVO
	     * @param dsnForSegmentMap
	     * @param mailArrivalVO
	     * @throws SystemException
	     * @throws ContainerAssignmentException
		 * @throws DuplicateMailBagsException
	     */
	    private void insertArrivedContainerDetails(
	            ContainerDetailsVO containerDetailsVO,
	            MailArrivalVO mailArrivalVO,
				   Map<String,Collection<MailbagVO>>         mailBagsMapForInventory,
				   Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory) throws SystemException,
	            ContainerAssignmentException, DuplicateMailBagsException {
	        log.entering("AssignedFlightSegment", "insertArrivedContainerDetails");
			    //commented for icrd-94608 BY a-4810
	        if (!containerDetailsVO.getContainerNumber().startsWith(
	                MailConstantsVO.CONST_BULK)) {
	            // exception will be thrown by this method
	            checkContainerAssignmentAtPol(containerDetailsVO);
	            /*
	             * No Container Assignment OR Not Accepted OR Accepted to Closed
	             * Flight
	             */
	            createContainerAssignment(containerDetailsVO, mailArrivalVO);
	        }
	        ULDForSegmentVO uldForSegmentVO =
	        	constructULDForSegmentFromDetails(containerDetailsVO);
	        uldForSegmentVO.setLastUpdateUser(mailArrivalVO.getArrivedUser());
	        // create uild forseg
	        ULDForSegment uldForSegment = null;
	        ULDForSegmentPK uldPK = new ULDForSegmentPK();
	        uldPK.setCarrierId(uldForSegmentVO.getCarrierId());
	        uldPK.setCompanyCode(uldForSegmentVO.getCompanyCode());
	        uldPK.setFlightNumber(uldForSegmentVO.getFlightNumber());
	        uldPK.setFlightSequenceNumber(uldForSegmentVO.getFlightSequenceNumber());
	        uldPK.setSegmentSerialNumber(uldForSegmentVO.getSegmentSerialNumber());
	        uldPK.setUldNumber(uldForSegmentVO.getUldNumber());
	        try{
	        	uldForSegment = ULDForSegment.find(uldPK);
	        }catch(FinderException exception){
	        	uldForSegment = new ULDForSegment(uldForSegmentVO);
	        }
	        //Added by A-7794 as part of ICRD-208677
            if(MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())){
            	boolean isUMSUpdateNeeded = new MailController().isULDIntegrationEnabled();
            if (isUMSUpdateNeeded && OPERATION_FLAG_INSERT.equals(containerDetailsVO
                    .getOperationFlag())) {
            	FlightDetailsVO flightDetailsVO = null;
			  	Collection<ULDInFlightVO> uldInFlightVos = null;
            	flightDetailsVO = new FlightDetailsVO();
      			flightDetailsVO
      					.setCompanyCode(containerDetailsVO.getCompanyCode());
      			flightDetailsVO.setFlightCarrierIdentifier(containerDetailsVO
      					.getCarrierId());
      			flightDetailsVO.setFlightDate(containerDetailsVO.getFlightDate());
      			flightDetailsVO.setFlightNumber(containerDetailsVO
      					.getFlightNumber());
      			flightDetailsVO.setFlightSequenceNumber(containerDetailsVO
      					.getFlightSequenceNumber());
      			flightDetailsVO
      					.setCarrierCode(containerDetailsVO.getCarrierCode());
      			flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
      			uldInFlightVos = new ArrayList<ULDInFlightVO>();
      			ULDInFlightVO uldFltVo = new ULDInFlightVO();
      			uldFltVo.setUldNumber(containerDetailsVO.getContainerNumber());
      			uldFltVo.setPointOfLading(containerDetailsVO.getPol());
    			uldFltVo.setPointOfUnLading(containerDetailsVO.getPou());
      			uldInFlightVos.add(uldFltVo);
      			flightDetailsVO.setUldInFlightVOs(uldInFlightVos);
      			flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
      			flightDetailsVO.setRemark(MailConstantsVO.MAIL_ULD_ASSIGNED);
      			flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
      			try {
      				new ULDDefaultsProxy().updateULDForOperations(flightDetailsVO);
      			} catch (ULDDefaultsProxyException uldDefaultsException) {
      				throw new SystemException(uldDefaultsException.getMessage());
      			}
            }
            }
	        
	        // save dsns, mailbags, despatches
	        uldForSegment.saveArrivalDetails(containerDetailsVO,mailArrivalVO,
	 			   mailBagsMapForInventory,
	 			   despatchesMapForInventory);
	        /*
	         * Acquittal of ULD after arriving all the mails inside the uld.
	         * START
	         */
	        uldForSegment.updateUldAcquittalStatus();
	        if(MailConstantsVO.FLAG_YES.equals(uldForSegment.getReleasedFlag())){
	        	try{
	        		Container containerToUpdate = null;
	        		containerToUpdate = findContainer(constructContainerVOFromDetails(containerDetailsVO));
	                /* THIS MEANS THAT THIS CONTAINER IS NO MORE ASSOSIATED TO THIS FLIGHT*/
	        		containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
	        		containerToUpdate.setLastUpdateUser(mailArrivalVO.getArrivedUser());
	        		 
	        	}catch(SystemException systemException){
	        		/* NO NEED TO THROW THIS EXCEPTION TO CLIENT*/
	        	}
	        }
	        // END
	        log.exiting("AssignedFlightSegment", "insertArrivedContainerDetails");
	    }
	    /**
	     * A-1739 This method checks if there is already a container assigned at the
	     * other port If it accepted If it is in a closed flight
	     *
	     * @param containerDetailsVO
	     * @throws SystemException
	     * @throws ContainerAssignmentException
	     */
	    private void checkContainerAssignmentAtPol(
	            ContainerDetailsVO containerDetailsVO) throws SystemException,
	            ContainerAssignmentException {
	        log.entering("AssignedFlightSegment", "checkContainerAssignmentAtPol");
	        assignedFlightSegmentPK = getAssignedFlightSegmentPK();
	        ContainerAssignmentVO containerAsgVO = Container
	        .findContainerAssignment(assignedFlightSegmentPK.getCompanyCode(),
	                containerDetailsVO.getContainerNumber(),
	                containerDetailsVO.getPol());
	        if (containerAsgVO != null) {
	            if (MailConstantsVO.FLAG_YES.equals(containerAsgVO
	                    .getAcceptanceFlag())) {
	            	if (containerAsgVO.getFlightSequenceNumber()==MailConstantsVO.DESTN_FLT) {
	                	throw new ContainerAssignmentException(
	                            ContainerAssignmentException.DESTN_ASSIGNED,
	                            new Object[] {containerAsgVO.getContainerNumber(),
	                            		containerDetailsVO.getPol()});
	                }
	            }
	        }
	        log.exiting("AssignedFlightSegment", "checkContainerAssignmentAtPol");
	    }
	    /**
	     * A-1739
	     *
	     * @param containerDetailsVO
	     * @param mailArrivalVO
	     * @throws SystemException
	     */
	    private void createContainerAssignment(
	            ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO)
	    throws SystemException {
	        log.entering("AssignedFlightSegment", "createContainerAssignment");
	        ContainerVO containerVO = constructContainerVOFromDetails(containerDetailsVO);
	        containerVO.setAssignedUser(mailArrivalVO.getArrivedUser());
	        containerVO.setLastUpdateUser(mailArrivalVO.getArrivedUser());
	        containerVO.setRemarks(MailConstantsVO.MAIL_ULD_ARRIVED);
	        validateAndCreateContainer(containerVO);
	        log.exiting("AssignedFlightSegment", "createContainerAssignment");
	    }
	    /**
		 * @author a-1936 This methodo is used to update the Container or create and
		 *         update the Container for OFFload
		 * @param toDestinationContainerVO
		 * @throws SystemException
		 */
		private void validateAndCreateContainer(ContainerVO containerVO)
				throws SystemException {
			 ContainerPK containerPk = new ContainerPK();
			 containerPk.setCompanyCode(   containerVO.getCompanyCode());
			 containerPk.setContainerNumber(   containerVO
					.getContainerNumber());
			 containerPk.setAssignmentPort(   containerVO.getAssignedPort());
			 containerPk.setFlightNumber(   containerVO.getFlightNumber());
			 containerPk.setCarrierId(   containerVO.getCarrierId());
			 containerPk.setFlightSequenceNumber(   containerVO
					.getFlightSequenceNumber());
			 containerPk.setLegSerialNumber(   containerVO
					.getLegSerialNumber());
			try {
			 Container container = Container.find(containerPk);
			 if(containerVO.getArrivedStatus()!=null){
	         container.setArrivedStatus(containerVO.getArrivedStatus());
			 }
	         container.setRemarks(containerVO.getRemarks());
			  }catch (FinderException ex) {
			       createContainer(containerVO);
			 }
		}
		/**
	     * A-1739
	     *
	     * @param containerDetailsVO
	     * @return
	     */
	    private ULDForSegmentVO constructULDForSegmentFromDetails(
	            ContainerDetailsVO containerDetailsVO) {
	        ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
	        uldForSegmentVO.setCompanyCode(containerDetailsVO.getCompanyCode());
	        uldForSegmentVO.setCarrierId(containerDetailsVO.getCarrierId());
	        uldForSegmentVO.setFlightNumber(containerDetailsVO.getFlightNumber());
	        uldForSegmentVO.setFlightSequenceNumber(containerDetailsVO
	                .getFlightSequenceNumber());
	        uldForSegmentVO.setSegmentSerialNumber(containerDetailsVO
	                .getSegmentSerialNumber());
	        uldForSegmentVO.setUldNumber(containerDetailsVO.getContainerNumber());
	        uldForSegmentVO.setRemarks(containerDetailsVO.getRemarks());//Added by A-7540
	        return uldForSegmentVO;
	    }
	    private void createContainer(ContainerVO containerVo)
	    	    throws SystemException {
	    	    	  log.entering("AssignedFlightSegment", "createContainer");
	    	        Container container = new Container(containerVo);
	    	        ContainerAuditVO containerAuditVO = new ContainerAuditVO(
	    	                ContainerVO.MODULE, ContainerVO.SUBMODULE, ContainerVO.ENTITY);
	    	        containerAuditVO = (ContainerAuditVO) AuditUtils.populateAuditDetails(
	    	                containerAuditVO, container, true);
	    	        // collectContainerAuditDetails(container,containerAuditVO);
	    	        containerAuditVO.setActionCode(AuditVO.CREATE_ACTION);
	    	        containerAuditVO.setAssignedPort(containerVo.getAssignedPort());
	    	        containerAuditVO.setContainerNumber(containerVo.getContainerNumber());
	    	        containerAuditVO.setFlightNumber(containerVo.getFlightNumber());
	    	        containerAuditVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
	    	        containerAuditVO.setLegSerialNumber(containerVo.getLegSerialNumber());
	    	        containerAuditVO.setCarrierId(containerVo.getCarrierId());
	    	        containerAuditVO.setCompanyCode(containerVo.getCompanyCode());
	    	        containerAuditVO.setAuditRemarks(containerVo.getRemarks());
	    	        log.log(Log.FINE, "ContainerAuditVO>>>>>>>>>>> ", containerAuditVO);
	    	        AuditUtils.performAudit(containerAuditVO);
log.exiting("AssignedFlightSegment", "createContainer");
	    	    }
	    


	    /**
	     * A-1739
	     *
	     * @param containerDetailsVO
	     * @param dsnForSegmentMap
	     * @param mailArrivalVO
	     * @return
	     * @throws SystemException
	     * @throws DuplicateMailBagsException
	     */
	    private boolean updateContainerArrivalDetails(
	            ContainerDetailsVO containerDetailsVO,
	              MailArrivalVO mailArrivalVO,
				   Map<String,Collection<MailbagVO>>         mailBagsMapForInventory,
				   Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory,
				   boolean isScanned) throws SystemException, DuplicateMailBagsException {
	        log.entering("AssignedFlightSegment", "updateArrivedContainerDetails");
			   //commented for icrd-94608 BY a-4810
	        if (!containerDetailsVO.getContainerNumber().startsWith(
	                MailConstantsVO.CONST_BULK)) {
	            try {
					updateContainerDetails(containerDetailsVO, mailArrivalVO);
				} catch (SystemException e) {
					if(isScanned){
						return false;
					}else {
		        		throw new SystemException("NO MASTER DETAILS FOR CONTAINER. IN MTKCONMST. CONNUM == >> "+containerDetailsVO.getContainerNumber());
		        	}
				}
	        }
	        ULDForSegmentPK uldForSegmentPK =
	        	constructULDForSegPKForContainer(containerDetailsVO);
	        ULDForSegment uldForSegment = null;
	        //Modified for ICRD-126626
	            try {
					uldForSegment = findULDForSegment(uldForSegmentPK);
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
	         if(uldForSegment==null) {
	        	if(mailArrivalVO.isScanned()) {
	        		return false;
	        	} else {
	        		throw new SystemException("NO ULD FOR SEGMENT ");
	        	}
	        }
	        
	        if( uldForSegment.getRemarks()!=null && !uldForSegment.getRemarks().equals(containerDetailsVO.getRemarks())){
	        uldForSegment.setRemarks(containerDetailsVO.getRemarks());//Added by A-7540
	       }
	        
uldForSegment.saveArrivalDetails(
	        		containerDetailsVO,  mailArrivalVO,
				    mailBagsMapForInventory,
				    despatchesMapForInventory);
	        /*
	         * Acquittal of ULD after arriving all the mails inside the uld.
	         * START
	         */
	        uldForSegment.updateUldAcquittalStatus();
	        if(MailConstantsVO.FLAG_YES.equals(uldForSegment.getReleasedFlag())){
	        	try{
	        		Container containerToUpdate = null;
	        		containerToUpdate = findContainer(constructContainerVOFromDetails(containerDetailsVO));
	                /* THIS MEANS THAT THIS CONTAINER IS NO MORE ASSOSIATED TO THIS FLIGHT*/
	        		containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
	        		containerToUpdate.setRemarks(containerDetailsVO.getRemarks());
	        		containerToUpdate.setLastUpdateUser(mailArrivalVO.getArrivedUser());
	        		log.log(Log.FINE, "After updatingcontainer------------> ",
	        				containerToUpdate);
	        	}catch(SystemException systemException){
	        		/* NO NEED TO THROW THIS EXCEPTION TO CLIENT*/
	        	}
	        }
	        // END
	        log.exiting("AssignedFlightSegment", "updateArrivedContainerDetails");
	        return true;
	    }
	    /**
	     * @author a-1739
	     * @param containerDetailsVO
	     * @return
	     */
	    private ULDForSegmentPK constructULDForSegPKForContainer(
	            ContainerDetailsVO containerDetailsVO) {
	        ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
	        uldForSegPK.setCompanyCode(   assignedFlightSegmentPK.getCompanyCode());
	        uldForSegPK.setCarrierId(   assignedFlightSegmentPK.getCarrierId());
	        uldForSegPK.setFlightNumber(   assignedFlightSegmentPK.getFlightNumber());
	        uldForSegPK.setFlightSequenceNumber(   assignedFlightSegmentPK.getFlightSequenceNumber());
	        uldForSegPK.setSegmentSerialNumber(   assignedFlightSegmentPK.getSegmentSerialNumber());
	        uldForSegPK.setUldNumber(   containerDetailsVO.getContainerNumber());
	        return uldForSegPK;
	    }
	    /**
	     * @author a-1739
	     * @param containerDetailsVO
	     * @param mailArrivalVO
	     * @throws SystemException
	     */
	    private void updateContainerDetails(ContainerDetailsVO containerDetailsVO,
	            MailArrivalVO mailArrivalVO) throws SystemException {
	        log.entering("AssignedFlightSegment", "updateContainerDetails");
	        ContainerVO containerVO = constructContainerVOFromDetails(containerDetailsVO);
	        log.log(Log.FINE, "containerVO ", containerVO);
			containerVO.setAssignedUser(mailArrivalVO.getArrivedUser());
	        containerVO.setLastUpdateUser(mailArrivalVO.getArrivedUser());
	        Container container = null;
	        try {
	            container = Container.find(constructContainerPK(containerVO));
	        } catch (FinderException exception) {
	        	try{
	        		int legSernum = containerVO.getLegSerialNumber();
	        		containerVO.setLegSerialNumber(0);
	        		container = Container.find(constructContainerPK(containerVO));
	        		containerVO.setLegSerialNumber(legSernum);
	        	}catch (FinderException exception1) {
	            throw new SystemException(exception.getMessage(), exception);
	        }
	        }
	        /*
	         * Added By Karthick V as the Part of the Optimistic Locking
	         */
	        if(containerDetailsVO.getLastUpdateTime()!= null){
	        	container.setLastUpdateTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true).toCalendar());
	        }
	        container.setRemarks(containerDetailsVO.getRemarks());
	        if(containerVO.getPaBuiltFlag()!=null && containerVO.getPaBuiltFlag().length()>0){
	        container.setPaBuiltFlag(containerVO.getPaBuiltFlag());
	        }
	        else{
	        	container.setPaBuiltFlag(MailConstantsVO.FLAG_NO);
	        }
	//Modified as part of bug ICRD-131199
	        /*if(MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())){
	        	container.setArrivedStatus(containerDetailsVO.getArrivedStatus());
	        }else{*/
	            container.setArrivedStatus(MailConstantsVO.FLAG_YES);
	       // }
	        if(containerDetailsVO.getDeliveredStatus()!=null){
	        container.setDeliveredFlag(containerDetailsVO.getDeliveredStatus());
	        }
	        if(containerDetailsVO.getIntact()!=null){
	        container.setIntact(containerDetailsVO.getIntact());
	        }
	        log.exiting("AssignedFlightSegment", "updateContainerDetails");
	    }


		/**
		 * @author A-1936
		 * This method is used to deliver all the Mail Bags from the Inventory .
		 * 	 * @param mailBags
		 * @param poaCode
		 */
		public  void deliverMailBagsFromInventory(Collection<MailbagVO> mailBags)
		 throws SystemException{
		   Map<ULDForSegmentPK ,Collection<MailbagVO>> uldSegmentMap = new HashMap<ULDForSegmentPK ,
		   Collection<MailbagVO>>();
		   ULDForSegmentPK uldForSegmentPk = null;
		   Collection<MailbagVO> mailBagsinULD = null;
		   ULDForSegment uldForSegment = null;
			  for(MailbagVO  mailBag:mailBags){
				  uldForSegmentPk=findUldFromSegment(mailBag).getUldForSegmentPk();
				  mailBagsinULD=uldSegmentMap.get(uldForSegmentPk);
				  if(mailBagsinULD==null){
					  mailBagsinULD=new ArrayList<MailbagVO>();
					  uldSegmentMap.put(uldForSegmentPk,mailBagsinULD);
				  }
				  mailBagsinULD.add(mailBag);
			  }
			  if(uldSegmentMap!=null && uldSegmentMap.size()>0){
			  try{
			     for(ULDForSegmentPK uldseg:uldSegmentMap.keySet()){
					   uldForSegment=ULDForSegment.find(uldseg);
					   uldForSegment.deliverMailBagsFromInventory(uldSegmentMap.get(uldseg));
				   }

			  }catch(FinderException ex){
				  throw new SystemException(ex.getErrorCode());
			  }
			  }
		}

	     /**
	      * @author A-1936
	      * This method is  Used to find the Uld corresponding to the Mail Bag
	      * @param mailBag
	      * @return
	      */
		 private ULDForSegment   findUldFromSegment (MailbagVO mailBag){
			  ULDForSegment uldForSegment = null;
			  String containerNumber=null;
			   for(ULDForSegment uld : getContainersForSegment()){
			     if(MailConstantsVO.BULK_TYPE.equals(mailBag.getContainerType())){
			    	 containerNumber=constructBulkULDNumber();
			     }else{
			    	 containerNumber=mailBag.getContainerNumber();
			     }
			     if(uld.getUldForSegmentPk().getUldNumber().equals(containerNumber)){
			    	 uldForSegment=uld;
			     }
			   }
			  return uldForSegment;
		 }


		 /**
		     * @author a-1883
		     * @param mailbagVOs
		     * @throws SystemException
		     */
		    public void saveDamageDetailsForMailbags(Collection<MailbagVO> mailbagVOs)
		    throws SystemException {
		        log.entering("AssignedFlightSegment", "saveDamageDetailsForMailbags");
		        new ULDForSegment().updateDamageDetails(mailbagVOs);
		        log.exiting("AssignedFlightSegment", "saveDamageDetailsForMailbags");
		    }

		    /**
		     * @author a-1936
		     * This methos is used to list  the containers and the associated dsns  present in that..
		     * @param operationalFlightVo
		     * @return
		     * @throws SystemException
		     */
		    public static MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo)
			   throws SystemException{
		      return 	ULDForSegment.findContainersInFlightForManifest(operationalFlightVo);
		    }

		    /**
		     * @author a-1936
		     * This method is used to fetch the MailBags and the Despatches  in the  Container for a Flight.
		     * @param containers
		     * @return
		     * @throws SystemException
		     */
		   public static  Collection<ContainerDetailsVO> findMailbagsInContainerForManifest(Collection<ContainerDetailsVO> containers)
			  throws SystemException{
			   return 	ULDForSegment.findMailbagsInContainerForManifest(containers);
		    }

		    /**
		     * A-1739
		     *
		     * @param opFlightVO
		     * @return
		     * @throws SystemException
		     */
		    public static MailArrivalVO findArrivalDetails(
		    		MailArrivalFilterVO mailArrivalFilterVO) throws SystemException {



		        Collection<ContainerDetailsVO> arrivedContainers = ULDForSegment
		        .findArrivedContainers(mailArrivalFilterVO);

		        /*
		         * Commented By Karthick V
		         *
		         *
		         */

		        /*if(arrivedContainers != null && arrivedContainers.size() > 0) {

			         * In case of a normail arrival enquiry all the despatches in a container are
			         * taken.
			         * In case of an enquirty for tranship/terminating mail, only the despatcehs
			         * corresponding to the terminating/transhippping DSNVOs are taken

			        if(MailConstantsVO.MAIL_TERMINATING.equals(
			        		mailArrivalFilterVO.getMailStatus()) ||
			        		MailConstantsVO.MAIL_TRANSHIP.equals(
			                		mailArrivalFilterVO.getMailStatus())) {
			        	for (ContainerDetailsVO arrivedContainerVO : arrivedContainers) {
			        		Collection<DSNVO> dsnSummaryVOs =
			        			arrivedContainerVO.getDsnVOs();
			        		if(dsnSummaryVOs != null && dsnSummaryVOs.size() > 0) {
			        			Collection<DespatchDetailsVO> despatchDetails =
			        				new ArrayList<DespatchDetailsVO>();
			        			for(DSNVO dsnSummaryVO : dsnSummaryVOs) {
			        				if(MailConstantsVO.FLAG_NO.equals(
			        						dsnSummaryVO.getPltEnableFlag())) {
				        				if (arrivedContainerVO.getContainerType().equals(
				    		                    MailConstantsVO.BULK_TYPE)) {
				        					despatchDetails.addAll(
				    		                		ULDForSegment.findDespatchesInBulkDSNForArrival(
				    		                				arrivedContainerVO, dsnSummaryVO));
				    		            } else {
				    		            	despatchDetails.addAll(
				    		                		ULDForSegment.findDespatchesInULDDSNForArrival(
				    		                				arrivedContainerVO, dsnSummaryVO));
				    		            }
			        				}
			        				if(despatchDetails != null &&
			        						despatchDetails.size() > 0) {
			        					updateDSNTransferFlag(despatchDetails, dsnSummaryVO);
			        				}
			        			}
			        			arrivedContainerVO.setDesptachDetailsVOs(despatchDetails);

			        		}
			        	}
			        } else {
			        	for (ContainerDetailsVO arrivedContainerVO : arrivedContainers) {
				            if (arrivedContainerVO.getContainerType().equals(
				                    MailConstantsVO.BULK_TYPE)) {
				                arrivedContainerVO.setDesptachDetailsVOs(ULDForSegment
				                        .findDespatchesInBulkForArrival(arrivedContainerVO));
				            } else {
				                arrivedContainerVO.setDesptachDetailsVOs(ULDForSegment
				                        .findDespatchesInULDForArrival(arrivedContainerVO));
				            }
				        }
			        }
		        }*/

		        MailArrivalVO arrivalVO = new MailArrivalVO();
		        arrivalVO.setCompanyCode(mailArrivalFilterVO.getCompanyCode());
		        arrivalVO.setFlightCarrierCode(mailArrivalFilterVO.getCarrierCode());
		        arrivalVO.setCarrierId(mailArrivalFilterVO.getCarrierId());
		        arrivalVO.setFlightNumber(mailArrivalFilterVO.getFlightNumber());
		        arrivalVO.setFlightSequenceNumber(mailArrivalFilterVO.getFlightSequenceNumber());
		        arrivalVO.setArrivalDate(mailArrivalFilterVO.getFlightDate());
		        arrivalVO.setAirportCode(mailArrivalFilterVO.getPou());
		        arrivalVO.setLegSerialNumber(mailArrivalFilterVO.getLegSerialNumber());
		        //Added by A-4809 for stamping open/closed status.. Starts
		        if(arrivedContainers!=null && arrivedContainers.size()>0){
		        	arrivalVO.setFlightStatus(arrivedContainers.iterator().next().getFlightStatus());
		        }
		        //Added by A-4809 for stamping open/closed status.. Ends
		        arrivalVO.setContainerDetails(arrivedContainers);

		        return arrivalVO;
		    }


			/**
			 * TODO Purpose
			 * Jan 29, 2007, A-1739
			 * @param carditEnquiryFilterVO
			 * @return
			 * @throws SystemException
			 */
			public static CarditEnquiryVO findCarditDetails(
					CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException {
				String searchMode = carditEnquiryFilterVO.getSearchMode();
				try {
					if(MailConstantsVO.CARDITENQ_MODE_MAL.equals(searchMode)) {
						return constructDAO().findCarditMailDetails(carditEnquiryFilterVO);
					} else if(MailConstantsVO.CARDITENQ_MODE_DESP.equals(searchMode)) {
						return constructDAO().findCarditDespatchDetails(carditEnquiryFilterVO);
					} else if(MailConstantsVO.CARDITENQ_MODE_DOC.equals(searchMode)) {
						return constructDAO().findCarditDocumentDetails(carditEnquiryFilterVO);
					}
				} catch(PersistenceException exception) {
					throw new SystemException(exception.getErrorCode(), exception);
				}
				return null;
			}



			   /**
			   * @author A-3251 SREEJITH P.C.
			   * This  method is used to find the details of ulds and bulk to generate a report for Daily Mail Station
			   * @param containers
			   * @return
			   * @throws SystemException
			   * @throws PersistenceException
			   */
		   public static Collection<DailyMailStationReportVO> generateDailyMailStationReport(DailyMailStationFilterVO filterVO)
		   throws SystemException,ReportGenerationException{
			    return constructDAO().generateDailyMailStationReport(filterVO);

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
					return  ULDForSegment.findImportManifestDetails(operationalFlightVo);
			}

			/**
			 * @author A-5166
			 * @param companyCode
			 * @return
			 * @throws SystemException
			 */
			public static Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode)
			throws SystemException{
				//staticLogger().entering("AssignedFlightSegment","findImportFlghtsForArrival");
				try {
					return constructDAO().findImportFlghtsForArrival(companyCode);
				} catch (PersistenceException exception) {
					throw new SystemException(exception.getErrorCode(), exception);
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
		            ShipmentValidationVO shipmentValidationVO, String shipmentPrefix)
		    throws SystemException {
		        ULDForSegment.attachAWBForDSN(containerDetailsVO, shipmentValidationVO,
		                shipmentPrefix);
		    }
			/***
			 * @author a-1936
			 * @param dsnVo
			 * @param containerDetailsVO
			 * @return
			 * @throws SystemException
			 */
			public static  Collection<DespatchDetailsVO> findOtherDSNsForSameAWB(DSNVO dsnVo
					 ,ContainerDetailsVO containerDetailsVO)
					 throws SystemException{
				return ULDForSegment.findOtherDSNsForSameAWB(dsnVo,containerDetailsVO);
		}
			/**
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
			        ULDForSegment.autoAttachAWBDetailsForDSN(dsnVOs, shipmentValidationVO,
			        		operationalFlightVO, shipmentPrefix);
			    }

		    /**
		     * @author a-1936
		     * This method is used to fetch the MailBags and the Despatches  in the  Container for a Flight.
		     * @param containers
		     * @return
		     * @throws SystemException
		     */
		   public static  Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
			  throws SystemException{
			   return 	ULDForSegment.findMailbagsInContainerForImportManifest(containers);
		    }

		 //Added by A-7794 as part of ICRD-208677
		   /**
		     * @author a-7794
		     * @param toContainerVO
		     * @throws SystemException
		     */
		public static void updateULDForOperations( ContainerVO toContainerVO) throws SystemException{
			  	FlightDetailsVO flightDetailsVO = null;
			  	Collection<ULDInFlightVO> uldInFlightVos = null;
			  	flightDetailsVO = new FlightDetailsVO();
     			flightDetailsVO
     					.setCompanyCode(toContainerVO.getCompanyCode());
     			flightDetailsVO.setFlightCarrierIdentifier(toContainerVO
     					.getCarrierId());
     			flightDetailsVO.setFlightDate(toContainerVO.getFlightDate());
     			flightDetailsVO.setFlightNumber(toContainerVO
     					.getFlightNumber());
     			flightDetailsVO.setFlightSequenceNumber(toContainerVO
     					.getFlightSequenceNumber());
     			flightDetailsVO
     					.setCarrierCode(toContainerVO.getCarrierCode());
     			flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
     			uldInFlightVos = new ArrayList<ULDInFlightVO>();
     			ULDInFlightVO uldFltVo = new ULDInFlightVO();
     			uldFltVo.setUldNumber(toContainerVO.getContainerNumber());
     			uldFltVo.setPointOfLading(toContainerVO.getPol());
    			uldFltVo.setPointOfUnLading(toContainerVO.getPou());
    			uldFltVo.setRemark(MailConstantsVO.MAIL_ULD_ASSIGNED);
     			uldInFlightVos.add(uldFltVo);
     			flightDetailsVO.setUldInFlightVOs(uldInFlightVos);
     			flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
     			flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
     			try {
     				new ULDDefaultsProxy().updateULDForOperations(flightDetailsVO);
     			} catch (ULDDefaultsProxyException uldDefaultsException) {
     				throw new SystemException(uldDefaultsException.getMessage());
     			}
		  }

		//Added by A-7794 as part of ICRD-208677
		   /**
		     * @author a-7794
		     * @param operationalFlightVo
		     * @throws SystemException
		     */
		 public static void updateULDForOperationsForContainerAcceptance( OperationalFlightVO operationalFlightVo, ULDInFlightVO uldFltVo) throws SystemException{
			  	FlightDetailsVO flightDetailsVO = null;
			  	Collection<ULDInFlightVO> uldInFlightVos = null;
			flightDetailsVO = new FlightDetailsVO();
  			flightDetailsVO
  					.setCompanyCode(operationalFlightVo.getCompanyCode());
  			flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVo
  					.getCarrierId());
  			flightDetailsVO.setFlightDate(operationalFlightVo.getFlightDate());
  			flightDetailsVO.setFlightNumber(operationalFlightVo
  					.getFlightNumber());
  			flightDetailsVO.setFlightSequenceNumber(operationalFlightVo
  					.getFlightSequenceNumber());
  			flightDetailsVO
  					.setCarrierCode(operationalFlightVo.getCarrierCode());
  			flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
  			uldInFlightVos = new ArrayList<ULDInFlightVO>();
 			uldInFlightVos.add(uldFltVo);
  			flightDetailsVO.setUldInFlightVOs(uldInFlightVos);
  			flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
  			flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
  			//Added by A-7794 as part of ICRD-226708
  			flightDetailsVO.setRemark(uldFltVo.getRemark());
  			try {
  				new ULDDefaultsProxy().updateULDForOperations(flightDetailsVO);
  			} catch (ULDDefaultsProxyException uldDefaultsException) {
  				throw new SystemException(uldDefaultsException.getMessage());
  			}
		  }
		//Added by A-7794 as part of ICRD-208677
		   /**
		     * @author a-7794
		     * @param mailAcceptanceVO
		     */
		 private OperationalFlightVO constructOprFlightFromMailAcp(
					MailAcceptanceVO mailAcceptanceVO) {
				OperationalFlightVO opFlightVO = new OperationalFlightVO();
				opFlightVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				opFlightVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				opFlightVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				opFlightVO.setFlightSequenceNumber(mailAcceptanceVO
						.getFlightSequenceNumber());
				opFlightVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
				opFlightVO.setPol(mailAcceptanceVO.getPol());
				opFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
				opFlightVO.setFlightDate(mailAcceptanceVO.getFlightDate());
				opFlightVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				opFlightVO.setOwnAirlineCode(mailAcceptanceVO.getOwnAirlineCode());
				opFlightVO.setOwnAirlineId(mailAcceptanceVO.getOwnAirlineId());
				opFlightVO.setOperator(mailAcceptanceVO.getAcceptedUser());
				log.log(Log.FINE, "THE accepted User", mailAcceptanceVO.getAcceptedUser());
				return opFlightVO;
			}
}

