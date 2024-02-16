/*
 * MailSLAMaster.java Created on Apr 2, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLAFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */

@Entity
@Table(name = "MTKSLAMST")
@Staleable
@Deprecated
public class MailSLAMaster {
	
	//private static final Log log  = LogFactory.getLogger("MRA MAILSLAMASTER");
	private static final String MODULE_NAME = "mail.mra.defaults";
	private MailSLAMasterPK mailSLAMasterPK;
	private String currencyCode;
	private String slaDescription;
	
	

    /**
     * Default Constructor
     */
    public MailSLAMaster() {        
    }
    
    /**
	 * 
	 * @return
	 */
	public static Log returnLogger() {
		return LogFactory.getLogger("MRA MAILSLAMASTER");
	}

	/**
	 * @return Returns the currencyCode.
	 */
	   @Column(name="CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return Returns the mailSLAMasterPK.
	 */
	 @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="slaId", column=@Column(name="SLAIDR"))}
	)
	public MailSLAMasterPK getMailSLAMasterPK() {
		return mailSLAMasterPK;
	}

	/**
	 * @param mailSLAMasterPK The mailSLAMasterPK to set.
	 */
	public void setMailSLAMasterPK(MailSLAMasterPK mailSLAMasterPK) {
		this.mailSLAMasterPK = mailSLAMasterPK;
	}

	/**
	 * @return Returns the slaDescription.
	 */
	@Column(name="SLADSC")
	public String getSlaDescription() {
		return slaDescription;
	}

	/**
	 * @param slaDescription The slaDescription to set.
	 */
	public void setSlaDescription(String slaDescription) {
		this.slaDescription = slaDescription;
	}
	
	/**
	 * 
	 * @param mailSLAVo
	 * @throws SystemException
	 */	
	public MailSLAMaster(MailSLAVO mailSLAVo)throws SystemException{
		returnLogger().entering("MailSLAMaster","MailSLAMaster");
		populatePK(mailSLAVo);
		populateAttributes(mailSLAVo);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}		
		returnLogger().exiting("MailSLAMaster","MailSLAMaster");
	}
    /**
     * 
     * @param mailSLAVo
     */
    private void populatePK(MailSLAVO mailSLAVo) {
    	returnLogger().entering("MailSLAMaster","populatePK");
    	MailSLAMasterPK mailSLAMasterPk=new MailSLAMasterPK(mailSLAVo.getCompanyCode(),mailSLAVo.getSlaId());
		this.setMailSLAMasterPK(mailSLAMasterPk);
		returnLogger().exiting("MailSLAMaster","populatePK");
	}


    /**
     * 
     * @param mailSLAVo
     */
	private void populateAttributes(MailSLAVO mailSLAVo) {
		returnLogger().entering("MailSLAMaster","populateAttributes");
		this.setCurrencyCode(mailSLAVo.getCurrency());
		this.setSlaDescription(mailSLAVo.getDescription());
		returnLogger().exiting("MailSLAMaster","populateAttributes");
		
	}


	/**
	 * 
	 * @param companyCode
	 * @param slaId
	 * @return
	 * @throws SystemException
	 */
	public static MailSLAMaster find(String companyCode,String slaId)throws SystemException{
		returnLogger().entering("MailSLAMaster","find");
		MailSLAMaster mailSLAMaster=null;
		MailSLAMasterPK mailSLAMasterPk = new MailSLAMasterPK(companyCode,slaId);
		try {
			mailSLAMaster=PersistenceController.getEntityManager().find(
					MailSLAMaster.class,mailSLAMasterPk);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting("MailSLAMaster","find");
		return mailSLAMaster;
		
	}
	
	/**
	 * 
	 * @param mailSLAVo
	 * @throws SystemException
	 */
	public void update(MailSLAVO mailSLAVo)
	throws SystemException{
		returnLogger().entering("MailSLAMaster","update");
		populateAttributes(mailSLAVo);		
		//populateChildren(mailSLAVo);
		returnLogger().exiting("MailSLAMaster","update");
		
		
	}
	/**
	 * 
	 * @param mailSLAVo
	 * @throws SystemException
	
	private void populateChildren(MailSLAVO mailSLAVo) throws SystemException{
		returnLogger().entering("MailSLAMaster","populateChildren");
		Collection<MailSLADetailsVO> mailSLADetailsVOs = 
			mailSLAVo.getMailSLADetailsVos();
		if(mailSLADetailsVOs != null && mailSLADetailsVOs.size() > 0){
			for(MailSLADetailsVO mailSLADetailsVo : mailSLADetailsVOs){
				if(MailSLADetailsVO.OPERATION_FLAG_INSERT.equals(mailSLADetailsVo.getOperationFlag())){
					new MailSLADetail(mailSLADetailsVo);
				}
				else if(MailSLADetailsVO.OPERATION_FLAG_UPDATE.equals(mailSLADetailsVo.getOperationFlag())) {
					MailSLADetail mailSLADetail = MailSLADetail.find(
							mailSLADetailsVo.getCompanyCode(),
							mailSLADetailsVo.getSlaId(),
							mailSLADetailsVo.getSerialNumber());
					mailSLADetail.update(mailSLADetailsVo);
				}
				else if(MailSLADetailsVO.OPERATION_FLAG_DELETE.equals(mailSLADetailsVo.getOperationFlag())) {
					MailSLADetail mailSLADetail = MailSLADetail.find
						(mailSLADetailsVo.getCompanyCode(),
						 mailSLADetailsVo.getSlaId(),
						 mailSLADetailsVo.getSerialNumber());					
					mailSLADetail.remove();
					
				}
			}
		}
		returnLogger().entering("MailSLAMaster","populateChildren");
	}
   */
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		returnLogger().entering("MailSLAMaster","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting("MailSLAMaster","remove");
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @param slaId
	 * @return mailSLAVo
	 * @throws SystemException
	 */
	public static MailSLAVO findMailSla(String companyCode, String slaId) throws SystemException {
		returnLogger().entering("MailSLAMaster","findBillingMatrixDetails");
		MailSLAVO  mailSLAVo = null;
		try{
			MRADefaultsDAO mraDefaultsDAO = MRADefaultsDAO.class.cast(PersistenceController.getEntityManager()
					.getQueryDAO (MODULE_NAME));	
			mailSLAVo=mraDefaultsDAO.findMailSla(companyCode,slaId);
			returnLogger().log(Log.FINE,"Returned vo...."+mailSLAVo);
		}catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
        }catch(SystemException systemException){
			systemException.getMessage();
        }		
		return mailSLAVo;		
	}
	
	/**
	 * Method to construct corresponding DAO
	 * @return
	 * @throws SystemException
	 */
    private static MRADefaultsDAO constructDAO()
    							throws SystemException {
    	
    	MRADefaultsDAO queryDAO =null;
        try {
			 queryDAO = (MRADefaultsDAO)PersistenceController
			 								.getEntityManager()
							 				.getQueryDAO(MODULE_NAME);
        } catch (PersistenceException e) {
				throw new SystemException(e.getMessage(),e);
        }

        return queryDAO;
    }
	
	/**
	 * Added by A-2521 for SLAId Lov
	 * @param slaFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<SLADetailsVO> displaySLADetails(
			SLAFilterVO slaFilterVO)throws SystemException{		
			return constructDAO().displaySLADetails( slaFilterVO );
		
	}
	

	/**
     * @param companyCode
     * @param slaId
     * @return int
     * @throws SystemException
     */
    
	public static int findMaxSerialNumber(String companyCode, String slaId)throws SystemException{
		int serialNumber=0;
		try {			
			serialNumber= constructDAO().findMailSLAMaxSerialNumber(companyCode,slaId);
	  	  } catch (SystemException systemException){
				systemException.getMessage();
	  	 }	  	 
	  	  return serialNumber;
	}
	
	/**
	 * This method validates Service Level Activity(SLA) codes
	 * 
	 * @author A-2518
	 * @param companyCode
	 * @param slaCodes
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> validateSLACodes(String companyCode,
			Collection<String> slaCodes) throws SystemException {
		returnLogger().entering("MailSLAMaster", "validateSLACodes");
		try {
			return constructDAO().validateSLACodes(companyCode, slaCodes);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
	}	
}
