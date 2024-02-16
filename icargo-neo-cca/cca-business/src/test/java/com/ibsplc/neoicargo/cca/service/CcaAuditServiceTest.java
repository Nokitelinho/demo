package com.ibsplc.neoicargo.cca.service;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEntryType;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditFieldVO;
import com.ibsplc.neoicargo.framework.tenant.audit.ChangeGroupDetails;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaChargeWithoutTypeAndValue;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.APPROVED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AWB_CHARGE_DETAIL_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AWB_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_CC_TAX;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_COMMISSION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_MASTER_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_NUMBER;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_OTHER_CHARGES;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_FREIGHT;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_OTHER;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PP_TAX;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_STATUS;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_TAXES_AND_COMMISSION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_TOTAL_TAX;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CHARGE_HEAD_CODE_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CHARGE_HEAD_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CHARGE_VALUE_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CREATED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.DELETED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.DUE_AGENT_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.DUE_CARRIER_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.INITIATED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.OCDA;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.OCDC;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.RECOMMENDED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.REJECTED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.UPDATED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.AUTO_CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.INTERNAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
class CcaAuditServiceTest {

    private static final String MASTER_DOCUMENT_NUMBER = "23323311";
    private static final String CCA_000001 = "CCA000001";

    @Mock
    private Mustache.Compiler mustacheCompiler;

    @Mock
    private Template template;

    @InjectMocks
    private CcaAuditServiceImpl ccaAuditService;

    @Captor
    ArgumentCaptor<Map<String, Object>> mustacheContextCaptor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForGetInfoByNewStatus")
    void getInfoByNewStatusReturnsTextWhenStatusIs(CcaStatus status, String templateName, String text) {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(templateName);
        doReturn(text)
                .when(template).execute(anyMap());
        var ccaMasterVO = getCcaMasterVO();

        // When
        var actual = ccaAuditService.getInfoByNewStatus(status, ccaMasterVO);

        // Then
        assertEquals(text, actual);
    }

    private static Stream<Arguments> provideTestDataForGetInfoByNewStatus() {
        return Stream.of(
                Arguments.of(CcaStatus.N, CREATED_TEMPLATE, "Saved"),
                Arguments.of(CcaStatus.I, INITIATED_TEMPLATE, "Initiated"),
                Arguments.of(CcaStatus.C, RECOMMENDED_TEMPLATE, "Recommended"),
                Arguments.of(CcaStatus.A, APPROVED_TEMPLATE, "Approved"),
                Arguments.of(CcaStatus.R, REJECTED_TEMPLATE, "Rejected"),
                Arguments.of(CcaStatus.D, DELETED_TEMPLATE, "Deleted")
        );
    }

    @Test
    void getInfoByNewStatusSetEmptyCcaSourceWhenItIsNull() {
        // Given
        var expectedText = "Initiated";
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(INITIATED_TEMPLATE);
        doReturn(expectedText)
                .when(template).execute(anyMap());
        var ccaMasterVO = getCcaMasterVO();
        ccaMasterVO.setCcaSource(null);

        // When
        var actual = ccaAuditService.getInfoByNewStatus(CcaStatus.I, ccaMasterVO);

        // Then
        assertEquals(expectedText, actual);
    }

    private CCAMasterVO getCcaMasterVO() {
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        ccaMasterVO.setCcaSource(AUTO_CCA_SOURCE);
        ccaMasterVO.setCcaType(INTERNAL);
        return ccaMasterVO;
    }

    @ParameterizedTest
    @MethodSource("provideCcaStatus")
    void getInfoByNewStatusRequiredDataPresentsInContext(CcaStatus status) {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(anyString());
        var ccaMasterVO = getCcaMasterVO();
        ccaMasterVO.setCcaStatus(status);

        // When
        ccaAuditService.getInfoByNewStatus(status, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertEquals(ccaMasterVO.getBusinessId(), context.get(CCA_NUMBER));
        assertEquals(ccaMasterVO.getCcaType(), context.get(CCA_TYPE));
        assertEquals(ccaMasterVO.getCcaSource(), context.get(CCA_SOURCE));
        assertEquals(status.getDescriptionInfo(), context.get(CCA_STATUS));
    }

    private static Stream<CcaStatus> provideCcaStatus() {
        return Stream.of(CcaStatus.N, CcaStatus.C, CcaStatus.A, CcaStatus.R, CcaStatus.D);
    }

    @Test
    void getUpdatedInfoWhenUpdatedCcaMaster() {
        // Given
        var expectedText = "Updated";
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);
        doReturn(expectedText)
                .when(template).execute(anyMap());

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_TYPE);
        field.setChangeGroupId(CCA_MASTER_GROUP);
        field.setFieldModified(true);
        field.setNewValue(ACTUAL);
        field.setOldValue(ccaMasterVO.getCcaType());

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_MASTER_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_MASTER_GROUP, List.of(changeGroupDetail));

        // When
        var actual = ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        assertEquals(expectedText, actual);
    }

    @Test
    void getUpdatedInfoWhenUpdatedCommission() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_COMMISSION);
        field.setChangeGroupId(CCA_AWB_GROUP);
        field.setFieldModified(true);
        field.setNewValue(100.0);
        field.setOldValue(0.0);

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_AWB_GROUP, List.of(changeGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_TAXES_AND_COMMISSION));
        assertTrue(context.containsKey(CCA_COMMISSION));
    }

    @Test
    void getUpdatedInfoWhenUpdatedTaxCC() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_CC_TAX);
        field.setChangeGroupId(CCA_AWB_GROUP);
        field.setFieldModified(true);
        field.setNewValue(100.0);
        field.setOldValue(0.0);

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_AWB_GROUP, List.of(changeGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_TAXES_AND_COMMISSION));
        assertTrue(context.containsKey(CCA_CC_TAX));
    }

    @Test
    void getUpdatedInfoWhenUpdatedTaxPP() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_PP_TAX);
        field.setChangeGroupId(CCA_AWB_GROUP);
        field.setFieldModified(true);
        field.setNewValue(100.0);
        field.setOldValue(0.0);

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_AWB_GROUP, List.of(changeGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_TAXES_AND_COMMISSION));
        assertTrue(context.containsKey(CCA_PP_TAX));
    }

    @Test
    void getUpdatedInfoWhenUpdatedTotalTax() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_TOTAL_TAX);
        field.setChangeGroupId(CCA_AWB_GROUP);
        field.setFieldModified(true);
        field.setNewValue(100.0);
        field.setOldValue(0.0);

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_AWB_GROUP, List.of(changeGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_TAXES_AND_COMMISSION));
        assertTrue(context.containsKey(CCA_TOTAL_TAX));
    }

    @Test
    void getUpdatedInfoWhenUpdatedFreightPaymentType() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_PAYMENT_TYPE);
        field.setChangeGroupId(CCA_AWB_GROUP);
        field.setFieldModified(true);
        field.setOldValue("CC");
        field.setNewValue("PC");

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_AWB_GROUP, List.of(changeGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_PAYMENT_FREIGHT));
        assertFalse(context.containsKey(CCA_PAYMENT_OTHER));
    }

    @Test
    void getUpdatedInfoWhenUpdatedOtherPaymentType() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_PAYMENT_TYPE);
        field.setChangeGroupId(CCA_AWB_GROUP);
        field.setFieldModified(true);
        field.setOldValue("CC");
        field.setNewValue("CP");

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_AWB_GROUP, List.of(changeGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_PAYMENT_OTHER));
        assertFalse(context.containsKey(CCA_PAYMENT_FREIGHT));
    }

    @Test
    void getUpdatedInfoWithoutPaymentTypeInContextWhenUpdatedPaymentTypeWithIncorrectValue() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var ccaMasterVO = getCcaMasterVO();
        var field = new AuditFieldVO();
        field.setField(CCA_PAYMENT_TYPE);
        field.setChangeGroupId(CCA_AWB_GROUP);
        field.setFieldModified(true);
        field.setOldValue("CC");
        field.setNewValue(100);

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(field));

        var changeGroupDetails = Map.of(CCA_AWB_GROUP, List.of(changeGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertFalse(context.containsKey(CCA_PAYMENT_OTHER));
        assertFalse(context.containsKey(CCA_PAYMENT_FREIGHT));
    }

    @SuppressWarnings("unchecked")
    @Test
    void getUpdatedInfoWhenUpdatedCarrierCharges() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var carrierCharge = getCcaChargeWithoutTypeAndValue();
        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setAwbCharges(List.of(carrierCharge));
        carrierCharge.setDueCarrier(true);
        var ccaMasterVO = getCcaMasterVO();
        ccaMasterVO.setRevisedShipmentVO(revised);

        var valueField = new AuditFieldVO();
        valueField.setField(CHARGE_VALUE_FIELD);
        valueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        valueField.setFieldModified(true);
        valueField.setNewValue(10.0);
        valueField.setOldValue(carrierCharge.getCharge());

        var carrierField = new AuditFieldVO();
        carrierField.setField(DUE_CARRIER_FIELD);
        carrierField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        carrierField.setFieldModified(false);
        carrierField.setNewValue("Y");
        carrierField.setOldValue("Y");

        var headField = new AuditFieldVO();
        headField.setField(CHARGE_HEAD_FIELD);
        headField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headField.setFieldModified(false);
        headField.setNewValue(carrierCharge.getChargeHead());
        headField.setOldValue(carrierCharge.getChargeHead());

        var headCodeField = new AuditFieldVO();
        headCodeField.setField(CHARGE_HEAD_CODE_FIELD);
        headCodeField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headCodeField.setFieldModified(false);
        headCodeField.setNewValue(carrierCharge.getChargeHeadCode());
        headCodeField.setOldValue(carrierCharge.getChargeHeadCode());

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(valueField, carrierField, headField, headCodeField));

        var changeGroupDetails = Map.of(CCA_AWB_CHARGE_DETAIL_GROUP, List.of(changeGroupDetail));

        var formattedOcdcChargeView = headField.getNewValue() + " (" + headCodeField.getNewValue() + ")";

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_OTHER_CHARGES));
        assertTrue(context.containsKey(OCDC));
        var ocdcCharges = (Map<Object, Object>) context.get(OCDC);
        assertTrue(ocdcCharges.containsKey(formattedOcdcChargeView));
        assertEquals(valueField.getNewValue().toString(), ocdcCharges.get(formattedOcdcChargeView).toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    void getUpdatedInfoWhenUpdatedCarrierChargesWithTheDifferentValue() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var carrierCharge = getCcaChargeWithoutTypeAndValue();
        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setAwbCharges(List.of(carrierCharge));
        carrierCharge.setDueCarrier(true);
        var ccaMasterVO = getCcaMasterVO();
        ccaMasterVO.setRevisedShipmentVO(revised);

        var deletedValueField = new AuditFieldVO();
        deletedValueField.setField(CHARGE_VALUE_FIELD);
        deletedValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        deletedValueField.setFieldModified(true);
        deletedValueField.setNewValue(null);
        deletedValueField.setOldValue(carrierCharge.getCharge());

        var carrierField = new AuditFieldVO();
        carrierField.setField(DUE_CARRIER_FIELD);
        carrierField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        carrierField.setFieldModified(false);
        carrierField.setNewValue("Y");
        carrierField.setOldValue("Y");

        var headField = new AuditFieldVO();
        headField.setField(CHARGE_HEAD_FIELD);
        headField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headField.setFieldModified(false);
        headField.setNewValue(carrierCharge.getChargeHead());
        headField.setOldValue(carrierCharge.getChargeHead());

        var headCodeField = new AuditFieldVO();
        headCodeField.setField(CHARGE_HEAD_CODE_FIELD);
        headCodeField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headCodeField.setFieldModified(false);
        headCodeField.setNewValue(carrierCharge.getChargeHeadCode());
        headCodeField.setOldValue(carrierCharge.getChargeHeadCode());

        var createdValueField = new AuditFieldVO();
        createdValueField.setField(CHARGE_VALUE_FIELD);
        createdValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        createdValueField.setFieldModified(true);
        createdValueField.setNewValue(10.0);
        createdValueField.setOldValue(null);

        var deletedGroupDetail = new ChangeGroupDetails();
        deletedGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        deletedGroupDetail.setActionType(AuditEntryType.DELETED);
        deletedGroupDetail.setFields(List.of(deletedValueField, carrierField, headField, headCodeField));

        var createdGroupDetail = new ChangeGroupDetails();
        createdGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        createdGroupDetail.setActionType(AuditEntryType.CREATED);
        createdGroupDetail.setFields(List.of(createdValueField, carrierField, headField, headCodeField));

        var changeGroupDetails = Map.of(CCA_AWB_CHARGE_DETAIL_GROUP, List.of(deletedGroupDetail, createdGroupDetail));

        var formattedOcdcChargeView = headField.getNewValue() + " (" + headCodeField.getNewValue() + ")";

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_OTHER_CHARGES));
        assertTrue(context.containsKey(OCDC));
        var ocdcCharges = (Map<Object, Object>) context.get(OCDC);
        assertTrue(ocdcCharges.containsKey(formattedOcdcChargeView));
        assertEquals(createdValueField.getNewValue().toString(), ocdcCharges.get(formattedOcdcChargeView).toString());
    }

    @Test
    void getUpdatedInfoWithoutChargesInContextWhenUpdatedCarrierChargesWithTheSameValue() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var carrierCharge = getCcaChargeWithoutTypeAndValue();
        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setAwbCharges(List.of(carrierCharge));
        carrierCharge.setDueCarrier(true);
        var ccaMasterVO = getCcaMasterVO();
        ccaMasterVO.setRevisedShipmentVO(revised);

        var deletedValueField = new AuditFieldVO();
        deletedValueField.setField(CHARGE_VALUE_FIELD);
        deletedValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        deletedValueField.setFieldModified(true);
        deletedValueField.setNewValue(null);
        deletedValueField.setOldValue(carrierCharge.getCharge());

        var carrierField = new AuditFieldVO();
        carrierField.setField(DUE_CARRIER_FIELD);
        carrierField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        carrierField.setFieldModified(false);
        carrierField.setNewValue("Y");
        carrierField.setOldValue("Y");

        var headField = new AuditFieldVO();
        headField.setField(CHARGE_HEAD_FIELD);
        headField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headField.setFieldModified(false);
        headField.setNewValue(carrierCharge.getChargeHead());
        headField.setOldValue(carrierCharge.getChargeHead());

        var headCodeField = new AuditFieldVO();
        headCodeField.setField(CHARGE_HEAD_CODE_FIELD);
        headCodeField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headCodeField.setFieldModified(false);
        headCodeField.setNewValue(carrierCharge.getChargeHeadCode());
        headCodeField.setOldValue(carrierCharge.getChargeHeadCode());

        var createdValueField = new AuditFieldVO();
        createdValueField.setField(CHARGE_VALUE_FIELD);
        createdValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        createdValueField.setFieldModified(true);
        createdValueField.setNewValue(carrierCharge.getCharge());
        createdValueField.setOldValue(null);

        var deletedGroupDetail = new ChangeGroupDetails();
        deletedGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        deletedGroupDetail.setActionType(AuditEntryType.DELETED);
        deletedGroupDetail.setFields(List.of(deletedValueField, carrierField, headField, headCodeField));

        var createdGroupDetail = new ChangeGroupDetails();
        createdGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        createdGroupDetail.setActionType(AuditEntryType.CREATED);
        createdGroupDetail.setFields(List.of(createdValueField, carrierField, headField, headCodeField));

        var changeGroupDetails = Map.of(CCA_AWB_CHARGE_DETAIL_GROUP, List.of(deletedGroupDetail, createdGroupDetail));

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertFalse(context.containsKey(CCA_OTHER_CHARGES));
        assertFalse(context.containsKey(OCDC));
    }

    @SuppressWarnings("unchecked")
    @Test
    void getUpdatedInfoWhenUpdatedAgentCharges() {
        // Given
        doReturn(template)
                .when(mustacheCompiler).loadTemplate(UPDATED_TEMPLATE);

        var agentCharge = getCcaChargeWithoutTypeAndValue();
        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setAwbCharges(List.of(agentCharge));
        agentCharge.setDueAgent(true);
        var ccaMasterVO = getCcaMasterVO();
        ccaMasterVO.setRevisedShipmentVO(revised);

        var valueField = new AuditFieldVO();
        valueField.setField(CHARGE_VALUE_FIELD);
        valueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        valueField.setFieldModified(true);
        valueField.setNewValue(10.0);
        valueField.setOldValue(agentCharge.getCharge());

        var agentField = new AuditFieldVO();
        agentField.setField(DUE_AGENT_FIELD);
        agentField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        agentField.setFieldModified(false);
        agentField.setNewValue("Y");
        agentField.setOldValue("Y");

        var headField = new AuditFieldVO();
        headField.setField(CHARGE_HEAD_FIELD);
        headField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headField.setFieldModified(false);
        headField.setNewValue(agentCharge.getChargeHead());
        headField.setOldValue(agentCharge.getChargeHead());

        var headCodeField = new AuditFieldVO();
        headCodeField.setField(CHARGE_HEAD_CODE_FIELD);
        headCodeField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headCodeField.setFieldModified(false);
        headCodeField.setNewValue(agentCharge.getChargeHeadCode());
        headCodeField.setOldValue(agentCharge.getChargeHeadCode());

        var changeGroupDetail = new ChangeGroupDetails();
        changeGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        changeGroupDetail.setActionType(AuditEntryType.UPDATED);
        changeGroupDetail.setFields(List.of(valueField, agentField, headField, headCodeField));

        var changeGroupDetails = Map.of(CCA_AWB_CHARGE_DETAIL_GROUP, List.of(changeGroupDetail));

        var formattedOcdaChargeView = headField.getNewValue() + " (" + headCodeField.getNewValue() + ")";

        // When
        ccaAuditService.getUpdatedInfo(changeGroupDetails, ccaMasterVO);

        // Then
        verify(template, atLeastOnce()).execute(mustacheContextCaptor.capture());
        var context = mustacheContextCaptor.getValue();
        assertTrue(context.containsKey(CCA_OTHER_CHARGES));
        assertTrue(context.containsKey(OCDA));
        var ocdaCharges = (Map<Object, Object>) context.get(OCDA);
        assertTrue(ocdaCharges.containsKey(formattedOcdaChargeView));
        assertEquals(valueField.getNewValue().toString(), ocdaCharges.get(formattedOcdaChargeView).toString());
    }

    @Test
    void countUpdatedChargeChangeGroupDetailsReturnsZeroWhenChargeTheSame() {
        // Given
        var carrierCharge = getCcaChargeWithoutTypeAndValue();
        carrierCharge.setDueCarrier(true);

        var oldValueField = new AuditFieldVO();
        oldValueField.setField(CHARGE_VALUE_FIELD);
        oldValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        oldValueField.setFieldModified(true);
        oldValueField.setNewValue(null);
        oldValueField.setOldValue(carrierCharge.getCharge());

        var carrierField = new AuditFieldVO();
        carrierField.setField(DUE_CARRIER_FIELD);
        carrierField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        carrierField.setFieldModified(false);
        carrierField.setNewValue("Y");
        carrierField.setOldValue("Y");

        var headField = new AuditFieldVO();
        headField.setField(CHARGE_HEAD_FIELD);
        headField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headField.setFieldModified(false);
        headField.setNewValue(carrierCharge.getChargeHead());
        headField.setOldValue(carrierCharge.getChargeHead());

        var headCodeField = new AuditFieldVO();
        headCodeField.setField(CHARGE_HEAD_CODE_FIELD);
        headCodeField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headCodeField.setFieldModified(false);
        headCodeField.setNewValue(carrierCharge.getChargeHeadCode());
        headCodeField.setOldValue(carrierCharge.getChargeHeadCode());

        var newValueField = new AuditFieldVO();
        newValueField.setField(CHARGE_VALUE_FIELD);
        newValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        newValueField.setFieldModified(true);
        newValueField.setNewValue(carrierCharge.getCharge());
        newValueField.setOldValue(null);

        var oldGroupDetail = new ChangeGroupDetails();
        oldGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        oldGroupDetail.setActionType(AuditEntryType.DELETED);
        oldGroupDetail.setFields(List.of(oldValueField, carrierField, headField, headCodeField));

        var newGroupDetail = new ChangeGroupDetails();
        newGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        newGroupDetail.setActionType(AuditEntryType.CREATED);
        newGroupDetail.setFields(List.of(newValueField, carrierField, headField, headCodeField));

        var changeGroupDetails = Map.of(CCA_AWB_CHARGE_DETAIL_GROUP, List.of(oldGroupDetail, newGroupDetail));

        // When
        var actual = ccaAuditService.countUpdatedChargeChangeGroupDetails(changeGroupDetails);

        // Then
        assertEquals(0, actual);
    }

    @Test
    void countUpdatedChargeChangeGroupDetailsReturnsOneWhenChargeHasDifferentValue() {
        // Given
        var carrierCharge = getCcaChargeWithoutTypeAndValue();
        carrierCharge.setDueCarrier(true);

        var oldValueField = new AuditFieldVO();
        oldValueField.setField(CHARGE_VALUE_FIELD);
        oldValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        oldValueField.setFieldModified(true);
        oldValueField.setNewValue(null);
        oldValueField.setOldValue(carrierCharge.getCharge());

        var carrierField = new AuditFieldVO();
        carrierField.setField(DUE_CARRIER_FIELD);
        carrierField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        carrierField.setFieldModified(false);
        carrierField.setNewValue("Y");
        carrierField.setOldValue("Y");

        var headField = new AuditFieldVO();
        headField.setField(CHARGE_HEAD_FIELD);
        headField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headField.setFieldModified(false);
        headField.setNewValue(carrierCharge.getChargeHead());
        headField.setOldValue(carrierCharge.getChargeHead());

        var headCodeField = new AuditFieldVO();
        headCodeField.setField(CHARGE_HEAD_CODE_FIELD);
        headCodeField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headCodeField.setFieldModified(false);
        headCodeField.setNewValue(carrierCharge.getChargeHeadCode());
        headCodeField.setOldValue(carrierCharge.getChargeHeadCode());

        var newValueField = new AuditFieldVO();
        newValueField.setField(CHARGE_VALUE_FIELD);
        newValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        newValueField.setFieldModified(true);
        newValueField.setNewValue(1000.0);
        newValueField.setOldValue(null);

        var oldGroupDetail = new ChangeGroupDetails();
        oldGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        oldGroupDetail.setActionType(AuditEntryType.DELETED);
        oldGroupDetail.setFields(List.of(oldValueField, carrierField, headField, headCodeField));

        var newGroupDetail = new ChangeGroupDetails();
        newGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        newGroupDetail.setActionType(AuditEntryType.CREATED);
        newGroupDetail.setFields(List.of(newValueField, carrierField, headField, headCodeField));

        var changeGroupDetails = Map.of(CCA_AWB_CHARGE_DETAIL_GROUP, List.of(oldGroupDetail, newGroupDetail));

        // When
        var actual = ccaAuditService.countUpdatedChargeChangeGroupDetails(changeGroupDetails);

        // Then
        assertEquals(1, actual);
    }

    @Test
    void countUpdatedChargeChangeGroupDetailsReturnsOneWhenChargeHasDifferentAuditedFieldsAndTheSameChargeValue() {
        // Given
        var carrierCharge = getCcaChargeWithoutTypeAndValue();
        carrierCharge.setDueCarrier(true);

        var oldValueField = new AuditFieldVO();
        oldValueField.setField(CHARGE_VALUE_FIELD);
        oldValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        oldValueField.setFieldModified(true);
        oldValueField.setNewValue(null);
        oldValueField.setOldValue(carrierCharge.getCharge());

        var carrierField = new AuditFieldVO();
        carrierField.setField(DUE_CARRIER_FIELD);
        carrierField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        carrierField.setFieldModified(false);
        carrierField.setNewValue("Y");
        carrierField.setOldValue("Y");

        var headField = new AuditFieldVO();
        headField.setField(CHARGE_HEAD_FIELD);
        headField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headField.setFieldModified(false);
        headField.setNewValue(carrierCharge.getChargeHead());
        headField.setOldValue(carrierCharge.getChargeHead());

        var headCodeField = new AuditFieldVO();
        headCodeField.setField(CHARGE_HEAD_CODE_FIELD);
        headCodeField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        headCodeField.setFieldModified(false);
        headCodeField.setNewValue(carrierCharge.getChargeHeadCode());
        headCodeField.setOldValue(carrierCharge.getChargeHeadCode());

        var newValueField = new AuditFieldVO();
        newValueField.setField(CHARGE_VALUE_FIELD);
        newValueField.setChangeGroupId(CCA_AWB_CHARGE_DETAIL_GROUP);
        newValueField.setFieldModified(true);
        newValueField.setNewValue(carrierCharge.getCharge());
        newValueField.setOldValue(null);

        var oldGroupDetail = new ChangeGroupDetails();
        oldGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        oldGroupDetail.setActionType(AuditEntryType.DELETED);
        oldGroupDetail.setFields(List.of(oldValueField, carrierField, headField, headCodeField));

        var newGroupDetail = new ChangeGroupDetails();
        newGroupDetail.setId(CCA_AWB_CHARGE_DETAIL_GROUP);
        newGroupDetail.setActionType(AuditEntryType.CREATED);
        newGroupDetail.setFields(List.of(newValueField, carrierField, headCodeField));

        var changeGroupDetails = Map.of(CCA_AWB_CHARGE_DETAIL_GROUP, List.of(oldGroupDetail, newGroupDetail));

        // When
        var actual = ccaAuditService.countUpdatedChargeChangeGroupDetails(changeGroupDetails);

        // Then
        assertEquals(1, actual);
    }
}