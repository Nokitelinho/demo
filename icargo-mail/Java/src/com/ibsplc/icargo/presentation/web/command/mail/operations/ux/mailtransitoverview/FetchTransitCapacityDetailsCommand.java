package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailtransitoverview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailTransitFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailTransitModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailTransit;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailTransitFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class FetchTransitCapacityDetailsCommand extends AbstractCommand {

	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
	  private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LogonAttributes logonAttributes = getLogonAttribute();
		MailTransitModel mailTransitModel = (MailTransitModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		List<MailTransit> mailTransitList = mailTransitModel.getMailTransitList();
		MailTransitFilter mailTransitFilter = mailTransitModel.getMailTransitFilter();
		HashMap<String, MailTransit> mailTansitCapMap = new HashMap<>();
		ArrayList<MailTransitModel> results = new ArrayList<>();
		if (mailTransitList != null && !mailTransitList.isEmpty()) {
			List<PartnerCarrierVO> partnerCarrierVOs = (ArrayList<PartnerCarrierVO>) mailTrackingDefaultsDelegate
					.findAllPartnerCarriers(logonAttributes.getCompanyCode(),
							logonAttributes.getOwnAirlineCode(), mailTransitFilter.getAirportCode());
			for (MailTransit mailTransit : mailTransitList) {
				try {
					MailTransit mailTran = new MailTransit();
					setFlightFSCapacityDetails(fromDate, toDate, mailTransitFilter, mailTransit, logonAttributes,
							mailTran, partnerCarrierVOs);
					setFlightAllotedDetails(fromDate, toDate, mailTransitFilter, mailTransit, logonAttributes, mailTran,
							partnerCarrierVOs);
					setMailConsumedDetails(mailTransitFilter, mailTransit, mailTran);
					mailTansitCapMap.put(mailTransit.getCarrierCode() + "-" + mailTransit.getMailBagDestination(),
							mailTran);
				} catch (BusinessDelegateException | UnitException e) {
					LOGGER.log(Log.INFO, e);
				}
			}
		}
		if (!mailTansitCapMap.isEmpty()) {
			mailTransitModel.setMailTansitCapMap(mailTansitCapMap);
			results.add(mailTransitModel);
			responseVO.setResults(results);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
		} else {
			responseVO.setStatus("failed");
			actionContext.setResponseVO(responseVO);
			actionContext.addError(new ErrorVO("Error in fetching capacity details"));

		}

	}

	private void setFlightFSCapacityDetails(LocalDate fromDate, LocalDate toDate, MailTransitFilter mailTransitFilter,
			MailTransit mailTransit, LogonAttributes logonAttributes, MailTransit mailTran,
			List<PartnerCarrierVO> partnerCarrierVOs) throws BusinessDelegateException, UnitException {

		Collection<FlightSegmentCapacitySummaryVO> flightCapacity = mailTrackingDefaultsDelegate.findFlightListings(
				setFilterVOforFindFligtListings(fromDate, toDate, mailTransitFilter, mailTransit, logonAttributes));

		if (flightCapacity != null && !flightCapacity.isEmpty()) {
			Measure availableFreeSaleCapacity = new Measure(UnitConstants.WEIGHT, 0);
			double freeSaleULDPostnLDP = 0;
			double freeSaleULDPostnLDC = 0;
			double freeSaleULDPostnMDP = 0;
			for (FlightSegmentCapacitySummaryVO flightMontrCapacity : flightCapacity) {
				if ((logonAttributes.getOwnAirlineCode().equals(flightMontrCapacity.getFlightCarrierCode()))
						|| (!partnerCarrierVOs.isEmpty()
								&& partnerCarrierVOs.stream().anyMatch(partnrCrr -> flightMontrCapacity.getFlightCarrierCode()
										.equals(partnrCrr.getPartnerCarrierCode())))) {
				availableFreeSaleCapacity = Measure.addMeasureValues(availableFreeSaleCapacity,
						flightMontrCapacity.getTotalWeight());
				freeSaleULDPostnLDC = freeSaleULDPostnLDC + flightMontrCapacity.getTotalLowerDeckOne();
				freeSaleULDPostnLDP = freeSaleULDPostnLDP + flightMontrCapacity.getTotalLowerDeckTwo();
				freeSaleULDPostnMDP = freeSaleULDPostnMDP + flightMontrCapacity.getTotalUpperDeckOne();
			}
			}

			mailTran.setAvailableFreeSaleCapacity(availableFreeSaleCapacity.getDisplayValue());
			mailTran.setFreeSaleULDPostnLDC(freeSaleULDPostnLDC);
			mailTran.setFreeSaleULDPostnLDP(freeSaleULDPostnLDP);
			mailTran.setFreeSaleULDPostnMDP(freeSaleULDPostnMDP);
		}
	}

	private void setFlightAllotedDetails(LocalDate fromDate, LocalDate toDate, MailTransitFilter mailTransitFilter,
			MailTransit mailTransit, LogonAttributes logonAttributes, MailTransit mailTran,
			List<PartnerCarrierVO> partnerCarrierVOs) throws BusinessDelegateException, UnitException {
		Page<FlightSegmentCapacitySummaryVO> flightsSegments;
		flightsSegments = mailTrackingDefaultsDelegate.findActiveAllotments(
				setFilterVOforActiveAllotments(fromDate, toDate, mailTransitFilter, mailTransit, logonAttributes));
		if (flightsSegments != null && !flightsSegments.isEmpty()) {
			Measure allotedWeight = new Measure(UnitConstants.WEIGHT, 0);
			double allotedULDPostnLDC = 0;
			double allotedULDPostnLDP = 0;
			double allotedULDPostnMDP = 0;
			for (FlightSegmentCapacitySummaryVO flightSegCapacity : flightsSegments) {
				if ((logonAttributes.getOwnAirlineCode().equals(flightSegCapacity.getFlightCarrierCode()))
						|| (!partnerCarrierVOs.isEmpty()&&partnerCarrierVOs.stream().anyMatch(
						partnrCrr -> flightSegCapacity.getFlightCarrierCode().equals(partnrCrr.getPartnerCarrierCode())))) {
				allotedWeight = Measure.addMeasureValues(allotedWeight, flightSegCapacity.getTotalWeight());
				allotedULDPostnMDP = allotedULDPostnMDP + flightSegCapacity.getTotalUpperDeckOne();
				allotedULDPostnLDC = allotedULDPostnLDC + flightSegCapacity.getTotalLowerDeckOne();
				allotedULDPostnLDP = allotedULDPostnLDP + flightSegCapacity.getTotalLowerDeckTwo();
				}
			}
			mailTran.setAllotedWeight(allotedWeight.getDisplayValue());
			mailTran.setAllotedULDPostnLDC(allotedULDPostnLDC);
			mailTran.setAllotedULDPostnLDP(allotedULDPostnLDP);
			mailTran.setAllotedULDPostnMDP(allotedULDPostnMDP);
		}

	}

	private void setMailConsumedDetails(MailTransitFilter mailTransitFilter, MailTransit mailTransit,
			MailTransit mailTran) throws BusinessDelegateException {

		MailbagVO mailbagVO = mailTrackingDefaultsDelegate
				.findMailConsumed(setFilterVOforMailConsumed(mailTransitFilter, mailTransit));
		if (mailbagVO != null) {
			double weight = 0;
			weight = weight + mailbagVO.getWeight().getDisplayValue();
			mailTran.setUsedCapacityofMailbag(Double.toString(weight) + "/" + mailTran.getAllotedWeight());
		}
	}

	private FlightFilterVO setFilterVOforFindFligtListings(LocalDate fromDate, LocalDate toDate,
			MailTransitFilter mailTransitFilter, MailTransit mailTransit, LogonAttributes logonAttributes) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setFromDate(fromDate.setDate(mailTransitFilter.getFromDate()));
		flightFilterVO.setTimeFrom(mailTransitFilter.getFromTime());
		flightFilterVO.setToDate(toDate.setDate(mailTransitFilter.getToDate()));
		flightFilterVO.setTimeTo(mailTransitFilter.getToTime());
		flightFilterVO.setSegmentOrigin(mailTransitFilter.getAirportCode());
		// flightFilterVO.setCarrierCode(mailTransit.getCarrierCode())
		flightFilterVO.setSegmentDestination(mailTransit.getMailBagDestination());
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		return flightFilterVO;

	}

	private FlightSegmentCapacityFilterVO setFilterVOforActiveAllotments(LocalDate fromDate, LocalDate toDate,
			MailTransitFilter mailTransitFilter, MailTransit mailTransit, LogonAttributes logonAttributes) {
		FlightSegmentCapacityFilterVO flightSegmentCapacityFilterVO = new FlightSegmentCapacityFilterVO();
		flightSegmentCapacityFilterVO.setFromDate(fromDate.setDate(mailTransitFilter.getFromDate()));
		flightSegmentCapacityFilterVO.setToDate(toDate.setDate(mailTransitFilter.getToDate()));
		flightSegmentCapacityFilterVO.setSegmentOrigin(mailTransitFilter.getAirportCode());
		flightSegmentCapacityFilterVO.setAllotmentType("P");
		flightSegmentCapacityFilterVO.setSegmentDestination(mailTransit.getMailBagDestination());
		flightSegmentCapacityFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightSegmentCapacityFilterVO.setPageNumber(1);
		return flightSegmentCapacityFilterVO;

	}

	private MailTransitFilterVO setFilterVOforMailConsumed(MailTransitFilter mailTransitFilter,
			MailTransit mailTransit) {

		MailTransitFilterVO mailTransitFilterVO = new MailTransitFilterVO();
		mailTransitFilterVO.setAirportCode(mailTransitFilter.getAirportCode());
		mailTransitFilterVO.setSegmentDestination(mailTransit.getMailBagDestination());
		if ((mailTransitFilter.getFromDate() != null) && (mailTransitFilter.getFromDate().trim().length() > 0))
        {
            LocalDate date = new LocalDate(mailTransitFilter.getAirportCode(),Location.ARP,false);
               if(mailTransitFilter.getFromTime()!=null && mailTransitFilter.getFromTime().trim().length() > 0){
                   String fromDT=null;
                   fromDT = new StringBuilder(mailTransitFilter.getFromDate()).append(" ") 
                            .append(mailTransitFilter.getFromTime()).append(":00").toString();
                   mailTransitFilterVO.setFlightFromDate((date.setDateAndTime(fromDT)));
               }else{
            	   mailTransitFilterVO.setFlightFromDate((date.setDate(mailTransitFilter.getFromDate())));
               }
        }
		if ((mailTransitFilter.getToDate() != null) && (mailTransitFilter.getToDate().trim().length() > 0))
        {
            LocalDate date = new LocalDate(mailTransitFilter.getAirportCode(),Location.ARP,false);
               if(mailTransitFilter.getToTime()!=null && mailTransitFilter.getToTime().trim().length() > 0){
                   String toDT=null;
                   toDT = new StringBuilder(mailTransitFilter.getToDate()).append(" ") 
                            .append(mailTransitFilter.getToTime()).append(":00").toString();
                   mailTransitFilterVO.setFlightToDate((date.setDateAndTime(toDT)));
               }else{
            	   mailTransitFilterVO.setFlightToDate((date.setDate(mailTransitFilter.getToDate())));
               }
        }
		mailTransitFilterVO.setCarrierCode(mailTransit.getCarrierCode());
		return mailTransitFilterVO;

	}

}
