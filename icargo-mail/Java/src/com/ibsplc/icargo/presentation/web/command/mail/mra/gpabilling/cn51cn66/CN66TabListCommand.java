/*
 * CN66TabListCommand.java Created on Jan 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class CN66TabListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String LISTDETAILS_SUCCESS = "listdetails_success";

	private static final String LISTDETAILS_FAILURE = "listdetails_failure";

	private static final String CLASS_NAME = "CN66TabListCommand";

   private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.gpabilling.cn51cn66.Tabnoresultsfound";
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";

	private static final String BILLINGSTATUS_ONETIME = "mra.gpabilling.billingstatus";
	private static final String UNITCODDISP_ONTIME ="mail.mra.defaults.weightunit";
   private static final Log log = LogFactory.getLogger("MRA_GPABILLING");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo
	 * .framework.web.command.InvocationContext)
     */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ListCN51CN66Form form =(ListCN51CN66Form)invocationContext.screenModel;
		ListCN51CN66Session session = (ListCN51CN66Session)getScreenSession(
				MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		CN51CN66VO cN51CN66Vo 	= new CN51CN66VO();
		String category = form.getCategory();
		String origin   = form.getOrigin();
		String destination = form.getDestination();
		String dsnNum = form.getDsnNumber();
		log.log(Log.FINE, "category ---->>>:", category);
		log.log(Log.FINE, "origin ---->>>:", origin);
		log.log(Log.FINE, "destination ---->>>:", destination);
		
		CN51CN66FilterVO filterVO = session.getCN51CN66FilterVO();
		Page<CN66DetailsVO> cN66DetailsVOs = null;
		
		log.log(Log.FINE, "Filter VO ---->>>:", filterVO);
		
		if (category != null && category.trim().length() > 0) {
			filterVO.setCategory(category.toUpperCase());

		}else{
			filterVO.setCategory("");//added by A-8464 for ICRD-274269
		}
		
		if (origin != null && origin.trim().length() > 0) {
			filterVO.setOrgin(origin.toUpperCase());

		}else{
			filterVO.setOrgin("");//added by A-8464 for ICRD-274269
		}
		
		if (destination != null && destination.trim().length() > 0) {
			filterVO.setDestination(destination.toUpperCase());

		}else{
			filterVO.setDestination("");//added by A-8464 for ICRD-274269
		}
		//Added by A-6991 for ICRD-211662 Starts
		if (dsnNum != null && dsnNum.trim().length() > 0) {
			filterVO.setDsnNumber(dsnNum);
		}else{
			filterVO.setDsnNumber("");//added by A-8464 for ICRD-274269
		}
		//Added by A-6991 for ICRD-211662 Ends
		session.setCN51CN66FilterVO(filterVO);
		session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) fetchOneTimeDetails(filterVO.getCompanyCode()));
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		filterVO.setPageNumberCn66(Integer.parseInt(form.getDisplayPage()));
		filterVO.setTotalRecordCount(-1);
		
		try {
			log.log(Log.FINE, "Inside try : Calling findCN51CN66Details");
			cN51CN66Vo = new MailTrackingMRADelegate()
					.findCN51CN66Details(filterVO);
			log.log(Log.FINE, "CN51CN66Vo from Server:--> ", cN51CN66Vo);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught");
		}
		//form.setDisplayPageCN66("1");
		
		cN66DetailsVOs = cN51CN66Vo.getCn66DetailsVOs();
		Collection<OneTimeVO> wgtUntVOs = session.getOneTimeVOs().get(UNITCODDISP_ONTIME);
		if (wgtUntVOs != null && !wgtUntVOs.isEmpty() && cN66DetailsVOs != null && !cN66DetailsVOs.isEmpty()) { 
			for (CN66DetailsVO cn66VO : cN66DetailsVOs) {
				String unitcode = cn66VO.getUnitcode();
					for(OneTimeVO onetimeVO :wgtUntVOs){
						if (onetimeVO.getFieldValue().equalsIgnoreCase(unitcode)) {
							cn66VO.setUnitcode(onetimeVO.getFieldDescription());
						}
					}
			}
		}
		log.log(Log.FINE, "cN66DetailsVOs--- ", cN66DetailsVOs);
		if (cN66DetailsVOs != null && cN66DetailsVOs.size() > 0) {
			session.setCN66VOs(cN66DetailsVOs);
	    	invocationContext.target = LISTDETAILS_SUCCESS;
			
		} else {
    		errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
    		invocationContext.addAllError(errors);
    		form.setBtnStatus("TN");
    		// ADDED
    		session.setCN66VOs(null);
			invocationContext.target = LISTDETAILS_FAILURE;
		}
	    form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		log.exiting(CLASS_NAME, "execute");
		}

	
	
	
	
	/**
	 * Helper method to get the one time data.
	 * 
	 * @param companyCode
	 *            String
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(
			String companyCode) {

		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CATEGORY_ONETIME);
		oneTimeList.add(BILLINGSTATUS_ONETIME);
		oneTimeList.add(UNITCODDISP_ONTIME);
		try {
			hashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		log.exiting(CLASS_NAME, "fetchOneTimeDetails");
		return hashMap;
	}
	

}
