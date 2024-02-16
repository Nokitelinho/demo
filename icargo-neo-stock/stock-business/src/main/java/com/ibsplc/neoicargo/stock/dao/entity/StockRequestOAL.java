/*
 * StockRequestForOAL.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
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

@Getter
@Setter
@Table(name = "STKREQOAL")
@Entity
public class StockRequestOAL extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "STKREQOALSERNUM")
  @SequenceGenerator(name = "STKREQOALSEQ", sequenceName = "STKREQOAL_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STKREQOALSEQ")
  private Long stockRequestOALSerialNumber;

  @Column(name = "ARPCOD")
  private String airportCode;

  @Column(name = "ARLIDR")
  private int airlineIdentifier;

  @Column(name = "DOCTYP")
  private String documentType;

  @Column(name = "DOCSUBTYP")
  private String documentSubType;

  @Column(name = "SERNUM")
  private int serialNumber;

  @Column(name = "MODCOM")
  private String modeOfCommunication;

  @Column(name = "REQCNT")
  private String content;

  @Column(name = "REQCMPFLG")
  private String isRequestCompleted;

  @Column(name = "REQADR")
  private String address;

  @Column(name = "REQDAT")
  private ZonedDateTime requestedDate;

  @Column(name = "ACTTIM")
  private ZonedDateTime actionTime;
}
