<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {
	var frm = targetFormName;

	with(frm) {

		evtHandler.addEvents("btnList","Listfn()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearfn()",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addIDEvents("addLink","addDetailsfn()",EVT_CLICK);
		evtHandler.addIDEvents("modifyLink","modifyDetailsfn()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteDetailsfn()",EVT_CLICK);
		evtHandler.addIDEvents("btnSave","saveDetailsfn()",EVT_CLICK);
		//evtHandler.addIDEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("btnProcess","processfn()",EVT_CLICK);
		evtHandler.addEvents("rowCount","singleSelectCb(targetFormName,this.value,'rowCount')",EVT_CLICK);
		//evtHandler.addEvents("rowCount","updateCheck()", EVT_CLICK);


		//
		evtHandler.addEvents("btnViewAccount","viewAccount()",EVT_CLICK);

		//evtHandler.addEvents("gpaCodeLov","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'Gpa Code','1','gpaCode','gpaName','gpaName',0)",EVT_CLICK)
		evtHandler.addEvents("gpaCodeLov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);

		// For toggling of CheckBox

		if(targetFormName.elements.chk!=null){

			evtHandler.addEvents("chk","select()",EVT_CLICK);
		}
		evtHandler.addEvents("headChk","selectAll()",EVT_CLICK);


	}

	onScreenLoad();
}

function onScreenLoad(){

//added for resolution starts
/*var clientHeight = document.body.clientHeight;
var clientWidth = document.body.clientWidth;

//alert(clientHeight);
document.getElementById('contentdiv').style.height = ((clientHeight*87)/100)+'px';
document.getElementById('outertable').style.height=((clientHeight*83)/100)+'px';

//alert(document.getElementById('outertable').style.height);

var pageTitle=35;
var filterlegend=70;
var filterrow=60;
var bottomrow=45;
var height=(clientHeight*83)/100;
var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
document.getElementById('div1').style.height=tableheight+'px';*/
// added for resolution ends
if(targetFormName.elements.accEntryFlag.value == "N"){
			disableField(targetFormName.elements.btnViewAccount);
		}else{
			enableField(targetFormName.elements.btnViewAccount);
		}
         //alert(targetFormName.elements.allProcessed.value);
 	if(targetFormName.elements.allProcessed.value == "Y"){
 		disableField(targetFormName.elements.btnProcess);
 	}
 	else{
		if(targetFormName.elements.btnProcess.privileged == 'Y'){
 		enableField(targetFormName.elements.btnProcess);
		}
	}
 	if(!targetFormName.elements.gpaCode.disabled) {

			targetFormName.elements.gpaCode.focus();

	}

	if(targetFormName.elements.screenStatusFlag.value !='screenload'){

		disableField(targetFormName.elements.gpaCodeLov);
		disableField(targetFormName.elements.frmDate);
		disableField(targetFormName.elements.toDate);

	 }


	if(targetFormName.elements.screenStatusFlag.value == 'screenload') {

		disableLink(document.getElementById('addLink'));
		disableLink(document.getElementById('modifyLink'));
		disableLink(document.getElementById('deleteLink'));

		disableField(targetFormName.elements.btnProcess);
		disableField(targetFormName.elements.btnViewAccount);

	}


 	if(targetFormName.elements.screenFlag.value == 'POPUP'){


 		targetFormName.elements.screenFlag.value ="";
 		var action = "mailtracking.mra.gpareporting.capturegpareportpopup.screenload.do?popUpStatusFlag="+targetFormName.elements.popUpStatusFlag.value
 		+"&basistype="+targetFormName.elements.basistype.value
 		+"&gpaselect="+targetFormName.elements.gpaselect.value
 		+"&currencyCode="+targetFormName.elements.currencyCode.value;

 		openPopUp(action,800,420);
	}

 }


function Listfn(){
	var frm = targetFormName;
	frm.elements.lastPageNum.value="0";
	frm.elements.displayPage.value="1";

	submitForm(targetFormName,"mailtracking.mra.gpareporting.listcapturegpareport.do");

}

function selectNextDetails(strLastPageNum,strDisplayPage){

	var frm = targetFormName;


	 if(isFormModified()){
			
			showDialog({msg:"<common:message bundle='capturegpareport' key='mailtracking.mra.gpareporting.unsaveddatawarning' />",type:4,parentWindow:self, parentForm:frm, dialogId:'id_1',
				onClose:function(result){
				screenConfirmDialog(frm,'id_1');
				screenNonConfirmDialog(frm,'id_1');}
				});

	  	

	 }else{

	 	frm.elements.lastPageNum.value= strLastPageNum;
		frm.elements.displayPage.value = strDisplayPage;
		submitForm(targetFormName,'mailtracking.mra.gpareporting.listcapturegpareport.do');

	}


}


function addDetailsfn(){

	targetFormName.elements.popUpStatusFlag.value= 'ADD';
	action="mailtracking.mra.gpareporting.capturegpareport.adddetails.do";
	submitForm(targetFormName,action);


}

function deleteDetailsfn(){
   	var check=",";
	//var chkbox = document.getElementsByName("chk");
	var mailsta=document.getElementsByName("mailStatus");
	var chkbox=document.getElementsByName("rowCount");
      //alert(mailsta.length);
	if(chkbox.length > 0){
	 	//if(validateSelectedCheckBoxes(targetFormName,'chk','chkbox.length+1','1')){
		 if(validateSelectedCheckBoxes(targetFormName,'rowCount','chkbox.length+1','1')){
		       for(var i=0;i<chkbox.length;i++) {
		      	if(chkbox[i].checked){
				   // added for validation during delete
				   if(mailsta[i].value == 'P'){
				   showDialog({msg:'<common:message bundle="capturegpareport" key="mailtracking.mra.gpareporting.mailcannotdeleted" />',  type:1,parentWindow: self});
				   return;
				   }

					if(check==','){

						check=i;
					}
					else{
						check=check+","+i;

					}
				  }
			}

		targetFormName.elements.selectedRows.value = check;

		//action="mailtracking.mra.gpareporting.capturegpareport.deletecapturegpareport.do";

		//submitForm(targetFormName,action);

		recreateTableDetails("mailtracking.mra.gpareporting.capturegpareport.deletecapturegpareport.do","div1","refreshParentTable");

		}
	}

}



function modifyDetailsfn(){

	var check=",";
	//var chkbox = document.getElementsByName("chk");
	//
	var chkbox = document.getElementsByName("rowCount");
	 if(chkbox.length > 0){
		//if(validateSelectedCheckBoxes(targetFormName,'chk','1','1')){
		if(validateSelectedCheckBoxes(targetFormName,'rowCount','1','1')){
		      for(var i=0;i<chkbox.length;i++) {
				if(chkbox[i].checked){

					if(check==','){

						check=i;
					}
					else{
						check=check+","+i;

					}
				  }
			}


		 targetFormName.elements.selectedRows.value = check;

		 targetFormName.elements.popUpStatusFlag.value = 'MODIFY';

		 action="mailtracking.mra.gpareporting.modifycapturegpareport.do"
		 submitForm(targetFormName,action);

	         }
	    }



}

function clearfn(){

	var action="mailtracking.mra.gpareporting.clearcapturegpareport.do";
	submitForm(targetFormName,action);
}

function saveDetailsfn(){
	submitForm(targetFormName,'mailtracking.mra.gpareporting.savecapturegpareport.do');

}

function closeScreen(){

    //submitForm(targetFormName,'mailtracking.mra.gpareporting.closecapturegpareport.do');
    submitFormWithUnsaveCheck('mailtracking.mra.gpareporting.closecapturegpareport.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.gpaCode.disabled == false && targetFormName.elements.gpaCode.readOnly== false){
			targetFormName.elements.gpaCode.focus();
		}
	}
}

//Function for toggling checkbox

function select(){

	toggleTableHeaderCheckbox('chk', targetFormName.elements.headChk);
}

function selectAll(){

	updateHeaderCheckBox(targetFormName, targetFormName.elements.headChk, targetFormName.elements.chk);
}

function confirmMessage(){

	disableLink(document.getElementById('modifyLink'));
	disableLink(document.getElementById('deleteLink'));

	targetFormName.elements.popUpStatusFlag.value= 'ADD';
	action="mailtracking.mra.gpareporting.capturegpareport.adddetails.do";
	submitForm(targetFormName,action);

}


function nonconfirmMessage(){

	var action="mailtracking.mra.gpareporting.clearcapturegpareport.do";
	submitForm(targetFormName,action);

}


function processfn(){
	submitForm(targetFormName,'mailtracking.mra.gpareporting.processcapturegpareport.do');
}



/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
	    if(dialogId == 'id_1'){

	    frm.elements.lastPageNum.value= strLastPageNum;
	    frm.elements.displayPage.value = strDisplayPage;
	    submitForm(targetFormName,'mailtracking.mra.gpareporting.listcapturegpareport.do');

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


		}

	}
}

//////////////////////Ajax Functions//////////////////

function recreateTableDetails(url,divId,asyncFunction) {
	asyncSubmit(targetFormName,url,asyncFunction,null,null,divId);

}
function refreshParentTable(_refreshInfo) {

	if(_asyncErrorsExist == false) {

		document.getElementById(_refreshInfo.currDivId).innerHTML=_refreshInfo.getTableData();

	}

}

function viewAccount(){

var chkbox=targetFormName.elements.rowCount;
if(chkbox != null && chkbox.length>1){
   for(var i=0;i<chkbox.length;i++) {

  	if(chkbox[i].checked==true){
		if(targetFormName.elements.mailStatus[i].value == "P"){

		    submitForm(targetFormName,'mailtracking.mra.gpareporting.viewaccount.do');
		}else {
			showDialog({msg:'<common:message bundle="capturegpareport" key="mailtracking.mra.gpareporting.selectmail" />', type:1,parentWindow: self});
			return;
		}
	}
   }


}else if(chkbox != null){
	          if(targetFormName.elements.mailStatus.value == "P"){
		    submitForm(targetFormName,'mailtracking.mra.gpareporting.viewaccount.do');
		  }else{
			showDialog({msg:'<common:message bundle="capturegpareport" key="mailtracking.mra.gpareporting.selectmail" />', type:1,parentWindow: self});
			return;
	          }
}
else if(validateSelectedCheckBoxes(targetFormName,'rowCount',1,1)){
        for(var i=0;i<chkbox.length;i++) {
            if(chkbox[i].checked==true){
		    if(targetFormName.elements.mailStatus[i].value == "P"){
			 submitForm(targetFormName,'mailtracking.mra.gpareporting.viewaccount.do');
		     }else{
			showDialog({msg:'<common:message bundle="capturegpareport" key="mailtracking.mra.gpareporting.selectmail" />', type:1,parentWindow: self});
			return;
		     }
            }
        }

}
}



