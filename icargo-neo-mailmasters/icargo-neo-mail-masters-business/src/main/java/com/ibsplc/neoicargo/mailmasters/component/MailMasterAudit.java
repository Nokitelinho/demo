package com.ibsplc.neoicargo.mailmasters.component;

import com.ibsplc.neoicargo.framework.tenant.jpa.history.entity.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MALMSTAUD")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "MALMSTAUDSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALMSTAUD_SEQ")
public class MailMasterAudit extends AuditEntity implements Serializable {

    @Id
    @Column(name = "AUDSEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMSTAUDSEQ")
    private long serialNumber;

    @Column(name = "CMPCOD")
    private String companyCode;

   @Column(name="AUDENT")
   private String auditingEntity;

}
