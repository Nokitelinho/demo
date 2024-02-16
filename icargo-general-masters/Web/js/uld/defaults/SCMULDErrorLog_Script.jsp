<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;
	//frm.scmUldAirport.focus();
	onScreenLoad();

	with(frm){
		evtHandler.addEvents("btnClose","onClickClose(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus(targetFormName)",EVT_BLUR);
		evtHandler.addEvents("btnList","list(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnDelete","deleteUlds(targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.airline.value,'airline','1','airline','',0)",EVT_CLICK);
		evtHandler.addEvents("scmseqnolov","showSCMSeqLov()",EVT_CLICK);
		evtHandler.addEvents("uldNumber","validateAlphanumeric(targetFormName.uldNumber,'ULD Number','Invalid ULD Number',true)",EVT_BLUR);

		if(frm.elements.selectedUlds!=null){
					evtHandler.addEvents("selectedUlds","selectedUlds(targetFormName)",EVT_CLICK);
				}
		evtHandler.addEvents("checkUldsAll","checkUlds(targetFormName)",EVT_CLICK);

	}

}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				/*if((!frm.elements.scmUldAirport.disabled) && (!frm.elements.scmUldAirport.readOnly)
					&& (frm.elements.scmULDDisable.value != 'GHA')){*/
					if(frm.elements.scmUldAirport.readOnly==true){
							if(targetFormName.elements.listStatus.value!='N'){
					frm.elements.scmUldAirport.focus();
				}								
							else{
								document.getElementById('headerCheckBox').focus();
							}
							
				}
					else{
						frm.elements.scmUldAirport.focus();
						if(targetFormName.elements.listStatus.value!='N'){
									frm.elements.scmUldAirport.focus();
							}								
							else{
								document.getElementById('headerCheckBox').focus();
							}
					}
	}	
}

function deleteUlds(frm){
if(validateSelectedCheckBoxes(frm,'selectedUlds',1000000,1)){
		showDialog({msg:'Do you want to delete the selected ulds?', type:4, parentWindow:self, parentForm:frm, dialogId:'id_2',
			onClose:function(result){
					screenConfirmDialog(frm,'id_2');
			        screenNonConfirmDialog(frm,'id_2');
							}});
		}

	}

function selectedUlds(frm){
	toggleTableHeaderCheckbox('selectedUlds', frm.elements.checkUldsAll);
}


function checkUlds(frm){
	updateHeaderCheckBox(frm, frm.checkUldsAll, frm.elements.selectedUlds);
}

function list(frm){
targetFormName.elements.displayPage.value="1";
submitForm(frm,"uld.defaults.messaging.listscmulderrorlog.do");
}
function clear(frm){
//submitForm(frm,"uld.defaults.messaging.clearscmulderrorlog.do");
submitFormWithUnsaveCheck('uld.defaults.messaging.clearscmulderrorlog.do');
}
function onClickClose(frm){

submitForm(frm,'uld.defaults.closescmerrorlog.do');
}

function submitSCMErrorLog(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.messaging.listscmulderrorlog.do";
	targetFormName.submit();
}


function reconcileULD(name,index){
	var freq=index.id;
	var rowindex=freq.split(name)[1];
	targetFormName.elements.rowIndex.value=rowindex;
	targetFormName.action ="uld.defaults.messaging.reconcilescmulderror.do";
	targetFormName.submit();
}


function onScreenLoad(){
targetFormName.elements.stockCheckdate.readOnly = true;


if(targetFormName.elements.listStatus.value!="N"){
	disableField(targetFormName.elements.btnDelete);
	targetFormName.elements.scmSeqNo.focus();
}
if(targetFormName.listStatus.value=="N"){	 //coded for ICRD-28379
document.getElementById('headerCheckBox').focus();
}
if(targetFormName.elements.scmULDDisable.value=="GHA"){
	targetFormName.elements.scmUldAirport.readOnly = true;
	targetFormName.airportLovImg.disabled = true;
}
if(targetFormName.elements.returnTxn.value == "RECORDULDMOVT"){
	targetFormName.elements.returnTxn.value = "";
	var currentStation=targetFormName.elements.scmUldAirport.value;

	var strAction ="uld.defaults.misc.screenloadrecorduldmovement.do?currentStation="+currentStation;
	openPopUp(strAction,950,480);
  }

  if(targetFormName.elements.returnTxn.value == "RETURNTXN"){
      	targetFormName.elements.returnTxn.value = "";
        var strAction="uld.defaults.transaction.screenloadreturnuldtransaction.do";
        openPopUp(strAction,935,470);
  }

}


function screenConfirmDialog(frm, dialogId) {


	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {


 		if(dialogId == 'id_2'){

		submitForm(targetFormName,"uld.defaults.messaging.deletescmulds.do");
 		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {
 		if(dialogId == 'id_2'){
 		}

}


function showSCMSeqLov(){
	if(targetFormName.elements.airline.value.trim().length==0){
	showDialog({msg:'Enter Airline Code', type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_3'});
	targetFormName.airline.focus();
	return;
	}
	var textfiledDesc="";
	var airlineCode=targetFormName.elements.airline.value;
	var arpCode=targetFormName.elements.scmUldAirport.value;
	var seqNo=targetFormName.elements.scmSeqNo.value;
	var textfiledDate="stockCheckdate";
	var textfiledTime="scmStockCheckTime";
	var strAction="uld.defaults.messaging.screenloadscmseqnolov.do";
	var StrUrl=strAction+"?textfiledObj=scmSeqNo&formNumber=1&textfiledDate="+textfiledDate+"&textfiledTime="+textfiledTime+"&airline="+airlineCode+"&airportCode="+arpCode+"&sequenceNo="+seqNo;
	var myWindow = openPopUpWithHeight(StrUrl, "500");
}

function submitscmulderrorlog(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.messaging.listscmulderrorlog.do";
	targetFormName.submit();
}