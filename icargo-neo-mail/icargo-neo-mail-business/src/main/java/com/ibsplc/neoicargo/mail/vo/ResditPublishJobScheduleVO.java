package com.ibsplc.neoicargo.mail.vo;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class ResditPublishJobScheduleVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String PA_CODE = "PA_CODE";
	public static final String NO_OF_DAYS = "NO_OF_DAYS";
	public static final String REPORT_ID = "RESPUBRPT";
	public static final String REPORT_NAME = "RESDIT_PUBLISH";
	private String companyCode;
	private String paCode;
	private int days;
	private String airportCode;
	private String reportID;
	private String scheduleId;
	private static HashMap<String, Integer> map;
	static {
		map = new HashMap<String, Integer>();
		map.put(COMPANY_CODE, 1);
		map.put(NO_OF_DAYS, 2);
		map.put(PA_CODE, 3);
	}

	/** 
	* @param key
	* @return
	*/
	public int getIndex(String key) {
		return map.get(key);
	}

	/** 
	* @param index
	* @return
	*/
	public String getValue(int index) {
		switch (index) {
		case 1: {
			return companyCode;
		}
		case 2: {
			return String.valueOf(days);
		}
		case 3: {
			return paCode;
		}
		case 4: {
			return airportCode;
		}
		case 5: {
			return reportID;
		}
		case 6: {
			return scheduleId;
		}
		default: {
			return null;
		}
		}
	}

	public void setValue(int index, String value) {
		switch (index) {
		case 1: {
			setCompanyCode(value);
			break;
		}
		case 2: {
			setDays(Integer.parseInt(value));
			break;
		}
		case 3: {
			setPaCode(value);
			break;
		}
		case 4: {
			setAirportCode(value);
		}
		case 5: {
			setReportID(value);
		}
		case 6: {
			setScheduleId(value);
		}
		default: {
			setAirportCode(null);
			break;
		}
		}
	}

	/** 
	 * @return
	 */
	public int getPropertyCount() {
		return map.size();
	}
}
