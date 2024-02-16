/*
 * StockRangeUtilisation.java Created on Jun 15, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.stock.dao.entity;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_RANGE_UTILISATION_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the range of documents belonging to a particular stock Aggregation of ranges
 * constitute a stock
 *
 * @author A-1358
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "STKRNGUTL")
@Entity
@Auditable(eventName = STOCK_RANGE_UTILISATION_AUDIT_EVENT_NAME, allFields = false, isParent = true)
public class StockRangeUtilisation extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKRNGUTLSERNUM")
  @SequenceGenerator(name = "STKRNGUTLSEQ", sequenceName = "STKRNGUTL_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKRNGUTLSEQ")
  private Long stockRangeUtilisationSerialNumber;

  @Column(name = "ARLIDR")
  private int airlineIdentifier;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "MSTDOCNUM")
  private String documentNumber;

  @Column(name = "SEQNUM")
  private String sequenceNumber;

  @Column(name = "ASCDOCNUM")
  private long asciiDocumentNumber;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "LSTUPDTIMUTC")
  private ZonedDateTime lastUpdateTimeUTC;

  @Override
  public String getBusinessIdAsString() {
    return super.getCompanyCode()
        + HYPHEN
        + stockHolderCode
        + HYPHEN
        + airlineIdentifier
        + HYPHEN
        + documentType
        + HYPHEN
        + documentSubType;
  }

  @Override
  public String getIdAsString() {
    return super.getCompanyCode()
        + HYPHEN
        + stockHolderCode
        + HYPHEN
        + airlineIdentifier
        + HYPHEN
        + documentType
        + HYPHEN
        + documentSubType;
  }
}
