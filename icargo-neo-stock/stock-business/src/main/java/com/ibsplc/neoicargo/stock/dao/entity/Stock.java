/*
 * Stock.java Created on Sep 3, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.stock.dao.entity;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_GROUP;
import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the stock held by a stockholder against a particular document type. Also holds the
 * information regarding the approver and reorder properties
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STKHLDSTK")
@Entity
@Auditable(eventName = STOCK_AUDIT_EVENT_NAME, allFields = false, isParent = true)
public class Stock extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKHLDSTKSERNUM")
  @SequenceGenerator(name = "STKHLDSTKSEQ", sequenceName = "STKHLDSTK_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKHLDSTKSEQ")
  private Long stockSerialNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(
      value = {
        @JoinColumn(
            name = "STKHLDMSTSERNUM",
            referencedColumnName = "STKHLDMSTSERNUM",
            nullable = false)
      })
  private StockHolder stockHolder;

  @Column(name = "ARLIDR")
  private int airlineIdentifier;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "ORDLVL")
  @Audit(name = "reorderLevel", changeGroupId = STOCK_GROUP)
  private long reorderLevel;

  @Column(name = "ORDQTY")
  @Audit(name = "reorderQuantity", changeGroupId = STOCK_GROUP)
  private int reorderQuantity;

  @Column(name = "STKAPRCMPCOD")
  @Audit(name = "stockApproverCompany", changeGroupId = STOCK_GROUP)
  private String stockApproverCompany;

  @Column(name = "STKAPRCOD")
  @Audit(name = "stockApproverCode", changeGroupId = STOCK_GROUP)
  private String stockApproverCode;

  @Column(name = "ORDALT")
  @Audit(name = "reorderAlertFlag", changeGroupId = STOCK_GROUP)
  private String reorderAlertFlag;

  @Column(name = "AUTREQFLG")
  @Audit(name = "autoRequestFlag", changeGroupId = STOCK_GROUP)
  private String autoRequestFlag;

  @Column(name = "AUTGENFLG")
  @Audit(name = "autoPopulateFlag", changeGroupId = STOCK_GROUP)
  private String autoPopulateFlag = "N";

  @Column(name = "STKRMK")
  @Audit(name = "remarks", changeGroupId = STOCK_GROUP)
  private String remarks;

  @Column(name = "PHYSTKQTYAVL")
  private long physicalAvailableStock;

  @Column(name = "PHYSTKQTYALC")
  private long physicalAllocatedStock;

  @Column(name = "MNLSTKQTYAVL")
  private long manualAvailableStock;

  @Column(name = "MNLSTKQTYALC")
  private long manualAllocatedStock;

  @Column(name = "ATOPRCQTY")
  @Audit(name = "autoprocessQuantity", changeGroupId = STOCK_GROUP)
  private int autoprocessQuantity;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "stock", orphanRemoval = true)
  private Set<StockRequest> stockRequests;

  /** Collection of ranges that constitute this stock Set<Range> */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "stock", orphanRemoval = true)
  private Set<Range> ranges;

  public void addRanges(Range... ranges) {
    if (this.ranges == null) {
      this.ranges = new HashSet<>();
    }
    for (Range range : ranges) {
      range.setStock(this);
      this.ranges.add(range);
    }
  }

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

  @Override
  public BaseEntity getParentEntity() {
    return this.stockHolder;
  }
}
