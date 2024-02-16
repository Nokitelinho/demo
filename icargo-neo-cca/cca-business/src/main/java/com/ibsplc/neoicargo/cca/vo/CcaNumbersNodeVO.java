package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CcaNumbersNodeVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long ccaNumberId;
    private String ccaNumber;
}
