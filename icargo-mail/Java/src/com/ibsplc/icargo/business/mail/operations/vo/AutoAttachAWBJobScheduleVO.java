package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

import java.util.HashMap;

public class AutoAttachAWBJobScheduleVO extends JobScheduleVO {

    public static final String MAL_AUTO_ATTACH_AWB_JOBIDR = "1042";
    public static final String MAL_AUTO_ATTACH_AWB_JOB_NAME = "MAL_AUTO_ATTACH_AWB_JOB";
    public static final String MAL_AUTO_ATTACH_CMPCOD = "MAL_AUTO_ATTACH_CMPCOD";
    public static final String MAL_AUTO_ATTACH_CARIDR = "MAL_AUTO_ATTACH_CARIDR";
    public static final String MAL_AUTO_ATTACH_CARCOD = "MAL_AUTO_ATTACH_CARCOD";
    public static final String MAL_AUTO_ATTACH_FLTNUM = "MAL_AUTO_ATTACH_FLTNUM";
    public static final String MAL_AUTO_ATTACH_FLTSEQNUM = "MAL_AUTO_ATTACH_FLTSEQNUM";
    public static final String MAL_AUTO_ATTACH_POL = "MAL_AUTO_ATTACH_POL";
    public static final String MAL_AUTO_ATTACH_ATD = "MAL_AUTO_ATTACH_ATD";

    private String companyCode;
    private int carrierId;
    private String carrierCode;
    private String flightNumber;
    private long flightSequenceNumber;
    private String pol;
    private String actualTimeOfDeparture;

    private static HashMap<String,Integer> map;

    static {
        map = new HashMap<>();
        map.put(MAL_AUTO_ATTACH_CMPCOD, 1);
        map.put(MAL_AUTO_ATTACH_CARIDR, 2);
        map.put(MAL_AUTO_ATTACH_CARCOD, 3);
        map.put(MAL_AUTO_ATTACH_FLTNUM, 4);
        map.put(MAL_AUTO_ATTACH_FLTSEQNUM, 5);
        map.put(MAL_AUTO_ATTACH_POL, 6);
        map.put(MAL_AUTO_ATTACH_ATD, 7);
    }
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public long getFlightSequenceNumber() {
        return flightSequenceNumber;
    }

    public void setFlightSequenceNumber(long flightSequenceNumber) {
        this.flightSequenceNumber = flightSequenceNumber;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getActualTimeOfDeparture() {
        return actualTimeOfDeparture;
    }

    public void setActualTimeOfDeparture(String actualTimeOfDeparture) {
        this.actualTimeOfDeparture = actualTimeOfDeparture;
    }
    @Override
    public int getPropertyCount() {
        return map.size();
    }

    @Override
    public int getIndex(String s) {
        return map.get(s);
    }

    @Override
    public String getValue(int i) {
        switch (i) {
            case 1:
                return getCompanyCode();
            case 2:
                return String.valueOf(getCarrierId());
            case 3:
                return getCarrierCode();
            case 4:
                return getFlightNumber();
            case 5:
                return String.valueOf(getFlightSequenceNumber());
            case 6:
                return getPol();
            case 7:
                return getActualTimeOfDeparture();
            default:
                return null;
        }
    }

    @Override
    public void setValue(int i, String s) {
        switch (i) {
            case 1:
                setCompanyCode(s);
                break;
            case 2:
                setCarrierId(Integer.parseInt(s));
                break;
            case 3:
                setCarrierCode(s);
                break;
            case 4:
                setFlightNumber(s);
                break;
            case 5:
                setFlightSequenceNumber(Integer.parseInt(s));
                break;
            case 6:
                setPol(s);
                break;
            case 7:
                setActualTimeOfDeparture(s);
                break;
            default:
                break;
        }
    }
}
