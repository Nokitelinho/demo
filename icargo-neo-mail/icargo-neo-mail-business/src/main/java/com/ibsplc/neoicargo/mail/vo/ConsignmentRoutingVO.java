/*
 * ConsignmentRoutingVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * 
 * @author A-5991
 *
 */
@Setter
@Getter
public class ConsignmentRoutingVO extends AbstractVO {

	private String companyCode;	
	private String consignmentDocNumber;	
	private int consignmentSeqNumber;	
	private String poaCode;	
	private int routingSerialNumber;
	private String flightCarrierCode;
	private int flightCarrierId;
	private String flightNumber;
	private ZonedDateTime flightDate;
	private String pol;
	private String pou;


}
