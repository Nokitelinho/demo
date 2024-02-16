package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CcaAwbRoutingDetailsVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    private String origin;

    private String destination;

    private String flightNumber;

    private LocalDate flightDate;

    private Integer pieces;

    private Quantity<Weight> weight;

    private String argumentType;

    private String source;

    private String firstCarrierCode;

    private String flightCarrierCode;

}
