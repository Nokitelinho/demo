/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.MailOperationProxy.java
 *
 *	Created by	:	A-6287
 *	Created on	:	03-Mar-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationReportVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamageMailFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandedOverFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandedOverVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScreeningFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.MailOperationProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6287	:	03-Mar-2020	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class MailOperationsProxy extends ProductProxy{
	
	private static final String SAVE_CARDIT_MSGS = "saveCarditMsgs";
	private static final String FIND_MAIL_SEQUENCE_NUMBER = "findMailBagSequenceNumberFromMailIdr";
	private static final String FIND_ARRIVAL_DETAILS_FOR_RELEASING_MAILS = "findArrivalDetailsForReleasingMails";
	private static final String FIND_MAILBOX_ID_FROM_CONFIG = "findMailboxIdFromConfig";
	private Log log = LogFactory.getLogger("MAIL OPERATION");
	private static final String GET_FOUND_ARRIVAL_MAILBAGS = "getFoundArrivalMailBags";
	private static final String FIND_MAILBAG_HISTORIES = "findMailbagHistories";	
	private static final String GENERATE_MAIL_TAG_DETAILS = "generateMailTagDetails";
	private static final String GENERATE_CONSIGNMENT_REPORT_DETAILS = "generateConsignmentReportDtls";
    private static final String GENERATE_CN46CONSIGNMENT_REPORT_DETAILS = "generateCN46ConsignmentReportDtls";
	private static final String GENERATE_CN46CONSIGNMENT_SUMMARY_REPORT_DETAILS = "generateCN46ConsignmentSummaryReportDtls";
    private static final String FETCH_MAIL_SECURITY_DETAILS= "fetchMailSecurityDetails";
	private static final String FIND_ROUTING_DETAILS= "findRoutingDetails";
    private static final String GENERATE_MAIL_STATUS_RT= "generateMailStatusRT";
	private static final String GENERATE_DAILY_MAIL_STATION_RT="generateDailyMailStationRT";
	private static final String GENERATE_MAIL_HANDOVER_RT="generateMailHandedOverRT";
	private static final String FIND_IMPORT_MANIFEST_DETAILS= "findImportManifestDetails";	
private static final String FIND_DAMAGE_MAIL_REPORT= "findDamageMailReport";

	public Collection<ErrorVO> saveCarditMsgs(com.ibsplc.icargo.business.mail.operations.vo.EDIInterchangeVO ediInterchangeVO)
	        throws ProxyException, SystemException {
	        log.entering("MailOperationsProxy", SAVE_CARDIT_MSGS);
	        return despatchRequest(SAVE_CARDIT_MSGS, ediInterchangeVO);
	    }
	
	public long findMailSequenceNumber(String mailbagId, String companyCode) throws ProxyException, SystemException {
		log.entering(this.getClass().getSimpleName(), FIND_MAIL_SEQUENCE_NUMBER);
		return despatchRequest(FIND_MAIL_SEQUENCE_NUMBER, mailbagId, companyCode);
	    }

	public Collection<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO operationalFlightVO)
			throws ProxyException, SystemException {
		log.entering(getClass().getSimpleName(), FIND_ARRIVAL_DETAILS_FOR_RELEASING_MAILS);
		return despatchRequest(FIND_ARRIVAL_DETAILS_FOR_RELEASING_MAILS, operationalFlightVO);
	}

	public String findMailboxIdFromConfig(MailbagVO mailbagVO) throws ProxyException, SystemException {
		log.entering(getClass().getSimpleName(), FIND_MAILBOX_ID_FROM_CONFIG);
		return despatchRequest(FIND_MAILBOX_ID_FROM_CONFIG, mailbagVO);
	}
	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO)
			throws ProxyException, SystemException {
		log.entering(getClass().getSimpleName(), GET_FOUND_ARRIVAL_MAILBAGS);
		return despatchRequest(GET_FOUND_ARRIVAL_MAILBAGS, mailArrivalVO);
	}
	public ConsignmentDocumentVO generateConsignmentSummaryReportDtls(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException, ProxyException {
		log.entering(getClass().getSimpleName(), "generateConsignmentSummaryReportDtls");
		return despatchRequest("generateConsignmentSummaryReportDtls",consignmentFilterVO);
	}   
public Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String fieldType, Long mailSeqNum) throws ProxyException, SystemException{
		 log.entering("MailController", FIND_MAILBAG_HISTORIES);
		 return despatchRequest(FIND_MAILBAG_HISTORIES, companyCode,fieldType,mailSeqNum);
	}
	public MailManifestVO findMailbagManifest(OperationalFlightVO flightVO) throws ProxyException, SystemException{
				return despatchRequest("findMailbagManifest", flightVO);
		}	
	public MailManifestVO findMailAWBManifest(OperationalFlightVO flightVO)throws ProxyException, SystemException{
		return despatchRequest("findMailAWBManifest", flightVO);
}	
	public MailManifestVO findDSNMailbagManifest(OperationalFlightVO flightVO)throws ProxyException, SystemException{
		return despatchRequest("findDSNMailbagManifest", flightVO);
}
	public MailManifestVO findDestnCatManifest(OperationalFlightVO flightVO)throws ProxyException, SystemException{
		return despatchRequest("findDestnCatManifest", flightVO);
} 
  public TransferManifestVO generateTransferManifestReportDetails(String companyCode, String transferManifestId) throws SystemException, ProxyException  {
		log.entering(getClass().getSimpleName(), "generateTransferManifestReportDetails");
		return despatchRequest("generateTransferManifestReportDetails",companyCode,transferManifestId);
}
	public ConsignmentDocumentVO generateConsignmentSecurityReportDtls(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException, ProxyException {
		return despatchRequest("generateConsignmentSecurityReportDtls",consignmentFilterVO);
	}
public Collection<MailbagVO> generateMailTagDetails(Collection<MailbagVO> mailbagVOs) throws SystemException,ProxyException{
		log.entering(getClass().getSimpleName(), GENERATE_MAIL_TAG_DETAILS);
		return despatchRequest(GENERATE_MAIL_TAG_DETAILS,mailbagVOs);
}  
public ConsignmentDocumentVO generateConsignmentReportDtls(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException, ProxyException {
		log.entering(getClass().getSimpleName(), GENERATE_CONSIGNMENT_REPORT_DETAILS);
		return despatchRequest(GENERATE_CONSIGNMENT_REPORT_DETAILS,consignmentFilterVO);
	}
public Collection<ConsignmentDocumentVO> generateCN46ConsignmentReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException, ProxyException{
	log.entering(getClass().getSimpleName(), GENERATE_CN46CONSIGNMENT_REPORT_DETAILS);
	return despatchRequest(GENERATE_CN46CONSIGNMENT_REPORT_DETAILS,consignmentFilterVO);
	}
public Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReportDtls(
		ConsignmentFilterVO consignmentFilterVO)throws SystemException, ProxyException {
	log.entering(getClass().getSimpleName(), GENERATE_CN46CONSIGNMENT_SUMMARY_REPORT_DETAILS);
	return despatchRequest(GENERATE_CN46CONSIGNMENT_SUMMARY_REPORT_DETAILS,consignmentFilterVO);
}
public MailbagVO fetchMailSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo)throws ProxyException, SystemException {
	log.entering(getClass().getSimpleName(), FETCH_MAIL_SECURITY_DETAILS);
	return despatchRequest(FETCH_MAIL_SECURITY_DETAILS, mailScreeningFilterVo);
	}
	public String findRoutingDetails(String companyCode,long malseqnum) throws ProxyException, SystemException{
	log.entering(getClass().getSimpleName(), FIND_ROUTING_DETAILS);
	return despatchRequest(FIND_ROUTING_DETAILS, companyCode,malseqnum);
	}
public Collection<MailStatusVO> generateMailStatusRT(MailStatusFilterVO mailStatusFilterVO) throws ProxyException, SystemException {
	log.entering(getClass().getSimpleName(), GENERATE_MAIL_STATUS_RT);
	return despatchRequest(GENERATE_MAIL_STATUS_RT, mailStatusFilterVO);
	}

public Collection<DailyMailStationReportVO> generateDailyMailStationRT(DailyMailStationFilterVO filterVO) throws ProxyException, SystemException {
	log.entering(getClass().getSimpleName(), GENERATE_DAILY_MAIL_STATION_RT);
	return despatchRequest(GENERATE_DAILY_MAIL_STATION_RT, filterVO);
}
	public Collection<MailHandedOverVO> generateMailHandedOverRT(MailHandedOverFilterVO mailHandedOverFilterVO)throws SystemException, ProxyException {
		log.entering(getClass().getSimpleName(),GENERATE_MAIL_HANDOVER_RT);
		return despatchRequest(GENERATE_MAIL_HANDOVER_RT,mailHandedOverFilterVO);
	}

	public MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo) throws ProxyException, SystemException{
		log.entering(getClass().getSimpleName(), FIND_IMPORT_MANIFEST_DETAILS);
		return despatchRequest(FIND_IMPORT_MANIFEST_DETAILS, operationalFlightVo);
	}	  
public Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO damageMailReportFilterVO)  throws ProxyException, SystemException {
	log.entering(getClass().getSimpleName(), FIND_DAMAGE_MAIL_REPORT);
	return despatchRequest(FIND_DAMAGE_MAIL_REPORT, damageMailReportFilterVO);
  }
}