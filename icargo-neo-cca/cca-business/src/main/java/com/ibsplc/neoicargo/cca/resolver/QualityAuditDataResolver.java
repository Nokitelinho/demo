package com.ibsplc.neoicargo.cca.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibsplc.neoicargo.businessrules.client.fact.DataResolver;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.country.CountryComponent;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component("qualityAuditDataResolver")
@AllArgsConstructor
public class QualityAuditDataResolver extends DataResolver<CcaAwbVO> {

    private static final String AGENT = "agent";
    private static final String CUSTOMER_TYPE = "customerType";
    private static final String IS_AIRLINE_COUNTER_STAFF = "isAirlineCounterStaff";

    private final CustomerComponent customerComponent;
    private final AirportComponent airportComponent;
    private final CountryComponent countryComponent;
    private final ContextUtil contextUtil;
    private final ObjectMapper objectMapper;

    @Override
    public ObjectNode find(CcaAwbVO ccaAwbVO) {

        var objectNode = objectMapper.createObjectNode();
        updateAirportDetails("origin", ccaAwbVO.getOrigin(), objectNode);
        updateAirportDetails("destination", ccaAwbVO.getDestination(), objectNode);
        updateAgentDetails(objectNode, ccaAwbVO);
        objectNode.put("awbOwner", Integer.toString(contextUtil.callerLoginProfile().getOwnAirlineIdentifier())
                .substring(1).equals(ccaAwbVO.getShipmentPrefix()) ? "OWN" : "OAL");
        return objectNode;
    }

    private void updateAgentDetails(ObjectNode objectNode, CcaAwbVO ccaAwbVO) {
        final var emptyProperties = new AtomicBoolean(true);
        if (Objects.nonNull(ccaAwbVO.getAgentCode()) && !ccaAwbVO.getAgentCode().isEmpty()) {
            try {
                customerComponent.getCustomerDetails(ccaAwbVO.getAgentCode()).stream()
                        .findFirst()
                        .ifPresent(customerModel -> {
                            emptyProperties.set(false);
                            updateAirportDetails(AGENT, customerModel.getStationCode(), objectNode);
                            objectNode.put(String.join("", AGENT, "StationCode"), customerModel.getStationCode());
                            objectNode.put(CUSTOMER_TYPE, customerModel.getCustomerType());
                            if (Objects.nonNull(customerModel.getOwnSales())) {
                                objectNode.put(IS_AIRLINE_COUNTER_STAFF, "Y".equals(customerModel.getOwnSales()));
                            } else {
                                objectNode.put(IS_AIRLINE_COUNTER_STAFF, false);
                            }
                        });
            } catch (BusinessException e) {
                log.warn("Invalid Agent");
                log.debug("Cannot get Agent details", e);
                emptyProperties.set(true);
            }
        }

        updateObjectNode(objectNode, emptyProperties);
    }

    private void updateObjectNode(ObjectNode objectNode, AtomicBoolean emptyProperties) {
        if (emptyProperties.get()) {
            objectNode.put(String.join("", AGENT, "AirportCode"), "");
            objectNode.put(String.join("", AGENT, "CityCode"), "");
            objectNode.put(String.join("", AGENT, "CountryCode"), "");
            objectNode.put(String.join("", AGENT, "RegionCode"), "");
            objectNode.put(String.join("", AGENT, "StationCode"), "");
            objectNode.put(CUSTOMER_TYPE, "");
            objectNode.put(IS_AIRLINE_COUNTER_STAFF, false);
        }
    }

    private void updateAirportDetails(String airportType, String airport, ObjectNode objectNode) {
        try {
            var airportModal = airportComponent.validateAirport(airport);
            var countryModal = countryComponent.findCountry(airportModal.getCountryCode());
            objectNode.put(String.join("", airportType, "AirportCode"), airport);
            objectNode.put(String.join("", airportType, "CityCode"), airportModal.getCityCode());
            objectNode.put(String.join("", airportType, "CountryCode"), countryModal.getCountryCode());
            objectNode.put(String.join("", airportType, "RegionCode"), countryModal.getRegionCode());
        } catch (BusinessException e) {
            log.info("Invalid Airport");
            log.debug("Cannot get Airport details", e);
        }
    }

}
