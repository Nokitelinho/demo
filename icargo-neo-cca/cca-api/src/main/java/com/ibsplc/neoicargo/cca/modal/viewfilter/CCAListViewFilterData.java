package com.ibsplc.neoicargo.cca.modal.viewfilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CCAListViewFilterData implements Serializable {

    private static final long serialVersionUID = -8852548149708338699L;

    public static final int DEFAULT_PAGE_SIZE = 25;
    public static final int FIRST_PAGE_NUMBER = 1;

    @JsonProperty("page_number")
    private int page = FIRST_PAGE_NUMBER;

    @JsonProperty("page_size")
    private int size = DEFAULT_PAGE_SIZE;

    @JsonProperty("cca_number")
    private List<String> ccaReferenceNumber;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    private List<String> currency;

    private List<String> origin;

    private List<String> destination;

    @JsonProperty("issue_date")
    private DateRangeFilterData ccaIssueDate;

    @JsonProperty("assignee")
    private List<String> ccaAssignee;

    @JsonProperty("cca_type")
    private List<String> ccaType;

    @JsonProperty("cca_status")
    private List<CcaStatus> ccaStatus;

    @JsonProperty("export_billing_status")
    private List<String> exportBillingStatus;

    @JsonProperty("import_billing_status")
    private List<String> importBillingStatus;

    @JsonProperty("cca_reason_codes")
    private List<String> ccaReason;

    @JsonProperty("cca_source")
    private List<String> ccaSource;

    @JsonProperty("outbound_customer_code")
    private List<String> outboundCustomerCode;

    @JsonProperty("inbound_customer_code")
    private List<String> inboundCustomerCode;

    @JsonProperty("agent_code")
    private List<String> agentCode;

    @JsonProperty("outbound_station")
    private List<String> outboundStationCode;

    @JsonProperty("inbound_station")
    private List<String> inboundStationCode;

    @JsonProperty("agent_station")
    private List<String> agentStationCode;

    @JsonProperty("outbound_country")
    private List<String> outboundCountryCode;

    @JsonProperty("inbound_country")
    private List<String> inboundCountryCode;

    @JsonProperty("agent_country")
    private List<String> agentCountryCode;

    @JsonProperty("outbound_account_number")
    private List<String> outboundAccountNumber;

    @JsonProperty("inbound_account_number")
    private List<String> inboundAccountNumber;

    @JsonProperty("agent_iata_code")
    private List<String> agentIataCode;

    @JsonProperty("outbound_cass_indicator")
    private Boolean outboundCassIndicator;

    @JsonProperty("sort_basis")
    private List<CCAListViewSortData> ccaListViewSortData;

    private String companyCode;

    @JsonProperty("agent_group")
    private List<String> agentGroup;

}
