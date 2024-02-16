package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DamageListDetailsCommand extends BaseCommand{


    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_failure";
    private static final String SCREENID_MAINTAIN ="uld.defaults.ux.maintaindamagereport";
    private static final String MODULE = "uld.defaults";
	private Log log = LogFactory.getLogger("ListCommand");


	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
    log.entering("ListCommand---------------->>>>","Entering");
    String companyCode = logonAttributes.getCompanyCode();
    MaintainDamageReportForm masterForm =(MaintainDamageReportForm) invocationContext.screenModel;
    MaintainDamageReportSession maintainDamageReportSession =(MaintainDamageReportSession)getScreenSession(MODULE,SCREENID_MAINTAIN);

   String section= masterForm.getSection()[0];
    log.log(Log.FINE, "The section value", section);
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    ArrayList<ULDDamageChecklistVO>damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();

    ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
    try {
    	damageChecklistVOs = (ArrayList<ULDDamageChecklistVO>)delegate.listULDDamageChecklistMaster(companyCode,section);
	log
			.log(
					Log.FINE,
					"damageChecklistVOs getting from delegate--------->>>>>>>>>>>>>>",
					damageChecklistVOs);
} catch (BusinessDelegateException e) {
	e.getMessage();
	errors= handleDelegateException(e);
}
  if(damageChecklistVOs==null ||damageChecklistVOs.size()==0)
  {
	 invocationContext.addError(new ErrorVO(
	            "uld.defaults.nodetails",null));
			invocationContext.addAllError(errors);
			//log.log(Log.FINE,"No values in the Session From equalto null "+damageChecklistVOs);
			 maintainDamageReportSession.setULDDamageChecklistVO(null);
			masterForm.setStatusFlag("list_success");
			masterForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_FAILURE;
			return;
  }
  else
  {

	log.log(Log.FINE, "No values in the Session", damageChecklistVOs);
		//damageChecklistMasterSession.setULDDamageChecklistVO(damageChecklistVOs);
		  maintainDamageReportSession.setULDDamageChecklistVO(damageChecklistVOs);
	  //}
	  masterForm.setStatusFlag("list_success");
	  masterForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	 /* log.log(Log.FINE,"The session values"+damageChecklistVOs);
	 damageChecklistMasterSession.setULDDamageChecklistVO(damageChecklistVOs);
	  masterForm.setStatusFlag("list_success");
	  masterForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);*/
	  invocationContext.target = LIST_SUCCESS;
  }

}

}
