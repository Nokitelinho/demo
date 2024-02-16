<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){

	var frm;
	var selectedRows;
	frm=targetFormName;
	// if(!frm.airlineCode.readOnly){
		//frm.airlineCode.focus();
	//}
	with(frm){
		onScreenLoad(frm);
		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","doClear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btPrint","onClickPrint(this.form)",EVT_CLICK);
		evtHandler.addEvents("btDelete","doDelete(this.form)",EVT_CLICK);

		evtHandler.addEvents("btClose","doClose(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);		
		evtHandler.addIDEvents("createLink","addStock()",EVT_CLICK);
		//evtHandler.addEvents("spmtSave","doSave(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("modifyLink","doModify()",EVT_CLICK);
		//evtHandler.addIDEvents("deleteLink","doDelete()",EVT_CLICK);
		evtHandler.addEvents("airportlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.stationCode.value,'Airport Code','1','stationCode','',0)",EVT_CLICK);
		evtHandler.addEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);
		var chkBoxObj=document.getElementsByName("rowId");
		if(frm.elements.rowId!=null){
			evtHandler.addEvents("rowId","rowId(this.form)",EVT_CLICK);
		}
			evtHandler.addEvents("masterRowId","masterRowId(this.form)",EVT_CLICK);
	}
	if(!(targetFormName.elements.linkStatus.value == "E")) {
		checklinkstatus();
	}else{
		//Commented By A-6841 for ICRD-185395 
		//targetFormName.elements.airlineCode.readOnly = true;
		targetFormName.elements.stationCode.readOnly = true;
		targetFormName.airportlov.disabled = true;
		disableLink(targetFormName.elements.airportlov);
		targetFormName.airlinelov.disabled = true;
		disableLink(targetFormName.elements.airlinelov);
	}
	checkstatus();
	onScreenload();
		

	applySortOnTable("maintainuldtable",new Array("None","String","String","String","String","String","String","String","String"));
}

function onScreenload(){
		if(!targetFormName.elements.airlineCode.disabled){	
			targetFormName.elements.airlineCode.focus();
		}
		else{
			document.getElementById('createLink').focus();
		}
}
function resetFocus(evt){

	 var frm=targetFormName;
	 var event=window.event?window.event:evt;
	 
	 if(!event.shiftKey){ 
				//if(!frm.stationCode.disabled && frm.stationCode.readOnly == false){
				if(frm.elements.airlineCode.disabled==false){
					frm.elements.stationCode.focus();
				}
				else{
					 if(document.getElementById('createLink').disabled == false) {
						frm.document.getElementById('createLink').focus();
						}
						else{
						frm.btClear.focus();
						}
				}				
	}	
}

//function for listing
function list(frm){
	submitForm(frm,'uld.defaults.finduldstocksetup.do');
}

//function to close
function doClose(frm) {
	submitForm(frm,'uld.defaults.closeuldstocksetup.do');
}

//function to save
function doSave(frm) {
	submitForm(frm,'uld.defaults.saveuldstocksetup.do');
}


//function to add a stock
function addStock() {
	var frm=targetFormName;
	if(targetFormName.elements.screenStatusFlag.value=="screenload"  ){
		 var createLink = document.getElementById('createLink');
		 createLink.onclick = function() { return true; }
		 return;
	}
	frm.elements.statusFlag.value = "uld_def_add_dmg";
	var statusFlag=frm.elements.statusFlag.value;
	var airline1 = targetFormName.elements.airlineCode.value;
	var station = targetFormName.elements.stationCode.value;
	var fromScreen = "Create";
	var myWindow = openPopUp("uld.defaults.screenloadcreateuldsetupstock.do?airline="+
	airline1+"&station="+station+"&fromScreen="+fromScreen+"&statusFlag="+statusFlag,650,280);
}



//function to modify a stock
function doModify(){

	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'rowId', 1000000000000, 1);
	if (check){
		frm.elements.statusFlag.value = "uld_def_mod_dmg";
		var statusFlag=frm.elements.statusFlag.value;
		var selectedRows = "";
		for(var i=0;i < targetFormName.elements.length;i++) {
			if(targetFormName.elements[i].name =='rowId') {
				if(targetFormName.elements[i].checked) {
					var val = targetFormName.elements[i].value;
					if(selectedRows != "")
						selectedRows = selectedRows+","+val;
					else if(selectedRows == "")
					selectedRows = val;
				}
			}
		}
		 frm.elements.flag.value= selectedRows;
		 var flagdmg=frm.elements.flag.value;
		 var station = targetFormName.elements.stationCode.value;
		 var stn = frm.elements.stationCode.value;
		 frm.elements.stationCode.value = stn;
		 var newWindow = openPopUp(
					"uld.defaults.screenloadcreateuldsetupstock.do?statusFlag="+statusFlag+"&flag="+flagdmg+"&station="+station,650,280);
	 }
}

//function to get the selected rows
function getSelectedRows(){
	var selectedRows = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
		if(targetFormName.elements[i].name =="selectedRowId") {
			if(targetFormName.elements[i].checked) {
				var val = targetFormName.elements[i].value;
				if(selectedRows != "")
					//alert("select one at a time");
					//Added by A-4772 for ICRD-5569
					showDialog('select one at a time',1, self);
				else if(selectedRows == "")
					selectedRows = val;
			}
		}

	}
	return selectedRows;
}

//function to get the row contents
function getRowContents(selectedRows) {
	var row = "";
	var airlineIdentifiers = document.getElementsByName("airlineIdentifiers");
	var stations = document.getElementsByName("stationCodes");
	var ulds = document.getElementsByName("uldTypeCodes");
	var max =document.getElementsByName("maxQty");
	var min = document.getElementsByName("minQty");
	row= row+airlineIdentifiers[selectedRows].value+","+stations[selectedRows].value+","+ulds[selectedRows].value+","+max[selectedRows].value+","+min[selectedRows].value;
	return row;
}

//function to delete
function doDelete(){
	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'rowId', 1000000000000, 1);
	if (check){

	showDialog({msg:'<common:message bundle='maintainuldstock' key='uld.defaults.maintainuldstock.info.selectedrowsdeleteconfirmation'/>', type:4, parentWindow:self, parentForm:frm, dialogId:'id_1',
						onClose: function (result) {
							if(frm.elements.currentDialogOption.value == 'Y') {
		screenConfirmDialog(frm,'id_1');
							}else if(frm.elements.currentDialogOption.value == 'N') {
		screenNonConfirmDialog(frm,'id_1');
							}
						}});
	/*
	showDialog({msg:"<common:message bundle='maintainuldstock' key='uld.defaults.maintainuldstock.info.selectedrowsdeleteconfirmation'/>",
			type:4,
			parentWindow:self,
			parentForm: frm});
		//showDialog("<common:message bundle='maintainuldstock' key='uld.defaults.maintainuldstock.info.selectedrowsdeleteconfirmation'/>", 4, self, frm, 'id_1');
		screenConfirmDialog(frm,'id_1');
		screenNonConfirmDialog(frm,'id_1');*/
	}

}
function screenConfirmDialog(frm, dialogId) {

	/*
	while(frm.elements.currentDialogId.value == ''){

 	}*/

 	if(frm.elements.currentDialogOption.value == 'Y') {

 	if(dialogId=='id_1'){

	frm.elements.statusFlag.value = "uld_def_del_dmg";
	var statusFlag=frm.elements.statusFlag.value;
	submitForm(frm,'uld.defaults.deletestockdetails.do');
	}

	}
}

 function screenNonConfirmDialog(frm, dialogId) {
	/*
 	while(frm.elements.currentDialogId.value == ''){

 	}*/

 	if(frm.elements.currentDialogOption.value == 'N') {
	if(dialogId=='id_1'){
	}

	}
}
function getRows(){
	var selectedRows = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
		if(targetFormName.elements[i].name =='rowId') {
			if(targetFormName.elements[i].checked) {
				var val = targetFormName.elements[i].value;
				if(selectedRows != "")
					selectedRows = selectedRows+","+val;
				else if(selectedRows == "")
					selectedRows = val;
			}
		}
	}

	return selectedRows;
}

//function to clear
function doClear(frm) {
	targetFormName.elements.linkStatus.value = "";
	//added by a-3045 for bug20371 starts
	targetFormName.elements.linkStatus.defaultValue = targetFormName.elements.linkStatus.value;
	//added by a-3045 for bug20371 ends
	//submitForm(frm,'uld.defaults.clearuldstocksetup.do');
	submitFormWithUnsaveCheck('uld.defaults.clearuldstocksetup.do');

}

//function to check link status
function checklinkstatus() {
	var link= document.links;
	disableLinkTest(link[0]);
	disableLinkTest(link[1]);
	//disableLinkTest(link[2]);

	//link[0].disabled = true;
	disableLink(link[0]);
	//link[1].disabled = true;
	disableLink(link[1]);
	//link[2].disabled = true;
}

//function to check whether the screen is from modify mode or create mode
function checkstatus() {
	if(targetFormName.elements.validateStatus.value == "success") {
		targetFormName.elements.validateStatus.value="failed";
		selectedRows = targetFormName.elements.selectedRows.value;
		var fromScreen = "Modify";
		var rowContents = getRowContents(selectedRows);
		var myWindow = openPopUp("uld.defaults.screenloadmodifyuldsetupstock.do?rowContents="+rowContents+"&fromScreen="+fromScreen,"600","200");
	}
}

//function to cancel a link
function cancelLink () {
  return false;
}

//function to disable the link
function disableLinkTest(link) {
	if (link.onclick){
		link.oldOnClick = link.onclick;
	}
	link.onclick = cancelLink;
	if (link.style)
		link.style.cursor = 'default';

}

//function to enable the link
function enableDocumentLink(link) {
	if (link.onclick){
		link.onClick = link.oldOnClick;
	}else{
	 	link.onClick = null;
	}
	if (link.style)
		link.style.cursor =
		document.all ? 'hand' : 'pointer';
}

//function to chack the master checkbox
function checkAll(parentbox,name){
	var chkBoxObj=document.getElementsByName(name);
	if(chkBoxObj!=null){
		var chkBoxLength = chkBoxObj.length;
		for(var i=0;i<chkBoxLength;i++)
		{
			chkBoxObj[i].checked=parentbox.checked;
		}
	}
}

//function to check one check box
function checkOne(checkbox,parentObj,childbox){

	var childObjs=document.getElementsByName(childbox);
	var isAllCheck=true;
	if(checkbox.checked){
		var chkBoxLength = childObjs.length;
		for(var i=0;i<chkBoxLength;i++)
		{
			if(!childObjs[i].checked)
			{
				isAllCheck=false;
				break;
			}
		}
	}else{
		isAllCheck=false;
	}
	if(isAllCheck){
		parentObj.checked=false;
	}else{
	parentObj.checked=false;
	}
}

//function to print
function onClickPrint(frm) {
	generateReport(frm,"/uld.defaults.maintainuldstock.doReport.do");
 }

//function to excute on screenload
function onScreenLoad(frm){


	if(frm.elements.listStatus.value!="N"){
		disableField(frm.btDelete);
		disableField(frm.btPrint);
	}
	//Changes for IASCB-178006 by A-9775 starts
	//if(targetFormName.elements.stkDisableStatus.value=="airline"){
		//targetFormName.elements.airlineCode.readOnly = true;
		//targetFormName.airlinelov.disabled = true;
		//disableLink(targetFormName.elements.airlinelov);
	//}
	//Changes for IASCB-178006 by A-9775 ends
	if(targetFormName.elements.stkDisableStatus.value=="GHA"){
		targetFormName.stationCode.readOnly = true;
		
	//To fix the issue - ICRD-197630	
		targetFormName.airportlov.disabled = true;
		//disableLink(targetFormName.elements.airportlov);
		
		if(!frm.elements.airlineCode.readOnly){
			targetFormName.elements.airlineCode.focus();
			}
	}
  	if(frm.elements.createStatus.value=="success") {
  		submitForm(frm,'uld.defaults.reload.do');
  	}
}

//function to confirm a message
function confirmMessage() {
	var frm = targetFormName;
	if(frm.elements.closeStatus.value=="whethertoclose") {
		frm.elements.closeStatus.value="canclose";
		submitForm(frm,'uld.defaults.closeuldstocksetup.do');
	}
	else if(frm.elements.closeStatus.value=="whethertoclear") {
		frm.elements.closeStatus.value="canclear";
		submitForm(frm,'uld.defaults.clearuldstocksetup.do');
	}
}

//function not to confirm a message
function nonconfirmMessage() {

}


function rowId(frm){
	toggleTableHeaderCheckbox('rowId', frm.elements.masterRowId);
}

function masterRowId(frm){
	updateHeaderCheckBox(frm, frm.elements.masterRowId, frm.elements.rowId);
}

//function to show a message
function saveMessage(frm) {
	var opFlag = eval(frm.elements.operationFlag);
	if(opFlag != null) {
		if(opFlag.length > 1) {
			for(var i=0; i<opFlag.length; i++) {
				if(opFlag[i].value !="null") {
					return true;

				}
			}
		 }else {
			if(opFlag.value !="null") {
				return true;
			}
		}
	}
	return false;
}