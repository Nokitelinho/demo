package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailcontainerlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailContainerListModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.session.interfaces.operations.flthandling.loadplan.MaintainLoadPlanSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class AssignMailContainerToLoadPlanCommand extends AbstractCommand {

	private static final String CLASS_NAME = "AssignMailContainerToLoadPlanCommand";
	private static final Log LOG = LogFactory.getLogger(CLASS_NAME);
	private static final String SCREEN_ID_LP = "operations.flthandling.loadplan.maintainloadplan";
	private static final String MODULE_NAME_LP = "operations.flthandling.loadplan";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {

		LOG.entering(CLASS_NAME, "execute");
		MaintainLoadPlanSession loadPlanSession = getScreenSession(MODULE_NAME_LP, SCREEN_ID_LP);
		MailContainerListModel mailContainerListModel = (MailContainerListModel) actionContext.getScreenModel();
		Collection<ContainerVO> selectedMailContainerVOs = new ArrayList<>();
		Collection<ContainerDetails> mailContainerVOs = mailContainerListModel.getSelectedContainerDetails();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		FlightValidationVO flightValidationVO = loadPlanSession.getFlightValidationVO();
		if (flightValidationVO != null) {

			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(logonAttributes.getAirportCode());
			flightFilterVO.setCarrierCode(flightValidationVO.getCarrierCode());
			flightFilterVO.setFlightNumber(flightValidationVO.getFlightNumber());
			flightFilterVO.setStringFlightDate(flightValidationVO.getFlightDate().toDisplayFormat());
			loadPlanSession.setFlightFilterVO(flightFilterVO);
		}

		if (Objects.nonNull(mailContainerVOs) && !mailContainerVOs.isEmpty()) {
			ContainerVO selectedContainervo = null; 
			for (ContainerDetails containerDetails : mailContainerVOs) {
				selectedContainervo = new ContainerVO();
				selectedContainervo.setContainerNumber(containerDetails.getContainerNumber());
				selectedContainervo.setFinalDestination(containerDetails.getFinalDestination());
				selectedContainervo.setSubclassGroup(containerDetails.getSubclassGroup());
				selectedContainervo.setBags(containerDetails.getBags());
				selectedContainervo.setNoOfDaysInCurrentLoc(containerDetails.getNoOfDaysInCurrentLoc());
				selectedContainervo.setUldFulIndFlag(containerDetails.getUldFulIndFlag());
				selectedContainervo.setExpectedOrProvisionalCharge(containerDetails.getProvosionalCharge());
				selectedContainervo.setPosition(containerDetails.getPosition());
				double wgtToMeasure = Objects.nonNull(containerDetails.getWeight()) ? containerDetails.getWeight()
						: 0.0;
				selectedContainervo.setWeight(new Measure(UnitConstants.WEIGHT, wgtToMeasure));
				selectedContainervo.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
				selectedContainervo.setUldReferenceNo(containerDetails.getUldReferenceNo());
				/*
				 * Temporary code added to handle previously selected mail container from pop
				 * up. Will be reverted once mail team completes their change.
				 */
				Collection<ContainerVO> selectedMailContainers = loadPlanSession.getMailContainerVOs();
				
				if (Objects.nonNull(selectedMailContainers) && !selectedMailContainers.isEmpty()) {

					if (!(selectedMailContainers.stream().anyMatch(selectedMailContainer -> containerDetails
							.getContainerNumber().equals(selectedMailContainer.getContainerNumber())))) {

						loadPlanSession.getMailContainerVOs().add(selectedContainervo);
					}
				} else {
					selectedMailContainerVOs.add(selectedContainervo);
					loadPlanSession.setMailContainerVOs(selectedMailContainerVOs); 
				}
				loadPlanSession.setModifyFlag("Y");
			}
		}

		ArrayList<MailContainerListModel> results = new ArrayList<>();
		ResponseVO responseVO = new ResponseVO();
		results.add(mailContainerListModel);
		responseVO.setResults(results);
		responseVO.setStatus("SUCCESS");
		actionContext.setResponseVO(responseVO);
		LOG.exiting(CLASS_NAME, "execute");

	}
}
