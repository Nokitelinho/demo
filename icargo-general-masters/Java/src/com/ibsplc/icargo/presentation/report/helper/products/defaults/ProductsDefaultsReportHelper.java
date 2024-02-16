/*
 * ProductsDefaultsReportHelper.java Created on Aug 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.helper.products.defaults;


import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
/**
 * helper class for product brochure report
 * @author A-2046
 *
 */
public class ProductsDefaultsReportHelper {

	private static final String LOWPRIORITY = "L";
	private static final String MEDIUMPRIORITY = "M";
	private static final String HIGHPRIORITY = "H";
	private static final String LOW = "LOW";
	private static final String MEDIUM = "MEDIUM";
	private static final String HIGH= "HIGH";
	private static final String ACTIVE = "A";
	private static final String INACTIVE = "I";
	private static final String ACTIVED = "Active";
	private static final String INACTIVED = "Inactive";
	private static final String NEWD = "New";
	private static final String BLANK = "";

	@Helper(
			{
				@Help(
					reportId = "RPRPRD001",
					voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO",
					"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
					fieldNames = {"startDate","endDate"}
					)
			}
	)
	/**
	 * @param value
	 * @param extraInfo
	 */
	public static String formatStartDate(Object value, Collection extraInfo) {

		//System.out.println("ReportHelper.formatdate()");
		String startDate="";
		LocalDate localDate = (LocalDate)value;
		if((value)!=null){
			startDate=TimeConvertor.toStringFormat
					(localDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
		}
		return startDate;
	}

	@Helper(
				{
					@Help(
						reportId = "RPRPRD001",
						voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO",
						"com.ibsplc.icargo.business.products.defaults.vo.ProductVO",
						"com.ibsplc.icargo.business.products.defaults.vo.ProductVO",
						"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
						fieldNames = {"minimumWeightDisplay","maximumWeightDisplay",
						"minimumVolumeDisplay","maximumVolumeDisplay"}
						)
				}
		)
   /**
	 * @param value
	 * @param extraInfo
	 */
		public static String formatWeight(Object value, Collection extraInfo) {

		String weight=null;
		weight=String.valueOf(value);
		return weight;
		}

	@Helper(
					{
							@Help(
									reportId = "RPRPRD001",
									voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"
											},
									fieldNames = {"status"
									}
							)
					}
			)
	/**
	 * @param value
	 * @param extraInfo
	 */
			public static String formatStatus(Object value, Collection extraInfo) {
			if(ACTIVE.equals(value)) {
				return ACTIVED;
			} else if(INACTIVE.equals(value)) {
				return INACTIVED;
			} else {
				return NEWD;
			}

			}
		@Helper(
				{
						@Help(
								reportId = "RPRPRD001",
								voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
								fieldNames = {"transportMode"}
						)
				}
		)
		/**
	 * @param value
	 * @param extraInfo
	 */
		public static String formatTransportDetails(Object value, Collection extraInfo) {
			StringBuilder str = new StringBuilder();
			//System.out.println("********formatting transport mode**********");
		//	System.out.println(extraInfo);
			String transportDetails = null;
			ArrayList arr =(ArrayList)extraInfo;
			Collection<ProductTransportModeVO> transportDetail = 
				(Collection<ProductTransportModeVO>)arr.get(0);
			if(transportDetail.size()>0){
			//System.out.println(transportDetail);
			for(ProductTransportModeVO productTransportModeVO:transportDetail){
				str.append(productTransportModeVO.getTransportMode());
				//System.out.println(str);
				str.append(", ");
				}
				transportDetails = str.toString();
			return (transportDetails.substring(0,(transportDetails.length()-2)));
			}
	return BLANK;
	}
	@Helper(
					{
							@Help(
									reportId = "RPRPRD001",
									voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
									fieldNames = {"productScc"}
							)
					}
			)
   /**
	 * @param value
	 * @param extraInfo
	 */
			public static String formatSccDetails(Object value, Collection extraInfo) {
				StringBuilder str = new StringBuilder();
				String scc = null;
				ArrayList sccDetails = (ArrayList)extraInfo;
				Collection<ProductSCCVO> sccDetail = 
					(Collection<ProductSCCVO>)sccDetails.get(1);
				if(sccDetail.size()>0){
				for(ProductSCCVO productSCCVO:sccDetail){
					str.append(productSCCVO.getScc());
					str.append(", ");
					}
					scc = str.toString();

					return (scc.substring(0,(scc.length()-2)));
				}

				return BLANK;
}
@Helper(
					{
							@Help(
									reportId = "RPRPRD001",
									voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
									fieldNames = {"priority"}
							)
					}
			)
	/**
	 * @param value
	 * @param extraInfo
	 * @return 
	 */
			public static String formatPriorityDetails(Object value, Collection extraInfo) {
				StringBuilder str = new StringBuilder();
				String priority = null;

				ArrayList priorityDetails = (ArrayList)extraInfo;
				Collection<ProductPriorityVO> prioDetails =(Collection<ProductPriorityVO>)priorityDetails.get(2);
				if(prioDetails.size()>0){
				Map<String,String> hashmap = new HashMap<String,String>();
				hashmap.put(LOWPRIORITY,LOW);
				hashmap.put(HIGHPRIORITY,HIGH);
				hashmap.put(MEDIUMPRIORITY,MEDIUM);
				for(ProductPriorityVO priorityVO:prioDetails){
					str.append(hashmap.get(priorityVO.getPriority()));
					str.append(", ");
					}
					priority = str.toString();
				return (priority.substring(0,(priority.length()-2)));
				}
				return BLANK;
}
	@Helper(
						{
								@Help(
										reportId = "RPRPRD001",
										voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
										fieldNames = {"productEvents"}
								)
						}
				)
	/**
	 * @param value
	 * @param extraInfo
	 * @return 
	 */
	
				public static String formatMileStoneDetails(Object value, Collection extraInfo) {
					StringBuilder str = new StringBuilder();
					String mileStone = null;
					ArrayList mileStoneDetails = (ArrayList)extraInfo;
					Collection<ProductEventVO> mileStones = (Collection<ProductEventVO>)mileStoneDetails.get(3);
					if(mileStones.size()>0){
					for(ProductEventVO eventVO:mileStones){
						str.append(eventVO.getEventCode());
						str.append(", ");
						}
						mileStone = str.toString();
				return (mileStone.substring(0,(mileStone.length()-2)));
					}
					return BLANK;
}

@Helper(
						{
								@Help(
										reportId = "RPRPRD001",
										voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
										fieldNames = {"restrictionCommodity"}
								)
						}
				)
	/**
	 * @param value
	 * @param extraInfo
	 * @return 
	 */
	
	public static String formatCommodityDetails(Object value, Collection extraInfo) {
		StringBuilder str = new StringBuilder();
		String commodity= null;
		ArrayList commodityDetails =(ArrayList)extraInfo;
		Collection<RestrictionCommodityVO> commodities =
		(Collection<RestrictionCommodityVO>)commodityDetails.get(4);
			if(commodities.size()>0){
			for(RestrictionCommodityVO restrictionVO:commodities){
			if(restrictionVO.getIsRestricted()){
				str.append(restrictionVO.getCommodity());
				str.append(", ");
					}
			}
				commodity= str.toString();
			return (commodity.substring(0,(commodity.length()-2)));
					}
					return BLANK;
}

@Helper(
						{
								@Help(
										reportId = "RPRPRD001",
										voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
										fieldNames = {"restrictionSegment"}
								)
						}
				)
	/**
	 * @param value
	 * @param extraInfo
	 * @return 
	 */
	
	public static String formatSegmentDetails(Object value, Collection extraInfo) {
		StringBuilder str = new StringBuilder();
		String segment= null;
		ArrayList segmentDetails = (ArrayList)extraInfo;
		Collection<RestrictionSegmentVO> segments =
		(Collection<RestrictionSegmentVO>)segmentDetails.get(5);
		if(segments.size()>0){
		for(RestrictionSegmentVO segmentVO:segments){
			if(segmentVO.getIsRestricted()){
			str.append(segmentVO.getOrigin());
				str.append("-");
				str.append(segmentVO.getDestination());
				str.append(", ");
				}
				}
				segment= str.toString();
			return (segment.substring(0,(segment.length()-2)));
			}
					return BLANK;
}
@Helper(
				{
						@Help(
								reportId = "RPRPRD001",
								voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
								fieldNames = {"restrictionCustomerGroup"}
						)
				}
		)
	/**
	 * @param value
	 * @param extraInfo
	 * @return 
	 */
	
		public static String formatCustomerDetails(Object value, Collection extraInfo) {
			StringBuilder str = new StringBuilder();
			String customer= null;
			ArrayList customerDetails = (ArrayList)extraInfo;
				Collection<RestrictionCustomerGroupVO> customers =
				(Collection<RestrictionCustomerGroupVO>)customerDetails.get(6);
				if(customers.size()>0){
			for(RestrictionCustomerGroupVO customerVO:customers){
				if(customerVO.getIsRestricted()){
				str.append(customerVO.getCustomerGroup());
				str.append(", ");
				}
			}
				customer= str.toString();
				if(customer != null && customer.trim().length()>2)	{
			return (customer.substring(0,(customer.length()-2)));
				} else {
					return customer;
				}
			}
			return BLANK;
}

@Helper(
			{
					@Help(
							reportId = "RPRPRD001",
							voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
							fieldNames = {"restrictionPaymentTerms"}
					)
			}
	)
	/**
	 * @param value
	 * @param extraInfo
	 * @return 
	 */
	
	public static String formatPaymentDetails(Object value, Collection extraInfo) {
		StringBuilder str = new StringBuilder();
		String paymentTerms= null;
		ArrayList paymentDetails = (ArrayList)extraInfo;
		Collection<RestrictionPaymentTermsVO> payments =
			(Collection<RestrictionPaymentTermsVO>)paymentDetails.get(7);
			if(payments.size()>0){
		for(RestrictionPaymentTermsVO paymentVO:payments){
			if(paymentVO.getIsRestricted()){
			str.append(paymentVO.getPaymentTerm());
			str.append(", ");
			}
		}
			paymentTerms= str.toString();
		return (paymentTerms.substring(0,(paymentTerms.length()-2)));
	}
	return BLANK;
}


@Helper(
			{
					@Help(
							reportId = "RPRPRD001",
							voNames = {"com.ibsplc.icargo.business.products.defaults.vo.ProductVO"},
							fieldNames = {"restrictionStation"}
					)
			}
	)
	/**
	 * @param value
	 * @param extraInfo
	 * @return 
	 */
	
	public static String formatRestrictionStation(Object value, Collection extraInfo) {
		String originDestination = null;
		StringBuilder origin= new StringBuilder();
		StringBuilder destination =new StringBuilder();
		ArrayList restrictStations = (ArrayList)extraInfo;
		Collection<RestrictionStationVO> restrictedStations =
		(Collection<RestrictionStationVO>)restrictStations.get(8);
		if(restrictedStations.size()>0){
		for(RestrictionStationVO stationVO:restrictedStations){
			if(stationVO.getIsRestricted()){
			if(stationVO.getIsOrigin()){
			origin.append(stationVO.getStation());
			origin.append(", ");
		}
			else{
			destination.append(stationVO.getStation());
			destination.append(", ");
			}
		}
		}

			originDestination = ((origin.deleteCharAt(origin.length()-2).
			append("\n").append(destination))).toString();
			return (originDestination.substring(0,(originDestination.length()-2)));
		}
		return BLANK;
	}
}
