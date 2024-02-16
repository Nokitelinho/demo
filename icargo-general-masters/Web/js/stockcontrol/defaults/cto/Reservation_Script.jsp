<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
	
	var frm = document.forms[1];
	
	with(frm){
		frm.elements.airline.focus();
		evtHandler.addEvents("btReserve","onReserve()",EVT_CLICK);
		evtHandler.addEvents("btClear","onClear()",EVT_CLICK);
		evtHandler.addEvents("btClose","onClose()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
		evtHandler.addEvents("general","onGeneral()",EVT_CLICK);
		evtHandler.addEvents("specific","onSpecific()",EVT_CLICK);
		evtHandler.addEvents("airline","onAirline()",EVT_BLUR);
		evtHandler.addEvents("custCode","onCode()",EVT_BLUR);
		evtHandler.addEvents("remarks","onRemarks()",EVT_BLUR);
		evtHandler.addEvents("totAwb","onTotAwb()",EVT_BLUR);
		evtHandler.addEvents("awbNumber","onAwbNumber(this)",EVT_BLUR);
		evtHandler.addEvents("rowId","singleSelectRow(this)",EVT_CLICK);
		evtHandler.addEvents("checkbox2","selectAll(this)",EVT_CLICK);
		evtHandler.addIDEvents("cuslovImage","showCustomerLOV()",EVT_CLICK);


	}
	onLoad();

}
function showCustomerLOV(){
	//displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.elements.custCode.value,'CustomerCode','1','custCode','','0')
		var accNo = targetFormName.elements.custCode.value;
		var StrUrl = "showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code="+accNo+"&title=CustomerCode&formCount=1&lovTxtFieldName=custCode&lovDescriptionTxtFieldName=";
		var options = {url:StrUrl}
		var myWindow = CustomLovHandler.invokeCustomerLov(options);
}
function onSpecific(){

	if(document.forms[1].elements.specific.checked){
		document.forms[1].elements.airline.readOnly=true;
		recreateTableDetails('stockcontrol.defaults.cto.reservestockaddrow.do','div1');
	}else{
		document.forms[1].elements.airline.readOnly=false;
	}

}
function selectAll(obj){
	updateHeaderCheckBox(document.forms[1],document.forms[1].elements.checkbox2, document.forms[1].elements.rowId);


}

function onAwbNumber(docnum){
	if(validateMaxLength(docnum,8)){
		validateFields(docnum,-1, "Document Number", 0, true, true);
	}

}

function addrow(){
	if(document.forms[1].elements.airline.value!=""){
		if(document.forms[1].elements.specific.checked==true){
			//recreateTableDetails('stockcontrol.defaults.cto.reservestockaddrow.do','div1');
			addTemplateRow('reservationTemplateRow','reservationTableBody','reservationOperationFlag');
		}else{
			showDialog({	
				msg			:	"Please select Specific",
				type		:	1, 
				parentWindow:	self,
				parentForm	:	targetFormName,
			});
		}
	}else{
		showDialog({	
			msg			:	"Please enter Airline",
			type		:	1, 
			parentWindow:	self,
			parentForm	:	targetFormName,
		});
	}
}

function deleterow(){
	if(validateSelectedCheckBoxes(document.forms[1],'rowId',10,1)){
		//recreateTableDetails('stockcontrol.defaults.cto.reservestockdeleterow.do','div1');
	 	//submitForm(document.forms[1],);
	 	deleteTableRow('rowId','reservationOperationFlag');
	}
}

function onReserve(){
	submitForm(document.forms[1],'stockcontrol.defaults.cto.reserveawbstock.do');
}

function onLoad(){
	document.forms[1].elements.airline.focus();
	document.forms[1].elements.expiryDate.disabled="true";
	document.getElementById("btn_expirydate").disabled="true";
	onGeneral();
	if(document.forms[1].elements.afterReserve.value=="Y"){
		document.forms[1].elements.afterReserve.value="N";
		openPopUp("stockcontrol.defaults.cto.reserveawbstocklistscreenload.do",'410','300');
	}
}

function onGeneral(){
	if(document.forms[1].elements.general.checked){
		document.forms[1].elements.totAwb.disabled=false;
	}
	else{
		document.forms[1].elements.totAwb.disabled=true;
		document.forms[1].elements.totAwb.value="";

	}
}

function onClose(){
	submitForm(document.forms[1],'stockcontrol.defaults.cto.closereservescreen.do');
}

function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.airline.disabled == false && targetFormName.elements.airline.readOnly == false){
			targetFormName.elements.airline.focus();
		}
	}
}

function onClear(){
	submitForm(document.forms[1],'stockcontrol.defaults.cto.clearreservationscreen.do');
}

function onAirline(){
	return validateMaxLength(document.forms[1].elements.airline,3);
}

function onCode(){
	if(validateFields(document.forms[1].elements.custCode,-1, "CustomerCode ", 0, true, true)){
		return validateMaxLength(document.forms[1].elements.custCode,12);
	}
}

function onRemarks(){
	return validateMaxLength(document.forms[1].elements.remarks,20);
}

function onTotAwb(){
	if(validateMaxLength(document.forms[1].elements.totAwb,9)){
		return validateFields(document.forms[1].elements.totAwb,-1, "No Of AWBs ", 3, true, true);
	}
}

function singleSelectRow(obj){

	toggleTableHeaderCheckbox('rowId', document.forms[1].elements.checkbox2);

}


////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";


function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableCode";

	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
}

function updateTableCode(_tableInfo){

	_str=getActualData(_tableInfo);

	document.getElementById(_tableInfo.currDivId).innerHTML=_str;
}

function getActualData(_tableInfo){
	_frm=_tableInfo.document.getElementsByTagName("table")[0];
	return _frm.outerHTML;
}

