<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   selectDiv();
   onScreenLoad(frm);
   
  
   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnList","listReassignMail()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearReassignMail()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeReassignMail()",EVT_CLICK);
	evtHandler.addEvents("btnSave","saveReassignMail()",EVT_CLICK);
	evtHandler.addIDEvents("addLink","addContainer()",EVT_CLICK);
	
     //BLUR Events

   	}
}

function onScreenLoad(frm){
  frm=targetFormName;
  
   if(frm.elements.preassignFlag.value == "N")
  {
   if(frm.elements.hidScanTime.value=="Y"){   	
    	enableLink(document.getElementById("addLink"));   
    	 
   } 
   else{
  
   		disableLink(document.getElementById("addLink"));		
   }
   }
  if(frm.elements.fromScreen.value == "INVENTORY_LIST"){
		targetFormName.elements.btnSave.disabled=false;
  }
  if(frm.elements.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.duplicateflight.do","600","280");
  }
  if(frm.elements.initialFocus.value == "Y"){
        frm.elements.initialFocus.value = "";
        frm.elements.flightCarrierCode.focus();
  }
  //added by a-7871 starts--
if(frm.elements.embargoFlag.value == "embargo_exists"){
        frm.elements.embargoFlag.value = "";
		openPopUp("reco.defaults.showEmbargo.do", 900,400);
   }
   //added by a-7871 ends--
  if(frm.elements.closeFlag.value == "Y"){
    frm.elements.closeFlag.value = "";

	 if(frm.elements.fromScreen.value == "MAILBAG_ENQUIRY"){
	 //Modified by A-7794 as part of ICRD-223414
	 window.opener.IC.util.common.childUnloadEventHandler();
	        frm.elements.fromScreen.value = "";
	        frm = self.opener.targetFormName;
	        frm.elements.category.disabled = false;
		frm.elements.currentStatus.disabled = false;
		frm.elements.damaged.disabled = false;
		frm.elements.displayPage.value=1;
		frm.elements.lastPageNum.value=0;
		frm.action="mailtracking.defaults.mailbagenquiry.list.do";
		frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler();
		frm.submit();
		window.close();
		return;
	    }
	    if(frm.elements.fromScreen.value == "DSN_ENQUIRY"){
	        IC.util.common.childUnloadEventHandler();
	        frm.elements.fromScreen.value = "";
	        frm = self.opener.targetFormName;
	    	frm.action="mailtracking.defaults.dsnenquiry.listdetails.do";
	    	frm.method="post";
			window.opener.IC.util.common.childUnloadEventHandler();
	    	frm.submit();
	    //	window.opener.recreateTableDetails("mailtracking.defaults.dsnenquiry.list.do","div1","chkListFlow");
	    	window.close();
	    	return;
	    }
	    if(frm.elements.fromScreen.value == "MAIL_ARRIVAL"){
		IC.util.common.childUnloadEventHandler();
		frm.elements.fromScreen.value = "";
		frm = self.opener.targetFormName;
		window.opener.targetFormName.action=appPath+"/mailtracking.defaults.mailarrival.listmailarrival.do";
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.targetFormName.submit();
		window.close();
		return;
    	    }
    	    if(frm.elements.fromScreen.value == "INVENTORY_LIST"){
		IC.util.common.childUnloadEventHandler();
		frm.elements.fromScreen.value = "";
		frm = self.opener.document.forms[1];
		//window.opener.targetFormName.action=appPath+"/mailtracking.defaults.inventorylist.list.do";
		//window.opener.targetFormName.submit();
		var strAction = "mailtracking.defaults.inventorylist.list.do";
		window.opener.recreateListDetails(strAction,'inventoryListTable','inventoryButtons','carrierText');
		window.opener.IC.util.common.childUnloadEventHandler();
		window.close();
		return;
    	    }
			if(frm.elements.fromScreen.value == "MAIL_EXPORT_LIST"){
	 window.opener.IC.util.common.childUnloadEventHandler();
	        frm.elements.fromScreen.value = "";
	        frm = self.opener.targetFormName;
		frm.action="mailtracking.defaults.mailexportlist.listflight.do";
		frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler();
		frm.submit();
		window.close();
		return;
	    }

   }

}
function okCheckEnable(){
	targetFormName.elements.btnSave.disabled = false;
}
/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

function clearReassignMail(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.transfermail.cleartransfermail.do');
}

function listReassignMail(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.transfermail.listflight.do');   
}

function closeReassignMail(){
   frm=targetFormName;
   window.opener.IC.util.common.childUnloadEventHandler();
   window.close();
}

function saveReassignMail(){
   frm=targetFormName;
   var carCode=frm.elements.carrierCode.value;
   var fltCarCod=frm.elements.flightCarrierCode.value;
   var dummyCarCode=frm.elements.dummyCarCod.value;
   var radiobuttons = document.getElementsByName("assignToFlight");
   var assignedTo="";
	for(var i=0; i<radiobuttons.length; i++){
	       if(radiobuttons[i].checked == true){
			if("DESTINATION"==targetFormName.elements.assignToFlight[i].value ){
				assignedTo="DESTINATION";
			}else{
				assignedTo="FLIGHT";
			}
	       }
	}
		if("FLIGHT"==assignedTo){
			if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){
			 	/*if((dummyCarCode.toUpperCase()!=fltCarCod.toUpperCase()) && ("MAIL_ARRIVAL" == frm.fromScreen.value)){*/
			 	if(dummyCarCode.toUpperCase()!=fltCarCod.toUpperCase()){
					showDialog({msg:'<common:message bundle="transferMailResources" key="mailtracking.defaults.transfermail.msg.warn" />',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
						}
						});
			    }else {
			    	frm.elements.printTransferManifestFlag.value="N";
					submitForm(frm,'mailtracking.defaults.transfermail.oktransfermail.do');
			    }
			}
		}else{
		    if(frm.elements.carrierCode.value == ""){
				
				showDialog({msg:'Carrier code is mandatory',type:1,parentWindow:self});			

				frm.elements.carrierCode.focus();
				return;
			}
			/*if((dummyCarCode.toUpperCase()!=carCode.toUpperCase())&& ("MAIL_ARRIVAL" == frm.fromScreen.value)){*/
			if(dummyCarCode.toUpperCase() != carCode.toUpperCase()){
						showDialog({msg:'<common:message bundle="transferMailResources" key="mailtracking.defaults.transfermail.msg.warn" />',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
						}
						});
			}else {
					frm.elements.printTransferManifestFlag.value="N";
					submitForm(frm,'mailtracking.defaults.transfermail.oktransfermail.do');
			 }
		}		

}


function selectDiv() {
    
	var radiobuttons = document.getElementsByName("assignToFlight");
	
	if(targetFormName.elements.hideRadio.value == "FLIGHT"){
	targetFormName.elements.hideRadio.value ="";
	
	
	     togglePanel(1,radiobuttons[1]);
	}else{
	       for(var i=0; i<radiobuttons.length; i++){
	               if(radiobuttons[i].checked == true){
				if(targetFormName.elements.assignToFlight[i].value =='DESTINATION'){
					okCheckEnable();
				}
			togglePanel(1,radiobuttons[i]);
			break;
		     }
	      }
		
	}

}

function togglePanel(iState,comboObj) // 1 visible, 0 hidden
{
	if(comboObj != null) {


                if(targetFormName.hideRadio.value == "FLIGHT"){
		    var divID = "DESTINATION";
		}else{
		    var divID = comboObj.value;
		}

		var comboLength = comboObj.length;
		var obj = null;
		var comboValues = null;

		var divValues = ['FLIGHT','DESTINATION'];
		var length = divValues.length;

		for(var index=0; index<length; index++) {

			if(divValues[index] == divID) {

				var divObj = document.layers ? document.layers[divID] :
							 document.getElementById ?  document.getElementById(divID).style :
							 document.all[divID].style;

				divObj.visibility = document.layers ? (1 ? "show" : "hide") :
								 (1 ? "visible" : "hidden");

				divObj.display = document.layers ? (1 ? "" : "none") :
								 (1 ? "" : "none");

				divObj.zIndex = 120;

			}
			else {

				var divAlt = document.layers ? document.layers[divValues[index]] :
							 document.getElementById ?  document.getElementById(divValues[index]).style :
							 document.all[divValues[index]].style;

				divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
					 (0 ? "visible" : "hidden");

				divAlt.display = document.layers ? (0 ? "" : "none") :
						 (0 ? "" : "none");

			}

		}
		if(divID == "DESTINATION"){

					var divAlt = document.layers ? document.layers['FLIGHTDETAILS'] :
								 document.getElementById ?  document.getElementById('FLIGHTDETAILS').style :
								 document.all['FLIGHTDETAILS'].style;

					divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
						 (0 ? "visible" : "hidden");

					divAlt.display = document.layers ? (0 ? "" : "none") :
						 (0 ? "" : "none");
					var divAlt = document.layers ? document.layers['divLinks'] :
						document.getElementById ?  document.getElementById('divLinks').style :
						document.all['divLinks'].style;
						divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
						(0 ? "visible" : "hidden");
						divAlt.display = document.layers ? (0 ? "" : "none") :
						(0 ? "" : "none");
					var divAlt = document.layers ? document.layers['div1'] :
						document.getElementById ?  document.getElementById('div1').style :
						document.all['div1'].style;
						divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
						(0 ? "visible" : "hidden");
						divAlt.display = document.layers ? (0 ? "" : "none") :
						(0 ? "" : "none");
				}
				else {

					var divObj = document.layers ? document.layers['FLIGHTDETAILS'] :
								 document.getElementById ?  document.getElementById('FLIGHTDETAILS').style :
								 document.all['FLIGHTDETAILS'].style;

					divObj.visibility = document.layers ? (1 ? "show" : "hide") :
									 (1 ? "visible" : "hidden");

					divObj.display = document.layers ? (1 ? "" : "none") :
									 (1 ? "" : "none");

					divObj.zIndex = 120;

					var divObj = document.layers ? document.layers['divLinks'] :
					document.getElementById ?  document.getElementById('divLinks').style :
					document.all['divLinks'].style;
					divObj.visibility = document.layers ? (1 ? "show" : "hide") :
									 (1 ? "visible" : "hidden");
					divObj.display = document.layers ? (1 ? "" : "none") :
									 (1 ? "" : "none");
					divObj.zIndex = 120;
					var divObj = document.layers ? document.layers['div1'] :
					document.getElementById ?  document.getElementById('div1').style :
					document.all['div1'].style;
					divObj.visibility = document.layers ? (1 ? "show" : "hide") :
									 (1 ? "visible" : "hidden");
					divObj.display = document.layers ? (1 ? "" : "none") :
									 (1 ? "" : "none");
					divObj.zIndex = 120;
				}



	}

}


function addContainer(){
frm=targetFormName;
	var stat = "CREATE";
	var fromScreen = "TRANSFER_MAIL";
	var flightCarrierCode = frm.elements.flightCarrierCode.value;
	var flightNumber = frm.elements.flightNumber.value;
	var flightDate = frm.elements.flightDate.value;
	var carrierCode = frm.elements.carrierCode.value;
	var str = "mailtracking.defaults.assigncontainer.screenloadAddUpdatePopup.do?fromScreen="+fromScreen+"&status="+stat+"&flightCarrierCode="+flightCarrierCode+"&flightNumber="+flightNumber+"&flightDate="+flightDate+"&carrier="+carrierCode;
	openPopUp(str,"600","425");
	
}

/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
	    if(dialogId == 'id_1'){	
	    	frm.elements.printTransferManifestFlag.value="Y";
				//submitForm(frm,'mailtracking.defaults.transfermail.oktransfermail.do');	
				generateReport(frm,'/mailtracking.defaults.transfermail.oktransfermail.do',true);
				
		}
	}
}

function reloadPage(arg) {
frm=targetFormName;
submitForm(frm,"mailtracking.defaults.transfermail.screenload.do?closeFlag=Y");
}

/**
*function to Non-Confirm Dialog
*/
function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){	
	    	frm.elements.printTransferManifestFlag.value="N";	
				submitForm(frm,'mailtracking.defaults.transfermail.oktransfermail.do');	
		}
	}
}