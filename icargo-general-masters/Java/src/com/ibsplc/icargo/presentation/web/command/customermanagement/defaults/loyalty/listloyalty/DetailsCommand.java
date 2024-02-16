/*
 * DetailsCommand.java Created on Apr 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.listloyalty;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListLoyaltySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListLoyaltyForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class DetailsCommand  extends BaseCommand {

    private static final String VIEW_SUCCESS = "view_success";
    private static final String MODULE = "customermanagement.defaults";
    private static final String SCREENID ="customermanagement.defaults.listloyalty";
    private static final String SCREEN_ID = "customermanagement.defaults.maintainloyalty";

    
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("customermanagement");
    	log.entering("View Command","-------ListLoyalty");   
    	ListLoyaltyForm form = (ListLoyaltyForm)invocationContext.screenModel;
    	MaintainLoyaltySession session = getScreenSession(MODULE,SCREEN_ID);
    	ListLoyaltySession listSession = getScreenSession(MODULE,SCREENID);

 		Collection<LoyaltyProgrammeVO> loyaltyProgrammeVOs = listSession.getLoyaltyDetails();
 		ArrayList<String> loyaltyProgrammes = new ArrayList<String>();
 		
 		String[] checked =form.getSelectedProgramme();
 		
 		
 		

 		
 		for(LoyaltyProgrammeVO loyaltyProgrammeVO:loyaltyProgrammeVOs){
 			String loyaltyprgcode=loyaltyProgrammeVO.getLoyaltyProgrammeCode();
 			loyaltyProgrammes.add(loyaltyprgcode);			
 		}

 		ArrayList<String> selectedLoyaltyPrgs = new ArrayList<String>();
 		
    for(int i=0;i<checked.length;i++){
        	log.log(Log.FINE,"inside for------------");
        	String selectedLoyalty = loyaltyProgrammes.get(Integer.parseInt(checked[i]));
        	if(!selectedLoyaltyPrgs.contains(selectedLoyalty)){
        		
        		selectedLoyaltyPrgs.add(selectedLoyalty);
        	   	
        	}
        }
        session.setLoyaltyNames(selectedLoyaltyPrgs);
        session.setPageURL("listloyalty");
        form.setFromList("");
     
        invocationContext.target=VIEW_SUCCESS;
    	}
    }

