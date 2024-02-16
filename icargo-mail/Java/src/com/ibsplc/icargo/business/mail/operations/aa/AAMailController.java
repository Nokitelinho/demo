/**
 * AAMailController.java Created on Sep 14, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.operations.aa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import com.ibsplc.icargo.business.mail.operations.Container;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.aa.AAMailController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6245	:	14-Sep-2018	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class AAMailController extends MailController {

	private static final String CONTENT_ID_SORT_ORDER = "operations.flthandling.manifest.contentidsortorder";

	private static final String MAIL_INTERNATIONAL = "MI";

	private static final String MAIL_LOCAL = "ML";

	private static final String MAIL_CONTAINERIZED = "CM";

	private static final String MAIL_DEFERRED = "MD";

	private static final String MAIL_ONLINE = "MO";

	private static final String EMPTY_CONTAINER = "MT";

	private static final String CIVILIAN_DEFERRED = "CD";
	
	private static final String MILITARY_DEFERRED = "MD";

	private static final String CLASS_NAME = "AAMailController";

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/**
	 * 
	 * 	Method		:	AAMailController.calculateULDContentId
	 *	Added by 	:	A-6245 on 14-Sep-2018
	 * 	Used for 	: 	ICRD-239331
	 *	Parameters	:	@param containerDetailsVOs
	 *	Parameters	:	@param toFlightVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public String calculateULDContentId(Collection<ContainerDetailsVO> containerDetailsVOs,
			OperationalFlightVO toFlightVO) throws SystemException {
		log.entering(CLASS_NAME, "calculateULDContentId");
		HashSet<String> applicableContentIds = new HashSet<>();
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		Collection<OneTimeVO> contentIdSortOrder = null;
		Collection<String> fieldTypes = new ArrayList<>();
		TreeMap<String, String> priorityMap = null;
		String contentId=null;
		fieldTypes.add(CONTENT_ID_SORT_ORDER);
		oneTimeMap = findOneTimeValues(toFlightVO.getCompanyCode(), fieldTypes);
		if(oneTimeMap!=null&&!oneTimeMap.isEmpty()){
			contentIdSortOrder = oneTimeMap.get(CONTENT_ID_SORT_ORDER);
		}
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
					if (containerDetailsVO.getMailDetails() == null
							|| containerDetailsVO.getMailDetails().size() == 0) {
						containerDetailsVO.setContentId(EMPTY_CONTAINER);
					} else if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
						containerDetailsVO.setContentId(MAIL_CONTAINERIZED);
					} else {
						TreeMap<String, String> mailPriorityMap = null;
						Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
						HashSet<String> applicableMailContentIds = new HashSet<>();
						String mailContentId = null;
						for (MailbagVO mailbagVO : mailbagVOs) {
							mailContentId = calculateMailbagContentId(containerDetailsVO, mailbagVO, toFlightVO);
							applicableMailContentIds.add(mailContentId);
						}
						mailPriorityMap = constructSortedContentIds(applicableMailContentIds, contentIdSortOrder);
						if (mailPriorityMap != null && mailPriorityMap.firstEntry() != null) {
							containerDetailsVO.setContentId(mailPriorityMap.firstEntry().getValue());
						}
					}
					if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
						applicableContentIds.add(containerDetailsVO.getContentId());
					}

			}
			priorityMap = constructSortedContentIds(applicableContentIds, contentIdSortOrder);
			if (priorityMap != null && priorityMap.firstEntry() != null) {
				for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
					if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
						containerDetailsVO.setContentId(priorityMap.firstEntry().getValue());
					}
				}
			}
			for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
				if (isNotNullAndEmpty(containerDetailsVO.getContainerNumber()) && isNotNullAndEmpty(containerDetailsVO.getContentId())) {
					contentId= containerDetailsVO.getContentId();
			}
		}
		}
return contentId;

	}
	/**
	 * 
	 * 	Method		:	AAMailController.constructSortedContentIds
	 *	Added by 	:	A-6245 on 16-Sep-2018
	 * 	Used for 	:	Derive the sorted content id for applicable content id's
	 * 					Sort all the applicable content ids based on priority
	 * 					First entry in the treemap will have the content id 
	 * 					with highest priority
	 *	Parameters	:	@param applicableContentIds
	 *	Parameters	:	@param contentIdSortOrder
	 *	Parameters	:	@return 
	 *	Return type	: 	TreeMap<String,String>
	 */
	private TreeMap<String, String> constructSortedContentIds(HashSet<String> applicableContentIds,
			Collection<OneTimeVO> contentIdSortOrder) {
		TreeMap<String, String> priorityMap = new TreeMap<>();
		if (applicableContentIds != null && applicableContentIds.size() > 0) {
			for (String contentId : applicableContentIds) {
				for (OneTimeVO oneTimeVO : contentIdSortOrder) {
					if (oneTimeVO.getFieldValue().equals(contentId)) {
						priorityMap.put(oneTimeVO.getFieldDescription(), contentId);
					}
				}
			}
		}
		return priorityMap;
	}
	/**
	 * 
	 * 	Method		:	AAMailController.calculateMailbagContentId
	 *	Added by 	:	A-6245 on 14-Sep-2018
	 * 	Used for 	:	Return the content id based on the business logic
	 *	Parameters	:	@param containerDetailsVO
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param toFlightVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	private String calculateMailbagContentId(ContainerDetailsVO containerDetailsVO, MailbagVO mailbagVO,
			OperationalFlightVO toFlightVO) throws SystemException {
		Collection<String> airportCodes = new ArrayList<>();
		Map<String, AirportValidationVO> airportMap = null;
		String contentId = null;
		String mailOrigin = mailbagVO.getOrigin() != null ? mailbagVO.getOrigin()
				: getAirportForExchangeOffice(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
		String mailDestination = mailbagVO.getDestination() != null ? mailbagVO.getDestination()
				: getAirportForExchangeOffice(mailbagVO.getCompanyCode(), mailbagVO.getDoe());
		airportCodes.add(mailOrigin);
		airportCodes.add(mailDestination);
		airportCodes.add(toFlightVO.getPol());
		airportCodes.add(toFlightVO.getPou());
		airportMap = new SharedAreaProxy()
				.validateAirportCodes(toFlightVO.getCompanyCode(), airportCodes);
		if (airportMap != null) {
			if (!airportMap.get(mailOrigin).getCountryCode()
					.equals(airportMap.get(mailDestination).getCountryCode())){
					//&& !airportMap.get(toFlightVO.getPol()).getCountryCode()
							//.equals(airportMap.get(toFlightVO.getPou()).getCountryCode())) {
				contentId = MAIL_INTERNATIONAL;
			} else if (containerDetailsVO.getFlightSequenceNumber()>0 && airportMap.get(toFlightVO.getPol()).getCountryCode()
					.equals(airportMap.get(toFlightVO.getPou()).getCountryCode())
					&& mailDestination.equals(containerDetailsVO.getPou())) {
				contentId = MAIL_LOCAL;
			}else if (containerDetailsVO.getFlightSequenceNumber()<=0 && airportMap.get(mailOrigin).getCountryCode()
					.equals(airportMap.get(mailDestination).getCountryCode())
					) {
				contentId = MAIL_LOCAL;  
			}  
			else if (!toFlightVO.getPou().equals(mailDestination)) {
				contentId = MAIL_ONLINE;
			} else if (MILITARY_DEFERRED.equals(mailbagVO.getMailServiceLevel())||
					CIVILIAN_DEFERRED.equals(mailbagVO.getMailServiceLevel())) {
				contentId = MAIL_DEFERRED;
			};
		}
		return contentId;
	}
	/**
	 * 
	 * 	Method		:	AAMailController.getAirportForExchangeOffice
	 *	Added by 	:	A-6245 on 20-Sep-2018
	 * 	Used for 	:	Get Airport code from exchange office
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param exchangeOffice
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String getAirportForExchangeOffice(String companyCode, String exchangeOffice) {
		String airportCode = null;
		OfficeOfExchangeVO exchangeOfficeVO = null;
		try {
			exchangeOfficeVO = validateOfficeOfExchange(companyCode, exchangeOffice);
		} catch (SystemException ex) {
			log.log(Log.SEVERE, "System Exception in getAirportForExchangeOffice");
		}
		if (exchangeOfficeVO != null && exchangeOfficeVO.getAirportCode() != null
				&& exchangeOfficeVO.getAirportCode().trim().length() > 0) {
			airportCode = exchangeOfficeVO.getAirportCode();
		} else {
			if (exchangeOffice != null && exchangeOffice.trim().length() > 0) {
				airportCode = exchangeOffice.substring(2, 5);
			}
		}
		return airportCode;
	}
	/**
	 * 
	 * 	Method		:	AAMailController.findOneTimeValues
	 *	Added by 	:	A-6245 on 14-Sep-2018
	 * 	Used for 	:	Find one time values
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param fieldTypes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode, Collection<String> fieldTypes)
			throws SystemException {
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		try {
			oneTimeMap = new SharedDefaultsProxy().findOneTimeValues(companyCode, fieldTypes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getErrors());
		}
		return oneTimeMap;
	}
	
	private ContainerVO constructContainerVO(
			ContainerDetailsVO containerDetailsVO, OperationalFlightVO toFlightVO) {
		log.entering(CLASS_NAME, "constructContainerVO");
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(toFlightVO.getCompanyCode());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setAssignedPort(toFlightVO.getPol());
		containerVO.setCarrierId(toFlightVO.getCarrierId());
		containerVO.setFlightNumber(toFlightVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
		containerVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
		containerVO.setContentId(containerDetailsVO.getContentId());
		return containerVO;
	}
	/**
	 * 	Method		:	AAMailController.populateUldManifestVOWithContentID
	 *	Added by 	:	A-7929 on 4-March-2019
	 * 	Parameters	:	@param dwsMasterVO
	 *	Return type	: 	Collection<UldManifestVO>
	 * @param assignedAirport 
	 * @param containerNumber 
	 * @throws SystemException 
	 * @throws FinderException 
	 */
	public String fetchMailContentIDs(DWSMasterVO dwsMasterVO, String containerNumber, String assignedAirport) throws FinderException, SystemException {
		log.entering(CLASS_NAME, "fetchMailContentIDs");
	
		return  Container.fetchMailContentIDs(dwsMasterVO,containerNumber,assignedAirport);
	}

	private static boolean isNotNullAndEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}

}