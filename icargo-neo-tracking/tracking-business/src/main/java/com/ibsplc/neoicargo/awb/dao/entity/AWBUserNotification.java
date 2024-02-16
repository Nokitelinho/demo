package com.ibsplc.neoicargo.awb.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TRKAWBUSRNTFPII")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
@Getter
@Setter
@NoArgsConstructor
public class AWBUserNotification extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AWBUserNotificationKey notificationsKey;

    @Type(type = "list-array")
    @Column(name = "NTFMILSTN", columnDefinition = "text[]")
    private List<String> notificationMilestones;

    @Type(type = "list-array")
    @Column(name = "NTFEMLADR", columnDefinition = "text[]")
    private List<String> emails;
}