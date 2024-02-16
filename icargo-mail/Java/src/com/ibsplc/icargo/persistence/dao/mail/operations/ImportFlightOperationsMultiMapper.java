package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.*;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by A-9529 on 22-08-2020.
 */
public class ImportFlightOperationsMultiMapper implements MultiMapper<ImportFlightOperationsVO> {
	
	
	private static final String CMPCOD = "CMPCOD";
	private static final String LEGDST = "LEGDST";
	private static final String FLTCARIDR = "FLTCARIDR";
	private static final String FLTNUM = "FLTNUM";
	private static final String FLTSEQNUM = "FLTSEQNUM";
	private static final String FLTDAT = "FLTDAT";
	private static final String  ULDNUM = "ULDNUM";
	private static final String  MFTPCS = "MFTPCS";
	private static final String  MFTWGT = "MFTWGT";
	private static final String RCVWGT = "RCVWGT";
	private static final String RCVPCS = "RCVPCS";
	private static final String SCCCOD = "SCCCOD";
	private static final String ADDSCC = "ADDSCC";
	private static final String MSTDOCNUM = "MSTDOCNUM";
	private static final String SEQNUM = "SEQNUM";
   




    private ImportFlightOperationsVO importFlightOperationsVO = null;
    private ImportULDOperationsVO importULDOperationsVO = null;
    private ImportShipmentOperationsVO importShipmentOperationsVO = null;
    private String flightPK = null;
    private String uldPK = null;
    private String shipmentPK = null;
    private HashMap<String, ImportFlightOperationsVO> importFlightOperationsMap = new HashMap<>();
    private HashMap<String, ImportULDOperationsVO> importULDOperationsMap = new HashMap<>();
    private HashMap<String, ImportShipmentOperationsVO> importShipmentOperationsMap = new HashMap<>();





    public List<ImportFlightOperationsVO> map(ResultSet rs) throws SQLException {


        while(true) {
            while(true) {
                do {
                    if(!rs.next()) {
                        return new ArrayList<>(importFlightOperationsMap.values());
                    }

                    flightPK = rs.getString(CMPCOD) + rs.getString(LEGDST) + rs.getString(FLTCARIDR) + rs.getString(FLTNUM) + rs.getString(FLTSEQNUM);
                    flightMapping(rs);
                    
                    uldPK = rs.getString(CMPCOD) + rs.getString(LEGDST) + rs.getString(FLTCARIDR) + rs.getString(FLTNUM) + rs.getString(FLTSEQNUM) + rs.getString(ULDNUM);
                    uldMapping(rs);
                } while(rs.getString(MSTDOCNUM) == null);

                shipmentPK = rs.getString(CMPCOD) + rs.getString(LEGDST) + rs.getString(FLTCARIDR) + rs.getString(FLTNUM) + rs.getString(FLTSEQNUM) + rs.getString(ULDNUM) + rs.getString(MSTDOCNUM) + rs.getString("DOCOWRIDR") + rs.getString(SEQNUM) + rs.getString("DUPNUM");
                shipmentMapping(rs);
            }
        }
    }

    private void shipmentPkMapping(ResultSet rs) throws SQLException {
    	int var14;
        if(rs.getInt(SEQNUM) == 1) {
            importULDOperationsVO.setShipmentCount(1);
        }
        String sccCode= null;
        String additionalSccs = null;
        importShipmentOperationsVO = new ImportShipmentOperationsVO();
        importShipmentOperationsVO.setMasterDocumentNumber(rs.getString(MSTDOCNUM));
        importShipmentOperationsVO.setDuplicateNumber(rs.getInt("DUPNUM"));
        importShipmentOperationsVO.setSequenceNumber(rs.getInt(SEQNUM));
        importShipmentOperationsVO.setShipmentPrefix(rs.getString("SHPPFX"));
        importShipmentOperationsVO.setOwnerId(rs.getInt("DOCOWRIDR"));
        importShipmentOperationsVO.setCtoShipmentManifestVO(new CTOShipmentManifestVO());
        importShipmentOperationsVO.getCtoShipmentManifestVO().setProductName(rs.getString("PRDNAM"));
        sccCode = rs.getString(SCCCOD);
        if(sccCode != null && sccCode.trim().length() > 0) {
            importShipmentOperationsVO.getCtoShipmentManifestVO().setScc(sccCode);
            additionalSccs = rs.getString(ADDSCC);
            if(additionalSccs != null && additionalSccs.trim().length() > 0) {
                String[] var27;
                var27 = additionalSccs.split(",");
                int var26 = var27.length;

                for(var14 = 0; var14 < var26; ++var14) {
                    String scc = var27[var14];
                    if(!importShipmentOperationsVO.getCtoShipmentManifestVO().getScc().contains(scc)) {
                        importShipmentOperationsVO.getCtoShipmentManifestVO().setScc(importShipmentOperationsVO.getCtoShipmentManifestVO().getScc() + "," + scc);
                    }
                }
            }
        } else {
            importShipmentOperationsVO.getCtoShipmentManifestVO().setScc(rs.getString(ADDSCC));
        }

        importShipmentOperationsVO.setBlockCount(rs.getInt("BLKCNT"));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setManifestedPieces(rs.getInt(MFTPCS));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setManifestedWeight(new Measure("WGT", rs.getDouble(MFTWGT)));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setOrigin(rs.getString("ORGCOD"));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setDestination(rs.getString("DSTCOD"));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setConsigneeName(rs.getString("CNSNAM"));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setShipmentDescription(rs.getString("SHPDES"));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setRemarks(rs.getString("RMK"));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setReceivedPieces(rs.getInt(RCVPCS));
        importShipmentOperationsVO.getCtoShipmentManifestVO().setReceivedWeight(new Measure("WGT", rs.getDouble(RCVWGT)));
        importShipmentOperationsVO.setShipmentBreakdownVO(new ShipmentBreakdownVO());
        
        importShipmentOperationsMap.put(shipmentPK, importShipmentOperationsVO);
        if(importULDOperationsVO.getImportShipmentOperationsVOs() == null) {
            importULDOperationsVO.setImportShipmentOperationsVOs(new ArrayList<>());
        }
        setShipmentTimeValues(rs);
        importULDOperationsVO.getImportShipmentOperationsVOs().add(importShipmentOperationsVO);
    
    }

    private CTOImportManifestVO flightPKMapping(ResultSet rs) throws SQLException {
        importFlightOperationsVO = new ImportFlightOperationsVO();
        importFlightOperationsVO.setCtoImportManifestVO(new CTOImportManifestVO());
        CTOImportManifestVO ctoImportManifestVO = new CTOImportManifestVO();
        importFlightOperationsVO.setCompanyCode(rs.getString(CMPCOD));
        importFlightOperationsVO.setCarrierId(rs.getInt(FLTCARIDR));
        if(rs.getInt("APHCODUSE") == 2) {
            importFlightOperationsVO.setCarrierCode(rs.getString("TWOAPHCOD"));
        } else {
            importFlightOperationsVO.setCarrierCode(rs.getString("THRAPHCOD"));
        }

        importFlightOperationsVO.setFlightNumber(rs.getString(FLTNUM));
        importFlightOperationsVO.setFlightSequenceNumber((long)rs.getInt(FLTSEQNUM));
        if("T".equals(rs.getString("FLTTYP"))) {
            importFlightOperationsVO.setFlightType("T");
        } else if("Y".equals(rs.getString("DOMFLTFLG"))) {
            importFlightOperationsVO.setFlightType("D");
        } else {
            importFlightOperationsVO.setFlightType("I");
        }

        importFlightOperationsVO.setFlightCategory(rs.getString("FLTTYP"));
        importFlightOperationsVO.setAircraftType(rs.getString("ACRTYP"));
        importFlightOperationsVO.setTailNumber(rs.getString("TALNUM"));
        ctoImportManifestVO.setAirportCode(rs.getString(LEGDST));
        ctoImportManifestVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
        ctoImportManifestVO.setDocumentLocation(rs.getString("DOCLOC"));
        ctoImportManifestVO.setFlightRoute(rs.getString("FLTROU"));
        if(rs.getTimestamp(FLTDAT) != null) {
            if(rs.getString(LEGDST) != null) {
                ctoImportManifestVO.setFlightDate(new LocalDate(rs.getString(LEGDST), Location.ARP, rs.getTimestamp(FLTDAT)));
            } else {
                ctoImportManifestVO.setFlightDate(new LocalDate("***", Location.NONE, rs.getTimestamp(FLTDAT)));
            }
        }

        setFlightDetails(rs);
        importFlightOperationsVO.setCtoImportManifestVO(ctoImportManifestVO);
        importFlightOperationsMap.put(flightPK, importFlightOperationsVO);
        return ctoImportManifestVO;
    }


    private void uldDetailsMapping(ResultSet rs) throws SQLException {
        importULDOperationsVO = importULDOperationsMap.get(uldPK);
        importULDOperationsVO.getCtoULDManifestVO().setPol(rs.getString("POL"));
        importULDOperationsVO.getCtoULDManifestVO().setPou(rs.getString("POU"));
        importULDOperationsVO.getCtoULDManifestVO().setManifestedPiece(importULDOperationsVO.getCtoULDManifestVO().getManifestedPiece() + rs.getInt(MFTPCS));
        double manifestSum;
        manifestSum = importULDOperationsVO.getCtoULDManifestVO().getManifestedWeight().getSystemValue() + rs.getDouble(MFTWGT);
        double recievedSum = importULDOperationsVO.getCtoULDManifestVO().getReceivedWeight().getSystemValue() + rs.getDouble(RCVWGT);
        importULDOperationsVO.getCtoULDManifestVO().setManifestedWeight(new Measure("WGT", manifestSum));
        importULDOperationsVO.getCtoULDManifestVO().setReceivedWeight(new Measure("WGT", recievedSum));
        importULDOperationsVO.getCtoULDManifestVO().setReceivedPiece(importULDOperationsVO.getCtoULDManifestVO().getReceivedPiece() + rs.getInt(RCVPCS));
        String sccCode = rs.getString(SCCCOD);
        String additionalSccs = rs.getString(ADDSCC);
        
        
        if(importULDOperationsVO.getCtoULDManifestVO().getSccs() != null && importULDOperationsVO.getCtoULDManifestVO().getSccs().trim().length() > 0) {
        	
        	setSCCForManifestedULD(sccCode,additionalSccs);
        	
        	
        	
        } else {
        	
        	setSCCForULD(sccCode,additionalSccs);
        	
        	
        }
    }

    private void setFlightDetails(ResultSet rs) throws SQLException {

        if(rs.getTimestamp("ATA") != null) {
            if(rs.getString(LEGDST) != null) {
                importFlightOperationsVO.setAta(new LocalDate(rs.getString(LEGDST), Location.ARP, rs.getTimestamp("ATA")));
            } else {
                importFlightOperationsVO.setAta(new LocalDate("***", Location.NONE, rs.getTimestamp("ATA")));
            }
        }

        if(rs.getTimestamp("ETA") != null) {
            if(rs.getString(LEGDST) != null) {
                importFlightOperationsVO.setEta(new LocalDate(rs.getString(LEGDST), Location.ARP, rs.getTimestamp("ETA")));
            } else {
                importFlightOperationsVO.setEta(new LocalDate("***", Location.NONE, rs.getTimestamp("ETA")));
            }
        }

        if(rs.getTimestamp("STA") != null) {
            if(rs.getString(LEGDST) != null) {
                importFlightOperationsVO.setSta(new LocalDate(rs.getString(LEGDST), Location.ARP, rs.getTimestamp("STA")));
            } else {
                importFlightOperationsVO.setSta(new LocalDate("***", Location.NONE, rs.getTimestamp("STA")));
            }
        }

    }
    
    private void setSCCForManifestedULD(String sccCode, String additionalSccs) {

    	int var18;
        int var19;
        String[] var20;
        String scc;
        if(sccCode != null && sccCode.trim().length() > 0 && !importULDOperationsVO.getCtoULDManifestVO().getSccs().contains(sccCode)) {
            importULDOperationsVO.getCtoULDManifestVO().setSccs(importULDOperationsVO.getCtoULDManifestVO().getSccs() + "," + sccCode);
        }

        if(additionalSccs != null) {
            var20 = additionalSccs.split(",");
            var19 = var20.length;

            for(var18 = 0; var18 < var19; ++var18) {
                scc = var20[var18];
                if(!importULDOperationsVO.getCtoULDManifestVO().getSccs().contains(scc)) {
                    importULDOperationsVO.getCtoULDManifestVO().setSccs(importULDOperationsVO.getCtoULDManifestVO().getSccs() + "," + scc);
                }
            }
        }
    
    }
    
    private void setSCCForULD(String sccCode, String additionalSccs) {

    	int var18;
        int var19;
        String[] var20;
        String scc;
        if(sccCode != null && sccCode.trim().length() > 0) {
            importULDOperationsVO.getCtoULDManifestVO().setSccs(sccCode);
        }

        
        if(additionalSccs != null && additionalSccs.trim().length() > 0) {
        	var20 = additionalSccs.split(",");
            var19 = var20.length;

            for(var18 = 0; var18 < var19; ++var18) {
                scc = var20[var18];
                if(importULDOperationsVO.getCtoULDManifestVO().getSccs() == null || !importULDOperationsVO.getCtoULDManifestVO().getSccs().contains(scc)) {
                    importULDOperationsVO.getCtoULDManifestVO().setSccs(importULDOperationsVO.getCtoULDManifestVO().getSccs() + "," + scc);
                }
            }
        }
    
    
    }
    
    private void setShipmentTimeValues(ResultSet rs) throws SQLException {
    	
    	if(rs.getTimestamp("CIQTIM") != null) {
            importShipmentOperationsVO.getCtoShipmentManifestVO().setCiqTime(new LocalDate(rs.getString(LEGDST), Location.ARP, rs.getTimestamp("CIQTIM")));
        }

        if(rs.getTimestamp("EPSTIM") != null) {
            importShipmentOperationsVO.getCtoShipmentManifestVO().setEpsTime(new LocalDate(rs.getString(LEGDST), Location.ARP, rs.getTimestamp("EPSTIM")));
        }

        if(rs.getTimestamp("BDNCMPTIM") != null) {
            importShipmentOperationsVO.getCtoShipmentManifestVO().setBdncmpTime(new LocalDate(rs.getString(LEGDST), Location.ARP, rs.getTimestamp("BDNCMPTIM")));
        }

    }
    
    private void uldPkMappingForULD(ResultSet rs) throws SQLException {

        importULDOperationsVO = new ImportULDOperationsVO();
        importULDOperationsVO.setCompanyCode(rs.getString(CMPCOD));
        importULDOperationsVO.setUldNumber(rs.getString(ULDNUM));
        importULDOperationsVO.setCtoULDManifestVO(new CTOULDManifestVO());
        importULDOperationsVO.getCtoULDManifestVO().setSccs(rs.getString(SCCCOD));
        String sccCode = "";
        String additionalSccs = "";
        int var14 = 0;
        sccCode = rs.getString(ADDSCC);
        if(importULDOperationsVO.getCtoULDManifestVO().getSccs() != null && importULDOperationsVO.getCtoULDManifestVO().getSccs().trim().length() > 0) {
            if(rs.getString(ADDSCC) != null) {
                String[] var15 = sccCode.split(",");
                var14 = var15.length;

                for(int var13 = 0; var13 < var14; ++var13) {
                    additionalSccs = var15[var13];
                    if(!importULDOperationsVO.getCtoULDManifestVO().getSccs().contains(additionalSccs)) {
                        importULDOperationsVO.getCtoULDManifestVO().setSccs(importULDOperationsVO.getCtoULDManifestVO().getSccs() + "," + additionalSccs);
                    }
                }
            }
        } else {
            importULDOperationsVO.getCtoULDManifestVO().setSccs(rs.getString(ADDSCC));
        }

        importULDOperationsVO.getCtoULDManifestVO().setUldNumber(rs.getString(ULDNUM));
        importULDOperationsVO.getCtoULDManifestVO().setGrossWeight(new Measure("WGT", rs.getDouble("GRSWGT")));
        importULDOperationsVO.getCtoULDManifestVO().setTareWeight(new Measure("WGT", rs.getDouble("TARWGT")));
        importULDOperationsVO.getCtoULDManifestVO().setShpmntRemark(rs.getString("SHPRMRK"));
        importULDOperationsVO.getCtoULDManifestVO().setULDRemarks(rs.getString("ULDRMK"));
        importULDOperationsVO.getCtoULDManifestVO().setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
        importULDOperationsVO.getCtoULDManifestVO().setBreakdownInstruction(rs.getString("BDNITN"));
        importULDOperationsVO.getCtoULDManifestVO().setBreakdownCompletedFlag(rs.getString("BDNCPLFLG"));
        importULDOperationsVO.getCtoULDManifestVO().setNetWeight(new Measure("WGT", rs.getDouble("NETWGT")));
        importULDOperationsVO.getCtoULDManifestVO().setManifestedPiece(rs.getInt(MFTPCS));
        importULDOperationsVO.getCtoULDManifestVO().setManifestedWeight(new Measure("WGT", rs.getDouble(MFTWGT)));
        importULDOperationsVO.getCtoULDManifestVO().setPol(rs.getString("POL"));
        importULDOperationsVO.getCtoULDManifestVO().setPou(rs.getString("POU"));
        importULDOperationsVO.getCtoULDManifestVO().setReceivedPiece(rs.getInt(RCVPCS));
        importULDOperationsVO.getCtoULDManifestVO().setReceivedWeight(new Measure("WGT", rs.getDouble(RCVWGT)));
        importULDOperationsVO.getCtoULDManifestVO().setDeckPos(rs.getString("ULDPOS"));
        importULDOperationsVO.setBasePalletFlag("Y".equals(rs.getString("BSEFLG")));
        importULDOperationsVO.getCtoULDManifestVO().setShpmntType(rs.getString("SHPTYP"));
        importULDOperationsVO.getCtoULDManifestVO().setSighted(rs.getString("RCVHNDFLG"));
        importULDOperationsVO.setBreakdownVO(new BreakdownVO());
        importULDOperationsVO.getBreakdownVO().setWarehouseCode(rs.getString("ULDBDNWHSCOD"));
        importULDOperationsVO.setCtoRampHandlingVO(new CTORampHandlingVO());
        importULDOperationsVO.getCtoRampHandlingVO().setWarehouseCode(rs.getString("RMPWHSCOD"));
        importULDOperationsVO.getCtoRampHandlingVO().setUldNumber(rs.getString(ULDNUM));
        importULDOperationsVO.getCtoRampHandlingVO().setRampHandlingType(rs.getString("RMPHDLTYP"));
        importULDOperationsVO.getCtoRampHandlingVO().setReceivedHandOver("Y".equals(rs.getString("RCVHNDFLG")));
        importULDOperationsVO.getCtoRampHandlingVO().setForwardingLocation(rs.getString("FWDLOCCOD"));
        getUldTiming(rs);
        importULDOperationsMap.put(uldPK, importULDOperationsVO);
        if(importFlightOperationsVO.getImportULDOperationsVOs() == null) {
            importFlightOperationsVO.setImportULDOperationsVOs(new ArrayList<ImportULDOperationsVO>());
        }

        importFlightOperationsVO.getImportULDOperationsVOs().add(importULDOperationsVO);
    
    }
    
    private void flightMapping(ResultSet rs) throws SQLException {
    	if(importFlightOperationsMap.containsKey(flightPK)) {
            importFlightOperationsVO = importFlightOperationsMap.get(flightPK);
        } else {
            flightPKMapping(rs);
        }
    	
    }
    
    private void uldMapping(ResultSet rs) throws SQLException {
    	if(importULDOperationsMap.containsKey(uldPK)) {
            uldDetailsMapping(rs);
        } else {
        	
        	uldPkMappingForULD(rs);
   
        }
    }
    
    private void shipmentMapping(ResultSet rs) throws SQLException {
    	if(importShipmentOperationsMap.containsKey(shipmentPK)) {
            if(rs.getInt(SEQNUM) == 1) {
                importULDOperationsVO.setShipmentCount(importULDOperationsVO.getShipmentCount() + 1);
            }
        } else {
        	shipmentPkMapping(rs);
        }
    }
    private void getUldTiming(ResultSet rs) throws SQLException {
    	if(rs.getString("DMGDOCNUM") == null && rs.getString("DMGSTUCOD") == null) {
            importULDOperationsVO.setDamageFlag(false);
        } else {
            importULDOperationsVO.setDamageFlag(true);
        }
        if(rs.getTimestamp("PSHTIM") != null) {
            importULDOperationsVO.getCtoRampHandlingVO().setPushInOutTime(new LocalDate("***", Location.NONE, rs.getTimestamp("PSHTIM")));
        }

    }
}
