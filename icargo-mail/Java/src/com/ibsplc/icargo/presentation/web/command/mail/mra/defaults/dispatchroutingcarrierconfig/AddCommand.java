package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dispatchroutingcarrierconfig;
/**
 * AddCommand
 * 
 * @author A-4452
 * 
 */
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAAWBRoutingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
//import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class AddCommand extends BaseCommand {
	
	private static final String CLASS_NAME = "AddCommand";

	/*
	 * The module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/*
	 * The screen id
	 */

	private static final String SCREENID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";

	/**
	 * Target Action
	 */

	private static final String ADDDETAILS_SUCCESS = "adddetails_success";

	//private static final String ADDDETAILS_FAILURE = "adddetails_failure";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String BLANK = "";

	

	/**
	 * 
	 * execute method
	 * 
	 * @param invocationContext
	 * 
	 * @throws CommandInvocationException
	 * 
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		int index = 0;
		/**
		 * Obtaining form
		 */
		MRARoutingCarrierForm mraRoutingCarrierForm = (MRARoutingCarrierForm) invocationContext.screenModel;
		/**
		 * Obtaining session
		 */			
		MRARoutingCarrierSession routingCarriersession = getScreenSession(MODULE_NAME,SCREENID); 
		//MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();		
		Collection<RoutingCarrierVO> routingCarrierVOs=null;  
		
		String[] rowId = mraRoutingCarrierForm.getCheckBoxForRoutingCarrier();
		
		Collection<RoutingCarrierVO> newRoutingCarrierVOs = new ArrayList<RoutingCarrierVO>();
		routingCarrierVOs = routingCarriersession
				.getRoutingCarrierVOs();

		/** if no collection of VO create new one and one empty VO to it */

		if (routingCarrierVOs != null && routingCarrierVOs.size() > 0) {

			/** getting values from form */
			
			String[] hiddenOpFlag = mraRoutingCarrierForm.getHiddenOpFlag();

			String[] origincode = mraRoutingCarrierForm.getOrigincode();
			String[] destcode = mraRoutingCarrierForm.getDestcode();
			String[] ownSectorFrm = mraRoutingCarrierForm.getOwnSectorFrm();
			String[] ownSectorTo = mraRoutingCarrierForm.getOwnSectorTo();
			String[] oalSectorFrm = mraRoutingCarrierForm.getOalSectorFrm();
			String[] oalSectorTo = mraRoutingCarrierForm.getOalSectorTo();
			String[] carrierCode = mraRoutingCarrierForm.getCarriercode();
			
			log.log(Log.FINE, "--dsnRoutingVOsin add command==>>:\n\n ",
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
				
					}
				++index;
			
				}
			
			log.log(Log.FINE, "dsnRoutingVOs=--=>>:\n\n ", routingCarrierVOs);
			/** Adding an row to selected row of collection */
			if (rowId != null && rowId.length == 1) {

				int rowCount = Integer.parseInt(rowId[0]);
				index = 0;
				// log.log(Log.FINE, "\n\nSelected row is: " + rowCount );

				for (RoutingCarrierVO routingCarrierVO : routingCarrierVOs) {

					if (index == (rowCount + 1)) {
						log.log(Log.FINE, "\n\nSelected row inserted: ",
								rowCount);
						newRoutingCarrierVOs.add(createEmptyDetailsVO(
								mraRoutingCarrierForm, routingCarrierVOs));
					}
					newRoutingCarrierVOs.add(routingCarrierVO);
					++index;
				}
				/** for adding row as last row */
				if (rowCount == routingCarrierVOs.size() - 1) {
					newRoutingCarrierVOs.add(createEmptyDetailsVO(
							mraRoutingCarrierForm, routingCarrierVOs));
				}
			} else if (rowId == null && routingCarrierVOs != null) {
				for (RoutingCarrierVO routingCarrierVO : routingCarrierVOs) {
					newRoutingCarrierVOs.add(routingCarrierVO);
				}
				newRoutingCarrierVOs.add(createEmptyDetailsVO(mraRoutingCarrierForm,
						routingCarrierVOs));
			}
			log.log(Log.FINE, "newDSNRoutingVOs==>>:\n\n ",
					newRoutingCarrierVOs);
			routingCarriersession.setRoutingCarrierVOs(newRoutingCarrierVOs);
			invocationContext.target = ADDDETAILS_SUCCESS; // sets target
		
		

	} else {
		newRoutingCarrierVOs.add(createEmptyDetailsVO(mraRoutingCarrierForm,
				routingCarrierVOs));
		routingCarriersession.setRoutingCarrierVOs(newRoutingCarrierVOs);
		invocationContext.target = ADDDETAILS_SUCCESS; // sets target

	}

	log.exiting(CLASS_NAME, "execute");
	return;
}
	/**
	 * creates an emplty CRAAWBRoutingDetailsVO
	 * 
	 * @param craAWBRoutingVO
	 * @return
	 */      
	private RoutingCarrierVO createEmptyDetailsVO(
			MRARoutingCarrierForm mraRoutingCarrierForm,
			Collection<RoutingCarrierVO> routingCarrierVOs) {		
		log.entering(CLASS_NAME, "createEmptyDetailsVO");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		RoutingCarrierVO routingCarrierVO = new RoutingCarrierVO();
		routingCarrierVO.setCompanyCode(logonAttributes.getCompanyCode());
		routingCarrierVO.setOriginCity(BLANK);
		routingCarrierVO.setDestCity(BLANK);
		routingCarrierVO.setOwnSectorFrm(BLANK);
		routingCarrierVO.setOwnSectorTo(BLANK);
		routingCarrierVO.setOalSectorFrm(BLANK);
		routingCarrierVO.setOalSectorTo(BLANK);
		routingCarrierVO.setCarrier(BLANK);		
		routingCarrierVO.setOperationFlag(CRAAWBRoutingDetailsVO.OPERATION_FLAG_INSERT);
	
		log.exiting(CLASS_NAME, "createEmptyDetailsVO");
		return routingCarrierVO;
	}
}
