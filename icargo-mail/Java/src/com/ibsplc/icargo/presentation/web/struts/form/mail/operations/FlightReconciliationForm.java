/*
 * FlightReconciliationForm.java Created on July 06, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3217
 *
 */
public class FlightReconciliationForm extends ScreenModel{

	private static final String SCREEN_ID = "mailtracking.defaults.flightreconcilation";

	private static final String PRODUCT_NAME = "mail";

	private static final String SUBPRODUCT_NAME = "operations";

	private static final String BUNDLE = "flightreconcilationResources";

	private String inbound;

	private String outbound;
	
	private String fromScreen;
	
	private String selectedRow;
	
	private String flightMessage;
	
	private String port;
	
	private String closeFlag;
	
	private String flightInfo;
	
	private String listflag;
	
	private String acceptBtnFlag;
	
	private String finaliseBtnFlag;
	
	private String currentDialogOption;
	
	private String[] selectedElements;

	/**
	 * @return the inbound
	 */
	public String getInbound() {
		return inbound;
	}

	/**
	 * @param inbound
	 *            the inbound to set
	 */
	public void setInbound(String inbound) {
		this.inbound = inbound;
	}

	/**
	 * @return the outbound
	 */
	public String getOutbound() {
		return outbound;
	}

	/**
	 * @param outbound
	 *            the outbound to set
	 */
	public void setOutbound(String outbound) {
		this.outbound = outbound;
	}

	public String getProduct() {

		return PRODUCT_NAME;
	}

	public String getScreenId() {

		return SCREEN_ID;
	}

	public String getSubProduct() {

		return SUBPRODUCT_NAME;
	}

	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return the selectedElements
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements the selectedElements to set
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the flightMessage
	 */
	public String getFlightMessage() {
		return flightMessage;
	}

	/**
	 * @param flightMessage the flightMessage to set
	 */
	public void setFlightMessage(String flightMessage) {
		this.flightMessage = flightMessage;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the closeFlag
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag the closeFlag to set
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return the listflag
	 */
	public String getListflag() {
		return listflag;
	}

	/**
	 * @param listflag the listflag to set
	 */
	public void setListflag(String listflag) {
		this.listflag = listflag;
	}

	/**
	 * @return the acceptBtnFlag
	 */
	public String getAcceptBtnFlag() {
		return acceptBtnFlag;
	}

	/**
	 * @param acceptBtnFlag the acceptBtnFlag to set
	 */
	public void setAcceptBtnFlag(String acceptBtnFlag) {
		this.acceptBtnFlag = acceptBtnFlag;
	}

	/**
	 * @return the finaliseBtnFlag
	 */
	public String getFinaliseBtnFlag() {
		return finaliseBtnFlag;
	}

	/**
	 * @param finaliseBtnFlag the finaliseBtnFlag to set
	 */
	public void setFinaliseBtnFlag(String finaliseBtnFlag) {
		this.finaliseBtnFlag = finaliseBtnFlag;
	}

	/**
	 * @return the currentDialogOption
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption the currentDialogOption to set
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * @return Returns the flightInfo.
	 */
	public String getFlightInfo() {
		return flightInfo;
	}

	/**
	 * @param flightInfo The flightInfo to set.
	 */
	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}
	
}
