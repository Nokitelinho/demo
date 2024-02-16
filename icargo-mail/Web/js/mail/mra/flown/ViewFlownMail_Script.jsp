<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificTabSetup(){

   setupPanes('container1','Despatch');
   displayTabPane('container1','Despatch');

}

function screenSpecificEventRegister(){


	var frm = targetFormName;

	with(frm){

	evtHandler.addEvents("btnList","listSegments()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
	evtHandler.addEvents("btnListMails","list()",EVT_CLICK);
	evtHandler.addEvents("btnImport", "importData()",EVT_CLICK);
	evtHandler.addEvents("btnViewExceptions","viewExceptions()",EVT_CLICK);
	//evtHandler.addEvents("btnProcess","process()",EVT_CLICK);
	//evtHandler.addEvents("btnCloseFlight","closeFlight()",EVT_CLICK);
	evtHandler.addEvents("btnViewAccount","viewAccount()",EVT_CLICK);
	evtHandler.addEvents("rowCount","singleSelectCb(targetFormName,this.value,'rowCount')",EVT_CLICK);
	evtHandler.addEvents("selectedRow","singleSelectCb(targetFormName,this.value,'selectedRow')",EVT_CLICK);
	//evtHandler.addEvents("btnProcess","process()",EVT_CLICK);
	//evtHandler.addEvents("btnClose","close()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
 	}

 	onScreenLoad();
}



function onScreenLoad(){

	var frm = targetFormName;
	targetFormName.elements.carrierCode.focus();

	frm.elements.btnListMails.disabled=true;
	//frm.btnViewExceptions.disabled=true;
	//frm.btnProcess.disabled=true;
	frm.elements.btnViewAccount.disabled = true;
	//frm.btnCloseFlight.disabled=true;



	if(frm.elements.listSegmentsFlag.value=="Y"){

		frm.elements.carrierCode.disabled=true;
		frm.elements.flightNumber.disabled=true;
		frm.elements.flightDate.disabled=true;
		frm.elements.btnList.disabled=true;
		frm.elements.btnListMails.disabled=false;
		if(targetFormName.elements.carrierCode.disabled == true){
			document.getElementById('Despatch').focus();
		}
	}
	if(frm.elements.listFlag.value=="Y"){
		//frm.btnViewExceptions.disabled=false;
		//frm.btnProcess.disabled=false;
		if(targetFormName.elements.accEntryFlag.value != "N"){
			//Added by A-8527 for ICRD-345683
			if(frm.elements.btnViewAccount.getAttribute("privileged") == 'Y'){	
		frm.elements.btnViewAccount.disabled = false;
			}
		}
		//frm.btnCloseFlight.disabled=false;

	}
	if(frm.elements.fromScreen.value=="from_accountingScreen"){
		if(targetFormName.elements.accEntryFlag.value != "N"){
			//Added by A-8527 for ICRD-345683
			if(frm.elements.btnViewAccount.getAttribute("privileged") == 'Y'){
	     frm.elements.btnViewAccount.disabled = false;
			}
	 	}
	}

	var segstatus=document.getElementsByName('segmentStatus');
	//alert(segstatus.value);
	if(segstatus.length>0){
	if(segstatus[0].value=="P"){
	//targetFormName.btnProcess.disabled=true;
	}

	if(segstatus[0].value!="E"){

	//targetFormName.btnViewExceptions.disabled=true;
	}
	if(segstatus[0].value=="T"){
		//targetFormName.btnViewExceptions.disabled=false;
		//targetFormName.btnProcess.disabled=false;
	}
	if(segstatus[0].value=="E"){
			//targetFormName.btnViewExceptions.disabled=false;
			//targetFormName.btnProcess.disabled=false;
	}

	}
	if(frm.elements.duplicateFlightFlag.value=="Y"){
	frm.elements.duplicateFlightFlag.value="";
	openPopUp("flight.operation.duplicateflight.do","600","280");

	}

	if(frm.elements.oneSegmentFlag.value=="Y"){

		frm.elements.segment.disabled=true;

	}

	if(frm.elements.processFlag.value=="Y"){

		//showDialog('Processing Success',2,self);
		showDialog({msg:'Processing Success',type:2});
		frm.elements.processFlag.value="";
		submitForm(targetFormName,'mra.flown.viewflownmail.listflownmails.do');
	}
	if(frm.elements.fromScreen.value=="from_accountingScreen"){
		frm.elements.btnListMails.disabled=false;
		if(targetFormName.elements.accEntryFlag.value != "N"){
		      frm.elements.btnViewAccount.disabled = false;
		  }
	}
}




/**
 * for displaying the flight details
 */
function listSegments(){


	submitForm(targetFormName,'mra.flown.viewflownmail.listsegments.do');

}

/**
 * liting the Flown Mails
 */
function list(){


	submitForm(targetFormName,'mra.flown.viewflownmail.listflownmails.do');


}



/**
 * for clearing the screen
 */

function clear(){


	submitForm(targetFormName,'mra.flown.viewflownmail.clear.do');

}

function importData(){


	submitForm(targetFormName,'mra.flown.viewflownmail.importData.do');

}

function viewExceptions(){

     var frm = targetFormName;
     submitForm(targetFormName,'mailtracking.mra.flown.assignexceptions.listexceptions.do?viewFlownMailFlag=Y&statusFlag='+"toList"+'&flightCarrierCode='+frm.elements.carrierCode.value+'&flightNumber='+frm.elements.flightNumber.value+'&fromDate='+frm.elements.flightDate.value+'&toDate='+frm.elements.flightDate.value);

}

/*function process(){


	submitForm(targetFormName,'mra.flown.viewflownmail.processmails.do');

}*/


function closeFlight(){

	submitForm(targetFormName,'mra.flown.viewflownmail.closeflight.do');

}


/**
 * for closing the screen
 */
function closeScreen(){


	submitForm(targetFormName,'mra.flown.viewflownmail.closescreen.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.carrierCode.disabled == false && targetFormName.elements.carrierCode.readOnly== false){
			targetFormName.elements.carrierCode.focus();
		}
		else{
			document.getElementById('Despatch').focus();
		}
	}
}
//function viewAccount(){
//var frm= targetFormName ;
//for(var i=0;i<frm.mailBagSegmentStatus.length;i++){
//alert(frm.mailBagSegmentStatus[i]);
//alert(frm.mailBagSegmentStatus[i].value);
//}

  //submitForm(targetFormName,'mra.flown.viewflownmail.viewAccount.do');

//}

/////////////

function viewAccount(){

var chkbox=targetFormName.elements.rowCount;
var selectedRow=targetFormName.elements.selectedRow;
var selectedRows = document.getElementsByName('selectedRow');
var rowCount = document.getElementsByName('rowCount');
//alert(targetFormName.rowCount.value);
var selectedFlg="Y";
if(selectedRows != null){
	for(var i=0;i<selectedRows.length;i++) {
		if(selectedRows[i].checked==true){
			selectedFlg="N";
		}
	}
}
if(rowCount != null){
	for(var i=0;i<rowCount.length;i++) {
		if(rowCount[i].checked==true){
			selectedFlg="N";
		}
	}
}
if(selectedFlg=="Y"){
	//showDialog('Please select a Row', 1, self, targetFormName, 'id_2');
	showDialog({msg:'Please select a Row',type:1,parentWindow:self,dialogId:'id_2'});
	return;
}
if(selectedRow != null){
   submitForm(targetFormName,'mra.flown.viewflownmail.viewAccount.do');
}
else if(chkbox != null && chkbox.length>1){
   for(var i=0;i<chkbox.length;i++) {

  if(chkbox[i].checked==true){
	    submitForm(targetFormName,'mra.flown.viewflownmail.viewAccount.do');
}
}


}else if(chkbox != null){
		submitForm(targetFormName,'mra.flown.viewflownmail.viewAccount.do');
}

else if(validateSelectedCheckBoxes(targetFormName,'rowCount',1,1)){
//alert(chkbox.value);
for(var i=0;i<chkbox.length;i++) {
 //alert(1);
if(chkbox[i].checked==true){

	//alert(targetFormName.dsnSegmentStatus[i].value);
alert(1);
    submitForm(targetFormName,'mra.flown.viewflownmail.viewAccount.do');

   }
   }

}
}


