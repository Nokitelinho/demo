package com.ibsplc.icargo.business.uld.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

public class SCMJobSchedulerVO extends JobScheduleVO {
	
	private static final String OPR_SCMJOB_CMPCOD = "OPR_SCMJOB_CMPCOD";
	private static final String OPR_SCMJOB_NOOFDAYS = "OPR_SCMJOB_NOOFDAYS";
	private static final String OPR_SCMJOB_ARPGRP = "OPR_SCMJOB_ARPGRP";
	
	private String companyCode;
	private String noOfDays;
	private String airportGroup;
	
	private static HashMap<String,Integer> map;
	
	static {
		map = new HashMap<>();
		map.put(OPR_SCMJOB_CMPCOD,1);
		map.put(OPR_SCMJOB_NOOFDAYS,2);
		map.put(OPR_SCMJOB_ARPGRP,3);
	}
	
	/** (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getPropertyCount()
	 * @return int
	 */
	@Override
	public int getPropertyCount() {
		return map.size() ;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getIndex(java.lang.String)
	 */
	@Override
	public int getIndex(String key) {
		return map.get(key);
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getValue(int)
	 */
	@Override
	public String getValue(int index) {
		switch(index) {
			case 1:
				return companyCode;
			case 2:
				return noOfDays;
			case 3:
				return airportGroup;
			default : return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#setValue(int, java.lang.String)
	 */
	@Override
	public void setValue(int index, String value) {
		switch(index) {
			case 1:
				setCompanyCode(value);
				break;
			case 2:
				setNoOfDays(value);
				break;
			case 3:
				setAirportGroup(value);
				break;
			default : 
		}
	}
	
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * @return Returns the noOfDays.
	 */
	public String getNoOfDays() {
		return noOfDays;
	}

	/**
	 * @param noOfDays The noOfDays to set.
	 */
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	
	/**
	 * @return Returns the airportGroup.
	 */
	public String getAirportGroup() {
		return airportGroup;
	}

	/**
	 * @param airportGroup The airportGroup to set.
	 */
	public void setAirportGroup(String airportGroup) {
		this.airportGroup = airportGroup;
	}

}

