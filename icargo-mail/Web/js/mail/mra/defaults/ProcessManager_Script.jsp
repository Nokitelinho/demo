<%@ include file="/jsp/includes/js_contenttype.jsp" %>




function screenSpecificEventRegister() {
	var frm = targetFormName;

	with(frm) {
		evtHandler.addEvents("btnClose","closefn()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addEvents("btnExecute","executefn()",EVT_CLICK);
        evtHandler.addIDEvents("rerateAirlineCodeLov1","displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].toAirlineCode.value,'Airline Code','1','toAirlineCode','',0)",EVT_CLICK);
	    evtHandler.addIDEvents("rerateAirlineCodeLov2","displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].fromAirlineCode.value,'Airline Code','1','fromAirlineCode','',0)",EVT_CLICK);
		evtHandler.addIDEvents("gpaCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);
		evtHandler.addIDEvents("paCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.paCode.value,'GPA Code','1','paCode','',0)",EVT_CLICK);
	    evtHandler.addIDEvents("mailBaglov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.elements.mailbag.value,'MailBag Num','1','mailbag','',0)",EVT_CLICK);
	    evtHandler.addEvents("mailOOELov","mailOrigin()",EVT_CLICK);
		evtHandler.addEvents("mailDOELov","mailDestination()",EVT_CLICK);
		
		evtHandler.addEvents("upliftAirportLov","upliftAirport()",EVT_CLICK);
		evtHandler.addEvents("dischargeAirportLov","dischargeAirport()",EVT_CLICK);
		evtHandler.addEvents("upliftAirportExpLov","upliftAirportExp()",EVT_CLICK);
		evtHandler.addEvents("dischargeAirportExpLov","dischargeAirportExp()",EVT_CLICK);
		evtHandler.addEvents("transferPALov","showTransferPA()",EVT_CLICK);
		evtHandler.addEvents("transferPAExpLov","showTransferPAExp()",EVT_CLICK);
		
		evtHandler.addEvents("originAirportExceptionLov","originAirport()",EVT_CLICK);
		evtHandler.addEvents("destinationAirportExceptionLov","destinationAirport()",EVT_CLICK);
		
		evtHandler.addIDEvents("transferAirlineExpLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.transferAirlineException.value,'Airline Code','1','transferAirlineException','',0)",EVT_CLICK);
		evtHandler.addIDEvents("transferAirlineLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.transferAirlineReRate.value,'Airline Code','1','transferAirlineReRate','',0)",EVT_CLICK);
		evtHandler.addEvents("mailOOERRLov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOEReRate.value,'Exchange Office','1','originOEReRate','',0)",EVT_CLICK);
		evtHandler.addEvents("mailDOERRLov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destinationOEReRate.value,'Exchange Office','1','destinationOEReRate','',0)",EVT_CLICK);
		
		
		//Modified by A-8236 for ICRD-255110
		evtHandler.addEvents("mailOOELov1","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originforProrate.value,'Airport','1','originforProrate','',0)",EVT_CLICK);
		//Modified by A-8236 for ICRD-255110
		evtHandler.addEvents("mailDOELov1","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destinationforProrate.value,'Airport','1','destinationforProrate','',0)",EVT_CLICK);
		evtHandler.addEvents("clearanceperiodlov","displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].elements.clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
		 evtHandler.addEvents("processOneTime","togglePanel(1,targetFormName.elements.processOneTime)",EVT_CHANGE);
		  evtHandler.addEvents("processOneTime","importMailToMRAFilter()",EVT_CHANGE);
		  evtHandler.addEvents("gpaCode","tabout()",EVT_BLUR);//Added for icrd-254820
          evtHandler.addEvents("toAirlineCode","tabout()",EVT_BLUR);//Added for icrd-254820
          evtHandler.addEvents("fromAirlineCode","tabout()",EVT_BLUR);//Added for icrd-254820

		 
	}
	 disablefilter();
	 onScreenLoad();

}
function onScreenLoad()
{
 togglePanel(1,targetFormName.elements.processOneTime);
}
function executefn(){
	var frm = targetFormName;
	var filter =targetFormName.elements.processOneTime.value;
	
	if(filter=='FP'){

			var xtrafn="countAlerter";
			var elemFull = document.getElementById("fullProgressBar");
			var elem1 = document.getElementById("myBarValue");
			var numberElement = document.getElementById("intialProgress");
			
			numberElement.innerHTML=0;
			elem1.innerHTML = 0+'%';
			elemFull.style.width=0+'%';
			targetFormName.elements.completionFlag.value="I";
			asyncSubmit(targetFormName,"mailtracking.mra.defaults.processmanager.finalizepass.do",xtrafn,null,null,null);

	}else{
	
	if(filter=='I'){
		if(  (targetFormName.elements.flightNumber.value=='' || targetFormName.elements.carrierCode.value==''  || targetFormName.elements.flightDate.value=='' )  &&
			 (targetFormName.elements.mailbag.value=='') &&
			 (targetFormName.elements.fromDateImportmail.value=='' || targetFormName.elements.toDateImportmail.value=='') ){
			showDialog({msg:'Please enter Flight Number and Flight Date or Mailbag ID or Consignment Date range  or Transaction Date range',type:1,parentWindow:self});
			return;
		}
	}

	frm.action="mailtracking.mra.defaults.processmanager.excecute.do";
	frm.submit();
	
	}
}

function countAlerter(_tableInfo){
		 var xtrafn="countAlerter";
		 
		 if(_tableInfo.document.getElementById("completionFlagAjax").innerHTML=="N"){
			 
		var countOfInvoices =_tableInfo.document.getElementById("countOfInvoicesAjax").innerHTML;
		
		 targetFormName.elements.nextFetchValue.value=_tableInfo.document.getElementById("ajaxNextFetch").innerHTML;
		 
		if( targetFormName.elements.completionFlag.value=="I"){
			targetFormName.elements.completionFlag.value="N";
			targetFormName.elements.totalInvoiceCount.value=_tableInfo.document.getElementById("totalInvoicesAjax").innerHTML;
		  }

		var elemFull = document.getElementById("fullProgressBar");
		var numberElement = document.getElementById("intialProgress");

		var widthFull = elemFull.style.width;
		var currentCount=Number(countOfInvoices);
		var totalCount = targetFormName.elements.totalInvoiceCount.value;
		var currentProgress=0;
		if(totalCount!=0){
		currentProgress=Math.round((currentCount/totalCount)*100);
		}

		targetFormName.elements.finalizedInvoiceCount.value=currentCount;

		widthFull = widthFull +countOfInvoices;
		numberElement.innerHTML = currentCount;

		elemFull.style.width = currentProgress + '%';
		var elem1 = document.getElementById("myBarValue");
		elem1.innerHTML = currentProgress+'%';
		asyncSubmit(targetFormName,"mailtracking.mra.defaults.processmanager.finalizepass.do",xtrafn,null,null,null);
		
		
	 }else if(_tableInfo.document.getElementById("completionFlagAjax").innerHTML=="Y"){

		var elem1 = document.getElementById("myBarValue");
		var elemFull = document.getElementById("fullProgressBar");

		document.getElementById("intialProgress").innerHTML=Number(_tableInfo.document.getElementById("countOfInvoicesAjax").innerHTML);
		
		var currentCount=Number(document.getElementById("intialProgress").innerHTML);
		elemFull.style.width = 100 + '%';
		elem1.innerHTML = 100+'%';
		showDialog({
				msg :currentCount+' Invoices sent for Finalisation',
				type:2,
				title:'Finalize PASS Invoices',
				parentWindow:self
			   });

	 }else{
		 var ERR=_tableInfo.document.getElementById("completionFlagAjax").innerHTML.split('-');
		
	 }
		 
		 
		 
		 
		 
		 
}


function closefn(){
	var frm = targetFormName;
	frm.action="mailtracking.mra.defaults.processmanager.closecommand.do";
	frm.submit();
}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.processOneTime.disabled == false){
			targetFormName.elements.processOneTime.focus();
		}
	}
}

function disablefilter(){
	var filter =targetFormName.elements.processOneTime.value;

	if(filter=='I'){//changed as part of ICRD-250469
		targetFormName.elements.carrierCode.disabled=false;
		targetFormName.elements.flightNumber.disabled=false;
		targetFormName.elements.flightDate.disabled=false
		targetFormName.elements.flightDate_BTN.disabled=false;
		targetFormName.elements.carrierCode.value="";
		targetFormName.elements.flightNumber.value="";
		targetFormName.elements.mailbag.value="";
		targetFormName.elements.flightDate.value="";
	}else{// only import Mail to MRA  requires the filter fields,Generate Claim File, Import to Reconcile etc inputs are not required.
		targetFormName.elements.carrierCode.disabled=true;
		targetFormName.elements.flightNumber.disabled=true;
		targetFormName.elements.flightDate.disabled=true;
		targetFormName.elements.flightDate_BTN.disabled=true;
	}

	// initial focus on page load
	if(targetFormName.elements.processOneTime.disabled == false) {
	   targetFormName.elements.processOneTime.focus();
	}
}

function upliftAirport(){
	var frm=targetFormName;
	 displayLOV('showAirport.do','N','Y','showAirport.do',frm.upliftAirportReRate.value,'Airport Code','','upliftAirportReRate','',0);
}
function upliftAirportExp(){
	var frm=targetFormName;
	 displayLOV('showAirport.do','N','Y','showAirport.do',frm.upliftAirportException.value,'Airport Code','','upliftAirportException','',0);
}

function originAirport(){
	var frm=targetFormName;
	 displayLOV('showAirport.do','N','Y','showAirport.do',frm.originAirportException.value,'Airport Code','','originAirportException','',0);
}

function destinationAirport(){
	var frm=targetFormName;
	 displayLOV('showAirport.do','N','Y','showAirport.do',frm.destinationAirportException.value,'Airport Code','','destinationAirportException','',0);
}


function dischargeAirport(){
	var frm=targetFormName;
	 displayLOV('showAirport.do','N','Y','showAirport.do',frm.dischargeAirportReRate.value,'Airport Code','','dischargeAirportReRate','',0);
}

function dischargeAirportExp(){
	var frm=targetFormName;
	 displayLOV('showAirport.do','N','Y','showAirport.do',frm.dischargeAirportException.value,'Airport Code','','dischargeAirportException','',0);
}

function showTransferAirline(){
	var frm=targetFormName;
	 displayLOV('showAirline.do','N','Y','showAirline.do',frm.transferAirlineReRate.value,'Airline Code','','transferAirlineReRate','',0);
}
function showTransferPA(){
	var frm=targetFormName;
	 displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',frm.transferPAReRate.value,'GPA Code','','transferPAReRate','',0);
}

function showTransferAirlineExp(){
	var frm=targetFormName;
	 displayLOV('showAirline.do','N','Y','showAirline.do',frm.transferAirlineException.value,'Airline Code','','transferAirlineException','',0);
}
function showTransferPAExp(){
	var frm=targetFormName;
	 displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',frm.transferPAException.value,'GPA Code','','transferPAException','',0);
}


function mailOrigin(){
var frm=targetFormName;
 if(frm.origin.value=="CNT"){
           
         displayLOV('showCountry.do','N','Y','showCountry.do',frm.originOfficeOfExchange.value,'Country Code','','originOfficeOfExchange','',0);;

}else if(frm.origin.value=="CTY")
{

        displayLOV('showCity.do','N','Y','showCity.do',frm.originOfficeOfExchange.value,'City Code','','originOfficeOfExchange','',0);
}
else if(frm.origin.value=="ARP")
{

        displayLOV('showAirport.do','N','Y','showAirport.do',frm.originOfficeOfExchange.value,'Airport Code','','originOfficeOfExchange','',0);
}
}


function mailDestination(){
var frm=targetFormName;
 if(frm.destination.value=="CNT"){
            
         displayLOV('showCountry.do','N','Y','showCountry.do',frm.destinationOfficeOfExchange.value,'Country Code','','destinationOfficeOfExchange','',0);;

}else if(frm.destination.value=="CTY")
{
        displayLOV('showCity.do','N','Y','showCity.do',frm.destinationOfficeOfExchange.value,'City Code','','destinationOfficeOfExchange','',0);
}
else if(frm.destination.value=="ARP")
{

        displayLOV('showAirport.do','N','Y','showAirport.do',frm.destinationOfficeOfExchange.value,'Airport Code','','destinationOfficeOfExchange','',0);
}
}

function togglePanel(iState,comboObj) // 1 visible, 0 hidden
	{
var frm=targetFormName;
		if(comboObj != null) {

	   	    var divID = comboObj.value;

			var divValues ='PRCSMGR5';
			var divValue = 'PRCSMGR6'
			var divValueIC = 'PRCSMGR7';
			var value='top';
			var length = divValues.length;
			var divSisMal = 'SISOWBLG';
			var divImpMal = 'PRCSMGR1';
			var divMalbag = 'PRCSMGRMAL';
			var divFinalizePASS = 'FP';
			
			
				if(divID=='RR') {
				
					document.getElementById(divValues).style.display = "block";
					document.getElementById(divValueIC).style.display = "none";
					document.getElementById(value).style.display = "none";
					document.getElementById(divValue).style.display = "none";
					document.getElementById(divSisMal).style.display = "none";
					targetFormName.elements.processOneTime.value='RR';
					document.getElementById(divImpMal).style.display = "none";
					document.getElementById(divMalbag).style.display = "none";
					document.getElementById(divFinalizePASS).style.display = "none";
					
				}
				else if(divID=='RP'){
				    document.getElementById(divValue).style.display = "block";
					 document.getElementById(divValueIC).style.display = "none";
				    document.getElementById(value).style.display = "none";
					document.getElementById(divValues).style.display = "none";
					document.getElementById(divSisMal).style.display = "none";
					targetFormName.elements.processOneTime.value='RP';
					document.getElementById(divImpMal).style.display = "none";
					document.getElementById(divMalbag).style.display = "none";
					document.getElementById(divFinalizePASS).style.display = "none";
				}
				else if(divID=='S'){
					document.getElementById(divSisMal).style.display = "block";
					document.getElementById(value).style.display = "none";
					document.getElementById(divValues).style.display = "none";
					document.getElementById(divValue).style.display = "none";
					targetFormName.elements.processOneTime.value='S';
					document.getElementById(divImpMal).style.display = "none";
					document.getElementById(divMalbag).style.display = "none";
					document.getElementById(divFinalizePASS).style.display = "none";
					document.getElementById(divValueIC).style.display = "none";	
				}
				else if (divID=='IC') {
				    document.getElementById(divValue).style.display = "none";
					 document.getElementById(divValues).style.display = "none";
				    document.getElementById(value).style.display = "block";
					document.getElementById(divValueIC).style.display = "block";
					targetFormName.elements.processOneTime.value='IC';				
					document.getElementById(divImpMal).style.display = "none";
					document.getElementById(divMalbag).style.display = "block";					
					document.getElementById(divFinalizePASS).style.display = "none";	
					document.getElementById(divSisMal).style.display = "none";					
					
					
				}
				else if (divID=='I'){
					document.getElementById(divValues).style.display = "none";
					document.getElementById(value).style.display = "block"
					document.getElementById(divValue).style.display = "none";
					document.getElementById(divSisMal).style.display = "none";
					document.getElementById(divValueIC).style.display = "none";					
					document.getElementById(divImpMal).style.display = "block";
					document.getElementById(divMalbag).style.display = "block";	
					targetFormName.elements.processOneTime.value='I';	
					document.getElementById(divFinalizePASS).style.display = "none";					
					
				}
				else if (divID=='FP'){
					document.getElementById(divValues).style.display = "none";
					document.getElementById(value).style.display = "none"
					document.getElementById(divValue).style.display = "none";
					document.getElementById(divSisMal).style.display = "none";
					document.getElementById(divValueIC).style.display = "none";					
					document.getElementById(divImpMal).style.display = "none";
					document.getElementById(divMalbag).style.display = "none";	
					targetFormName.elements.processOneTime.value='FP';
					document.getElementById(divFinalizePASS).style.display = "block";					
					
				}
				else {
					document.getElementById(divValues).style.display = "none";
					document.getElementById(value).style.display = "block"
					document.getElementById(divValue).style.display = "none";
					document.getElementById(divSisMal).style.display = "none";
					document.getElementById(divValueIC).style.display = "none";
					document.getElementById(divImpMal).style.display = "none";
					document.getElementById(divMalbag).style.display = "none";
					document.getElementById(divFinalizePASS).style.display = "none";
		    	}

			
		}

}
function importMailToMRAFilter(){//added by A-7371 as part of ICRD-250469

    var filter =targetFormName.elements.processOneTime.value; 
    if(filter=='I'){

        targetFormName.elements.carrierCode.disabled=false;
		targetFormName.elements.flightNumber.disabled=false;
		targetFormName.elements.flightDate.disabled=false
		targetFormName.elements.flightDate_BTN.disabled=false;
		targetFormName.elements.carrierCode.value="";
		targetFormName.elements.flightNumber.value="";
		targetFormName.elements.flightDate.value="";
		targetFormName.elements.mailbag.value="";
	}else if(filter=='IC'){
	    targetFormName.elements.carrierCode.disabled=false;
		targetFormName.elements.flightNumber.disabled=false;
		targetFormName.elements.flightDate.disabled=false
		targetFormName.elements.flightDate_BTN.disabled=false;
		targetFormName.elements.mailToDate_BTN.disabled=false;
		targetFormName.elements.mailFromDate_BTN.disabled=false;
		targetFormName.elements.carrierCode.value="";
		targetFormName.elements.flightNumber.value="";
		targetFormName.elements.flightDate.value="";
		targetFormName.elements.mailbag.value="";
	}
	else{// only import Mail to MRA  requires the filter fields,Generate Claim File, Import to Reconcile etc inputs are not required.
		targetFormName.elements.carrierCode.disabled=true;
		targetFormName.elements.flightNumber.disabled=true;
		targetFormName.elements.flightDate.disabled=true;
		targetFormName.elements.flightDate_BTN.disabled=true;
		
	}
}

function tabout(){//Added for icrd-254820
var frm=targetFormName;
if(frm.elements.gpaCode.value!=null&&frm.gpaCode.value.length>0)
{
frm.elements.toAirlineCode.disabled=true;
frm.elements.fromAirlineCode.disabled=true;
}
else if(frm.elements.toAirlineCode.value!=null&&frm.toAirlineCode.value.length>0)
{
frm.elements.gpaCode.disabled=true;
frm.elements.fromAirlineCode.disabled=true;
}
else if(frm.elements.fromAirlineCode.value!=null&&frm.fromAirlineCode.value.length>0)
{
frm.elements.gpaCode.disabled=true;
frm.elements.toAirlineCode.disabled=true;
}
else if(frm.elements.gpaCode.value.length==0&&frm.elements.toAirlineCode.value.length==0&&frm.elements.fromAirlineCode.value.length==0)
{
frm.elements.gpaCode.disabled=false;
frm.elements.toAirlineCode.disabled=false;
frm.elements.fromAirlineCode.disabled=false;
	}
}
function confirmMessage() {
	var frm = targetFormName;
	frm.action="mailtracking.mra.defaults.processmanager.excecute.do?byPassWarning="+"Y";
	frm.submit();
}
function nonconfirmMessage() {
	var frm = targetFormName;
	targetFormName.elements.clearancePeriod.value="";
	targetFormName.elements.byPassWarning.value="";
	frm.action="mailtracking.mra.defaults.processmanager.onScreenLoad.do";
	frm.submit();
	onScreenLoad();
}
function screenConfirmDialog(targetFormName, dialogId) {
	var frm = targetFormName;
	if(dialogId == 'id_1'){
		frm.action="mailtracking.mra.defaults.processmanager.excecute.do?byPassWarning="+"Y";
		frm.submit();
		}
	else{
	}
}
function screenNonConfirmDialog(targetFormName, dialogId) {
	if(targetFormName.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){
		}
		if(dialogId == 'id_2'){
		}
		if(dialogId == 'id_3'){
		}
	}
}