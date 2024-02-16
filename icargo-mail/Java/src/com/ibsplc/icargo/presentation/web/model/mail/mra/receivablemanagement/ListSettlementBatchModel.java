package com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.ListSettlementBatchFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailSettlementBatch;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailSettlementBatchDetails;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class ListSettlementBatchModel extends AbstractScreenModel {

	/*
	 * The constant variable for product mail
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product operations
	 */
	private static final String SUBPRODUCT = "mra";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.mra.ux.listsettlementbatch";
	private ListSettlementBatchFilter listSettlementBatchFilter;
    private Collection<MailSettlementBatch> batchLists;	
	private PageResult<MailSettlementBatchDetails> batchDetails;
	private Map<String, Collection<OneTime>> oneTimeValues;
	private String displayPage;
	private String selectedBatchId;
	private int pageSize;
	private int PageNumber;	
	private Collection<MailSettlementBatchDetails>selectedBatchDetail;
	private String selectedBatchDate;
	private String selectedBatchStatus;
	
	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public ListSettlementBatchFilter getListSettlementBatchFilter() {
		return listSettlementBatchFilter;
	}

	public void setListSettlementBatchFilter(ListSettlementBatchFilter listSettlementBatchFilter) {
		this.listSettlementBatchFilter = listSettlementBatchFilter;
	}

	public Collection<MailSettlementBatch> getBatchLists() {
		return batchLists;
	}

	public void setBatchLists(Collection<MailSettlementBatch> batchLists) {
		this.batchLists = batchLists;
	}

	public PageResult<MailSettlementBatchDetails> getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(PageResult<MailSettlementBatchDetails> batchDetails) {
		this.batchDetails = batchDetails;
	}
	
	public String getSelectedBatchId() {
		return selectedBatchId;
	}

	public void setSelectedBatchId(String selectedBatchId) {
		this.selectedBatchId = selectedBatchId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(int pageNumber) {
		PageNumber = pageNumber;
	}
	public Collection<MailSettlementBatchDetails> getSelectedBatchDetail() {
		return selectedBatchDetail;
	}
	public void setSelectedBatchDetail(Collection<MailSettlementBatchDetails> selectedBatchDetail) {
		this.selectedBatchDetail = selectedBatchDetail;
	}
	public String getSelectedBatchDate() {
		return selectedBatchDate;
	}
	public void setSelectedBatchDate(String selectedBatchDate) {
		this.selectedBatchDate = selectedBatchDate;
	}
	public String getSelectedBatchStatus() {
		return selectedBatchStatus;
	}
	public void setSelectedBatchStatus(String selectedBatchStatus) {
		this.selectedBatchStatus = selectedBatchStatus;
	}
	
}