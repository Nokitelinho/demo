/*
 * CarditEnquiryVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class CarditEnquiryVO extends AbstractVO {
	
	private String  companyCode;
	private String  searchMode;
	private String  unsendResditEvent;
	private String  flightType; 
	private String  resditEventPort;
	private String resditEventCode;
	private  OperationalFlightVO operationalFlightVo;
	
	
	
	 private Collection<ContainerVO> containerVos;
	
	/*
     *Collection
     */
    private Collection<MailbagVO> mailbagVos;
    /*
     *Collection
     */
    private Collection<DespatchDetailsVO> despatchDetailVos;
    /*
     *Collection<ConsignmentDocumentVO>
     */
    private Collection<ConsignmentDocumentVO> consignmentDocumentVos;
	/**
	 * @return Returns the consignmentDocumentVos.
	 */
	public Collection<ConsignmentDocumentVO> getConsignmentDocumentVos() {
		return consignmentDocumentVos;
	}
	/**
	 * @param consignmentDocumentVos The consignmentDocumentVos to set.
	 */
	public void setConsignmentDocumentVos(
			Collection<ConsignmentDocumentVO> consignmentDocumentVos) {
		this.consignmentDocumentVos = consignmentDocumentVos;
	}
	/**
	 * @return Returns the despatchDetailVos.
	 */
	public Collection<DespatchDetailsVO> getDespatchDetailVos() {
		return despatchDetailVos;
	}
	/**
	 * @param despatchDetailVos The despatchDetailVos to set.
	 */
	public void setDespatchDetailVos(Collection<DespatchDetailsVO> despatchDetailVos) {
		this.despatchDetailVos = despatchDetailVos;
	}
	/**
	 * @return Returns the mailbagVos.
	 */
	public Collection<MailbagVO> getMailbagVos() {
		return mailbagVos;
	}
	/**
	 * @param mailbagVos The mailbagVos to set.
	 */
	public void setMailbagVos(Collection<MailbagVO> mailbagVos) {
		this.mailbagVos = mailbagVos; 
	}
	
	
	
	
	
	
	/**
	 * @return Returns the searchMode.
	 */
	public String getSearchMode() {
		return searchMode;
	}
	/**
	 * @param searchMode The searchMode to set.
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	/**
	 * @return Returns the unsendResditEvent.
	 */
	public String getUnsendResditEvent() {
		return this.unsendResditEvent;
	}
	/**
	 * @param unsendResditEvent The unsendResditEvent to set.
	 */
	public void setUnsendResditEvent(String unsendResditEvent) {
		this.unsendResditEvent = unsendResditEvent;
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
	 * @return Returns the flightType.
	 */
	public String getFlightType() {
		return flightType;
	}
	/**
	 * @param flightType The flightType to set.
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public Collection<ContainerVO> getContainerVos() {
		return containerVos;
	}
	public void setContainerVos(Collection<ContainerVO> containerVos) {
		this.containerVos = containerVos;
	}
	public String getResditEventCode() {
		return resditEventCode;
	}
	public void setResditEventCode(String resditEventCode) {
		this.resditEventCode = resditEventCode;
	}
	public String getResditEventPort() {
		return resditEventPort;
	}
	public void setResditEventPort(String resditEventPort) {
		this.resditEventPort = resditEventPort;
	}
	public OperationalFlightVO getOperationalFlightVo() {
		return operationalFlightVo;
	}
	public void setOperationalFlightVo(OperationalFlightVO operationalFlightVo) {
		this.operationalFlightVo = operationalFlightVo;
	}
}
