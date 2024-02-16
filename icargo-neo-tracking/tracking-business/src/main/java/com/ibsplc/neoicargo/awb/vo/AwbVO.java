package com.ibsplc.neoicargo.awb.vo;

import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AwbVO extends AbstractVO {
    //    private Long serialNumber;
    private String companyCode;
    private String shipmentPrefix;
    private String masterDocumentNumber;
    private Long shipmentSequenceNumber;
    private String origin;
    private String destination;
    private int statedPieces;
    private Quantity<Weight> statedWeight;
    private Quantity<Volume> statedVolume;
    private Units unitsOfMeasure;
    private String productName;
    private String specialHandlingCode;
    private String shipmentDescription;
    private String awbStatus;
    private AwbContactDetailsVO awbContactDetailsVO;
    private LocalDateTime lastUpdatedTime;
    private String lastUpdatedUser;

    @Override
    public String getBusinessId() {
        return String.format("%s-%s", this.getShipmentPrefix(), this.getMasterDocumentNumber());
    }
}
