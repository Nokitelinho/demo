package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * Java file : com.ibsplc.icargo.presentation.web.command.mail.operations.ux.
 * forcemajeurerequest.ListForceMajeureCommand.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-8527 :
 * 01-Dec-2018 : Draft
 */
public class ListForceMajeureCommand extends BaseCommand {

private static final String MODULE_NAME = "mail.operations";
private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
private static final String TARGET = "reqlist_success";
private Log log = LogFactory.getLogger("Mail Operations force majeure request");
private static final String NO_RESULT = "mailtracking.defaults.forcemajeureRequest.msg.err.noresultsfound";
private static final String ONETIMESTATTYPE="mailtracking.defaults.forcemajeurerequest.status";
private static final String ONETIMEBNDTYPE="mailtracking.defaults.operationtype";
private static final String NO_RECORDS = "mailtracking.defaults.forcemajeureRequest.msg.err.norecords";

@Override
public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
	log.log(Log.FINE,
			"\n\n in the list command of Req tab force Majeure Request----------> \n\n");
	ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
	invocationContext.target = TARGET;
	ForceMajeureRequestSession forceMajeureRequestSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	String companyCode = logonAttributes.getCompanyCode();
	ForceMajeureRequestFilterVO forceMajeureRequestFilterVO = new ForceMajeureRequestFilterVO();
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	Page<ForceMajeureRequestVO> forceMajeureRequestVOs = null;
	
		if(forceMajeureRequestForm.getForceid()!=null&& forceMajeureRequestForm.getForceid().trim().length()>0){
		forceMajeureRequestFilterVO.setForceMajeureID(forceMajeureRequestForm.getForceid());
		}
		forceMajeureRequestFilterVO.setCompanyCode(companyCode);
		
		
		if(forceMajeureRequestForm.getActionFlag()!=null && forceMajeureRequestForm.getActionFlag().trim().length()>0 ){
		forceMajeureRequestForm.setActionFlag(forceMajeureRequestForm.getActionFlag());
		}
		forceMajeureRequestFilterVO.setSortingField(forceMajeureRequestForm.getSortingField());
		forceMajeureRequestFilterVO.setSortOrder(forceMajeureRequestForm.getSortOrder());
	int displaypage=1;
	if(forceMajeureRequestFilterVO.getTotalRecords() == 0){
		forceMajeureRequestFilterVO.setTotalRecords(-1);
	}
	boolean filterApplied = false;
	if(forceMajeureRequestForm.getDefaultPageSize()!=null && forceMajeureRequestForm.getDefaultPageSize().trim().length()>0){
		forceMajeureRequestFilterVO.setDefaultPageSize(Integer.parseInt(forceMajeureRequestForm.getDefaultPageSize()));
	}
	if(forceMajeureRequestForm.getDisplayPage()!=null&& forceMajeureRequestForm.getDisplayPage().trim().length()>0){
		displaypage=Integer.parseInt(forceMajeureRequestForm.getDisplayPage());
	}
	if(forceMajeureRequestForm.getAirportFilter()!=null && forceMajeureRequestForm.getAirportFilter().trim().length()>0){
		filterApplied = true;
		forceMajeureRequestFilterVO.setAirportCode(forceMajeureRequestForm.getAirportFilter().toUpperCase());
	}
	if(forceMajeureRequestForm.getCarrierFilter()!=null && forceMajeureRequestForm.getCarrierFilter().trim().length()>0){
		filterApplied = true;
		forceMajeureRequestFilterVO.setCarrierCode(forceMajeureRequestForm.getCarrierFilter().toUpperCase().toUpperCase());
	}
	if(forceMajeureRequestForm.getFlightNumberFilter()!=null && forceMajeureRequestForm.getFlightNumberFilter().trim().length()>0){
		filterApplied = true;
		forceMajeureRequestFilterVO.setFlightNumber(forceMajeureRequestForm.getFlightNumberFilter().toUpperCase());
	}
	if(forceMajeureRequestForm.getFlightDateFilter()!=null && forceMajeureRequestForm.getFlightDateFilter().trim().length()>0){
		filterApplied = true;
		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false).setDate(forceMajeureRequestForm.getFlightDateFilter());
		forceMajeureRequestFilterVO.setFlightDate(flightDate);
	}
	if(forceMajeureRequestForm.getConsignmentNo()!=null && forceMajeureRequestForm.getConsignmentNo().trim().length()>0){
		filterApplied = true;
		forceMajeureRequestFilterVO.setConsignmentNo(forceMajeureRequestForm.getConsignmentNo().toUpperCase());
	}
	if(forceMajeureRequestForm.getMailbagId()!=null && forceMajeureRequestForm.getMailbagId().trim().length()>0){
		filterApplied = true;
		forceMajeureRequestFilterVO.setMailbagId(forceMajeureRequestForm.getMailbagId().toUpperCase());
	} 
	Map<String,String> map = new HashMap<String,String>();
	Map<String, Collection<OneTimeVO>> oneTimeValues = new HashMap<String, Collection<OneTimeVO>>();
    Collection <String> oneTimeList = new ArrayList<String>();
    oneTimeList.add(ONETIMESTATTYPE); 
    oneTimeList.add(ONETIMEBNDTYPE);
	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	try{
		oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
	} catch (BusinessDelegateException businessDelegateException) {
		businessDelegateException.getMessage();
		handleDelegateException(businessDelegateException);
	}
	try{
		forceMajeureRequestVOs = listForceMajeureDetails(
						forceMajeureRequestFilterVO,displaypage );
	}catch(BusinessDelegateException businessDelegateException) {
		businessDelegateException.getMessage();
		handleDelegateException(businessDelegateException);
		
	}
		if (forceMajeureRequestVOs == null
				|| forceMajeureRequestVOs.size() == 0) {
			forceMajeureRequestSession
			.setTotalRecords(0);
			forceMajeureRequestSession
			.setReqforcemajeurevos(null);
			log.log(Log.SEVERE,
					"\n\n *******no record found********** \n\n");
			ErrorVO error = new ErrorVO(NO_RESULT);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			if(!filterApplied) {
			errors.add(error);
			} else {
				error = new ErrorVO(NO_RECORDS);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			invocationContext.addAllError(errors);

			invocationContext.target = TARGET;
			return;
		}
		if (forceMajeureRequestVOs != null
				&& forceMajeureRequestVOs.size() > 0) {
			Collection<OneTimeVO> vos = new ArrayList<OneTimeVO>();
            vos.addAll(oneTimeValues.get(ONETIMESTATTYPE));
            vos.addAll(oneTimeValues.get(ONETIMEBNDTYPE));
            for(OneTimeVO onetime : vos){
				map.put(onetime.getFieldValue(), onetime.getFieldDescription());
			}
            String reqstatus="REQ";
			for(ForceMajeureRequestVO reqVO : forceMajeureRequestVOs){
                        reqVO.setStatus(map.get(reqVO.getStatus())== null?"":map.get(reqVO.getStatus()));
                        reqVO.setType(map.get(reqVO.getType())== null?"":map.get(reqVO.getType()));
                        reqstatus=reqVO.getStatus();
            }
            String reqremarks=forceMajeureRequestVOs.iterator().next().getRequestRemarks();
            String appredremarks =forceMajeureRequestVOs.iterator().next().getApprovalRemarks();
            String userId =forceMajeureRequestVOs.iterator().next().getLastUpdatedUser();
            if(userId!=null && !userId.isEmpty()){
            forceMajeureRequestForm.setUserId(userId);        
            }else{      
            	forceMajeureRequestForm.setUserId(logonAttributes.getUserId());
            }              
      forceMajeureRequestFilterVO.setForceMajeureID(forceMajeureRequestVOs.iterator().next().getForceMajuereID());
          if(reqremarks!=null){
        	  forceMajeureRequestFilterVO.setReqRemarks(reqremarks);
            }
           else{
           	 forceMajeureRequestForm.setReqTabRemarks("");
            }
          if(appredremarks!=null){
          forceMajeureRequestForm.setReqTabRemarks(appredremarks);
            }
          forceMajeureRequestForm.setReqStatus(reqstatus);
			forceMajeureRequestForm.setDisplaytype("SHOWREQ");
			forceMajeureRequestSession
					.setTotalRecords(forceMajeureRequestVOs.size());
			forceMajeureRequestSession
					.setReqforcemajeurevos(forceMajeureRequestVOs);
			forceMajeureRequestSession.setFilterParamValues(forceMajeureRequestFilterVO);
		}
}

public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO forceMajeureRequestFilterVO,int displaypage)
	    throws BusinessDelegateException{
	Page<ForceMajeureRequestVO> listForceMajeureReqVOs = null;
	
	try {

		listForceMajeureReqVOs = new MailTrackingDefaultsDelegate().listForceMajeureDetails(forceMajeureRequestFilterVO,displaypage);
	} catch (BusinessDelegateException businessDelegateException) {
		businessDelegateException.getMessageVO().getErrors();
		handleDelegateException(businessDelegateException);
	}
	return listForceMajeureReqVOs;
}

}
