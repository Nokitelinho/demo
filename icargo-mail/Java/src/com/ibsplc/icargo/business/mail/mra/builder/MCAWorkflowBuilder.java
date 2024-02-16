package com.ibsplc.icargo.business.mail.mra.builder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.proxy.WorkflowDefaultsProxy;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterInProcessVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.WorkflowVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MCAWorkflowBuilder extends AbstractActionBuilder{
	
	private Log log = LogFactory.getLogger("MCA WORKFLOW BUILDER");
	private static final String CLASSNAME ="MCAWorkflowBuilder";
	private static final String MCAWORKFLOWNAME = "mail.mra.mcaworkflowname";
	
	private static final String WORKFLOWENABLED = "mailtracking.mra.workflowneededforMCA";
	private static final String BASED_ON_RULES = "R";
	
	
	public void startMCAWorkflow(CCAdetailsVO cCAdetailsVO) throws SystemException {
		log.entering(CLASSNAME, "startMCAWorkflow");
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(MCAWORKFLOWNAME);
		parameterCodes.add(WORKFLOWENABLED);
		Map<String,String> systemParameters = findSystemParameterByCodes(parameterCodes);
		String isWorkFowEnabled = null;
		if(systemParameters != null){
			isWorkFowEnabled = systemParameters.get(WORKFLOWENABLED);
		}
		if(BASED_ON_RULES.equals(isWorkFowEnabled)){
			if ((cCAdetailsVO.getAssignee() != null)
					&& (cCAdetailsVO.getAssignee().length() > 0)) {  // workflow will trigger only when assignee is evaluated using business evaluator
					LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
						.getLogonAttributesVO();
					Collection<WorkflowVO> workflowVOs = new ArrayList<WorkflowVO>();
					String workFlowName = null;
					if(systemParameters != null){
						workFlowName = systemParameters.get(MCAWORKFLOWNAME);
					}
					log.log(Log.FINE,"Workflow Name -->"+workFlowName);
					String assignee = cCAdetailsVO.getAssignee();
					//Added as part of ICRD-342197 starts
					String[] assignees = null;
					if(assignee.contains(","))
						assignees=assignee.split(",");
						if(assignees!=null && assignees.length>0){
						for(int i=0;i<assignees.length;i++){
							//Added as part of ICRD-342197 ends
					WorkflowVO workflowVO = new WorkflowVO();
					workflowVO.setCompanyCode(cCAdetailsVO.getCompanyCode());
					workflowVO.setWorkflowName(workFlowName); 
					workflowVO.setStationCode(logonAttributes.getStationCode());
					workflowVO.setUserId(logonAttributes.getUserId());
					workflowVO.setLastUpdateUser(cCAdetailsVO.getLastUpdateUser());
					Collection<ParameterInProcessVO> workflowParameterInProcessVOs = new ArrayList<ParameterInProcessVO>();
					ParameterInProcessVO workflowParameterVO;
					workflowParameterVO = new ParameterInProcessVO();
					workflowParameterVO
						.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_MCAREFNUM);// MCA Number
					workflowParameterVO.setParamaterValue(cCAdetailsVO.getCcaRefNumber());
					workflowParameterInProcessVOs.add(workflowParameterVO);
					workflowParameterVO = new ParameterInProcessVO();
					workflowParameterVO
						.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_ARPCOD);// Airport code
					workflowParameterVO.setParamaterValue(logonAttributes
						.getAirportCode());
					workflowParameterInProcessVOs.add(workflowParameterVO);
					workflowParameterVO = new ParameterInProcessVO();
					workflowParameterVO
						.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_CMPCOD);// Company Code
					workflowParameterVO.setParamaterValue(cCAdetailsVO.getCompanyCode());
					workflowParameterInProcessVOs.add(workflowParameterVO);
					workflowParameterVO = new ParameterInProcessVO();
					//workflowParameterVO
					//	.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_APPROVERROLEGRP);// MCA approver rolegroup
					workflowParameterVO = new ParameterInProcessVO();
					workflowParameterVO
						.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_APRUSR);// MCA approver rolegroup
					
					
					workflowParameterVO.setParamaterValue(assignees[i]);//Modified as part of ICRD-342197
					workflowParameterInProcessVOs.add(workflowParameterVO);
					workflowParameterVO = new ParameterInProcessVO();
					workflowParameterVO
						.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_WRKFLOMCAAPRSTA);// WRKFLO_PARCOD_WRKFLOMCAAPRSTA
					//Modified as part of ICRD-342197
					workflowParameterVO.setParamaterValue(assignees[i] != null ? "RSN"
						: "APR");
					workflowParameterInProcessVOs.add(workflowParameterVO);
				workflowVO.setParametersInProcess(workflowParameterInProcessVOs);
				
				workflowVOs.add(workflowVO);
				
				try {
					new WorkflowDefaultsProxy().startWorkflow(workflowVOs); 
				} catch (ProxyException e) {
					throw new SystemException(e.getMessage(), e);
				}
					}
			}//Added as part of ICRD-342197 starts
					else{
						WorkflowVO workflowVO = new WorkflowVO();
						workflowVO.setCompanyCode(cCAdetailsVO.getCompanyCode());
						workflowVO.setWorkflowName(workFlowName); 
						workflowVO.setStationCode(logonAttributes.getStationCode());
						workflowVO.setUserId(logonAttributes.getUserId());
						workflowVO.setLastUpdateUser(cCAdetailsVO.getLastUpdateUser());
						Collection<ParameterInProcessVO> workflowParameterInProcessVOs = new ArrayList<ParameterInProcessVO>();
						ParameterInProcessVO workflowParameterVO;
						workflowParameterVO = new ParameterInProcessVO();
						workflowParameterVO
							.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_MCAREFNUM);// MCA Number
						workflowParameterVO.setParamaterValue(cCAdetailsVO.getCcaRefNumber());
						workflowParameterInProcessVOs.add(workflowParameterVO);
						workflowParameterVO = new ParameterInProcessVO();
						workflowParameterVO
							.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_ARPCOD);// Airport code
						workflowParameterVO.setParamaterValue(logonAttributes
							.getAirportCode());
						workflowParameterInProcessVOs.add(workflowParameterVO);
						workflowParameterVO = new ParameterInProcessVO();
						workflowParameterVO
							.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_CMPCOD);// Company Code
						workflowParameterVO.setParamaterValue(cCAdetailsVO.getCompanyCode());
						workflowParameterInProcessVOs.add(workflowParameterVO);
						workflowParameterVO = new ParameterInProcessVO();
						//workflowParameterVO
						//	.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_APPROVERROLEGRP);// MCA approver rolegroup
						workflowParameterVO = new ParameterInProcessVO();
						workflowParameterVO
							.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_APRUSR);// MCA approver rolegroup
						
						
					workflowParameterVO.setParamaterValue(assignee);
					workflowParameterInProcessVOs.add(workflowParameterVO);
					workflowParameterVO = new ParameterInProcessVO();
					workflowParameterVO
						.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_WRKFLOMCAAPRSTA);// WRKFLO_PARCOD_WRKFLOMCAAPRSTA
					workflowParameterVO.setParamaterValue(assignee != null ? "RSN"
						: "APR");
					workflowParameterInProcessVOs.add(workflowParameterVO);
				workflowVO.setParametersInProcess(workflowParameterInProcessVOs);
				workflowVOs.add(workflowVO);
				try {
					new WorkflowDefaultsProxy().startWorkflow(workflowVOs); 
				} catch (ProxyException e) {
					throw new SystemException(e.getMessage(), e);
				}
					
			}//Added as part of ICRD-342197 ends
			}
		}
		log.exiting(CLASSNAME, "startMCAWorkflow");
		}
	//for multiple MCAs
	/**
	 * Get system Parameters
	 * @param parameterCodes
	 * @return
	 * @throws SystemException
	 */	
	public void initiateMCAWorkflow(Collection<CCAdetailsVO> cCAdetailsVOs,GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException {
	 for (CCAdetailsVO cCAdetailsVO: cCAdetailsVOs){
		startMCAWorkflow(cCAdetailsVO);
	 }
	}
	/**
	 * Get system Parameters
	 * @param parameterCodes
	 * @return
	 * @throws SystemException
	 */
	private Map<String, String> findSystemParameterByCodes(
			Collection<String> parameterCodes) throws SystemException {
		Map<String, String> systemParameters = null;
		try {
			systemParameters = new SharedDefaultsProxy()
			.findSystemParameterByCodes(parameterCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
		return systemParameters;
	}
}