/*
 * AddULDNumberCommand.java Created on jan 29, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.recorduldmovement;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 /**
  * 
  * @author A-1936 Karthick.V
  *
  */
public class AddULDNumberCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("ULD_DEFAULTS");
	  
	   /**
	   * The execute method in BaseCommand
	   * @author A-1936
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	
	   public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
          log.entering("ADDULDNUMBERS","ADDULDNUMBERS");
		  log.entering("ADDULDNUMBERS","ADDULDNUMBERS");
		  Collection<ULDMovementVO> uldMovementVos=null;
		 
 		  RecordULDMovementForm  recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;
		  RecordUldMovementSession session = (RecordUldMovementSession)
							getScreenSession( "uld.defaults","uld.defaults.misc.recorduldmovement");
	
		 
         String[] uldNumbers = recordULDMovementForm.getUldNumber();
	        int count=0;
	        String str="";
	       
          if(session.getULDNumbers()==null){
			   log.log(Log.FINE,"...........THERE ARE NO ENTRIES ......");
			  Collection<String> strings= new ArrayList<String>();
			  strings.add(str);
			  session.setULDNumbers(strings);
		   }
          else{
    	    count=0;
    	      String uldNumber=null;
    	       log.log(Log.FINE,"...........Else part.......");
    	       Collection<String> uldNos =new ArrayList<String>();
    	     if(uldNumbers!=null){
			     for(int i=0;i<uldNumbers.length;i++){
				   log.entering("INSIDE THE ADD ULD NUMBER ITERATED ","FROM  FORM");
				   uldNumber=uldNumbers[count];
				   uldNos.add(uldNumber);
				   count++;
		     }
    	    }
			uldNos.add(str);
			session.setULDNumbers(null);
			session.setULDNumbers(uldNos);
			  
		    log.exiting("AddRowCommand","execute");
	        
	}
          invocationContext.target = "save_success";
}
	
}
