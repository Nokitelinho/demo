package com.ibsplc.neoicargo.cca.component.feature.savecca.helper;

import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.util.CcaParameterUtil;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.masters.ParameterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CUSTOMER_TYPE_CC_COLLECTOR;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.INBOUND_FOP_CREDIT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SYSTEM_PARAMETER_WALK_IN_CUSTOMER;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.NEO_CCA_019;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.NEO_CCA_020;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.NEO_CCA_021;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;

@Slf4j
@Component
@RequiredArgsConstructor
public class CcaBillingTypeHelper {

    private final AirportUtil airportUtil;
    private final CcaParameterUtil ccaParameterUtil;

    public static final Set<String> CC_PAYMENT_TYPES = Set.of("CC", "PC", "CP");

    /**
     * Customer details are required for this validation. This method should be called after processing ccaMasterVO by
     * {@link com.ibsplc.neoicargo.cca.component.feature.savecca.enricher.CustomerDetailsEnricher}
     *
     * @param ccaMasterVO CCA Master with revised AWB
     */
    public void validateBillingType(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("Validate other charge payment type for [{}]", ccaMasterVO.getBusinessId());
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();

        if (CC_PAYMENT_TYPES.contains(revisedAwb.getPayType())) {
            validateChargeCollectType(revisedAwb);
        }

        if (airportUtil.isOwnAirline(revisedAwb)
                && getDetails(revisedAwb.getCcaCustomerDetails(), CustomerType.O) == null) {
            throw new CcaBusinessException((constructErrorVO(NEO_CCA_020, ErrorType.INFO)));
        }
    }

    private void validateChargeCollectType(CcaAwbVO revisedAwb) throws CcaBusinessException {
        var inboundCustomerDetails = getDetails(revisedAwb.getCcaCustomerDetails(), CustomerType.I);
        if (INBOUND_FOP_CREDIT.equals(revisedAwb.getInboundFop())
                && airportUtil.isLastOwnFlownRoute(revisedAwb)
                && inboundCustomerDetails == null) {
            throw new CcaBusinessException((constructErrorVO(NEO_CCA_019, ErrorType.INFO)));
        }

        if (INBOUND_FOP_CREDIT.equals(revisedAwb.getInboundFop()) || isNotWalkInCustomer(revisedAwb)) {
            if (!INBOUND_FOP_CREDIT.equals(revisedAwb.getInboundFop())
                    && airportUtil.isLastOwnFlownRoute(revisedAwb)
                    && inboundCustomerDetails == null) {
                throw new CcaBusinessException((constructErrorVO(NEO_CCA_019, ErrorType.INFO)));
            }
            if (isCollectorCC(inboundCustomerDetails)
                    && isCustomerStationDifferentWithDestination(revisedAwb, inboundCustomerDetails)) {
                throw new CcaBusinessException(constructErrorVO(NEO_CCA_021, ErrorType.INFO));
            }
        }
    }

    private boolean isNotWalkInCustomer(CcaAwbVO revised) {
        var walkInCustomer =
                ccaParameterUtil.getSystemParameter(SYSTEM_PARAMETER_WALK_IN_CUSTOMER, ParameterType.SYSTEM_PARAMETER);
        return !Objects.equals(revised.getInboundCustomerCode(), walkInCustomer);
    }

    @Nullable
    private CcaCustomerDetailVO getDetails(Set<CcaCustomerDetailVO> ccaCustomerDetails, CustomerType customerType) {
        return ccaCustomerDetails == null
                ? null
                : ccaCustomerDetails.stream()
                .filter(ccaCustomerDetailVO -> customerType.equals(ccaCustomerDetailVO.getCustomerType()))
                .findFirst()
                .orElse(null);
    }

    private boolean isCollectorCC(CcaCustomerDetailVO inboundCustomerDetails) {
        return inboundCustomerDetails != null
                && CUSTOMER_TYPE_CC_COLLECTOR.equals(inboundCustomerDetails.getCustomerTypeCode());
    }

    private boolean isCustomerStationDifferentWithDestination(CcaAwbVO revisedAwb,
                                                              CcaCustomerDetailVO inboundCustomerDetails) {
        return !Objects.equals(revisedAwb.getDestination(), inboundCustomerDetails.getStationCode());
    }

}