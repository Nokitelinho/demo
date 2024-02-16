	/*
	 * ViewProrationCommand.java Created on Sep 19, 2008
	*
	* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	*
	* This software is the proprietary information of IBS Software Services (P) Ltd.
	* Use is subject to license terms.
	*/
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.prorationlog;


	
	import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;

	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProrationLogSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProrationLogForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
	import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	* @author A-3229
	* Command class for listing 
	*
	* Revision History
	*
	* Version      Date           Author          		    Description
	*
	*  0.1         Sep 19, 2008    A-3229		 		   Initial draft
	*/
	public class ViewProrationCommand extends BaseCommand {
		/**
		 * Logger and the file name
		 */
		
		private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
		
		
		private static final String CLASS_NAME = "ViewProrationCommand";
		
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.prorationlog";
			
		private static final String VIEWPRORATION_SUCCESS = "viewproration_success";
		private static final String	VIEWPRORATION_FAILURE ="viewproration_failure";
		private static final String VOID_MAILBAGS =	"mailtracking.mra.defaults.prorationlog.msg.err.voidmailbags";
		
		/**
		 * Execute method
		 *
		 * @param invocationContext InvocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
												throws CommandInvocationException {
			log.entering(CLASS_NAME, "execute");
			
			ProrationLogSession prorationLogSession=(ProrationLogSession)getScreenSession(MODULE_NAME, SCREEN_ID);
			
			MRAProrationLogForm mraProrationLogForm=(MRAProrationLogForm)invocationContext.screenModel;
			
			log.log(Log.INFO, "session----------", prorationLogSession.getMailProrationLogVOs());
				ArrayList<MailProrationLogVO> mailProrationLogVOs=null;
				 if(prorationLogSession.getMailProrationLogVOs()!=null && prorationLogSession.getMailProrationLogVOs().size()>0){
					 mailProrationLogVOs=new ArrayList<MailProrationLogVO>(prorationLogSession.getMailProrationLogVOs());
			   		}
				
				 String index[]=null;
				 log.log(Log.INFO, "form.getCheck() ", mraProrationLogForm.getCheckBoxLog());
					if(mraProrationLogForm.getCheckBoxLog()!=null ){
			   		 log.log(Log.INFO,"form.getCheck() not null");
			   			index=mraProrationLogForm.getCheckBoxLog();
			   		}
					
					
					 if (index!=null)
					  {
			   		//To fetch the selected row and its details been put in to session
			   		for(int i=0;i<index.length;i++)
					{
			   			log.log(Log.INFO,"inside loop");
						int ind=Integer.parseInt(index[i]);
						MailProrationLogVO mailProrationLogVO=null;
						mailProrationLogVO=mailProrationLogVOs.get(ind-1);
						prorationLogSession.setSelectedDespatchDetails(mailProrationLogVO);
						log.log(Log.INFO, "inside loop ", prorationLogSession.getSelectedDespatchDetails());
					}
			   		   }
			   		
			   		if(mailProrationLogVOs!=null){
						Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
						for(MailProrationLogVO mailProrationLogVO:mailProrationLogVOs){
							if(MRAConstantsVO.VOID.equals(mailProrationLogVO.getBlgStatus())){
								errors = new ArrayList<ErrorVO>();
						        ErrorVO errorVO = new ErrorVO(VOID_MAILBAGS);
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
								
								prorationLogSession.removeSelectedDespatchDetails();
			                    invocationContext.addAllError(errors);					
								invocationContext.target = VIEWPRORATION_FAILURE;
								return; 
							}
						}
					}
					
					
			   		
			   		
			   		mraProrationLogForm.setProrateFlag("Y");
			   		mraProrationLogForm.setFromScreen("PRORATIONLOG");
			   		log.log(Log.INFO, "DSN--", mraProrationLogForm.getDsn());
				invocationContext.target = VIEWPRORATION_SUCCESS;		       

		
					log.exiting(CLASS_NAME, "execute");
		}
		
		
	}






