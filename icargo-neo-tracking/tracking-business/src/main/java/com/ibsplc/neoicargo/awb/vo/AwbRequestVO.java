package com.ibsplc.neoicargo.awb.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwbRequestVO extends AbstractVO {
    private String awb;

    public String getShipmentPrefix(){
        return awb.split("-")[0];
    }

    public String getMasterDocumentNumber(){
        return awb.split("-")[1];
    }
}
