package com.ibsplc.icargo.business.mail.operations.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightMVTVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightStatusVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.flight.operation.vo.MarkFlightMovementVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailtrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProductProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultProductProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AutoAttachAWBJobScheduleVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.event.EventConstants;
import com.ibsplc.icargo.framework.event.EventConstants.ParameterMap;
import com.ibsplc.icargo.framework.event.EventMapper;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class AutoAttachAwbsEventMapper implements EventMapper  {
	private static final String COLL_CONTAINERDETAILSVO_PARM = "COLL_CONTAINERDETAILSVO_PARM";
	private static final String OPERATIONALFLIGHTVO_PARM = "OPERATIONALFLIGHTVO_PARM";
    private static final String ATTACH_FROM_EXPORT_MANIFEST="flight.operation.updateFlightStatus";
    private static final String ATTACH_FROM_MARK_FLIGHT_MOVEMENT="flight.operation.saveMarkFlightMovement";
    private static final String ATTACH_FROM_MVT_DETAILS="flight.operation.turnBackFlight";
    private static final String SYSPAR_OF_DEST_COUNTRY_CODE="mail.operations.destinationcountriesforautoattachment";
    private static final String SYSTEM_PARAMETER_MODE_OF_AUTO_ATTACH_AWB="mail.operations.modeofautoattachawb";
	private static final String MODE_OF_AUTO_ATTACH_AWB ="MODE_OF_AUTO_ATTACH_AWB";
	private static final String AUTO_ATTACH_AWB_JOBSCHEDULEVO_PARM = "AUTO_ATTACH_AWB_JOBSCHEDULEVO_PARM";
	private static final String JOB = "JOB";
    private Log log = LogFactory.getLogger("mail.operations");
    @Override 
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.event.EventMapper#mapToEventVO(java.util.HashMap)
	 *	Added by 			: 	A-8353 on 13-Aug-2019
	 * 	Used for 			:	Override the mapToEventVO and get the business vo
	 *	Parameters			:	@param eventParamMap
	 *	Parameters			:	@return
	 *	Parameters			:	@throws Throwable
	 */
	public EventVO mapToEventVO(HashMap<ParameterMap, Object[]> eventParamMap) {
    	//EventVO eventVO =  null;
    	log.log(Log.FINE, "AutoAttachAwbsEventMapper--mapToEventVO");
		Object payload = null;
		Map<String, Object> context = null;
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		MailManifestVO  mailManifestVO=null;
		String modeOfAutoAttach = null;
		String module = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.MODULE))[0];
		String subModule = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.SUBMODULE))[0];
		String eventType = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.EVENT))[0];
		String methodId = (String) ((Object[]) eventParamMap.get(EventConstants.ParameterMap.METHOD_ID))[0];
		Object[] parameters = (Object[])eventParamMap.get(EventConstants.ParameterMap.PARAMETERS);
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);
		Collection<String> codes = new ArrayList<String>();
		codes.add(SYSPAR_OF_DEST_COUNTRY_CODE);
		codes.add(SYSTEM_PARAMETER_MODE_OF_AUTO_ATTACH_AWB);
		Map<String, String> paramResults = null;
		 try {
	    		paramResults = new SharedDefaultProductProxy().findSystemParameterByCodes(codes);  
	    	 } catch (SystemException ex) {
	    		log.log(Log.FINE, "SystemException");
	    	} catch (ProxyException e) {
	    		log.log(Log.FINE, "ProxyException");
			}
		 String DestCountryCodes = paramResults.get(SYSPAR_OF_DEST_COUNTRY_CODE);
		if ((parameters != null) && (parameters.length > 0)){  
	        OperationalFlightVO operationalFlightVo=null;
			if(ATTACH_FROM_MARK_FLIGHT_MOVEMENT.equals(methodId)){
			Collection<MarkFlightMovementVO> flightMovementVOs = null;
			flightMovementVOs = (Collection<MarkFlightMovementVO>) eventParamMap.get(EventConstants.ParameterMap.PARAMETERS)[0];
			/*MarkFlightMovementVO markFlightMovementVO = null;
			if(flightMovementVOs!=null && flightMovementVOs.size()>0){
				markFlightMovementVO =((ArrayList<MarkFlightMovementVO>) flightMovementVOs).get(0);
			}*/
			String legOrginCountry=null;
			String legDestCountry=null;
			for(MarkFlightMovementVO markFlightMovementVO : flightMovementVOs){
			if(markFlightMovementVO!=null){
				if(markFlightMovementVO.getActualDepTime()!=null){  
				if (markFlightMovementVO.getLegOrigin() != null && markFlightMovementVO.getLegOrigin().trim().length()>0) {
					legOrginCountry = findCountryCode(
									markFlightMovementVO.getCompanyCode(), markFlightMovementVO.getLegOrigin().toUpperCase());
					}
				if (markFlightMovementVO.getLegDestination() != null && markFlightMovementVO.getLegDestination().trim().length()>0) {
					legDestCountry = findCountryCode(
									markFlightMovementVO.getCompanyCode(), markFlightMovementVO.getLegDestination().toUpperCase());
					}
		        if(legOrginCountry!=null && legDestCountry!=null){
				if (legDestCountry.equals(legOrginCountry)){
					eventVO.setPayload(null);
                 // return eventVO;
				 }
				else if(!(DestCountryCodes.contains(legDestCountry))){  
					eventVO.setPayload(null);
	                 // return eventVO;
				}
				else{
					operationalFlightVo=constructOperationalFlightVO(markFlightMovementVO);
				}
			   }  
			  }  
		    
			 }
		   }	
	     }
			
			if(ATTACH_FROM_EXPORT_MANIFEST.equals(methodId)){
				FlightStatusVO flightStatusVO = null;
				flightStatusVO =  (FlightStatusVO) parameters[0];  
				if(flightStatusVO!=null ){
					String legOrginCountry=null;
					String legDestination=null;
					String legDestCountry = null;
					if (flightStatusVO.getCurrentStation() != null && flightStatusVO.getCurrentStation().trim().length()>0) {
						
						legOrginCountry =  findCountryCode(  
								flightStatusVO.getCompanyCode(), flightStatusVO.getCurrentStation().toUpperCase());
						legDestination=findDestinationCountryCode(flightStatusVO);
						legDestCountry = findCountryCode(  
								flightStatusVO.getCompanyCode(), legDestination.toUpperCase());
					}
					
		      if(legOrginCountry!=null && legDestCountry!=null)	{
		    	  if (legDestCountry.equals(legOrginCountry)){
						eventVO.setPayload(null);
						return eventVO;
					}
					else if(!(DestCountryCodes.contains(legDestCountry))){  
					eventVO.setPayload(null);
	                  return eventVO;
				}
				else{	
				  operationalFlightVo=constructOperationalFlightVO(flightStatusVO);
				  }
				}
			}
			}
					
				
				
				  
			
	        
			if(ATTACH_FROM_MVT_DETAILS.equals(methodId)){
				operationalFlightVo = attachAwbViaMvt(parameters, eventVO, DestCountryCodes, operationalFlightVo);
				
				
			
				
					
			}
			
			if(operationalFlightVo!=null){
				if (paramResults != null && !paramResults.isEmpty()) {
					modeOfAutoAttach = paramResults.get(SYSTEM_PARAMETER_MODE_OF_AUTO_ATTACH_AWB);
				}
			if(!JOB.equals(modeOfAutoAttach)) {
			try {
				mailManifestVO= new MailtrackingDefaultsProxy().findContainersInFlightForManifest(operationalFlightVo);
			} catch (ProxyException e) {
				log.log(Log.FINE, "ProxyException");
			} catch (SystemException e) {
				log.log(Log.FINE, "SystemException");
				
			}
			containerDetailsVOs.addAll(mailManifestVO.getContainerDetails());
			context = new HashMap<>();
			context.put(COLL_CONTAINERDETAILSVO_PARM, containerDetailsVOs);
			context.put(OPERATIONALFLIGHTVO_PARM, operationalFlightVo);
			}else{
				AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO = constructAutoAttachAWBJobScheduleVO(operationalFlightVo);
				context = new HashMap<>();
				context.put(AUTO_ATTACH_AWB_JOBSCHEDULEVO_PARM, autoAttachAWBJobScheduleVO);
			}
			context.put(MODE_OF_AUTO_ATTACH_AWB, modeOfAutoAttach);
		  }
		}	
		payload = context;
		eventVO.setPayload(payload);  
		eventVO.setMethodId(methodId);
		return eventVO;
    }
    
    /**
	 * @param parameters
	 * @param eventVO
	 * @param DestCountryCodes
	 * @param operationalFlightVo
	 * @return
	 */
	protected OperationalFlightVO attachAwbViaMvt(Object[] parameters, EventVO eventVO, String destCountryCodes,
			OperationalFlightVO operationalFlightVo) {
		FlightMVTVO flightMVTVO = null;
		flightMVTVO =  (FlightMVTVO) parameters[0];
		if(flightMVTVO!=null ){
			Collection<FlightSegmentSummaryVO> flightSegmentSummaryVOs = new ArrayList<>();
			try {
				flightSegmentSummaryVOs = Proxy.getInstance()
						.get(FlightOperationsProxy.class).findFlightSegments(flightMVTVO.getCompanyCode(),
								flightMVTVO.getFlightCarrierID(), flightMVTVO.getFlightNumber(),
								flightMVTVO.getSequenceNumber());
			} catch (SystemException e) {
				log.log(Log.FINE, e);
			}
			if(flightSegmentSummaryVOs != null) {
				Collection<FlightSegmentSummaryVO> flightSegmentSummaryVOsFiltered = filterSummaryVO(flightMVTVO, flightSegmentSummaryVOs);
				if(flightSegmentSummaryVOsFiltered != null && !flightSegmentSummaryVOsFiltered.isEmpty()) {
					for(FlightSegmentSummaryVO flightSegmentSummaryVO : flightSegmentSummaryVOsFiltered) {
						String legOrginCountry=null;
						String legDestCountry=null;
						if(flightMVTVO.getATD()!=null){
							if (flightMVTVO.getLegOrigin() != null && flightMVTVO.getLegOrigin().trim().length()>0) {
								legOrginCountry =findCountryCode(
										flightMVTVO.getCompanyCode(), flightMVTVO.getLegOrigin().toUpperCase());
							}
							if (flightSegmentSummaryVO.getSegmentDestination() != null && flightSegmentSummaryVO.getSegmentDestination().trim().length()>0) {
								legDestCountry = findCountryCode(
										flightMVTVO.getCompanyCode(), flightSegmentSummaryVO.getSegmentDestination().toUpperCase());
							}
							operationalFlightVo = checkAndUpdateOperationalFlightVO(eventVO, destCountryCodes, operationalFlightVo, flightMVTVO,
									legOrginCountry, legDestCountry);
						}
					}
				}
			}
		}
		return operationalFlightVo;
	}
	/**
	 * @param eventVO
	 * @param destCountryCodes
	 * @param operationalFlightVo
	 * @param flightMVTVO
	 * @param legOrginCountry
	 * @param legDestCountry
	 * @return
	 */
	protected OperationalFlightVO checkAndUpdateOperationalFlightVO(EventVO eventVO, String destCountryCodes,
			OperationalFlightVO operationalFlightVo, FlightMVTVO flightMVTVO, String legOrginCountry,
			String legDestCountry) {
		if(legOrginCountry!=null && legDestCountry!=null)	{
				  if (legDestCountry.equals(legOrginCountry)){
					   eventVO.setPayload(null);
					 }
					else if(!(destCountryCodes.contains(legDestCountry))){  
						eventVO.setPayload(null);
					}
					else{
					  	operationalFlightVo=constructOperationalFlightVO(flightMVTVO);
					}
				  }
		return operationalFlightVo;
	}
	/**
	 * @param flightMVTVO
	 * @param flightSegmentSummaryVOs
	 * @return 
	 */
	private Collection<FlightSegmentSummaryVO> filterSummaryVO(FlightMVTVO flightMVTVO, Collection<FlightSegmentSummaryVO> flightSegmentSummaryVOs) {
		Collection<FlightSegmentSummaryVO> flightSegmentSummaryVOsFiltered;
		flightSegmentSummaryVOsFiltered = flightSegmentSummaryVOs.stream().filter(
				flightSegmentSummaryVO -> flightMVTVO.getLegOrigin().equals(flightSegmentSummaryVO.getSegmentOrigin()))
				.collect(Collectors.toList());
		return flightSegmentSummaryVOsFiltered;
	}
    /**
     * 
     * 	Method		:	ContentIDEventMapper.constructOperationalFLightVO
     *	Added by 	:	A-8353 on 14-Aug-2019
     * 	Used for 	:	Construct OperationalFlightVO
     *	Parameters	:	@param markFlightMovementVO
     *	Parameters	:	@return 
     *	Return type	: 	OperationalFlightVO
     */
    private OperationalFlightVO constructOperationalFlightVO(MarkFlightMovementVO markFlightMovementVO) {
    	OperationalFlightVO operationalFlightVo=new OperationalFlightVO() ;
    	
    	
    	operationalFlightVo.setCompanyCode(markFlightMovementVO.getCompanyCode());
    	operationalFlightVo.setCarrierId(markFlightMovementVO.getFlightCarrierId());
    	operationalFlightVo.setCarrierCode(markFlightMovementVO.getFlightCarrierCode());
    	operationalFlightVo.setFlightNumber(markFlightMovementVO.getFlightNumber());
    	operationalFlightVo.setFlightSequenceNumber(markFlightMovementVO.getFlightSequenceNumber());
    	operationalFlightVo.setPol(markFlightMovementVO.getLegOrigin());
    	operationalFlightVo.setActualTimeOfDeparture(markFlightMovementVO.getActualTimeDeparture());
    	operationalFlightVo.setFlightDate(markFlightMovementVO.getFlightDate());
    	operationalFlightVo.setDirection(markFlightMovementVO.getRoute());
    	
    	return operationalFlightVo;
    }
    
    /**
     * 
     * 	Method		:	ContentIDEventMapper.constructOperationalFlightVO
     *	Added by 	:	A-8353 on 14-Aug-2019
     * 	Used for 	:	Construct OperationalFlightVO
     *	Parameters	:	@param flightStatusVO
     *	Return type	: 	OperationalFlightVO
     */
    private OperationalFlightVO constructOperationalFlightVO(FlightStatusVO flightStatusVO) {
    	OperationalFlightVO operationalFlightVo=new OperationalFlightVO();
    	operationalFlightVo.setCompanyCode(flightStatusVO.getCompanyCode());
    	operationalFlightVo.setCarrierId(flightStatusVO.getFlightCarrierId());  
    	operationalFlightVo.setFlightNumber(flightStatusVO.getFlightNumber());
    	operationalFlightVo.setFlightSequenceNumber(flightStatusVO.getFlightSequenceNumber());
    	operationalFlightVo.setPol(flightStatusVO.getCurrentStation());
    	operationalFlightVo.setActualTimeOfDeparture(flightStatusVO.getAtd());
    	
    	
    	return operationalFlightVo;
    }
    /**
     * 
     * 	Method		:	ContentIDEventMapper.constructOperationalFlightVO
     *	Added by 	:	A-8353 on 14-Aug-2019
     * 	Used for 	:	Construct OperationalFlightVO
     *	Parameters	:	@param flightMVTVO
     *	Return type	: 	OperationalFlightVO
     */
    private OperationalFlightVO constructOperationalFlightVO(FlightMVTVO flightMVTVO) {
    	OperationalFlightVO operationalFlightVo=new OperationalFlightVO() ;
    	
    	
    	operationalFlightVo.setCompanyCode(flightMVTVO.getCompanyCode());
    	operationalFlightVo.setCarrierId(flightMVTVO.getFlightCarrierID());
    	operationalFlightVo.setCarrierCode(flightMVTVO.getCarrierCode());
    	operationalFlightVo.setFlightNumber(flightMVTVO.getFlightNumber());
    	operationalFlightVo.setFlightSequenceNumber(flightMVTVO.getSequenceNumber());
    	operationalFlightVo.setPol(flightMVTVO.getLegOrigin());
    	operationalFlightVo.setActualTimeOfDeparture(flightMVTVO.getATD());
    	
    	
    	return operationalFlightVo;
    }
    
    /**
     * 
     * 	Method		:	ContentIDEventMapper.findCountryCode
     *	Added by 	:	A-8353 on 14-Aug-2019
     * 	Used for 	:	Validate Country Code
     *	Parameters	:	@param companyCode
     *	Parameters	:	@param Airport
     *	Parameters	:	@return airportValidationVO.getCountryCode()
     *	Return type	: 	String
     */			
    private String findCountryCode(String companyCode,String Airport){
    	AirportValidationVO airportValidationVO=new AirportValidationVO();
    	try {
    		airportValidationVO =  Proxy.getInstance().get(SharedAreaProductProxy.class).validateAirportCode(  
    				companyCode, Airport);
    	} catch (SystemException ex) {
    		log.log(Log.FINE, "SystemException");
    	} catch (ProxyException e) {
    		log.log(Log.FINE, "ProxyException");
    	}
    	
    	if(airportValidationVO!=null && airportValidationVO.getCountryCode()!=null && airportValidationVO.getCountryCode().trim().length()>0){
    		return airportValidationVO.getCountryCode();
    	}
    	
    	return null;
    }
    /**
     * 
     * 	Method		:	ContentIDEventMapper.findCountryCode
     *	Added by 	:	A-8353 on 14-Aug-2019
     * 	Used for 	:	Validate Country Code
     *	Parameters	:	@param companyCode
     *	Parameters	:	@param Airport
     *	Parameters	:	@return airportValidationVO.getCountryCode()
     *	Return type	: 	String
     */	
    
    private String findDestinationCountryCode(FlightStatusVO flightStatusVO) {
 	   if(flightStatusVO.getCompanyCode()!=null && flightStatusVO.getFlightCarrierId()>=0 && flightStatusVO.getFlightNumber()!=null&&flightStatusVO.getFlightDate()!=null && flightStatusVO.getFlightSequenceNumber()>0){
 		  Collection<FlightValidationVO> flightValidationVOs = null ;
 		   FlightFilterVO flightFilterVO = new FlightFilterVO();
			 flightFilterVO.setCompanyCode(flightStatusVO.getCompanyCode());
			 flightFilterVO.setFlightCarrierId(flightStatusVO.getFlightCarrierId());
			 flightFilterVO.setFlightNumber(flightStatusVO.getFlightNumber());
			 flightFilterVO.setFlightDate(flightStatusVO.getFlightDate());
			 flightFilterVO.setFlightSequenceNumber(flightStatusVO.getFlightSequenceNumber());
			try {
				flightValidationVOs = new FlightOperationsProxy()
						.validateFlightForAirport(flightFilterVO);
			} catch (SystemException e) {
				
				log.log(Log.FINE, "findDestinationCountryCodeException");
			}  
            if (flightValidationVOs!=null){
           for(FlightValidationVO flightValidation : flightValidationVOs ){
        	       if (flightValidation.getLegOrigin()!=null && flightValidation.getLegOrigin().equals(flightStatusVO.getCurrentStation())){
        	    	   return flightValidation.getLegDestination();
        	       }
               }
           }
 	   }
 	   return null;
    }
    /**
     * 
     * 	Method		:	AutoAttachAwbsEventMapper.constructAutoAttachAWBJobScheduleVO
     *	Added by 	:	U-1467 on 23-Sep-2020
     * 	Used for 	:	IASCB-72629
     *	Parameters	:	@param operationalFlightVO
     *	Parameters	:	@return 
     *	Return type	: 	AutoAttachAWBJobScheduleVO
     */
	private AutoAttachAWBJobScheduleVO constructAutoAttachAWBJobScheduleVO(OperationalFlightVO operationalFlightVO) {
		AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO = new AutoAttachAWBJobScheduleVO();
		autoAttachAWBJobScheduleVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		autoAttachAWBJobScheduleVO.setCarrierId(operationalFlightVO.getCarrierId());
		autoAttachAWBJobScheduleVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		autoAttachAWBJobScheduleVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		autoAttachAWBJobScheduleVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		autoAttachAWBJobScheduleVO.setPol(operationalFlightVO.getPol());
		autoAttachAWBJobScheduleVO.setActualTimeOfDeparture(operationalFlightVO.getActualTimeOfDeparture() != null ? operationalFlightVO.getActualTimeOfDeparture().toDisplayFormat() : null);
		return autoAttachAWBJobScheduleVO;
	}
    
    
    
}
				  
			
