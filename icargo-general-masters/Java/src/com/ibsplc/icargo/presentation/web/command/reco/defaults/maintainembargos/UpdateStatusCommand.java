package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class UpdateStatusCommand extends BaseCommand{
	
	private static final String ACTIVE = "A";
	private static final String DRAFT = "D";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String OPERATION_FLAG_UPDATE = "U";
	private static final String OPERATION_FLAG_DELETE = "D";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		MaintainEmbargoRulesForm maintainEmbargoForm =
			(MaintainEmbargoRulesForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();

		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		MaintainEmbargoRulesSession maintainEmbargoSession =
			getScreenSession("reco.defaults", "reco.defaults.maintainembargo");
		
		EmbargoRulesVO embargoVO = null;
		
		Collection<ErrorVO> errors = null;

		errors = validateForm(maintainEmbargoForm, companyCode);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			maintainEmbargoForm.setRefNumberFlag("true");
			invocationContext.target = "screenload_failure";
			return;
		}
		embargoVO=maintainEmbargoSession.getEmbargoVo();
		if(embargoVO.getStatus()!=null && embargoVO.getStatus().trim().length()>0){
			Collection<EmbargoParameterVO> paramTemp = new ArrayList<EmbargoParameterVO>();
			Collection<EmbargoGeographicLevelVO> geoTemp = new ArrayList<EmbargoGeographicLevelVO>();
			if((ACTIVE.equals(embargoVO.getStatus()) || "S".equals(embargoVO.getStatus()))  && OPERATION_FLAG_UPDATE.equals(embargoVO.getOperationalFlag())){
				embargoVO.setOperationalFlag(OPERATION_FLAG_INSERT);
				embargoVO.setStatus(DRAFT);
				if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){
					for(EmbargoParameterVO param:embargoVO.getParameters()){
						if(OPERATION_FLAG_DELETE.equals(param.getOperationalFlag())){
							paramTemp.add(param);
						}
						else{
							param.setOperationalFlag(OPERATION_FLAG_INSERT);
							paramTemp.add(param);
						}
					}
				}
				if(embargoVO.getGeographicLevels()!=null && embargoVO.getGeographicLevels().size()>0){
					for(EmbargoGeographicLevelVO geolevel: embargoVO.getGeographicLevels()){
						if(OPERATION_FLAG_DELETE.equals(geolevel.getOperationFlag())){
							geoTemp.add(geolevel);
						}
						else{
							geolevel.setOperationFlag(OPERATION_FLAG_INSERT);
							geoTemp.add(geolevel);
						}
					}
				}
				embargoVO.setParameters(paramTemp);
				embargoVO.setGeographicLevels(geoTemp);
			}
		}
		else{
			embargoVO.setStatus("D");
		}
		invocationContext.target = "screenload_success";
	}
	private Collection<ErrorVO> validateForm(MaintainEmbargoRulesForm form,
			 String companyCode) {
	
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		
		if(form.getCategory() == null || form.getCategory().trim().length() == 0){
			error = new ErrorVO("reco.defaults.categoryempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else{
			if((form.getRemarks()==null || form.getRemarks().trim().length()==0) && "O".equals(form.getCategory()) ){
				error = new ErrorVO("reco.defaults.remarksmandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if(form.getComplianceType() == null || form.getComplianceType().trim().length() == 0){
			error = new ErrorVO("reco.defaults.compliancetypeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		return errors;
	}
}