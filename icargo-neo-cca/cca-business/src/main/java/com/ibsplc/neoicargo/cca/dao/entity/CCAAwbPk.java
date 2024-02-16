package com.ibsplc.neoicargo.cca.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CCAAwbPk implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long ccaSerialNumber;

    @Column(name = "rectyp")
    private String recordType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var ccaAwbPk = (CCAAwbPk) o;
        return Objects.equals(recordType, ccaAwbPk.recordType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordType);
    }

}
