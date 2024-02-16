<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{
	disableButtons();

	with(targetFormName){
evtHandler.addEvents("btnSurcharge","viewSurcharge()",EVT_CLICK);
	evtHandler.addEvents("btList","list()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
	evtHandler.addEvents("btnChangeStatus","changeStatus()",EVT_CLICK);
	evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
	evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
	evtHandler.addEvents("originLOV","invokeLov(this,'originLOV')",EVT_CLICK);
	evtHandler.addEvents("destLOV","invokeLov(this,'destLOV')",EVT_CLICK);
	evtHandler.addIDEvents("subClassFilterLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.mailSubclass.value,'Subclass','1','mailSubclass','',0)", EVT_CLICK);
	//evtHandler.addEvents("btnGenerateInv","generateInvoice()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("countrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.country.value,'Country Code','1','country','',0)",EVT_CLICK);
	//evtHandler.addEvents("btnSave","save()",EVT_CLICK);
	evtHandler.addEvents("allCheck","updateHeaderCheckBox(targetFormName, targetFormName.elements.allCheck, targetFormName.elements.check)", EVT_CLICK);
	//evtHandler.addEvents("check","updateCheck()", EVT_CLICK);
	evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.allCheck)", EVT_CLICK);
	//evtHandler.addEvents("gpaCodelov","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCodeFilter.value,'Gpa Code','1','gpaCodeFilter','gpaName','gpaName',0)",EVT_CLICK);
	evtHandler.addIDEvents("gpaCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodeFilter.value,'GPA Code','1','gpaCodeFilter','',0)",EVT_CLICK);
	evtHandler.addEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.elements.dsn.value,'DSN No','1','dsn','consignmentNumber',0)",EVT_CLICK);
	evtHandler.addEvents("btnRerate","reRate()",EVT_CLICK);
	evtHandler.addEvents("btnRouting","navigateToRouting()",EVT_CLICK);
	evtHandler.addEvents("btnVoid","voidAction()",EVT_CLICK);
	evtHandler.addEvents("btnAutoMCA","navigateToMailCorrection()",EVT_CLICK); //Added by A-7929 as part of ICRD-132548

	}
 applySortOnTable("listGPABillTable",new Array("None","String","String","String","String","String","String","String","Number","String","String","Number","Number","String","Number","Number","Number","Number","Number"));

onlistFailure();
}
function onlistFailure(){
if(targetFormName.elements.contractRate.checked){
targetFormName.elements.contractRate.value="Y"
}
if(targetFormName.elements.UPURate.checked){
targetFormName.elements.UPURate.value="Y"
}
}

function viewSurcharge(){
	
	var billingstatus = document.getElementsByName("saveBillingStatus");
	var validBillingStatus="";
if(validateSelectedCheckBoxes(targetFormName,'check',1,1) ){
var surchg=new Array();
surchg=document.getElementsByName("surCharge");
	var chkboxes = document.getElementsByName('check');
	
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				
			 validBillingStatus=billingstatus[index].value;	
				if(validBillingStatus =="VD"){
			showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.voidmailbags' />",type:1,parentWindow:self});
			}
			
				
			if(surchg[index].value==0.0){
			showDialog({msg:"Surcharge does not exist for the selected mail",type:1,parentWindow:self});
			return;
		  	}
			}
			
			else
			{
				
			selIndex=chkboxes[index].value;
			}
			
		
	}
}
	
	openPopUp("mailtracking.mra.gpabilling.listbillingsurcharge.do?selectedRow="+selIndex,430,250);
	
}

}
function invokeLov(obj,name){
var index = obj.id.split(name)[1];

 if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOfficeOfExchange.value,'OfficeOfExchange','0','originOfficeOfExchange','',index);
   }
   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destinationOfficeOfExchange.value,'OfficeOfExchange','0','destinationOfficeOfExchange','',index);
   }
   if(name == "originLOV"){
         displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.origin.value,'Airport','0','origin','',index);
   }
   if(name == "destLOV"){
         displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.destination.value,'Airport','0','destination','',index);
   }
}


//Function for displaying BillingEntries

function list(){

targetFormName.elements.lastPageNum.value=0;
targetFormName.elements.displayPage.value=1;
//Added by A-6991 for ICRD-137019 Starts
 if(targetFormName.elements.contractRate.checked ==false && targetFormName.elements.UPURate.checked ==false){
showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.norecords' />",type:1,parentWindow:self});
clear();
}
else{
if(targetFormName.elements.contractRate.checked){
targetFormName.elements.contractRate.value="Y"
}
if(targetFormName.elements.UPURate.checked){
targetFormName.elements.UPURate.value="Y"
}
submitForm(targetFormName,'mailtracking.mra.gpabilling.listbillingentries.do?navigationMode=FILTER');
}
//Added by A-6991 for ICRD-137019 Ends
}

function submitPage1(strLastPageNum,strDisplayPage){

	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName, 'mailtracking.mra.gpabilling.listbillingentries.do?navigationMode=NAVIGATION');
}
function clear(){

submitForm(targetFormName,'mailtracking.mra.gpabilling.clearbillingentries.do');

}

function changeStatus(){

	//var isSelect='false';
	var isCorrect='false';
	var isSame='true';
	var isBill='';
	var count=0;
	var billingstatus=new Array();
	var check=new Array();
	var dsn=new Array();
	var p=new Array();
	var dsns= new Array();

	billingstatus=document.getElementsByName("saveBillingStatus");
	dsn=document.getElementsByName("despatchNumber");
	check=document.getElementsByName("check");
	//ccaRefNum= document.getElementsByName('ccaReferenceNumber');
	var fromScreen="fromGpaBilling";
	var selectedrows="";

// This is find the first checked status
	if(check.length>0){
	for(var i=0;i<check.length;i++){
		if(check[i].checked){
			var status=billingstatus[i].value;
			break;
		}
	    }
	}


//This to check if different status are selected
	if(check.length>0){

		for(var i=0;i<check.length;i++){
			if(check[i].checked){

			if(billingstatus[i].value!=status){
			isSame='false';
			break;
			}

			}
		}

	}
//This gives an alert if different status are selected
	if(isSame=='false'){
	showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.selectdespatches' />",type:1,parentWindow:self});

	}
	else{
	if(status=='BB')
	isBill='billable';
	else if(status=='OH')
	isBill='onhold';
	else if(status=='PB')
	isBill='proformabillable';
	else if(status=='PO')
	isBill='proformaonhold';
	else if(status=='WP')
	isBill='withdrawnproforma';
	else if(status=='DB')
	isBill='directbillable';
	/*else if(status=='WD')
	isBill='withdrawndirect';*/
     else if(status=='VD')
	 {
   showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.voidmailbags' />",type:1,parentWindow:self});
	 }

	else
	isBill='billed';
	}

//This gives an alert if actual cca is present

	if(check.length>0){
	for(var i=0;i<check.length;i++){
			if(check[i].checked){

	/*if(ccaRefNum[i].value!=""){


		showDialog("Cannot change the status of despatches which contains an actual cca",1,self);

		return;

	}*/
}
}
}

//This to check whether only BB and OH status are selected
	for(var i=0;i<check.length;i++){

		if(check[i].checked){

		//isSelect='true';
		if(billingstatus[i].value=='BB' || billingstatus[i].value=='OH'
		|| billingstatus[i].value=='PO'|| billingstatus[i].value=='WP' || billingstatus[i].value=='DB'
		|| billingstatus[i].value=='WD'){



		if(selectedrows != "")

		selectedrows = selectedrows+","+check[i].value;

		else if(selectedrows== "")

		selectedrows =check[i].value;

		dsns[i]=dsn[i].value;

		isCorrect='true';

		}



		}

		}




	if(validateSelectedCheckBoxes(targetFormName,'check','','1')){

	if(isCorrect=='true' && isSame=='true'){

	//alert("selectedrows"+selectedrows);
	openPopUp("mailtracking.mra.defaults.changestatuspopup.do?select="+p+"&selectedrows="+selectedrows+"&despatchNumbers="+dsns+"&isBillable="+isBill+"&fromScreen="+fromScreen,500,200);

	}

	else if(isCorrect=='false'){
	showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.statuscantbechanged' />",type:1,parentWindow:self});
	}
    }

}
function generateInvoice(){
//submitForm(targetFormName,'mailtracking.mra.gpabilling.generateinvoice.onScreenLoad.do');


}
function save(){
submitForm(targetFormName,'mailtracking.mra.gpabilling.savebillingentries.do');

}

function closeScreen(){

submitForm(targetFormName,'mailtracking.mra.gpabilling.billingentries.close.do');

}

function disableButtons(){


//added for resolution starts
//var clientHeight = document.body.clientHeight;
//var clientWidth = document.body.clientWidth;

//alert(clientHeight);
//document.getElementById('contentdiv').style.height = ((clientHeight*84)/100)+'px';
//document.getElementById('outertable').style.height=((clientHeight*83)/100)+'px';
//document.getElementById('div1').style.height = ((clientHeight*56)/100)+'px';

//alert(document.getElementById('outertable').style.height);

//var pageTitle=30;
//var filterlegend=50;
//var filterrow=45;
//var bottomrow=45;
//var height=(clientHeight*83)/100;
//var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
//document.getElementById('div1').style.height=tableheight+'px';
// added for resolution ends

targetFormName.elements.fromDate.focus();
var status=targetFormName.elements.screenStatus.value;
var privilege=targetFormName.elements.hasPrivilege.value;
//alert(privilege);
if(status=="screenload" ||status=="clear"){
disableField(targetFormName.btnChangeStatus);
disableField(targetFormName.btnSurcharge);
disableField(targetFormName.btnRerate);
disableField(targetFormName.btnVoid);
disableField(targetFormName.btnRouting);
disableField(targetFormName.btnAutoMCA);  //Added by A-7929 as part of ICRD-132548
//document.forms[1].btnGenerateInv.disabled=true;
//targetFormName.btnSave.disabled=true;
}else if(status=="list"){
enableField(targetFormName.btnChangeStatus);
enableField(targetFormName.btnSurcharge);
enableField(targetFormName.btnRerate);
enableField(targetFormName.btnVoid);
enableField(targetFormName.btnRouting);
enableField(targetFormName.btnAutoMCA);//Added by A-7929 as part of ICRD-132548
if(privilege=="NO"){
disableField(targetFormName.btnAutoMCA);
}
//enableField(document.forms[1].btnGenerateInv);
//enableField(targetFormName.btnSave);
}

}



function updateCheck(){
//alert('update');
  var chkBoxIds = document.getElementsByName('check');
	    var isChecked = 0;
	    var length= chkBoxIds.length;

		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				isChecked = isChecked + 1;
			}

		}

		if(isChecked != length){
		targetFormName.elements.allCheck.checked=false;
		}

}


function reRate(){

		var chkBoxIds = document.getElementsByName('check');
		var billingstatus = document.getElementsByName("saveBillingStatus");
		var validForVoid = false;
		var billableExists = false;
		var onHoldExists = false;
		var selected = "";
	    var isChecked = 0;
		var ccaRefNumber=document.getElementsByName("ccaReferenceNumber");
		var mcastatus ="";
		var validBillingStatus="";
		var validMCAstatus=true;

		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				isChecked = isChecked + 1;
				if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}


			if(validForVoid == false && billingstatus[i].value =='VD'){
				validForVoid=true;
			}
			if(billableExists == false && billingstatus[i].value == 'BB'){
				billableExists = true;
			}
			if(onHoldExists == false && billingstatus[i].value == 'OH'){
				onHoldExists = true;
			}
			}
		}
		if(chkBoxIds.length>0){
	for(var i=0;i<chkBoxIds.length;i++){
		if(chkBoxIds[i].checked){
        validBillingStatus=billingstatus[i].value;
		mcastatus=mcastatus+"," +ccaRefNumber[i].value;
   if(mcastatus.length==1){
			validMCAstatus = false;
			break;

	      }
	   }

	  }
	}
	if(isChecked == 0){
			showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.selectcheckbox'/>",type:1,parentWindow:self});
		}else if(validBillingStatus=='BD' || validBillingStatus=='PB') {
		     showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.billed'/>",type:1,parentWindow:self});
		}else if(validBillingStatus=='WD') {
		     showDialog({msg:"Withdrawn mails cannot be re-rated",type:1,parentWindow:self});
		}else if(validBillingStatus=='PB') {
		     showDialog({msg:"Proforma Billed mails cannot be re-rated",type:1,parentWindow:self});
		}else if(validBillingStatus=='RR') {
		     showDialog({msg:"To be re-rated mails cannot be re-rated",type:1,parentWindow:self});
		}else if(validForVoid==true && billableExists==false) {
		     showDialog({msg:"Mailbag/(s) is voided from MRA",type:1,parentWindow:self});
		}else if(onHoldExists==true){
		     showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.onhold'/>",type:1,parentWindow:self});
		}else if(validMCAstatus==true){
		     showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.mcastatus'/>",type:1,parentWindow:self});
		}else if(validMCAstatus==false){
			submitForm(targetFormName,'mailtracking.mra.gpabilling.rerate.do?&selectedRow='+selected);

		}

}
//Added by A-7929 for ICRD - 224586  starts
function callbackGPABillingEntries(collapse,collapseFilterOrginalHeight,mainContainerHeight){
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               //IC.util.widget.updateTableContainerHeight(jquery("div1"),+collapseFilterOrginalHeight);

}
//Added by A-7929 for ICRD - 224586 ends

function navigateToRouting(){
	var validBillingStatus="";
	 var billingstatus = document.getElementsByName("saveBillingStatus");
	if(validateSelectedCheckBoxes(targetFormName,'check',1,1) ){
		var mailBagId = "";
		var chkBoxIds = document.getElementsByName('check');
		var mailBagIds = document.getElementsByName("mailBagId");
		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				mailBagId = mailBagIds[i].value;
				break;
			}
		}
		
		if(chkBoxIds.length>0){
	   for(var i=0;i<chkBoxIds.length;i++){
		if(chkBoxIds[i].checked){
        validBillingStatus=billingstatus[i].value;
	
  
	   }

	  }
	}
		
		
		if(mailBagId!="" && validBillingStatus =="VD"){
			showDialog({msg:"<common:message bundle='gpabillingentries' key='mailtracking.mra.gpabilling.billingentries.msg.err.voidmailbags' />",type:1,parentWindow:self});
		}
		
		else {
		
		if(mailBagId != ""){
			submitForm(targetFormName,'mailtracking.mra.gpabilling.routing.do?&parameterValue='+mailBagId);
		}
	}
}}
function confirmMessage() {
	submitForm(targetFormName,'mailtracking.mra.gpabilling.voidaction.do?');
}
function nonconfirmMessage() {
	submitForm(targetFormName,'mailtracking.mra.gpabilling.listbillingentries.do?navigationMode=FILTER');
}
function screenConfirmDialog(targetFormName, dialogId) {
	if(dialogId == 'id_1'){
		submitForm(targetFormName,'mailtracking.mra.gpabilling.voidaction.do');
		}
	else{

	}
}
function screenNonConfirmDialog(targetFormName, dialogId) {
	if(targetFormName.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}
function voidAction(){
	var chkBoxIds = document.getElementsByName('check');
	var selected = "";
	var validBillingStatus="";
	var billingstatus = document.getElementsByName("saveBillingStatus");
	if(validateSelectedCheckBoxes(targetFormName,'check','','1')){
		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				if(billingstatus[i].value == 'OH' || billingstatus[i].value =='VD'){
				validBillingStatus=billingstatus[i].value;
				}
				if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}
			}
		}
		if(selected.length > 0){
			if(validBillingStatus=='VD') {
		     showDialog({msg:"Mailbag/(s) already voided from MRA",type:1,parentWindow:self});
				return;
			}else if (validBillingStatus=='OH'){
				showDialog({msg:"Mailbags on-hold cannot be voided",type:1,parentWindow:self});
			 return;
		}
			submitForm(targetFormName,'mailtracking.mra.gpabilling.voidaction.do?&selectedRow='+selected);
		}
	}
}
//Added by A-7929 as part of ICRD-132548  starts
function navigateToMailCorrection(){
    //var fromScreen = "listbillingentries";
	//if(validateSelectedCheckBoxes(targetFormName,'check','',1)){
 submitForm(targetFormName,'mailtracking.mra.gpabilling.automca.do');
 //submitForm(targetFormName,'mailtracking.mra.gpabilling.automca.do?&parameterValue='+fromScreen');
 //}
}
//Added by A-7929 as part of ICRD-132548  ends
