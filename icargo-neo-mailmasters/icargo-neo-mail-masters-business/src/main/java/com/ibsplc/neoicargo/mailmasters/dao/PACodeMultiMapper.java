/*
 * PACodeMultiMapper.java Created on June 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationDetailsVO;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-2037
 * This class is used to map the Resultset into PostalAdministrationVO
 */
public class PACodeMultiMapper implements MultiMapper<PostalAdministrationVO> {

    private static final String BLGSRC = "BLGSRC";
    private static final String PARCOD = "PARCOD";

    private static final String VLDFRM = "VLDFRM";
    private static final String VLDTOO = "VLDTOO";

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public List<PostalAdministrationVO> map(ResultSet rs) throws SQLException {
        PostalAdministrationVO postalAdministrationVO =
                null;
        PostalAdministrationDetailsVO postalAdministrationDetailsVO = null;
        List<PostalAdministrationVO> postalAdministrationVOList = new ArrayList<PostalAdministrationVO>();
        Collection<PostalAdministrationDetailsVO> billingDetails = new ArrayList<PostalAdministrationDetailsVO>();
        Collection<PostalAdministrationDetailsVO> settlementCurrencyDetals =
                new ArrayList<PostalAdministrationDetailsVO>();
        Collection<PostalAdministrationDetailsVO> invoiceDetals =
                new ArrayList<PostalAdministrationDetailsVO>();
        Collection<PostalAdministrationDetailsVO> passDetails = new ArrayList<PostalAdministrationDetailsVO>();
        HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsMap = null;
        List<PostalAdministrationVO> postalAdministrationVOOldList = new ArrayList<PostalAdministrationVO>();
        int checkCount = 0;
        boolean parCodeFlag = false;
        while (rs.next()) {
            postalAdministrationVO = new PostalAdministrationVO();

            setDetails(rs, postalAdministrationVO);
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
            postalAdministrationVO.setStatus(rs.getString("ACTFLG"));
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
            if (rs.getString("MALCTGCOD") != null && checkCount == 0) {
                postalAdministrationVOOldList.add(postalAdministrationVO);
                ++checkCount;
            }
            if (rs.getString(PARCOD) != null) {
                parCodeFlag = true;
                postalAdministrationDetailsVO = new PostalAdministrationDetailsVO();

                postalAdministrationDetailsVO.setParCode(rs.getString(PARCOD));

                postalAdministrationDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
                postalAdministrationDetailsVO.setPoaCode(rs.getString("POACOD"));
                if (rs.getString("SERNUM") != null) {
                    postalAdministrationDetailsVO.setSernum(rs.getString("SERNUM"));
                }

                setPartyDetails(rs, postalAdministrationDetailsVO);

                if (("BLGINFO").equals(rs.getString(PARCOD))) {
                    setBillingDetails(rs, postalAdministrationDetailsVO, billingDetails);
                } else if (("STLINFO").equals(rs.getString(PARCOD))) {
                    setSettlementCurrencyDetails(rs, postalAdministrationDetailsVO, settlementCurrencyDetals);
                } else if (("I").equals(rs.getString("PARTYP"))) {
                    setAdditionalInformation(rs, postalAdministrationDetailsVO, invoiceDetals);
                }

            }

        }
        if (billingDetails.size() > 0 || settlementCurrencyDetals.size() > 0 ||
                invoiceDetals.size() > 0 || passDetails.size() > 0) {
            postalAdministrationDetailsMap = new HashMap<String, Collection<PostalAdministrationDetailsVO>>();

            postalAdministrationDetailsMap.put("BLGINFO", billingDetails);
            postalAdministrationDetailsMap.put("STLINFO", settlementCurrencyDetals);
            postalAdministrationDetailsMap.put("INVINFO", invoiceDetals);
        }
        if (postalAdministrationDetailsMap != null) {

            postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsMap);
        }

        if ((!parCodeFlag) && checkCount > 0) {
            postalAdministrationVOList.add(postalAdministrationVOOldList.get(0));
        } else {
            postalAdministrationVOList.add(postalAdministrationVO);
        }
        return postalAdministrationVOList;
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     * Extracted method as part of refactoring
     */
    private static void setDetails(ResultSet rs, PostalAdministrationVO postalAdministrationVO) throws SQLException {
        postalAdministrationVO.setAddress(rs.getString("POAADR"));
        postalAdministrationVO.setCompanyCode(rs.getString("CMPCOD"));
        postalAdministrationVO.setCountryCode(rs.getString("CNTCOD"));
        postalAdministrationVO.setPaCode(rs.getString("POACOD"));
        postalAdministrationVO.setPaName(rs.getString("POANAM"));
        //Added for MRA
        postalAdministrationVO.setBaseType(rs.getString("BASTYP"));
        postalAdministrationVO.setBillingSource(rs.getString(BLGSRC));
        postalAdministrationVO.setBillingFrequency(rs.getString("BLGFRQ"));
        postalAdministrationVO.setSettlementCurrencyCode(rs.getString("STLCURCOD"));
        postalAdministrationVO.setMessagingEnabled(rs.getString("MSGENBFLG"));
        postalAdministrationVO.setBasisType(rs.getString("BASTYP"));
        postalAdministrationVO.setBillingSource(rs.getString(BLGSRC));
        postalAdministrationVO.setPartialResdit(PostalAdministrationVO.
                FLAG_YES.equals(rs.getString("PRTRDT")));
        postalAdministrationVO.setMsgEventLocationNeeded(PostalAdministrationVO.
                FLAG_YES.equals(rs.getString("MSGEVTLOC")));
        Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
        if(lstUpdTime != null) {
            postalAdministrationVO.setLastUpdateTime(LocalDateMapper.toZonedDateTime(
                    new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime)));
        }
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     * This method is used to set additional information details
     */
    private static void setAdditionalInformation(ResultSet rs,
                                                 PostalAdministrationDetailsVO postalAdministrationDetailsVO,
                                                 Collection<PostalAdministrationDetailsVO> invoiceDetals)
            throws SQLException {
        if (rs.getString(PARCOD) != null) {
            postalAdministrationDetailsVO.setParCode(rs.getString(PARCOD));
        }
        if (rs.getString("PARVAL") != null) {
            postalAdministrationDetailsVO.setParameterValue(rs.getString("PARVAL"));
        }
        if (rs.getDate(VLDFRM) != null) {
            Date frnDat = rs.getDate(VLDFRM);
            postalAdministrationDetailsVO.setValidFrom(LocalDateMapper.toZonedDateTime
                    (new LocalDate("***", Location.NONE, frnDat)));
        }
        if (rs.getDate(VLDTOO) != null) {
            Date toDat = rs.getDate(VLDTOO);
            postalAdministrationDetailsVO.setValidTo(LocalDateMapper.toZonedDateTime
                    (new LocalDate("***", Location.NONE, toDat)));
        }
        if (rs.getString("DTLRMK") != null) {
            postalAdministrationDetailsVO.setDetailedRemarks(rs.getString("DTLRMK"));
        }
        invoiceDetals.add(postalAdministrationDetailsVO);
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     * This method is used to set settlement details
     */
    private static void setSettlementCurrencyDetails(ResultSet rs,
                                                    PostalAdministrationDetailsVO postalAdministrationDetailsVO,
                                                    Collection<PostalAdministrationDetailsVO> settlementCurrencyDetals)
            throws SQLException {
        if (rs.getString("STLCUR") != null) {
            postalAdministrationDetailsVO.setSettlementCurrencyCode(rs.getString("STLCUR"));
        }
        if (rs.getDate(VLDFRM) != null) {
            Date frnDat = rs.getDate(VLDFRM);
            postalAdministrationDetailsVO.setValidFrom(LocalDateMapper.toZonedDateTime(
                    new LocalDate("***", Location.NONE, frnDat)));
        }
        if (rs.getDate(VLDTOO) != null) {
            Date toDat = rs.getDate(VLDTOO);
            postalAdministrationDetailsVO.setValidTo(LocalDateMapper.toZonedDateTime(
                    new LocalDate("***", Location.NONE, toDat)));
        }
        settlementCurrencyDetals.add(postalAdministrationDetailsVO);
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     * This method is used to set billing details
     */
    private static void setBillingDetails(ResultSet rs,
                                          PostalAdministrationDetailsVO postalAdministrationDetailsVO,
                                          Collection<PostalAdministrationDetailsVO> billingDetails)
            throws SQLException {
        if (rs.getString(BLGSRC) != null) {
            postalAdministrationDetailsVO.setBillingSource(rs.getString(BLGSRC));
        }
        if (rs.getString("BLGFRQ") != null) {
            postalAdministrationDetailsVO.setBillingFrequency(rs.getString("BLGFRQ"));
        }
        if (rs.getString("PFMAINV") != null) {
            postalAdministrationDetailsVO.setProfInv(rs.getString("PFMAINV"));
        }
        if (rs.getDate(VLDFRM) != null) {
            Date frnDat = rs.getDate(VLDFRM);
            postalAdministrationDetailsVO.setValidFrom(LocalDateMapper.toZonedDateTime(
                    new LocalDate("***", Location.NONE, frnDat)));
        }
        if (rs.getDate(VLDTOO) != null) {
            Date toDat = rs.getDate(VLDTOO);
            postalAdministrationDetailsVO.setValidTo(LocalDateMapper.toZonedDateTime(
                    new LocalDate("***", Location.NONE, toDat)));
        }
        billingDetails.add(postalAdministrationDetailsVO);
    }

    /**
     * @param rs
     * @return
     * @throws SQLException
     * This method is used to set party details
     */
    private static void setPartyDetails(ResultSet rs,
                                        PostalAdministrationDetailsVO postalAdministrationDetailsVO)
            throws SQLException {
        if (("UPUCOD").equals(rs.getString(PARCOD))) {
            postalAdministrationDetailsVO.setPartyIdentifier(rs.getString("PTYIDR"));
            //Added by A-7794 as part of ICRD-223754
            postalAdministrationDetailsVO.setParameterValue(rs.getString("PARVAL"));
        }
    }

}
