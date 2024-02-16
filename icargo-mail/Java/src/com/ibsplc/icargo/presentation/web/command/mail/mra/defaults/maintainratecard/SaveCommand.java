/*
 * SaveCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class SaveCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Save Command");
	private static final String CLASS_NAME = "Save Command";
	static final String RATECARD_RATE_NULL="mailtracking.mra.defaults.msg.err.atleastonerate";
	static final String RATECARDID_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.ratecardidnotnull";
	static final String RATECARDSTATUS_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.ratecardstatusnotnull";
	static final String RATECARDDESC_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.ratecarddescnotnull";
	static final String  FROMDATE_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.fromdatenotnull";
	static final String EMS_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.emsnotnull";
	static final String EXGRATE_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.exgratenotnull";
	static final String AIRMAIL_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.airmailnotnull";
	static final String CP_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.cpnotnull";
	static final String SAL_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.salnotnull";
	private static final String RATECARD_FROMDATE_MUST_NOT_BE_GREATER="mailtracking.mra.defaults.msg.err.ratecardfromdatenotgreater";
	private static final String RATELINE_FROMDATE_MUST_NOT_BE_GREATER="mailtracking.mra.defaults.msg.err.ratelinefromdatenotgreater";
	private static final String RATELINE_FROMDATE_MUST_NOT_BE_EARLIERTORATECARD="mailtracking.mra.defaults.msg.err.ratelinefromdatenotearliertoratecard";
	static final String RATECARD_INVALID_START_DATE="mailtracking.mra.defaults.msg.err.ratecardinvalidstartdate";
	static final String RATELINE_INVALID_START_DATE="mailtracking.mra.defaults.msg.err.ratelineinvalidstartdate";
	private static final String RATELINE_TODATE_MUST_NOT_BE_GREATER="mailtracking.mra.defaults.msg.err.ratelinetodategreater";
	static final String RATELINE_NULL="mailtracking.mra.defaults.msg.err.ratelinenull";
	static final String INVALID_STATUS="mailtracking.mra.defaults.msg.err.invalidstatus";
    static final String TODATE_MUST_NOT_BE_NULL="mailtracking.mra.defaults.msg.err.todatenotnull";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String DETAILS_FAILURE= "details_failure";
	private static final String SAVE_SUCCESSFULL="mailtracking.mra.defaults.msg.info.savesucess";
	private static final String RATELINE_EXISTS="mailtracking.mra.defaults.ratelinesexist";
	private static final String BLANK= "";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("save","execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	Collection<ErrorVO> finErrors = new ArrayList<ErrorVO>();
    	ErrorVO error=null;
    	int rateFlag=0;
    	LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	boolean hasChanged = false;
    	MaintainUPURateCardSession session=null;
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
   		
   	 /*For setting update flag in ratelinedetails*/
   		
 		ArrayList<RateLineVO> ratelinevos=null;
 		ArrayList<RateLineVO> chratelinevos=null;
   		RateCardVO listedratecard=null;
   		Double mdf=1.0;
   		ArrayList<RateLineVO> listedRates=null;
   	    ArrayList<RateLineVO> rates=null;
   	    RateCardVO ratecardvo=new RateCardVO();
   	    ratecardvo.setCompanyCode(companyCode);
   	    
   	    if(session.getRateCardDetails()!=null){
   	    	listedratecard=session.getRateCardDetails();
   	    }
   		if((session.getRateCardDetails()!=null)&&(!("new").equals(form.getScreenStatus()))){
   			
   			if(!RateCardVO.OPERATION_FLAG_INSERT.equals(ratecardvo.getOperationFlag())){
   			ratecardvo.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
   			/**
   			 * for opmistic locking mec'm
   			 */
//   			ratecardvo.setLastUpdateTime(listedratecard.getLastUpdateTime());
//   			ratecardvo.setLastUpdateUser(listedratecard.getLastUpdateUser());
   			}
   		}
   		 if(("new").equals(form.getScreenStatus()))
   		{   
   			ratecardvo=new RateCardVO();
   		    ratecardvo.setCompanyCode(companyCode);
   			
   			ratecardvo.setOperationFlag(RateCardVO.OPERATION_FLAG_INSERT);
   		}
   		
   		
   	   if(session.getRateCardDetails()!=null){
   		 if(session.getRateCardDetails().getRateLineVOss()!=null){
   		 listedRates=new ArrayList<RateLineVO>(listedratecard.getRateLineVOss());
   		}
   	   }
   		if(session.getRateLineDetails()!=null && session.getRateLineDetails().size()>0){
         rates=new ArrayList<RateLineVO>(session.getRateLineDetails());
   		}
   		
   		
   		/*for errorvos*/
   		
       if(!BLANK.equals(form.getRateCardId()) && form.getRateCardId().trim().length()>0){
    	   
    	   ratecardvo.setRateCardID(form.getRateCardId());
    	   
       }
       else{
			error=new ErrorVO(RATECARDID_MUST_NOT_BE_NULL);
			errors.add(error);
			}
       if(!BLANK.equals(form.getStatus()) && form.getStatus().trim().length()>0){
    	       ratecardvo.setRateCardStatus("N");
    	  
      			if(("CANCELLED").equals(form.getStatus())){
      				
      				ratecardvo.setRateCardStatus("C");
      			}
      			
      			if(("ACTIVE").equals(form.getStatus())){
      				
      				ratecardvo.setRateCardStatus("A");
      			}
      			 if(("INACTIVE").equals(form.getStatus())){
      			
      				ratecardvo.setRateCardStatus("I");
      			}
      			 if(("NEW").equals(form.getStatus())){
           			
       				ratecardvo.setRateCardStatus("N");
       			}
      			 
      			 //Commented as part of ICRD-106032
      			/*if(("EXPIRED").equals(form.getStatus())){
      			   error=new ErrorVO(INVALID_STATUS);
          		   errors.add(error);
      			}*/
      			
    	  
    		if(session.getRateCardDetails()!=null){
    			
    			if (listedratecard.getRateCardStatus().toUpperCase()!=null && listedratecard.getRateCardStatus().toUpperCase().trim().length()>0){
    				if(!(listedratecard.getRateCardStatus().toUpperCase().equals(form.getStatus().toUpperCase()))){
    					
    					if(!RateCardVO.OPERATION_FLAG_INSERT.equals(ratecardvo.getOperationFlag())){
    					ratecardvo.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
    				}
    			}
    			}
    		}
       }
       else{
			error=new ErrorVO(RATECARDSTATUS_MUST_NOT_BE_NULL);
			errors.add(error);
			}
       if(!BLANK.equals(form.getDescription()) && form.getDescription().trim().length()>0){
    	   ratecardvo.setRateCardDescription(form.getDescription());
    	   if(session.getRateCardDetails()!=null){
   			if (listedratecard.getRateCardDescription()!=null && listedratecard.getRateCardDescription().trim().length()>0){
   				if(!listedratecard.getRateCardDescription().equals(ratecardvo.getRateCardDescription())){
   					
   					if(!RateCardVO.OPERATION_FLAG_INSERT.equals(ratecardvo.getOperationFlag())){
   					ratecardvo.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
   				}
   			}
   			}
   		}
       }
       else{
			error=new ErrorVO(RATECARDDESC_MUST_NOT_BE_NULL);
			errors.add(error);
			}
       if(!BLANK.equals(form.getMialDistFactor()) && form.getMialDistFactor().trim().length()>0){
    	   ratecardvo.setMailDistanceFactor(Double.parseDouble(form.getMialDistFactor()));
    	   mdf=ratecardvo.getMailDistanceFactor();
    	   if(mdf==0.0){
    		   ratecardvo.setMailDistanceFactor(1.0);
    		   mdf=1.0;
    	   }
    	   
    	   log.log(Log.INFO, "mdf ", mdf);
       }
       else {
    	 
    	   ratecardvo.setMailDistanceFactor(1.0);
    	   
       }
    	   if(!BLANK.equals(form.getSvTkm())&& form.getSvTkm().trim().length()>0){
    		   ratecardvo.setCategoryTonKMRefOne(Double.parseDouble(form.getSvTkm()));
    		   if(Double.parseDouble(form.getSvTkm())==0.0){
    			   rateFlag++;
    		   }
    	   }
    	   else{
    		   ratecardvo.setCategoryTonKMRefOne(1.0);
    	   }
    	   if(!BLANK.equals(form.getSalTkm()) && form.getSalTkm().trim().length()>0){
    		   ratecardvo.setCategoryTonKMRefTwo(Double.parseDouble(form.getSalTkm()));
    		   if(Double.parseDouble(form.getSalTkm())==0.0){
    			   rateFlag++;
    		   }
    	   }
    	   else{
    		   ratecardvo.setCategoryTonKMRefTwo(0.0);
    	   }
    	   if(!BLANK.equals(form.getAirmialTkm()) && form.getAirmialTkm().trim().length()>0){
    		   ratecardvo.setCategoryTonKMRefThree(Double.parseDouble(form.getAirmialTkm()));
    		   if(Double.parseDouble(form.getAirmialTkm())==0.0){
    			   rateFlag++;
    		   }
    	   }
    	   else{
    		   ratecardvo.setCategoryTonKMRefThree(0.0);
    	   }
    	   if(rateFlag==3){
    		   error=new ErrorVO(RATECARD_RATE_NULL);
   			   errors.add(error);
    	   }
      fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
       LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
       log.log(Log.INFO, "sys date ", currentDate);
		if(!BLANK.equals(form.getValidFrom()) && form.getValidFrom().trim().length()>0){
			log.log(Log.INFO,"from date not null");
         fromDate.setDate(form.getValidFrom());
         log.log(Log.INFO, "frm date ", fromDate);
         //Commented as part of ICRD-106032
	/*	if(("N").equals(ratecardvo.getRateCardStatus())){
        if(fromDate.before(currentDate)){
        		 error=new ErrorVO(RATECARD_INVALID_START_DATE);
     			 errors.add(error);
         	}
        	
         }*/
         
         ratecardvo.setValidityStartDate(fromDate);
    	 if(session.getRateCardDetails()!=null){
    			if (listedratecard.getValidityStartDate()!=null){
    				if(listedratecard.getValidityStartDate()!= ratecardvo.getValidityStartDate()){
    					if(!RateCardVO.OPERATION_FLAG_INSERT.equals(ratecardvo.getOperationFlag())){
    					ratecardvo.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
    					}
    					
    				}
    			}
    		}
         
         }
		
		else{
			log.log(Log.INFO,"from date null");
			error=new ErrorVO(FROMDATE_MUST_NOT_BE_NULL);
			errors.add(error);
			}
		 toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
 		 if(!BLANK.equals(form.getValidTo()) && form.getValidTo().trim().length()>0){ 
 			 toDate.setDate(form.getValidTo());
 		     ratecardvo.setValidityEndDate(toDate);
 		    if(session.getRateCardDetails()!=null){
    			if (listedratecard.getValidityEndDate()!=null){
    				if(listedratecard.getValidityEndDate()!= ratecardvo.getValidityEndDate()){
    					if(!RateCardVO.OPERATION_FLAG_INSERT.equals(ratecardvo.getOperationFlag())){
    					ratecardvo.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
    					}
    					
    				}
    			}
    		}
     
 		     
 		 }
 		 else{
 			error=new ErrorVO(TODATE_MUST_NOT_BE_NULL);
 			errors.add(error);
 		 }
 		  if ((ratecardvo.getValidityStartDate()!= null) && (!("").equals(ratecardvo.getValidityStartDate().toString()))
  			&& (ratecardvo.getValidityEndDate()!= null) && (!("").equals(ratecardvo.getValidityEndDate().toString()))){
         if(ratecardvo.getValidityStartDate().isGreaterThan(ratecardvo.getValidityEndDate())){
 				error=new ErrorVO(RATECARD_FROMDATE_MUST_NOT_BE_GREATER);
 		   		errors.add(error);
          }
          }
 		  int flag=0;
 		  if(rates!=null && rates.size()>0){
 			  int rateSize=rates.size();
 			  for(int i=0;i<rateSize;i++){
 				  if(rates.get(i).getOperationFlag()!=null && ("D").equals(rates.get(i).getOperationFlag())){
 					 flag++; 
 				  }
 			  }
 			  if(flag==rates.size()){
 				 error=new ErrorVO(RATELINE_NULL);
 	 			 errors.add(error);
 			  }
 		  }
 		  else{
 			 error=new ErrorVO(RATELINE_NULL);
 			errors.add(error);
 		  }
 		  
 		 	 
	
 		 if(session.getRateLineDetails()!=null && session.getRateLineDetails().size()>0){
 			 ratecardvo.setRateLineVOss(null);
 			 chratelinevos=new ArrayList<RateLineVO>();
    		 ratelinevos=new ArrayList<RateLineVO>(session.getRateLineDetails()); 
    		 int size=ratelinevos.size();
 			 LocalDate frmDate[]=new LocalDate[size]; 
		  	 LocalDate toDat[]=new LocalDate[size];
    		   for(int i=0;i<size;i++) {
    			   hasChanged=false;
    			  RateLineVO ratelinevo=new RateLineVO();
    			  
    			  if(ratelinevos.get(i) != null){
    				  ratelinevo=ratelinevos.get(i);
    			  }
    			  ratelinevo.setRateCardID(ratecardvo.getRateCardID());
    			  /**
    			   * 
    			   */
    			  ratelinevo.setLastUpdateTime(ratelinevos.get(i).getLastUpdateTime());
    			  ratelinevo.setLastUpdateUser(ratelinevos.get(i).getLastUpdateUser());
    			   log.log(Log.FINE, "Lastupdated time in ratelineVO ", ratelinevo.getLastUpdateTime());
				Double iataKm=ratelinevo.getIataKilometre();
    			  if(form.getStatus().equals(("New").toUpperCase())||
    					  form.getStatus().equals(("Active").toUpperCase())||
    					  form.getStatus().equals(("Expired").toUpperCase())){
    				  hasChanged = true;
    				   if(ratelinevo.getOperationFlag()==null){
    					   if(form.getOperationFlag() != null && form.getOperationFlag().length > 0
    							   && i <= form.getOperationFlag().length &&
    						   (!ratelinevos.get(i).getValidityStartDate().toDisplayDateOnlyFormat().equalsIgnoreCase(form.getValidFromRateLine()[i])
    								   || !ratelinevos.get(i).getValidityEndDate().toDisplayDateOnlyFormat().equalsIgnoreCase(form.getValidToRateLine()[i]))){
    				    ratelinevo.setOperationFlag(RateLineVO.OPERATION_FLAG_UPDATE);
    					   }
    				     }
    				   if("N".equals(ratelinevo.getRatelineStatus())){
    				   	ratelinevo.setMailKilometre(iataKm*mdf);
    				    ratelinevo.setRateInSDRForCategoryRefOne(iataKm*mdf*((ratecardvo.getCategoryTonKMRefOne())/1000));
    					ratelinevo.setRateInSDRForCategoryRefTwo(iataKm*mdf*((ratecardvo.getCategoryTonKMRefTwo())/1000));
    					ratelinevo.setRateInSDRForCategoryRefThree(iataKm*mdf*((ratecardvo.getCategoryTonKMRefThree())/1000));
    				   }
    			  }  
    			  if(form.getValidFromRateLine()!=null && form.getValidFromRateLine().length>0){
    				 if((!BLANK.equals(form.getValidFromRateLine()[i]))&&(form.getValidFromRateLine()[i].trim().length()>0)){
    					 log.log(Log.INFO,"RateLine From Date not null");
    					frmDate[i]=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    			  frmDate[i].setDate(form.getValidFromRateLine()[i]);
    			  log.log(Log.INFO, "rateline from dates set!!!!!!", frmDate, i);
    			   }
    				 else{
    					 log.log(Log.INFO,"RateLine From Date null");
    					 frmDate[i]=ratecardvo.getValidityStartDate();
    				 }
    			 }
    			
    			  if(form.getValidToRateLine()!=null && form.getValidToRateLine().length>0){
    				 if((!BLANK.equals(form.getValidToRateLine()[i]))&&(form.getValidToRateLine()[i].trim().length()>0)){
    					 toDat[i]=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    			  toDat[i].setDate(form.getValidToRateLine()[i]);
    			   }
    				 else{
    					 toDat[i] = null;
    					 toDat[i]=ratecardvo.getValidityEndDate();
    				 }
    			 }
    			  if(frmDate[i]!=null && toDat[i]!=null && !("D").equals(ratelinevo.getOperationFlag())){
    				 
    				  log.log(Log.INFO,"inside ratelinedates not null loop************");
    				  log.log(Log.INFO, "frm date in loop************", frmDate,
							i);
					log.log(Log.INFO,
							"ratelinevo frm date in loop************",
							ratelinevo.getValidityStartDate());
					//Commented as part of ICRD-106032
						/*if(("N").equals(ratelinevo.getRatelineStatus())){
    						  log.log(Log.INFO,"inside new status-----------");
    					 if(frmDate[i].before(currentDate)){
    						 log.log(Log.INFO, "ratelinedates ", frmDate, i);
								ratelinevo.setValidityStartDate(frmDate[i]);
    	      		        		error=new ErrorVO(RATELINE_INVALID_START_DATE);
    	      		    			errors.add(error);
    					 		}
    					  }*/
    					  log.log(Log.INFO, "ratecard before outside", ratecardvo.getValidityStartDate());
						log.log(Log.INFO, "ratelinedates outside", frmDate, i);
						if(ratecardvo.getValidityStartDate()!=null &&
								("N").equals(ratelinevo.getRatelineStatus())){
    					  if(frmDate[i].before(ratecardvo.getValidityStartDate())){
    						  ratelinevo.setValidityStartDate(frmDate[i]);
    						  log.log(Log.INFO, "ratecard before", ratecardvo.getValidityStartDate());
							log.log(Log.INFO, "ratelinedates before", frmDate,
									i);
							error=new ErrorVO(RATELINE_FROMDATE_MUST_NOT_BE_EARLIERTORATECARD);
      		    			errors.add(error);
      		                }
    					  else{
    						  log.log(Log.INFO, "ratelinedates ", ratecardvo.getValidityStartDate());
							log.log(Log.INFO, "ratelinedates ", frmDate, i);
							ratelinevo.setValidityStartDate(frmDate[i]);
    					  }
      				  }
      		 		 
      			         if(frmDate[i].isGreaterThan(toDat[i]) &&
      			        		("N").equals(ratelinevo.getRatelineStatus())){
      			        	    ratelinevo.setValidityEndDate(toDat[i]);
      			        	    log.log(Log.INFO,"ratelinefrom date greater");
      			 				error=new ErrorVO(RATELINE_FROMDATE_MUST_NOT_BE_GREATER);
      			 		   		errors.add(error);
      			          }
      			         if(ratecardvo.getValidityEndDate()!=null){
      			          if(toDat[i].isGreaterThan(ratecardvo.getValidityEndDate())&&
      			        		("N").equals(ratelinevo.getRatelineStatus())){
      			        	ratelinevo.setValidityEndDate(toDat[i]);
      			        	 log.log(Log.INFO,"ratelineto date greater to ratecard's");
      			        	error=new ErrorVO(RATELINE_TODATE_MUST_NOT_BE_GREATER);
  			 		   		errors.add(error);
      			         }
      			         else{ 
	  				      ratelinevo.setValidityEndDate(toDat[i]);
      			         }
      			         }
  				        
      			        if((!frmDate[i].equals(ratelinevo.getValidityStartDate()))||(!toDat[i].equals(ratelinevo.getValidityEndDate()))){
      					  if (ratelinevo.getOperationFlag() == null) {
  							hasChanged = true;
  							log.log(Log.INFO,"UPDATE FLAG SET");
  							ratelinevo
  									.setOperationFlag(RateLineVO.OPERATION_FLAG_UPDATE);
  							log.log(Log.INFO, "UPDATE FLAG SET", ratelinevo
  									.getOperationFlag());
							if (!RateCardVO.OPERATION_FLAG_INSERT
  									.equals(ratecardvo.getOperationFlag())) {
  								ratecardvo
  										.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
  								
  							}
  							
  						}
      			      }
    				 
    				  
    			  
    			 }
    			
    			  chratelinevos.add(i,ratelinevo);
    			  log.log(Log.INFO, "UPDATE FLAG SET", ratelinevo
							.getOperationFlag());
				ratelinevo=null;
    			  log.log(Log.INFO, "new ratelinevos ", chratelinevos);
    		   }
    		   
		      int s=chratelinevos.size();
		      
			  Page<RateLineVO> oldratespage=new Page<RateLineVO>(chratelinevos,1,0,s,0,0,false);
				
			  session.setRateLineDetails(oldratespage);
			  	
			
			  ratecardvo.setRateLineVOss(oldratespage);
			  /**
		 	  * for optimisitic locking
		 	  */
		 		 ratecardvo.setLastUpdateTime(session.getRateCardDetails().getLastUpdateTime());
		 		 ratecardvo.setLastUpdateUser(session.getRateCardDetails().getLastUpdateUser());
			  log.log(Log.INFO, "ratecardvo***** ", ratecardvo);
			ratelinevos=new ArrayList<RateLineVO>(ratecardvo.getRateLineVOss());
			  
    	 }
 		 
 		 
 		 else {
 			 ratecardvo.setNumberOfRateLines(0);
 			
 		 }
    	
 		  
 		 if(errors!=null && errors.size()>0){
	          	 log.log(Log.FINE,"!!!inside errors!= null");
	   				invocationContext.addAllError(errors);
	   				//session.removeRateLineDetails();
	   				invocationContext.target=DETAILS_FAILURE;
	   				return;
	   		}
 		 
 		 // Delegate call
 		 	try{	
 			 new MailTrackingMRADelegate().saveRateCard(ratecardvo);
 			}
 		catch (BusinessDelegateException businessDelegateException) {
 			
 			
			errors=handleDelegateException(businessDelegateException);
 			
			errors = handleErrorMessage(errors);	
			//handles error data from server
			invocationContext.addAllError(errors);
			invocationContext.target = DETAILS_FAILURE;
			return;
		}
 		
 		 
		    form.setRateCardId("");
 		form.setStatus("");
 		form.setDescription("");
 		form.setValidFrom("");
 		form.setValidTo("");
 		form.setMialDistFactor("");
 		form.setSvTkm("");
 		form.setSalTkm("");
 		form.setCpTkm("");
 		form.setAirmialTkm("");
 		form.setExchangeRate("");
	    session.removeRateLineDetails();
	    form.setScreenStatus("screenload");
	    error=new ErrorVO(SAVE_SUCCESSFULL);
	    errors.add(error);
	    if(errors!=null && errors.size()>0){
          	 log.log(Log.FINE,"!!!inside errors!= null after save");
   				invocationContext.addAllError(errors);
   		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("SaveCommand", "execute");
 
   }      
 		/**
 		 * Creates errorvo collection to be displayed in screen
 		 * 
 		 * @param errors
 		 * @return finErrors 		 
 		 * */
 		private Collection<ErrorVO> handleErrorMessage(Collection<ErrorVO> errors){
 			
 			log.entering(CLASS_NAME,"handleErrorMessage");
 			
 			Collection<ErrorVO> finErrors = new ArrayList<ErrorVO>();
 			
 			if(errors != null && errors.size() > 0){
 				
 				for(ErrorVO error:errors){
 					
 					RateLineErrorVO[] rateLineErrorVos = 
 						new RateLineErrorVO[error.getErrorData() == null ? 0: error.getErrorData().length];
 					
 					if(RATELINE_EXISTS.equals(error.getErrorCode())){
 						
 						System.arraycopy(error.getErrorData(),0,
 								rateLineErrorVos,0,error.getErrorData().length);
 						
 						for(RateLineErrorVO rateLineErrorVO :rateLineErrorVos){
 							
 							log.log(Log.INFO, "printing the errorVOS>>>>>>>>>",
									rateLineErrorVO);
							finErrors.add(new ErrorVO(RATELINE_EXISTS,
 									new String[]{rateLineErrorVO.getNewRateCardID(),
 									rateLineErrorVO.getCurrentRateCardID(),
 									rateLineErrorVO.getOrigin(),
 									rateLineErrorVO.getDestination(),
 									rateLineErrorVO.getCurrentValidityStartDate().toDisplayFormat(),
 									rateLineErrorVO.getCurrentValidityEndDate().toDisplayFormat()}
 							));
 						}
 					}
 				}
 			}
 				
 			log.exiting(CLASS_NAME,"handleErrorMessage");
 			return finErrors;
 		}
    





}
