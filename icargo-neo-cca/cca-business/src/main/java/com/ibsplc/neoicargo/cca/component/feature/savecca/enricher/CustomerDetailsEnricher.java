package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import static com.ibsplc.neoicargo.framework.core.lang.SystemException.UNEXPECTED_SERVER_ERROR;

import com.ibsplc.neoicargo.cca.component.feature.savecca.helper.CcaBillingTypeHelper;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.mapper.CcaCustomerDetailMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerDetailsEnricher extends Enricher<CCAMasterVO> {

    private static final int MAXIMUM_UNIQUE_CUSTOMER_CODE = 6;

    private final CustomerComponent customerComponent;
    private final CcaCustomerDetailMapper ccaCustomerDetailMapper;
    private final CcaBillingTypeHelper ccaBillingTypeHelper;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("CustomerDetailsEnricher -> Invoked");
        final var customerDetailByCode = findCustomerDetail(ccaMasterVO);
        enrichCustomerDetails(customerDetailByCode, ccaMasterVO.getOriginalShipmentVO());
        enrichCustomerDetails(customerDetailByCode, ccaMasterVO.getRevisedShipmentVO());
        // validate accordance between payment type and customer data
        ccaBillingTypeHelper.validateBillingType(ccaMasterVO);
    }

    private Map<String, CustomerModel> findCustomerDetail(CCAMasterVO ccaMasterVO) {
        final var customerCodes = getCustomerCodes(ccaMasterVO);
        final var forkJoinPool = new ForkJoinPool(MAXIMUM_UNIQUE_CUSTOMER_CODE);
        try {
            return forkJoinPool.submit(() -> getCustomerModelsForEachCustomerCode(customerCodes)).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new SystemException(UNEXPECTED_SERVER_ERROR, e);
        } finally {
            forkJoinPool.shutdown();
        }
    }

    private Map<String, CustomerModel> getCustomerModelsForEachCustomerCode(Set<String> customerCodes) {
        return customerCodes.parallelStream()
                .map(this::getCustomerDetails)
                .map(customerModels -> customerModels.stream()
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(CustomerModel::getCustomerCode, Function.identity()));
    }

    @NotNull
    private Set<String> getCustomerCodes(CCAMasterVO ccaMasterVO) {
        final var originalShipmentVO = ccaMasterVO.getOriginalShipmentVO();
        final var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        return Stream.of(originalShipmentVO.getAgentCode(),
                originalShipmentVO.getInboundCustomerCode(),
                originalShipmentVO.getOutboundCustomerCode(),
                revisedShipmentVO.getAgentCode(),
                revisedShipmentVO.getInboundCustomerCode(),
                revisedShipmentVO.getOutboundCustomerCode())
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
    }

    private List<CustomerModel> getCustomerDetails(String customerCode) {
        try {
            return customerComponent.getCustomerDetails(customerCode);
        } catch (CustomerBusinessException e) {
            log.warn("Error while getting customer details by code [{}] ", customerCode, e);
            return Collections.emptyList();
        }
    }

    private void enrichCustomerDetails(Map<String, CustomerModel> customerDetailByCode, CcaAwbVO ccaAwbVO) {
        final var agentDetails = ccaCustomerDetailMapper.constructCcaCustomerDetailVo(
                customerDetailByCode.get(ccaAwbVO.getAgentCode()));
        Optional.ofNullable(agentDetails).ifPresent(details -> details.setCustomerType(CustomerType.A));
        final var inboundCustomerDetails = ccaCustomerDetailMapper.constructCcaCustomerDetailVo(
                customerDetailByCode.get(ccaAwbVO.getInboundCustomerCode()));
        Optional.ofNullable(inboundCustomerDetails).ifPresent(details -> details.setCustomerType(CustomerType.I));
        final var outboundCustomerDetails = ccaCustomerDetailMapper.constructCcaCustomerDetailVo(
                customerDetailByCode.get(ccaAwbVO.getOutboundCustomerCode()));
        Optional.ofNullable(outboundCustomerDetails).ifPresent(details -> details.setCustomerType(CustomerType.O));

        ccaAwbVO.setCcaCustomerDetails(
                Stream.of(agentDetails, inboundCustomerDetails, outboundCustomerDetails)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
        );
    }
}
