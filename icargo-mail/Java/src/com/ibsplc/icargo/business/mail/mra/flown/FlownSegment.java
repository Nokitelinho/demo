/*
 * FlownSegment.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.flown.vo.DSNForFlownSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.flown.MRAFlownDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

//TODO

/**
 * @author A-2338
 *
 */

@Entity
@Table(name = "MALMRAFLNSEG")
@Staleable
public class FlownSegment {

	private static final String MODULE_NAME = "mail.mra.flown";

	private static final String PROCEDURE_STATUS_OK = "OK";

	private FlownSegmentPK flownSegmentPK;


	/**
	 * Comment for <code>pol</code>
	 */
	private String pol;
	/**
	 * Comment for <code>pou</code>
	 */
	private String pou;
	/**
	 * Comment for <code>segmentStatus</code>
	 */
	private String segmentStatus;
	/**
	 * 
	 * @return Returns the mraFlightSegmentPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="carrierId", column=@Column(name="FLTCARIDR")),
		@AttributeOverride(name="flightNumber", column=@Column(name="FLTNUM")),
		@AttributeOverride(name="flightSequenceNumber", column=@Column(name="FLTSEQNUM")),
		@AttributeOverride(name="segmentSerialNumber", column=@Column(name="SEGSERNUM"))}
	)
	public FlownSegmentPK getFlownSegmentPK() {
		return flownSegmentPK;
	}

	/**
	 * @param flownSegmentPK
	 * The mraFlightSegmentPK to set.
	 */

	public void setFlownSegmentPK(FlownSegmentPK flownSegmentPK) {
		this.flownSegmentPK = flownSegmentPK;
	}

	
	/**
	 * @return the pol
	 */
	@Column(name = "POL")
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return the pou
	 */
	@Column(name = "POU")
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return the segmentStatus
	 */
	@Column(name = "SEGSTA")
	public String getSegmentStatus() {
		return segmentStatus;
	}

	/**
	 * @param segmentStatus the segmentStatus to set
	 */
	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}

	/**
	 * @param flownMailFilterVO
	 * @return Collection <MailBagForFlownSegmentVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<MailBagForFlownSegmentVO> findFlownMailMags(
			FlownMailFilterVO flownMailFilterVO)throws SystemException, PersistenceException {
		return constructDAO().findFlownMailMags(flownMailFilterVO);



	}


	/**
	 * @param flownMailFilterVO
	 * @return Collection <DSNForFlownSegmentVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<DSNForFlownSegmentVO> findFlownDSNs(
			FlownMailFilterVO flownMailFilterVO) throws SystemException, PersistenceException{
		return constructDAO().findFlownDSNs(flownMailFilterVO);
	}


	/**
	 * @param flownMailFilterVO
	 * @return FlownMailSegmentVO
	 */
	public static FlownMailSegmentVO findFlownMails(
			FlownMailFilterVO flownMailFilterVO) {
		Collection<MailBagForFlownSegmentVO> mailBagForFlownSegmentVOs=new ArrayList<MailBagForFlownSegmentVO>();
		Collection<DSNForFlownSegmentVO> dSNForFlownSegmentVOs=new ArrayList<DSNForFlownSegmentVO>();
		try {
			mailBagForFlownSegmentVOs=
				findFlownMailMags(flownMailFilterVO);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		try {
			dSNForFlownSegmentVOs=findFlownDSNs(flownMailFilterVO);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		FlownMailSegmentVO flownMailSegmentVO=new FlownMailSegmentVO();
		flownMailSegmentVO.setSegmentMailBags(mailBagForFlownSegmentVOs);
		flownMailSegmentVO.setSegmentDSNs(dSNForFlownSegmentVOs);
		
		if(mailBagForFlownSegmentVOs!=null && mailBagForFlownSegmentVOs.size()>0){
			ArrayList<MailBagForFlownSegmentVO> mailBags=new ArrayList<MailBagForFlownSegmentVO>(mailBagForFlownSegmentVOs);
			
			flownMailSegmentVO.setSegmentStatus(mailBags.get(0).getSegmentStatus());
		}else if(dSNForFlownSegmentVOs!=null && dSNForFlownSegmentVOs.size()>0){
			ArrayList<DSNForFlownSegmentVO>	 dsns=new ArrayList<DSNForFlownSegmentVO>(dSNForFlownSegmentVOs);
			flownMailSegmentVO.setSegmentStatus(dsns.get(0).getSegmentStatus());
		}
			
		
			
		

		return flownMailSegmentVO;
	}


	/**
	 * @param flownMailExceptionVOs
	 * @throws SystemException
	 */
	public void assignFlownMailExceptions(Collection<FlownMailExceptionVO> flownMailExceptionVOs)
	throws SystemException{
		//log.entering("flown Controller", "assignFlownMailExceptions");
		ExceptionsInFlownSegment exceptionsInFlownSegment = null;
		for(FlownMailExceptionVO flownMailExceptionVO : flownMailExceptionVOs){
			if(FlownMailExceptionVO.OPERATION_FLAG_UPDATE.
					equals(flownMailExceptionVO.getOperationFlag())){
				//log.log(log.FINE, "operational flag is update");
				try{
					exceptionsInFlownSegment = ExceptionsInFlownSegment.find(
							flownMailExceptionVO.getCompanyCode(),flownMailExceptionVO.getFlightCarrierId(),
							flownMailExceptionVO.getFlightNumber(),flownMailExceptionVO.getFlightSequenceNumber(),
							flownMailExceptionVO.getSegmentSerialNumber(),flownMailExceptionVO.getSerialNumber(),
							flownMailExceptionVO.getBillingBasis(),flownMailExceptionVO.getExceptionCode());
					exceptionsInFlownSegment.assignFlownMailExceptions(flownMailExceptionVO);
				}catch(FinderException finderException ){
					throw new SystemException(finderException.getErrorCode());
				}

			}
		}

	}


	/**
	 * This method is for findFlownMailExceptions
	 * @param flownMailFilterVO
	 * @return Collection <FlownMailExceptionVO>
	 * @throws SystemException
	 */
	public static Collection<FlownMailExceptionVO>
	findFlownMailExceptions(FlownMailFilterVO flownMailFilterVO) throws SystemException{
		Collection<FlownMailExceptionVO> flownMailExceptionVOs = null;
		return constructDAO().findFlownMailExceptions(flownMailFilterVO);
	}


	/**
	 * @param flownMailFilterVO
	 * @return Collection <FlownMailExceptionVO>
	 * @throws SystemException
	 */
	public static Collection<FlownMailExceptionVO>
	findFlownMailExceptionsforprint(FlownMailFilterVO flownMailFilterVO) throws SystemException{
		Collection<FlownMailExceptionVO> flownMailExceptionVOs = null;
		return constructDAO().findFlownMailExceptionsforprint(flownMailFilterVO);
	}


	/**
	 * @param flownMailFilterVO
	 * @return Collection <FlownMailExceptionVO>
	 * @throws SystemException
	 */
	public static Collection<FlownMailExceptionVO>
	findFlownMailExceptionsforprintDetails(FlownMailFilterVO flownMailFilterVO) throws SystemException{
		Collection<FlownMailExceptionVO> flownMailExceptionVOs = null;
		return constructDAO().findFlownMailExceptionsforprintDetails(flownMailFilterVO);
	}
	/**
	 * This method is for closeFlight
	 * @param flownMailFilterVO
	 *
	 */
	public static void closeFlight(FlownMailFilterVO flownMailFilterVO) {
		try{
			constructDAO().closeFlight(flownMailFilterVO);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}


    /**
     * This method is used to process a flight segment
     *
     * Mar 26, 2007, A-2401
     * @param flownMailFilterVO
     * @return
     * @throws SystemException
     * @throws MailTrackingMRABusinessException
     */
	public static void processFlight(FlownMailFilterVO flownMailFilterVO)
	throws SystemException, MailTrackingMRABusinessException {

		String outParameter = null;

			try {
				outParameter = constructDAO().processFlight(flownMailFilterVO);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}

			if(!(PROCEDURE_STATUS_OK.equalsIgnoreCase(outParameter))){
				throw new MailTrackingMRABusinessException(
							MailTrackingMRABusinessException.
								MAILTRACKING_MRA_EXCEPTION_PROCESSFLIGHT_FAILED);
			}

	}

	/**
	 * This method is used for finding flight details
	 * @param flownMailFilterVO
	 * @return Collection<FlownMailSegmentVO>
	 */
	public static Collection<FlownMailSegmentVO> findFlightDetails(
			FlownMailFilterVO flownMailFilterVO) {
		Collection<FlownMailSegmentVO> flownMailSegmentVOs=new ArrayList<FlownMailSegmentVO>();
		try{
			flownMailSegmentVOs=constructDAO().findFlightDetails(flownMailFilterVO);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return flownMailSegmentVOs;

	}

	/**
	 * @return CRAFlownDAO
	 * @throws SystemException
	 */
	private static MRAFlownDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MRAFlownDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}

	}

	/**
	 * Method to find a flown segment.
	 * @param companyCode
	 * @param flightCarrierId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @param segmentSerialNumber
	 * @return FlownSegment
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static FlownSegment find(String companyCode,
			int flightCarrierId,
			String flightNumber,
			int flightSequenceNumber,int segmentSerialNumber)
	throws SystemException,FinderException {
		FlownSegmentPK flownsegmentPK = new FlownSegmentPK();
		flownsegmentPK.setCompanyCode(   companyCode);
		flownsegmentPK.setCarrierId(   flightCarrierId);
		flownsegmentPK.setFlightNumber(   flightNumber);
		flownsegmentPK.setFlightSequenceNumber(   flightSequenceNumber);
		flownsegmentPK.setSegmentSerialNumber( segmentSerialNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(FlownSegment.class,flownsegmentPK);
	}

	/**
	 * @param flownMailFilterVO
	 * @return Collection<FlownMailSegmentVO>
	 * @throws SystemException
	 */
	public static Collection<FlownMailSegmentVO> findListOfFlightsForReport(FlownMailFilterVO flownMailFilterVO)
	throws SystemException{

		Collection<FlownMailSegmentVO> flownMailSegmentVOs=new ArrayList<FlownMailSegmentVO>();
		try{
			flownMailSegmentVOs=constructDAO().findListOfFlightsForReport(flownMailFilterVO);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return flownMailSegmentVOs;


	}


	/**
	 * @param flownMailFilterVO
	 * @return Collection<FlownMailSegmentVO>
	 * @throws SystemException
	 */
	public static Collection<FlownMailSegmentVO> findListOfFlownMailsForReport(FlownMailFilterVO flownMailFilterVO)
	throws SystemException{

		Collection<FlownMailSegmentVO> flownMailSegmentVOs=new ArrayList<FlownMailSegmentVO>();
		try{
			flownMailSegmentVOs=constructDAO().findListOfFlownMailsForReport(flownMailFilterVO);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return flownMailSegmentVOs;


	}
	/**
	 * 	Method		:	FlownSegment.importArrivedMailstoMRA
	 *	Added by 	:	A-4809 on Oct 12, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public static void importArrivedMailstoMRA(String companyCode) throws SystemException{
		try{
		constructDAO().importArrivedMailstoMRA(companyCode);
		}catch(SystemException e){
			e.getMessage();

		}
	}
	/**
	 * @author A-7794 as part of ICRD-232299
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @throws SystemException
	 */
	public static void forceImportScannedMailbags(String companyCode,String startDate,String endDate)throws SystemException{
		try{
		constructDAO().forceImportScannedMailbags(companyCode,startDate,endDate);
		}catch(SystemException e){
			e.getMessage();
		}
	}
}
