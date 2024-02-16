package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DiscrepancyDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.LoadDiscrepancyCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	24-Oct-2018		:	Draft
 */
public class LoadDiscrepancyCommand extends AbstractCommand  {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS LoadDiscrepancyCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("LoadDiscrepancyCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();

		ArrayList<DiscrepancyDetails> discrepancyDetails=null;
		
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVO=null;
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		
		Collection<MailDiscrepancyVO> mailDiscrepancyVOs = null;
		if(operationalFlightVO != null) {
			operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
			try {
				mailDiscrepancyVOs = 
					new MailTrackingDefaultsDelegate().findMailDiscrepancies(
							operationalFlightVO);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
		}
		if(mailDiscrepancyVOs.size()>0){
			discrepancyDetails=populateDiscrepancyDetails(mailDiscrepancyVOs);
			mailinboundModel.setDiscrepancyDetailsCollection(discrepancyDetails);
		}
		
		
		ArrayList<MailinboundModel> mailinboundModels=
				new ArrayList<MailinboundModel>();
		mailinboundModels.add(mailinboundModel);
		ResponseVO responseVO = new ResponseVO();	  
	    responseVO.setStatus("success");
	    responseVO.setResults(mailinboundModels);
	    
	    actionContext.setResponseVO(responseVO);  
		log.exiting("LoadDiscrepancyCommand","execute");
	}

	private ArrayList<DiscrepancyDetails> populateDiscrepancyDetails(Collection<MailDiscrepancyVO> mailDiscrepancyVOs) {
		
		ArrayList<DiscrepancyDetails> discrepancyDetailsCollection=
				new ArrayList<DiscrepancyDetails>();
		for(MailDiscrepancyVO mailDiscrepancyVO:mailDiscrepancyVOs){
			DiscrepancyDetails discrepancyDetails=
					new DiscrepancyDetails();
			discrepancyDetails.setCompanyCode(mailDiscrepancyVO.getCompanyCode());
			discrepancyDetails.setDiscrepancyType(mailDiscrepancyVO.getDiscrepancyType());
			discrepancyDetails.setMailIdentifier(mailDiscrepancyVO.getMailIdentifier());
			discrepancyDetails.setUldNumber(mailDiscrepancyVO.getUldNumber());
			discrepancyDetailsCollection.add(discrepancyDetails);
		}
		
		return discrepancyDetailsCollection;
		
	}

}
