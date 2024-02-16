/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listestimateduldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.capacity.booking.vo.FlightAvailabilityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentForBookingVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListExcessStockAirportsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListExcessStockAirportsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5125
 * Class is used to display FlightDetails
 * 
 *
 */
public class ShowFlightDetailsCommand extends BaseCommand{

private static final String SCREEN_ID = 
		"uld.defaults.stock.findairportswithexcessstock";
private static final String MODULE_NAME = "uld.defaults";
private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
/**
* execute method
* @param invocationContext
* @throws CommandInvocationException
*/
	
public void execute(InvocationContext invocationContext)
throws CommandInvocationException {
		
		log.entering("ListExcessStockAirportsCommand","ListExcessStockAirportsCommand");
    	/**
		 * Obtain the logonAttributes
		 */
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ListExcessStockAirportsForm listExcessStockAirportsForm = (ListExcessStockAirportsForm)invocationContext.screenModel;
    	
    	ListExcessStockAirportsSession listExcessStockAirportsSession =   getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ExcessStockAirportFilterVO excessStockAirportFilterVO= listExcessStockAirportsSession.getExcessStockAirportFilterVO();
    	String days="";
    	log.log(Log.FINE, "listExcessStockAirportsFgetUldType()",
				listExcessStockAirportsForm.getUldType());
		ULDDefaultsDelegate uldDefDel=new ULDDefaultsDelegate();
    	Page<FlightSegmentForBookingVO> fltSegForBookingVos=null;
    	FlightAvailabilityFilterVO fltAvbFilterVO=listExcessStockAirportsSession.getFlightAvailabilityFilterVO();
    	
    	if(fltAvbFilterVO == null)
    	{
    		fltAvbFilterVO=new FlightAvailabilityFilterVO();
    	// by CR requirement changed origin to destination author A-5125 , 
    	fltAvbFilterVO.setOrigin(listExcessStockAirportsForm.getDestination());
    	fltAvbFilterVO.setDestination(excessStockAirportFilterVO.getOrigin());
    	fltAvbFilterVO.setBookingStation(logonAttributes.getStationCode());
    	fltAvbFilterVO.setCombinationFlightsRequired(true);
    	}
    	if(listExcessStockAirportsForm.getDestination()!=null){
    		fltAvbFilterVO.setOrigin(listExcessStockAirportsForm.getDestination());
    	}
    	LocalDate sDate = null;
		LocalDate eDate = null;
		String time = null;
		errors = validateForm(fltAvbFilterVO);
		if(errors != null &&
				errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target = "listFailure";
			return;
		}
		
		if (LocalDate.isStationMapped(fltAvbFilterVO
				.getOrigin())
				&& LocalDate.isStationMapped(fltAvbFilterVO
						.getDestination())) {
			sDate = new LocalDate(fltAvbFilterVO.getOrigin(),
					Location.ARP, true);
			time = sDate.toDisplayTimeOnlyFormat();
			eDate = new LocalDate(fltAvbFilterVO
					.getDestination(), Location.ARP, true);
		} else {
			sDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			eDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		    //new addition by a-2903
			time = sDate.toDisplayTimeOnlyFormat();

		}
		//errors = validateForm(fltAvbFilterVO);
		
		LocalDate shippingDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		//LocalDate currentDate = new LocalDate(fltAvbFilterVO.getOrigin(), Location.ARP, true);
		//LocalDate shippingDate = new LocalDate(fltAvbFilterVO.getOrigin(), Location.ARP, true);
		//shippingDate.setDate(currentDate.toString());
		
		String shpDate=shippingDate.toDisplayDateOnlyFormat();
		fltAvbFilterVO.setShippingDate(sDate
				.setDateAndTime(shpDate.concat(" ").concat("00:00:00")));
		
			try {
				days = findSystemParameter();
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
			}
			
			if (days != null) {
				if (fltAvbFilterVO.getShippingDate() != null
						&& LocalDate
								.isStationMapped(fltAvbFilterVO
										.getOrigin())) {
					LocalDate shippingDateForAddingOffset = new LocalDate(
							fltAvbFilterVO.getOrigin(),
							Location.ARP, fltAvbFilterVO
									.getShippingDate().toCalendar(),
							true);

					LocalDate expArrDate=shippingDateForAddingOffset
				                         .addDays(Integer
											       .parseInt(days));
                   if(expArrDate!=null){
                    expArrDate.setTime("23:59:59");
                    fltAvbFilterVO.setExpectedArrivalDate(expArrDate);
                   }

				}
			}
			fltAvbFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		HashMap<String, String> fltIndexMap = getIndexMap(listExcessStockAirportsSession.getIndexMap(), invocationContext);
			HashMap fltfinalMap = null;
			if (listExcessStockAirportsSession.getFltIndexMap() != null) {
				fltIndexMap = listExcessStockAirportsSession.getFltIndexMap();
			}
			if (fltIndexMap == null) {
				
				fltIndexMap = new HashMap();
				fltIndexMap.put("1", "1");
			}
			String dispPage = listExcessStockAirportsForm.getFltDisplayPage();
			int nAbsoluteIndex = 0;
			String strAbsoluteIndex = (String) fltIndexMap.get(dispPage);
			if (strAbsoluteIndex != null) {
				nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			}
			fltAvbFilterVO.setAbsoluteIndex(nAbsoluteIndex);
			if(listExcessStockAirportsForm.getFltDisplayPage()!=null){
				
				String pageNumber=listExcessStockAirportsForm.getFltDisplayPage();
				fltAvbFilterVO.setPageNumber(Integer.parseInt(pageNumber));
			}
		//Modified by A-7426 as part of ICRD-174537 starts
		if (listExcessStockAirportsForm.getNavigationMode()==null ) {
			log.log(Log.FINE, "PAGINATION MODE FROM FILTER>>>>>");
			fltAvbFilterVO.setTotalRecords(-1);
		} else{
			log.log(Log.FINE, "PAGINATION MODE FROM NAVIGATION>>>>>");
			fltAvbFilterVO.setTotalRecords(listExcessStockAirportsSession
					.getTotalRecords());
			fltAvbFilterVO.setPageNumber(Integer.parseInt(listExcessStockAirportsForm
					.getFltDisplayPage()));
		}
			log.log(Log.INFO, "FLIGHT AVAILABLITY FILTER VO ", fltAvbFilterVO);
			listExcessStockAirportsSession.setFlightAvailabilityFilterVO(fltAvbFilterVO);
		try {
    		
			log.log(Log.FINE, "_____________fltAvbFilterVO__________",
					fltAvbFilterVO);
			listExcessStockAirportsSession.setFlightAvailabilityFilterVO(fltAvbFilterVO);
			fltSegForBookingVos=uldDefDel.listFlightDetails(fltAvbFilterVO);
			log.log(Log.FINE, "__________fltSegForBookingVos__________", fltSegForBookingVos);

			if (fltSegForBookingVos == null)  {



		
			listExcessStockAirportsSession.setFlightSegmentForBookingVOs(null);
			listExcessStockAirportsSession.setFltIndexMap(null);
			ErrorVO error = new ErrorVO(
			    "uld.defaults.stock.listestimateduldstock.NoMatchingRecords");
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "listFailure";
				return;
			}
			else{

				log.log(Log.FINE, " the total records in the    list___",fltSegForBookingVos.getTotalRecordCount());
				listExcessStockAirportsSession.setTotalRecords(fltSegForBookingVos.getTotalRecordCount());

				fltfinalMap = fltIndexMap;
				listExcessStockAirportsSession.setFlightSegmentForBookingVOs(fltSegForBookingVos);
				if (listExcessStockAirportsSession.getFlightSegmentForBookingVOs() != null) {
					fltfinalMap = buildIndexMap(fltIndexMap, listExcessStockAirportsSession.getFlightSegmentForBookingVOs());
					listExcessStockAirportsSession.setFltIndexMap(fltfinalMap);
				}
			} 
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e	);
		}
		invocationContext.target = "listFlightSuccess";
		//Modified by A-7426 as part of ICRD-174537 ends
	}
		
public Collection<ErrorVO> validateForm(FlightAvailabilityFilterVO fltAvbFilterVO) {
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			if ((!(("").equals(fltAvbFilterVO.getOrigin())) && !(("").equals(fltAvbFilterVO
					.getDestination())))
					&& (fltAvbFilterVO.getOrigin().equals(fltAvbFilterVO
							.getDestination()))) {

				error = new ErrorVO(
						"uld.defaults.stock.listestimateduldstock.sameOriginAndDestination");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

			return errors;
		}

private String findSystemParameter() throws BusinessDelegateException {
	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	Map<String, String> systemParamMap = new HashMap<String, String>();
	String systemParam = "uld.defaults.stock.listestimateduldstock.days";
	Collection<String> systemParamList = new ArrayList<String>();
	systemParamList.add(systemParam);
	systemParamMap = sharedDefaultsDelegate
			.findSystemParameterByCodes(systemParamList);

	return systemParamMap.get(systemParam);
}
private HashMap buildIndexMap(HashMap existingMap, Page page) {
	HashMap finalMap = existingMap;
	String indexPage = String.valueOf((page.getPageNumber() + 1));

	boolean isPageExits = false;
	Set<Map.Entry<String, String>> set = existingMap.entrySet();
	for (Map.Entry<String, String> entry : set) {
		String pageNum = entry.getKey();
		if (pageNum.equals(indexPage)) {
			isPageExits = true;
		}
	}

	if (!isPageExits) {
		finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
	}
	return finalMap;
}
	

}


