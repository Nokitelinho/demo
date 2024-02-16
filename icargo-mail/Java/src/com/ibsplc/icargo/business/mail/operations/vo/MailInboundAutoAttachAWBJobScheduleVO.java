package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

public class MailInboundAutoAttachAWBJobScheduleVO extends JobScheduleVO {

	public static final String MAL_INB_AUTO_ATTACH_AWB_JOBIDR = "8157";
	public static final String MAL_INB_AUTO_ATTACH_AWB_JOB_NAME = "MAL_INB_AUTO_ATTACH_AWB_JOB";
	public static final String MAL_INB_AUTO_ATTACH_AWB_CARRIERS = "MAL_INB_AUTO_ATTACH_AWB_CARRIERS";
	public static final String MAL_INB_AUTO_ATTACH_AWB_POL_COUNTRIES = "MAL_INB_AUTO_ATTACH_AWB_POL_COUNTRIES";
	public static final String MAL_INB_AUTO_ATTACH_AWB_POU_COUNTRIES = "MAL_INB_AUTO_ATTACH_AWB_POU_COUNTRIES";

	private String companyCode;
	private String carrierCodes;
	private String pointOfLadingCountries;
	private String pointOfUnladingCountries;

	private static HashMap<String, Integer> map;

	static {
		map = new HashMap<>();
		map.put(MAL_INB_AUTO_ATTACH_AWB_CARRIERS, 1);
		map.put(MAL_INB_AUTO_ATTACH_AWB_POL_COUNTRIES, 2);
		map.put(MAL_INB_AUTO_ATTACH_AWB_POU_COUNTRIES, 3);
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
			return getCarrierCodes();
		case 2:
			return getPointOfLadingCountries();
		case 3:
			return getPointOfUnladingCountries();
		default:
			return null;
		}
	}

	@Override
	public void setValue(int i, String s) {
		switch (i) {
		case 1:
			setCarrierCodes(s);
			break;
		case 2:
			setPointOfLadingCountries(s);
			break;
		case 3:
			setPointOfUnladingCountries(s);
			break;
		default:
			break;
		}
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCarrierCodes() {
		return carrierCodes;
	}

	public void setCarrierCodes(String carrierCodes) {
		this.carrierCodes = carrierCodes;
	}

	public String getPointOfLadingCountries() {
		return pointOfLadingCountries;
	}

	public void setPointOfLadingCountries(String pointOfLadingCountries) {
		this.pointOfLadingCountries = pointOfLadingCountries;
	}

	public String getPointOfUnladingCountries() {
		return pointOfUnladingCountries;
	}

	public void setPointOfUnladingCountries(String pointOfUnladingCountries) {
		this.pointOfUnladingCountries = pointOfUnladingCountries;
	}

}
