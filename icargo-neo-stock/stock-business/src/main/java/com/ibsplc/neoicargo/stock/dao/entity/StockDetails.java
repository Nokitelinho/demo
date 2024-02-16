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

@Setter
@Getter
@Slf4j
@Table(name = "STKHLDSTKDTL")
@Entity
public class StockDetails extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKHLDSTKDTLSERNUM")
  @SequenceGenerator(
      name = "STKHLDSTKDTLSEQ",
      sequenceName = "STKHLDSTKDTL_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKHLDSTKDTLSEQ")
  private Long stockHolderStockDetailSerialNumber;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "TXNDATSTR")
  private Integer txnDateString;

  @Column(name = "OPGBAL")
  private long openingBalance;

  @Column(name = "RCVSTK")
  private long receivedStock;

  @Column(name = "ALCSTK")
  private long allocatedStock;

  @Column(name = "TFDSTK")
  private long transferredStock;

  @Column(name = "RTNSTK")
  private long returnStock;

  @Column(name = "RTNUTLSTK")
  private long returnUtilisedStock;

  @Column(name = "BLKLSTSTK")
  private long blackListedStock;

  @Column(name = "UTLSTK")
  private long utilisedStock;

  @Column(name = "CLSBAL")
  private long closingBalance;

  @Column(name = "TXNDATUTC")
  private ZonedDateTime txnDateUTC;

  @Column(name = "LSTUPDTIMUTC")
  private ZonedDateTime lastUpdateTimeUTC;
}
