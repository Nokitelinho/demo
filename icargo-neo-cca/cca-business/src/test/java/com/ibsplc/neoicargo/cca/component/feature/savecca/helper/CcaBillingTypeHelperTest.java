package com.ibsplc.neoicargo.cca.component.feature.savecca.helper;

import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.util.CcaParameterUtil;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.masters.ParameterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.DESTINATION_AIRPORT;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.ORIGIN_AIRPORT;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getWithPaymentTypeCcaMasterVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CUSTOMER_TYPE_CC_COLLECTOR;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.INBOUND_FOP_CASH;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.INBOUND_FOP_CREDIT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.PAYMENT_TYPE_CHARGE_COLLECT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.PAYMENT_TYPE_PRE_PAID;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SYSTEM_PARAMETER_WALK_IN_CUSTOMER;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.NEO_CCA_019;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.NEO_CCA_020;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.NEO_CCA_021;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaBillingTypeHelperTest {

    @Mock
    private CcaParameterUtil ccaParameterUtil;
    @Mock
    private AirportUtil airportUtil;

    @InjectMocks
    private CcaBillingTypeHelper ccaBillingTypeHelper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        doReturn(true).when(airportUtil).isOwnAirline(any());
        doReturn(true).when(airportUtil).isLastOwnFlownRoute(any());
    }


    @Test
    void validateOtherPaymentTypeShouldPassAllConditionsWhenOtherChargePP() {
        // Given
        var ccaMasterVO = getWithPaymentTypeCcaMasterVO(PAYMENT_TYPE_PRE_PAID, PAYMENT_TYPE_PRE_PAID);

        // When + Then
        assertDoesNotThrow(() -> ccaBillingTypeHelper.validateBillingType(ccaMasterVO));
    }

    @Test
    void validateOtherPaymentTypeShouldPassAllConditionsWhenOtherChargeCC() {
        // Given
        var customerCode = "CUSTOMER_CODE";
        var ccaMasterVO = getWithPaymentTypeCcaMasterVO(PAYMENT_TYPE_CHARGE_COLLECT, PAYMENT_TYPE_CHARGE_COLLECT);
        ccaMasterVO.getRevisedShipmentVO().setInboundFop(INBOUND_FOP_CASH);
        ccaMasterVO.getRevisedShipmentVO().setInboundCustomerCode(customerCode);

        // When + Then
        doReturn(false).when(airportUtil).isOwnAirline(any());
        doReturn(customerCode)
                .when(ccaParameterUtil).getSystemParameter(SYSTEM_PARAMETER_WALK_IN_CUSTOMER, ParameterType.SYSTEM_PARAMETER);

        assertDoesNotThrow(() -> ccaBillingTypeHelper.validateBillingType(ccaMasterVO));
    }

    @Test
    void validateOtherPaymentTypeShouldThrowCcaBusinessExceptionWhenOtherChargeCCAndFopCreditAndCustomerDataIsNull() {
        // Given
        var customerCode = "CUSTOMER_CODE";
        var ccaMasterVO = getWithPaymentTypeCcaMasterVO(PAYMENT_TYPE_CHARGE_COLLECT, PAYMENT_TYPE_CHARGE_COLLECT);
        ccaMasterVO.getRevisedShipmentVO().setInboundFop(INBOUND_FOP_CREDIT);
        ccaMasterVO.getRevisedShipmentVO().setInboundCustomerCode("ANOTHER_CODE");
        ccaMasterVO.getRevisedShipmentVO().setCcaCustomerDetails(null);

        // When + Then
        doReturn(customerCode)
                .when(ccaParameterUtil).getSystemParameter(SYSTEM_PARAMETER_WALK_IN_CUSTOMER, ParameterType.SYSTEM_PARAMETER);

        assertThrows(
                BusinessException.class,
                () -> ccaBillingTypeHelper.validateBillingType(ccaMasterVO));
    }

    @Test
    void validateOtherPaymentTypeShouldThrowCcaBusinessExceptionWhenOtherChargePPAndCustomerDataIsNull() {
        // Given
        var ccaMasterVO = getWithPaymentTypeCcaMasterVO(PAYMENT_TYPE_PRE_PAID, PAYMENT_TYPE_PRE_PAID);
        ccaMasterVO.getRevisedShipmentVO().setCcaCustomerDetails(null);

        // When
        var exception = assertThrows(
                BusinessException.class,
                () -> ccaBillingTypeHelper.validateBillingType(ccaMasterVO));

        // Then
        var actualErrorCodes = exception.getErrors().stream().map(ErrorVO::getErrorCode).collect(Collectors.toSet());
        assertTrue(actualErrorCodes.contains(NEO_CCA_020.getErrorCode()));
    }

    @Test
    void validateOtherPaymentTypeShouldThrowCcaBusinessExceptionWhenOtherChargePPAndCustomerDataIsEmpty() {
        // Given
        var inboundCustomerCode = "CODE1234";
        var ccaMasterVO = getWithPaymentTypeCcaMasterVO(PAYMENT_TYPE_CHARGE_COLLECT, PAYMENT_TYPE_PRE_PAID);
        ccaMasterVO.getRevisedShipmentVO().setCcaCustomerDetails(Set.of());
        ccaMasterVO.getRevisedShipmentVO().setInboundCustomerCode(inboundCustomerCode);

        // When
        doReturn(inboundCustomerCode)
            .when(ccaParameterUtil).getSystemParameter(SYSTEM_PARAMETER_WALK_IN_CUSTOMER, ParameterType.SYSTEM_PARAMETER);

        var exception = assertThrows(
                BusinessException.class,
                () -> ccaBillingTypeHelper.validateBillingType(ccaMasterVO));

        // Then
        var actualErrorCodes = exception.getErrors().stream().map(ErrorVO::getErrorCode).collect(Collectors.toSet());
        assertTrue(actualErrorCodes.contains(NEO_CCA_020.getErrorCode()));
    }

    @Test
    void validateOtherPaymentTypeShouldThrowCcaBusinessExceptionWhenOtherChargeCCAndWalkinCustomerAndCreditAndInboundCustomerDataIsEmpty() {
        // Given
        var customerCode = "CUSTOMER_CODE";
        var ccaMasterVO = getWithPaymentTypeCcaMasterVO(PAYMENT_TYPE_CHARGE_COLLECT, PAYMENT_TYPE_CHARGE_COLLECT);
        ccaMasterVO.getRevisedShipmentVO().setInboundFop(INBOUND_FOP_CREDIT);
        ccaMasterVO.getRevisedShipmentVO().setCcaCustomerDetails(Set.of());
        ccaMasterVO.getRevisedShipmentVO().setInboundCustomerCode(customerCode);

        // When
        doReturn(customerCode)
                .when(ccaParameterUtil).getSystemParameter(SYSTEM_PARAMETER_WALK_IN_CUSTOMER, ParameterType.SYSTEM_PARAMETER);

        var exception = assertThrows(
                BusinessException.class,
                () -> ccaBillingTypeHelper.validateBillingType(ccaMasterVO));

        // Then
        var actualErrorCodes = exception.getErrors().stream().map(ErrorVO::getErrorCode).collect(Collectors.toSet());
        assertTrue(actualErrorCodes.contains(NEO_CCA_019.getErrorCode()));
    }

    @Test
    void validateOtherPaymentTypeShouldThrowCcaBusinessExceptionWhenOtherChargeCCAndInboundCustomerCollectCCAndAirportDifferent() {
        // Given
        var ccaMasterVO = getWithPaymentTypeCcaMasterVO(PAYMENT_TYPE_CHARGE_COLLECT, PAYMENT_TYPE_CHARGE_COLLECT);
        var customerDetail = ccaMasterVO.getRevisedShipmentVO().getCcaCustomerDetails().iterator().next();
        customerDetail.setCustomerTypeCode(CUSTOMER_TYPE_CC_COLLECTOR);
        customerDetail.setCustomerType(CustomerType.I);
        customerDetail.setStationCode(ORIGIN_AIRPORT);
        ccaMasterVO.getRevisedShipmentVO().setDestination(DESTINATION_AIRPORT);

        // When
        var exception = assertThrows(
                BusinessException.class,
                () -> ccaBillingTypeHelper.validateBillingType(ccaMasterVO));

        // Then
        var actualErrorCodes = exception.getErrors().stream().map(ErrorVO::getErrorCode).collect(Collectors.toSet());
        assertTrue(actualErrorCodes.contains(NEO_CCA_021.getErrorCode()));
    }
}