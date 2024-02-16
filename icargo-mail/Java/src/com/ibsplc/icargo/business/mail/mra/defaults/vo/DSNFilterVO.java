
	/*
	 * DSNFilterVO.java Created on Sep 17, 2008
	 *
	 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.business.mail.mra.defaults.vo;

	import com.ibsplc.xibase.server.framework.vo.AbstractVO;

	/**
	 * @author A-3229
	 *
	 */
	public class DSNFilterVO extends AbstractVO {
		
		private String companyCode;
		
		private String dsn;
		
		private String poaCode;
		
		private String blgBasis;
		
		private int csgSequenceNumber;
		
		private String csgDocumentNumber;
		
		private int serialNumber;
		
		private int seqNumber;
		
		private String baseCurrency;
		
		
		
		/**
		 * @return Returns the dsn.
		 */
		public String getDsn() {
			return dsn;
		}



		/**
		 * @param dsn The dsn to set.
		 */
		public void setDsn(String dsn) {
			this.dsn = dsn;
		}


		/**
		 * @return Returns the companyCode
		 */

		public String getCompanyCode() {
			return companyCode;
		}
		/**
		 * @param companyCode
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}


		/**
		 * @return Returns the blgBasis.
		 */
		public String getBlgBasis() {
			return blgBasis;
		}



		/**
		 * @param blgBasis The blgBasis to set.
		 */
		public void setBlgBasis(String blgBasis) {
			this.blgBasis = blgBasis;
		}


		/**
		 * @return Returns the csgDocumentNumber.
		 */
		public String getCsgDocumentNumber() {
			return csgDocumentNumber;
		}

		/**
		 * @param csgDocumentNumber The csgDocumentNumber to set.
		 */

		public void setCsgDocumentNumber(String csgDocumentNumber) {
			this.csgDocumentNumber = csgDocumentNumber;
		}


		/**
		 * @return Returns the csgSequenceNumber.
		 */
		public int getCsgSequenceNumber() {
			return csgSequenceNumber;
		}


		/**
		 * @param csgSequenceNumber The csgSequenceNumber to set.
		 */
		public void setCsgSequenceNumber(int csgSequenceNumber) {
			this.csgSequenceNumber = csgSequenceNumber;
		}


		/**
		 * @return Returns the poaCode.
		 */
		public String getPoaCode() {
			return poaCode;
		}


		/**
		 * @param poaCode The poaCode to set.
		 */
		public void setPoaCode(String poaCode) {
			this.poaCode = poaCode;
		}


		/**
		 * @return Returns the seqNumber.
		 */
		public int getSeqNumber() {
			return seqNumber;
		}

		/**
		 * @param seqNumber The seqNumber to set.
		 */

		public void setSeqNumber(int seqNumber) {
			this.seqNumber = seqNumber;
		}


		/**
		 * @return Returns the serialNumber.
		 */
		public int getSerialNumber() {
			return serialNumber;
		}

		/**
		 * @param serialNumber The serialNumber to set.
		 */

		public void setSerialNumber(int serialNumber) {
			this.serialNumber = serialNumber;
		}


		/**
		 * @return Returns the baseCurrency.
		 */
		public String getBaseCurrency() {
			return baseCurrency;
		}

		/**
		 * @param baseCurrency The baseCurrency to set.
		 */

		public void setBaseCurrency(String baseCurrency) {
			this.baseCurrency = baseCurrency;
		}
		
		
		
		
	}

