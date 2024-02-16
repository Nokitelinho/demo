/*
 * MaintainCCASession.java Created on July 14-2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

/**
 * @author A-3447
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;

/**
 * 
 * @author A-3447
 * 
 */
public interface MaintainCCASession extends ScreenSession {
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 * removes removeOneTimeVOs
	 */
	public void removeOneTimeVOs();
	/**
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getSystemparametres();
	/**
	 * 
	 * @param sysparameters sysparameters
	 */
	public void setSystemparametres(HashMap<String, String> sysparameters);

	/**
	 * @return MaintainCCAFilterVO
	 */
	public MaintainCCAFilterVO getMaintainCCAFilterVO();

	/**
	 * @param maintainCCAFilterVO
	 */
	public void setCCAFilterVO(MaintainCCAFilterVO maintainCCAFilterVO);

	/**
	 * removes MaintainCCAFilterVO
	 */
	public void removeMaintainCCAFilterVO();

	/**
	 * 
	 * @return CCAdetailsVO
	 */
	public CCAdetailsVO getCCAdetailsVO();

	/**
	 * 
	 * @param exceptionInInvoiceVOs
	 */
	public void setCCAdetailsVO(CCAdetailsVO cCAdetailsVO);

	/**
	 * remove CCAdetailsVO
	 */
	public void removeCCAdetailsVO();

	/**
	 * 
	 * @return Collection<CCAdetailsVO>
	 */
	public Collection<CCAdetailsVO> getCCAdetailsVOs();

	/**
	 * 
	 * @param cCAdetailsVOs
	 */
	public void setCCAdetailsVOs(Collection<CCAdetailsVO> cCAdetailsVOs);

	/**
	 * removes cCAdetailsVOs
	 * 
	 */
	public void removeCCAdetailsVOs();
	
	/**
	 * getCCARefNumbers
	 * @return Collection<String>
	 */
	public Collection<String> getCCARefNumbers();
	
	/**
	 * setCCARefNumbers
	 * @param cCARefNumbers
	 */
	public void setCCARefNumbers(Collection<String> cCARefNumbers);
	
	/**
	 * removes CCARefNumbers
	 */
	public void removeCCARefNumbers();
	
	
	/**
	 * getUsrCCANumFlg
	 * @return String
	 */
	public String getUsrCCANumFlg();
	
	/**
	 * setCCARefNumbers
	 * @param usrCCANumFlg
	 */
	public void setUsrCCANumFlg(String usrCCANumFlg);
	
	/**
	 * removes UsrCCANumFlg
	 */
	public void removeUsrCCANumFlg();
	
	
	/**
	 * getRateAuditVO
	 * @return RateAuditVO
	 */
	public RateAuditVO getRateAuditVO();
	
	/**
	 * setRateAuditVO
	 * @param rateAuditVO
	 */
	public void setRateAuditVO(RateAuditVO rateAuditVO);
	
	/**
	 * removes RateAuditVO
	 */
	public void  removeRateAuditVO();	
	
	
	/**
	 * getAutoRateFlg
	 * @return String
	 */
	public String getAutoRateFlg();
	
	/**
	 * setCCARefNumbers
	 * @param usrCCANumFlg
	 */
	public void setAutoRateFlg(String autoRateFlg);
	
	/**
	 * removes removeAutoRateFlg
	 */
	public void removeAutoRateFlg();
	
	
	/**
	 *
	 * @param statusinfo
	 */
	public void setStatusinfo(String statusinfo);

	/**
	 *
	 * @return
	 */
	public String getStatusinfo();
	/**
	 *
	 */
	public void removeStatusinfo(); 
	
	/**
	 *
	 * @param refNoToDisp
	 */
	public void setRefNoToDisp(String refNoToDisp);

	/**
	 *
	 * @return
	 */
	public String getRefNoToDisp();
	/**
	 *
	 */
	public void removeRefNoToDisp(); 
	
	 public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO);
	    
	    public UnitRoundingVO getVolumeRoundingVO();
	    
	    public void removeVolumeRoundingVO();
	    
	    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
	    
	    public UnitRoundingVO getWeightRoundingVO();
	    
	    public void removeWeightRoundingVO();
	    /**
		 *
		 * @param statusinfo
		 */
		public void setPopupFlag(String statusinfo);

		/**
		 *
		 * @return
		 */
		public String getPopupFlag();
	
	public void setSurchargeCCAdetailsVOs(
			ArrayList<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs);

	public ArrayList<SurchargeCCAdetailsVO> getSurchargeCCAdetailsVOs();

	public void removeSurchargeCCAdetailsVOs();
	
	//Added by A-7929 as part of ICRD-132548 starts.......
	
	/**
	 * 
	 * @return Collection<DocumentBillingDetailsVO>
	 */
	public Collection<DocumentBillingDetailsVO> getDocumentBillingDetailsVOs();

	/**
	 * 
	 * @param documentBillingDetailsVOs
	 */
	public void setDocumentBillingDetailsVOs(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs);
	
	/**
	 * @author a-7540
	 */
     public GPABillingEntriesFilterVO getGPABillingEntriesFilterVO();
	
	public void setGPABillingEntriesFilterVO(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO);
	
	/**
	 * getFromScreen
	 * @return String
	 */
	public String getFromScreen();
	
	/**
	 * setFromScreen 
	 * @param fromScreen
	 */
	public void setFromScreen(String fromScreen);
	
	/**
	 * getBillingStatus
	 * @return String
	 *//*
	public String getBillingStatus();
	
	*//**
	 * setBillingStatus 
	 * @param billingStatus
	 *//*
	public void setBillingStatus(String billingStatus);*/
	
	//Added by A-7929 as part of ICRD-132548 ends.......
	
	

	public Collection<CRAParameterVO> getCRAParameterVOs();

	

	/**

	 * 

	 * @param Collection<CRAParameterVO>

	 */

	public void setCRAParameterVOs(Collection<CRAParameterVO> cRAParameterVOs) ;

}
