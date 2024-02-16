package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.cca.vo.blockspacedetails.CcaBlockSpaceDetailsVO;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class CcaRoutingVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long rtgsernum;

    private Long fltSerialNumber;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private int sequenceNumber;

    private int duplicateNumber;

    private String companyCode;

    private int flightCarrierId;

    private String flightCarrierCode;

    private LocalDate flightDate;

    private String flightNumber;

    private String flightType;

    private int pieces;

    private Quantity<Weight> chgWeight;

    private Double chgwgt;

    private Quantity<Weight> volWeight;

    private Double volwgt;

    private Quantity<Weight> weight;

    private Double wgt;

    private Quantity<Volume> volume;

    private Double vol;

    private String segOrgCod;

    private String segDstCod;

    private String routingSource;

    private String ctmReferenceNumber;

    private LocalDate ctmFlightDate;

    private String firstCarrierCode;

    private int documentOwnerId;

    private String uldList;

    private String agreementType;

    private int routeOrder;

    private Collection<CcaBlockSpaceDetailsVO> bsaDetails;

    private boolean tolApplied;

}
