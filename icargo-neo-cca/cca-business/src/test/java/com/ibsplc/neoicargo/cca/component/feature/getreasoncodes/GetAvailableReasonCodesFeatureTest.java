package com.ibsplc.neoicargo.cca.component.feature.getreasoncodes;

import com.ibsplc.neoicargo.cca.component.feature.getavailablereasoncodes.GetAvailableReasonCodesFeature;
import com.ibsplc.neoicargo.cca.modal.AvailableReasonCodeData;
import com.ibsplc.neoicargo.cca.vo.filter.ReasonCodesFilterVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class GetAvailableReasonCodesFeatureTest {

    @Mock
    private ServiceProxy<ReasonCodesFilterVO> serviceProxy;

    @Mock
    private ContextUtil context;

    @InjectMocks
    private GetAvailableReasonCodesFeature getAvailableReasonCodesFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(getAvailableReasonCodesFeature, "eblurl", "http://localhost:9591/ebl-nbridge/v1/");
    }

    @Test
    void shouldGetAvailableReasonCodes() {
        // When
        doReturn(new LoginProfile()).when(context).callerLoginProfile();
        doReturn(List.of(new AvailableReasonCodeData())).when(serviceProxy).dispatch(anyString(),
                eq(HttpMethod.POST), any(ReasonCodesFilterVO.class), eq(new ParameterizedTypeReference<List<AvailableReasonCodeData>>() {
                }));

        // Then
        assertNotNull(getAvailableReasonCodesFeature.perform());
    }

    @Test
    void shouldGetSortedAvailableReasonCodes() {
        // Given
        final var availableReasonCodes = getAvailableReasonCodes();

        // When
        doReturn(new LoginProfile()).when(context).callerLoginProfile();
        doReturn(availableReasonCodes).when(serviceProxy).dispatch(anyString(),
                eq(HttpMethod.POST), any(ReasonCodesFilterVO.class), eq(new ParameterizedTypeReference<List<AvailableReasonCodeData>>() {
                }));

        // Then
        final var availableReasonCodeData = getAvailableReasonCodesFeature.perform();
        assertNotNull(availableReasonCodeData);

        // And
        final var parameterDescriptions = availableReasonCodeData.stream()
                .map(AvailableReasonCodeData::getParameterDescription)
                .collect(Collectors.toList());
        // should be sorted in alphabet order
        assertTrue(isSorted(parameterDescriptions));
    }

    private List<AvailableReasonCodeData> getAvailableReasonCodes() {
        final var availableReasonCodeData1 = new AvailableReasonCodeData();
        availableReasonCodeData1.setParameterCode("01");
        availableReasonCodeData1.setParameterDescription("Change in Gross/Chargeable Weight");

        final var availableReasonCodeData2 = new AvailableReasonCodeData();
        availableReasonCodeData2.setParameterCode("02");
        availableReasonCodeData2.setParameterDescription("Change in Charges - Weight/Other Charges");

        final var availableReasonCodeData3 = new AvailableReasonCodeData();
        availableReasonCodeData3.setParameterCode("03");
        availableReasonCodeData3.setParameterDescription("Change in Billing Type");

        final var availableReasonCodeData4 = new AvailableReasonCodeData();
        availableReasonCodeData4.setParameterCode("04");
        availableReasonCodeData4.setParameterDescription("Change in Agent");

        final var availableReasonCodeData5 = new AvailableReasonCodeData();
        availableReasonCodeData5.setParameterCode("05");
        availableReasonCodeData5.setParameterDescription("Others");

        final var availableReasonCodeData6 = new AvailableReasonCodeData();
        availableReasonCodeData6.setParameterCode("VD");
        availableReasonCodeData6.setParameterDescription("Auto CCA As Part Of Voiding");
        return List.of(availableReasonCodeData1, availableReasonCodeData2, availableReasonCodeData3,
                availableReasonCodeData4, availableReasonCodeData5, availableReasonCodeData6);
    }

    public static boolean isSorted(List<String> listOfStrings) {
        if (isEmpty(listOfStrings) || listOfStrings.size() == 1) {
            return true;
        }

        final Iterator<String> iter = listOfStrings.iterator();
        String current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

}