package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class ListCarditEnquiryCommand  extends AbstractCommand{
	private Log log = LogFactory.getLogger("OPERATIONS CARDITENQUIRY NEO");
	private static final String MAIL_COUNT_LIMIT_SYSPAR = "mail.operations.mailcountlimitforawbhandling";
	@Override
	public void execute(ActionContext actionContext)
	    throws BusinessDelegateException {
		this.log.entering("ListCarditEnquiryCommand", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		  LogonAttributes logonAttributes = getLogonAttribute();
		  CarditEnquiryModel carditEnquiryModel = (CarditEnquiryModel)actionContext.getScreenModel();
		  this.log.log(5, new Object[] { "OOE ---> ", carditEnquiryModel.getOoe() });		
		  this.log.log(5, new Object[] { "DOE ---> ", carditEnquiryModel.getDoe()});
		  CarditEnquiryFilterVO carditEnquiryFilterVO = null;
		  carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		 // String displayPage = "1";
		  String displayPage="";
		  ResponseVO responseVO = new ResponseVO();
		  FlightValidationVO flightValidationVO = null;
	//	  flightValidationVO = validateFlight(carditEnquiryModel, logonAttributes, actionContext);
		  List<CarditEnquiryModel> results = new ArrayList();
	      if ((carditEnquiryModel.getCarditFilter().getOoe() != null) && (carditEnquiryModel.getCarditFilter().getOoe().trim().length() > 0)) {
	        carditEnquiryFilterVO.setOoe(carditEnquiryModel.getCarditFilter().getOoe());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getDoe() != null) && (carditEnquiryModel.getCarditFilter().getDoe().trim().length() > 0)) {
	        carditEnquiryFilterVO.setDoe(carditEnquiryModel.getCarditFilter().getDoe());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getMailCategoryCode()!= null) && (carditEnquiryModel.getCarditFilter().getMailCategoryCode().trim().length() > 0)) {
	        carditEnquiryFilterVO.setMailCategoryCode(carditEnquiryModel.getCarditFilter().getMailCategoryCode());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getMailSubclass() != null) && (carditEnquiryModel.getCarditFilter().getMailSubclass().trim().length() > 0)) {
	        carditEnquiryFilterVO.setMailSubclass(carditEnquiryModel.getCarditFilter().getMailSubclass() );
	      }
	      if ((carditEnquiryModel.getCarditFilter().getYear()!= null) && (carditEnquiryModel.getCarditFilter().getYear().trim().length() > 0)) {
	        carditEnquiryFilterVO.setYear(carditEnquiryModel.getCarditFilter().getYear());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getDespatchSerialNumber()!= null) && (carditEnquiryModel.getCarditFilter().getDespatchSerialNumber().trim().length() > 0)) {
	        carditEnquiryFilterVO.setDespatchSerialNumber(carditEnquiryModel.getCarditFilter().getDespatchSerialNumber());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getReceptacleSerialNumber() != null) && (carditEnquiryModel.getCarditFilter().getReceptacleSerialNumber().trim().length() > 0)) {
	        carditEnquiryFilterVO.setReceptacleSerialNumber(carditEnquiryModel.getCarditFilter().getReceptacleSerialNumber());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getConDocNo() != null) && (carditEnquiryModel.getCarditFilter().getConDocNo().trim().length() > 0)) {
	       carditEnquiryFilterVO.setConsignmentDocument(carditEnquiryModel.getCarditFilter().getConDocNo());
	     }
	      if ((carditEnquiryModel.getCarditFilter().getConsignmentDate() != null) && (carditEnquiryModel.getCarditFilter().getConsignmentDate().trim().length() > 0)) {
	    	  LocalDate consignmentDate = new LocalDate("***", Location.NONE, false);
	    	  consignmentDate.setDate(carditEnquiryModel.getCarditFilter().getConsignmentDate().toUpperCase());
	    	  carditEnquiryFilterVO.setConsignmentDate(consignmentDate);
		     }
	     if ((carditEnquiryModel.getCarditFilter().getPaCode()!= null) && (carditEnquiryModel.getCarditFilter().getPaCode().trim().length() > 0)) {
	       carditEnquiryFilterVO.setPaoCode(carditEnquiryModel.getCarditFilter().getPaCode());
	     }
	      if ((carditEnquiryModel.getCarditFilter().getMailbagId() != null) && (carditEnquiryModel.getCarditFilter().getMailbagId().trim().length() > 0)) {
	          carditEnquiryFilterVO.setMailbagId(carditEnquiryModel.getCarditFilter().getMailbagId());
	      }
	      if ((logonAttributes.getCompanyCode() != null) && (logonAttributes.getCompanyCode().trim().length() > 0)) {
	        carditEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getFlightNumber()!= null) && (carditEnquiryModel.getCarditFilter().getFlightNumber().trim().length() > 0)) {
	        carditEnquiryFilterVO.setFlightNumber(carditEnquiryModel.getCarditFilter().getFlightNumber());
	      }
	      if ((carditEnquiryModel.getCarditFilter().getFlightDate()!= null) && (carditEnquiryModel.getCarditFilter().getFlightDate().trim().length() > 0))
	        {
	          LocalDate flightDate = new LocalDate("***", Location.NONE, false);
	          flightDate.setDate(carditEnquiryModel.getCarditFilter().getFlightDate().toUpperCase());
	          carditEnquiryFilterVO.setFlightDate(flightDate);
	        }
	      if(carditEnquiryModel.getCarditFilter().getAirportCode() != null && carditEnquiryModel.getCarditFilter().getAirportCode().trim().length() > 0) {
	        	carditEnquiryFilterVO.setPol(carditEnquiryModel.getCarditFilter().getAirportCode());
	    	}
	      if ((carditEnquiryModel.getCarditFilter().getUldNumber() != null) && (carditEnquiryModel.getCarditFilter().getUldNumber().trim().length() > 0)) {
	        carditEnquiryFilterVO.setUldNumber(carditEnquiryModel.getCarditFilter().getUldNumber());
	      }
	        LocalDate fromDate = new LocalDate("***", Location.NONE, false);
	        LocalDate toDate = new LocalDate("***", Location.NONE, false);
	        LocalDate reqDate = new LocalDate("***", Location.NONE, false);
	        if ((carditEnquiryModel.getCarditFilter().getFromDate()!= null) && (carditEnquiryModel.getCarditFilter().getFromDate().trim().length() > 0))
	        {
	          fromDate.setDate(carditEnquiryModel.getCarditFilter().getFromDate().toUpperCase());
	          carditEnquiryFilterVO.setFromDate(fromDate);
	        }
	        if ((carditEnquiryModel.getCarditFilter().getToDate() != null) && (carditEnquiryModel.getCarditFilter().getToDate().trim().length() > 0))
	        {
	          toDate.setDate(carditEnquiryModel.getCarditFilter().getToDate().toUpperCase());
	          carditEnquiryFilterVO.setToDate(toDate);
	        }
	        if (fromDate.isGreaterThan(toDate))
	        {
	        	actionContext.addError(new ErrorVO("mail.operations.carditenquiry.greaterdate"));
	        	return;
	        }
	        
	        if ((carditEnquiryModel.getCarditFilter().getMailOrigin()!= null) && (carditEnquiryModel.getCarditFilter().getMailOrigin().trim().length() > 0)) {
		        carditEnquiryFilterVO.setMailOrigin(carditEnquiryModel.getCarditFilter().getMailOrigin());
		     }
	        if ((carditEnquiryModel.getCarditFilter().getMailDestination()!= null) && (carditEnquiryModel.getCarditFilter().getMailDestination().trim().length()) > 0) {
		        carditEnquiryFilterVO.setMaildestination(carditEnquiryModel.getCarditFilter().getMailDestination());
		     }
	        if ((carditEnquiryModel.getCarditFilter().getMailStatus()!= null) && (carditEnquiryModel.getCarditFilter().getMailStatus().trim().length()) > 0) {
		        carditEnquiryFilterVO.setMailStatus(carditEnquiryModel.getCarditFilter().getMailStatus());
		     }
	        if ((carditEnquiryModel.getCarditFilter().getFlightType()!= null) && (carditEnquiryModel.getCarditFilter().getFlightType().trim().length()) > 0) {
		        carditEnquiryFilterVO.setFlightType(carditEnquiryModel.getCarditFilter().getFlightType());
		     }
	        if(carditEnquiryModel.getCarditFilter().isPendingResditChecked()) {
	        	carditEnquiryFilterVO.setPendingResditChecked(carditEnquiryModel.getCarditFilter().isPendingResditChecked());
		     }
	        if ((carditEnquiryModel.getCarditFilter().getShipmentPrefix()!= null) && (carditEnquiryModel.getCarditFilter().getShipmentPrefix().trim().length()) > 0) {
		        carditEnquiryFilterVO.setShipmentPrefix(carditEnquiryModel.getCarditFilter().getShipmentPrefix());
		     }
	        if ((carditEnquiryModel.getCarditFilter().getMasterDocumentNumber()!= null) && (carditEnquiryModel.getCarditFilter().getMasterDocumentNumber().trim().length()) > 0) {
		        carditEnquiryFilterVO.setDocumentNumber(carditEnquiryModel.getCarditFilter().getMasterDocumentNumber());
		     }
	        if ((carditEnquiryModel.getCarditFilter().getPageSize()!= null) && (carditEnquiryModel.getCarditFilter().getPageSize().trim().length()) > 0) { //Added by A-8164 for ICRD-320122
		        carditEnquiryFilterVO.setPageSize(Integer.parseInt(carditEnquiryModel.getCarditFilter().getPageSize()));
		     }
	        else{
	        	carditEnquiryFilterVO.setPageSize(10);
	        }
	        if(carditEnquiryModel.getCarditFilter().getReqDeliveryDate()!=null&&
	        		carditEnquiryModel.getCarditFilter().getReqDeliveryDate().trim().length()>0){
	    		LocalDate rqdDlvTim=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
	    		StringBuilder reqDeliveryTime=new StringBuilder(carditEnquiryModel.getCarditFilter().getReqDeliveryDate());
	    		if(carditEnquiryModel.getCarditFilter().getReqDeliveryTime()!=null&&
	    				carditEnquiryModel.getCarditFilter().getReqDeliveryTime().trim().length()>0){
	    			reqDeliveryTime.append(" ").append(carditEnquiryModel.getCarditFilter().getReqDeliveryTime()).append(":00");
	    			rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
	    		}else{
	    			rqdDlvTim.setDate(reqDeliveryTime.toString());
	    		}    		
	    		carditEnquiryFilterVO.setReqDeliveryTime(rqdDlvTim);
	    	}
	        if(carditEnquiryModel.getCarditFilter().getTransportServWindowDate()!=null&&
	        		carditEnquiryModel.getCarditFilter().getTransportServWindowDate().trim().length()>0){
	    		LocalDate transportServWindow=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
	    		StringBuilder transportSerTime=new StringBuilder(carditEnquiryModel.getCarditFilter().getTransportServWindowDate());
	    		if(carditEnquiryModel.getCarditFilter().getTransportServWindowTime()!=null&&
	    				carditEnquiryModel.getCarditFilter().getTransportServWindowTime().trim().length()>0){
	    			transportSerTime.append(" ").append(carditEnquiryModel.getCarditFilter().getTransportServWindowTime()).append(":00");
	    			transportServWindow.setDateAndTime(transportSerTime.toString());
	    		}else{
	    			transportServWindow.setDate(transportSerTime.toString());
	    		}    		
	    		carditEnquiryFilterVO.setTransportServWindow(transportServWindow);
	    	}
	    	if(  (carditEnquiryModel.getCarditFilter().getFromDate()==null || carditEnquiryModel.getCarditFilter().getFromDate().trim().length()==0  || carditEnquiryModel.getCarditFilter().getToDate()==null || carditEnquiryModel.getCarditFilter().getToDate().trim().length()==0) 
	    			&& (carditEnquiryModel.getCarditFilter().getMailbagId()==null||carditEnquiryModel.getCarditFilter().getMailbagId().trim().length()==0) 
	    			&& (carditEnquiryModel.getCarditFilter().getConDocNo()==null || carditEnquiryModel.getCarditFilter().getConDocNo().trim().length()==0 )
	    			&& (carditEnquiryModel.getCarditFilter().getConsignmentDate()==null || carditEnquiryModel.getCarditFilter().getConsignmentDate().trim().length()==0)
	    			&& (carditEnquiryModel.getCarditFilter().getMasterDocumentNumber()==null ||carditEnquiryModel.getCarditFilter().getMasterDocumentNumber().trim().length()==0 ||carditEnquiryModel.getCarditFilter().getShipmentPrefix()==null || carditEnquiryModel.getCarditFilter().getShipmentPrefix().trim().length()==0)
	    			&& (carditEnquiryModel.getCarditFilter().getFlightDate()==null ||carditEnquiryModel.getCarditFilter().getFlightDate().trim().length()==0 ||
	    				carditEnquiryModel.getCarditFilter().getCarrierCode()==null || carditEnquiryModel.getCarditFilter().getCarrierCode().trim().length()==0||
	    				carditEnquiryModel.getCarditFilter().getFlightNumber()==null||carditEnquiryModel.getCarditFilter().getFlightNumber().trim().length()==0) 
	    			){
	            actionContext.addError(new ErrorVO("mailtracking.defaults.searchconsignment.mandatoryfieldnull"));
				return;
	    	}
			
			if ((carditEnquiryModel.getCarditFilter().getAwbAttached()!= null) && (carditEnquiryModel.getCarditFilter().getAwbAttached().trim().length() > 0)) {
	 	        carditEnquiryFilterVO.setIsAWBAttached(carditEnquiryModel.getCarditFilter().getAwbAttached()); 	     
	 	      }
	    	
	        displayPage = carditEnquiryModel.getCarditFilter().getDisplayPage();
	        this.log.log(3, new Object[] { "carditEnquiryFilterVO ---> ", carditEnquiryFilterVO });
	        MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	       // ArrayList<MailbagVO> listBags=null;
	        Page<MailbagVO> listBags = null;
	        try {
	        listBags = delegate.findCarditMails(carditEnquiryFilterVO, Integer.parseInt(displayPage));
	        }catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
	        if( listBags == null || listBags.size() == 0 )
			{
	        actionContext.addError(new ErrorVO("mail.operations.carditenquiry.mailbagdoesntexists"));
			return;
		    }
	        
	        if(displayPage!=null && "1".equals(displayPage)){
	          String[] grandTotals = null;
	          
	          grandTotals = delegate.findGrandTotals(carditEnquiryFilterVO);
				
				if(grandTotals!=null&&grandTotals.length==2){
					carditEnquiryModel.setTotalPieces(Integer.valueOf(grandTotals[0]));
					carditEnquiryModel.setTotalWeight(new Measure(UnitConstants.MAIL_WGT ,Double.parseDouble(grandTotals[1])));
	          }
	        }
	        
	        Collection<String> systemParameterCodes = new ArrayList<>();   
	    	systemParameterCodes.add(MAIL_COUNT_LIMIT_SYSPAR);
	    	Map<String, String> result = null;
	    	String mailCount="0";
	    	try {
	    		result = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
	    	} catch(BusinessDelegateException businessDelegateException) {
	    		handleDelegateException(businessDelegateException);
	    	}
	    	if(result!=null && !result.isEmpty()){
	    		 mailCount = result.get(MAIL_COUNT_LIMIT_SYSPAR);
	    	}
	    	carditEnquiryModel.getCarditFilter().setMailCount(mailCount);
	        
	        //Added by A-8464 for ICRD-329095 begins
	        Page<MailbagVO> listmails= null;
	        if(carditEnquiryFilterVO.getPageSize()!=listBags.getActualPageSize()){
	        	ArrayList<MailbagVO> listMailBags = new ArrayList<>();
        		for(int i =0; i<listBags.size();i++){
        			listMailBags.add(listBags.get(i));
        		}//modified by A-7371 for ICRD-339725
        		 listmails= new Page<MailbagVO>(listMailBags, listBags.getPageNumber(), carditEnquiryFilterVO.getPageSize(), listBags.size(), listBags.getStartIndex() , listBags.getStartIndex()+listBags.size(), listBags.hasNextPage(),listBags.getTotalRecordCount());
	        }
	        
	        if(listmails==null){
	        	listmails=listBags;
	        }
	        //Added by A-8464 for ICRD-329095 ends
	        
	        List<Mailbag> mailbagList = 
	        		MailOperationsModelConverter.constructMailbags(listmails);
	        if(mailbagList.size() > 0) {
	        PageResult<Mailbag> pageList= new PageResult<Mailbag>(listmails,mailbagList);
	        carditEnquiryModel.setMailbags(pageList);
	        }
	        else {
	        	carditEnquiryModel.setMailbags(null);
	        }
	       // carditEnquiryModel.setMailbagsVO(listBags);
	        results.add(carditEnquiryModel);
	        responseVO.setResults(results);
	        responseVO.setStatus("success");
	        actionContext.setResponseVO(responseVO); 
	        
	        
	    
	
	}
	
	
}
