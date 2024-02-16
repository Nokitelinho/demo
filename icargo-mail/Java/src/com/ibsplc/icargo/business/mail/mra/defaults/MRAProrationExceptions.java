/*
 * MRAProrationExceptions.java Created on Sep 03,2008
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailExceptionReportsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author a-3108
 * 
 */
@Entity
@Table(name = "MALMRAEXPDTL")
@Staleable
public class MRAProrationExceptions{
	
	private static final String CLASS_NAME = "ProrationExceptions";

	private static final String MODULE_NAME = "mail.mra.defaults";
	
	private  Log log = localLogger();

	private MRAProrationExceptionsPK prorationExceptionsPK;
	
	
	private String exceptionCode;
	private Calendar recievedDate;
	private Calendar assignedDate;
	private Calendar resolvedDate;
	private String assignee;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private String triggerPoint;
	private String exceptionStatus;
	private String exceptionRemark;
	private String sectorFrom;
	private String sectorTo;
	
	
    
    
    private static Log localLogger() {
        return LogFactory.getLogger("MRA DEFAULTS");
    }
    @EmbeddedId
	@AttributeOverrides( {
		
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")) ,
		@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
		@AttributeOverride(name = "exceptionSequenceNumber", column = @Column(name = "EXPSEQNUM"))
				})
	/**
	 * @return the prorationExceptionsPK
	 */
	public MRAProrationExceptionsPK getProrationExceptionsPK() {
		return prorationExceptionsPK;
	}

	/**
	 * @param prorationExceptionsPK
	 *            the prorationExceptionsPK to set
	 */
	public void setProrationExceptionsPK(
			MRAProrationExceptionsPK prorationExceptionsPK) {
		this.prorationExceptionsPK = prorationExceptionsPK;
	}
	
	
	/**
	 * @return the exceptionCode
	 */
	@Column(name="EXPCOD")
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * @return the recievedDate
	 */
	@Column(name="RCVDAT")
	public Calendar getRecievedDate() {
		return recievedDate;
	}

	/**
	 * @param recievedDate the recievedDate to set
	 */
	public void setRecievedDate(Calendar recievedDate) {
		this.recievedDate = recievedDate;
	}

	/**
	 * @return the assignedDate
	 */
	@Column(name="ASGDAT")
	public Calendar getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate the assignedDate to set
	 */
	public void setAssignedDate(Calendar assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return the resolvedDate
	 */
	@Column(name="RSDDAT")
	public Calendar getResolvedDate() {
		return resolvedDate;
	}

	/**
	 * @param resolvedDate the resolvedDate to set
	 */
	public void setResolvedDate(Calendar resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	/**
	 * @return the assignee
	 */
	@Column(name="ASDUSR")
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name="LSTUPDTIM")
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the triggerPoint
	 */
	@Column(name="TRGPNT")
	public String getTriggerPoint() {
		return triggerPoint;
	}

	/**
	 * @param triggerPoint the triggerPoint to set
	 */
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}

	/**
	 * @return the exceptionStatus
	 */
	@Column(name="EXPSTA")
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * @param exceptionStatus the exceptionStatus to set
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * @return the exceptionRemark
	 */
	@Column(name="EXPRMK")
	public String getExceptionRemark() {
		return exceptionRemark;
	}

	/**
	 * @param exceptionRemark the exceptionRemark to set
	 */
	public void setExceptionRemark(String exceptionRemark) {
		this.exceptionRemark = exceptionRemark;
	}

	/**
	 * @return the sectorFrom
	 */
	@Column(name="SECFRM")
	public String getSectorFrom() {
		return sectorFrom;
	}

	/**
	 * @param sectorFrom the sectorFrom to set
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}

	/**
	 * @return the sectorTo
	 */
	@Column(name="SECTOO")
	public String getSectorTo() {
		return sectorTo;
	}

	/**
	 * @param sectorTo the sectorTo to set
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}

	private static MRADefaultsDAO constructDAO() throws SystemException {

			try {

				EntityManager em = PersistenceController.getEntityManager();

				return MRADefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));

			} catch (PersistenceException ex) {

				throw new SystemException(ex.getErrorCode(), ex);

			}

		}

	 /**
	  * Method for listing the proration exceptions details
	  * 
	  * 
	 * 
	  * @author A-3108 
	  * @param prorationExceptionsFilterVO
	  * @return
	  * @throws SystemException
	  */
	 public static Page<ProrationExceptionVO> findProrationExceptions(
			 ProrationExceptionsFilterVO prorationExceptionsFilterVO) throws SystemException {

		
			return constructDAO().findProrationExceptions(prorationExceptionsFilterVO);
	}
	/* *//**
		 * Method for saving the proration exceptions
		 * 
		 * @param prorationExceptionVO
		 * @throws SystemException
		 * @throws CreateException
		 */
			 public void saveProrationExceptions(ProrationExceptionVO prorationExceptionVO) throws SystemException{

				log.entering(CLASS_NAME,"ProrationExceptions"+prorationExceptionVO);	
				try{
				MRAProrationExceptions prorationExceptions = new MRAProrationExceptions();

				prorationExceptions = MRAProrationExceptions.find(prorationExceptionVO);
				if(prorationExceptionVO
						.getAssignedUser()!=null){
				prorationExceptions.setAssignee(prorationExceptionVO
						.getAssignedUser());
				}
				if(prorationExceptionVO.getAssignedTime()!=null){
				prorationExceptions.setAssignedDate(prorationExceptionVO.getAssignedTime());
				}
				if(prorationExceptionVO.getStatus()!=null){
				prorationExceptions.setExceptionStatus(prorationExceptionVO.getStatus());
				}

				if(prorationExceptionVO.getLastUpdatedTime()!=null){
					prorationExceptions.setLastUpdatedTime(prorationExceptionVO.getAssignedTime().toCalendar());
					 }
				prorationExceptions.setLastUpdatedUser(prorationExceptionVO
						.getLastUpdatedUser());
				PersistenceController.getEntityManager().persist(
						prorationExceptions);
					}catch(CreateException createException){
				throw new SystemException(createException.getMessage(),
						createException);
				 	
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getMessage(),
						finderException);
					}

					log.log(Log.FINE, "ProrationExceptionsDetails---->"+this);			
					log.exiting(CLASS_NAME,"execute");
			 }
			 
				
		public static MRAProrationExceptions find(ProrationExceptionVO prorationExceptionVO)
				throws SystemException, FinderException {
			
			//log.log(Log.FINE, "ProrationExceptionsDetails find---->"+prorationExceptionVO);	
					 MRAProrationExceptions prorationExceptions=null;			
					 MRAProrationExceptionsPK prorationExpPK =  new MRAProrationExceptionsPK(prorationExceptionVO);		 
					 //log.log(Log.FINE, "ProrationExceptionsDetails pk---csgdocnumber->"+prorationExpPK.getCsgDocumentNumber());
					 //log.log(Log.FINE, "ProrationExceptionsDetails pk---csgseq->"+prorationExpPK.getCsgSequenceNumber());
			        
				try {
					prorationExceptions = PersistenceController.getEntityManager()
							.find(MRAProrationExceptions.class, prorationExpPK);
		         }catch(FinderException finderException){
					throw new SystemException(finderException.getMessage(),
							finderException);
				 }
				
			
				return prorationExceptions;

			

		      }
	 
		public static Collection<ProrationExceptionVO> printProrationExceptionReport(
				ProrationExceptionsFilterVO filterVO) throws SystemException {
					Collection<ProrationExceptionVO> returnVOs = null;
					try {
				returnVOs = constructDAO().printProrationExceptionReport(
						filterVO);
					} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getMessage(),
						persistenceException);
					}
					return returnVOs;
				}
		

		/**
		  * 
		  * 
		  * 
		  * 
		  * @author A-3108 
		  * @param ProrationExceptionVO
		  * @return
		  * @throws SystemException
		  */
		 public static void prorateDSN(
				 ProrationExceptionVO ProrationExceptionVO) throws SystemException {

			 String outParameter = null;
		    	
		    	try{
		    		outParameter = constructDAO().prorateDSN(ProrationExceptionVO);		
				}
				catch (PersistenceException persistenceException) {
					persistenceException.getErrorCode();
					throw new SystemException(persistenceException.getMessage());
				}
				 
		}
		 
		 /**
		  * 
		  * @author A-2414
		  * @param mailExceptionReportsFilterVo
		  * @return String - file data
		  * @throws SystemException
		  * @throws RemoteException
		  */
		 public static String generateMailExceptionReport(
			MailExceptionReportsFilterVO mailExceptionReportsFilterVo)
			throws SystemException {
			 
			String fileData = null;
	
			try {
				fileData = constructDAO().generateMailExceptionReport(
						mailExceptionReportsFilterVo);
			} catch (PersistenceException persistenceException) {
				persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
	
			return fileData;
		}
		
		 /**
		  * Method		:	MRAProrationExceptions.prorateExceptionFlights
		  *	Added by 	:	A-6245 on 17-06-2015
		  * @param flightValidationVOs
		  * @throws SystemException
		  */
		 public static void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs) throws SystemException {
		    	try{
		    		 constructDAO().prorateExceptionFlights(flightValidationVOs);		
				}
				catch (PersistenceException persistenceException) {
					persistenceException.getErrorCode();
					throw new SystemException(persistenceException.getMessage());
				}
		}		 

		
}

