package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.incentiveconfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.FormulaUxLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
public class ScreenloadFormulaLovCommand extends BaseCommand  {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n ScreenloadFormulaLovCommand----------> \n\n");



		FormulaUxLovForm formulaUxLovForm = (FormulaUxLovForm) invocationContext.screenModel;
		invocationContext.target = SUCCESS;
		MailPerformanceSession mailPerformanceSession =
				getScreenSession(MODULE_NAME,SCREEN_ID);

		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		ArrayList<String> oneTimeList = new ArrayList<String>();
		ArrayList<String> formula = new ArrayList<String>();
		String[] formulaExist = null;
		oneTimeList.add("mail.operations.incentiveformulabasis");
		oneTimeList.add("mail.operations.incentiveformulareference");

		if(formulaUxLovForm.getCode()!=null&& formulaUxLovForm.getCode().length()>0){
			if((formulaUxLovForm.getCode().contains("AND")) || (formulaUxLovForm.getCode().contains("OR"))){
				formulaExist = formulaUxLovForm.getCode().split("~");
				StringBuilder newFormula = null;
				for(int i=0;i<formulaExist.length;i++){
					if(((" AND ").equals(formulaExist[i])) || ((" OR ").equals(formulaExist[i])) ){
						 if(i==0){
							 newFormula = new StringBuilder();
							 newFormula=newFormula.append(formulaExist[i]).append("~");
						 }else{
							 newFormula=newFormula.append(formulaExist[i]).append("~");
							 formula.add(newFormula.toString());
							 newFormula = new StringBuilder();
						 }
					}else{
						if(i==0){
							 newFormula = new StringBuilder();
							 newFormula=newFormula.append(formulaExist[i]).append("~");
						}else{
							newFormula=newFormula.append(formulaExist[i]).append("~");
						}
						if(i==formulaExist.length-1){
				formula.add(newFormula.toString());
						}
					}
				}
				
				mailPerformanceSession.setFormula(formula);
			}else{
				formula.add(formulaUxLovForm.getCode());
				mailPerformanceSession.setFormula(formula);
			}
		}else{
			mailPerformanceSession.setFormula(null);
		}

		try {
			hashMap = defaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException localBusinessDelegateException3) {
			this.log.log(7, "onetime fetch exception");
		}

		Collection<OneTimeVO> basisValues = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.incentiveformulabasis");
		Collection<OneTimeVO> referenceValues = (Collection<OneTimeVO>) hashMap
				.get("mail.operations.incentiveformulareference");
		if (basisValues != null) {
			log.log(Log.INFO, "Sizeee----", basisValues.size());
			for (OneTimeVO list : basisValues) {
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}
		if (referenceValues != null) {
			log.log(Log.INFO, "reference value Sizeee----", referenceValues.size());
			for (OneTimeVO list : referenceValues) {
				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
			}
		}

		mailPerformanceSession.setFormulaBasis((ArrayList<OneTimeVO>)basisValues);
		mailPerformanceSession.setFormulaReference((ArrayList<OneTimeVO>)referenceValues);




		formulaUxLovForm.setScreenStatusFlag
		(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		log.exiting("ScreenloadFormulaLovCommand", "execute");
	}
}
