package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.listinvoic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand  extends BaseCommand {
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.listinvoic";
	private static final String TARGET = "list_success";
	private Log log = LogFactory.getLogger("Mail MRA of List invoic Screen ");
	private static final String NO_RESULT = "mailtracking.defaults.listinvoic.msg.err.noresultsfound";
	private static final String LIST_FAILURE="list_failure";
	private static final String ONETIMEBNDTYPE="mailtracking.mra.gpareporting.invoicstatus";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE,
				"\n\n in the list command of List invoic Screen----------> \n\n");
		ListInvoicForm listInvoicForm = (ListInvoicForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ListInvoicSession listinvoicsession =getScreenSession(MODULE_NAME,SCREENID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<InvoicVO> invoicVOs = null;
		InvoicFilterVO invoicFilterVO=null;
		if(listInvoicForm.getActionFlag() == null && listinvoicsession.getFilterParamValues() != null){
			invoicFilterVO = listinvoicsession.getFilterParamValues();
		}else{
			invoicFilterVO = new InvoicFilterVO();
		}

		invoicFilterVO.setCmpcod(companyCode);
		if(listInvoicForm.getFromDate()!=null && listInvoicForm.getFromDate().length()>0){
			invoicFilterVO.setFromDate(listInvoicForm.getFromDate());
		}

		if(listInvoicForm.getToDate()!=null && listInvoicForm.getToDate().length()>0){
			invoicFilterVO.setToDate(listInvoicForm.getToDate());
		}
		if (listInvoicForm.getPaCode() != null
				&& listInvoicForm.getPaCode().trim().length() > 0) {
			invoicFilterVO.setGpaCode(listInvoicForm.getPaCode());
		}
		if(listInvoicForm.getStatus()!=null && listInvoicForm.getStatus().length()>0){
			invoicFilterVO.setInvoicfilterStatus(listInvoicForm.getStatus());  	
		}
		if(listInvoicForm.getFileName() != null && listInvoicForm.getFileName().trim().length()>0){
			invoicFilterVO.setFileName(listInvoicForm.getFileName());  	
		}
		if(listInvoicForm.getInvoicRefId() != null && listInvoicForm.getInvoicRefId().trim().length()>0){
			invoicFilterVO.setInvoicRefId(listInvoicForm.getInvoicRefId());  	
		}
		listinvoicsession.setFilterParamValues(invoicFilterVO);
		
		int displaypage=1;
		if(invoicFilterVO.getTotalRecords() == 0){
			invoicFilterVO.setTotalRecords(-1);
		}
		if(listInvoicForm.getDefaultPageSize()!=null && listInvoicForm.getDefaultPageSize().trim().length()>0){
			invoicFilterVO.setDefaultPageSize(Integer.parseInt(listInvoicForm.getDefaultPageSize()));
		}
		if(listInvoicForm.getDisplayPage()!=null&& listInvoicForm.getDisplayPage().trim().length()>0){
			displaypage=Integer.parseInt(listInvoicForm.getDisplayPage());
		}

		invoicVOs=listInvoic(invoicFilterVO,displaypage);
		if (invoicVOs == null
				|| invoicVOs.size() == 0) {
			log.log(Log.SEVERE,
					"\n\n *******no record found********** \n\n");
			ErrorVO error = new ErrorVO(NO_RESULT);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);

			invocationContext.target = LIST_FAILURE;
			return;
		}else{
			Map<String,String> map = new HashMap<String,String>();
			Map<String, Collection<OneTimeVO>> oneTimeValues = new HashMap<String, Collection<OneTimeVO>>();
		    Collection <String> oneTimeList = new ArrayList<String>();
		    oneTimeList.add(ONETIMEBNDTYPE); 
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			try {
				oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				handleDelegateException(businessDelegateException);
			}
			if (invoicVOs != null && invoicVOs.size() > 0) {
				for(OneTimeVO onetime : oneTimeValues.get(ONETIMEBNDTYPE)){
					map.put(onetime.getFieldValue(), onetime.getFieldDescription());
				}
				for(InvoicVO reqVO : invoicVOs){
					if(reqVO.getInvoicStatusCode() != null){
						reqVO.setInvoicStatus(map.get(reqVO.getInvoicStatusCode()));
					}
				}
			
			listInvoicForm.setActionFlag("SHOWLIST");
			listinvoicsession.setTotalRecords(invoicVOs.size());
			listinvoicsession.setListinvoicvos(invoicVOs);
			}
			if(listinvoicsession.getRejectInvoic()!=null && !"NW".equals(listinvoicsession.getRejectInvoic()) ){
				ErrorVO displayErrorVO = new ErrorVO("mailtracking.mra.defaults.listinvoic.rejectInfo");
				displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(displayErrorVO);
				invocationContext.addAllError(errors);
			}
		}
		log.exiting("ScreenloadCommand","execute");
	}

	private Page<InvoicVO> listInvoic(InvoicFilterVO invoicFilterVO,int pageNumber) {
		Page<InvoicVO> listinvoicVOs = null;
		log.log(Log.INFO,
				"\n\n ******* Inside listForceMajeureApplicableMails********** \n\n");
		try {

			listinvoicVOs = new MailTrackingMRADelegate().listInvoic(invoicFilterVO, pageNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return listinvoicVOs;
	}


}