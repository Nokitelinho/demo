/* ArriveDispatchSessionImpl.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

/*
  *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

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
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;

/**
 * @author A-4810
 *
 */
public class ArriveDispatchSessionImpl extends AbstractScreenSession
        implements ArriveDispatchSession {

	private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_MAILARRIVALVO = "mailArrivalVO";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightValidationVO";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalFlightVO";
	private static final String KEY_CONTAINERDETAILSVOS = "containerDetailsVOs";
	private static final String KEY_CONTAINERDETAILSVO = "containerDetailsVO";
	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_ONETIMEMAILCLASS = "oneTimeMailClass";
	private static final String KEY_ONETIMERSN = "oneTimeRSN";
	private static final String KEY_ONETIMEHNI = "oneTimeHNI";
	private static final String KEY_ONETIMEDAMAGECODE = "oneTimeDamageCode";
	private static final String KEY_POLS = "pols";
	private static final String DAMAGEMAILBAGVOS = "DamageMailBagVOs";
	private static final String KEY_ONETIMECONTAINERTYPE = "oneTimeContainerType";
	private static final String KEY_MSGSTAT = "messagestat";
	private static final String KEY_MAILDISCREPANCYVOS = "maildiscrepancyvos";
	private static final String KEY_ONETIMEMAILSTATUS = "oneTimeMailStatus";
	private static final String KEY_MAILARRIVALFILTERVO="mailArrivalFilterVo";
	private static final String KEY_FROMSCREEN="fromScreen";
	private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
	private static final String KEY_MAIL_DENSITY = "maildensity";
	private static final String ONETIME_MAILCATEGORY = "mailcategory";
		
    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	
	/**
	 * The setter method for MailArrivalVO
	 * @param mailArrivalVO
	 */
    public void setMailArrivalVO(MailArrivalVO mailArrivalVO) {
    	setAttribute(KEY_MAILARRIVALVO,mailArrivalVO);
    }
    /**
     * The getter method for mailArrivalVO
     * @return mailArrivalVO
     */
    public MailArrivalVO getMailArrivalVO() {
    	return getAttribute(KEY_MAILARRIVALVO);
    }
        
	/**
     * This method is used to get the flightValidationVO from the session
     * @return flightValidationVO
     */
	public FlightValidationVO getFlightValidationVO(){
	    return getAttribute(KEY_FLIGHTVALIDATIONVO);
	}
	
	/**
	 * This method is used to set the flightValidationVO in session
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO  flightValidationVO) {
	    setAttribute(KEY_FLIGHTVALIDATIONVO, flightValidationVO);
	}
	
	/**
     * This method is used to get the operationalFlightVO from the session
     * @return operationalFlightVO
     */
	public OperationalFlightVO getOperationalFlightVO(){
	    return getAttribute(KEY_OPERATIONALFLIGHTVO);
	}
	
	/**
	 * This method is used to set the operationalFlightVO in session
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO  operationalFlightVO) {
	    setAttribute(KEY_OPERATIONALFLIGHTVO, operationalFlightVO);
	}
	
	/**
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
    public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO) {
    	setAttribute(KEY_CONTAINERDETAILSVO,containerDetailsVO);
    }
    /**
     * The getter method for containerDetailsVO
     * @return containerDetailsVO
     */
    public ContainerDetailsVO getContainerDetailsVO() {
    	return getAttribute(KEY_CONTAINERDETAILSVO);
    }
    
    /**
     * This method is used to set ContainerDetailsVOs values to the session
     * @param containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs) {
		setAttribute(KEY_CONTAINERDETAILSVOS,(ArrayList<ContainerDetailsVO>)containerDetailsVOs);
	}

	/**
     * This method returns the ContainerDetailsVOs
     * @return KEY_CONTAINERDETAILSVOS - Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs() {
		return (Collection<ContainerDetailsVO>)getAttribute(KEY_CONTAINERDETAILSVOS);
	}
    
	/**
     * This method is used to set pols to the session
     * @param pols - Collection<String>
     */
	public void setPols(Collection<String> pols) {
		setAttribute(KEY_POLS,(ArrayList<String>)pols);
	}

	/**
     * This method returns the pols
     * @return KEY_POLS - Collection<String>
     */
	public Collection<String> getPols() {
		return (Collection<String>)getAttribute(KEY_POLS);
	}
	
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeContainerType - Collection<OneTimeVO>
     */
	public void setOneTimeContainerType(Collection<OneTimeVO> oneTimeContainerType) {
		setAttribute(KEY_ONETIMECONTAINERTYPE,(ArrayList<OneTimeVO>)oneTimeContainerType);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECONTAINERTYPE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeContainerType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECONTAINERTYPE);
	}
	
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat) {
		setAttribute(KEY_ONETIMECAT,(ArrayList<OneTimeVO>)oneTimeCat);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECAT - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECAT);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeMailClass - Collection<OneTimeVO>
     */
	public void setOneTimeMailClass(Collection<OneTimeVO> oneTimeMailClass) {
		setAttribute(KEY_ONETIMEMAILCLASS,(ArrayList<OneTimeVO>)oneTimeMailClass);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEMAILCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeMailClass() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEMAILCLASS);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeRSN - Collection<OneTimeVO>
     */
	public void setOneTimeRSN(Collection<OneTimeVO> oneTimeRSN) {
		setAttribute(KEY_ONETIMERSN,(ArrayList<OneTimeVO>)oneTimeRSN);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMERSN - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRSN() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMERSN);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI) {
		setAttribute(KEY_ONETIMEHNI,(ArrayList<OneTimeVO>)oneTimeHNI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEHNI);
	}
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeDamageCodes() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEDAMAGECODE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeDamageCodes(Collection<OneTimeVO> oneTimeDamageCode) {
		setAttribute(KEY_ONETIMEDAMAGECODE,(ArrayList<OneTimeVO>)oneTimeDamageCode);
	}
	
	/**
     * This method returns the DamagedMailbagVOS
     * @return DAMAGEMAILBAGVOS - Collection<DamagedMailbagVO>
     */
	public Collection<DamagedMailbagVO> getDamagedMailbagVOs() {
		return (Collection<DamagedMailbagVO>)getAttribute(DAMAGEMAILBAGVOS);
	}
	
	/**
     * This method is used to set DamagedMailbagVOs to the session
     * @param damagedMailbagVOs - Collection<DamagedMailbagVO>
     */
	public void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs) {
		setAttribute(DAMAGEMAILBAGVOS,(ArrayList<DamagedMailbagVO>)damagedMailbagVOs);
	}
	/**
     * This method returns the message status
     * @return String
     */
	public String getMessageStatus() {
		return (String)getAttribute(KEY_MSGSTAT);
	}
	
	/**
     * This method is used to set the message status
     * @param String
     */
	public void setMessageStatus(String status) {
		setAttribute(KEY_MSGSTAT,(String)status);
	}
	/**
     * This method returns the MailDiscrepancyVOS
     * @return mailDiscrepancyVOs - ArrayList<MailDiscrepancyVO>
     */
	public ArrayList<MailDiscrepancyVO> getMailDiscrepancyVOs() {
		return (ArrayList<MailDiscrepancyVO>)getAttribute(KEY_MAILDISCREPANCYVOS);
	}
	
	/**
     * This method is used to set MailDiscrepancyVOs to the session
     * @param mailDiscrepancyVOs - ArrayList<MailDiscrepancyVO>
     */
	public void setMailDiscrepancyVOs(ArrayList<MailDiscrepancyVO> mailDiscrepancyVOs) {
		setAttribute(KEY_MAILDISCREPANCYVOS,(ArrayList<MailDiscrepancyVO>)mailDiscrepancyVOs);
	}
	
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeMailStatus - Collection<OneTimeVO>
     */
	public void setOneTimeMailStatus(Collection<OneTimeVO> oneTimeMailStatus) {
		setAttribute(KEY_ONETIMEMAILSTATUS,(ArrayList<OneTimeVO>)oneTimeMailStatus);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEMAILSTATUS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeMailStatus() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEMAILSTATUS);
	}

	public MailArrivalFilterVO getMailArrivalFilterVO() {
		// TODO Auto-generated method stub
		return (MailArrivalFilterVO)getAttribute(KEY_MAILARRIVALFILTERVO);
	}

	public void setMailArrivalFilterVO(MailArrivalFilterVO mailArrivalFilterVO) {
		// TODO Auto-generated method stub
		setAttribute(KEY_MAILARRIVALFILTERVO,mailArrivalFilterVO);
	}

	public String getFromScreen() {
		// TODO Auto-generated method stub
		return (String)getAttribute(KEY_FROMSCREEN) ;
	}

	public void setFromScreen(String fromScreen) {
		// TODO Auto-generated method stub
		setAttribute(KEY_FROMSCREEN,fromScreen);
	}
	
	 /**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
    }

    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getWeightRoundingVO() {
    	return getAttribute(KEY_WEIGHTROUNDINGVO);
    }

    /**
     * @param key String
     */
    public void removeWeightRoundingVO(String key) {
    }    

	/**
     * This method is used to set onetime values to the session
     * @param mailDensity - String
     */
   public void setMailDensity(String mailDensity) {
   	setAttribute(KEY_MAIL_DENSITY, mailDensity);
   }

	/**
    * This method returns the double value
    * @return mailDensity - String
    */
   public String getMailDensity() {
   	return getAttribute(KEY_MAIL_DENSITY);
   }

   /**
    * @param key String
    */
   public void removeMailDensity(String key) {
	   removeAttribute(KEY_MAIL_DENSITY);
    }    
   //Added by a-4810 for icrd-18030
   /**
	 * This method is used to set onetime values to the session
	 * @param mailCategory - Collection<OneTimeVO>
	 */
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(ONETIME_MAILCATEGORY,(ArrayList<OneTimeVO>)mailCategory);
	}

	/**
	 * This method returns the onetime vos
	 * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getMailCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCATEGORY);
	}
	
}
