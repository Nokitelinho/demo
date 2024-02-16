<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnSave","save(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose(this.form)",EVT_CLICK);
		//evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
		evtHandler.addEvents("btnDispTot","total(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnPrint","print()",EVT_CLICK);   
		evtHandler.addEvents("btnAddPic","picFocus()",EVT_CLICK);
		evtHandler.addEvents("btnaddDamage","add_Damage()",EVT_CLICK);	
		evtHandler.addEvents("btnaddRepair","add_Repair()",EVT_CLICK);
		evtHandler.addEvents("invRep","validateMaximumLength(this,100,'Investigation Report','Maximum length',true)",EVT_BLUR);
		//Added by A-3415 for ICRD-113953
		evtHandler.addEvents("damageStatus","damageStatusChange()",EVT_CHANGE);
		evtHandler.addEvents("dmgRepairRefNo","closeSatus(this)",EVT_CHANGE);
		onScreenLoad();
	}
	onLoad();
}

function onScreenLoad(){
var frm=targetFormName;
var statusFlag = frm.elements.statusFlag.value;
if(statusFlag == "action_mainsave"){
	setTimeout(function() {
	submitForm(frm,'uld.defaults.ux.listulddmgdetails.do')
	}, 600);
}
}

function openImgUploadPopUp(frm){
	var imageIndex = frm.getAttribute('id').split('_')[1];
	console.log('caught');
	var frm=targetFormName;
	openPopUp(appPath+'/uld.defaults.ux.damageImageUploadScreenLoad.do?imageIndex='+imageIndex,800,500);
}

function resetFocus(frm){
var screenStatus = targetFormName.elements.screenStatusValue.value;
	 if(!event.shiftKey){ 
				if((!frm.elements.uldNumber.disabled) 
					&& screenStatus != "LIST" && screenStatus != "DMGPRESENTLIST" 
					&& screenStatus != "DMGNOTPRESENT" && screenStatus != "REPPRESENT"
					&& screenStatus != "REPNOTPRESENT" && frm.pageURL.value !="LISTDAMAGEREPORT" ){
					frm.elements.uldNumber.focus();
				}
				else{
					 if(document.getElementById('addDmgLink').disabled == false) {
						frm.document.getElementById('addDmgLink').focus();
						}
				}				
	}	
}

function print(){
generateReport(targetFormName,'/uld.defaults.ux.maintaindamageprint.do');
}

function add_Damage(){
	var frm=targetFormName;
	changeToDamagedStatus();
	changeToNonOperationalStatus();
    addTemplateRow('addDamageDetailTemplateRow','damageDetailsTableBody','tempOperationFlag');
}  

function changeToDamagedStatus(){
	document.getElementById('CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DMGSTA').getElementsByTagName('option')[2].selected = 'selected';
}

function changeToNonOperationalStatus(){
	document.getElementById('CMB_ULD_DEFAULTS_UX_MAINTAINDMG_OVRSTA').getElementsByTagName('option')[2].selected = 'selected';
}

function checkDamageCount(frm){
	var totalCount = 0;
	const damageRows = targetFormName.elements.tempOperationFlag;
	for(let i = 0; i < damageRows.length; i++){
		if(damageRows[i].defaultValue == 'I'){
			totalCount++;
		}
	}
	if(totalCount==0){
	document.getElementById('CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DMGSTA').getElementsByTagName('option')[4].selected = 'selected';
	document.getElementById('CMB_ULD_DEFAULTS_UX_MAINTAINDMG_OVRSTA').getElementsByTagName('option')[3].selected = 'selected';
	}
}



function showReportedAirport(obj){
	var index = obj.id.split("repStnlov_")[1];
	var optionsArray = {
		mainActionUrl:'ux.showAirport.do',
		isMultiSelect:'N',
		isPageable:'Y',
		paginationActionUrl:'ux.showAirport.do',
		lovTitle:'Select Airport',
		codeFldNameInScrn:'repStn',
		descriptionFldNameInScrn:'',
		index:index,
		closeButtonIds:['btnOk','btnClose'],
		additionalOptions:'',
		mode:'',
		parentOkFnHook:'',
		lovOkFnHook:'',
		parentCloseFnHook:'',
		lovCloseFnHook:'',
		dialogWidth:'600',
		dialogHeight:'500',
		fieldToFocus:'repStn',
		lovIconId:'',
		maxlength:''};	
	
	lovUtils.displayLOV(optionsArray);	
}
function showRepairAirport(obj){
	var index = obj.id.split("repairStnlov_")[1];
	var optionsArray = {
		mainActionUrl:'ux.showAirport.do',
		isMultiSelect:'N',
		isPageable:'Y',
		paginationActionUrl:'ux.showAirport.do',
		lovTitle:'Select Airport',
		codeFldNameInScrn:'repairStn',
		descriptionFldNameInScrn:'',
		index:index,
		closeButtonIds:['btnOk','btnClose'],
		additionalOptions:'',
		mode:'',
		parentOkFnHook:'',
		lovOkFnHook:'',
		parentCloseFnHook:'',
		lovCloseFnHook:'',
		dialogWidth:'600',
		dialogHeight:'500',
		fieldToFocus:'repairStn',
		lovIconId:'',
		maxlength:''};	
	
	lovUtils.displayLOV(optionsArray);	
}

function showCurrencyLov(lovName) {
	var index = lovName.id.split("currencylov_")[1];
	var dialogConfig = {
		mainActionUrl: 'ux.showCurrency.do',
		isMultiSelect: 'N',
		isPageable: 'Y',
		paginationActionUrl: 'ux.showCurrency.do',
		lovTitle: '<common:message bundle="lovResources" key="icargo.shared.currency.pagetitle"	scope="request" />',
		codeFldNameInScrn: 'currency',
		descriptionFldNameInScrn: '',
		index: index,
		closeButtonIds: ['CMP_Shared_Currency_UX_CurrencyLov_Close', 'btnOk'],
		dialogWidth: 600,
		dialogHeight: 500,
		fieldToFocus: 'currency',
		lovIconId: ''
	};
	lovUtils.displayLOV(dialogConfig);
}

 function viewPic(seqno){
        targetFormName.elements.seqNum.value=seqno;
	var newWindow = openPopUp("uld.defaults.ux.viewpicture.do?seqNum="+seqno,"800","500");
} 


function delDamage(){
	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'selectedDmgRowId', 1000000000000, 1);
	if (check){
	frm.elements.statusFlag.value = "uld_def_del_dmg";
	var statusFlag=frm.elements.statusFlag.value;
	submitForm(frm,'uld.defaults.deldmgdetails.do');}
}

function deleteDamage(obj){
	var elementName ="delete"+obj.id;
	var index = obj.id.split("_")[1];
	targetFormName.selectedDmgRowId[index].checked=true; 
	deleteTableRow('selectedDmgRowId','tempOperationFlag');
/* 
var index = obj.id.split("_")[1];   
if(indexId == null){
targetFormName.selectedDmgRowId[index].checked=true;
deleteTableRow('selectedDmgRowId','operationFlag');

}else{
    targetFormName.elements.setSelectedRepairRowId.value=index;
	var frm=targetFormName;
	submitForm(frm,'uld.defaults.ux.deldmgdetails.do');
} */
}

function updateDamageDetailsTable(url,divId,asyncFunction) {
	asyncSubmit(targetFormName,url,asyncFunction,null,null,divId);
}
function refreshDamageDetailsTable(_refreshInfo) {
	if(_asyncErrorsExist == false) {
		//document.getElementById(_refreshInfo.currDivId).innerHTML=_refreshInfo.getTableData();
        _resultDiv = _refreshInfo.document.getElementById("refreshDamageDetails_Table").innerHTML; 
		document.getElementById(_refreshInfo.currDivId).innerHTML=_resultDiv;
	}
	else if(_asyncErrorsExist == true){
		document.getElementById(_refreshInfo.currDivId).innerHTML= '';
	}
} 


function deleteRepair(indexId,obj){
var index = obj.id.split("_")[1];   
if(indexId == null){
targetFormName.selectedRepRowId[index].checked=true;
deleteTableRow('selectedRepRowId','operationFlag');

}else{
    targetFormName.elements.selectedRepairRowId.value=index;
	var frm=targetFormName;
	submitForm(frm,'uld.defaults.ux.delrepdetails.do');
}
}
function modDamage(){

	var frm=targetFormName;

	var check = validateSelectedCheckBoxes(frm, 'selectedDmgRowId', 1000000000000, 1);
	if (check){
	frm.elements.statusFlag.value = "uld_def_mod_dmg";
	var statusFlag=frm.elements.statusFlag.value;
	//alert(statusFlag);
	var selectedRows = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
		if(targetFormName.elements[i].name =='selectedDmgRowId') {
			if(targetFormName.elements[i].checked) {
				var val = targetFormName.elements[i].value;
				if(selectedRows != "")
					selectedRows = selectedRows+","+val;
				else if(selectedRows == "")
				selectedRows = val;

			}
		}
	}
	//alert(selectedRows);

	 frm.elements.flag.value= selectedRows;
	 var flagdmg=frm.elements.flag.value;
	 var screenStatusValue=frm.elements.screenStatusValue.value;
	//alert(frm.flag.value);

	damageStatus=targetFormName.elements.damageStatus.value;
	overallStatus=targetFormName.elements.overallStatus.value;
	repairStatus=targetFormName.elements.repairStatus.value;
	supervisor=targetFormName.elements.supervisor.value;
	invRep=targetFormName.elements.invRep.value;
	//picture=targetFormName.picture.value;

	//submitForm(frm,'uld.defaults.updatedmg.do');

	<!--  Modified By A-5170 for ICRD-32241 starts -->
	var newWindow = openPopUp(
				"uld.defaults.damageDetailsScreenLoadCommand.do?statusFlag="+statusFlag+"&flag="+flagdmg+"&screenStatusValue="+screenStatusValue+"&damageStatus="+damageStatus+"&overallStatus="+overallStatus+"&repairStatus="+repairStatus+"&supervisor="+supervisor+"&invRep="+invRep,
		"760","420");
	<!--  Modified By A-5170 for ICRD-32241 ends -->
	 }

}
function add_Repair(){
	var frm=targetFormName;
	 if(targetFormName.elements.screenStatusValue.value=="SCREENLOAD"  ){
	 return;
	 }
	addTemplateRow('addRepairDetailTemplateRow','repairDetailsTableBody','tempRepairOpFlag');	
	//Added by A-7390 for ICRD-234401
	checkForSurveyorFieldEnabling();
}
function delRepair(){
	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'selectedRepRowId', 1000000000000, 1);
	if (check){
	frm.elements.statusFlag.value = "uld_def_del_rep";

	var statusFlag=frm.elements.statusFlag.value;
	//alert(statusFlag);
	submitForm(frm,'uld.defaults.delrepdetails.do');}
}
function modRepair(){
	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'selectedRepRowId', 1000000000000, 1);
	if (check){
	frm.elements.statusFlag.value = "uld_def_mod_rep";
	var statusFlag=frm.elements.statusFlag.value;
	//alert(statusFlag);
	var selectedRows = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
		if(targetFormName.elements[i].name =='selectedRepRowId') {
			if(targetFormName.elements[i].checked) {
				var val = targetFormName.elements[i].value;
				if(selectedRows != "")
					selectedRows = selectedRows+","+val;
				else if(selectedRows == "")
				selectedRows = val;

			}
		}
	}
	//alert(selectedRows);

	 frm.elements.flag.value= selectedRows;
	 var flagrep=frm.elements.flag.value;
	//alert(frm.flag.value);
	var screenStatusValue=frm.elements.screenStatusValue.value;

	damageStatus=targetFormName.elements.damageStatus.value;
	overallStatus=targetFormName.elements.overallStatus.value;
	repairStatus=targetFormName.elements.repairStatus.value;
	supervisor=targetFormName.elements.supervisor.value;
	invRep=targetFormName.elements.invRep.value;
	//picture=targetFormName.picture.value;

	//submitForm(frm,'uld.defaults.updatedmg.do');

	var newWindow = openPopUp(
				"uld.defaults.repairDetailsScreenLoadCommand.do?statusFlag="+statusFlag+"&flag="+flagrep+"&screenStatusValue="+screenStatusValue+"&damageStatus="+damageStatus+"&overallStatus="+overallStatus+"&repairStatus="+repairStatus+"&supervisor="+supervisor+"&invRep="+invRep,
		"650","280");}


}


function list(frm){
	submitForm(frm,'uld.defaults.ux.listulddmgdetails.do');
}

function total(frm){
	submitForm(frm,'uld.defaults.ux.totaldmgdetails.do');
}

function clear(frm){
	//submitForm(frm,'uld.defaults.clearulddmgdetails.do');
	//submitFormWithUnsaveCheck('uld.defaults.ux.clearulddmgdetails.do');
	submitForm(frm,'uld.defaults.ux.clearulddmgdetails.do'); //Added by A-7924 as part of ICRD-301426
}

function changeSelected(){

	targetFormName.elements.damageStatusFlag.value="";
	var COMBO_BOX     = "SELECT";
	var FIELD_SET     = "FIELDSET";
	var COMBO_BOX_ONE     = "SELECT-ONE";
	var formObj  = document.forms[document.forms.length - 1];
	var formElementObj = null;
	var elementType = null;
	if(document.forms != null){
		for(var cnt=0; cnt<document.forms.length;cnt++){
		formObj = document.forms[cnt];
			for (var i = 0; i < formObj.elements.length; i++) {
				formElementObj = formObj.elements[i];
				if (formElementObj.tagName != FIELD_SET) {
					elementType = formElementObj.type.toUpperCase();
						if (elementType == COMBO_BOX || elementType == COMBO_BOX_ONE){
							for (var j = 0; j < formElementObj.length; j++) {
									if (formElementObj.options[j].selected == formElementObj.options[j].defaultSelected){
											if(formElementObj.options[j].selected){
												formElementObj.options[j].defaultSelected=false;
											}else{
												formElementObj.options[j].defaultSelected=true;
											}

									}
							}
					}
				}
			}
		}
	}
}

function save(frm){
//Aded by A-7924 as part of ICRD-289219 starts
    for (var j = 0; j < frm.elements.tempOperationFlag.length; j++) {
	if(frm.elements.tempOperationFlag[j].value != 'NOOP' && frm.elements.tempOperationFlag[j].value != 'D') {
		if(frm.elements.facilityType[j].value.trim()!="" && frm.elements.location[j].value.trim()==""){
			showDialog({msg :'Please Enter Location',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
		return;
		}
		if(frm.elements.partyType[j].value.trim()!="" && frm.elements.party[j].value.trim()==""){
			showDialog({msg :'Please Enter party',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
		return;
		}
		if(frm.elements.location[j].value.trim()!="" && frm.elements.facilityType[j].value.trim()==""){
			showDialog({msg :'Please Enter Facility Type',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
		return;
		}
		if(frm.elements.party[j].value.trim()!="" && frm.elements.partyType[j].value.trim()==""){
			showDialog({msg :'Please Enter party Type',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
		return;
		}
		var d = new Date(targetFormName.elements.reportedDate[j].value);
		if(d.getTime()>new Date().getTime())
{
showDialog({	msg		:" Reported date is greater than present date",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
						});	
						return;
}

 //if(d.getTime()>new Date().getTime())
else if(targetFormName.elements.repairDate[j]!=undefined){
	d = new Date(targetFormName.elements.repairDate[j].value);
if(d.getTime()>new Date().getTime()){
	showDialog({	msg		:" Repair date is greater than present date",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
						});	
						return;
}
	
	
	
}
	}
}
//Aded by A-7924 as part of ICRD-289219 ends
	
	
	if(frm.elements.damageStatusFlag.value=="N"){
		changeSelected();
	}
	var screenReloadStatus=targetFormName.elements.screenReloadStatus.value;
	var checkedList = new Array(targetFormName.elements.dmgRefNo.length);
	var closedChecksbox = document.getElementsByName('closed');
	for(var i=0;i<checkedList.length;i++) {
		if(closedChecksbox[i].checked==true) {
			targetFormName.elements.closed[i].checked = true;
		} else if(targetFormName.elements.closed[i]){
			targetFormName.elements.closed[i].checked = false;
		}
	}

	if(isFormModified(targetFormName) || screenReloadStatus=="reload"){
		submitForm(frm,'uld.defaults.ux.saveulddmgdetails.do?fromScreen='+targetFormName.elements.fromScreen.value);
	}else{
	//Modified by A-5116 for ICRD-43240
	//showDialog('"<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.nochanges'/>"', 2, self);
	//showDialog('<common:message bundle="maintainDamageReportResources" key="uld.defaults.err.nochanges"/>', 2, self);
	  showDialog({
					msg:'<common:message bundle="maintainDamageReportResources" key="uld.defaults.err.nochanges"/>',
					type:2,
					parentWindow:self,		
					onClose:function(result){
	
					}
				});
	}
	if(targetFormName.elements.screenMode.value == "uldacceptance" ){
		window.close();
	}
}


function deleteUld(frm){
	submitForm(frm,'uld.defaults.deleteuld.do');
}

function closeUld(frm){
	submitForm(frm,'uld.defaults.closeuld.do');
}

function onLoad(){
		if(targetFormName.elements.screenStatusValue.value=="SCREENLOAD"  ){
		jquery('#damageDtlDiv').hide();
		jquery('.btmbtnpane').hide();

		}
		
		if(targetFormName.elements.screenStatusValue.value=="SCREENLOAD" && targetFormName.elements.screenMode.value== "uldacceptance"  ){
		// addTemplateRow('addDamageDetailTemplateRow','damageDetailsTableBody','tempOperationFlag');
		}
		
		if(targetFormName.elements.screenStatusValue.value=="LIST"  ){
       // addTemplateRow('addDamageDetailTemplateRow','damageDetailsTableBody','tempOperationFlag');
		//alert("LIST");
		var damageStatus=targetFormName.elements.damageStatus.value;
		var overallStatus=targetFormName.elements.overallStatus.value;

		/*if(damageStatus=='N' && overallStatus=='O'){

			var addDmgLink = document.getElementById('addDmgLink');
			addDmgLink.disabled=true;
			var modDmgLink = document.getElementById('modDmgLink');
			modDmgLink.disabled=true;
			var delDmgLink = document.getElementById('delDmgLink');
			delDmgLink.disabled=true;

			targetFormName.btnSave.disabled=true;
			targetFormName.btnAddPic.disabled=true;
			targetFormName.dmgPicture.disabled=true;
		}*/


		var btnaddRepair = document.getElementById('btnaddRepair');
		//addRepLink.disabled=true;
		disableField(btnaddRepair);
		//addRepLink.onclick = function() { return false; }
		var modRepLink = document.getElementById('modRepLink');
		//modRepLink.disabled=true;
		disableLink(modRepLink);
		//modRepLink.onclick = function() { return false; }
		var delRepLink = document.getElementById('delRepLink');
		//delRepLink.disabled=true;
		disableLink(delRepLink);
		//delRepLink.onclick = function() { return false; }



		//targetFormName.uldNumber.disabled=true;
		disableField(targetFormName.elements.uldNumber);
		//targetFormName.btnList.disabled=true;
		disableField(targetFormName.elements.btnList);
		//targetFormName.repairStatus.disabled=true;
		disableField(targetFormName.elements.repairStatus);
		//targetFormName.supervisor.disabled=true;
		disableField(targetFormName.elements.supervisor);
		//targetFormName.invRep.disabled=true;
		disableField(targetFormName.elements.invRep);

		//targetFormName.btnRepInv.disabled=false;
		//targetFormName.btnSave.disabled=false;
		//targetFormName.dmgPicture.disabled=false;
		enableField(targetFormName.elements.dmgPicture);
		//targetFormName.btnAddPic.disabled=false;
		enableField(targetFormName.elements.btnAddPic);
		//targetFormName.btnView.disabled=false;
		//targetFormName.btnDispTot.disabled=true;
		disableField(targetFormName.elements.btnDispTot);
		enableField(targetFormName.elements.btnPrint);
		}


		if(targetFormName.elements.screenStatusValue.value=="DMGPRESENTLIST"  ){
			if(!(targetFormName.elements.statusFlag.value=="TOTAL_SUCESS" || targetFormName.elements.statusFlag.value=="TOTAL_FAILURE")) {
				targetFormName.elements.statusFlag.value="";
		//alert("DMGPRESENTLIST");
			//targetFormName.uldNumber.disabled=true;
			disableField(targetFormName.elements.uldNumber);
			//targetFormName.btnList.disabled=true;
			disableField(targetFormName.elements.btnList);
			//targetFormName.repairStatus.disabled=true;
			disableField(targetFormName.elements.repairStatus);
			//targetFormName.supervisor.disabled=true;
			disableField(targetFormName.elements.supervisor);
			//targetFormName.invRep.disabled=true;
			disableField(targetFormName.elements.invRep);

			//targetFormName.btnRepInv.disabled=false;
			//targetFormName.btnSave.disabled=false;
			//targetFormName.dmgPicture.disabled=false;
			enableField(targetFormName.elements.dmgPicture);
			//targetFormName.btnAddPic.disabled=false;
			enableField(targetFormName.elements.btnAddPic);
			//targetFormName.btnView.disabled=false;
			//targetFormName.btnDispTot.disabled=true;
			disableField(targetFormName.elements.btnDispTot);
		}
		}

		if(targetFormName.elements.screenStatusValue.value=="DMGNOTPRESENT"  ){

			//alert("DMGNOTPRESENT");

			//var addRepLink = document.getElementById('addRepLink');
			//addRepLink.disabled=true;
			disableField(btnaddRepair);
			//addRepLink.onclick = function() { return false; }


			//targetFormName.uldNumber.disabled=true;
			disableField(targetFormName.elements.uldNumber);
			//targetFormName.btnList.disabled=true;
			disableField(targetFormName.elements.btnList);
			//targetFormName.repairStatus.disabled=true;
			disableField(targetFormName.elements.repairStatus);
			//targetFormName.supervisor.disabled=true;
			disableField(targetFormName.elements.supervisor);
			//targetFormName.invRep.disabled=true;
			disableField(targetFormName.elements.invRep);
			//targetFormName.btnDispTot.disabled=true;
			disableField(targetFormName.elements.btnDispTot);
		}
		if(targetFormName.elements.screenStatusValue.value=="REPPRESENT"  ){

			//alert("REPPRESENT");

			//targetFormName.uldNumber.disabled=true;
			disableField(targetFormName.elements.uldNumber);
			//targetFormName.btnList.disabled=true;
            disableField(targetFormName.elements.btnList);
			//targetFormName.repairStatus.disabled=false;
			enableField(targetFormName.elements.repairStatus);
			//targetFormName.supervisor.disabled=false;
			enableField(targetFormName.elements.supervisor);
			//targetFormName.invRep.disabled=false;
			enableField(targetFormName.elements.invRep);
			//targetFormName.btnDispTot.disabled=false;
			enableField(targetFormName.elements.btnDispTot);
		}
		if(targetFormName.elements.screenStatusValue.value=="REPNOTPRESENT"  ){

			//alert("REPPRESENT");

			//targetFormName.uldNumber.disabled=true;
			disableField(targetFormName.elements.uldNumber);
			//targetFormName.btnList.disabled=true;
			disableField(targetFormName.elements.btnList);
			//targetFormName.repairStatus.value="";
			targetFormName.elements.supervisor.value="";
			targetFormName.elements.invRep.value="";

			//targetFormName.repairStatus.disabled=true;
			disableField(targetFormName.elements.repairStatus);
			//targetFormName.supervisor.disabled=true;
			disableField(targetFormName.elements.supervisor);
			//targetFormName.invRep.disabled=true;
			disableField(targetFormName.elements.invRep);
			//targetFormName.btnDispTot.disabled=true;
			disableField(targetFormName.elements.btnDispTot);
		}

		if(targetFormName.elements.pageURL.value=="LISTDAMAGEREPORT"  ){
			//alert("LIST");
			//targetFormName.uldNumber.disabled=true;
			disableField(targetFormName.elements.uldNumber);
			//targetFormName.btnList.disabled=true;
			disableField(targetFormName.elements.btnList);
			//targetFormName.btnClear.disabled=true;
			disableField(targetFormName.elements.btnClear);
		}

		if(!targetFormName.elements.uldNumber.disabled){
			targetFormName.elements.uldNumber.focus();
		}
		else if(targetFormName.elements.screenStatusValue.value!="SCREENLOAD" ){
			// if(document.getElementById('addDmgLink').disabled == false) {
						// targetFormName.document.getElementById('addDmgLink').focus();
						// }
		}

		
		//Modified by A-3415 for ICRD-113953
		if(targetFormName.elements.screenStatusValue.value!="SCREENLOAD"  ){
			enableField(targetFormName.elements.damageStatus);
			damageStatusLoad();
		}

	//if(targetFormName.uldNumber.disabled==false){
	//		targetFormName.uldNumber.focus();
	//}
}

//Added by A-3415 for ICRD-113953
function damageStatusChange() {
	var damageStatus = targetFormName.elements.damageStatus.value;
	if(targetFormName.elements.nonOperationalDamageCodes.value !=null && targetFormName.elements.nonOperationalDamageCodes.value.indexOf(damageStatus)>=0){
		targetFormName.elements.overallStatus.value="N";
		disableField(targetFormName.elements.overallStatus);
	}else{
		targetFormName.elements.overallStatus.value="O";
		enableField(targetFormName.elements.overallStatus);
	}
}
function damageStatusLoad() {
	var damageStatus = targetFormName.elements.damageStatus.value;
	if(targetFormName.elements.nonOperationalDamageCodes.value !=null && targetFormName.elements.nonOperationalDamageCodes.value.indexOf(damageStatus)>=0){
		targetFormName.elements.overallStatus.value="N";
		disableField(targetFormName.elements.overallStatus);
	}else{		
		enableField(targetFormName.elements.overallStatus);
	}
}
function onClickClose(frm){
	//added by saritha
	if(targetFormName.elements.screenMode.value == "uldacceptance" ){
		submitForm(frm,'uld.defaults.ux.closecommand.do');
	}else{
		submitForm(frm,'uld.defaults.ux.closecommand.do');
		// Added by A-3268 as part of 102797
		if(targetFormName.elements.screenMode.value == "popupMode"){
			window.close();
		}
	}
}

function confirmMessage() {

	var frm = targetFormName;
	if(frm.elements.saveStatus.value=="whethertoclose") {
		frm.elements.saveStatus.value="continue";
		submitForm(frm,'uld.defaults.ux.saveulddmgdetails.do?fromScreen='+targetFormName.elements.fromScreen.value);
	}
	else if(frm.elements.saveStatus.value=="whethertosave") {
		frm.elements.saveStatus.value="cansave";
		submitForm(frm,'uld.defaults.closeaction.do');
	}

}

function nonconfirmMessage() {
//if(frm.saveStatus.value=="whethertosave") {
//		frm.saveStatus.value="cansave";
		//submitForm(frm,'uld.defaults.saveulddmgdetails.do');
//	}
}

function picFocus(inActivePic){
    var index = inActivePic.id.split("inactive_")[1];
	targetFormName.elements.selectedDamageRowId.value=index;
	if(targetFormName.elements.dmgPicture.value!=""){
	    //var size = document.appletsupport.getFileSize(targetFormName.dmgPicture.value);
		//var size =0;
		try{
        var size = document.appletsupport.getFileSize(targetFormName.elements.dmgPicture.value);
		if(size > 2097152) {
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>", 1, self, targetFormName, 'id_3');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_3'
                });
		}else{
			//var picNamArr = targetFormName.dmgPicture.value.split(".");
			if(targetFormName.elements.dmgPicture.value.lastIndexOf(".")>0){
			var pic = targetFormName.elements.dmgPicture.value.substring(targetFormName.elements.dmgPicture.value.lastIndexOf(".")+1,targetFormName.elements.dmgPicture.value.length);
				if((pic.toUpperCase() == "JPG") ||
				(pic.toUpperCase() == "JPEG") ||
				(pic.toUpperCase() == "BMP")||
				(pic.toUpperCase() == "GIF") ||
				(pic.toUpperCase() == "PNG")) {
				submitForm(targetFormName,'uld.defaults.ux.adddmgpic.do?selectedDamageRowId='+index);
			}else{
				//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			      showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
                });
			}
			}
			else{
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
				   
                });
			}
		}		
        }catch(e){
        var size = document.getElementsByName("dmgPicture")[0].files[0].size ;
        if(size > 2097152) {
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>", 1, self, targetFormName, 'id_3');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_3'
                });
		}else{
			//var picNamArr = targetFormName.dmgPicture.value.split(".");
			if(targetFormName.elements.dmgPicture.value.lastIndexOf(".")>0){
			var pic = targetFormName.elements.dmgPicture.value.substring(targetFormName.elements.dmgPicture.value.lastIndexOf(".")+1,targetFormName.elements.dmgPicture.value.length);
				if((pic.toUpperCase() == "JPG") ||
				(pic.toUpperCase() == "JPEG") ||
				(pic.toUpperCase() == "BMP")||
				(pic.toUpperCase() == "GIF") ||
				(pic.toUpperCase() == "PNG")) {
				submitForm(targetFormName,'uld.defaults.ux.adddmgpic.do?selectedDamageRowId='+index);
			}else{
				//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			      showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
                });
			}
			}
			else{
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
                });
			}
			}
		}
	}else{
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.info.uploadpicture'/>", 1, self, targetFormName, 'id_1');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.info.uploadpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_1'
                });
			}
		// }
	}

function showFacilityCode(lovName){
//targetFormName.elements.facilityType.value="CRG";
//targetFormName.elements.repStn.value="TRV";
	// if(targetFormName.elements.facilityType.value=="" ){
		// //showDialog("Facility Type cannot be blank", 1, self, targetFormName, 'id_77');
		  // showDialog({msg :'Facility Type cannot be blank',
				// type:1,
				// parentWindow:self,                                       
				// parentForm:targetFormName,
				// dialogId:'id_77'
                // });
		// return;
	// }
	// if(targetFormName.elements.repStn.value==""){

		// //showDialog('Reported Airport cannot be blank', 1, self);
		  // showDialog({msg :'Reported Airport cannot be blank',
			            	// type:1,
				            // parentWindow:self,                                       
				            // parentForm:targetFormName,
                         // }); 
		// return;
	// }
	//targetFormName.currentStation.defaultValue=targetFormName.currentStation.value;
	//targetFormName.facilityType.defaultValue=targetFormName.facilityType.value;
	
	//var currentStationForLov="TRV";
	//var facilityTypeForLov="CRG";
	
	//Modified by A-5170 for ICRD-32241 starts
	//var myScreen = openPopUpWithHeight(StrUrl, "500");
	//var myScreen = openPopUp(StrUrl,500,350);
	//Modified by A-5170 for ICRD-32241 ends
	
	var index = lovName.id.split("locationlov_")[1];
	var textfiledDesc="";
	var currentStationForLov=targetFormName.elements.repStn[index].value;
	var facilityTypeForLov=targetFormName.elements.facilityType[index].value;
	var strAction="uld.defaults.ux.lov.screenloadfacilitycodelov.do";
	var StrUrl=strAction+"?textfiledObj=location&formNumber=1&textfiledDesc="+textfiledDesc+'&rowCount='+index+'&facilityTypeForLov='+facilityTypeForLov+'&currentStationForLov='+currentStationForLov;
	var dialogConfig = {
		mainActionUrl: StrUrl,
		isMultiSelect: 'N',
		isPageable: 'Y',
		paginationActionUrl: 'uld.defaults.ux.lov.screenloadfacilitycodelov.do',
		lovTitle: 'Select Location',
		codeFldNameInScrn: 'location',
		descriptionFldNameInScrn: '',
		index: index,
		closeButtonIds: ['CMP_ULD_DEFAULTS_UX_FCPOPUP_OK_BTN','CMP_ULD_DEFAULTS_UX_FCPOPUP_CLOSE_BTN'],
		dialogWidth: 500,
		dialogHeight: 350,
		fieldToFocus: 'location',
		lovIconId: ''
	};
	lovUtils.displayLOV(dialogConfig);
}	
  
function showPartyCode(lovButton){
var frm=targetFormName;
	var index = lovButton.id.split("partylov_")[1];
	if(frm.elements.partyType[index].value=='A'){
		var optionsArray = {
			mainActionUrl:'ux.showAirline.do',
			isMultiSelect:'N',
			isPageable:'Y',
			paginationActionUrl:'ux.showAirline.do',
			lovTitle:'Select Airline',
			codeFldNameInScrn:'party',
			descriptionFldNameInScrn:'',
			index:index,
			closeButtonIds:['btnOk','CMP_Shared_Airline_AirlineLov_Close'],
			additionalOptions:'',
			mode:'',
			parentOkFnHook:'',
			lovOkFnHook:'',
			parentCloseFnHook:'',
			lovCloseFnHook:'',
			dialogWidth:'600',
			dialogHeight:'500',
			fieldToFocus:'party',
			lovIconId:'',
			maxlength:''
		};	
		lovUtils.displayLOV(optionsArray);	
	}else if(frm.elements.partyType[index].value=='G'){
		var id="party";
		var textfiledDesc="";
		// Modified by A-8188 for ICRD-297273
		var partyVal = frm.elements.party[index].value;
		var strUrl ="shared.defaults.ux.agent.screenloadagentlov.do?textfiledObj=party&formNumber=1&textfiledDesc="+textfiledDesc+"&rowCount="+index+"&agentCode="+partyVal;
		var optionsArray = {	
			mainActionUrl				: strUrl,
			isMultiSelect				: 'N' ,
			isPageable					: 'Y',
			paginationActionUrl			: strUrl,
			lovTitle					: 'Agent',
			codeFldNameInScrn			: id,
			descriptionFldNameInScrn	: '' ,
			index						: index,
			closeButtonIds 				: ['btnOk','btnClose'],
			additionalOptions           :'',
			mode                        :'',
			parentOkFnHook              :'',
			lovOkFnHook                	:'',
			parentCloseFnHook           :'',
			lovCloseFnHook              :'',
			dialogWidth                 :'600',
			dialogHeight				:'500',
			fieldToFocus				:id,
			lovIconId					:'agentLOV',
			maxlength					:''
		};
		lovUtils.displayLOV(optionsArray);
}else if(frm.elements.partyType[index].value=='C'){
					optionsArray = {	
						mainActionUrl				: 'ux.showCustomer.do?formCount=1',
						isMultiSelect				: 'N' ,
						isPageable					: 'Y',
						paginationActionUrl			:  'ux.showCustomer.do',
						lovTitle					: 'Customer',
						codeFldNameInScrn			: 'party',
						descriptionFldNameInScrn	: '' ,
						index						: index,
						closeButtonIds 				: ['btnOk','CMP_Shared_Customer_CustomerLov_Close'],
						dialogWidth					: 600,
						dialogHeight				: 550,
						fieldToFocus				: 'customerCode',
						lovIconId					: 'custCodeLov',
						maxlength					: 15,
						parentOkFnHook				: "",
						lovOkFnHook					: ""
						
					}
		lovUtils.displayLOV(optionsArray);
}

else if(frm.elements.partyType.value=='O'){
		//
}
}

function showDmgRefNoLov(){
var statusFlag=targetFormName.elements.statusFlag.value;
openPopUpWithHeight("uld.defaults.showdmgreflov.do?statusFlag="+statusFlag,"500");
}
function populateOnChange(obj)
{

//Added by A-8187 as part of ICRD-264433
var divid="damageType";
if(obj.id!="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_SECTION"){
var id = obj.id;

var firstDigit = id.search(/\d/);

var index = id.substring(firstDigit);
var divid="damageType"+index;
}
var section=obj.value;
//Added by A-8187 as part of ICRD-264433 End
//var section=targetFormName.elements.section[rowId].value;
//alert("targetFormName.section.value"+targetFormName.section.value);
			// targetFormName.elements.totalPoints.value=0;
			//submitForm(targetFormName, 'uld.defaults.displaydetails.do');
			// submitForm(targetFormName, 'uld.defaults.ux.damageDetailsListCommand.do');		
populateDamage("uld.defaults.ux.damageDetailsListCommand.do?section="+section,divid,"refreshDamageCode");			
}
 function doComboChange(){
 //alert(111);
 targetFormName.elements.party.value="";
 }
function populateDamage(url,divId,asyncFunction) {
	asyncSubmit(targetFormName,url,asyncFunction,null,null,divId);
}
function refreshDamageCode(_refreshInfo) {
	if(_asyncErrorsExist == false) {
		//document.getElementById(_refreshInfo.currDivId).innerHTML=_refreshInfo.getTableData();
        _resultDiv = _refreshInfo.document.getElementById("changedDamageType").innerHTML; 
		document.getElementById(_refreshInfo.currDivId).innerHTML=_resultDiv;
	}
	else if(_asyncErrorsExist == true){
		_resultDiv = _refreshInfo.document.getElementById("noDetailsFound").innerHTML; 
		document.getElementById(_refreshInfo.currDivId).innerHTML=_resultDiv;
	}
} 
//Added by A-7390 for ICRD-234401
function checkForSurveyorFieldEnabling(){
	var frm=targetFormName;
	var repairOpFlags=frm.elements.tempRepairOpFlag;
	var flag=true;
	for(var i=0;i<repairOpFlags.length;i++){
		if(repairOpFlags[i].value=='I'){
		flag=false;
		break;
		}
	}
	if(flag){
		disableField(targetFormName.elements.repairStatus);
		disableField(targetFormName.elements.supervisor);
		disableField(targetFormName.elements.invRep);
		disableField(targetFormName.elements.btnDispTot);
	}
	else{
		enableField(targetFormName.elements.repairStatus);
		enableField(targetFormName.elements.supervisor);
		enableField(targetFormName.elements.invRep);
		enableField(targetFormName.elements.btnDispTot);
	}
} 

function updateCheckboxValue(obj) {
	index = obj.attributes["rowcount"].value;
	obj.value = index;
	targetFormName.elements.closed[index].checked = obj.checked;
} 
//Modified by A-7359 for ICRD-296701
function closeSatus(obj){
	var index = obj.attributes["rowcount"].value;
	var dmgRefo = document.getElementsByName("dmgRefNo");
	//var dmgRepairRefNo =document.getElementById("dmgRepairRefNo")[index];
	//var damageRefNoOptions = document.getElementsByName("multiselect_damageRefNo"+index);
	var damageRefNoOptions = [];
    var inputs = document.getElementsByTagName("input");
     for(var i = 0; i < inputs.length; i++) {
    if(inputs[i].name.indexOf('multiselect_damageRefNo') == 0) {
        damageRefNoOptions.push(inputs[i]);
    }
    }
	//Commented for Bug ICRD-318056 for avoid removing already closed damage details
	//for(var j=0;j<dmgRefo.length;j++){
	// targetFormName.elements.closed[j].checked=false;
	// targetFormName.elements.closed[j].value="N";
	//}
	for(var i=0;i<damageRefNoOptions.length;i++){
	    var z=0;
		if(damageRefNoOptions[i].checked){	
	        var z=i;
		    if(i>=dmgRefo.length-1){
		       z =i%(dmgRefo.length-1); 
		    }
		  for(var j=0;j<dmgRefo.length;j++){
		   if((dmgRefo[j].value==damageRefNoOptions[i].value)){			
			 targetFormName.elements.closed[z].checked=true;
			 targetFormName.elements.closed[z].value="Y";
		   }
		   //z++;
		  }	
		}
		else{
		 var x=i;
		    if(i>=dmgRefo.length-1){
			  z =i%(dmgRefo.length-1); 			  
		    }
		   for(var k=0;k<dmgRefo.length;k++){
		   if((dmgRefo[k].value==damageRefNoOptions[i].value)){			
			 targetFormName.elements.closed[x].checked=false;
			 targetFormName.elements.closed[x].value="N";
		   }
		  }	
	  }
	}
	/*
	for(var i=0;i<dmgRefo.length;i++){
		if(dmgRefo[i].value==dmgRepairRefNo.value){			
			targetFormName.elements.closed[i].checked=true;
			targetFormName.elements.closed[i].value="Y";
		}

	}*/
	
}
function colorBox(){
		 jquery('.attach-count').colorbox({
                maxHeight: "90%",
                maxWidth: "90%",
				photo : true,
                loop: false,
				rel: function() {
                    return jquery(this).attr('rel');
                }
            });

} 