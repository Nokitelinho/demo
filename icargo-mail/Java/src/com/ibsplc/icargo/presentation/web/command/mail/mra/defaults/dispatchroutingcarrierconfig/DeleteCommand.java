package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dispatchroutingcarrierconfig;

/**
 * DeleteCommand
 * 
 * @author A-4452
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm;
//import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DeleteCommand extends BaseCommand {
	
	
	private static final String CLASS_NAME = "DeleteCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String BLANK = "";

	/*
	 * 
	 * For setting the Target action. If the system successfully saves the
	 * 
	 * data, then SAVE_SUCCESS target action is set to invocation context
	 * 
	 */

	private static final String DELETEDETAILS_SUCCESS = "deletedetails_success";

	//private static final String DELETEDETAILS_FAILURE = "deletedetails_failure";

	//private static final String ERROR_KEY_NOROWSELECTED = "cra.defaults.awbroutingdetails.selectrow";

	/**
	 * executes delete function
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		
		log.entering(CLASS_NAME, "execute");

		//ErrorVO error = null;
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//Collection<RoutingCarrierVO> newRoutingCarrierVOs = new ArrayList<RoutingCarrierVO>();
		Collection<RoutingCarrierVO> routingCarrierVOstoBeRemoved = new ArrayList<RoutingCarrierVO>();
		Collection<RoutingCarrierVO> routingCarrierVOs=null; 
		/** getting values from form */
		MRARoutingCarrierForm  mraRoutingCarrierForm = (MRARoutingCarrierForm)invocationContext.screenModel;
		MRARoutingCarrierSession routingCarriersession = getScreenSession(MODULE_NAME,SCREENID);
	 
		routingCarrierVOs = routingCarriersession.getRoutingCarrierVOs();		
		String[] rowId = mraRoutingCarrierForm.getCheckBoxForRoutingCarrier();	
		//Added by A-4809 for bug ICMN-3304
		if(routingCarrierVOs !=null && routingCarrierVOs.size()>0){
		int index = 0;
		String[] hiddenOpFlag = mraRoutingCarrierForm.getHiddenOpFlag();

		String[] origincode = mraRoutingCarrierForm.getOrigincode();
		String[] destcode = mraRoutingCarrierForm.getDestcode();
		String[] ownSectorFrm = mraRoutingCarrierForm.getOwnSectorFrm();
		String[] ownSectorTo = mraRoutingCarrierForm.getOwnSectorTo();
		String[] oalSectorFrm = mraRoutingCarrierForm.getOalSectorFrm();
		String[] oalSectorTo = mraRoutingCarrierForm.getOalSectorTo();
		String[] carrierCode = mraRoutingCarrierForm.getCarriercode();
		String[] validFrom = mraRoutingCarrierForm.getValidFrom();
		String[] validTo = mraRoutingCarrierForm.getValidTo();
		
		log.log(Log.FINE, "dsnRoutingVOsin add command==>>:\n\n ",
				routingCarrierVOs);
		for (RoutingCarrierVO routingCarrierVO : routingCarrierVOs) {

			// craAWBRoutingDetailsVO.setCompanyCode(companyCode);
			if (!"D".equalsIgnoreCase(hiddenOpFlag[index])) {

				if (!origincode[index].equals(BLANK)) {
					routingCarrierVO.setOriginCity(origincode[index]);
				} else {
					routingCarrierVO.setOriginCity(BLANK);
				}

				if (!destcode[index].equals(BLANK)) {
					routingCarrierVO.setDestCity(destcode[index]);
				} else {
					routingCarrierVO.setDestCity(BLANK);
				}

				if (!ownSectorFrm[index].equals(BLANK)) {
					routingCarrierVO.setOwnSectorFrm(ownSectorFrm[index]);
				}else {
					routingCarrierVO.setOwnSectorFrm(BLANK);
				}

				if (!ownSectorTo[index].equals(BLANK)) {
					routingCarrierVO.setOwnSectorTo(ownSectorTo[index]);
				} else {
					routingCarrierVO.setOwnSectorTo(BLANK);
				}

				if (!oalSectorFrm[index].equals(BLANK)) {
					routingCarrierVO.setOalSectorFrm(oalSectorFrm[index]);
				} else {
					routingCarrierVO.setOalSectorFrm(BLANK);
				}

				if (!oalSectorTo[index].equals(BLANK)) {
					routingCarrierVO.setOalSectorTo(oalSectorTo[index]);
				} else {
					routingCarrierVO.setOalSectorTo(BLANK);
				}

				if (!carrierCode[index].equals(BLANK)) {
					routingCarrierVO.setCarrier(carrierCode[index]);
				} else {
					routingCarrierVO.setCarrier(BLANK);
				}
				
				if (!validFrom[index].equals(BLANK)) {
					routingCarrierVO.setValidFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(validFrom[index]));
				} else {
					routingCarrierVO.setValidFrom(null);
				}
				if (!validTo[index].equals(BLANK)) {
					routingCarrierVO.setValidTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(validTo[index]));
				} else {
					routingCarrierVO.setValidTo(null);
				}
				
				
			++index;
		}
			}
		//Added by A-4809 for bug ICMN-3304
		if(rowId!=null && rowId.length >0){
		/** Changing flag values for selected rows */
		for (String rowCount : rowId) {

			 index = 0;
			log.log(Log.FINE, "Selected row is: ", rowCount);
			for (RoutingCarrierVO routingCarrierVO : routingCarrierVOs) {

				if (index == Integer.valueOf(rowCount)
						&& "I".equals(routingCarrierVO.getOperationFlag())) {
					routingCarrierVOstoBeRemoved.add(routingCarrierVO);

				} else if (index == Integer.parseInt(rowCount)) {
					routingCarrierVO.setOperationFlag("D");
				}
				++index;
			}
		}
		}
		routingCarrierVOs.removeAll(routingCarrierVOstoBeRemoved);
		routingCarriersession.setRoutingCarrierVOs(routingCarrierVOs);
		}
		invocationContext.target = DELETEDETAILS_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	

	}


}
