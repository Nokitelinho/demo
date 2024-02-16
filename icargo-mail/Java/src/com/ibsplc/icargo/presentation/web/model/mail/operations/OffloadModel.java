package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadDSNFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadMailFilter;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

/**
 * @author A-7929
 *
 */
public class OffloadModel extends AbstractScreenModel {
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "mail.operations.ux.offload";
	
	
    private PageResult<OffloadDetails> offloadDetailsPageResult;
    
	private OffloadDetails selectedOffloadDetail;
	
	private Collection<OffloadDetails> selectedOffloadDetails;
	
	private OffloadFilter offloadFilter; //For Container
	
	private OffloadMailFilter offloadMailFilter;  //For mailbags  
	
	private OffloadDSNFilter offloadDSNFilter;  //For DSN
	
	private Map<String, Collection<OneTime>> oneTimeValues;
	
	
	@Override
	public String getProduct() {
		return PRODUCT;
	}
	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}
	public OffloadFilter getOffloadFilter() {
		return offloadFilter;
	}
	public void setOffloadFilter(OffloadFilter offloadFilter) {
		this.offloadFilter = offloadFilter;
	}
	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}
	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}
	public Collection<OffloadDetails> getSelectedOffloadDetails() {
		return selectedOffloadDetails;
	}
	public void setSelectedOffloadDetails(Collection<OffloadDetails> selectedOffloadDetails) {
		this.selectedOffloadDetails = selectedOffloadDetails;
	}
	public OffloadDetails getSelectedOffloadDetail() {
		return selectedOffloadDetail;
	}
	public void setSelectedOffloadDetail(OffloadDetails selectedOffloadDetail) {
		this.selectedOffloadDetail = selectedOffloadDetail;
	}
	public PageResult<OffloadDetails> getOffloadDetailsPageResult() {
		return offloadDetailsPageResult;
	}
	public void setOffloadDetailsPageResult(PageResult<OffloadDetails> offloadDetailsPageResult) {
		this.offloadDetailsPageResult = offloadDetailsPageResult;
	}
	public OffloadMailFilter getOffloadMailFilter() {
		return offloadMailFilter;
	}
	public void setOffloadMailFilter(OffloadMailFilter offloadMailFilter) {
		this.offloadMailFilter = offloadMailFilter;
	}
	public OffloadDSNFilter getOffloadDSNFilter() {
		return offloadDSNFilter;
	}
	public void setOffloadDSNFilter(OffloadDSNFilter offloadDSNFilter) {
		this.offloadDSNFilter = offloadDSNFilter;
	}

}
