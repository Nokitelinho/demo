package com.ibsplc.neoicargo.awb.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AWBUserNotificationKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "TRKAWBSERNUM")
    private Long trackingAwbSerialNumber;

    @Column(name = "USRIDR")
    private String userId;

}
