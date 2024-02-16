package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

public class ProvisionalRateJobScheduleVO extends JobScheduleVO {
	private static final String MAL_OPS_PROV_NOOFRECORDS = "MAL_OPS_PROV_NOOFRECORDS";
	private long noOfRecords;
	private static HashMap<String, Integer> map;
	
	static {
		map = new HashMap<>();
		map.put(MAL_OPS_PROV_NOOFRECORDS, 1);	
		} 
	@Override
	public int getPropertyCount() {
		return map.size();
	}

	@Override
	public int getIndex(String key) {
		return map.get(key);
	}


	
	public String getValue(int index) {
		if(index == 1) {
			return String.valueOf(noOfRecords);
		}
		return null;
}

	@Override
	public void setValue(int index, String key) {
		if(index == 1) {
			setnoOfRecords( Long.parseLong(key));
		}

}

	public void setnoOfRecords(long noOfRecords) {
		this.noOfRecords=noOfRecords;
	}
	
	public long getnoOfRecords() {
		return noOfRecords;
	}
}
