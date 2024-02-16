<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {
	var frm = targetFormName;

	with(frm) {
		evtHandler.addEvents("btOk","btsave()",EVT_CLICK);
		evtHandler.addEvents("btClose","btClose()",EVT_CLICK);
		evtHandler.addEvents("mailSCLov","invokeLov(this,'mailSCLov')",EVT_CLICK);
		evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
        evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
		evtHandler.addEvents("dsn","mailDSN()",EVT_BLUR);
		evtHandler.addEvents("rsn","mailRSN()",EVT_BLUR);
		evtHandler.addEvents("weight","mailWeight()",EVT_BLUR);
		evtHandler.addEvents("barrowCheck","setValue()",EVT_BLUR);
		evtHandler.addEvents("barrowCheck","containerTypeChangeOnEdit()",EVT_CHANGE);
		evtHandler.addEvents("flightDate","flightDateChangeOnEdit()",EVT_CHANGE);
		evtHandler.addEvents("flightCarrierCode","flightCarrierChangeOnEdit()",EVT_CHANGE);
		//Added as part of ICRD-229584
		evtHandler.addEvents("flightNumber","flightNumberChangeOnEdit()",EVT_CHANGE);
		evtHandler.addIDEvents("gpaCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);
        evtHandler.addIDEvents("mailOrgLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.orgin.value,'Airport Code','1','orgin','',0)",EVT_CLICK);	
        evtHandler.addIDEvents("mailDestLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destn.value,'Airport Code','1','destn','',0)",EVT_CLICK);	
	    evtHandler.addEvents("mailBag","populateMailDetails(this)",EVT_BLUR);
		evtHandler.addEvents("ooe","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("doe","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("category","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("subclass","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("year","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("dsn","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("rsn","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("hni","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("ri","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("weight","populateMailbagId(this)",EVT_BLUR);
	}
  onScreenLoad(); 
 
 
  
}

function btCheckAndsave(frm){
	var frm = targetFormName;	

var indexValues=frm.elements.selectedIndex.value;
var selectedCount=indexValues.split(",");


 
 
if(selectedCount.length>1 ){
	showDialog({msg:'<common:message bundle="errorhandlingresources" key="admin.monitoring.errorhandling.multiselectreprocess" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_3',
						onClose:function(){
						screenConfirmDialog(frm,'id_3');
						screenNonConfirmDialog(frm,'id_3'); 
						}
						});
						
						}
						else { 
						reprocess();
						}
	
}
function reprocess(frm){
var frm = targetFormName;
	
	var incomplete = validateContainerDetails();
	if(incomplete == "N"){
        return;
    }
	incomplete = validateMailDetails();
      	if(incomplete == "N"){
           return;
      	}

	        var __extraFn="populateResolutionStatus";
			
			var transactionLabel =targetFormName.transactionName.value;

		if( transactionLabel=='performMailOperationForGHA'){
		asyncSubmit(targetFormName,"admin.monitoring.saveerrorhandlingforsatscommand.do",__extraFn,null,null);	
		}
		else if (transactionLabel=='saveMailUploadDetailsForAndroid'){

			asyncSubmit(targetFormName,"admin.monitoring.saveerrorhandlingforandroidcommand.do",__extraFn,null,null);
		}
		else{
				
			asyncSubmit(targetFormName,"admin.monitoring.saveerrorhandlingforandroidcommand.do",__extraFn,null,null);
			//asyncSubmit(targetFormName,"admin.monitoring.saveerrorhandlingcommand.do",__extraFn,null,null);	
			}

			clearRelist();
}


function btsave(frm){
var frm = targetFormName;
btCheckAndsave();
	

}
function populateResolutionStatus(_result){
 	  //var selectedIndex = targetFormName.selectedIndex.value;
	 //var status = "Status"+selectedIndex;	 
	window.opener.document.getElementById("status1").innerHTML = _result.document.getElementById("RelistStatus").innerHTML;
			window.close();
	
}
function onScreenLoad(){

var frm = targetFormName;	
var funtion=frm.elements.functionType.value;
if(targetFormName.elements.mailCompanyCode!==undefined){
if((targetFormName.elements.functionType.value == "ARR")||(targetFormName.elements.functionType.value == "EXP")
	||(targetFormName.elements.functionType.value == "ACP")){
targetFormName.elements.mailCompanyCode.enabled=true;
}else{
targetFormName.elements.mailCompanyCode.disabled=true;
}
 if((targetFormName.elements.functionType.value == "RTN")||(targetFormName.elements.functionType.value == "DMG")||(targetFormName.elements.functionType.value == "DLV") || (targetFormName.elements.functionType.value == "OFL")){
 targetFormName.elements.mailCompanyCode.disabled=true;
 }
 }
	if((targetFormName.elements.functionType.value == "RTN")||(targetFormName.elements.functionType.value == "DMG")||(targetFormName.elements.functionType.value == "DLV")){
		
//targetFormName.bulk.disabled=true;
  targetFormName.elements.barrowCheck.disabled=true;

   targetFormName.elements.container.disabled=true;

   targetFormName.elements.pou.disabled=true
   targetFormName.elements.flightNumber.disabled=true
   targetFormName.elements.flightDate.disabled=true
     disableField(targetFormName.elements.btn_flightDate);
	 ///*Added as part of ICRD-229584 starts
   if(targetFormName.elements.transactionName.value=="getManifestInfo")
	{
		
		targetFormName.elements.flightNumber.disabled=false;
  	}
	//Added as part of ICRD-229584 starts */
	else
   targetFormName.elements.flightCarrierCode.disabled=true
targetFormName.elements.destination.disabled=true
		
	}
	
	
	if(targetFormName.elements.functionType.value == "ACP" && targetFormName.elements.flightCarrierCode.value=="" && targetFormName.elements.flightNumber.value=="")
	{ 
   targetFormName.elements.pou.disabled=true
   targetFormName.elements.flightNumber.disabled=true
   targetFormName.elements.flightDate.disabled=true
     disableField(targetFormName.elements.btn_flightDate);
   targetFormName.elements.flightCarrierCode.disabled=true
targetFormName.elements.destination.disabled=true
	}
	if(targetFormName.elements.functionType.value == "EXP")
	{
   targetFormName.elements.pou.disabled=true
   targetFormName.elements.flightNumber.disabled=true
   targetFormName.elements.flightDate.disabled=true
     disableField(targetFormName.elements.btn_flightDate);
   targetFormName.elements.flightCarrierCode.disabled=true
targetFormName.elements.destination.disabled=true
	}
	if((targetFormName.elements.functionType.value == "OFL")){
	var mailOOE =targetFormName.elements.ooe.value
    var mailDOE =targetFormName.elements.doe.value
    var mailSC =targetFormName.elements.subclass.value
    var mailYr =targetFormName.elements.year.value;
    var mailDSN =targetFormName.elements.dsn.value;
    var mailRSN =targetFormName.elements.rsn.value;
    var mailWt =targetFormName.elements.weight.value;
	
	var mailBagId=mailOOE+mailDOE+mailSC+mailDSN+mailRSN;

if(mailBagId.length>12){
targetFormName.elements.container.disabled=true;
}
	targetFormName.elements.barrowCheck.disabled=true;
	//targetFormName.bulk.disabled=true;
   targetFormName.elements.pou.disabled=true
   targetFormName.elements.flightNumber.disabled=true
   targetFormName.elements.flightDate.disabled=true
   disableField(targetFormName.elements.btn_flightDate);
   targetFormName.elements.flightCarrierCode.disabled=true
targetFormName.elements.destination.disabled=true
	}
if((targetFormName.elements.functionType.value == "ARR")){
	
	 targetFormName.elements.destination.disabled=true
	 targetFormName.elements.pou.disabled=true
	}
	//var ooe=document.getElementById("ooe").value;
	//var doe=document.getElementById("doe").value;
	var ooe=targetFormName.elements.ooe.value;
	var doe=targetFormName.elements.doe.value;
	
	if(ooe.length==0 && doe.length==0 ){
	
	targetFormName.elements.ooe.disabled=true;
	disableField(targetFormName.elements.mailOOELov);
   targetFormName.elements.doe.disabled=true
   disableField(targetFormName.elements.mailDOELov);
   targetFormName.elements.category.disabled=true
   targetFormName.elements.rsn.disabled=true
   targetFormName.elements.year.disabled=true
targetFormName.elements.hni.disabled=true
targetFormName.elements.ri.disabled=true
targetFormName.elements.dsn.disabled=true
targetFormName.elements.weight.disabled=true
targetFormName.elements.subclass.disabled=true
disableField(targetFormName.elements.mailSCLov);

} 

if((targetFormName.elements.functionType.value == "RTN")||(targetFormName.elements.functionType.value == "DMG")||(targetFormName.elements.functionType.value == "DLV")|| (targetFormName.elements.functionType.value == "OFL")){
		
//targetFormName.bulk.disabled=true;
if(targetFormName.elements.barrowCheck.value==""){
  targetFormName.elements.barrowCheck.disabled=true;
  }else
  targetFormName.elements.barrowCheck.disabled=false;
if(targetFormName.elements.container.value==""){
  targetFormName.elements.container.disabled=true;
  }else
  targetFormName.elements.container.disabled=false;
   if(targetFormName.elements.pou.value==""){
  targetFormName.elements.pou.disabled=true;
  }else
  targetFormName.elements.pou.disabled=false;
  //*Added as part of ICRD-229584 starts
   if(targetFormName.elements.transactionName.value=="getManifestInfo")
	{
		
		targetFormName.elements.flightNumber.disabled=false;
  	}
//	Added as part of ICRD-229584 starts*/

else
{
  if(targetFormName.elements.flightNumber.value==""){
  targetFormName.elements.flightNumber.disabled=true;
  }else
  targetFormName.elements.flightNumber.disabled=false;
}
  if(targetFormName.elements.flightDate.value==""){
  targetFormName.elements.flightDate.disabled=true;
  }else
  targetFormName.elements.flightDate.disabled=false;
//*Added as part of ICRD-229584 starts
   if(targetFormName.elements.transactionName.value=="getManifestInfo")
	{
		
		targetFormName.elements.flightNumber.disabled=false;
  	}
//	Added as part of ICRD-229584 starts*/
else{
  if(targetFormName.elements.flightCarrierCode.value==""){
  targetFormName.elements.flightCarrierCode.disabled=true;
  }else
  targetFormName.elements.flightCarrierCode.disabled=false;
}
   if(targetFormName.elements.destination.value==""){
  targetFormName.elements.destination.disabled=true;
  }else
  targetFormName.elements.destination.disabled=false;
		
	}

if(targetFormName.elements.ooe.value==""){
  targetFormName.elements.ooe.disabled=true;
  disableField(targetFormName.elements.mailOOELov);
  }else{
  targetFormName.elements.ooe.disabled=false;
}
  if(targetFormName.elements.doe.value==""){
  targetFormName.elements.doe.disabled=true;
  disableField(targetFormName.elements.mailDOELov);
  }else
  targetFormName.elements.doe.disabled=false;
  
	if(targetFormName.elements.category.value==""){
  targetFormName.elements.category.disabled=true;
  }else
  targetFormName.elements.category.disabled=false;
	
   if(targetFormName.elements.rsn.value==""){
  targetFormName.elements.rsn.disabled=true;
  }else
  targetFormName.elements.rsn.disabled=false;
   
   	
   if(targetFormName.elements.year.value==""){
  targetFormName.elements.year.disabled=true;
  }else
  targetFormName.elements.year.disabled=false;
   if(targetFormName.elements.hni.value==""){
  targetFormName.elements.hni.disabled=true;
  }else
  targetFormName.elements.hni.disabled=false;
  
if(targetFormName.elements.ri.value==""){
  targetFormName.elements.ri.disabled=true;
  }else
  targetFormName.elements.ri.disabled=false;
  
if(targetFormName.elements.dsn.value==""){
  targetFormName.elements.dsn.disabled=true;
  }else
  targetFormName.elements.dsn.disabled=false;
  if(targetFormName.elements.weight.value==""){
  targetFormName.elements.weight.disabled=true;
  }else
  targetFormName.elements.weight.disabled=false;

 if(targetFormName.elements.subclass.value==""){
  targetFormName.elements.subclass.disabled=true;
  disableField(targetFormName.elements.mailSCLov);
  }else
  targetFormName.elements.subclass.disabled=false;

//Added by A-7540 starts 
    var mailbagID=targetFormName.elements.mailBag.value;
     if(mailbagID.length==12 && ooe.length!=0 && doe.length!=0 ){
			   targetFormName.elements.ooe.disabled=true;
			   targetFormName.elements.doe.disabled=true;
			   disableField(document.getElementById("mailOOELov"));
			   disableField(document.getElementById("mailDOELov"));
			   disableField(document.getElementById("mailSCLov"));
			   targetFormName.elements.category.disabled=true;
			   targetFormName.elements.rsn.disabled=true;
			   targetFormName.elements.year.disabled=true;
	           targetFormName.elements.hni.disabled=true;
			   targetFormName.elements.ri.disabled=true;
			   targetFormName.elements.dsn.disabled=true;
			   targetFormName.elements.weight.disabled=true;
			   targetFormName.elements.subclass.disabled=true;
       } 
	   if(mailbagID.length==12 && ooe.length==0 && doe.length==0 ){
		      targetFormName.elements.category.disabled=true;
			  targetFormName.elements.hni.disabled=true;
			  targetFormName.elements.ri.disabled=true;
			  disableField(document.getElementById("mailOOELov"));
			   disableField(document.getElementById("mailDOELov"));
			   disableField(document.getElementById("mailSCLov"));
	   }
//Added by A-7540 ends 
		
setBarrow();
} 
function btClose(frm){

if(hasFormChanged()){
	var frm = targetFormName;	
	showDialog({msg:'<bean:message bundle="errorhandlingresources" key="admin.monitoring.errorhandling.unsaveddataexists" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_2',
						onClose:function(){
					
						screenConfirmDialog(frm,'id_2');
						
						}
						});
	
				
			}                                                                             else {
				window.close();
			}
			}
			
function screenConfirmDialog(frm, dialogId) {
var frm = targetFormName;



var flg=frm.elements.currentDialogOption.value;

if(dialogId == 'id_3' && flg=='Y'){
reprocess();
}else{
if(flg=='Y'){
 window.close();
 
}
}
}

function screenNonConfirmDialog(frm, dialogId) {
var frm = targetFormName;



var flg=frm.elements.currentDialogOption.value;
if(dialogId == 'id_3' && flg=='N'){
frm.elements.operationFlag.value="";
	frm.elements.isFlightCarrierCodeChanged.value="";
	frm.elements.isFlightNumberChanged.value="";
	frm.elements.isFlightDateChanged.value="";
	frm.elements.isContainerTypeChanged.value="";
	frm.elements.isContainerNumberChanged.value="";
	frm.elements.isDestinationChanged.value="";
	frm.elements.isMailCompanyCodeChanged.value="";
	frm.elements.isPouChanged.value="";
	frm.elements.isPolChanged.value="";
	frm.elements.isOOEChanged.value="";
	frm.elements.isDOEChanged.value="";
	frm.elements.isCategoryChanged.value="";
	frm.elements.isSubClassChanged.value="";
	frm.elements.isYearChanged.value="";
	frm.elements.isDsnChanged.value="";
	frm.elements.isRsnChanged.value="";
	frm.elements.isHniChanged.value="";
	frm.elements.isRiChanged.value="";
	frm.elements.isWeightChanged.value="";
	frm.elements.isTransferCarrierChanged.value="";
	//added as part of ICRD-229584 starts
	frm.elements.isFlightNumberChanged.value="";
	//frm.elements.isFlightDateChanged.value="";
	//added as part of ICRD-229584 starts
	
	document.forms[0].elements.lastPopupPageNum.value=document.forms[0].elements.lastPopupPageNum.value;
	document.forms[0].elements.displayPopupPage.value=document.forms[0].elements.displayPopupPage.value;
	document.forms[0].action="admin.monitoring.openresendorreprocesspopupnext.do";
	document.forms[0].submit();

}
}
function closepopup(frm){
close();
	opener.submitForm(window.opener.targetFormName,"admin.monitoring.Closeerrorhandling.do");
}
function invokeLov(obj,name){
   var index = obj.id.split(name)[1];

   
   
   if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.ooe.value,'OfficeOfExchange','0','ooe','',index);
   }
   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.doe.value,'OfficeOfExchange','0','doe','',index);
   }
   if(name == "mailSCLov"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subclass.value,'OfficeOfExchange','0','subclass','',index);
   }
   
  }
  function validateMailDetails(){
	var mailbagID = document.getElementsByName("mailBag");
	var mailOOE =document.getElementsByName("ooe");
    var mailDOE =document.getElementsByName("doe");
	var mailCat =document.getElementsByName("category");
	var mailHNI =document.getElementsByName("hni"); 
    var mailRI =document.getElementsByName("ri");	
    var mailSC =document.getElementsByName("subclass");
    var mailYr =document.getElementsByName("year");
    var mailDSN =document.getElementsByName("dsn");
    var mailRSN =document.getElementsByName("rsn");
    var mailWt =document.getElementsByName("weight");
	if(mailbagID.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailbagidempty" scope="request"/>',type:1,parentWindow:self});
    	    mailbagID.focus();
    	    return "N";
	}
	var mailBagId="";
	for(var i=0;i<mailOOE.length;i++){
		mailBagId=mailOOE[i].value + mailDOE[i].value + mailSC[i].value + mailYr[i].value + mailDSN[i].value + mailRSN[i].value + mailWt[i].value;
		if(mailBagId.length>0){
    	if(mailOOE[i].value.length == 0){
			
			  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooeempty" scope="request"/>',type:1,parentWindow:self});
    	    mailOOE[i].focus();
    	    return "N";
    	}
    	/*if(mailOOE[i].value.length != 6){
			showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooelength" scope="request"/>',type:1,parentWindow:self});
			mailOOE[i].focus();
			return "N";
        }*/
     	if(mailDOE[i].value.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoeempty" scope="request"/>',type:1,parentWindow:self});
     	     mailDOE[i].focus();
     	    return "N";
     	}
     	/*if(mailDOE[i].value.length != 6){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoelength" scope="request"/>',type:1,parentWindow:self});
			mailDOE[i].focus();
			return "N";
		}*/
		//Added by A-7540 starts
		if(mailCat[i].value.length == 0){  
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailCatempty" scope="request"/>',type:1,parentWindow:self});			
   	     mailCat[i].focus();
   	     return "N";
   	  }
	  if(mailHNI[i].value.length == 0){  
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailhniempty" scope="request"/>',type:1,parentWindow:self});			
   	     mailHNI[i].focus();
   	     return "N";
   	  }
	  if(mailRI[i].value.length == 0){  
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailriempty" scope="request"/>',type:1,parentWindow:self});			
   	     mailRI[i].focus();
   	     return "N";
   	  }
	  //Added by A-7540 ends
     	if(mailSC[i].value.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailscempty" scope="request"/>',type:1,parentWindow:self});
     	    mailSC[i].focus();
     	    return "N";
     	}
     	if(mailSC[i].value.length != 2){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailsclength" scope="request"/>',type:1,parentWindow:self});
		mailSC[i].focus();
			return "N";
        }
        if(mailYr[i].value.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailyearempty" scope="request"/>',type:1,parentWindow:self});
      	    mailYr[i].focus();
      	    return "N";
      	 }
        if(mailDSN[i].value.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildsnempty" scope="request"/>',type:1,parentWindow:self});
      	    mailDSN[i].focus();
      	    return "N";
      	 }
        if(mailRSN[i].value.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailrsnempty" scope="request"/>',type:1,parentWindow:self});
       	    mailRSN[i].focus();
       	    return "N";
       	}
        if(mailWt[i].value.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtempty" scope="request"/>',type:1,parentWindow:self});
			mailWt[i].focus();
			return "N";
		}
		if(mailWt[i].value == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtzero" scope="request"/>',type:1,parentWindow:self});
			mailWt[i].focus();
			return "N";
		}
	}
	}
	return "Y";
  }
  function validateContainerDetails(){
	var mailFltNo =document.getElementsByName("flightNumber");
    var mailFlightDate =document.getElementsByName("flightDate");
	var mailContainer =document.getElementsByName("container");
	var functionpoint =targetFormName.elements.functionType.value;
	var mailOOE =targetFormName.elements.ooe.value
    var mailDOE =targetFormName.elements.doe.value
    var mailSC =targetFormName.elements.subclass.value
    var mailYr =targetFormName.elements.year.value;
    var mailDSN =targetFormName.elements.dsn.value;
    var mailRSN =targetFormName.elements.rsn.value;
    var mailWt =targetFormName.elements.weight.value;
	
	var mailBagId=mailOOE+mailDOE+mailSC+mailDSN+mailRSN;

	if((targetFormName.elements.functionType.value != "DMG") && (targetFormName.elements.functionType.value != "RTN") && (targetFormName.elements.functionType.value != "DLV")&&((targetFormName.elements.functionType.value == "OFL")
	&& mailBagId.length<12 )){
	for(var i=0;i<mailContainer.length;i++){
		if(mailContainer[i].value.length == 0){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.err.containernumberempty" scope="request"/>',type:1,parentWindow:self});
			mailContainer[i].focus();
    	    return "N";
    	}
    	if((mailFltNo[i].value.length != 0) && (mailFlightDate[i].value.length == 0)){
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.err.flightdateempty" scope="request"/>',type:1,parentWindow:self});
			mailFlightDate[i].focus();
    	    return "N";
    	}
    	}
	}
	return "Y";
  }
  function mailDSN(){
		var frm=targetFormName;
		var mailDSNArr =document.getElementsByName("dsn");
		var mailDSN =document.getElementsByName("dsn");
		for(var i=0;i<mailDSNArr.length;i++){
		  if(mailDSNArr[i].value.length == 1){
			  mailDSN[i].value = "000"+mailDSNArr[i].value;
		  }
		  if(mailDSNArr[i].value.length == 2){
					mailDSN[i].value = "00"+mailDSNArr[i].value;
		  }
		  if(mailDSNArr[i].value.length == 3){
					mailDSN[i].value = "0"+mailDSNArr[i].value;
		  }
		}
	}
	function mailRSN(){
	 var frm=targetFormName;
	 var mailRSNArr =document.getElementsByName("rsn");
	 var mailRSN =document.getElementsByName("rsn");
	   for(var i=0;i<mailRSNArr.length;i++){
		  if(mailRSNArr[i].value.length == 1){
			  mailRSN[i].value = "00"+mailRSNArr[i].value;
		  }
		  if(mailRSNArr[i].value.length == 2){
			  mailRSN[i].value = "0"+mailRSNArr[i].value;
		  }
	   }
	}
	function mailWeight(){
	 var frm=targetFormName;
	 var weightArr =document.getElementsByName("weight");
	 var weight =document.getElementsByName("weight");
	   for(var i=0;i<weightArr.length;i++){
		  if(weightArr[i].value.length == 1){
			  weight[i].value = "000"+weightArr[i].value;
		  }
		  if(weightArr[i].value.length == 2){
					weight[i].value = "00"+weightArr[i].value;
		  }
		  if(weightArr[i].value.length == 3){
					weight[i].value = "0"+weightArr[i].value;
		  }
	   }
	}
	function setValue(){
	var frm=targetFormName;
	if(frm.elements.barrowCheck.checked){
	frm.elements.bulk.value="Y";
	}else{
	frm.elements.bulk.value="N";
	   }
	 
	}
	function setBarrow(){
	var frm=targetFormName;
	if(frm.elements.bulk.value=="Y"){
	frm.elements.barrowCheck.checked=true;
	}else{
	frm.elements.barrowCheck.checked=false;
	   }
	}
	
	function displayNextError(lastPopupPg,displayPopupPg)
{
var frm=targetFormName;

if(frm.elements.operationFlag.value=='U'){
	showDialog({msg:'<common:message bundle="errorhandlingresources" key="admin.monitoring.errorhandling.multiselectreprocess" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_3',
						onClose:function(){
						performpagination(lastPopupPg,displayPopupPg);
						screenNonConfirmDialog(frm,'id_3'); 
						}
						});
						
						}else{
										
						
						document.forms[0].elements.lastPopupPageNum.value=lastPopupPg;
	document.forms[0].elements.displayPopupPage.value=displayPopupPg;
	document.forms[0].action="admin.monitoring.openresendorreprocesspopupnext.do";
	document.forms[0].submit();
						
						}
	
}
function performpagination(lastPopupPg,displayPopupPg){
var frm = targetFormName;
var flg=frm.elements.currentDialogOption.value;
if(flg=='Y'){

document.forms[0].elements.lastPopupPageNum.value=lastPopupPg;
	document.forms[0].elements.displayPopupPage.value=displayPopupPg;
	document.forms[0].action="admin.monitoring.openresendorreprocesspopupnext.do";
	frm.elements.operationFlag.value="";
	document.forms[0].submit();
	

	}
	else{ 
	frm.elements.operationFlag.value="";
	frm.elements.isFlightCarrierCodeChanged.value="";
	frm.elements.isFlightNumberChanged.value="";
	frm.elements.isFlightDateChanged.value="";
	frm.elements.isContainerTypeChanged.value="";
	frm.elements.isContainerNumberChanged.value="";
	frm.elements.isDestinationChanged.value="";
	frm.elements.isMailCompanyCodeChanged.value="";
	frm.elements.isPouChanged.value="";
	frm.elements.isPolChanged.value="";
	frm.elements.isOOEChanged.value="";
	frm.elements.isDOEChanged.value="";
	frm.elements.isCategoryChanged.value="";
	frm.elements.isSubClassChanged.value="";
	frm.elements.isYearChanged.value="";
	frm.elements.isDsnChanged.value="";
	frm.elements.isRsnChanged.value="";
	frm.elements.isHniChanged.value="";
	frm.elements.isRiChanged.value="";
	frm.elements.isWeightChanged.value="";
	frm.elements.isTransferCarrierChanged.value="";
	//document.forms[0].elements.lastPopupPageNum.value=document.forms[0].elements.lastPopupPageNum.value;
	//document.forms[0].elements.displayPopupPage.value=document.forms[0].elements.displayPopupPage.value;
	//document.forms[0].action="admin.monitoring.openresendorreprocesspopupnext.do";
	//document.forms[0].submit();
	}
}


function flightCarrierChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isFlightCarrierCodeChanged.value="Y";

}
function flightDateChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isFlightDateChanged.value="Y";

}


function containerTypeChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isContainerTypeChanged.value="Y";

}

function containerNumberChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isContainerNumberChanged.value="Y";

}
function transferCarrierChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isTransferCarrierChanged.value="Y";

}
function polChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isPolChanged.value="Y";

}
function pouChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isPouChanged.value="Y";

}
function destinationChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isDestinationChanged.value="Y";

}
function mailCompanyCodeChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isMailCompanyCodeChanged.value="Y";

}
function ooeChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isOOEChanged.value="Y";

}
function doeChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isDOEChanged.value="Y";

}
function categoryChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isCategoryChanged.value="Y";

}
function subclassChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isSubClassChanged.value="Y";

}
function yearChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isYearChanged.value="Y";

}
function dsnChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isDsnChanged.value="Y";

}
function rsnChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isRsnChanged.value="Y";

}
function hniChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isHniChanged.value="Y";

}
function riChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isRiChanged.value="Y";

}
function weightChangeOnEdit(){
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isWeightChanged.value="Y";

	}
	//Added as part of ICRD-229584 starts
	//Modified by A-8893 ,the same method is used 2 times here.removed one added the element inside this method
	function flightNumberChangeOnEdit()
	{
var frm=targetFormName;
frm.elements.operationFlag.value="U";
frm.elements.isWeightChanged.value="Y";
frm.elements.isFlightNumberChanged.value="Y";

	}
	
	
	//Added as part of ICRD-229584 ends
//Added by A-7540 starts
function mailbagChangeOnEdit(){
	var frm=targetFormName;
frm.elements.operationFlag.value="U";
}	
function populateMailbagId(obj){
	if(targetFormName.elements.ooe.value!=null&&targetFormName.elements.ooe.value!=""&&
	targetFormName.elements.doe.value!=null&&targetFormName.elements.doe.value!=""&&
	targetFormName.elements.category.value!=null&&targetFormName.elements.category.value!=""&&
	targetFormName.elements.subclass.value!=null&&targetFormName.elements.subclass.value!=""&&
	targetFormName.elements.year.value!=null&&targetFormName.elements.year.value!=""&&
	targetFormName.elements.dsn.value!=null&&targetFormName.elements.dsn.value!=""&&
	targetFormName.elements.rsn.value!=null&&targetFormName.elements.rsn.value!=""&&
	targetFormName.elements.hni.value!=null&&targetFormName.elements.hni.value!=""&&
	targetFormName.elements.ri.value!=null&&targetFormName.elements.ri.value!=""&&
	targetFormName.elements.weight.value!=null&&targetFormName.elements.weight.value!=""){
	targetFormName.elements.mailBag.value="";
	populateMailDetails(obj);
	}else{
	targetFormName.elements.mailBag.value="";
	}
	}
function populateMailDetails(obj){
	if(targetFormName.elements.mailBag.value.length==12){
		targetFormName.elements.ooe.disabled=true;
		targetFormName.elements.doe.disabled=true;
		disableField(document.getElementById("mailOOELov"));
		disableField(document.getElementById("mailDOELov"));
		disableField(document.getElementById("mailSCLov"));
		targetFormName.elements.category.disabled=true;
		targetFormName.elements.rsn.disabled=true;
		targetFormName.elements.year.disabled=true;
	    targetFormName.elements.hni.disabled=true;
	    targetFormName.elements.ri.disabled=true;
		targetFormName.elements.dsn.disabled=true;
		targetFormName.elements.weight.disabled=true;
		targetFormName.elements.subclass.disabled=true;
	}
	else if(targetFormName.elements.mailBag.value.length==29){
		targetFormName.elements.ooe.disabled=false;
		targetFormName.elements.doe.disabled=false;
		enableField(document.getElementById("mailOOELov"));
		enableField(document.getElementById("mailDOELov"));
		enableField(document.getElementById("mailSCLov"));
		targetFormName.elements.category.disabled=false;
		targetFormName.elements.rsn.disabled=false;
		targetFormName.elements.year.disabled=false;
	    targetFormName.elements.hni.disabled=false;
	    targetFormName.elements.ri.disabled=false;
		targetFormName.elements.dsn.disabled=false;
		targetFormName.elements.weight.disabled=false;
		targetFormName.elements.subclass.disabled=false;
	}
	var mailbagId = targetFormName.elements.mailBag.value;
  var funct_to_overwrite = "refreshMailDetails";
	var strAction = 'admin.monitoring.errorhandling.populatemailtagdetails.do?mailbagId';
  	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}
function refreshMailDetails(_tableInfo){
if(_tableInfo.document.getElementById("_ajax_inValidId").innerHTML=='true'){
	showDialog({msg:'Invalid Mail Bag ID',type:1,parentWindow:self});
	targetFormName.elements.weight.focus();
	return;
}	
	targetFormName.elements.ooe.value=_tableInfo.document.getElementById("_ajax_mailOOE").innerHTML;
    targetFormName.elements.doe.value=_tableInfo.document.getElementById("_ajax_mailDOE").innerHTML;
    targetFormName.elements.category.value=_tableInfo.document.getElementById("_ajax_mailCat").innerHTML;
    targetFormName.elements.subclass.value=_tableInfo.document.getElementById("_ajax_mailSC").innerHTML;
	targetFormName.elements.year.value=_tableInfo.document.getElementById("_ajax_mailYr").innerHTML;
	targetFormName.elements.dsn.value=_tableInfo.document.getElementById("_ajax_mailDSN").innerHTML;
	targetFormName.elements.rsn.value=_tableInfo.document.getElementById("_ajax_mailRSN").innerHTML;
	targetFormName.elements.hni.value=_tableInfo.document.getElementById("_ajax_mailHNI").innerHTML;
	targetFormName.elements.ri.value=_tableInfo.document.getElementById("_ajax_mailRI").innerHTML;
	targetFormName.elements.weight.value=_tableInfo.document.getElementById("_ajax_mailWt").innerHTML;
	targetFormName.elements.mailBag.value=_tableInfo.document.getElementById("_ajax_mailbagId").innerHTML;
var incomplete=mailWeight();
mailDSN();
mailRSN();
}