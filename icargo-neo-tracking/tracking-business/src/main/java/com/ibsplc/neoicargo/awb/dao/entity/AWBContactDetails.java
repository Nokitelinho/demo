package com.ibsplc.neoicargo.awb.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TRKAWBPII")
@Getter
@Setter
@NoArgsConstructor
public class AWBContactDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRKAWBSERNUM", updatable = false, nullable = false)
    private Long serialNumber;

    @OneToOne
    @JoinColumn(name = "TRKAWBSERNUM", referencedColumnName = "TRKAWBSERNUM", nullable = false)
    @MapsId
    private Awb awb;

    @Column(name = "CMPCOD")
    private String companyCode;

    @Column(name = "SHPCOD")
    private String shipperCode;

    @Type(type = "jsonb")
    @Column(name = "SHPDTL", columnDefinition = "json")
    private Object shipperDetails;

    @Column(name = "CNSCOD")
    private String consigneeCode;

    @Type(type = "jsonb")
    @Column(name = "CNSDTL", columnDefinition = "json")
    private Object consigneeDetails;
}