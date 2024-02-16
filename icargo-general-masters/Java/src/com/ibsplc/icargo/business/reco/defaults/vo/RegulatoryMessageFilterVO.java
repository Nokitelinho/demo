package com.ibsplc.icargo.business.reco.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


public class RegulatoryMessageFilterVO extends AbstractVO{

	private String companyCode;
	
	private String rolGroup;
	
	private String startDate;
	
	private String endDate;
	
	private LocalDate currentDate;
	
	private int pageNumber;
	
	private int recordSize;
	
	private int totalRecordCount;
	
	private int absoluteIndex;

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getRolGroup() {
		return rolGroup;
	}

	public void setRolGroup(String rolGroup) {
		this.rolGroup = rolGroup;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getAbsoluteIndex() {
		return absoluteIndex;
	}

	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}
