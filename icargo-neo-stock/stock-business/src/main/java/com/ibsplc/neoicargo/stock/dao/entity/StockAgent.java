package com.ibsplc.neoicargo.stock.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
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

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "STKHLDAGT")
public class StockAgent extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKHLDAGTSERNUM")
  @SequenceGenerator(name = "STKHLDAGTSEQ", sequenceName = "STKHLDAGT_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKHLDAGTSEQ")
  private Long stockAgentSerialNumber;

  @Column(name = "STKHLDCOD")
  private String stockHolderCode;

  @Column(name = "AGTCOD")
  private String agentCode;
}
