<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   //onScreenloadSetHeight();
   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
   	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btMailBagList","mailbagListDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btMailBagClear","mailbagclearDetails(this.form)",EVT_CLICK);
    evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
    evtHandler.addEvents("btOffload","offloadDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("uldCheckAll","updateHeaderCheckBox(targetFormName, targetFormName.elements.uldCheckAll, targetFormName.elements.uldSubCheck)", EVT_CLICK);
    evtHandler.addIDEvents("originOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOE.value,'OfficeOfExchange','1','originOE','',0)", EVT_CLICK);
	evtHandler.addIDEvents("destnOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destnOE.value,'OfficeOfExchange','1','destnOE','',0)", EVT_CLICK);
	evtHandler.addIDEvents("mailbagSubClassLov","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.mailbagSubClass.value,'SubClass','1','mailbagSubClass','',0)", EVT_CLICK);
	evtHandler.addIDEvents("mailbagOriginOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.mailbagOriginOE.value,'OfficeOfExchange','1','mailbagOriginOE','',0)", EVT_CLICK);
	evtHandler.addIDEvents("mailbagDestnOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.mailbagDestnOE.value,'OfficeOfExchange','1','mailbagDestnOE','',0)", EVT_CLICK);

   // evtHandler.addEvents("uldCheckAll","updateHeaderCheckBox(this.form,targetFormName.elements.uldCheckAll,targetFormName.elements.uldSubCheck)",EVT_CLICK);
	if(frm.elements.uldSubCheck != null){
	evtHandler.addEvents("uldSubCheck","toggleTableHeaderCheckbox('uldSubCheck',targetFormName.uldCheckAll)",EVT_CLICK);

	}
	//evtHandler.addEvents("dsnCheckAll","updateHeaderCheckBox(this.form,targetFormName.elements.dsnCheckAll,targetFormName.elements.dsnSubCheck)",EVT_CLICK);
	if(frm.elements.dsnSubCheck != null){
		evtHandler.addEvents("dsnSubCheck","toggleTableHeaderCheckbox('dsnSubCheck',targetFormName.elements.dsnCheckAll)",EVT_CLICK);
	}
	//evtHandler.addEvents("mailbagCheckAll","updateHeaderCheckBox(this.form,targetFormName.elements.mailbagCheckAll,targetFormName.elements.mailbagSubCheck)",EVT_CLICK);
	if(frm.elements.mailbagSubCheck != null){
	evtHandler.addEvents("mailbagSubCheck","toggleTableHeaderCheckbox('mailbagSubCheck',targetFormName.mailbagCheckAll)",EVT_CLICK);

	}



	evtHandler.addEvents("mailbagDsn","dsnPadding()",EVT_BLUR);
	evtHandler.addEvents("mailbagRsn","rsnPadding()",EVT_BLUR);
	evtHandler.addEvents("containerType","selectContainer()",EVT_CHANGE);


   	}
   	applySortOnTable("offloadULD",new Array("None","String","String","String","Number","Number","None","String"));
	applySortOnTable("offloadDSN",new Array("None","Number","String","String","String","String","Number","String","String","String","String","Number","Number","None","String"));
	applySortOnTable("offloadMailbag",new Array("None","String","String","String","String","None","String"));
}

function onScreenLoad(frm){



	selectDiv();

	var mode = frm.elements.status.value;
	if(mode == "showDuplicateFlights"){
		openPopUp("flight.operation.validateFlights.do","600","280");
		frm.elements.status.value = "";
	}

	var statusFlag = frm.elements.screenStatusFlag.value;
	if(statusFlag == "screenload"){

		disableDetails();
		frm.elements.flightCarrierCode.focus();
	}
	else if(statusFlag == "detail"){

		if(mode == "NORMAL_SEARCH_DONE"){
			disableFirstPanel();
		}

		if((targetFormName.elements.type.value=="U" || targetFormName.elements.type.value=="D" || targetFormName.elements.type.value=="M")
			&& (!targetFormName.elements.containerNumber.disabled) && (!targetFormName.elements.containerNumber.readOnly)){
			//targetFormName.elements.containerNumber.focus();
		}
		else if((targetFormName.elements.type.value=="D") && (!targetFormName.elements.despatchSn.disabled)
			&& (!targetFormName.elements.despatchSn.readOnly)){
			targetFormName.elements.despatchSn.focus();
		}
	}



	var flightStatus = frm.elements.flightStatus.value;
	if(frm.clearFlag.value == "Y"){
		//frm.type.disabled = false;
		enableField(frm.elements.type);
		frm.elements.clearFlag.value="N";
		frm.elements.status.value = "NORMAL";
		frm.elements.mode.value = "NORMAL";
		submitForm(frm,'mailtracking.defaults.offload.list.do');
	}

	if(flightStatus == "emptyuld"){
		frm.elements.flightStatus.value = "";
		var frmscreen = frm.elements.fromScreen.value;
		var strUrl = "mailtracking.defaults.emptyulds.screenload.do?fromScreen="+mode+"&toScreen="+frmscreen;

  		openPopUp(strUrl,"600","280");
	}

	if(flightStatus == "offloaded"){

		//frm.type.disabled = false;
		enableField(frm.type);
		frm.elements.flightStatus.value = "";


		//frm.mode.value = "NORMAL";
		var frmscrn=frm.elements.fromScreen.value;
		//if(!"MAILBAGENQUIRY" == frmscrn){
		//	submitForm(frm,'mailtracking.defaults.offload.clear.do');
		//}
		//frm.clearFlag.value="N";
		//frm.status.value = "NORMAL";
		//frm.mode.value = "NORMAL";
		//frm.lastPageNumber.value = 0;
		//frm.displayPageNum.value = 1;
		//submitForm(frm,'mailtracking.defaults.offload.list.do');
	}

	selectCombo();
}

function resetFocus(){
	 if(!event.shiftKey){
		if((!targetFormName.elements.flightCarrierCode.disabled) && (!targetFormName.elements.flightCarrierCode.readOnly)
			&& (targetFormName.elements.screenStatusFlag.value=="screenload")){
			targetFormName.elements.flightCarrierCode.focus();
		}
		else if((targetFormName.elements.type.value=="U" || targetFormName.elements.type.value=="D" || targetFormName.elements.type.value=="M")
			&& (!targetFormName.elements.containerNumber.disabled) && (!targetFormName.elements.containerNumber.readOnly)){
			targetFormName.elements.containerNumber.focus();
		}
		else if((targetFormName.elements.type.value=="D") && (!targetFormName.elements.despatchSn.disabled)
			&& (!targetFormName.elements.despatchSn.readOnly)){
			targetFormName.elements.despatchSn.focus();
		}
	}
}

function onScreenloadSetHeight(){
	var height = document.body.clientHeight;;
	document.getElementById('pageDiv').style.height = ((height*95)/100)+'px';
	//alert((height*95)/100);
}

function disableFirstPanel(){

	var frm = targetFormName;
	frm.elements.flightCarrierCode.readOnly = true;
	frm.elements.flightNumber.readOnly = true;
	frm.elements.date.readOnly = true;
	//frm.btn_incalender.disabled = true;
	//frm.type.disabled = true;
	disableField(frm.elements.btn_incalender);
	disableField(frm.elements.type);
}

function reopenFlight(frm){

	submitForm(frm,'mailtracking.defaults.offload.reopenflight.do');
}

function closeDetails(frm){

	submitForm(frm,'mailtracking.defaults.offload.close.do');
}

function listDetails(frm){

	//frm.type.disabled = false;
	enableField(frm.elements.type);
	frm.elements.status.value = "NORMAL";
	frm.elements.mode.value = "NORMAL";
	frm.elements.lastPageNumber.value=0;
	frm.elements.displayPageNum.value=1;
	submitForm(frm,'mailtracking.defaults.offload.list.do');
}

function mailbagListDetails(frm){

	frm.elements.mode.value = "ADVANCED";
	frm.elements.lastPageNumber.value=0;
	frm.elements.displayPageNum.value=1;
	submitForm(frm,'mailtracking.defaults.offload.list.do');
}

function clearDetails(frm){

	frm.elements.mode.value = "NORMAL";
	submitForm(frm,'mailtracking.defaults.offload.clear.do');
}

function mailbagclearDetails(frm){

	frm.elements.mode.value = "ADVANCED";
	frm.elements.clearFlag.value = "Y";
	submitForm(frm,'mailtracking.defaults.offload.clear.do');
}

function offloadDetails(frm){

	var isSelected = false;
	var type = frm.elements.type.value;
	if(type == "U"){
		if(validateSelectedCheckBoxes(frm,'uldSubCheck',50,1)){
			isSelected = true;
		}
	}
	else if(type == "D"){
		if(validateSelectedCheckBoxes(frm,'dsnSubCheck',1,1)){
			isSelected = true;
		}
	}
	else if(type == "M"){
		if(validateSelectedCheckBoxes(frm,'mailbagSubCheck',50,1)){
			isSelected = true;
		}
	}

	if(isSelected){
		//frm.type.disabled = false;
		enableField(frm.type);
		submitForm(frm,'mailtracking.defaults.offload.offloaddetails.do');
	}
}

function disableDetails(){

	var frm = targetFormName;
	//frm.btOffload.disabled = true;
	disableField(frm.btOffload);
	//frm.uldCheckAll.disabled = true;
	disableField(frm.elements.uldCheckAll);
	//frm.dsnCheckAll.disabled = true;
	disableField(frm.elements.dsnCheckAll);
	//frm.mailbagCheckAll.disabled = true;
	disableField(frm.elements.mailbagCheckAll);

	//frm.containerType.disabled = true;
	//disableField(frm.containerType);
	//frm.containerNumber.readOnly = true;
	frm.elements.despatchSn.readOnly = true;
	frm.elements.originOE.readOnly = true;
	frm.elements.destnOE.readOnly = true;
	//frm.mailClass.disabled = false;
	disableField(frm.elements.mailClass);
	frm.elements.year.readOnly = true;
	frm.elements.mailbagRsn.readOnly = true;
	frm.elements.mailbagOriginOE.readOnly = true;
	frm.elements.mailbagDestnOE.readOnly = true;
	//frm.mailbagCategory.disabled = true;
	disableField(frm.elements.mailbagCategory);
	frm.elements.mailbagSubClass.readOnly = true;
	frm.elements.mailbagYear.readOnly = true;
	frm.elements.mailbagDsn.readOnly = true;

	frm.originOELov.disabled = true;
	//disableField(frm.elements.originOELov);
	frm.destnOELov.disabled = true;
	//disableField(frm.elements.destnOELov);
	//frm.mailbagSubClassLov.disabled = true;
	disableField(frm.elements.mailbagSubClassLov);
	//frm.mailbagOriginOELov.disabled = true;
	disableField(frm.elements.mailbagOriginOELov);
	//frm.mailbagDestnOELov.disabled = true;
	disableField(frm.elements.mailbagDestnOELov);

	//frm.btMailBagList.disabled = true;
	//frm.btMailBagClear.disabled = true;
	disableField(frm.btMailBagList);
	disableField(frm.btMailBagClear);
}

function handleUld(){

	var frm = targetFormName;
	frm.elements.containerNumber.readOnly = false;

	frm.elements.despatchSn.value = "";
	frm.elements.originOE.value = "";
	frm.elements.destnOE.value = "";
	frm.elements.year.value = "";
	frm.elements.mailbagRsn.value = "";
	frm.elements.mailbagOriginOE.value = "";
	frm.elements.mailbagDestnOE.value = "";
	frm.elements.mailbagSubClass.value = "";
	frm.elements.mailbagYear.value = "";
	frm.elements.mailbagDsn.value = "";

	frm.elements.despatchSn.readOnly = true;
	frm.elements.originOE.readOnly = true;
	frm.elements.destnOE.readOnly = true;
	//frm.mailClass.disabled = true;
	disableField(frm.elements.mailClass);
	frm.elements.year.readOnly = true;
	frm.elements.mailbagRsn.readOnly = true;
	frm.elements.mailbagOriginOE.readOnly = true;
	frm.elements.mailbagDestnOE.readOnly = true;
	//frm.mailbagCategory.disabled = true;
	disableField(frm.elements.mailbagCategory);
	frm.elements.mailbagSubClass.readOnly = true;
	frm.elements.mailbagYear.readOnly = true;
	frm.elements.mailbagDsn.readOnly = true;

	//frm.originOELov.disabled = true;
	disableField(frm.elements.originOELov);
	//frm.destnOELov.disabled = true;
	disableField(frm.elements.destnOELov);
	//frm.mailbagSubClassLov.disabled = true;
	disableField(frm.mailbagSubClassLov);
	//frm.mailbagOriginOELov.disabled = true;
	disableField(frm.mailbagOriginOELov);
	//frm.mailbagDestnOELov.disabled = true;
	disableField(frm.mailbagDestnOELov);

}

function handleDsn(){

	var frm = targetFormName;
	frm.elements.containerNumber.readOnly = false;
	frm.elements.despatchSn.readOnly = false;
	frm.elements.originOE.readOnly = false;
	frm.elements.destnOE.readOnly = false;
	//frm.mailClass.disabled = false;
	enableField(frm.elements.mailClass);
	frm.elements.year.readOnly = false;

	frm.elements.mailbagRsn.value = "";
	frm.elements.mailbagOriginOE.value = "";
	frm.elements.mailbagDestnOE.value = "";
	frm.elements.mailbagSubClass.value = "";
	frm.elements.mailbagYear.value = "";
	frm.elements.mailbagDsn.value = "";

	frm.elements.mailbagRsn.readOnly = true;
	frm.elements.mailbagOriginOE.readOnly = true;
	frm.elements.mailbagDestnOE.readOnly = true;
	//frm.mailbagCategory.disabled = true;
	disableField(frm.elements.mailbagCategory);
	frm.elements.mailbagSubClass.readOnly = true;
	frm.elements.mailbagYear.readOnly = true;
	frm.elements.mailbagDsn.readOnly = true;

	//frm.originOELov.disabled = false;
	//frm.destnOELov.disabled = false;
	enableField(frm.originOELov);
	enableField(frm.destnOELov);
	//frm.mailbagSubClassLov.disabled = true;
	//frm.mailbagOriginOELov.disabled = true;
	//frm.mailbagDestnOELov.disabled = true;
	disableField(frm.mailbagSubClassLov);
	disableField(frm.mailbagOriginOELov);
	disableField(frm.mailbagDestnOELov);

}

function handleMailBag(){

	var frm = targetFormName;
	frm.elements.containerNumber.readOnly = false;

	frm.elements.despatchSn.value = "";
	frm.elements.originOE.value = "";
	frm.elements.destnOE.value = "";
	//frm.mailClass.value = "";
	frm.elements.year.value = "";
	frm.elements.despatchSn.readOnly = true;
	frm.elements.originOE.readOnly = true;
	frm.elements.destnOE.readOnly = true;
	//frm.mailClass.disabled = true;
	frm.elements.mailbagYear.readOnly = false;
	frm.elements.mailbagDsn.readOnly = false;
	frm.elements.mailbagRsn.readOnly = false;
	disableField(frm.elements.mailClass);
	frm.elements.year.readOnly = true;
	frm.elements.mailbagOriginOE.readOnly = false;
	frm.elements.mailbagDestnOE.readOnly = false;
	//frm.mailbagCategory.disabled = false;
	enableField(frm.elements.mailbagCategory);
	frm.elements.mailbagSubClass.readOnly = false;

	disableField(frm.originOELov);
	disableField(frm.destnOELov);
	enableField(frm.mailbagSubClassLov);
	enableField(frm.mailbagOriginOELov);
	enableField(frm.mailbagDestnOELov);

}


function selectDiv() {

	var frm = targetFormName;

	togglePanel(1,frm.elements.type);

	var type = frm.elements.type.value;
	if(type == "U"){
		handleUld();
	}
	else if(type == "D"){
		handleDsn();
	}
	else if(type == "M"){
		handleMailBag();
	}

	selectCombo();
	selectContainer();
}

function togglePanel(iState,comboObj) // 1 visible, 0 hidden
{
	if(comboObj != null) {

   	    var divID = comboObj.value;

  	    var comboLength = comboObj.length;
		var obj = null;
		var comboValues = null;

		var divValues = ['U','D','M'];
		var length = divValues.length;

		for(var index=0; index<length; index++) {

			if(divValues[index] == divID) {

				var divObj = document.layers ? document.layers[divID] :
							 document.getElementById ?  document.getElementById(divID).style :
					 		 document.all[divID].style;

				divObj.visibility = document.layers ? (1 ? "show" : "hide") :
								 (1 ? "visible" : "hidden");

				divObj.display = document.layers ? (1 ? "" : "none") :
								 (1 ? "" : "none");

				divObj.zIndex = 120;

			}
			else {

				var divAlt = document.layers ? document.layers[divValues[index]] :
							 document.getElementById ?  document.getElementById(divValues[index]).style :
							 document.all[divValues[index]].style;

				divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
					 (0 ? "visible" : "hidden");

				divAlt.display = document.layers ? (0 ? "" : "none") :
						 (0 ? "" : "none");

	    	}

		}
	}
}

function confirmMessage(){
	//targetFormName.type.disabled = false;
	enableField(targetFormName.elements.type);
	var frm = targetFormName;
 	var warningCode = frm.elements.warningFlag.value;
 	if(warningCode != null && warningCode.length>0){
		if('flight_tba_tbc' == warningCode ){
	frm.elements.status.value = "NORMAL";
	frm.elements.mode.value = "NORMAL";
	frm.elements.lastPageNumber.value=0;
	frm.elements.displayPageNum.value=1;
	submitForm(frm,'mailtracking.defaults.offload.list.do?warningOveride=Y');
		}
		}else{
	submitForm(targetFormName,'mailtracking.defaults.offload.offloaddetails.do?flightStatus=OVERRIDE');
	}
}

function nonconfirmMessage(){
	targetFormName.elements.flightStatus.value="";
}

function selectCombo(){

	var str = targetFormName.elements.containerType.value;
	if(str == 'ALL'){

		//targetFormName.containerNumber.value = "";
		//targetFormName.containerNumber.readOnly = true;
	}
	else{
		targetFormName.elements.containerNumber.readOnly = false;
	}
}


function dsnPadding(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("mailbagDsn");
 var mailDSN =document.getElementsByName("mailbagDsn");
   for(var i=0;i<mailDSNArr.length;i++){
      if(mailDSNArr[i].value.length == 1){
          mailDSN[i].value = "000"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 2){
                mailDSN[i].value = "00"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 3){
                mailDSN[i].value = "0"+mailDSNArr[i].value;
      }
   }

}
function submitList(strLastPageNum,strDisplayPage){
	var frm = targetFormName;
	frm.elements.lastPageNumber.value= strLastPageNum;
	frm.elements.displayPageNum.value = strDisplayPage;
	submitForm(frm, 'mailtracking.defaults.offload.list.do');
}


function rsnPadding(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("mailbagRsn");
 var mailRSN =document.getElementsByName("mailbagRsn");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}
function selectContainer(){
	frm=targetFormName;
	var divULD = document.getElementById("contType_U");
	var divBULK = document.getElementById("contType_B");
	if (frm.elements.containerType.value=="U"){
		divBULK.style.display = "none";
		divULD.style.display = "block";
		}else{
		divULD.style.display = "none";
		divBULK.style.display = "block";
	}
}