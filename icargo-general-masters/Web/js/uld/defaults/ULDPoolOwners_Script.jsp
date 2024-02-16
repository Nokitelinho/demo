<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	if(!frm.elements.firstAirline.readOnly && frm.elements.linkStatus.value!="Y"){
	frm.elements.firstAirline.focus();
}

	with(frm){

		addIEonScroll();
   		//DivSetVisible(true);
   		onScreenLoad();
		evtHandler.addIDEvents("addPool","handleLink('add')",EVT_CLICK);
		evtHandler.addIDEvents("deletePool","handleLink('delete')",EVT_CLICK);
		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addIDEvents("firstairlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.firstAirline.value,'FirstAirline','1','firstAirline','',0)",EVT_CLICK);
		evtHandler.addIDEvents("secondairlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.secondAirline.value,'SecondAirline','1','secondAirline','',0)",EVT_CLICK);
		//evtHandler.addEvents("selectairline","viewAirlineLov('selectairline',this)",EVT_CLICK);
		//evtHandler.addEvents("selectairlineone","viewAirlineLovone('selectairlineone',this)",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnSegmentException","segExp()",EVT_CLICK);
		evtHandler.addEvents("btnSave","onClickSave()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);

		evtHandler.addEvents("firstAirline","validateFields(this, -1, 'FirstAirline', 0, true, true)",EVT_BLUR);
		evtHandler.addEvents("secondAirline","validateFields(this, -1, 'SecondAirline', 0, true, true)",EVT_BLUR);
		evtHandler.addEvents("polAirport","validateFields(this, -1, 'Airport', 1, true, true)",EVT_BLUR);
		evtHandler.addIDEvents("airportlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.polAirport.value,'Airport Code','1','polAirport','',0)",EVT_CLICK);
		//added by a-3278 for bug 23977 on 04Nov08
		//evtHandler.addEvents("airportLovImg","viewAirport('airportLovImg',this)",EVT_CLICK);
		//a-3278 ends
		//added by a-3045 for bug 24465 on 09Dec08 starts
		evtHandler.addEvents("btnClose","onBlurClose()",EVT_BLUR);
		//added by a-3045 for bug 24465 on 09Dec08 ends

	}
}

	function onScreenLoad(){
		var frm=targetFormName;
		if(frm.elements.linkStatus.value!="Y"){
			var addLink=document.getElementById("addPool");

			addLink.disabled=true;

			var deleteLink=document.getElementById('deletePool');
			deleteLink.disabled=true;

			frm.elements.btnSave.disabled=true;
			frm.elements.btnSegmentException.disabled=true;

			}else{
			//added by a-3045 for bug 24465 on 09Dec08 starts
			document.getElementById('addPool').focus();
			//added by a-3045 for bug 24465 on 09Dec08 ends
			}

		if(frm.elements.popupFlag.value == "Y"){
		frm.elements.popupFlag.value = "";
		segmentExceptions(frm);
		}

	}
	function onClickList(){

		var frm=targetFormName;
		submitForm(targetFormName,"uld.defaults.uldpoolowners.listuldpoolowners.do");

	}

	function onClickClose(){
	submitForm(targetFormName,"uld.defaults.closeaction.do");

		}

	function onClickClear(){
		//submitForm(targetFormName,"uld.defaults.uldpoolowners.clearuldpoolowners.do");
		submitFormWithUnsaveCheck('uld.defaults.uldpoolowners.clearuldpoolowners.do');
		}

	function onClickSave(){

		updateOperationFlags();
		var airlineOne=document.getElementsByName('airlineOne');
		var airlineTwo=document.getElementsByName('airlineTwo');
		var airport=document.getElementsByName('airport');
		var opFlag = document.getElementsByName('hiddenOperationFlag');

		if(validateFormFields(targetFormName.airlineOne)){
			showDialog({msg:'AirlineOne cannot be blank',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_1"});
			return;
		}

		if(validateFormFields(targetFormName.airlineTwo)){
				showDialog({msg:'AirlineTwo cannot be blank',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_2"});
				return;
			}
		/*if(validateFormFields(targetFormName.airport)){
				showDialog({msg:'Please enter a valid Airport',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_3"});
				return;
			}*/

		if(validatePoolAirline(targetFormName.airlineOne)){
		showDialog({msg:'AirlineOne Code cannot be same as airlineTwo',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_4"});
		return;
		}
		var isDuplicate="N";
			for (var i = 0; i < airlineOne.length; i++){
						if((opFlag[i].value=="U") || (opFlag[i].value=="I") /*|| (opFlag[i].value=="NA")*/){
							for(var j=i+1;j<airlineOne.length;j++){
									{
											if((opFlag[j].value=="U") || (opFlag[j].value=="I") /*|| (opFlag[j].value=="NA")*/){
												if((((airlineOne[i].value).toUpperCase())==(airlineOne[j].value).toUpperCase())&&((airlineTwo[i].value).toUpperCase()==(airlineTwo[j].value).toUpperCase())&&((airport[i].value).toUpperCase()==(airport[j].value).toUpperCase())){
													isDuplicate="Y";
												}

											}
									}
								}
						}
					}

					if(isDuplicate=="Y")
					{
						showDialog({msg:'Duplicate entries present in the table',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_5"});
						return;
					}

		var flag='save';
		submitForm(targetFormName,"uld.defaults.updateuldpoolowners.do?flag="+flag);
		}

		function handleLink(linkName){

		 var link = linkName;
		 if(link == "add") {

			var opFlag = document.getElementsByName('hiddenOperationFlag');
			var airlineOne=document.getElementsByName('airlineOne');
			var airlineTwo=document.getElementsByName('airlineTwo');
			var airport=document.getElementsByName('airport');

			if(validateFormFields(targetFormName.airlineOne)){
				showDialog({msg:'AirlineOne cannot be blank',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_1"});
				return;
			}

		if(validateFormFields(targetFormName.airlineTwo)){
				showDialog({msg:'AirlineTwo cannot be blank',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_2"});
				return;
			}
		/*if(validateFormFields(targetFormName.airport)){
				showDialog('Please enter a valid Airport or NA as Airport', 1, self, targetFormName, 'id_3');
				return;
			}*/
		if(validatePoolAirline(targetFormName.airlineOne)){
		showDialog({msg:'AirlineOne Code cannot be same as airlineTwo',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_4"});
		return;
		}
		var isDuplicate="N";
			for (var i = 0; i < airlineOne.length; i++){
						if((opFlag[i].value=="U") || (opFlag[i].value=="I") || (opFlag[i].value=="NA")){
							for(var j=i+1;j<airlineOne.length;j++){
									{
											if((opFlag[j].value=="U") || (opFlag[j].value=="I") || (opFlag[j].value=="NA")){
												if((((airlineOne[i].value).toUpperCase())==(airlineOne[j].value).toUpperCase())&&((airlineTwo[i].value).toUpperCase()==(airlineTwo[j].value).toUpperCase())&&((airport[i].value).toUpperCase()==(airport[j].value).toUpperCase())){
													isDuplicate="Y";
												}

											}
									}
								}
						}
					}

					if(isDuplicate=="Y")
					{
						showDialog({msg:'Duplicate entries present in the table',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_5"});
						return;
					}

			var flag='add';
			addRow(flag);
			//submitForm(targetFormName,"uld.defaults.updateuldpoolowners.do?flag="+flag);
		}
		else if(link =="delete") {
			var frm = targetFormName;
			if(validateSelectedCheckBoxes(targetFormName,'selectedRows',1000000,1)){
			var flag='delete';
			deleteRow(flag);
	        //submitForm(targetFormName,"uld.defaults.updateuldpoolowners.do?flag="+flag);
			}

		}
		}
		function segExp(){
		var flag='exceptions';
		var opFlag = document.getElementsByName('hiddenOperationFlag');
		var airlineOne=document.getElementsByName('airlineOne');
		var airlineTwo=document.getElementsByName('airlineTwo');
		var airport=document.getElementsByName('airport');
		var checkBoxesId = document.getElementsByName('selectedRows');
		if(validateFormFields(targetFormName.elements.airlineOne)){
			showDialog({msg:'AirlineOne cannot be blank', type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_1"});
			return;
		}

		if(validateFormFields(targetFormName.elements.airlineTwo)){
				showDialog({msg:'AirlineTwo cannot be blank', type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_2"});
				return;
			}
		/*if(validateFormFields(targetFormName.elements.airport)){
				showDialog({msg:'Please enter a valid Airport or NA as Airport',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_3"});
				return;
			}*/

		if(validatePoolAirline(targetFormName.elements.airlineOne)){
		showDialog({msg:'AirlineOne Code cannot be same as airlineTwo',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_4"});
		return;
		}
		var isDuplicate="N";
			for (var i = 0; i < airlineOne.length; i++){
						if((opFlag[i].value=="U") || (opFlag[i].value=="I") || (opFlag[i].value=="NA")){
							for(var j=i+1;j<airlineOne.length;j++){
									{
											if((opFlag[j].value=="U") || (opFlag[j].value=="I") || (opFlag[j].value=="NA")){
												if((((airlineOne[i].value).toUpperCase())==(airlineOne[j].value).toUpperCase())&&((airlineTwo[i].value).toUpperCase()==(airlineTwo[j].value).toUpperCase())&&((airport[i].value).toUpperCase()==(airport[j].value).toUpperCase())){
													isDuplicate="Y";
												}

											}
									}
								}
						}
					}

			if(isDuplicate=="Y")
				{
					showDialog({msg:'Duplicate entries present in the table',type:1, parentWindow:self, parentForm:targetFormName, dialogId:"id_5"});
					return;
				}
		var count=0;
		var check=0;

	    for (var i = 0; i < checkBoxesId.length; i++){
			if((checkBoxesId[i].checked)){
			check=check+1;
			}
		}
		if(check==0){

				showDialog({msg:'Please select a row',type:1,parentWindow:self});
		}else if(check>1){
				showDialog({msg:'Please select only one row',type:1,parentWindow:self});
			}
		else{
			for (var i=0;i<checkBoxesId.length;i++)
				{
					if(checkBoxesId[i].checked)
						{
						  count=i;

						}
			 	}

		submitForm(targetFormName,"uld.defaults.updateuldpoolowners.do?flag="+flag+"&selectedRow="+count);
		 }
		}
		function segmentExceptions(frm) {
		var opFlag = document.getElementsByName('hiddenOperationFlag');
		var checkBoxesId = document.getElementsByName('selectedRows');

		/*var count=0;
		var check=0;

	    for (var i = 0; i < checkBoxesId.length; i++){
			if((checkBoxesId[i].checked)){
			check=check+1;
			}
		}

			for (var i=0;i<checkBoxesId.length;i++)
				{
					if(checkBoxesId[i].checked)
						{
						  count=i;

						}
			 	}
			var airlineone=document.getElementsByName('airlineOne')
			var airlineOne=(airlineone[count]).value;
			var airlinetwo=document.getElementsByName('airlineTwo')
			var airlineTwo=(airlinetwo[count]).value;
			var airports=document.getElementsByName('airport')
			var airport=(airports[count]).value;
			var remark=document.getElementsByName('remarks')
			var remarks=(remark[count]).value;
			alert(airport);*/
			flag='exceptions';
			var selectedRow=frm.elements.selectedRow.value;
			//alert(selectedRow+'***************');
			openPopUp("uld.defaults.screenloadsegmentexceptions.do?selectedRow="+selectedRow,"450", "400");

			}




function chkDate(date){

 	if(date.value.trim().length==0){

 	return;
 	}
         else
 	{
 	checkdate(date, 'DD-MMM-YYYY');
 		}
 	}


function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){
 	}
 	if(frm.elements.currentDialogOption.value == 'Y') {
 	if(dialogId=='id_1'){
	submitForm(targetFormName,"uld.defaults.uldpoolowners.deleteuldpoolowners.do");
		}
	}
}

 function screenNonConfirmDialog(frm, dialogId) {
 	while(frm.elements.currentDialogId.value == ''){
 		}
 	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId=='id_1'){
			}
		}
	}

function viewAirlineLov(name,lov){
var selected=lov.id;
var index=selected.split(name)[1];
displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineOne.value,'CurrentAirline','1','airlineOne','',index);
}

function viewAirlineLovone(name,lov){
var selected=lov.id;
var index=selected.split(name)[1];
displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineTwo.value,'CurrentAirline','1','airlineTwo','',index);
}
function validateFormFields(code){
var code = eval(code);
var opFlag = document.getElementsByName('hiddenOperationFlag');
if(code != null){
	if(code.length>1){
	for(var i=0;i<code.length;i++){
		if((code[i].value==""||code[i].value.trim().length==0)&& (opFlag [i].value != 'NOOP'&&opFlag [i].value!='D')){
			return true;
			}
		}
	}else{

		if(code.value==""||code.value.trim().length==0){
			return true;
		}
	}
	}
	return false;
}


function validatePoolAirline(code){
var airlinecode=targetFormName.elements.airlineTwo;
var opFlag = document.getElementsByName('hiddenOperationFlag');
var code=eval(code);
if(code!=null){
	if(code.length>1){
	 for(var i=0;i<code.length;i++){
		if((opFlag[i].value != 'NOOP'&&opFlag[i].value!='D')){
				if(code[i].value==airlinecode[i].value){
				return true;
			}
		 }
		}
	}else{
		if(code.value==airlinecode.value){
		return true;
		}
	}
}
return false;
}

function updateOperationFlags(){
	var frm = targetFormName;
	var opFlag = document.getElementsByName('hiddenOperationFlag');
	var airlineOne = document.getElementsByName('airlineOne');
	var airlineTwo = document.getElementsByName('airlineTwo');
	var airport = document.getElementsByName('airport');
	var remarks = document.getElementsByName('remarks');

	if(opFlag != null && opFlag.length >1){
	for(var i=0;i<opFlag.length;i++){
		if(opFlag[i].value == 'NA'){

			if(isElementModified(airlineOne[i])
				|| isElementModified(airlineTwo[i])
				|| isElementModified(airport[i])
				|| isElementModified(remarks[i]))
				{
					opFlag[i].value = 'U';

				}
		}
	  }
	}

}
function addRow(flag){
	recreateTableDetails('uld.defaults.updateuldpoolowners.do','uldPoolOwnerParentdiv',flag);
}

function deleteRow(flag){
	recreateTableDetails('uld.defaults.updateuldpoolowners.do','uldPoolOwnerParentdiv',flag);
}
var _tableDivId = "";

function recreateTableDetails(strAction,divId,flag){
	var __extraFn="updateTableDetails";
	_tableDivId = divId;
	var flagValue=flag;
	var action=strAction+'?flag='+flagValue;
	asyncSubmit(targetFormName,action,__extraFn,null,null,_tableDivId);
}

function updateTableDetails(_tableInfo){
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	_filter=_tableInfo.document.getElementById("uldPoolOwnerChilddiv");
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;

	var hidairlineOne = document.getElementsByName("airlineOne");
	var ind=hidairlineOne.length-1;
	if(ind > -1){
		hidairlineOne[ind].focus();
	}
	setTimeout('showRowFocus()',500);
}
function populateAirport(ind){
	var index = ind.getAttribute("rowCount");
	var airportNA = document.getElementsByName("airport");
	//airportNA[index].value = "NA";
}
//added by a-3278 for bug 23977 on 04Nov08
function viewAirport(name,lov){
var selected=lov.id;
var index=selected.split(name)[1];
displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.airport.value,'Airport','1','airport','',index);
}
//a-3278 ends

function showRowFocus(){
	var hidairlineOne = document.getElementsByName("airlineOne");
	var ind=hidairlineOne.length-1;
	if(ind > -1){
		hidairlineOne[ind].focus();
	}
}

//added by a-3045 for bug 24465 on 09Dec08 starts
function onBlurClose(){
	var frm=targetFormName;
	if(frm.linkStatus.value =="Y"){
		document.getElementById('addPool').focus();
	}
}
//added by a-3045 for bug 24465 on 09Dec08 ends