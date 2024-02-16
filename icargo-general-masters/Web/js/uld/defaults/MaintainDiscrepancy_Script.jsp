<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
	var frm=targetFormName;
	if(frm !=null){
	disable(); 
	with(frm){
		evtHandler.addEvents("btnClose","onClose();",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
		evtHandler.addEvents("btnSave","onSave();",EVT_CLICK);
		evtHandler.addEvents("facilityType","defaultCombo();",EVT_CHANGE);
		evtHandler.addEvents("uldNo","validateFields(this, -1, 'ULDNo', 0, true, true)",EVT_BLUR);
		evtHandler.addEvents("reportingStation","validateFields(this, -1, 'Reporting Station', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("reportingstationlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.reportingStationChild.value,'ReportingStation','1','reportingStationChild','',0)",EVT_CLICK);
		evtHandler.addEvents("locationlov","populateLocationLov(targetFormName.defaultComboValue.value,targetFormName.reportingStationChild.value)",EVT_CLICK);
		//added by a-3278 for bug 19635 on 29Sep08
		evtHandler.addEvents("discrepancyCode","discrepancyChange();",EVT_BLUR);
		//a-3278 ends
		
		if(targetFormName.elements.filterStatus.value == "uldno") {
			targetFormName.elements.uldNoChild.readOnly=true;
		}
		if(targetFormName.elements.filterStatus.value == "reportingstation") {
			targetFormName.elements.reportingStationChild.readOnly=true;
			targetFormName.elements.reportingstationlov.disabled=true;
		}
		if(targetFormName.elements.filterStatus.value == "uldnoreportingstation") {
			targetFormName.elements.reportingStationChild.readOnly=true;
			targetFormName.elements.reportingstationlov.disabled=true;
			targetFormName.elements.uldNoChild.readOnly=true;
		}
		if(targetFormName.elements.flag.value == "modifymode") {
			targetFormName.elements.uldNoChild.readOnly=true;
		}
		if(targetFormName.elements.flag.value == "fromlistuld") {
		//	targetFormName.flag.value = "";                       
			targetFormName.elements.uldNoChild.readOnly=true;
		}
		onScreenLoad();
		if(targetFormName.elements.discrepancyCode.value=="M" && targetFormName.elements.uldNoChild.value !=""
			&& frm.elements.remarks.disabled==false){
			frm.elements.remarks.focus();
		}
		else if(frm.elements.uldNoChild.disabled==false){
			frm.elements.uldNoChild.focus();
		}
	}
}
}

function resetFocus(){
	 if(!event.shiftKey){ 
				if((!targetFormName.elements.uldNoChild.disabled)  
				 && (targetFormName.elements.uldNoChild.readOnly == false)){
					targetFormName.elements.uldNoChild.focus();
				}							
	}	
}

//added by a-3278 for bug 19635 on 29Sep08
function discrepancyChange(){
	if(targetFormName.elements.discrepancyCode.value=="M" && targetFormName.elements.uldNoChild.value==""){		
	showDialog({msg:"Please enter the ULD Number", type : 1, parentWindow:self, parentForm:targetFormName, dialogId:'id_1'});	
	targetFormName.elements.uldNoChild.focus();
	return;
	}
	if(targetFormName.elements.discrepancyCode.value=="M" && targetFormName.elements.uldNoChild.value !=""){	
	submitForm(targetFormName,'uld.defaults.maintainulddiscrepancies.updatediscrepency.do');	
	}
	if(targetFormName.elements.discrepancyCode.value=="F"){
	//alert(targetFormName.discrepancyCode.value);
	targetFormName.elements.location.disabled = false;
	targetFormName.elements.facilityType.disabled = false;
	targetFormName.elements.reportingStationChild.disabled = false;
	targetFormName.elements.reportingstationlov.disabled = false;
	targetFormName.elements.discrepancyDate.value = "";
	targetFormName.elements.location.value = "";
	//added by a-3278 for bug 23043 on 29Oct08
	if(targetFormName.elements.facilityType.options(0).value =="NIL"){
	targetFormName.elements.facilityType.remove(0);
	}
	//added by a-3278 for bug 23043 on 29Oct08 ends
	targetFormName.elements.facilityType.value = "OPS";	
	targetFormName.elements.defaultComboValue.value="OPS";	
	targetFormName.elements.reportingStationChild.value = "";
	}
	
}
//a-3278 ends


function disable(){
	if(targetFormName.elements.discDisableStat.value=="airline"){
	}
	if(targetFormName.elements.discDisableStat.value=="GHA"){
		targetFormName.elements.reportingStationChild.disabled = true;
		targetFormName.reportingstationlov.disabled = true;
	}
}

function onScreenLoad(){
	var frm = targetFormName;
	
	defaultCombo();
	if(frm.elements.buttonStatusFlag.value != null && frm.elements.buttonStatusFlag.value=="CreateDiscrepancy"){
		openPopUp(appPath+"/uld.defaults.listulddiscrepancies.creatediscrepancy.do",'470','210');
	}
	if(frm.elements.errorStatus.value == "discrepancytobesolved"){
		frm.elements.errorStatus.value ="";
		var uldNumbers = frm.elements.recordUldNumber.value;
		var pointOfLading = frm.elements.recordPOL.value;
		var pointOfUnLading = frm.elements.recordPOU.value;
		var currentStation = frm.elements.recordPOU.value;
		var discrepancyDate = frm.elements.discrepancyDate.value;
		var discrepancyCode = frm.elements.discrepancyCode.value;
		submitForm(frm,"uld.defaults.misc.screenloadrecorduldmovement.do?discrepancyStatus=ulddiscrepancy"+"&uldNumbers="+uldNumbers+"&pointOfLadings="+pointOfLading+"&pointOfUnLadings="+pointOfUnLading+"&currentStation="+currentStation+"&discrepancyDate="+discrepancyDate+"&discrepancyCode="+discrepancyCode);
	}

	if(frm.pageURL != null && frm.elements.pageURL.value=="fromScmUldReconcile"){
				frm.elements.uldNoChild.readOnly=true;
				frm.elements.discrepancyCode.value="F";
		}
	//added by a-3278 for bug 19635 on 29Sep08	
	/*if(frm.listFlag.value == "Y"){
	frm.location.disabled = true;
	frm.facilityType.disabled = true;
	frm.reportingStationChild.disabled = true;
	frm.reportingstationlov.disabled = true;
	} */
	//a-3278 ends
	

}


function defaultCombo(){
	var frm = targetFormName;
	var comboValue = targetFormName.elements.facilityType.value;
	var station = targetFormName.elements.reportingStationChild.value;
	frm.elements.defaultComboValue.value = comboValue;
	frm.elements.defaultComboValue.defaultValue = comboValue;
	
	
}

function populateLocationLov(lovValue,reportingStation){
	
	var comboValue = lovValue;
	
	var strAction = "uld.defaults.listulddiscrepancies.screenloadlocationlov.do?defaultComboValue="+comboValue+"&reportingStationChild="+reportingStation+"&locationName="+"location";
	openPopUp(strAction,"450", "390");
}


function onClose(){
	var frm = targetFormName;
	
    // Added by A-5210  
	if(frm.elements.flag.value == "fromlistuld") {
	   submitForm(frm,"uld.defaults.listuld.do?listStatus=fromSCM");
     }	
	 
	if(frm.pageURL != null && frm.elements.pageURL.value=="fromScmUldReconcile"){
			submitForm(frm,"uld.defaults.maintainulddiscrepancies.closeaction.do");
		}else{
	submitForm(frm,'uld.defaults.maintainulddiscrepancies.closeaction.do?statusFlag=recorduld');
}
}


function onSave(){
	var frm = targetFormName;
	if(frm.elements.discrepancyDate.value != "" && !chkdate(frm.discrepancyDate)){
		showDialog({msg:"Invalid Discrepancy Date", type:1, parentWindow:self, parentForm:frm, dialogId:'id_1'});
		return;
	}
	if(frm.elements.uldNoChild.value ==""){
		showDialog({msg:"Enter ULD No", type:1, parentWindow:self, parentForm:frm, dialogId:'id_1'});
		return;
	}
	if(frm.elements.discrepancyDate.value ==""){
		showDialog({msg:"Enter Discrepancy Date", type:1, parentWindow:self, parentForm:frm, dialogId:'id_1'});
		return;
	}
	if(frm.elements.reportingStationChild.value ==""){
		showDialog({msg:"Enter Reporting Station", type:1, parentWindow:self, parentForm:frm, dialogId:'id_1'});
		return;
	}
	/*if(frm.location.value==""){
	   showDialog("Enter Location", 1, self, frm, 'id_2');
		return;
	}*/ //ICRD-120395 

	var flag = eval(frm.flag);
	submitForm(frm,'uld.defaults.maintainulddiscrepancies.save.do?flag='+flag.value);
}

function selectNextUld(uldLastPageNum,uldDisplayPage){
	var frm=targetFormName;
	frm.elements.uldLastPageNum.value= uldLastPageNum;
	frm.elements.uldDisplayPage.value = uldDisplayPage;
	frm.action ="uld.defaults.listulddiscrepancies.navigatediscrepancy.do";
	frm.submit();
	disablePage();
}