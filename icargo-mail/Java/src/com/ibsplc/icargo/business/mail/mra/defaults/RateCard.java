/*
 * RateCard.java Created on Dec 15, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */


@Entity
@Table(name = "MALMRARATCRD")

public class RateCard {
	
    private RateCardPK rateCardPK;
    private String rateCardStatus;
    private String description;
    private Calendar validityStartDate;
    private Calendar validityEndDate;
    private Calendar creationDate;
    private String creationSource;
    private double mailDistanceFactor;
    private double categoryTonKMRefOne;
    private double categoryTonKMRefTwo;
    private double categoryTonKMRefThree;
    private double categoryTonKMRefFour;
    private double categoryTonKMRefFive;
    private double exchangeRate;
    private double totalRatelines;
    private String lastUpdatedUser;
    private Calendar lastUpdateTime;

    private Set<MRARateLine> rateLines;

    private static final String MODULE_NAME = "mail.mra.defaults";

    private static final String CLASS_NAME = "RateCard";

    /**
     * Default Constructor
     */
    public RateCard() {
    }

   /**
    * Constructor
    * @author A-2408
    * @param rateCardVO
    * @throws SystemException
    */
    public RateCard(RateCardVO rateCardVO)
    throws SystemException{
    	staticLogger().entering("RateCard","inside ratecard");
    	RateCardPK constructedrateCardPK
    						=new RateCardPK(rateCardVO.getCompanyCode(),rateCardVO.getRateCardID());
    	this.setRateCardPK(constructedrateCardPK);
    	populateAttributes(rateCardVO);


    	try{
    	PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException e){
    		throw new SystemException(e.getErrorCode());
    	}
    	//add <RateLineVO> if needed
    	Page<RateLineVO> rateLinesVOs=rateCardVO.getRateLineVOss();
    	/****Added by A-5166 for ICRD-17262 on 12-Feb-2013 Starts****/
    	 Collection<RateLineVO> rateLineVOs = rateCardVO.getRateLineVOs();
    	if(rateLinesVOs!=null && rateLinesVOs.size()>0){
    		 setTotalRatelines(rateCardVO.getRateLineVOss().size());
             MRARateLine rateLine;
             for(Iterator<RateLineVO> i$ = rateLinesVOs.iterator(); i$.hasNext(); getRateLines().add(rateLine))
             {
                 RateLineVO rateLineVO = (RateLineVO)i$.next();
                 rateLine = new MRARateLine(rateLineVO);
                 if(getRateLines() == null)
                     {
                     setRateLines(new HashSet());
                     }
             }

         } else
         if(rateLineVOs != null && rateLineVOs.size() > 0)
         {
             setTotalRatelines(rateCardVO.getRateLineVOs().size());
             MRARateLine rateLine;
             for(Iterator<RateLineVO> i$ = rateLineVOs.iterator(); i$.hasNext(); getRateLines().add(rateLine))
             {
                 RateLineVO rateLineVO = (RateLineVO)i$.next();
                 rateLine = new MRARateLine(rateLineVO);
                 if(getRateLines() == null)
                     {
                     setRateLines(new HashSet());
                     }
             }

         }
    	/****Added by A-5166 for ICRD-17262 on 12-Feb-2013 Ends****/
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
	 * @return the lastUpdateTime
	 *  
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
     * @return Returns the categoryTonKMRefFive.
     */

    @Column(name="CATFIVTKM")
    public double getCategoryTonKMRefFive() {
        return categoryTonKMRefFive;
    }
    /**
     * @return Returns the categoryTonKMRefFour.
     */

    @Column(name="CATFORTKM")
    public double getCategoryTonKMRefFour() {
        return categoryTonKMRefFour;
    }
    /**
     * @return Returns the categoryTonKMRefOne.
     */

    @Column(name="CATONETKM")
    public double getCategoryTonKMRefOne() {
        return categoryTonKMRefOne;
    }
    /**
     * @return Returns the categoryTonKMRefThree.
     */

    @Column(name="CATTHRTKM")
    public double getCategoryTonKMRefThree() {
        return categoryTonKMRefThree;
    }
    /**
     * @return Returns the categoryTonKMRefTwo.
     */

    @Column(name="CATTWOTKM")
    public double getCategoryTonKMRefTwo() {
        return categoryTonKMRefTwo;
    }
    /**
     * @return Returns the description.
     */

    @Column(name="RATCRDDES")
    public String getDescription() {
        return description;
    }
    /**
     * @return Returns the exchangeRate.
     */

    @Column(name="EXGRAT")
    public double getExchangeRate() {
        return exchangeRate;
    }
    /**
     * @return Returns the mailDistanceFactor.
     */

    @Column(name="MALDISFCT")
    public double getMailDistanceFactor() {
        return mailDistanceFactor;
    }
    /**
     * @return Returns the rateCardPK.
     */

    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="rateCardID", column=@Column(name="RATCRDCOD"))}
	)
    public RateCardPK getRateCardPK() {
        return rateCardPK;
    }
    /**
     * @return Returns the rateCardStatus.
     */

    @Column(name="RATCRDSTA")
    public String getRateCardStatus() {
        return rateCardStatus;
    }
    /**
     * @return Returns the subclassTonKMRefFive.
     */

    /**
     * @return Returns the validityEndDate.
     */

    @Column(name="VLDENDDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getValidityEndDate() {
        return validityEndDate;
    }
    /**
     * @return Returns the validityStartDate.
     */

    @Column(name="VLDSTRDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getValidityStartDate() {
        return validityStartDate;
    }
    /**
     * @return Returns the creationDate.
     */

    @Column(name="CRTDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate The creationDate to set.
     */
    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return Returns the creationSource.
     */

    @Column(name="CRTSRC")
    public String getCreationSource() {
        return creationSource;
    }
    /**
     * @param creationSource The creationSource to set.
     */
    public void setCreationSource(String creationSource) {
        this.creationSource = creationSource;
    }

    /**
     * @return Returns the totalRatelines.
     */

    @Column(name="TOTRATLIN")
    public double getTotalRatelines() {
        return totalRatelines;
    }
    /**
     * @param totalRatelines The totalRatelines to set.
     */
    public void setTotalRatelines(double totalRatelines) {
        this.totalRatelines = totalRatelines;
    }
    /**
     * @return Returns the rateLines.
     */


    @OneToMany
    @JoinColumns( {
	 @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 @JoinColumn(name = "RATCRDCOD", referencedColumnName = "RATCRDCOD", insertable=false, updatable=false)})

    public Set<MRARateLine> getRateLines() {
        return rateLines;
    }
    /**
     * @param rateLines The rateLines to set.
     */
    public void setRateLines(Set<MRARateLine> rateLines) {
        this.rateLines = rateLines;
    }
    /**
     * @param categoryTonKMRefFive The categoryTonKMRefFive to set.
     */
    public void setCategoryTonKMRefFive(double categoryTonKMRefFive) {
        this.categoryTonKMRefFive = categoryTonKMRefFive;
    }
    /**
     * @param categoryTonKMRefFour The categoryTonKMRefFour to set.
     */
    public void setCategoryTonKMRefFour(double categoryTonKMRefFour) {
        this.categoryTonKMRefFour = categoryTonKMRefFour;
    }
    /**
     * @param categoryTonKMRefOne The categoryTonKMRefOne to set.
     */
    public void setCategoryTonKMRefOne(double categoryTonKMRefOne) {
        this.categoryTonKMRefOne = categoryTonKMRefOne;
    }
    /**
     * @param categoryTonKMRefThree The categoryTonKMRefThree to set.
     */
    public void setCategoryTonKMRefThree(double categoryTonKMRefThree) {
        this.categoryTonKMRefThree = categoryTonKMRefThree;
    }
    /**
     * @param categoryTonKMRefTwo The categoryTonKMRefTwo to set.
     */
    public void setCategoryTonKMRefTwo(double categoryTonKMRefTwo) {
        this.categoryTonKMRefTwo = categoryTonKMRefTwo;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @param exchangeRate The exchangeRate to set.
     */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    /**
     * @param mailDistanceFactor The mailDistanceFactor to set.
     */
    public void setMailDistanceFactor(double mailDistanceFactor) {
        this.mailDistanceFactor = mailDistanceFactor;
    }
    /**
     * @param rateCardPK The rateCardPK to set.
     */
    public void setRateCardPK(RateCardPK rateCardPK) {
        this.rateCardPK = rateCardPK;
    }
    /**
     * @param rateCardStatus The rateCardStatus to set.
     */
    public void setRateCardStatus(String rateCardStatus) {
        this.rateCardStatus = rateCardStatus;
    }
   
    /**
     * @param validityEndDate The validityEndDate to set.
     */
    public void setValidityEndDate(Calendar validityEndDate) {
        this.validityEndDate = validityEndDate;
    }
    /**
     * @param validityStartDate The validityStartDate to set.
     */
    public void setValidityStartDate(Calendar validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

	/**
     * @param companyCode
     * @param rateCardID
     * @return
     * @throws SystemException
     * @throws FinderException
     */
    public static RateCard find(String companyCode, String rateCardID)
    throws SystemException,FinderException {
    	RateCardPK entityPK  = new RateCardPK(companyCode,rateCardID) ;
    	RateCard rateCard = null;
    	try {
    		rateCard = PersistenceController.getEntityManager().find(
    				RateCard.class,entityPK);
        } catch(FinderException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
        return rateCard;
    }
    /**
     * @author A-2408
     * @param rateCardVO
     * @return
     * @exception SystemException
     */
    public void update(RateCardVO rateCardVO)
    throws SystemException{
    	try{
    	if(OPERATION_FLAG_UPDATE.equals(rateCardVO.getOperationFlag())){
    		populateAttributes(rateCardVO);
    		if(rateCardVO.getRateLineVOss()!=null && rateCardVO.getRateLineVOss().size()>0){
    			Page<RateLineVO> rateLinesVOs=rateCardVO.getRateLineVOss();
    			//System.out.println("inside iterator");

    			for(RateLineVO rateLineVO:rateLinesVOs){

    				if(OPERATION_FLAG_DELETE.equals(rateLineVO.getOperationFlag())){
    					MRARateLine.find(rateLineVO.getCompanyCode(),
    								  rateLineVO.getRateCardID(),
    								  rateLineVO.getRatelineSequenceNumber())
    							      .remove();
    					this.setTotalRatelines(getTotalRatelines()-1);
    				}
    				if(OPERATION_FLAG_UPDATE.equals(rateLineVO.getOperationFlag())){
    					MRARateLine.find(rateLineVO.getCompanyCode(),
								  rateLineVO.getRateCardID(),
								  rateLineVO.getRatelineSequenceNumber())
							      .update(rateLineVO);
    				}
    				if(OPERATION_FLAG_INSERT.equals(rateLineVO.getOperationFlag())){
    					//System.out.println("inside insert");
    					new MRARateLine(rateLineVO);
    					this.setTotalRatelines(getTotalRatelines()+1);
    				}
    			}
    		}

    	}
    	}
    	catch(FinderException finderException){
    		throw new SystemException(finderException.getMessage(),finderException);
    	}

    }
    /**
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove()
    throws SystemException{
    	Set<MRARateLine> rateLinesVOs=this.rateLines;
    	if(rateLinesVOs!=null && rateLinesVOs.size()>0){
    		for(MRARateLine rateLine:rateLinesVOs){
    			rateLine.remove();
    		}
    	}
    	try{
    	PersistenceController.getEntityManager().remove(this);
    	}
    	catch(RemoveException removeException){
    		throw new SystemException(removeException.getMessage(),removeException);
    	}
    }
    /**
     * Finds the rate card and assosciated rate lines
     *
     * @param companyCode
     * @param rateCardID
     * @return RateCardVO
     * @throws SystemException
     *
     */
    public static RateCardVO findRateCardDetails(
            String companyCode,String rateCardID,int pagenum)
    throws SystemException{
    	staticLogger().entering("RateCard","findRAteCardDetails");
    	RateCardVO rateCardVO=null;
    	RateLineFilterVO rateLineFilterVO=new RateLineFilterVO();
    	Page<RateLineVO> rateLineVOs=null;
    	rateLineFilterVO.setCompanyCode(companyCode);
    	rateLineFilterVO.setRateCardID(rateCardID);
    	rateLineFilterVO.setPageNumber(pagenum);
    	rateLineFilterVO.setTotalRecordCount(-1);
    	try{
    	rateCardVO=constructDAO().findRateCardDetails(companyCode,rateCardID);
    	//System.out.println("inside rate card ratecardvo"+rateCardVO);
    	if(rateCardVO!=null){
    	rateLineVOs=findRateLineDetails(rateLineFilterVO);
    	//System.out.println("inside rate card"+rateLineVOs.getActualPageSize());
    	rateCardVO.setRateLineVOss(rateLineVOs);
    	}
    	}
    	catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
        return rateCardVO;
    }

    /**
     * Returns the ratelines based on the filter criteria
     *
     * @param rateLineFilterVO
     * @return Page<RateLineVO>
     * @throws SystemException
     */

    public static Page<RateLineVO> findRateLineDetails(
            RateLineFilterVO rateLineFilterVO)
    throws SystemException{
    	staticLogger().entering("RateCardEntity","findRateLineDetails");
    	try{
    		return constructDAO().findRateLineDetails(rateLineFilterVO);
    	}
    	catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}


    }


    /**
     * Finds the rate cards based on the filter
     * @author a-2049
     * @param rateCardFilterVO
     * @return Page<RateCardVO>
     * @throws SystemException
     *
     */
    public static Page<RateCardVO> findAllRateCards
    					(RateCardFilterVO rateCardFilterVO)
    										throws SystemException{
    	staticLogger().entering(CLASS_NAME,"findAllRateCards");
        try {
			return constructDAO().findAllRateCards(rateCardFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
    }
    /**
     * @param rateLineFilterVO
     * @return
     * @throws SystemException
     */
    public static Collection<RateLineVO> findAllRateLines
    						(RateLineFilterVO rateLineFilterVO)
    											throws SystemException{
    	staticLogger().entering(CLASS_NAME,"findAllRateLines");
    	try{
    		return constructDAO().findAllRateLines(rateLineFilterVO);
    	}
    	catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getMessage(),persistenceException);
    	}
    }

    /**
     * @author A-2408
     * @param companyCode
     * @param rateCardId
     * @param pageNumber
     * @return
     * @throws SystemException
     */
    public static Page<RateCardLovVO> findRateCardLov(String companyCode,String rateCardId,int pageNumber)
    throws SystemException{
    	staticLogger().entering(CLASS_NAME,"findRateCardLov");
    	try{
    		return constructDAO().findRateCardLov(companyCode,rateCardId,pageNumber);
    	}
    	catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getMessage(),persistenceException);
    	}
    }


    /**
     * @return
     * @throws SystemException
     */
    private static MRADefaultsDAO constructDAO()
	throws SystemException {
    	MRADefaultsDAO mraDefaultsDAO=null;
    	try{
    		mraDefaultsDAO=(MRADefaultsDAO)PersistenceController.getEntityManager()
 												.getQueryDAO(MODULE_NAME);
    	}
    	catch(PersistenceException e){
    		throw new SystemException(e.getMessage(),e);
    	}

    	return mraDefaultsDAO;
    }

    private static Log staticLogger(){
    	return LogFactory.getLogger(MODULE_NAME);
    }

    /**
     * @param rateCardVO
     */
    private void populateAttributes(RateCardVO rateCardVO){
    	//System.out.println("size if rate lines"+rateCardVO.getRateLineVOss().getActualPageSize());
    	 this.setRateCardStatus(rateCardVO.getRateCardStatus());
    	 this.setDescription(rateCardVO.getRateCardDescription());
    	 this.setValidityStartDate(rateCardVO.getValidityStartDate());
    	 this.setValidityEndDate(rateCardVO.getValidityEndDate());
    	 this.setCreationDate(rateCardVO.getCreationDate());
    	 this.setCreationSource(rateCardVO.getCreationSource());
    	 this.setMailDistanceFactor(rateCardVO.getMailDistanceFactor());
    	 this.setCategoryTonKMRefOne(rateCardVO.getCategoryTonKMRefOne());
    	 this.setCategoryTonKMRefTwo(rateCardVO.getCategoryTonKMRefTwo());
    	 this.setCategoryTonKMRefThree(rateCardVO.getCategoryTonKMRefThree());
    	 this.setCategoryTonKMRefFour(rateCardVO.getCategoryTonKMRefFour());
    	 this.setCategoryTonKMRefFive(rateCardVO.getCategoryTonKMRefFive());
    	 this.setExchangeRate(rateCardVO.getExchangeRate());
    	 this.setLastUpdatedUser(rateCardVO.getLastUpdateUser());
    	 this.setLastUpdateTime(rateCardVO.getLastUpdateTime());



    }
    /**
     * @author A-5166
     * Added for ICRD-17262 on 07-Feb-2013
     * @param rateLineFilterVO
     * @return
     * @throws SystemException
     */
    public static Collection<RateLineVO> findRateLineDetail(RateLineFilterVO rateLineFilterVO)
    throws SystemException
    {
    	staticLogger().entering("RateCardEntity", "findRateLineDetail");
    	try
    	{
    		return constructDAO().findRateLineDetail(rateLineFilterVO);
    	}
    	catch(PersistenceException persistenceException)
    	{
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    /**
     * @author A-5166
     * Added for ICRD-17262 on 07-Feb-2013
     * @param rateLineFilterVO
     * @return
     * @throws SystemException
     */
    
    public static Collection<RateLineVO> findOverlapingRateLineDetails(RateLineFilterVO filterVO)
    throws SystemException
    {
    	staticLogger().entering("RateCardEntity", "findOverlapingRateLineDetails");
    	try
    	{
    		return constructDAO().findOverlapingRateLineDetails(filterVO);
    	}
    	catch(PersistenceException persistenceException)
    	{
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    
}
