package com.ibsplc.neoicargo.tracking.helper;

import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class TestUtils {

    public static final LocalDateTime baseDate = LocalDateTime.of(2021, Month.APRIL, 2, 11, 0, 0);

    public static ShipmentMilestonePlanVO createPlan(MilestoneCodeEnum code, String airport, int hoursToAdd, int minutesToAdd,
                                                     int timeZone) {
        return createPlan(code, 100, airport,  hoursToAdd, minutesToAdd, timeZone);
    }

    public static ShipmentMilestonePlanVO createPlan(MilestoneCodeEnum code, String airport) {
        return createPlan(code, 100, airport, 0, 0, 0);
    }

    public static ShipmentMilestonePlanVO createPlan(MilestoneCodeEnum code) {
        return createPlan(code, 100, "FRA", 0, 0, 0);
    }


    public static ShipmentMilestonePlanVO createPlan(MilestoneCodeEnum code, int pieces, String airport, int hoursToAdd,
                                                     int minutesToAdd, int timeZone) {
        var plan = new ShipmentMilestonePlanVO();
        plan.setMilestoneCode(code);
        plan.setPieces(pieces);
        plan.setAirportCode(airport);
        plan.setMilestoneTime(baseDate.plusHours(hoursToAdd + timeZone).plusMinutes(minutesToAdd));
        plan.setMilestoneTimeUTC(baseDate.plusHours(hoursToAdd).plusMinutes(minutesToAdd));
        return plan;
    }

    public static ShipmentMilestonePlanVO createPlanWithFlightData(MilestoneCodeEnum code, String airportCode, String flightNumber, String flightDate) {
        var plan = new ShipmentMilestonePlanVO();
        plan.setMilestoneCode(code);
        plan.setAirportCode(airportCode);
        plan.setFlightDate(LocalDate.parse("2000-01-01"));
        plan.setFlightNumber(flightNumber);
        return plan;
    }

    public static ShipmentMilestoneEventVO createEvent(MilestoneCodeEnum code, String airport, int hoursToAdd, int minutesToAdd,
                                                       int timeZone) {
        return createEvent(code, airport,100, hoursToAdd, minutesToAdd, timeZone);
    }

    public static ShipmentMilestoneEventVO createEvent(MilestoneCodeEnum code) {
        return createEvent(code, "DXB", 100, 0, 0, 0);
    }

    public static ShipmentMilestoneEventVO createEvent(MilestoneCodeEnum code, String airport) {
        return createEvent(code, airport, 100, 0, 0, 0);
    }

    public static ShipmentMilestoneEventVO createEvent(MilestoneCodeEnum code, String airport, int pieces, int hoursToAdd,
                                                       int minutesToAdd, int timeZone) {
        var event = new ShipmentMilestoneEventVO();
        event.setMilestoneCode(code);
        event.setPieces(pieces);
        event.setAirportCode(airport);
        event.setMilestoneTime(baseDate.plusHours(hoursToAdd + timeZone).plusMinutes(minutesToAdd));
        event.setMilestoneTimeUTC(baseDate.plusHours(hoursToAdd).plusMinutes(minutesToAdd));
        return event;
    }

    public static MilestoneVO createMilestone(MilestoneNameEnum milestoneName) {
        return createMilestone(milestoneName, MilestoneStatusEnum.TO_DO);
    }

    public static MilestoneVO createMilestone(MilestoneNameEnum milestoneName, MilestoneStatusEnum status) {
        var milestone = new MilestoneVO();
        milestone.setMilestone(milestoneName);
        milestone.setStatus(status);
        return milestone;
    }

}
