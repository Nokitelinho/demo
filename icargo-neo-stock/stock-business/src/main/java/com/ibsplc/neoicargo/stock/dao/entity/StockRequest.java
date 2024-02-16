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

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STKREQMST")
@Entity
public class StockRequest extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKREQMSTSERNUM")
  @SequenceGenerator(name = "STKREQMSTSEQ", sequenceName = "STKREQMST_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKREQMSTSEQ")
  private Long stockRequestSerialNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(
      value = {
        @JoinColumn(
            name = "STKHLDSTKSERNUM",
            referencedColumnName = "STKHLDSTKSERNUM",
            nullable = false)
      })
  private Stock stock;

  @Column(name = "REQDAT")
  private ZonedDateTime requestDate;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "MNLSTKFLG")
  private String isManual;

  @Column(name = "REQSTA")
  private String status;

  @Column(name = "REQSTKQTY")
  private long requestedStock;

  @Column(name = "APRSTKQTY")
  private long approvedStock;

  @Column(name = "ALCSTKQTY")
  private long allocatedStock;

  @Column(name = "REQRMK")
  private String remarks;

  @Column(name = "APRREJREM")
  private String approvalRemarks;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "REQCRTUSR")
  private String requestCreatedBy;

  @Column(name = "REQREFNUM")
  private String requestRefNumber;

  @Column(name = "ARLIDR")
  private Integer airlineIdentifier;
}
