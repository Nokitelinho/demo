package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.operations.flthandling.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.FreightDetailsVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExportFlightOperationsMultiMapper implements MultiMapper<ManifestVO> {
	public static final String CMPCOD = "CMPCOD";
	public static final String LEGDST = "LEGDST";
	public static final String FLTCARIDR = "FLTCARIDR";
	public static final String FLTNUM = "FLTNUM";
	public static final String FLTSEQNUM = "FLTSEQNUM";
	public static final String ULDNUM = "ULDNUM";
	public static final String PRDNAM = "PRDNAM";
	public static final String SCCCOD = "SCCCOD";
	public static final String ADDSCC = "ADDSCC";
	public static final String MFTPCS = "MFTPCS";
	public static final String MFTWGT = "MFTWGT";
	public static final String ORGCOD = "ORGCOD";
	public static final String DSTCOD = "DSTCOD";
	public static final String CNSNAM = "CNSNAM";
	public static final String SHPDES = "SHPDES";
	public static final String RMK = "RMK";
	public static final String GRSWGT = "GRSWGT";
	public static final String TARWGT = "TARWGT";
	public static final String SHPRMRK = "SHPRMRK";
	public static final String ULDRMK = "ULDRMK";
	public static final String SEGSERNUM = "SEGSERNUM";
	public static final String NETWGT = "NETWGT";
	public static final String POL = "POL";
	public static final String POU = "POU";
	public static final String ULDPOS = "ULDPOS";
	public static final String BSEFLG = "BSEFLG";
	public static final String FLTCARCOD = "FLTCARCOD";
	public static final String FLTTYP = "FLTTYP";
	public static final String DOMFLTFLG = "DOMFLTFLG";
	public static final String ACRTYP = "ACRTYP";
	public static final String TALNUM = "TALNUM";
	public static final String FLTATD = "ATD";
	public static final String LEGORG = "LEGORG";
	public static final String FLTETD = "ETD";
	public static final String FLTSTD = "STD";
	public static final String LEGSERNUM = "LEGSERNUM";
	public static final String DOCLOC = "DOCLOC";
	public static final String FLTROU = "FLTROU";
	public static final String FLTDAT = "FLTDAT";
	public static final String APHCODUSE = "APHCODUSE";
	public static final String TWOAPHCOD = "TWOAPHCOD";
	public static final String THRAPHCOD = "THRAPHCOD";
	public static final String STDWGT = "STDWGT";
	public static final String STDPCS = "STDPCS";
	public static final String BARROWFLG = "BARROWFLG";
	public static final String BDPPCS = "BDPPCS";
	public static final String BDPWGT = "BDPWGT";
	public static final String DEPGTE = "DEPGTE";
	public static final String FOWMILTIM = "FOWMILTIM";
	public static final String CURLOCCOD = "CURLOCCOD";
	public static final String FWDLOCCOD = "FWDLOCCOD";

	@Override
	public List<ManifestVO> map(ResultSet rs) throws SQLException {
		ManifestVO manifestVO = null;
		UldManifestVO uldManifestVO = null;
		String flightPK = null;
		String uldPK = null;
		HashMap<String, ManifestVO> manifestVOMap = new HashMap<>();
		HashMap<String, UldManifestVO> uldManifestVOMap = new HashMap<>();
		while (rs.next()) {
			flightPK = new StringBuilder().append(rs.getString(CMPCOD)).append(rs.getString(LEGDST))
					.append(rs.getString(FLTCARIDR)).append(rs.getString(FLTNUM)).append(rs.getString(FLTSEQNUM))
					.toString();
			if (manifestVOMap.containsKey(flightPK)) {
				manifestVO = manifestVOMap.get(flightPK);
			} else {
				manifestVO = populateExportFlightOperationsVO(rs);
				manifestVOMap.put(flightPK, manifestVO);
			}
			uldPK = new StringBuilder().append(rs.getString(CMPCOD)).append(rs.getString(LEGDST))
					.append(rs.getString(FLTCARIDR)).append(rs.getString(FLTNUM)).append(rs.getString(FLTSEQNUM))
					.append(rs.getString(ULDNUM)).toString();
			if (uldManifestVOMap.containsKey(uldPK)) {
				uldManifestVO = uldManifestVOMap.get(uldPK);
				updateManifestedUldDetails(uldManifestVO, rs);
			} else {
				uldManifestVO = new UldManifestVO();
				populateManifestedUldDetails(uldManifestVO, rs);
				populateManifestUldDetailsVO(uldManifestVO, manifestVO);
				uldManifestVOMap.put(uldPK, uldManifestVO);
			}
		}
		return new ArrayList<>(manifestVOMap.values());
	}

	private void populateManifestUldDetailsVO(UldManifestVO uldManifestVO, ManifestVO manifestVO) {
		if (manifestVO.getFreightDetailsVO() != null) {
			FreightDetailsVO freightDetailsVO = manifestVO.getFreightDetailsVO();
			if (freightDetailsVO.getManifestUldDetails() != null
					&& !freightDetailsVO.getManifestUldDetails().isEmpty()) {
				freightDetailsVO.getManifestUldDetails().add(uldManifestVO);
			} else {
				freightDetailsVO.setManifestUldDetails(new ArrayList<UldManifestVO>());
				freightDetailsVO.getManifestUldDetails().add(uldManifestVO);
			}
		} else {
			FreightDetailsVO freightDetailsVO = new FreightDetailsVO();
			freightDetailsVO.setManifestUldDetails(new ArrayList<UldManifestVO>());
			freightDetailsVO.getManifestUldDetails().add(uldManifestVO);
			manifestVO.setFreightDetailsVO(freightDetailsVO);
		}
	}

	private void populateManifestedUldDetails(UldManifestVO uldManifestVO, ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		uldManifestVO.setCompanyCode(rs.getString(CMPCOD));
		uldManifestVO.setUldNumber(rs.getString(ULDNUM));
		uldManifestVO.setSccs(rs.getString(SCCCOD));
		String additionalSccs = rs.getString(ADDSCC);
		if (uldManifestVO.getSccs() != null && !uldManifestVO.getSccs().isEmpty()) {
			if (rs.getString(ADDSCC) != null) {
				for (String scc : additionalSccs.split(",")) {
					if (!uldManifestVO.getSccs().contains(scc)) {
						uldManifestVO
								.setSccs(new StringBuilder(uldManifestVO.getSccs()).append(",").append(scc).toString());
					}
				}
			}
		} else {
			uldManifestVO.setSccs(rs.getString(ADDSCC));
		}
		uldManifestVO.setGrossWeight(measureMapper
				.toMeasure(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(rs.getDouble(GRSWGT)))));
		uldManifestVO.setTareWeight(measureMapper
				.toMeasure(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(rs.getDouble(TARWGT)))));
		uldManifestVO.setRemarks(rs.getString(ULDRMK));
		uldManifestVO.setSegmentSerialNumber(rs.getInt(SEGSERNUM));
		uldManifestVO.setNetWeight(measureMapper
				.toMeasure(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(rs.getDouble(NETWGT)))));
		uldManifestVO.setManifestedPieces(rs.getInt(MFTPCS));
		uldManifestVO.setManifestedWeight(measureMapper
				.toMeasure(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(rs.getDouble(MFTWGT)))));
		uldManifestVO.setPol(rs.getString(POL));
		uldManifestVO.setPol(rs.getString(POU));
		uldManifestVO.setUldPosition(rs.getString(ULDPOS));
		uldManifestVO.setIsBaseFlag(String.valueOf("Y".equals(rs.getString(BSEFLG))));
		uldManifestVO.setLocationCode(rs.getString(CURLOCCOD));
		uldManifestVO.setLocation(rs.getString(FWDLOCCOD));
	}

	private void updateManifestedUldDetails(UldManifestVO uldManifestVO, ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		uldManifestVO.setBarrowFlag(rs.getString(BARROWFLG));
		uldManifestVO.setPol(rs.getString(POL));
		uldManifestVO.setPou(rs.getString(POU));
		uldManifestVO.setManifestedPieces(uldManifestVO.getManifestedPieces() + rs.getInt(MFTPCS));
		double manifestSum = uldManifestVO.getManifestedWeight().getSystemValue() + rs.getDouble(MFTWGT);
		uldManifestVO.setManifestedWeight(
				measureMapper.toMeasure(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(manifestSum))));
		String sccCode = rs.getString(SCCCOD);
		String additionalSccs = rs.getString(ADDSCC);
		if (uldManifestVO.getSccs() != null && !uldManifestVO.getSccs().isEmpty()) {
			updateULDManifestSCCDetails(uldManifestVO, sccCode, additionalSccs);
		} else {
			populateULDManifestSCCDetails(uldManifestVO, sccCode, additionalSccs);
		}
	}

	private void populateULDManifestSCCDetails(UldManifestVO uldManifestVO, String sccCode, String additionalSccs) {
		if (sccCode != null && !sccCode.isEmpty()) {
			uldManifestVO.setSccs(sccCode);
		}
		if (additionalSccs != null && additionalSccs.trim().length() > 0) {
			for (String scc : additionalSccs.split(",")) {
				if (uldManifestVO.getSccs() == null || !uldManifestVO.getSccs().contains(scc)) {
					uldManifestVO
							.setSccs(new StringBuilder(uldManifestVO.getSccs()).append(",").append(scc).toString());
				}
			}
		}
	}

	private void updateULDManifestSCCDetails(UldManifestVO uldManifestVO, String sccCode, String additionalSccs) {
		if (sccCode != null && !sccCode.isEmpty() && !uldManifestVO.getSccs().contains(sccCode)) {
			uldManifestVO.setSccs(new StringBuilder(uldManifestVO.getSccs()).append(",").append(sccCode).toString());
		}
		if (additionalSccs != null) {
			for (String scc : additionalSccs.split(",")) {
				if (!uldManifestVO.getSccs().contains(scc)) {
					uldManifestVO
							.setSccs(new StringBuilder(uldManifestVO.getSccs()).append(",").append(scc).toString());
				}
			}
		}
	}

	private ManifestVO populateExportFlightOperationsVO(ResultSet rs) throws SQLException {
		ManifestVO manifestVO = new ManifestVO();
		String fltCarrierCode = rs.getInt(APHCODUSE) == 2 ? rs.getString(TWOAPHCOD) : rs.getString(THRAPHCOD);
		FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
		flightDetailsVO.setCompanyCode(rs.getString(CMPCOD));
		flightDetailsVO.setAirportCode(rs.getString(LEGORG));
		flightDetailsVO.setCarrierId(rs.getInt(FLTCARIDR));
		flightDetailsVO.setCarrierCode(fltCarrierCode);
		flightDetailsVO.setFlightNumber(rs.getString(FLTNUM));
		flightDetailsVO.setFlightSequenceNumber(rs.getInt(FLTSEQNUM));
		flightDetailsVO.setLegSerialNumber(rs.getInt(LEGSERNUM));
		flightDetailsVO.setFlightRoute(rs.getString(FLTROU));
		flightDetailsVO.setFlightTypeCode("O");
		if ("T".equals(rs.getString(FLTTYP))) {
			flightDetailsVO.setFlightTypeCode("T");
		} else if ("Y".equals(rs.getString(DOMFLTFLG))) {
			flightDetailsVO.setFlightTypeCode("D");
		}
		flightDetailsVO.setFlightCategory(rs.getString(FLTTYP));
		flightDetailsVO.setAircraftType(rs.getString(ACRTYP));
		flightDetailsVO.setTailNumber(rs.getString(TALNUM));
		flightDetailsVO.setDepartureGate(rs.getString(DEPGTE));
		this.populateFlightDateAndTimeDetails(flightDetailsVO, rs);
		manifestVO.setFlightDetailsVO(flightDetailsVO);
		return manifestVO;
	}

	private void populateFlightDateAndTimeDetails(FlightDetailsVO flightDetailsVO, ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		if (rs.getTimestamp(FLTDAT) != null) {
			ZonedDateTime date = rs.getString(LEGORG) != null
					? localDateUtil.getLocalDate(rs.getString(LEGORG), rs.getTimestamp(FLTDAT))
					: localDateUtil.getLocalDate(null, rs.getTimestamp(FLTDAT));
			flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(date));
		}
		if (rs.getTimestamp(FLTATD) != null) {
			ZonedDateTime atd = rs.getString(LEGORG) != null
					? localDateUtil.getLocalDate(rs.getString(LEGORG), rs.getTimestamp(FLTATD))
					: localDateUtil.getLocalDate(null, rs.getTimestamp(FLTATD));
			flightDetailsVO.setAtd(LocalDateMapper.toLocalDate(atd));
		}
		if (rs.getTimestamp(FLTETD) != null) {
			ZonedDateTime etd = rs.getString(LEGORG) != null
					? localDateUtil.getLocalDate(rs.getString(LEGORG), rs.getTimestamp(FLTETD))
					: localDateUtil.getLocalDate(null, rs.getTimestamp(FLTETD));
			flightDetailsVO.setEtd(LocalDateMapper.toLocalDate(etd));
		}
		if (rs.getTimestamp(FLTSTD) != null) {
			ZonedDateTime std = rs.getString(LEGORG) != null
					? localDateUtil.getLocalDate(rs.getString(LEGORG), rs.getTimestamp(FLTSTD))
					: localDateUtil.getLocalDate(null, rs.getTimestamp(FLTSTD));
			flightDetailsVO.setStd(LocalDateMapper.toLocalDate(std));
		}
		if (Objects.nonNull(rs.getTimestamp(FOWMILTIM))) {
			ZonedDateTime fowMilestoneTime = localDateUtil.getLocalDate(rs.getString(LEGORG),
					rs.getTimestamp(FOWMILTIM));
			flightDetailsVO.setFowMilestoneTime(LocalDateMapper.toLocalDate(fowMilestoneTime));
		}
	}
}
