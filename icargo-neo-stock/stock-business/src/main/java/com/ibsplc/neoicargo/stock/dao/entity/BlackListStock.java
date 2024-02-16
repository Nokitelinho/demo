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
@Table(name = "STKBLKLSTRNG")
public class BlackListStock extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKBLKLSTRNGSERNUM")
  @SequenceGenerator(
      name = "STKBLKLSTRNGSEQ",
      sequenceName = "STKBLKLSTRNG_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKBLKLSTRNGSEQ")
  private Long blackListStockSerialNumber;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "ARLIDR")
  private int airlineIdentifier;

  @Column(name = "STARNG")
  private String startRange;

  @Column(name = "ENDRNG")
  private String endRange;

  @Column(name = "BLKSTA")
  private String status;

  @Column(name = "BLKLSTRMK")
  private String remarks;

  @Column(name = "FRTLVLSTKHLDCOD")
  private String firstLevelStockHolderCode;

  @Column(name = "BLKLSTDAT")
  private ZonedDateTime blacklistDate;

  @Column(name = "ASCSTARNG")
  private long asciiStartRange;

  @Column(name = "ASCENDRNG")
  private long asciiEndRange;

  @Column(name = "ACTCOD")
  private String actionCode;

  @Column(name = "MNLFLG")
  private String isManual = "N";
}
