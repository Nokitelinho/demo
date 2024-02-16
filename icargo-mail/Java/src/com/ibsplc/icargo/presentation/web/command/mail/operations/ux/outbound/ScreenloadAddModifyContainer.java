package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;   
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
public class ScreenloadAddModifyContainer extends AbstractCommand{
	private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");
	 private static final String PREASSIGNMENT_SYS = "mailtracking.defaults.acceptance.preassignmentneeded";
	   private static final String INVENTORYENABLED_SYS = "mailtracking.defaults.inventoryenabled";
	   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	   private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";
	   private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	   private static final String SYSPAR_ULDTOBARROW_ALLOW="mail.operations.allowuldasbarrow";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		this.log.entering("ScreenloadAddModifyContainer", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		String cmpcod = logonAttributes.getCompanyCode();
	    OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	    AreaDelegate areaDelegate = new AreaDelegate();
	    Map stationParameters = null; 
	    ContainerDetails containerDetails =null;
		ContainerDetailsVO containerDetailsVO = null;
		Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> containerDetVO = new ArrayList<ContainerDetailsVO>();
	    ArrayList<ContainerDetails> containerDetailsCollection= 
				outboundModel.getContainerDetailsCollection();
	    if(containerDetailsCollection != null) {
			containerDetails = containerDetailsCollection.get(0);
			containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
			containers.add(containerDetailsVO);
			try{
				containerDetVO=new MailTrackingDefaultsDelegate().findMailbagsInContainer(containers);
			}catch(BusinessDelegateException businessDelegateException){
	  	      actionContext.addAllError(handleDelegateException(businessDelegateException));
			}
	    }
	   String stationCode = logonAttributes.getStationCode();//added by A-8353 for ICRD-274933
	    //DefaultScreenSessionImpl screenSession= getScreenSession();
	    SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
	     Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     Map<String, String> paramResults = null;
			Collection<String> codes = new ArrayList<String>();
	    	codes.add(PREASSIGNMENT_SYS);
	    	codes.add(INVENTORYENABLED_SYS);
	    	codes.add(MAIL_COMMODITY_SYS);
	    	codes.add(SYSPAR_DEFUNIT_WEIGHT);
	    	codes.add(SYSPAR_ULDTOBARROW_ALLOW);
	     try {
	    		paramResults = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
	    	} catch(BusinessDelegateException businessDelegateException) {
	    		handleDelegateException(businessDelegateException);
	    	}
	    	/*if(paramResults != null && paramResults.size() > 0) {
	    		screenSession.setAttribute("PreassignFlag", paramResults.get(PREASSIGNMENT_SYS));
	    		screenSession.setAttribute("Inventoryparameter", paramResults.get(INVENTORYENABLED_SYS));
	    		screenSession.setAttribute("MailCommidityCode", paramResults.get(MAIL_COMMODITY_SYS));
	    	}*/
	     try
	    	 {
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	     // systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
	    	    }
	    // logonAttributes.setAirportCode("FRA");
	     this.log.log(5, new Object[] { "oneTimeValues ---> ", oneTimeValues });
	     this.log.log(5, new Object[] { "LoginAirport ---> ", logonAttributes.getAirportCode() });
	     outboundModel.setOneTimeValues(MailOutboundModelConverter.constructOneTimeValues(oneTimeValues));
	     String commodityCode = paramResults.get(MAIL_COMMODITY_SYS);
	     String defaultSysUnit = paramResults.get(SYSPAR_DEFUNIT_WEIGHT);
	     String allowuldasbarrow =paramResults.get(SYSPAR_ULDTOBARROW_ALLOW);
			Collection<String> commodites = new ArrayList<String>();
			if(commodityCode!=null && commodityCode.trim().length()>0) {
				commodites.add(commodityCode);
				Map<String,CommodityValidationVO> densityMap = null;
				CommodityDelegate  commodityDelegate = new CommodityDelegate();

				try {
					densityMap = commodityDelegate.validateCommodityCodes(cmpcod, commodites);
				} catch (BusinessDelegateException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}

				if(densityMap !=null && densityMap.size()>0){
					CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
					log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
					outboundModel.setDensity(String.valueOf(commodityValidationVO.getDensityFactor()));
				}
				if(defaultSysUnit!=null){
					outboundModel.setDefWeightUnit(defaultSysUnit);
    	    		
    	    	}
			}
			
			/*FlightValidationVO flightValidatonVO=screenSession.getAttribute("flightValidationVO");
			if(flightValidatonVO != null) {
				String route = flightValidatonVO.getFlightRoute();
				if(route != null && !"".equals(route)){
					String[] routeArr = route.split("-");
					int flag = 0;
					Collection<String> pous = new ArrayList<String>();
					for(int i=0;i<routeArr.length;i++){
						if(flag == 1){
							pous.add(routeArr[i]);
						}
						if(routeArr[i].equals(logonAttributes.getAirportCode())){
							flag = 1;
						}
					}
					pous.remove(logonAttributes.getAirportCode());
					outboundModel.getMailFlight().setPouList(pous);
					outboundModel.setPouList(pous);
					}
				}else{*/
					String route = outboundModel.getMailAcceptance().getFlightRoute();
					if(route != null && !"".equals(route)){
						String[] routeArr = route.split("-");
						int flag = 0;
						Collection<String> pous = new ArrayList<String>();
						for(int i=0;i<routeArr.length;i++){
							if(flag == 1){
								pous.add(routeArr[i]);
							}
							if(routeArr[i].equals(logonAttributes.getAirportCode())){
								flag = 1;
							}
						}
						pous.remove(logonAttributes.getAirportCode());
						outboundModel.getMailAcceptance().setPouList(pous);
						outboundModel.setPouList(pous);
						}
					else {
						outboundModel.getMailAcceptance().setPouList(new ArrayList<>());
						outboundModel.setPouList(new ArrayList<>());
					}
				/*}*/
					LocalDate date=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					String dateToSave=date.toDisplayDateOnlyFormat();
					String time=date.toDisplayTimeOnlyFormat(true);
					outboundModel.setCurrentDate(dateToSave);
					outboundModel.setCurrentTime(time);
	     outboundModel.setAirportCode(logonAttributes.getAirportCode());
	     outboundModel.setUldToBarrowAllow(allowuldasbarrow);//added by A-8893 for IASCB-38903
	  	try {
			stationParameters = areaDelegate.findStationParametersByCode(cmpcod, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {//added by A-8353 for ICRD-274933
			e1.getMessage();
		}
	  	ArrayList<MailbagVO> mailbagVOs =null;
	  	List<Mailbag> mailbags =null;
	  	if(containerDetVO!=null){
			 for(ContainerDetailsVO containersDetailsVO :containerDetVO) { 
				 mailbagVOs=  (ArrayList)containersDetailsVO.getMailDetails();
				mailbags=MailOutboundModelConverter.constructMailbagDetails(mailbagVOs, containersDetailsVO);
			 }
	  	}
	  	if("TRANSFER_MAIL".equals(outboundModel.getActionType())){
	  		outboundModel.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		Collection<PartnerCarrierVO> partnerCarriers = new ArrayList<>();
		ArrayList<String> partnerCarriersList = new ArrayList<>();
		try {
			partnerCarriers = new MailTrackingDefaultsDelegate().findAllPartnerCarriers(
					logonAttributes.getCompanyCode(), logonAttributes.getOwnAirlineCode(),
					logonAttributes.getAirportCode());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}
		if (partnerCarriers != null && !partnerCarriers.isEmpty()) {
			for (PartnerCarrierVO partnerCarrierVO : partnerCarriers) {
				partnerCarriersList.add(partnerCarrierVO.getPartnerCarrierCode());
			}
		}
		outboundModel.setPartnerCarriers(partnerCarriersList);
			}
	  	outboundModel.setStationVolUnt((String)stationParameters.get(STNPAR_DEFUNIT_VOL));//added by A-8353 for ICRD-274933
	  	outboundModel.setMailbags(mailbags);
	     ResponseVO responseVO = new ResponseVO();
	     List<OutboundModel> results = new ArrayList();
	     results.add(outboundModel);
	     responseVO.setResults(results);
	     actionContext.setResponseVO(responseVO);
	     this.log.exiting("ScreenLoadCommand", "execute");
	     
	     
		
	}
	
	private Collection<String> getOneTimeParameterTypes()
	  {
	    Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add("mailtracking.defaults.registeredorinsuredcode");
	    parameterTypes.add("mailtracking.defaults.mailcategory");
	    parameterTypes.add("mailtracking.defaults.highestnumbermail");
	    parameterTypes.add("mailtracking.defaults.mailclass");
	    parameterTypes.add("mailtracking.defaults.return.reasoncode");
	    parameterTypes.add("mailtracking.defaults.companycode");
	    parameterTypes.add("mailtracking.defaults.containertype");
	    return parameterTypes;
	  }
	/**
	 * @author A-8353
	 * @return stationParameterCodes
	 * 
	 */
	private Collection<String> getStationParameterCodes()
	  {
	    Collection stationParameterCodes = new ArrayList();
	    stationParameterCodes.add(STNPAR_DEFUNIT_VOL);
	    return stationParameterCodes;
	  }

}
