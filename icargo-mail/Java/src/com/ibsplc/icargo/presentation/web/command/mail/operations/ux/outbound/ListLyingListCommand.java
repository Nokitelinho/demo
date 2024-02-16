package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListLyingListCommand extends AbstractCommand {
	private static final String CONST_MAILBAGENQUIRY = "MAILBAGENQUIRY";
	private Log log = LogFactory.getLogger("OPERATIONS MAILOUTBOUND NEO");
	   private static final String OUTBOUND = "O";
		public void execute(ActionContext actionContext)
			    throws BusinessDelegateException {
				this.log.entering("ListCarditGroupCommand", "execute");
				  LogonAttributes logonAttributes = getLogonAttribute();
				  OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
				  Page<MailbagVO> mailbagVOPage = null;		
                   MailbagEnquiryFilterVO mailbagEnquiryFilterVO = null;  
				   MailbagFilter mailbagFilter = null;
				  String displayPage="";
				  ResponseVO responseVO = new ResponseVO();
				  FlightValidationVO flightValidationVO = null;
				  List<OutboundModel> results = new ArrayList();
				  Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			    
			        MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
			        
			        if( outboundModel !=null && outboundModel.getMailbagFilter()!=null){
						
						log.log(Log.FINE,"mailbagFilter not null");
						
						mailbagFilter = outboundModel.getMailbagFilter();			
						mailbagEnquiryFilterVO = constructMailBagFilterVO(mailbagFilter,logonAttributes);		
						
						log.log(Log.FINE, "MailbagEnquiryFilterVO --------->>", mailbagEnquiryFilterVO);
						log.log(Log.FINE, "PageNumber --------->>", outboundModel.getDisplayPage());
						
					
						
						
					}
			      
			        MailbagVO MailbgSummaryVO = new MailbagVO();
			        try {
			        MailbgSummaryVO=delegate.findLyinglistSummaryView(mailbagEnquiryFilterVO);
			        } catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
						businessDelegateException.getMessage();
					}
					Mailbag summary = null;
					if(Objects.nonNull(MailbgSummaryVO)){
						summary=MailOutboundModelConverter.constructCarditSummary(MailbgSummaryVO);
					}
			        outboundModel.setCarditSummary(summary);
			        //added by A-7815 as part of IASCB-45659
			        if(mailbagEnquiryFilterVO.getPageSize()==0) {
			        	 mailbagEnquiryFilterVO.setPageSize(100);
			        }
			       
			   if(outboundModel.getMailbagFilter().getLyingListActiveTab().equals("LIST_VIEW")) {
				   		mailbagEnquiryFilterVO.setFromScreen("MAIL_OUTBOUND");//Added for ICRD-343119
			        	mailbagVOPage = delegate.findMailbags(mailbagEnquiryFilterVO, mailbagEnquiryFilterVO.getPageNumber());
			        if ((mailbagVOPage == null || mailbagVOPage.getActualPageSize() == 0)) {
						 actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.nomailbags"));
					         return;
			        } else {
						for (MailbagVO mailbagVO : mailbagVOPage) {
							String maibagid = mailbagVO.getMailbagId();
							if (mailbagVO.getWeight() != null) {
								if (maibagid.length() == 29 && mailbagVO.getWeight().getSystemValue() == 0.0) {
									double weight = Double.parseDouble(maibagid.substring(25, 29)) / 10;
									Measure wgt = new Measure(UnitConstants.WEIGHT, weight);
									mailbagVO.setWeight(wgt);// added by A-7371
								}
							}
						}
					}
			        List<Mailbag> mailbagList = MailOperationsModelConverter.constructMailbags(mailbagVOPage);

					PageResult<Mailbag> pageList = new PageResult(mailbagVOPage, mailbagList);
					outboundModel.setLyinglistMailbags(pageList);
			       }
				

			        
			      
			       if(outboundModel.getMailbagFilter().getLyingListActiveTab().equals("GROUP_VIEW")) {
				        Page<MailbagVO> listGroupedBags = null;
				        listGroupedBags = delegate.findGroupedLyingList(mailbagEnquiryFilterVO, mailbagEnquiryFilterVO.getPageNumber());
				        
				       
				        if( listGroupedBags == null || listGroupedBags.size() == 0 )
						{
				        actionContext.addError(new ErrorVO("mail.operations.carditenquiry.mailbagdoesntexists"));
						return;
					    }
				        List<Mailbag> mailbagGroupedList = 
				        		MailOutboundModelConverter.constructCarditGroupedMailbag(listGroupedBags);
				     
				        PageResult<Mailbag> pageGroupedList= new PageResult<Mailbag>(listGroupedBags,mailbagGroupedList);
				        outboundModel.setLyinglistGroupedMailbags(pageGroupedList);
				       }
			        results.add(outboundModel);
			        responseVO.setResults(results);
			        responseVO.setStatus("success");
			        actionContext.setResponseVO(responseVO);
		}
		
		private MailbagEnquiryFilterVO constructMailBagFilterVO(MailbagFilter mailbagFilter,LogonAttributes logonAttributes){
			
			log.entering("ListMailbagsCommand","exconstructMailBagFilterVOecute");
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();	
			
			
			log.log(Log.FINE,"mailbagEnquiryModel.getfilter"+ mailbagFilter.getMailbagId());
			
			mailbagEnquiryFilterVO.setFromScreen(CONST_MAILBAGENQUIRY);
			mailbagEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());		
			
			if(mailbagFilter.getDisplayPage() !=null &&  mailbagFilter.getDisplayPage().trim().length()>0){
				
				mailbagEnquiryFilterVO.setPageNumber(Integer.valueOf(mailbagFilter.getDisplayPage()));
			}else{
				mailbagEnquiryFilterVO.setPageNumber(1);
			}
			
			mailbagEnquiryFilterVO.setMailbagId(upper(mailbagFilter.getMailbagId()));
			mailbagEnquiryFilterVO.setDoe(upper(mailbagFilter.getDoe()));
			mailbagEnquiryFilterVO.setOoe(upper(mailbagFilter.getOoe()));
			mailbagEnquiryFilterVO.setMailCategoryCode(mailbagFilter.getMailCategoryCode());
			mailbagEnquiryFilterVO.setMailSubclass(upper(mailbagFilter.getMailSubclass()));
			if (!"".equals(mailbagFilter.getYear())) {
				mailbagEnquiryFilterVO.setYear(mailbagFilter.getYear());
			}
			mailbagEnquiryFilterVO.setDespatchSerialNumber(upper(mailbagFilter.getDespatchSerialNumber()));
			mailbagEnquiryFilterVO.setReceptacleSerialNumber(upper(mailbagFilter.getReceptacleSerialNumber()));
			mailbagEnquiryFilterVO.setCurrentStatus(mailbagFilter.getOperationalStatus());
			mailbagEnquiryFilterVO.setScanPort(upper(mailbagFilter.getScanPort()));
			
			if ((!("").equals(mailbagFilter.getFromDate())) && mailbagFilter.getFromDate() != null) {
				LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(), ARP, true);
				String scanFromDT = new StringBuilder(mailbagFilter.getFromDate()).append(" ").append("00:00:00")
						.toString();
				mailbagEnquiryFilterVO.setScanFromDate(fromdate.setDateAndTime(scanFromDT, false));
			}
			if ((!("").equals(mailbagFilter.getToDate())) && mailbagFilter.getToDate() != null) {
				LocalDate todate = new LocalDate(logonAttributes.getAirportCode(), ARP, true);
				String scanToDT = new StringBuilder(mailbagFilter.getToDate()).append(" ").append("23:59:59")
						.toString();
				mailbagEnquiryFilterVO.setScanToDate(todate.setDateAndTime(scanToDT, false));
			}
			mailbagEnquiryFilterVO.setScanUser(upper(mailbagFilter.getUserID()));
			
		  if (mailbagFilter.getDamageFlag()!=null && mailbagFilter.getDamageFlag().equalsIgnoreCase("true")) {
				mailbagEnquiryFilterVO.setDamageFlag(MailbagEnquiryFilterVO.FLAG_YES);
			} else {
				mailbagEnquiryFilterVO.setDamageFlag(MailbagEnquiryFilterVO.FLAG_NO);
			}
		  if (mailbagFilter.getTransitFlag()!=null && mailbagFilter.getTransitFlag().equalsIgnoreCase("true")) {
			  	mailbagEnquiryFilterVO.setTransitFlag(MailbagEnquiryFilterVO.FLAG_YES);
			} else {
				mailbagEnquiryFilterVO.setTransitFlag(MailbagEnquiryFilterVO.FLAG_NO);
			}
				
			if (mailbagFilter.getRdtDate() != null
					&& mailbagFilter.getRdtDate().trim().length() > 0) {
				LocalDate rqdDlvTim = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				StringBuilder reqDeliveryTime = new StringBuilder(mailbagFilter.getRdtDate());
				if (mailbagFilter.getReqDeliveryTime() != null
						&& mailbagFilter.getReqDeliveryTime().trim().length() > 0) {
					reqDeliveryTime.append(" ").append(mailbagFilter.getReqDeliveryTime()).append(":00");
					rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
				} else {
					rqdDlvTim.setDate(reqDeliveryTime.toString());
				}
				mailbagEnquiryFilterVO.setReqDeliveryTime(rqdDlvTim);

			}
			//mailbagEnquiryFilterVO.setContainerNumber(upper(mailbagFilter.getUldNumber()));
			if (mailbagFilter.getFlightDate() != null && mailbagFilter.getFlightDate().length() > 0 && !"null".equals(mailbagFilter.getFlightDate())) {
				LocalDate fltdate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
				mailbagEnquiryFilterVO.setFlightDate(fltdate.setDate(mailbagFilter.getFlightDate()));
			}
			if (mailbagFilter.getFlightNumber() != null && !"".equals(mailbagFilter.getFlightNumber())) {
				String fltnum = (mailbagFilter.getFlightNumber());
				mailbagEnquiryFilterVO.setFlightNumber(upper(fltnum));
			}
			
			mailbagEnquiryFilterVO.setCarrierCode(upper(mailbagFilter.getCarrierCode()));
			
			//mailbagEnquiryFilterVO.setConsigmentNumber(upper(mailbagFilter.getConsigmentNumber()));
			mailbagEnquiryFilterVO.setUpuCode(upper(mailbagFilter.getUpuCode()));
			
			if (MailbagEnquiryFilterVO.FLAG_YES.equals(mailbagFilter.getCarditStatus())) {
				mailbagEnquiryFilterVO.setCarditPresent(MailConstantsVO.FLAG_YES);
			}
			if (MailbagEnquiryFilterVO.FLAG_NO.equals(mailbagFilter.getCarditStatus())) {
				mailbagEnquiryFilterVO.setCarditPresent(MailConstantsVO.FLAG_NO);
			}
			if (("").equals(mailbagFilter.getCarditStatus())) {
				mailbagEnquiryFilterVO.setCarditPresent("ALL");
			}
			if((mailbagFilter.getPageSize()!=null) && ((mailbagFilter.getPageSize().trim().length()) > 0)){
				mailbagEnquiryFilterVO.setPageSize(Integer.parseInt(mailbagFilter.getPageSize()));//Added by A-8353 for ICRD-324698
			}
			mailbagEnquiryFilterVO.setConsigmentNumber(mailbagFilter.getConsignmentNo());
			mailbagEnquiryFilterVO.setContainerNumber(mailbagFilter.getContainerNo());
			mailbagEnquiryFilterVO.setServiceLevel(mailbagFilter.getServiceLevel());
			mailbagEnquiryFilterVO.setOnTimeDelivery(mailbagFilter.getOnTimeDelivery());
			mailbagEnquiryFilterVO.setOriginAirportCode(mailbagFilter.getMailOrigin());
			mailbagEnquiryFilterVO.setDestinationAirportCode(mailbagFilter.getMailDestination());
       return mailbagEnquiryFilterVO;
		}
		
		private String upper(String input){//to convert sting to uppercase

			if(input!=null){
				return input.trim().toUpperCase();
			}else{
				return "";
			}
		}
}