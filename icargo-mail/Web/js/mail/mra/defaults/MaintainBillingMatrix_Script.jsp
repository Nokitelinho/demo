<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){


   var frm=targetFormName;
   callOnScreenLoad();
 	with(frm){
 	 evtHandler.addEvents("btnChangeEnddate", "onChangeEnddate()", EVT_CLICK);
		evtHandler.addEvents("btnSurcharge", "onSurchargeDetails()", EVT_CLICK);
		evtHandler.addEvents("btnList", "onList()", EVT_CLICK);
		evtHandler.addEvents("btnClear", "onClear()", EVT_CLICK);
		evtHandler.addEvents("btnCopy","copyBillingLine()",EVT_CLICK);
		evtHandler.addIDEvents("addLink","onLinkClicks('ADD')", EVT_CLICK);
		evtHandler.addIDEvents("modifyLink","onLinkClicks('MODIFY')", EVT_CLICK);
		evtHandler.addEvents("btnSave", "onSave()", EVT_CLICK);
		evtHandler.addEvents("btnClose", "onClose()", EVT_CLICK);
		evtHandler.addEvents("btnClose", "resetFocus()", EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addIDEvents("deleteLink","onLinkClicks('DELETE')",EVT_CLICK);
		evtHandler.addIDEvents("blgMatrixIDLov","billingMatrixLOV()", EVT_CLICK);
		evtHandler.addEvents("checkboxes","toggleTableHeaderCheckbox('checkboxes',document.forms[1].headChk)",EVT_CLICK);
		evtHandler.addEvents("headChk","updateHeaderCheckBox(targetFormName,targetFormName.headChk,targetFormName.checkboxes)",EVT_CLICK);
		evtHandler.addEvents("btnActivate", "onActivate()", EVT_CLICK);
		evtHandler.addEvents("btnInactivate", "onInactivate()", EVT_CLICK);
		evtHandler.addEvents("btnCancel", "onCancel()", EVT_CLICK);
		evtHandler.addIDEvents("gpaCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'GPACode','1','gpaCode','',0)",EVT_CLICK);
		evtHandler.addIDEvents("airlinelov","showAirline()",EVT_CLICK);
	}

	if(frm.elements.validateStatus.value.trim().toUpperCase() == "Y"){

		frm.elements.validateStatus.value = "N";
		launchPopUp();
	}

}
function onChangeEnddate(){

	var frm =targetFormName;

	var chkboxes=document.getElementsByName('checkboxes');
	var selectedId=new Array();
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes',25,1)){

		var j=0;
		for(var i=0;i<chkboxes.length;i++){
			if(chkboxes[i].checked){
				selectedId[j]=chkboxes[i].value;
				j++;
			}
		}



	var sta = document.getElementsByName('statusValue');
		var selIndex ='';
		if(chkboxes != null){
			for(var index=0;index<chkboxes.length;index++){
				if(chkboxes[index].checked == true){
					if(selIndex.trim().length == 0){
						selIndex=chkboxes[index].value;
						if(sta[selIndex].value == "I"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}
						else if (sta[selIndex].value == "C"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}
						/*else if(sta[selIndex].value == "I"){

							showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivecopy" scope="request"/>',1,self);
							return;
						}
						else if(sta[index].value == "E"){
							showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.expirecopy" scope="request"/>',1,self);
							return;

							}*/
					}
					else{
						if(sta[index].value == "I"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}
						else if (sta[index].value == "C"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}
						selIndex=selIndex+"-"+chkboxes[index].value;
					}
				}
			}
	}
	openPopUp("mailtracking.mra.defaults.maintainbillingmatrix.changedate.do?selectedIndexes="+selIndex,600,220);
	//openPopUp("mailtracking.mra.defaults.maintainbillingmatrix.copybillingline.do?checkboxes="+selectedId,460,250);
}
}
function onSurchargeDetails() {
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes',1,1) ){
	var chkboxes = document.getElementsByName('checkboxes');
	var surchargeIndicators = document.getElementsByName('surchargeIndicator');
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
			selIndex=chkboxes[index].value;
				
			}
		}
	}
	if("Y"!=surchargeIndicators[selIndex].value)
	{
		//showDialog('Surcharges do not exist for the selected rate line',1,self);
		showDialog({msg:'Surcharges do not exist for the selected rate line',type:1,parentWindow:self});
		return;
	}
	openPopUp("mailtracking.mra.defaults.maintainbillingmatrix.surchargedetails.do?selectedIndex="+selIndex,430,225);
	}	
}

function billingMatrixLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showBillingMatrixLOV.do','N','Y','showBillingMatrixLOV.do',document.forms[1].blgMatrixID.value,'Billing Matrix','1','blgMatrixID','',0,_reqHeight);
}
function showAirline(){

	//displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',
		//document.forms[1].airlineCode.value,'Airline code','1',
		//'airlineCode','',0)
	displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline','0','airlineCode','',0);
 }
function callOnScreenLoad(action) {
	//added for screen resolution
	/* var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	//document.getElementById('contentdiv').style.height = ((clientHeight*90)/100)+'px';
	//document.getElementById('outertable').style.height=((clientHeight*84)/100)+'px';
	var pageTitle=30;
	var filterlegend=80;
	var filterrow=90;
	var bottomrow=40;
	var height=(clientHeight*84)/100;
	var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow );*/
	//document.getElementById('div1').style.height=tableheight+'px';
    //added for screen resolution ends
	var frm=targetFormName;
	//if( frm.status.value.trim().toUpperCase()=="ACTIVE" ){

			//frm.btnActivate.disabled = true;
		//}
	//else if( frm.status.value.trim().toUpperCase()=="INACTIVE" ){

			//frm.btnInactivate.disabled = true;
		//}
	 if(frm.elements.disableButton.value=="Y" && frm.elements.status.value.trim().toUpperCase()=="NEW" ){

			disableField(frm.elements.btnActivate);
			disableField(frm.elements.btnInactivate);

		}

		if(frm.elements.disableActive.value=="N" && frm.elements.status.value.trim().toUpperCase()=="NEW" ){

				enableField(frm.elements.btnActivate);
				enableField(frm.elements.btnInactivate);

		}
	if(document.forms[1].copyFlag.value=="COPY"){
		submitForm(targetFormName,'mailtracking.mra.defaults.screenloadcopybillingline.do');
	}
	if(frm.elements.status.value == null || frm.elements.status.value.trim().length == 0){
		disableLink(addLink);
		disableLink(modifyLink);
		disableLink(deleteLink);
	}
	else{
		//CHANGE
		/*enableLink(addLink);
		enableLink(modifyLink);
		enableLink(deleteLink);*/
	}
	if(frm.elements.idValue.value != null && frm.elements.idValue.value.trim().length > 0){
		frm.elements.blgMatrixID.value = frm.elements.idValue.value ;
		}
	if(frm.formStatusFlag.value != "ENABLE"){
		frm.elements.btnSave.disabled = true;
		frm.elements.description.disabled=true;
		frm.elements.status.disabled=true;
		frm.elements.validFrom.disabled=true;
		frm.elements.btn_validFrom.disabled = true;
		frm.elements.validTo.disabled=true;
		frm.elements.btn_validTo.disabled = true;
		frm.elements.btnCopy.disabled=true;
		disableField(frm.elements.btnActivate);
		disableField(frm.elements.btnInactivate);
		disableField(frm.elements.btnSurcharge);
		disableField(frm.elements.btnChangeEnddate);
		frm.elements.btnCancel.disabled=true;
		if(document.forms[1].copyFlag.value!="COPY")
			frm.elements.blgMatrixID.focus();
	}
	else{
		if(frm.elements.status.value != null && frm.elements.status.value.trim().toUpperCase() != "NEW" ){
			frm.elements.description.disabled=true;
			frm.elements.validFrom.disabled=true;
			frm.elements.validTo.disabled=true;
			frm.elements.btn_validTo.disabled = true;
			frm.elements.btn_validFrom.disabled = true;
			document.getElementById('blgMatrixIDLov').disabled = true;
			frm.elements.blgMatrixID.disabled=true;
			//Added as part of bug-icrd-16356
			document.getElementById('gpaCodelov').disabled = true;
			document.getElementById('airlinelov').disabled = true;
			//CHANGE STARTS
			if(frm.elements.status.value.trim().toUpperCase()=="CANCELLED"

						  ||frm.elements.status.value.trim().toUpperCase()=="EXPIRED"){
							  disableLink(addLink);
							  disableLink(modifyLink);
							  disableLink(deleteLink);
			  }
			//CHANGE ENDS
		}
		else{
			document.getElementById('blgMatrixIDLov').disabled = true;
			frm.elements.description.disabled = false;
			frm.elements.description.focus();
			frm.elements.blgMatrixID.disabled=true;
			//Added as part of bug-icrd-16356
			document.getElementById('gpaCodelov').disabled = true;
			document.getElementById('airlinelov').disabled = true;
		}
		if(	document.getElementById('addLink').disabled == false){
			document.getElementById('addLink').focus();
		}	
		
	}
	if(("EXPIRED" == frm.elements.status.value.toUpperCase())){
		disableField(frm.elements.btnActivate);
		disableField(frm.elements.btnInactivate);
		frm.elements.btnCancel.disabled = true;
		frm.elements.operationFlag.value = "";
	}
	if("EXPIRED" == frm.elements.status.value.toUpperCase()){
			frm.elements.btnSave.disabled = true;
	}
	
}

function launchPopUp(){

	var frm = targetFormName;
	var validateStatus = frm.elements.validateStatus.value;
	var arr=null;
	var chkboxes = document.getElementsByName('checkboxes');
	if(frm.elements.selectedIndexes.value != null && frm.elements.selectedIndexes.value.trim().length > 0){
		arr = frm.elements.selectedIndexes.value.split("-");
		for(var i = 0;i<arr.length;i++){
			if(document.getElementsByName('checkboxes').length!=0)
				document.getElementsByName('checkboxes')[arr[i]].checked =true;
		}
	}
	if(frm.elements.actionType.value != null && frm.elements.actionType.value.trim().length > 0){
		popUpFunction(frm.elements.actionType.value);
}
}

function onChangeStatus(){
	var frm = targetFormName;
	var size;
	var chks = document.getElementsByName("checkboxes");
	if(chks!= null && chks.length>0){
		openPopUp("mailtracking.mra.defaults.maintainbillingmatrix.changestatuspopup.do?fromPage=maintainbillingmatrix",230,110);
	}
}

function confirmMessage() {
	frm =  targetFormName;
	frm.elements.formStatusFlag.value = "ENABLE";
	frm.elements.status.value = "NEW";
	frm.elements.validTo.value = "";
	frm.elements.validFrom.value = "";
	frm.elements.description.value = "";
	frm.elements.operationFlag.value = "I";
	//CHANGE
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.screenload.do');
}

function nonconfirmMessage() {
       submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.clear.do');
}

function onLinkClicks(actionType){
	var chkboxes = document.getElementsByName('checkboxes');
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				if(selIndex.trim().length==0)
					selIndex=chkboxes[index].value;
				else
					selIndex=selIndex+"-"+chkboxes[index].value;
			}
		}
	}
	var actionString = "mailtracking.mra.defaults.maintainbillingmatrix.validate.do?actionType="+actionType+"&selectedIndexes="+selIndex;
	submitForm(targetFormName,actionString);
	return;
}

function popUpFunction(actionType){

	var frm = targetFormName;

	var actionType = frm.elements.actionType.value;
	var selIndex = frm.elements.selectedIndexes.value;
	var chkboxes = document.getElementsByName('checkboxes');
	var option;
	var choice;
	var screenStatus=frm.elements.fromDateFlag.value;

	if(targetFormName.elements.status.value == null || targetFormName.elements.status.value.trim().length == 0){
		return;
	}
	var action = 'mailtracking.mra.defaults.maintainbillingline.add.do';
	var sta = document.getElementsByName('statusValue');
	var fDate = targetFormName.elements.validFrom.value;
	var tDate = targetFormName.elements.validTo.value;
	if(actionType == 'ADD'){
		if(targetFormName.validTo.value == "" || targetFormName.validFrom.value == ""){
			//showDialog('Specify the valid From Date and To Date of the Billing Matrix',1,self);
			showDialog({msg:'Specify the valid From Date and To Date of the Billing Matrix',type:1,parentWindow:self});
			return;
		}
	}
	if(actionType == 'MODIFY'){
		if(targetFormName.elements.blgMatrixID.value != null && targetFormName.elements.blgMatrixID.value.trim().length > 0){
			if(validateSelectedCheckBoxes(targetFormName,'checkboxes',1,1) ){
				if(sta[selIndex].value == "N"){
					var rvExVals = document.getElementsByName('revExpFlag');
					var rvEx=rvExVals[selIndex].value;
					var blgBasis = document.getElementsByName('billingBasis');
					var blgBasisVal=blgBasis[selIndex].value;
					openPopUp("mailtracking.mra.defaults.maintainbillingline.screenload.do?selectedIndex="+selIndex+"&actionType="+actionType+"&frmDate="+fDate+"&toDate="+tDate+"&reFlag="+rvEx+"&billingBasis="+blgBasisVal,'880','780');
				}
				else{
					//showDialog("The specified Billing Line cannot be modified",1, self);
					showDialog({msg:'The specified Billing Line cannot be modified',type:1,parentWindow:self});
					return;
			}
			}
		}
		else{
			//showDialog("Specifiy a valid Billing Matrix ID",1, self);
			showDialog({msg:'Specifiy a valid Billing Matrix ID',type:1,parentWindow:self});
			return;
	}
	}
	else if(actionType== 'ADD'){
		if(targetFormName.elements.blgMatrixID.value != null && targetFormName.elements.blgMatrixID.value.trim().length > 0){
			if(screenStatus=='DateLesser'){

				frm.elements.fromDateFlag.value="";
				//A-8061 commented date validation warning as part of ICRD-302439 , confirmed the same with Manoj James
				/*showDialog({msg:'Valid From date is less than current date, do you want to continue?',type :3, parentWindow:self,parentForm:targetFormName,
						onClose:function(result){
							if(result) {*/
				openPopUp("mailtracking.mra.defaults.maintainbillingline.screenload.do?actionType="+actionType+"&frmDate="+fDate+"&toDate="+tDate,'880','780');
				/*}
						}
						});*/
				//option=showWarningDialog('Valid From date is less than current date, do you want to continue?');
				//if(option==true){
				//openPopUp("mailtracking.mra.defaults.maintainbillingline.screenload.do?actionType="+actionType+"&frmDate="+fDate+"&toDate="+tDate,'880','470');
				//}

				}
			else{
				openPopUp("mailtracking.mra.defaults.maintainbillingline.screenload.do?actionType="+actionType+"&frmDate="+fDate+"&toDate="+tDate,'880','780');
				}

		}
		else{
			//showDialog("Specifiy a valid Billing Matrix ID",1, self);
			showDialog({msg:'Specifiy a valid Billing Matrix ID',type:1,parentWindow:self});
			return;
		}
	}
	else if(actionType == 'DELETE'){
		if(targetFormName.elements.blgMatrixID.value != null && targetFormName.elements.blgMatrixID.value.trim().length > 0){
				if(validateSelectedCheckBoxes(targetFormName,'checkboxes',1,1) ){
				if(sta[selIndex].value == "N"){
										showDialog({msg:'Do you want to delete the selected billing line?',type :3, parentWindow:self,parentForm:targetFormName,
						onClose:function(result){
							if(result) {
						submitForm(targetFormName,"mailtracking.mra.defaults.maintainbillingmatrix.delete.do?selectedIndex="+selIndex+"&actionType="+actionType,'870','500');
					}
						}
						});
						//choice=showWarningDialog('Do you want to delete the selected billing line?');
						//if(choice==true){
						//submitForm(targetFormName,"mailtracking.mra.defaults.maintainbillingmatrix.delete.do?selectedIndex="+selIndex+"&actionType="+actionType,'870','470');
					//}
					}
					else if(sta[selIndex].value == "A"){
						//showDialog("The specified active Billing Line cannot be deleted",1, self);
						showDialog({msg:'The specified active Billing Line cannot be deleted',type:1,parentWindow:self});
						return;
						}
					else if(sta[selIndex].value == "I"){
						//showDialog("The specified inactive Billing Line cannot be deleted",1, self);
						showDialog({msg:'The specified inactive Billing Line cannot be deleted',type:1,parentWindow:self});
						return;
						}
					else if(sta[selIndex].value == "C"){
						//showDialog("The specified cancelled Billing Line cannot be deleted",1, self);
						showDialog({msg:'The specified cancelled Billing Line cannot be deleted',type:1,parentWindow:self});
						return;
						}
				}

			}
			else {
				//showDialog("Specifiy a valid Billing Matrix ID",1, self);
				showDialog({msg:'Specifiy a valid Billing Matrix ID',type:1,parentWindow:self});
				return;
			}
		}
}




function onList() {
	var frm=targetFormName;
	frm.elements.formStatusFlag.value ='DISABLED';
	frm.elements.displayPage.value="1";
  	frm.elements.lastPageNum.value="0";
	submitForm(frm,'mailtracking.mra.defaults.maintainbillingmatrix.list.do');
}

function onSave(){
	submitForm(targetFormName,"mailtracking.mra.defaults.maintainbillingmatrix.save.do");
}

function onClear(){
	targetFormName.elements.status.value = "";
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.clear.do');
}

function submitPage(lastPg, displayPg) {
	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	targetFormName.action="mailtracking.mra.defaults.maintainbillingmatrix.list.do";
	targetFormName.submit();
}

//Function to copyRateLine
function copyBillingLine(){

	var frm =targetFormName;

	var chkboxes=document.getElementsByName('checkboxes');
	var selectedId=new Array();
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes',25,1)){

		var j=0;
		for(var i=0;i<chkboxes.length;i++){
			if(chkboxes[i].checked){
				selectedId[j]=chkboxes[i].value;
				j++;
			}
		}



	var sta = document.getElementsByName('statusValue');
		var selIndex ='';
		if(chkboxes != null){
			for(var index=0;index<chkboxes.length;index++){
				if(chkboxes[index].checked == true){
					if(selIndex.trim().length == 0){
						selIndex=chkboxes[index].value;
						if(sta[selIndex].value == "C"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
							showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',type:1,parentWindow:self});
							return;
						}
						/*else if(sta[selIndex].value == "I"){

							showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivecopy" scope="request"/>',1,self);
							return;
						}
						else if(sta[index].value == "E"){
							showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.expirecopy" scope="request"/>',1,self);
							return;

							}*/
					}
					else
						selIndex=selIndex+"-"+chkboxes[index].value;
				}
			}
	}
	openPopUp("mailtracking.mra.defaults.maintainbillingmatrix.copybillingline.do?checkboxes="+selectedId,460,250);
}
}


function onActivate(){
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes','',1)){
	var chkboxes = document.getElementsByName('checkboxes');
	var sta = document.getElementsByName('statusValue');
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
					if(sta[selIndex].value == "C"){
						//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.canceltoactive" scope="request"/>',1,self);
						showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.canceltoactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[selIndex].value == "A"){
						//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.activetoactive" scope="request"/>',1,self);
						showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.activetoactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						//showDialog('BillingLines with Expired status cannot be modified',1,self);
						showDialog({msg:'BillingLines with Expired status cannot be modified',type:1,parentWindow:self});
						return;

						}
				}
				else
					selIndex=selIndex+"-"+chkboxes[index].value;
			}
		}
	}

		showDialog({msg:'Do you want to activate?',type :3, parentWindow:self,
						onClose:function(result){
						if(result){
						submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.activate.do?selectedIndexes='+selIndex);
						}
						}
						});

	//option=showWarningDialog('Do you want to activate?');
	//if(option==true){
	//submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.activate.do?selectedIndexes='+selIndex);
	}
}

function onInactivate(){
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes','',1)){
	var chkboxes = document.getElementsByName('checkboxes');
	var sta = document.getElementsByName('statusValue');
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
                    }
				else{
					selIndex=selIndex+"-"+chkboxes[index].value;
				}
					if(sta[selIndex].value == "N"){
						//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.newtoinactive" scope="request"/>',1,self);
						showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.newtoinactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[selIndex].value == "I"){
						//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivetoinactive" scope="request"/>',1,self);
						showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivetoinactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[selIndex].value == "C"){
						//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.canceltoinactive" scope="request"/>',1,self);
						showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.canceltoinactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					 else if(sta[index].value == "E"){
						//showDialog('BillingLines with Expired status cannot be modified',1,self);
						showDialog({msg:'BillingLines with Expired status cannot be modified',type:1,parentWindow:self});
						return;

				}

			}
		}
	}
	/*option=showWarningDialog('Do you want to inactivate?');
	if(option==true){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.inactivate.do?selectedIndexes='+selIndex);
}*/

showDialog({msg:'Do you want to inactivate?',type :3, parentWindow:self,
						onClose:function(result){
						if(result){
						submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.inactivate.do?selectedIndexes='+selIndex);
						}
						}
						});

}
}
function onCancel(){
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes','',1)){
	var chkboxes = document.getElementsByName('checkboxes');
	var sta = document.getElementsByName('statusValue');
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
					}
				else{
					selIndex=selIndex+"-"+chkboxes[index].value;
				}
					if(sta[selIndex].value == "C"){
						//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.canceltocancel" scope="request"/>',1,self);
						showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.canceltocancel" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[selIndex].value == "N"){
						//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.newtocancel" scope="request"/>',1,self);
						showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.newtocancel" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						//showDialog('BillingLines with Expired status cannot be modified',1,self);
						showDialog({msg:'BillingLines with Expired status cannot be modified',type:1,parentWindow:self});
						return;
				}

			}
		}
	}
	//submitFormWithUnsaveCheck('mailtracking.mra.defaults.maintainbillingmatrix.cancel.do?selectedIndexes='+selIndex);
	/*option=showWarningDialog('Do you want to cancel?');
	if(option==true){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.cancel.do?selectedIndexes='+selIndex);
	}*/
	
	
	showDialog({msg:'Do you want to cancel?',type :3, parentWindow:self,
						onClose:function(result){
						if(result){
						submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.cancel.do?selectedIndexes='+selIndex);
						}
						}
						});
}
}

function onClose() {
var option;

if(isFormModified(targetFormName)||targetFormName.elements.notSave.value=="Y"){


	showDialog({msg:'Unsaved data exists.Do you want to continue?',type :3, parentWindow:self,
						onClose:function(result){
						if(result){
						submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.close.do');
						}
						}
						});
		}

/*option=showWarningDialog('Unsaved data exists.Do you want to continue?');
if(option==true){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.close.do');
	}

}*/
else{
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainbillingmatrix.close.do');
	}
}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
			if(targetFormName.elements.blgMatrixID.disabled == false && targetFormName.elements.blgMatrixID.readOnly== false){
			targetFormName.elements.blgMatrixID.focus();
		}else{
			if(	document.getElementById('addLink').disabled == false){
			 document.getElementById('addLink').focus();
		    }	
		}
	}
}