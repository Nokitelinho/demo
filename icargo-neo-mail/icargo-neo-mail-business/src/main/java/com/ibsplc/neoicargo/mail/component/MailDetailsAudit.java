package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.tenant.jpa.history.entity.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MALOPSAUD")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "MALOPSAUDSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALOPSAUD_SEQ")
public class MailDetailsAudit extends AuditEntity implements Serializable  {

    @Id
    @Column(name = "AUDSEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALOPSAUDSEQ")
    private long serialNumber;

    @Column(name = "CMPCOD")
    private String companyCode;

    @Column(name="MALIDR")
    private String mailIdr;


}