package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditdsnenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditDsnEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailCarditModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditdsnenquiry.ListMailbagsForDsnCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	23-Sep-2019		:	Draft
 */
public class ListMailbagsForDsnCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("OPERATIONS CARDITDSNENQUIRY");
	private static final String NO_DSN_SELECTED = "mail.operations.carditdsnenquiry.err.nodsnseleted";
	private static final String NO_MAILBAGS_PRESENT = "mail.operations.carditdsnenquiry.err.nomailbagspresent";
	
	public void execute(ActionContext actionContext){
		
		log.entering("ListMailbagsForDsnCommand","execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		CarditDsnEnquiryModel carditDsnEnquiryModel=
				(CarditDsnEnquiryModel) actionContext.getScreenModel();
		Collection<DSNDetails> dsnDetailsCollection=
				carditDsnEnquiryModel.getDsnDetailsCollection();
		CarditFilter carditFilter=
				(CarditFilter)carditDsnEnquiryModel.getCarditFilter();
		CarditEnquiryFilterVO carditEnquiryFilterVO=null;
		Collection<Mailbag> mailbagDetailsCollection=
				new ArrayList<>();
		Page<MailbagVO> mailbagVOs=null;
		Collection<MailbagVO> selectedMailbagVos=
				new ArrayList<>();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate=
				new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ResponseVO responseVO = new ResponseVO();
		
		if(dsnDetailsCollection==null){
			actionContext.addError(new ErrorVO(NO_DSN_SELECTED));
			return;
		}
		
		if(dsnDetailsCollection.size()==0 && carditFilter!=null &&
				carditFilter.getNonSelectionOperation()!=null && 
					carditFilter.getNonSelectionOperation().trim().length()>0){
			carditEnquiryFilterVO=
					MailOperationsModelConverter.constructCarditFilterVO(carditFilter, logonAttributes);
			carditEnquiryFilterVO.setTotalRecordsCount(Integer.parseInt(carditFilter.getTotalMailbagCount()));
			try {
				mailbagVOs=mailTrackingDefaultsDelegate
						.findCarditMails(carditEnquiryFilterVO,1);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				errors = handleDelegateException(businessDelegateException); 
			}
			if (errors != null && errors.size() > 0) {
	    		actionContext.addAllError((List<ErrorVO>) errors); 
	    	    return;
			}
			if(mailbagVOs==null || mailbagVOs.isEmpty()){
				actionContext.addError(new ErrorVO(NO_MAILBAGS_PRESENT));
				return;
			}
			mailbagDetailsCollection.addAll(MailCarditModelConverter.constructMailbag(mailbagVOs));	
			selectedMailbagVos.addAll(mailbagVOs);
			
		}
		else{
			for(DSNDetails dsnDetails:dsnDetailsCollection){
				carditEnquiryFilterVO=
						MailOperationsModelConverter.constructCarditFilterVOFromDsn(dsnDetails, logonAttributes);
				carditEnquiryFilterVO.setPol(carditFilter.getAirportCode());
				
				try {
					mailbagVOs=mailTrackingDefaultsDelegate
							.findCarditMails(carditEnquiryFilterVO,1);
				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessageVO().getErrors();
					errors = handleDelegateException(businessDelegateException); 
				}
				if (errors != null && errors.size() > 0) {
		    		actionContext.addAllError((List<ErrorVO>) errors); 
		    	    return;
				}
				if(mailbagVOs==null || mailbagVOs.isEmpty()){
					actionContext.addError(new ErrorVO(NO_DSN_SELECTED));
					return;
				}
				mailbagDetailsCollection.addAll(MailCarditModelConverter.constructMailbag(mailbagVOs));
				selectedMailbagVos.addAll(mailbagVOs);
			}
		}
		
		carditDsnEnquiryModel
				.setMailbagDetailsCollection(mailbagDetailsCollection);
		carditDsnEnquiryModel
			.setMailbagVoCollection(selectedMailbagVos);     
		ArrayList<CarditDsnEnquiryModel> result=
				new ArrayList<>();  
		result.add(carditDsnEnquiryModel);    
		responseVO.setResults(result);  
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO); 
		return;
		
	}

}
