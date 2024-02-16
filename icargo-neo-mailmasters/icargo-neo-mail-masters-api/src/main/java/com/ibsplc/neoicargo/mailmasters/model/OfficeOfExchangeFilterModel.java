package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
@Setter
@Getter
public class OfficeOfExchangeFilterModel  extends BaseModel {
    private String companyCode;
    private Collection<String> officeOfExchanges;
}
