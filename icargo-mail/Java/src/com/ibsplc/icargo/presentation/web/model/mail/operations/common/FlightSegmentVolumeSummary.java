package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.unit.Measure;

public class FlightSegmentVolumeSummary {

	private String companyCode;
	private int flightCarrierIdentifier;
	private String flightNumber;
	private long flightSequenceNumber;
	private String segmentOrigin;
	private String segmentDestination;
	private String aircraftType;
	private String legCfgnam;
	private Measure altWeight;
	private Measure altVolume;
	private double altUpperDeckOne;
	private double altLowerDeckOne;
	private double altLowerDeckTwo;
	private String allotmentId;
	private String connum;
	private String contyp;
	private String conpos;
	private Measure totUtlVolume;
	private double totUtlUpperDeckOne;
	private double totUtlLowerDeckOne;
	private double totUtlLowerDeckTwo;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public String getSegmentOrigin() {
		return segmentOrigin;
	}
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}
	public String getSegmentDestination() {
		return segmentDestination;
	}
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}
	public String getAircraftType() {
		return aircraftType;
	}
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
	public String getLegCfgnam() {
		return legCfgnam;
	}
	public void setLegCfgnam(String legCfgnam) {
		this.legCfgnam = legCfgnam;
	}
	public Measure getAltWeight() {
		return altWeight;
	}
	public void setAltWeight(Measure altWeight) {
		this.altWeight = altWeight;
	}
	public Measure getAltVolume() {
		return altVolume;
	}
	public void setAltVolume(Measure altVolume) {
		this.altVolume = altVolume;
	}
	public double getAltUpperDeckOne() {
		return altUpperDeckOne;
	}
	public void setAltUpperDeckOne(double altUpperDeckOne) {
		this.altUpperDeckOne = altUpperDeckOne;
	}
	public double getAltLowerDeckOne() {
		return altLowerDeckOne;
	}
	public void setAltLowerDeckOne(double altLowerDeckOne) {
		this.altLowerDeckOne = altLowerDeckOne;
	}
	public double getAltLowerDeckTwo() {
		return altLowerDeckTwo;
	}
	public void setAltLowerDeckTwo(double altLowerDeckTwo) {
		this.altLowerDeckTwo = altLowerDeckTwo;
	}
	public String getAllotmentId() {
		return allotmentId;
	}
	public void setAllotmentId(String allotmentId) {
		this.allotmentId = allotmentId;
	}
	public String getConnum() {
		return connum;
	}
	public void setConnum(String connum) {
		this.connum = connum;
	}
	public String getContyp() {
		return contyp;
	}
	public void setContyp(String contyp) {
		this.contyp = contyp;
	}
	public String getConpos() {
		return conpos;
	}
	public void setConpos(String conpos) {
		this.conpos = conpos;
	}
	public Measure getTotUtlVolume() {
		return totUtlVolume;
	}
	public void setTotUtlVolume(Measure totUtlVolume) {
		this.totUtlVolume = totUtlVolume;
	}
	public double getTotUtlUpperDeckOne() {
		return totUtlUpperDeckOne;
	}
	public void setTotUtlUpperDeckOne(double totUtlUpperDeckOne) {
		this.totUtlUpperDeckOne = totUtlUpperDeckOne;
	}
	public double getTotUtlLowerDeckOne() {
		return totUtlLowerDeckOne;
	}
	public void setTotUtlLowerDeckOne(double totUtlLowerDeckOne) {
		this.totUtlLowerDeckOne = totUtlLowerDeckOne;
	}
	public double getTotUtlLowerDeckTwo() {
		return totUtlLowerDeckTwo;
	}
	public void setTotUtlLowerDeckTwo(double totUtlLowerDeckTwo) {
		this.totUtlLowerDeckTwo = totUtlLowerDeckTwo;
	}
	
}
