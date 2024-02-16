package com.ibsplc.icargo.business.mail.operations.event.aa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.proxy.MailtrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.event.EventConstants;
import com.ibsplc.icargo.framework.event.EventConstants.ParameterMap;
import com.ibsplc.icargo.framework.event.EventMapper;
import com.ibsplc.icargo.framework.event.vo.EventVO;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.event.aa.ContentIDEventMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6245	:	14-Sep-2018	:	Draft
 */
public class ContentIDEventMapper implements EventMapper {

	private static final String SAVE_CONTAINERS = "mail.operations.saveContainers";
	private static final String REASSIGN_CONTAINERS = "mail.operations.reassignContainers";
	private static final String TRANSFER_CONTAINERS = "mail.operations.transferContainers";
	private static final String TRANSFER_MAILBAGS = "mail.operations.transferMailbags";
	private static final String CONTAINER_PARAM = "CONTAINER_PARAM";
	private static final String FLIGHT_PARAM = "FLIGHT_PARAM";

	@Override
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.event.EventMapper#mapToEventVO(java.util.HashMap)
	 *	Added by 			: 	A-6245 on 14-Sep-2018
	 * 	Used for 			:	Override the mapToEventVO and get the business vo
	 *	Parameters			:	@param eventParamMap
	 *	Parameters			:	@return
	 *	Parameters			:	@throws Throwable
	 */
	public EventVO mapToEventVO(HashMap<ParameterMap, Object[]> eventParamMap) throws Throwable {
		Object payload = null;
		Map<String, Object> context = null;
		Collection<ContainerVO> containerVOs = null;
		OperationalFlightVO toFlightVO = null;
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		Collection<ContainerDetailsVO> containerDetailsVOsPayload = null;
		String module = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.MODULE))[0];
		String subModule = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.SUBMODULE))[0];
		String eventType = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.EVENT))[0];
		String methodId = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.METHOD_ID))[0];
		if (REASSIGN_CONTAINERS.equals(methodId)||TRANSFER_CONTAINERS.equals(methodId)) {
			containerVOs = (Collection<ContainerVO>) eventParamMap.get(EventConstants.ParameterMap.PARAMETERS)[0];
			toFlightVO = (OperationalFlightVO) eventParamMap.get(EventConstants.ParameterMap.PARAMETERS)[1];
		} else if (SAVE_CONTAINERS.equals(methodId)) {
			containerVOs = (Collection<ContainerVO>) eventParamMap.get(EventConstants.ParameterMap.PARAMETERS)[1];
			toFlightVO = (OperationalFlightVO) eventParamMap.get(EventConstants.ParameterMap.PARAMETERS)[0];
		}else if(TRANSFER_MAILBAGS.equals(methodId)){
			ContainerVO containerVO = (ContainerVO)eventParamMap.get(EventConstants.ParameterMap.PARAMETERS)[1];
			if(containerVO!=null){
				toFlightVO = constructOperationalFlightVO(containerVO);
				containerVOs = new ArrayList<>();
				containerVOs.add(containerVO);
			}
		}
		if (toFlightVO != null) {
			if (isNotNullAndEmpty(toFlightVO.getFlightNumber()) && toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {
				if (containerVOs != null && containerVOs.size() > 0) {
					for (ContainerVO containerVO : containerVOs) {
						if(isNotNullAndEmpty(containerVO.getContainerNumber())){
				containerDetailsVOs = new MailtrackingDefaultsProxy()
								.findMailbagsInContainer(constructContainerDetailsVO(containerVO, toFlightVO));
				if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
					if (toFlightVO.getPou() == null||toFlightVO.getPou().trim().isEmpty()) {
								toFlightVO.setPou(containerVO.getPou());
							}
							for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
								if (containerDetailsVO.getMailDetails() == null||containerDetailsVO.getMailDetails().size()==0) {
									//Handle case of empty container as findMailbagsInContainer will not return values
									containerDetailsVOs = constructContainerDetailsVO(containerVO, toFlightVO);
								}
							}
							if (containerDetailsVOsPayload == null) {
								containerDetailsVOsPayload = new ArrayList<>();
							}
							containerDetailsVOsPayload.add(containerDetailsVOs.iterator().next());
						}
					}
				}
					context = new HashMap<>();
					context.put(CONTAINER_PARAM, containerDetailsVOsPayload);
					context.put(FLIGHT_PARAM, toFlightVO);
				}
			}
		}
		payload = context;
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);
		eventVO.setMethodId(methodId);
		return eventVO;

	}
	/**
	 * 
	 * 	Method		:	ContentIDEventMapper.constructContainerDetailsVO
	 *	Added by 	:	A-6245 on 14-Sep-2018
	 * 	Used for 	:	Construct ContainerDetailsVO
	 *	Parameters	:	@param containerVOs
	 *	Parameters	:	@param toFlightVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ContainerDetailsVO>
	 */
	private Collection<ContainerDetailsVO> constructContainerDetailsVO(ContainerVO containerVO,
			OperationalFlightVO toFlightVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			if(containerVO.getContentId()==null) {//Content Id auto calculation not needed if value present from screen
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
			containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
			containerDetailsVO.setPol(toFlightVO.getPol());
			containerDetailsVO.setCarrierId(toFlightVO.getCarrierId());
			containerDetailsVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
			containerDetailsVO.setContainerType(containerVO.getType());
			containerDetailsVO.setContentId(containerVO.getContentId());
			containerDetailsVOs.add(containerDetailsVO);
		}
		return containerDetailsVOs;
	}
	/**
	 * 
	 * 	Method		:	ContentIDEventMapper.constructOperationalFlightVO
	 *	Added by 	:	U-1467 on 18-Apr-2020
	 * 	Used for 	:	Construct operationFlightVO from ContainerVO
	 *	Parameters	:	@param containerVO
	 *	Parameters	:	@return 
	 *	Return type	: 	OperationalFlightVO
	 */
	private OperationalFlightVO constructOperationalFlightVO(ContainerVO containerVO) {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(containerVO.getCompanyCode());
		operationalFlightVO.setPol(containerVO.getPol()!=null?containerVO.getPol():containerVO.getAssignedPort());
		operationalFlightVO.setPou(containerVO.getPou());
		operationalFlightVO.setCarrierId(containerVO.getCarrierId());
		operationalFlightVO.setFlightNumber(containerVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		operationalFlightVO.setFlightDate(containerVO.getFlightDate());
		operationalFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		return operationalFlightVO;
	}
	private static boolean isNotNullAndEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}
}
