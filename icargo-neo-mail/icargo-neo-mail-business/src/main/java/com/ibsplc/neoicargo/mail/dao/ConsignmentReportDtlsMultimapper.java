package com.ibsplc.neoicargo.mail.dao;

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
public class ConsignmentReportDtlsMultimapper implements
        MultiMapper<ConsignmentDocumentVO> {
    LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
    Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
    /**
     *
     * @param rs
     * @return List<ConsignmentDocumentVO>
     * @throws SQLException
     */
    public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
        log.debug("ConsignmentDetailsMultimapper", "map");
        List<ConsignmentDocumentVO> consignmentDocumentVOs = null;
        String currentKey = null;
        String previousKey = null;
        StringBuilder stringBuilder = null;
        Collection<RoutingInConsignmentVO> routingInConsignmentVOs = null;
        Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
        ConsignmentDocumentVO consignmentDocumentVO = null;
        MailInConsignmentVO mailInConsignmentVO = null;
        RoutingInConsignmentVO routingInConsignmentVO = null;
        String mailCurrentKey = null;
        String mailPreviousKey = null;
        StringBuilder mailKey = null;
        Collection<Integer> routingSerialNumbers = null;
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
            /* Collecting mail details */
            mailKey = new StringBuilder();
            mailKey.append(rs.getString("DSN")).append(
                            rs.getString("ORGEXGOFC"))
                    .append(rs.getString("DSTEXGOFC")).append(
                            rs.getString("MALSUBCLS")).append(
                            rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
                    .append(rs.getLong("MALSEQNUM"));
            mailCurrentKey = mailKey.toString();
            if (!mailCurrentKey.equals(mailPreviousKey)) {
                mailInConsignmentVO = new MailInConsignmentVO();
                mailInConsignmentVO.setCompanyCode(consignmentDocumentVO
                        .getCompanyCode());
                mailInConsignmentVO.setConsignmentNumber(consignmentDocumentVO
                        .getConsignmentNumber());
                mailInConsignmentVO
                        .setPaCode(consignmentDocumentVO.getPaCode());
                mailInConsignmentVO
                        .setConsignmentSequenceNumber(consignmentDocumentVO
                                .getConsignmentSequenceNumber());
                collectMailDetails(mailInConsignmentVO, rs);
                mailInConsignmentVOs.add(mailInConsignmentVO);
                mailPreviousKey = mailCurrentKey;
            }
            /* Collecting Routing Details */
            if (rs.getInt("RTGSERNUM") > 0) {
                routingInConsignmentVO = new RoutingInConsignmentVO();
                routingInConsignmentVO.setCompanyCode(consignmentDocumentVO
                        .getCompanyCode());
                routingInConsignmentVO
                        .setConsignmentNumber(consignmentDocumentVO
                                .getConsignmentNumber());
                routingInConsignmentVO.setPaCode(consignmentDocumentVO
                        .getPaCode());
                routingInConsignmentVO
                        .setConsignmentSequenceNumber(consignmentDocumentVO
                                .getConsignmentSequenceNumber());
                collectRoutingDetails(routingInConsignmentVO, rs);
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
        log.debug("ConsignmentDetailsMultimapper", "map");
        return consignmentDocumentVOs;
    }

    /**
     * @param consignmentDocumentVO
     * @param rs
     * @throws SQLException
     */
    private void collectConsignmentDetails(
            ConsignmentDocumentVO consignmentDocumentVO, ResultSet rs)
            throws SQLException {
        log.debug("ConsignmentDetailsMultimapper",
                "collectConsignmentDetails");
        consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
        if (rs.getDate("CSGDAT") != null) {
            consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(rs
                            .getString("ARPCOD"), rs.getTimestamp("CSGDAT")));
        }
        consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
        consignmentDocumentVO.setConsignmentSequenceNumber(rs
                .getInt("CSGSEQNUM"));
        consignmentDocumentVO.setOperation(rs.getString("OPRTYP"));
        consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
        consignmentDocumentVO.setRemarks(rs.getString("RMK"));
        consignmentDocumentVO.setStatedBags(rs.getInt("STDBAG"));
        consignmentDocumentVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT"))));
        consignmentDocumentVO.setType(rs.getString("CSGTYP"));
        consignmentDocumentVO.setAirportCode(rs.getString("ARPCOD"));
        consignmentDocumentVO.setSubType(rs.getString("SUBTYP"));
        if(rs.getString("OPRORG") != null){
            consignmentDocumentVO.setOperatorOrigin(rs.getString("OPRORG"));
        }
        else{
            consignmentDocumentVO.setOperatorOrigin(rs.getString("POACOD"));
        }
        consignmentDocumentVO.setOperatorDestination(rs.getString("OPRDST"));
        consignmentDocumentVO.setOoeDescription(rs.getString("ORGEXGOFCDES"));
        consignmentDocumentVO.setDoeDescription(rs.getString("DSTEXGOFCDES"));
        consignmentDocumentVO.setTransportationMeans(rs.getString("TRPMNS"));
        consignmentDocumentVO.setConsignmentPriority(rs.getString("CSGPRI"));
        consignmentDocumentVO.setFlightDetails(rs.getString("FLTDTL"));
        consignmentDocumentVO.setFlightRoute(rs.getString("FLTRUT"));
        if (rs.getDate("FSTFLTDEPDAT") != null) {
            consignmentDocumentVO.setFirstFlightDepartureDate(
                    localDateUtil.getLocalDate(rs
                            .getString("ARPCOD"), rs.getTimestamp("FSTFLTDEPDAT")));
        }
        consignmentDocumentVO.setAirlineCode(rs.getString("ARLCOD"));

        if (rs.getTimestamp("LSTUPDTIM") != null) {
            consignmentDocumentVO.setLastUpdateTime(
                    localDateUtil.getLocalDate(null, rs.getTimestamp("LSTUPDTIM")));
        }
        consignmentDocumentVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
        log.debug("ConsignmentDetailsMultimapper",
                "collectConsignmentDetails");
    }

    /**
     * @param mailInConsignmentVO
     * @param rs
     * @throws SQLException
     */
    private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO,
                                    ResultSet rs) throws SQLException {
        log.debug("ConsignmentDetailsMultimapper", "collectMailDetails");
        mailInConsignmentVO.setDsn(rs.getString("DSN"));
        mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
        mailInConsignmentVO.setDestinationExchangeOffice(rs
                .getString("DSTEXGOFC"));
        mailInConsignmentVO.setMailClass(rs.getString("MALCLS"));
        mailInConsignmentVO.setYear(rs.getInt("YER"));
        mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
        mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
        mailInConsignmentVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
        mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
        mailInConsignmentVO.setHighestNumberedReceptacle(rs.getString("HSN"));
        mailInConsignmentVO.setReceptacleSerialNumber(rs.getString("RSN"));
        mailInConsignmentVO.setRegisteredOrInsuredIndicator(rs
                .getString("REGIND"));
        mailInConsignmentVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")), BigDecimal.valueOf(rs.getDouble("WGT")),
                "K"));
        mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
        mailInConsignmentVO.setDeclaredValue(rs.getDouble("DCLVAL"));
        mailInConsignmentVO.setCurrencyCode(rs.getString("CURCOD"));
        mailInConsignmentVO.setStatedBags(rs.getInt("BAGCNT"));
        log.debug("ConsignmentDetailsMultimapper", "collectMailDetails");
    }

    /**
     * @param routingInConsignmentVO
     * @param rs
     * @throws SQLException
     */
    private void collectRoutingDetails(
            RoutingInConsignmentVO routingInConsignmentVO, ResultSet rs)
            throws SQLException {
        log.debug("ConsignmentDetailsMultimapper", "collectRoutingDetails");
        routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
        routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
        routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
        if (rs.getDate("FLTDAT") != null) {
            routingInConsignmentVO.setOnwardFlightDate(
                    localDateUtil.getLocalDate(rs
                            .getString("POL"), rs.getTimestamp("LSTUPDTIM")));
        }
        routingInConsignmentVO.setOnwardCarrierId(rs.getInt("FLTCARIDR"));
        routingInConsignmentVO.setPou(rs.getString("POU"));
        routingInConsignmentVO.setPol(rs.getString("POL"));
        log.debug("ConsignmentDetailsMultimapper", "collectRoutingDetails");
    }
}
