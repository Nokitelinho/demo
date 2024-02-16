package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.ScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailMonitoringFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailMonitoringSummary;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class MailPerformanceMonitorModel implements ScreenModel{

	/*
	 * The constant variable for product mail
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product operations
	 */
	private static final String SUBPRODUCT = "operations";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.operations.ux.mailPerformance";
	private Map<String, Collection<OneTime>> oneTimeValues;
	private MailMonitoringFilter mailMonitorFilter;
	private Collection<MailMonitoringSummary> mailMonitorSummary;
	private PageResult<Mailbag> missingOriginScanMailbags;
	private PageResult<Mailbag> missingArrivalScanMailbags;
	private PageResult<Mailbag> missingOriginAndArrivalScanMailbags;
	private PageResult<Mailbag> onTimeMailbags;
	private PageResult<Mailbag> delayedMailbags;
	private PageResult<Mailbag> raisedMailbags;
	private PageResult<Mailbag> deniedMailbags;
	private PageResult<Mailbag> approvedMailbags;
	private Collection<Mailbag> onTimeMailbagsList;
	private Collection<Mailbag> delayedMailbagsList;
	private Collection<Mailbag> missingOriginScanMailbagsList;
	private Collection<Mailbag> missingArrivalScanMailbagsList;
	private Collection<Mailbag> missingOriginAndArrivalScanMailbagsList;
	private Collection<Mailbag> forceMajeureRaisedMailbagsList;
	private Collection<Mailbag> forceMajeureApprovedMailbagsList;
	private Collection<Mailbag> forceMajeureDeniedMailbagsList;		 

	private String selectedTab;

	
/*	public PageResult<Mailbag> getForceMajeureRaisedMailbags() {
		return forceMajeureRaisedMailbags;
	}

	public void setForceMajeureRaisedMailbags(PageResult<Mailbag> forceMajeureRaisedMailbags) {
		this.forceMajeureRaisedMailbags = forceMajeureRaisedMailbags;
	}

	public PageResult<Mailbag> getForceMajeureApprovedMailbags() {
		return forceMajeureApprovedMailbags;
	}

	public void setForceMajeureApprovedMailbags(PageResult<Mailbag> forceMajeureApprovedMailbags) {
		this.forceMajeureApprovedMailbags = forceMajeureApprovedMailbags;
	}

	public PageResult<Mailbag> getForceMajeureDeniedMailbags() {
		return forceMajeureDeniedMailbags;
	}

	public void setForceMajeureDeniedMailbags(PageResult<Mailbag> forceMajeureDeniedMailbags) {
		this.forceMajeureDeniedMailbags = forceMajeureDeniedMailbags;
	}*/

	public Collection<Mailbag> getMissingOriginScanMailbagsList() {
		return missingOriginScanMailbagsList;
	}

	public void setMissingOriginScanMailbagsList(Collection<Mailbag> missingOriginScanMailbagsList) {
		this.missingOriginScanMailbagsList = missingOriginScanMailbagsList;
	}

	public Collection<Mailbag> getMissingArrivalScanMailbagsList() {
		return missingArrivalScanMailbagsList;
	}

	public void setMissingArrivalScanMailbagsList(Collection<Mailbag> missingArrivalScanMailbagsList) {
		this.missingArrivalScanMailbagsList = missingArrivalScanMailbagsList;
	}

	public Collection<Mailbag> getMissingOriginAndArrivalScanMailbagsList() {
		return missingOriginAndArrivalScanMailbagsList;
	}

	public void setMissingOriginAndArrivalScanMailbagsList(Collection<Mailbag> missingOriginAndArrivalScanMailbagsList) {
		this.missingOriginAndArrivalScanMailbagsList = missingOriginAndArrivalScanMailbagsList;
	}

	public Collection<Mailbag> getForceMajeureRaisedMailbagsList() {
		return forceMajeureRaisedMailbagsList;
	}

	public void setForceMajeureRaisedMailbagsList(Collection<Mailbag> forceMajeureRaisedMailbagsList) {
		this.forceMajeureRaisedMailbagsList = forceMajeureRaisedMailbagsList;
	}

	public Collection<Mailbag> getForceMajeureApprovedMailbagsList() {
		return forceMajeureApprovedMailbagsList;
	}

	public void setForceMajeureApprovedMailbagsList(Collection<Mailbag> forceMajeureApprovedMailbagsList) {
		this.forceMajeureApprovedMailbagsList = forceMajeureApprovedMailbagsList;
	}

	public Collection<Mailbag> getForceMajeureDeniedMailbagsList() {
		return forceMajeureDeniedMailbagsList;
	}

	public void setForceMajeureDeniedMailbagsList(Collection<Mailbag> forceMajeureDeniedMailbagsList) {
		this.forceMajeureDeniedMailbagsList = forceMajeureDeniedMailbagsList;
	}


	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}
	

	public MailMonitoringFilter getMailMonitorFilter() {
		return mailMonitorFilter;
	}

	public void setMailMonitorFilter(MailMonitoringFilter mailMonitorFilter) {
		this.mailMonitorFilter = mailMonitorFilter;
	}

	public Collection<MailMonitoringSummary> getMailMonitorSummary() {
		return mailMonitorSummary;
	}

	public void setMailMonorSummary(
			Collection<MailMonitoringSummary> mailMonitorSummary) {
		this.mailMonitorSummary = mailMonitorSummary;
	}

	
	public PageResult<Mailbag> getMissingOriginScanMailbags() {
		return missingOriginScanMailbags;
	}

	public void setMissingOriginScanMailbags(
			PageResult<Mailbag> missingOriginScanMailbags) {
		this.missingOriginScanMailbags = missingOriginScanMailbags;
	}

	
	public PageResult<Mailbag> getMissingArrivalScanMailbags() {
		return missingArrivalScanMailbags;
	}

	public void setMissingArrivalScanMailbags(
			PageResult<Mailbag> missingArrivalScanMailbags) {
		this.missingArrivalScanMailbags = missingArrivalScanMailbags;
	}

	public PageResult<Mailbag> getMissingOriginAndArrivalScanMailbags() {
		return missingOriginAndArrivalScanMailbags;
	}

	public void setMissingOriginAndArrivalScanMailbags(
			PageResult<Mailbag> missingOriginAndArrivalScanMailbags) {
		this.missingOriginAndArrivalScanMailbags = missingOriginAndArrivalScanMailbags;
	}

	public PageResult<Mailbag> getOnTimeMailbags() {
		return onTimeMailbags;
	}

	public void setOnTimeMailbags(PageResult<Mailbag> onTimeMailbags) {
		this.onTimeMailbags = onTimeMailbags;
	}

	public PageResult<Mailbag> getDelayedMailbags() {
		return delayedMailbags;
	}

	public void setDelayedMailbags(PageResult<Mailbag> delayedMailbags) {
		this.delayedMailbags = delayedMailbags;
	}

	public void setMailMonitorSummary(
			Collection<MailMonitoringSummary> mailMonitorSummary) {
		this.mailMonitorSummary = mailMonitorSummary;
	}

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

	public Collection<Mailbag> getOnTimeMailbagsList() {
		return onTimeMailbagsList;
	}

	public void setOnTimeMailbagsList(Collection<Mailbag> onTimeMailbagsList) {
		this.onTimeMailbagsList = onTimeMailbagsList;
	}

	public Collection<Mailbag> getDelayedMailbagsList() {
		return delayedMailbagsList;
	}

	public void setDelayedMailbagsList(Collection<Mailbag> delayedMailbagsList) {
		this.delayedMailbagsList = delayedMailbagsList;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	public PageResult<Mailbag> getRaisedMailbags() {
		return raisedMailbags;
	}

	public void setRaisedMailbags(PageResult<Mailbag> raisedMailbags) {
		this.raisedMailbags = raisedMailbags;
	}

	public PageResult<Mailbag> getApprovedMailbags() {
		return approvedMailbags;
	}

	public void setApprovedMailbags(PageResult<Mailbag> approvedMailbags) {
		this.approvedMailbags = approvedMailbags;
	}

	public PageResult<Mailbag> getDeniedMailbags() {
		return deniedMailbags;
	}

	public void setDeniedMailbags(PageResult<Mailbag> deniedMailbags) {
		this.deniedMailbags = deniedMailbags;
	}
	

}
