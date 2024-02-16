/*
 * FlownDetailsCommand.java created on Sep 30, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3229
 *
 */
public class FlownDetailsCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String CLASS_NAME = "FlownDetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";

	private static final String LISTFLOWN_SUCCESS = "listflown_success";
	
	private static final String NO_RESULTS = "mailtracking.mra.defaults.despatchenquiry.noflownresultsfound";
	
	private static final String LISTFLOWN_FAILURE = "listflown_failure";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	
	private static final String CLOSED = "CLOSED";

	private static final String OPEN = "OPEN";

	private static final String ACCOUNTED = "Accounted";

	private static final String PARTIALLY_ACCOUNTED = "Partially Accounted";

	private static final String NEW = "New";

	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
		DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		//Local variales
		boolean statusFlag=true;
		
		DSNPopUpVO popUpVO = null;
		popUpVO = popUpSession.getSelectedDespatchDetails();
		if(popUpVO == null) {
			popUpVO = despatchEnqSession.getDispatchFilterVO();
		}
		if(popUpVO != null) {
			despatchEnquiryForm.setDespatchNum(popUpVO.getBlgBasis());
			despatchEnquiryForm.setDsnFilterDate(popUpVO.getDsnDate());
		}
		
		if(despatchEnquiryForm.getListed()!=null  && 
				despatchEnquiryForm.getListed().trim().length()>0 &&
				("N".equals(despatchEnquiryForm.getListed())) && 
				!("".equals(despatchEnquiryForm.getListed()))){
			despatchEnquiryForm.setListed("N");
			invocationContext.target=LISTFLOWN_FAILURE;
			return;
		}else{
			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			ArrayList<SectorRevenueDetailsVO> sectorRevenueDetailsVOs=null;

			//server call to get flown details
			try {
				sectorRevenueDetailsVOs=new ArrayList<SectorRevenueDetailsVO>(delegate.findFlownDetails(popUpVO));
				log.log(Log.INFO, "sectorRevenueDetailsVOs---------",
						sectorRevenueDetailsVOs);

			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}

			//To set flightsector status and flight statuss

			if(sectorRevenueDetailsVOs!=null && sectorRevenueDetailsVOs.size()>0){
				for (SectorRevenueDetailsVO detailsVO : sectorRevenueDetailsVOs) {
					if (!("A".equals(detailsVO.getAccStatus()))) {
						statusFlag = false;
						break;
					}
				}				
				for (SectorRevenueDetailsVO detailsVO : sectorRevenueDetailsVOs) {
//Commented as part of bug ICRD-335415 by A-5526 starts
					
					/*if (statusFlag) {
						// case of A as accstatus
						log.log(Log.INFO, "inside flag true===>");
						detailsVO.setFlightStatus(CLOSED);
						detailsVO.setFlightSegmentStatus(ACCOUNTED);
					} else {

						//case where not As
						log.log(Log.INFO, "inside falg false===>");
						detailsVO.setFlightStatus(OPEN);
						detailsVO.setFlightSegmentStatus(PARTIALLY_ACCOUNTED);
					}*/
					//Setting flight status as open and flightsegment status as New as we are not
					//sure about the correct business of these fields.Will be corrected in future.
					detailsVO.setFlightStatus(OPEN);
					detailsVO.setFlightSegmentStatus(NEW);
				}
				despatchEnqSession.setFlownDetails(sectorRevenueDetailsVOs);
				despatchEnquiryForm.setListed("Y");
				//Added as part of ICRD-161029 starts
				Collection<String> codes = new ArrayList<String>();
				codes.add(BASE_CURRENCY);
				Map<String, String> results = new HashMap<String, String>();
				try {
					results = new SharedDefaultsDelegate()
							.findSystemParameterByCodes(codes);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				String baseCurrency = results.get(BASE_CURRENCY);
				despatchEnquiryForm.setCurrency(baseCurrency);
				//Added as part of ICRD-161029 ends
				invocationContext.target=LISTFLOWN_SUCCESS;
			}
			else{
				if(errors!=null){
					ErrorVO err=new ErrorVO(NO_RESULTS);
					despatchEnqSession.removeFlownDetails();
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
					invocationContext.addAllError(errors);
					invocationContext.target=LISTFLOWN_FAILURE;
				}
			}		
		}
		log.log(Log.INFO, "despatchEnqSession.getFlownDetails---------",
				despatchEnqSession.getFlownDetails());
		log.exiting(CLASS_NAME,"execute");
	}
	
}

