package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.tenant.jpa.history.entity.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MALOPSFLTCONAUD")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "MALOPSFLTCONAUDSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALOPSFLTCONAUD_SEQ")
public class ContainerAudit extends AuditEntity implements Serializable  {

    @Id
    @Column(name = "AUDSEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALOPSFLTCONAUDSEQ")
    private long serialNumber;

    @Column(name = "CMPCOD")
    private String companyCode;


    @Column(name="CONNUM")
    private String connum;


}