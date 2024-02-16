<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){
var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnClose","close(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnSave","save(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("roleGroupLovFilter","showRoleGroupLovFilter()",EVT_CLICK);
	}
}

function list(frm){
	targetFormName.elements.displayPage.value=1;
	targetFormName.elements.lastPageNum.value=0;
	submitForm(targetFormName,'reco.defaults.listcomposemessage.do?navigationMode=FILTER');
}

function close(frm){
	targetFormName.action= "home.do";
	targetFormName.submit();
}

function clear(frm){
	submitForm(targetFormName,'reco.defaults.clearcomposemessage.do');
}

function save(frm){
updateOperationFlags(targetFormName);
	submitForm(targetFormName,'reco.defaults.savecomposemessage.do');
}

function updateOperationFlags(frm){
	var hiddenOpFlag = frm.elements.hiddenOpFlag;
	var roleGroups = frm.elements.roleGroups;
	var messages = frm.elements.messages;
	var startDates = frm.elements.startDates;
	var endDates = frm.elements.endDates;
	for(var index = 0; index<hiddenOpFlag.length;index++){
		if(isElementModified(roleGroups[index]) || isElementModified(messages[index]) || isElementModified(startDates[index]) || isElementModified(endDates[index]) ){
			if("NOOP" != hiddenOpFlag[index].value && "I" != hiddenOpFlag[index].value && "D" != hiddenOpFlag[index].value){
				frm.hiddenOpFlag[index].value = "U";
			}
		}
	}
}

function addRows(flightNotesTableTemplateRow,flightNotesTableBody,hiddenOpFlag){
	addTemplateRow(flightNotesTableTemplateRow,flightNotesTableBody,hiddenOpFlag)
}

function deleteRows(mstChexk,hiddenOpFlag){
	deleteTableRow(mstChexk,hiddenOpFlag);
}

function submitPage(lastPg,displayPg){
	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	targetFormName.action="reco.defaults.listcomposemessage.do?navigationMode=NAVIGATION";
	targetFormName.submit();
}

function showRoleGroupLov(obj) {
var index = obj.id.split('roleGroupLov')[1];
var roleGroupValue=targetFormName.elements.roleGroups[index].value;
	displayLOV('admin.accesscontrol.showrolegroup.do','Y','Y','admin.accesscontrol.showrolegroup.do',roleGroupValue,'Rolegroup','4','roleGroups','',index);
}
function showRoleGroupLovFilter() {
var roleGroupValue=targetFormName.elements.roleGroup.value;
	displayLOV('admin.accesscontrol.showrolegroup.do','N','Y','admin.accesscontrol.showrolegroup.do',roleGroupValue,'Rolegroup','1','roleGroup','',0);
}