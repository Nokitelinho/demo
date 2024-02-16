/*
 * UpdateSegmentCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import java.util.ArrayList;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command is used
 * @author A-1754
 *
 */
public class UpdateSegmentCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("UpdateSegmentCommand");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("UpdateSegmentCommand","execute");
		MaintainProductForm maintainProductForm= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		updateSegmentStatus(session,maintainProductForm);
		//updateSegmentRowData(session,maintainProductForm);
		log.exiting("UpdateSegmentCommand","execute");
	}
	/**
	 * This fucntion tracks and updates the changes in the segment displayed as a part of
	 * displaying product details
	 * @param maintainProductForm
	 * @param session
	 *
	 */
	private void updateSegmentStatus(
			MaintainProductSessionInterface session,
			MaintainProductForm maintainProductForm){    	
		
		String hiddenFlags[] = maintainProductForm.getSegmentOperationFlag();
		for(String p : hiddenFlags){
        	log.log(Log.FINE, "UpdateSegmentCommand from PAGE----> ", p);
        }
		ArrayList<RestrictionSegmentVO> collNew =new ArrayList<RestrictionSegmentVO>();
		String[] segmentData = maintainProductForm.getSegment();
		
		int count = 0;
		log.log(Log.FINE, "getProductSegmentVOs---&&&&&&&&&&-> ", session.getProductSegmentVOs());
		if(session.getProductSegmentVOs()!=null){
			log.log(Log.FINE, "getProductSegmentVOs----> ", session.getProductSegmentVOs());
			String[] isRowModified = maintainProductForm.getIsSegmentRowModified();  
			ArrayList<RestrictionSegmentVO> voList =(ArrayList<RestrictionSegmentVO>)session.getProductSegmentVOs();
			//ArrayList<RestrictionSegmentVO> newVoList =new ArrayList<RestrictionSegmentVO>(session.getProductSegmentVOs());
			ArrayList<RestrictionSegmentVO> listAfterListing =(ArrayList<RestrictionSegmentVO>)session.getSegmentAfterListing();
			
			
			
			
//			log.log(Log.FINE,"segmentData---*********->>>>"+segmentData);
			for(RestrictionSegmentVO restrictionSegmentVO : voList) {
				RestrictionSegmentVO segmentVo = (RestrictionSegmentVO)voList.get(count);
				if(RestrictionSegmentVO.OPERATION_FLAG_DELETE.equals(hiddenFlags[count])) {		
					restrictionSegmentVO.setOperationFlag(RestrictionSegmentVO.OPERATION_FLAG_DELETE);
					collNew.add(restrictionSegmentVO);
				}
				else if("NOOP".equals(hiddenFlags[count])) {
					log.log(Log.FINE,"<------  NOOP OPERATIONS   ----> " );
				}
				else if(RestrictionSegmentVO.OPERATION_FLAG_UPDATE.equals(hiddenFlags[count])) {
					restrictionSegmentVO.setOperationFlag(RestrictionSegmentVO.OPERATION_FLAG_UPDATE);
					if(segmentData!=null){   
						StringTokenizer tok = new StringTokenizer(segmentData[count],"-");
						int count1=0;
						String[] seperateToken = new String[2]; 
						seperateToken[0]="";
						seperateToken[1]="";
						while(tok.hasMoreTokens()){									
							seperateToken[count1] = tok.nextToken();
							count1++;
						}    
						restrictionSegmentVO.setDestination(seperateToken[1]);
						restrictionSegmentVO.setOrigin(seperateToken[0]);  	
					}
					restrictionSegmentVO.setIsRestricted(segmentVo.getIsRestricted());
					log
							.log(
									Log.FINE,
									"<------ AFTER  restrictionSegmentVO OPERATION_FLAG_UPDATE  ----> ",
									restrictionSegmentVO);
					collNew.add(restrictionSegmentVO);
				}
				count++;
			}
			
			
			
			/*
			if(isRowModified!=null){
				for(int i=0;i<isRowModified.length;i++){
					if("1".equals(isRowModified[i])){    			
						RestrictionSegmentVO segmentVo = (RestrictionSegmentVO)voList.get(i);
						newVoList.remove(segmentVo);
						RestrictionSegmentVO newVo = new RestrictionSegmentVO();
						newVo.setOrigin(segmentVo.getOrigin());
						newVo.setDestination(segmentVo.getDestination());
						newVo.setOperationFlag(segmentVo.getOperationFlag());
						newVo.setIsRestricted(segmentVo.getIsRestricted());
						newVo.setOperationFlag(ProductVO.OPERATION_FLAG_UPDATE); 
						RestrictionSegmentVO oldVo = (RestrictionSegmentVO)listAfterListing.get(i);
						oldVo.setOperationFlag("D");
						newVoList.add(newVo);
					}
				}
			}*/
			
		}
	
		log.log(Log.FINE, "collNew---*********b4 insert->>>>", collNew);
		for(int i=0;i<hiddenFlags.length-1;i++) {
			if("I".equals(hiddenFlags[i])) {
				
				log.log(Log.FINE,"<------INSERT  OPERATIONS   ----> " );
//				log.log(Log.FINE,"hiddenFlags---->>>>"+hiddenFlags);
				RestrictionSegmentVO restrictionSegmentVO = new RestrictionSegmentVO();
				if(segmentData!=null){   
					StringTokenizer tok = new StringTokenizer(segmentData[i],"-");
					int count1=0;
					String[] seperateToken = new String[2]; 
					seperateToken[0]="";
					seperateToken[1]="";
					while(tok.hasMoreTokens()){									
						seperateToken[count1] = tok.nextToken();
						count1++;
					}    
					restrictionSegmentVO.setDestination(seperateToken[1]);
					restrictionSegmentVO.setOrigin(seperateToken[0]);  	
					log.log(Log.FINE, "seperateToken[0]----------*->>>>",
							seperateToken);
					log.log(Log.FINE, "seperateToken[1]----------*->>>>",
							seperateToken);
				}
				restrictionSegmentVO.setOperationFlag("I");
				collNew.add(restrictionSegmentVO);	
			}
		}
		
		log.log(Log.FINE, "collNew---*********->>>>", collNew);
		session.setProductSegmentVOs(collNew);    	
		
	}
	/*
	 * adding segments in the screen through Add link
	 * @param session
	 * @param maintainProductForm
	 */
	private void updateSegmentRowData(
			MaintainProductSessionInterface session,
			MaintainProductForm maintainProductForm){

		if(session.getProductSegmentVOs()!=null){
			String[] segmentData = maintainProductForm.getSegment();
			if(segmentData!=null){
				ArrayList<RestrictionSegmentVO> newVoList =new ArrayList<RestrictionSegmentVO>(session.getProductSegmentVOs());
				int outerCount=-1;
				//Iterator it = newVoList.iterator();
				for(RestrictionSegmentVO segmentVo : newVoList){
					outerCount++;

					if(ProductVO.OPERATION_FLAG_INSERT.equals(segmentVo.getOperationFlag())
							|| ProductVO.OPERATION_FLAG_UPDATE.equals(segmentVo.getOperationFlag()) ){
						if(!"".equals(segmentData[outerCount])){

							StringTokenizer tok = new StringTokenizer(segmentData[outerCount],"-");
							int count=0;
							String[] seperateToken = new String[2];
							seperateToken[0]="";
							seperateToken[1]="";
							while(tok.hasMoreTokens()){
								seperateToken[count] = tok.nextToken();
								count++;
							}
							segmentVo.setDestination(seperateToken[1]);
							segmentVo.setOrigin(seperateToken[0]);

						}
					}

				}
				session.setProductSegmentVOs(newVoList);

			}
		}

	}
}
