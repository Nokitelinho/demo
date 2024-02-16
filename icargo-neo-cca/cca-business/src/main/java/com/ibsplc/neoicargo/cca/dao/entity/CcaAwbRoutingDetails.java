package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ccaawbrtg")
@Getter
@Setter
@NoArgsConstructor
public class CcaAwbRoutingDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccaawbrtgsernum", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccaawbrtgseq")
    @SequenceGenerator(name = "ccaawbrtgseq", sequenceName = "ccaawbrtg_seq", allocationSize = 1)
    private Long serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false),
            @JoinColumn(name = "rectyp", referencedColumnName = "rectyp", nullable = false)})
    private CCAAwb ccaawb;

    @Column(name = "segorgcod")
    private String origin;

    @Column(name = "segdstcod")
    private String destination;

    @Column(name = "fltcar")
    private String flightCarrierCode;

    @Column(name = "fltnum")
    private String flightNumber;

    @Column(name = "fltdat")
    @DateTimeFormat(pattern = "dd/MMM/yyyy")
    private LocalDate flightDate;

    @Column(name = "fstcar")
    private String firstCarrierCode;

    @Column(name = "pcs")
    private Integer pieces;

    @Column(name = "wgt")
    private Double weight;

    @Column(name = "diswgt")
    private Double displayWeight;

    @Column(name = "diswgtunt")
    private String displayWeightUnit;

    @Column(name = "agrmnttyp")
    private String argumentType;

    @Column(name = "rtgsrc")
    private String source;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CcaAwbRoutingDetails that = (CcaAwbRoutingDetails) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }

}
