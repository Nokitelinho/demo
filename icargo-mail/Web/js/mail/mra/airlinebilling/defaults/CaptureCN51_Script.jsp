<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister()
{
	with(targetFormName){
		evtHandler.addEvents("btList","doList()",EVT_CLICK);
		evtHandler.addEvents("btClear","doClear()",EVT_CLICK);
		evtHandler.addEvents("btnPrint","doPrint()",EVT_CLICK);
		evtHandler.addEvents("btEnquiry","showCn66Details()",EVT_CLICK);
		evtHandler.addEvents("btSave","doSave()",EVT_CLICK);
		evtHandler.addEvents("btClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addEvents("modifyLink","onModify()",EVT_CLICK);
		evtHandler.addEvents("deleteLink","ondelete()",EVT_CLICK);
		evtHandler.addEvents("airlinelov","showAirline()",EVT_CLICK);
		//evtHandler.addEvents("clearancePeriodlov","clearanceLOV()",EVT_CLICK);
		evtHandler.addEvents("clearancePeriodlov","displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
		evtHandler.addEvents("invoicenolov","invoiceLOV()",EVT_CLICK);
		evtHandler.addEvents("carriageFromlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriageFrom.value,'Carriage From','1','carriageFrom','',0)",EVT_CLICK);
		evtHandler.addEvents("carriageTolov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriageTo.value,'Carriage To','1','carriageTo','',0)",EVT_CLICK);

		if(targetFormName.elements.select!=null){
			evtHandler.addEvents("select","select()",EVT_CLICK);
		}

		evtHandler.addEvents("wtLCAO","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("wtCP","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("wtUld","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("wtSv","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("rate","restrictFloat(this,8,3)",EVT_KEYPRESS);

	}
	screenload();
}
function invoiceLOV(){
	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
	displayLOVCodeNameAndDesc('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.invoiceRefNo.value,'Invoice Number','1','invoiceRefNo','clearancePeriod','airlineCode',0,_reqHeight);
	
}
function clearanceLOV(){
	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
	displayLOV('showUPUClearancePeriods.do','N','Y','showUPUClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0,_reqHeight);
}
function screenload(){
	/*if(targetFormName.billingType.value!="O") {
	//updateCpStatus();
}*/

if(targetFormName.elements.billingType.value=="O") {
	disableTableFields();
	targetFormName.elements.linkStatusFlag.value="disable";
	targetFormName.elements.btSave.disabled=true;
	disableLink(document.getElementById("modifyLink"));
	//disableLink(document.getElementById('deleteLink'));
}
	if(!targetFormName.elements.invoiceRefNo.disabled) {
		targetFormName.elements.invoiceRefNo.focus();
	}else{
		document.getElementById('modifyLink').focus();
	}
	if(targetFormName.elements.statusFlag.value=="DISABLEALL") {
		setViewMode();
	}else if(targetFormName.elements.statusFlag.value=="DISABLE") {
		disableAllFields();
	}else {
		checkLinkStatus();
	}
	
	if(targetFormName.elements.btSave.disabled==true && targetFormName.elements.billingType.value!="O"){
			targetFormName.elements.btnPrint.disabled=true;
	}else{
		targetFormName.elements.btnPrint.disabled=false;
	}
	var status=new Array();
	status=document.getElementsByName('cn51Status');
	if(status!=null && status.length>0){
		if(status[0]!=null){
			if(status[0].value=='P' || status[0].value=='W'){
				
				disableLinks();
				disableTableFields();
				disableButtons();
			}
		}
	}
	var linkStatus=document.getElementsByName('cn51Status').value;
			if(linkStatus!=null){
				if(linkStatus!=null){
				//alert(linkStatus);
				if(linkStatus=='P' || linkStatus=='E'){
				disableLinks();
				disableTableFields();
				disableButtons();
					}
				}
	}

	
	
}

function checkLinkStatus()
{
var flag=targetFormName.elements.linkStatusFlag.value;
	if(flag == "disable"){
		disableLinks();
	}
	else {
		enableLinks();
	}
}

function disableAllFields() {
	disableLinks();
	disableTableFields();
	disableButtons();
}

function setViewMode() {
	disableLinks();
	disableTableFields();
	disableAllButtons();
}
function disableButtons(){
	targetFormName.elements.btSave.disabled=true;
}

function disableAllButtons() {
	targetFormName.elements.btList.disabled=true;
	targetFormName.elements.btClear.disabled=true;
	targetFormName.elements.btEnquiry.disabled=true;
	targetFormName.elements.btSave.disabled=true;
}

function disableTableFields() {
	var rowId = document.getElementsByName('select');

	var carriagesFrom = document.getElementsByName('carriagesFrom');

	var carriagesTo = document.getElementsByName('carriagesTo');

	var wtLCAO = document.getElementsByName('wtLCAO');

	//var rateLCAO = document.getElementsByName('rateLCAO');
	//var amountLCAO = document.getElementsByName('amountLCAO');
	var wtCP = document.getElementsByName('wtCP');

	//var rateCP = document.getElementsByName('rateCP');
	//var amountCP = document.getElementsByName('amountCP');
	if(rowId != null) {

		if (rowId.length > 0) {

			for (var i = 0; i < rowId.length; i++) {

			   //var checkBoxValue = rowId[i].value;
			   //carriagesFrom[checkBoxValue].readonly=true;

			  // carriagesTo[checkBoxValue].readonly=true;

			  // wtLCAO[checkBoxValue].readonly=true;
			   //rateLCAO[checkBoxValue].readonly=true;
			  // wtCP[checkBoxValue].readonly=true;
			  // rateCP[checkBoxValue].readonly=true;
			}
		}
	}
}

// Function for disabling the add and delete link
function disableLinks()
 {
 	disableLink(document.getElementById('modifyLink'));
 	disableLink(document.getElementById('deleteLink'));
}


// Function for enabling the add and delete link
function enableLinks()
 {
 	if(document.getElementById('modifyLink').disabled) {
 		enableLink(document.getElementById('modifyLink'));
 	}
 	if(document.getElementById('deleteLink').disabled) {
 		enableLink(document.getElementById('deleteLink'));
 	}
}

//Function for toggling checkbox
function select(){
	toggleTableHeaderCheckbox('select', targetFormName.selectAll);
	var operationFlag = document.getElementsByName('operationFlag');
	var select = document.getElementsByName('select');
	for(var i=0;i< select.length;i++) {
	var selectedObj = select[i];
		if(selectedObj.checked) {
			if(operationFlag[i].value=="I") {
				targetFormName.btEnquiry.disabled=true;
			}else {
				if(targetFormName.btEnquiry.privileged == 'Y'){
				targetFormName.btEnquiry.disabled=false;
				}
			}
		}else {
			if(targetFormName.btEnquiry.privileged == 'Y'){
			targetFormName.btEnquiry.disabled=false;
			}
		}
	}
}

function selectAll(){
	updateHeaderCheckBox(targetFormName, targetFormName.elements.selectAll, targetFormName.elements.select);
}



 //function for listing
function doList() {
	targetFormName.lastPageNumber.value=0;
	targetFormName.displayPageNum.value=1;
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.list.do');
}

//function for clearing
function doClear() {
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.clear.do');
}

//function for saving
function doSave() {
	var frm = targetFormName;
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.save.do');
}

function showCn66Details() {
	if(validateSelectedCheckBoxes(targetFormName,'select','',1)){				
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.cn66enquiry.do');						
	}
}





function screenConfirmDialog(frm, dialogId) {



    	while(frm.currentDialogId.value == ''){

 	}

 	   	if(frm.currentDialogOption.value == 'Y') {
 			if(dialogId == 'id_1'){

			setOperationalFlag(targetFormName);

			if(isFormModified(targetFormName)) {
				submitForm(targetFormName,'cra.airlinebilling.defaults.captureform1.save.do');
			}else {
				showDialog({msg:'No Modified Data For Save',type:1,parentWindow:self});

			}

			//submitForm(targetFormName,'cra.airlinebilling.defaults.captureform1.save.do');
 		}
 		if(dialogId == 'id_2'){
				targetFormName.screenFlg.value="";
				targetFormName.lastPageNumber.value= lstPageNum;
				targetFormName.displayPageNum.value = dispPageNum;
				lstPageNum=0;
				dispPageNum=0;

			    submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.list.do');

	    }

 	}
 }


 function screenNonConfirmDialog(frm, dialogId) {

  	while(frm.currentDialogId.value == ''){

  	}

  	if(frm.currentDialogOption.value == 'N') {
  		if(dialogId == 'id_1'){

  		}
  		if(dialogId == 'id_2'){

  		}
  	}
 }


//function for close
function doClose() {
	var option;
	if(isFormModified(targetFormName)||rowDeleted()||targetFormName.deleteFlag.value=="true"){
	
	submitFormWithUnsaveCheck('mailtracking.mra.airlinebilling.defaults.capturecn51.close.do');
		/*option=showWarningDialog('Unsaved data exists.Do you want to continue?');
			if(option==true){
				submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.close.do');
				}*/
		}else{

		submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.close.do');
		}

}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.invoiceRefNo.disabled == false && targetFormName.invoiceRefNo.readOnly== false){
			targetFormName.invoiceRefNo.focus();
		}
		else{
			document.getElementById('modifyLink').focus();
		}
	}
}
function doPrint() {
	var frm = targetFormName;
	generateReport(frm,"/mailtracking.mra.airlinebilling.defaults.capturecn51.print.do");
}

//function to validate clearance period
function validateClearancePeriod() {

	var clearancePeriod = targetFormName.clearancePeriod.value;
	var month = "";
	var weeks = 0;
	if(clearancePeriod != "") {
		if(clearancePeriod.length<6){
			showDialog({msg:"<common:message bundle='captureform1' key='cra.airlinebilling.defaults.captureform1.msg.err.clearanceperiodformat' scope='request'/>",type:1,parentWindow:self});
			targetFormName.clearancePeriod.focus();
			return;

		}

		if(clearancePeriod.length==7){
			showDialog({msg:"<common:message bundle='captureform1' key='cra.airlinebilling.defaults.captureform1.msg.err.clearanceperiodformatweeks' scope='request'/>",type:1,parentWindow:self});
			targetFormName.clearancePeriod.focus();
			return;
		}

		month = clearancePeriod.substring(4,6);

		if(eval(month)<1||eval(month)>12){
			showDialog({msg:'<common:message bundle="captureform1" key="cra.airlinebilling.defaults.captureform1.msg.err.monthentrynotvalid" scope="request"/>',type:1,parentWindow:self});
			targetFormName.clearancePeriod.focus();
			return;

		}

		if(clearancePeriod.length==8){

			weeks = clearancePeriod.substring(6,8);

			if(eval(weeks)<1||eval(weeks)>5){
				showDialog({msg:'<common:message bundle="captureform1" key="cra.airlinebilling.defaults.captureform1.msg.err.weeksentrynotvalid" scope="request"/>',type:1,parentWindow:self});
				targetFormName.clearancePeriod.focus();
				return;
			}

		}
	}
}

function confirmMessage(frm) {
	onModify();
}

function nonconfirmMessage(frm) {
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.clear.do');
}

function onModify(){
	  var chkbox =document.getElementsByName("select");

	  var selectedCN51 = "";
	  var cnt1 = 0;
	  for(var i=0; i<chkbox.length-1;i++){
		 if(chkbox[i].checked) {
			 if(cnt1 == 0){
				selectedCN51 = chkbox[i].value;
				cnt1 = 1;
			 }else{
				selectedCN51 = selectedCN51 + "," + chkbox[i].value;
			 }
		}
	 }
	 var frm = targetFormName;
	 //var blgCurCode= document.getElementById('blgCurCode').value;
	 var blgCurCode= frm.elements.blgCurCode[0].value;

  	openPopUp("mailtracking.mra.airlinebilling.defaults.capturecn51.screenloadcn51details.do?selectedRow="+selectedCN51+"&blgCurCode="+blgCurCode,"600","300");
}

function ondelete(){


var chkBoxIds = document.getElementsByName('select');
	var chkcount = chkBoxIds.length+1;
 	if(validateSelectedCheckBoxes(targetFormName,'select',chkcount,'1')){
 		targetFormName.deleteFlag.value="true";
		deleteTableRow('select','operationFlag');
		//submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.delete.do');
 	}
	
	
	/*
	function deleterow(){
		if(validateSelectedCheckBoxes(targetFormName,'select','','1')){
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturecn51.delete.do');
		}
	}*/
	
	
	
	/*
	
	
	var chkBoxIds = document.getElementsByName('select');
	var chkcount = chkBoxIds.length+1;
 	if(validateSelectedCheckBoxes(targetFormName,'select',chkcount,'1')){
 		targetFormName.deleteFlag.value="true";
		deleteTableRow('select','operationFlag');
 	}*/
}


function submitFormWithUnsaveChk(url){
	if( isSubmitInvoked == false){
	unsaveChkSpan=document.getElementById("ignoreUnSaveCheck");
	if(unsaveChkSpan==null){
		unsaveChkSpan=document.createElement("span");
		unsaveChkSpan.id="ignoreUnSaveCheck";
		unsaveChkSpan.style.display="none";
	}
	unsaveChkSpan.innerHTML="N";
	document.body.style.cursor = 'wait';
		if(targetFormName!=null){
    		if(checkFormModified(targetFormName)){
                var fChanged=checkFormModified(targetFormName);
                if( fChanged==true){
                    var cnfrm=self.showWarningDialog("Unsaved data exists. Do you want to continue ?");
                    if ( cnfrm==false ){
                        document.body.style.cursor = 'default';
						isSubmitInvoked = false;
                    }
                    else{

                       submitForm(targetFormName,url);
                    }
                }
                else{
                   submitForm(targetFormName,url);
                }
            }
            else{
            	submitForm(targetFormName,url);
            }
		}else{
			self.top.location=appPath+"/"+url;
			isSubmitInvoked = true;
		}
	}
}

/* Check if a form has been modified after it was loaded */
function checkFormModified(fobj) {
	var CHECK_BOX     = "CHECKBOX";
	var TEXT_FIELD    = "TEXT";
	var TEXT_AREA     = "TEXTAREA";
	var COMBO_BOX     = "SELECT";
	var COMBO_BOX_ONE     = "SELECT-ONE";
	var RADIO_BUTTON  = "RADIO";
	var FIELD_SET     = "FIELDSET";
	var _hasFormChanged = false;
	if(arguments.length==0){
		fobj = targetFormName;
	}
	if(fobj==null)
	{
		fobj = document.forms[document.forms.length - 1];
	}
	var formObj  = fobj;
	if(formObj!=null)
	{
		var formElementObj = null;
		var elementType = null;
		for (var i = 0; i < formObj.elements.length; i++) {
			formElementObj = formObj.elements[i];
			if(formElementObj.getAttribute("ic_filter")!=null){
				continue;
			}
			if (formElementObj.tagName != FIELD_SET) {
				elementType = formElementObj.type.toUpperCase();
				if (elementType == TEXT_FIELD) {
					if (formElementObj.value != formElementObj.defaultValue) {
						_hasFormChanged = true;
						break;
					}
				} // End of IF TEXT
				else if (elementType == TEXT_AREA) {
					if (formElementObj.value != formElementObj.defaultValue) {
						_hasFormChanged = true;
						break;
					}
				} // End of if TEXTAREA
				// END of IF CHECKBOX OR RADIOBUTTON
				else if (elementType == COMBO_BOX || elementType == COMBO_BOX_ONE){

					var _isDefault = false;
					for (var j = 0; j < formElementObj.length; j++) {
						if( formElementObj.options[j].defaultSelected)
						{
							_isDefault = true;
							break;
						}
					}
					if(!_isDefault)
					{
						try{
						formElementObj.options[0].defaultSelected = true;

						}catch(e){
						continue;
						}
					}
					for (var j = 0; j < formElementObj.length; j++) {

						if (formElementObj.options[j].selected != formElementObj.options[j].defaultSelected){
							_hasFormChanged = true;
							break;
						}
					}
				}
				if (_hasFormChanged) { break; }
			}
		}
	}
	return _hasFormChanged;
}

//for updating the totalAmount
function updateTotal(obj,displayName) {
	var name =obj.name;
	var rowCount =obj.id.split(name)[1];
	var passengerAmount = document.getElementsByName('passengerAmount');
	var cargoAmount = document.getElementsByName('cargoAmount');
	var miscAmount = document.getElementsByName('miscAmount');
	var uatpAmount = document.getElementsByName('uatpAmount');
	if(validateFields(obj,-1,displayName,2,true,true,8,4)){
		if(obj.value == null || obj.value.trim().length == 0) {
			obj.value = 0;
		}
	}
	updateSummary(obj);
}


function updateLcStatus(obj) {
	var code = "";

	if(obj.value=="") {
		obj.value=0.0;
	}

	if(name=="wtCP") {
		code = "weight";
	}
	if(name=="rateCP") {
		code = "rate";
	}
	if(name=="amountCP") {
		code = "amount";
	}
	var wtLCAO = document.getElementsByName('wtLCAO');

	var rateLCAO = document.getElementsByName('rateLCAO');

	var amountLCAO = document.getElementsByName('amountLCAO');

	var wtCP = document.getElementsByName('wtCP');

	var rateCP = document.getElementsByName('rateCP');

	var amountCP = document.getElementsByName('amountCP');

	var rowCount = obj.id.split(obj.name)[1];
	/*if(targetFormName.operationFlag[targetFormName.operationFlag.length - rowCount - 2].value == 'I'){
		rowCount = targetFormName.wtCP.length - rowCount - 2;
	}
	else{
		var insertedFlags = 0;
		for(var i = 0; i < targetFormName.operationFlag.length - 1; i++){
			if(targetFormName.operationFlag[i].value == 'I'){
				++insertedFlags;
			}
		}
		rowCount = eval(rowCount + insertedFlags);
	}*/
	rowCount = eval(rowCount);
	if(validateFields(obj,-1,code,2,true,true,8,4)){
			if(obj.value != null && obj.value.trim().length >0) {
				if(parseFloat(obj.value) >0) {
					wtLCAO[rowCount].value=0.0;
					rateLCAO[rowCount].value=0.0;
					amountLCAO[rowCount].value=0.0;
					if(wtCP[rowCount].value != '' && rateCP[rowCount].value != ''){
						amountCP[rowCount].value=(parseFloat(wtCP[rowCount].value)*parseFloat(rateCP[rowCount].value)).toFixed(2);
					}
					wtLCAO[rowCount].disabled=true;
					rateLCAO[rowCount].disabled=true;
					amountLCAO[rowCount].disabled=true;
				}else {
					amountCP[rowCount].value =0.0;
					if(wtCP[rowCount].value ==0.0 &&
					rateCP[rowCount].value ==0.0 &&
					amountCP[rowCount].value ==0.0 ) {
						wtLCAO[rowCount].disabled=false;
						rateLCAO[rowCount].disabled=false;
						amountLCAO[rowCount].disabled=false;
						wtCP[rowCount].disabled=false;
						rateCP[rowCount].disabled=false;
						amountCP[rowCount].disabled=false;
					}
				}
			}
	}
}

function showAirline(){

	//displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',
		//document.forms[1].airlineCode.value,'Airline code','1',
		//'airlineCode', 'clearancePeriod','',0)
	displayLOV('showAirline.do','N','Y','showAirline.do',
		document.forms[1].airlineCode.value,'Airline code','0',
		'airlineCode', '',0)
 }

function showFromStation(obj) {
	var rowCount = obj.id.split(obj.name)[1];
	/*if(targetFormName.operationFlag[targetFormName.operationFlag.length - rowCount - 2].value == 'I'){
			rowCount = targetFormName.wtCP.length - rowCount - 2;
		}
		else{
			var insertedFlags = 0;
			for(var i = 0; i < targetFormName.operationFlag.length - 1; i++){
				if(targetFormName.operationFlag[i].value == 'I'){
					++insertedFlags;
				}
			}
			rowCount = eval(rowCount + insertedFlags);
	}*/
	rowCount = eval(rowCount);
	displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriagesFrom.value,'carriagesFrom','1','carriagesFrom','',rowCount);
}
function showToStation(obj) {
	var rowCount = obj.id.split(obj.name)[1];
		/*if(targetFormName.operationFlag[targetFormName.operationFlag.length - rowCount - 2].value == 'I'){
				rowCount = targetFormName.wtCP.length - rowCount - 2;
			}
			else{
				var insertedFlags = 0;
				for(var i = 0; i < targetFormName.operationFlag.length - 1; i++){
					if(targetFormName.operationFlag[i].value == 'I'){
						++insertedFlags;
					}
				}
				rowCount = eval(rowCount + insertedFlags);
	}*/
	rowCount = eval(rowCount);
	displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriagesTo.value,'carriagesTo','1','carriagesTo','',rowCount);
}

function rowDeleted() {
   	var operationFlag = document.getElementsByName('operationFlag');   //for operation flag
   	var flag=0;
   	for(var i=0;i<operationFlag.length;i++) {
   		if(operationFlag[i].value=="D" || operationFlag[i].value=="U" ||operationFlag[i].value=="I")  {
   			flag=1;
   			break;
   		}
       }
       if(flag==0){
       	return false;
       }
       else {
        return true;
       }
}

function updateDataAfterAsync(_tableInfo){
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	targetFormName.screenFlag.value=_innerFrm.screenFlag.value;
	targetFormName.statusFlag.value=_innerFrm.statusFlag.value;
	targetFormName.linkStatusFlag.value=_innerFrm.linkStatusFlag.value;
	updateTableCode(_tableInfo);
	checkLinkStatus();
	if(_asyncErrorsExist) return;
 }

////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";

function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

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
}

function getActualData(_tableInfo){
	_frm=_tableInfo.document.getElementsByTagName("table")[0];
	return _frm.outerHTML;
}

function cleanupTmpTable(){
	document.getElementById("tmpSpan").innerHTML="";
}
/////////////////////////////////////////////////////////////////////////////////////////


function afterSum()
{
	var resultMap = arguments[1];

	firstResult  = resultMap.getResult(0);
	//secondResult=resultMap.getResult(1);


	document.getElementById("netWeight").innerHTML= firstResult;
	//document.getElementById("netUsd").innerHTML= secondResult;




}

function change(rowcount){

	var exgrate=document.getElementsByName("rate");
	var lc=document.getElementsByName("wtLCAO");
	var cp=document.getElementsByName("wtCP");
	//var sal=document.getElementsByName("wtSal");
	var uld=document.getElementsByName("wtUld");
	var sv=document.getElementsByName("wtSv");
	var val=0.0;
	var operation = new MoneyFldOperations();

	targetFormName.rowCount.value=rowcount;
	if(lc[rowcount].value>0.0) {
		val=lc[rowcount].value;
	}

	else if(cp[rowcount].value>0.0)
		val=cp[rowcount].value;
	/* SAL NOT IN USE
		else if(sal[rowcount].value>0.0)
		val=sal[rowcount].value;
	*/
	else if(uld[rowcount].value>0.0)
		val=uld[rowcount].value;
	else if(sv[rowcount].value>0.0)
		val=sv[rowcount].value;

	var ans=val*exgrate[rowcount].value;

	//targetFormName.totalAmount[rowcount].value=ans;
	
	var operation = new MoneyFldOperations();
	var currencyCode = document.getElementById('blgCurCode').value;
	var carAmtVal=val;		
	var excrate=exgrate[rowcount].value;
	if(excrate!="."){
	
	operation.multiplyRawMonies(carAmtVal,excrate,currencyCode,'false','onMultiplyAdjAmt');
	
	}
	//operation.multiplyRawMonies(carAmtVal,excrate,currencyCode,'false','onAmtChange');
	
	/*
	
	
	
	}
	
	
	
	
	
	
	*/
	onAmtChange();
	//operation.multiplyRawMonies(val,exgrate[rowcount].value,
	//targetFormName.blgCurCode.value,'false','onMultiplyAdjAmt');


}

function onMultiplyAdjAmt(){
////////alert("----");
		setMoneyValue(targetFormName.totalAmount[targetFormName.rowCount.value],arguments[0]);
		change(targetFormName.rowCount.value);
	}



function onAmtChange(){
	var netamt = 0;
	var operationFlag = document.getElementsByName('operationFlag');
	var amt=document.getElementsByName("totalAmount");
	////////alert(amt);
	for(var rowCount=0;rowCount<amt.length;rowCount++) {
		if(operationFlag[rowCount].value!="D" && operationFlag[rowCount].value!="NOOP"){
			netamt=netamt+Number(amt[rowCount].value);
		}
	}
	document.getElementById("netamt").innerHTML= netamt;
}

var lstPageNum=0;
var dispPageNum=0;
function submitList(strLastPageNum,strDisplayPage){
	lstPageNum= strLastPageNum;
  	dispPageNum = strDisplayPage;

	if("unsaveddata"==targetFormName.screenFlg.value){
			showDialog({msg:'Unsaved data exists. Do you want to continue?',type:4,parentWindow:self});
		 	screenConfirmDialog(targetFormName,'id_2');
	   		screenNonConfirmDialog(targetFormName,'id_2');

	 }else{
		 	targetFormName.lastPageNumber.value= strLastPageNum;
			targetFormName.displayPageNum.value = strDisplayPage;
			submitForm(targetFormName, 'mailtracking.mra.airlinebilling.defaults.capturecn51.list.do');
		  }
}