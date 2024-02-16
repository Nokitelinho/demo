package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;

/**
 * This command is used
 * @author A-1754
 *
 */
public class UpdateSegmentCommand extends BaseCommand {
/**
 * The execute method in BaseCommand
 * @author A-1754
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainSubProductSessionInterface session = getScreenSession(
					"product.defaults", "products.defaults.maintainsubproducts");	
		updateSegmentStatus(session,maintainSubProductForm);
		updateSegmentRowData(session,maintainSubProductForm);
		
		
	}
	/**
     * This fucntion tracks and updates the changes in the segment displayed as a part of 
     * displaying product details
     * @param maintainSubProductForm
     * @param session
     * @return void
     * @exception none
     * 
     */
      private void updateSegmentStatus(
    		  MaintainSubProductSessionInterface session,
    		  MaintainSubProductForm maintainSubProductForm){    	
    	if(session.getSegmentVOs()!=null){
    	String[] isRowModified = maintainSubProductForm.getIsSegmentRowModified();  
    	ArrayList<RestrictionSegmentVO> voList =(ArrayList<RestrictionSegmentVO>)session.getSegmentVOs();
    	ArrayList<RestrictionSegmentVO> newVoList =new ArrayList<RestrictionSegmentVO>(session.getSegmentVOs());
    	ArrayList<RestrictionSegmentVO> listAfterListing =(ArrayList<RestrictionSegmentVO>)session.getSegmentAfterListing();
    	
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
    	}
    	session.setSegmentVOs(newVoList);    	
    	}
    	
    }

      /**
       * This fucntion tracks and updates the changes in the segment displayed as a part of 
       * adding segments in the screen through Add link
       * @param session
       * @param maintainSubProductForm
       */
    private void updateSegmentRowData(
    		MaintainSubProductSessionInterface session,
  		  MaintainSubProductForm maintainSubProductForm){
       
    	if(session.getSegmentVOs()!=null){
    	String[] segmentData = maintainSubProductForm.getSegment();
    	if(segmentData!=null){    	
    	ArrayList<RestrictionSegmentVO> newVoList =new ArrayList<RestrictionSegmentVO>(session.getSegmentVOs());
    	int cnt = -1;
    	Iterator it = newVoList.iterator();
    	while(it.hasNext()){    	
    		cnt++;
    		RestrictionSegmentVO segmentVo = (RestrictionSegmentVO)it.next();
    		   		
    			if(ProductVO.OPERATION_FLAG_INSERT.equals(segmentVo.getOperationFlag())
    					|| ProductVO.OPERATION_FLAG_UPDATE.equals(segmentVo.getOperationFlag()) ){
    				if(!"".equals(segmentData[cnt])){  
    				
    					StringTokenizer tok = new StringTokenizer(segmentData[cnt],"-");
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
    	session.setSegmentVOs(newVoList);
    	
    	}
    	}
    	
    }
}
