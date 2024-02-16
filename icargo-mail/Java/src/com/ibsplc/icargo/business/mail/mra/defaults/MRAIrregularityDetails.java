
	/*
	 * MRAIrregularityDetails.java Created on 19 Nov 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author A-3229
	 *
	 */
	@Entity
	@Staleable
	@Deprecated
	@Table(name = "MRAIRPDTL")
	public class MRAIrregularityDetails {
		
		/**
		 * Module name
		 */
		public static final String MODULE_NAME = "mail.mra.defaults";
					
		private MRAIrregularityDetailsPK irregularityDetailsPK;
		
		private Calendar flightDate;
		
		private Calendar rescheduledFlightDate;
		
		private String offloadedStation;
		
		private double offloadedWeight;
		
		private String irregularityStatus;
		
		private int offloadedPieces;
		
		private Calendar irpDate;
		
		private String paymentMode;
		
		private String dsn;
		
		private Log log = LogFactory.getLogger("MRA_DEFAULTS");
		
			    
	    /**
	     * @return irregularityDetailsPK
	     */
	    @EmbeddedId
		@AttributeOverrides( {
				@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
				@AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS")),
				@AttributeOverride(name = "consigndocno", column = @Column(name = "CSGDOCNUM")),
				@AttributeOverride(name = "consigndocseqno", column = @Column(name = "CSGSEQNUM")),
				@AttributeOverride(name = "postalcode", column = @Column(name = "POACOD")),
				@AttributeOverride(name = "rescheduledFlightCarrierId", column = @Column(name = "REVFLTCARIDR")),
				@AttributeOverride(name = "rescheduledFlightNo", column = @Column(name = "REVFLTNUM")),
				@AttributeOverride(name = "rescheduledFlightSeqNo", column = @Column(name = "REVFLTSEQNUM")),
				@AttributeOverride(name = "flightNumber", column = @Column(name = "ORGFLTNUM")),
				@AttributeOverride(name = "flightCarrierId", column = @Column(name = "ORGFLTCARIDR")),
				@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "ORGFLTSEQNUM"))
				
		})
		
		/**
		 * @return irregularityDetailsPK
		 */
		
		public MRAIrregularityDetailsPK getMRAIrregularityDetailsPK() {
			return irregularityDetailsPK;
		}
		
	    /**
	     * @param irregularityDetailsPK
	     */
	    public void setMRAIrregularityDetailsPK(MRAIrregularityDetailsPK irregularityDetailsPK) {
			this.irregularityDetailsPK = irregularityDetailsPK;
		}
	
		/**
		 * @return offloadedPieces
		 */
		@Column(name="OFLPCS")
		public int getOffloadedPieces() {
			return offloadedPieces;
		}

		/**
		 * @param offloadedPieces
		 */
		public void setOffloadedPieces(int offloadedPieces) {
			this.offloadedPieces = offloadedPieces;
		}

		/**
		 * @return offloadedStation
		 */
		@Column(name="OFLSTN")
		public String getOffloadedStation() {
			return offloadedStation;
		}

		/**
		 * @param offloadedStation
		 */
		public void setOffloadedStation(String offloadedStation) {
			this.offloadedStation = offloadedStation;
		}

		/**
		 * @return offloadedWeight
		 */
		@Column(name="OFLWGT")
		public double getOffloadedWeight() {
			return offloadedWeight;
		}

		/**
		 * @param offloadedWeight
		 */
		public void setOffloadedWeight(double offloadedWeight) {
			this.offloadedWeight = offloadedWeight;
		}

		/**
		 * @return irregularityStatus
		 */
		@Column(name="IRPSTA")
		public String getIrregularityStatus() {
			return irregularityStatus;
		}

		/**
		 * @param irregularityStatus
		 */
		public void setIrregularityStatus(String irregularityStatus) {
			this.irregularityStatus = irregularityStatus;
		}

		/**
		 * @return flightDate
		 */
		@Column(name = "ORGFLTDAT")
		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getFlightDate() {
			return flightDate;
		}

		/**
		 * @param flightDate
		 */
		public void setFlightDate(Calendar flightDate) {
			this.flightDate = flightDate;
		}
		/**
		 * @return
		 */
		@Column(name = "REVFLTDAT")
		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getRescheduledFlightDate() {
			return rescheduledFlightDate;
		}

		/**
		 * @param rescheduledFlightDate
		 */
		public void setRescheduledFlightDate(Calendar rescheduledFlightDate) {
			this.rescheduledFlightDate = rescheduledFlightDate;
		}
		/**
		 * @return irpDate
		 */
		@Column(name = "IRPDAT")
		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getIrpDate() {
			return irpDate;
		}
		/**
		 * @param irpDate
		 */
		public void setIrpDate(Calendar irpDate) {
			this.irpDate = irpDate;
		}
		/**
		 * @return paymentMode
		 */
		@Column(name = "MOP")
		public String getPaymentMode() {
			return paymentMode;
		}
		/**
		 * @param paymentMode
		 */
		public void setPaymentMode(String paymentMode) {
			this.paymentMode = paymentMode;
		}
		/**
		 * @return Returns the dsn.
		 */
		@Column(name="DSN")
		public String getDsn() {
			return dsn;
		}
		/**
		 * @param dsn
		 */

		public void setDsn(String dsn) {
			this.dsn = dsn;
		}
		
		/**
		 * @return
		 * @throws SystemException
		 */
		
		private static MRADefaultsDAO constructDAO() throws SystemException {
			try{
				return MRADefaultsDAO.class.cast(PersistenceController.getEntityManager().getQueryDAO(MODULE_NAME));
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getMessage(),persistenceException);
			}
		}
		
		/**
		 * Default Construtor
		 */
		
		public MRAIrregularityDetails() {
			super();
		}
		
		/**
		 * 
		 */
		
		public MRAIrregularityDetails(OffloadDetailVO offloadDetailsVO) throws SystemException{
			
			log.entering("MRAIrregularityDetails","MRAIrregularityDetails ");
			this.populatePK(offloadDetailsVO);
			this.populateAttributes(offloadDetailsVO);
			
			try {
				log.log(Log.INFO," @@@ going for persistance @@@ ");
				PersistenceController.getEntityManager().persist(this);
			} catch (CreateException createException) {
				throw new SystemException(createException.getMessage(),
										  createException);
			} 
			
			log.exiting("MRAIrregularityDetails","MRAIrregularityDetails ");
		}


		/**
		 * @param filterVO
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		
		public static Collection<MRAIrregularityVO> viewIrregularityDetails(MRAIrregularityFilterVO filterVO)
		throws SystemException{
			try {
				Log log = LogFactory.getLogger("MRA DEFAULTS");
				log.entering("MRAIrregularityDetails","entity");
				return constructDAO().viewIrregularityDetails(filterVO);
			} catch (PersistenceException e) {
				throw new SystemException(e.getErrorCode(), e);
			}
		}
		
		
		/**
		 * @param filterVO
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		
		public static Collection<MRAIrregularityVO> printIrregularityReport(MRAIrregularityFilterVO filterVO)
		throws SystemException{
			try{
				Log log=LogFactory.getLogger("MRA DEFAULTS");
				log.entering("MRAIrregularityDetails ", "entity");
				return constructDAO().printIrregularityReport(filterVO);
			}catch(PersistenceException e){
				throw new SystemException(e.getErrorCode(),e);
			}
		}
		
		 /**
		   * @author A-2107
		   * This method id used to find the instance of the Entity
		   * @param MRAIrregularityDetailsPK
		   * @return
		   * @throws FinderException
		   * @throws SystemException
		   */
		
		  public static MRAIrregularityDetails find(MRAIrregularityDetailsPK irregularitydetailsPK)
		      throws FinderException, SystemException {
		      return PersistenceController.getEntityManager().find(MRAIrregularityDetails.class, irregularitydetailsPK);
		  }
		  
		  
			/**
			 * @param mailSubClassVO
			 * @throws SystemException
			 */
			public void update(OffloadDetailVO offloadDetailsVO) throws SystemException {
				populateAttributes(offloadDetailsVO);
			}
		
		
		/**
		 * 
		 * @param offloadDetailsVO
		 */
		
		public void populateAttributes(OffloadDetailVO offloadDetailsVO){
			log.entering("MRAIrregularityDetails","populateAttributes");
			
			this.setFlightDate(offloadDetailsVO.getFlightDate());
			this.setRescheduledFlightDate(offloadDetailsVO.getRescheduledFlightDate());
			this.setOffloadedStation(offloadDetailsVO.getAirportCode());
			this.setOffloadedPieces(offloadDetailsVO.getOffloadedBags());
			//this.setOffloadedWeight(offloadDetailsVO.getOffloadedWeight());
			this.setOffloadedWeight(offloadDetailsVO.getOffloadedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
			this.setIrpDate(offloadDetailsVO.getOffloadedDate());
		    this.setIrregularityStatus(offloadDetailsVO.getIrregularityStatus());
			this.setDsn(offloadDetailsVO.getDsn());
			
			log.exiting("MRAIrregularityDetails","populateAttributes");
		}
		
		/**
		 * @author a-2107
		 * @param offloadDetailsVO
		 */
		public void populatePK(OffloadDetailVO offloadDetailsVO){
			log.entering("MRAIrregularityDetails","populatePK");
			
			MRAIrregularityDetailsPK irregularitydetailsPK = new MRAIrregularityDetailsPK();
			irregularitydetailsPK.setCompanyCode(offloadDetailsVO.getCompanyCode());
			irregularitydetailsPK.setBillingBasis(offloadDetailsVO.getBillingBasis());
			irregularitydetailsPK.setConsigndocno(offloadDetailsVO.getCsgDocumentNumber());
			irregularitydetailsPK.setConsigndocseqno(offloadDetailsVO.getCsgSequenceNumber());
			irregularitydetailsPK.setPostalcode(offloadDetailsVO.getPoaCode());
			irregularitydetailsPK.setRescheduledFlightCarrierId(offloadDetailsVO.getRescheduledFlightCarrierId());
			irregularitydetailsPK.setRescheduledFlightNo(offloadDetailsVO.getRescheduledFlightNo());
			irregularitydetailsPK.setRescheduledFlightSeqNo(offloadDetailsVO.getRescheduledFlightSeqNo());
			irregularitydetailsPK.setFlightCarrierId(offloadDetailsVO.getCarrierId());
			irregularitydetailsPK.setFlightNumber(offloadDetailsVO.getFlightNumber());
			irregularitydetailsPK.setFlightSequenceNumber(offloadDetailsVO.getFlightSequenceNumber());
			this.setMRAIrregularityDetailsPK(irregularitydetailsPK);
						
			log.exiting("MRAIrregularityDetails","populatePK");
			
		}

		/**
		 * @author a-2107
		 * @param offloadDetailsVO
		 */
		public void updateIrregularityDetails(OffloadDetailVO offloadDetailsVO)throws SystemException {
			log.entering("MRAIrregularityDetails", "updateIrregularityDetails");
			int offloadbags = offloadDetailsVO.getOffloadedBags();
			//double offloadwt = offloadDetailsVO.getOffloadedWeight();
			double offloadwt = offloadDetailsVO.getOffloadedWeight().getRoundedSystemValue();//added by A-7371
			log.log(Log.FINE, "offloadbags = ", offloadbags);
			log.log(Log.FINE, "offloadwt = ", offloadwt);
			if(getOffloadedPieces()>0){
				offloadDetailsVO.setOffloadedBags(getOffloadedPieces()+offloadbags);
			}else{
				offloadDetailsVO.setOffloadedBags(offloadbags);
			}
			if(getOffloadedWeight()>0){
				//offloadDetailsVO.setOffloadedWeight(getOffloadedWeight()+offloadwt);
				offloadDetailsVO.setOffloadedWeight(new Measure(UnitConstants.MAIL_WGT,getOffloadedWeight()+offloadwt));
			}else{
				//offloadDetailsVO.setOffloadedWeight(offloadwt);
				offloadDetailsVO.setOffloadedWeight(new Measure(UnitConstants.MAIL_WGT,offloadwt));//added by A-7371
			}
			update(offloadDetailsVO);
			log.exiting("MRAIrregularityDetails", "updateIrregularityDetails");
		}
		
		public void populateMRAIrregularityDetails(MRAIrregularityDetails  mraIrregularityDetails,OffloadDetailVO offloaddetailvo)
			throws SystemException{
			String station = mraIrregularityDetails.getOffloadedStation();
			log.log(Log.FINE, "station = ", station);
			log.log(Log.FINE, "OffloadedPieces = ", mraIrregularityDetails.getOffloadedPieces());
			log.log(Log.FINE, "OffloadedWeight = ", mraIrregularityDetails.getOffloadedWeight());
			offloaddetailvo.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, mraIrregularityDetails.getFlightDate(), true));
			offloaddetailvo.setAirportCode(station);
			offloaddetailvo.setOffloadedDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,mraIrregularityDetails.getIrpDate(), true));
			offloaddetailvo.setIrregularityStatus(mraIrregularityDetails.getIrregularityStatus());
			offloaddetailvo.setDsn(mraIrregularityDetails.getDsn());
			offloaddetailvo.setOffloadedBags(MailConstantsVO.ONE);
		}
		
		
		public void updateOffloadedPiece(OffloadDetailVO offloaddetailvo,int balbag, double balwt)throws SystemException {
			log.entering("MRAIrregularityDetails", "updateOflloadedPiece");
			offloaddetailvo.setOffloadedBags(balbag);
			//offloaddetailvo.setOffloadedWeight(balwt);
			offloaddetailvo.setOffloadedWeight(new Measure(UnitConstants.MAIL_WGT,balwt));//added by A-7371
			update(offloaddetailvo);
			log.exiting("MRAIrregularityDetails", "updateOflloadedPiece");
		}
		
		
		/**Method to delete a row of Office of Exchange
    	 * @throws RemoveException
    	 * @throws SystemException
    	 */
    	public void remove()throws RemoveException,SystemException{
    		PersistenceController.getEntityManager().remove(this);
    	}


		
		
	}


