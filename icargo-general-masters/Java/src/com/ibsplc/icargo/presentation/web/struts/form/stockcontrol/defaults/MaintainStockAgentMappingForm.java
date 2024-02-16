/*
 * MaintainStockAgentMappingForm.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-2394
 *
 */
public class MaintainStockAgentMappingForm extends ScreenModel {

	private static final String BUNDLE = "maintainstockagentmapping";

	private String bundle;

	private String stockHolder = "";

	private String agent = "";

	private String checkbox[];

	private String agents[];

	private String stockHolders[];

	// For Pagination

	private String displayPage = "1";

	private String lastPageNum = "0";

	private String currentPage = "1";

	private String totalRecords;

	private boolean hasNext;

	private String currentPageIndex;

	private String commandIdentifier;

	private boolean isErrorPresent;
	
	private String checkAll;
	/**
	 * @return Returns the checkAll.
	 */
	public String getCheckAll() {
		return checkAll;
	}

	/**
	 * @param checkAll The checkAll to set.
	 */
	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
	}

	/**
	 * @return Returns the agent.
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *            The agent to set.
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return Returns the agents.
	 */
	public String[] getAgents() {
		return agents;
	}

	/**
	 * @param agents
	 *            The agents to set.
	 */
	public void setAgents(String[] agents) {
		this.agents = agents;
	}

	/**
	 * @return Returns the checkbox.
	 */
	public String[] getCheckbox() {
		return checkbox;
	}

	/**
	 * @param checkbox
	 *            The checkbox to set.
	 */
	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * @return Returns the stockHolder.
	 */
	public String getStockHolder() {
		return stockHolder;
	}

	/**
	 * @param stockHolder
	 *            The stockHolder to set.
	 */
	public void setStockHolder(String stockHolder) {
		this.stockHolder = stockHolder;
	}

	/**
	 * @return Returns the stockHolders.
	 */
	public String[] getStockHolders() {
		return stockHolders;
	}

	/**
	 * @param stockHolders
	 *            The stockHolders to set.
	 */
	public void setStockHolders(String[] stockHolders) {
		this.stockHolders = stockHolders;
	}

	/**
	 * @return Returns the bUNDLE.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle
	 *            The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.maintainstockagentmapping";
	}

	/**
	 * @return
	 */
	public String getProduct() {
		return "stockcontrol";
	}

	/**
	 * @return
	 */
	public String getSubProduct() {
		return "defaults";
	}

	/**
	 * @return Returns the currentPage.
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the currentPageIndex.
	 */
	public String getCurrentPageIndex() {
		return currentPageIndex;
	}

	/**
	 * @param currentPageIndex
	 *            The currentPageIndex to set.
	 */
	public void setCurrentPageIndex(String currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the hasNext.
	 */
	public boolean isHasNext() {
		return hasNext;
	}

	/**
	 * @param hasNext
	 *            The hasNext to set.
	 */
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	/**
	 * @return Returns the isErrorPresent.
	 */
	public boolean getIsErrorPresent() {
		return isErrorPresent;
	}

	/**
	 * @param isErrorPresent
	 *            The isErrorPresent to set.
	 */
	public void setIsErrorPresent(boolean isErrorPresent) {
		this.isErrorPresent = isErrorPresent;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum
	 *            The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords
	 *            The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return Returns the commandIdentifier.
	 */
	public String getCommandIdentifier() {
		return commandIdentifier;
	}

	/**
	 * @param commandIdentifier
	 *            The commandIdentifier to set.
	 */
	public void setCommandIdentifier(String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
	}
}
