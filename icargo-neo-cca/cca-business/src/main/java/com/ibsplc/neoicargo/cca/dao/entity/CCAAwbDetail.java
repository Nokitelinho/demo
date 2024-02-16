package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.util.QuantityUtil.getDisplayValue;
import static com.ibsplc.neoicargo.cca.util.QuantityUtil.getValue;

@Entity
@Table(name = "ccaawbdtl")
@Getter
@Setter
@NoArgsConstructor
public class CCAAwbDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccaawbdtlsernum", updatable = false, nullable = false)
    @SequenceGenerator(name = "CCAAWBDTLSEQ", sequenceName = "CCAAWBDTL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CCAAWBDTLSEQ")
    private Long serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false),
            @JoinColumn(name = "rectyp", referencedColumnName = "rectyp", nullable = false)})
    private CCAAwb ccaawb;

    @Column(name = "pcs")
    private long numberOfPieces;

    @Column(name = "comcod")
    private String commodityCode;

    @Column(name = "comdes")
    private String commodityDescription;

    @Embedded
    @AttributeOverride(name = "weight", column = @Column(name = "diswgtunt"))
    @AttributeOverride(name = "volume", column = @Column(name = "disvolunt"))
    private EUnitOfQuantity unitOfQuantity;

    @Column(name = "wgt")
    private Double weightOfShipment;

    @Column(name = "diswgt")
    private Double displayWeightOfShipment;

    @Column(name = "vol")
    private Double volumeOfShipment;

    @Column(name = "disvol")
    private Double displayVolumeOfShipment;

    @Column(name = "chgwgt")
    private Double chargeableWeight;

    @Column(name = "dischgwgt")
    private Double displayChargeableWeight;

    @Column(name = "volwgt")
    private Double volumetricWeight;

    @Column(name = "disvolwgt")
    private Double displayVolumetricWeight;

    @Column(name = "adjwgt")
    private Double adjustedWeight;

    @Column(name = "disadjwgt")
    private Double displayAdjustedWeight;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ccaAwbDetail", orphanRemoval = true)
    private Set<CcaAwbDetailDimensions> dimensions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ccaAwbDetail", orphanRemoval = true)
    private Set<CcaAwbUldDetail> ulds;

    public CCAAwbDetail update(@NotNull final CcaRatingDetailVO ratingDetailVO,
                               @NotNull final QuantityMapper quantityMapper,
                               @NotNull final CcaAwbDetailMapper ccaAwbDetailMapper) {
        final var eUnitOfQuantity = new EUnitOfQuantity();
        if (ratingDetailVO.getVolumeOfShipment() != null) {
            eUnitOfQuantity.setVolume(
                    quantityMapper.unitConvetion(ratingDetailVO.getVolumeOfShipment().getDisplayUnit())
            );
        }
        if (ratingDetailVO.getWeightOfShipment() != null) {
            eUnitOfQuantity.setWeight(
                    quantityMapper.unitConvetion(ratingDetailVO.getWeightOfShipment().getDisplayUnit())
            );
        }

        setCommodityCode(ratingDetailVO.getCommodityCode());
        setCommodityDescription(ratingDetailVO.getShipmentDescription());
        setUnitOfQuantity(eUnitOfQuantity);
        setVolumeOfShipment(getValue(ratingDetailVO.getVolumeOfShipment()));
        setDisplayVolumeOfShipment(getDisplayValue(ratingDetailVO.getVolumeOfShipment()));
        setWeightOfShipment(getValue(ratingDetailVO.getWeightOfShipment()));
        setDisplayWeightOfShipment(getDisplayValue(ratingDetailVO.getWeightOfShipment()));
        setChargeableWeight(getValue(ratingDetailVO.getChargeableWeight()));
        setDisplayChargeableWeight(getDisplayValue(ratingDetailVO.getChargeableWeight()));
        setVolumetricWeight(getValue(ratingDetailVO.getVolumetricWeight()));
        setDisplayVolumetricWeight(getDisplayValue(ratingDetailVO.getVolumetricWeight()));
        setAdjustedWeight(getValue(ratingDetailVO.getAdjustedWeight()));
        setDisplayAdjustedWeight(getDisplayValue(ratingDetailVO.getAdjustedWeight()));
        setNumberOfPieces(ratingDetailVO.getNumberOfPieces());
        setCommodityDescription(ratingDetailVO.getShipmentDescription());
        updateDimensions(ratingDetailVO, ccaAwbDetailMapper);
        updateUlds(ratingDetailVO, ccaAwbDetailMapper);
        return this;
    }

    private void updateDimensions(CcaRatingDetailVO ratingDetailVO, CcaAwbDetailMapper ccaAwbDetailMapper) {
        if (dimensions != null) {
            dimensions.clear();
        } else {
            setDimensions(new HashSet<>());
        }

        final var routingDetails = ratingDetailVO.getDimensions();
        if (routingDetails != null) {
            routingDetails.forEach(dim -> {
                final var entity = ccaAwbDetailMapper.constructCCAAwbDetailDimensions(dim);
                entity.setCcaAwbDetail(this);
                dimensions.add(entity);
            });
        }
    }

    private void updateUlds(CcaRatingDetailVO ratingDetailVO, CcaAwbDetailMapper ccaAwbDetailMapper) {
        if (ulds != null) {
            ulds.clear();
        } else {
            setUlds(new HashSet<>());
        }

        final var routingDetails = ratingDetailVO.getUlds();
        if (routingDetails != null) {
            routingDetails.forEach(uld -> {
                final var entity = ccaAwbDetailMapper.constructCcaAwbDetailUld(uld);
                entity.setCcaAwbDetail(this);
                ulds.add(entity);
            });
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CCAAwbDetail that = (CCAAwbDetail) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }

    @Override
    public BaseEntity getParentEntity() {
        return this.ccaawb;
    }

    @Override
    public String getIdAsString() {
        return getParentEntity().getIdAsString() + "-" + getSerialNumber();
    }

}
