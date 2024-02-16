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
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a StockRangeHistory that can store the stock range distribution for different stock.
 *
 * @author A-3184 & A-3155
 */
@Setter
@Getter
@Slf4j
@Table(name = "STKRNGTXNHIS")
@Entity
public class StockRangeHistory extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKRNGTXNHISSERNUM")
  @SequenceGenerator(
      name = "STKRNGTXNHISSEQ",
      sequenceName = "STKRNGTXNHIS_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKRNGTXNHISSEQ")
  private Long stockRangeHistorySerialNumber;

  @Column(name = "rngtyp")
  private String rangeType;

  @Column(name = "starng")
  private String startRange;

  @Column(name = "endrng")
  private String endRange;

  @Column(name = "ascstarng")
  private long asciiStartRange;

  @Column(name = "ascendrng")
  private long asciiEndRange;

  @Column(name = "numdoc")
  private long numberOfDocuments;

  @Column(name = "status")
  private String status;

  @Column(name = "txndat")
  private ZonedDateTime transactionDate;

  @Column(name = "accnum")
  private String accountNumber;

  @Column(name = "tostkhldcod")
  private String toStockHolderCode;

  @Column(name = "usrcod")
  private String userId;

  @Column(name = "txnrmk")
  private String remarks;

  @Column(name = "vodchg")
  private double voidingCharge;

  @Column(name = "curcod")
  private String currencyCode;

  @Column(name = "autalcflg")
  private String autoAllocated;

  @Column(name = "arlidr")
  private int airlineIdentifier;

  @Column(name = "frmstkhldcod")
  private String fromStockHolderCode;

  @Column(name = "doctyp")
  private String documentType;

  @Column(name = "docsubtyp")
  private String documentSubType;
}
