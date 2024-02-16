<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister()
{


	with(targetFormName){
		evtHandler.addEvents("listButton","doList()",EVT_CLICK);
		evtHandler.addEvents("clearButton","doClear()",EVT_CLICK);
		evtHandler.addEvents("processButton","doProcess()",EVT_CLICK);
		//evtHandler.addEvents("closeflightButton","doCloseFlight()",EVT_CLICK);
		evtHandler.addEvents("closeButton","doClose()",EVT_CLICK);
		evtHandler.addEvents("closeButton","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addEvents("saveButton","doSave()",EVT_CLICK);
		//evtHandler.addEvents("detailsButton","onAwbEnquiry()",EVT_CLICK);
		//evtHandler.addEvents("printButton", "onPrintAWBExceptions()", EVT_CLICK);
		if(targetFormName.elements.rowId!=null){
		  evtHandler.addEvents("selectedElements","select(this.form)",EVT_CLICK);
		}
		evtHandler.addEvents("headChk","selectAll(this.form)",EVT_CLICK);


		//evtHandler.addEvents("btnRouting","onClickRouting()",EVT_CLICK);
		evtHandler.addEvents("exceptionlov","invokeExceptionsLov()",EVT_CLICK);

	}
	targetFormName.elements.flightCarrierCode.focus();
	disableField(targetFormName.elements.saveButton);
	disableField(targetFormName.elements.processButton);
	//targetFormName.elements.closeflightButton.disabled=true;
	disableMultiButton(document.getElementById('CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_MAINPRINT'));
	checkStatus();
}
//functions for toggling of check box
function select(frm){
	toggleTableHeaderCheckbox('selectedElements', targetFormName.elements.headChk);
}

function selectAll(frm){
	updateHeaderCheckBox(targetFormName, targetFormName.elements.headChk, targetFormName.elements.selectedElements);
}
//function for list
function doList() {//alert(targetFormName.elements.descriptionField.value);
	targetFormName.elements.statusFlag.value = "toList";
	submitForm(targetFormName,'mailtracking.mra.flown.assignexceptions.listexceptions.do');

}
//function for clear
function doClear() {
	submitForm(targetFormName,'mailtracking.mra.flown.assignexceptions.clearexceptions.do');
}
//function for process
function doProcess() {
	submitForm(targetFormName,'mailtracking.mra.flown.assignexceptions.processexceptions.do');
}
//function for close flight
function doCloseFlight() {
	submitForm(targetFormName,'mailtracking.mra.flown.assignexceptions.closeflight.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.flightCarrierCode.disabled == false && targetFormName.elements.flightCarrierCode.readOnly== false){
			targetFormName.elements.flightCarrierCode.focus();
		}
	}
}
//function for Close
function doClose() {
	if(targetFormName.elements.viewFlownMailFlag.value=="Y"){
		submitForm(targetFormName,'mra.flown.viewflownmail.listflownmails.do?fromExceptionScreenFlag='+"Y");
		targetFromName.viewFlownMailFlag.value="";
	}
	else{
		submitForm(targetFormName,'mailtracking.mra.flown.assignexceptions.closeexceptions.do');
	}
}

function doPrint() {
	generateReport(targetFormName,'/mailtracking.mra.flown.assignexceptions.print.do');

}

function doPrintDetails() {
	generateReport(targetFormName,'/mailtracking.mra.flown.assignexceptions.printdetails.do');
}

//function for Save
function doSave() {
	setHdOperationFlag();
	submitForm(targetFormName,'mailtracking.mra.flown.assignexceptions.saveexceptions.do');
}
//function for updation of operatinFlag

function setHdOperationFlag(){

	var frm = targetFormName;
	var operationFlag = eval(frm.elements.operationFlag);   //for operation flag


 	var rowIds = eval(frm.elements.selectedElements);

	var asigneeCodes = eval(frm.elements.asigneeCodes);

	if (rowIds != null) {

	 		if (rowIds.length > 1) {

	 			for (var i = 0; i < rowIds.length; i++) {


	 				var checkBoxValue = rowIds[i].value;



	 					if(operationFlag[checkBoxValue].value !='U')

	 				  	 {

							if ((asigneeCodes[checkBoxValue].value != asigneeCodes[checkBoxValue].defaultValue))
							{


 								frm.elements.operationFlag[checkBoxValue].value='U';

	 						}

	 					}

	 			}

	 		}else{
	 			if ((asigneeCodes.value != asigneeCodes.defaultValue))
				{

					frm.elements.operationFlag.value='U';

	 			}
	 		}

	 }
}



function checkStatus() {

//added for resolution starts
//var clientHeight = document.body.clientHeight;
//var clientWidth = document.body.clientWidth;

//alert(clientHeight);
//document.getElementById('contentdiv').style.height = ((clientHeight*86)/100)+'px';
//document.getElementById('outertable').style.height=((clientHeight*83)/100)+'px';

//alert(document.getElementById('outertable').style.height);

//var pageTitle=35;
//var filterlegend=100;
//var filterrow=40;
//var bottomrow=60;
//var height=(clientHeight*83)/100;
//var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
//document.getElementById('div1').style.height=tableheight+'px';
// added for resolution ends

	if(targetFormName.elements.statusFlag.value == "toSave") {
	  if(targetFormName.elements.saveButton.privileged == 'Y'){
		enableField(targetFormName.elements.saveButton);
		}
		if(targetFormName.elements.processButton.privileged == 'Y'){
		enableField(targetFormName.elements.processButton);
		}
		//targetFormName.elements.closeflightButton.disabled=false;
		enableMultiButton(document.getElementById('CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_MAINPRINT'));
	}
}


function invokeExceptionsLov(){
//alert();
//onclick="displayLOV('showOneTime.do','N','Y','showOneTime.do',targetFormName.elements.exceptionCode.value,'Exception','1','exceptionCode','',0)"
var mainAction  = 'showOneTime.do';
var strAction = 'showOneTime.do';
//var descName =targetFormName.elements.exceptionCode.value;
var indexval="";
var parentAction="";
var strUrl = mainAction+"?multiselect=N&pagination=Y&lovaction="+strAction+"&code="+targetFormName.elements.exceptionCode.value+"&title=Exception&formCount=1&lovTxtFieldName=descriptionField&lovDescriptionTxtFieldName=exceptionCode&fieldType=maitracking.flown.exceptioncode&index="+indexval+"&parentAction="+parentAction;
//var myWindow = window.open(strUrl, "LOV", 'status,scrollbars,width=430,height=320,screenX=800,screenY=600,left=250,top=100')
//changed by A-5222 as part of ICRD-41909
var myWindow = openPopUp(strUrl,'430','320');
}