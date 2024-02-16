package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.jpa.history.entity.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MALOPSFLTAUD")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "MALOPSFLTAUDSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALOPSFLTAUD_SEQ")
public class MailFlightAudit  extends AuditEntity implements Serializable  {

    @Id
    @Column(name = "AUDSEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALOPSFLTAUDSEQ")
    private long serialNumber;

    @Column(name = "CMPCOD")
    private String companyCode;

//    @Column(name="AUDENT")
//    private String auditingEntity;


    @Column(name = "FLTCARIDR")
    private int carrierId;

    @Column(name = "FLTNUM")
    private String flightNumber;

    @Column(name = "FLTSEQNUM")
    private long flightSequenceNumber;

    @Column(name = "LEGSERNUM")
    private int legSerialNumber;
}
