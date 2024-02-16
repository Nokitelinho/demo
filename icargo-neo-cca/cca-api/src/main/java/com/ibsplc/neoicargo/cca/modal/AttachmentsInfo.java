package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class AttachmentsInfo implements Serializable {

    private static final long serialVersionUID = -5924660604070261755L;

    @JsonProperty("update_attachments_success")
    private Boolean updateAttachmentsSuccess;
}
