package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class EmbargoValidationCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String HYPHEN = "-";
	private static final String ERROR_DMG_REASON_MANDATORY = "mailtracking.defaults.err.reasonfordamagemandatory";
	private static final String WARN_COTERMINUS="mailtracking.defaults.war.coterminus";
	private static final String COTERMINUS_STATUS="coterminus";
	private static final String LATVALIDATION_STATUS="latvalidation";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
		this.log.entering("AcceptMailCommand", "execute");
	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	
	String flightCarrierFlag = (String)actionContext.getAttribute("flightCarrierFlag");
	    List<ErrorVO> errors = new ArrayList();
	    ResponseVO responseVO = new ResponseVO();
	OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	 List<ErrorVO> existingerrors = actionContext.getErrors();
	 if(CollectionUtils.isNotEmpty(existingerrors)) {
        return;
      }
		ContainerDetailsVO newVo = new ContainerDetailsVO();
		MailAcceptanceVO mailAcceptanceVO =(MailAcceptanceVO)actionContext.getAttribute("mailAcceptanceDetails");
		Collection<EmbargoDetailsVO> embargoDetailVos = null;
		Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();
		ContainerDetailsVO containerDetailsVO = (ContainerDetailsVO)actionContext.getAttribute("containerDetails");
	    if (containerDetailsVO != null && mailAcceptanceVO.getPopupAction()==null )
	    {
			containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			embargoDetailVos = checkEmbargoForMail(containerDetailsVO);
		}		
	    if ((embargoDetailVos != null) && (!embargoDetailVos.isEmpty()))
	    {
	      mailAcceptanceVO.setEmbargoDetails(embargoDetailVos);
	      errors.add(new ErrorVO("mailtracking.defaults.embargoexists"));
			actionContext.setAttribute("mailAcceptanceDetails",mailAcceptanceVO);
	      actionContext.addAllError(errors);
	    }
		actionContext.setAttribute("mailAcceptanceDetails",mailAcceptanceVO);
	    actionContext.setAttribute("containerDetails", containerDetailsVO);
	    List<OutboundModel> results = new ArrayList();
	    results.add(outboundModel);
	    responseVO.setResults(results);
	    actionContext.setResponseVO(responseVO);
	   // actionContext.addAllError(errors);
	}
	
	/**
	 * 
	 * @param containerDetailsVO
	 * @return
	 */
	public Collection<EmbargoDetailsVO>  checkEmbargoForMail(ContainerDetailsVO containerDetailsVO) {
		Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
		Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
		Map<String, Collection<String>> detailsMap = null;
		Map<String, MailbagVO> mailbagVOMap = new HashMap<String, MailbagVO>();
		if(containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() >0){
		for(MailbagVO mailbagVO :containerDetailsVO.getMailDetails() ){
			ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
			shipmentDetailsVO.setCompanyCode(mailbagVO.getCompanyCode());
			shipmentDetailsVO.setOoe(mailbagVO.getOoe());
			shipmentDetailsVO.setDoe(mailbagVO.getDoe());
			Collection<String> officeOfExchanges=new ArrayList<String>();
			Collection<ArrayList<String>> groupedOECityArpCodes = null;
			String originOE = mailbagVO.getOoe();
			String destOE   = mailbagVO.getDoe();
			officeOfExchanges.add(originOE);
			officeOfExchanges.add(destOE); 
			try {
				groupedOECityArpCodes=new MailTrackingDefaultsDelegate()     
						.findCityAndAirportForOE(mailbagVO.getCompanyCode(), officeOfExchanges);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException); 
			}
			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
				for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {  
					if(cityAndArpForOE.size() == 3) {
						if(originOE != null && originOE.length() > 0 && originOE.equals(cityAndArpForOE.get(0))) {
							shipmentDetailsVO.setOrgStation(cityAndArpForOE.get(2)); 
						}
						if(destOE != null && destOE.length() > 0 && destOE.equals(cityAndArpForOE.get(0))) {
							shipmentDetailsVO.setDstStation(cityAndArpForOE.get(2)); 
						}
					} 
				}
			}else{
			shipmentDetailsVO.setOrgStation(mailbagVO.getOoe().substring(2,5));
			shipmentDetailsVO.setDstStation(mailbagVO.getDoe().substring(2,5));
			}
			shipmentDetailsVO.setOrgCountry(mailbagVO.getOoe().substring(0,2));
			shipmentDetailsVO.setDstCountry(mailbagVO.getDoe().substring(0, 2));
			shipmentDetailsVO.setShipmentID(mailbagVO.getMailbagId());
			mailbagVOMap.put(mailbagVO.getMailbagId(), mailbagVO);
			//shipmentDetailsVO.setOrgCntGrp(orgCntGrp)
			//shipmentDetailsVO.setDstCntGrp(dstCntGrp)
			//shipmentDetailsVO.setOrgArpGrp(orgArpGrp)
			//shipmentDetailsVO.setDstArpGrp(dstArpGrp)
			shipmentDetailsVO.setShipmentDate(mailbagVO.getScannedDate());
			//shipmentDetailsVO.setMap(map)
			String orgPaCod = null;
			String dstPaCod =  null;
		
			Collection<String> mailsubclassGrp =new ArrayList<String>();
			try {
				 orgPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),mailbagVO.getOoe());
			}
			catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log
				.log(Log.SEVERE,
				"\n\n Excepption while checking for embargo. ---------------------->");
			}
			try {
				dstPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),mailbagVO.getDoe());
			}
			catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log
				.log(Log.SEVERE,
				"\n\n Excepption while checking for embargo. ---------------------->");
			}

			shipmentDetailsVO.setOrgPaCod(orgPaCod);
			shipmentDetailsVO.setDstPaCod(dstPaCod);
			
			
			String mailBagId = mailbagVO.getMailbagId();
			String carrierCode = mailbagVO.getCarrierCode();
			detailsMap = populateDetailsMapforMail(mailBagId,containerDetailsVO.getCompanyCode(),carrierCode);
			if(detailsMap != null && detailsMap.size()>0){
				shipmentDetailsVO.setMap(detailsMap);
			}
			//added for ICRD-223091 by A-7815
			shipmentDetailsVO.setUserLocaleNeeded(true);
			shipmentDetailsVO.setApplicableTransaction("MALACP");//modified by a-7871
			shipmentDetailsVOs.add(shipmentDetailsVO);
			}
		
		}    
		Map<String, DespatchDetailsVO> despathdetailsvoMap = new HashMap<String, DespatchDetailsVO>();
		if(containerDetailsVO.getDesptachDetailsVOs() != null && containerDetailsVO.getDesptachDetailsVOs().size() >0){
			newDespatchVOs = containerDetailsVO.getDesptachDetailsVOs();
			for(DespatchDetailsVO  despatchDetailsVO : newDespatchVOs){
				ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
				shipmentDetailsVO.setOoe(despatchDetailsVO.getOriginOfficeOfExchange());
				shipmentDetailsVO.setDoe(despatchDetailsVO.getDestinationOfficeOfExchange());
				shipmentDetailsVO.setOrgStation(despatchDetailsVO.getOriginOfficeOfExchange().substring(2,5));
				shipmentDetailsVO.setDstStation(despatchDetailsVO.getDestinationOfficeOfExchange().substring(2,5));
				shipmentDetailsVO.setOrgCountry(despatchDetailsVO.getOriginOfficeOfExchange().substring(0,2));
				shipmentDetailsVO.setDstCountry(despatchDetailsVO.getDestinationOfficeOfExchange().substring(0, 2));
				shipmentDetailsVO.setCompanyCode(containerDetailsVO.getCompanyCode());
				String mailId = new StringBuilder()
		            .append(despatchDetailsVO.getOriginOfficeOfExchange())
		            .append(despatchDetailsVO.getDestinationOfficeOfExchange())
					.append(despatchDetailsVO.getMailCategoryCode())
					.append(despatchDetailsVO.getMailSubclass())
					.append(despatchDetailsVO.getYear())
					.append(despatchDetailsVO.getDsn()).toString();
				shipmentDetailsVO.setShipmentID(mailId);
				despathdetailsvoMap.put(mailId, despatchDetailsVO);
				shipmentDetailsVO.setApplicableTransaction("MALACP");//modified by a-7871
				shipmentDetailsVO.setShipmentDate(despatchDetailsVO.getAcceptedDate());
				detailsMap = populateDetailsMapforMail(mailId,containerDetailsVO.getCompanyCode(),despatchDetailsVO.getCarrierCode());
				if(detailsMap != null && detailsMap.size()>0){
					shipmentDetailsVO.setMap(detailsMap);
				}
			  	String orgPaCod = null;
				String dstPaCod =  null;
				try {
					 orgPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),shipmentDetailsVO.getOoe());
				}
				catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					log
					.log(Log.SEVERE,
					"\n\n Excepption while checking for embargo. ---------------------->");
				}
				try {
					dstPaCod = new MailTrackingDefaultsDelegate().findPAForOfficeOfExchange(containerDetailsVO.getCompanyCode(),shipmentDetailsVO.getDoe());
				}
				catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					log
					.log(Log.SEVERE,
					"\n\n Excepption while checking for embargo. ---------------------->");
				} 
				shipmentDetailsVO.setOrgPaCod(orgPaCod);
				shipmentDetailsVO.setDstPaCod(dstPaCod);
				//added for ICRD-223091 by A-7815
				shipmentDetailsVO.setUserLocaleNeeded(true);
				shipmentDetailsVOs.add(shipmentDetailsVO);
			 }
       }
		//ShipmentDetailsVO shipmentDetailsVo = new ShipmentDetailsVO();
		Collection<EmbargoDetailsVO> embargoDetails = null;
		if (shipmentDetailsVOs != null && shipmentDetailsVOs.size() > 0) {
			// shipmentDetailsVo.setMailBagVOs(newMailbagVOs);
			try {
				embargoDetails = new MailTrackingDefaultsDelegate()
						.checkEmbargoForMail(shipmentDetailsVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log.log(Log.SEVERE,
						"\n\n Excepption while checking for embargo. ---------------------->");
			}
		}
		log.log(Log.INFO, "EmbargoDetails Collection ", embargoDetails);
		return embargoDetails;
	}
	



/**
 * @author A-4810
 * @param mailBagId
 * @param companyCode
 * @return
 */
	public Map<String, Collection<String>> populateDetailsMapforMail(String mailBagId, String companyCode,String carrierCode) {
		Map<String, Collection<String>> detailsMap = null;
		Collection<String> mailclass =new ArrayList<String>();
		Collection<String> mailsubclass =new ArrayList<String>();
		Collection<String> mailcat =new ArrayList<String>();
		Collection<String> carrierOrg =new ArrayList<>();
		Collection<MailSubClassVO> mailSubClassVOs = null;
		Collection<String> mailsubclassGrp =new ArrayList<String>();
		String subClassGrp =  null;
		if (mailBagId != null && mailBagId.trim().length() > 0) {
			if(mailBagId.length() >19) {
				detailsMap = new HashMap<String, Collection<String>>();
				mailcat.add(mailBagId.substring(12, 13));
				mailsubclass.add(mailBagId.substring(13, 15));
				mailclass.add(mailBagId.substring(13, 15).substring(0, 1));
				carrierOrg.add(carrierCode);
				detailsMap.put("MALCLS", mailclass);
				detailsMap.put("MALSUBCLS",mailsubclass);
				detailsMap.put("MALCAT",mailcat);
				detailsMap.put("CARORG",carrierOrg);
				try {
					mailSubClassVOs = new MailTrackingDefaultsDelegate().findMailSubClassCodes(companyCode,mailBagId.substring(13, 15));
				}
				catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
				if(mailSubClassVOs != null && mailSubClassVOs.size()>0){
					subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
					if(subClassGrp != null) {
						mailsubclassGrp.add(subClassGrp);
						detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
						
					}
				}
			
			}
		}
		return detailsMap;
	} 

}
