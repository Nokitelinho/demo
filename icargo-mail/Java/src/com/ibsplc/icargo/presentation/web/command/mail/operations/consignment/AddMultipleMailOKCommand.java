package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentMailPopUpVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.consignment.AddMultipleMailOKCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	17-Jul-2017	:	Draft
 */

public class AddMultipleMailOKCommand extends BaseCommand {

	       private Log log = LogFactory.getLogger("MAILOPERATIONS");
		   private static final String MODULE_NAME = "mail.operations";
		   private static final String SCREEN_ID = "mailtracking.defaults.consignment";
		   private static final String TARGET = "success_add";
		   private static final String FAILED = "success_failed";
		   private static final String CHECK_RSN_RANGE = "mailtracking.defaults.consignment.checkRsn";

		   private static final String CHECK_PAGE_LIMIT_ERROR = "mailtracking.defaults.consignment.maximumpagelimit";
		   private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";
		   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";

		/**
		 *
		 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
		 *	Added by 			: A-7531 on 17-Jul-2017
		 * 	Used for 	:
		 *	Parameters	:	@param invocationContext
		 *	Parameters	:	@throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {

 			log.entering("AddMultipleMailOKCommand","execute");
		     ConsignmentForm consignmentForm = (ConsignmentForm)invocationContext.screenModel;
			ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,SCREEN_ID);
			List<MailInConsignmentVO> mailbagVOs=new ArrayList<MailInConsignmentVO>();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();

			
			Map systemParameters = null;  
			SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();
			try {
				systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			
			AreaDelegate areaDelegate = new AreaDelegate();
			Map stationParameters = null; 
		    	String stationCode = logonAttributes.getStationCode();
	    	String companyCode=logonAttributes.getCompanyCode();
	    	try {
				stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
			} catch (BusinessDelegateException e1) {
				
				e1.getMessage();
			}
	    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
				consignmentForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
	    		}
	    		else{
	    			consignmentForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
	    		}
			
			ConsignmentDocumentVO consignmentDocumentVO=consignmentSession.getConsignmentDocumentVO();
			MailInConsignmentVO mailbagVO = new MailInConsignmentVO();
			 String keys=new StringBuilder().append(consignmentForm.getOrginOfficeOfExchange()).append(consignmentForm.getDestOfficeOfExchange()).
					 append(consignmentForm.getMailCategory()).append(consignmentForm.getMailClassType()).append(consignmentForm.getMailYear()).
					 append(consignmentForm.getMailDsn()).append(consignmentForm.getHighestNumberIndicator()).append(consignmentForm.getRegisteredIndicator()).append(consignmentForm
						.getRsnRangeFrom()).append(consignmentForm.getRsnRangeTo()).toString();//Added by a-7871 for ICRD-223492
		//consignmentDocumentVO.getMailInConsignmentVOs();
			 // Added as part of ICRD-341431
			 ConsignmentMailPopUpVO consignmentMailPopUpVO=consignmentSession.getConsignmentMailPopUpVO();
		    String endingRsn=consignmentForm.getRsnRangeTo();
		    String startingRsn=consignmentForm.getRsnRangeFrom();
		    int totalRectls;
		    if(endingRsn!=null &&  endingRsn.trim().length()>0)
		    {
		    if(Integer.parseInt(endingRsn) <= Integer.parseInt(startingRsn))
		    		{
		    	ErrorVO error=new ErrorVO(CHECK_RSN_RANGE);
	 			errors.add(error);
	 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 			invocationContext.addAllError(errors);
				invocationContext.target = FAILED;

		    		}
		    }

			String rsn;
			if((consignmentForm.getTotalReceptacles())>consignmentForm.getMaxPageLimit())
			{
				ErrorVO error=new ErrorVO(CHECK_PAGE_LIMIT_ERROR);
	 			errors.add(error);
	 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 			invocationContext.addAllError(errors);
				invocationContext.target = FAILED;
			}
			if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){
				if(consignmentForm.getMailOpFlag() != null
					&& !consignmentDocumentVO.getMailInConsignmentVOs().isEmpty() && consignmentForm.getMailOpFlag().length > 0){
				String[] opFlag = consignmentForm.getMailOpFlag();
				int i=0;
				for(MailInConsignmentVO mailVO : consignmentDocumentVO.getMailInConsignmentVOs()){
					if(!MailInConsignmentVO.OPERATION_FLAG_DELETE.equals(opFlag[i++])){
						mailbagVOs.add(mailVO);
					}
				}
			}else{
			mailbagVOs.addAll(consignmentDocumentVO.getMailInConsignmentVOs());
			}
			}
			//Modified as part of ICRD-341431
			if(consignmentSession.getMultipleMailDetailsMap()!=null && consignmentSession.getConsignmentMailPopUpVO() !=null && consignmentMailPopUpVO.getRsnRangeFrom().equals(startingRsn) ){//added by A-7371
				 populateMailDetails(consignmentSession,mailbagVO,mailbagVOs);
			}

			if(endingRsn==null||endingRsn.isEmpty())
			{
				if(consignmentSession.getMultipleMailDetailsMap()!=null && consignmentSession.getMultipleMailDetailsMap().keySet().contains(keys))//Added by a-7871 for ICRD-223492
				{
				totalRectls=0;
				}
				else
				{
					totalRectls=1;
				}
	            consignmentForm.setTotalReceptacles(totalRectls);
			}
			else
			{
				if(consignmentSession.getMultipleMailDetailsMap()!=null && consignmentSession.getMultipleMailDetailsMap().keySet().contains(keys))
				{
					totalRectls=0;

				}
				else
				{
				 totalRectls=(Integer.parseInt(endingRsn)-Integer.parseInt(startingRsn))+1;
				}
				 consignmentForm.setTotalReceptacles(totalRectls);
			}

			for(int i=0;i<totalRectls;i++){
				mailbagVO=new MailInConsignmentVO();


				rsn = String.valueOf(Integer.parseInt(startingRsn)+i);
			 StringBuilder finalRsn = new StringBuilder();
				if(rsn.length()==0){
					finalRsn = finalRsn.append("000");
				}
				else if(rsn.length()==1){
					finalRsn = finalRsn.append("00").append(rsn);
				}
				else if(rsn.length()==2){
					finalRsn.append("0").append(rsn);
				}
				else if(rsn.length()==3){//Added by a-7871 for ICRD-223492
					finalRsn.append(rsn);
				}

			 mailbagVO.setReceptacleSerialNumber(String.valueOf(finalRsn));
		     mailbagVO.setOriginExchangeOffice(consignmentForm.getOrginOfficeOfExchange().toUpperCase());
		     mailbagVO.setDestinationExchangeOffice(consignmentForm.getDestOfficeOfExchange().toUpperCase());
		     mailbagVO.setMailCategoryCode(consignmentForm.getMailCategory());
		     mailbagVO.setMailClass(consignmentForm.getMailClassType());
		     mailbagVO.setMailSubclass(consignmentForm.getMailSubClass().toUpperCase());
		     mailbagVO.setYear(Integer.parseInt(consignmentForm.getMailYear()));
		     mailbagVO.setDsn(consignmentForm.getMailDsn());
		     mailbagVO.setHighestNumberedReceptacle(consignmentForm.getHighestNumberIndicator());
		     mailbagVO.setRegisteredOrInsuredIndicator(consignmentForm.getRegisteredIndicator());
		     mailbagVO.setStatedBags(1);//Added by A-5219 for ICRD-255265
		     mailbagVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_INSERT);
		   	mailbagVOs.add(mailbagVO);

			}
		   	Page<MailInConsignmentVO> newmailbagVO= new Page<MailInConsignmentVO>(mailbagVOs,0,0,0,0,0,false);

			//consignmentDocumentVO.setMailInConsignment(newmailbagVO);
			consignmentDocumentVO.setMailInConsignmentVOs(newmailbagVO);
			consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
			 invocationContext.target = TARGET;
		        log.exiting("AddMultipleMailOKCommand","execute");
		}

		/**
		 * 	Method		:	AddMultipleMailOKCommand.populateMailDetails
		 *	Added by 	:	A-7371 on 21-Sep-2017
		 * 	Used for 	:
		 *	Parameters	:
		 *	Return type	: 	void
		 */
		private void populateMailDetails(ConsignmentSession consignmentSession,MailInConsignmentVO mailbagVO,List<MailInConsignmentVO> mailbagVOs) {
			// TODO Auto-generated method stub
			int consignmentTotalRec;
			for(ConsignmentMailPopUpVO consignmentVO:consignmentSession.getMultipleMailDetailsMap().values()){

				if(consignmentVO.getRsnRangeTo()!=null && consignmentVO.getRsnRangeTo().trim().length()>0)//Modified by a-7871 for ICRD-223492, moved logic from MailDetailsNewCommand
				{
					consignmentTotalRec=Integer.parseInt(consignmentVO.getRsnRangeTo())-Integer.parseInt(consignmentVO.getRsnRangeFrom());
				}
				else
				{
					consignmentTotalRec=Integer.parseInt(consignmentVO.getRsnRangeFrom())-Integer.parseInt(consignmentVO.getRsnRangeFrom());
				}
				if(consignmentTotalRec==0){//entries with only RsnRangeFrom hence only single entry,written to handle business need for ICRD-221978
					mailbagVO=new MailInConsignmentVO();
					 mailbagVO.setReceptacleSerialNumber(consignmentVO.getRsnRangeFrom());
				     mailbagVO.setOriginExchangeOffice(consignmentVO.getOrginOfficeOfExchange().toUpperCase());
				     mailbagVO.setDestinationExchangeOffice(consignmentVO.getDestOfficeOfExchange().toUpperCase());
				     mailbagVO.setMailCategoryCode(consignmentVO.getMailCategory());
				     mailbagVO.setMailClass(consignmentVO.getMailClassType());
				     mailbagVO.setMailSubclass(consignmentVO.getMailSubClass().toUpperCase());
				     mailbagVO.setYear(Integer.parseInt(consignmentVO.getMailYear()));
				     mailbagVO.setDsn(consignmentVO.getMailDsn());
				     mailbagVO.setStatedBags(1);//Added by A-5219 for ICRD-255265
				     mailbagVO.setHighestNumberedReceptacle(consignmentVO.getHighestNumberIndicator());
				     mailbagVO.setRegisteredOrInsuredIndicator(consignmentVO.getRegisteredIndicator());
				     mailbagVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_INSERT);

				   	mailbagVOs.add(mailbagVO);
				}else{// for range of RSN entries
					 String consignmentstartingRsn=consignmentVO.getRsnRangeFrom();
					 String consignmentRsn;
					for(int i=0;i<=consignmentTotalRec;i++){

						mailbagVO=new MailInConsignmentVO();
						consignmentRsn = String.valueOf(Integer.parseInt(consignmentstartingRsn)+i);
					 StringBuilder consignmentFinalRsn = new StringBuilder();
						if(consignmentRsn.length()==0){
							consignmentFinalRsn = consignmentFinalRsn.append("000");
						}
						else if(consignmentRsn.length()==1){
							consignmentFinalRsn = consignmentFinalRsn.append("00").append(consignmentRsn);
						}
						else if(consignmentRsn.length()==2){
							consignmentFinalRsn.append("0").append(consignmentRsn);
						}
						else if(consignmentRsn.length()==3){//Added by a-7871 for ICRD-223492
							consignmentFinalRsn.append(consignmentRsn);
						}
						 mailbagVO.setReceptacleSerialNumber(String.valueOf(consignmentFinalRsn));
					     mailbagVO.setOriginExchangeOffice(consignmentVO.getOrginOfficeOfExchange().toUpperCase());
					     mailbagVO.setDestinationExchangeOffice(consignmentVO.getDestOfficeOfExchange().toUpperCase());
					     mailbagVO.setMailCategoryCode(consignmentVO.getMailCategory());
					     mailbagVO.setMailClass(consignmentVO.getMailClassType());
					     mailbagVO.setMailSubclass(consignmentVO.getMailSubClass().toUpperCase());
					     mailbagVO.setStatedBags(1);//Added by A-5219 for ICRD-255265
					     mailbagVO.setYear(Integer.parseInt(consignmentVO.getMailYear()));
					     mailbagVO.setDsn(consignmentVO.getMailDsn());
					     mailbagVO.setHighestNumberedReceptacle(consignmentVO.getHighestNumberIndicator());
					     mailbagVO.setRegisteredOrInsuredIndicator(consignmentVO.getRegisteredIndicator());
					     mailbagVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_INSERT);

					   	mailbagVOs.add(mailbagVO);


					}

				}


			}

		}

		/**
		 * added by A-8353
		 * @return systemParameterCodes
		 */
		private Collection<String> getSystemParameterCodes(){
			  Collection systemParameterCodes = new ArrayList();
			    systemParameterCodes.add("mail.operations.defaultcaptureunit");
			    return systemParameterCodes;
		  }
		 /**
		 * added by A-8353
		 * @return stationParameterCodes
		 */
	  private Collection<String> getStationParameterCodes()
	  {
	    Collection stationParameterCodes = new ArrayList();
	    stationParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
	    return stationParameterCodes;
	  }

	}


