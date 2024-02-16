/*
 * BillingMatrix.java Created on Feb 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-1556
 *
 */
//TODO

@Entity
@Table(name = "MALMRABLGMTX")
//@Staleable


public class BillingMatrix {


    private static final String MODULE_NAME = "mail.mra.defaults";
    
    private static final String OPFLAG_INS = "I";
    private static final String OPFLAG_UPD = "U";
    private static final String OPFLAG_DEL = "D";

	private BillingMatrixPK billingMatrixPK;
    
    private String billingMatrixStatus;
    private String description;
    private Calendar validityStartDate;
    private Calendar validityEndDate; 
    private String poaCode;
    private int airlineIdentifier;
    private String airlineCode;
    private int totalBillinglines;
    private String lastUpdatedUser;
    private Calendar lastUpdatedTime;
    
    private Set<BillingLine> billingLines;
    
    /**
     * Default Constructor
     */
    public BillingMatrix() {        
    }

    /**
	 * 
	 * @return
	 */
	public static Log returnLogger() {
		return LogFactory.getLogger("MRA BILLINGMATRIX");
	}  
    
    /**
     * @return Returns the billingMatrixPK.
     */
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="billingMatrixID", column=@Column(name="BLGMTXCOD"))}
	)
    public BillingMatrixPK getBillingMatrixPK() {
        return billingMatrixPK;
    }
    /**
     * @param billingMatrixPK The billingMatrixPK to set.
     */
    public void setBillingMatrixPK(BillingMatrixPK billingMatrixPK) {
        this.billingMatrixPK = billingMatrixPK;
    }
 
	/**
	 * @return the billingMatrixStatus
	 */
	@Column(name="BLGMTXSTA")
	public String getBillingMatrixStatus() {
		return billingMatrixStatus;
	}

	/**
	 * @param billingMatrixStatus the billingMatrixStatus to set
	 */
	public void setBillingMatrixStatus(String billingMatrixStatus) {
		this.billingMatrixStatus = billingMatrixStatus;
	}

	/**
	 * @return the description
	 */
	@Column(name="BLGMTXDES")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the validityStartDate
	 */
	@Column(name="VLDSTRDAT")
	public Calendar getValidityStartDate() {
		return validityStartDate;
	}

	/**
	 * @param validityStartDate the validityStartDate to set
	 */
	public void setValidityStartDate(Calendar validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	/**
	 * @return the validityEndDate
	 */
	@Column(name="VLDENDDAT")
	public Calendar getValidityEndDate() {
		return validityEndDate;
	}

	/**
	 * @param validityEndDate the validityEndDate to set
	 */
	public void setValidityEndDate(Calendar validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	/**
	 * @return the poaCode
	 */
	@Column(name="POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the airlineIdentifier
	 */
	@Column(name="ARLIDR")
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier the airlineIdentifier to set
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return the airlineCode
	 */
	@Column(name="ARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return the totalBillinglines
	 */
	@Column(name="TOTBLGLIN")
	public int getTotalBillinglines() {
		return totalBillinglines;
	}

	/**
	 * @param totalBillinglines the totalBillinglines to set
	 */
	public void setTotalBillinglines(int totalBillinglines) {
		this.totalBillinglines = totalBillinglines;
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
	 * @return Returns the billingLines.
	 */
    @OneToMany
    @JoinColumns( {
	 @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 @JoinColumn(name = "BLGMTXCOD", referencedColumnName = "BLGMTXCOD", insertable=false, updatable=false)})
	public Set<BillingLine> getBillingLines() {
		return billingLines;
	}


	/**
	 * @param billingLines The billingLines to set.
	 */
	public void setBillingLines(Set<BillingLine> billingLines) {
		this.billingLines = billingLines;
	}
	

	/**
	 * 
	 * @param billingMatrixVO
	 * @throws SystemException
	 */
	public BillingMatrix(BillingMatrixVO billingMatrixVO)throws SystemException{
		returnLogger().entering("BillingMatrix","BillingMatrix");
		populatePK(billingMatrixVO);
		populateAttributes(billingMatrixVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		populateChildren(billingMatrixVO);
		returnLogger().exiting("BillingMatrix","BillingMatrix");
	}
    /**
     * 
     * @param billingMatrixVO
     */
    private void populatePK(BillingMatrixVO billingMatrixVO) {
    	returnLogger().entering("BillingMatrix","populatePK");
		BillingMatrixPK billingMatrixpk=new BillingMatrixPK(billingMatrixVO.getCompanyCode(),billingMatrixVO.getBillingMatrixId());
		this.setBillingMatrixPK(billingMatrixpk);
		returnLogger().exiting("BillingMatrix","populatePK");
	}


    /**
     * 
     * @param billingMatrixVO
     */
	private void populateAttributes(BillingMatrixVO billingMatrixVO) {
		returnLogger().entering("BillingMatrix","populateAttributes");		
		this.setDescription(billingMatrixVO.getDescription());
		this.setBillingMatrixStatus(billingMatrixVO.getBillingMatrixStatus());
		this.setValidityStartDate(billingMatrixVO.getValidityStartDate());
		this.setValidityEndDate(billingMatrixVO.getValidityEndDate());
		this.setTotalBillinglines(billingMatrixVO.getTotalBillinglines());
		this.setBillingMatrixStatus(billingMatrixVO.getBillingMatrixStatus());
		/**
		 * for optimistic locking
		 */
		this.setLastUpdatedTime(billingMatrixVO.getLastUpdatedTime());
		this.setLastUpdatedUser(billingMatrixVO.getLastUpdatedUser());
		returnLogger().exiting("BillingMatrix","populateAttributes");
		
	}


	/**
     * 
     * @param companyCode
     * @param billingMatrixId
     * @return
     * @throws SystemException
     */
	public static BillingMatrix find(String companyCode,String billingMatrixId)throws SystemException{
		returnLogger().entering("BillingMatrix","find");
		BillingMatrix billingMatrix=null;
		BillingMatrixPK blgMatrixPK=new BillingMatrixPK(companyCode,billingMatrixId);
		try {
			billingMatrix=PersistenceController.getEntityManager().find(
					BillingMatrix.class,blgMatrixPK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting("BillingMatrix","find");
		return billingMatrix;
		
	}
	
	
	/**
	 * 
	 * @param billingMatrixVO
	 * @throws SystemException
	 */
	public void update(BillingMatrixVO billingMatrixVO)
	throws SystemException{
		returnLogger().entering("BillingMatrix","update");
		populateAttributes(billingMatrixVO);
		
		populateChildren(billingMatrixVO);
		returnLogger().exiting("BillingMatrix","update");
		
		
	}
	/**
	 * 
	 * @param billingMatrixVO
	 * @throws SystemException
	 */
	private void populateChildren(BillingMatrixVO billingMatrixVO) throws SystemException{
		returnLogger().entering("BillingMatrix","populateChildren");
		Collection<BillingLineVO> billingLineVOs = 
			billingMatrixVO.getBillingLineVOs();
		BillingMatrixAuditVO billingMatrixAuditVO = new BillingMatrixAuditVO(
				BillingMatrixAuditVO.AUDIT_MODULENAME,
				BillingMatrixAuditVO.AUDIT_SUBMODULENAME,
				BillingMatrixAuditVO.AUDIT_ENTITY);
		if(billingLineVOs != null && billingLineVOs.size() > 0){
			for(BillingLineVO billingLineVO : billingLineVOs){
				if(OPFLAG_INS.equals(billingLineVO.getOperationFlag())){

					new BillingLine(billingMatrixVO.getBillingMatrixId(),billingLineVO);
				}
				else if(OPFLAG_UPD.equals(billingLineVO.getOperationFlag())) {
					
					BillingLine billingLine = BillingLine.find(billingMatrixVO.getCompanyCode()
							,billingMatrixVO.getBillingMatrixId(),billingLineVO.getBillingLineSequenceNumber());
					StringBuilder oldParamterInfo=new StringBuilder(" Old Parameter Values: ");//added by a-7531 for icrd-224979
					StringBuilder newParamterInfo=new StringBuilder(" New Parameter Values: ");
					billingMatrixAuditVO= (BillingMatrixAuditVO)AuditUtils.populateAuditDetails(billingMatrixAuditVO, billingLine, false);
					billingLine.update(billingMatrixVO.getBillingMatrixId(),billingLineVO);
					//added by a-7531 for icrd-224979 starts
					Collection<BillingLineParameterVO> oldBlgLinPar= billingLineVO.getOldBlgLinPars();
					Collection<BillingLineParameterVO> newBillingParameters=billingLineVO.getBillingLineParameters();
					Collection<BillingLineParameterVO> vosForDesc = findblgLinePars(billingLineVO.getCompanyCode());
					 if(newBillingParameters!=null)
					 {
						 for(BillingLineParameterVO newParameters:newBillingParameters)
						 {
							 for(BillingLineParameterVO vo : vosForDesc){
								 if(vo.getParameterCode().equals(newParameters.getParameterCode())){
									 newParameters.setParameterDesc(vo.getParameterDesc());
									 break;
								 }
							 }
							 newParamterInfo.append(" ").append(newParameters.getParameterDesc())
							 .append(": Value : ").append(newParameters.getParameterValue()).append(" Exclude flag: ")
							 .append(newParameters.getExcludeFlag()).append(" ,");
						 }
					 }
					 if(oldBlgLinPar!=null)
					 {
						 for(BillingLineParameterVO oldParameters:oldBlgLinPar)
						 {
							 for(BillingLineParameterVO vo : vosForDesc){
								 if(vo.getParameterCode().equals(oldParameters.getParameterCode())){
									 oldParameters.setParameterDesc(vo.getParameterDesc());
									 break;
								 }
							 }
							 oldParamterInfo.append(" ").append(oldParameters.getParameterDesc())
							 .append(": Value : ").append(oldParameters.getParameterValue()).append(" Exclude flag: ")
							 .append(oldParameters.getExcludeFlag());
						 }
							
					 }
//added by a-7531 for icrd-224979 ends
					billingMatrixAuditVO= (BillingMatrixAuditVO)AuditUtils.populateAuditDetails(billingMatrixAuditVO, billingLine, false);
				
					
					StringBuilder additionalinfo =new StringBuilder();
					additionalinfo.append("Rate Line ID: ");
					additionalinfo.append(billingLineVO.getBillingLineSequenceNumber());
					additionalinfo.append(findHistoryValues(billingMatrixAuditVO));
								additionalinfo.append(" ").append(newParamterInfo).append(oldParamterInfo);//added by a-7531 for icrd-224979
					billingMatrixAuditVO.setAdditionalInformation(additionalinfo.toString());
					billingMatrixAuditVO.setCompanyCode(billingMatrixVO.getCompanyCode());
					billingMatrixAuditVO.setBillingmatrixID(billingMatrixVO.getBillingMatrixId());
					billingMatrixAuditVO.setActionCode("UPDATED the rate line");
					billingMatrixAuditVO.setAuditRemarks("UPDATED the rate line");
					AuditUtils.performAudit(billingMatrixAuditVO);
							
					
					/**
					 * Audit
					 */
					
				}
				else if(OPFLAG_DEL.equals(billingLineVO.getOperationFlag())) {

					BillingLine billingLine = BillingLine.find(billingMatrixVO.getCompanyCode(),
							billingMatrixVO.getBillingMatrixId(),billingLineVO.getBillingLineSequenceNumber());
					billingLine.deleteParameters(); 
					billingLine.remove();
					
				}
			}
		}
		returnLogger().entering("BillingMatrix","populateChildren");
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingMatrixAuditVO
	 * @return
	 */
	private String findHistoryValues(BillingMatrixAuditVO billingMatrixAuditVO) {
		StringBuffer newFieldInfo = new StringBuffer("New Values:");
		StringBuffer oldFieldInfo = new StringBuffer("Old Values:");
		StringBuffer addInfo = new StringBuffer();
		String info = "";
		addInfo.append("<b/>");
		int count = 0;
		if ((billingMatrixAuditVO.getAuditFields() != null)
				&& (billingMatrixAuditVO.getAuditFields().size() > 0)) {
			for (AuditFieldVO auditField : billingMatrixAuditVO
					.getAuditFields()) {
				if (auditField != null && !"BillingLineParameter".equals(auditField.getDescription())) {
					if (!auditField.getOldValue().equals(
							auditField.getNewValue())) {
						count++;
						addInfo.append(auditField.getDescription()).append(",");
						oldFieldInfo=oldFieldInfo.append(auditField.getDescription())
								.append(":").append(auditField.getOldValue()).append("  ");//modified by a7531 for icrd-224979
						if (auditField.getNewValue() != null) {
							newFieldInfo=newFieldInfo.append(auditField.getNewValue());
									
						}
					}
				}
			}
		}
		if(count > 0)
		  {
		  info = addInfo.append(newFieldInfo.toString()).append(oldFieldInfo.toString()).toString();
		  }
		return info;
		// billingSitevo.setNewFieldValue(info.concat((newFieldInfo.toString()).toString()));
	}
	/**
	 * 
	 * @author A-5255
	 * @param textToformat
	 */
private String formatBillingLineText(String textToformat){
	
	String innertokens[] = null;
	String colonToken[] = null;
	StringBuilder mailString = new StringBuilder();
	StringBuilder surString = null;
	StringBuilder chargeDetails = null;
	String ratingBasis = "";
	String chargeType = "";
	boolean minCharge = false;
	boolean normalRate = false;
	final HashMap<String, String> ratingBasisMap = new HashMap<String, String>() {
		{
			put("FC", "Flat Rate");
			put("WB", "Weight Break");
			put("FR", "Flat Rate");
		}
	};
	final HashMap<String, String> chargeTypeMap = new HashMap<String, String>() {
		{
			put("FS", "Fuel Charge");
			put("M", "Mail");
			put("SS", "Security Surcharge");
			put("HC", "Handling Charge");
		}
	};
	if(textToformat!=null && textToformat.length()>0){
	if (textToformat.startsWith("Rating Basis")){
	String tokens[] = textToformat.split("Rating Basis");
	for (int i = 0; i < tokens.length; i++) {
		if ("".equals(tokens[i])) {
			continue;
		}
		innertokens = tokens[i].split(";");
		if (innertokens != null && innertokens.length > 0) {

			ratingBasis = innertokens[0].replaceAll(":", "").trim();
			chargeType = innertokens[1].replaceAll(":", "")
					.replaceAll("BillingLineChargePK", "")
					.replaceAll("BillingLineCharge", "")
					.replaceAll("chargeType", "").trim();
			if (ratingBasisMap.containsKey(ratingBasis)) {
				chargeDetails = new StringBuilder();

				if (chargeTypeMap.containsKey(chargeType)) {
					chargeDetails.append("Charge head="
							+ chargeTypeMap.get(chargeType) + " ");
				}

				chargeDetails.append("Rating Basis = "
						+ ratingBasisMap.get(ratingBasis));
				chargeDetails.append("<br/>");

				for (int j = 2; j < innertokens.length; j++) {
					colonToken = innertokens[j].split(":");
					if (colonToken.length == 2) {
						if ("FC".equals(ratingBasis)
								|| "FR".equals(ratingBasis)) {
							if ("applicableRate".equals(colonToken[0]
									.trim())) {
								chargeDetails.append(ratingBasisMap
										.get(ratingBasis)
										+ "="
										+ colonToken[1] + "<br/>");
								break;
							}
						} else if ("WB".equals(ratingBasis)) {
							if ("Weight".equals(colonToken[0].trim())) {
								if ("0.0".equals(colonToken[1].trim())) {
									normalRate = true;
								} else if ("-1.0".equals(colonToken[1]
										.trim())) {
									minCharge = true;
								} else {
									chargeDetails.append("Weight ="
											+ colonToken[1]);
								}
							} else if ("applicableRate"
									.equals(colonToken[0].trim())) {
								if (minCharge) {
									minCharge = false;
									chargeDetails.append(" Min.Charge ="
											+ colonToken[1] + "<br/>");
								} else if (normalRate) {
									normalRate = false;
									chargeDetails.append(" Normal Rate="
											+ colonToken[1] + "<br/>");
								} else {
									chargeDetails.append(" Rate ="
											+ colonToken[1] + "<br/>");
								}
							}
						}
					}
				}
				if ("M".equals(chargeType)) {
					mailString.append("Mail Charges = Y ");
					mailString.append(chargeDetails);
				} else if ("FS".equals(chargeType)
						|| "SS".equals(chargeType)
						|| "HC".equals(chargeType)) {
					if (surString == null) {
						surString = new StringBuilder("Surcharge = Y ");
					}
					surString.append(chargeDetails);
				}
			}

		}
		}
	}
	}
	return mailString.append(surString==null ? "":surString.toString()).toString();
}

	/**
	 * 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		returnLogger().entering("BillingMatrix","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting("BillingMatrix","remove");
	}
	
	/**
	 * This method finds All BillingMatrix 
	 * @author A-2280
	 * @param billingMatrixFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<BillingMatrixVO> findAllBillingMatrix(BillingMatrixFilterVO billingMatrixFilterVO)throws SystemException{
		returnLogger().entering("BillingMatrix","findAllBillingMatrix");
		Page<BillingMatrixVO> billingMatrixVOs=null;
		try {
			billingMatrixVOs= constructDAO().findAllBillingMatrix(billingMatrixFilterVO);
		} catch (PersistenceException e) {	
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}
		return billingMatrixVOs;
		
	}
	
	/**
	 * @author A-2398
	 * @param blgMatFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static BillingMatrixVO findBillingMatrixDetails(BillingMatrixFilterVO blgMatFilterVO)throws SystemException{
		returnLogger().entering("BillingMatrix","findBillingMatrixDetails");
		BillingMatrixVO  billingMatrixVO = null;
		try{
			MRADefaultsDAO mraDefaultsDAO = MRADefaultsDAO.class.cast(PersistenceController.getEntityManager()
					.getQueryDAO (MODULE_NAME));	
			billingMatrixVO=mraDefaultsDAO.findBillingMatrixDetails(blgMatFilterVO);
			returnLogger().log(Log.FINE,"Returned vo...."+billingMatrixVO);
		}catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
        }catch(SystemException systemException){
			systemException.getMessage();
        }
		
		return billingMatrixVO;
		
	}
	/**
	 * @param blgLineFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<BillingLineVO> findBillingLineValues(BillingLineFilterVO blgLineFilterVO)
	throws SystemException{
		returnLogger().entering( "BillingMatrix" , "findBillingLineValues" );
		Page<BillingLineVO> billingLineDetails = null;
		try {
			billingLineDetails =constructDAO().findBillingLineValues( blgLineFilterVO );
		} catch ( PersistenceException persistenceException ) {
			throw new SystemException( persistenceException.getMessage(), persistenceException );
		}
		returnLogger().exiting( "BillingMatrix" , "findBillingLineValues" );
		return billingLineDetails;
	}
	/**
	 * @author A-2398
	 * @param blgLineFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<BillingLineVO> findBillingLineDetails(BillingLineFilterVO blgLineFilterVO)
	throws SystemException{
		returnLogger().entering("BillingMatrix","findBillingLineDetails");
		
		Page<BillingLineVO> blgDetailsPage = null;
		try{
			 blgDetailsPage = constructDAO().findBillingLineDetails(blgLineFilterVO);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
		returnLogger().exiting("BillingMatrix","findBillingLineDetails");
		return blgDetailsPage;
	}
	/**
	 * 
	 * 	Method		:	BillingMatrix.findblgLinePars
	 *	Added by 	:	A-7531 on 20-Feb-2018
	 * 	Used for 	:
	 *	Parameters	:	@param cmpcod
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<BillingLineParameterVO>
	 */
	public Collection<BillingLineParameterVO> findblgLinePars(String cmpcod)
	throws SystemException{
		returnLogger().entering("BillingMatrix","findblgLinePars");
		Collection<BillingLineParameterVO> billingLineParameter = null;

		try{
			billingLineParameter = constructDAO().findblgLinePars(cmpcod);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
		returnLogger().exiting("BillingMatrix","findBillingLineDetails");
		return billingLineParameter;
	}
	
	/**
	 * @author A-2280
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private static MRADefaultsDAO constructDAO()
	  throws PersistenceException, SystemException {
		returnLogger().entering( "BillingMatrix" , "constructDAO" );
		MRADefaultsDAO mraDefaultsDAO = null;
		EntityManager entityManager = PersistenceController.getEntityManager();
		mraDefaultsDAO = MRADefaultsDAO.class.cast( entityManager.getQueryDAO( MODULE_NAME ) );
		returnLogger().exiting( "BillingMatrix" , "constructDAO" );
		return mraDefaultsDAO;
	}
	/**
	 * Method to find the billin lines with the provided parameters
	 * 
	 * @author A-1872
	 * Mar 5, 2007
	 * @param billingLineVO
	 * @param billingLineStatus
	 * @return Collection<BillingLineVO>
	 * @throws SystemException
	 */
	public static Collection<BillingLineVO> findOverlappingBillingLines ( BillingLineVO billingLineVO, 
	 String billingLineStatus ) throws SystemException {
		returnLogger().entering( "BillingMatrix" , "constructDAO" );
		try {
			return constructDAO().findOverlappingBillingLines( billingLineVO, billingLineStatus );
		} catch ( PersistenceException persistenceException ) {
			throw new SystemException( persistenceException.getMessage(), persistenceException );
		}
	}
	/**
	 * Method for finding the billing lines with the desired status
	 * TODO Purpose
	 * * @author A-1872
	 * * Mar 6, 2007
	 * * * @param billingLineFilterVO
	 * * * @return findBillingLines
	 * * * @throws SystemException
	 */
	public static Collection<BillingLineVO> findBillingLines( BillingLineFilterVO billingLineFilterVO )
	  throws SystemException {
		returnLogger().entering( "BillingMatrix" , "findBillingLines" );
		Collection<BillingLineVO> findBillingLines = null;
		try {
			findBillingLines =constructDAO().findBillingLines( billingLineFilterVO );
		} catch ( PersistenceException persistenceException ) {
			throw new SystemException( persistenceException.getMessage(), persistenceException );
		}
		returnLogger().exiting( "BillingMatrix" , "findBillingLines" );
		return findBillingLines;
	}
	/**
	 * @author A-2408
	 * @param companyCode
	 * @param billingMatrixCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<BillingMatrixLovVO> findBillingMatrixLov(String companyCode,String billingMatrixCode,int pageNumber)
	throws SystemException{
		returnLogger().entering( "BillingMatrix" , "constructDAO" );
		try{
			return constructDAO().findBillingMatrixLov(companyCode,billingMatrixCode,pageNumber);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException( persistenceException.getMessage(), persistenceException );
		}
	}
	
	/**
	 * This method validates billing matrix codes
	 * 
	 * @author A-2518
	 * @param companyCode
	 * @param billingMatrixCodes
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public static Collection<String> validateBillingMatrixCodes(
			String companyCode, Collection<String> billingMatrixCodes)
			throws SystemException {
		returnLogger().entering("BillingMatrix", "validateBillingMatrixCodes");
		try {
			return constructDAO().validateBillingMatrixCodes(companyCode,
					billingMatrixCodes);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
	}
	 /**
 	 * Process billing matrix upload details.
 	 *
 	 * @param filterVO the filter vo
 	 * @return the string
 	 * @throws SystemException the system exception
 	 */
 	public static String processBillingMatrixUploadDetails(FileUploadFilterVO filterVO)
	 			throws SystemException{
 		returnLogger().entering("BillingMatrix", "processBillingMatrixUploadDetails");
	    try
	    {
	      MRADefaultsDAO mRADefaultsDAO = (MRADefaultsDAO)MRADefaultsDAO.class.cast(PersistenceController.getEntityManager().getQueryDAO(MODULE_NAME));
	      return mRADefaultsDAO.processBillingMatrixUploadDetails(filterVO);
	      } catch (PersistenceException persistenceException) {
	    	  throw new SystemException(persistenceException.getErrorCode());
		}
	}
 
}
