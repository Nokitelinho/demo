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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "STKTRSRNG")
public class TransitStock extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKTRSRNGSERNUM")
  @SequenceGenerator(name = "STKTRSRNGSEQ", sequenceName = "STKTRSRNG_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKTRSRNGSEQ")
  private Long transitStockSerialNumber;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "ARLIDR")
  private int airlineIdentifier;

  @Column(name = "FRMSTKHLDCOD")
  private String stockControlFor;

  @Column(name = "ACTSTARNG")
  private String actualStartRange;

  @Column(name = "ACTENDRNG")
  private String actualEndRange;

  @Column(name = "MISSTARNG")
  private String missingStartRange;

  @Column(name = "MISENDRNG")
  private String missingEndRange;

  @Column(name = "CFRSTA")
  private String confirmStatus;

  @Column(name = "CFRDAT")
  private ZonedDateTime confirmDate;

  @Column(name = "ASCMISSTARNG")
  private long asciiMissingStartRange;

  @Column(name = "ASCMISENDRNG")
  private long asciiMissingEndRange;

  @Column(name = "TXNCOD")
  private String txnCode;

  @Column(name = "TXNDAT")
  private ZonedDateTime txnDate;

  @Column(name = "MNLFLG")
  private String isManual;

  @Column(name = "MISNUMDOC")
  private long missingNumberOfDocs;

  @Column(name = "NUMDOC")
  private long numberOfDocs;

  @Column(name = "MISRMK")
  private String missingRemarks;

  @Column(name = "TXNRMK")
  private String txnRemarks;
}
