
	/*
	 * IrregularityDetailsMultiMapper.java Created on Sep 29, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.HashSet;
	import java.util.List;

	import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityDetailsVO;
    import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
    import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;
    import com.ibsplc.icargo.framework.util.currency.CurrencyException;
    import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
    import com.ibsplc.icargo.framework.util.currency.Money;
	import com.ibsplc.icargo.framework.util.time.LocalDate;
	import com.ibsplc.icargo.framework.util.time.Location;
	import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


	/**
	 * @author A-3229
	 *
	 */
	public class IrregularityDetailsMultiMapper implements MultiMapper {
		
	
		 private Log log = LogFactory.getLogger("MRA DEFAULTS");
		  
		  
		  private static final String CLASS_NAME  = "IrregularityDetailsMultiMapper";
		  
		  private MRAIrregularityFilterVO irregularityFilterVO;
	
			
			/**
		    *
		    * @param irregularityFilterVO
		    */
		   public IrregularityDetailsMultiMapper(MRAIrregularityFilterVO irregularityFilterVO) {
		       this.irregularityFilterVO = irregularityFilterVO;
		   }
			
	
			/**
			 * @param rs
			 * @throws SQLException
			 */
			public List<MRAIrregularityVO> map(ResultSet rs) 
		    throws SQLException {
				
				log.entering( CLASS_NAME, "mapper" );
			  	List<MRAIrregularityVO> irregularityVOs
			  						= new ArrayList<MRAIrregularityVO>();
			  	//Collection<MRAIrregularityDetailsVO> detailsVOs=new ArrayList<MRAIrregularityDetailsVO>();
			  	MRAIrregularityVO irregularityVO = null;
			  	Collection<String> irregularities = new HashSet<String>();	
				String irregularityKey = null;

				try {
					String currency = irregularityFilterVO.getBaseCurrency();
	
					Money totalFreightCharges = CurrencyHelper.getMoney(currency);
					
					totalFreightCharges.setAmount(0.0);
						
					Money total = CurrencyHelper.getMoney(currency);
					
					total.setAmount(0.0);
						
				
		  		
				
				  	while(rs.next()){
				  		
				  		irregularityKey = new StringBuilder().append(rs.getString("BLGBAS")).append(rs.getString("CMPCOD"))
			        	.append(rs.getString("CSGDOCNUM"))
			        	.append(rs.getInt("CSGSEQNUM")).append(rs.getString("POACOD")).toString();
				
					
				  		
					  		if(!irregularities.contains(irregularityKey)){
					  		 	log.log(log.INFO,"Inside FIRST time");
					  			irregularityVO =new MRAIrregularityVO();
					  			MRAIrregularityDetailsVO irregularityDetailsVO = new MRAIrregularityDetailsVO();
					  			irregularityVO.setFlightDetails(new ArrayList<MRAIrregularityDetailsVO>());
					  			irregularityDetailsVO.setFlightNumber(rs.getString("ORGFLTNUM"));
				            	irregularityDetailsVO.setRescheduledFlightNumber(rs.getString("REVFLTNUM"));
				            	irregularityDetailsVO.setOffloadedStation(rs.getString("OFLSTN"));
				            	irregularityDetailsVO.setOffloadedWeight(rs.getInt("OFLWGT"));
				            	irregularityDetailsVO.setWeight(rs.getDouble("UPDWGT"));
				            	irregularityDetailsVO.setDsn(rs.getString("DSN"));
				            	
				            	if(rs.getString("POL")!=null && rs.getString("POU")!=null){
					            	String origin=rs.getString("POL");
					            	String destination=rs.getString("POU");
					            	StringBuilder route=new StringBuilder().append(origin).append("-").append(destination);
					            	irregularityDetailsVO.setRoute(route.toString());
					            	}
				            	irregularityDetailsVO.setIrregularityStatus(rs.getString("IRPSTA"));
				            	
				            	if(rs.getTimestamp("ORGFLTDAT")!=null){
				            		irregularityDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("ORGFLTDAT")));
								}
							  	
				              	if(rs.getTimestamp("REVFLTDAT")!=null){
							  		irregularityDetailsVO.setRescheduledFlightDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("REVFLTDAT")));
								}
							  	
							  	if(rs.getTimestamp("IRPDAT")!=null){
							  		irregularityDetailsVO.setOffLoadedDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("IRPDAT")));
								}
							  	
							  	
							  	irregularityVO.getFlightDetails().add(irregularityDetailsVO);
							  	log.log(Log.INFO, "detailsVOs-------",
										irregularityVO.getFlightDetails());
								irregularityVO.setDsnNumber(rs.getString("DSN"));
							  	irregularityVO.setCurrency(rs.getString("CTRCURCOD"));
					  			irregularityVO.setFlightCarrierId(rs.getInt("ORGFLTCARIDR"));
					  			irregularityVO.setFlightSequenceNumber(rs.getInt("ORGFLTNUM"));
					  			irregularityVO.setRescheduledFlightCarrierId(rs.getInt("REVFLTCARIDR"));
					  			irregularityVO.setRescheduledFlightSequenceNumber(rs.getInt("REVFLTNUM"));
					  			
					  			irregularityVO.setOffloadedPieces(rs.getInt("OFLPCS"));
					  			irregularityVO.setModeOfPayment(rs.getString("MOP"));
							  	
					  			irregularityVO.setAwbOrigin(rs.getString("ORGEXGOFC"));
					  			irregularityVO.setAwbDestination(rs.getString("DSTEXGOFC"));
							  	
							 
									Money freightCharge = CurrencyHelper.getMoney(currency);
									freightCharge.setAmount(rs.getDouble("UPDWGTCHG"));
									irregularityVO.setFrieghtCharges(freightCharge);
								    
								    
								    
								    Money totalCharges = CurrencyHelper.getMoney(currency);
								    totalCharges.plusEquals(irregularityVO.getFrieghtCharges());
								    irregularityVO.setTotalCharges(totalCharges);
				
									
									
								
							  	if(rs.getTimestamp("RCVDAT")!=null){
							  		irregularityVO.setExecutionDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("RCVDAT")));
								}
					  		
						 
					  			if(irregularityVO.getFrieghtCharges()!=null){
					  			totalFreightCharges = totalFreightCharges.plusEquals(irregularityVO.getFrieghtCharges());
						  		}

					  			if(irregularityVO.getTotalCharges()!=null){
					  			total = total.plusEquals(irregularityVO.getTotalCharges());
						  		}
						  		
					  		
					  			
					  			//irregularityVO.setFlightDetails(detailsVOs);
					  			irregularityVOs.add(irregularityVO);
					  			irregularities.add(irregularityKey);
					  		 }
					  			
					  			else{
					  				log.log(log.INFO,"Inside SECOND time");
					  				MRAIrregularityDetailsVO irregularityDetailsVO = new MRAIrregularityDetailsVO();
					  		    irregularityDetailsVO.setFlightNumber(rs.getString("ORGFLTNUM"));
				            	irregularityDetailsVO.setRescheduledFlightNumber(rs.getString("REVFLTNUM"));
				            	irregularityDetailsVO.setOffloadedStation(rs.getString("OFLSTN"));
				            	irregularityDetailsVO.setOffloadedWeight(rs.getInt("OFLWGT"));
				            	irregularityDetailsVO.setWeight(rs.getDouble("UPDWGT"));
				            	if(rs.getString("POL")!=null && rs.getString("POU")!=null){
				            	String origin=rs.getString("POL");
				            	String destination=rs.getString("POU");
				            	StringBuilder route=new StringBuilder().append(origin).append("-").append(destination);
				            	irregularityDetailsVO.setRoute(route.toString());
				            	}
				            	
				            
				            	irregularityDetailsVO.setDsn(rs.getString("DSN"));
				            	irregularityDetailsVO.setIrregularityStatus(rs.getString("IRPSTA"));
				            	
				            	if(rs.getTimestamp("ORGFLTDAT")!=null){
				            		irregularityDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("ORGFLTDAT")));
								}
							  	
							  	
							  	if(rs.getTimestamp("REVFLTDAT")!=null){
							  		irregularityDetailsVO.setRescheduledFlightDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("REVFLTDAT")));
								}
							  	
							  	if(rs.getTimestamp("IRPDAT")!=null){
							  		irregularityDetailsVO.setOffLoadedDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("IRPDAT")));
								}
								//irregularityReportVOs.add(irregularityVO);
								
					  			irregularityVO.setCompanyCode(rs.getString("CMPCOD"));  
					  		
					  		
					  			if(rs.getTimestamp("RCVDAT")!=null){
					  				irregularityVO.setExecutionDate(new LocalDate(LocalDate.NO_STATION,
											Location.NONE,rs.getTimestamp("RCVDAT")));
								}
					  		//	detailsVOs.add(irregularityDetailsVO);
					  			irregularityVO.getFlightDetails().add(irregularityDetailsVO);
					  			
					  			}
					  		
					  		 log.log(Log.FINE, "totalFreightCharges->",
									totalFreightCharges);
							log.log(Log.FINE, "total->", total);
							irregularityVO.setTotalFreightCharges(totalFreightCharges);
				  			 irregularityVO.setTotal(total);
				  			 log.log(Log.FINE, "totalFreightCharges->",
									irregularityVO);
				  	}
					
				} catch (CurrencyException e) {
					
					e.getErrorCode();
				}
				  
			  		
			  		
				
					
				  	log.exiting( CLASS_NAME, "map" );
				  	
				  	log.log(Log.FINE, "IrregularityDetailsMultiMapper->",
							irregularityVOs);
				return irregularityVOs;
			  	
			  	
			}
	}
