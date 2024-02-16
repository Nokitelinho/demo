package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.OnwardRoutingVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ContainerMapperForFlight implements MultiMapper<ContainerVO> {
    @Override
    public List<ContainerVO> map(ResultSet rs) throws SQLException {
        var localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
        var quantities = ContextUtil.getInstance().getBean(Quantities.class);
        List<ContainerVO> containerVosList = null;
        ContainerVO containerVo = null;
        String parentId = "";
        String prevParentId = "";
        String childId = "";
        String prevChildId = "";
        StringBuilder onwardFlightBuilder = null;
        String pou = null;
        String flightNumber = null;
        String carrierCode = null;
        Collection<com.ibsplc.neoicargo.mail.vo.OnwardRoutingVO> onwardRoutingVos = null;

        while (rs.next()) {
            /*
             * generate the ParentID corresponding to this resultset
             */
            parentId = new StringBuilder(rs.getString("CMPCOD")).append(
                            rs.getString("CONNUM")).append(rs.getString("FLTCARIDR"))
                    .append(rs.getString("FLTNUM")).append(
                            rs.getString("FLTSEQNUM")).append(
                            rs.getString("LEGSERNUM")).append(
                            rs.getString("ASGPRT")).toString();
            String assignedPort = rs.getString("ASGPRT");

            if (!parentId.equals(prevParentId) && containerVo != null) {
                if (onwardFlightBuilder != null) {
                    containerVo.setOnwardFlights(onwardFlightBuilder
                            .deleteCharAt(onwardFlightBuilder.length() - 1)
                            .toString());
                }

                if (CollectionUtils.isNotEmpty(onwardRoutingVos)) {
                    containerVo.setOnwardRoutings(onwardRoutingVos);
                }

                if (CollectionUtils.isEmpty(containerVosList)) {
                    containerVosList = new ArrayList<ContainerVO>();
                }
                containerVosList.add(containerVo);
                onwardFlightBuilder = null;
                onwardRoutingVos = null;
            }

            /*
             *
             * Enters inside whenever a new Parent Comes Create a new
             * ContainerVO
             *
             */
            if (!parentId.equals(prevParentId)) {
                containerVo = new ContainerVO();
                containerVo.setCompanyCode(rs.getString("CMPCOD"));
                containerVo.setContainerNumber(rs.getString("CONNUM"));
                containerVo.setCarrierCode(rs.getString("FLTCARCOD"));
                containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
                containerVo.setFlightNumber(rs.getString("FLTNUM"));
                containerVo.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
                containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
                containerVo.setAssignedPort(rs.getString("ASGPRT"));
                containerVo.setFinalDestination(rs.getString("DSTCOD"));
                if (rs.getTimestamp("ASGDAT") != null) {
                    containerVo.setAssignedDate(localDateUtil.getLocalDate(assignedPort, rs.getTimestamp("ASGDAT")));
                }
                containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
                containerVo.setAssignedUser(rs.getString("USRCOD"));
                containerVo.setRemarks(rs.getString("RMK"));
                containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
                containerVo.setPou(rs.getString("POU"));
                containerVo.setType(rs.getString("CONTYP"));
                containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
                containerVo.setBags(rs.getInt("BAGCNT"));
                containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
                containerVo.setWeight( quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
                containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
                containerVo.setShipperBuiltCode(rs.getString("SBCODE"));
                containerVo.setWarehouseCode(rs.getString("WHSCOD"));
                containerVo.setTransitFlag(rs.getString("TRNFLG"));

                if (rs.getDate("FLTDAT") != null) {
                    containerVo.setFlightDate(localDateUtil.getLocalDate(assignedPort, rs.getDate("FLTDAT")));
                }
                containerVo.setOffloadFlag(rs.getString("OFLFLG"));
                containerVo.setArrivedStatus(rs.getString("ARRSTA"));
                var uldlastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
                if(uldlastUpdateTime != null) {
                    containerVo.setULDLastUpdateTime(localDateUtil.getLocalDate(null, uldlastUpdateTime));
                }
                var lastUpdateTime = rs.getTimestamp("LSTUPDTIM");
                if(lastUpdateTime != null) {
                    containerVo.setLastUpdateTime(localDateUtil.getLocalDate(null, lastUpdateTime));
                }

                containerVo.setLastUpdateUser(rs.getString("LSTUPDUSR"));
                containerVo.setULDLastUpdateUser(rs.getString("ULDLSTUPDUSR"));
                prevParentId = parentId;
            }

            /**
             *
             * For each RoutingSerialNum create a String like EK
             * 1303-30/6/2006-DXB,EK 1305-30/6/2006-TRV (FLTNUM-FLTDAT-POU)
             *
             */
            if (rs.getInt("RTGSERNUM") > 0) {
                childId = new StringBuffer(parentId).append(rs.getString("RTGSERNUM")).toString();
                if (!childId.equals(prevChildId)) {
                    flightNumber = rs.getString("ONWFLTNUM");
                    pou = rs.getString("RTGPOU");
                    carrierCode = rs.getString("ONWFLTCARCOD");

                    if (onwardRoutingVos == null) {
                        onwardRoutingVos = new ArrayList<OnwardRoutingVO>();
                    }

                    OnwardRoutingVO onwardRoutingVo = new OnwardRoutingVO();
                    onwardRoutingVo.setCompanyCode(rs.getString("CMPCOD"));
                    onwardRoutingVo.setOnwardCarrierId(rs
                            .getInt("ONWFLTCARIDR"));
                    onwardRoutingVo.setContainerNumber(rs.getString("CONNUM"));

                    onwardRoutingVo.setCarrierId(rs.getInt("RTGFLTCARIDR"));
                    onwardRoutingVo.setFlightNumber(rs.getString("FLTNUM"));
                    onwardRoutingVo.setFlightSequenceNumber(rs
                            .getLong("FLTSEQNUM"));
                    onwardRoutingVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));

                    onwardRoutingVo.setRoutingSerialNumber(rs
                            .getInt("RTGSERNUM"));

                    if (rs.getString("ONWFLTDAT") != null) {
                        onwardRoutingVo.setOnwardFlightDate(localDateUtil.getLocalDate(assignedPort, rs.getDate("ONWFLTDAT")));
                    }

                    onwardRoutingVo.setOnwardFlightNumber(flightNumber);
                    onwardRoutingVo.setPou(pou);
                    onwardRoutingVo.setOnwardCarrierCode(carrierCode);
                    onwardRoutingVo.setAssignmenrPort(rs.getString("ASGPRT"));

                    onwardRoutingVos.add(onwardRoutingVo);

                    /*
                     * iF FlightNumber,FlightDate ,Pou exists For each
                     * RoutingSerialNum create a String like EK
                     * 1303-30/6/2006-DXB
                     */
                    if (rs.getString("ONWFLTDAT") != null
                            && flightNumber != null
                            && flightNumber.trim().length() > 0
                            && carrierCode != null
                            && carrierCode.trim().length() > 0 && pou != null
                            && pou.trim().length() > 0) {

                        if (onwardFlightBuilder == null) {
                            onwardFlightBuilder = new StringBuilder();
                        }

                        onwardFlightBuilder.append(carrierCode).append("-")
                                .append(flightNumber).append("-").append(
                                        rs.getString("ONWFLTDAT")).append("-")
                                .append(pou).append(",");
                    }
                    childId = prevChildId;

                }
            }
        }

        /*
         *
         * This block is used to insert the Last Record in the ResultSet say the
         * Last ContainerVO in to the List If there is Only one parent it will
         * always enter this block
         *
         */
        if (Objects.nonNull(containerVo)) {
            if (onwardFlightBuilder != null) {
                containerVo.setOnwardFlights(onwardFlightBuilder.deleteCharAt(
                        onwardFlightBuilder.length() - 1).toString());
            }
            if (CollectionUtils.isNotEmpty(onwardRoutingVos)) {
                containerVo.setOnwardRoutings(onwardRoutingVos);
            }

            if (CollectionUtils.isEmpty(containerVosList)) {
                containerVosList = new ArrayList<ContainerVO>();
            }

            containerVosList.add(containerVo);

        }

        return containerVosList;
    }
}
