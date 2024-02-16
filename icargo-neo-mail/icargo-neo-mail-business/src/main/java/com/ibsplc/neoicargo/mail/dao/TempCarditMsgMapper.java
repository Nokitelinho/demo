package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleInformationMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleMessageVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.TempCarditMsgMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6287	:	02-Mar-2020	:	Draft
 */
@Slf4j
public class TempCarditMsgMapper implements MultiMapper<CarditTempMsgVO> {
	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet) Added by 			: A-6287 on 02-Mar-2020 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException
	*/
	@Override
	public List<CarditTempMsgVO> map(ResultSet rs) throws SQLException {
		List<CarditTempMsgVO> carditTempMsgVOs = new ArrayList<CarditTempMsgVO>();
		CarditTempMsgVO tmpVO = null;
		ReceptacleInformationMessageVO rcpInfoVO = null;
		ReceptacleMessageVO rcpMsgVO = null;
		log.debug("TempCarditMsgMapper" + " : " + "Map" + " Entering");
		while (rs.next()) {
			tmpVO = new CarditTempMsgVO();
			tmpVO.setCompanyCode(rs.getString("CMPCOD"));
			tmpVO.setSequenceNumber(rs.getLong("SEQNUM"));
			tmpVO.setMessageSeqnum(rs.getLong("MSGSEQNUM"));
			tmpVO.setSyntaxID(rs.getString("SYNIDR"));
			tmpVO.setSyntaxVersion(rs.getString("SYNVER"));
			tmpVO.setSenderID(rs.getString("SNDIDR"));
			tmpVO.setProcessingFailed(rs.getString("PRCFLD"));
			tmpVO.setSenderPartnerIDQualifier(rs.getString("SNDPRTIDRQFR"));
			tmpVO.setRecipientID(rs.getString("RECPNTIDR"));
			tmpVO.setRecipientPartnerIDQualifier(rs.getString("RCPPRTIDRQFR"));
			tmpVO.setPreparationDate(rs.getString("PRPDAT"));
			tmpVO.setInterchangeControlReference(rs.getString("ICHCTLREF"));
			tmpVO.setTestIndicator(rs.getString("TSTIND"));
			tmpVO.setInterchangeControlCount(rs.getString("INTCHGCNLCNT"));
			tmpVO.setTrailerInterchangeControlReference(rs.getString("TRLINTCHGCNLREF"));
			tmpVO.setControllingAgency(rs.getString("CTLAGY"));
			tmpVO.setMessageReferenceNumber(rs.getString("MSGREFNUM"));
			tmpVO.setMessageTypeIdentifier(rs.getString("MSGTYP"));
			tmpVO.setMessageVersionNumber(rs.getString("MSGVER"));
			tmpVO.setMessageReleaseNumber(rs.getString("MSGRELNUM"));
			tmpVO.setAssociationAssignedCode(rs.getString("ASNASGCOD"));
			tmpVO.setConsignmentIdentifier(rs.getString("CNSMNTIDR"));
			tmpVO.setMessageFunction(rs.getString("MSGFUN"));
			tmpVO.setDateTimePeriodQualifier(rs.getString("DTMPRDQLF"));
			tmpVO.setDateTimeFormatQualifier(rs.getString("DTMFMTQLF"));
			tmpVO.setTrailerMessageReferenceNumber(rs.getString("TRLMSGREFNUM"));
			tmpVO.setNumberOfSegments(rs.getString("NUMSEG"));
			tmpVO.setErrorCardit(rs.getString("ERRCAR"));
			tmpVO.setMailCategoryCodeIndicator(rs.getString("MALCATCODIDR"));
			tmpVO.setConsignmentCompletionDate(rs.getString("CNSCMPDAT"));
			tmpVO.setReqDeliveryTime(rs.getString("REQDLVTIM"));
			tmpVO.setTransportStageQualifier(rs.getString("TRPSTGQFR"));
			tmpVO.setConveyanceReference(rs.getString("CNVREFNUM"));
			tmpVO.setTransportIdentification(rs.getString("TRSIDR"));
			tmpVO.setCarrierID(rs.getString("CARIDR"));
			tmpVO.setDepartureLocationQualifier(rs.getString("DEPLOCQFR"));
			tmpVO.setDeparturePlace(rs.getString("DEPPLC"));
			tmpVO.setArrivalLocationQualifier(rs.getString("ARRLOCQFR"));
			tmpVO.setArrivalPlace(rs.getString("ARRPLC"));
			tmpVO.setTransportInfoDepartureDate(rs.getString("TRPDEPDAT"));
			tmpVO.setTransportInfoArrivalDate(rs.getString("TRPARRDAT"));
			tmpVO.setMailCategoryCode(rs.getString("MALCATCOD"));
			tmpVO.setDomesticTransportStageQualifier(rs.getString("DOMTRPSTGQFR"));
			tmpVO.setDomesticConveyanceReference(rs.getString("DOMCNVREFNUM"));
			tmpVO.setDomesticTransportIdentification(rs.getString("DOMTRSIDR"));
			tmpVO.setDomesticCarrierID(rs.getString("DOMCARIDR"));
			tmpVO.setModeOfTransport(rs.getString("TRPMODTRNSPT"));
			tmpVO.setCarrierName(rs.getString("CARNAM"));
			tmpVO.setCodeListQualifier(rs.getString("CODLSTQFR"));
			tmpVO.setAgencyForCarrierCode(rs.getString("AGYCARCOD"));
			tmpVO.setTransportLegRate(rs.getString("TSRLEGRAT"));
			tmpVO.setContractReference(rs.getString("CNTREF"));
			tmpVO.setDomesticDepartureLocationQualifier(rs.getString("DOMDEPLOCQFR"));
			tmpVO.setDomesticDeparturePlace(rs.getString("DOMDEPPLC"));
			tmpVO.setDomesticArrivalLocationQualifier(rs.getString("DOMARRLOCQFR"));
			tmpVO.setDomesticArrivalPlace(rs.getString("DOMARRPLC"));
			tmpVO.setDeparturePlaceName(rs.getString("DEPPLCNAM"));
			tmpVO.setDepartureCodeListQualifier(rs.getString("DEPCODLSTQFR"));
			tmpVO.setDepartureCodeListAgency(rs.getString("DEPCODLSTAGY"));
			tmpVO.setArrivalPlaceName(rs.getString("ARRPLCNAM"));
			tmpVO.setArrivalCodeListQualifier(rs.getString("ARRCODLSTQFR"));
			tmpVO.setArrivalCodeListAgency(rs.getString("ARRCODLSTAGY"));
			tmpVO.setTransportInfoDateTimePeriodQualifier(rs.getString("TRPDTMPRDQLF"));
			tmpVO.setTransportInfoDateTimeFormatQualifier(rs.getString("TRPDTMFMTQFR"));
			tmpVO.setDepartureDate(rs.getString("DEPDAT"));
			tmpVO.setArrivalDate(rs.getString("ARRDAT"));
			rcpInfoVO = new ReceptacleInformationMessageVO();
			rcpMsgVO = new ReceptacleMessageVO();
			rcpInfoVO.setNumberOfPackages(rs.getString("NUMPKG"));
			rcpInfoVO.setReceptacleType(rs.getString("RCPTYP"));
			rcpInfoVO.setPkgType(rs.getString("PKGTYP"));
			rcpInfoVO.setDRTagNo(rs.getString("DRTAGNUM"));
			rcpInfoVO.setReferenceQualifier(rs.getString("REFQFR"));
			rcpInfoVO.setReceptacleID(rs.getString("RCPIDR"));
			rcpInfoVO.setDangerousGoodsIndicator(rs.getString("DGRIND"));
			rcpInfoVO.setInsuranceIndicator(rs.getString("INSIND"));
			rcpInfoVO.setHandlingClass(rs.getString("HNDCLS"));
			rcpInfoVO.setCodeListResponsibleAgency(rs.getString("CODLSTAGY"));
			rcpInfoVO.setMeasurementApplicationQualifier(rs.getString("MSRAPPQFR"));
			rcpInfoVO.setReceptacleWeightType(rs.getString("RCPWGTTYP"));
			rcpInfoVO.setMeasureUnitQualifier(rs.getString("MSRUNTQFR"));
			rcpInfoVO.setDocumentOrMessageNameCode(rs.getString("DOCMSGNAMCOD"));
			rcpInfoVO.setDespatchIdentification(rs.getString("DSPIDN"));
			rcpInfoVO.setSealNumber(rs.getString("CONSELNUM"));
			rcpMsgVO.setOriginExchangeOffice(rs.getString("ORGEXEOFF"));
			rcpMsgVO.setDestinationExchangeOffice(rs.getString("DSTEXEOFF"));
			rcpMsgVO.setMailCategoryCode(rs.getString("RCPMALCATCOD"));
			rcpMsgVO.setMailSubClassCode(rs.getString("MALSUBCLSCOD"));
			rcpMsgVO.setLastDigitOfYear(Integer.parseInt(rs.getString("LSTDGTYAR")));
			rcpMsgVO.setDespatchNumber(rs.getString("DSPNUM"));
			rcpMsgVO.setReceptacleSerialNumber(rs.getString("RCPSERNUM"));
			rcpMsgVO.setHighestNumberReceptacleIndicator(rs.getString("HGHNUMRCPIND"));
			rcpMsgVO.setRegdOrInsuredIndicator(rs.getString("RGDINSIND"));
			rcpMsgVO.setReceptacleWeight(Double.parseDouble(rs.getString("RCPWGT")));
			rcpInfoVO.setReceptacleVO(rcpMsgVO);
			if (tmpVO.getMailbagVOs() == null) {
				tmpVO.setMailbagVOs(new ArrayList<ReceptacleInformationMessageVO>());
				tmpVO.getMailbagVOs().add(rcpInfoVO);
			} else {
				tmpVO.getMailbagVOs().add(rcpInfoVO);
			}
			tmpVO.setMailClassCode(rs.getString("MALCLSCOD"));
			tmpVO.setControlValue(rs.getString("CNLVAL"));
			tmpVO.setTotalPiecesControlQualifier(rs.getString("TOTPCSCNLQFR"));
			tmpVO.setNumberOfReceptacles(rs.getString("NUMRCP"));
			tmpVO.setTotalPiecesMeasureUnitQualifier(rs.getString("TOTPCSMSRUNTQFR"));
			tmpVO.setTotalWeightControlQualifier(rs.getString("TOTWGTCNLQFR"));
			tmpVO.setWeightOfReceptacles(rs.getString("WGTRCP"));
			tmpVO.setTotalWeightMeasureUnitQualifier(rs.getString("TOTWGTMSRUNTQFR"));
			tmpVO.setConsignmentContractReferenceNumber(rs.getString("CNSCRTREFNUM"));
			tmpVO.setTransportContractReferenceQualifier(rs.getString("TRPCRTREFQFR"));
			tmpVO.setRefInfoTransportStageQualifier(rs.getString("HNDOVRTRPSTGQFR"));
			tmpVO.setHandoverOriginLocationQualifier(rs.getString("HNDOVRORGLOCQFR"));
			tmpVO.setHandoverOriginLocationIdentifier(rs.getString("HNDOVRORGLOCIDR"));
			tmpVO.setHandoverOriginLocationName(rs.getString("HNDOVRORGNAM"));
			tmpVO.setHandoverOriginCodeListQualifier(rs.getString("HNDOVRORGCODQFR"));
			tmpVO.setHandoverOriginCodeListAgency(rs.getString("HNDOVRORGCODAGY"));
			tmpVO.setHandoverDestnLocationQualifier(rs.getString("HNDOVRDSTLOCQFR"));
			tmpVO.setHandoverDestnLocationIdentifier(rs.getString("HNDOVRDSTLOCIDR"));
			tmpVO.setHandoverDestnLocationName(rs.getString("HNDOVRDSTNAM"));
			tmpVO.setHandoverDestnCodeListQualifier(rs.getString("HNDOVRDSTCODQFR"));
			tmpVO.setHandoverDestnCodeListAgency(rs.getString("HNDOVRDSTCODAGY"));
			tmpVO.setHandOverInfoDateTimePeriodQualifier(rs.getString("HNDOVRDTMQFR"));
			tmpVO.setHandOverInfoDateTimeFormatQualifier(rs.getString("HNDOVRDTMFMTQFR"));
			tmpVO.setOriginCutOffPeriod(rs.getString("HNDOVRORGCUTOFF"));
			tmpVO.setDestinationCutOffPeriod(rs.getString("HNDOVRDSTCUTOFF"));
			tmpVO.setEquipmentQualifier(rs.getString("EQPQFR"));
			tmpVO.setContainerNumber(rs.getString("CTRNUM"));
			tmpVO.setContainerInfoCodeListResponsibleAgency(rs.getString("CODLSTRSPAGY"));
			tmpVO.setContainerType(rs.getString("CTRTYP"));
			tmpVO.setContainerInfoCodeListQualifier(rs.getString("CTRCODLSTQFR"));
			tmpVO.setTypeCodeListResponsibleAgency(rs.getString("TYPCODLSTRSPAGY"));
			tmpVO.setContainerJourneyIdentifier(rs.getString("CTRJNYIDR"));
			tmpVO.setMeasurementApplication(rs.getString("MSRAPP"));
			tmpVO.setMeasurementDimension(rs.getString("MSRDIM"));
			tmpVO.setContainerInfoMeasureUnitQualifier(rs.getString("CTRMSRUNTQFR"));
			tmpVO.setContainerWeight(rs.getString("CTRWGT"));
			tmpVO.setSealNumber(rs.getString("SELNUM"));
			carditTempMsgVOs.add(tmpVO);
		}
		return carditTempMsgVOs;
	}
}
