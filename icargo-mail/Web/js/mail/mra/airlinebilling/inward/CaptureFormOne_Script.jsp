<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{
	var frm=targetFormName;


	with(targetFormName){

	evtHandler.addEvents("btList","doList()",EVT_CLICK);
	evtHandler.addEvents("btClose","doClose()",EVT_CLICK);
	evtHandler.addEvents("btSave","doSave()",EVT_CLICK);
	evtHandler.addEvents("btClear","doClear()",EVT_CLICK);
	evtHandler.addIDEvents("airlinelov","showAirline()",EVT_CLICK);	
	//evtHandler.addEvents("exgRate","changeExgRate()",EVT_CHANGE);
	
	evtHandler.addIDEvents("clearancePeriodlov","displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
	evtHandler.addEvents("btFormthree","doFormthree()",EVT_CLICK);
	evtHandler.addEvents("btSummary","doSummary()",EVT_CLICK);
	evtHandler.addEvents("btnAccDetails","doAccountDetails()",EVT_CLICK);
	evtHandler.addEvents("btRejectFormOne","doRejectFormOne()",EVT_CLICK);
	evtHandler.addEvents("exgRate","restrictFloat(this,11,5)",EVT_KEYPRESS);
	evtHandler.addEvents("exgRate","populateValues()",EVT_BLUR);
	evtHandler.addEvents("airlineCode","populateAirlineNo(targetFormName)",EVT_BLUR);
	//evtHandler.addEvents("exgRate","restrictFloat(this,8,8)",EVT_KEYPRESS);
	
	}
screenload();

}

function populateValues(){
var operationFlag = document.getElementsByName('operationFlag');
var checkBox = document.getElementsByName('checkBox');
var exgRates = document.getElementsByName('exgRate');
 for (var i = 0; i < checkBox.length; i++){
		  if(operationFlag[i].value!="NOOP"){
				  if(document.getElementsByName('exgRate')[i].value==""){
					document.getElementsByName('exgRate')[i].value="0.00000";
					}
				  if(document.getElementsByName('exgRate')[i].value.charAt(0)=="."){
				  document.getElementsByName('exgRate')[i].value="0"+document.getElementsByName('exgRate')[i].value;
				  }
				 var ind= exgRates[i].value.toString().indexOf('.',0);
					if(ind>0){
					
					
						if(document.getElementsByName('exgRate')[i].value!="0.00000"){
							var whole=exgRates[i].value.toString().substring(0,ind);
							
							 var dec=exgRates[i].value.toString().substring(ind+1,ind+6);
							 
								     if(dec.length==1){
								      dec=dec+"0000";
								     
								      document.getElementsByName('exgRate')[i].value=whole+"."+dec;
								      }
								      if(dec.length==2){
									    dec=dec+"000";
									   
									    document.getElementsByName('exgRate')[i].value=whole+"."+dec;
								      }
								      if(dec.length==3){
									    dec=dec+"00";
									   
									    document.getElementsByName('exgRate')[i].value=whole+"."+dec;
								      }
								      if(dec.length==4){
									    dec=dec+"0";
									    
									    document.getElementsByName('exgRate')[i].value=whole+"."+dec;
								      }
						           }
						    }
				     else{
				     document.getElementsByName('exgRate')[i].value=document.getElementsByName('exgRate')[i].value+".00000";
				     }

		     }
  	}
}


function doRejectFormOne(){

				var checkBox = document.getElementsByName('checkBox');
				var count=0;
				var check=0;
				var errorFlg="N";
				var operationFlag = document.getElementsByName('operationFlag');
				 for (var i = 0; i < checkBox.length; i++){
					   
					         
			  if(operationFlag[i].value!="NOOP" && operationFlag[i].value!="D"){
					    
			if(operationFlag[i].value=="I" ){
				 errorFlg="Y"
					    	
					    				   
					   }
					    
					    
					    				    
					if((checkBox[i].checked)){
							
					check=check+1;
						}
					}
					}
						
						
						if(check==0){
							showDialog({msg:'Please select a row',type:1,parentWindow:self});
								
						}else if(check>1){		
						showDialog({msg:'Please select only one row',type:1,parentWindow:self});
								
							}
						else{
							for (var i=0;i<checkBox.length;i++)
								{
									if(checkBox[i].checked)
									if(operationFlag[i].value!="NOOP" && operationFlag[i].value!="D"){
										{
										
										  count=i;
										  
										}
										}
						 	}
						 	
						 
					 	
		
				 	
				 				
				 				
				 				//alert(count+"--count-"+errorFlg);
				 				
			 				if(errorFlg!="Y"){
	

			targetFormName.elements.selectedRow.value=count;
			targetFormName.elements.buttonFlag.value="rejectinvoice";
			submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.navigatebutton.do');

			}
			else{
											//alert(count+"--count****-"+errorFlg);
							showDialog({msg:'Save the Data First',type:1,parentWindow:self});					
				
			}


		}





		



}

function doAccountDetails(){

	var checkBox = document.getElementsByName('checkBox');
				var count=0;
				var check=0;
				var errorFlg="N";
				var operationFlag = document.getElementsByName('operationFlag');
				 for (var i = 0; i < checkBox.length; i++){
					   
					         
			  if(operationFlag[i].value!="NOOP" && operationFlag[i].value!="D"){
					    
			if(operationFlag[i].value=="I" ){
				 errorFlg="Y"
					    	
					    				   
					   }
					    
					    
					    				    
					if((checkBox[i].checked)){
							
					check=check+1;
						}
					}
					}
						
						
						if(check==0){
						showDialog({msg:'Please select a row',type:1,parentWindow:self});
								
						}else if(check>1){	
showDialog({msg:'Please select only one row',type:1,parentWindow:self});						
								
							}
						else{
							for (var i=0;i<checkBox.length;i++)
								{
									if(checkBox[i].checked)
									if(operationFlag[i].value!="NOOP" && operationFlag[i].value!="D"){
										{
										
										  count=i;
										  
										}
										}
						 	}
						 	
						 
					 	
		
				 	
				 				
				 				
				 				//alert(count+"--count-"+errorFlg);
				 				
			 				if(errorFlg!="Y"){
	
	
					targetFormName.elements.selectedRow.value=count;
					targetFormName.elements.buttonFlag.value="listaccountinentires";
					submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.navigatebutton.do');

					}else{
								//alert(count+"--count****-"+errorFlg);
								showDialog({msg:'Save the Data First',type:1,parentWindow:self});
							
						}

}

}




function doFormthree(){
		
	
	
		targetFormName.elements.buttonFlag.value="formthree";
		submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.navigatebutton.do');	
	
	
		
		
}



function doSummary(){
	var checkBox = document.getElementsByName('checkBox');
			var count=0;
			var check=0;
			var errorFlg="N";
			var operationFlag = document.getElementsByName('operationFlag');
			 for (var i = 0; i < checkBox.length; i++){
				   
				         
		  if(operationFlag[i].value!="NOOP" && operationFlag[i].value!="D"){
				    
		if(operationFlag[i].value=="I" ){
			 errorFlg="Y"
				    	
				    				   
				   }
				    
				    
				    				    
			if((checkBox[i].checked)){
						
				check=check+1;
					}
				}
			}
					
					
			if(check==0){
					showDialog({msg:'Please select a row',type:1,parentWindow:self});	
							
					}else if(check>1){		
					showDialog({msg:'Please select only one row',type:1,parentWindow:self});
							
						}
					else{
						for (var i=0;i<checkBox.length;i++)
							{
								if(checkBox[i].checked)
								if(operationFlag[i].value!="NOOP" && operationFlag[i].value!="D"){
									{
									
									  count=i;
									  
									}
									}
					 	}
					 	
					 
				 	
	
			 	
			 				
			 				
			 				//alert(count+"--count-"+errorFlg);
			 				
			 				if(errorFlg!="Y"){
			 				//alert(count+"--count////-"+errorFlg);
			 				targetFormName.elements.selectedRow.value=count;
								targetFormName.elements.buttonFlag.value="captureinvoicesummary";
								submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.navigatebutton.do');
								
								}else{
								//alert(count+"--count****-"+errorFlg);
								showDialog({msg:'Save the Data First',type:1,parentWindow:self});	
							
								}
	

	}
	

}



function screenload(){

if(targetFormName.elements.processedFlag.value=='Y'){
/***disable links and save button here*/
 var addlinks=document.getElementById('lnkAdd');
 disableLink(addlinks);
 var delLnks=document.getElementById('lnkDelete');
 disableLink(delLnks);
 
 targetFormName.elements.btSave.disabled=true;

}


disable();
		var chkBoxIds = document.getElementsByName('checkBox');
		
		for(var i = 0; i < chkBoxIds.length; i++){
		
		var operationFlag = document.getElementsByName('operationFlag');
		
		var invStatus=document.getElementsByName('invStatus');
		if(operationFlag[i].value!="D" &&operationFlag[i].value!="NOOP" ) {
				var invStatus=document.getElementsByName('invStatus');
				
				if(invStatus[i].value=="P"){
				        
					targetFormName.elements.invNum[i].disabled=true;
					targetFormName.elements.invDate[i].disabled=true;
					targetFormName.elements.curList[i].disabled=true;
					targetFormName.elements.misAmt[i].disabled=true;
					targetFormName.elements.exgRate[i].disabled=true;
					targetFormName.elements.amtUsd[i].disabled=true;
					
				}
			}
		}
		
}
function addInv()
{
if(targetFormName.linkDisable.value=="N"){

			addTemplateRow('targetWeightRow','targetWeightTableBody','operationFlag');
	

}
}

function deleteInv()
{
var chkBoxIds = document.getElementsByName('checkBox');
	var isError = 1;var processed=0;
	for(var i = 0; i < chkBoxIds.length; i++){
		if(chkBoxIds[i].checked){
		
		
			isError = 0;
			
			
			
			var operationFlag = document.getElementsByName('operationFlag');
			
			
			if(operationFlag[i].value!="D" 
							&&
							operationFlag[i].value!="NOOP" 
				) {
				var invStatus=document.getElementsByName('invStatus');
				if(invStatus[i].value=="P"){
						processed=1;
					}
				}
			
		}
		
	}
	if(processed==1){
	showDialog({msg:'Form1 Details with invoice status Processed cannot be deleted',type:1,parentWindow:self});
	
	}
	if(isError == 1) {
	showDialog({msg:'Please select a row',type:1,parentWindow:self});
		
	}
	if(isError == 0 ){
		if(processed==0)
		
		deleteTableRow('checkBox','operationFlag');
	}
	updateSummary();
}




function showAirline(){

	displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',
		document.forms[1].airlineCode.value,'Airline code','1',
		'airlineCode', 'airlineNo','',0)
 }
function doList(){
submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.list.do');
}




//function for updating the sum on footer
function updateSummary(){
	
	var operationFlag = document.getElementsByName('operationFlag');   //for operation flag
	
	var miscAmount = document.getElementsByName('misAmt');
	
	var blgAmount = document.getElementsByName('amtUsd');	
				
 		var miscAmountArr =  new Array();
 		var blgAmountArr =  new Array();
 		 							
		for(var rowCount=0;rowCount<miscAmount.length;rowCount++) {
			//alert(operationFlag[rowCount].value);
			if(operationFlag[rowCount].value!="D" 
				&&
				operationFlag[rowCount].value!="NOOP" 
				) {
				
				miscAmountArr.push(miscAmount[rowCount]);
				
				blgAmountArr.push(blgAmount[rowCount]);
				
			}

		}		
		
		var operation = new MoneyFldOperations();
		
		operation.startBatch();
		
		operation.sumUpMonies(miscAmountArr);
		operation.sumUpMonies(blgAmountArr,'afterSum');		
		operation.endBatch();	
		
	
	
}

function afterSum()
{	

	var resultMap = arguments[1];	
	
	firstResult  = resultMap.getResult(0);
	secondResult=resultMap.getResult(1);
	
	
	document.getElementById("netMisc").innerHTML= firstResult;
	document.getElementById("netUsd").innerHTML= secondResult;
	
	
		
	
}
function changeExgRate(){
//alert(targetFormName.exgRate[0].value);

}

function change(rowcount){
	var exgrate=document.getElementsByName("exgRate");
	var misamt=document.getElementsByName("misAmt");
	var operation = new MoneyFldOperations();
	//alert(targetFormName.misAmt[rowcount].value);
	targetFormName.elements.rowCount.value=rowcount;
	operation.multiplyRawMonies(misamt[rowcount].value,1/exgrate[rowcount].value,
	targetFormName.elements.blgCurCode.value,'false','onMultiplyAdjAmt');


}


function onMultiplyAdjAmt(){
	setMoneyValue(targetFormName.amtUsd[targetFormName.rowCount.value],arguments[0]);
	updateSummary();
}




function confirmMessage() {
	//targetFormName.linkDisable.value=="N";
	//targetFormName.airlineNo.disabled=true;
	//targetFormName.airlineCode.disabled=true;
	//targetFormName.clearancePeriod.disabled=true;
	//targetFormName.clearancePeriodlov.disabled=true;
	targetFormName.elements.btSave.disabled=false;
	//var addlink=document.getElementById('lnkAdd');
	//enableLink(addlink);
	//var delLnk=document.getElementById('lnkDelete');
	//enableLink(delLnk);
	//targetFormName.airlinecodelov.disabled=true;
	targetFormName.elements.formOneOpFlag.value="I";
	addTemplateRow('targetWeightRow','targetWeightTableBody','operationFlag');
	//targetFormName.linkDisable.value=="N";
}

function nonconfirmMessage() {
	submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.clear.do');

}

function doClear(){
	submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.clear.do');
}

function doClose(){
	submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.close.do');
}

function doSave(){

	
 if(isFormModified(targetFormName)) {
	

submitForm(targetFormName,'mra.airlinebilling.inward.captureformone.save.do');
}
	
	
	
	else{
	showDialog({msg:'No Modified Data For Save',type:1,parentWindow:self});
	
	
	}
}

function disable() {
// Initial focus on page load.
if(targetFormName.elements.clearancePeriod.disabled == false) {
    targetFormName.elements.clearancePeriod.focus();	
}

var operationFlag = document.getElementsByName('operationFlag');
var chkBoxIds = document.getElementsByName('checkBox');
if(targetFormName.elements.listFlag.value=="Y"){
	/* var addlink=document.getElementById('lnkAdd');
	  enableLink(addlink);
	 var delLnk=document.getElementById('lnkDelete');
    	enableLink(delLnk);*/
	for(var i = 0; i < chkBoxIds.length; i++){
		if(operationFlag[i].value!="NOOP"){
			targetFormName.elements.invNum[i].disabled=true;
			targetFormName.elements.invDate[i].disabled=true;
			
			
			if(targetFormName.elements.invStatus[i].value=="R")
				{
				targetFormName.elements.exgRate[i].disabled=true;
				targetFormName.elements.curList[i].disabled=true;
				}
			}
	}
	//targetFormName.airlineNo.disabled=true;
	//targetFormName.airlineCode.disabled=true;
	//targetFormName.clearancePeriod.disabled=true;
	//targetFormName.clearancePeriodlov.disabled=true;
	//targetFormName.airlinecodelov.disabled=true;
	
	
    }
    
else if(targetFormName.listFlag.value=="N"){ 
targetFormName.elements.btnAccDetails.disabled=true;   
    targetFormName.elements.btRejectFormOne.disabled=true;  
    targetFormName.elements.btSummary.disabled=true;  
     targetFormName.elements.btFormthree.disabled=true;  
   targetFormName.elements.btSave.disabled=true;  
    
  /*  var addlink=document.getElementById('lnkAdd');
    disableLink(addlink);
    var delLnk=document.getElementById('lnkDelete');
    disableLink(delLnk);*/
   
    }
}






function populateAirlineNo(targetFormName)
{


if(targetFormName.elements.airlineCode.value != ""){
if(targetFormName.elements.airlineNo.value == ""){
		
	
	targetFormName.action="mailtracking.mra.airlinebilling.inward.captureformone.ajaxonScreenLoad.do";
	divIdSeg="checkScreenRefresh_new";
	recreateTableDetails(targetFormName.elements.action,'captureInvoiceParent',divIdSeg);
	

		}
	}	
}




////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";

function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
}
function checkScreenRefresh_new(_tableInfo){
	
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];	
	updateTableCode(_tableInfo);
	
}

function updateTableCode(_tableInfo){

	_str=getActualData(_tableInfo);		
	
	document.getElementById(_tableInfo.currDivId).innerHTML=_str;  	
	
   	
}

function getActualData(_tableInfo){
	_frm=_tableInfo.document.getElementsByTagName("table")[0];
	
	return _frm.outerHTML;
}

////////////////// FOR ASYNC SUBMIT ENDS///////////////////////////////////////////////

















