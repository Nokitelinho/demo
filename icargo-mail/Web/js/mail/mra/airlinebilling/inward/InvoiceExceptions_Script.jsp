<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){

	setscreenstatus();
	with(targetFormName){

		evtHandler.addEvents("btnList","doList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","doClear()",EVT_CLICK);
		evtHandler.addEvents("btnDelete","doDelete()",EVT_CLICK);
		//evtHandler.addEvents("btnAccept","doAccept()",EVT_CLICK);
		evtHandler.addEvents("btnIssueRejectionMemo","doIssueRM()",EVT_CLICK);
		//evtHandler.addEvents("btnViewRejectionMemo","doViewRM()",EVT_CLICK);
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btnViewExcDetails","doViewExcDetails()",EVT_CLICK);

		//evtHandler.addEvents("btnPrint","onClickPrint('PrintRejection')",EVT_CLICK);
		evtHandler.addEvents("btnPrint","onClickPrint()",EVT_CLICK);

		evtHandler.addIDEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.airlineCode.value,'airlineCode','1','airlineCode','',0)",EVT_CLICK);
		evtHandler.addIDEvents("invoiceNumberlov","invoiceLOV()",EVT_CLICK);
		evtHandler.addIDEvents("clearencePeriodlov","clearanceLOV()",EVT_CLICK);
		evtHandler.addIDEvents("rejectionMemoNumberlov","rejectionLOV()",EVT_CLICK);

	}
	targetFormName.elements.airlineCode.focus();
	screenload();

}
function invoiceLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.elements.invoiceNumber.value,'invoiceNumber','1','invoiceNumber','',0,_reqHeight);
}
function clearanceLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',targetFormName.elements.clearencePeriod.value,'clearencePeriod','1','clearencePeriod','',0,_reqHeight);//Modified as part of ICRD-101583
}
function rejectionLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('mra.airlinebilling.defaults.showMemoLov.do','N','Y','mra.airlinebilling.defaults.showMemoLov.do',targetFormName.elements.rejectionMemoNumber.value,'rejectionMemoNumber','1','rejectionMemoNumber','',0,_reqHeight);
}

function screenload(){

//added for resolution starts
//var clientHeight = document.body.clientHeight;
//var clientWidth = document.body.clientWidth;

//alert(clientHeight);
//document.getElementById('contentdiv').style.height = ((clientHeight*85)/100)+'px';
//document.getElementById('outertable').style.height=((clientHeight*81)/100)+'px';

//alert(document.getElementById('outertable').style.height);

//var pageTitle=30;
//var filterlegend=120;
//var filterrow=30;
//var bottomrow=45;
//var height=(clientHeight*81)/100;
//var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
//document.getElementById('div1').style.height=tableheight+'px';
// added for resolution ends

	frm=targetFormName;
	//alert(frm.cn66CloseFlag.value);
		if(frm.cn66CloseFlag.value=='true'){


			frm.cn66CloseFlag.value='cn66close';
			//doList();

	}
}

/**
*List function done here
*/
function doList(){
	frm=targetFormName;
	frm.fromScreenFlag.value="invoiceexception";
	/*if(frm.airlineCode.value==''){
		showDialog('Airline Code is Mandatory',1,self);
	}
	else{*/
		submitForm(frm,'mailtracking.mra.airlinebilling.inward.invoiceexceptionslist.do');
	//}
}
function submitPage(lastPg,displayPg){

var frm = targetFormName;
	frm.lastPageNumber.value=lastPg;
	frm.displayPage.value=displayPg;
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.invoiceexceptionslist.do');
}

/**
*Clear function done here
*/
function doClear(){

	frm=targetFormName;
	submitForm(frm,'mailtracking.mra.airlinebilling.inward.invoiceexceptionsclear.do');
}


/**
*Delete function done here
*/
function doDelete(){
	frm=targetFormName;
	if(validateSelectedCheckBoxes(targetFormName,'rowId','',1)){
		submitForm(frm,'mailtracking.mra.airlinebilling.inward.invoiceexceptionsdelete.do');
	}
}


/**
*Accept function done here
*/
/*function doAccept(){
	frm=targetFormName;
	var p=new Array();
	var rowId=new Array();
	var expStatus=new Array();
	var flag=0;
 	expStatus=document.getElementsByName('expStatus');

	rowId=document.getElementsByName("rowId");

	for(var i=0;i<rowId.length;i++){

						if(rowId[i].checked){

						p[i]=rowId[i].value;
						if(expStatus[i].value!="E"){
						   flag++;
					   	}
					}
				}

			  if(flag>0){

							showDialog("Excpetions with satus exception can only be accepted",1,self);
			            }
			  else{
				if(validateSelectedCheckBoxes(targetFormName,'rowId',1,1)){
				openPopUp("mailtracking.mra.airlinebilling.inward.remarkspopup.do?rowId="+p,400,150);
				}
		}
}*/
/**
*IssueRM function done here
*/
function doIssueRM(){
	frm=targetFormName;
	var cn66flag=targetFormName.elements.cn66CloseFlag.value;
	if(validateSelectedCheckBoxes(targetFormName,'rowId',1,1)){
		submitForm(frm,'mailtracking.mra.airlinebilling.inward.invoiceexceptionsissuerm.do?fromScreenFlag='+"invexpscreen"+'&cn66CloseFlag='+cn66flag);
	}
}

/*function doViewRM(){

	if(validateSelectedCheckBoxes(targetFormName,'rowId',1,1)){
	var checkIndex;
	var memCode;
	var selectedRow=document.getElementsByName('rowId');

		for(var i=0;i<selectedRow.length;i++){
			if(selectedRow[i].checked){

				checkIndex=selectedRow[i].value;
				break;
				}
		}
	//var expstatus=document.getElementsByName('expStatus');
	var mem=document.getElementsByName('memoNumber');
	var cn66flag=targetFormName.elements.cn66CloseFlag.value;
		if(expstatus!=null && expstatus.length>0){
			if(expstatus[checkIndex].value=="R"){
				memCode=mem[checkIndex].value;

				submitForm(frm,'mailtracking.mra.airlinebilling.inward.rejectionmemo.list.do?fromScreenFlag='+"invexpscreenview"+'&memoCode='+memCode+'&cn66CloseFlag='+cn66flag);
				}
			else{
				showDialog("Please select a rejected invoice",1,self);
			}
		}
	}
}*/
/**
*Close function done here
*/
function doClose(){
	frm=targetFormName;
		var flag=targetFormName.elements.cn66CloseFlag.value;
		//alert(targetFormName.elements.cn66CloseFlag.value);
		if(flag=='cn66close'){

		submitForm(frm,'mailtracking.mra.airlinebilling.defaults.captureCN66.onScreenLoad.do?fromScreenStatus='+"invoiceexception");
		}
		else{
		submitForm(frm,'mailtracking.mra.airlinebilling.inward.invoiceexceptionsclose.do');
		}
}

/**
*ViewExcDetails function done here
*/
function doViewExcDetails(){
	frm=targetFormName;
	var invoiceNumber='';
		var fromscreenflag;
		var cn66status=frm.cn66CloseFlag.value;

		if(cn66status=='cn66close'){
		fromscreenflag='cn66screen';
		}
		else{
		fromscreenflag='invoiceexceptions';
		}
		if(validateSelectedCheckBoxes(targetFormName,'rowId','1',1)){

			if(frm.rowId.length>1){
				for(var i=0;i<frm.rowId.length;i++ ){
				if(frm.rowId[i].checked==true){
					var seelctedRow=frm.rowId[i].value;
					invoiceNumber=frm.invNumber[seelctedRow].value;
					break;
				}
				}
			}
			else{
			 invoiceNumber=frm.invNumber.value;
			}

			submitForm(frm,'mailtracking.mra.airlinebilling.inward.invoiceexceptionsviewexcdetails.do?fromScreenFlag='+fromscreenflag+'&invoiceRefNo='+invoiceNumber);
		}
}

/**
*checking for rows in table to print
*/

function setscreenstatus(){


	// if(targetFormName.rowId==null){

	 		//disableMultiButton(document.getElementById('Btn_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_Print'));
	 //}
	 //else{
	 		//enableMultiButton(document.getElementById('Btn_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_Print'));
	// }


}

/**
* print functions
*/

function onClickPrint(){

	//if(printType=='PrintRejection'){
		//if(document.getElementById('exceptionStatus').value=='' || document.getElementById('exceptionStatus').value=='R')
			//generateReport(targetFormName, "/mailtracking.mra.airlinebilling.inward.printrejections.do");
		//else
			//alert('Exception Status selected is not proper');
	//}
	//if(printType=='PrintExceptionInvoice'){
		//if(document.getElementById('exceptionStatus').value=='' || document.getElementById('exceptionStatus').value=='E')
			generateReport(targetFormName, "/mailtracking.mra.airlinebilling.inward.printexceptionininvoice.do");
		//else
			//alert('Exception Status selected is not proper');
	//}

}