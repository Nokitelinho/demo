package com.ibsplc.neoicargo.cca.component.feature.maintainccalist;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;

@Slf4j
@Component
@AllArgsConstructor
public class GetCCAListFeature {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;
    private final AirportUtil airportUtil;

    public List<CCAMasterData> perform(CcaDataFilter ccaDataFilter) {
        log.info("GetCCAListFeature feature invoked with filter -> {}", ccaDataFilter);
        final var originAwbAirportCodes = new HashSet<String>();
        final var destinationAwbAirportCodes = new HashSet<String>();
        var ccaMasterData = ccaDao.getCCAList(List.of(ccaDataFilter))
                .stream()
                .map(getCcaListMasterVO -> {
                    final var constructCCAMasterData =
                            ccaMasterMapper.constructCcaMasterDataFromGetCcaListMasterVO(getCcaListMasterVO);
                    if (getCcaListMasterVO.getCassIndicator() == null) {
                        constructCCAMasterData.setIsAgentCass(false);
                    } else if ("B".equals(getCcaListMasterVO.getCassIndicator())) {
                        constructCCAMasterData.setIsAgentCass(true);
                    } else {
                        getCcaListMasterVO.getShipmentDetailVOs().stream()
                                .filter(shipmentVO -> CCA_RECORD_TYPE_REVISED.equals(shipmentVO.getRecordType()))
                                .forEach(shipmentVO -> {
                                    constructCCAMasterData.setOriginAwbAirportCode(shipmentVO.getOrigin());
                                    constructCCAMasterData.setDestinationAwbAirportCode(shipmentVO.getDestination());
                                    originAwbAirportCodes.add(shipmentVO.getOrigin());
                                    destinationAwbAirportCodes.add(shipmentVO.getDestination());
                                });
                    }
                    return constructCCAMasterData;
                })
                .collect(Collectors.toList());
        if (!originAwbAirportCodes.isEmpty()) {
            setAgentCassForCcaMasterData(originAwbAirportCodes, destinationAwbAirportCodes, ccaMasterData);
        }
        return ccaMasterData;
    }

    private void setAgentCassForCcaMasterData(Set<String> originAwbAirportCodes, Set<String> destinationAwbAirportCodes,
                                              List<CCAMasterData> ccaMasterData) {
        final var originsAirportMap = airportUtil.validateAirportCodes(originAwbAirportCodes);
        final var destinationsAirportMap = airportUtil.validateAirportCodes(destinationAwbAirportCodes);
        ccaMasterData.forEach(masterData -> {
            if (masterData.getIsAgentCass() == null) {
                final var countryCodeForOriginAirportCode = originsAirportMap.get(masterData.getOriginAwbAirportCode());
                final var countryCodeForDestinationAirportCode =
                        destinationsAirportMap.get(masterData.getDestinationAwbAirportCode());
                final var cassIndicator = masterData.getCassIndicator();
                final var isAgentCass = airportUtil.determineAgentCass(
                        cassIndicator, countryCodeForOriginAirportCode, countryCodeForDestinationAirportCode);
                masterData.setIsAgentCass(isAgentCass);
            }
        });
    }

}
