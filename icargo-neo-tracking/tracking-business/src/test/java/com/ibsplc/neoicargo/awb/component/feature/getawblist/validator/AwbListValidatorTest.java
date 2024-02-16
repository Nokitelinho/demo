package com.ibsplc.neoicargo.awb.component.feature.getawblist.validator;

import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AwbListValidatorTest {

    private final AwbListValidator awbListValidator = new AwbListValidator();

    @Test
    public void shouldPassValidRequest() {

        // given
        var request = new AwbRequestVO("020-3456");
        var payload = new ArrayList<AwbRequestVO>();
        payload.add(request);

        // when
        awbListValidator.validate(payload);

        // then
        Assertions.assertEquals(1, payload.size());
        Assertions.assertEquals("020", payload.get(0).getShipmentPrefix());
        Assertions.assertEquals("3456", payload.get(0).getMasterDocumentNumber());
    }

    @Test
    public void shouldFilterEmptyRequest() {

        // given
        var emptyRequest = new AwbRequestVO();
        var payload = new ArrayList<AwbRequestVO>();
        payload.add(emptyRequest);

        // when
        awbListValidator.validate(payload);

        // then
        Assertions.assertEquals(0, payload.size());
    }

    @Test
    public void shouldFilterEmptyAndNullRequest() {

        // given
        var emptyRequest = new AwbRequestVO();
        var payload = new ArrayList<AwbRequestVO>();
        payload.add(emptyRequest);
        payload.add(null);

        // when
        awbListValidator.validate(payload);

        // then
        Assertions.assertEquals(0, payload.size());
    }

    @Test
    public void shouldFilterInvalidRequest() {

        // given
        var invalidRequest1 = new AwbRequestVO("1234");
        var invalidRequest2 = new AwbRequestVO("-1234");
        var invalidRequest3 = new AwbRequestVO("1234-");
        var payload = new ArrayList<AwbRequestVO>();
        payload.add(invalidRequest1);
        payload.add(invalidRequest2);
        payload.add(invalidRequest3);

        // when
        awbListValidator.validate(payload);

        // then
        Assertions.assertEquals(0, payload.size());
    }

}
