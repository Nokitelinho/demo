package com.ibsplc.neoicargo.cca.util.awb;

import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportModal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Component
@Slf4j
@AllArgsConstructor
public class AirportUtil {

    private final AirportComponent airportComponent;
    private final ContextUtil contextUtil;

    public Map<String, String> validateAirportCodes(Set<String> airportCodes) {
        try {
            return airportComponent.validateAirports(new ArrayList<>(airportCodes)).stream()
                    .collect(Collectors.toMap(AirportModal::getAirportCode, AirportModal::getCountryCode));
        } catch (AirportBusinessException e) {
            log.warn("Cannot receive airports data for codes [{}] ", airportCodes, e);
            return Collections.emptyMap();
        }
    }

    public Boolean determineAgentCass(String cassIndicator, String countryCodeForOriginAirportCode,
                                      String countryCodeForDestinationAirportCode) {
        if ("D".equals(cassIndicator)) {
            // origin country code == destination country code - it means AWB is domestic
            // cassIndicator == "D" and AWB is domestic than agent cass = true
            // cassIndicator == "D" and AWB is international than agent cass = false
            return Objects.equals(countryCodeForOriginAirportCode, countryCodeForDestinationAirportCode);
        } else if ("I".equals(cassIndicator)) {
            // origin country code != destination country code - it means AWB is international
            // cassIndicator == "I" and AWB is domestic than agent cass = false
            // cassIndicator == "I" and AWB is international than agent cass = true
            return !Objects.equals(countryCodeForOriginAirportCode, countryCodeForDestinationAirportCode);
        }
        return false;
    }

    public boolean isLastOwnFlownRoute(CcaAwbVO ccaAwbVO) {
        boolean isOwnCarrier = false;
        var routingDetails = ccaAwbVO.getCcaAwbRoutingDetails();
        if (!isNullOrEmpty(routingDetails)) {
            if (routingDetails.stream().filter(f -> Objects.nonNull(f.getDestination())
                    && f.getDestination().equals(ccaAwbVO.getDestination())).count() > 1) {
                isOwnCarrier = routingDetails.stream()
                        .anyMatch(routing -> Objects.equals(ccaAwbVO.getDestination(), routing.getDestination())
                                && contextUtil.callerLoginProfile().getOwnAirlineCode()
                                .equalsIgnoreCase(routing.getFirstCarrierCode()));
            } else {
                isOwnCarrier = routingDetails.stream()
                        .anyMatch(routing -> Objects.equals(ccaAwbVO.getDestination(), routing.getDestination())
                                && contextUtil.callerLoginProfile().getOwnAirlineCode()
                                .equalsIgnoreCase(routing.getFlightCarrierCode()));
            }
        }
        return isOwnCarrier;
    }

    public boolean isOwnAirline(CcaAwbVO revisedAwb) {
        var ownAirlineIdentifier = contextUtil.callerLoginProfile().getOwnAirlineIdentifier();
        return Integer.toString(ownAirlineIdentifier).substring(1).equals(revisedAwb.getShipmentPrefix());
    }
}
