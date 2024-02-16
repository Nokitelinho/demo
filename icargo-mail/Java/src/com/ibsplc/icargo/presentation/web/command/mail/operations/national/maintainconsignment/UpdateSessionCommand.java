/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-3817
 *
 */
public class UpdateSessionCommand extends BaseCommand {
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String TARGET = "success";
	private static final String OUTBOUND = "O";
	private static final String FAILURE = "failure";

	@Override
	public boolean breakOnInvocationFailure() {

		return true;
	}


	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		CaptureMailDocumentForm consignmentForm = (CaptureMailDocumentForm) invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<RoutingInConsignmentVO> routingInConsignmentVOs = null;
		consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailInConsignmentVOs = null;
		MailInConsignmentVO mailInConsignmentVO = null;
		Collection<RoutingInConsignmentVO> newRoutingInConsignmentVOs = new ArrayList<RoutingInConsignmentVO>();
		LogonAttributes  logonAttributes = getApplicationSession().getLogonVO();
		List<RoutingInConsignmentVO> acceptanceConsignmentVOs = null;
		Collection<RoutingInConsignmentVO> newacceptanceConsignmentVOs  = new ArrayList<RoutingInConsignmentVO>();


		/**
		 * Code for updating routing part starts
		 */
		String[] operationFlag = consignmentForm.getOperationFlag();
		String[] flightCarrierCode = consignmentForm.getFlightCarrierCode();
		String[] flightNumber = consignmentForm.getFlightNumber();
		String[] flightDate = consignmentForm.getFlightDate();
		String[] pol = consignmentForm.getPol();
		String[] pou = consignmentForm.getPou();
		String[] noOfPcs = consignmentForm.getNoOfPcs();
		String[] weight = consignmentForm.getWeight();
		String[] invalidFlightFlag = consignmentForm.getInvalidFlightFlag();
		String[] accpRemarks = consignmentForm.getAccpRemarks();
		String[] rtgRemarks = consignmentForm.getRtgRemarks();

		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		String[] acceptanceOperationFlag = consignmentForm.getAcceptanceOperationFlag();
		boolean isUpdateNeeded = checkIfUpdationIsNeeded(operationFlag,acceptanceOperationFlag);
		Collection<RoutingInConsignmentVO> newVOsToRemoveBeforeUpdate = new ArrayList<RoutingInConsignmentVO>();
		Collection<RoutingInConsignmentVO> routingVOsToRemoveBeforeUpdate = new ArrayList<RoutingInConsignmentVO>();
		if(!isUpdateNeeded){
			//modified by a-4810 for icrd-17304
			if(consignmentDocumentVO!=null){
				if(consignmentDocumentVO.getAcceptanceInfo()!=null){
				for(RoutingInConsignmentVO acceptanceInfoVO:consignmentDocumentVO.getAcceptanceInfo()){
					if(!"N".equals(acceptanceInfoVO.getOperationFlag())){
						
						newVOsToRemoveBeforeUpdate.add(acceptanceInfoVO);
						//consignmentDocumentVO.getAcceptanceInfo().remove(acceptanceInfoVO);
					}
				}
				if(newVOsToRemoveBeforeUpdate!=null && newVOsToRemoveBeforeUpdate.size()>0){
				consignmentDocumentVO.getAcceptanceInfo().removeAll(newVOsToRemoveBeforeUpdate);
				}
			  }
				//added
				if(consignmentDocumentVO.getRoutingInConsignmentVOs()!=null){
					//

					for(RoutingInConsignmentVO routingInfoVO:consignmentDocumentVO.getRoutingInConsignmentVOs()){
						if(!"N".equals(routingInfoVO.getOperationFlag())){
							
							routingVOsToRemoveBeforeUpdate.add(routingInfoVO);
							//consignmentDocumentVO.getAcceptanceInfo().remove(acceptanceInfoVO);
						}
					}
					if(routingVOsToRemoveBeforeUpdate!=null && routingVOsToRemoveBeforeUpdate.size()>0){
					consignmentDocumentVO.getRoutingInConsignmentVOs().removeAll(routingVOsToRemoveBeforeUpdate);
					}
				  
					
					
					
					
					
				}
			
				
				}
			
			
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.noupdateneeded");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errorVOs.add(errorVO);
			invocationContext.addAllError(errorVOs);
			 consignmentForm.setNoconDocNo("");
			 consignmentForm.setConsignmentOriginFlag("");
			invocationContext.target = TARGET;
			return;
		}
		
			
		   


		if (consignmentDocumentVO == null) {
			consignmentDocumentVO = new ConsignmentDocumentVO();
			consignmentDocumentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			consignmentDocumentVO.setOperation(OUTBOUND);
			consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
			//routingInConsignmentVOs = new ArrayList<RoutingInConsignmentVO>();
			consignmentDocumentVO.setConsignmentNumber(consignmentForm
					.getConDocNo());
			consignmentDocumentVO.setMailCategory(consignmentForm
					.getMailCategory());
			if(consignmentForm.getOrigin() != null|| consignmentForm.getOrigin().trim().length() >0){
				consignmentDocumentVO.setOrgin(consignmentForm.getOrigin());
			}
			if(consignmentForm.getDestination()!= null && consignmentForm.getDestination().trim().length() >0){
				consignmentDocumentVO.setDestination(consignmentForm
						.getDestination());
			}
			//Added as part of bug-fix -icrd-12637 by A-4810
			if(consignmentForm.getConDocNo()!=null && consignmentForm.getConDocNo().trim().length()>0)
			{
			 consignmentDocumentVO.setConsignmentNumber(consignmentForm.getConDocNo());
			}
			consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			consignmentDocumentVO.setAirportCode(logonAttributes.getAirportCode());


			mailInConsignmentVO = new MailInConsignmentVO();
			mailInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
			mailInConsignmentVO
					.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			mailInConsignmentVOs = new Page<MailInConsignmentVO>(
					new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
			mailInConsignmentVOs.add(mailInConsignmentVO);
			mailInConsignmentVO
					.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);

			//consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);

			//consignmentDocumentVO.setAcceptanceInfo(newacceptanceConsignmentVOs);

		} else {
			//commented by A-4810 for icrd-18334 for validation while updation of details 
			//validateConsignmentDetailsForClosedFlight(consignmentDocumentVO, errorVOs);
			if(errorVOs != null && errorVOs.size() >0){
				invocationContext.addAllError(errorVOs);
				 consignmentForm.setNoconDocNo("");
				 consignmentForm.setConsignmentOriginFlag("");
				invocationContext.target = TARGET;
				return;
			}


			routingInConsignmentVOs = (List<RoutingInConsignmentVO>) consignmentDocumentVO
					.getRoutingInConsignmentVOs();
			mailInConsignmentVO =  consignmentDocumentVO
					.getMailInConsignmentVOs().get(0);
			acceptanceConsignmentVOs = (List<RoutingInConsignmentVO>)consignmentDocumentVO.getAcceptanceInfo();
		}
		if(consignmentForm.getOrigin() != null && consignmentForm.getOrigin().trim().length() >0){
			consignmentDocumentVO.setOrgin(consignmentForm.getOrigin());
		}
		if(consignmentForm.getDestination() != null && consignmentForm.getDestination().trim().length() >0){
			consignmentDocumentVO.setDestination(consignmentForm.getDestination());
		}

		if(consignmentForm.getRemarks() != null && consignmentForm.getRemarks().trim().length() >0){
			consignmentDocumentVO.setRemarks(consignmentForm.getRemarks());

		}


		RoutingInConsignmentVO routingInConsignmentVO = null;
		Collection<RoutingInConsignmentVO> newRoutingVOsToRemove = new ArrayList<RoutingInConsignmentVO>();
		int numberOfPcs = mailInConsignmentVO.getStatedBags();
		//double mailWeight = mailInConsignmentVO.getStatedWeight();
		double mailWeight = mailInConsignmentVO.getStatedWeight().getRoundedSystemValue();//added by A-7371
		//consignmentDocumentVO.setAcceptanceInfo(null);
		//consignmentDocumentVO.setRoutingInConsignmentVOs(null);
		//modified by a-4810 for icrd-17304
		if (operationFlag != null && operationFlag.length > 0) {
			for (int i = 0; i < operationFlag.length-1; i++) {
				//if (!"NOOP".equals(operationFlag[i])) {
					if (ConsignmentDocumentVO.OPERATION_FLAG_INSERT
							.equals(operationFlag[i])&& !ConsignmentDocumentVO.FLAG_YES.equals(invalidFlightFlag[i])) {
						routingInConsignmentVO = new RoutingInConsignmentVO();
						routingInConsignmentVO
								.setOnwardCarrierCode(flightCarrierCode[i]);
						routingInConsignmentVO
								.setOnwardFlightNumber(flightNumber[i]);
						routingInConsignmentVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
						if (flightDate[i] != null
								&& flightDate[i].trim().length() > 0) {
							routingInConsignmentVO
									.setOnwardFlightDate(new LocalDate(
											LocalDate.NO_STATION,
											Location.NONE, false)
											.setDate(flightDate[i]));
						}
						//if (pol[i] != null && pol[i].trim().length() > 0) {
							routingInConsignmentVO.setPol(pol[i]);
						//}
						//if (pou[i] != null && pou[i].trim().length() > 0) {
							routingInConsignmentVO.setPou(pou[i]);
						//}
						if (noOfPcs[i] != null
								&& noOfPcs[i].trim().length() > 0) {
							routingInConsignmentVO.setNoOfPieces(Integer
									.parseInt(noOfPcs[i]));
						}
						if (weight[i] != null && weight[i].trim().length() > 0) {
							/*routingInConsignmentVO.setWeight(Double
									.parseDouble(weight[i]));*/
							routingInConsignmentVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double
									.parseDouble(weight[i])));//added by A-7371
						}
						routingInConsignmentVO.setRemarks(rtgRemarks[i]);
						routingInConsignmentVO
								.setOperationFlag(operationFlag[i]);
						routingInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
						routingInConsignmentVO.setPaCode(consignmentDocumentVO.getPaCode());
						numberOfPcs = numberOfPcs
								+ routingInConsignmentVO.getNoOfPieces();
						/*mailWeight = mailWeight
								+ routingInConsignmentVO.getWeight();*/
						mailWeight = mailWeight
								+ routingInConsignmentVO.getWeight().getRoundedSystemValue();//added by A-7371
						newRoutingInConsignmentVOs.add(routingInConsignmentVO);

					}else if(ConsignmentDocumentVO.OPERATION_FLAG_INSERT
							.equals(operationFlag[i])){
						routingInConsignmentVO = routingInConsignmentVOs.get(i);
						routingInConsignmentVO
						.setOnwardCarrierCode(flightCarrierCode[i]);
						routingInConsignmentVO
						.setOnwardFlightNumber(flightNumber[i]);
						routingInConsignmentVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
						if (flightDate[i] != null
								&& flightDate[i].trim().length() > 0) {
							routingInConsignmentVO
							.setOnwardFlightDate(new LocalDate(
									LocalDate.NO_STATION,
									Location.NONE, false)
							.setDate(flightDate[i]));
						}
						//if (pol[i] != null && pol[i].trim().length() > 0) {
						routingInConsignmentVO.setPol(pol[i]);
						//}
						//if (pou[i] != null && pou[i].trim().length() > 0) {
						routingInConsignmentVO.setPou(pou[i]);
						//}
						if (noOfPcs[i] != null
								&& noOfPcs[i].trim().length() > 0) {
							routingInConsignmentVO.setNoOfPieces(Integer
									.parseInt(noOfPcs[i]));
						}
						if (weight[i] != null && weight[i].trim().length() > 0) {
							/*routingInConsignmentVO.setWeight(Double
									.parseDouble(weight[i]));*/
							routingInConsignmentVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double
									.parseDouble(weight[i])));
						}
						routingInConsignmentVO.setRemarks(rtgRemarks[i]);
						routingInConsignmentVO
						.setOperationFlag(operationFlag[i]);
						routingInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
						routingInConsignmentVO.setPaCode(consignmentDocumentVO.getPaCode());


					} else if (ConsignmentDocumentVO.OPERATION_FLAG_DELETE
									.equals(operationFlag[i])) {
						routingInConsignmentVO = routingInConsignmentVOs.get(i);
						routingInConsignmentVO
								.setOperationFlag(operationFlag[i]);
						numberOfPcs = numberOfPcs
								- routingInConsignmentVO.getNoOfPieces();
						/*mailWeight = mailWeight
								- routingInConsignmentVO.getWeight();*/
						mailWeight = mailWeight
								- routingInConsignmentVO.getWeight().getRoundedSystemValue();//added by A-7371

					} else if (ConsignmentDocumentVO.OPERATION_FLAG_UPDATE
							.equals(operationFlag[i])) {
						routingInConsignmentVO = routingInConsignmentVOs.get(i);
						if (noOfPcs[i] != null
								&& noOfPcs[i].trim().length() > 0) {
							routingInConsignmentVO.setNoOfPieces(Integer
									.parseInt(noOfPcs[i]));
						}
						if (weight[i] != null && weight[i].trim().length() > 0) {
							/*routingInConsignmentVO.setWeight(Double
									.parseDouble(weight[i]));*/
							routingInConsignmentVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double
									.parseDouble(weight[i])));//added by A-7371
							
						}
						routingInConsignmentVO
								.setOperationFlag(operationFlag[i]);
						numberOfPcs = numberOfPcs
								+ routingInConsignmentVO.getNoOfPieces();
						/*mailWeight = mailWeight
								+ routingInConsignmentVO.getWeight();*/
						mailWeight = mailWeight
								+ routingInConsignmentVO.getWeight().getRoundedSystemValue();//added by A-7371
						routingInConsignmentVO.setRemarks(rtgRemarks[i]);
					}
					else if ( "NOOP".equals(operationFlag[i]) &&  (routingInConsignmentVOs != null && routingInConsignmentVOs.size()!=0) ){
						RoutingInConsignmentVO routingVoToRemove = new RoutingInConsignmentVO() ;
						routingVoToRemove = routingInConsignmentVOs.get(i);
						newRoutingVOsToRemove.add(routingVoToRemove);
						numberOfPcs = numberOfPcs - routingVoToRemove.getNoOfPieces();
						//mailWeight = mailWeight- routingVoToRemove.getWeight();
						mailWeight = mailWeight- routingVoToRemove.getWeight().getRoundedSystemValue();//added by A-7371
						
						}
					

				}

				//}

			if(consignmentDocumentVO.getRoutingInConsignmentVOs() == null){
				consignmentDocumentVO.setRoutingInConsignmentVOs(new ArrayList<RoutingInConsignmentVO>());
			}
			else{
				if(newRoutingVOsToRemove !=null && newRoutingVOsToRemove.size()>0){
					//modified for icrd-17034 by a-4810
					//consignmentDocumentVO.getAcceptanceInfo().removeAll(newRoutingVOsToRemove);
				   	consignmentDocumentVO.getRoutingInConsignmentVOs().removeAll(newRoutingVOsToRemove);
				 }
			    }
			
			if (newRoutingInConsignmentVOs != null
					&& newRoutingInConsignmentVOs.size() > 0) {
				consignmentDocumentVO.getRoutingInConsignmentVOs().addAll(
						newRoutingInConsignmentVOs);
			}


//			mailInConsignmentVO.setStatedBags(numberOfPcs);
//			mailInConsignmentVO.setStatedWeight(mailWeight);
//
//			consignmentDocumentVO.setStatedBags(numberOfPcs);
//			consignmentDocumentVO.setStatedWeight(mailWeight);


		}

		/**
		 * Code for updating routing part ends
		 */

		/**
		 * Code for updating acceptance part starts
		 */


		RoutingInConsignmentVO acceptanceConsignmentVO = null;
		Collection<RoutingInConsignmentVO> newVOsToRemove = new ArrayList<RoutingInConsignmentVO>();
		String[] acceptanceFlightCarrierCode = consignmentForm.getAcceptanceCarrierCode();
		String[] acceptanceFlightNumber = consignmentForm.getAcceptanceFlightNumber();
		String[] acceptanceFlightDate  = consignmentForm.getAcceptanceFlightDate();
		String[] acceptancePol = consignmentForm.getAcceptancePol();
		String[] acceptancePou = consignmentForm.getAcceptancePou();
		String[] acceptanceNoOfPcs = consignmentForm.getAcceptancePieces();
		String[] acceptanceWeight = consignmentForm.getAcceptanceWeight();
		String[] invalidAcceptanceFlightFlag = consignmentForm.getInvalidAcceptanceFlightFlag();
		//modified by a-4810 for icrd-17304
		if (acceptanceOperationFlag != null && acceptanceOperationFlag.length > 0) {
			for (int i = 0; i < acceptanceOperationFlag.length-1; i++) {
				//if (!"NOOP".equals(acceptanceOperationFlag[i])) {
					if (ConsignmentDocumentVO.OPERATION_FLAG_INSERT
							.equals(acceptanceOperationFlag[i]) && !ConsignmentDocumentVO.FLAG_YES.equals(invalidAcceptanceFlightFlag[i])) {
						acceptanceConsignmentVO = new RoutingInConsignmentVO();
						acceptanceConsignmentVO
						.setOnwardCarrierCode(acceptanceFlightCarrierCode[i]);
						acceptanceConsignmentVO
						.setOnwardFlightNumber(acceptanceFlightNumber[i]);
						acceptanceConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
						acceptanceConsignmentVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
						acceptanceConsignmentVO.setPaCode(consignmentDocumentVO.getPaCode());
						if (acceptanceFlightDate[i] != null
								&& acceptanceFlightDate[i].trim().length() > 0) {
							acceptanceConsignmentVO
							.setOnwardFlightDate(new LocalDate(
									LocalDate.NO_STATION,
									Location.NONE, false)
							.setDate(acceptanceFlightDate[i]));
						}
						//if (acceptancePol[i] != null && acceptancePol[i].trim().length() > 0) {
						acceptanceConsignmentVO.setPol(acceptancePol[i]);
						//}
						//if (acceptancePou[i] != null && acceptancePou[i].trim().length() > 0) {
						acceptanceConsignmentVO.setPou(acceptancePou[i]);
						//}
						if (acceptanceNoOfPcs[i] != null
								&& acceptanceNoOfPcs[i].trim().length() > 0) {
							acceptanceConsignmentVO.setNoOfPieces(Integer
									.parseInt(acceptanceNoOfPcs[i]));
						}
						if (acceptanceWeight[i] != null && acceptanceWeight[i].trim().length() > 0) {
							/*acceptanceConsignmentVO.setWeight(Double
									.parseDouble(acceptanceWeight[i]));*/
							acceptanceConsignmentVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double
									.parseDouble(acceptanceWeight[i])));//added by A-7371
						}
						acceptanceConsignmentVO.setRemarks(accpRemarks[i]);
						acceptanceConsignmentVO
						.setOperationFlag(acceptanceOperationFlag[i]);
						newacceptanceConsignmentVOs.add(acceptanceConsignmentVO);


					}else if(ConsignmentDocumentVO.OPERATION_FLAG_INSERT
							.equals(acceptanceOperationFlag[i])){
						acceptanceConsignmentVO = acceptanceConsignmentVOs.get(i);
						acceptanceConsignmentVO
						.setOnwardCarrierCode(acceptanceFlightCarrierCode[i]);
						acceptanceConsignmentVO
						.setOnwardFlightNumber(acceptanceFlightNumber[i]);
						acceptanceConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
						acceptanceConsignmentVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
						acceptanceConsignmentVO.setPaCode(consignmentDocumentVO.getPaCode());
						if (acceptanceFlightDate[i] != null
								&& acceptanceFlightDate[i].trim().length() > 0) {
							acceptanceConsignmentVO
							.setOnwardFlightDate(new LocalDate(
									LocalDate.NO_STATION,
									Location.NONE, false)
							.setDate(acceptanceFlightDate[i]));
						}
						//if (acceptancePol[i] != null && acceptancePol[i].trim().length() > 0) {
						acceptanceConsignmentVO.setPol(acceptancePol[i]);
						//}
						//if (acceptancePou[i] != null && acceptancePou[i].trim().length() > 0) {
						acceptanceConsignmentVO.setPou(acceptancePou[i]);
						//}
						if (acceptanceNoOfPcs[i] != null
								&& acceptanceNoOfPcs[i].trim().length() > 0) {
							acceptanceConsignmentVO.setNoOfPieces(Integer
									.parseInt(acceptanceNoOfPcs[i]));
						}
						if (acceptanceWeight[i] != null && acceptanceWeight[i].trim().length() > 0) {
							/*acceptanceConsignmentVO.setWeight(Double
									.parseDouble(acceptanceWeight[i]));*/
							acceptanceConsignmentVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double
									.parseDouble(acceptanceWeight[i])));//added by A-7371
						}
						acceptanceConsignmentVO.setRemarks(accpRemarks[i]);
						acceptanceConsignmentVO
						.setOperationFlag(acceptanceOperationFlag[i]);
					} else if ( ConsignmentDocumentVO.OPERATION_FLAG_DELETE
							.equals(acceptanceOperationFlag[i])) {
						acceptanceConsignmentVO = acceptanceConsignmentVOs.get(i);
						acceptanceConsignmentVO
						.setOperationFlag(acceptanceOperationFlag[i]);


					} else if (ConsignmentDocumentVO.OPERATION_FLAG_UPDATE
							.equals(acceptanceOperationFlag[i])) {
						acceptanceConsignmentVO = acceptanceConsignmentVOs.get(i);
						if (acceptanceNoOfPcs[i] != null
								&& acceptanceNoOfPcs[i].trim().length() > 0) {
							acceptanceConsignmentVO.setNoOfPieces(Integer
									.parseInt(acceptanceNoOfPcs[i]));
						}
						if (acceptanceWeight[i] != null && acceptanceWeight[i].trim().length() > 0) {
							/*acceptanceConsignmentVO.setWeight(Double
									.parseDouble(acceptanceWeight[i]));*/
							acceptanceConsignmentVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double
									.parseDouble(acceptanceWeight[i])));//added by A-7371
						}
						acceptanceConsignmentVO.setRemarks(accpRemarks[i]);
						acceptanceConsignmentVO
						.setOperationFlag(acceptanceOperationFlag[i]);


					}
					else if ( "NOOP".equals(acceptanceOperationFlag[i])) {
						
						RoutingInConsignmentVO voToRemove = new RoutingInConsignmentVO() ;
						voToRemove = acceptanceConsignmentVOs.get(i);
						newVOsToRemove.add(voToRemove);
						
						}
			
					
			

			}
			if(consignmentDocumentVO.getAcceptanceInfo() == null){
				consignmentDocumentVO.setAcceptanceInfo(new ArrayList<RoutingInConsignmentVO>());
			}
			else{
				if(newVOsToRemove !=null && newVOsToRemove.size()>0){
					consignmentDocumentVO.getAcceptanceInfo().removeAll(newVOsToRemove);
				 }
			    }
			if (newacceptanceConsignmentVOs != null
					&& newacceptanceConsignmentVOs.size() > 0) {
				consignmentDocumentVO.getAcceptanceInfo().addAll(
						newacceptanceConsignmentVOs);
			}
			//Added by A-4777 for ICRD-15422
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			Collection<String> flightCollection = new ArrayList<String>();
			//Modified by A-4810 for bug-fix icrd-15417
			for(int i=0;i<((acceptanceFlightNumber.length)-1);i++){
					StringBuilder flightDetail = new StringBuilder(acceptanceFlightCarrierCode[i]).append(acceptanceFlightNumber[i]).append(acceptanceFlightDate[i]);
					if(flightDetail!=null && !"".equals(flightDetail.toString()) && flightDetail.length()>0 ){
					if(flightCollection.contains(flightDetail.toString())){
					errors .add(new ErrorVO("mailtracking.defaults.national.maintainconsignment.duplicateflightentry"));
						if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						consignmentForm.setConsignmentOriginFlag("");
						invocationContext.target = FAILURE;
						return;
						}
					}else{
					flightCollection.add(flightDetail.toString());
					}
				}

			}

			/**
			 * Code for updating acceptance part ends
			 */



		}
		//A-4810
		  consignmentForm.setNoconDocNo("");
		 consignmentForm.setConsignmentOriginFlag("");
		invocationContext.target = TARGET;
	}

	/**
	 *
	 * @param operationFlag
	 * @return
	 */
	private boolean checkIfUpdationIsNeeded(String[] operationFlag,String[] acceptanceOperationFlag){

		 if(operationFlag != null && operationFlag.length >0){
			 for(String opFlag : operationFlag){
				 if("U".equals(opFlag) ||
						 "I".equals(opFlag) ||
						 "D".equals(opFlag)){
					 return true;

				 }
			 }

		 }if(acceptanceOperationFlag != null && acceptanceOperationFlag.length >0){
			 for(String opFlag : acceptanceOperationFlag){
				 if("U".equals(opFlag) ||
						 "I".equals(opFlag) ||
						 "D".equals(opFlag)){

					 return true;

				 }
			 }

		 }

		return false;
	}
	/**
	 *
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 */
	private void validateConsignmentDetailsForClosedFlight(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){
		if(consignmentDocumentVO.getAcceptanceInfo() != null && consignmentDocumentVO.getAcceptanceInfo().size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
				if(routingInConsignmentVO.isFlightClosed()){
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.flightClosedForOperations");
					//routingInConsignmentVO.setInvalidFlightFlag(true);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					Object[] obj = {
							routingInConsignmentVO
							.getOnwardCarrierCode(),
							routingInConsignmentVO
							.getOnwardFlightNumber(),
							routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
							routingInConsignmentVO.getPol(),
							routingInConsignmentVO.getPou() };
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);

				}

			}

		}
	}
}
