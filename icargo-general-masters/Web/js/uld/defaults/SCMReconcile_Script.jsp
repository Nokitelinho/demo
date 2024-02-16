<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	onScreenLoad();
	//commented by a-3278 for bug 20382 on 03Oct08
	//disable(targetFormName);

	with(frm){
		evtHandler.addEvents("btnClose","onClickClose(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
		evtHandler.addEvents("btnList","list(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnSend","sendScm(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnDetails","viewUlds(targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airline.value,'airline','1','airline','',0)",EVT_CLICK);
		evtHandler.addIDEvents("scmseqnolov","showSCMSeqLov()",EVT_CLICK);

		if(frm.elements.selectedSCMErrorLog!=null){
				evtHandler.addEvents("selectedSCMErrorLog","selectedSCMErrorLog(targetFormName)",EVT_CLICK);
		}
		evtHandler.addEvents("selectAllSCMErrorLog","selectAllSCMErrorLog(targetFormName)",EVT_CLICK);


	}

}
function resetFocus(frm){
	 if(!event.shiftKey){ 
				if(targetFormName.elements.listStatus.value == ""){
					frm.elements.airport.focus();
				}
				else{
					 if(!frm.elements.selectAllSCMErrorLog.disabled){
						frm.elements.selectAllSCMErrorLog.focus();
					 }
				}				
	}	
}
function onScreenLoad(){

        //added by a-3045 for BUG_5750 starts
         
		 if(targetFormName.elements.listStatus.value == ""){	//code changed for ICRD-28379
			 //targetFormName.airport.readOnly= true;
         
       		//targetFormName.airline.readOnly= true;
			 //targetFormName.airlinelov.disabled= true;
			 //targetFormName.airportlov.disabled= true;
			if(targetFormName.elements.airline.readOnly == false){


			targetFormName.elements.airline.focus();
		}
		 }
		 
		 else{
			document.getElementById('headerCheckBox').focus();
		 }
       	//added by a-3045 for BUG_5750 ends


	//if(targetFormName.msgFlag.value=="TRUE"){
			
			//openPopUp("msgbroker.message.resendmessages.do?openPopUpFlg=ULD",800,425);
		//targetFormName.msgFlag.value="FALSE";
	//}commented by A-5844 for ICRD-98781
	if(targetFormName.elements.listStatus.value!="N"){
		disableField(targetFormName.btnDetails);
		disableField(targetFormName.btnSend);
	}
}
function selectedSCMErrorLog(frm){
	toggleTableHeaderCheckbox('selectedSCMErrorLog', frm.elements.selectAllSCMErrorLog);
}

function selectAllSCMErrorLog(frm){
	updateHeaderCheckBox(frm, frm.elements.selectAllSCMErrorLog, frm.elements.selectedSCMErrorLog);
}

function disable(frm){
	if(frm.elements.scmDisable.value=="GHA"){
		frm.elements.airport.disabled = true;
		frm.elements.airportLovImg.disabled = true;
	}
}

function list(frm){
	frm.elements.listflag.value="Y";
	frm.elements.displayPage.value= 1;
    frm.elements.lastPageNumber.value= 0;
	submitForm(frm,'uld.defaults.messaging.listscmerrorlog.do?navigationMode=LIST');
}
function clear(frm){
//submitForm(frm,"uld.defaults.messaging.clearscmreoncile.do");
targetFormName.elements.airport.readOnly= false;
submitFormWithUnsaveCheck('uld.defaults.messaging.clearscmreoncile.do');
}
function onClickClose(frm){
submitForm(frm,'uld.defaults.closeaction.do');;
}

function submitList(strLastPageNum,strDisplayPage){
	targetFormName.elements.listflag.value=="";
	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.messaging.listscmerrorlog.do?navigationMode=NAVIGATION";
	targetFormName.submit();
}


function showULDDetails(name,index){


var freq=index.id;
var rowindex=freq.split(name)[1];

targetFormName.elements.rowIndex.value=rowindex;
targetFormName.action ="uld.defaults.messaging.showscmulderrorlog.do";
targetFormName.submit();

}



function sendScm(){
if(validateSelectedCheckBoxes(targetFormName,'selectedSCMErrorLog',1,1)){
	var hasErrors = document.getElementsByName("hiddenError");
	var selectedRows = document.getElementsByName("selectedSCMErrorLog");
	for(var i=0;i<selectedRows.length;i++){
		if(selectedRows[i].checked){
			if(hasErrors[i].value == "true"){
				showDialog({msg:'SCM has errors. Do you want to continue?', type:4, parentWindow:self, parentForm:targetFormName, dialogId:'id_1'});
				//showDialog('SCM has errors.Do you want to continue?', 4, self, targetFormName, 'id_1');
				screenConfirmDialog(targetFormName,'id_1');
				screenNonConfirmDialog(targetFormName,'id_1');
				}else{
				
			//submitForm(targetFormName,"uld.defaults.messaging.sendscmmessagefromscmerr.do");
			//showDialog("<common:message bundle='scmReconcileResources' key='uld.defaults.messaging.additionaladdresseswarning' />", 4, self, targetFormName, 'id_12');
				
				//Added by A-7548 for ICRD-205986 starts
				showDialog({msg:'<common:message bundle="scmReconcileResources" key="uld.defaults.messaging.additionaladdresseswarning" />', type:4, parentWindow:self, parentForm:targetFormName, dialogId:'id_12',
						onClose: function (result) {
							if(targetFormName.elements.currentDialogOption.value == 'Y') {
			screenConfirmDialog(targetFormName,'id_12');
							}else if(targetFormName.elements.currentDialogOption.value == 'N') {
			screenNonConfirmDialog(targetFormName,'id_12');
			}
						}});
		         //ICRD-205986 ends
			}
		}

	}
	//submitForm(targetFormName,"uld.defaults.messaging.sendscmmessagefromscmerr.do")
	}
}

function viewUlds(){
		if(validateSelectedCheckBoxes(targetFormName,'selectedSCMErrorLog',1,1)){
	submitForm(targetFormName,"uld.defaults.messaging.viewscmulds.do");
}

	}

function showSCMSeqLov(){
	if(targetFormName.elements.airline.value.trim().length==0){
		//showDialog("Enter Airline Code", 1, self, targetFormName, 'id_3');
		showDialog({msg:'Enter Airline Code', type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_3'});
		targetFormName.elements.airline.focus();
		return;
	}
	var textfiledDate="stockChkdate";
	var textfiledTime="scmStockCheckTime";
	var airlineCode=targetFormName.elements.airline.value;
	var arpCode=targetFormName.elements.airport.value;
	var seqNo=targetFormName.elements.seqNo.value;
	var strAction="uld.defaults.messaging.screenloadscmseqnolov.do";
	var StrUrl=strAction+"?textfiledObj=seqNo&formNumber=1&textfiledDate="+textfiledDate+"&textfiledTime="+textfiledTime+"&airline="+airlineCode+"&airportCode="+arpCode+"&sequenceNo="+seqNo;
	var myWindow = openPopUpWithHeight(StrUrl, "500");
}
//Added by A-2408 for INT1104
function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {
 		if(dialogId == 'id_1'){
			//commented by A-5844 for ICRD-98781 starts
			//submitForm(targetFormName,"uld.defaults.messaging.sendscmmessagefromscmerr.do");
			showDialog("<common:message bundle='scmReconcileResources' key='uld.defaults.messaging.additionaladdresseswarning' />", 4, self, frm, 'id_12');
			screenConfirmDialog(frm,'id_12');
			screenNonConfirmDialog(frm,'id_12');
 		}
		if(dialogId == 'id_12'){
		openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=SCM&targetAction=uld.defaults.messaging.sendscmmessagefromscmerr.do",1050,320);
 		}
		//commented by A-5844 for ICRD-98781 ends
	}
}

function screenNonConfirmDialog(frm, dialogId) {


 		if(dialogId == 'id_1'){
		return;
 		}
	//commented by A-5844 for ICRD-98781 starts
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_12'){
				submitForm(targetFormName,"uld.defaults.messaging.sendscmmessagefromscmerr.do");
		}
	}
	//commented by A-5844 for ICRD-98781 ends
}
//ends