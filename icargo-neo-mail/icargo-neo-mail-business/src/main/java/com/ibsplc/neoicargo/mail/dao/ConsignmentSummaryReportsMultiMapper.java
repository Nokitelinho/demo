package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
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
public class ConsignmentSummaryReportsMultiMapper implements MultiMapper<ConsignmentDocumentVO> {
    Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
    LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);

    public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
        log.debug("ConsignmentDetailsMultimapper", "map");
        ArrayList<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
        Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
        ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
        Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
        boolean isPriorityMailExists = false;
        boolean isAirMailExists = false;
        boolean nonPriority = false;
        boolean isSal = false;
        boolean isSurface = false;
        boolean isParcelExists = false;
        boolean isEMSExists = false;
        boolean isTruck = false;
        boolean isRegularFlt = false;

        while (rs.next()) {
            MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
            RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();

            if(rs.getString("CMPCOD")!=null)
                consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
            if(rs.getString("ARPCOD")!=null)
                consignmentDocumentVO.setAirportCode(rs.getString("ARPCOD"));
            if (rs.getTimestamp("CSGDAT") != null) {
                consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(null,
                        rs.getTimestamp("CSGDAT")));
            }
            if(rs.getString("CSGTYP")!=null)
                consignmentDocumentVO.setReportType(rs.getString("CSGTYP"));
            if(rs.getString("SUBTYP")!=null)
                consignmentDocumentVO.setSubType(rs.getString("SUBTYP"));
            if(rs.getString("RMK")!=null)
                consignmentDocumentVO.setRemarks(rs.getString("RMK"));
            if(rs.getString("FSTFLTDEPDAT")!=null)
                consignmentDocumentVO.setDespatchDate(rs.getString("FSTFLTDEPDAT"));
            if(rs.getString("FSTFLTDEPDAT")!=null)
                consignmentDocumentVO.setFirstFlightDepartureDate(localDateUtil.getLocalDate(null, rs.getTimestamp("FSTFLTDEPDAT")));
            if(rs.getString("FLTRUT")!=null)
                consignmentDocumentVO.setFlightRoute(rs.getString("FLTRUT"));
            if(rs.getString("POACOD")!=null)
                consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
            if(rs.getString("ARLNAM")!=null)
                consignmentDocumentVO.setAirlineName(rs.getString("ARLNAM"));
            if(rs.getString("POANAM")!=null)
                consignmentDocumentVO.setPaName(rs.getString("POANAM"));
            if(rs.getString("OPRORG") != null)
                consignmentDocumentVO.setOperatorOrigin(rs.getString("OPRORG"));
            if(rs.getString("OPRDST") != null)
                consignmentDocumentVO.setOperatorDestination(rs.getString("OPRDST"));
            if(rs.getString("CSGDOCNUM") != null)
                consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
            if(rs.getString("ORGEXGOFCDES") != null)
                consignmentDocumentVO.setOoeDescription(rs.getString("ORGEXGOFCDES"));
            if(rs.getString("DSTEXGOFCDES") != null)
                consignmentDocumentVO.setDoeDescription(rs.getString("DSTEXGOFCDES"));
            if(rs.getString("PRYMAL") != null && "Y".equals(rs.getString("PRYMAL")))
                consignmentDocumentVO.setPriorityMalExists(true);
            if(rs.getString("AIRMAL") != null && "Y".equals(rs.getString("AIRMAL")))
                consignmentDocumentVO.setAirMalExists(true);
            RoutingInConsignmentVO routingInConsignmentVo = populateRoutingInformation(routingInConsignmentVO, rs);
            if(rs.getString("FLTTYP") != null && "T".equals(rs.getString("FLTTYP"))){
                isTruck = true;
            }else if(rs.getString("FLTTYP") != null && ("C".equals(rs.getString("FLTTYP"))
                    || "CO".equals(rs.getString("FLTTYP")))){
                isRegularFlt = true;
            }
            if(routingInConsignmentVo!=null){
                if(rs.getString("FSTFLTDEPDAT")==null&&consignmentDocumentVO.getFirstFlightDepartureDate()==null && routingInConsignmentVo.getRoutingSerialNumber() == 1
                        && rs.getString("FLTDAT") != null){
                    consignmentDocumentVO.setFirstFlightDepartureDate(localDateUtil.getLocalDate(null, rs.getTimestamp("FLTDAT")));
                }
                routingInConsignmentVOs.add(routingInConsignmentVo);
            }
            if(rs.getString("TRPMNS")!=null){
                consignmentDocumentVO.setTransportationMeans(rs.getString("TRPMNS"));
            }
            if(rs.getString("CSGPRI")!=null){
                consignmentDocumentVO.setConsignmentPriority(rs.getString("CSGPRI"));
            }

            MailInConsignmentVO mailInConsignmentVo = populateMailInformation(mailInConsignmentVO, rs);
            if(mailInConsignmentVo!=null){
                if(!isAirMailExists && MailConstantsVO.MAIL_CATEGORY_AIR.equals(mailInConsignmentVo.getMailCategoryCode())){
                    isAirMailExists = true;
                }
                else if(!isPriorityMailExists && MailConstantsVO.CN37_CATEGORY_D.equals(mailInConsignmentVo.getMailCategoryCode())){
                    isPriorityMailExists = true;
                }else{
                    nonPriority = true;
                }
                if(mailInConsignmentVo.getTotalEmsBags() > 0){
                    isEMSExists = true;
                }else if(mailInConsignmentVo.getTotalParcelBags() > 0){
                    isParcelExists = true;
                }
                mailInConsignmentVOs.add(mailInConsignmentVo);
            }
            consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
            consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
            consignmentDocumentVOs.add(consignmentDocumentVO);
        }

        if(consignmentDocumentVOs != null && !consignmentDocumentVOs.isEmpty()){
            ConsignmentDocumentVO csgVO = consignmentDocumentVOs.iterator().next();
            csgVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
            csgVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
            csgVO.setPriorityMalExists(isPriorityMailExists);
            csgVO.setAirMalExists(isAirMailExists);
            if(isPriorityMailExists || isAirMailExists){
                csgVO.setPriority(true);
            }
            csgVO.setNonPriority(nonPriority);
            if(isTruck && !isRegularFlt){
                csgVO.setBySurface(true);
            }else if(isTruck && isRegularFlt){
                csgVO.setBySal(true);
            }else{
                csgVO.setByAir(true);
            }
            csgVO.setEms(isEMSExists);
            csgVO.setParcels(isParcelExists);
            csgVO.setPriorityByAir(isPriorityMailExists);
            csgVO.setNonPrioritySurface(isAirMailExists);
        }

        return consignmentDocumentVOs;

    }

    /**
     * 	Method		:	ConsignmentSummaryReportsMultiMapper.populateMailInformation
     *	Added by 	:	A-9084 on 13-Nov-2020
     * 	Used for 	:
     *	Parameters	:	@param mailInConsignmentVO
     *	Parameters	:	@param rs
     *	Parameters	:	@return
     *	Return type	: 	MailInConsignmentVO
     * @throws SQLException
     */
    private MailInConsignmentVO populateMailInformation(MailInConsignmentVO mailInConsignmentVO, ResultSet rs) throws SQLException {
        if(rs.getString("DSN")!=null)
            mailInConsignmentVO.setDsn(rs.getString("DSN"));
        if(rs.getString("MALSUBCLS")!=null)
            mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
        if(rs.getString("ORGEXGOFC")!=null)
            mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
        if(rs.getString("DSTEXGOFC")!=null)
            mailInConsignmentVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
        Quantity wgt =quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")), BigDecimal.valueOf(rs.getDouble("WGT")),
                "K");
        if(rs.getString("WGT")!=null)
            mailInConsignmentVO.setStatedWeight(wgt);
        if(rs.getString("PCS")!=null)
            mailInConsignmentVO.setStatedBags(rs.getInt("PCS"));
        if(rs.getString("SUBCLSGRP")!=null)
            mailInConsignmentVO.setMailSubClassGroup(rs.getString("SUBCLSGRP"));
        if(rs.getString("MALCTGCOD")!=null)
            mailInConsignmentVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
        if(MRAConstantsVO.MAILSUBCLASSGROUP_LC.equals(mailInConsignmentVO.getMailSubClassGroup())){
            mailInConsignmentVO.setTotalLetterWeight(wgt);
            mailInConsignmentVO.setTotalLetterBags(rs.getInt("PCS"));
        }else if(MRAConstantsVO.MAILSUBCLASSGROUP_CP.equals(mailInConsignmentVO.getMailSubClassGroup())){
            mailInConsignmentVO.setTotalParcelWeight(wgt);
            mailInConsignmentVO.setTotalParcelBags(rs.getInt("PCS"));
        }else if(MRAConstantsVO.MAILSUBCLASSGROUP_EMS.equals(mailInConsignmentVO.getMailSubClassGroup())){
            mailInConsignmentVO.setTotalEmsWeight(wgt);
            mailInConsignmentVO.setTotalEmsBags(rs.getInt("PCS"));
        }else{
            mailInConsignmentVO.setTotalSVWeight(wgt);
            mailInConsignmentVO.setTotalSVbags(rs.getInt("PCS"));
        }
        if(rs.getString("ULDNUM")!=null && !"N".equals(rs.getString("ULDNUM")))
            mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
        if(rs.getString("CONSELNUM")!=null)
            mailInConsignmentVO.setSealNumber(rs.getString("CONSELNUM"));

        if(rs.getString("DSN")!=null && rs.getString("MALSUBCLS")!=null&&rs.getString("ORGEXGOFC")!=null&&
                rs.getString("DSTEXGOFC")!=null&& rs.getString("WGT")!=null &&rs.getString("PCS")!=null &&
                rs.getString("SUBCLSGRP")!=null){
            return mailInConsignmentVO;
        }else{
            return null;
        }
    }

    /**
     * 	Method		:	ConsignmentSummaryReportsMultiMapper.populateRoutingInformation
     *	Added by 	:	A-9084 on 13-Nov-2020
     * 	Used for 	:
     *	Parameters	:	@param routingInConsignmentVO
     *	Parameters	:	@return
     *	Return type	: 	Object
     * @throws SQLException
     */
    private RoutingInConsignmentVO populateRoutingInformation(RoutingInConsignmentVO routingInConsignmentVO, ResultSet rs) throws SQLException {
        if(rs.getString("FLTNUM")!=null)
            routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
        routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
        if(rs.getString("POL")!=null)
            routingInConsignmentVO.setPol(rs.getString("POL"));
        if(rs.getString("POU")!=null)
            routingInConsignmentVO.setPou(rs.getString("POU"));
        if(rs.getString("FLTCARCOD")!=null)
            routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
        if (rs.getTimestamp("FLTDAT") != null && rs.getString("FLTNUM")!=null) {
            routingInConsignmentVO.setOnwardFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("FLTDAT")));
        }
        routingInConsignmentVO.setPolAirportName(rs.getString("POLARPNAM"));
        routingInConsignmentVO.setPouAirportName(rs.getString("POUARPNAM"));
        if(rs.getString("FLTNUM")!=null && rs.getString("POL")!=null && rs.getString("POU")!=null
                && rs.getString("FLTCARCOD")!=null && rs.getTimestamp("FLTDAT") != null){
            return routingInConsignmentVO;
        }else{
            return null;
        }
    }
}
