/*
 * SecurityScreeningValidationFilterQuery.java Created on Sep 22, 2022
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 *
 */
package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;

/**
 * @author A-8353
 *
 */
@Slf4j
public class SecurityScreeningValidationFilterQuery extends NativeQuery {

    private String baseQuery;

    private SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO;


    /**
     * @throws SystemException
     * @param baseQuery
     */
    public SecurityScreeningValidationFilterQuery(String baseQuery,
                                                  SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO)  {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.securityScreeningValidationFilterVO = securityScreeningValidationFilterVO;
    }

    /**
     *
     * Sep 22, 2022, A-8353
     * @return
     * */
    @Override
    public String getNativeQuery() {
        log.debug("SecurityScreeningValidationFilterQuery  --> getNativeQuery");

        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        int idx = 0;
        boolean appendAnd=false;
        StringBuilder appendNotNullCondition=new StringBuilder();
        setParameter(++idx, securityScreeningValidationFilterVO.getCompanyCode());
        setParameter(++idx, securityScreeningValidationFilterVO.getApplicableTransaction());
        if (!securityScreeningValidationFilterVO.isSecurityValNotReq()){
            if(securityScreeningValidationFilterVO.getOriginAirport() != null && securityScreeningValidationFilterVO.getOriginAirport().trim().length()>0){
                queryBuilder.append("(COALESCE(ORGARPCODFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(ORGARPCOD,'')||',', ','||?||',')=0) ")
                        .append("OR (COALESCE(ORGARPCODFLG,'Y') = 'N' AND (INSTR(','||COALESCE(ORGARPCOD,'')||',', ','||?||',')>0))) ");
                setParameter(++idx,securityScreeningValidationFilterVO.getOriginAirport());
                setParameter(++idx,securityScreeningValidationFilterVO.getOriginAirport());
                appendAnd=true;
                appendNotNullCondition.append("ORGARPCOD IS  NOT NULL ");
            }
            if(securityScreeningValidationFilterVO.getDestinationAirport() != null && securityScreeningValidationFilterVO.getDestinationAirport().trim().length()>0){
                appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
                queryBuilder.append("(COALESCE(DSTARPCODFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(DSTARPCOD,'')||',', ','||?||',')=0) ")
                        .append("OR (COALESCE(DSTARPCODFLG,'Y') = 'N' AND (INSTR(','||COALESCE(DSTARPCOD,'')||',', ','||?||',')>0))) ");
                setParameter(++idx,securityScreeningValidationFilterVO.getDestinationAirport());
                setParameter(++idx,securityScreeningValidationFilterVO.getDestinationAirport());
                appendNotNullCondition.append("DSTARPCOD IS  NOT NULL ");
                appendAnd=true;
            }
            if(securityScreeningValidationFilterVO.getTransactionAirport() != null && securityScreeningValidationFilterVO.getTransactionAirport().trim().length()>0){
                appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
                queryBuilder.append("(COALESCE(TXNARPCODFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(TXNARPCOD,'')||',', ','||?||',')=0) ")
                        .append("OR (COALESCE(TXNARPCODFLG,'Y') = 'N' AND (INSTR(','||COALESCE(TXNARPCOD,'')||',', ','||?||',')>0))) ");
                setParameter(++idx,securityScreeningValidationFilterVO.getTransactionAirport());
                setParameter(++idx,securityScreeningValidationFilterVO.getTransactionAirport());
                appendNotNullCondition.append("TXNARPCOD IS  NOT NULL ");
                appendAnd=true;
            }
            if(securityScreeningValidationFilterVO.getSecurityStatusCode() != null && securityScreeningValidationFilterVO.getSecurityStatusCode().trim().length()>0){
                appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
                queryBuilder.append("(COALESCE(SECSTACODFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(SECSTACOD,'')||',', ','||?||',')=0) ")
                        .append("OR (COALESCE(SECSTACODFLG,'Y') = 'N' AND (INSTR(','||COALESCE(SECSTACOD,'')||',', ','||?||',')>0))) ");
                setParameter(++idx,securityScreeningValidationFilterVO.getSecurityStatusCode());
                setParameter(++idx,securityScreeningValidationFilterVO.getSecurityStatusCode());
                appendNotNullCondition.append("SECSTACOD IS  NOT NULL ");
                appendAnd=true;
            }
            if(securityScreeningValidationFilterVO.getFlightType() != null && securityScreeningValidationFilterVO.getFlightType().trim().length()>0){
                appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
                queryBuilder.append("(COALESCE(FLTTYPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(FLTTYP,'')||',', ','||?||',')=0) ")
                        .append("OR (COALESCE(FLTTYPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(FLTTYP,'')||',', ','||?||',')>0))) ");
                setParameter(++idx,securityScreeningValidationFilterVO.getFlightType());
                setParameter(++idx,securityScreeningValidationFilterVO.getFlightType());
                appendNotNullCondition.append("FLTTYP IS  NOT NULL ");
                appendAnd=true;
            }
            idx=populateGroupsForSecurityScreeningValidation(queryBuilder,appendAnd, appendNotNullCondition,idx);
        }
        if(securityScreeningValidationFilterVO.isAppRegValReq()){
            populateFilterValueForAppRegVal(queryBuilder, appendAnd, appendNotNullCondition,idx);
        }
        appendSuffix(queryBuilder, appendNotNullCondition);
        return queryBuilder.toString();
    }

    /**
     * @param queryBuilder
     * @param appendAnd
     * @param appendNotNullCondition
     */
    private void appendAndOrForFilter(StringBuilder queryBuilder, boolean appendAnd,
                                      StringBuilder appendNotNullCondition) {
        if (appendAnd){
            queryBuilder.append("AND ");
            appendNotNullCondition.append("OR ");
        }
    }

    /**
     * @author A-8353
     * @param queryBuilder
     * @param appendNotNullCondition
     */
    private void appendSuffix(StringBuilder queryBuilder, StringBuilder appendNotNullCondition) {
        queryBuilder.append("AND (").append(appendNotNullCondition);
        queryBuilder.append(" ) ORDER BY ")
                .append("CASE WHEN ERRTYP='E'THEN 1 WHEN ERRTYP='W' then 2 ELSE  3 END ");
        log.debug("SecurityScreeningValidationFilterQuery  -> getNativeQuery");
    }

    /**
     * @author A-8353
     * @param queryBuilder
     * @param idx
     * @param appendAnd
     * @param appendNotNullCondition
     * @param idx
     */
    private int populateGroupsForSecurityScreeningValidation(StringBuilder queryBuilder, boolean appendAnd,
                                                             StringBuilder appendNotNullCondition, int idx) {
        if(securityScreeningValidationFilterVO.getOriginAirportCountryGroup() != null && securityScreeningValidationFilterVO.getOriginAirportCountryGroup().trim().length()>0){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(ORGARPCNTGRPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(ORGARPCNTGRP,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(ORGARPCNTGRPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(ORGARPCNTGRP,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getOriginAirportCountryGroup());
            setParameter(++idx,securityScreeningValidationFilterVO.getOriginAirportCountryGroup());
            appendNotNullCondition.append("ORGARPCNTGRP IS  NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getDestAirportCountryGroup() != null && securityScreeningValidationFilterVO.getDestAirportCountryGroup().trim().length()>0){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(DSTARPCNTGRPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(DSTARPCNTGRP,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(DSTARPCNTGRPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(DSTARPCNTGRP,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getDestAirportCountryGroup());
            setParameter(++idx,securityScreeningValidationFilterVO.getDestAirportCountryGroup());
            appendNotNullCondition.append("DSTARPCNTGRP IS  NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getTxnAirportCountryGroup() != null && securityScreeningValidationFilterVO.getTxnAirportCountryGroup().trim().length()>0){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(TXNARPCNTGRPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(TXNARPCNTGRP,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(TXNARPCNTGRPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(TXNARPCNTGRP,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getTxnAirportCountryGroup());
            setParameter(++idx,securityScreeningValidationFilterVO.getTxnAirportCountryGroup());
            appendNotNullCondition.append("TXNARPCNTGRP IS  NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getTxnAirportGroup() != null && securityScreeningValidationFilterVO.getTxnAirportGroup().trim().length()>0){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(TXNARPGRPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(TXNARPGRP,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(TXNARPGRPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(TXNARPGRP,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getTxnAirportGroup());
            setParameter(++idx,securityScreeningValidationFilterVO.getTxnAirportGroup());
            appendNotNullCondition.append("TXNARPGRP IS  NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getSecurityStatusCodeGroup() != null && securityScreeningValidationFilterVO.getSecurityStatusCodeGroup().trim().length()>0){
            idx=appendSccGroup(queryBuilder, appendAnd, appendNotNullCondition,idx);
        }
        return idx;
    }
    /**
     * @param queryBuilder
     * @param appendAnd
     * @param appendNotNullCondition
     * @param idx
     */
    private int populateFilterValueForAppRegVal(StringBuilder queryBuilder, boolean appendAnd,
                                                StringBuilder appendNotNullCondition, int idx) {
        if(securityScreeningValidationFilterVO.getAppRegDestCountryGroup()!=null){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(APLREGDSTCNTGRPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(APLREGDSTCNTGRP,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(APLREGDSTCNTGRPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(APLREGDSTCNTGRP,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegDestCountryGroup());
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegDestCountryGroup());
            appendNotNullCondition.append("APLREGDSTCNTGRP IS  NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getSubClass()!=null){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(SUBCLSFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(SUBCLS,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(SUBCLSFLG,'Y') = 'N' AND (INSTR(','||COALESCE(SUBCLS,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getSubClass());
            setParameter(++idx,securityScreeningValidationFilterVO.getSubClass());
            appendNotNullCondition.append("SUBCLS IS  NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getAppRegFlg()!=null){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(APLREGFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(APLREG,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(APLREGFLG,'Y') = 'N' AND (INSTR(','||COALESCE(APLREG,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegFlg());
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegFlg());
            appendNotNullCondition.append("APLREG IS NOT NULL ");
        }
        if(securityScreeningValidationFilterVO.getDestinationCountry()!=null){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(DSTARPCNTFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(DSTARPCNT,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(DSTARPCNTFLG,'Y') = 'N' AND (INSTR(','||COALESCE(DSTARPCNT,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getDestinationCountry());
            setParameter(++idx,securityScreeningValidationFilterVO.getDestinationCountry());
            appendNotNullCondition.append("DSTARPCNT IS NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getAppRegTransistCountry()!=null){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(APLREGTRSCNTFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(APLREGTRSCNT,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(APLREGTRSCNTFLG,'Y') = 'N' AND (INSTR(','||COALESCE(APLREGTRSCNT,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegTransistCountry());
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegTransistCountry());
            appendNotNullCondition.append("APLREGTRSCNT IS  NOT NULL ");
            appendAnd=true;
        }
        if(securityScreeningValidationFilterVO.getAppRegTransistCountryGroup()!=null){
            appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
            queryBuilder.append("(COALESCE(APLREGTRSCNTGRPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(APLREGTRSCNTGRP,'')||',', ','||?||',')=0) ")
                    .append("OR (COALESCE(APLREGTRSCNTGRPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(APLREGTRSCNTGRP,'')||',', ','||?||',')>0)))");
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegTransistCountryGroup());
            setParameter(++idx,securityScreeningValidationFilterVO.getAppRegTransistCountryGroup());
            appendNotNullCondition.append("APLREGTRSCNTGRP IS  NOT NULL ");
        }
        return idx;
    }

    /**
     * @author A-8353
     * @param queryBuilder
     * @param appendAnd
     * @param appendNotNullCondition
     * @param idx
     */
    private int appendSccGroup(StringBuilder queryBuilder, boolean appendAnd,
                               StringBuilder appendNotNullCondition, int idx) {
        appendAndOrForFilter(queryBuilder, appendAnd, appendNotNullCondition);
        queryBuilder.append("(COALESCE(SCCGRPFLG,'Y') = 'Y' AND (INSTR(','||COALESCE(SCCGRP,'')||',', ','||?||',')=0)")
                .append("OR (COALESCE(SCCGRPFLG,'Y') = 'N' AND (INSTR(','||COALESCE(SCCGRP,'')||',', ','||?||',')>0)))");
        setParameter(++idx,securityScreeningValidationFilterVO.getSecurityStatusCodeGroup());
        setParameter(++idx,securityScreeningValidationFilterVO.getSecurityStatusCodeGroup());
        appendNotNullCondition.append("SCCGRP IS  NOT NULL ");
        return idx;
    }

}