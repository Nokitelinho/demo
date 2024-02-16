<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister() {
	var frm=targetFormName;

 	with(targetFormName){

				evtHandler.addEvents("btnSurcharge","viewSurcharge()",EVT_CLICK);
				evtHandler.addEvents("btnList","listAction(targetFormName)",EVT_CLICK);
				evtHandler.addEvents("btnSave","saveAction(targetFormName)",EVT_CLICK);
				evtHandler.addEvents("btnClear","ClearAction(targetFormName)",EVT_CLICK);
				//evtHandler.addEvents("airlineCodeLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);
				evtHandler.addIDEvents("airportlov","displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].revDStCode.value,'Airport','1','revDStCode','',0)",EVT_CLICK);
				evtHandler.addEvents("btnClose","CloseAction()",EVT_CLICK);
				evtHandler.addIDEvents("airlineCodeLov","onclickToLOV()",EVT_CLICK);
				//evtHandler.addEvents("issueParty","comboChanged()",EVT_CHANGE);
				evtHandler.addEvents("btnDelete","deleteAction(targetFormName)",EVT_CLICK);
				evtHandler.addEvents("btnReject","rejectAction(targetFormName)",EVT_CLICK);
				//evtHandler.addEvents("btnAutorate","autorateAction(targetFormName)",EVT_CLICK);
				evtHandler.addEvents("btnPrint","printCCAAction()",EVT_CLICK);
				evtHandler.addIDEvents("currLov","displayLOV('showCurrency.do','N','Y','showCurrency.do',targetFormName.elements.revCurCode.value,'revCurCode','1','revCurCode','',0)", EVT_CLICK);
				evtHandler.addEvents("gpacodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.revGpaCode.value,'GPA Code','1','revGpaCode','',0)",EVT_CLICK);
			    evtHandler.addEvents("btnAccept","acceptAction(targetFormName)",EVT_CLICK);
				evtHandler.addEvents("dsnNumber","mailDSN()",EVT_BLUR);
				//evtHandler.addEvents("revGpaCode","populateName()",EVT_BLUR);
				evtHandler.addEvents("revGrossWeight","chkChanged(this)",EVT_BLUR);
				//8331
				
				evtHandler.addEvents("revTax","chkChanged(this)",EVT_BLUR);
				
				evtHandler.addEvents("revGpaCode","chkChanged(this)",EVT_BLUR);
				evtHandler.addEvents("revChgGrossWeight","chkChanged(this)",EVT_BLUR);
				evtHandler.addEvents("revCurCode","chkChanged(this)",EVT_BLUR);
				//evtHandler.addEvents("revDueArl","chkChanged(this)",EVT_BLUR);
				//evtHandler.addEvents("revDuePostDbt","chkChanged(this)",EVT_BLUR);
				document.getElementById("ignoreHiddenCheck").value = "Y";
				//added for ICRD 7352
				evtHandler.addEvents("palov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);
				//evtHandler.addEvents("mcanolov","displayLOV('mailtracking.mra.defaults.mcalov.screenload.do','N','Y','mailtracking.mra.defaults.mcalov.screenload.do',targetFormName.ccaNum.value,'MCA No','1','ccaNum','dsnNumber',0)",EVT_CLICK);

				evtHandler.addIDEvents("mcanolov","mcaLov()",EVT_CLICK);
				
				evtHandler.addIDEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.elements.dsnNumber.value,'DSN No','0','dsnNumber',targetFormName.dsnNumber.value,0)",EVT_CLICK);
				evtHandler.addIDEvents("revChgGrossWe","onBlur()",EVT_BLUR);
				evtHandler.addIDEvents("otherRevChgGrossWg","onBlur()",EVT_CHANGE);
				evtHandler.addIDEvents("ccaType","onBlurCCA()",EVT_BLUR);
				evtHandler.addIDEvents("revisedRate","onBlur()",EVT_BLUR);
				
				evtHandler.addEvents("revGrossWeight","restrictFloat(this,16,4)",EVT_KEYPRESS);

				
	onScreenLoad();
	}
}
// Added for 76551
function viewSurcharge(){

var revcode=targetFormName.elements.revCurCode.value;
var revgrwgt =targetFormName.elements.revGrossWeight.value;
	openPopUp("mailtracking.mra.defaults.maintaincca.surchargedetails.do?surChargeAction=List&revCurCode="+revcode+'&revGrossWeight='+revgrwgt,430,400);

}
function addSurcharge(){
	addTemplateRow('surchargeTemplateRow','surchargeTableBody','surchargeOpFlag');
}
function deleteSurcharge(){
	deleteTableRow('chkSurChg','surchargeOpFlag');
}

function mcaLov(){
var height = document.body.clientHeight;
				var _reqHeight = (height*49)/100;
displayLOVCodeNameAndDesc('mailtracking.mra.defaults.mcalov.screenload.do','N','Y','mailtracking.mra.defaults.mcalov.screenload.do',targetFormName.elements.ccaNum.value,'MCA No','1','ccaNum','dsnNumber','conDocNo',0,_reqHeight);
}
function onScreenLoad(){

   // initial focus on page load.
    if(targetFormName.elements.dsnNumber.value=="")
   {
   disableField(targetFormName.elements.btnSave);
   disableField(targetFormName.elements.ccaType);
   disableField(targetFormName.elements.btnSurcharge);
   }
   
   if(targetFormName.elements.revChgGrossWe.value=="0" && targetFormName.elements.showpopUP.value!="" && targetFormName.elements.showpopUP.value!="TRUE" )
   {

    disableField(targetFormName.elements.btnSave);
	disableField(targetFormName.elements.btnReject);
	disableField(targetFormName.elements.btnAccept);
    disableField(targetFormName.elements.btnDelete);
	disableField(targetFormName.elements.btnPrint);
	disableField(targetFormName.elements.ccaType);
	disableField(targetFormName.elements.btnSurcharge);
   }
   if(targetFormName.elements.ccaNum.disabled == false) {
      targetFormName.elements.ccaNum.focus();
   }
   if(targetFormName.elements.showpopUP.value=="TRUE"){
	   	targetFormName.elements.popupon.value=='Y';

   		openPopUp('mailtracking.mra.defaults.showccas.screenload.do',500,500);
	}



   if(targetFormName.elements.dsnPopupFlag.value=="Y"&& targetFormName.elements.fromScreen.value!="RateAuditDetails" ){

		targetFormName.elements.dsnPopupFlag.value="N";
   		var popupheight = document.body.clientHeight*30/100;
   		var popupwidth = document.body.clientWidth*60/100;
   		var dsn = targetFormName.elements.dsnNumber.value;
   		var dat = targetFormName.elements.dsnDate.value;
   		var frmPg = "MAINTAIN_CCA";
   		submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.listdsndetail.do');
   		//openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+dsn+"&dsnDate="+dat+"&fromPage="+frmPg,725,450);
	}

	/*if(targetFormName.elements.issueParty.value=="GPA"){
		targetFormName.elements.comboFlag.value="GPA";
		disableField(targetFormName.elements.revDueArl);
		enableField(targetFormName.elements.revDuePostDbt);

	}else{
		targetFormName.elements.comboFlag.value="Airline";
		disableField(targetFormName.elements.revDuePostDbt);
		enableField(targetFormName.elements.revDueArl);

	}*/

	if((targetFormName.elements.ccaType.value=="R"||targetFormName.elements.ccaType.value=="A") && targetFormName.elements.disableFlag.value!="N"){
	targetFormName.elements.disableFlag.value ="";
	//viewModeON(targetFormName);
	}

	if(targetFormName.elements.showDsnPopUp.value=="TRUE"){
		targetFormName.elements.showDsnPopUp.value="FALSE";
		openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do",725,450);
    }
	//to disable buttons if workflow not enabled

	if(targetFormName.elements.privilegeFlag.value=="N"){
	if(targetFormName.elements.btnDelete!=null && targetFormName.elements.btnAccept!=null &&targetFormName.elements.btnReject!=null){
	//targetFormName.elements.btnDelete.disabled="true";
	//targetFormName.elements.btnAccept.disabled="true";
	//targetFormName.elements.btnReject.disabled="true";
	disableField(targetFormName.elements.btnDelete);
	disableField(targetFormName.elements.btnAccept);
	disableField(targetFormName.elements.btnReject);
	}
	}
	//disable buttons with mca status
	if(targetFormName.elements.ccaStatus.value!="N" && targetFormName.elements.ccaStatus.value!="C"){//Modifed for IASCB-2377
	if(targetFormName.elements.btnDelete!=null && targetFormName.elements.btnAccept!=null &&targetFormName.elements.btnReject!=null){
	//targetFormName.elements.btnDelete.disabled="true";
	//targetFormName.elements.btnAccept.disabled="true";
	//targetFormName.elements.btnReject.disabled="true";
	disableField(targetFormName.elements.btnDelete);
	disableField(targetFormName.elements.btnAccept);
	disableField(targetFormName.elements.btnReject);
	}
	//targetFormName.elements.btnSave.disabled="true";

	}

	if(targetFormName.elements.ccaPresent.value=="N"){

		if(targetFormName.elements.btnDelete!=null && targetFormName.elements.btnAccept!=null &&targetFormName.elements.btnReject!=null){
		//targetFormName.elements.btnDelete.disabled="true";
		//targetFormName.elements.btnAccept.disabled="true";
		//targetFormName.elements.btnReject.disabled="true";
		disableField(targetFormName.elements.btnDelete);
		disableField(targetFormName.elements.btnAccept);
		
	}

  }
  if(targetFormName.elements.ccaStatus.value!=null && targetFormName.elements.ccaStatus.value!="" &&
	 targetFormName.elements.ccaStatus.value!="N"){
			disableField(targetFormName.elements.reason1);
			disableField(targetFormName.elements.reason2);
			disableField(targetFormName.elements.reason3);
			disableField(targetFormName.elements.reason4);
		}
		//Added by A-7929 as part of ICRD-132548
	if (targetFormName.elements.fromScreen.value=="listbillingentries" || targetFormName.elements.fromScreen.value=="listbillingentriesuxopenPopUp"){
	//Added as part of ICRD-341145
	if (targetFormName.elements.fromScreen.value!="listbillingentries"){
	
	           // disableField(targetFormName.elements.reason1);
               // disableField(targetFormName.elements.reason2);
               // disableField(targetFormName.elements.reason3);
               // disableField(targetFormName.elements.reason4);
	}
	disableField(targetFormName.elements.isAutoMca);
				
				disableField(targetFormName.elements.btnList);
				disableField(targetFormName.elements.btnClear);
				disableField(targetFormName.elements.btnDelete);
                disableField(targetFormName.elements.btnPrint);
                disableField(targetFormName.elements.btnAccept);
                disableField(targetFormName.elements.btnReject);
                enableField(targetFormName.elements.btnSave);
                enableField(targetFormName.elements.btnSurcharge); 
				//disableField(airportlov);
				//disableField(airlineCodeLov);
				disableField(currLov);
				disableField(mcanolov);
				disableField(dsnlov);
				disableField(mcanolov);
				
				targetFormName.elements.dsnNumber.disabled=true;
                targetFormName.elements.revChgGrossWeight.disabled=true;
                targetFormName.elements.revGrossWeight.disabled=true;
				
				targetFormName.elements.ccaType.disabled=true;
                targetFormName.elements.revCurCode.disabled=true;
              
               // targetFormName.elements.remarks.disabled=true;
				targetFormName.elements.ccaNum.disabled=true;
                targetFormName.elements.conDocNo.disabled=true;
				
                targetFormName.elements.revChgGrossWe.disabled=true;
                targetFormName.elements.otherRevChgGrossWg.disabled=true;
				
				enableField(gpacodelov);
               
                if(targetFormName.elements.billingStatus.value=="BB")
               {
                 targetFormName.elements.revCurCode.disabled=false;
	             targetFormName.elements.isAutoMca.value="Y";
				 targetFormName.elements.isAutoMca.checked=true;
				  enableField(currLov);
                }
	
	if(targetFormName.elements.isAutoMca.checked)
	{
	  targetFormName.elements.isAutoMca.value="Y";
	}		  
	}
		
onBlurCCA();
if(targetFormName.elements.fromScreen.value=="listbillingentries" || targetFormName.elements.fromScreen.value=="listbillingentriesuxopenPopUp"){
	
	    if(targetFormName.elements.billingStatus.value=="BD")
               {
				     targetFormName.elements.revCurCode.disabled=true; 
			   }
	
	targetFormName.elements.revGrossWeight.disabled=true;
	targetFormName.elements.revTax.disabled=true;
	
	
	
}

}

function onBlur(){
if (targetFormName.elements.fromScreen.value!="listbillingentries"){
	replaceDecimalSepWithDot();
	refreshTableDetails("mailtracking.mra.defaults.maintaincca.populate.do","ajaxDiv");
	formatNumberForLocale();
	}
	}
	
	function refreshTableDetails(strAction,divId){
	
			var __extraFn="updateTableCode";
			
			asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
			
}
function updateTableCode(_tableInfo){
	
			var  oldDivId = "ajaxDiv";

    _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];

_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
// _innerFrm = _tableInfo.document.forms['MRAMaintainCCAForm'];
//targetFormName.count=_innerFrm.count;
targetFormName.elements.revisedChargeGrossWeignt.value = _innerFrm.elements.revisedChargeGrossWeignt.value;
//targetFormName.elements.revisedChargeGrossWeignt[1].value = _innerFrm.elements.revisedChargeGrossWeignt.value;

    var newDivId =_tableInfo.document.getElementById("ajaxDiv");

 	document.getElementById(oldDivId).innerHTML =  newDivId.innerHTML;
     onBlurCCA();
			//_str=_tableInfo.getTableData();
			
			//document.getElementById(_tableInfo.currDivId).innerHTML=_str;
				
}
function ClearAction(targetFormName){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.clearscreen.do');
}

function chkChanged(objt){
	if(objt.defaultValue!=objt.value){
	targetFormName.elements.autoratedFlag.value="N";
	}
	if(objt.name=='revGrossWeight'){
	onBlur();
	}
}

function acceptAction(targetFormName){
if(targetFormName.elements.ccaType.value!=''){
		if(targetFormName.elements.ccaStatus.value=="N" || targetFormName.elements.ccaStatus.value=="C"){//Modified For IASCB-2377
	targetFormName.elements.disableFlag.value="Y";
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.accept.do');

		}else{
		showDialog({msg:"<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.ccatypnew" />",type:1,parentWindow:self});
		return;
	}
	}else{
	showDialog({msg:"Please select CCA Type",type: 1, parentWindow:self});
	return;
	}
	
}

function autorateAction(targetFormName){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.autorate.do');
}


//-----------------Ajax call for GPA Name -----------------------------------//
function populateName(){

	var funct_to_overwrite = "overWriteOldDiv";
	var strAction = 'mailtracking.mra.defaults.maintaincca.populategpaname.do'

  	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}

 function overWriteOldDiv(_tableInfo){

  	var  oldDivId = "gpanameDiv";

    _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];


    var newDivId =_tableInfo.document.getElementById("_gpanameDiv");
	//alert(newDivId.innerHTML);
 	document.getElementById(oldDivId).innerHTML =  newDivId.innerHTML;

 	onScreenLoad();


  }
//------------------------------------------------------------------------//



function viewModeON(targetFormName){

	if(targetFormName.elements.btnDelete!=null && targetFormName.elements.btnAccept!= null && targetFormName.elements.btnReject!=null){
	//targetFormName.elements.btnDelete.disabled="true";
	//targetFormName.elements.btnAccept.disabled="true";
	//targetFormName.elements.btnReject.disabled="true";
	disableField(targetFormName.elements.btnDelete);
	disableField(targetFormName.elements.btnAccept);
	disableField(targetFormName.elements.btnReject);
	}
	//targetFormName.elements.btnAutorate.disabled="true";
	//targetFormName.elements.btnSave.disabled="true";
	//targetFormName.elements.ccaType.disabled="true";
	//targetFormName.elements.revGrossWeight.disabled="true";
	//targetFormName.elements.revChgGrossWeight.disabled="true";
	disableField(targetFormName.elements.btnSave);
	disableField(targetFormName.elements.ccaType);
	disableField(targetFormName.elements.revGrossWeight);
	disableField(targetFormName.elements.revChgGrossWeight);
	//targetFormName.elements.revDueArl.disabled="true";
	//targetFormName.elements.revDuePostDbt.disabled="true";
	//targetFormName.elements.revDStCode.disabled="true";
	//targetFormName.elements.revGpaCode.disabled="true";
	//targetFormName.elements.reason1.disabled="true";
	//targetFormName.elements.reason2.disabled="true";
	//targetFormName.elements.reason3.disabled="true";
	//targetFormName.elements.reason4.disabled="true";
	//targetFormName.elements.remarks.disabled="true";
	disableField(targetFormName.elements.reason1);
	disableField(targetFormName.elements.reason2);
	disableField(targetFormName.elements.reason3);
	disableField(targetFormName.elements.reason4);
	disableField(targetFormName.elements.remarks);
	//var airlineCodeLov = document.getElementById("airlineCodeLov");
  	//disableField(airlineCodeLov);

  	//var airlineLocLov = document.getElementById("airlineLocLov");
  	//disableField(airlineLocLov);

  	var gpaCodelov = document.getElementById("gpaCodelov");
  	disableField(gpaCodelov);





}

function rejectAction(targetFormName){
if(targetFormName.elements.ccaType.value!=''){
if(targetFormName.elements.ccaStatus.value=="N" || targetFormName.elements.ccaStatus.value=="C"){//Modifed for IASCB-2377
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.rejectcca.do');
}
	else{
		showDialog({msg:"<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.ccatypnew" />",type:1,parentWindow:self});
		return;
	}
	}else{
	showDialog({msg:"Please select CCA Type", type:1, parentWindow:self});
	return;
	}
}


 /**
  *Method for excecuting after confirmation
  */
function confirmMessage() {

   frm = targetFormName;
   if(frm.ccaNum.value==""){
     frm.usrCCANumFlg.value="AUTO";
   }else{
     frm.usrCCANumFlg.value="Y";
	}
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.updateccadetails.do');
 }

  /**
  *Method for excecuting after nonconfirmation
  */
function nonconfirmMessage() {

   frm = targetFormName;
     frm.usrCCANumFlg.value="N";
	 disableField(targetFormName.elements.btnSurcharge);

 }






function chkboxUpd(targetFormName){

	if(targetFormName.elements.reason1.checked){
	targetFormName.elements.reason1.value="Y";
	}else{
	targetFormName.elements.reason1.value="N";
	}

	if(targetFormName.elements.reason2.checked){
	targetFormName.elements.reason2.value="Y";
	}else{
	targetFormName.elements.reason2.value="N";
	}
	if(targetFormName.elements.reason3.checked){
	targetFormName.elements.reason3.value="Y";
	}else{
	targetFormName.elements.reason3.value="N";
	}
	if(targetFormName.elements.reason4.checked){
	targetFormName.elements.reason4.value="Y";
	}else{
	targetFormName.elements.reason4.value="N";
	}
	if(targetFormName.elements.isAutoMca.checked){
	
	targetFormName.elements.isAutoMca.value="Y";
	}
	else{
	targetFormName.elements.isAutoMca.value="N";
	}

}

function saveAction(targetFormName){

	var count=0;
	var reasonCodeRestrictionFlag=document.getElementsByName("reasonCodeRestrictionFlag");
	var chkboxes=document.getElementsByName("reasonCheck");
	if(targetFormName.elements.ccaType.value!=''){
	//chkboxUpd(targetFormName);
	
	
	for(var i=0;i<chkboxes.length;i++ ){ 
		if(chkboxes[i].value!=""){
			count=count+1;
		}
		
	}
	if(targetFormName.elements.ccaStatus.value=="N"){
	formatNumberForLocale();
	if(count == 0){
		showDialog({msg:"Select at least one reason code",type:1,parentWindow:self});
	}else{
   if(targetFormName.elements.reasonCodeRestrictionFlag.value=="Y" &&  count>1){
	showDialog({msg:"Select only one reason code",type:1,parentWindow:self});
   }else{
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.saveccadetails.do');
   }
   }
	}
	else{

		showDialog({msg:"<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.ccatypnew" />",type:1,parentWindow:self});
		return;
	}
	}else{
	showDialog({msg:"Please select CCA Type",type:1,parentWindow:self});
	return;
	}
}


function deleteAction(targetFormName){
if(targetFormName.elements.ccaType.value!=''){
if(targetFormName.elements.ccaStatus.value=="N"){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.deleteccadetails.do');
	}
	else{

		showDialog({msg:"<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.ccatypnew" />",type:1,parentWindow:self});
		return;
	}
}else{
showDialog({msg:"Please select CCA Type",type:1,parentWindow:self});
return;
}
}


function listAction(targetFormName){

    IC.util.common.childUnloadEventHandler();
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.updateccadetails.do');
	
}


function screenConfirmDialog(targetFormName, dialogId) {
	while(targetFormName.elements.currentDialogId.value == ''){

	}

	if(targetFormName.elements.currentDialogOption.value == 'Y') {
	    if(dialogId == 'id_3'){

	    }

	}
}


/**
*function to Non-Confirm Dialog
*/
function screenNonConfirmDialog(targetFormName, dialogId) {

	while(targetFormName.elements.currentDialogId.value == ''){

	}

	if(targetFormName.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_3'){


		}

	}
}



function onclickToLOV(){

if(targetFormName.elements.comboFlag.value=="GPA"){
displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',"",'GPACode','1','airlineCode','',0);

		}

		else {
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','1','airlineCode','',0);
		}


	}

function comboChanged(){
	if(targetFormName.elements.issueParty.value=="GPA"){
		targetFormName.elements.comboFlag.value="GPA";
		disableField(targetFormName.elements.revDueArl);
		enableField(targetFormName.elements.revDuePostDbt);
		targetFormName.elements.revDuePostDbt.value="";
	}else{
		targetFormName.elements.comboFlag.value="Airline";
		enableField(targetFormName.elements.revDueArl);
		disableField(targetFormName.elements.revDuePostDbt);
		targetFormName.elements.revDueArl.value="";
	}
}

function showGPAlov(){
	displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',"",'gPACode','1','airlineCode','',0);

}
function CloseAction(){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.closescreen.do');

}
/**
*For Printing CCA Details
*/
function printCCAAction(){

if(isFormModified(targetFormName)) {

//showDialog('Modified Data Exist.Save the data before Print', 1, self);
showDialog({msg:"<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.unsave" />",type:1,parentWindow:self});
}
else{


	generateReport(targetFormName,'/mra.defaults.maintaincca.printccadetails.do');
}


}

function mailDSN(){

 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("dsnNumber");
 var mailDSN =document.getElementsByName("dsnNumber");
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
function onBlurCCA(){
if(targetFormName.elements.ccaType.value=='I'){
targetFormName.elements.revGpaCode.disabled=true;
targetFormName.elements.revCurCode.disabled=true;
//added by A-8527 for BUG_ICRD-346934
document.getElementById("gpacodelov").disabled=true;
document.getElementById("currLov").disabled=true;
targetFormName.elements.revGrossWeight.disabled=false;
}
else if(targetFormName.elements.ccaType.value=='A'){
targetFormName.elements.revGpaCode.disabled=false;
targetFormName.elements.revCurCode.disabled=false;
//added by A-8527 for BUG_ICRD-346934
document.getElementById("gpacodelov").disabled=false;
document.getElementById("currLov").disabled=false;
targetFormName.elements.revGrossWeight.disabled=false;
}
	}
//added for IASCB-858	
function setReasonCheckValue(row,index,value){
	
	if(row.checked){
		targetFormName.elements.reasonCheck[index].value=value;
	}
	else{
		targetFormName.elements.reasonCheck[index].value="";
	}
}	
	
	