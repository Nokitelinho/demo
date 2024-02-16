package com.ibsplc.icargo.business.stockcontrol.defaults;


import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

@KeyTable(table="STKRUSHISKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN",table="STKRUSHISKEY",key="STKRUSHIS_SEQ")
@Embeddable
public class StockReuseHistoryPK implements Serializable {
	    /**
	     * CMPCOD,SERNUM,MSTDOCNUM,DOCOWRIDR,DUPNUM,SEQNUM
	     */
	    private String companyCode;
	    private int serialNumber;
	    private String masterDocumentNumber; 
	  

		private int documentOwnerId;
	    private int duplicateNumber;
		private int sequenceNumber;
	    

	    

	    /**
	     * This method tests for equality of one instance of this class with
	     * the other.
	     * @param other - another object to test for equality
	     * @return boolean - returns true if equal
	     */
		public boolean equals(Object other) {
			return (other != null) && (hashCode() == other.hashCode());
		}
		
		/**
		 * This method generates the hashcode of an instance
		 * @return int - returns the hashcode of the instance
		 */
		public int hashCode() {
			return new StringBuilder(companyCode).append(serialNumber).append(masterDocumentNumber).append(documentOwnerId).append(duplicateNumber).append(sequenceNumber).toString().hashCode();
		}
		
		public void setSerialNumber(int serialNumber) {
			this.serialNumber=serialNumber;
		}
		 
		 /**
			 * @return the companyCode
			 */
			@KeyCondition(column="CMPCOD")
			public java.lang.String getCompanyCode() {
				return companyCode;
			}

			/**
			 * @param companyCode the companyCode to set
			 */
			public void setCompanyCode(java.lang.String companyCode) {
				this.companyCode = companyCode;
			}

			/**
			 * @return the documentOwnerId
			 */
			@KeyCondition(column="DOCOWRIDR")
			public int getDocumentOwnerId() {
				return documentOwnerId;
			}

			/**
			 * @param documentOwnerId the documentOwnerId to set
			 */
			public void setDocumentOwnerId(int documentOwnerId) {
				this.documentOwnerId = documentOwnerId;
			}

			/**
			 * @return the duplicateNumber
			 */
			@KeyCondition(column="DUPNUM")
			public int getDuplicateNumber() {
				return duplicateNumber;
			}

			/**
			 * @param duplicateNumber the duplicateNumber to set
			 */
			public void setDuplicateNumber(int duplicateNumber) {
				this.duplicateNumber = duplicateNumber;
			}

			/**
			 * @return the sequenceNumber
			 */
			@KeyCondition(column="SEQNUM")
			public int getSequenceNumber() {
				return sequenceNumber;
			}

			/**
			 * @param sequenceNumber the sequenceNumber to set
			 */
			public void setSequenceNumber(int sequenceNumber) {
				this.sequenceNumber = sequenceNumber;
			}

			/**
			 * @return the serialNumber
			 */
			@Key(generator="ID_GEN",startAt="1")
			public int getSerialNumber() {
				return serialNumber;
			}
			/**
			 * 
			 * @return master document number
			 */
			@KeyCondition(column="MSTDOCNUM")
			  public java.lang.String getMasterDocumentNumber() {
					return masterDocumentNumber;
				}

				public void setMasterDocumentNumber(java.lang.String masterDocumentNumber) {
					this.masterDocumentNumber = masterDocumentNumber;
				}
		@Override
		public String toString() {
			StringBuilder sbul = new StringBuilder(110);
			sbul.append("StockReuseHistoryPK [ ");
			sbul.append("companyCode '").append(this.companyCode);
			sbul.append("', serialNumber '").append(this.serialNumber);
			sbul.append("', masterDocumentNumber '").append(this.masterDocumentNumber);
			sbul.append("', documentOwnerId '").append(this.documentOwnerId);
			sbul.append("', duplicateNumber '").append(this.duplicateNumber);
			sbul.append("', sequenceNumber '").append(this.sequenceNumber);
			sbul.append("' ]");
			return sbul.toString();
		}

		
}

