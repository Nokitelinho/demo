<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;
	//frm.carrierCode.focus();

	onScreenLoad();
	with(frm){
		evtHandler.addEvents("btnClose","onClickClose(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnList","list(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnSend","sendUCM(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnViewULDs","viewULDs(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnReconcileUCM","onClickUCMReconcile(targetFormName)",EVT_CLICK);
		
	}

}

function list(frm){
	//var frm = targetFormName;
	
	frm.elements.listflag.value="Y";
	frm.elements.displayPage.value="1";
	//targetFormName.listflag.value="Y"; A-5116 for ICRD-50252
	submitForm(frm,"uld.defaults.messaging.listucmerrorlog.do");
}
function clear(frm){
	//submitForm(frm,"uld.defaults.messaging.clearucmerrorlog.do");
	submitFormWithUnsaveCheck('uld.defaults.messaging.clearucmerrorlog.do');
}
function onClickClose(frm){
	submitForm(frm,'uld.defaults.ucmerrorlogcloseaction.do');//Modified by A-7359 fro ICRD-225848 starts here
}

function onClickUCMReconcile(frm){
	if(validateSelectedCheckBoxes(targetFormName,'selectedUCMErrorLog',2,1)){
	var chkbox = document.getElementsByName("selectedUCMErrorLog");
	var errorCodes = document.getElementsByName("errorCodes");
	var chkboxcount = chkbox.length;
	for(var i=0;i<chkbox.length;i++) {
		if(chkbox[i].checked){
			if(errorCodes[i].value=="E1" || errorCodes[i].value=="E2"){
				reconcileDup();
				return;
			}
			if(errorCodes[i].value=="E9"){
				reconcileMismatch();
				return;
			}
			if(errorCodes[i].value=="E13"){
				reconcileMismatch();
				return;
			}
		
				showDialog({msg:'No errors exist for the UCM',
							type:1,
							parentWindow:self,
							parentForm:targetFormName
						});
			break;
		}
	}
}}

function submitUCMErrorLog(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	targetFormName.elements.listflag.value="N";
	targetFormName.action ="uld.defaults.messaging.listucmerrorlog.do";
	targetFormName.submit();
}

function reconcileDup(){
if(validateSelectedCheckBoxes(targetFormName,'selectedUCMErrorLog',2,2)){
submitForm(targetFormName,"uld.defaults.messaging.selectduplicateucmreconcile.do");
}
}

function reconcileMismatch(){
if(validateSelectedCheckBoxes(targetFormName,'selectedUCMErrorLog',1,1)){
	submitForm(targetFormName,"uld.defaults.messaging.selectduplicateucmreconcile.do");
}

	}

function showULDDetails(name,index){


var freq=index.id;
var rowindex=freq.split(name)[1];

targetFormName.elements.rowindex.value=rowindex;
targetFormName.action ="uld.defaults.messaging.showulderrorlog.do";
targetFormName.submit();

}

function onScreenLoad(){
  frm=targetFormName;
  if(frm.listStatus.value!="N"){
	disableField(frm.elements.btnReconcileUCM);
	disableField(frm.elements.btnSend);
	disableField(frm.elements.btnViewULDs);
  }
  
  //frm.carrierCode.disabled = true;
  frm.elements.flightNo.focus();
  if(frm.elements.ucmDisableStat.value=="GHA"){
 	frm.elements.carrierCode.disabled = false;
 	frm.elements.carrierCode.focus();
  	//frm.ucmerrorlogAirport.disabled = true;
  	document.getElementById("airportLovImg").disabled = true;
  }
  if(frm.duplicateStatus.value == "Y"){
         openPopUp("flight.operation.validateFlights.do","600","280");
  }
  if(frm.reconcileStatus.value=="Y"){
	  var carCode=frm.elements.carrierCode.value;
	frm.elements.reconcileStatus.value="";
	  var fltNum=frm.elements.flightNo.value;
	  var status=frm.elements.mismatchStatus.value;

	  openPopUp("uld.defaults.messaging.screenloadduplicateucmreconcile.do?carrierCode="+carCode+"&flightNo="+fltNum+"&mismatchStatus="+status,"800","400");

	  }

}

function sendUCM(frm){
	if(validateSelectedCheckBoxes(targetFormName,'selectedUCMErrorLog',1,1)){
		var chkbox = document.getElementsByName("selectedUCMErrorLog");
		var hasULDError = document.getElementsByName("hasULDError");
		var hasUCMError = document.getElementsByName("hasUCMError");
		var chkboxcount = chkbox.length;
		for(var i=0;i<chkbox.length;i++) {
			if(chkbox[i].checked){
				if(hasUCMError[i].value=="true"){

					//showDialog("UCM Errors exist", 1, self);
					
	showDialog({msg:'UCM Errors exist. Do you wish to continue?',
							type:4,
							parentWindow:self,
							parentForm:targetFormName,
							dialogId:'id_2',
							onClose:function(result){
								screenConfirmDialog(targetFormName,'id_2');
			        screenNonConfirmDialog(targetFormName,'id_2');
							}
						})
					

					return;
				}
				if(hasULDError[i].value=="true"){
					//showDialog("ULD Errors exist for the UCM", 1, self);
					showDialog({msg:'ULD Errors exist for the UCM. Do you wish to continue?',
							type:4,
							parentWindow:self,
							parentForm:targetFormName,
							dialogId:'id_3',
							onClose:function(result){
								screenConfirmDialog(targetFormName,'id_3');
			        screenNonConfirmDialog(targetFormName,'id_3');
							}
						});
					
					return;
				}
			}
		}
		targetFormName.flightValidationStatus.value="Y";
		//submitForm(frm,"uld.defaults.messaging.senducmmsg.do");
		openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=UCM&targetAction=uld.defaults.messaging.senducmmsg.do",1050,320);
	}
}


function screenConfirmDialog(frm, dialogId) {
	var frm = targetFormName;
	while(frm.currentDialogId.value == ''){

 	}

	if(frm.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_2'){
			targetFormName.flightValidationStatus.value="Y";
			//submitForm(frm,"uld.defaults.messaging.senducmmsg.do");
			openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=UCM&targetAction=uld.defaults.messaging.senducmmsg.do",1050,320);
		}
		if(dialogId == 'id_3'){
			targetFormName.flightValidationStatus.value="Y";
			//submitForm(frm,"uld.defaults.messaging.senducmmsg.do");
			openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=UCM&targetAction=uld.defaults.messaging.senducmmsg.do",1050,320);
		}
 	}
 }


 function screenNonConfirmDialog(frm, dialogId) {
	var frm = targetFormName;
  	while(frm.currentDialogId.value == ''){

  	}

  	if(frm.currentDialogOption.value == 'N') {
  		if(dialogId == 'id_2'){

  		}
  	}
 }
function viewULDs(frm){
	if(validateSelectedCheckBoxes(targetFormName,'selectedUCMErrorLog',1,1)){
		targetFormName.rowindex.value="fromviewuldsbutton";
		targetFormName.action ="uld.defaults.messaging.showulderrorlog.do";
		targetFormName.submit();
	}
}