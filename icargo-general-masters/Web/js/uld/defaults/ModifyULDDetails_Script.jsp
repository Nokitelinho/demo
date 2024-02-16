<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister()
{
	var frm = targetFormName;
	onScreenLoad();
	with(frm){

	evtHandler.addEvents("btnClose","closeModify()",EVT_CLICK);
	evtHandler.addEvents("btnSave","saveModify()",EVT_CLICK);

	//BLUR Events
	evtHandler.addEvents("modDuration","validateFields(this, -1, 'modDuration', 3, true, true)",EVT_BLUR);
	//added by a-3278 for QF1015 on 04Jul08 starts
	evtHandler.addEvents("modRtnDate","returnTabout(this)",EVT_CHANGE);
	//Modified as part of ICRD-3207 by A-3767 on 19Jul11
	evtHandler.addEvents("modTxnDate","enableSave(this)",EVT_CHANGE);
	//a-3278 ends
	evtHandler.addEvents("rtnwaived","validateFields(this, -1, 'rtnwaived', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("rtntaxes","validateFields(this, -1, 'rtntaxes', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("rtndemurrage","validateFields(this, -1, 'rtndemurrage', 2, true, true)",EVT_BLUR);
	//Commenting as part of BUG ICRD-2051
	//evtHandler.addIDEvents("modLoaded","modLoaded()",EVT_CLICK);
	//added by a-3278 for bug 26527 on 21Nov08
	evtHandler.addEvents("dummy","dummy(this.form)",EVT_CLICK);
	//a-3278 ends
	//Added for bugfix starts
	evtHandler.addEvents("modTxnTime","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("txnStation","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("desStation","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("modTxnRemarks","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("modCRN","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("modCRN","validateCRNFormat()",EVT_BLUR);
	evtHandler.addEvents("modUldCondition","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("modAwbNumber","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("modLoaded","enableSave(this)",EVT_CLICK);
	evtHandler.addEvents("modRtnTime","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("returnStation","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("rtnRemarks","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("rtndemurrage","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("rtnwaived","enableSave(this)",EVT_CHANGE);
	evtHandler.addEvents("rtntaxes","enableSave(this)",EVT_CHANGE);
	//ends
	evtHandler.addIDEvents("returnStationLov","openReturnStationLOV();",EVT_CLICK);//Added by A-7426 as part of ICRD-200751
	}


}
//Added by A-7426 as part of ICRD-200751 starts
function openReturnStationLOV() {
	displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[0].returnStation.value,'returnStation','0','returnStation','');
}
//A-7426 ends

function onScreenLoad(){

    //targetFormName.modTxnDate.focus();     
    
    //added by a-3278 for bug 26527 on 21Nov08   
   
    if(targetFormName.elements.dummy.value == "on"){     	
        targetFormName.elements.modRtnTime.value="00:01";    	
    	targetFormName.elements.rtndemurrage.disabled=false;
	targetFormName.elements.rtnwaived.disabled=false;
	targetFormName.elements.rtntaxes.disabled=false;
    	targetFormName.elements.rtnRemarks.focus();    	
     }     
    if(targetFormName.elements.modRtnTime.value=="00:01" && targetFormName.elements.dummy.checked){     
    	targetFormName.elements.dummy.checked=true; 
    	targetFormName.elements.modRtnTime.readOnly = true;
    	targetFormName.elements.returnStation.readOnly = true;
    	//targetFormName.elements.returnStationImg.disabled = true;//Commented by A-7426 as part of ICRD-200751
		disableField(document.getElementById('returnStationLov'));//Added by A-7426 as part of ICRD-200751
    }else{  
    	targetFormName.elements.modRtnTime.readOnly = false;
    	targetFormName.elements.returnStation.readOnly = false;
    	//targetFormName.elements.returnStationImg.disabled = false;//Commented by A-7426 as part of ICRD-200751
		enableField(document.getElementById('returnStationLov'));//Added by A-7426 as part of ICRD-200751
    	targetFormName.elements.dummy.checked=false;          
    }  
     
        //a-3278 ends

    if(targetFormName.elements.closeModifyFlag.value == "Y"){
           self.opener.submitForm(self.opener.targetFormName, "uld.defaults.transaction.refreshlistuldtransactions.do");
           window.close();
    }
	//added by a-3278 for QF1015 on 06Jul08 starts
	if(targetFormName.elements.modRtnDate.value == ""){
		targetFormName.elements.rtndemurrage.disabled=true;
		targetFormName.elements.rtnwaived.disabled=true;
		targetFormName.elements.rtntaxes.disabled=true;
	}else{
	// added by a-3278 for bug 23062 on 29Oct08
	 if(targetFormName.elements.poolOwnerFlag.value == "Y"){    
	               targetFormName.elements.rtndemurrage.disabled=true;
			targetFormName.elements.rtnwaived.disabled=true;
			targetFormName.elements.rtntaxes.disabled=true;
	    }
	    if(targetFormName.elements.poolOwnerFlag.value == "N"){    
	    		targetFormName.elements.rtndemurrage.disabled=false;
	    		targetFormName.elements.rtnwaived.disabled=false;
			targetFormName.elements.rtntaxes.disabled=false;
	    }
	// added by a-3278 for bug 23062 on 29Oct08 ends

		//targetFormName.rtndemurrage.disabled=false;
		//targetFormName.rtnwaived.disabled=false;
	}
	//a-3278 ends
	if(targetFormName.elements.modifiedFlag.value == "Y"){
			setTimeout('enableSave()', 50);
			//enableSave();
	}
}
function enableSave(){
	targetFormName.elements.btnSave.disabled=false;
}
/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

//added by a-3278 for QF1015 on 08Jul08 starts
function returnTabout(obj){
	frm = targetFormName;
	autoFillDate(obj);
	if (dateCheck(obj) == false){
		return;
	}
	if(frm.elements.modRtnDate.value != "" && frm.elements.modTxnDate.value != ""){
	targetFormName.elements.rtndemurrage.disabled=false;
	targetFormName.elements.rtnwaived.disabled=false;
	if(frm.elements.txnStation.value == ""){
	showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.txnStationmandatory" />', 1, self);
	frm.elements.txnStation.focus();
       	return;
	}
	if(frm.elements.txnStation.value != ""){	
	targetFormName.elements.modifiedFlag.value = 'Y';	
	frm.elements.modFlag.value = "changeRtnDate";
	submitAction(frm,'/uld.defaults.transaction.calculatedemurragecharges.do');

	}
	}
}

function txnTabout(obj){
	frm = targetFormName;
	autoFillDate(obj);
	if (dateCheck(obj) == false){
		return;
	}
	if(frm.elements.modTxnDate.value != ""){
	if(frm.elements.txnStation.value == ""){
		showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.txnStationmandatory" />', 1, self);
		frm.elements.txnStation.focus();
	       	return;
	}

	  if(frm.elements.txnStation.value != ""){	  
	    targetFormName.elements.modifiedFlag.value = 'Y'; 
	    submitAction(frm,'/uld.defaults.transaction.calculatedemurragecharges.do');
	     }
	}
}

//a-3278 ends


function saveModify(){
   frm = targetFormName;

	//added by a-3278 for QF1015 starts
   if(frm.elements.modTxnDate.value == ""){
		showDialog({msg		:"<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.modTxnDatemandatory" />",
				     type	:	1, 
				     parentWindow:self,
				     parentForm:targetFormName
            });			
    	frm.elements.modTxnDate.focus();
    	return;
    }
	if(frm.elements.rtndemurrage.value == ""){
		showDialog({msg		:"<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.rtnDemmuragemandatory" />",
				     type	:	1, 
				     parentWindow:self,
				     parentForm:targetFormName
            });		
		frm.elements.rtndemurrage.focus();
		return;
	}
    if(frm.elements.returnStation.value == "" && frm.elements.modRtnDate.value != ""){
		showDialog({msg		:"<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returnStationmandatory" />",
				     type	:	1, 
				     parentWindow:self,
				     parentForm:targetFormName
            });    	
    	frm.elements.returnStation.focus();
    	return;
    }
    if(frm.elements.desStation.value == ""){
		showDialog({	
				     msg		:"<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.desStationmandatory" />",
				     type	:	1, 
				     parentWindow:self,
				     parentForm:targetFormName
            });
    	frm.elements.desStation.focus();
    	return;
    }
    if(frm.elements.txnStation.value == ""){
		showDialog({msg		:"<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.txnStationmandatory" />",
				     type	:	1, 
				     parentWindow:self,
				     parentForm:targetFormName
            });	        
    	frm.elements.txnStation.focus();
    	return;
    }
    //a-3278 ends
  /* if(frm.modDuration.value == ""){
        showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.modDurationmandatory" />', 1, self);
       	frm.modDuration.focus();
       	return;
   }

   if(frm.modTxnRemarks.value == ""){
        showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.modTxnRemarksmandatory" />', 1, self);
        frm.modTxnRemarks.focus();
        return;
   }*/

   frm.action="uld.defaults.transaction.savemodifyuldtransaction.do";
   frm.method="post";
   frm.submit();

}

function closeModify(){
if(isFormModified(targetFormName)) {
		var cnfrm=self.showWarningDialog("Unsaved data exists. Do you want to continue ?");
		    if ( cnfrm==false ){
			document.body.style.cursor = 'default';

		    }
		    else{
		      window.close();
		    }


	}
	else{
 	 window.close();
 	}
}
function navigateULDDetails(strLastPageNum, strDisplayPage){

 frm = targetFormName;

	//added by a-3278 for QF1015 starts
   if(frm.elements.modTxnDate.value == ""){
        showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.modTxnDatemandatory" />', 1, self);
    	frm.elements.modTxnDate.focus();
    	return;
    }
	if(frm.elements.rtndemurrage.value == ""){
		showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.rtnDemmuragemandatory" />', 1, self);
		frm.elements.rtndemurrage.focus();
		return;
	}
    if(frm.elements.returnStation.value == "" && frm.elements.modRtnDate.value != ""){
        showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.returnStationmandatory" />', 1, self);
    	frm.elements.returnStation.focus();
    	return;
    }
    if(frm.elements.desStation.value == ""){
        showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.desStationmandatory" />', 1, self);
    	frm.elements.desStation.focus();
    	return;
    }
    if(frm.elements.txnStation.value == ""){
        showDialog('<bean:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.msg.err.txnStationmandatory" />', 1, self);
    	frm.elements.txnStation.focus();
    	return;
    }


    	frm.elements.modDisplayPage.value = strDisplayPage;
    	frm.elements.modLastPageNum.value = strLastPageNum;
    	  submitForm(frm,'uld.defaults.transaction.navigatemodifyuldtxns.do');
}

function modLoaded(){
	 if(targetFormName.elements.modLoaded.checked){
	   	targetFormName.elements.modLoaded.value = "Y";
	   }else{
	   	targetFormName.elements.modLoaded.value = "N";
	}
}

//added by a-3278 for bug 26527 on 21Nov08
function dummy(frm){
	targetFormName.elements.modifiedFlag.value = 'Y';
	var dummyStation=document.getElementById('dummyStation');//Added by A-5782 for ICRD-107212
	if(frm.elements.dummy.checked){
	frm.elements.dummyAirport.value = frm.elements.returnStation.value;
	frm.elements.dummyDate.value = frm.elements.modRtnDate.value; 
	frm.elements.dummyTime.value = frm.elements.modRtnTime.value;
	frm.elements.dummyDemmurage.value = frm.elements.rtndemurrage.value;
	frm.elements.dummyCRNPrefix.value = frm.elements.modCrnPrefix.value;
	frm.elements.dummyCRN.value = frm.elements.modCRN.value;
		frm.elements.returnStation.value=dummyStation.value;
		frm.elements.modRtnTime.value="00:01";
		frm.elements.modRtnTime.readOnly = true;
		frm.elements.returnStation.readOnly = true;
		//frm.elements.returnStationImg.disabled = true;//Commented by A-7426 as part of ICRD-200751
		disableField(document.getElementById('returnStationLov'));//Added by A-7426 as part of ICRD-200751
		if(frm.elements.modRtnDate.value == ""){		
		frm.elements.dummy.value="Y";
		frm.elements.modFlag.value = "changeRtnDate";
		 submitAction(frm,'/uld.defaults.transaction.calculatedemurragecharges.do');
		 }
		 frm.elements.rtnRemarks.focus();
							
	}
	if(!frm.elements.dummy.checked){
	
		if(frm.elements.hiddenTxnStatus.value=="R"){		
		frm.elements.modRtnTime.readOnly = false;
		frm.elements.returnStation.readOnly = false;
		//frm.elements.returnStationImg.disabled = false;//Commented by A-7426 as part of ICRD-200751
		enableField(document.getElementById('returnStationLov'));//Added by A-7426 as part of ICRD-200751
		frm.elements.returnStation.value = frm.loginStation.value;
		frm.elements.modRtnTime.value = "00:00";
		}
		else{
		frm.elements.dummy.value="N";
		frm.elements.modRtnTime.readOnly = false;
		frm.elements.returnStation.readOnly = false;
		//frm.elements.returnStationImg.disabled = false;//Commented by A-7426 as part of ICRD-200751
		enableField(document.getElementById('returnStationLov'));//Added by A-7426 as part of ICRD-200751
		frm.elements.returnStation.value = frm.elements.dummyAirport.value;
		frm.elements.modRtnDate.value = frm.elements.dummyDate.value; 
		frm.elements.modRtnTime.value = frm.elements.dummyTime.value;
		frm.elements.rtndemurrage.value = frm.elements.dummyDemmurage.value;
		frm.elements.modCrnPrefix.value = frm.elements.dummyCRNPrefix.value ;
		frm.elements.modCRN.value = frm.elements.dummyCRN.value;
		if(frm.elements.modRtnDate.value == ""){
		targetFormName.elements.rtndemurrage.disabled=true;
		targetFormName.elements.rtnwaived.disabled=true;
		targetFormName.elements.rtntaxes.disabled=true;
		frm.elements.modRtnDate.focus();
		}else{
		frm.elements.returnStation.focus();
		}
		}
		
	}
	
}
//a-3278 ends

//Added for ICRD-2492 by A-3767 on 07Jun11
function validateCRNFormat(){
	var crn=targetFormName.elements.modCRN.value;
	if(crn!=null && crn.length >0 ){
		if(crn.length != 8 ){
			showDialog('Invalid CRN format', 1, self);
			targetFormName.elements.modCRN.focus();
			return
		}
	}
	
}