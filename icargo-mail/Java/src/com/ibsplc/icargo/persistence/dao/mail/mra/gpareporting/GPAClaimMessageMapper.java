package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class GPAClaimMessageMapper implements Mapper<ClaimDetailsVO> {
	private Log log = LogFactory.getLogger("MRA:GPAREPORTING");
	@Override
	public ClaimDetailsVO map(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		ClaimDetailsVO claimDetailsVO =new ClaimDetailsVO();  
		claimDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		claimDetailsVO.setMailSeqNumber(rs.getLong("MALSEQNUM"));
		claimDetailsVO.setGpaCode(rs.getString("POACOD")); 
		claimDetailsVO.setClaimType(rs.getString("CLMTYP"));
		claimDetailsVO.setVersnNumber(rs.getInt("VERNUM"));
		claimDetailsVO.setSernum(rs.getInt("SERNUM"));
		claimDetailsVO.setMailBagId(rs.getString("MALIDR"));
		claimDetailsVO.setControlNumber(rs.getString("CTLNUM"));
		claimDetailsVO.setRegionCode(rs.getString("REGCOD"));
		claimDetailsVO.setReferenceVersionNumber(rs.getString("REFVERNUM"));
		claimDetailsVO.setTruckingLocation(rs.getString("TRKLOC")); 
		if(rs.getTimestamp("ROUARRDAT")!=null){
		claimDetailsVO.setRouteArrivalDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("ROUARRDAT")));
		}
		claimDetailsVO.setOriginTripStagQualifier(rs.getString("ORGTRPSTG"));
		claimDetailsVO.setOriginTripFlightNumber(rs.getString("ORGFLTNUM"));
		claimDetailsVO.setOriginTripCarrierCode(rs.getString("ORGCARCOD"));
		

		claimDetailsVO.setPossessionScanStagQualifier(rs.getString("POSSCNTRPSTG"));
		claimDetailsVO.setPossessionScanCarrierCode(rs.getString("POSSCNCAR"));
		if(rs.getString("ACTMALSRVLVL")!=null && rs.getString("ACTMALSRVLVL").length() > 1){
			claimDetailsVO.setMailServiceLevel(rs.getString("ACTMALSRVLVL").substring(1,2));
		}
		if(rs.getTimestamp("POSSCNDAT")!=null){
		claimDetailsVO.setPossessionScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("POSSCNDAT")));
		}
		claimDetailsVO.setPossessionScanExpectedSite(rs.getString("POSSCNEXPSTE"));
		
		
		claimDetailsVO.setLoadScanCarrierCode(rs.getString("LODSCANCAR"));
		claimDetailsVO.setLoadScanStagQualifier(rs.getString("LODSCNTRPSTG"));
		if(rs.getTimestamp("LODSCNDAT")!=null){
		claimDetailsVO.setLoadScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LODSCNDAT")));
		}
		claimDetailsVO.setLoadScanExpectedSite(rs.getString("LODSCNEXPSTE"));
		claimDetailsVO.setLoadScanFlightNumber(rs.getString("LODSCNFLTNUM"));
		
		claimDetailsVO.setFirstTransferStagQualifier(rs.getString("TRFONETRPSTG"));
		claimDetailsVO.setFirstTransferExpectedSite(rs.getString("TRFONEEXPSTE"));
		claimDetailsVO.setFirstTransferScanCarrier(rs.getString("TRFONECAR"));
		claimDetailsVO.setFirstTransferFlightNumber(rs.getString("TRFONEFLTNUM"));
		if(rs.getTimestamp("TRFONEDAT")!=null){
		claimDetailsVO.setFirstTransferDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("TRFONEDAT")));
		}
		
		claimDetailsVO.setSecondTransferScanCarrier(rs.getString("TRFTWOCAR"));
		claimDetailsVO.setSecondTransferStagQualifier(rs.getString("TRFTWOTRPSTG"));
		if(rs.getTimestamp("TRFTWODAT")!=null){
		claimDetailsVO.setSecondTransferDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("TRFTWODAT")));
		}
		claimDetailsVO.setSecondTransferFlightNumber(rs.getString("TRFTWOFLTNUM"));
		claimDetailsVO.setSecondTransferExpectedSite(rs.getString("TRFTWOEXPSTE"));
		
		claimDetailsVO.setThirdTransferStagQualifier(rs.getString("TRFTHRTRPSTG"));
		claimDetailsVO.setThirdTransferExpectedSite(rs.getString("TRFTHREXPSTE"));
		claimDetailsVO.setThirdTransferFlightNumber(rs.getString("TRFTHRFLTNUM"));
		claimDetailsVO.setThirdTransferScanCarrier(rs.getString("TRFTHRCAR"));
		if(rs.getString("TRFTHRDAT")!=null){
		claimDetailsVO.setThirdTransferDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("TRFTHRDAT")));
		}
		claimDetailsVO.setFourthTransferScanCarrier(rs.getString("TRFFORCAR"));
		claimDetailsVO.setFourthTransferStagQualifier(rs.getString("TRFFORTRPSTG"));
		claimDetailsVO.setFourthTransferExpectedSite(rs.getString("TRFFOREXPSTE"));
		if(rs.getTimestamp("TRFFORDAT")!=null){
		claimDetailsVO.setFourthTransferDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("TRFFORDAT")));
		}
		claimDetailsVO.setFourthTransferFlightNumber(rs.getString("TRFFORFLTNUM"));
		
		claimDetailsVO.setDeliveryScanExpectedSite(rs.getString("DLVSCNTRPSTG"));
		claimDetailsVO.setDeliverScanActualSite(rs.getString("DLVSCNACTSTE"));
		claimDetailsVO.setDeliveryScanExpectedSite(rs.getString("DLVSCNEXPSTE"));
		if(rs.getTimestamp("DLVSCNDAT")!=null){
		claimDetailsVO.setDeliveryDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("DLVSCNDAT")));
		}
		claimDetailsVO.setCarrierFinalDestination(rs.getString("CARFNLDST"));
		claimDetailsVO.setRateTypeIndicator(rs.getString("RATTYPIND"));
		claimDetailsVO.setGreatCircleMiles(rs.getString("GCMORGDST"));
		claimDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		claimDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		claimDetailsVO.setMailProductCode(rs.getString("MALPRDTYP"));
		claimDetailsVO.setMailSubClassCode(rs.getString("MALSUBCLSCOD"));
		claimDetailsVO.setMailClass(rs.getString("MALCLS"));
		claimDetailsVO.setPayRateCode(rs.getString("PAYRATCOD"));
		claimDetailsVO.setLineHaulDollarRate(rs.getDouble("ACTRAT"));
		claimDetailsVO.setLineHaulSDRRate(rs.getDouble("ACTRAT"));
		claimDetailsVO.setTerminalHandlingDollarRate(rs.getDouble("TERHDLUSDRAT"));
		claimDetailsVO.setTerminalHandlingSDRRate(rs.getDouble("TERHDLSDRRAT"));
		claimDetailsVO.setSpecialPerKiloDollarRate(rs.getDouble("SPLPERKGMUSDRAT"));
		claimDetailsVO.setSpecialPerKiloSDRRate(rs.getDouble("SPLPERKGMSDRRAT"));
		claimDetailsVO.setContainerRate(rs.getDouble("CNTRAT"));
		claimDetailsVO.setContainerType(rs.getString("CONTYP"));
		if(rs.getString("CONTYP")!=null && rs.getString("CONTYP").length() > 3){
			if(!rs.getString("CONTYP").equalsIgnoreCase("LOOSE"))
			claimDetailsVO.setContainerType(rs.getString("CONTYP").substring(0,3));
			else{
				claimDetailsVO.setContainerType(rs.getString("CONTYP"));
			}
		}
		//claimDetailsVO.setContainerType(rs.getString("CONTYP"));
		claimDetailsVO.setWeightUnit(rs.getString("MALWGTUNT"));
		claimDetailsVO.setContainerWeightUnit(rs.getString("CONWGTUNT"));
		claimDetailsVO.setContainerGrossWeight(rs.getDouble("CONGRSWGT"));
		if(rs.getTimestamp("CSGCMPDAT")!=null){
		claimDetailsVO.setConsignmentCompletionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CSGCMPDAT")));
		}
		claimDetailsVO.setContractType(rs.getString("CTRTYP"));
		claimDetailsVO.setGrossWeight(rs.getDouble("MALGRSWGT"));
		claimDetailsVO.setCarrierToPay(rs.getString("CARTOOPAY"));
		claimDetailsVO.setOrginAirport(rs.getString("ORGARPCOD"));
		claimDetailsVO.setDestinationAirport(rs.getString("DSTARPCOD"));
		claimDetailsVO.setOfflineOriginAirport(rs.getString("OFLORGARP"));
		claimDetailsVO.setDeliveryScanCarrierCode(rs.getString("DLVSCNCAR"));
		claimDetailsVO.setOriginalOriginAirport(rs.getString("ORIORGARP"));
		claimDetailsVO.setFinalDestination(rs.getString("FNLDST"));
		claimDetailsVO.setSortScanCarrier(rs.getString("SORSCNCAR"));
		claimDetailsVO.setSortScanActualSite(rs.getString("SORSCNACTSTE"));
		claimDetailsVO.setSortScanExpectedSite(rs.getString("SORSCNEXPSTE"));
		if(rs.getTimestamp("SORSCNACTDAT")!=null){
			claimDetailsVO.setSortScanActualDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("SORSCNACTDAT")));
			}
		if(rs.getTimestamp("SORSCNRCVDAT")!=null){
			claimDetailsVO.setSortScanRecDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("SORSCNRCVDAT")));
			}
		if(rs.getTimestamp("ROUDEPDAT")!=null){
		claimDetailsVO.setRouteDepatureDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("ROUDEPDAT")));
		}
		claimDetailsVO.setClaimAmount(rs.getDouble("CLMAMT"));
		
		claimDetailsVO.setClaimRefNumber(rs.getString("CLMREFNUM"));
		return claimDetailsVO;
	}

}
