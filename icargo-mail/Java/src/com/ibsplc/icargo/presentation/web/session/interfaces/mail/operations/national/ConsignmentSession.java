package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;

public interface ConsignmentSession extends ScreenSession {

	/**
	 * The setter method for consignmentDocumentVO
	 * @param consignmentDocumentVO
	 */
	public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO);
	
    /**
     * The getter method for ConsignmentDocumentVO
     * @return ConsignmentDocumentVO
     */
    public ConsignmentDocumentVO getConsignmentDocumentVO();
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeType - Collection<OneTimeVO>
     */
    /**
	 * The setter method for ConsignmentFilterVO
	 * @param ConsignmentFilterVO
	 */
	public void setConsignmentFilterVO(ConsignmentFilterVO consignmentFilterVO);
	
    /**
     * The getter method for ConsignmentFilterVO
     * @return ConsignmentFilterVO
     */
    public ConsignmentFilterVO getConsignmentFilterVO();
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeType - Collection<OneTimeVO>
     */
	public void setOneTimeType(Collection<OneTimeVO> oneTimeType);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeType - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeType();
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeMailClass - Collection<OneTimeVO>
     */
	public void setOneTimeMailClass(Collection<OneTimeVO> oneTimeMailClass);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeMailClass - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeMailClass();
        
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeCat - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeRSN - Collection<OneTimeVO>
     */
	public void setOneTimeRSN(Collection<OneTimeVO> oneTimeRSN);

	/**
     * This method returns the onetime vos
     * @return ONETIME_oneTimeRSN - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRSN();
	
	
	   /**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getWeightRoundingVO() ;
    /**
     * @param key
     */
    public void removeWeightRoundingVO(String key) ;
    /**
	 * @return Returns the totalRecords.
	 */
 
	 public Integer  getTotalRecords();
	 /**
		 * @param totalRecords The totalRecords to set.
		 */
	 public void setTotalRecords(int totalRecords);

}
