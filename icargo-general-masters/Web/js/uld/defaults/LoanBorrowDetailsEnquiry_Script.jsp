<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;



	onScreenLoad();
	with(frm){

	if(frm.elements.uldNum.disabled==false && frm.elements.popupflag.value!="true"){
			frm.elements.uldNum.focus();
	}

	evtHandler.addEvents("uldNum","validateFields(this, -1, 'Uld Number', 0, true, true)",EVT_BLUR);
	//evtHandler.addIDEvents("txnType","txnTypeChanged()",EVT_CHANGE);
    evtHandler.addEvents("fromPartyCode","fromPartyCodeValidate()",EVT_BLUR);
	evtHandler.addEvents("toPartyCode","toPartyCodeValidate()",EVT_BLUR);
	evtHandler.addIDEvents("fromairlinelov","onclickFromLOV()",EVT_CLICK);
	evtHandler.addIDEvents("toairlinelov","onclickToLOV()",EVT_CLICK);
	evtHandler.addEvents("partyType","comboChanged()",EVT_CHANGE);

	//CLICK Events
	evtHandler.addEvents("btnList","listEnquiries()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearEnquiries()",EVT_CLICK);
	//evtHandler.addIDEvents("masterUld","updateHeaderCheckBox(this.form,this,this.form.uldDetails)",EVT_CLICK);
	//evtHandler.addIDEvents("masterAcc","updateHeaderCheckBox(this.form,this,this.form.accessoryDetails)",EVT_CLICK);
	//added by a-3278 for QF1015 on 24Jul08
	evtHandler.addIDEvents("totalDemmurage","showTotalDemmurage()",EVT_CLICK);
	//a-3278 ends
	evtHandler.addEvents("btnDelete","deleteULDDetails()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeEnquiries()",EVT_CLICK);
	evtHandler.addEvents("btnCreateTxn","createTxn()",EVT_CLICK);
	evtHandler.addEvents("btnModifyTxn","modifyTxn()",EVT_CLICK);
	evtHandler.addEvents("btnReturn","returnTxn()",EVT_CLICK);
	evtHandler.addEvents("btnGenLUC","generateLUC()",EVT_CLICK);
	evtHandler.addEvents("btnGenerateInvoice","generateInvoice()",EVT_CLICK);
	evtHandler.addEvents("btnViewUldDetails","viewUldDetails()",EVT_CLICK);
	evtHandler.addEvents("btnPrint","onClickPrint(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnPrintAll","onClickPrintAll(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnPrintUCR","onClickPrintUCR(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnGenMUC","generateMUC()",EVT_CLICK);
	evtHandler.addEvents("btnIgnoreReinstateMUC","ignoreReinstateMUC()",EVT_CLICK);
	
	evtHandler.addEvents("returnQuantity","validateFields(this, -1, 'Return Quantity', 3, true, true)",EVT_BLUR);
	evtHandler.addEvents("returnQuantity","validateReturnQunatity(this.form);",EVT_BLUR);

	if(frm.elements.uldDetails != null){
		evtHandler.addEvents("uldDetails","toggleTableHeaderCheckbox('uldDetails', frm.elements.masterUld);",EVT_CLICK);
	}
	if(frm.elements.accessoryDetails != null){
		evtHandler.addEvents("accessoryDetails","toggleTableHeaderCheckbox('accessoryDetails', frm.elements.masterAcc);",EVT_CLICK);
	}
	//BLUR Events
	evtHandler.addIDEvents("txnStatus","disableDates()",EVT_BLUR);

	}
	 //applySortOnTable("transactiontable",new Array("None","String","String","Number","String","Date","String","String","String","String","String","Date","String","String","String","String","Number","String","String"));
	 applySortOnTable("transactiontable",new Array("None","String","String","String","Date","String","String","String","String","String","String","String","String","String","Number","Date","Number","String","Date","String","None","Number"));
	 applySortOnTable("accessoriestable",new Array("None","String","String","Number","Date","String","String","String","String","String"));
	



}
function validateReturnQunatity(form){
	var accDtls = document.getElementsByName("accessoryDetails");
	var accDtlsLength=accDtls.length;
	if(accDtlsLength>1){
			for(var val=0;val<accDtlsLength;val++){
		var actualQun=document.getElementsByName("returnQuantityCheck")[val].value;
		var returnQun=parseInt(targetFormName.elements.elements.returnQuantity[val].value);
				
	if(actualQun!=""){
		if( returnQun>actualQun){
				showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanborrowdetailsenquiry.cannotreturnmorethanavailable" />',type:1,
				parentWindow:self
				});
				targetFormName.elements.returnQuantity[val].focus();
				return;
		}
	}
		}
	}
 else if(accDtlsLength==1){
		var actualQun=document.getElementsByName("returnQuantityCheck")[0].value;
		var returnQun=parseInt(targetFormName.elements.returnQuantity.value);
	if(actualQun!=""){
		if( returnQun>actualQun){
				showDialog({msg:"Cannot return more than the available quantity. ",type:1,
				parentWindow:self
				});
				targetFormName.elements.returnQuantity.focus();
				return;
		}
	}
 }
}
//added by a-3278 for QF1015 on 24Jul08
function showTotalDemmurage(){
recreateTableDetails('uld.defaults.showtotaldemmurage.do','totalDemmuragediv');	
}
function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableDetails";	
	_tableDivId = divId;		
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,_tableDivId);	
}
function updateTableDetails(_tableInfo){	
	_filter=_tableInfo.document.getElementById("totalDemmurageAjaxdiv");	
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;	
}

//a-3278 ends


function enableFieldsOnULDTab(){
	enableField(targetFormName.btnModifyTxn);
	enableField(targetFormName.btnViewUldDetails);
	enableField(targetFormName.btnGenerateInvoice);
	enableField(targetFormName.btnGenLUC);
	enableField(targetFormName.btnGenMUC);
	enableField(targetFormName.btnPrintUCR);
	enableField(targetFormName.btnPrint);
	enableField(targetFormName.btnPrintAll);
}

function disableFieldsOnAccessoryTab(){
	disableField(targetFormName.btnModifyTxn);
	disableField(targetFormName.btnViewUldDetails);
	disableField(targetFormName.btnGenerateInvoice);
	disableField(targetFormName.btnGenLUC);
	disableField(targetFormName.btnGenMUC);
	disableField(targetFormName.btnPrintUCR);
	disableField(targetFormName.btnPrint);
	disableField(targetFormName.btnPrintAll);
}

function screenSpecificTabSetup(){
	setupPanes('container1','tab.shipment');
    displayTabPane('container1','tab.shipment');

	if(targetFormName.elements.showTab.value=="ULD"){
	enableFieldsOnULDTab();

	}
	if(targetFormName.elements.showTab.value=="ACCESSORY"){
	disableFieldsOnAccessoryTab();
	}


}


function onTabSelection_pane1(){
	targetFormName.elements.showTab.value="ULD";
		enableFieldsOnULDTab();


 }

 function onTabSelection_pane2(){
 	targetFormName.elements.showTab.value="ACCESSORY"
  		disableFieldsOnAccessoryTab();

}
function txnTypeChanged()
{
	var frm=targetFormName;
	submitAction(frm,'/uld.defaults.transactionenquiry.txnchanged.do');

}

function toPartyCodeValidate(){
var frm=targetFormName;

	if(frm.elements.comboFlag.value=="airlinelov"){
		//validateMaxLength(frm.toPartyCode,3);
		if(frm.elements.toPartyCode.value.length > 3){
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanborrowdetailsenquiry.partycodelenghtlessthan3" />',type:1,
				parentWindow:self
				});
		return;
		}
	}else if(frm.elements.comboFlag.value=="agentlov"){
		//validateMaxLength(frm.toPartyCode,12);
		if(frm.elements.toPartyCode.value.length > 12){
				showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanborrowdetailsenquiry.partycodelenghtlessthan12" />',type:1,
				parentWindow:self
				});
				return;
		}
	}else if(frm.elements.comboFlag.value=="others"){
		//validateMaxLength(frm.toPartyCode,12);
		if(frm.elements.toPartyCode.value.length > 12){
				showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanborrowdetailsenquiry.partycodelenghtlessthan12" />',type:1,
				parentWindow:self
				});
				return;
		}
	}/*else if(frm.comboFlag.value==""){
		validateMaxLength(frm.toPartyCode,3);
	}*/

}

function fromPartyCodeValidate(){
var frm=targetFormName;

	if(frm.elements.comboFlag.value=="airlinelov"){
		//validateMaxLength(frm.fromPartyCode,3);
		if(frm.elements.fromPartyCode.value.length > 3){
				showDialog({msg:"Please enter character(s) less than 3 for From Party Code",type:1,
				parentWindow:self
				});
				return;
		}
	}else if(frm.elements.comboFlag.value=="agentlov"){
		//validateMaxLength(frm.fromPartyCode,12);
		if(frm.elements.fromPartyCode.value.length > 12){
				showDialog({msg:"Please enter character(s) less than 12 for From Party Code",type:1,
				parentWindow:self
				});
				return;
		}
	}else if(frm.elements.comboFlag.value=="others"){
		//validateMaxLength(frm.fromPartyCode,12);
		if(frm.elements.fromPartyCode.value.length > 12){
				showDialog({msg:"Please enter character(s) less than 12 for From Party Code",type:1,
				parentWindow:self
				});
				return;
		}
	}/*else if(frm.comboFlag.value==""){
		validateMaxLength(frm.fromPartyCode,3);
	}*/

}

function comboChanged(){
	frm=targetFormName;
	if(frm.elements.partyType.value=="G"){
		frm.elements.fromPartyCode.value="";
		frm.elements.toPartyCode.value="";
		frm.elements.comboFlag.value="agentlov";
	}else if(frm.elements.partyType.value=="O"){
		frm.elements.fromPartyCode.value="";
		frm.elements.toPartyCode.value="";
		frm.elements.comboFlag.value="others";
	}else if(frm.elements.partyType.value=="A"){
		frm.elements.fromPartyCode.value="";
		frm.elements.toPartyCode.value="";
		frm.elements.comboFlag.value="airlinelov";
	}else if(frm.elements.partyType.value==""){
		frm.elements.fromPartyCode.value="";
		frm.elements.toPartyCode.value="";
		frm.elements.comboFlag.value="airlinelov";
	}
}
function onclickFromLOV(){
	frm=targetFormName;
	if(frm.elements.comboFlag.value=="agentlov"){
		//frm.fromairlinelov.disabled = false;
		enableField(frm.elements.fromairlinelov);
		//showFromLOV();
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','',0)
	}else if(frm.elements.comboFlag.value=="agentlov"){
		//frm.fromairlinelov.disabled = true;
		disableField(frm.fromairlinelov);
	}else if(frm.elements.comboFlag.value=="airlinelov"){
		//frm.fromairlinelov.disabled = false;
		enableField(frm.elements.fromairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','',0)
	}else if(frm.elements.comboFlag.value==""){
		//frm.fromairlinelov.disabled = false;
		enableField(frm.fromairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','',0)
	}
}

function onclickToLOV(){
	frm=targetFormName;
	if(frm.elements.comboFlag.value=="agentlov"){
		//frm.toairlinelov.disabled = false;
		enableField(toairlinelov);
		showToLOV();
	}else if(frm.elements.comboFlag.value=="agentlov"){
		//frm.toairlinelov.disabled = true;
		disableField(frm.toairlinelov);
	}else if(frm.elements.comboFlag.value=="airlinelov"){
		//frm.toairlinelov.disabled = false;
		enableField(frm.toairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.toPartyCode.value,'toPartyCode','1','toPartyCode','',0)
	}else if(frm.elements.comboFlag.value==""){
	//	frm.toairlinelov.disabled = false;
		enableField(frm.toairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.toPartyCode.value,'toPartyCode','1','toPartyCode','',0)
	}
}

function showFromLOV(){
var textobj = "";
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=fromPartyCode&formNumber=1&textfiledDesc="+textobj;
	var myWindow = openPopUp(StrUrl, "500","400")

}

function showToLOV(){
var textobj = "";
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=toPartyCode&formNumber=1&textfiledDesc="+textobj;
	var myWindow = openPopUp(StrUrl, "500","400")

}


function onScreenLoad(){
   frm=targetFormName;
    if(frm.elements.chkTransaction.value == "T" ){
		frm.elements.chkTransaction.value = "";
		generateReport(frm,'/uld.defaults.loanborrowulddetails.doUCRReport.do');
	}
   //added by nisha for CR-15 starts
   /*if(frm.elements.msgFlag.value=="TRUE"){
   openPopUp("msgbroker.message.resendmessages.do?openPopUpFlg=ULD",1050,620);
   frm.elements.msgFlag.value="FALSE";
	}*/
	if(frm.elements.msgFlag.value=='TRUE'){
		openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=LUC&targetAction=uld.defaults.transaction.generatelucmessage.do?fromPopup=Y",1050,320);
		frm.elements.msgFlag.value="FALSE";
		frm.elements.fromPopup.value="Y";
	}
   //added by nisha for CR-15 ends
   if(frm.elements.listStatus.value!="N"){
	disableField(frm.elements.btnPrint);
	disableField(frm.elements.btnPrintAll);
	disableField(frm.elements.btnModifyTxn);
	disableField(frm.elements.btnReturn);
	disableField(frm.elements.btnViewUldDetails);
	disableField(frm.elements.btnGenerateInvoice);
	disableField(frm.elements.btnDelete);
	disableField(frm.elements.btnGenLUC);
	disableField(frm.elements.btnPrintUCR);
	disableField(frm.elements.btnGenMUC);

   }


	if(frm.elements.enquiryDisableStatus.value=="GHA"){
			//frm.elements.txnStation.disabled = false;
			enableField(frm.elements.txnStation);
			//frm.elements.airportLovImg.disabled = true;
			disableField(frm.elements.airportLovImg);
	}
	if(frm.elements.enquiryDisableStatus.value=="airline"){
			//frm.elements.txnStation.disabled = false;
		  enableField(frm.elements.txnStation);
	}
	


   if(frm.elements.mode.value == "Y"){
     var strAction="uld.defaults.transaction.screenloadmodifyuldtransactions.do?modTotalRecords="+targetFormName.elements.modTotalRecords.value
      +"&modDisplayPage="+targetFormName.elements.modDisplayPage.value+"&modCurrentPage="+targetFormName.elements.modCurrentPage.value+
      "&modLastPageNum="+targetFormName.elements.modLastPageNum.value;
      frm.elements.popupflag.value="true";
	


      openPopUp(strAction,950,375);
   }

   disableDates();

   if(frm.elements.mode.value == "R"){
         var strAction="uld.defaults.transaction.screenloadreturnuldtransaction.do";
         frm.elements.popupflag.value="true";
         openPopUp(strAction,1050,470);
   }

   if(frm.elements.mode.value == "G"){

   }
   frm.elements.mode.value = "";

 document.getElementById("ignoreHiddenCheck").value = "Y";
}

/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

/**
*function to list loan/borrow enquiry details
*/
function listEnquiries(){
   frm=targetFormName;
   if(frm.elements.txnFromDate.value != ""){
	   if(frm.elements.txnFrmTime.value == ""){
	  	   frm.elements.txnFrmTime.value ="00:00";
		    //Modified by A-2052 for the bug 102904 ends
		   }
	   }

	   if(frm.elements.txnToDate.value != ""){
	   	   if(frm.elements.txnToTime.value == ""){
	   	    	frm.elements.txnToTime.value ="23:59";
	   		     //Modified by A-2052 for the bug 102904 ends
	   		   }
	   	   }
  if(frm.elements.returnFromDate.value != ""){
	   	   if(frm.elements.returnFrmTime.value == ""){
	   	    	frm.elements.returnFrmTime.value ="00:00";
	   		    //Modified by A-2052 for the bug 102904 ends
	   		   }
	   	   }

	    if(frm.elements.returnToDate.value != ""){
	   	   if(frm.elements.returnToTime.value == ""){
	   	       frm.elements.returnToTime.value ="23:59";
	   		    //Modified by A-2052 for the bug 102904 ends
	   		   }
	   	   }


   frm.elements.popupflag.value="";
   frm.elements.listMode.value="FROMLISTBUTTON";
       submitAction(frm,'/uld.defaults.transaction.listuldtransactions.do?totalCountFlag=N');

}

/**
*function to clear loan/borrow enquiry details
*/
function clearEnquiries(){
   frm=targetFormName;
   frm.elements.txnFromDate.value = "";
   frm.elements.txnToDate.value = "";
   frm.elements.returnFromDate.value = "";
   frm.elements.returnToDate.value = "";
   frm.elements.popupflag.value="";
  // submitAction(frm,'/uld.defaults.transaction.clearlistuldtransactions.do');
  submitFormWithUnsaveCheck('uld.defaults.transaction.clearlistuldtransactions.do');
}


/**
*function to disable date ranges..based on transaction status selection
*/
function disableDates(){
  frm=targetFormName;
}

/**
*function to modify selected ULD Details
*/
function modifyTxn(){
frm=targetFormName;

// to get max number of records in uld table
 var chkboxuld = document.getElementsByName("uldDetails");
 var chkcountuld = chkboxuld.length;
 var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
 var flag = 0;

 if(validateSelectedCheckBoxes(frm,'uldDetails','','1')){
     for(var i=0; i<chkcountuld; i++) {
        if(chkboxuld[i].checked == true) {
   	     if(hidTxnStatus[i].value == "I"){
	        flag = 1;
   	     }
   	}
     }

     if(flag == 1){
          showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returntxnstatus" />',type:1,
				parentWindow:self
				});
     }else{
     	  frm.elements.popupflag.value="";
          submitAction(frm,'/uld.defaults.transaction.updatelistuldtransactions.do');
     }

   }

}

function generateLUC(){
	if(validateSelectedCheckBoxes(targetFormName,'uldDetails',10000,1)){
	var loanFlag = "N";
	var statusFlag = "N";
	var borrowFlag = "N";
	var prevflag="N";
	var occFlag1=0;
	var occFlag2=0;
	var occFlag3=0;
	var occFlag4=0;
	var checkedrows=0;
	frm=targetFormName;
	var hidTxnType = document.getElementsByName("hiddenTxnType");
    var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
    var hidAccTxnType = document.getElementsByName("hiddenAccTxnType");
	var chkboxuld1 = document.getElementsByName("uldDetails");
	var hiddenCrn = document.getElementsByName("hiddenCrn");
	var crnStatus="N";
	var hiddenPoolFlag = document.getElementsByName("hiddenPoolFlag");
	//var poolOwner = "N";
	var chkcountuld1 = chkboxuld1.length;
	for(var i=0; i<chkcountuld1; i++) {
		if(chkboxuld1[i].checked == true) {
			checkedrows++;
			if(hidTxnType[i].value == "L" && hidTxnStatus[i].value == "T" ){
				occFlag1++;
			}else if(hidTxnType[i].value == "L" &&  hidTxnStatus[i].value == "R" ){
				occFlag2++;
			}
			else if(hidTxnType[i].value == "B" && hidTxnStatus[i].value == "T" ){
				occFlag3++;
			}else if(hidTxnType[i].value == "B" && hidTxnStatus[i].value == "R" ){
				occFlag4++;
			}else{
				statusFlag = "Y";
			}
			if(hiddenCrn[i].value == ""){
				crnStatus = "Y";
			}
			/*if(hiddenPoolFlag[i].value == "Y"){
				poolOwner = "Y";
			}*/
		}
	}


	if(occFlag1 > 0 && occFlag1 != checkedrows){
		prevflag="Y";
	}else if(occFlag2 > 0 && occFlag2 != checkedrows){
		prevflag="Y";
	}else if(occFlag3 > 0 && occFlag3 != checkedrows){
		prevflag="Y";
	}else if(occFlag4 > 0 && occFlag4 != checkedrows){
		prevflag="Y";
	}
	/*if(poolOwner == "Y"){
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.luccannotsent" />',type:1,
				parentWindow:self
				});
		return;
	}*/
	if(crnStatus == "Y"){

	showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.crncannotbeblank" />',type:1,
				parentWindow:self
				});
	return;
	}
	if(statusFlag == "Y"){
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returntxnstatustobereturned" />',type:1,
				parentWindow:self
				});
	}else if( prevflag=="Y"){
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.selectuldwithsametxnstatus" />',type:1,
				parentWindow:self
				});
	}
	else{
		submitAction(frm,'/uld.defaults.transaction.generatelucmessage.do');
	}

	}
}


/**
*function to return selected ULD Details
*/
function returnTxn(){

 frm=targetFormName;

 //for uld tab any row selected
 var uldflag = "N";

 //for accessory tab any row selected
 var accessoryflag = "N";

 //for uld tab only rows of loan txnType selected
 var loanFlag = "N";

 //show popup flag
 var conFlag1 = "N";

 //for uld tab only rows with txn status To be returned
 var statusFlag = "N";

 //for uld tab only rows of borrow txnType selected
 var borrowFlag = "N";

 var hidTxnType = document.getElementsByName("hiddenTxnType");
 var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
 var hidAccTxnType = document.getElementsByName("hiddenAccTxnType");

//to get max number of records in uld table
 var chkboxuld1 = document.getElementsByName("uldDetails");
 var chkcountuld1 = chkboxuld1.length;

 var nMinSelected1 = 1;

   	var nNumSelected1 = 0;

   	for (j = 0; j < chkcountuld1; j++) {
   	     if (chkboxuld1[j].checked == true) {
   		nNumSelected1++;
   	     }
   	}

   	if(nNumSelected1 < nMinSelected1){
   	     uldflag = "U";
   	}

 var chkboxuld2 = document.getElementsByName("accessoryDetails");
 var chkcountuld2 = chkboxuld2.length;
   var nMinSelected2 = 1;

      	var nNumSelected2 = 0;

      	for (j = 0; j < chkcountuld2; j++) {
      	     if (chkboxuld2[j].checked == true) {
      		nNumSelected2++;
      	     }
      	}

      	if(nNumSelected2 < nMinSelected2){
      	      accessoryflag = "A";

      	}


 if(uldflag == "U" && accessoryflag == "A"){
    showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returnselectonerow" />',type:1,
				parentWindow:self
				});
 }else{
   	for(var i=0; i<chkcountuld1; i++) {
   		 if(chkboxuld1[i].checked == true) {
   		 	if(hidTxnType[i].value == "L" && hidTxnStatus[i].value == "T"){
   		 	 	loanFlag = "Y";
   			}
   		 	else if(hidTxnType[i].value == "B" && hidTxnStatus[i].value == "T"){
   		 		borrowFlag = "Y";
   			}else{
   				statusFlag = "Y";
   			}

  		}
   	}
	
   	for(var i=0; i<chkcountuld2; i++) {
	   if(chkboxuld2[i].checked == true) {
		if(hidAccTxnType[i].value == "L"){				
				if(chkcountuld2>1){
					if(targetFormName.elements.returnQuantity[i].value == ""){
						showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.returnquantityismandatory" />',type:1,
				parentWindow:self
				});
						return;
					}	
				}else{
					if(targetFormName.elements.returnQuantity.value == ""){
						showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.returnquantityismandatory" />',type:1,
				parentWindow:self
				});
						return;
					}
				}
			 loanFlag = "Y";
		}
		else if(hidAccTxnType[i].value == "B"){
			borrowFlag = "Y";
		}

	   }
   	}

	if(statusFlag == "Y"){
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returntxnstatustobereturned" />',type:1,
				parentWindow:self
				});
	}
	else if(loanFlag == "Y" && borrowFlag == "N"){
		conFlag1 = "Y";
	}
	else if(loanFlag == "N" && borrowFlag == "Y"){
		conFlag1 = "Y";
	}else{
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returnsametxntype" />',type:1,
				parentWindow:self
				});
	}

   if(conFlag1 == "Y"){

		frm.elements.popupflag.value="";
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.warn.return" />', type:4,
parentWindow:self,
parentForm:targetFormName,
dialogId:'id_4',
onClose:function(result){
screenConfirmDialog(targetFormName,'id_4');
screenNonConfirmDialog(targetFormName,'id_4');
}
});

   }

 }

}


/**
*function to create selected ULD Details
*/
function createTxn(){
   frm=targetFormName;
   var parent = "LoanBorrowEnquiry";
   frm.elements.popupflag.value="";
   submitAction(frm,'/uld.defaults.transaction.screenloadloanborrowuld.do?closeStatus='+parent);
}

/**
*function to delete selected ULD Details
*/
function deleteULDDetails(){
  frm=targetFormName;
 //for uld tab any row selected
 var uldflag = "N";

 //for accessory tab any row selected
 var accessoryflag = "N";
// to get max number of records in uld table
  var chkboxuld1 = document.getElementsByName("uldDetails");
  var chkcountuld1 = chkboxuld1.length;

  var nMinSelected1 = 1;

    	var nNumSelected1 = 0;

    	for (j = 0; j < chkcountuld1; j++) {
    	     if (chkboxuld1[j].checked == true) {
    		nNumSelected1++;
    		 uldflag = "Y";
    	     }
    	}

    	if(nNumSelected1 < nMinSelected1){
    	     uldflag = "U";
    	}

  var chkboxuld2 = document.getElementsByName("accessoryDetails");
  var chkcountuld2 = chkboxuld2.length;
    var nMinSelected2 = 1;

       	var nNumSelected2 = 0;

       	for (j = 0; j < chkcountuld2; j++) {
       	     if (chkboxuld2[j].checked == true) {
       		nNumSelected2++;
       		 accessoryflag = "Y";
       	     }
       	}

       	if(nNumSelected2 < nMinSelected2){
       	      accessoryflag = "A";

       	}


  if(uldflag == "U" && accessoryflag == "A"){
     showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returnselectonerow" />',type:1,
				parentWindow:self
				});
 }else{

	 // Added by A-2412
            var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
            var hidTxnType = document.getElementsByName("hiddenTxnType");
            for(var i=0; i<chkboxuld1.length; i++) {
                 if(chkboxuld1[i].checked) {
 		   if( (hidTxnType[i].value == "L" && hidTxnStatus[i].value == "T") ||
 			 (hidTxnType[i].value == "L" && hidTxnStatus[i].value == "R") ||
 			 (hidTxnType[i].value == "B" && hidTxnStatus[i].value == "T") ){
 			showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.transactioncannotbedeleted" />',type:1,
				parentWindow:self
				});
 			return;
 		     }
            	}
             }


      // Addition ends

      for(var i=0; i<frm.elements.length; i++) {
  	if(frm.elements[i].type == "checkbox") {
  	   if(frm.elements[i].name=="uldDetails") {
  	     if(frm.elements[i].checked) {
  	        uldDetails = frm.elements[i].value;
  	     }
 	   }
         }
      }
      showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.warn.delete" />',type:4,
parentWindow:self,
parentForm:targetFormName,
dialogId:'id_1',
onClose:function(result){
if(uldflag == "Y"){
      screenConfirmDialog(frm,'id_1');
      screenNonConfirmDialog(frm,'id_1');
      return;
  	 }
  	 if(accessoryflag == "Y"){
	       screenConfirmDialog(frm,'id_2');
	       screenNonConfirmDialog(frm,'id_2');
	       return;
  	 }
}
});
     
   }

}


/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		frm.elements.popupflag.value="";
		if(dialogId == 'id_1'){
			submitAction(frm,'/uld.defaults.transaction.deletelistuldtransactions.do');
		}
		if(dialogId == 'id_2') {
			submitAction(frm,'/uld.defaults.transaction.deletelistaccessorydetails.do');
		}
		if(dialogId == 'id_3') {
		        submitAction(frm,'/uld.defaults.transaction.deleteulddetails.do');
		}
		if(dialogId == 'id_4') {
				        submitAction(frm,'/uld.defaults.transaction.returnuldtransaction.do');
		}

	}
}


/**
*function to Non-Confirm Dialog
*/
function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
		if(dialogId == 'id_2'){

		}
		if(dialogId == 'id_3'){

		}
		if(dialogId == 'id_4') {
		
		}
	}
}




/**
*function to generate invoice for selected ULD Details
*/
function generateInvoice(){
frm=targetFormName;

// to get max number of records in uld table
 var chkboxuld = document.getElementsByName("uldDetails");
 var chkcountuld = chkboxuld.length+1;
 var hidTxnType = document.getElementsByName("hiddenTxnType");
 var hidTxnRefNo = document.getElementsByName("hiddenTxnRefNo");
 var hidUldNo = document.getElementsByName("hiddenUldNo");
 var hidInvoiceRefNo = document.getElementsByName("hiddenInvoiceRefNo");
 var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
 var hidReturnDate = document.getElementsByName("hiddenReturnDate");
 var hidFromPartyCode = document.getElementsByName("hiddenFromPartyCode");
 var hidToPartyCode = document.getElementsByName("hiddenPartyCode");
var hidToPartyName = document.getElementsByName("hiddenPartyName");
 var hidPartyType = document.getElementsByName("hiddenPartyType");
 var hidDmg = document.getElementsByName("hiddenDmg");
 var hidWaiver = document.getElementsByName("hiddenWaiver");

 var txntype = "L";
 var txnref = "";
 var uldno = "";
 var retdate = "";
 var retDmg = "";
 var retWaiver = "";
 var cnt1 = 0;
 var cnt2 = 0;
 var cnt3 = 0;
 var cnt4 = 0;
 var cnt5 = 0;
 var cnt6 = 0;
 var cnt7 = 0;
 var firstType = "";
 var partyCode = "";
 var partyType = "";
var partyName = "";
var firstToParty = "";
var firstPtyTyp = "";

 if(validateSelectedCheckBoxes(frm,'uldDetails',chkcountuld,'1')){

      for(var i=0; i<chkboxuld.length; i++) {

       	 if(chkboxuld[i].checked) {

       	 if(cnt3 == 0){
	 	firstType=hidTxnType[i].value;
	 	firstToParty=hidToPartyCode[i].value;
	 	firstPtyTyp=hidPartyType[i].value;
	  	cnt3 = 1;
	 }else{
	     if(hidTxnType[i].value != firstType){
	        showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.sametxntype" />',type:1,
				parentWindow:self
				});
	        return;
	     }
	     if(hidToPartyCode[i].value !=firstToParty){
	     	showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.info.selecttransactionwithsametoparty" />',type:1,
				parentWindow:self
				});
	        return;
	     }
	      if(hidPartyType[i].value !=firstPtyTyp){
		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.info.selecttransactionwithsameparty" />',type:1,
				parentWindow:self
				});
		return;
	     }
	 }

	 if(cnt5 == 0){
	// alert("hidTxnType[i].value"+hidTxnType[i].value);
	// alert("hidFromPartyCode"+hidFromPartyCode[i].value);
	//alert("hidToPartyCode"+hidToPartyCode[i].value);
	
		 if(hidTxnType[i].value=="L"){
		partyCode=hidToPartyCode[i].value;
		//alert("partyCode----"+partyCode);
	     }
	     if(hidTxnType[i].value=="B"){
		partyCode=hidFromPartyCode[i].value;
	     }
		partyType=hidPartyType[i].value;
		partyName = hidToPartyName[i].value;
		cnt5 = 1;
	 }else{
		 if(hidTxnType[i].value=="L"){
		 if(hidToPartyCode[i].value != partyCode){
				showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.samepartycode" />',type:1,
				parentWindow:self
				});
				return;
	     }
	 	}
		 if(hidTxnType[i].value=="B"){
		 if(hidFromPartyCode[i].value != partyCode){
				showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.samepartycode" />',type:1,
				parentWindow:self
				});
				return;
		 }
	 	}

	 }

	     if(hidInvoiceRefNo[i].value != ""){
	 	        showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.alredaygenerated" />'+" "+hidUldNo[i].value,type:1,
				parentWindow:self
				});
	 	        return;
	     }
	     if(hidTxnStatus[i].value == "T"){
			showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.tobereturned" />',type:1,
				parentWindow:self
				});
			return;
	     }
	     if(hidTxnType[i].value == "B"){
	     		showDialog({msg:'<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.isborrow" />'+" "+hidUldNo[i].value,type:1,
				parentWindow:self
				});
	     		return;
	     }


       	    if(cnt1 == 0){
	 	  uldno=hidUldNo[i].value;
	 	  cnt1 = 1;
	    }else{
	          uldno=uldno+","+hidUldNo[i].value;
	    }

  	    if(cnt2 == 0){
		  txnref=hidTxnRefNo[i].value;
		  cnt2 = 1;
	    }else{
	 	  txnref=txnref+","+hidTxnRefNo[i].value;
	    }

	    if(cnt4 == 0){
		  retdate=hidReturnDate[i].value;
		  cnt4 = 1;
	    }else{
		  retdate=retdate+","+hidReturnDate[i].value;
	    }
	    if(cnt6 == 0){
		  retDmg=hidDmg[i].value;
		  cnt6 = 1;
	    }else{
		  retDmg=retDmg+","+hidDmg[i].value;
	    }
	    if(cnt7 == 0){
		  retWaiver=hidWaiver[i].value;
		  cnt7 = 1;
	    }else{
		  retWaiver=retWaiver+","+hidWaiver[i].value;
	    }
         }
      }
     frm.elements.popupflag.value="true";
      //submitAction(frm,'/uld.defaults.transaction.updateenquirygenerateinvoice.do');
      frm.elements.mode.value="G";
      //alert("before pop up-- "+partyCode);
      var strAction="uld.defaults.generatetransactioninvoicescreenload.do?uldNumbers="+uldno+"&txnRefNos="+txnref+"&txndates="+retdate+"&txnType="+txntype+"&partyTypeFlag="+partyType+"&demAmt="+retDmg+
      "&waiver="+retWaiver+"&hiddenDmgAmt="+retDmg+"&hiddenWaiver="+retWaiver+"&hiddenToPartyCode="+partyCode+"&hiddenToPtyName="+partyName;
      openPopUp(strAction,680,220);

    }

}



/**
*function to View ULD Details
*/
function viewUldDetails(){
frm=targetFormName;

// to get max number of records in uld table
 var chkboxuld = document.getElementsByName("uldDetails");
 var chkcountuld = chkboxuld.length+1;
 var hidUldNo = document.getElementsByName("hiddenUldNo");
 var uldno = "";
 var cnt1 = 0;
 var listdetail = "LoanBorrow";


// Added by A-2412
var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
var hidTxnType = document.getElementsByName("hiddenTxnType");

// Addition ends


if(validateSelectedCheckBoxes(frm,'uldDetails',chkcountuld,'1')){

     for(var i=0; i<chkboxuld.length; i++) {
           if(chkboxuld[i].checked) {
		   if( (hidTxnType[i].value == "B" && hidTxnStatus[i].value == "I") ||
			 (hidTxnType[i].value == "B" && hidTxnStatus[i].value == "R") ){
		        showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.ulddetailsnotavailable' />",type:1,
				parentWindow:self
				});
			return;
		     }
		}
   	}

     for(var i=0; i<chkboxuld.length; i++) {
       	 if(chkboxuld[i].checked) {
       	    if(cnt1 == 0){
	 	  uldno=hidUldNo[i].value;
	 	  cnt1 = 1;
	    }else{
	          uldno=uldno+","+hidUldNo[i].value;
	    }
       	 }
      }
      frm.elements.popupflag.value="";
       submitForm(frm,'uld.defaults.detailuldinfo.do?uldNumbersSelected='+uldno+'&screenloadstatus='+listdetail);
    }
}

/**
*function to close Enquiries
*/
function closeEnquiries(){
    frm=targetFormName;
	if(frm.elements.pageURL.value == "ListLoanBorrowEnq") {
	       	submitAction(frm,'/uld.defaults.transaction.closelistuldtransactions.do');
    }else{
		location.href = appPath + "/home.jsp";
	}

}


/**
*Method for excecuting after confirmation
*/
function confirmMessage(){
  frm = targetFormName;

}

/**
*Method for excecuting after nonconfirmation
*/
function nonconfirmMessage(){

}


/**
*Method for printing ULD Details
*/
function onClickPrint(frm) {
	frm.elements.popupflag.value="";
 	//generateReport(frm,"/uld.defaults.loanborrowulddetails.doReport.do");
 	generateReport(frm,"/uld.defaults.loanborrowulddetailsforpage.doReport.do");
}
function onClickPrintAll(frm) {
	frm.elements.popupflag.value="";
 	generateReport(frm,"/uld.defaults.loanborrowulddetails.doReport.do");
}


/* Added by A-2412 on 22 nd Oct for printing UCR */
function onClickPrintUCR(frm){

	// to get max number of records in uld table
	frm = targetFormName;
	 var chkboxuld = document.getElementsByName("uldDetails");
	 var chkcountuld = chkboxuld.length+1;
	 var hidUldNo = document.getElementsByName("hiddenUldNo");
	 var hiddenTxnRefNo = document.getElementsByName("hiddenTxnRefNo");
	 var uldno = "";
	 var txnRefNo = "";
	 var cnt1 = 0;
	 frm.elements.popupflag.value="";
	 var txnValue1 = "";
	 var txnValueChk = "";
	 var txnFlag = 0;
	 var txnValue2="";
	 var controlRecptNumber = "";
	 var crn = "";
 
	//var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
	var hidTxnType = document.getElementsByName("hiddenTxnType");
	//var hidControlRcptNumber =  document.getElementsByName("hiddenCrnNumber");
	//alert(checkTxnStatus(this.form));
		//if(validateSelectedCheckBoxes(frm,'uldDetails','3','1')){
			var checkboxSelected = 0;
			for(var i=0;i<chkboxuld.length;i++) {
			if(chkboxuld[i].checked)	{
			checkboxSelected = checkboxSelected + 1;
			}
			}
			if(checkboxSelected == 0){
			showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.selectarow' />",type:1,
				parentWindow:self
				});
				return;
			}
		
		    for(var i=0; i<chkboxuld.length; i++) {
			if(chkboxuld[i].checked) {


				    if(cnt1 == 0){
					  uldno=hidUldNo[i].value;
					  txnRefNo=hiddenTxnRefNo[i].value;
					  txnValue1 = hidTxnType[i].value;

					  cnt1 = 1;
				    }else{
					  uldno=uldno+","+hidUldNo[i].value;
					  txnValue2 = hidTxnType[i].value;
					  if(txnValue1!=txnValue2){
							showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.cannotselectuldofdifferenttxntypes' />",type:1,
				parentWindow:self
				});
							txnFlag=1;
							return;
					  }
					  txnRefNo=txnRefNo+","+hiddenTxnRefNo[i].value;
				    }
			}
		     }
			 //Added as part of ICRD-4269 by A-3767 on 30Sep11
			 if(cnt1 == 0){
				for(var i=0; i<chkboxuld.length; i++) {
					if( cnt1 ==0){
						uldno=hidUldNo[i].value;
						txnRefNo=hiddenTxnRefNo[i].value;
						txnValue1 = hidTxnType[i].value;
						 cnt1 = 1;
					}else{
						uldno=uldno+","+hidUldNo[i].value;
						txnValue2 = hidTxnType[i].value;
						if(txnValue1!=txnValue2){
							showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.cannotprintucrforuldofdifferenttransactionstype' />",type:1,
				parentWindow:self
				});
							txnFlag=1;
							return;
						  }
						txnRefNo=txnRefNo+","+hiddenTxnRefNo[i].value;
					}
				}
			 }
			if(txnFlag != 1)
			 {
					frm.elements.uldNumbersSelected.value = uldno;
					frm.elements.txnNumbersSelected.value = txnRefNo;
					submitAction(frm,'/uld.defaults.transaction.validatetransactionstatus.do');
			 }
		// }
}
/* Addition by A-2412 on 22 nd Oct for printing UCR ends */


function submitList(strLastPageNum,strDisplayPage){
	var frm = targetFormName;
	if(frm.elements.txnFromDate.value != ""){
	if(frm.elements.txnFrmTime.value == ""){
	showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.entertxnfromtime' />",type:1,
				parentWindow:self
				});
	frm.elements.txnFrmTime.focus();
	return;
	}
	}

	if(frm.elements.txnToDate.value != ""){
	if(frm.elements.txnToTime.value == ""){
	showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.entertxntotime' />",type:1,
				parentWindow:self
				});
	frm.elements.txnToTime.focus();
	return;
	}
	}
	if(frm.elements.returnFromDate.value != ""){
	if(frm.elements.returnFrmTime.value == ""){
	showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.enterreturnfromtime' />",type:1,
				parentWindow:self
				});
	frm.elements.returnFrmTime.focus();
	return;
	}
	}

	if(frm.elements.returnToDate.value != ""){
	if(frm.elements.returnToTime.value == ""){
	showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.enterreturntotime' />",type:1,
				parentWindow:self
				});
	frm.elements.returnToTime.focus();
	return;
	}
	}


	frm.elements.popupflag.value="";
	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	frm.elements.listMode.value="";
	targetFormName.action ="uld.defaults.transaction.listuldtransactions.do?totalCountFlag=Y";
	targetFormName.submit();
}

function checkTxnStatus(frm){
var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
var hidTxnType = document.getElementsByName("hiddenTxnType");
var chkboxuld = document.getElementsByName("uldDetails");
var chkcountuld = chkboxuld.length+1;

if(validateSelectedCheckBoxes(frm,'uldDetails',chkcountuld,'1')){
     for(var i=0; i<chkboxuld.length; i++) {
           if(chkboxuld[i].checked) {
		   if( (hidTxnType[i].value == "L" && hidTxnStatus[i].value == "T") ||
			 (hidTxnType[i].value == "B" && hidTxnStatus[i].value == "R") ){
		       	return true;
		     }
		     else {
		     	showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.ucrcannotbeprinted' />",type:1,
				parentWindow:self
				});
		     	return true;
		     }
		}
   	}
}else{
	return false;
}

}
/* Added by A-2412 on 26 th Nov to generate MUC */
function generateMUC(){
	if(validateSelectedCheckBoxes(targetFormName,'uldDetails',10000,1)){
		var loanFlag = "N";
		var statusFlag = "N";
		var borrowFlag = "N";
		frm=targetFormName;
		var hidTxnType = document.getElementsByName("hiddenTxnType");
	    var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
	    var hidAccTxnType = document.getElementsByName("hiddenAccTxnType");
		var chkboxuld1 = document.getElementsByName("uldDetails");
		var hiddenCrn = document.getElementsByName("hiddenCrn");
		var crnStatus="N";
		var hiddenPoolFlag = document.getElementsByName("hiddenPoolFlag");
		var poolOwner = "N";
		var chkcountuld1 = chkboxuld1.length;
		var hidMucIataStatus = document.getElementsByName("hiddenMucIataStatus");
		var hidPartyType = document.getElementsByName("hiddenPartyType");
		var muccount = 0;
		var chkcount = 0;
		for(var i=0; i<chkcountuld1; i++) {
			if(chkboxuld1[i].checked == true) {
				if( (hidTxnType[i].value == "L" && hidTxnStatus[i].value == "T") ||
				(hidTxnType[i].value == "B" && hidTxnStatus[i].value == "R") ){
					loanFlag = "Y";
				}
				else{
					statusFlag = "Y";
				}
				if(hiddenCrn[i].value == ""){
					crnStatus = "Y";
				}
				if(hiddenPoolFlag[i].value == "Y"){
					poolOwner = "Y";
				}
				if(hidMucIataStatus[i].value == "MUC Required"){
					muccount++;
				}
				chkcount++;
			}
		}
		/*if(poolOwner == "Y"){
			showDialog('MUC cannot be send to Pool Owners',1,self);
			return;
		}*/
		if(crnStatus == "Y"){

			showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.crnismandatory' />",type:1,
				parentWindow:self
				});
			return;
		}
		if(muccount != chkcount){
			showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.muccannotbegenerateforuld' />",type:1,
				parentWindow:self
				});
		}else
		{
			submitAction(frm,'/uld.defaults.transaction.generatemucmessage.do');
	}
	}
}

/* Added by A-2412 on 26 th Nov to generate MUC ends*/

function ignoreReinstateMUC(){
		//alert("ignoreReinstateMUC");		
		var chkboxuld = document.getElementsByName("uldDetails");
		var chkcountuld = chkboxuld.length;
		//var detailMUCStatus = document.getElementsByName("detailMUCStatus");
		 var hidMucIataStatus = document.getElementsByName("hiddenMucIataStatus");
		  var hidConditionCode = document.getElementsByName("hiddenConditionCode");
		  var hidTxnType = document.getElementsByName("hiddenTxnType");
	   	  var hidTxnStatus = document.getElementsByName("hiddenTxnStatus");
	   	  var hidPartyType = document.getElementsByName("hiddenPartyType");
		var chkcount = 0;
		var statuscount = 0;
		var conditioncount = 0;
		for(var i=0; i<chkcountuld; i++) {
			if(chkboxuld[i].checked == true) {
				if(hidTxnType[i].value == "B" && hidTxnStatus[i].value == "T"){
					showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources'  key='uld.defaults.loanborrowdetailsenquiry.mucstatuscannotbereinstated' />",type:1,
				parentWindow:self
				});
					return;
				}
				if(hidPartyType[i].value == "G" || hidPartyType[i].value == "O"){
					showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources' key='uld.defaults.loanborrowdetailsenquiry.mucstatusforagentsandothers' />",type:1,
				parentWindow:self
				});
					return;
				}
				if(hidTxnType[i].value == "L" && ((hidTxnStatus[i].value == "R") || (hidTxnStatus[i].value == "I"))) {
					showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources' key='uld.defaults.loanborrowdetailsenquiry.mucstatusforinvoice' />",type:1,
				parentWindow:self
				});
					return;
				}
				chkcount++;
				//alert("1");
				//alert(hidConditionCode[i].value);
				if(hidMucIataStatus[i].value == "MUC Required" || hidMucIataStatus[i].value == "Not to be Reported"){
					statuscount++;
				}
				if(hidConditionCode[i].value != "ZZZ"){
					conditioncount++;
				}
			}
		}
		if(chkcount == 0 || chkcount!=statuscount){
			showDialog({msg:'Select ULDs with MUC Status as MUC Required or Not To Be Reported',type:1,
				parentWindow:self
				});
		}else if(chkcount == 0 || chkcount!=conditioncount){
			//showDialog('Select ULDs with ULD Condition other than ZZZ ',1,self);
			showDialog({msg:"<common:message bundle='loanBorrowDetailsEnquiryResources' key='uld.defaults.loanborrowdetailsenquiry.selectuldcondition' />",type:1,
				parentWindow:self
				});
		}else{
			submitAction(frm,'/uld.defaults.transaction.ignorereinstatemuc.do');
		}		
		
		//submitAction(frm,'/uld.defaults.transaction.ignorereinstatemuc.do');
}
//Added by A-7359 for ICRD - 224586 starts here
function callbackListULDTransaction (collapse,collapseFilterOrginalHeight,mainContainerHeight){       
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               IC.util.widget.updateTableContainerHeight("#container1",+collapseFilterOrginalHeight);
}
//Added by A-7359 for ICRD - 224586 ends here