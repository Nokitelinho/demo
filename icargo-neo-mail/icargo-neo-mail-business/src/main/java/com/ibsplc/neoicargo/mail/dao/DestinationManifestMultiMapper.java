package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DestinationManifestMultiMapper implements MultiMapper<MailManifestVO> {

    public List<MailManifestVO> map(ResultSet rs) throws SQLException {
        log.debug("DestinationManifestMultiMapper", "map");
        Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
        MailManifestVO mailManifestVO = new MailManifestVO();
        List<MailManifestVO> mailManifestVOs = new ArrayList<MailManifestVO>();
        mailManifestVOs.add(mailManifestVO);
        Collection<ContainerDetailsVO> containerDetails =
                new ArrayList<ContainerDetailsVO>();
        mailManifestVO.setContainerDetails(containerDetails);
        ContainerDetailsVO containerDetailsVO = null;
        MailSummaryVO mailSummaryVO = null;
        Collection<MailSummaryVO> mailSummaryVOs = null;

        String currContainerKey = null;
        String prevContainerKey = null;
        String currDestnKey = null;
        String prevDestnKey = null;

        String containerNum = null;
        int segSerialNum = 0;
        String destination = null;
        String categoryCode = null;
        String mailClass = null;
        String origin	= null;

        int sumbags = 0;
        double sumWeight = 0;
        int totalbags = 0;
        double totalWeight = 0;

        while(rs.next()) {
            containerNum = rs.getString("ULDNUM");
            segSerialNum = rs.getInt("SEGSERNUM");

            currContainerKey = new StringBuilder().append(containerNum).
                    append(segSerialNum).toString();
            if(!currContainerKey.equals(prevContainerKey)) {
                prevContainerKey = currContainerKey;
                containerDetailsVO = new ContainerDetailsVO();
                containerDetailsVO.setContainerNumber(containerNum);
                containerDetailsVO.setSegmentSerialNumber(segSerialNum);
                populateContainerDetailsVO(containerDetailsVO, rs);
                //totalbags += containerDetailsVO.getTotalBags();
                //totalWeight += containerDetailsVO.getTotalWeight();
                //totalWeight += containerDetailsVO.getTotalWeight().getRoundedDisplayValue();//added by A-7371
                containerDetails.add(containerDetailsVO);
                mailSummaryVOs = new ArrayList<MailSummaryVO>();
                containerDetailsVO.setMailSummaryVOs(mailSummaryVOs);
                mailSummaryVO = null;
                prevDestnKey = null;
            }

            String doe = rs.getString("DSTEXGOFC");
            destination = rs.getString("DSTCTY");
            origin		= rs.getString("ORGCTY");//Added as part of ICRD-113230
            if(doe != null && doe.length() > 0 &&
                    destination != null && destination.length() > 0) {
//				destination = doe.substring(2,5);
                categoryCode = rs.getString("MALCTGCOD");
                mailClass = rs.getString("MALCLS");

                for(int cls=0;cls < MailConstantsVO.MILITARY_CLASS.length;cls++){
                    if(MailConstantsVO.MILITARY_CLASS[cls].equals(mailClass)){
                        categoryCode = "M";
                    }
                }

                currDestnKey = new StringBuilder().append(currContainerKey).
                        append(destination).
                        append(categoryCode).
                        append(origin).toString();//Added as part of ICRD-113230
                if(!currDestnKey.equals(prevDestnKey)) {
                    sumbags = 0;
                    sumWeight = 0;
                    prevDestnKey = currDestnKey;
                    mailSummaryVO = new MailSummaryVO();
                    mailSummaryVO.setDestination(destination);
                    mailSummaryVO.setMailCategory(categoryCode);
                    mailSummaryVO.setOrigin(origin);//Added as part of ICRD-113230
                    mailSummaryVOs.add(mailSummaryVO);
                }
                if(mailSummaryVO != null) {
                    sumbags += rs.getInt("ACPBAG");
                    sumWeight += rs.getDouble("ACPWGT");
                    mailSummaryVO.setOriginPA(rs.getString("ORGPOA"));
                    mailSummaryVO.setDestinationPA(rs.getString("DSTPOA"));
                    mailSummaryVO.setBagCount(sumbags);
                    //mailSummaryVO.setTotalWeight(sumWeight);
                   mailSummaryVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(sumWeight),BigDecimal.valueOf(0.0),"K"));
                    containerDetailsVO.setTotalBags(rs.getInt("ACPBAG")+containerDetailsVO.getTotalBags());

                    if (Objects.nonNull(containerDetailsVO.getTotalWeight())) {
                            containerDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(0.0),BigDecimal.valueOf(rs.getDouble("ACPWGT")).add(containerDetailsVO.getTotalWeight().getValue()),"K"));
                    }else{
                        containerDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(0.0),BigDecimal.valueOf(rs.getDouble("ACPWGT")),"K"));
                    }

                    totalbags += rs.getInt("ACPBAG");
                    totalWeight +=rs.getDouble("ACPWGT");
                }
            }
        }
        mailManifestVO.setTotalbags(totalbags);
       Quantity totalWt=  quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(totalWeight),BigDecimal.valueOf(0.0),"K");
        mailManifestVO.setTotalWeight(totalWt);
        //mailManifestVO.setTotalWeight(totalWeight);
        log.debug("DestinationManifestMultiMapper", "map");
        return mailManifestVOs;
    }

    /**
     * TODO Purpose
     * Jan 18, 2007, A-1739
     * @param containerDetailsVO
     * @param rs
     * @throws SQLException
     */
    private void populateContainerDetailsVO(
            ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
        //containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
        //containerDetailsVO.setTotalWeight(rs.getDouble("BAGWGT"));
        Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
     containerDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.ZERO,BigDecimal.valueOf(0.0),"K"));
        containerDetailsVO.setPou(rs.getString("POU"));
        containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));

    }
}

