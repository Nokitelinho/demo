package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.SaveActualWeightCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8672	:	04-Feb-2019	:	Draft
 */
public class SaveActualWeightCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("Mail Operations");
	   
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";
	private static final String STNPAR_DEFUNIT_WGT = "station.defaults.unit.weight";

	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		  log.entering("SaveContentIDCommand","execute");
		  
		   LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		   ResponseVO responseVO = new ResponseVO();
		   ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		   ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
		   MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =new MailTrackingDefaultsDelegate();
	       
		  
	       if(listContainerModel != null){
	    	   Collection<ContainerDetails> containerActionData = listContainerModel.getSelectedContainerData();
	    	   String actualWeightUnit=null;
	    	   actualWeightUnit=findStationParameterValue(STNPAR_DEFUNIT_WGT);
	    	   for(ContainerDetails containerDetail : containerActionData){
	    		   containerDetail.setActualWeightUnit(actualWeightUnit);
	    		   containerDetail.setAssignedPort(containerDetail.getAcceptedPort());
	    	   mailTrackingDefaultsDelegate.updateActualWeightForMailULD(MailOperationsModelConverter.constructContainerVO(containerDetail, logonAttributes));
	    	   }
	    	   
	       }
	       if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}
	       responseVO.setStatus("success");
	       actionContext.setResponseVO(responseVO);
	       
		  log.exiting("SaveContentIDCommand","execute");
		
	}	

	/**
	 * 
	 * 	Method		:	SaveActualWeightCommand.findSystemParameterValue
	 *	Added by 	:	A-8164 on 02-Aug-2019
	 * 	Used for 	:
	 *	Parameters	:	@param syspar
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	String
	 */
	private static String findSystemParameterValue(String syspar) throws SystemException, BusinessDelegateException {
    	String sysparValue = null;
    	ArrayList<String> systemParameters = new ArrayList<String>();
    	systemParameters.add(syspar);
    	Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
    			.findSystemParameterByCodes(systemParameters);
    	if (systemParameterMap != null) {
    		sysparValue = systemParameterMap.get(syspar);
    	}
    	return sysparValue;
    }
	/**
	 * @author A-7779
	 * @param stnPar
	 * @return
	 */
	private String findStationParameterValue(String stnPar){
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		AreaDelegate areaDelegate = new AreaDelegate();
		//String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL); 
		Map stationParameters = null; 
		String stationCode = logonAttributes.getStationCode();	
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(STNPAR_DEFUNIT_WGT);
		try {
			stationParameters = areaDelegate.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode, parameterCodes);
		} catch (BusinessDelegateException e1) {
			e1.getMessage();
		} 
		return (String)stationParameters.get(STNPAR_DEFUNIT_WGT);
    }
}
