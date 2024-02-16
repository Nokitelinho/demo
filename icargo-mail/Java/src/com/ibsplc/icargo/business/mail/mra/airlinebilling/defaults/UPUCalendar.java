/*
 * UPUCalendar.java Created on Sep 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults;

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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1387
 *
 */

@Table(name="MTKIATCALMST")
@Entity
public class UPUCalendar {
	
	private Log log = LogFactory.getLogger("MailTracking:Mra");
	private UPUCalendarPK upuCalendarPK;
	private Calendar fromDate;
	private Calendar toDate;   
	private Calendar submissionDate;
	private int generateAfterToDate;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime; 
	
	/**
	 * module name of entity
	 */
	public static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	/**
	 * 
	 * @return
	 */
	public static Log staticLogger(){
		return LogFactory.getLogger("MailTracking:Mra");
	}
	
	
	/**
	 * @return the lastUpdatedTime
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
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
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
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
	 * @return Returns the fromDate.
	 */
	@Column(name="FRMDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the generateAfterToDate.
	 */
	@Column(name="GENAFT")
	public int getGenerateAfterToDate() {
		return generateAfterToDate;
	}
	/**
	 * @param generateAfterToDate The generateAfterToDate to set.
	 */
	public void setGenerateAfterToDate(int generateAfterToDate) {
		this.generateAfterToDate = generateAfterToDate;
	}
	/**
	 * @return Returns the upuCalendarPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="clrPeriod", column=@Column(name="CLRPRD"))}
	)
	public UPUCalendarPK getIataCalendarPK() {
		return upuCalendarPK;
	}
	/**
	 * @param upuCalendarPK The upuCalendarPK to set.
	 */
	public void setIataCalendarPK(UPUCalendarPK upuCalendarPK) {
		this.upuCalendarPK = upuCalendarPK;
	}
	/**
	 * @return Returns the submissionDate.
	 */
	@Column(name="SUBDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getSubmissionDate() {
		return submissionDate;
	}
	/**
	 * @param submissionDate The submissionDate to set.
	 */
	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}
	/**
	 * @return Returns the toDate.
	 */
	@Column(name="TOODAT")
	@Temporal(TemporalType.DATE)
	public Calendar getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}
	
	public UPUCalendar(){
		
	}
	
	/**
	 * 
	 * @param upuCalendarVO
	 * @throws SystemException
	 */
	public UPUCalendar( UPUCalendarVO upuCalendarVO ) 
									throws SystemException {
		
		log.entering("UPUCalendar","In constructor ");
    	//setting the pk 
		UPUCalendarPK upuCalendarPKForCreate = new UPUCalendarPK(
														upuCalendarVO.getCompanyCode(),
														upuCalendarVO.getBillingPeriod()
													);
		setIataCalendarPK(upuCalendarPKForCreate);
    	populateAttributes(upuCalendarVO);
    	log.log(Log.FINE,"Calling persist for uld child");
    	try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException( e.getErrorCode() );
		} 
    	log.log(Log.FINE,"After persist of uld BO");
		
	}
	
	/**
	 * 
	 * @param upuCalendarFilterVO
	 * @return Collection<UPUCalendarVO>
	 * @throws SystemException
	 */
	public static Collection<UPUCalendarVO> displayUPUCalendarDetails(
			UPUCalendarFilterVO upuCalendarFilterVO) throws SystemException {
		try{
			MRAAirlineBillingDAO mraAirlineBillingDAO = MRAAirlineBillingDAO.class.cast(
					
					PersistenceController.getEntityManager().getQueryDAO("mail.mra.airlinebilling"));
			
			return mraAirlineBillingDAO.displayUPUCalendarDetails( upuCalendarFilterVO );
		}catch( PersistenceException e){
			
			throw new SystemException( e.getErrorCode());
		}
	}
	
	/**
	 * 
	 * @param upuCalendarVO
	 * @throws SystemException
	 */
	private void populateAttributes( UPUCalendarVO upuCalendarVO )
 						throws SystemException {

		 setFromDate( upuCalendarVO.getFromDate());
		 setSubmissionDate( upuCalendarVO.getSubmissionDate() );
		 setToDate( upuCalendarVO.getToDate());
		 setGenerateAfterToDate( upuCalendarVO.getGenerateAfterToDate() );
		 setLastUpdatedTime(upuCalendarVO.getLastUpdateTime());
		 setLastUpdatedUser(upuCalendarVO.getLastUpdateUser());
	}
	
	/**
	 * 
	 * @param upuCalendarVO
	 * @throws SystemException
	 */
	public void update(UPUCalendarVO upuCalendarVO)throws SystemException{
				
		populateAttributes( upuCalendarVO );
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param clearancePrd
	 * @param billingPeriod
	 * @return
	 * @throws SystemException
	 */
	public static UPUCalendar find( String companyCode,String clearancePrd)
											throws SystemException{
		try{
			UPUCalendarPK upuCalendarPKToFind  = new UPUCalendarPK( 
														companyCode, clearancePrd );
				return PersistenceController.getEntityManager().find(
														UPUCalendar.class, upuCalendarPKToFind);
		}catch( FinderException e){
			
			throw new SystemException( e.getErrorCode());
		}
		
		
		
	} 
	
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			throw new SystemException( e.getErrorCode() );
		} 
	}
	
	/**
	 * 
	 * @param upuCalendarVO
	 * @return
	 * @throws SystemException
	 */
	public static boolean checkDuplicates( UPUCalendarVO upuCalendarVO) throws SystemException{
		
		try{
			UPUCalendarPK upuCalendarPKToFind  = 
				new UPUCalendarPK(upuCalendarVO.getCompanyCode(), upuCalendarVO.getBillingPeriod());
			PersistenceController.getEntityManager().find(UPUCalendar.class, upuCalendarPKToFind);
			
			return false;
			
		}catch( FinderException e){
			
			return true;
		}
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param iataClearancePrd
	 * @return
	 * @throws SystemException
	 */
	public static UPUCalendarVO validateIataClearancePeriod
									( String companyCode,String iataClearancePrd) 
									throws SystemException{
		staticLogger().entering("UPUCalendar","validateIataClearancePeriod");		
		
		UPUCalendarVO upuCalendarVO = null;
		
		UPUCalendar upuCalendar = null;
		
				upuCalendar = UPUCalendar.find(companyCode,iataClearancePrd);
		
			if(upuCalendar != null ){
				staticLogger().log(Log.INFO,"########## found the clearance as valid ");
				String iataClrPrd = upuCalendar.getIataCalendarPK().getClrPeriod();	
				LocalDate startDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,
						upuCalendar.getFromDate(),false); 
				LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,
						upuCalendar.getToDate(),false); 
				LocalDate dateOfSub =null;
				if(upuCalendar.getSubmissionDate()!=null){
				dateOfSub = new LocalDate(LocalDate.NO_STATION,Location.NONE,
						upuCalendar.getSubmissionDate(),false);
				}
				int daysForGeneration =
						upuCalendar.getGenerateAfterToDate();
								
				upuCalendarVO = new UPUCalendarVO();
				upuCalendarVO.setCompanyCode(companyCode);
				upuCalendarVO.setBillingPeriod(iataClrPrd);
				upuCalendarVO.setFromDate(startDate);
				upuCalendarVO.setToDate(endDate);
				upuCalendarVO.setSubmissionDate(dateOfSub);
				upuCalendarVO.setGenerateAfterToDate(daysForGeneration);
			}
			
		return upuCalendarVO;
				
	}

	
	
}

