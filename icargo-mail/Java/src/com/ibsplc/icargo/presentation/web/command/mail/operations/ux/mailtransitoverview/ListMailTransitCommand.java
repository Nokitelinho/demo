package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailtransitoverview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.MailTransitFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailTransitVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailTransitModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailTransit;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailTransitFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ListMailTransitCommand extends AbstractCommand{

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {

		
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		MailTransitModel mailTransitModel = (MailTransitModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();
		
		ArrayList<MailTransitModel> results = new ArrayList<>();	
		Page<MailTransitVO> mailTransitVOPage = null;
		MailTransitFilterVO mailTransitFilterVO = new MailTransitFilterVO();
		MailTransitFilter mailTransitFilter = mailTransitModel.getMailTransitFilter();
		
		if ((mailTransitFilter.getFromDate() != null) && (mailTransitFilter.getFromDate().trim().length() > 0))
        {

            LocalDate date = new LocalDate(mailTransitFilter.getAirportCode(),Location.ARP,false);
               if(mailTransitFilter.getFromTime()!=null && mailTransitFilter.getFromTime().trim().length() > 0){
                   String fromDT=null;
                   fromDT = new StringBuilder(mailTransitFilter.getFromDate()).append(" ") 
                            .append(mailTransitFilter.getFromTime()).append(":00").toString();
                   mailTransitFilterVO.setFlightFromDate((date.setDateAndTime(fromDT)));
               }else{
            	   mailTransitFilterVO.setFlightFromDate((date.setDate(mailTransitFilter.getFromDate())));
               }
        }
		if ((mailTransitFilter.getToDate() != null) && (mailTransitFilter.getToDate().trim().length() > 0))
        {

            LocalDate date = new LocalDate(mailTransitFilter.getAirportCode(),Location.ARP,false);
               if(mailTransitFilter.getToTime()!=null && mailTransitFilter.getToTime().trim().length() > 0){
                   String toDT=null;
                   toDT = new StringBuilder(mailTransitFilter.getToDate()).append(" ") 
                            .append(mailTransitFilter.getToTime()).append(":00").toString();
                   mailTransitFilterVO.setFlightToDate((date.setDateAndTime(toDT)));
               }else{
            	   mailTransitFilterVO.setFlightToDate((date.setDate(mailTransitFilter.getToDate())));
               }
        }
		if ((mailTransitFilter.getFlightDate() != null) && (mailTransitFilter.getFlightDate().trim().length() > 0))
        {
            LocalDate date = new LocalDate(mailTransitFilter.getAirportCode(),Location.ARP,false);
            	   mailTransitFilterVO.setFlightDate((date.setDate(mailTransitFilter.getFlightDate())));
        }
		

		
		mailTransitFilterVO.setAirportCode(mailTransitFilter.getAirportCode());
		mailTransitFilterVO.setFlightNumber(mailTransitFilter.getFlightNumber());
		mailTransitFilterVO.setPageNumber(Integer.parseInt(mailTransitFilter.getPageNumber()));
		
		
		if((mailTransitFilter.getPageSize()!=null) && ((mailTransitFilter.getPageSize().trim().length()) > 0)){
			mailTransitFilterVO.setPageSize(Integer.parseInt(mailTransitFilter.getPageSize()));
		}
		
		
		mailTransitVOPage = mailTrackingDefaultsDelegate.findMailTransit(mailTransitFilterVO, mailTransitFilterVO.getPageNumber());
		
		if ((mailTransitVOPage == null || mailTransitVOPage.getActualPageSize() == 0)) {
            actionContext.addError(new ErrorVO("mailtracking.defaults.mailtransitoverview.msg.err.norecordsfound"));
       } 
		else {
		
		List<MailTransit> mailTransitList = constructMailTransit(mailTransitVOPage);
		PageResult<MailTransit> pageList = new PageResult<>(mailTransitVOPage, mailTransitList);
		mailTransitModel.setMailTransits(pageList);
		results.add(mailTransitModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
       }
		
	}
	
	
	public static List<MailTransit> constructMailTransit(Collection<MailTransitVO> mailTransitVOs) {

		List<MailTransit> mailTransits = new ArrayList<>();

		MailTransit mailTransit= null;

		if (mailTransitVOs != null && !mailTransitVOs.isEmpty()) {
			for (MailTransitVO mailTransitVO : mailTransitVOs) {

				mailTransit = constructMailTransit(mailTransitVO);
				mailTransits.add(mailTransit);
			}

		}

		return mailTransits;

	}
	
	public static MailTransit constructMailTransit(MailTransitVO mailTransitVO) {

		MailTransit mailTransit = new MailTransit();
		
		mailTransit.setCarrierCode(mailTransitVO.getCarrierCode());
		mailTransit.setCountOfExportNonAssigned(mailTransitVO.getCountOfExportNonAssigned());
		mailTransit.setMailBagDestination(mailTransitVO.getMailBagDestination());
		mailTransit.setTotalNoImportBags(mailTransitVO.getTotalNoImportBags());
		mailTransit.setTotalWeightImportBags(mailTransitVO.getTotalWeightImportBags());
		mailTransit.setTotalWeightOfExportNotAssigned(mailTransitVO.getTotalWeightOfExportNotAssigned());
		
		return mailTransit;
	}

	
}