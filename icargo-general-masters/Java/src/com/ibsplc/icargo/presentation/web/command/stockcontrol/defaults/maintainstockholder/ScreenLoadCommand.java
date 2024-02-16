/*
 * ScreenLoadCommand.java Created on May 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidityDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1885
 *
 */
public class ScreenLoadCommand extends BaseCommand{
	//private static String COMPANY_CODE;
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String SAVE_MODE = "save";
	private static final String NULL_STRING="";
	private static final String OPERATION_FLAG_INSERT = "I";
	//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix starts
	private static final String CONTROL_PRIVILEGE_ALL = "All";
	private static final String DEFAULT_DOCTYPE = "AWB";
	private static final String DEFAULT_DOCSUBTYPE = "S";
	private static final String STOCK_APPROVER_CODE="approver";
	//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix ends
	private static final String STOCKHOLDER_TYPE_AGENT = "A";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		    ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();			
		    /**
			 * Added by A-4772 for ICRD-9882.Changed the 
			 * Screen id value as per standard for UISK009
			 */
		    MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
			session.setStockVO(null);
			//added by A-5174 for BUG ICRD-32653
			session.setStockApprovercode(STOCK_APPROVER_CODE);
			//Added by A-7364 as part of ICRD-217145
			MessageInboxSession messageInboxSession = 
					(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
			try {
				//Added as part of bug ICRD-1242 by A-3767 on 27Apr11
				List<AirlineLovVO> ownerAirlinesList=new ArrayList<AirlineLovVO>();
				Collection<String> systemParameterCodes=new ArrayList<String>();
				systemParameterCodes.add("logonattributes.ownairlineidentifier");
				Map<String, String> parameters=new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
				if(parameters!=null && parameters.size()>0){
					AirlineVO ownerAirline = new AirlineDelegate()
							.findAirlineDetails(
									logonAttributes.getCompanyCode(),
									Integer.valueOf(parameters
 											.get("logonattributes.ownairlineidentifier")));
					AirlineLovVO ownerAirlineVO=new AirlineLovVO();
					ownerAirlineVO.setCompanyCode(ownerAirline.getCompanyCode());
					ownerAirlineVO.setAirlineCode(ownerAirline.getAirlineCode());
					ownerAirlineVO.setAirlineIdentifier(ownerAirline.getAirlineIdentifier());
					ownerAirlineVO.setAirlineName(ownerAirline.getAirlineName());
	
					//added by A-4501 as part of ICRD-30728 starts
					LocalDate localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
	        				true);     
					
					if(ownerAirline.getAirlineValidityDetailsVOs() != null && 
							ownerAirline.getAirlineValidityDetailsVOs().size() > 0 ) {
			    		for(AirlineValidityDetailsVO airlineValidityDetailsVO : ownerAirline.getAirlineValidityDetailsVOs()) {
			    				if((localDate.toGMTDate()).compareTo(airlineValidityDetailsVO.getValidFromDate().toGMTDate()) >= 0 
			    						&& (localDate.toGMTDate()).compareTo(airlineValidityDetailsVO.getValidTillDate().toGMTDate()) <= 0 ) {
			    						if(airlineValidityDetailsVO.getNumberCodeUsed() == 3){
			    							ownerAirline.setNumericCode(airlineValidityDetailsVO.getThreeNumberCode());
			    						}
			    						else {
			    							ownerAirline.setNumericCode(airlineValidityDetailsVO.getFourNumberCode());
			    						}
			    					break;
			    				}
			    		}
			    	}
					ownerAirlineVO.setAirlineNumber(ownerAirline.getNumericCode());
					//added by A-4501 as part of ICRD-30728 ends					
					
					ownerAirlinesList.add(ownerAirlineVO);
					session.setPartnerAirlines(new Page<AirlineLovVO>(ownerAirlinesList, 0, 0, 0, 0, 0, false));
					//session.getPartnerAirlines().add(ownerAirlineVO);
				}
				if(ownerAirlinesList==null || ownerAirlinesList.size()==0){
					ownerAirlinesList.add(new AirlineLovVO());
					session.setPartnerAirlines(new Page<AirlineLovVO>(ownerAirlinesList, 0, 0, 0, 0, 0, false));
				}
				//bug ICRD-1242 by A-3767 on 27Apr11 ends
				loadSessionWithPartnerAirlines(session);
			} catch (BusinessDelegateException e) {				
//printStackTrrace()();
			}
			StockVO stockVO=new StockVO();
			stockVO.setReorderLevel(0);
			stockVO.setReorderQuantity(0);
			stockVO.setAutoprocessQuantity(0);
			stockVO.setRemarks("");
			//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix starts
			stockVO.setDocumentSubType(DEFAULT_DOCSUBTYPE);
			stockVO.setDocumentType(DEFAULT_DOCTYPE);
			//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix ends
			stockVO.setStockApproverCode("");
			stockVO.setReorderAlertFlag(false);
			stockVO.setAutoRequestFlag(false);
			stockVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
			stockVO.setOperationFlag(OPERATION_FLAG_INSERT);
			Collection<StockVO> stockVo=new ArrayList<StockVO>();
			stockVo.add(stockVO);
			session.setStockVO(stockVo);
			MaintainStockHolderForm maintainStockHolderForm=(MaintainStockHolderForm)invocationContext.screenModel;
			maintainStockHolderForm.setStockHolderType(NULL_STRING);
			maintainStockHolderForm.setStockHolderCode(NULL_STRING);
			maintainStockHolderForm.setStockHolderName(NULL_STRING);
			maintainStockHolderForm.setControlPrivilege(NULL_STRING);
			//Added by A-7364 as part of ICRD-217145 starts
			if(messageInboxSession.getMessageDetails() != null){
				maintainStockHolderForm.setStockHolderType(STOCKHOLDER_TYPE_AGENT);
				if(messageInboxSession.getParameterMap().get("AGTCOD")!=null)
					{
					maintainStockHolderForm.setStockHolderCode(messageInboxSession.getParameterMap().get("AGTCOD"));
					}
				if(messageInboxSession.getParameterMap().get("AGTNAM")!=null)
					{
					maintainStockHolderForm.setStockHolderName(messageInboxSession.getParameterMap().get("AGTNAM"));
					}
			}
			//Added by A-7364 as part of ICRD-217145 ends
			session.setMode(SAVE_MODE);
			Map<String, Collection<OneTimeVO>>  oneTimes=getScreenLoadDetails(logonAttributes.getCompanyCode());
			//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix starts
			maintainStockHolderForm.setControlPrivilege(CONTROL_PRIVILEGE_ALL);
			//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix ends
			HashMap<String,Collection<String>> documentList = null;
			try{
			 documentList =new HashMap<String,Collection<String>>(new DocumentTypeDelegate().
					 findAllDocuments(getApplicationSession().getLogonVO().getCompanyCode()));
			//documentList.put("",null);

			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}

			session.setMap(documentList); //set in session

			if(oneTimes!=null){
			Collection<OneTimeVO> stockHolderTypes=oneTimes.get("stockcontrol.defaults.stockholdertypes");
//			Setting stock holders priority
			try{
			Collection<StockHolderPriorityVO> stockHolderpriorityVos =
				 new StockControlDefaultsDelegate().findStockHolderTypes(logonAttributes.getCompanyCode());

			populatePriorityStockHolders(stockHolderpriorityVos,stockHolderTypes,session);
			log.log(Log.FINE,
					"------------------stockHolderpriorityVos-----------",
					stockHolderpriorityVos);
			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}
			session.setStockHolderType(stockHolderTypes);

			}

			invocationContext.target = "screenload_success";
		}


	/**
	 * Added for #102543 Base product enhancement
	 * @author A-2589
	 * @param session
	 * @throws BusinessDelegateException
	 * 	modified as part of bug ICRD-1242 by A-3767 on 27Apr11
	 */
	private void loadSessionWithPartnerAirlines(MaintainStockHolderSession session) throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO=new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		Page<AirlineLovVO> findAirlineLov=new AirlineDelegate().findAirlineLov(airlineLovFilterVO, 1);
		if(findAirlineLov !=null &&	findAirlineLov.size()>0){
			session.getPartnerAirlines().addAll(findAirlineLov); 
		}
	}



	/**
     * This method will be invoked at the time of screen load
     * @param companyCode
     * @return
     */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try{
		Collection<String> fieldValues = new ArrayList<String>();
		fieldValues.add("stockcontrol.defaults.stockholdertypes");


		 oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

			}catch(BusinessDelegateException be){
				log.log(Log.FINE,"\n\n<---------Inside BusinessDelegateException---------->\n\n");
//printStackTrrace()();
			}
	        return oneTimes;
	    }
	private void populatePriorityStockHolders(
			Collection<StockHolderPriorityVO> stockHolderpriorityVos,
			Collection<OneTimeVO> stockHolder,MaintainStockHolderSession session){
		log.entering("ScreenLoadCommand","populatePriorityStockHolders");
		if(stockHolderpriorityVos!=null){
			for(StockHolderPriorityVO priorityVO : stockHolderpriorityVos){
				for(OneTimeVO onetime : stockHolder){
					if(onetime.getFieldValue().equals(priorityVO.getStockHolderType())){
						priorityVO.setStockHolderDescription(onetime.getFieldDescription());
					}
				}
			}
			session.setPrioritizedStockHolders(stockHolderpriorityVos);
			log.exiting("ScreenLoadCommand","populatePriorityStockHolders");
		}
	}

}
