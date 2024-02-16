package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.history.entity.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ccaaud")
@Getter
@Setter
@NoArgsConstructor
public class CcaAudit extends AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "audsernum")
    @SequenceGenerator(name = "audccaseq", allocationSize = 1, sequenceName = "ccaaud_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audccaseq")
    private long serialNumber;

}
