package com.ibsplc.neoicargo.stock.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.history.entity.AuditEntity;
import java.io.Serializable;
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

@Entity
@Table(name = "stkaud")
@Getter
@Setter
@NoArgsConstructor
public class StockAudit extends AuditEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "audsernum")
  @SequenceGenerator(name = "audstkseq", allocationSize = 1, sequenceName = "stkaud_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audstkseq")
  private long serialNumber;
}
