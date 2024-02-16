/*
 * ULDDamagePicture.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;



import java.sql.Blob;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;

/**
 * @author A-1950
 *
 */
@Staleable
@Table(name="ULDDMGPIC")
@Entity

public class ULDDamagePicture{
    
	private Log log = LogFactory.getLogger("ULD");
	/**
	 * 
	 */
    private ULDDamagePicturePK uldDamagePicturePK;
    private String pictureType;
    private Blob damagePicture;
    private String fileName;

	/**
     * Default constructor
     *
     */
	public ULDDamagePicture() {

	}

	/**
     * constructor for the BO
     * @param uldDamagePictureVO
     * @throws SystemException
     */
	public ULDDamagePicture(
	        ULDDamagePictureVO uldDamagePictureVO)
		throws SystemException {
		log.entering("ULDDamagePicture","ULDDamagePicture");
		populatePk(uldDamagePictureVO.getCompanyCode() , 
				uldDamagePictureVO.getUldNumber() , 
				uldDamagePictureVO.getSequenceNumber());
		populateAttributes(uldDamagePictureVO);
		EntityManager em = PersistenceController.getEntityManager();
		try{
			em.persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 */	
	private void populatePk(
	        String companyCode, String uldNumber ,long damageSequenceNumber) {
		log.entering("ULDDamagePicture","populatePk");
		ULDDamagePicturePK damagePicturePK = new ULDDamagePicturePK();
		damagePicturePK.setCompanyCode(companyCode);
		damagePicturePK.setUldNumber(uldNumber);
		damagePicturePK.setDamageSequenceNumber(damageSequenceNumber);
		this.setULDDamagePicturePK(damagePicturePK);
	}

	/**
     * private method to populate attributes
     *
     * @param uldDamagePictureVO
     * @throws SystemException
     */
	private void populateAttributes(
			ULDDamagePictureVO uldDamagePictureVO)
		throws SystemException {
		log.entering("ULDDamagePicture","populateAttributes");
		ImageModel imageModel = uldDamagePictureVO.getImage();
		log.log(Log.FINE, "The Blob::>", imageModel);
		log.log(Log.FINE, "imageModel.getData()::>", imageModel.getData());
		log.log(Log.FINE, "imageModel.getContentType()::>", imageModel.getContentType());
		Blob tBlob = null;
		try{
			tBlob = PersistenceUtils.createBlob(imageModel.getData());
		}catch(SystemException e){
			//To be reviewed Auto-generated catch block
			e.getMessage();
		}
		
		this.setDamagePicture(tBlob);
		this.setFileName((uldDamagePictureVO.getFileName() != null && uldDamagePictureVO.getFileName().trim().length() > 0) 
				? uldDamagePictureVO.getFileName().trim() : "DamageImage");
		this.setPictureType(imageModel.getContentType());
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static ULDDamagePicture find(
	        String companyCode, String uldNumber , long damageSequenceNumber,int imageSequenceNumber)
	throws FinderException ,  SystemException{
		ULDDamagePicturePK picturePK = new ULDDamagePicturePK();
		picturePK.setCompanyCode(companyCode); 
		picturePK.setUldNumber(uldNumber);
		picturePK.setImageSequenceNumber(imageSequenceNumber);
		picturePK.setDamageSequenceNumber(damageSequenceNumber);
	    EntityManager em  = PersistenceController.getEntityManager() ;
    	return em.find(ULDDamagePicture.class , picturePK);
	}
	
   /**
    * method to update the BO
    *
    * @param uldDamagePictureVO
    * @throws SystemException
    */
    public void update(ULDDamagePictureVO uldDamagePictureVO)
   		throws SystemException {
    	log.entering("ULDDamagePicture","update");
    	populateAttributes(uldDamagePictureVO);
    	log.exiting("ULDDamagePicture","update");
    }

	/**
     * method to remove BO
     *
     * @throws SystemException
     */
    public void remove() throws SystemException {
    	log.entering("ULDDamagePicture","remove");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
	}
    
 	/**
 	 * @return Returns the uLDDamagePicturePK.
 	 */
 	
 	@EmbeddedId
	@AttributeOverrides({
 		@AttributeOverride(name = "companyCode",
 				column = @Column(name = "CMPCOD")),
 		@AttributeOverride(name = "uldNumber",
  				column = @Column(name = "ULDNUM")),		 				
 		@AttributeOverride(name = "damageSequenceNumber", 
 				column = @Column(name = "SEQNUM")),
 		@AttributeOverride(name = "imageSequenceNumber",
  				column = @Column(name = "IMGSEQNUM")), })
       public ULDDamagePicturePK getULDDamagePicturePK() {
		return uldDamagePicturePK;
	}
 	

	/**
	 * @param damagePicturePK The uLDDamagePicturePK to set.
	 */
	public void setULDDamagePicturePK(ULDDamagePicturePK damagePicturePK) {
		uldDamagePicturePK = damagePicturePK;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	 private static ULDDefaultsDAO constructDAO() throws SystemException{
			try{
	    		EntityManager em = PersistenceController.getEntityManager();
	    		return ULDDefaultsDAO.class.cast(em.getQueryDAO(
	    				ULDDefaultsPersistenceConstants.MODULE_NAME));
	    	}catch(PersistenceException persistenceException){
	    		throw new SystemException(persistenceException.getErrorCode());
	    	}
		  }
	 
	/**
	 * 
	 * @param ULDNumber
	 * @param companycode
	 * @return pictureList
	 * @throws SystemException
	 */
	public static Collection<ULDDamagePictureVO> findULDDamagePictures(ULDDamageFilterVO uldDamageFilterVO)throws SystemException {   
    	Collection<ULDDamagePictureVO> pictureList = null;
        try {
        	pictureList = constructDAO().findULDDamagePictures(uldDamageFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
        return pictureList ;
    } 
	

	@Lob
	@Column(name = "DMGPIC")
	/**
	 * @return Returns the damagePicture.
	 */
	
	public Blob getDamagePicture() {
		return damagePicture;
	}

	/**
	 * @param damagePicture The damagePicture to set.
	 */
	public void setDamagePicture(Blob damagePicture) {
		this.damagePicture = damagePicture;
	}


	@Column(name = "PICTYP")
	/**
	 * @return Returns the pictureType.
	 */
	public String getPictureType() {
		return pictureType;
	}

	/**
	 * @param pictureType The pictureType to set.
	 */
	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}
	@Column(name = "PICFILNAM")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
