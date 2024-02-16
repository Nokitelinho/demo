package com.ibsplc.neoicargo.mail.util;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("flightValidationUtils")
public class FlightValidationUtils {
    @Autowired
    private FlightOperationsProxy flightOperationsProxy;

    public Collection<FlightValidationVO> validateFlightForAirport(FlightFilterVO flightFilterVO) {
        return
                flightOperationsProxy
                        .validateFlightForAirport(flightFilterVO);
    }
}
