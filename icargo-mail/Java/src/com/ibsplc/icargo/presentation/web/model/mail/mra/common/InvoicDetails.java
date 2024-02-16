package com.ibsplc.icargo.presentation.web.model.mail.mra.common;
/**
 * @author A-8464
 *
 */
public class InvoicDetails {
	
	  private String mailIdr;
	    private double weight;
	    private double rate;
	    private double charge;
	    private double disincentive;
	    private double incentive;
	    private double netamount;
	    private double invoicamount;
	    private String invoicPayStatus;
	    private double claimamount;
	    private String claimStatus;
	    private String remarks;
	    
		//additional info - process status, PK
		private String mailbagInvoicProcessingStatus;
	    private String companyCode;     
	    private String poaCode; 
	    private long mailSequenceNumber;
	    private long versionNumber;
	    private String currencyCode;
	    private String mailSubClass;
	    
	    //expanded details
	    private String consignment;
	    private String origin;
	    private String destination;
	    private String mailClass;
	    private String codeshareInterlinePartner;
	    private String scheduledDelivery;
	    private String regionCode;
	    private double codeShareProrate;
	    private double codeShareAmount;
	    
	    //claim range sums
	    private String clmlessfiv;
	    private String clmfivtoten;
	    private String clmtentotwentyfiv;
	    private String clmtwentyfivtofifty;
	    private String clmfiftytohundred;
	    private String clmhundredtofivhundred;
	    private String clmgrtfivhundred;
	    private String weightUnit;
	    
	    private int serialNumber;
	    
	    private String forceMajeureStatus;
	  //bucket wise sums
	    private String cntawtinc;
	    private String cntovrnotmra;
	    private String clmzropay;
	    private String clmnoinc;
	    private String clmratdif;
	    private String clmwgtdif;
	    private String clmmisscn;
	    private String clmlatdlv;
	    private String clmsrvrsp;
	    private String latdlv;
	    private String misorgscn;
	    private String misdstscn;
	    private String fulpaid;
	    private String ovrratdif;
	    private String ovrwgtdif;
	    private String ovrclsdif;
	    private String ovrsrvrsp;
	    private String ovroth;
	    private String clmoth;
	    private String clmnotinv;
	    private String ovrpayacp;
	    private String ovrpayrej;  
	    private String clmfrcmjr;
	    private String shrpayacp;
	    private String clmstagen; 
	    private String clmstasub; 
	    private String dummyorg;
	    private String dummydst;
	    private String dlvscnwrg;
	    private String amotobeact;
	    private String amotact;
	    public String getAmotobeact() {
			return amotobeact;
		}
		public void setAmotobeact(String amotobeact) {
			this.amotobeact = amotobeact;
		}
		public String getAmotact() {
			return amotact;
		}
		public void setAmotact(String amotact) {
			this.amotact = amotact;
		}
	    public String getDummyorg() {
			return dummyorg;
		}
		public void setDummyorg(String dummyorg) {
			this.dummyorg = dummyorg;
		}
		public String getDummydst() {
			return dummydst;
		}
		public void setDummydst(String dummydst) {
			this.dummydst = dummydst;
		}
        public String getClmstagen() {
			return clmstagen;
		}
		public void setClmstagen(String clmstagen) {
			this.clmstagen = clmstagen;
		}
		public String getClmstasub() {
			return clmstasub;
		}
		public void setClmstasub(String clmstasub) {
			this.clmstasub = clmstasub;
		}
        public String getClmfrcmjr() {
			return clmfrcmjr;
		}
		public void setClmfrcmjr(String clmfrcmjr) {
			this.clmfrcmjr = clmfrcmjr;
		}
        private double totalSettlementAmount;
	    
	    public String getOvrpayacp() {
			return ovrpayacp;
		}
		public void setOvrpayacp(String ovrpayacp) {
			this.ovrpayacp = ovrpayacp;
		}
		public String getOvrpayrej() {
			return ovrpayrej;
		}
		public void setOvrpayrej(String ovrpayrej) {
			this.ovrpayrej = ovrpayrej;
		}   
        public double getTotalSettlementAmount() {
			return totalSettlementAmount;
		}
	
		public void setTotalSettlementAmount(double totalSettlementAmount) {
			this.totalSettlementAmount = totalSettlementAmount;
		}
	
		public String getClmlessfiv() {
			return clmlessfiv;
		}
		public void setClmlessfiv(String clmlessfiv) {
			this.clmlessfiv = clmlessfiv;
		}
		public String getClmfivtoten() {
			return clmfivtoten;
		}
		public void setClmfivtoten(String clmfivtoten) {
			this.clmfivtoten = clmfivtoten;
		}
		public String getClmtentotwentyfiv() {
			return clmtentotwentyfiv;
		}
		public void setClmtentotwentyfiv(String clmtentotwentyfiv) {
			this.clmtentotwentyfiv = clmtentotwentyfiv;
		}
		public String getClmtwentyfivtofifty() {
			return clmtwentyfivtofifty;
		}
		public void setClmtwentyfivtofifty(String clmtwentyfivtofifty) {
			this.clmtwentyfivtofifty = clmtwentyfivtofifty;
		}
		public String getClmfiftytohundred() {
			return clmfiftytohundred;
		}
		public void setClmfiftytohundred(String clmfiftytohundred) {
			this.clmfiftytohundred = clmfiftytohundred;
		}
		public String getClmhundredtofivhundred() {
			return clmhundredtofivhundred;
		}
		public void setClmhundredtofivhundred(String clmhundredtofivhundred) {
			this.clmhundredtofivhundred = clmhundredtofivhundred;
		}
		public String getClmgrtfivhundred() {
			return clmgrtfivhundred;
		}
		public void setClmgrtfivhundred(String clmgrtfivhundred) {
			this.clmgrtfivhundred = clmgrtfivhundred;
		}
		public String getMailIdr() {
			return mailIdr;
		}
		public void setMailIdr(String mailIdr) {
			this.mailIdr = mailIdr;
		}
		public double getWeight() {
			return weight;
		}
		public void setWeight(double weight) {
			this.weight = weight;
		}
		public double getRate() {
			return rate;
		}
		public void setRate(double rate) {
			this.rate = rate;
		}
		public double getCharge() {
			return charge;
		}
		public String getCompanyCode() {
			return companyCode;
		}
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		public String getPoaCode() {
			return poaCode;
		}
		public void setPoaCode(String poaCode) {
			this.poaCode = poaCode;
		}
		public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}
		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
		}
		public long getVersionNumber() {
			return versionNumber;
		}
		public void setVersionNumber(long versionNumber) {
			this.versionNumber = versionNumber;
		}
		public String getConsignment() {
			return consignment;
		}
		public void setConsignment(String consignment) {
			this.consignment = consignment;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public String getMailClass() {
			return mailClass;
		}
		public void setMailClass(String mailClass) {
			this.mailClass = mailClass;
		}
		public String getCodeshareInterlinePartner() {
			return codeshareInterlinePartner;
		}
		public void setCodeshareInterlinePartner(String codeshareInterlinePartner) {
			this.codeshareInterlinePartner = codeshareInterlinePartner;
		}
		public String getScheduledDelivery() {
			return scheduledDelivery;
		}
		public void setScheduledDelivery(String scheduledDelivery) {
			this.scheduledDelivery = scheduledDelivery;
		}
		public String getRegionCode() {
			return regionCode;
		}
		public void setRegionCode(String regionCode) {
			this.regionCode = regionCode;
		}
		public double getCodeShareProrate() {
			return codeShareProrate;
		}
		public void setCodeShareProrate(double codeShareProrate) {
			this.codeShareProrate = codeShareProrate;
		}
		public double getCodeShareAmount() {
			return codeShareAmount;
		}
		public void setCodeShareAmount(double codeShareAmount) {
			this.codeShareAmount = codeShareAmount;
		}
		public void setCharge(double charge) {
			this.charge = charge;
		}
		public double getDisincentive() {
			return disincentive;
		}
		public void setDisincentive(double disincentive) {
			this.disincentive = disincentive;
		}
		public double getIncentive() {
			return incentive;
		}
		public void setIncentive(double incentive) {
			this.incentive = incentive;
		}
		public double getNetamount() {
			return netamount;
		}
		public void setNetamount(double netamount) {
			this.netamount = netamount;
		}
		public double getInvoicamount() {
			return invoicamount;
		}
		public void setInvoicamount(double invoicamount) {
			this.invoicamount = invoicamount;
		}
		public String getInvoicPayStatus() {
			return invoicPayStatus;
		}
		public void setInvoicPayStatus(String invoicPayStatus) {
			this.invoicPayStatus = invoicPayStatus;
		}
		public double getClaimamount() {
			return claimamount;
		}
		public void setClaimamount(double claimamount) {
			this.claimamount = claimamount;
		}
		public String getClaimStatus() {
			return claimStatus;
		}
		public void setClaimStatus(String claimStatus) {
			this.claimStatus = claimStatus;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getMailbagInvoicProcessingStatus() {
			return mailbagInvoicProcessingStatus;
		}
		public void setMailbagInvoicProcessingStatus(String mailbagInvoicProcessingStatus) {
			this.mailbagInvoicProcessingStatus = mailbagInvoicProcessingStatus;
		}
		public String getOrigin() {
			return origin;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		public String getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		public String getMailSubClass() {
			return mailSubClass;
		}
		public void setMailSubClass(String mailSubClass) {
			this.mailSubClass = mailSubClass;
		}
		public int getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(int serialNumber) {
			this.serialNumber = serialNumber;
		}
		/**
		 * @return the weightUnit
		 */
		public String getWeightUnit() {
			return weightUnit;
		}
		/**
		 * @param weightUnit the weightUnit to set
		 */
		public void setWeightUnit(String weightUnit) {
			this.weightUnit = weightUnit;
		}
		/**
		 * @return the forceMajeureStatus
		 */
		public String getForceMajeureStatus() {
			return forceMajeureStatus;
		}
		/**
		 * @param forceMajeureStatus the forceMajeureStatus to set
		 */
		public void setForceMajeureStatus(String forceMajeureStatus) {
			this.forceMajeureStatus = forceMajeureStatus;
		}
		/**
		 * @return the cntawtinc
		 */
		public String getCntawtinc() {
			return cntawtinc;
		}
		/**
		 * @param cntawtinc the cntawtinc to set
		 */
		public void setCntawtinc(String cntawtinc) {
			this.cntawtinc = cntawtinc;
		}
		/**
		 * @return the cntovrnotmra
		 */
		public String getCntovrnotmra() {
			return cntovrnotmra;
		}
		/**
		 * @param cntovrnotmra the cntovrnotmra to set
		 */
		public void setCntovrnotmra(String cntovrnotmra) {
			this.cntovrnotmra = cntovrnotmra;
		}
		/**
		 * @return the clmzropay
		 */
		public String getClmzropay() {
			return clmzropay;
		}
		/**
		 * @param clmzropay the clmzropay to set
		 */
		public void setClmzropay(String clmzropay) {
			this.clmzropay = clmzropay;
		}
		/**
		 * @return the clmnoinc
		 */
		public String getClmnoinc() {
			return clmnoinc;
		}
		/**
		 * @param clmnoinc the clmnoinc to set
		 */
		public void setClmnoinc(String clmnoinc) {
			this.clmnoinc = clmnoinc;
		}
		/**
		 * @return the clmratdif
		 */
		public String getClmratdif() {
			return clmratdif;
		}
		/**
		 * @param clmratdif the clmratdif to set
		 */
		public void setClmratdif(String clmratdif) {
			this.clmratdif = clmratdif;
		}
		/**
		 * @return the clmwgtdif
		 */
		public String getClmwgtdif() {
			return clmwgtdif;
		}
		/**
		 * @param clmwgtdif the clmwgtdif to set
		 */
		public void setClmwgtdif(String clmwgtdif) {
			this.clmwgtdif = clmwgtdif;
		}
		/**
		 * @return the clmmisscn
		 */
		public String getClmmisscn() {
			return clmmisscn;
		}
		/**
		 * @param clmmisscn the clmmisscn to set
		 */
		public void setClmmisscn(String clmmisscn) {
			this.clmmisscn = clmmisscn;
		}
		/**
		 * @return the clmlatdlv
		 */
		public String getClmlatdlv() {
			return clmlatdlv;
		}
		/**
		 * @param clmlatdlv the clmlatdlv to set
		 */
		public void setClmlatdlv(String clmlatdlv) {
			this.clmlatdlv = clmlatdlv;
		}
		/**
		 * @return the clmsrvrsp
		 */
		public String getClmsrvrsp() {
			return clmsrvrsp;
		}
		/**
		 * @param clmsrvrsp the clmsrvrsp to set
		 */
		public void setClmsrvrsp(String clmsrvrsp) {
			this.clmsrvrsp = clmsrvrsp;
		}
		/**
		 * @return the latdlv
		 */
		public String getLatdlv() {
			return latdlv;
		}
		/**
		 * @param latdlv the latdlv to set
		 */
		public void setLatdlv(String latdlv) {
			this.latdlv = latdlv;
		}
		/**
		 * @return the misorgscn
		 */
		public String getMisorgscn() {
			return misorgscn;
		}
		/**
		 * @param misorgscn the misorgscn to set
		 */
		public void setMisorgscn(String misorgscn) {
			this.misorgscn = misorgscn;
		}
		/**
		 * @return the misdstscn
		 */
		public String getMisdstscn() {
			return misdstscn;
		}
		/**
		 * @param misdstscn the misdstscn to set
		 */
		public void setMisdstscn(String misdstscn) {
			this.misdstscn = misdstscn;
		}
		/**
		 * @return the fulpaid
		 */
		public String getFulpaid() {
			return fulpaid;
		}
		/**
		 * @param fulpaid the fulpaid to set
		 */
		public void setFulpaid(String fulpaid) {
			this.fulpaid = fulpaid;
		}
		/**
		 * @return the ovrratdif
		 */
		public String getOvrratdif() {
			return ovrratdif;
		}
		/**
		 * @param ovrratdif the ovrratdif to set
		 */
		public void setOvrratdif(String ovrratdif) {
			this.ovrratdif = ovrratdif;
		}
		/**
		 * @return the ovrwgtdif
		 */
		public String getOvrwgtdif() {
			return ovrwgtdif;
		}
		/**
		 * @param ovrwgtdif the ovrwgtdif to set
		 */
		public void setOvrwgtdif(String ovrwgtdif) {
			this.ovrwgtdif = ovrwgtdif;
		}
		/**
		 * @return the ovrclsdif
		 */
		public String getOvrclsdif() {
			return ovrclsdif;
		}
		/**
		 * @param ovrclsdif the ovrclsdif to set
		 */
		public void setOvrclsdif(String ovrclsdif) {
			this.ovrclsdif = ovrclsdif;
		}
		/**
		 * @return the ovrsrvrsp
		 */
		public String getOvrsrvrsp() {
			return ovrsrvrsp;
		}
		/**
		 * @param ovrsrvrsp the ovrsrvrsp to set
		 */
		public void setOvrsrvrsp(String ovrsrvrsp) {
			this.ovrsrvrsp = ovrsrvrsp;
		}
		/**
		 * @return the ovroth
		 */
		public String getOvroth() {
			return ovroth;
		}
		/**
		 * @param ovroth the ovroth to set
		 */
		public void setOvroth(String ovroth) {
			this.ovroth = ovroth;
		}
		/**
		 * @return the clmoth
		 */
		public String getClmoth() {
			return clmoth;
		}
		/**
		 * @param clmoth the clmoth to set
		 */
		public void setClmoth(String clmoth) {
			this.clmoth = clmoth;
		}
		/**
		 * @return the clmnotinv
		 */
		public String getClmnotinv() {
			return clmnotinv;
		}
		/**
		 * @param clmnotinv the clmnotinv to set
		 */
		public void setClmnotinv(String clmnotinv) {
			this.clmnotinv = clmnotinv;
		}
		/**
		 * 	Getter for shrpayacp 
		 *	Added by : A-5219 on 07-Oct-2020
		 * 	Used for :
		 */
		public String getShrpayacp() {
			return shrpayacp;
		}
		/**
		 *  @param shrpayacp the shrpayacp to set
		 * 	Setter for shrpayacp 
		 *	Added by : A-5219 on 07-Oct-2020
		 * 	Used for :
		 */
		public void setShrpayacp(String shrpayacp) {
			this.shrpayacp = shrpayacp;
		}
		 public String getDlvscnwrg() {
				return dlvscnwrg;
			}
			public void setDlvscnwrg(String dlvscnwrg) {
				this.dlvscnwrg = dlvscnwrg;
		}

}
