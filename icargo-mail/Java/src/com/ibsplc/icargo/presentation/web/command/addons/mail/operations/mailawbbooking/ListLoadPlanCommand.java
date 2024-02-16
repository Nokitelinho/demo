package com.ibsplc.icargo.presentation.web.command.addons.mail.operations.mailawbbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.addons.mail.operations.AddonsMailDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.MailAwbBookingModel;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.AwbFilter;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListLoadPlanCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("ListLoadPlanCommand");
	private static final String MAIL_SCC_CODES_SYSPAR = "operations.shipment.mailsccs";
	private static final String INVALID_MAIL_SCC = "Please search with a mail SCC";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ListLoadPlanCommand", "execute");
		MailAwbBookingModel mailAwbBookingModel = (MailAwbBookingModel) actionContext.getScreenModel();
		AwbFilter awbFilter = mailAwbBookingModel.getAwbFilter();
		
		ResponseVO responseVO = new ResponseVO();
		Collection<com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.MailBookingDetail> mailBookingDetails =null;
		MailBookingFilterVO mailBookingFilterVO = null;
		LogonAttributes logonAttributes = getLogonAttribute();
		
		if (awbFilter.getPlannedFlightDateFrom() == null || awbFilter.getPlannedFlightDateFrom().trim().length() == 0) {
			awbFilter.setPlannedFlightDateFrom((new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true).addDays(-30))
					.toDisplayDateOnlyFormat());
		}

		if (awbFilter.getPlannedFlightDateTo() == null || awbFilter.getPlannedFlightDateTo().trim().length() == 0) {
			awbFilter.setPlannedFlightDateTo(
					new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true).toDisplayDateOnlyFormat());
		}
		
		if(!awbFilter.isOnListButtonClick()){
			awbFilter.setPol(logonAttributes.getStationCode());
		}
		
		mailBookingFilterVO = MailOperationsModelConverter.constructLoadPlanFilterVO(awbFilter,logonAttributes);
		
		mailBookingFilterVO.setPageNumber(mailAwbBookingModel.getDisplayPage() != null
				? Integer.parseInt(mailAwbBookingModel.getDisplayPage()) : 1);
		mailBookingFilterVO.setPageSize(
				mailAwbBookingModel.getPageSize() != null ? Integer.parseInt(mailAwbBookingModel.getPageSize()) : 10);
		
		String displayPg = mailAwbBookingModel.getDisplayPage();
		
		ErrorVO errors = performValidationForScc(mailBookingFilterVO);
		if(errors!=null){
			actionContext.addError(errors);
		}
		
		Page<MailBookingDetailVO> loadPlanDetailVos = null;
		AddonsMailDelegate delegate = new AddonsMailDelegate();
		try {
			loadPlanDetailVos = delegate.findLoadPlanBookings(mailBookingFilterVO, Integer.parseInt(displayPg));
			} catch (BusinessDelegateException businessDelegateException) {
				actionContext.addAllError(handleDelegateException(businessDelegateException)); 
			}
			
			if (loadPlanDetailVos == null || loadPlanDetailVos.isEmpty()) {
				actionContext.addError(new ErrorVO("No Results Found"));
				return;
			}
			
			List<MailAwbBookingModel> results = new ArrayList<>();
			mailBookingDetails = MailOperationsModelConverter.convertLoadPlanDetailVOsToModel(loadPlanDetailVos);
			PageResult<com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.MailBookingDetail> loadPlanDetailsCollectionPage = 
					new PageResult<>(loadPlanDetailVos,(ArrayList<com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.MailBookingDetail>) mailBookingDetails);
			mailAwbBookingModel.setLoadPlanFilter(awbFilter);
			mailAwbBookingModel.setLoadPlanDetailsCollectionPage(loadPlanDetailsCollectionPage);
			results.add(mailAwbBookingModel);
			responseVO.setResults(results);  
		    responseVO.setStatus("success");
		    actionContext.setResponseVO(responseVO); 
		    return;
	} 
	
	private ErrorVO performValidationForScc(MailBookingFilterVO mailBookingFilterVO ) {
		Collection<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(MAIL_SCC_CODES_SYSPAR);
		String mailSccCode="";
		Map<String, String> result = null;
		try {
			result = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
		if(result!=null){
			mailSccCode = result.get(MAIL_SCC_CODES_SYSPAR);
		}
		mailBookingFilterVO.setMailSccFromSyspar(mailSccCode);
		
		if (mailBookingFilterVO.getMailScc() != null) {
			boolean sccValid = true;
			int count = 0;
			String[] mailSccs = null;
			if (mailSccCode != null)
				mailSccs = mailSccCode.split(",");
			if (mailBookingFilterVO.getMailScc() != null && mailBookingFilterVO.getMailScc().contains(",")) {
				ErrorVO errors = validateMultipleValuesOfScc(mailBookingFilterVO,mailSccs,count);
				if(errors!=null)
					return errors;
				
			} else {
				ErrorVO errors =validateSingleValueOfScc(mailBookingFilterVO,mailSccs,sccValid);
				if(errors!=null)
					return errors;
			}

		}
		return null;
		
	}

	private ErrorVO validateMultipleValuesOfScc(MailBookingFilterVO mailBookingFilterVO,
			 String[] mailSccs, int count) {
		String[] sccs = mailBookingFilterVO.getMailScc().split(",");
		int sccCount = sccs.length;

		if(mailSccs!=null){
		for (int i = 0; i < sccs.length; i++) {
			for (int j = 0; j < mailSccs.length; j++) {
				if (sccs[i].equals(mailSccs[j])) {
					count++;
				}
			}
		}
	}
		if (count < sccCount) {
			return(new ErrorVO(INVALID_MAIL_SCC));
		}
		return null;
		
	}

	private ErrorVO validateSingleValueOfScc(MailBookingFilterVO mailBookingFilterVO,
			String[] mailSccs, boolean sccValid) {
		if(mailSccs!=null){
			for (int j = 0; j < mailSccs.length; j++) {
				if (mailBookingFilterVO.getMailScc().equals(mailSccs[j])) {
					sccValid = true;
					break;
				} else {
					sccValid = false;
				}
			}
		}
			if (!sccValid) {
				return(new ErrorVO(INVALID_MAIL_SCC));
			}
			return null;
	}
}
