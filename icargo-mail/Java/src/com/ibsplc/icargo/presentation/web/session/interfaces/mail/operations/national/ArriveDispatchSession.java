/* ArriveDispatchSession.java Created on Feb 2, 2012
*
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to license terms.
*/

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;

/**
 * @author A-4810
 *
 */
public interface ArriveDispatchSession extends ScreenSession {

	/**
	 * The setter method for MailArrivalVO
	 * @param mailArrivalVO
	 */
	public void setMailArrivalVO(MailArrivalVO mailArrivalVO);
    /**
     * The getter method for MailArrivalVO
     * @return MailArrivalVO
     */
    public MailArrivalVO getMailArrivalVO();
    
	/**
	 * Method for getting FlightValidationVO from session
	 * @return flightValidationVO
	 */
	public FlightValidationVO  getFlightValidationVO();
	/**
	 * Method for setting FlightValidationVO to session
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);
	
	/**
	 * The setter method for OperationalFlightVO
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO);
    /**
     * The getter method for OperationalFlightVO
     * @return OperationalFlightVO
     */
    public OperationalFlightVO getOperationalFlightVO();
	
	/**
     * This method is used to set ContainerDetailsVOs to the session
     * @param containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs);

	/**
     * This method returns the ContainerDetails vos
     * @return containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs();
	/**
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
	public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO);
    /**
     * The getter method for containerDetailsVO
     * @return containerDetailsVO
     */
    public ContainerDetailsVO getContainerDetailsVO();
    
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeContainerType - Collection<OneTimeVO>
     */
	public void setOneTimeContainerType(Collection<OneTimeVO> oneTimeContainerType);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeContainerType - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeContainerType();
    
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
     * This method is used to set pols values to the session
     * @param pols - Collection<String>
     */
	public void setPols(Collection<String> pols);

	/**
     * This method returns the pols
     * @return Collection<String>
     */
	public Collection<String> getPols();
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeDamageCodes();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeDamageCodes(Collection<OneTimeVO> oneTimeDamageCode);
	
	/**
     * This method returns the DamagedMailbagVOS
     * @return DAMAGEMAILBAGVOS - Collection<DamagedMailbagVO>
     */
	public Collection<DamagedMailbagVO> getDamagedMailbagVOs();
	
	/**
     * This method is used to set DamagedMailbagVOs to the session
     * @param damagedMailbagVOs - Collection<DamagedMailbagVO>
     */
	public void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs);
	/**
     * This method returns the message status
     * @return String
     */
	public String getMessageStatus();
	
	/**
     * This method is used to set the message status
     * @param String
     */
	public void setMessageStatus(String status);
	
	/**
     * This method returns the MailDiscrepancyVOS
     * @return mailDiscrepancyVOs - ArrayList<MailDiscrepancyVO>
     */
	public ArrayList<MailDiscrepancyVO> getMailDiscrepancyVOs();
	
	/**
     * This method is used to set MailDiscrepancyVOs to the session
     * @param mailDiscrepancyVOs - ArrayList<MailDiscrepancyVO>
     */
	public void setMailDiscrepancyVOs(ArrayList<MailDiscrepancyVO> mailDiscrepancyVOs);
	
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeMailStatus - Collection<OneTimeVO>
     */
	public void setOneTimeMailStatus(Collection<OneTimeVO> oneTimeMailStatus);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeMailStatus - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeMailStatus();
	
	/**
     * This method is used to set MailArrivalFilterVO to the session
     * @param MailArrivalFilterVO - ArrayList<MailDiscrepancyVO>
     */
	public void setMailArrivalFilterVO(MailArrivalFilterVO mailArrivalFilterVO);
	/**
     * This method returns the MailArrivalFilterVO
     * @return MailArrivalFilterVO - ArrayList<MailDiscrepancyVO>
     */
    public MailArrivalFilterVO getMailArrivalFilterVO();
    /**
     * This method returns the FromScreen
     *
     */
    public String getFromScreen();
   
    /**
     * This method is used to set  fromScreen to the session
     * @param  fromScreen
     */
    public void setFromScreen(String fromScreen);
    
    
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
     * This method is used to set onetime values to the session
     * @param mailDensity - String
     */
	public void setMailDensity(String mailDensity);

	/**
     * This method returns the double value
     * @return mailDensity - String
     */
	public String getMailDensity();
    /**
     * @param key
     */
    public void removeMailDensity(String key) ;
    //Added by a-4810 for icrd-18030
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory();

}

