package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewSortData;
import com.ibsplc.neoicargo.cca.modal.viewfilter.DateRangeFilterData;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData.FIRST_PAGE_NUMBER;

@Getter
@Setter
@NoArgsConstructor
public class CCAListViewFilterVO extends AbstractVO {

    private static final long serialVersionUID = -2405522082118531207L;

    private int page = FIRST_PAGE_NUMBER;

    private int size = DEFAULT_PAGE_SIZE;

    private List<String> ccaReferenceNumber;

    private String masterDocumentNumber;

    private String shipmentPrefix;

    private List<String> currency;

    private List<String> origin;

    private List<String> destination;

    private DateRangeFilterData ccaIssueDate;

    private List<String> ccaAssignee;

    private List<String> ccaType;

    private List<CcaStatus> ccaStatus;

    private List<String> exportBillingStatus;

    private List<String> importBillingStatus;

    private List<String> ccaReason;

    private List<String> ccaSource;

    private List<String> outboundCustomerCode;

    private List<String> inboundCustomerCode;

    private List<String> agentCode;

    private List<String> outboundStationCode;

    private List<String> inboundStationCode;

    private List<String> agentStationCode;

    private List<String> outboundCountryCode;

    private List<String> inboundCountryCode;

    private List<String> agentCountryCode;

    private List<String> outboundAccountNumber;

    private List<String> inboundAccountNumber;

    private List<String> agentIataCode;

    private Boolean outboundCassIndicator;

    private List<CCAListViewSortData> ccaListViewSortData;

    private String companyCode;

    private List<String> agentGroup;
}
