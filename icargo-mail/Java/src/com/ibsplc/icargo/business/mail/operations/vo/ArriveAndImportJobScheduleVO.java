package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * 
 * @author A-8952
 * 
 */

public class ArriveAndImportJobScheduleVO extends JobScheduleVO {
	
	private String offset;
	private String arrivalAndDeliveryMarkedTogether;
	private static Map<String, Integer> map;
	static {
		map = new HashMap<String, Integer>();
		map.put("Hours after ATA for marking mailbag Delivery", 1);
		map.put("Arrival and Delivery of mailbag be marked together", 2);
	}
	
	@Override
	public int getPropertyCount() {
		return map.size();
	}
	
	@Override
	public int getIndex(String paramString) {
		return  map.get(paramString);
	}

	@Override
	public String getValue(int paramInt) {

		switch (paramInt) {
		case 1:
			return this.offset;
		case 2:
			return this.arrivalAndDeliveryMarkedTogether;
		default:
			return null;
		}
	
	}

	@Override
	public void setValue(int paramInt, String paramString) {
		switch (paramInt) {
		case 1:
			setOffset(paramString);
			break;
		case 2:
			setArrivalAndDeliveryMarkedTogether(paramString);
			break;
		default:
			setOffset(null);
			setArrivalAndDeliveryMarkedTogether("Yes");
			break;
		}
	}

	
	/**
	 * @return Returns the offset.
	 */
	public String getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            The offset to set.
	 */
	public void setOffset(String offset) {
		this.offset = offset;
	}

	/**
	 * @return the arrivalAndDeliveryMarkedTogether
	 */
	public String getArrivalAndDeliveryMarkedTogether() {
		return arrivalAndDeliveryMarkedTogether;
	}

	/**
	 * @param arrivalAndDeliveryMarkedTogether the arrivalAndDeliveryMarkedTogether to set
	 */
	public void setArrivalAndDeliveryMarkedTogether(String arrivalAndDeliveryMarkedTogether) {
		this.arrivalAndDeliveryMarkedTogether = arrivalAndDeliveryMarkedTogether;
	}

}
