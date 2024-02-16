package com.ibsplc.neoicargo.cca.component.feature.getavailablereasoncodes;

import com.ibsplc.neoicargo.cca.modal.AvailableReasonCodeData;
import com.ibsplc.neoicargo.cca.vo.filter.ReasonCodesFilterVO;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GetAvailableReasonCodesFeature {

    @Value("${EBL_URL_NBRIDGE}")
    private String eblurl;

    private final ContextUtil contextUtil;
    private final ServiceProxy<ReasonCodesFilterVO> serviceProxy;

    public GetAvailableReasonCodesFeature(ContextUtil contextUtil, ServiceProxy<ReasonCodesFilterVO> serviceProxy) {
        this.contextUtil = contextUtil;
        this.serviceProxy = serviceProxy;
    }

    public List<AvailableReasonCodeData> perform() {
        log.info("Invoked GetAvailableReasonCodes feature");
        final var requestData = new ReasonCodesFilterVO("CCA", contextUtil.callerLoginProfile().getCompanyCode());
        final var url = eblurl.concat("cra").concat("/").concat("getReasons");
        return serviceProxy.dispatch(
                url, HttpMethod.POST, requestData, new ParameterizedTypeReference<List<AvailableReasonCodeData>>() {
        })
                .stream()
                .sorted(Comparator.comparing(AvailableReasonCodeData::getParameterDescription))
                .collect(Collectors.toList());
    }

}
