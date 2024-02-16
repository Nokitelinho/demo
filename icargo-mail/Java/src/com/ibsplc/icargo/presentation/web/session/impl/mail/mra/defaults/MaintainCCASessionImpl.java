/*
 * MaintainCCASessionImpl.java Created on July 14-2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

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
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;

/**
 * @author A-3447
 * 
 * 
 */
public class MaintainCCASessionImpl extends AbstractScreenSession implements
		MaintainCCASession {

	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/*
	 * The screen id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaincca";

	private static final String KEY_ONETIME_VOS = "onetimevalues";

	/**
	 * Constant for the session variable ccaFilterVO
	 */
	private static final String KEY_LISTFILTER = "maintainCCAFilterVO";

	/**
	 * CCADetails Vo
	 */

	private static final String KEY_DETAILS = "cCADetailsVO";

	/**
	 * CCDetails Vos
	 * 
	 */
	private static final String KEY_CCADETAILS = "cCADetailsVOs";
	
	private static final String KEY_CCA_REF_NUMBERS = "ccaRefNumbers";

	private static final String KEY_USRCCANUMFLG = "usrccanumflg";
	private static final String KEY_RATEAUDITVO = "rateauditvo";
	private static final String KEY_AUTORATEFLG = "autorateflg";
	 private static final  String KEY_STATUS_INFO = "statusinfo";
	 private static final  String KEY_REFNO_TO_DISP = "refNoToDisp";
	 
	 private static final  String KEY_VOLUMEROUNDINGVO = "volumeRounding";
	 
	 private static final  String KEY_WEIGHTROUNDINGVO = "weightRounding";
	 
	 private static final  String KEY_POPUPFLAG= "popupflag";
	 private static final  String KEY_SURCHARGECCADETAILS= "surchargeccadetails";
	 
	 private static final String KEY_SYSPARAMETERS="systemParameterByCodes";
	 
	 private static final String KEY_FROMSCREEN = "fromScreen";  //Added by A-7929 as part of ICRD-132548
	// private static final String KEY_BILLINGSTATUS = "billingStatus"; //Added by A-7929 as part of ICRD-132548
	 private static final String KEY_DOCUMENTDETAILS = "documentBillingDetailsVOs"; //Added by A-7929 as part of ICRD-132548
	
	 private static final String KEY_GPABILLINGENTRIESFILTERVO = "gpabillingentriesfiltervo";//Added by A-7540
	 private static final String KEY_CRAPARAMETERVOS = "craParameterVO";	//Added for ICASB-858
	/**
	 * @return
	 */
	public String getScreenID() {

		return SCREEN_ID;
	}

	/**
	 * @return MODULE_NAME
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * constructor
	 * 
	 */
	public MaintainCCASessionImpl() {
		super();
	}

	/**
	 * @author A-3447 for getting one time into the session
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return getAttribute(KEY_ONETIME_VOS);

	}

	/**
	 * @param OneTimeVO
	 *            for setting one times
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {

		setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	}

	/**
	 * for removing one times
	 */
	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOS);

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see getMaintainCCAFilterVO()
	 */
	public MaintainCCAFilterVO getMaintainCCAFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}

	/**
	 * removing filer (non-Javadoc) *
	 * 
	 * @see removeMaintainCCAFilterVO()
	 */
	public void removeMaintainCCAFilterVO() {
		removeAttribute(KEY_LISTFILTER);
	}

	/**
	 * @param maintainCCAFilterVO
	 */
	public void setCCAFilterVO(MaintainCCAFilterVO maintainCCAFilterVO) {
		setAttribute(KEY_LISTFILTER, maintainCCAFilterVO);
	}

	/**
	 * getter for CCAdetails
	 */
	public CCAdetailsVO getCCAdetailsVO() {

		return getAttribute(KEY_DETAILS);

	}

	/**
	 * removing ccadetails Vo
	 * 
	 */
	public void removeCCAdetailsVO() {
		removeAttribute(KEY_DETAILS);

	}

	/**
	 * setting CCAdetails Vo
	 * 
	 */
	public void setCCAdetailsVO(CCAdetailsVO cCAdetailsVO) {
		setAttribute(KEY_DETAILS, cCAdetailsVO);

	}

	/**
	 * getting CCAdetailsVOs
	 */
	public Collection<CCAdetailsVO> getCCAdetailsVOs() {

		return (ArrayList<CCAdetailsVO>) getAttribute(KEY_CCADETAILS);
	}

	/**
	 * removes CCAdetailsVOs
	 */
	public void removeCCAdetailsVOs() {
		removeAttribute(KEY_CCADETAILS);

	}

	/**
	 * @author A-3447 setting CCAdetailsVOs
	 */
	public void setCCAdetailsVOs(Collection<CCAdetailsVO> cCAdetailsVOs) {
		setAttribute(KEY_CCADETAILS, (ArrayList<CCAdetailsVO>) cCAdetailsVOs);

	}
	
	/**
	 * get CCARefNumbers
	 */
	public Collection<String> getCCARefNumbers() {
		return (ArrayList<String>) getAttribute(KEY_CCA_REF_NUMBERS);
	}
	/**
	 * remove CCARefNumbers
	 */
	public void removeCCARefNumbers() {
		removeAttribute(KEY_CCA_REF_NUMBERS);
	}
	
	/**
	 * set CCARefNumbers
	 */
	public void setCCARefNumbers(Collection<String> cCARefNumbers) {
		setAttribute(KEY_CCA_REF_NUMBERS, (ArrayList<String>) cCARefNumbers);
	}
	
	/**
	 * getUsrCCANumFlg
	 */
	public String getUsrCCANumFlg() {
		return getAttribute(KEY_USRCCANUMFLG);
	}
	/**
	 * remove UsrCCANumFlg
	 */
	public void removeUsrCCANumFlg() {
		removeAttribute(KEY_USRCCANUMFLG);
	}
	
	/**
	 * set UsrCCANumFlg
	 */
	public void setUsrCCANumFlg(String usrCCANumFlg) {
		setAttribute(KEY_USRCCANUMFLG,usrCCANumFlg);
	}
	
	
	
	/**
	 * getRateAuditVO
	 */
	public RateAuditVO getRateAuditVO() {
		return getAttribute(KEY_RATEAUDITVO);
	}
	/**
	 * remove RateAuditVO
	 */
	public void removeRateAuditVO() {
		removeAttribute(KEY_RATEAUDITVO);
	}
	
	/**
	 * set RateAuditVO
	 */
	public void setRateAuditVO(RateAuditVO rateAuditVO) {
		setAttribute(KEY_RATEAUDITVO,rateAuditVO);
	}
	
	
	/**
	 * get AutoRateFlg
	 */
	public String getAutoRateFlg() {
		return getAttribute(KEY_AUTORATEFLG);
	}
	/**
	 * remove AutoRateFlg
	 */
	public void removeAutoRateFlg() {
		removeAttribute(KEY_AUTORATEFLG);
	}
	
	/**
	 * set AutoRateFlg
	 */
	public void setAutoRateFlg(String autoRateFlg) {
		setAttribute(KEY_AUTORATEFLG,autoRateFlg);
	}
	
	/**
	 *
	 * @param statusinfo
	 */
	public void setStatusinfo(String statusinfo) {
		setAttribute(KEY_STATUS_INFO,statusinfo);

	}

	/**
	 *
	 * @return
	 */
	public String getStatusinfo() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_STATUS_INFO);
	}

	/**
	 *
	 */
	public void removeStatusinfo() {
		removeAttribute(KEY_STATUS_INFO);

	}
	
	/**
	 *
	 * @param statusinfo
	 */
	public void setRefNoToDisp(String refNoToDisp) {
		setAttribute(KEY_REFNO_TO_DISP,refNoToDisp);

	}

	/**
	 *
	 * @return
	 */
	public String getRefNoToDisp() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_REFNO_TO_DISP);
	}

	/**
	 *
	 */
	public void removeRefNoToDisp() {
		removeAttribute(KEY_REFNO_TO_DISP);

	}
	
	 public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO) {
	    	setAttribute(KEY_VOLUMEROUNDINGVO, unitRoundingVO);
	    }

	    public UnitRoundingVO getVolumeRoundingVO() {
	    	return getAttribute(KEY_VOLUMEROUNDINGVO);
	    }

	    public void removeVolumeRoundingVO() {   
	    	removeAttribute(KEY_VOLUMEROUNDINGVO);
	    }  
	    
	    
	    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
	    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
	    }

	    public UnitRoundingVO getWeightRoundingVO() {
	    	return getAttribute(KEY_WEIGHTROUNDINGVO);
	    }

	    public void removeWeightRoundingVO() {
	    	removeAttribute(KEY_WEIGHTROUNDINGVO);
	    	
	    } 
	
	    /**
		 *
		 * @param statusinfo
		 */
		public void setPopupFlag(String popupflag) {
			setAttribute(KEY_POPUPFLAG,popupflag);

		}

		/**
		 *
		 * @return
		 */
		public String getPopupFlag() {
			// TODO Auto-generated method stub
			return getAttribute(KEY_POPUPFLAG);
		}
		/**
		 * 
		  * @author A-5255
		  * @param surchargeCCAdetailsVOs
		  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession#setSurchargeCCAdetailsVOs(java.util.ArrayList)
		 */
		public void setSurchargeCCAdetailsVOs(
				ArrayList<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs){
			setAttribute(KEY_SURCHARGECCADETAILS,surchargeCCAdetailsVOs);
		}
		/**
		 * 
		  * @author A-5255
		  * @return
		  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession#getSurchargeCCAdetailsVOs()
		 */
		public ArrayList<SurchargeCCAdetailsVO> getSurchargeCCAdetailsVOs(){
			return getAttribute(KEY_SURCHARGECCADETAILS);
		}
		/**
		 * 
		  * @author A-5255
		  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession#removeSurchargeCCAdetailsVOs()
		 */
		public void removeSurchargeCCAdetailsVOs(){
			removeAttribute(KEY_SURCHARGECCADETAILS);
		}
		/**
		 * 
		 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession#getSystemparametres()
		 *	Added by 			: A-6991 on 08-Jun-2017
		 * 	Used for 	        : ICRD-208114      
		 *	Parameters			:	@return
		 */
		public HashMap<String, String> getSystemparametres(){
			return getAttribute(KEY_SYSPARAMETERS);
		}
		public void setSystemparametres(HashMap<String, String> sysparameters){
			setAttribute(KEY_SYSPARAMETERS, sysparameters);
		}

		//Added by A-7929 as part of ICRD-132548 starts...
		
		/**
		 * getting DocumentBillingDetailsVOs
		 */
		public Collection<DocumentBillingDetailsVO> getDocumentBillingDetailsVOs() {

			return (ArrayList<DocumentBillingDetailsVO>) getAttribute(KEY_DOCUMENTDETAILS);
		}

		/**
		 *  setting DocumentBillingDetailsVOs
		 */
		public void setDocumentBillingDetailsVOs(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs) {
			setAttribute(KEY_DOCUMENTDETAILS, (ArrayList<DocumentBillingDetailsVO>) documentBillingDetailsVOs);

		}

		/**
		 * @author a-7540
		 */
		public GPABillingEntriesFilterVO getGPABillingEntriesFilterVO() {
			
			return getAttribute(KEY_GPABILLINGENTRIESFILTERVO);
		}
		
		public void setGPABillingEntriesFilterVO(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) {
			
			setAttribute(KEY_GPABILLINGENTRIESFILTERVO, gpaBillingEntriesFilterVO);
		}
		
		/**
		 * getFromScreen
		 */
		public String getFromScreen() {
			return getAttribute(KEY_FROMSCREEN);
		}
		/**
		 * set setFromScreen
		 */
		public void setFromScreen(String fromScreen) {
			setAttribute(KEY_FROMSCREEN,fromScreen);
		}

		/**
		 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession#getCRAParameterVOs()
		 *	Added by 			: A-7531 on 06-Feb-2019
		 * 	Used for 	:
		 *	Parameters	:	@return 
		 */
		@Override
		public Collection<CRAParameterVO> getCRAParameterVOs(){

			return (Collection<CRAParameterVO>) getAttribute(KEY_CRAPARAMETERVOS);

		}


		/**
		 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession#setCRAParameterVOs(java.util.Collection)
		 *	Added by 			: A-7531 on 06-Feb-2019
		 * 	Used for 	:
		 *	Parameters	:	@param cRAParameterVOs 
		 */
		@Override
		public void setCRAParameterVOs(Collection<CRAParameterVO> cRAParameterVO){

			setAttribute(KEY_CRAPARAMETERVOS,(ArrayList<CRAParameterVO>) cRAParameterVO);

		}

		
		

		
		/**
		 * getBillingStatus
		 *//*
		public String getBillingStatus() {
			return getAttribute(KEY_BILLINGSTATUS);
		}
		*//**
		 * set setBillingStatus
		 *//*
		public void setBillingStatus(String billingStatus) {
			setAttribute(KEY_BILLINGSTATUS,billingStatus);
		}*/
		
		//Added by A-7929 as part of ICRD-132548 ends...
}
