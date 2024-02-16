/*
 * MRABillingMasterTemp.java Created on Aug 27, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
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
 * @author A-3251
 *
 */
@Table(name = "MTKMRABLGMSTTMP")
@Entity
@Staleable
@Deprecated
public class MRABillingMasterTemp {

    private static final String OPFLAG_INS = "I";
    private static final String OPFLAG_UPD = "U";

    private static final String MODULE_NAME = "mail.mra.defaults";

    private static final String CLASS_NAME = "MRABillingMasterTemp";

    private static final Log log = LogFactory.getLogger("MRA BILLINGLINE");



	private MRABillingMasterTempPK billingMasterPK;

	private String orgExchangeOffice;
	private String dstExchangeOffice;
	private String mailCatagoryCode;
	private String updMailCatagoryCode;
	private String mailSubClass;
	private String updMailSubClass;
    private String rateStatus;    
    private String route;
    private double totpcs;
    private double updGrsWgt;    
    private String uldno;
    private Calendar recvDate;
    
    
    
    private Set<MRABillingDetailsTemp> billingDetails;
	

	/**
	 * @return Returns the rateStatus.
	 */
	@Column(name="RATSTA")
	public String getRateStatus() {
		return rateStatus;
	}
	/**
	 * @param rateStatus The rateStatus to set.
	 */
	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}
	
	/**
	 *
	 * @return
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "csgSequenceNumber", column = @Column(name = "CSGSEQNUM")),
			@AttributeOverride(name = "csgDocumentNumber", column = @Column(name = "CSGDOCNUM")),
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "postalAuthCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS"))})
			
	public MRABillingMasterTempPK getBillingMasterPK() {
		return billingMasterPK;
	}
	/**
	 *
	 * @param billingMasterPK
	 */
	public void setBillingMasterPK(MRABillingMasterTempPK billingMasterPK) {
		this.billingMasterPK = billingMasterPK;
	}

	


	/**
	 *
	 * @return
	 */
	@Column(name="DSTEXGOFC")
	public String getDstExchangeOffice() {
		return dstExchangeOffice;
	}
	/**
	 *
	 * @param dstExchangeOffice
	 */
	public void setDstExchangeOffice(String dstExchangeOffice) {
		this.dstExchangeOffice = dstExchangeOffice;
	}
	
	
	/**
	 *
	 * @return
	 */
	@Column(name="MALCTGCOD")
	public String getMailCatagoryCode() {
		return mailCatagoryCode;
	}
	/**
	 *
	 * @param mailCatagoryCode
	 */
	public void setMailCatagoryCode(String mailCatagoryCode) {
		this.mailCatagoryCode = mailCatagoryCode;
	}
	/**
	 *
	 * @return
	 */
	@Column(name="MALSUBCLS")
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 *
	 * @param mailSubClass
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 *
	 * @return
	 */
	@Column(name="ORGEXGOFC")
	public String getOrgExchangeOffice() {
		return orgExchangeOffice;
	}
	/**
	 *
	 * @param orgExchangeOffice
	 */
	public void setOrgExchangeOffice(String orgExchangeOffice) {
		this.orgExchangeOffice = orgExchangeOffice;
	}
		
	

	 public MRABillingMasterTemp() {

	  }
	 
	 	/** 
		 * 
		 * @param rateAuditVO
		 * @throws SystemException
		 */
		public MRABillingMasterTemp(RateAuditVO rateAuditVO )throws SystemException{			
			populatePK(rateAuditVO);
			populateAttributes(rateAuditVO);
			populateChildren(rateAuditVO);
			try {
				PersistenceController.getEntityManager().persist(this);
			  } catch (CreateException e) {
				e.getErrorCode();
				throw new SystemException(e.getMessage());
			} 		
			 
			  
		}
		
		
		/**
		 * @param rateAuditVO
		 */
		private void populatePK(RateAuditVO rateAuditVO) {
			MRABillingMasterTempPK mRABillingMasterTempPK = new MRABillingMasterTempPK();
			mRABillingMasterTempPK.setCsgSequenceNumber(rateAuditVO.getConSerNum());
			mRABillingMasterTempPK.setCsgDocumentNumber(rateAuditVO.getConDocNum());
			mRABillingMasterTempPK.setCompanyCode(rateAuditVO.getCompanyCode());
			mRABillingMasterTempPK.setBillingBasis(rateAuditVO.getBillingBasis());
			mRABillingMasterTempPK.setPostalAuthCode(rateAuditVO.getGpaCode());			
			this.setBillingMasterPK(mRABillingMasterTempPK);
		}
		
		
		/**
		 * @param rateAuditVO
		 */
		private void populateAttributes(RateAuditVO rateAuditVO) {
			
			this.setOrgExchangeOffice(rateAuditVO.getOrigin());
			this.setDstExchangeOffice(rateAuditVO.getDestination());
			this.setUpdMailCatagoryCode(rateAuditVO.getCategory());
			this.setUpdMailSubClass(rateAuditVO.getSubClass());			
			this.setRateStatus(rateAuditVO.getDsnStatus());
			this.setRoute(rateAuditVO.getRoute());
			this.setTotpcs(Double.parseDouble(rateAuditVO.getPcs()));
			this.setUpdGrsWgt(Double.parseDouble(rateAuditVO.getUpdWt()));
			this.setUldno(rateAuditVO.getUld());
			this.setRecvDate(rateAuditVO.getDsnDate().toCalendar());
			
		}
		
		/**
		 * This method populates the Child tables
		 * @param rateAuditVO
		 * @throws SystemException
		 */
		private void populateChildren(RateAuditVO rateAuditVO) throws SystemException  {
			
			Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
			MRABillingDetailsTemp mRABillingDetailsTemp=null;
			rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();
			if(rateAuditDetailsVOs!=null){
				for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){	
					 mRABillingDetailsTemp =new MRABillingDetailsTemp(rateAuditDetailsVO);	
					if(this.billingDetails!=null){
						this.billingDetails.add(mRABillingDetailsTemp);
					}else{
						this.billingDetails =  new HashSet<MRABillingDetailsTemp>();
						this.billingDetails.add(mRABillingDetailsTemp);
						}				
				 }			
			}			
		}
		
		/**
		 * @return the billingDetails
		 */
		@OneToMany
		@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "BLGBAS", referencedColumnName = "BLGBAS", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false)
		})
		public Set<MRABillingDetailsTemp> getBillingDetails() {
			return billingDetails;
		}
		/**
		 * @param billingDetails the billingDetails to set
		 */
		public void setBillingDetails(Set<MRABillingDetailsTemp> billingDetails) {
			this.billingDetails = billingDetails;
		}
		
		/**
		 * @return the updMailCatagoryCode
		 */
		@Column(name="UPDMALCTGCOD")
		public String getUpdMailCatagoryCode() {
			return updMailCatagoryCode;
		}
		/**
		 * @param updMailCatagoryCode the updMailCatagoryCode to set
		 */
		public void setUpdMailCatagoryCode(String updMailCatagoryCode) {
			this.updMailCatagoryCode = updMailCatagoryCode;
		}
		/**
		 * @return the updMailSubClass
		 */
		@Column(name="UPDMALSUBCLS")
		public String getUpdMailSubClass() {
			return updMailSubClass;
		}
		/**
		 * @param updMailSubClass the updMailSubClass to set
		 */
		public void setUpdMailSubClass(String updMailSubClass) {
			this.updMailSubClass = updMailSubClass;
		}
		/**
		 * @return the route
		 */
		@Column(name="ROU")
		public String getRoute() {
			return route;
		}
		/**
		 * @param route the route to set
		 */
		public void setRoute(String route) {
			this.route = route;
		}
		/**
		 * @return the totpcs
		 */
		@Column(name="TOTPCS")
		public double getTotpcs() {
			return totpcs;
		}
		/**
		 * @param totpcs the totpcs to set
		 */
		public void setTotpcs(double totpcs) {
			this.totpcs = totpcs;
		}
		
		/**
		 * @return the uldno
		 */
		@Column(name="ULDNUM")
		public String getUldno() {
			return uldno;
		}
		/**
		 * @param uldno the uldno to set
		 */
		public void setUldno(String uldno) {
			this.uldno = uldno;
		}
		/**
		 * @return the updGrsWgt
		 */
		@Column(name="UPDGRSWGT")
		public double getUpdGrsWgt() {
			return updGrsWgt;
		}
		/**
		 * @param updGrsWgt the updGrsWgt to set
		 */
		public void setUpdGrsWgt(double updGrsWgt) {
			this.updGrsWgt = updGrsWgt;
		}
		
		/**
		 * @return the recvDate
		 */
	    @Column(name="RCVDAT")
		public Calendar getRecvDate() {
			return recvDate;
		}
		/**
		 * @param recvDate the recvDate to set
		 */
		public void setRecvDate(Calendar recvDate) {
			this.recvDate = recvDate;
		}

		
		/**
		  * 
		  * @param rateAuditFilterVO
		  * @return
		  * @throws SystemException
		  */
		 public static RateAuditVO findListRateAuditDetailsFromTemp(RateAuditFilterVO rateAuditFilterVO)throws SystemException{
				try{
					return MRADefaultsDAO.class.cast(
							PersistenceController.getEntityManager().
							getQueryDAO("mail.mra.defaults")).findListRateAuditDetailsFromTemp(rateAuditFilterVO);
			    }catch (PersistenceException persistenceException) {
					  persistenceException.getErrorCode();
					throw new SystemException(persistenceException.getMessage());
				}
			}	
		 	 
		
		 
		    /**
		     *
		     * @param mRABillingMasterTempPK
		     * @return
		     * @throws SystemException
		     * @throws FinderException
		     */
		    public static MRABillingMasterTemp find(MRABillingMasterTempPK mRABillingMasterTempPK)
		    throws SystemException,FinderException {
		    	
		    	MRABillingMasterTemp mRABillingMasterTemp = null;
		    	try {
		    		mRABillingMasterTemp = PersistenceController.getEntityManager().find(
		    				MRABillingMasterTemp.class,mRABillingMasterTempPK);
		        } catch(FinderException e) {
		            throw new SystemException(e.getErrorCode(), e);
		        }
		        return mRABillingMasterTemp;
		    }
		    
		    /**
		     * @throws RemoveException
		     * @throws SystemException
		     */
		    public void remove()
		    throws SystemException{
		    	Set<MRABillingDetailsTemp> billingDetailVOs = this.getBillingDetails();
		    	if(billingDetailVOs!=null && billingDetailVOs.size()>0){
		    		for(MRABillingDetailsTemp billingDetailVO: billingDetailVOs){
		    			billingDetailVO.remove();
		    		}
		    	}
		    	try{
		    	PersistenceController.getEntityManager().remove(this);
		    	}
		    	catch(RemoveException removeException){
		    		throw new SystemException(removeException.getMessage(),removeException);
		    	}
		    }
			
			
				 
}
