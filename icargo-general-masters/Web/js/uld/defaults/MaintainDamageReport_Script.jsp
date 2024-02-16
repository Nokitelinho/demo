<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>



function screenSpecificEventRegister()
{
	var frm=targetFormName;
	//onScreenloadSetHeight();

	with(frm){


	evtHandler.addEvents("btnList","list(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClear","clear(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnSave","save(this.form)",EVT_CLICK);
	//evtHandler.addEvents("btnView","view(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","onClickClose(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
	evtHandler.addEvents("btnDispTot","total(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnPrint","print()",EVT_CLICK);

	//if(frm.selectedDmgRowId!=null){
	//evtHandler.addEvents("selectedDmgRowId","selectedDmgRowId(this.form)",EVT_CLICK);
	//}
	//evtHandler.addEvents("checkDmgAll","checkDmgAll(this.form)",EVT_CLICK);
	if(frm.elements.selectedRepRowId!=null){
	evtHandler.addEvents("selectedRepRowId","selectedRepRowId(this.form)",EVT_CLICK);
	}
	evtHandler.addEvents("checkRepAll","checkRepAll(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnAddPic","picFocus()",EVT_CLICK);


	evtHandler.addIDEvents("addDmgLink","addDamage()",EVT_CLICK);
	evtHandler.addIDEvents("modDmgLink","modDamage()",EVT_CLICK);
	evtHandler.addIDEvents("delDmgLink","delDamage()",EVT_CLICK);
	//evtHandler.addIDEvents("addRepLink","addRepair()",EVT_CLICK);
	//evtHandler.addIDEvents("modRepLink","modRepair()",EVT_CLICK);
	//evtHandler.addIDEvents("delRepLink","delRepair()",EVT_CLICK);
	evtHandler.addEvents("invRep","validateMaximumLength(this,100,'Investigation Report','Maximum length',true)",EVT_BLUR);
	//Added by A-3415 for ICRD-113953
	evtHandler.addEvents("damageStatus","damageStatusChange()",EVT_CHANGE);


	}
onLoad();

}
function onScreenloadSetHeight(){
 	jquery('#tbladddamage').height(((document.body.clientHeight)*38)/100);

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
generateReport(targetFormName,'/uld.defaults.maintaindamageprint.do');
}


function selectedDmgRowId(frm){

	toggleTableHeaderCheckbox('selectedDmgRowId', frm.elements.checkDmgAll);
}

function checkDmgAll(frm){

	updateHeaderCheckBox(frm, frm.elements.checkDmgAll, frm.elements.selectedDmgRowId);
}

function selectedRepRowId(frm){

	toggleTableHeaderCheckbox('selectedRepRowId', frm.elements.checkRepAll);
}

function checkRepAll(frm){

	updateHeaderCheckBox(frm, frm.elements.checkRepAll, frm.elements.selectedRepRowId);
}

function addDamage(){
	var frm=targetFormName;
	if(targetFormName.elements.screenStatusValue.value=="SCREENLOAD"  ){
	 var addDmgLink = document.getElementById('addDmgLink');
	 addDmgLink.onclick = function() { return true; }
	 return;
	}

	frm.elements.statusFlag.value = "uld_def_add_dmg";
	var statusFlag=frm.elements.statusFlag.value;
	var screenStatusValue=frm.elements.screenStatusValue.value;
	//alert(statusFlag);
	damageStatus=targetFormName.elements.damageStatus.value;
	overallStatus=targetFormName.elements.overallStatus.value;
	repairStatus=targetFormName.elements.repairStatus.value;
	supervisor=targetFormName.elements.supervisor.value;
	invRep=targetFormName.elements.invRep.value;
	//picture=targetFormName.picture.value;

	//submitForm(frm,'uld.defaults.updatedmg.do');

	<!--  Modified By A-5170 for ICRD-32241 starts -->
	var newWindow = openPopUp(
				"uld.defaults.damageDetailsScreenLoadCommand.do?statusFlag="+statusFlag+"&screenStatusValue="+screenStatusValue+"&damageStatus="+damageStatus+"&overallStatus="+overallStatus+"&repairStatus="+repairStatus+"&supervisor="+supervisor+"&invRep="+invRep,
		"760","500");
	<!--  Modified By A-5170 for ICRD-32241 ends -->


}

function viewPic(seqno){
//alert("seqno"+seqno);
        targetFormName.elements.seqNum.value=seqno;


				var newWindow = openPopUp(
								"uld.defaults.viewpicture.do?seqNum="+seqno,
				"800","500");

}


function delDamage(){
	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'selectedDmgRowId', 1000000000000, 1);
	if (check){
	frm.elements.statusFlag.value = "uld_def_del_dmg";
	var statusFlag=frm.elements.statusFlag.value;
	//alert(statusFlag);
	submitForm(frm,'uld.defaults.deldmgdetails.do');}

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
		"760","500");
	<!--  Modified By A-5170 for ICRD-32241 ends -->
	 }

}
function addRepair(){
	var frm=targetFormName;
	if(targetFormName.elements.screenStatusValue.value=="SCREENLOAD"  ){
	 var addRepLink = document.getElementById('addRepLink');
	 addRepLink.onclick = function() { return true; }
	 return;
	}


	frm.elements.statusFlag.value = "uld_def_add_rep";
	var statusFlag=frm.elements.statusFlag.value;
	var screenStatusValue=frm.elements.screenStatusValue.value;
	//alert(statusFlag);

	damageStatus=targetFormName.elements.damageStatus.value;
	overallStatus=targetFormName.elements.overallStatus.value;
	repairStatus=targetFormName.elements.repairStatus.value;
	supervisor=targetFormName.elements.supervisor.value;
	invRep=targetFormName.elements.invRep.value;
	//picture=targetFormName.picture.value;

	//submitForm(frm,'uld.defaults.updatedmg.do');

	var newWindow = openPopUp(
				"uld.defaults.repairDetailsScreenLoadCommand.do?statusFlag="+statusFlag+"&screenStatusValue="+screenStatusValue+"&damageStatus="+damageStatus+"&overallStatus="+overallStatus+"&repairStatus="+repairStatus+"&supervisor="+supervisor+"&invRep="+invRep,
		"650","330");


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
		"650","330");}


}


function list(frm){
	submitForm(frm,'uld.defaults.listulddmgdetails.do');
}

function total(frm){
	submitForm(frm,'uld.defaults.totaldmgdetails.do');
}

function clear(frm){
	//submitForm(frm,'uld.defaults.clearulddmgdetails.do');
	submitFormWithUnsaveCheck('uld.defaults.clearulddmgdetails.do');
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
	if(frm.elements.damageStatusFlag.value=="N"){
		changeSelected();
	}
	var screenReloadStatus=targetFormName.elements.screenReloadStatus.value;

	if(isFormModified(targetFormName) || screenReloadStatus=="reload"){
		submitForm(frm,'uld.defaults.saveulddmgdetails.do');
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
		
		//alert("!:::::::"+targetFormName.screenStatusValue.value);
		if(targetFormName.elements.screenStatusValue.value=="SCREENLOAD"  ){

		//alert("SCREENLOAD");

		var addDmgLink = document.getElementById('addDmgLink');
		//addDmgLink.disabled=true;
		disableLink(addDmgLink);
		addDmgLink.onclick = function() { return false; }
		var modDmgLink = document.getElementById('modDmgLink');
		//modDmgLink.disabled=true;
		disableLink(modDmgLink);
		modDmgLink.onclick = function() { return false; }
		var delDmgLink = document.getElementById('delDmgLink');
		//delDmgLink.disabled=true;
		disableLink(delDmgLink);
		delDmgLink.onclick = function() { return false; }
		var addRepLink = document.getElementById('addRepLink');
		//addRepLink.disabled=true;
		disableLink(addRepLink);
		addRepLink.onclick = function() { return false; }
		var modRepLink = document.getElementById('modRepLink');
		//modRepLink.disabled=true;
		disableLink(modRepLink);
		modRepLink.onclick = function() { return false; }
		var delRepLink = document.getElementById('delRepLink');
		//delRepLink.disabled=true;
		disableLink(delRepLink);
		delRepLink.onclick = function() { return false; }



		//targetFormName.checkDmgAll.disabled=true;
		//targetFormName.checkRepAll.disabled=true;
		disableField(targetFormName.elements.checkRepAll);
		//targetFormName.btnClear.disabled=false;
		enableField(targetFormName.elements.btnClear);
		//targetFormName.damageStatus.disabled=true;
		disableField(targetFormName.elements.damageStatus);
		//targetFormName.overallStatus.disabled=true;
		disableField(targetFormName.elements.overallStatus);
		//targetFormName.repairStatus.disabled=true;
		disableField(targetFormName.elements.repairStatus);
		//targetFormName.supervisor.disabled=true;
		disableField(targetFormName.elements.supervisor);
		//targetFormName.invRep.disabled=true;
		disableField(targetFormName.elements.invRep);
		//targetFormName.btnRepInv.disabled=true;
		//targetFormName.btnSave.disabled=true;
		//targetFormName.dmgPicture.disabled=true;
		disableField(targetFormName.elements.dmgPicture);
		//targetFormName.btnAddPic.disabled=true;
		disableField(targetFormName.elements.btnAddPic);
		//targetFormName.btnView.disabled=true;

		//targetFormName.btnDispTot.disabled=true;
		disableField(targetFormName.elements.btnDispTot);
		disableField(targetFormName.elements.btnPrint);

		}
		if(targetFormName.elements.screenStatusValue.value=="LIST"  ){

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


		var addRepLink = document.getElementById('addRepLink');
		//addRepLink.disabled=true;
		disableLink(addRepLink);
		addRepLink.onclick = function() { return false; }
		var modRepLink = document.getElementById('modRepLink');
		//modRepLink.disabled=true;
		disableLink(modRepLink);
		modRepLink.onclick = function() { return false; }
		var delRepLink = document.getElementById('delRepLink');
		//delRepLink.disabled=true;
		disableLink(delRepLink);
		delRepLink.onclick = function() { return false; }



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

		if(targetFormName.elements.screenStatusValue.value=="DMGNOTPRESENT"  ){

			//alert("DMGNOTPRESENT");

			var addRepLink = document.getElementById('addRepLink');
			//addRepLink.disabled=true;
			disableLink(addRepLink);
			addRepLink.onclick = function() { return false; }


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
			if(document.getElementById('addDmgLink').disabled == false) {
						targetFormName.document.getElementById('addDmgLink').focus();
						}
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
		window.close();
	}else{
		submitForm(frm,'uld.defaults.closecommand.do');
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
		submitForm(frm,'uld.defaults.saveulddmgdetails.do');
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

function picFocus(){
		var check = validateSelectedCheckBoxes(targetFormName, 'selectedDmgRowId', 1, 1);
	if (check){
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
				submitForm(targetFormName,'uld.defaults.adddmgpic.do');
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
				submitForm(targetFormName,'uld.defaults.adddmgpic.do');
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
		}
	}




    