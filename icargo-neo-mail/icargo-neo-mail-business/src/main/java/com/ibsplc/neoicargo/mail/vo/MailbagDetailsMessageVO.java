package com.ibsplc.neoicargo.mail.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
public class MailbagDetailsMessageVO extends BaseMessageVO {
    private static final long serialVersionUID = 1L;
    private Collection<MailbagDetailsVO> mailbagDetailsVOs;
}
