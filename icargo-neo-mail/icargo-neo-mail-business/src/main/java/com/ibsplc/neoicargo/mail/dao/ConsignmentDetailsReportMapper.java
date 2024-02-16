package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ConsignmentDetailsReportMapper implements MultiMapper<ConsignmentDocumentVO> {
    LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
    Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);


    public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {

        List<ConsignmentDocumentVO>  consignmentDocumentVOs  = null;

        String currentKey = null;
        String previousKey = null;
        StringBuilder stringBuilder = null;
        Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
        Collection<RoutingInConsignmentVO> routingInConsignmentVOs = null;
        ConsignmentDocumentVO consignmentDocumentVO = null;
        MailInConsignmentVO mailInConsignmentVO = null;
        String mailCurrentKey = null;
        String mailPreviousKey = null;
        StringBuilder mailKey = null;
        Collection<Integer> routingSerialNumbers = null;
        RoutingInConsignmentVO routingInConsignmentVO = null;
        Collection<String> mailInConsignment = null;


        while (rs.next()) {
            if (consignmentDocumentVOs == null) {
                consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
            }
            stringBuilder = new StringBuilder();
            currentKey = stringBuilder.append(rs.getString("CMPCOD")).append(
                            rs.getString("CSGDOCNUM")).append(rs.getString("POACOD"))
                    .append(rs.getInt("CSGSEQNUM")).toString();

            if (!currentKey.equals(previousKey)) {

                consignmentDocumentVO = new ConsignmentDocumentVO();
                collectConsignmentDetails(consignmentDocumentVO, rs);
                mailInConsignmentVOs = new ArrayList<MailInConsignmentVO>();
                routingInConsignmentVOs = new ArrayList<RoutingInConsignmentVO>();

                consignmentDocumentVO
                        .setMailInConsignmentcollVOs(mailInConsignmentVOs);
                consignmentDocumentVO
                        .setRoutingInConsignmentVOs(routingInConsignmentVOs);

                consignmentDocumentVOs.add(consignmentDocumentVO);
                previousKey = currentKey;
            }

            mailKey = new StringBuilder();
            mailKey.append(rs.getString("ORGEXGOFC"))
                    .append(rs.getString("DSTEXGOFC"));
            mailCurrentKey = mailKey.toString();
            if(mailInConsignment == null){
                mailInConsignment = new ArrayList<String>();
            }
            if (!mailInConsignment.contains(mailCurrentKey)) {

                mailInConsignmentVO = new MailInConsignmentVO();

                mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ARPCODORG"));
                mailInConsignmentVO.setDestinationExchangeOffice(rs
                        .getString("ARPCODDST"));
                collectMailDetails(mailInConsignmentVO, rs);


                mailInConsignmentVOs.add(mailInConsignmentVO);

                mailInConsignment.add(mailCurrentKey);
            }else{
                for(MailInConsignmentVO mailVO : mailInConsignmentVOs ){
                    if(mailVO.getOriginExchangeOffice().equals(rs.getString("ARPCODORG"))
                            && mailVO.getDestinationExchangeOffice().equals(rs.getString("ARPCODDST"))){
                        collectMailDetails(mailVO, rs);
                    }
                }
            }
            if (rs.getInt("RTGSERNUM") > 0) {
                routingInConsignmentVO = new RoutingInConsignmentVO();
                routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
                routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
                routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
                routingInConsignmentVO.setPol(rs.getString("POL"));
                if (routingSerialNumbers == null) {
                    routingSerialNumbers = new ArrayList<Integer>();
                }
                if (!routingSerialNumbers.contains(Integer
                        .valueOf(routingInConsignmentVO
                                .getRoutingSerialNumber()))) {
                    routingSerialNumbers.add(Integer
                            .valueOf(routingInConsignmentVO
                                    .getRoutingSerialNumber()));
                    routingInConsignmentVOs.add(routingInConsignmentVO);
                }
            }
        }
        return consignmentDocumentVOs;


    }


    private void collectConsignmentDetails(
            ConsignmentDocumentVO consignmentDocumentVO, ResultSet rs)
            throws SQLException {
        ContextUtil contextUtil = ContextUtil.getInstance();
        LoginProfile logonAttributes = contextUtil.callerLoginProfile();



        StringBuilder airport  = null;
        airport = new StringBuilder();

        airport = airport.append(rs.getString("ARPCODDST")).append(",").append(
                rs.getString("CTYCODDST")).append(",").append(rs.getString("CNTCODDST"));

        consignmentDocumentVO.setDestination(airport.toString());

        if (rs.getDate("CSGDAT") != null) {
            if(rs.getString("ARPCODORG")!=null && !rs.getString("ARPCODORG").isEmpty()){
                consignmentDocumentVO.setConsignmentDate(
                        localDateUtil.getLocalDate(rs
                                .getString("ARPCOD"), rs.getTimestamp("CSGDAT")));
            }else{
                consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(logonAttributes.getAirportCode(), rs.getTimestamp("CSGDAT")));
            }
        }
        consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));

        consignmentDocumentVO.setOriginOfficeOfExchange(rs.getString("ARPCODORG"));
        consignmentDocumentVO.setDestinationOfficeOfExchange(rs.getString("ARPCODDST"));
        consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
        consignmentDocumentVO.setRemarks(rs.getString("RMK"));
        consignmentDocumentVO.setFlightDetails(rs.getString("FLTDTL"));



    }
    private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO,
                                    ResultSet rs) throws SQLException {
        log.debug("ConsignmentDetailsReportMapper", "collectMailDetails");
        if(mailInConsignmentVO.getTotalLetterBags()==0)
            mailInConsignmentVO.setTotalLetterBags(rs.getInt("LC_CNT"));
        if(mailInConsignmentVO.getTotalLetterWeight()== null ||
                mailInConsignmentVO.getTotalLetterWeight().getDisplayValue()== new BigDecimal(0)) {
            mailInConsignmentVO.setTotalLetterWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("LC_WGT")), BigDecimal.valueOf(rs.getDouble("LC_WGT")),
                    "K"));

        }
        if(mailInConsignmentVO.getTotalParcelBags()==0)
            mailInConsignmentVO.setTotalParcelBags(rs.getInt("CP_CNT"));
        if(mailInConsignmentVO.getTotalParcelWeight()==null ||
                mailInConsignmentVO.getTotalParcelWeight().getDisplayValue()== new BigDecimal(0))
            mailInConsignmentVO.setTotalParcelWeight(
                    quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CP_WGT")), BigDecimal.valueOf(rs.getDouble("CP_WGT")),
                            "K"));
        log.debug("ConsignmentDetailsReportMapper", "collectMailDetails");
    }

}
