/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-7794
 *
 */
public class MiscFileFilterVO extends AbstractVO{

	public static final String NO_DATA_FOUND_FOR_IS_FILE_GENERATION = "cra.sisbilling.isfilegeneration.nodatafound";
	public static final String ISFILE_MESSAGE_TYPE = "ISXMLINVOICE";
	public static final String MESSAGE_STANDARD = "IFCSTD";
	public static final String MISCELLANEOUS="Miscellaneous";
	public static final String MAIL="Mail";
	
	private String classType;
	private String clearancePeriod;
	private String fileName;
	private boolean areNewInvoicesOnlyRequired;
	private String companyCode;
	private String transactionStatus;
	private boolean generateFile;
	private String generatedFileName;
	private String airlineIdr;
	/**
	 * @return the classType
	 */
	public String getClassType() {
		return classType;
	}
	/**
	 * @param classType the classType to set
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}
	/**
	 * @return the clearancePeriod
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}
	/**
	 * @param clearancePeriod the clearancePeriod to set
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the areNewInvoicesOnlyRequired
	 */
	public boolean isAreNewInvoicesOnlyRequired() {
		return areNewInvoicesOnlyRequired;
	}
	/**
	 * @param areNewInvoicesOnlyRequired the areNewInvoicesOnlyRequired to set
	 */
	public void setAreNewInvoicesOnlyRequired(boolean areNewInvoicesOnlyRequired) {
		this.areNewInvoicesOnlyRequired = areNewInvoicesOnlyRequired;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * @return the generateFile
	 */
	public boolean isGenerateFile() {
		return generateFile;
	}
	/**
	 * @param generateFile the generateFile to set
	 */
	public void setGenerateFile(boolean generateFile) {
		this.generateFile = generateFile;
	}
	/**
	 * @return the generatedFileName
	 */
	public String getGeneratedFileName() {
		return generatedFileName;
	}
	/**
	 * @param generatedFileName the generatedFileName to set
	 */
	public void setGeneratedFileName(String generatedFileName) {
		this.generatedFileName = generatedFileName;
	}
	/**
	 * @return the airlineIdr
	 */
	public String getAirlineIdr() {
		return airlineIdr;
	}
	/**
	 * @param airlineIdr the airlineIdr to set
	 */
	public void setAirlineIdr(String airlineIdr) {
		this.airlineIdr = airlineIdr;
	}
	
	
}
