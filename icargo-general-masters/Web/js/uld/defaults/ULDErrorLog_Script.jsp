<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;



	with(frm){
		evtHandler.addEvents("btnClose","onClickClose(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus(targetFormName)",EVT_BLUR);
		evtHandler.addEvents("btnList","list(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear(targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnDelete","deleteULDs(targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnAdd","addULDs()",EVT_CLICK);
		evtHandler.addEvents("btnSave","saveULDs()",EVT_CLICK);
		if(frm.selectedULDErrorLog!=null){
			evtHandler.addEvents("selectedULDErrorLog","selectedULDErrorLog(targetFormName)",EVT_CLICK);
		}
		evtHandler.addEvents("selectedULDErrorLogAll","selectedULDErrorLogAll(targetFormName)",EVT_CLICK);

		evtHandler.addIDEvents("airportLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.ulderrorlogAirport.value,'CurrentAirport','1','ulderrorlogAirport','',0)" ,EVT_CLICK);
	}
	onScreenLoad();
}   
function resetFocus(frm){
    if(!event.shiftKey){	   
		if((!frm.elements.carrierCode.disabled)
		      && (!frm.elements.carrierCode.readOnly)){
		  frm.elements.carrierCode.focus();
		}	 
	}	
}
function saveULDs(){

submitForm(targetFormName,'uld.defaults.messaging.saveulderrorlog.do');
}

function addULDs(){

//submitForm(targetFormName,'uld.defaults.messaging.addulderrorlog.do');
var divIdSeg="checkScreenRefresh_new";
	var strAction = "uld.defaults.messaging.addulderrorlog.do";

	recreateTableDetails("uld.defaults.messaging.addulderrorlog.do","div1");
}

function selectedULDErrorLog(frm){
	toggleTableHeaderCheckbox('selectedULDErrorLog', frm.selectedULDErrorLogAll);
}
function selectedULDErrorLogAll(frm){
	updateHeaderCheckBox(frm, frm.selectedULDErrorLogAll, frm.selectedULDErrorLog);
}
function list(frm){
	submitForm(frm,"uld.defaults.messaging.listulderrorlog.do");
}
function clear(frm){
	//submitForm(frm,"uld.defaults.messaging.clearulderrorlog.do");
	submitFormWithUnsaveCheck('uld.defaults.messaging.clearulderrorlog.do');
}
function onClickClose(frm){
	submitForm(frm,'uld.defaults.ulderrorlogcloseaction.do');;
}
function submitULDErrorLog(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.messaging.listulderrorlog.do";
	targetFormName.submit();
}
function onScreenLoad(){
  frm=targetFormName;
   if(targetFormName.elements.screenFlag.value=="screenload"){
  	disableLink(btnAdd);
  	disableLink(btnDelete);
  	targetFormName.elements.btnSave.disabled=true;
    }
    if(targetFormName.elements.screenFlag.value=="list"){
     if(targetFormName.elements.btnSave.privileged == 'Y'){
    	targetFormName.elements.btnSave.disabled=false;
    	}
  }
  if(frm.elements.carrierCode!=null && frm.elements.errorCode.value!="error15"){
    frm.elements.carrierCode.focus();
  }
  frm.elements.errorCode.value="";
  if(frm.elements.uldDisableStat.value=="GHA"){
	//frm.ulderrorlogAirport.disabled = true;
	//frm.airportLov.disabled = true;
  }
  if(frm.elements.dupStat.value == "Y"){
  	openPopUp("flight.operation.validateFlights.do","600","280");
  }
  if(frm.elements.returnTxn.value == "RETURNTXN"){
  	frm.elements.returnTxn.value = "";
    var strAction="uld.defaults.transaction.screenloadreturnuldtransaction.do";
    openPopUp(strAction,935,470);
  }
  if(frm.elements.returnTxn.value == "RECORDULDMOVT"){
	frm.returnTxn.value = "";
	var strAction ="uld.defaults.misc.screenloadrecorduldmovement.do";
	openPopUp(strAction,950,480);
  }
}

function reconcileULD(name,index){
	var freq=index.id;
	var rowindex=freq.split(name)[1];
	targetFormName.elements.uldrowindex.value=rowindex;
	targetFormName.action ="uld.defaults.messaging.reconcileulderror.do";
	targetFormName.submit();
}

function deleteULDs(frm){
	if(validateSelectedCheckBoxes(targetFormName,'selectedULDErrorLog',10000,1)){
	
	
	showDialog({msg:'Do you want to delete the selected rows?',
							type:4,
							parentWindow:self,
							parentForm:frm,
							dialogId:'id_1',
							onClose:function(result){
								screenConfirmDialog(frm,'id_1');
			        screenNonConfirmDialog(frm,'id_1');
							}
						})
		
	}
}
function screenConfirmDialog(frm, dialogId) {


	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {

 	if(dialogId=='id_1'){

	//submitForm(frm,"uld.defaults.messaging.deleteulderrorlog.do");
			var divIdSeg="checkScreenRefresh_new";
			var strAction = "uld.defaults.messaging.deleteulderrorlog.do";

		recreateTableDetails("uld.defaults.messaging.deleteulderrorlog.do","div1");
	}

	}
}

 function screenNonConfirmDialog(frm, dialogId) {

 	while(frm.currentDialogId.value == ''){

 	}

 	if(frm.currentDialogOption.value == 'N') {
	if(dialogId=='id_1'){
	}

	}
 	}

function confirmMessage(){
	frm = targetFormName;
	frm.elements.errorCode.value="error15";
	submitForm(frm,"uld.defaults.messaging.updatetransit.do");
}


function nonconfirmMessage(){
	frm = targetFormName;
	frm.elements.errorCode.value="error15";
	//submitForm(frm,'uld.defaults.messaging.deleteulderrorlog.do');
}

////////////////// ASYNC SUBMIT //////////////////

	var _currDivId="";

	function clearTable(){
		document.getElementById(_currDivId).innerHTML="";
	}

	function recreateTableDetails(strAction,divId){

		var __extraFn="updateTableCode";

		if(arguments[2]!=null){
			__extraFn=arguments[2];
		}

		//_currDivId=divId;

		asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
	}


	function checkScreenRefresh_new(_tableInfo){
		//alert("_tableInfo"+_tableInfo);
		//document.getElementById("tmpSpan").innerHTML=_tableInfo;
		//alert(document.getElementById("tmpSpan").getElementsByTagName("form")[0]);
		_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
		updateTableForCode(_tableInfo);
	}

	function updateTableCode(_tableInfo){


		_str = _tableInfo.getTableData();

		//_str=getActualData(_tableInfo);

		//document.getElementById(_currDivId).innerHTML=_str;


		document.getElementById(_tableInfo.currDivId).innerHTML=_str;


		/*cleanupTmpTable();
		reapplyEvents();
		applyScrollTableStyles();*/


	}

	function getActualData(_tableInfo){


		document.getElementById("tmpSpan").innerHTML=_tableInfo;

		_frm=document.getElementById("tmpSpan").getElementsByTagName("table")[0];

		return _frm.outerHTML;

	}

	function cleanupTmpTable(){
		document.getElementById("tmpSpan").innerHTML="";
}