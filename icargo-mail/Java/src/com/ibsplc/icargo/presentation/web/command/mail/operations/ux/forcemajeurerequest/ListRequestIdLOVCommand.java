package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ListRequestIdLOVCommand extends BaseCommand
{

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
	private static final String TARGET = "lov_success";
	private Log log = LogFactory.getLogger("Mail Operations force majeure request");
	private static final String NO_RESULT = "mailtracking.defaults.forcemajeure.msg.err.noresultsfound";
	@Override
	public void execute(InvocationContext invocationContext)
		    throws CommandInvocationException
		  {
		    this.log.entering("ListRequestIdLOVCommand", "execute");

		    LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		    ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
			invocationContext.target = TARGET;
			ForceMajeureRequestSession forceMajeureRequestSession = getScreenSession(
					MODULE_NAME, SCREEN_ID);
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		    Page <ForceMajeureRequestVO>forceMajeureRequesLovVOs = null;
		    ForceMajeureRequestFilterVO forceMajeureRequestFilterVO = new ForceMajeureRequestFilterVO();
		    forceMajeureRequestFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		    if("CLEARLOV".equals(forceMajeureRequestForm.getActionFlag())){
		    	forceMajeureRequestForm.setFrmDate("");
		    	forceMajeureRequestForm.setToDate("");
		    	forceMajeureRequestForm.setForceid("");
		    }
		    LocalDate fromdate = null;
			if(forceMajeureRequestForm.getFrmDate() != null && forceMajeureRequestForm.getFrmDate().trim().length() >0){
			fromdate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false).setDate(forceMajeureRequestForm.getFrmDate());  
			}
			else{      
				fromdate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false).addMonths(-1);
				forceMajeureRequestForm.setFrmDate(fromdate.toDisplayDateOnlyFormat());  
			}   
			LocalDate todate = null;
			if(forceMajeureRequestForm.getToDate() != null && forceMajeureRequestForm.getToDate().trim().length() >0){
			 todate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false).setDate(forceMajeureRequestForm.getToDate());
			}
			else{
				todate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false);
				forceMajeureRequestForm.setToDate(todate.toDisplayDateOnlyFormat());    
			}   
		    if ((forceMajeureRequestForm.getForceid() != null) && (forceMajeureRequestForm.getForceid().trim().length() > 0)) {
		    	forceMajeureRequestFilterVO.setForceMajeureID(forceMajeureRequestForm.getForceid().toUpperCase());
		    }else{
		    	forceMajeureRequestFilterVO.setForceMajeureID("");
		    }
		   int displaypage=1;
		    if(forceMajeureRequestForm.getDisplayPage()!=null&& forceMajeureRequestForm.getDisplayPage().trim().length()>0){
				displaypage=Integer.parseInt(forceMajeureRequestForm.getDisplayPage());
			}
		    forceMajeureRequestFilterVO.setFromDate(fromdate);
		    forceMajeureRequestFilterVO.setToDate(todate);
		    forceMajeureRequestFilterVO.setPageNumber(displaypage);
		    String defaultSize = forceMajeureRequestForm.getDefaultPageSize();
		    int defaultPageSize = Integer.parseInt(defaultSize);
		    if(forceMajeureRequestFilterVO.getTotalRecords() == 0){
		    forceMajeureRequestFilterVO.setTotalRecords(-1);
			}
		    forceMajeureRequestFilterVO.setDefaultPageSize(defaultPageSize);
		    MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		    try {
		    	forceMajeureRequesLovVOs = mailTrackingDefaultsDelegate.listForceMajeureRequestIds(forceMajeureRequestFilterVO,displaypage);
		    }
		    catch (BusinessDelegateException businessDelegateException) {
		      handleDelegateException(businessDelegateException);
		      this.log.log(3, "caught");
		    }
			if (forceMajeureRequesLovVOs == null
					|| forceMajeureRequesLovVOs.size() == 0) {
				log.log(Log.SEVERE,
						"\n\n *******no record found********** \n\n");
				ErrorVO error = new ErrorVO(NO_RESULT);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error); 
				forceMajeureRequestSession
				.setListForceMajeureLovVos(null); //Added by A-8164 for ICRD-316302     
				invocationContext.addAllError(errors);   
				invocationContext.target = TARGET; 
				return;
			}
			if (forceMajeureRequesLovVOs != null
					&& forceMajeureRequesLovVOs.size() > 0) {
				forceMajeureRequestSession 
				.setTotalRecords(forceMajeureRequesLovVOs.size()); 
		forceMajeureRequestSession
				.setListForceMajeureLovVos(forceMajeureRequesLovVOs); //Changed by A-8164 for ICRD-316302
			}
		    

		    invocationContext.target = "lov_success";
		    this.log.exiting("ListRequestIdLOVCommand", "execute");
		  }
}
