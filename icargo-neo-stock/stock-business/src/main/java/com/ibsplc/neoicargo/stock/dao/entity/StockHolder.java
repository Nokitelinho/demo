/*
 * StockHolder.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.stock.dao.entity;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_GROUP;
import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a stockholder that can hold the stock for different document types. Every stockholder
 * belongs to a specific type
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STKHLDMST")
@Entity
@Auditable(eventName = STOCK_HOLDER_AUDIT_EVENT_NAME, allFields = false, isParent = true)
public class StockHolder extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKHLDMSTSERNUM")
  @SequenceGenerator(name = "STKHLDMSTSEQ", sequenceName = "STKHLDMST_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKHLDMSTSEQ")
  private Long stockHolderSerialNumber;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "STKHLDTYP")
  @Enumerated(EnumType.STRING)
  @Audit(name = "stockHolderType", changeGroupId = STOCK_HOLDER_GROUP)
  private StockHolderType stockHolderType;

  /** Full qualified name of the stock holder. */
  @Column(name = "STKHLDNAM")
  @Audit(name = "stockHolderName", changeGroupId = STOCK_HOLDER_GROUP)
  private String stockHolderName;

  /** Description of the stock holder */
  @Column(name = "STKHLDDES")
  @Audit(name = "description", changeGroupId = STOCK_HOLDER_GROUP)
  private String description;

  /**
   * Users holding this privilege would be able to perform actions over the stock held by this stock
   * holder
   */
  @Column(name = "CTLPVG")
  @Audit(name = "controlPrivilege", changeGroupId = STOCK_HOLDER_GROUP)
  private String controlPrivilege;

  @Column(name = "CNTADR")
  @Audit(name = "stockHolderContactDetails", changeGroupId = STOCK_HOLDER_GROUP)
  private String stockHolderContactDetails;

  /**
   * Collection of stocks held by the user. Each stock represents the ranges held by the user
   * against a particyular document type Set<Stock>
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockHolder", orphanRemoval = true)
  private Set<Stock> stock;

  public void addStock(Stock stock) {
    stock.setStockHolder(this);
    if (this.stock == null) {
      this.stock = new HashSet<>();
    }
    this.stock.add(stock);
  }

  @Override
  public String getBusinessIdAsString() {
    return super.getCompanyCode() + HYPHEN + stockHolderCode;
  }

  @Override
  public String getIdAsString() {
    return super.getCompanyCode() + HYPHEN + stockHolderCode;
  }
}
