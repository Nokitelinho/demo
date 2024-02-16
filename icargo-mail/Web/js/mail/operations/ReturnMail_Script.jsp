<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad();
   with(frm){

   	//CLICK Events
   	evtHandler.addEvents("btOk","okPopup(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btClose","cancelPopup(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("addLink","addDamage()",EVT_CLICK);
	evtHandler.addIDEvents("deleteLink","deleteDamage()",EVT_CLICK);

	evtHandler.addEvents("returnCheckAll","updateHeaderCheckBox(this.form,targetFormName.returnCheckAll,targetFormName.returnSubCheck)",EVT_CLICK);
	if(frm.returnSubCheck != null){
		evtHandler.addEvents("returnSubCheck","toggleTableHeaderCheckbox('returnSubCheck',targetFormName.returnCheckAll)",EVT_CLICK);
	}

   	}
}

function onScreenLoad(){
	frm=targetFormName;
	if(targetFormName.elements.selectedContainers.value == "CLOSE"){
		targetFormName.elements.selectedContainers.value = "";
		if(targetFormName.elements.fromScreen.value == 'MAILBAG_ENQUIRY'){
		    var successFlg = targetFormName.elements.successFlag.value;
			window.opener.targetFormName.action = "mailtracking.defaults.mailbagenquiry.list.do?successMailFlag="+successFlg;
			targetFormName.elements.successFlag.value="";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
		}

		window.closeNow();
	}
	else if(targetFormName.elements.selectedContainers.value == "SHOWPOPUP"){
var frmscreen = "EMPTY_ULD";
		targetFormName.elements.selectedContainers.value = "";
				if(targetFormName.elements.fromScreen.value == 'MAILBAG_ENQUIRY'){
				   // var successFlg = targetFormName.successFlag.value;
					window.opener.targetFormName.action = "mailtracking.defaults.mailbagenquiry.list.do?status="+frmscreen;
					//targetFormName.successFlag.value="";
					window.opener.IC.util.common.childUnloadEventHandler();
					window.opener.targetFormName.submit();
					window.closeNow();
				}
		//var strUrl = "mailtracking.defaults.emptyulds.screenload.do?fromScreen=RETURNMAIL";
  		//submitForm(frm,strUrl);


	}
}

function addDamage(){

	submitForm(targetFormName,'mailtracking.defaults.returnmail.add.do');
}

function deleteDamage(){
	var check = validateSelectedCheckBoxes(frm, 'returnSubCheck', 1000000000000, 1);
	if (check){

	submitForm(targetFormName,'mailtracking.defaults.returnmail.delete.do');
}
}

function okPopup(frm){
	if(frm.elements.paBuiltFlag.value  == "Y" ){
		showDialog({msg:'<common:message bundle="returnMailResources" key="mailtracking.defaults.returnmail.msg.warn.sbULD" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
						}
						});
		IC.util.common.childUnloadEventHandler();				
		 
	}else {		
		if(frm.elements.postalAdmin.length == 0 ){
			//showDialog('<bean:message bundle="returnMailResources" key="mailtracking.defaults.returnmail.msg.warn.noPOA" />',1,self);
			showDialog({msg:'<common:message bundle="returnMailResources" key="mailtracking.defaults.returnmail.msg.warn.noPOA" />',type:1,parentWindow:self});	
		}else{			
			window.opener.IC.util.common.childUnloadEventHandler();
			submitForm(targetFormName,'mailtracking.defaults.returnmail.ok.do');	
		}
			IC.util.common.childUnloadEventHandler();
	}	
}

function cancelPopup(frm){

	submitForm(targetFormName,'mailtracking.defaults.returnmail.cancel.do');
	//window.closeNow();
}

/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
	    if(dialogId == 'id_1'){

	    frm.elements.flagSBReturn.value="Y";
	    submitForm(targetFormName,'mailtracking.defaults.returnmail.ok.do');	

	    }

	}
}

/**
*function to Non-Confirm Dialog
*/
function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){
			frm.elements.flagSBReturn.value="N";
			submitForm(targetFormName,'mailtracking.defaults.returnmail.ok.do');	


		}

	}
}