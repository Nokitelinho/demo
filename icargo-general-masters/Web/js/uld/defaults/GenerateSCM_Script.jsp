<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	//onScreenloadSetHeight();


	with(frm){
		onScreenLoad();

		highlightULDErrors();
		evtHandler.addIDEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.scmAirline.value,'scmAirline','1','scmAirline','',0)",EVT_CLICK);
		evtHandler.addIDEvents("airportlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.scmAirport.value,'scmAirport','1','scmAirport','',0)",EVT_CLICK);
		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btnStockList","onClickStockList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnStockClear","onClickStockClear()",EVT_CLICK);
		//evtHandler.addEvents("btnSave","onClickSave()",EVT_CLICK);
		evtHandler.addEvents("btnStockCheck","onClickFinalizeStockCheck()",EVT_CLICK);
		evtHandler.addEvents("btnSaveASDraft","onClickSaveASDraft()",EVT_CLICK);
		evtHandler.addEvents("btnSendSCM","onClickSave()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
		evtHandler.addEvents("btnMark","onMarkAsMissing()",EVT_CLICK);
		evtHandler.addEvents("btnSighted","onMarkAsSighted()",EVT_CLICK);
		evtHandler.addEvents("btnReturn","returnToStock()",EVT_CLICK);
		

		//evtHandler.addEvents("facilityType","defaultCombo();",EVT_CHANGE);
		//alert(targetFormName.tempFacilityType.value);
		evtHandler.addEvents("locationlov","invokeLov(this,'locationlov',targetFormName.elements.tempOperationFlag.value)",EVT_CLICK);

		if(frm.elements.selectedSysStock!=null){
			evtHandler.addEvents("selectedSysStock","selectedSysStock(targetFormName)",EVT_CLICK);
		}
		evtHandler.addEvents("checkSysStockAll","checkSysStockAll(targetFormName)",EVT_CLICK);

		if(frm.elements.selectedAddSysStock!=null){
					evtHandler.addEvents("selectedAddSysStock","selectedAddSysStock(targetFormName)",EVT_CLICK);
		}
		evtHandler.addEvents("checkSysAddStockAll","checkAddSysStockAll(targetFormName)",EVT_CLICK);

	}
	if(targetFormName.elements.listStatus.value!="Y" && (!targetFormName.elements.scmAirport.disabled)){
			targetFormName.elements.scmAirline.focus();
		}else if(!targetFormName.elements.checkSysStockAll.disabled) {
						targetFormName.elements.checkSysStockAll.focus();
		}
}





function onScreenloadSetHeight(){
 	jquery('#div1').height((((document.body.clientHeight)*0.79)-200));
	jquery('#div2').height((((document.body.clientHeight)*0.82)-200));
	
}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				if(!frm.elements.scmAirport.disabled 
					&& frm.elements.listStatus.value != 'Y'){
					frm.elements.scmAirport.focus();
				}
				else{
					 if(!targetFormName.elements.checkSysStockAll.disabled) {
						targetFormName.elements.checkSysStockAll.focus();
					}
				}				
	}
}

function selectedSysStock(frm){
	toggleTableHeaderCheckbox('selectedSysStock', frm.elements.checkSysStockAll);
}

function checkSysStockAll(frm){
	updateHeaderCheckBox(frm, frm.elements.checkSysStockAll, frm.elements.selectedSysStock);
}

function selectedAddSysStock(frm){
	toggleTableHeaderCheckbox('selectedAddSysStock', frm.elements.checkSysAddStockAll);
}

function checkAddSysStockAll(frm){
	updateHeaderCheckBox(frm, frm.elements.checkSysAddStockAll, frm.elements.selectedAddSysStock);
}



function onScreenLoad(){
//defaultCombo();
var addLink=document.getElementById('addUld');
var deleteLink=document.getElementById('deleteUld');
targetFormName.elements.scmStockCheckTime.focus();
disableLink(addLink);
disableLink(deleteLink);
//added by A-6344 for ICRD-55460 start
disableField(targetFormName.elements.btnSighted);
disableField(targetFormName.elements.btnSaveASDraft);
disableField(targetFormName.elements.btnStockCheck);
disableField(targetFormName.elements.btnSendSCM);
disableField(targetFormName.elements.btnStockClear);
disableField(targetFormName.elements.btnStockList);
disableField(targetFormName.elements.uldNumberStock);
disableField(targetFormName.elements.btnMark);
disableField(targetFormName.elements.btnReturn);
//added by A-6344 for ICRD-55460 end
var fac = document.getElementsByName("facilityType");
	for( var i =0; i < fac.length;i++){
		obj = fac[i];
		for (var j = 0; j < obj.length; j++) {
			obj.options[j].defaultSelected = obj.options[j].selected;
		}	
	}
//added by a-3278 for bug 32226 on 08Jan09 ends
//added by a-3045 for BUG_5750 starts

//Commented By A-6841 for ICRD-185395 
/*if(targetFormName.elements.scmAirline.value != ""){
	targetFormName.elements.scmAirline.readOnly=true;
	document.getElementById("airlinelov").disabled=true;
}*/
//added by a-3045 for BUG_5750 ends

if(targetFormName.elements.msgFlag.value=="TRUE"){
	//openPopUp("msgbroker.message.resendmessages.do?openPopUpFlg=ULDSCM",800,425);
	//showDialog("Message Sent", 2, self);
	targetFormName.elements.msgFlag.value="FALSE";
}
if(targetFormName.elements.airportDisable.value=="GHA"){
	targetFormName.elements.scmAirport.disabled = true;
	//targetFormName.airportlov.disabled = true;
}else{
	targetFormName.elements.scmAirport.disabled = false;
	
	
	document.getElementById("airportlov").disabled=false;
}
 
if(targetFormName.elements.listStatus.value=="Y"){

	enableField(targetFormName.elements.uldNumberStock);	
	enableField(targetFormName.elements.btnStockClear);	
	enableField(targetFormName.elements.btnStockList);	
	//Commented By A-6841 for ICRD-185395 
	/*if(targetFormName.elements.scmAirline.value != ""){
	targetFormName.elements.scmAirline.readOnly=true;
	}*/
if(targetFormName.elements.scmAirport.value != ""){
	targetFormName.elements.scmAirport.readOnly=true;
	document.getElementById("airportlov").disabled=true;
}
	targetFormName.elements.scmStockCheckTime.readOnly=true;
	targetFormName.elements.scmStockCheckDate.readOnly=true;
	targetFormName.elements.btn_scmStockCheckDate.disabled=true;
	enableLink(addLink);
	enableLink(deleteLink);
	enableField(targetFormName.elements.btnStockCheck);	
	enableField(targetFormName.elements.btnSendSCM);	
	enableField(targetFormName.elements.btnSighted);	
	enableField(targetFormName.elements.btnSaveASDraft);	
	enableField(targetFormName.elements.btnMark);	
	enableField(targetFormName.elements.btnReturn);	

	}
	if(targetFormName.elements.pageURL.value=="LISTULD"){
	
		disableField(targetFormName.elements.btnList);
		disableField(targetFormName.elements.btnClear);
	}
	document.getElementById("ignoreHiddenCheck").value = "Y";
addUldEvent();
}

function onClickList(){
	targetFormName.elements.listFromStock.value="N";
	targetFormName.elements.displayPage.value = 1;
	targetFormName.elements.lastPageNum.value= 0;
	targetFormName.elements.totalRecords.value = -1;
	submitForm(targetFormName,"uld.defaults.messaging.listgeneratescm.do?"+mapColumns(targetFormName));
}

function addUldEvent() {
	jQuery('#generateSCMMainContainer').on('keypress',  '#CMB_ULD_DEFAULTS_GENERATESCM_ULDNO', function(event) {
		 var regex = new RegExp("^[a-zA-Z0-9%*]+$");
		var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		if (!regex.test(key)) {
			event.preventDefault();
			return false;
		}
	});
}

function onClickStockList(){
	targetFormName.elements.listFromStock.value="Y";
	targetFormName.elements.displayPage.value = 1;
	targetFormName.elements.lastPageNum.value= 0;
	targetFormName.elements.totalRecords.value = -1;
	recreateTableForDetails('uld.defaults.messaging.listgeneratescmstock.do',"div1",mapColumns(targetFormName));
	//submitForm(targetFormName,"uld.defaults.messaging.listgeneratescmstock.do");
}
function submitList(strLastPageNum,strDisplayPage){
  
       /*if(targetFormName.changeStatusFlag.value) {
	 
	   var cnfrm=self.showWarningDialog("Unsaved data exists. Do you want to continue ?");
	     if ( cnfrm==false ){		  
		       document.body.style.cursor = 'default';
		    }else{		
	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName,'uld.defaults.messaging.listgeneratescm.do');
			      }
				  }else{*/
			
				  targetFormName.elements.lastPageNum.value= strLastPageNum;
				targetFormName.elements.displayPage.value = strDisplayPage;
				//submitForm(targetFormName,'uld.defaults.messaging.listgeneratescm.do');
				recreateTableForDetails('uld.defaults.messaging.listgeneratescmstock.do',"div1",mapColumns(targetFormName));
				
				  //}                
}
function onClickClear(){
	//submitForm(targetFormName,"uld.defaults.messaging.cleargeneratescm.do");
	submitFormWithUnsaveCheck('uld.defaults.messaging.cleargeneratescm.do');
}
function onClickStockClear(){
	targetFormName.elements.listFromStock.value="N";
	submitForm(targetFormName,'uld.defaults.messaging.stockcleargeneratescm.do');
}
function onClickSave(){
	//alert(targetFormName.location[0].value);
	//alert(targetFormName.location[1].value);
	targetFormName.elements.fromFinalized.value ="N";
	targetFormName.dynamicQuery.value=targetFormName.elements.listedDynamicQuery.value;
	if(validateFormFields(targetFormName) ) {
	//submitForm(targetFormName,"uld.defaults.messaging.sendgeneratescm.do");  //commented for ICRD-53926
	//Added by A-5258 for ICRD-53926 starts
	var frm=targetFormName;
	if(targetFormName.elements.listedDynamicQuery.value!=null && targetFormName.elements.listedDynamicQuery.value.trim().length>0 ){
			showDialog({msg:"<common:message bundle='generatescmResources' key='uld.defaults.messaging.ScmMessageFilterWarning' />", type:4, parentWindow:self, parentForm:frm, dialogId:'id_16',
			onClose: function (result) {
			if(frm.elements.currentDialogOption.value == 'Y') {
				screenConfirmDialog(frm,'id_16');
			}else if(frm.elements.currentDialogOption.value == 'N') {
				screenNonConfirmDialog(frm,'id_16');
			}
	}});
	}else{
	showDialog({msg:"<common:message bundle='generatescmResources' key='uld.defaults.messaging.additionaladdresseswarning' />", type:4, parentWindow:self, parentForm:frm, dialogId:'id_12',
	onClose: function (result) {
		if(frm.elements.currentDialogOption.value == 'Y') {
			screenConfirmDialog(frm,'id_12');
		}else if(frm.elements.currentDialogOption.value == 'N') {
	screenNonConfirmDialog(frm,'id_12');
		}
	}});
	}
	
}
}

function onClickFinalizeStockCheck(){
	targetFormName.elements.fromFinalized.value ="Y";
	submitForm(targetFormName,'uld.defaults.messaging.sendgeneratescm.do');
}
function onClickSaveASDraft(){
	if(validateFormFields(targetFormName) ) {
	var frm=targetFormName;
	submitForm(targetFormName,"uld.defaults.messaging.SaveAsDraftgeneratescm.do");
	
}
}
function onClickClose(){
	var pageURL=targetFormName.elements.pageURL.value;
	if(pageURL == "LISTULD" || pageURL == "LISTULDS"){
		submitForm(targetFormName,"uld.defaults.listuld.do?listStatus=fromSCM");
	}else{
	submitForm(targetFormName,"uld.defaults.closeaction.do");
	}
}

function addExtraUld(){

	var frm=targetFormName;
	if(validateFormFields(frm) ) {
		//submitForm(frm,'uld.defaults.messaging.addgeneratescm.do');
		addTemplateRow('scmTemplateRow','scmTableBody','newOperationFlag');
		//targetFormName.btnSave.disabled=false;
	}
}


function defaultCombo(){
	var frm = targetFormName;
	var comboValue = frm.elements.facilityType.value;
	frm.elements.defaultComboValue.value = comboValue;


}

function populateLocationLov(lovValue){

	var comboValue = lovValue;
	var scmAirport = targetFormName.elements.scmAirport.value;
	var facCodeArray=document.getElementsByName('newFacilityType');
	 var facCodeValue="";
	 facCodeValue=facCodeArray[comboValue].value;

	var strAction = "uld.defaults.listulddiscrepancies.screenloadlocationlov.do?defaultComboValue="+facCodeValue+"&reportingStationChild="+scmAirport+"&index="+lovValue+"&locationName="+"newLocations";
	openPopUp(strAction,"650", "450");

}

function invokeLov(obj,name,value){
   var index = obj.id.split(name)[1];
   var scmAirport = targetFormName.elements.scmAirport.value;
   var facCodeValue="";
   var facCodeArray=document.getElementsByName('tempOperationFlag');
   var facCodeArraytb=document.getElementsByName('newFacilityType');
   var count = 0;
   var indexvalue = index;
   var arrayLenght= facCodeArray.length;
   facCodeValue=facCodeArraytb[index].value;
 if(name == "locationlov"){
	var strAction = "uld.defaults.listulddiscrepancies.screenloadlocationlov.do?defaultComboValue="+facCodeValue+"&reportingStationChild="+scmAirport+"&index="+index+"&locationName="+"newLocations";
	openPopUp(strAction,"650", "450");
   }
}

function validateFormFields(frm) {
      if(!validateULDNo(frm)) {
     		showDialog({msg:"ULD No. cannot be blank", type:1, parentWindow:self, parentForm:frm, dialogId:'id_2'});
     		return false;
     	}
     if(!validateLocation(frm)) {
     		showDialog({msg:"Location cannot be blank", type:1, parentWindow:self, parentForm:frm, dialogId:'id_3'});
     		return false;
     	}
     		return true;
     	}


function validateULDNo(frm) {
	var extrauld = eval(frm.elements.extrauld);
	var opflags = eval(frm.elements.operationFlag);
	var newUlds=eval(frm.elements.newuld);
	var scmflgs=eval(frm.elements.newOperationFlag);
	if(newUlds != null && newUlds.length>0) {
 		var size = newUlds.length;
 		if(size > 1) {
 			for(var i=0; i<size-1; i++) {
				//alert(scmflgs[i].value);
				//alert(newUlds[i].value);
				if(newUlds[i].value.trim().length == 0 && "I"==scmflgs[i].value) {
 					//alert(opflags[i].value)
					return false;
 				}
 			}
 		}
 	}
 	if(extrauld != null) {
 		var size = extrauld.length;
 		if(size > 1) {

 			for(var i=0; i<size-1; i++) {

				if(extrauld[i].value.trim().length == 0 && "I"==opflags[i].value) {
 					return false;
 				}

 			}
 		}
 	}
 	return true;
 }

 
 function validateLocation(frm) {

	var extrauld = eval(frm.elements.locations);
	var opflags = eval(frm.elements.operationFlag);
	if(extrauld != null) {
 		var size = extrauld.length;
		if(size > 1) {

 			for(var i=0; i<size-1; i++) {
					if(extrauld[i].value.trim().length == 0 && "I"==opflags[i].value) {
 					return false;
 				}

 			}
 		}
 	}
 	return true;
 }

function onClickDelete(){
	var frm=targetFormName;
	if(validateSelectedCheckBoxes(frm,'selectedAddSysStock',1000000,1)){

	/*var checked = frm.elements.selectedAddSysStock;

		if(checked.value==0 && checked.checked == true && frm.elements.scmStatusFlags.value == "S"){
		showDialog('ULD(s) from System Stock cannot be deleted', 1, self, frm, 'id_1');
		//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
		return;
		}

		if(checked.value==0 && checked.checked == true && frm.elements.scmStatusFlags.value == "M"){
				showDialog('ULD(s) from System Stock cannot be deleted', 1, self, frm, 'id_1');
				//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
				return;
		}


var length= checked.length;

		if(length>1){
		for(var i=0;i<length;i++){

		if(checked[i].checked == true && frm.elementsscmStatusFlags[i].value == "S"){
		showDialog('ULD(s) from System Stock cannot be deleted', 1, self, frm, 'id_1');
		//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
		return;
		}
		if(checked[i].checked == true && frm.elementsscmStatusFlags[i].value == "M"){
			showDialog('ULD(s) from System Stock cannot be deleted', 1, self, frm, 'id_1');
			//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
			return;
				}


		}
		}
		else if(length==1){
		if(checked[0].checked == true && frm.elementsstatusvalues[0].value == "S"){
		showDialog('ULD(s) from System Stock cannot be deleted', 1, self, frm, 'id_1');
		//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
		return;
		}

		if(checked[0].checked == true && frm.elementsstatusvalues[0].value == "M"){
				showDialog('ULD(s) from System Stock cannot be deleted', 1, self, frm, 'id_1');
				//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
				return;
		}

			}*/

	showDialog({msg:'Do you want to delete the selected ulds?', type:4, parentWindow:self, parentForm:frm, dialogId:'id_10',
						onClose: function (result) {
						
							if(frm.elements.currentDialogOption.value == 'Y') {
	screenConfirmDialog(frm,'id_10');
							}else if(frm.elements.currentDialogOption.value == 'N') {
	screenNonConfirmDialog(frm,'id_10');
	}
						}});
	}
 }


function onMarkAsSighted(){
	var frm=targetFormName;
		if(validateFormFields(frm) ) {
		if(validateSelectedCheckBoxes(frm,'selectedSysStock',1000000,1)){
			var checked = frm.elements.selectedSysStock;
			var length= checked.length;
			if(length>1){
				for(var i=0;i<length;i++){
					if(checked[i].checked == true && frm.elements.scmStatusFlags[i].value == "F"){
						showDialog({msg:'ULD(s) already in Sighted status', type:1, parentWindow:self, parentForm:frm, dialogId:'id_8'});
						return;
					}
				}
			}
			showDialog({msg:'Do you want to mark the selected ULD(s) as Sighted?',type:4,parentWindow:self, parentForm:frm, dialogId:'id_15',
				onClose:function(result){
			screenConfirmDialog(frm,'id_15');
			screenNonConfirmDialog(frm,'id_15');
			}
			});
			
			}
		}

	 }

 function onMarkAsMissing(){
	var frm=targetFormName;
		if(validateFormFields(frm) ) {
		if(validateSelectedCheckBoxes(frm,'selectedSysStock',1000000,1)){

		var checked = frm.elements.selectedSysStock;

			/*if(checked.value==0 && checked.checked == true && "F" == frm.elementsscmStatusFlags.value ){

			showDialog('Select ULD(s) from System Stock only', 1, self, frm, 'id_11');
			//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
			return;
			}else*/ if(checked.value==0 && checked.checked == true && frm.elements.scmStatusFlags.value == "M"){
					showDialog({msg:'ULD(s) already in missing status', type:1, parentWindow:self, parentForm:frm, dialogId:'id_8'});
					//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
					return;
				}

			var length= checked.length;
			if(length>1){
			for(var i=0;i<length;i++){
				
			
				/*if(checked[i].checked == true && "F" == frm.elementsscmStatusFlags[i].value   && "NOOP" != frm.elementsoperationFlag[i].value){

			showDialog('Select ULD(s) from System Stock only', 1, self, frm, 'id_11');
				//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
			return;
			}else */ if(checked[i].checked == true && frm.elements.scmStatusFlags[i].value == "M"){
				showDialog({msg:'ULD(s) already in missing status', type:1, parentWindow:self, parentForm:frm, dialogId:'id_8'});
				//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
				return;
				}

			}
			}
			else if(length==1){
			/*if(checked[0].checked == true && "F" == frm.elementsstatusvalues[0].value  ){
			showDialog('Select ULD(s) from System Stock only', 1, self, frm, '1d_11');
			//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
			return;
			}else*/ if(checked[0].checked == true && "M" == frm.elements.statusvalues[0].value){
				showDialog({msg:'ULD(s) already in missing status', type:1, parentWindow:self, parentForm:frm, dialogId:'id_8'});
				//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
				return;
				}



				}
		showDialog({msg:'Do you want to mark the selected ULD(s) as missing?',type:4,parentWindow:self, parentForm:frm, dialogId:'id_4',
			onClose:function(result){
		screenConfirmDialog(frm,'id_4');
		screenNonConfirmDialog(frm,'id_4');
	}
		});
		
		
	}
}

	 }

	 function returnToStock(){
		var frm=targetFormName;
		if(validateFormFields(frm) ) {
		if(validateSelectedCheckBoxes(frm,'selectedSysStock',1000000,1)){

		var checked = frm.elements.selectedSysStock;

			/*if(checked.value==0 && checked.checked == true && frm.elementsscmStatusFlags.value == "F"){
			showDialog('Select ULD(s) having Missing status ', 1, self, frm, 'id_5');
			//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
			return;

			}else*/ if(checked.value==0 && checked.checked == true && frm.elements.scmStatusFlags.value == "S"){

					showDialog({msg:'ULD(s) already in System Stock', type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
					//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
					return;
				}

			var length= checked.length;
			if(length>1){
			for(var i=0;i<length;i++){

			/*if(checked[i].checked == true && frm.elementsscmStatusFlags[i].value == "F" && frm.elementsoperationFlag[i].value!="NOOP"){
			showDialog('Select ULD(s) from System Stock only', 1, self, frm, 'id_5');
			//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
			return;
			}else*/ if(checked[i].checked == true && frm.elements.scmStatusFlags[i].value == "S"){
				showDialog({msg:'ULD(s) already in System Stock', type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});

				//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
					return;
				}

			}
			}
			else if(length==1){
			/*if(checked[0].checked == true && frm.elementsstatusvalues[0].value == "F"){
			showDialog({msg:'Select ULD(s) from System Stock only', 1, self, frm, 'id_5');
			//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
			return;
			}else*/ if(checked[0].checked == true && frm.elements.statusvalues[0].value == "S"){
				showDialog({msg:'ULD(s) already in System Stock', type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
			//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
				return;
				}

				}
			showDialog({msg:'Do you want to return the selected ULD(s) to System stock?',type:4,parentWindow:self, parentForm:frm, dialogId:'id_6',
				onClose:function(result){
		screenConfirmDialog(frm,'id_6');
		screenNonConfirmDialog(frm,'id_6');
				}
			});
	}
}

		 }

 function screenConfirmDialog(frm, dialogId) {


	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {


 		if(dialogId == 'id_10'){
		
			deleteTableRow('selectedAddSysStock','newOperationFlag');
 			//submitForm(frm,"uld.defaults.messaging.deletegeneratescm.do");
 		}

 		if(dialogId == 'id_4'){


		 	submitForm(frm,"uld.defaults.messaging.markmissingulds.do?missingFlag=Y");
 		}

 		if(dialogId == 'id_6'){

		submitForm(frm,"uld.defaults.messaging.markmissingulds.do?missingFlag=N");
 		}
		if(dialogId == 'id_15'){
			submitForm(frm,"uld.defaults.messaging.markmissingulds.do?missingFlag=S");
 		}
		
		if(dialogId == 'id_12'){
		
		openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=SCM&targetAction=uld.defaults.messaging.sendgeneratescm.do",1050,320);
		
 		}
		if(dialogId == 'id_16'){
			showDialog({msg:"<common:message bundle='generatescmResources' key='uld.defaults.messaging.additionaladdresseswarning' />", type:4, parentWindow:self, parentForm:frm, dialogId:'id_12',
			onClose: function (result) {
				if(frm.elements.currentDialogOption.value == 'Y') {
					screenConfirmDialog(frm,'id_12');
				}else if(frm.elements.currentDialogOption.value == 'N') {
					screenNonConfirmDialog(frm,'id_12');
				}
			}});
 		}
		
}
}

function screenNonConfirmDialog(frm, dialogId) {


 		if(dialogId == 'id_2'){
		//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
		return;
 		}

 		if(dialogId == 'id_4'){
			//	submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
				return;
 		}

 		if(dialogId == 'id_6'){
						//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
						return;
 		}
		if(dialogId == 'id_15'){
		//submitForm(frm,"uld.defaults.messaging.markmissingulds.do?missingFlag=N");
			return;
		}
		if(dialogId == 'id_3'){
						//submitForm(targetFormName,"uld.defaults.messsaging.refreshscmulddetails.do");
						return;
 		}
		if(frm.elements.currentDialogOption.value == 'N') {
			if(dialogId == 'id_12'){
				submitForm(targetFormName,"uld.defaults.messaging.sendgeneratescm.do");
				}
 		}
		if(dialogId == 'id_16'){
				return;
		}
}

function recreateTableForDetails(strAction,divId1,test){
    jquery('#'+divId1).html('<table style="width:100%;height:100%;"><tr><td width="47%"></td><td width="53%"><img src="<%=request.getContextPath()%>/images/ajaxloader.gif" alt="Processing...."/><br>Loading....</br></td></tr></table>')
   	var __extraFn="updateTableCode";
	_refreshurl = 'operations.shipment.exportshipmentenquirylist.do';
	strAction=strAction+'?'+test;
   	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId1);
   }
   function updateTableCode(_tableInfo){
   	var _str=getActualData(_tableInfo);
	document.getElementById(_tableInfo.currDivId).innerHTML=_str;
	document.getElementById("_paginationResultsLabel").innerHTML=_tableInfo.document.getElementById('_paginationResultsLabel').innerHTML
	document.getElementById("_paginationLink").innerHTML=_tableInfo.document.getElementById('_paginationLink').innerHTML
    targetFormName.elements.listedDynamicQuery.value=_tableInfo.document.getElementById('_listedDynamicQuery').innerHTML
	highlightULDErrors();
  }
    function getActualData(_tableInfo){
      	if(_tableInfo.currDivId=="div1"){
   		_frm=_tableInfo.document.getElementsByTagName("table")[0];
   		return _frm.outerHTML;
   	}else{
   		/*_frm=_tableInfo.document.getElementsByTagName("table")[1];
   		return _frm.outerHTML;*/
   	}
   }
function highlightULDErrors(){

var statusFlags=document.getElementsByName('scmStatusFlags');

if(statusFlags != null){

	for(var i=0;i<statusFlags.length;i++){

		if(statusFlags[i].value != null && statusFlags[i].value=='M'){
			var currentUldNumber = document.getElementById('extrauld'+i);
			currentUldNumber.style.color = "red";
			currentUldNumber.style.fontWeight = "bold";
		}
		if(statusFlags[i].value != null && statusFlags[i].value=='F'){
					var currentUldNumber = document.getElementById('extrauld'+i);
					currentUldNumber.style.color = "green";
					currentUldNumber.style.fontWeight = "bold";
			}
		}
	}

	}
function confirmMessage() {
	targetFormName.elements.listFromStock.value="N";
	targetFormName.elements.displayPage.value = 1;
	targetFormName.elements.lastPageNum.value= 0;
	targetFormName.elements.totalRecords.value = -1;
	submitForm(targetFormName,"uld.defaults.messaging.listgeneratescm.do?"+mapColumns(targetFormName));
}
function nonconfirmMessage() {
	submitFormWithUnsaveCheck('uld.defaults.messaging.cleargeneratescm.do');
	}
