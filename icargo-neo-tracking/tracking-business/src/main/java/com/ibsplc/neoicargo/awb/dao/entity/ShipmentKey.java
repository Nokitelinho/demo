package com.ibsplc.neoicargo.awb.dao.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ShipmentKey implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name = "SHPPFX")
    private String shipmentPrefix;

    @Column(name = "MSTDOCNUM")
    private String masterDocumentNumber;

    @Override
    public String toString(){
        return String.format("%s-%s", this.getShipmentPrefix(), this.getMasterDocumentNumber());
    }

}
