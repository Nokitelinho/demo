
	/*
	 * ListCommand.java Created on Sep 03, 2008
	*
	* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	*
	* This software is the proprietary information of IBS Software Services (P) Ltd.
	* Use is subject to license terms.
	*/
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchrouting;

	import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	* Command class for listing
	*
	* Revision History
	*
	* Version      Date           Author          		    Description
	*
	*  0.1         Sep 03, 2008    A-3229		 		   Initial draft
	*/
	public class ListCommand extends BaseCommand {
		/**
		 * Logger and the file name
		 */

		private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");


		private static final String CLASS_NAME = "ListCommand";

		private static final String MODULE_NAME = "mailtracking.mra.defaults";

		private static final String SCREEN_ID = "mailtracking.mra.defaults.despatchrouting";

		private static final String SCREENID_DSNPOPUP = "mailtracking.mra.defaults.dsnselectpopup";

		private static final String LIST_SUCCESS = "list_success";

		private static final String LIST_FAILURE = "list_failure";

		private static final String WEIGHT_UNIT_ONETIME="mail.mra.defaults.weightunit";
		//SEG_SOURCE Added by Manish for IASCB-40970
		private static final String SEG_SOURCE = "mailtracking.mra.routingsegmentsource";
		private static final String VOID_MAILBAGS =	"mail.mra.defaults.despatchrouting.msg.err.voidmailbags";

		/**
		 * Execute method
		 *
		 * @param invocationContext InvocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
												throws CommandInvocationException {
			log.entering(CLASS_NAME, "execute");

			DSNRoutingSession  dsnRoutingSession =
				(DSNRoutingSession)getScreenSession(MODULE_NAME, SCREEN_ID);
			DSNRoutingFilterVO dsnRoutingFilterVO=null;
			DespatchRoutingForm despatchRoutingForm=(DespatchRoutingForm)invocationContext.screenModel;
			LogonAttributes logonAttributes=getApplicationSession().getLogonVO();

			//Added for Unit Component
			UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
			dsnRoutingSession.setWeightRoundingVO(unitRoundingVO);
			setUnitComponent(logonAttributes.getStationCode(),dsnRoutingSession);

			if(dsnRoutingSession.getDSNRoutingFilterVO()!=null && dsnRoutingSession.getDSNRoutingFilterVO().getDsnDate()!=null){
				dsnRoutingFilterVO=dsnRoutingSession.getDSNRoutingFilterVO();
				despatchRoutingForm.setDsn(dsnRoutingFilterVO.getBillingBasis());
				despatchRoutingForm.setDsnDate(dsnRoutingFilterVO.getDsnDate().toDisplayDateOnlyFormat());
				log.log(Log.INFO, "dsnRoutingFilterVO....", dsnRoutingFilterVO);
				despatchRoutingForm.setCloseFlag("fromlistprorationexception");
				log.log(Log.INFO, "CloseFlag.....", despatchRoutingForm.getCloseFlag());
				//Added as part of ICRD-341226 starts
				DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREENID_DSNPOPUP);
				DSNPopUpVO popUpVO=popUpSession.getSelectedDespatchDetails();
				if(popUpVO.getMailSource() != null){
					despatchRoutingForm.setExactMailSource(popUpVO.getMailSource());
					}
				
				if(popUpVO.getTransferAirline()!=null ){
					despatchRoutingForm.setTransferAirline(popUpVO.getTransferAirline());	
				}else if(popUpVO.getTransferPA()!=null){
					despatchRoutingForm.setTransferPA(popUpVO.getTransferPA());
				}
				
				
				//Added as part of ICRD-341226 ends
			}else{
			 dsnRoutingFilterVO = new DSNRoutingFilterVO();
			DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREENID_DSNPOPUP);
			SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();


					log.log(Log.INFO, "POPUPSESSION------------------",
							popUpSession.getSelectedDespatchDetails());
					DSNPopUpVO popUpVO=popUpSession.getSelectedDespatchDetails();
					log.log(Log.INFO, "inside list command popupvo ", popUpVO);
					if(popUpVO!=null){
						despatchRoutingForm.setDsn(popUpVO.getBlgBasis());
						despatchRoutingForm.setDsnDate(popUpVO.getDsnDate());
						despatchRoutingForm.setBillingBasis(popUpVO.getBlgBasis());
						despatchRoutingForm.setCsgDocumentNumber(popUpVO.getCsgdocnum());
						despatchRoutingForm.setCsgSequenceNumber(String.valueOf(popUpVO.getCsgseqnum()));
						despatchRoutingForm.setPoaCode(popUpVO.getPoaCode());
						despatchRoutingForm.setCompanyCode(popUpVO.getCompanyCode());
						//Added by A-7794 as part of ICRD-232299
						if(popUpVO.getMailSource() != null){
						despatchRoutingForm.setExactMailSource(popUpVO.getMailSource());
						}else{
							despatchRoutingForm.setExactMailSource("TEMP");
						}
						
						if(popUpVO.getTransferAirline()!=null ){
							despatchRoutingForm.setTransferAirline(popUpVO.getTransferAirline());	
						}
						if(popUpVO.getTransferPA()!=null){
							despatchRoutingForm.setTransferPA(popUpVO.getTransferPA());
						}

					}

			    	 Collection<String> oneTimeList = new ArrayList();
			    	 oneTimeList.add("cra.proration.blockspacetype");
			    	 oneTimeList.add("mail.mra.defaults.mailsource");
			    	 oneTimeList.add(SEG_SOURCE);//Added by Manish for IASCB-40970
			    	 try
			    	    {
			    	      hashMap = defaultsDelegate.findOneTimeValues(popUpVO.getCompanyCode(), 
			    	        oneTimeList);
			    	    }
			    	    catch (BusinessDelegateException localBusinessDelegateException3)
			    	    {
			    	      this.log.log(7, "onetime fetch exception");
			    	 }
			    	 
				       
			    	Collection<OneTimeVO> blockSpaceTypes = (Collection<OneTimeVO>)hashMap.get("cra.proration.blockspacetype");
			    	dsnRoutingSession.setBlockSpaceTypes((ArrayList<OneTimeVO>)blockSpaceTypes);
			    	Collection<OneTimeVO> mailSources = (Collection<OneTimeVO>)hashMap.get("mail.mra.defaults.mailsource");
			        dsnRoutingSession.setMailSources((ArrayList<OneTimeVO>)mailSources);

					//To set values for filtervo
					dsnRoutingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

					if(!("".equals(despatchRoutingForm.getBillingBasis()))){
						dsnRoutingFilterVO.setBillingBasis(despatchRoutingForm.getBillingBasis());
					}
					if(!("".equals(despatchRoutingForm.getCsgDocumentNumber()))){
						dsnRoutingFilterVO.setCsgDocumentNumber(despatchRoutingForm.getCsgDocumentNumber());
					}

					if(!("".equals(despatchRoutingForm.getCsgSequenceNumber()))&& despatchRoutingForm.getCsgSequenceNumber()!= null){
						dsnRoutingFilterVO.setCsgSequenceNumber(Integer.parseInt(despatchRoutingForm.getCsgSequenceNumber()));
					}

					if(!("".equals(despatchRoutingForm.getPoaCode()))){
						dsnRoutingFilterVO.setPoaCode(despatchRoutingForm.getPoaCode());
					}

			}
			if(despatchRoutingForm.getDsnDate()!=null && despatchRoutingForm.getDsnDate().trim().length()>0) {
				LocalDate dsnDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				dsnDate.setDate(despatchRoutingForm.getDsnDate());
				dsnRoutingFilterVO.setDsnDate(dsnDate);
			}

					//server call
			        Collection<DSNRoutingVO> dsnRoutingVOs = null;
			        try{
			        	MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
				    		dsnRoutingVOs = mailTrackingMRADelegate.findDSNRoutingDetails(dsnRoutingFilterVO);
							log.log(Log.FINE,
									"dsnRoutingVOs from Server is----->",
									dsnRoutingVOs);

						}catch(BusinessDelegateException businessDelegateException){
					    		log.log(Log.FINE,"inside try...caught businessDelegateException");
					        	businessDelegateException.getMessage();
					        	handleDelegateException(businessDelegateException);
						}
			        
			        
			        
			        Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());
			        
			        ArrayList<DSNRoutingVO> dsnRtgVOs = new ArrayList<DSNRoutingVO>();
			        if(dsnRoutingVOs!=null && dsnRoutingVOs.size()>0){
						int k =0;
			        	if(dsnRoutingVOs!=null && dsnRoutingVOs.size()>0){
							dsnRtgVOs=(ArrayList<DSNRoutingVO>)dsnRoutingVOs;
							
							while (dsnRtgVOs.size() > k) {
							if (dsnRtgVOs.get(k).getDisplayWgtUnit() != null  && oneTimeValues != null && !oneTimeValues.isEmpty() && oneTimeValues.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeValues.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
								for (OneTimeVO oneTimeVO : oneTimeValues.get(WEIGHT_UNIT_ONETIME)) {
									if (oneTimeVO.getFieldValue().equals(dsnRtgVOs.get(k).getDisplayWgtUnit()) ) {																								
										dsnRtgVOs.get(k).setDisplayWgtUnit(oneTimeVO.getFieldDescription());
										despatchRoutingForm.setDiplayWgtUnit(oneTimeVO.getFieldDescription());
									}
								}
								//Added by Manish for IASCB-40970 start
								if(oneTimeValues != null && oneTimeValues.get(SEG_SOURCE) != null && !oneTimeValues.get(SEG_SOURCE).isEmpty()){
									for (OneTimeVO oneTimeVO : oneTimeValues.get(SEG_SOURCE)) {
										if (dsnRtgVOs.get(k).getSource() != null && oneTimeVO.getFieldValue().equals(dsnRtgVOs.get(k).getSource()) ) {																								
											dsnRtgVOs.get(k).setSource(oneTimeVO.getFieldDescription());
										}else if(dsnRtgVOs.get(k).getSource() == null){
											dsnRtgVOs.get(k).setSource(" ");
										}
									}
								}
								//Added by Manish for IASCB-40970 end
							}
							k++;							
							}
						}
						
						
					}

						boolean isRoutAdjReq = false;
						if(dsnRoutingVOs!=null && !dsnRoutingVOs.isEmpty()){
							Collection<ErrorVO> errors = null;	
							String orgCty="";
							String dstCty ="";
							for(DSNRoutingVO dsnVO: dsnRoutingVOs){
								if(!orgCty.equals(dsnVO.getPol()) && !dstCty.equals(dsnVO.getPou())){
									orgCty = dsnVO.getPol();
									dstCty = dsnVO.getPou();
								}else{
									isRoutAdjReq = true;
									break;
								}
							
								if(MRAConstantsVO.VOID.equals(dsnVO.getBillingStatus()))
								{
							errors = new ArrayList<ErrorVO>();
							ErrorVO errorVO = new ErrorVO(VOID_MAILBAGS);
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							dsnRoutingSession.removeDSNRoutingVOs();
							errors.add(errorVO);
							invocationContext.addAllError(errors);
							invocationContext.target = LIST_FAILURE;
							return;
								}
								
								
							}
						}
						if(isRoutAdjReq)
							{
							checkForRouteAdjustment(dsnRoutingVOs);
							}
						dsnRoutingSession.setDSNRoutingFilterVO(dsnRoutingFilterVO);
						dsnRoutingSession.setDSNRoutingVOs(dsnRoutingVOs);

						/*
						 * Validate for client errors. The method
						 * will check for mandatory fields
						 */
						Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
						errors = validateForm(despatchRoutingForm);

						if(dsnRoutingVOs == null ||dsnRoutingVOs.size() == 0){
								log.log(Log.FINE,"!!!inside resultList== null");
								ErrorVO errorVO = new ErrorVO(
										"mailtracking.mra.defaults.despatchrouting.msg.err.noroutingdetails");
								errorVO.setErrorDisplayType(ERROR);
								errors.add(errorVO);
								//removeFormValues(despatchRoutingForm);
								dsnRoutingSession.removeDSNRoutingVOs();
								despatchRoutingForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);

						}

						if(errors != null && errors.size() > 0){
							log.log(Log.FINE,"!!!inside errors!= null");
							//removeFormValues(despatchRoutingForm);
							dsnRoutingSession.removeDSNRoutingVOs();

							invocationContext.addAllError(errors);
							invocationContext.target = LIST_FAILURE;
						}else{
							log.log(Log.FINE,"!!!inside resultList!= null");


						    dsnRtgVOs = new ArrayList<DSNRoutingVO>();
							if(dsnRoutingVOs!=null && dsnRoutingVOs.size()>0){
								dsnRtgVOs=(ArrayList<DSNRoutingVO>)dsnRoutingVOs;
								despatchRoutingForm.setOrigin(dsnRtgVOs.get(0).getPol());
								despatchRoutingForm.setDestn(dsnRtgVOs.get(dsnRoutingVOs.size()-1).getPou());
								despatchRoutingForm.setOalPcs(String.valueOf(dsnRtgVOs.get(0).getAcctualnopieces()));
								despatchRoutingForm.setOalwgt(String.valueOf(dsnRtgVOs.get(0).getAcctualweight()));
								for(DSNRoutingVO dSNRoutingVO :dsnRtgVOs){
									dSNRoutingVO.setOwnairlinecode(logonAttributes.getOwnAirlineCode());
								}


							despatchRoutingForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
							invocationContext.target = LIST_SUCCESS;


				}

						despatchRoutingForm.setOwncarcode(logonAttributes.getOwnAirlineCode());
					log.exiting(CLASS_NAME, "execute");
		}}

		public Collection<ErrorVO> validateForm(DespatchRoutingForm despatchRoutingForm){

			Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
			ErrorVO error = null;

			String dsn=despatchRoutingForm.getDsn();
			if("".equals(dsn)){

			    error=new ErrorVO("mailtracking.mra.defaults.despatchrouting.msg.err.dsnmandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}


			return errors;
		}

		private void removeFormValues(DespatchRoutingForm despatchRoutingForm){
			despatchRoutingForm.setDsn("");
			despatchRoutingForm.setDsnDate("");
			despatchRoutingForm.setAirport("");
			despatchRoutingForm.setDepartureDate(null);
			despatchRoutingForm.setFlightCarrierCode(null);
			despatchRoutingForm.setFlightCarrierId(null);
			despatchRoutingForm.setFlightNumber(null);
			despatchRoutingForm.setPol(null);
			despatchRoutingForm.setPou(null);

		}

		/**
		 * A-3251
		 * @param stationCode
		 * @param mailAcceptanceSession
		 * @return
		 */
		private void setUnitComponent(String stationCode,
				DSNRoutingSession  dsnRoutingSession){
			UnitRoundingVO unitRoundingVO = null;
			try{
				log.log(Log.FINE, "station code is ----------->>", stationCode);
				unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_KILOGRAM);
				log.log(Log.FINE, "unit vo for wt--in session---",
						unitRoundingVO);
				dsnRoutingSession.setWeightRoundingVO(unitRoundingVO);

			   }catch(UnitException unitException) {
					unitException.getErrorCode();
			   }

		}
		/**
		 *
		 * @param dsnRoutingVOs
		 */
		private void checkForRouteAdjustment(Collection<DSNRoutingVO> dsnRoutingVOs){
			//String[] route = dsnRoutingVOs.iterator().next().getRoute().split("-");
			//String basis = dsnRoutingVOs.iterator().next().getBillingBasis();
			//String mailOrgin= basis.substring(2,5);
			//String mailDest = basis.substring(8,11);
			ArrayList<DSNRoutingVO> dVOs = new ArrayList<DSNRoutingVO>();
			dVOs.addAll(dsnRoutingVOs);
			String fltOrg = "";
			//String fltDst="";
			boolean isVOAdjustReq = false;
			for(DSNRoutingVO dsnVO : dsnRoutingVOs){
				if(!"YY".equals(dsnVO.getFlightCarrierCode())){
					fltOrg = dsnVO.getPol();
					//fltDst = dsnVO.getPou();
				}
			}
			/*if(!mailOrgin.equals(fltOrg)){
				isVOAdjustReq = true;
			}*/
			if(isVOAdjustReq){
				DSNRoutingVO tempVO = dVOs.get(0);
				dVOs.remove(0);
				dVOs.add(tempVO);
				/*Collection<DSNRoutingVO> routeVOs = new ArrayList<DSNRoutingVO>();
				StringBuilder routeBuilder = new StringBuilder("");
				Collection<String> routePairs = new ArrayList<String>();
				for(String pair:route){
					routeBuilder.append(pair).append("-").toString();
					if(routeBuilder.toString().split("-").length==2){
						routePairs.add(routeBuilder.substring(0,routeBuilder.toString().length()-1));
						routeBuilder.delete(0, routeBuilder.length()-4);
					}else
						continue;
				}
				routePairs.add(routePairs.iterator().next());
					for(String pairs: routePairs){
						String[] split = pairs.split("-");
						String org = split[0];
						String dst = split[1];
						for(DSNRoutingVO dsnVO : dsnRoutingVOs){
							 if(org.equals(dsnVO.getPol()) && dst.equals(dsnVO.getPou())){
								 routeVOs.add(dsnVO);
								 break;
							 }else{
								 continue;
							 }
					}
				}*/
					//dsnRoutingVOs.removeAll(dsnRoutingVOs);
					dsnRoutingVOs.clear();
					dsnRoutingVOs.addAll(dVOs);
			}
		}
		
		
		private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
			log.entering(CLASS_NAME,"fetchOneTimeDetails");
			Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
			Collection<String> oneTimeList=new ArrayList<String>();

			oneTimeList.add(WEIGHT_UNIT_ONETIME);
			oneTimeList.add(SEG_SOURCE);//Added by Manish for IASCB-40970

			SharedDefaultsDelegate sharedDefaultsDelegate =
				new SharedDefaultsDelegate();
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
						oneTimeList);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			log.exiting(CLASS_NAME,"fetchOneTimeDetails");
			return hashMap;
		}


	}

