package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ccawrkflwdtl")
@Getter
@Setter
@NoArgsConstructor
public class CcaWorkflow extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccawrkflwdtlsernum")
    @SequenceGenerator(name = "ccawrkflwdtlseq", allocationSize = 1, sequenceName = "ccawrkflwdtl_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccawrkflwdtlseq")
    private Long serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false)
    private CcaMaster ccaMaster;

    @Column(name = "usrid")
    private String userId;

    @Column(name = "usrnam")
    private String username;

    @Column(name = "ccasta")
    @Enumerated(EnumType.STRING)
    private CcaStatus ccaStatus;

    @Column(name = "reqdat")
    private LocalDateTime requestedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CcaWorkflow that = (CcaWorkflow) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }

}
