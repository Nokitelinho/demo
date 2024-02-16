/*
 * ListSCMValidationCommand.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmvalidation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMValidationSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMValidationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ListSCMValidationCommand extends BaseCommand{
	
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String SCREENID = "uld.defaults.scmvalidation";
    private static final String MODULE = "uld.defaults";
	private Log log = LogFactory.getLogger("ListCommand");
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
	private static final String FORMAT_STRING="%1$-16.2f";
	private static final String SYS_PARAM_DISPLAYDISONSTOCKCHECK = "uld.defaults.displaydiscrepancyonstockcheck";
	
	
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
	log.entering("ListSCMValidationCommand", "execute");
	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	SCMValidationForm scmValidationForm = (SCMValidationForm)invocationContext.screenModel;
	SCMValidationSession scmValidationSession = (SCMValidationSession) getScreenSession(
			MODULE, SCREENID);
	String companyCode = logonAttributes.getCompanyCode();
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	SCMValidationFilterVO scmValidationFilterVO = new SCMValidationFilterVO();
	errors = validateForm(scmValidationForm, scmValidationFilterVO);
	if (errors != null && errors.size() > 0) {
		invocationContext.addAllError(errors);
		invocationContext.target = LIST_FAILURE;
		return;
	}
	scmValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	log.log(Log.FINE, "logonAttributes--->", logonAttributes);
	log.log(Log.FINE, "logonAttributes.getOwnAirlineIdentifier()------------>",
			logonAttributes.getOwnAirlineIdentifier());
	scmValidationFilterVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
	//a-3278 ends
	if((scmValidationForm.getUldTypeCode() != null &&
			scmValidationForm.getUldTypeCode().trim().length() > 0)) {
		scmValidationFilterVO.setUldTypeCode(scmValidationForm.getUldTypeCode().toUpperCase());
	}
	if((scmValidationForm.getFacilityType()!= null &&
			scmValidationForm.getFacilityType().trim().length() > 0)) {
	scmValidationFilterVO.setFacilityType(scmValidationForm.getFacilityType().toUpperCase());
	}
	if((scmValidationForm.getLocation()!= null &&
			scmValidationForm.getLocation().trim().length() > 0)) {
		scmValidationFilterVO.setLocation(scmValidationForm.getLocation().toUpperCase());
	}
	if((scmValidationForm.getScmStatus()!= null &&
			scmValidationForm.getScmStatus().trim().length() > 0)) {
	scmValidationFilterVO.setScmStatus(scmValidationForm.getScmStatus().toUpperCase());
	}
	scmValidationFilterVO.setPageNumber(Integer.parseInt(scmValidationForm.getDisplayPage()));
	log.log(Log.FINE, "Filter VO To Server------------->",
			scmValidationFilterVO);
	ULDDefaultsDelegate uldDelegate = new ULDDefaultsDelegate();
	Page<SCMValidationVO> scmvalidationVOs = null;
	Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
	SCMValidationVO scmvalidationVOTORemove = null;
	try {
		scmvalidationVOs = uldDelegate.findSCMValidationList(scmValidationFilterVO);
	} catch (BusinessDelegateException businessDelegateException) {
		businessDelegateException.getMessage();
		exception = handleDelegateException(businessDelegateException);
	}
	if(scmvalidationVOs !=null && scmvalidationVOs.size()>0){			
		 boolean includeDiscrepancyCheck=false;
		 Collection<String> parameterTypes = new ArrayList<String>();
			parameterTypes.add(SYS_PARAM_DISPLAYDISONSTOCKCHECK);
			Map<String,String> sysParameterMap = null;
			 SharedDefaultsDelegate sharedDelegate = new SharedDefaultsDelegate();
			 try {
				 sysParameterMap = sharedDelegate.findSystemParameterByCodes(parameterTypes);
		        } catch (BusinessDelegateException ex) {
		          handleDelegateException(ex);
		        }
			 if(sysParameterMap!=null&&sysParameterMap.get(SYS_PARAM_DISPLAYDISONSTOCKCHECK)!=null){
				 includeDiscrepancyCheck=OneTimeVO.FLAG_YES.equals(sysParameterMap.get(SYS_PARAM_DISPLAYDISONSTOCKCHECK));
			 }
		Collection<String> oneTimeToModes = new ArrayList<String>();
		oneTimeToModes.add(FACILITYTYPE_ONETIME);			
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeToModeMap = null;
		try {
			oneTimeToModeMap = sharedDefaultsDelegate.findOneTimeValues(
					companyCode, oneTimeToModes);				
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}			
		Collection<OneTimeVO> facilitytype = oneTimeToModeMap.get(FACILITYTYPE_ONETIME);
		for(SCMValidationVO vo : scmvalidationVOs){
			if(includeDiscrepancyCheck){
				if(vo.isMissingDiscrepancyCaptured()){
					vo.setScmFlag(ULDVO.FLAG_NO);
				}
			}
			for(OneTimeVO oneTimeVO : facilitytype){
				if(oneTimeVO.getFieldValue().equals(vo.getFacilityType())){
					vo.setFacilityType(oneTimeVO.getFieldDescription());
				}
			}			
		}
		log.log(Log.FINE, "SCMValidationVO page pg after converting onetime",
				scmvalidationVOs);
	}
		if (scmvalidationVOs != null && scmvalidationVOs.size()>0) {
			log.log(Log.FINE, "size at the beginning--->", scmvalidationVOs.size());
			int tot = 0;
			int notsighted = 0;
			double missing = 0.0;
			notsighted = Integer.parseInt(scmvalidationVOs.get(0).getNotSighted());
			tot = Integer.parseInt(scmvalidationVOs.get(0).getTotal());
			
		  if(tot != 0){
			  scmValidationForm.setTotal(String.valueOf(tot));
			  scmValidationForm.setNotSighted(String.valueOf(notsighted));
			  scmValidationForm.setLastPageNum(new StringBuilder("").append((int)(Math.ceil(tot/(float)scmvalidationVOs.getActualPageSize()))).toString());
			  missing = (double)notsighted/(double)(tot);
			  missing = missing*100;
			  String missingForm = String.valueOf(new Formatter()
				.format(FORMAT_STRING,missing));
			  log.log(Log.FINE, "tot--->", tot);
			log.log(Log.FINE, "missing--->", missingForm);
			log.log(Log.FINE, "missing--->", String.valueOf(missingForm));
			/* Added By A-5275 for ICRD-26017 */
			  scmValidationForm.setMissing(missingForm.trim());
			  

		  }
		  log.log(Log.FINE, "size seeting to session--->", scmvalidationVOs.size());
			scmValidationForm.setStatusFlag("list_success");
			scmValidationSession.setSCMValidationVOs(scmvalidationVOs);
		} 
		
	if(scmvalidationVOs == null ||scmvalidationVOs.size() == 0){
		ErrorVO errorVO = new ErrorVO(
				"uld.defaults.scmvalidation.norecords");
		errors.add(errorVO);
		invocationContext.addAllError(errors);
		scmValidationSession.setSCMValidationVOs(null);
		scmValidationForm.setTotal("");
		scmValidationForm.setNotSighted("");
		scmValidationForm.setMissing("");
		scmValidationForm.setStatusFlag("list_success");
		invocationContext.target = LIST_SUCCESS;
		return;
	} 
	invocationContext.target = LIST_SUCCESS;
 }
/**
 * 
 * @param form
 * @param scmValidationFilterVO
 * @return
 */

private Collection<ErrorVO> validateForm(SCMValidationForm form,
		SCMValidationFilterVO scmValidationFilterVO) {
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	ErrorVO error = null;
	String companyCode = getApplicationSession().getLogonVO()
			.getCompanyCode();
	if((form.getAirport()== null 
			|| form.getAirport().trim().length()== 0)){
		 error = new ErrorVO("uld.defaults.scmvalidation.airportmandatory");
		errors.add(error);
	}
	log
			.log(Log.FINE, "scm validation airport------------->", form.getAirport());
	if (form.getAirport() != null && !"".equals(form.getAirport())) {
		log.log(Log.FINE,
				"scm validation airport inside first if------------->", form.getAirport());
		if (validateAirportCodes(form.getAirport().toUpperCase(),
				companyCode) == null) {
			log.log(Log.FINE,
					"scm validation airport inside second if-------------->",
					form.getAirport());
			scmValidationFilterVO.setAirportCode(form.getAirport().toUpperCase());
		} else {
			error = new ErrorVO(
					"uld.defaults.scmvalidation.invalidairport",
					new Object[] { form.getAirport().toUpperCase() });
			errors.add(error);
		}
	}
	return errors;

}
/**
 * 
 * @param station
 * @param companyCode
 * @return
 */
public Collection<ErrorVO> validateAirportCodes(String station,
		String companyCode) {
	log.entering("ListCommand", "validateAirportCodes");
	Collection<ErrorVO> errors = null;
	try {
		AreaDelegate delegate = new AreaDelegate();
		delegate.validateAirportCode(companyCode, station);

	} catch (BusinessDelegateException e) {
		e.getMessage();
		errors = handleDelegateException(e);
	}
	log.exiting("ListCommand", "validateAirportCodes");
	return errors;
}

}
