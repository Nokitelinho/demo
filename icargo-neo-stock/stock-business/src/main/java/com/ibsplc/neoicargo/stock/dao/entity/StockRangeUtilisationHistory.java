/*
 * StockRangeUtilisationHistory.java Created on Jan 18,2008.
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.stock.dao.entity;

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
import lombok.Setter;

@Table(name = "STKRNGUTLHIS")
@Entity
@Getter
@Setter
public class StockRangeUtilisationHistory extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKRNGUTLHISSERNUM")
  @SequenceGenerator(
      name = "STKRNGUTLHISSEQ",
      sequenceName = "STKRNGUTLHIS_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKRNGUTLHISSEQ")
  private Long stockRangeUtilisationHistorySerialNumber;

  @Column(name = "ARLIDR")
  private int airlineIdentifier;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "HISSEQNUM")
  private String serialNumber;

  @Column(name = "STARNG")
  private String startRange;

  @Column(name = "ENDRNG")
  private String endRange;

  @Column(name = "ASCSTARNG")
  private long asciiStartRange;

  @Column(name = "ASCENDRNG")
  private long asciiEndRange;

  @Column(name = "NUMDOC")
  private long numberOfDocuments;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "RNGTYP")
  private String rangeType;

  @Column(name = "TXNDAT")
  private ZonedDateTime transactionDate;
}
