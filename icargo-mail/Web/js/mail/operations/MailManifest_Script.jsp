<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   //onScreenloadSetHeight();
   onScreenLoad();
   initializePanels();
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnList","listMailManifest()",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clearMailManifest()",EVT_CLICK);
     	evtHandler.addEvents("btnViewMailDetails","viewMailDetails()",EVT_CLICK);
     	evtHandler.addEvents("btnReopenFlight","reopenFlight()",EVT_CLICK);
     	evtHandler.addEvents("btnCloseFlight","closeFlight()",EVT_CLICK);
     	evtHandler.addEvents("btnGenerateManifest","generateManifest()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeMailManifest()",EVT_CLICK);
		evtHandler.addEvents("btSave","saveMailManifest()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
     	evtHandler.addEvents("btnAttachAWB","attachAWB()",EVT_CLICK);
     	evtHandler.addEvents("btnAutoAttachAWB","autoattachAWB()",EVT_CLICK);
		evtHandler.addEvents("btnDetachAWB","detachAWB()",EVT_CLICK);
     	evtHandler.addEvents("btnAttachRouting","attachRouting()",EVT_CLICK);
	  }

}

function preWarningMessages(){
   collapseAllRows();
}

function resetFocus(){
	 if(!event.shiftKey){ 
		if((!targetFormName.elements.flightCarrierCode.disabled) && (!targetFormName.elements.flightCarrierCode.readOnly)){
			 targetFormName.elements.flightCarrierCode.focus();
		}								
	}
}


function onScreenLoad(){
  frm=targetFormName;  
  
  
  
  
  
  if(frm.attachRouting.value=="OK"){
  	targetFormName.elements.attachRouting.value = "";
  	var csgDocNum = targetFormName.elements.csgDocNumForRouting.value;
  	var paCode = targetFormName.elements.paCodeForRouting.value;
  	
  	if(csgDocNum!=""){
  		var strAction="mailtracking.defaults.mailmanifest.attachroutinglist.do";
  		var strUrl=strAction+"?conDocNo="+csgDocNum+"&paCode="+paCode;
  		openPopUp(strUrl,800,360);
  	}else{
  		var strAction="mailtracking.defaults.mailmanifest.attachroutingscreenload.do";		
  		openPopUp(strAction,800,450); // Modified by A-7929
  	}	 
  }

 if (frm.mailFlightSummary.value=="MailFlightSummary"){
	frm.initialFocus.value = "N"
	frm.mailFlightSummary.value="ToMailFlightSummary";
  }
  if (frm.mailFlightSummary.value=="MailFlightSummaryClose"){
	frm.initialFocus.value = "N"
	frm.mailFlightSummary.value="";
  }
	
 if(frm.operationalStatus.value == "OPEN"){
      //targetFormName.elements.btnReopenFlight.disabled=true;
	  disableField(targetFormName.elements.btnReopenFlight);
      enableField(targetFormName.elements.btnCloseFlight);
  }else if(frm.operationalStatus.value == "CLOSED"){
      //targetFormName.elements.btnReopenFlight.disabled=false;
	  enableField(targetFormName.elements.btnReopenFlight);
      disableField(targetFormName.elements.btnCloseFlight);
      //targetFormName.elements.btSave.disabled=true;
	  disableField(targetFormName.elements.btSave);
  }else{
      //targetFormName.elements.btnReopenFlight.disabled=true;
	  disableField(targetFormName.elements.btnReopenFlight);
      disableField(targetFormName.elements.btnCloseFlight);
  }


  if(frm.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.duplicateflight.do","600","280");
  }

  if(frm.uldsSelectedFlag.value == "N"){
	  frm.uldsSelectedFlag.value = "";
	  var screen = "MANIFEST";
	  var strurl = "mailtracking.defaults.mailacceptance.selectemptyulds.do?fromScreen="+screen;
	  openPopUp(strurl,"400","290");
  }

  if(frm.disableSaveFlag.value == "Y"){
  	//targetFormName.elements.btSave.disabled=true;
	//disableField(targetFormName.elements.btSave);
  	//frm.btnAttachAWB.disabled = true;
	//disableField(targetFormName.elements.btnAttachAWB);
  }

  if(frm.initialFocus.value == "Y"){
      frm.initialFocus.value = "";
      frm.initialFocus.defaultValue = "";
      frm.flightCarrierCode.focus();
  }

  if(frm.openAttachAWB.value == "Y"){
     frm.openAttachAWB.value = "";
     var pol = frm.pol.value;
     var pou = frm.pou.value;
	 var fromScreen="MTK003";
     var strAction="mailtracking.defaults.attachawb.screenload.do";
     var strUrl=strAction+"?screenStatus=SCREENLOAD&origin="+pol+"&destination="+pou+"&fromScreen="+fromScreen;
     openPopUp(strUrl,600,228);

  }
   if(frm.disableButtonsForTBA.value == "Y"){ 
   
		disableField(frm.btnViewMailDetails);   
		disableField(frm.btnReopenFlight);
		disableField(frm.btnCloseFlight);
		disableField(frm.btnGenerateManifest);
		disableField(frm.btnAttachAWB);
		disableField(frm.btnAutoAttachAWB);
		disableField(frm.btnAttachRouting);
		disableField(frm.btSave);
  }
  if(frm.elements.disableButtonsForAirport.value == "Y"){
		disableField(frm.elements.btnAttachRouting);		
		disableField(frm.elements.btnAutoAttachAWB);
		disableField(frm.elements.btnViewMailDetails);
		disableField(frm.elements.btnReopenFlight);
		disableField(frm.elements.btnCloseFlight);
		disableField(frm.elements.btnAttachAWB);	
		disableField(frm.elements.btnGenerateManifest);
		disableField(frm.elements.btSave);
  }
}

function onScreenloadSetHeight(){
	var height = document.body.clientHeight;;
	document.getElementById('pageDiv').style.height = ((height*95)/100)+'px'; 
	//alert((height*95)/100);
}


/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
    var actionName = appPath+action;
    submitForm(frm,actionName);
}

function listMailManifest(){
    frm=targetFormName;
    submitAction(frm,'/mailtracking.defaults.mailmanifest.listflight.do');
}

function clearMailManifest(){
    frm=targetFormName;
    submitAction(frm,'/mailtracking.defaults.mailmanifest.clearmailmanifest.do');
}
function saveMailManifest(){
    frm=targetFormName;
    submitAction(frm,'/mailtracking.defaults.mailmanifest.savemailmanifest.do');

}
function closeMailManifest(){
	
	if(targetFormName.elements.mailFlightSummary.value=="ToMailFlightSummary"){
		var carriercode = frm.flightCarrierCode.value;
		var flightNumber=frm.flightNumber.value;
		var flightDate=frm.depDate.value;
		var fromscreen="MailManifestClose";
		targetFormName.elements.mailFlightSummary.value="";
		var strAction="mailtracking.defaults.mailflightsummary.manifesttosummary.do";
		var strUrl=strAction+"?flightCarrierCode="+carriercode+"&flightNumber="+flightNumber+"&flightDate="+flightDate+"&manifest="+fromscreen;
		submitForm(frm,strUrl);
	}else{
		location.href = appPath + "/home.jsp";
	}	
}

function reopenFlight(){
    frm=targetFormName;
    	var opflags =document.getElementsByName("operationalFlag");
    	var csgdocs =document.getElementsByName("csgDocNum");
    	for(var i=0; i<opflags.length;i++){
            if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
                showDialog("Please save the new Data.", 1, self);
    			return;
            }
    }
    submitAction(frm,'/mailtracking.defaults.mailmanifest.reopenflight.do');
}

function closeFlight(){
    frm=targetFormName;
    	var opflags =document.getElementsByName("operationalFlag");
    	var csgdocs =document.getElementsByName("csgDocNum");
    	for(var i=0; i<opflags.length;i++){
            if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
                showDialog("Please save the new Data.", 1, self);
    			return;
            }
    }
    submitAction(frm,"/mailtracking.defaults.mailmanifest.closeflight.do");
}

function generateManifest(){
   frm=targetFormName;
   
   var opflags =document.getElementsByName("operationalFlag");
       	var csgdocs =document.getElementsByName("csgDocNum");
       	for(var i=0; i<opflags.length;i++){
               if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
                   showDialog("Please save the new Data.", 1, self);
       			return;
               }
    }
   var strAction="mailtracking.defaults.mailmanifest.generatemailmanifest.do";
   openPopUp(strAction,500,190);
   
}
function validateContCheckBox(childCheckBoxName){
	var rows=document.getElementsByName(childCheckBoxName);
	var flag = false; 
	for(var i=0;i<rows.length;i++){
		if(rows[i].checked==true){
			flag=true;
		}
	}	
 if(flag==false){
  showDialog({msg:"<bean:message bundle='mailManifestResources' key='mailtracking.defaults.mailmanifest.info.selectDsn' />", type:1, parentWindow:self}); 
	
	return false;
	}else
	return true;
}

function viewMailDetails(){
    frm=targetFormName;

     var chkbox =document.getElementsByName("selectDSN");
	if(validateContCheckBox('selectDSN')){
    if(validateSelectedCheckBoxes(frm,'selectDSN','100','1')){
         var selectCont="";
    	 var selectDSN = "";
	 var cnt1 = 0;

	 for(var i=0; i<chkbox.length;i++){
	     if(chkbox[i].checked) {
	 	 if(cnt1 == 0){
	 	     selectCont = (chkbox[i].value.split("-"))[0];
	 	     selectDSN = (chkbox[i].value.split("-"))[1];
		     cnt1 = 1;
 		 }else{
	 	     selectSameCont = (chkbox[i].value.split("-"))[0];
	 	     if(selectCont==selectSameCont){
		     	selectDSN = selectDSN + "," + (chkbox[i].value.split("-"))[1];
		     }else{	
			 
			 showDialog({msg:"<bean:message bundle='mailManifestResources' key='mailtracking.defaults.mailmanifest.info.selectDsns' />", type:1, parentWindow:self}); 
		     	
		     	return;
		     }
		 }
	     }
	 }

	   var strAction="mailtracking.defaults.mailmanifest.viewmaildetails.do";
	   var strUrl=strAction+"?selectDSN="+selectDSN+"&parentContainer="+selectCont;
       openPopUp(strUrl,875,390);

	}
    }
}

function generateManifest(){
   frm=targetFormName;
   
   var strAction="mailtracking.defaults.mailmanifest.generatemailmanifest.do";
   openPopUp(strAction,500,190);
   
}

function attachAWB(){
   frm=targetFormName;
   var opflags =document.getElementsByName("operationalFlag");
       	var csgdocs =document.getElementsByName("csgDocNum");
       	for(var i=0; i<opflags.length;i++){
               if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
                   showDialog("Please save the new Data.", 1, self);
       			return;
               }
    }

   var chkboxMail =document.getElementsByName("selectMail");
   var chkboxDSN =document.getElementsByName("selectDSN");

   var close = "YES";
   
   var selectMail = "";
   
     for(var i=0; i<chkboxMail.length;i++){
  	  if(chkboxMail[i].checked) {
  	      close = "NO";
  	      selectMail = chkboxMail[i].value;
  	  }
     }

     if(close == "YES"){
        for(var i=0; i<chkboxDSN.length;i++){
       	   if(chkboxDSN[i].checked) {
       	      close = "NO";
       	   }
        }
        if(close == "YES"){
		
		showDialog({msg:'<bean:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.info.plsSelectRow" />', type:1, parentWindow:self}); 
          //  showDialog('<bean:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.info.plsSelectRow" />',1,self);
            return;
        }
     }

         var selectCont = "";
         var selectDSN = "";
         var cnt1 = 0;

         for(var i=0; i<chkboxDSN.length;i++){
		  if(chkboxDSN[i].checked) {
			 if(selectCont != ""){

				var checkCont = (chkboxDSN[i].value.split("-"))[0];
				if(selectCont != checkCont){
				showDialog({msg:'<bean:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.info.selectDsns" />', type:1, parentWindow:self}); 
					// showDialog('<bean:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.info.selectDsns" />',1,self);
					 return;
				}

			 }

			 if(cnt1 == 0){
			     selectDSN = (chkboxDSN[i].value.split("-"))[1];
			     selectCont = (chkboxDSN[i].value.split("-"))[0];
			     cnt1 = 1;

			}else{
			     selectDSN = selectDSN + "," + (chkboxDSN[i].value.split("-"))[1];
			}
		 }
       }
       
       if(chkboxDSN.length > 0){
       
       if(selectCont == ""){
            cnt1 = 0;
            selectCont = selectMail;
            for(var i=0; i<chkboxDSN.length;i++){
            
               if(selectCont == (chkboxDSN[i].value.split("-"))[0]){
                
                  if(cnt1 == 0){
		       selectDSN = (chkboxDSN[i].value.split("-"))[1];
		       cnt1 = 1;

		  }else{
		
		       selectDSN = selectDSN + "," + (chkboxDSN[i].value.split("-"))[1];
	          }
	       }  

            }
       
       }
       
       	  var strAction="mailtracking.defaults.mailmanifest.attachawb.do";
       	  var strUrl=strAction+"?selectDSN="+selectDSN+"&parentContainer="+selectCont;
       	 // submitAction(frm,strUrl);
       	 recreateListDetails(strUrl,'mailManifest_table');
       
       }else{
       showDialog({msg:"No Mailbags accepted to this container", type:1, parentWindow:self}); 
         
       
       }


}



function autoattachAWB(){
	frm=targetFormName;
    showDialog({  //Modified by A-8164 for ICRD-259783
        msg: '<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.autoattachawb" scope="request"/>',
        type: 4,
        parentWindow: self,
        parentForm: targetFormName,
        dialogId: 'id_1',
        onClose: function() {
            screenConfirmDialog(targetFormName, 'id_1');
            screenNonConfirmDialog(targetFormName, 'id_1');
	            }
    }); 
  // submitAction(frm, '/mailtracking.defaults.mailmanifest.autoattachawb.do'); 
    }
function screenConfirmDialog(frm, dialogId) {
    while (frm.elements.currentDialogId.value == '') {
			}
    if (frm.elements.currentDialogOption.value == 'Y') {
        if (dialogId == 'id_1') {
            submitAction(frm, '/mailtracking.defaults.mailmanifest.autoattachawb.do');
		}
	}
			}

function screenNonConfirmDialog(frm, dialogId) {
    while (frm.elements.currentDialogId.value == '') {
		}
    if (frm.elements.currentDialogOption.value == 'N') {
        if (dialogId == 'id_1') {
	}
   }
}
function attachRouting(){
	frm=targetFormName;
	var opflags =document.getElementsByName("operationalFlag");
	    	var csgdocs =document.getElementsByName("csgDocNum");
	    	for(var i=0; i<opflags.length;i++){
	            if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
				 showDialog({msg:"Please save the new Data.", type:1, parentWindow:self}); 
	                //showDialog("Please save the new Data.", 1, self);
	    			return;
	            }
    }
	var chkboxMail =document.getElementsByName("selectMail");
	var chkboxDSN =document.getElementsByName("selectDSN");
	var routingAvl=document.getElementsByName("hiddenRoutingAvl");
	var B_chkboxMail = true;
	var B_chkboxDSN = true;
	var container_flag="";
	var csgDoc=targetFormName.elements.csgDocNum;
	var paCode=targetFormName.elements.paCod;
	var filterCsg="N";
	var filterPaCod="N";
	var routingFlag=0;
	checked="YES";
	cnt=0;
	selectDSN="";
	selectCont="";
	if(chkboxMail.length>0){
	     for(var i=0;i<chkboxMail.length;i++){
		    if(chkboxMail[i].checked){
			   B_chkboxMail = false;
			   break;
			}
		 }
	 }
	 if(chkboxDSN.length>0){
	    for(var i=0;i<chkboxDSN.length;i++){
		    if(chkboxDSN[i].checked){
			   B_chkboxDSN = false;
			   break;
			}
		 }
	 }
	 if(B_chkboxMail && B_chkboxDSN ){
	    showDialog({msg:'<bean:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.info.plsSelectRow" />', type:1, parentWindow:self}); 
		return;
	 }
	
	for(var i=0; i<chkboxDSN.length;i++){
		if(chkboxDSN[i].checked) {
			if(csgDoc[i]){	
				if(csgDoc[i].value!="N"){
					filterCsg=csgDoc[i].value;
					filterPaCod=paCode[i].value;
				}
			}
			if(cnt==0){
				cnt=1;
				selectDSN=chkboxDSN[i].value;
			}else{
				selectDSN=selectDSN+","+chkboxDSN[i].value;
			}
			if(routingAvl[i].value == "Y"){
			//if(routingAvl[i].checked ){
				routingFlag++;
			}          
			checked="NO";
		}
	}
	cnt=0;
	for(var i=0; i<chkboxMail.length;i++){
		if(chkboxMail[i].checked) {
			if(cnt==0){
				cnt=1;
				selectCont=chkboxMail[i].value;
			}else{
				selectCont=selectCont+"-"+chkboxMail[i].value;
			}
		checked="NO";
		}
	}
	
			
       
       if(chkboxDSN.length > 0){       
	       
		if (routingFlag>0){
		 showDialog({msg:"Some of the selected mails are already attached with a routing.", type:1, parentWindow:self}); 
			//showDialog("Some of the selected mails are already attached with a routing.", 1, self);

		}else{		
		     if(selectCont.length>0){
				var container_Array =selectCont. split("-");
				var dsn_Array =selectDSN. split(",");
				var new_select_dsn="";
				var empty_string ="";
				 if(container_Array.length>0){
				   for(var i=0;i<container_Array.length;i++){
				     if(dsn_Array.length>0){
					   for(var j=0;j<dsn_Array.length;j++){
					    if(dsn_Array[j].split("-")[0] == container_Array[i] ){						 
						  container_flag = "Y";
						  if(j==0){
						  new_select_dsn = empty_string;
						  }else{
						  new_select_dsn = new_select_dsn+",";
						  new_select_dsn =new_select_dsn+ empty_string;
						  }
						  selectDSN = "";
						}else{
						   if(j==0){
						  new_select_dsn = dsn_Array[j];
						  }else{
						  new_select_dsn = new_select_dsn+",";
						  new_select_dsn =new_select_dsn+ dsn_Array[j];
						  }
						  
						}
						
						}
					 }
				   }
				 }
				}
				if(container_flag == "Y"){
				    container_flag="";
					selectDSN = new_select_dsn;
				}
             
			submitAction(targetFormName,"/mailtracking.defaults.mailmanifest.attachroutingvalidate.do?selectChild="+selectDSN+"&parentContainer="+selectCont);
		}
			
	       }
       
       else{   
	    showDialog({msg:"No Mailbags accepted to this container", type:1, parentWindow:self}); 
       		//showDialog("No Mailbags accepted to this container",1,self);       
       }
       

}

 ////////////////// FOR ASYNC SUBMIT - AJAX - ATTACH AWB///////////////////////////////////////////////
 
  var _tableDivId = "";
  
  function recreateListDetails(strAction,divId1){
 
  	var __extraFn="updateListCode";
  	_tableDivId = divId1;
  	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
 
  }
 
  function updateListCode(_tableInfo){
 	
 	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
 	targetFormName.elements.initialFocus.value = _innerFrm.initialFocus.value;
  	targetFormName.elements.duplicateFlightStatus.value = _innerFrm.duplicateFlightStatus.value;
  	targetFormName.elements.disableSaveFlag.value = _innerFrm.disableSaveFlag.value;
  	targetFormName.elements.uldsSelectedFlag.value = _innerFrm.uldsSelectedFlag.value;
  	targetFormName.elements.uldsPopupCloseFlag.value = _innerFrm.uldsPopupCloseFlag.value;
  	targetFormName.elements.openAttachAWB.value = _innerFrm.openAttachAWB.value;
  	targetFormName.elements.pou.value = _innerFrm.pou.value;
  	targetFormName.elements.pol.value = _innerFrm.pol.value;
  	targetFormName.elements.operationalStatus.value = _innerFrm.operationalStatus.value;
  	targetFormName.elements.mailFlightSummary.value = _innerFrm.mailFlightSummary.value;
  	targetFormName.elements.type.value = _innerFrm.type.value;
  	targetFormName.elements.parentContainer.value = _innerFrm.parentContainer.value;
  	targetFormName.elements.selectChild.value = _innerFrm.selectChild.value;
  	targetFormName.elements.autoAttach.value = _innerFrm.autoAttach.value;
  	targetFormName.elements.shipmentDesc.value = _innerFrm.shipmentDesc.value;
  	
        _mailManifest_table=_tableInfo.document.getElementById("_mailManifest_table");
 	document.getElementById(_tableDivId).innerHTML=_mailManifest_table.innerHTML;
 	
 	preWarningMessages();
 	onScreenLoad();
 	
  }

function confirmMessage() {
var frm = targetFormName;
 	var warningCode = frm.warningFlag.value;
 	if(warningCode != null){
		if('flight_tba_tbc' == warningCode ){		       
        submitAction(frm,'/mailtracking.defaults.mailmanifest.listflight.do?warningOveride=Y&disableButtonsForTBA=Y');		
		}else
		if('list_flight_tba_tbc' == warningCode ){		
		submitAction(frm,'/mailtracking.defaults.mailmanifest.listmailmanifest.do');		
		}
		}
}
 ////////////////////////////////////////////////////////////////////////////////////////
 function prepareAttributes(event,obj,div,divName){
	var invId=obj.id;
	var divId;
	var indexId=invId.split('_')[1];
	if(indexId != null && indexId != ""){
	 divId=div;
	}else{
	 divId=div+'';
	}	
	//getPlatformEvent().cancelBubble = true;
	showInfoMessage(event,divId,invId,divName);	
}

function initializePanels(){

	var options_arry_polpouInfo = new Array();
	options_arry_polpouInfo = {
	  "autoOpen" : false,
	  "width" : 100,
	  "height": 78,
	  "draggable" :false,
	  "resizable" :false
	};
	   	
	   initDialog('polpouInfo',options_arry_polpouInfo);
}
function detachAWB() {
    var frm = targetFormName;
    var opflags = document.getElementsByName("operationalFlag");
    var csgdocs = document.getElementsByName("csgDocNum");
    for (var i = 0; i < opflags.length; i++) {
        if ("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
            showDialog("Please save the new Data.", 1, self);
            return;
        }
    }
    var chkboxMail = document.getElementsByName("selectMail");
    var chkboxDSN = document.getElementsByName("selectDSN");
    var close = "YES";
    var selectMail = "";

    for (var i = 0; i < chkboxMail.length; i++) {
        if (chkboxMail[i].checked) {
            close = "NO";
            selectMail = chkboxMail[i].value;
        }
    }

    if (close == "YES") {
        for (var i = 0; i < chkboxDSN.length; i++) {
            if (chkboxDSN[i].checked) {
                close = "NO";
            }
        }
        if (close == "YES") {

            showDialog({
                msg: '<bean:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.info.plsSelectRow" />',
                type: 1,
                parentWindow: self
            });
            return;
        }
    }
	
    var selectCont = "";
    var selectDSN = "";
    var cnt1 = 0;

    for (var i = 0; i < chkboxDSN.length; i++) {
        if (chkboxDSN[i].checked) {
            if (selectCont != "") {

                var checkCont = (chkboxDSN[i].value.split("-"))[0];
                if (selectCont != checkCont) {
                    showDialog({
                        msg: '<bean:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.info.selectDsns" />',
                        type: 1,
                        parentWindow: self
                    });
                    return;
                }

            }

            if (cnt1 == 0) {
                selectDSN = (chkboxDSN[i].value.split("-"))[1];
                selectCont = (chkboxDSN[i].value.split("-"))[0];
                cnt1 = 1;

            } else {
                selectDSN = selectDSN + "," + (chkboxDSN[i].value.split("-"))[1];
            }
        }
    }

    if (chkboxDSN.length > 0) {

        if (selectCont == "") {
            cnt1 = 0;
            selectCont = selectMail;
            for (var i = 0; i < chkboxDSN.length; i++) {

                if (selectCont == (chkboxDSN[i].value.split("-"))[0]) {

                    if (cnt1 == 0) {
                        selectDSN = (chkboxDSN[i].value.split("-"))[1];
                        cnt1 = 1;

                    } else {

                        selectDSN = selectDSN + "," + (chkboxDSN[i].value.split("-"))[1];
                    }
                }

            }

        }

        var strAction = "mailtracking.defaults.mailmanifest.detachawb.do";
        var strUrl = strAction + "?selectDSN=" + selectDSN + "&parentContainer=" + selectCont;
        submitForm(frm,strUrl);

    } else {
        showDialog({
            msg: "No Mailbags accepted to this container",
            type: 1,
            parentWindow: self
        });


    }
}