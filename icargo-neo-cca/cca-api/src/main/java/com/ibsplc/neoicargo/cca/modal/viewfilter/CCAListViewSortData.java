package com.ibsplc.neoicargo.cca.modal.viewfilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class CCAListViewSortData implements Serializable {

    private static final long serialVersionUID = -1327763353373258646L;

    @NotNull
    @JsonProperty("sort_on")
    private String sortOn;

    @NotNull
    private String order;

}
