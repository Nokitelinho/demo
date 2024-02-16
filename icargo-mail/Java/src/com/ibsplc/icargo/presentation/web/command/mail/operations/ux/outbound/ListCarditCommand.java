package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
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
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCarditCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("OPERATIONS MAILOUTBOUND NEO");
	   private static final String OUTBOUND = "O";
		public void execute(ActionContext actionContext)
			    throws BusinessDelegateException {
				this.log.entering("ListCarditGroupCommand", "execute");
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				  LogonAttributes logonAttributes = getLogonAttribute();
				  OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
				  CarditEnquiryFilterVO carditEnquiryFilterVO = null;
				  carditEnquiryFilterVO = new CarditEnquiryFilterVO();
				 // String displayPage = "1";
				  String displayPage="";
				  ResponseVO responseVO = new ResponseVO();
				  FlightValidationVO flightValidationVO = null;
				  List<OutboundModel> results = new ArrayList();
		
			      if ((logonAttributes.getCompanyCode() != null) && (logonAttributes.getCompanyCode().trim().length() > 0)) {
			        carditEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			      }
			   
			      if(outboundModel.getCarditFilter().getAirportCode() != null && outboundModel.getCarditFilter().getAirportCode().trim().length() > 0) {
			        	carditEnquiryFilterVO.setPol(outboundModel.getCarditFilter().getAirportCode());
			    	}
			      if ((outboundModel.getCarditFilter().getOoe() != null) && (outboundModel.getCarditFilter().getOoe().trim().length() > 0)) {
				        carditEnquiryFilterVO.setOoe(outboundModel.getCarditFilter().getOoe());
				      }
				      if ((outboundModel.getCarditFilter().getDoe() != null) && (outboundModel.getCarditFilter().getDoe().trim().length() > 0)) {
				        carditEnquiryFilterVO.setDoe(outboundModel.getCarditFilter().getDoe());
				      }
				      if ((outboundModel.getCarditFilter().getMailCategoryCode()!= null) && (outboundModel.getCarditFilter().getMailCategoryCode().trim().length() > 0)) {
				        carditEnquiryFilterVO.setMailCategoryCode(outboundModel.getCarditFilter().getMailCategoryCode());
				      }
				      if ((outboundModel.getCarditFilter().getMailSubclass() != null) && (outboundModel.getCarditFilter().getMailSubclass().trim().length() > 0)) {
				        carditEnquiryFilterVO.setMailSubclass(outboundModel.getCarditFilter().getMailSubclass() );
				      }
				      if ((outboundModel.getCarditFilter().getYear()!= null) && (outboundModel.getCarditFilter().getYear().trim().length() > 0)) {
				        carditEnquiryFilterVO.setYear(outboundModel.getCarditFilter().getYear());
				      }
				      if ((outboundModel.getCarditFilter().getDespatchSerialNumber()!= null) && (outboundModel.getCarditFilter().getDespatchSerialNumber().trim().length() > 0)) {
				        carditEnquiryFilterVO.setDespatchSerialNumber(outboundModel.getCarditFilter().getDespatchSerialNumber());
				      }
				      if ((outboundModel.getCarditFilter().getReceptacleSerialNumber() != null) && (outboundModel.getCarditFilter().getReceptacleSerialNumber().trim().length() > 0)) {
				        carditEnquiryFilterVO.setReceptacleSerialNumber(outboundModel.getCarditFilter().getReceptacleSerialNumber());
				      }
				      if ((outboundModel.getCarditFilter().getConDocNo() != null) && (outboundModel.getCarditFilter().getConDocNo().trim().length() > 0)) {
				       carditEnquiryFilterVO.setConsignmentDocument(outboundModel.getCarditFilter().getConDocNo());
				     }
					if ((outboundModel.getCarditFilter().getConsignmentDate() != null) && (outboundModel.getCarditFilter().getConsignmentDate().trim().length() > 0)) {
					    	  LocalDate consignmentDate = new LocalDate("***", Location.NONE, false);
					    	  consignmentDate.setDate(outboundModel.getCarditFilter().getConsignmentDate().toUpperCase());
					    	  carditEnquiryFilterVO.setConsignmentDate(consignmentDate);
					}
				     if ((outboundModel.getCarditFilter().getPaCode()!= null) && (outboundModel.getCarditFilter().getPaCode().trim().length() > 0)) {
				       carditEnquiryFilterVO.setPaoCode(outboundModel.getCarditFilter().getPaCode());
				     }
				      if ((outboundModel.getCarditFilter().getMailbagId() != null) && (outboundModel.getCarditFilter().getMailbagId().trim().length() > 0)) {
				          carditEnquiryFilterVO.setMailbagId(outboundModel.getCarditFilter().getMailbagId());
				      }
				      if ((logonAttributes.getCompanyCode() != null) && (logonAttributes.getCompanyCode().trim().length() > 0)) {
				        carditEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				      }
				      if ((outboundModel.getCarditFilter().getFlightNumber()!= null) && (outboundModel.getCarditFilter().getFlightNumber().trim().length() > 0)) {
				        carditEnquiryFilterVO.setFlightNumber(outboundModel.getCarditFilter().getFlightNumber());
				      }
				      if ((outboundModel.getCarditFilter().getFlightDate()!= null) && (outboundModel.getCarditFilter().getFlightDate().trim().length() > 0))
				        {
				          LocalDate flightDate = new LocalDate("***", Location.NONE, false);
				          flightDate.setDate(outboundModel.getCarditFilter().getFlightDate().toUpperCase());
				          carditEnquiryFilterVO.setFlightDate(flightDate);
				        }
				      if(outboundModel.getCarditFilter().getAirportCode() != null && outboundModel.getCarditFilter().getAirportCode().trim().length() > 0) {
				        	carditEnquiryFilterVO.setPol(outboundModel.getCarditFilter().getAirportCode());
				    	}
				      if ((outboundModel.getCarditFilter().getUldNumber() != null) && (outboundModel.getCarditFilter().getUldNumber().trim().length() > 0)) {
				        carditEnquiryFilterVO.setUldNumber(outboundModel.getCarditFilter().getUldNumber());
				      }
				        LocalDate fromDate = new LocalDate("***", Location.NONE, false);
				        LocalDate toDate = new LocalDate("***", Location.NONE, false);
				        if ((outboundModel.getCarditFilter().getFromDate()!= null) && (outboundModel.getCarditFilter().getFromDate().trim().length() > 0))
				        {
				          fromDate.setDate(outboundModel.getCarditFilter().getFromDate().toUpperCase());
				          carditEnquiryFilterVO.setFromDate(fromDate);
				        }
				        if ((outboundModel.getCarditFilter().getToDate() != null) && (outboundModel.getCarditFilter().getToDate().trim().length() > 0))
				        {
				          toDate.setDate(outboundModel.getCarditFilter().getToDate().toUpperCase());
				          carditEnquiryFilterVO.setToDate(toDate);
				        }
				        if ((outboundModel.getCarditFilter().getMailOrigin()!= null) && (outboundModel.getCarditFilter().getMailOrigin().trim().length() > 0)) {
					        carditEnquiryFilterVO.setMailOrigin(outboundModel.getCarditFilter().getMailOrigin());
					     }
				        if ((outboundModel.getCarditFilter().getMailDestination()!= null) && (outboundModel.getCarditFilter().getMailDestination().trim().length()) > 0) {
					        carditEnquiryFilterVO.setMaildestination(outboundModel.getCarditFilter().getMailDestination());
					     }
				        if ((outboundModel.getCarditFilter().getMailStatus()!= null) && (outboundModel.getCarditFilter().getMailStatus().trim().length()) > 0) {
					        carditEnquiryFilterVO.setMailStatus(outboundModel.getCarditFilter().getMailStatus());
					     }
				        if ((outboundModel.getCarditFilter().getFlightType()!= null) && (outboundModel.getCarditFilter().getFlightType().trim().length()) > 0) {
					        carditEnquiryFilterVO.setFlightType(outboundModel.getCarditFilter().getFlightType());
					     }
			        displayPage = outboundModel.getCarditFilter().getDisplayPage();
			        MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
			       
			        carditEnquiryFilterVO.setPageSize(100);
			        if(outboundModel.getCarditFilter().getCarditActiveTab().equals("LIST_VIEW")) {
			        Page<MailbagVO> listBags = null;
			        try {
			        	// added as part of IASCB-56008
			        	carditEnquiryFilterVO.setFromScreen("MTK060");
			        listBags = delegate.findCarditMails(carditEnquiryFilterVO, Integer.parseInt(displayPage));
				        } catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
							businessDelegateException.getMessage();
				        }
			        if( listBags == null || listBags.size() == 0 )
					{
			        actionContext.addError(new ErrorVO("mail.operations.carditenquiry.mailbagdoesntexists"));
					return;
					   
						}
			          
			        
			        List<Mailbag> mailbagList = 
			        		MailOperationsModelConverter.constructMailbags(listBags);
			          
			          
			        PageResult<Mailbag> pageList= new PageResult<Mailbag>(listBags,mailbagList);
			         if(mailbagList.size() > 0) {
			        outboundModel.setCarditMailbags(pageList);
			        String[] grandTotals = null;
			          
			          grandTotals = delegate.findGrandTotals(carditEnquiryFilterVO);
			          Mailbag summary = new Mailbag();
						if(grandTotals!=null&&grandTotals.length==2){
							summary.setCount(Integer.valueOf(grandTotals[0]));
							summary.setWeight(new Measure(UnitConstants.MAIL_WGT ,Double.parseDouble(grandTotals[1])));
			          }
				    outboundModel.setCarditSummary(summary);
                     }
				     else {
					        outboundModel.setCarditMailbags(pageList);
                     }
			        }
			        if(outboundModel.getCarditFilter().getCarditActiveTab().equals("GROUP_VIEW")) {
				        Page<MailbagVO> listGroupedBags = null;
				        try {
				        listGroupedBags = delegate.findGroupedCarditMails(carditEnquiryFilterVO, Integer.parseInt(displayPage));
				        if( listGroupedBags == null || listGroupedBags.size() == 0 )
						{
				        actionContext.addError(new ErrorVO("mail.operations.carditenquiry.mailbagdoesntexists"));
						return;
					    }
				        } catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
							businessDelegateException.getMessage();
						}
				        List<Mailbag> mailbagGroupedList = 
				        		MailOutboundModelConverter.constructCarditGroupedMailbag(listGroupedBags);
				     
				        PageResult<Mailbag> pageGroupedList= new PageResult<Mailbag>(listGroupedBags,mailbagGroupedList);
				        if(mailbagGroupedList.size() > 0) {
				        outboundModel.setCarditGroupMailbags(pageGroupedList);
				        String[] grandTotals = null;
				          grandTotals = delegate.findGrandTotals(carditEnquiryFilterVO);
				          Mailbag summary = new Mailbag();
							if(grandTotals!=null&&grandTotals.length==2){
								summary.setCount(Integer.valueOf(grandTotals[0]));
								summary.setWeight(new Measure(UnitConstants.MAIL_WGT ,Double.parseDouble(grandTotals[1])));
				          }
					    outboundModel.setCarditSummary(summary);
	                     }
					     else {
					    	 outboundModel.setCarditGroupMailbags(pageGroupedList);
	                     }
				        }
			        results.add(outboundModel);
			        responseVO.setResults(results);
			        responseVO.setStatus("success");
			        actionContext.setResponseVO(responseVO);
		}
}