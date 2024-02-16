<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {

	var frm = targetFormName;
	with(frm) {
		evtHandler.addEvents("btnList","listAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnExtend","extendAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnCancel","cancelAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnPrint","printBtnAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnView","viewAction(this.form)",EVT_CLICK);

        evtHandler.addEvents("airlineFilterCode","validateFields(this,-1,'Airline',1,true,true)",EVT_BLUR);
		evtHandler.addEvents("customerFilterCode","validateFields(this,-1,'CustomerCode',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("checkbox2","updateHeaderCheckBox(targetFormName,targetFormName.elements.checkbox2,targetFormName.elements.rowId)",EVT_CLICK);
	}
	onScreenLoading();
	
	applySortOnTable("reservationlisttable",new Array("None","String", "String","String","Date", "Date","String"));
}

function viewAction(frm){
	frm.elements.preview.value="true";
	frm.action = "/stockcontrol.defaults.cto.reservationlistreport.do";
	generateReport(frm,frm.action);
}

function printBtnAction(frm){
	frm.elements.preview.value="false";
	frm.action = "/stockcontrol.defaults.cto.reservationlistreport.do";
	generateReport(frm,frm.action);
}

function listAction(frm) {
	recreateTableDetails("stockcontrol.defaults.reservationlisting.ListCommand.do",'div1',"handleonloadforenablebutton_new");
}

function clearAction(frm) {
	frm.action = "stockcontrol.defaults.reservationlisting.ClearCommand.do";
	frm.submit();
}

function extendAction(frm) {

	if(validateSelectedCheckBoxes(document.forms[1],'rowId',100,1)){
		var selectedRows = "";
		var checkBoxValue = document.getElementsByName('rowId');

		for(var index=0; index<checkBoxValue.length; index++) {
			if(checkBoxValue[index].checked) {
				if(selectedRows != ""){
					selectedRows = selectedRows  + "," + checkBoxValue[index].value;
				}else if(selectedRows == ""){
					selectedRows = checkBoxValue[index].value;
				}
			}
		}

		//alert("arraySelected ---> " + selectedRows);
		var actionString = "stockcontrol.defaults.reservationlisting.ExtendCommand.do";
		var strUrl = actionString+"?popupRowId="+selectedRows;
		openPopUp(strUrl,'300','200') ;
	}
}

function cancelAction(frm) {
	if(validateSelectedCheckBoxes(document.forms[1],'rowId',100,1)){
		var selectedRows = "";
		var checkBoxValue = document.getElementsByName('rowId');

		for(var index=0; index<checkBoxValue.length; index++) {
			if(checkBoxValue[index].checked) {
				if(selectedRows != ""){
					selectedRows = selectedRows  + "," + checkBoxValue[index].value;
				}else if(selectedRows == ""){
					selectedRows = checkBoxValue[index].value;
				}
			}
		}

		//alert("arraySelected ---> " + selectedRows);
		var actionString = "stockcontrol.defaults.reservationlisting.CancelCommand.do";
		var strUrl = actionString+"?popupRowId="+selectedRows;
		//modified by A-5221 for ICRD-41909 
		//window.open(strUrl,"popup","width=350,height=150,left=180,top=250");
		openPopUp(strUrl,'350','150') ;

	}
}

function closeAction(frm) {
	frm.action = "stockcontrol.defaults.reservationlisting.CloseCommand.do";
	frm.submit();
}

function onScreenLoading(){
	var frm = targetFormName;
	
	if(frm.elements.enableBtn.value == 'N'){
		frm.elements.btnView.disabled=true;
		frm.elements.btnPrint.disabled=true;
	}else{
		frm.elements.btnView.disabled=false;
		frm.elements.btnPrint.disabled=false;
	}
	
}

function okCancelAction(frm) {
	frm.action = "stockcontrol.defaults.reservationlisting.OkCancelCommand.do";
	frm.submit();
}

function okExtendAction(frm) {
	frm.action = "stockcontrol.defaults.reservationlisting.OkExtendCommand.do";
	frm.submit();
}

function popupCloseAction(frm) {
	window.close();
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
	applySortOnTable("reservationlisttable",new Array("None","String", "String","String","Date", "Date","String"));
	onScreenLoading();
	
}

function getActualData(_tableInfo){
	_frm=_tableInfo.document.getElementById("resTableDiv_Temp").innerHTML;
	return _frm;
}
/////////////////////////////////////////////////////////////////////////////////////////
function handleonloadforenablebutton_new(_tableInfo){

	var frm = targetFormName;

//@author A-1944 @TRV on 09 Feb 2007 starts
//On Ajax framework changes
	_innerFrm = _tableInfo.document.getElementsByTagName("form")[0];
	_frm=_tableInfo.document.getElementsByTagName("table")[0];
	_trs = _frm.getElementsByTagName("TR");
//@author A-1944 @TRV on 09 Feb 2007 ends

	if(_trs.length > 1) {
		frm.elements.enableBtn.value ="Y";
	} else {
		frm.elements.enableBtn.value= "N";
	}
	
	updateTableCode(_tableInfo);
}