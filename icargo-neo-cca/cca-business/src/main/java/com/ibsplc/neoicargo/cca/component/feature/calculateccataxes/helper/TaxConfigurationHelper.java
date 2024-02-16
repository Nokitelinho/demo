package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper;

import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxConfigurationData;
import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.vo.TaxFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SLASH;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxConfigurationHelper {

    private final ServiceProxy<AbstractVO> serviceProxy;

    @Value("${EBL_URL_NBRIDGE}")
    private String eblUrl;

    public void populateConfigurationDetails(TaxFilterVO taxFilterVO) {
        log.info("CalculateTaxFeature -> Entry");
        var taxConfigurationDetails = new HashMap<String, Collection<String>>();
        for (var configType : constructConfigurations()) {
            var consolidatedConfigurationCodes = new HashMap<String, String>();
            taxFilterVO.setConfigurationType(configType);

            var taxConfigurations = getTaxConfigurationData(taxFilterVO);
            if (taxConfigurations != null) {
                Stream.of(taxConfigurations).forEach(tagConfig ->
                        consolidatedConfigurationCodes.putIfAbsent(tagConfig.getConfigurationCode(), configType)
                );
                taxConfigurationDetails.put(configType, new ArrayList<>(consolidatedConfigurationCodes.keySet()));
            }
        }

        taxFilterVO.setTaxConfigurationDetails(taxConfigurationDetails);
        log.info("CalculateTaxFeature -> Exit");
    }

    @Nullable
    private TaxConfigurationData[] getTaxConfigurationData(TaxFilterVO taxFilterVO) {
        try {
            return serviceProxy.dispatch(getTaxCalculateUrl(), POST, taxFilterVO, TaxConfigurationData[].class);
        } catch (SystemException e) {
            log.warn("Cannot calculate taxes", e);
            return null;
        }
    }

    private String getTaxCalculateUrl() {
        return eblUrl
                .concat("tariff")
                .concat(SLASH)
                .concat("findTaxConfigurationCodes");
    }

    private Collection<String> constructConfigurations() {
        return List.of(
                TaxFilterConstant.CONFIGURATIONTYPE_TAX,
                TaxFilterConstant.CONFIGURATIONTYPE_TDS,
                TaxFilterConstant.CONFIGURATIONTYPE_COMMISION
        );
    }

}
