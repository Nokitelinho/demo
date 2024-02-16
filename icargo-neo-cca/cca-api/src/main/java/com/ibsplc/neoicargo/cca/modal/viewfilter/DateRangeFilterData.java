package com.ibsplc.neoicargo.cca.modal.viewfilter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class DateRangeFilterData implements Serializable {

    private static final long serialVersionUID = -6567684411512445779L;

    @Nullable
    private String from;

    @Nullable
    private String to;

    @Nullable
    private LocalDate fromLocalDate;

    @Nullable
    private LocalDate toLocalDate;

}
