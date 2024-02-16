<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{

	var frm = targetFormName;
	if((!frm.elements.mucReferenceNumber.disabled)&&(!frm.elements.mucReferenceNumber.readOnly)){
	frm.elements.mucReferenceNumber.focus();
	}
	with(frm)
		{		
				onScreenLoad();
				evtHandler.addEvents("btnClose","closeMUC(this.frm)",EVT_CLICK);
				evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
				evtHandler.addEvents("btnList","listEvent()",EVT_CLICK);
				evtHandler.addEvents("btnClear","clearEvent()",EVT_CLICK);
				evtHandler.addEvents("btnSave","saveEvent()",EVT_CLICK);
				evtHandler.addEvents("btnSend","sendEvent()",EVT_CLICK);
				evtHandler.addEvents("btnDetails","detailsEvent()",EVT_CLICK);
				evtHandler.addEvents("btnActions","actionsEvent()",EVT_CLICK);
				evtHandler.addEvents("invoiceRefNumber","validateFields(this, -1, 'InvoiceRefNumber', 0, true, true)",EVT_BLUR);
				addIEonScroll();
				DivSetVisible(true);
		}
}

function onScreenLoad(){
		var frm=targetFormName;
	if(frm.elements.enableFlag.value == "enable"){
		frm.elements.btnSend.disabled=false;
		frm.elements.btnDetails.disabled=false;
		frm.elements.btnActions.disabled=false;
		frm.elements.btnSave.disabled=false;
		frm.elements.btnList.disabled=true;
		frm.elements.enableFlag.value ="";
	} else {
		frm.elements.btnSend.disabled=true;
		frm.elements.btnDetails.disabled=true;
		frm.elements.btnActions.disabled=true;
		frm.elements.btnSave.disabled=true;
	}
						
	}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				 if((!frm.elements.mucReferenceNumber.disabled)&&(!frm.elements.mucReferenceNumber.readOnly)){
					frm.elements.mucReferenceNumber.focus();
				}							
	}	
}	

function closeMUC(frm)
{
   location.href = appPath+"/home.jsp";
}
function listEvent()
{
	//alert("inside List button");
	
	if(targetFormName.elements.mucReferenceNumber.value == ""){
				showDialog({msg:"MUC Reference Number is mandatory",type:1,parentWindow:self});
				return;
			}
	submitForm(targetFormName, 'uld.defaults.messaging.muctracking.listcommand.do');
}
function clearEvent()
{
	//alert("inside clear button");
	//submitForm(targetFormName, 'uld.defaults.messaging.muctracking.clearcommand.do');
	//added by a-3045 for bug19358 starts
	//This part is to uncheck the selection checkboxes so that Unsave Check dialog box must not come while selecting those checkboxes alone and clicking clear
	var selectedRows = document.getElementsByName("selectedrow");
	selectedRowsLength = selectedRows.length;
	if(selectedRowsLength == 1){
	targetFormName.elements.selectedrow.checked = false;
	}else if(selectedRowsLength > 1){
		for(var i= 0;i<selectedRowsLength;i++){
			targetFormName.elements.selectedrow[i].checked = false;
		}
	}
	targetFormName.elements.masterRowId.checked = false;
	//added by a-3045 for bug19358 ends
	submitForm(targetFormName,'uld.defaults.messaging.muctracking.clearcommand.do');

}
function saveEvent()
{
	//alert("inside save button");	
	updateOperationFlags();
	updateMucTime();
	var uldNumber = document.getElementsByName('uldNumber');
	var mucDate = document.getElementsByName('mucDate');
	var mucTime = document.getElementsByName('mucTime');
	var txnAirport = document.getElementsByName('txnAirport');
	var CRN = document.getElementsByName('CRN');
	var destAirport = document.getElementsByName('destAirport');
	var length=uldNumber.length;	
	if(length == 0){
       showDialog({msg:'ULDs are Mandatory',type:1, parentWindow:self});
       return;
	}	
	for(var i=0;i<length;i++){
		if(length>1){
			if(targetFormName.elements.mucDate[i].value == ""){
				showDialog({msg:"MUC Date is mandatory",type:1,parentWindow:self});
				return;
			}
			if(targetFormName.elements.mucTime[i].value == ""){
				showDialog({msg:"MUC Time is mandatory",type:1,parentWindow:self});
				return;
			}
			if(targetFormName.elements.txnAirport[i].value == ""){
				showDialog({msg:"Txn Airport is mandatory",type:1,parentWindow:self});
				return;
			}
			if(targetFormName.elements.CRN[i].value == ""){
				showDialog({msg:"CRN is mandatory",type:1,parentWindow:self});
				return;
			}
			if(targetFormName.elements.destAirport[i].value == ""){
				showDialog({msg:"Dest Airport is mandatory",type:1,parentWindow:self});
				return;
			}
			
		}else{
			if(targetFormName.elements.mucDate.value == ""){
				showDialog({msg:"MUC Date is mandatory", type:1, parentWindow:self});
				return;
			}
			if(targetFormName.elements.mucTime.value == ""){
				showDialog({msg:"MUC Time is mandatory", type:1, parentWindow:self});
				return;
			}
			if(targetFormName.elements.txnAirport.value == ""){
				showDialog({msg:"Txn Airport is mandatory", type:1, parentWindow:self});
				return;
			}
			if(targetFormName.elements.CRN.value == ""){
				showDialog({msg:"CRN is mandatory", type:1, parentWindow:self});
				return;
			}
			if(targetFormName.elements.destAirport.value == ""){
				showDialog({msg:"Dest Airport is mandatory", type:1, parentWindow:self});
				return;
			}
		}
	}
	//duplicateCRNCheck();
	submitForm(targetFormName, 'uld.defaults.messaging.muctracking.savecommand.do');

}
function sendEvent()
{
	//alert("inside send button");
	var uldNumber = document.getElementsByName('uldNumber');  
	var chkcount = uldNumber.length;		
	if(chkcount == 0){
       showDialog({msg:'ULDs are Mandatory', type:1, parentWindow:self});
       return;
	}
	submitForm(targetFormName, 'uld.defaults.messaging.muctracking.sendcommand.do');

}
function detailsEvent()
{
	//alert("inside details button");
	var uldNumber = document.getElementsByName('uldNumber');
		var chkcount = uldNumber.length;
		if(chkcount == 0){
	       showDialog({msg:'ULDs are Mandatory',type:1, parentWindow:self});
	       return;
	}
	var mucRefNumber = targetFormName.elements.mucReferenceNumber.value;
	submitForm(targetFormName, 'uld.defaults.transaction.listuldtransactions.do?pageURL=ListLoanBorrowEnq&mucReferenceNumber='+mucRefNumber);

}
function actionsEvent()
{

if(validateSelectedCheckBoxes(targetFormName,'selectedrow',1,1)){	
	//alert("inside actionsbutton");
	var frm=targetFormName;	
	var selectedRows = document.getElementsByName("selectedrow");
	var CRN = document.getElementsByName('CRN');
	var uldNumber = document.getElementsByName('uldNumber');
	var crnNum = "";
	var uldNum = "";
	for(var i=0;i<selectedRows.length;i++){
	//alert(selectedRows[i].value);
		if(selectedRows[i].checked){
		
		crnNum = CRN[i].value;
		uldNum = uldNumber[i].value;
			var newWindow = openPopUp(
				"uld.defaults.messaging.muctracking.actions.screenload.do?selectedrow="+selectedRows[i]+"&crnNumber="+crnNum+"&uldNum="+uldNum,"600","305");
		}
	}
	//var selectedrow=frm.selectedrow.value;
	//var newWindow = openPopUp(
	//			"uld.defaults.messaging.muctracking.actions.screenload.do?selectedrow="+selectedrow,"600","300");	
}
}
function displayMUCRefNoLov() {
	var frm=targetFormName;
	
	var strurl='uld.defaults.messaging.mucrefnolov.screenload.do?mucReferenceNum='+frm.elements.mucReferenceNumber.value+'&mucLovFilterDate='+frm.elements.mucFilterDate.value;
	openPopUp(strurl,500,450);
}

function submitLovPage(lastPg,displayPg){
  targetFormName.elements.lastLovPageNum.value=lastPg;
  targetFormName.elements.displayLovPage.value=displayPg;
  submitForm(targetFormName, appPath + '/uld.defaults.messaging.mucrefnolov.screenload.do');
}


function updateOperationFlags(){
	var frm = targetFormName;
	var uldNumber = document.getElementsByName('uldNumber');
	var mucDate = document.getElementsByName('mucDate');
	var mucTime = document.getElementsByName('mucTime');
	var txnAirport = document.getElementsByName('txnAirport');
	var CRN = document.getElementsByName('CRN');
	var destAirport = document.getElementsByName('destAirport');
	var condition = document.getElementsByName('condition');
	var iataStatus = document.getElementsByName('iataStatus');	
	if(uldNumber != null && uldNumber.length >0){
	for(var i=0;i<uldNumber.length;i++){			
			if(isElementModified(mucDate[i])
				|| isElementModified(mucTime[i])
				|| isElementModified(txnAirport[i])
				|| isElementModified(CRN[i])
				|| isElementModified(destAirport[i])
				|| isElementModified(condition[i]))
				{
					iataStatus[i].value = 'U';			
				}		
	  }
	}

}


function updateMucTime(){
	var frm = targetFormName;
	var uldNumber = document.getElementsByName('uldNumber');
	var mucTime = document.getElementsByName('mucTime');
	//modified by A-5165 for icrd-31854
	if(uldNumber != null && uldNumber.length >0){
	for(var i=0;i<uldNumber.length;i++){			
		//	if(isElementModified(mucTime[i]))
			//	{
					mucTime[i].value = mucTime[i].value+":00";			
				//}		
	  }
	}

}

/*function duplicateCRNCheck(){
	var frm = targetFormName;
	var uldNumber = document.getElementsByName('uldNumber');
	var CRN = document.getElementsByName('CRN');
	var crnSize = 0;
	crnSize = uldNumber.length;
	if(uldNumber != null && uldNumber.length >1){
	for(var i=0;i<crnSize;i++){		
alert("1");	
			frm.crnCheck[i].value = 'N';
alert("2");				
	  }
	  for(var i=0;i<crnSize;i++){			
			if(isElementModified(CRN[i]))
				{
				alert("3");	
					frm.crnCheck[i].value = 'Y';		
				}		
	  }
	}
	alert();
	if(uldNumber != null && uldNumber.length == 1){			
		frm.crnCheck.value = 'N';		  
		  for(var i=0;i<crnSize;i++){			
				if(isElementModified(CRN[i]))
					{
						frm.crnCheck.value = 'Y';		
					}		
		  }
	}

}*/
