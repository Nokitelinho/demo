/*
 * WorkflowDefaultsProxy.java 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterInProcessVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.WorkflowVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
@Module("workflow")
@SubModule("defaults")
public class WorkflowDefaultsProxy extends ProductProxy{
	
    private static final String MODULE_NAME = "mra.defaults";

    /**
	 * Logger and the file name
	 */
	private static final String CLASS_NAME = "WorkflowProxy";

	private Log log = LogFactory.getLogger(MODULE_NAME);

	private static final String PROFORMA_CCA_WORKFLOW = "mailtracking.mra.defaults.proformaccaworkflowname";

  
	/**
	 *
	 * Method for calling WorkflowDefaultsProxy,
	 *
	 * to start the detailsVO
	 *
	 * @param detailsVO
	 *
	 * @throws SystemException
	 *
	 * @author a-3251
	 *
	 */

	public void startWorkflow(CCAdetailsVO detailsVO) throws SystemException {

		log.entering("saveCCA(PROFORMA)", "startWorkflow");

		Collection<WorkflowVO> workflowVOs = populateWorkflowCollection(detailsVO); 

		if (workflowVOs != null && workflowVOs.size() > 0) {

			try { 
				startWorkflow(workflowVOs); 

			} catch (ProxyException proxyException) { 

				throw new SystemException(proxyException.getMessage(),proxyException);

			}

		}

		log.exiting("saveCCA(PROFORMA)", "startWorkflow");

	}

	/**
	 *
	 * Method for populating the Collection<WorkflowVO> for starting the workflow
	 * @param detailsVO
	 * @return Collection<WorkflowVO> * parameters to be set in the returning
	 *         collection * CompanyCode * UserId * WorkFlowName	 *
	 * @author a-3251
	 * @throws SystemException
	 *
	 */

	private Collection<WorkflowVO> populateWorkflowCollection(CCAdetailsVO detailsVO)

	throws SystemException {

		
		log.entering("startWorkflow", "populateWorkflowCollection");
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<WorkflowVO> workflowVOs = null;
		WorkflowVO workflowVO = new WorkflowVO();
		Collection<ParameterInProcessVO> parameterInProcessVOs = new ArrayList<ParameterInProcessVO>();
		
		
		workflowVO.setCompanyCode(detailsVO.getCompanyCode());
		workflowVO.setUserId(logonAttributes.getUserId());
		

		// for geting system parameter -->> if workflow is needed or not
		Map<String, String> workflowSystemPars = null;
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(PROFORMA_CCA_WORKFLOW);

		log.exiting(CLASS_NAME, "workflowSystemParameterCodes-");

		try {

			workflowSystemPars = new SharedDefaultsProxy().findSystemParameterByCodes(parameterCodes);

		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),proxyException);
		}

		if (workflowSystemPars != null) {

			//work flow must proceed so further steps -->>
				workflowVO.setWorkflowName(workflowSystemPars.get(PROFORMA_CCA_WORKFLOW));
				log.log(Log.INFO, "<:workflowSystemPars:>", workflowSystemPars);
					//setting workflow parameters with values from CCAdetailvo
					parameterInProcessVOs.add(populateParameterInProcessVO(ParameterConstantsVO.WRKFLO_PARCOD_CCAREFNUM, detailsVO.getCcaRefNumber()));			
					parameterInProcessVOs.add(populateParameterInProcessVO(ParameterConstantsVO.WRKFLO_PARCOD_CMPCOD, detailsVO.getCompanyCode()));
					parameterInProcessVOs.add(populateParameterInProcessVO(ParameterConstantsVO.WRKFLO_PARCOD_BLGBAS, detailsVO.getBillingBasis()));
					workflowVO.setParametersInProcess(parameterInProcessVOs);			
					workflowVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, false));
					workflowVO.setLastUpdateUser(logonAttributes.getUserId());
					
					workflowVOs = new ArrayList<WorkflowVO>();
					workflowVOs.add(workflowVO);
		}
		log.log(Log.FINE, "The returning WorkflowVOs :::> ", workflowVOs);
		log.exiting("startWorkflow", "populateWorkflowCollection");
		return workflowVOs;

	}

	/**
	 *
	 * Method for populating the parameters in ParameterInProcessVO
	 *
	 * for workflow
	 *
	 * *@author a-3251
	 *
	 * *@param paramOne -- parameterCode
	 *
	 * *@param paramTwo -- parameterName
	 *
	 * *@return parameterInProcessVO
	 *
	 */

	private ParameterInProcessVO populateParameterInProcessVO(String paramOne,String paramTwo){

		log.entering("populateWorkflowCollection", "populateParameterInProcessVO");
		ParameterInProcessVO parameterInProcessVO = new ParameterInProcessVO();
		parameterInProcessVO.setParameterCode(paramOne);
		parameterInProcessVO.setParamaterValue(paramTwo); 
		log.exiting("populateWorkflowCollection","populateParameterInProcessVO");

		return parameterInProcessVO;

	}
	
	  /**
	 * Method will start the configured workflow on the
	 * desired changes for ccaType for a proforma CCA.
	 * @author a-3251
	 * @param workflowVOs
     * @throws ProxyException
	 * @throws SystemException
	 */
	public void startWorkflow(Collection<WorkflowVO> workflowVOs)
			throws ProxyException, SystemException {
	    despatchRequest("startWorkflow", workflowVOs );
	    // request will be mapped in workflow request-mapping.xml
	}
	

}
