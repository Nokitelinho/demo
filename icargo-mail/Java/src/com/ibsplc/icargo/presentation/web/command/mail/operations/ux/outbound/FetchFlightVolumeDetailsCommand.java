package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVolumeDetailsVO;
import com.ibsplc.icargo.business.shared.uld.vo.AircraftCompatibilityVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightCarrierFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightSegmentVolumeSummary;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class FetchFlightVolumeDetailsCommand extends AbstractCommand {

	private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");
	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		OutboundModel outboundModel = (OutboundModel) actionContext.getScreenModel();
		List<MailAcceptance> mailAcceptanceList = outboundModel.getMailAcceptanceList();
		FlightCarrierFilter flightcarrierfilter = outboundModel.getFlightCarrierFilter();
		Collection<FlightSegmentVolumeDetailsVO> flightSegmentVolumeDetailsVOs = null;
		Collection<FlightFilterVO> flightFilterVOs = createFlightFliterVos(mailAcceptanceList, flightcarrierfilter);
		Set<String> uldTypeSet = new HashSet<>();
		try {
			flightSegmentVolumeDetailsVOs = delegate.fetchFlightVolumeDetails(flightFilterVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			LOGGER.log(Log.INFO, businessDelegateException);
		}
		Map<String, Collection<FlightSegmentVolumeDetailsVO>> flightSegmentHashMap = createFlightSegmentHashMap(
				flightSegmentVolumeDetailsVOs, uldTypeSet);
		Collection<ULDTypeVO> uldTypes = new ArrayList<>();
		if (!uldTypeSet.isEmpty()) {
			uldTypes = findUldTypes(uldTypeSet);
		}

		HashMap<String, Collection<FlightSegmentVolumeSummary>> flightSegmentFinalHashMap = new HashMap<>();
		Collection<FlightSegmentVolumeDetailsVO> newflightSegmentVolumeDetails = null;
		for (String key : flightSegmentHashMap.keySet()) {
			newflightSegmentVolumeDetails = flightSegmentHashMap.get(key);
			List<String> segmentOrgDes = new ArrayList<>();
			Collection<FlightSegmentVolumeSummary> newflightSegmentVolumeDetailsList = new ArrayList<>();
			for (FlightSegmentVolumeDetailsVO flightSegment : newflightSegmentVolumeDetails) {
				double utilldc = 0.0;
				double utilldp = 0.0;
				double utilmdp = 0.0;
				double utilvol = 0.0;
				FlightSegmentVolumeSummary volume = null;
				if (segmentOrgDes.isEmpty() || segmentOrgDes.stream().noneMatch(
						str -> str.equals(flightSegment.getSegmentOrigin() + flightSegment.getSegmentDestination()))) {
					volume = new FlightSegmentVolumeSummary();
					volume.setCompanyCode(flightSegment.getCompanyCode());
					volume.setFlightNumber(flightSegment.getFlightNumber());
					volume.setFlightCarrierIdentifier(flightSegment.getFlightCarrierIdentifier());
					volume.setFlightSequenceNumber(flightSegment.getFlightSequenceNumber());
					volume.setSegmentOrigin(flightSegment.getSegmentOrigin());
					volume.setSegmentDestination(flightSegment.getSegmentDestination());
					volume.setAllotmentId(flightSegment.getAllotmentId());
					volume.setAltLowerDeckOne(flightSegment.getAltLowerDeckOne());
					volume.setAltLowerDeckTwo(flightSegment.getAltLowerDeckTwo());
					volume.setAltUpperDeckOne(flightSegment.getAltUpperDeckOne());
					volume.setAltVolume(flightSegment.getAltVolume());
					segmentOrgDes.add(flightSegment.getSegmentOrigin() + flightSegment.getSegmentDestination());
				} else {
					volume = newflightSegmentVolumeDetailsList.stream()
							.filter(seg -> (seg.getSegmentOrigin() + seg.getSegmentDestination())
									.equals(flightSegment.getSegmentOrigin() + flightSegment.getSegmentDestination()))
							.collect(toSingleton());
					utilvol = volume.getTotUtlVolume().getDisplayValue();
					utilldc = volume.getTotUtlLowerDeckOne();
					utilldp = volume.getTotUtlLowerDeckTwo();
					utilmdp = volume.getTotUtlUpperDeckOne();

				}
				if (flightSegment.getConnum() != null) {
					ULDTypeVO uldType = uldTypes.stream()
							.filter(type -> type.getUldTypeCode().equals(flightSegment.getConnum().substring(0, 3)))
							.collect(toSingleton());
					Optional<AircraftCompatibilityVO> aircraft = uldType.getAircraftCompatibility().stream()
							.filter(air -> air.getAircraftType().equals(flightSegment.getAircraftType())).findFirst();
					if (aircraft.isPresent()) {
						 boolean isWSCLNotAvailable=false;
						if (flightSegment.getConpos() == null) {
							isWSCLNotAvailable=true;
							flightSegment.setConpos(aircraft.get().isUpperDeck() ? "MD" : "LD");
						}
						if (flightSegment.getConpos().equals("MD")) {
							utilvol = utilvol + flightSegment.getTotUtlUpperDeckOne();
							utilmdp = updateCount(utilmdp, isWSCLNotAvailable);
						} else {
							if (flightSegment.getConpos().equals("LD") && uldType.getUldType().equals("C")
								 && !"AMP".equals(uldType.getUldTypeCode())) {
								utilvol = utilvol + flightSegment.getTotUtlLowerDeckOne();
								utilldc = updateCount(utilldc, isWSCLNotAvailable);
							} else {
								utilvol = utilvol + flightSegment.getTotUtlLowerDeckTwo();
								utilldp = updateCount(utilldp, isWSCLNotAvailable);
						}
					}
					}

				}
				volume.setTotUtlLowerDeckOne(utilldc);
				volume.setTotUtlLowerDeckTwo(utilldp);
				volume.setTotUtlUpperDeckOne(utilmdp);
				volume.setTotUtlVolume(new Measure(UnitConstants.VOLUME, utilvol));
				if (newflightSegmentVolumeDetailsList.isEmpty() || !newflightSegmentVolumeDetailsList.stream()
						.anyMatch(seg -> (seg.getSegmentOrigin() + seg.getSegmentDestination())
								.equals(flightSegment.getSegmentOrigin() + flightSegment.getSegmentDestination()))) {
					newflightSegmentVolumeDetailsList.add(volume);
				}

			}
			flightSegmentFinalHashMap.put(key, newflightSegmentVolumeDetailsList);
		}

		ResponseVO responseVO = new ResponseVO();
		List<OutboundModel> results = new ArrayList<>();
		outboundModel.setFlightVolumeDetails(flightSegmentFinalHashMap);
		results.add(outboundModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
	}
	/**
	 * @param utilmdp
	 * @param isWSCLNotAvailable
	 * @return
	 */
	public double updateCount(double utilmdp, boolean isWSCLNotAvailable) {
		if(!isWSCLNotAvailable){
		utilmdp++;
		}
		return utilmdp;
	}

	public static <T> Collector<T, ?, T> toSingleton() {
		return Collectors.collectingAndThen(Collectors.toList(), list -> {
			if (list.size() != 1) {
				throw new IllegalStateException();
			}
			return list.get(0);
		});
	}

	public Collection<FlightFilterVO> createFlightFliterVos(List<MailAcceptance> mailAcceptanceList,
			FlightCarrierFilter flightcarrierfilter) {
		Collection<FlightFilterVO> flightFilterVOs = new ArrayList<>();
		for (MailAcceptance acceptanceList : mailAcceptanceList) {
			FlightFilterVO flightVO = new FlightFilterVO();
			flightVO.setCompanyCode(acceptanceList.getCompanyCode());
			flightVO.setFlightCarrierId(acceptanceList.getCarrierId());
			flightVO.setFlightNumber(acceptanceList.getFlightNumber());
			flightVO.setFlightSequenceNumber(acceptanceList.getFlightSequenceNumber());
			LocalDate date = new LocalDate(flightcarrierfilter.getAirportCode(), Location.ARP, false);
			date.setDate(acceptanceList.getFlightDate());
			flightVO.setFlightDate(date);
			Set<String> carrierCodes = null;
			carrierCodes = new HashSet<>();
			carrierCodes.add(acceptanceList.getCompanyCode());
			flightVO.setCarrierCodes(carrierCodes);
			flightFilterVOs.add(flightVO);

		}
		return flightFilterVOs;
	}

	public Map<String, Collection<FlightSegmentVolumeDetailsVO>> createFlightSegmentHashMap(
			Collection<FlightSegmentVolumeDetailsVO> flightSegmentVolumeDetailsVOs, Set<String> uldTypeSet)
			 {
		Set<String> flightkeySet = new HashSet<>();
		StringBuilder flightKey = null;
		HashMap<String, Collection<FlightSegmentVolumeDetailsVO>> flightSegmentHashMap = new HashMap<>();
		for (FlightSegmentVolumeDetailsVO segmentVolumeDetails : flightSegmentVolumeDetailsVOs) {
			if (segmentVolumeDetails.getConnum() != null) {
				String uld = segmentVolumeDetails.getConnum().substring(0, 3);
				uldTypeSet.add(uld);
			}
			Collection<FlightSegmentVolumeDetailsVO> segmentVolumeDetailsList = new ArrayList<>();
			flightKey = new StringBuilder(segmentVolumeDetails.getFlightCarrierIdentifier())
					.append(segmentVolumeDetails.getFlightNumber())
					.append(segmentVolumeDetails.getFlightSequenceNumber());
			if (flightkeySet.isEmpty()) {
				flightkeySet.add(flightKey.toString());
				segmentVolumeDetailsList.add(segmentVolumeDetails);
				flightSegmentHashMap.put(flightKey.toString(), segmentVolumeDetailsList);
			} else {
				if (flightkeySet.contains(flightKey.toString())) {
					Collection<FlightSegmentVolumeDetailsVO> updatedSegmentVolume = flightSegmentHashMap
							.get(flightKey.toString());
					updatedSegmentVolume.add(segmentVolumeDetails);
					flightSegmentHashMap.put(flightKey.toString(), updatedSegmentVolume);

				} else {
					flightkeySet.add(flightKey.toString());
					segmentVolumeDetailsList.add(segmentVolumeDetails);
					flightSegmentHashMap.put(flightKey.toString(), segmentVolumeDetailsList);
				}
			}

		}
		return flightSegmentHashMap;
	}

	private Collection<ULDTypeVO> findUldTypes(Set<String> uldTypeSet) throws BusinessDelegateException {
		StringBuilder uldTypecode = new StringBuilder();
		for (String uld : uldTypeSet) {
			uldTypecode.append(uld).append(",");
		}
		ULDTypeFilterVO uldTypeFilterVO = new ULDTypeFilterVO();
		uldTypeFilterVO.setCompanyCode(getLogonAttribute().getCompanyCode());
		uldTypeFilterVO.setUldTypeCode(uldTypecode.toString());
		return delegate.findULDTypes(uldTypeFilterVO);
	}

}