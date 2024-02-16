/*
 * Range.java Created on Jul 20, 2005
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the range of documents belonging to a particular stock Aggregation of ranges
 * constitute a stock
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STKRNG")
@Entity
public class Range extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKRNGSERNUM")
  @SequenceGenerator(name = "STKRNGSEQ", sequenceName = "STKRNG_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKRNGSEQ")
  private Long rangeSerialNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(
      value = {
        @JoinColumn(
            name = "STKHLDSTKSERNUM",
            referencedColumnName = "STKHLDSTKSERNUM",
            nullable = false)
      })
  private Stock stock;

  @Column(name = "STARNG")
  private String startRange;

  @Column(name = "ENDRNG")
  private String endRange;

  @Column(name = "DOCNUM")
  private long numberOfDocuments;

  @Column(name = "MNLFLG")
  private String isManual = "N";

  @Column(name = "ASCSTARNG")
  private long asciiStartRange;

  @Column(name = "ASCENDRNG")
  private long asciiEndRange;

  @Column(name = "RNGACPDAT")
  private ZonedDateTime stockAcceptanceDate;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "ARLIDR")
  private int airlineIdentifier;
}
