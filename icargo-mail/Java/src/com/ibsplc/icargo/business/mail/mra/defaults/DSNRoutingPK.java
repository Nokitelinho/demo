
	/*
	 * DSNRoutingPK.java Created on Sep 05, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.business.mail.mra.defaults;

	import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

	

	/**
	 * @author A-3229
	 *
	 */
	
	@Embeddable
	public class DSNRoutingPK implements Serializable{
		
		private String companyCode;
		private long mailSequenceNumebr;
		private int routingSerialNum;
		
		

		/**
		 * @return the companyCode
		 */
		@KeyCondition(column = "CMPCOD")
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
		 * @return the mailSequenceNumebr
		 */
		@KeyCondition(column = "MALSEQNUM")
		public long getMailSequenceNumebr() {
			return mailSequenceNumebr;
		}


		/**
		 * @param mailSequenceNumebr the mailSequenceNumebr to set
		 */
		public void setMailSequenceNumebr(long mailSequenceNumebr) {
			this.mailSequenceNumebr = mailSequenceNumebr;
		}


		/**
		 * @return the routingSerialNum
		 */
		@KeyCondition(column = "RTGSERNUM")
		public int getRoutingSerialNum() {
			return routingSerialNum;
		}


		/**
		 * @param routingSerialNum the routingSerialNum to set
		 */
		public void setRoutingSerialNum(int routingSerialNum) {
			this.routingSerialNum = routingSerialNum;
		}


		public DSNRoutingPK(){}
		

		
		
		/**
		 * @param companyCode
		 * @param mailSequenceNumebr
		 * @param routingSerialNum
		 */
		public DSNRoutingPK(String companyCode, long mailSequenceNumebr,
				int routingSerialNum) {
			super();
			this.companyCode = companyCode;
			this.mailSequenceNumebr = mailSequenceNumebr;
			this.routingSerialNum = routingSerialNum;
		}


		/**
		 * @param object
		 * @return boolean
		 */
		public boolean equals(Object object) {
			if (object != null) {
				return (hashCode() == object.hashCode());
			}
			return false;
		}

		/**
		 * @return int
		 */
		public int hashCode() {
			return new StringBuilder().append(companyCode)
					.append(mailSequenceNumebr).append(routingSerialNum).toString().hashCode();
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "DSNRoutingPK [companyCode=" + companyCode
					+ ", mailSequenceNumebr=" + mailSequenceNumebr
					+ ", routingSerialNum=" + routingSerialNum + "]";
		}


		

		
		

	}


