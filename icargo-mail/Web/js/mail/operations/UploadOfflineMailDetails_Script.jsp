<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
	var frm=targetFormName;	 
	onScreenLoad();
	with(frm){
		evtHandler.addEvents("btnSave","onClickSave()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
		evtHandler.addEvents("selectedMails","toggleTableHeaderCheckbox('selectedMails',targetFormName.checkAllOfflineUploadMailTag)", EVT_CLICK);
	}	 	
}

// This method is to change the form data to alert user of unsaved data while closing the tab.
function onScreenLoad(){
	var statusArry = document.getElementsByName("status");
	var unsavedDataPresent = document.getElementById("isUnsavedDataPresent");
	if(statusArry != null){
		for(var i=0; i<statusArry.length;i++){
			if(statusArry[i].value == 'U'){
				if(unsavedDataPresent != null){
					if(unsavedDataPresent.value == 'Y'){
						unsavedDataPresent.value = 'Yes';
					} else {
						unsavedDataPresent.value = 'Y';
					}
				}
			}
		}	
	}	 	
}

function onClickSave(){
	var frm=targetFormName;		
	var selcheckboxes = document.getElementsByName("selectedMails");
	for(var i=0; i<selcheckboxes.length; i++){
		if(selcheckboxes[i].checked == true){			
			strURL="mailtracking.defaults.offlineupload.savecommand.do";
			submitForm(frm,strURL);
		}
	}	
}

function onClickClose(){
    var frm= targetFormName;
	
	showDialog({msg:"The data will be flushed. Do you want to continue?",type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
					
						screenConfirmDialog(frm,'id_1');
						 screenNonConfirmDialog(frm,'id_1');
						
						}
						});
	
}

function screenConfirmDialog(frm, dialogId) {
  if(frm.elements.currentDialogOption.value == 'Y') {
     if(dialogId == 'id_1'){
		submitForm(frm,"mailtracking.defaults.offlineupload.flush.do");		
 	    }
  }
}

function screenNonConfirmDialog(frm, dialogId) {
   if(frm.elements.currentDialogOption.value == 'N') {
      if(dialogId == 'id_1'){
           //do nothing
 		}
   }
}

function selectAllMails(){
var selectAllChkBox = document.getElementById("checkAllOfflineUploadMailTag");
var chkboxArry = document.getElementsByName("selectedMails");
	if(selectAllChkBox.checked){
		if(chkboxArry != null){
			for(var i=0; i<chkboxArry.length;i++){
				if(chkboxArry[i].disabled == false){
				chkboxArry[i].checked = true;
				}
			}
		}
	} else {
		if(chkboxArry != null){
			for(var i=0; i<chkboxArry.length;i++){
				if(chkboxArry[i].disabled == false){
				chkboxArry[i].checked = false;
				
				}
			}
		}
	}
}

function selectEachMails(){

var selectAllChkBox = document.getElementById("checkAllOfflineUploadMailTag");
var chkboxArry = document.getElementsByName("selectedMails");
var checked=0;

		
			for(var i=0; i<chkboxArry.length;i++){
				if(chkboxArry[i].checked == false){
				checked++;
				}
				
			}
		if(checked>0){
		selectAllChkBox.checked = false;
		}else{
		selectAllChkBox.checked = true;
		}
	
}