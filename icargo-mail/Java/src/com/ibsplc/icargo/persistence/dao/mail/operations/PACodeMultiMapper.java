/*
 * PACodeMultiMapper.java Created on June 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 *
 * @author A-2037
 * This class is used to map the Resultset into PostalAdministrationVO
 *
 */
public class PACodeMultiMapper implements MultiMapper<PostalAdministrationVO>{
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<PostalAdministrationVO> map(ResultSet rs) throws SQLException {
		PostalAdministrationVO postalAdministrationVO=
			null;
		Collection<MailEventVO> mailEventVOs=
			new ArrayList<MailEventVO>();
		MailEventVO mailEventVO=null;
		PostalAdministrationDetailsVO postalAdministrationDetailsVO=null;
		List<PostalAdministrationVO> postalAdministrationVOList = new ArrayList<PostalAdministrationVO>();
		Collection<PostalAdministrationDetailsVO> billingDetails=new ArrayList<PostalAdministrationDetailsVO>();
		Collection<PostalAdministrationDetailsVO> settlementCurrencyDetals=new ArrayList<PostalAdministrationDetailsVO>();
		Collection<PostalAdministrationDetailsVO> invoiceDetals=new ArrayList<PostalAdministrationDetailsVO>();
		Collection<PostalAdministrationDetailsVO> passDetails=new ArrayList<PostalAdministrationDetailsVO>();
		HashMap<String,Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsMap=null;
		List<PostalAdministrationVO> postalAdministrationVOOldList = new ArrayList<PostalAdministrationVO>();
		int checkCount=0;
		boolean parCodeFlag=false;
		while(rs.next()){
			postalAdministrationVO=new PostalAdministrationVO();

		postalAdministrationVO.setAddress(rs.getString("POAADR"));
		postalAdministrationVO.setCompanyCode(rs.getString("CMPCOD"));
		postalAdministrationVO.setCountryCode(rs.getString("CNTCOD"));
		postalAdministrationVO.setPaCode(rs.getString("POACOD"));
		postalAdministrationVO.setPaName(rs.getString("POANAM"));
		//Added for MRA
		postalAdministrationVO.setBaseType(rs.getString("BASTYP"));
		postalAdministrationVO.setBillingSource(rs.getString("BLGSRC"));
		postalAdministrationVO.setBillingFrequency(rs.getString("BLGFRQ"));
		postalAdministrationVO.setSettlementCurrencyCode(rs.getString("STLCURCOD"));
		postalAdministrationVO.setMessagingEnabled(rs.getString("MSGENBFLG"));
        postalAdministrationVO.setBasisType(rs.getString("BASTYP"));
        postalAdministrationVO.setBillingSource(rs.getString("BLGSRC"));
		postalAdministrationVO.setPartialResdit(PostalAdministrationVO.
				FLAG_YES.equals(rs.getString("PRTRDT")));
		postalAdministrationVO.setMsgEventLocationNeeded(PostalAdministrationVO.
				FLAG_YES.equals(rs.getString("MSGEVTLOC")));
		Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
	     if(lstUpdTime != null) {
	    	 postalAdministrationVO.setLastUpdateTime(
	    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
	     }
	    postalAdministrationVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	    postalAdministrationVO.setConPerson(rs.getString("CONPERSON"));
	    postalAdministrationVO.setCity(rs.getString("CITY"));
	    postalAdministrationVO.setState(rs.getString("STATE"));
	    postalAdministrationVO.setCountry(rs.getString("COUNTRY"));
	    postalAdministrationVO.setMobile(rs.getString("MOBILE"));
	    postalAdministrationVO.setPostCod(rs.getString("POSCOD"));
	    postalAdministrationVO.setPhone1(rs.getString("PHONE1"));
	    postalAdministrationVO.setPhone2(rs.getString("PHONE2"));
	    postalAdministrationVO.setFax(rs.getString("FAX"));
	    postalAdministrationVO.setEmail(rs.getString("EMLADR"));
	    postalAdministrationVO.setAutoEmailReqd(rs.getString("AUTEMLREQ"));
	    postalAdministrationVO.setDebInvCode(rs.getString("DBTINVCOD"));
	    postalAdministrationVO.setRemarks(rs.getString("RMK"));
	    postalAdministrationVO.setStatus(rs.getString("ACTFLG")) ;
	    postalAdministrationVO.setAccNum(rs.getString("ACCNO"));
	    postalAdministrationVO.setVatNumber(rs.getString("VATNUM"));	 
	    postalAdministrationVO.setDueInDays(rs.getInt("DUEDAY"));
	    postalAdministrationVO.setResidtversion(rs.getString("RDTVERNUM"));
	    postalAdministrationVO.setLatValLevel(rs.getString("LATVALLVL"));
	
	    postalAdministrationVO.setGibCustomerFlag(rs.getString("GIBFLG"));//Added by A-5200 for the ICRD-78230
	    postalAdministrationVO.setProformaInvoiceRequired(rs.getString("PROINVREQ"));
        postalAdministrationVO.setResditTriggerPeriod(rs.getInt("RDTSNDPRD"));//added by A-7371 for ICRD-212135
        postalAdministrationVO.setSettlementLevel(rs.getString("STLLVL"));//added by A-7531 for icrd-235799
        postalAdministrationVO.setMaxValue(rs.getDouble("STLTOLMAXVAL"));//added by A-7531 for icrd-235799
        postalAdministrationVO.setTolerancePercent(rs.getDouble("STLTOLPER"));//added by A-7531 for icrd-235799
        postalAdministrationVO.setToleranceValue(rs.getDouble("STLTOLVAL"));//added by A-7531 for icrd-235799
        postalAdministrationVO.setDupMailbagPeriod(rs.getInt("DUPMALPRD"));//added by A-8353 for ICRD-230449
       //Added as part of IASCB-853 starts
        postalAdministrationVO.setSecondaryEmail1(rs.getString("SECEMLADRONE"));
        postalAdministrationVO.setSecondaryEmail2(rs.getString("SECEMLADRTWO"));
        //Added as part of IASCB-853 ends
		if(rs.getString("MALCTGCOD")!=null){
//			mailEventVO=new MailEventVO();
//			if(MailEventVO.FLAG_YES.equals(rs.getString("ASGFLG"))){
//				mailEventVO.setAssigned(true);
//			}
//			else{
//				mailEventVO.setAssigned(false);
//			}
//			if(MailEventVO.FLAG_YES.equals(rs.getString("HNDOVRFLG"))){
//				mailEventVO.setHandedOver(true);
//			}
//			else{
//				mailEventVO.setHandedOver(false);
//			}
//			if(MailEventVO.FLAG_YES.equals(rs.getString("PNDFLG"))){
//				mailEventVO.setPending(true);
//			}
//			else{
//				mailEventVO.setPending(false);
//			}
//
//			if(MailEventVO.FLAG_YES.equals(rs.getString("RCVFLG"))){
//				mailEventVO.setReceived(true);
//			}
//			else{
//				mailEventVO.setReceived(false);
//			}
//
//			if(MailEventVO.FLAG_YES.equals(rs.getString("RTNFLG"))){
//				mailEventVO.setReturned(true);
//			}
//			else{
//				mailEventVO.setReturned(false);
//			}
//
//			if(MailEventVO.FLAG_YES.equals(rs.getString("UPLFLG"))){
//				mailEventVO.setUplifted(true);
//			}
//			else{
//				mailEventVO.setUplifted(false);
//			}
//
//			if(MailEventVO.FLAG_YES.equals(rs.getString("DLVFLG"))){
//				mailEventVO.setDelivered(true);
//			}
//			else{
//				mailEventVO.setDelivered(false);
//			}
//			//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 starts			
//			if(MailEventVO.FLAG_YES.equals(rs.getString("RDYDLVFLG"))){
//				mailEventVO.setReadyForDelivery(true);
//			}
//			else{
//				mailEventVO.setReadyForDelivery(false);
//			}
//			if(MailEventVO.FLAG_YES.equals(rs.getString("TRTCPLFLG"))){
//				mailEventVO.setTransportationCompleted(true);
//			}
//			else{
//				mailEventVO.setTransportationCompleted(false);
//			}
//			if(MailEventVO.FLAG_YES.equals(rs.getString("ARRFLG"))){
//				mailEventVO.setArrived(true);
//			}
//			else{
//				mailEventVO.setArrived(false);
//			}
//			//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 end
//			mailEventVO.setLoadedResditFlag(MailEventVO.FLAG_YES.equals(
//					rs.getString("LODFLG")));
//			mailEventVO.setHandedOverReceivedResditFlag(MailEventVO.FLAG_YES.equals(
//					rs.getString("HNDOVRRCVFLG")));
//			mailEventVO.setOnlineHandedOverResditFlag(MailEventVO.FLAG_YES.equals(
//					rs.getString("HNDOVRONLFLG")));
//			if(("ALL").equals(rs.getString("MALCTGCOD"))){
//				mailEventVO.setMailCategory(null);	
//			}else{
//				mailEventVO.setMailCategory(rs.getString("MALCTGCOD"));
//			}			
//			if(("ALL").equals(rs.getString("SUBCLSCOD"))){
//				mailEventVO.setMailClass(null);	
//			}else{				
//				mailEventVO.setMailClass(rs.getString("SUBCLSCOD"));
//			}
//			
//			mailEventVOs.add(mailEventVO);
//			postalAdministrationVO.setMailEvents(mailEventVOs);
			if(checkCount==0){
				postalAdministrationVOOldList.add(postalAdministrationVO);
				++checkCount;
			}
		}
		if(rs.getString("PARCOD")!=null){
			parCodeFlag=true;
			postalAdministrationDetailsVO=new PostalAdministrationDetailsVO();

			postalAdministrationDetailsVO.setParCode(rs.getString("PARCOD"));

			postalAdministrationDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			postalAdministrationDetailsVO.setPoaCode(rs.getString("POACOD"));
			if(rs.getString("SERNUM") !=null){
			postalAdministrationDetailsVO.setSernum(rs.getString("SERNUM"));
			}
			
		 if(("UPUCOD").equals(rs.getString("PARCOD"))){
			 postalAdministrationDetailsVO.setPartyIdentifier(rs.getString("PTYIDR")) ;
			 //Added by A-7794 as part of ICRD-223754
			 postalAdministrationDetailsVO.setParameterValue(rs.getString("PARVAL"));
		 }
			
			if(("BLGINFO").equals(rs.getString("PARCOD"))){
			if(rs.getString("BLGSRC") !=null){
			postalAdministrationDetailsVO.setBillingSource(rs.getString("BLGSRC"));
			}
			if(rs.getString("BLGFRQ") !=null){
			postalAdministrationDetailsVO.setBillingFrequency(rs.getString("BLGFRQ"));
			}
			if(rs.getString("PFMAINV") !=null){
			postalAdministrationDetailsVO.setProfInv(rs.getString("PFMAINV"));
			}
			if(rs.getDate("VLDFRM")!=null){
			Date frnDat = rs.getDate("VLDFRM");
			postalAdministrationDetailsVO.setValidFrom(new LocalDate("***",Location.NONE, frnDat));
			}
			if(rs.getDate("VLDTOO")!=null){
			Date toDat = rs.getDate("VLDTOO");
			postalAdministrationDetailsVO.setValidTo(new LocalDate("***",Location.NONE, toDat));
			}			
			billingDetails.add(postalAdministrationDetailsVO);
			}
			else if(("STLINFO").equals(rs.getString("PARCOD"))){
			if(rs.getString("STLCUR") !=null){
				postalAdministrationDetailsVO.setSettlementCurrencyCode(rs.getString("STLCUR"));
				}
				if(rs.getDate("VLDFRM")!=null){
				Date frnDat = rs.getDate("VLDFRM");
				postalAdministrationDetailsVO.setValidFrom(new LocalDate("***",Location.NONE, frnDat));
				}
				if(rs.getDate("VLDTOO")!=null){
				Date toDat = rs.getDate("VLDTOO");
				postalAdministrationDetailsVO.setValidTo(new LocalDate("***",Location.NONE, toDat));
				}
				settlementCurrencyDetals.add(postalAdministrationDetailsVO);
			}

			
			else if(("I").equals(rs.getString("PARTYP"))){
						
				if(rs.getString("PARCOD") != null){
					postalAdministrationDetailsVO.setParCode(rs.getString("PARCOD"));
				}
				if(rs.getString("PARVAL") != null){
					postalAdministrationDetailsVO.setParameterValue(rs.getString("PARVAL"));
				}
				if(rs.getDate("VLDFRM")!=null){
					Date frnDat = rs.getDate("VLDFRM");
					postalAdministrationDetailsVO.setValidFrom(new LocalDate("***",Location.NONE, frnDat));
					}
				if(rs.getDate("VLDTOO")!=null){
					Date toDat = rs.getDate("VLDTOO");
					postalAdministrationDetailsVO.setValidTo(new LocalDate("***",Location.NONE, toDat));
					}
				if(rs.getString("DTLRMK") != null){
					postalAdministrationDetailsVO.setDetailedRemarks(rs.getString("DTLRMK"));
				}
				invoiceDetals.add(postalAdministrationDetailsVO);
			}

		}

		}
		if(billingDetails.size()>0 ||settlementCurrencyDetals.size()>0 || invoiceDetals.size()>0 || passDetails.size()>0){
		postalAdministrationDetailsMap=new HashMap<String,Collection<PostalAdministrationDetailsVO>>();

		postalAdministrationDetailsMap.put("BLGINFO",billingDetails);
		postalAdministrationDetailsMap.put("STLINFO",settlementCurrencyDetals);
		postalAdministrationDetailsMap.put("INVINFO", invoiceDetals);
		}
		if(postalAdministrationDetailsMap!=null){

		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsMap);
		}
		
		if((!parCodeFlag) && checkCount>0){
			postalAdministrationVOList.add(postalAdministrationVOOldList.get(0));
		}else{
			postalAdministrationVOList.add(postalAdministrationVO);
		}
		return postalAdministrationVOList;
	}

}
