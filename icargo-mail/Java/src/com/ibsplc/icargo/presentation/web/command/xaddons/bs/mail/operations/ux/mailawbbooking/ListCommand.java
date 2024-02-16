package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.mailawbbooking;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.MailAwbBookingPopupModel;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.AwbFilter;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.MailBookingDetail;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.mailbooking.ListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	30-Sep-2019		:	Draft
 */
public class ListCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("ListCommand");

	private static final String MODULE_NAME = "bsmail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailawbbooking";
	private static final String NO_REULTS_FOUND = "xaddons.bs.mail.operations.msg.status.noresultsfound";
	private static final String SHIPMENT_STATUS = "operations.shipment.shipmentstatus";
	private static final String MAIL_SCC_CODES_SYSPAR = "operations.shipment.mailsccs";
	private static final String INVALID_MAIl_SCC = "xaddons.bs.mail.operations.msg.status.notmailscc";
	
	public void execute(ActionContext actionContext){
		
		log.entering("ListCommand", "execute");
		Collection<ErrorVO> errors = null;
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		MailAwbBookingPopupModel mailAwbBookingPopupModel=
				(MailAwbBookingPopupModel) actionContext.getScreenModel();  
		AwbFilter awbFilter= mailAwbBookingPopupModel.getAwbFilter();
		ResponseVO responseVO = new ResponseVO(); 
		Collection<MailBookingDetail> mailBookingDetails = null; 

		MailBookingFilterVO mailBookingFilterVO = null;

		LocalDate date = null;

    	if(awbFilter.getBookingFrom()==null 
    			|| awbFilter.getBookingFrom().trim().length()==0){	
    		awbFilter.setBookingFrom
			((new LocalDate(
					logonAttributes.getAirportCode(),Location.ARP,true).
							addDays(-30)).toDisplayDateOnlyFormat());  
    	}
    	
    	if(awbFilter.getBookingTo()==null
    			|| awbFilter.getBookingTo().trim().length()==0){
    		awbFilter.setBookingTo(
			new LocalDate(
					logonAttributes.getAirportCode(),Location.ARP,true).toDisplayDateOnlyFormat());
    	}

		mailBookingFilterVO = 
				MailOperationsModelConverter.constructMailBookingFilterVO(awbFilter, logonAttributes);
		
		mailBookingFilterVO.setPageNumber(mailAwbBookingPopupModel.getDisplayPage()!=null?
				Integer.parseInt(mailAwbBookingPopupModel.getDisplayPage()):1);
		mailBookingFilterVO.setPageSize(mailAwbBookingPopupModel.getPageSize()!=null?
				Integer.parseInt(mailAwbBookingPopupModel.getPageSize()):10);
        
		String displayPg = mailAwbBookingPopupModel.getDisplayPage();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(MAIL_SCC_CODES_SYSPAR);
		String mailSccCode = findSystemParameterByCodes(systemParameterCodes).get(MAIL_SCC_CODES_SYSPAR);
		mailBookingFilterVO.setMailSccFromSyspar(mailSccCode);
		if (mailBookingFilterVO.getMailScc() != null && mailBookingFilterVO.getMailScc().trim().length() > 0) {
			boolean sccValid = true;
			int count = 0;
			int sccCount = 0;
			String[] mailSccs = null;
			String[] Sccs = null;
			if (mailSccCode != null)
				mailSccs = mailSccCode.split(",");
			if (mailBookingFilterVO.getMailScc() != null && mailBookingFilterVO.getMailScc().contains(",")) {
				Sccs = mailBookingFilterVO.getMailScc().split(",");
				sccCount = Sccs.length;

				for (int i = 0; i < Sccs.length; i++) {
					for (int j = 0; j < mailSccs.length; j++) {
						if (Sccs[i].equals(mailSccs[j])) {
							sccValid = true;
							count++;
						}
					}
				}
				if (count < sccCount) {
					actionContext.addError(new ErrorVO(INVALID_MAIl_SCC));
					return;
				}
			} else {
				sccCount = 1;
				for (int j = 0; j < mailSccs.length; j++) {
					if (mailBookingFilterVO.getMailScc().equals(mailSccs[j])) {
						sccValid = true;
						break;
					} else {
						sccValid = false;
					}
				}
				if (!sccValid) {
					actionContext.addError(new ErrorVO(INVALID_MAIl_SCC));
					return;
				}
			}

		}
		
		Page<MailBookingDetailVO> mailbookingVOs = null;
		BaseMailOperationsDelegate delegate = new BaseMailOperationsDelegate();
		try {
			mailbookingVOs = delegate.findMailBookingAWBs(mailBookingFilterVO, Integer.parseInt(displayPg));
		} catch (BusinessDelegateException businessDelegateException) {
			errors = new ArrayList<ErrorVO>();
			errors = handleDelegateException(businessDelegateException);
		}
		if (mailbookingVOs == null || mailbookingVOs.size() == 0) {
			actionContext.addError(new ErrorVO(NO_REULTS_FOUND));
			return;
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((java.util.List<ErrorVO>) errors);
		}
		Collection<MailBookingDetailVO> coll = new ArrayList<MailBookingDetailVO>();

		if (mailbookingVOs != null) {
			int size1 = mailbookingVOs.size();

			for (int i = 0; i < size1; i++) {
				coll.add(mailbookingVOs.get(i));

			}
		}
		
		if (mailbookingVOs != null) {
			Collection<String> oneTimeList = new ArrayList<String>();
			Map<String, Collection<OneTimeVO>> oneTimeVOsMap = new HashMap<String, Collection<OneTimeVO>>();
			try {
				oneTimeList.add(SHIPMENT_STATUS);
				SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
				oneTimeVOsMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(), oneTimeList);
				if (mailAwbBookingPopupModel.getOneTimeValues()!=null&&mailAwbBookingPopupModel.getOneTimeValues().isEmpty()) {
					mailAwbBookingPopupModel.setOneTimeValues((MailOperationsModelConverter.constructOneTimeValues(oneTimeVOsMap)));
				} else if(mailAwbBookingPopupModel.getOneTimeValues()!=null) {
					mailAwbBookingPopupModel.getOneTimeValues().put(SHIPMENT_STATUS, MailOperationsModelConverter.constructOneTimeValues(oneTimeVOsMap).get(SHIPMENT_STATUS));
				}
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);  
			}
		}


		if (coll != null && coll.size() > 0) {
			List<MailAwbBookingPopupModel> results = new ArrayList<MailAwbBookingPopupModel>();
			mailBookingDetails=MailOperationsModelConverter.convertMailBookingDetailVosToModel(mailbookingVOs);
			PageResult<MailBookingDetail> mailBookingDetailsCollectionPage = 
					new PageResult<MailBookingDetail>(mailbookingVOs,(ArrayList<MailBookingDetail>) mailBookingDetails);
			mailAwbBookingPopupModel.setAwbFilter(awbFilter);
			mailAwbBookingPopupModel.setMailBookingDetailsCollectionPage(mailBookingDetailsCollectionPage);
			results.add(mailAwbBookingPopupModel);
			responseVO.setResults(results);  
		    responseVO.setStatus("success");
		    actionContext.setResponseVO(responseVO); 
		    return;

		}

    	
    	
	}
	
	
	private Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		log.entering("UploadMailDetailsCommand","findSystemParameterByCodes");
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
		log.exiting("UploadMailDetailsCommand","findSystemParameterByCodes");
		return results;
	}

}
