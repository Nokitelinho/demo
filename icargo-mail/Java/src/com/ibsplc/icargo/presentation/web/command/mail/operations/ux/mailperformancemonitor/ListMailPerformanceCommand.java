package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformancemonitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailPerformanceMonitorModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailMonitoringFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailMonitoringSummary;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListMailPerformanceCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("OPERATIONS CARDITENQUIRY NEO");
	private static final String MISSING_ORG_SCAN = "MISSING_ORIGIN_SCAN";
	private static final String MISSING_DST_SCAN = "MISSING_DESTINATION_SCAN";
	private static final String MISSING_BOTH_SCAN = "MISSING_BOTH_SCAN";
	private static final String ON_TIME_MAILBAGS = "ON_TIME_MAILBAGS";
	private static final String DELAYED_MAILBAGS = "DELAYED_MAILBAGS";
	private static final String RAISED_MAILBAGS = "RAISED_MAILBAGS";
	private static final String APPROVED_MAILBAGS = "APPROVED_MAILBAGS";
	private static final String DENIED_MAILBAGS = "REJECTED_MAILBAGS";
	 private static final String NO_DATA_FOUND = "No data found";
	  private static final String STATUS_SUCCESS = "success";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		this.log.entering("ListMailPerformanceCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		int displayPage=1;
		ResponseVO responseVO = new ResponseVO();
		
		MailPerformanceMonitorModel performanceModel = (MailPerformanceMonitorModel)actionContext.getScreenModel();
		MailMonitoringFilter mailMonitorFilter = performanceModel.getMailMonitorFilter();
	    MailMonitorFilterVO mailMonitorFilterVO = new MailMonitorFilterVO();
	    mailMonitorFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	    mailMonitorFilterVO.setType(mailMonitorFilter.getType());
	    
	    Collection<MailMonitorSummaryVO> mailMonitorSumary = new ArrayList<MailMonitorSummaryVO>();
	    List<MailPerformanceMonitorModel> results = new ArrayList();
	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    
	    if ((mailMonitorFilter.getFromDate()!= null) && (mailMonitorFilter.getFromDate().trim().length() > 0))
        {
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			mailMonitorFilterVO.setFromDate((date.setDate(mailMonitorFilter.getFromDate())));
        }
        if ((mailMonitorFilter.getToDate() != null) && (mailMonitorFilter.getToDate().trim().length() > 0))
        {
        	
        	LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
        	mailMonitorFilterVO.setToDate((date.setDate(mailMonitorFilter.getToDate())));
        }
        if ((mailMonitorFilter.getStation() != null) && (mailMonitorFilter.getStation().trim().length() > 0))
        {
        	
        	mailMonitorFilterVO.setStation(mailMonitorFilter.getStation());
        }
        if ((mailMonitorFilter.getPaCode() != null) && (mailMonitorFilter.getPaCode().trim().length() > 0))
        {
        	
        	mailMonitorFilterVO.setPaCode(mailMonitorFilter.getPaCode());
        }
        if ((mailMonitorFilter.getServiceLevel() != null) && (mailMonitorFilter.getServiceLevel().trim().length() > 0))
        {
        	
        	mailMonitorFilterVO.setServiceLevel(mailMonitorFilter.getServiceLevel());
        }
        displayPage = mailMonitorFilter.getDisplayPage();
        mailMonitorFilterVO.setPageSize(mailMonitorFilter.getPageSize());
        
      try {
        	
    	  mailMonitorSumary = delegate.getPerformanceMonitorDetails(mailMonitorFilterVO);
        }catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
      
      List<MailMonitoringSummary> summary= 
      		MailOperationsModelConverter.constructMailMonitorSummary(mailMonitorSumary);
   
      performanceModel.setMailMonitorSummary(summary);
       
         
        Page<MailbagVO> listBags = null;
        try {
        	
          	listBags = delegate.getPerformanceMonitorMailbags(mailMonitorFilterVO, mailMonitorFilterVO.getType(), displayPage);
        }catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
        if( listBags == null || listBags.size() == 0 )
		{
        //	actionContext.addError(new ErrorVO(NO_DATA_FOUND));		
           	performanceModel.setMissingOriginScanMailbags(null);
        	performanceModel.setMissingArrivalScanMailbags(null);
        	performanceModel.setMissingOriginAndArrivalScanMailbags(null);
        	performanceModel.setOnTimeMailbags(null);
        	performanceModel.setDelayedMailbags(null);
		
        	//return;
	    }
    
        else{
        List<Mailbag> mailbagList = 
        		MailOperationsModelConverter.constructMailbags(listBags);
        if(mailbagList.size() > 0) {
        PageResult<Mailbag> pageList= new PageResult<Mailbag>(listBags,mailbagList);
        if(mailMonitorFilter.getType().equals(MISSING_ORG_SCAN))
          {
        	 performanceModel.setMissingOriginScanMailbags(pageList);
          }
         else   if(mailMonitorFilter.getType().equals(MISSING_DST_SCAN))
          {
       	      performanceModel.setMissingArrivalScanMailbags(pageList);
          }
         else   if(mailMonitorFilter.getType().equals(MISSING_BOTH_SCAN))
         {
      	      performanceModel.setMissingOriginAndArrivalScanMailbags(pageList);
         }
         else   if(mailMonitorFilter.getType().equals(ON_TIME_MAILBAGS))
         {
      	      performanceModel.setOnTimeMailbags(pageList);
      	      
         }
         else   if(mailMonitorFilter.getType().equals(DELAYED_MAILBAGS))
         {
      	      performanceModel.setDelayedMailbags(pageList);
      	      
         }
         else   if(mailMonitorFilter.getType().equals(RAISED_MAILBAGS))
         {
      	      performanceModel.setRaisedMailbags(pageList);
      	      
         }
         else   if(mailMonitorFilter.getType().equals(APPROVED_MAILBAGS))
         {
      	      performanceModel.setApprovedMailbags(pageList);
      	      
         }
         else   if(mailMonitorFilter.getType().equals(DENIED_MAILBAGS))
         {
      	      performanceModel.setDeniedMailbags(pageList);
      	      
         }
          
        }}
      //  else {
     
        //}
        
        results.add(performanceModel);
        responseVO.setResults(results);
        responseVO.setStatus("success");
        actionContext.setResponseVO(responseVO);
        
        this.log.exiting("ListMailPerformanceCommand","execute");
	}
	
}
