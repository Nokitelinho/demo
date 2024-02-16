<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
var frm=targetFormName;
with(frm){
if(targetFormName.checkall){
					evtHandler.addEvents("checkall","updateHeaderCheckBox(targetFormName,targetFormName.checkall,targetFormName.check)",EVT_CLICK);
				}
				if(targetFormName.check){
					evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.checkall)",EVT_CLICK);
		}
	evtHandler.addEvents("btok","onClickOk(this.form,'OK')",EVT_CLICK);
	evtHandler.addEvents("btcancel","onClickCancel(this.form,'CANCEL')",EVT_CLICK);
	}
	checkOnLoad();
}
function onClickAdd(){

		addTemplateRow('rangeTableTemplateRow','rangeTableBody','hiddenOpFlag');
	
}


function onClickDelete(check){
deleteTableRow('check','hiddenOpFlag');
	
}
function checkOnLoad(){

if(targetFormName.okSuccess.value =="Y"){

	  targetFormName.okSuccess.value="";
		window.opener.targetFormName.action=appPath + "/stockcontrol.defaults.confirmstock.reloadconfirmstock.do"
		window.opener.targetFormName.submit();
		window.close();
}
}

function onClickOk(frm,btn){

	<%-- showDialog('<common:message bundle="missingstock" key="stockcontrol.defaults.missingstock.missingstockwarning" scope="request"/>', 4, self,frm,'id_1'); --%>
	 
	 showDialog({
		msg: "<common:message bundle="missingstock" key="stockcontrol.defaults.missingstock.missingstockwarning" scope="request"/>",
		type:4,
        parentWindow:self,
		parentForm:frm,
		dialogId:'id_1',
		onClose:function(){
		screenConfirmDialog(frm,'id_1')
                  }
		});


	 
	<%--	screenConfirmDialog(frm,'id_1');
		screenNonConfirmDialog(frm,'id_1'); --%>
	}
function onClickCancel(frm,btn){
   	if(btn == 'CANCEL'){

		window.close();

	}

}
function screenConfirmDialog(frm, dialogId) {

	if(frm.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){		
			submitForm(frm,'stockcontrol.defaults.missingstockok.do');
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {

	if(frm.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){
     
		}
	}
}


