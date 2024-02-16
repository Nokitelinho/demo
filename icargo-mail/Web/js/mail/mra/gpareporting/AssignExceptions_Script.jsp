<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){

   var frm=targetFormName;

	with(frm){

	   evtHandler.addEvents("gpaCode","validateFields(this,-1,'GPA Code',8,true,true)",EVT_BLUR);
	   evtHandler.addEvents("gpaCode","targetFormName.gpaName.value='';",EVT_CHANGE);
	   evtHandler.addEvents("assignee","validateFields(this,-1,'Assignee',8,true,true)",EVT_BLUR);
	   evtHandler.addEvents("gpaCodeLov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);
	   evtHandler.addEvents("assigneeLov","showAsigneLOV()",EVT_CLICK);
	   evtHandler.addEvents("btnList","onList(targetFormName)", EVT_CLICK);
	   evtHandler.addEvents("btnClear","onClear(this.form)", EVT_CLICK);
	   evtHandler.addEvents("btnProcess","onProcess(targetFormName)", EVT_CLICK);
	   evtHandler.addEvents("btnGPAReport","onGPAReport(targetFormName)", EVT_CLICK);
	   evtHandler.addEvents("btnSave","onSave(targetFormName)", EVT_CLICK);
	   evtHandler.addEvents("btnClose","onClose(targetFormName)",EVT_CLICK);
	   evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
	   onScreenLoad(frm);
	}
}

function onScreenLoad(frm){

	if(!frm.elements.gpaCode.disabled)
		frm.elements.gpaCode.focus();
	if(frm.elements.rowId!=null){
	if(frm.elements.rowId.length>0){
		for(var i=0;i<frm.elements.rowId.length;i++){
			if(frm.elements.resolvedDate!=null){
			if(frm.elements.resolvedDate[i].value.length>0){

				frm.elements.assignedDate[i].readOnly=true;
				var calendarIconId = "btn_assignedDate"+i;
				disableField(document.getElementById(calendarIconId));
				frm.elements.assignedUser[i].readOnly=true;
			}
			}
		}
	}else{
			if(frm.elements.resolvedDate!=null){
			if(frm.elements.resolvedDate.value.length>0){

				frm.elements.assignedDate.readOnly=true;
				var calendarIconId = "btn_assignedDate0";
				disableField(document.getElementById(calendarIconId));
				frm.elements.assignedUser.readOnly=true;
			}
			}
	}
	enableField(frm.elements.btnGPAReport);
	if(frm.elements.btnProcess.privileged = 'Y'){
	enableField(frm.elements.btnProcess);
	}
	if(frm.elements.btnSave.privileged = 'Y'){
	enableField(frm.elements.btnSave);
	}

	}else{

	
	disableField(frm.elements.btnGPAReport);
	disableField(frm.elements.btnProcess);
	disableField(frm.elements.btnSave);

	}

}

function submitPage1(lastPg,displayPg){
	var frm=targetFormName;
	setHdOperationFlag(frm);
	if(frm.elements.saveFlag.value=="YES"){
		frm.elements.saveFlag.value="";
		showDialog('<common:message bundle="mraassignexceptions" key="mailtracking.mra.gpareporting.assignexceptions.dlg.msg.unsaveddata" />',4,self,frm,'id_1');

		if(frm.elements.currentDialogOption.value == 'Y') {
			if(frm.elements.currentDialogId.value== 'id_1'){
				 setHdOperationFlag(frm);
				 submitForm(frm,'mailtracking.mra.gpareporting.saveassignexceptions.do');
			}
		}
		else{
			frm.elements.lastPageNum.value=lastPg;
			frm.elements.displayPage.value=displayPg;
			submitForm(frm,'mailtracking.mra.gpareporting.listassignexceptions.do');
		}
	}else{
		frm.elements.lastPageNum.value=lastPg;
		frm.elements.displayPage.value=displayPg;
		frm.elements.saveFlag.value="";
		submitForm(frm,'mailtracking.mra.gpareporting.listassignexceptions.do');
	}
}


function onList(frm){
	frm.elements.lastPageNum.value="0";
	frm.elements.displayPage.value="1";
	frm.elements.saveFlag.value="";
	submitForm(frm,'mailtracking.mra.gpareporting.listassignexceptions.do');
}

function onClear(frm){
	submitForm(frm,'mailtracking.mra.gpareporting.clearassignexceptions.do');
}


function onSave(frm){
	setHdOperationFlag(frm);
	submitForm(frm,'mailtracking.mra.gpareporting.saveassignexceptions.do');
}

function onProcess(frm){
	submitForm(frm,'mailtracking.mra.gpareporting.processassignexceptions.do');
}

function onClose(frm){
	//submitFormWithUnsaveCheck('mailtracking.mra.gpareporting.closeassignexceptions.do');
	submitForm(frm,'mailtracking.mra.gpareporting.closeassignexceptions.do');
}

function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.gpaCode.disabled == false && targetFormName.elements.gpaCode.readOnly== false){
			targetFormName.elements.gpaCode.focus();
		}
	}
}
function setHdOperationFlag(frm){

	var operationFlag = eval(frm.elements.operationFlag);   //for operation flag

	var rowIds = eval(frm.elements.rowId);

	var assignedUser = eval(frm.elements.assignedUser);

	var assignedDate = eval(frm.elements.assignedDate);

	if (rowIds != null) {
		if (rowIds.length > 1) {
			for (var i = 0; i < rowIds.length; i++) {
				var id = rowIds[i].value;

				if ((assignedUser[id].value != assignedUser[id].defaultValue) ||
					(assignedDate[id].value != assignedDate[id].defaultValue))
				{
					frm.elements.operationFlag[id].value='U';

					frm.elements.saveFlag.value="YES";
				}
			}

		}else {

			if ((assignedUser.value != assignedUser.defaultValue) ||
					(assignedDate.value != assignedDate.defaultValue)){
				frm.elements.operationFlag.value = 'U';
				frm.elements.saveFlag.value="YES";
			}
		}
	}
}

function showAssigneeLov(rowId){
	var mode = getScreenId(targetFormName) ;
	if(targetFormName.elements.rowId.length > 1 && targetFormName.elements.resolvedDate[rowId].value.length == 0){
		displayLOV('showAssigneeLOV.do','N','Y','showAssigneeLOV.do',targetFormName.assignedUser[rowId].value,'Assignee','1','assignedUser','',rowId,'','',mode);
	}
	else if(targetFormName.elements.rowId.value != null && targetFormName.elements.resolvedDate.value.length == 0){
		displayLOV('showAssigneeLOV.do','N','Y','showAssigneeLOV.do',targetFormName.assignedUser.value,'Assignee','1','assignedUser','',0,'','',mode);
	}
}



function onGPAReport(frm){
		submitForm(frm,'mailtracking.mra.gpareporting.gpareportassignexceptions.do');
}

function showAsigneLOV(){	
	//var mode = getScreenId(targetFormName) ;
	displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.assignee.value,'Assignee','1','assignee','',0,'','');
	//displayLOV('showAssigneeLOV.do','N','Y','showAssigneeLOV.do',targetFormName.assignee.value,'Assignee','1','assignee','',0,'','',mode);
}

function getScreenId(frm){
	var actCode = frm.action; 
	var actCodeArr = actCode.split("/");
	var len = actCodeArr.length;
	var lastVal = actCodeArr[len-1];
	var scrid = getScreenIdFromActionCode(lastVal);
	var length = scrid.split("'");
	var mode = length[0];
	return mode ;	
}