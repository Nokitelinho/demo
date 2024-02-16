/*
 * MRAInterfacedDetails.java Created on Sep 2, 2019
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5526
 *
 */
@Entity
@Table(name = "MALMRAINTFCDDTL")
@Staleable
public class MRAInterfacedDetails{
	
	
	private static final String CLASS_NAME = "MRAInterfacedDetails";
	private static final String MODULE_NAME = "mail.mra.defaults";
	
	private MRAInterfacedDetailsPK mRAInterfacedDetailsPK;
	
	   
	

	private String referenceOne;
	private String referenceTwo;
	private String referenceThree;
	private String referenceFour;
	private String referenceFive;
	private String referenceSix;
	private String referenceSeven;
	private String referenceEight;
	private String referenceNine;
	private String referenceTen;
	private String referenceEleven;
	private String referenceTwelve;
	private String referenceThirteen;
	private String referenceFourteen;
	private String referenceFifteen;
	private String referenceSixteen;
	private String referenceSeventeen;
	private String referenceEighteen;
	private String referenceNineteen;
	private String referencTwenty;
	private String referencTwentyOne;
	private String referencTwentyTwo;
	private String referencTwentyThree;
	private String referencTwentyFour;
	private String referencTwentyFive;
	private String referencTwentySix;
	private String referencTwentySeven;
	private String referencTwentyEight;
	private String referencTwentyNine;
	private String referenceThirty;
	private String referenceThirtyOne;
	private String referenceThirtyTwo;
	private String referenceThirtyThree;
	private String interfaceFlag;
	private Calendar interfacedTime;
	
	 @EmbeddedId
		@AttributeOverrides({
	        @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	        @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))} )
	public MRAInterfacedDetailsPK getmRAInterfacedDetailsPK() {
		return mRAInterfacedDetailsPK;
	}

	public void setmRAInterfacedDetailsPK(MRAInterfacedDetailsPK mRAInterfacedDetailsPK) {
		this.mRAInterfacedDetailsPK = mRAInterfacedDetailsPK;
	}
	@Column(name = "REFONE")
	public String getReferenceOne() {
		return referenceOne;
	}

	public void setReferenceOne(String referenceOne) {
		this.referenceOne = referenceOne;
	}
	@Column(name = "REFTWO")
	public String getReferenceTwo() {
		return referenceTwo;
	}

	public void setReferenceTwo(String referenceTwo) {
		this.referenceTwo = referenceTwo;
	}
	@Column(name = "REFTHREE")
	public String getReferenceThree() {
		return referenceThree;
	}

	public void setReferenceThree(String referenceThree) {
		this.referenceThree = referenceThree;
	}
	@Column(name = "REFFOUR")
	public String getReferenceFour() {
		return referenceFour;
	}

	public void setReferenceFour(String referenceFour) {
		this.referenceFour = referenceFour;
	}
	@Column(name = "REFFIVE")
	public String getReferenceFive() {
		return referenceFive;
	}

	public void setReferenceFive(String referenceFive) {
		this.referenceFive = referenceFive;
	}
	@Column(name = "REFSIX")
	public String getReferenceSix() {
		return referenceSix;
	}

	public void setReferenceSix(String referenceSix) {
		this.referenceSix = referenceSix;
	}
	@Column(name = "REFSEVEN")
	public String getReferenceSeven() {
		return referenceSeven;
	}

	public void setReferenceSeven(String referenceSeven) {
		this.referenceSeven = referenceSeven;
	}
	@Column(name = "REFEIGHT")
	public String getReferenceEight() {
		return referenceEight;
	}

	public void setReferenceEight(String referenceEight) {
		this.referenceEight = referenceEight;
	}
	@Column(name = "REFNINE")
	public String getReferenceNine() {
		return referenceNine;
	}

	public void setReferenceNine(String referenceNine) {
		this.referenceNine = referenceNine;
	}
	@Column(name = "REFTEN")
	public String getReferenceTen() {
		return referenceTen;
	}

	public void setReferenceTen(String referenceTen) {
		this.referenceTen = referenceTen;
	}
	@Column(name = "REFELEVEN")
	public String getReferenceEleven() {
		return referenceEleven;
	}

	public void setReferenceEleven(String referenceEleven) {
		this.referenceEleven = referenceEleven;
	}
	@Column(name = "REFTWELVE")
	public String getReferenceTwelve() {
		return referenceTwelve;
	}

	public void setReferenceTwelve(String referenceTwelve) {
		this.referenceTwelve = referenceTwelve;
	}
	@Column(name = "REFTHIRTEEN")
	public String getReferenceThirteen() {
		return referenceThirteen;
	}

	public void setReferenceThirteen(String referenceThirteen) {
		this.referenceThirteen = referenceThirteen;
	}
	@Column(name = "REFFOURTEEN")
	public String getReferenceFourteen() {
		return referenceFourteen;
	}

	public void setReferenceFourteen(String referenceFourteen) {
		this.referenceFourteen = referenceFourteen;
	}
	@Column(name = "REFFIFTEEN")
	public String getReferenceFifteen() {
		return referenceFifteen;
	}

	public void setReferenceFifteen(String referenceFifteen) {
		this.referenceFifteen = referenceFifteen;
	}
	@Column(name = "REFSIXTEEN")
	public String getReferenceSixteen() {
		return referenceSixteen;
	}

	public void setReferenceSixteen(String referenceSixteen) {
		this.referenceSixteen = referenceSixteen;
	}
	@Column(name = "REFSEVENTEEN")
	public String getReferenceSeventeen() {
		return referenceSeventeen;
	}

	public void setReferenceSeventeen(String referenceSeventeen) {
		this.referenceSeventeen = referenceSeventeen;
	}
	@Column(name = "REFEIGHTEEN")
	public String getReferenceEighteen() {
		return referenceEighteen;
	}

	public void setReferenceEighteen(String referenceEighteen) {
		this.referenceEighteen = referenceEighteen;
	}
	@Column(name = "REFNINTEEN")
	public String getReferenceNineteen() {
		return referenceNineteen;
	}

	public void setReferenceNineteen(String referenceNineteen) {
		this.referenceNineteen = referenceNineteen;
	}
	@Column(name = "REFTWENTY")
	public String getReferencTwenty() {
		return referencTwenty;
	}

	public void setReferencTwenty(String referencTwenty) {
		this.referencTwenty = referencTwenty;
	}
	@Column(name = "REFTWENTYONE")
	public String getReferencTwentyOne() {
		return referencTwentyOne;
	}

	public void setReferencTwentyOne(String referencTwentyOne) {
		this.referencTwentyOne = referencTwentyOne;
	}
	@Column(name = "REFTWENTYTWO")
	public String getReferencTwentyTwo() {
		return referencTwentyTwo;
	}

	public void setReferencTwentyTwo(String referencTwentyTwo) {
		this.referencTwentyTwo = referencTwentyTwo;
	}
	@Column(name = "REFTWENTYTHREE")
	public String getReferencTwentyThree() {
		return referencTwentyThree;
	}

	public void setReferencTwentyThree(String referencTwentyThree) {
		this.referencTwentyThree = referencTwentyThree;
	}
	@Column(name = "REFTWENTYFOUR")
	public String getReferencTwentyFour() {
		return referencTwentyFour;
	}

	public void setReferencTwentyFour(String referencTwentyFour) {
		this.referencTwentyFour = referencTwentyFour;
	}
	@Column(name = "REFTWENTYFIVE")
	public String getReferencTwentyFive() {
		return referencTwentyFive;
	}

	public void setReferencTwentyFive(String referencTwentyFive) {
		this.referencTwentyFive = referencTwentyFive;
	}
	@Column(name = "REFTWENTYSIX")
	public String getReferencTwentySix() {
		return referencTwentySix;
	}

	public void setReferencTwentySix(String referencTwentySix) {
		this.referencTwentySix = referencTwentySix;
	}
	@Column(name = "REFTWENTYSEVEN")
	public String getReferencTwentySeven() {
		return referencTwentySeven;
	}

	public void setReferencTwentySeven(String referencTwentySeven) {
		this.referencTwentySeven = referencTwentySeven;
	}
	@Column(name = "REFTWENTYEIGHT")
	public String getReferencTwentyEight() {
		return referencTwentyEight;
	}

	public void setReferencTwentyEight(String referencTwentyEight) {
		this.referencTwentyEight = referencTwentyEight;
	}
	@Column(name = "REFTWENTYNINE")
	public String getReferencTwentyNine() {
		return referencTwentyNine;
	}

	public void setReferencTwentyNine(String referencTwentyNine) {
		this.referencTwentyNine = referencTwentyNine;
	}
	@Column(name = "REFTHIRTY")
	public String getReferenceThirty() {
		return referenceThirty;
	}

	public void setReferenceThirty(String referenceThirty) {
		this.referenceThirty = referenceThirty;
	}
	@Column(name = "REFTHIRTYONE")
	public String getReferenceThirtyOne() {
		return referenceThirtyOne;
	}

	public void setReferenceThirtyOne(String referenceThirtyOne) {
		this.referenceThirtyOne = referenceThirtyOne;
	}
	@Column(name = "REFTHIRTYTWO")
	public String getReferenceThirtyTwo() {
		return referenceThirtyTwo;
	}

	public void setReferenceThirtyTwo(String referenceThirtyTwo) {
		this.referenceThirtyTwo = referenceThirtyTwo;
	}
	@Column(name = "REFTHIRTYTHREE")
	public String getReferenceThirtyThree() {
		return referenceThirtyThree;
	}

	public void setReferenceThirtyThree(String referenceThirtyThree) {
		this.referenceThirtyThree = referenceThirtyThree;
	}
	@Column(name = "INTFCDFLG")
	public String getInterfaceFlag() {
		return interfaceFlag;
	}

	public void setInterfaceFlag(String interfaceFlag) {
		this.interfaceFlag = interfaceFlag;
	}

	@Column(name = "INTFCDTIM")
	public Calendar getInterfacedTime() {
		return interfacedTime;
	}

	public void setInterfacedTime(Calendar interfacedTime) {
		this.interfacedTime = interfacedTime;
	}

	/**
	 * 
	 * @log
	 */
	
	public static Log returnLogger() {
		return LogFactory.getLogger("MRA MAILSLAMASTER");
	}
	/**
	
	
	/**
	 * @return Returns the mraBillingDetailsPk.
	 */
	public MRAInterfacedDetails() {

	  }

	
	
	public MRAInterfacedDetails(FlightRevenueInterfaceVO flightRevenueInterfaceVO )throws SystemException{
		returnLogger().entering(CLASS_NAME,"MRABillingDetailsTemp-Constructor for manual proration");
		populatePK(flightRevenueInterfaceVO);
		populateAttributes(flightRevenueInterfaceVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		
		returnLogger().exiting(CLASS_NAME,"MRABillingDetailsTemp-Constructor for manual proration");
	}
	
	
	
	
	
	
	/**
	 * @param flightRevenueInterfaceVO
	 */
	private void populateAttributes(FlightRevenueInterfaceVO flightRevenueInterfaceVO) {
		
		returnLogger().entering(CLASS_NAME,"populateAttributes");	
		this.setReferenceOne(String.valueOf(flightRevenueInterfaceVO.getMailSeqNumber()));  
		this.setReferenceTwo(String.valueOf(flightRevenueInterfaceVO.getSerNumber()));  
		this.setReferenceThree(String.valueOf(flightRevenueInterfaceVO.getVersionNumber()));  
		this.setReferenceFour(flightRevenueInterfaceVO.getBranchCode());    
		this.setReferenceFive(flightRevenueInterfaceVO.getCarrTypeC());      
		this.setReferenceSix(flightRevenueInterfaceVO.getCurrency());        
		this.setReferenceSeven(flightRevenueInterfaceVO.getFirstFlightDate()); 
		this.setReferenceEight(flightRevenueInterfaceVO.getFlightDate());          
		this.setReferenceNine(flightRevenueInterfaceVO.getFlightDestination());  
		this.setReferenceTen(flightRevenueInterfaceVO.getFlightLineCode());   
		this.setReferenceEleven(flightRevenueInterfaceVO.getFlightNumber());    
		this.setReferenceTwelve(flightRevenueInterfaceVO.getFlightOrigin());   
		this.setReferenceThirteen(flightRevenueInterfaceVO.gethLNumber());          
		this.setReferenceFourteen(flightRevenueInterfaceVO.getInterfaceDate());  
		this.setReferenceFifteen(flightRevenueInterfaceVO.getTriggerPoint());   
		this.setReferenceSixteen(flightRevenueInterfaceVO.getMailCategory());    
		this.setReferenceSeventeen(flightRevenueInterfaceVO.getMailDestination());
		this.setReferenceEighteen(flightRevenueInterfaceVO.getMailNumber());          
		this.setReferenceNineteen(flightRevenueInterfaceVO.getMailOrigin());           
		this.setReferencTwenty(flightRevenueInterfaceVO.getMailWeight());          
		this.setReferencTwentyOne(flightRevenueInterfaceVO.getRateAmount());           
		this.setReferencTwentyTwo(flightRevenueInterfaceVO.getRateAmountInUSD()); 
		this.setReferencTwentyThree(flightRevenueInterfaceVO.getRegionCode());           
		this.setReferencTwentyFour(flightRevenueInterfaceVO.getrSN());                          
		this.setReferencTwentyFive(flightRevenueInterfaceVO.getSerialNumber());        
		this.setReferencTwentySix(flightRevenueInterfaceVO.getSettlement());            
		this.setReferencTwentySeven(flightRevenueInterfaceVO.getSubClassGroup());      
		this.setReferencTwentyEight(flightRevenueInterfaceVO.getBlockCheckMailWeight());  
		this.setReferencTwentyNine(flightRevenueInterfaceVO.getBlockCheckRateAmount());  
		this.setReferenceThirty(flightRevenueInterfaceVO.getBlockCheckRateAmountInUSD()); 
		this.setReferenceThirtyOne(flightRevenueInterfaceVO.getAccountDate());   
		this.setReferenceThirtyTwo(flightRevenueInterfaceVO.getAdjustCode());    
		this.setReferenceThirtyThree(flightRevenueInterfaceVO.getBillingBranch()); 
		this.setInterfaceFlag("N");        
		this.setInterfacedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));

		returnLogger().exiting(CLASS_NAME,"populateAttributes");
		
		
	}

	

	/**
	 * @param flightRevenueInterfaceVO
	 */
	private void populatePK(FlightRevenueInterfaceVO flightRevenueInterfaceVO ) {
		
		mRAInterfacedDetailsPK = new MRAInterfacedDetailsPK();
		mRAInterfacedDetailsPK.setCompanyCode(flightRevenueInterfaceVO.getCompanyCode());
		
		
	}
	
	
	public Collection<FlightRevenueInterfaceVO> findInterfaceDetails(String companycode, boolean isFromRetrigger)throws SystemException {
		
		 try {
			return  constructDAO().findInterfaceDetails(companycode,isFromRetrigger);
		} catch (PersistenceException e) {
			
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	
	 /**
		 * This method constructs
		 * @throws PersistenceException
		 * @throws SystemException
		 */
		private static MRADefaultsDAO constructDAO()
		throws PersistenceException, SystemException{
			Log log = LogFactory.getLogger("MRA BILLING");
			log.entering("MRAInterfacedDetails","constructDAO");
			EntityManager entityManager =
				PersistenceController.getEntityManager();
			return MRADefaultsDAO.class.cast(
					entityManager.getQueryDAO(MODULE_NAME));
		}
		
		/**
		 * 
		 * 	Method		:	MRABillingDetailsHistory.find
		 *	Added by 	:	a-5526 on on 02-Sep-2019
		 * 	Used for 	:	ICRD-341786
		 *	Parameters	:	@param mRAInterfacedDetailsPK
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws FinderException 
		 *	Return type	: 	MRABillingDetailsHistory
		 */
		public static MRAInterfacedDetails find(MRAInterfacedDetailsPK mRAInterfacedDetailsPK)
				throws SystemException, FinderException {
			return PersistenceController.getEntityManager().find(
					MRAInterfacedDetails.class, mRAInterfacedDetailsPK);
		}
		
		
		/**
		 * 
		 * 	Method		:	MRABillingDetailsHistory.updateInterfaceStatus
		 *	Added by 	:	a-5526 on 02-Sep-2019
		 * 	Used for 	:	ICRD-341786
		 *	Parameters	:	@param flightRevenueInterfaceVOs 
		 *	Return type	: 	void
		 * @throws SystemException 
		 * @throws FinderException 
		 */
		public void updateInterfaceStatus(Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs,String statusFLag) throws SystemException {
			
			LocalDate currTime=null;
			
			try{
			if(flightRevenueInterfaceVOs!=null && !flightRevenueInterfaceVOs.isEmpty()){
			for(FlightRevenueInterfaceVO flightRevenueInterfaceVO :flightRevenueInterfaceVOs ){
				
				MRAInterfacedDetailsPK mRAInterfacedDetailsPK=new MRAInterfacedDetailsPK();
				mRAInterfacedDetailsPK.setCompanyCode(flightRevenueInterfaceVO.getCompanyCode());
				mRAInterfacedDetailsPK.setSequenceNumber(flightRevenueInterfaceVO.getSequenceNumber());

				MRAInterfacedDetails mRAInterfacedDetails=null;
				try {
					mRAInterfacedDetails = MRAInterfacedDetails.find(mRAInterfacedDetailsPK);
				} catch (FinderException e) {
					e.getErrorCode();
				}
				if(mRAInterfacedDetails!=null){
				currTime=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
	
				mRAInterfacedDetails.setInterfacedTime(currTime);
				
				if("Y".equals(statusFLag)){//update interface flag only if interfaced successfully .IASCB-36713
				//if it is failed due to ERP down or other issues then keep the flag as N
				mRAInterfacedDetails.setInterfaceFlag(statusFLag);
				}else{
					mRAInterfacedDetails.setReferencTwentyNine(statusFLag);//updating status in  temporary  column for analysing data 
				}
				}
			}
			}
			}catch( Throwable e){
				e.getMessage();
			//If exception occurred for one data,the transaction shouldn't get roll back.	
			}

		}
		
		/**
		 * 
		 * 	Method		:	MRAInterfacedDetails.findVoidedInterfaceDetails
		 *	Added by 	:	A-8061 on 15-Oct-2019
		 * 	Used for 	:	ICRD-336689
		 *	Parameters	:	@param documentBillingDetailsVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	Collection<FlightRevenueInterfaceVO>
		 */
		public Collection<FlightRevenueInterfaceVO> findVoidedInterfaceDetails(DocumentBillingDetailsVO documentBillingDetailsVO)throws SystemException{
			
			 try {
					return  constructDAO().findVoidedInterfaceDetails(documentBillingDetailsVO);
				} catch (PersistenceException e) {
					throw new SystemException(e.getErrorCode(), e);
				}
			 
		}
		
		
		
}


	

