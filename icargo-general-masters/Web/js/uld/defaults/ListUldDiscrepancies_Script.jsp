<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
	var frm=targetFormName;
	//onScreenloadSetHeight();
	with(frm){
		disable();
		evtHandler.addEvents("btnList","onList();",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClear();",EVT_CLICK);
		evtHandler.addEvents("btnCloseDiscrepancy","onCloseDiscrepancy();",EVT_CLICK);
		evtHandler.addEvents("btnCreate","onCreate();",EVT_CLICK);
		evtHandler.addEvents("btnDetails","onDetails();",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClose();",EVT_CLICK);
		evtHandler.addEvents("airlineCode","validateFields(this, -1, 'Airline Code', 0, true, true)",EVT_BLUR);
		evtHandler.addEvents("uldNo","validateFields(this, -1, 'ULDNo', 0, true, true)",EVT_BLUR);
		evtHandler.addEvents("reportingStation","validateFields(this, -1, 'Reporting Station', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("ownerStation","validateFields(this, -1, 'Owner Station', 1, true, true)",EVT_BLUR);
		evtHandler.addIDEvents("airlinecodelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'AirlineCode','1','airlineCode','',0)",EVT_CLICK);
		evtHandler.addIDEvents("reportingstationlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.reportingStation.value,'ReportingStation','1','reportingStation','',0)",EVT_CLICK);
		evtHandler.addIDEvents("ownerstationlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.ownerStation.value,'OwnerAirport','1','ownerStation','',0)",EVT_CLICK);

		if(frm.elements.uldNo.disabled==false){
			frm.elements.uldNo.focus();
		}
		if(frm.elements.rowId!=null){
			evtHandler.addEvents("rowId","selectedRows()",EVT_CLICK);
		}
		evtHandler.addEvents("masterRowId","selectedrowsAll()",EVT_CLICK);
		onScreenLoad();
	}
		//changed by a-3045 for bug22302
		 applySortOnTable("ulddiscrepencies",new Array("None","String","String","Date","String","String","String","String"));

}
/*function onScreenloadSetHeight(){
 	jquery('#div1').height((((document.body.clientHeight)*0.79)-200));

}*/

function disable(){
//Commented By A-6841 for ICRD-185395 
	/*if(targetFormName.elements.discDisableStat.value=="airline"){
		targetFormName.elements.airlineCode.disabled = true;
	
		//targetFormName.elements.airlinecodelov.disabled = true;
		document.getElementById("airlinecodelov").disabled = true;
	}*/
	if(targetFormName.elements.discDisableStat.value=="GHA"){

		targetFormName.elements.reportingStation.disabled = true;
		//targetFormName.reportingstationlov.disabled = true;
		document.getElementById("reportingstationlov").disabled = true;
	}
}

function onScreenLoad(){
	var frm = targetFormName;
	if(frm.elements.buttonStatusFlag.value != null && frm.elements.buttonStatusFlag.value=="CreateDiscrepancy"){
		openPopUp(appPath+"/uld.defaults.listulddiscrepancies.creatediscrepancy.do",'470','210');
	}
	else if(frm.elements.buttonStatusFlag.value == "savedSuccessfully"){
		window.opener.targetFormName.elements.action = "uld.defaults.listulddiscrepancies.listulddiscrepancies.do";
		window.opener.targetFormName.submit();
	}
	if(frm.elements.listStatus.value !="afterlist"){
		frm.elements.listStatus.value="";
		frm.elements.btnCloseDiscrepancy.disabled=true;
		frm.elements.btnCreate.disabled=true;
		frm.elements.btnDetails.disabled=true;
	}
}

function selectedRows(){
	var frm=targetFormName;
	toggleTableHeaderCheckbox('rowId', frm.elements.masterRowId);
}

function selectedrowsAll(){
	var frm=targetFormName;
	updateHeaderCheckBox(frm, frm.elements.masterRowId, frm.elements.rowId);
}


function onList(){
	var frm = targetFormName;
	frm.elements.fromList.value="Y";
	
	<!-- Modified by A-5220 for ICRD-22824 starts -->
	frm.elements.displayPage.value='1';
	frm.elements.lastPageNum.value='0';
	submitForm(frm,'uld.defaults.listulddiscrepancies.listulddiscrepancies.do?navigationMode=list');
	<!-- Modified by A-5220 for ICRD-22824 ends -->
}

function onClear(){
	var frm = targetFormName;
	//submitForm(frm,'uld.defaults.listulddiscrepancies.clearulddiscrepancies.do');
	submitFormWithUnsaveCheck('uld.defaults.listulddiscrepancies.clearulddiscrepancies.do');
}

function onCloseDiscrepancy(){
	var frm = targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'rowId', 1000000000000, 1);
	if (check){
		showDialog({msg:'Do you want to close the discrepancy?', type:4, parentWindow:self,parentForm: frm, dialogId:'id_1',
		onClose:function(result){
		screenConfirmDialog(frm,'id_1');
		screenNonConfirmDialog(frm,'id_1');
	}
	});
}
}
function onCreate(){
	var frm = targetFormName;
	var flag = "createmode";
	var uldNoChild = frm.elements.uldNo.value;
	var reportingStationChild = frm.elements.reportingStation.value;
	submitForm(frm,'uld.defaults.listulddiscrepancies.creatediscrepancy.do?flag='+flag+'&reportingStationChild'+reportingStationChild+'&uldNoChild'+uldNoChild);
}

function onDetails(){
	var frm = targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'rowId', 1000000000000, 1);
	if (check){
		var flag="listulddiscrepancies";
		var flag = "modifymode";
		submitForm(frm,"uld.defaults.listulddiscrepancies.detailsulddiscrepancies.do?closeFlag="+flag+"&flag"+flag);
	}
}

function onClose(){
	var frm = targetFormName;
	submitForm(frm,'uld.defaults.closeaction.do');
}

function selectNextDetail(lastPg,displayPg){
	var frm = targetFormName;
	frm.elements.fromList.value="Y";
	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	<!-- Modified by A-5220 for ICRD-22824 starts -->
	submitForm(targetFormName, appPath + '/uld.defaults.listulddiscrepancies.listulddiscrepancies.do?navigationMode=navigation');
	<!-- Modified by A-5220 for ICRD-22824 ends -->
}

function screenConfirmDialog(frm, dialogId){
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.listulddiscrepancies.closediscrepancyulddiscrepancies.do');
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}