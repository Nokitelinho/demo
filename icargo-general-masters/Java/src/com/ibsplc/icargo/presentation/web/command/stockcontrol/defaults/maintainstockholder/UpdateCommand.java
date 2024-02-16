/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1870
 *
 */
public class UpdateCommand extends BaseCommand{
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String OPERATION_FLAG_DELETE = "D";
	private static final String OPERATION_FLAG_UPDATE = "U";
	private static final String STOCK_APPROVER_CODE="approver";
	private Log log = LogFactory.getLogger("MAINTAINSTOCKHOLDER");
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)    
	throws CommandInvocationException {
		log.entering("UpdateCommand","**************************entering  update  command***************************");		
		 /**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
		MaintainStockHolderForm maintainStockHolderForm=(MaintainStockHolderForm)invocationContext.screenModel;
		// added by a-5174 for BUG icrd-32653
		session.setStockApprovercode(STOCK_APPROVER_CODE);
		
		
		
		ArrayList<StockVO> allvo = (ArrayList<StockVO>)session.getStockVO();
		log.log(Log.FINE, "****************allvo ", allvo);
		if(allvo!=null){
				
		String[] opFlag=maintainStockHolderForm.getOpFlag();
		//log.log(Log.FINE,"****************operational flag"+opFlag);           
	
		String[] docType=maintainStockHolderForm.getDocType();
		String[] docSubType=maintainStockHolderForm.getDocSubType();
		String[] approver=maintainStockHolderForm.getApprover();
		String[] reOrderLevel=maintainStockHolderForm.getReorderLevel();
		String[] reOrderQuantity=maintainStockHolderForm.getReorderQuantity();
		String[] remarks=maintainStockHolderForm.getRemarks();
		String[] awbPrefixTokens=maintainStockHolderForm.getAwbPrefix(); 
		String checkedReorder=maintainStockHolderForm.getCheckedReorder();
		//Added for Auto processing
		String[] autoprocessQuantity=maintainStockHolderForm.getAutoprocessQuantity();
		log.log(Log.FINE, "------ApproverCode ", maintainStockHolderForm.getApproverCode());
		StringTokenizer tok = new StringTokenizer(checkedReorder,"-");
		int count=0;
		String[] seperateExt = new String[allvo.size()]; 
		
		while(tok.hasMoreTokens()){									
			seperateExt[count] = tok.nextToken();
			count++;
		}
		String checkedAutoRequest=maintainStockHolderForm.getCheckedAutoRequest();

		StringTokenizer token = new StringTokenizer(checkedAutoRequest,"-");
		count=0;
		log.log(Log.FINE, "allvo size-------", allvo.size());
		String[] seperateInt = new String[allvo.size()];
		while(token.hasMoreTokens()){									
			seperateInt[count] = token.nextToken();
			log.log(Log.FINE, "Count-------", count);
			count++;
		}
		String checkedAutoPopulate=maintainStockHolderForm.getCheckedAutoPopulate();
		StringTokenizer tokens = new StringTokenizer(checkedAutoPopulate,"-");
		count=0;
		String[] autoPopulateflags = new String[allvo.size()];
		while(tokens.hasMoreTokens()){									
			autoPopulateflags[count] = tokens.nextToken();
			count++;
		}
		int i=-1;
		//int awbPrefixCount=0;
		for(StockVO vo : allvo){
			log.log(Log.FINE, "In For loop-------", i);
				//if(!OPERATION_FLAG_DELETE.equalsIgnoreCase(vo.getOperationFlag())){
				i++;
			//}
			if(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
				log.log(Log.FINE, "---------------", docType.length);
				if(!"".equals(docType[i])){
					log.log(Log.FINE, "******************doctype", docType, i);
					vo.setDocumentType(docType[i]);
					
				} 
				
				if(!"".equals(docSubType[i])){
					log.log(Log.FINE, "******************docSubType",
							docSubType, i);
					vo.setDocumentSubType(docSubType[i]);
					
				}
				
				log.log(Log.FINE, "******************approver", approver, i);
				vo.setStockApproverCode(upper(approver[i]));
				if(maintainStockHolderForm.getApproverCode().equals(String.valueOf(i))){         
					session.setApproverCode(approver[i]);
				}
				if(!"".equals(reOrderLevel[i])){
					log.log(Log.FINE, "******************reOrderLevel",
							reOrderLevel, i);
					vo.setReorderLevel(Integer.parseInt(reOrderLevel[i]));
					
				}else{
					vo.setReorderLevel(0);
				}
				if(!"".equals(reOrderQuantity[i])){
					log.log(Log.FINE, "******************reOrderQuantity",
							reOrderQuantity, i);
					vo.setReorderQuantity(Integer.parseInt(reOrderQuantity[i]));
					
				}else{
					vo.setReorderQuantity(0);
				}
				
				log.log(Log.FINE, "******************remarks", remarks, i);
				vo.setRemarks(remarks[i]);
				//Added for Auto processing	
				if(!"".equals(autoprocessQuantity[i])){
					log.log(Log.FINE, "******************autoprocessQuantity",
							autoprocessQuantity, i);
					vo.setAutoprocessQuantity(Integer.parseInt(autoprocessQuantity[i]));
					
				}else{
					vo.setAutoprocessQuantity(0);
				}
				
				if ("true".equals(seperateExt[i])){
					log.log(Log.FINE, "******************flag", seperateExt, i);
					vo.setReorderAlertFlag(true);
				}else{
					vo.setReorderAlertFlag(false);
				}
				if ("true".equals(autoPopulateflags[i])){
					log.log(Log.FINE, "******************AutoPopulateflag", autoPopulateflags, i);
					vo.setAutoPopulateFlag(true);
				}else{
					log.log(Log.FINE, "******************AutoPopulateflag", autoPopulateflags, i);
					vo.setAutoPopulateFlag(false);
				}
				if("true".equals(seperateInt[i])){
					log.log(Log.FINE, "******************flag", seperateInt, i);
					vo.setAutoRequestFlag(true);
				}else{
					vo.setAutoRequestFlag(false);
				}
				if(awbPrefixTokens.length>i && awbPrefixTokens[i]!=null 
						&& awbPrefixTokens[i].trim().length()>0){					
					String airlineIdentifier=awbPrefixTokens[i];
					vo.setAirlineIdentifier(Integer.parseInt(airlineIdentifier));
					
				}
				log.log(Log.FINE, "---------reached outside cond-------", vo);
			}
			log.log(Log.FINE, "---------reached outside cond-------", allvo);
		}
			session.setStockVO(allvo);
		}
		
		handleDataUpdation(session,maintainStockHolderForm);
		
		log.exiting("UpdateCommand", "**********exiting  update  command*** allvo*********************" + allvo);
	}
		
	/**
	 * The function is to incorporate the updations made by the user in the mile stone table.
	 * The function must be called each time an addition, deletion(on Milestone Table) or save is done 
	 * @param mintainProductForm
	 * @param sessionModel
	 * @return
	 */
	
	private void handleDataUpdation(
			MaintainStockHolderSession session,
			MaintainStockHolderForm maintainStockHolderForm){
		Collection<StockVO> newSet=null;
		ArrayList<StockVO> allvo = (ArrayList<StockVO>)session.getStockVO();
		log.log(Log.FINE, "****************allvo ", allvo);
		if(allvo!=null){
		newSet = new ArrayList<StockVO>();		
		String[] isRowmodified = maintainStockHolderForm.getIsRowModified();
		if(isRowmodified!=null){
			String[] opFlag=maintainStockHolderForm.getOpFlag();
			//log.log(Log.FINE,"****************operational flag"+opFlag);
			String[] docType=maintainStockHolderForm.getDocType();
			String[] docSubType=maintainStockHolderForm.getDocSubType();
			String[] approver=maintainStockHolderForm.getApprover();
			String[] reOrderLevel=maintainStockHolderForm.getReorderLevel();
			String[] reOrderQuantity=maintainStockHolderForm.getReorderQuantity();
			String[] remarks=maintainStockHolderForm.getRemarks();
			String[] awbPrefixTokens=maintainStockHolderForm.getAwbPrefix(); 
			String checkedReorder=maintainStockHolderForm.getCheckedReorder();
			log.log(Log.FINE, "***************checkedReorder--------",
					checkedReorder);
			//Added for Auto processing
			String[] autoprocessQuantity=maintainStockHolderForm.getAutoprocessQuantity();
			
			StringTokenizer tok = new StringTokenizer(checkedReorder,"-");
			int count=0;
			String[] seperateExt = new String[allvo.size()]; 
			
			while(tok.hasMoreTokens()){									
				seperateExt[count] = tok.nextToken();
				count++;
			}
			String checkedAutoRequest=maintainStockHolderForm.getCheckedAutoRequest();
			log.log(Log.FINE, "***************checkedAutoRequest--------",
					checkedAutoRequest);
			StringTokenizer token = new StringTokenizer(checkedAutoRequest,"-");
			count=0;
			String[] seperateInt = new String[allvo.size()]; 
			
			while(token.hasMoreTokens()){									
				seperateInt[count] = token.nextToken();
				count++;
			}
			String checkedAutoPopulate=maintainStockHolderForm.getCheckedAutoPopulate();
			StringTokenizer tokens = new StringTokenizer(checkedAutoPopulate,"-");
			count=0;
			String[] autoPopulateflags = new String[allvo.size()];
			while(tokens.hasMoreTokens()){									
				autoPopulateflags[count] = tokens.nextToken();
				count++;
			}
			for(int i=0;i<isRowmodified.length;i++){
			StockVO vo = (StockVO)allvo.get(i);
			//System.out.println("***************Row Moified----->>>----i----"+i);
			//System.out.println("***************Row Moified----->>>--------"+isRowmodified[i]);
			//System.out.println("***************vo.getOperationFlag()----->>>--------"+isRowmodified[i]);
			if(("1").equals(isRowmodified[i]) && !"I".equals(vo.getOperationFlag())){
				
				if(vo.getDocumentType().equals(docType[i]) && 
						vo.getDocumentSubType().equals(docSubType[i])){
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
					if(!"".equals(docType[i])){
						
						vo.setDocumentType(docType[i]);
						
					}
					if(!"".equals(docSubType[i])){
						//System.out.println("*********^^*^U*^^^^^************docSubType"+docSubType[i]);
						vo.setDocumentSubType(docSubType[i]);
						
					}
					//System.out.println("**********^*^U*^^^^^^***********approver"+approver[i]);
					vo.setStockApproverCode(upper(approver[i]));
						
					
				/*	if(!"".equals(reOrderLevel[i])){
						System.out.println("************^*^U*^^^^^^*********reOrderLevel"+reOrderLevel[i]);
						vo.setReorderLevel(Integer.parseInt(reOrderLevel[i]));
						
					}
					if(!"".equals(reOrderQuantity[i])){
						System.out.println("************^U********reOrderQuantity"+reOrderQuantity[i]);
						vo.setReorderQuantity(Integer.parseInt(reOrderQuantity[i]));
						
					}*/
					if(!"".equals(reOrderLevel[i])){
						log.log(Log.FINE,
								"*********^*^U*^^^^^^***********reOrderLevel",
								reOrderLevel, i);
						vo.setReorderLevel(Integer.parseInt(reOrderLevel[i]));
						
					}else{
						vo.setReorderLevel(0);
					}
					if(!"".equals(reOrderQuantity[i])){
						log
								.log(
										Log.FINE,
										"************^*^U*^^^^^^********reOrderQuantity",
										reOrderQuantity, i);
						vo.setReorderQuantity(Integer.parseInt(reOrderQuantity[i]));
						
					}else{
						vo.setReorderQuantity(0);
					}
					
					
					vo.setRemarks(remarks[i]);
						
					
					if ("true".equals(seperateExt[i])){
						
						vo.setReorderAlertFlag(true);
					}else{
						vo.setReorderAlertFlag(false);
					}
					if("true".equals(seperateInt[i])){
					
						vo.setAutoRequestFlag(true);
					}else{
						vo.setAutoRequestFlag(false);
					}
					if ("true".equals(autoPopulateflags[i])){
						vo.setAutoPopulateFlag(true);
					}else{
						vo.setAutoPopulateFlag(false);
					}
					//Added as part of ICRD-81783
					if(awbPrefixTokens != null && awbPrefixTokens.length > 0) {
						if(awbPrefixTokens[i]!=null && awbPrefixTokens[i].trim().length()>0){						
							String airlineIdentifier=awbPrefixTokens[i];
							vo.setAirlineIdentifier(Integer.parseInt(airlineIdentifier));						
						}
					}				
					
					//Added for Autoprocessing
					if(!"".equals(autoprocessQuantity[i])){
						
						vo.setAutoprocessQuantity(Integer.parseInt(autoprocessQuantity[i]));
						
					}else{
						vo.setAutoprocessQuantity(0);
					}
			
					
				}else{
				    vo.setOperationFlag(OPERATION_FLAG_DELETE);
				   
				    
				    StockVO newVO=new StockVO();
				    newVO.setOperationFlag(OPERATION_FLAG_INSERT);
				    if(!"".equals(docType[i])){
					
						newVO.setDocumentType(docType[i]);
						
					}
					if(!"".equals(docSubType[i])){
						
						newVO.setDocumentSubType(docSubType[i]);
						
					}
				    
					
						newVO.setStockApproverCode(upper(approver[i]));
						
					
				/*	if(!"".equals(reOrderLevel[i])){
						System.out.println("**********I********reOrderLevel"+reOrderLevel[i]);
						newVO.setReorderLevel(Integer.parseInt(reOrderLevel[i]));
						
					}
					if(!"".equals(reOrderQuantity[i])){
						System.out.println("*********I*********reOrderQuantity"+reOrderQuantity[i]);
						newVO.setReorderQuantity(Integer.parseInt(reOrderQuantity[i]));
						
					}*/
					if(awbPrefixTokens != null && awbPrefixTokens.length > 0) {
						if(awbPrefixTokens[i]!=null && awbPrefixTokens[i].trim().length()>0){						
							String airlineIdentifier=awbPrefixTokens[i];
							newVO.setAirlineIdentifier(Integer.parseInt(airlineIdentifier));						
						}
					}		
					if(!"".equals(reOrderLevel[i])){
					
						vo.setReorderLevel(Integer.parseInt(reOrderLevel[i]));
						
					}else{
						vo.setReorderLevel(0);
					}
					if(!"".equals(reOrderQuantity[i])){
						
						vo.setReorderQuantity(Integer.parseInt(reOrderQuantity[i]));
						
					}else{
						vo.setReorderQuantity(0);
					}
					
					
					newVO.setRemarks(remarks[i]);
					
					if ("true".equals(seperateExt[i])){
					
						newVO.setReorderAlertFlag(true);
					}else{
						newVO.setReorderAlertFlag(false);
					}
					if("true".equals(seperateInt[i])){
						
						newVO.setAutoRequestFlag(true);
					}else{
						newVO.setAutoRequestFlag(false);
					}
					if ("true".equals(autoPopulateflags[i])){
					
						vo.setAutoPopulateFlag(true);
					}else{
						vo.setAutoPopulateFlag(false);
					}
					//Added for Autoprocessing
					if(!"".equals(autoprocessQuantity[i])){
						
						vo.setAutoprocessQuantity(Integer.parseInt(autoprocessQuantity[i]));
						
					}else{
						vo.setAutoprocessQuantity(0);
					}
					
					newSet.add(newVO);
				    
				}
				
			}
			newSet.add(vo);	
			
		}
			session.setStockVO(newSet);
		}
		}
		
		
			
		
	}
	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}
	
}
