<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

window.onload = screenSpecificEventRegister;
function screenSpecificEventRegister() {
	var frm=targetFormName;
	with(frm){
		onScreenLoad();
		evtHandler.addEvents("btSave","doSave(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","windowClose()",EVT_CLICK);
		evtHandler.addEvents("facilitycodelov","showFacilityCode()",EVT_CLICK);
		evtHandler.addEvents("locationType","locationChange()",EVT_CHANGE);


}
}

function windowClose(){
	close();
}

function doSave(frm){
	if(targetFormName.elements.locationType.value=="" ){
		showDialog({msg:'Location Type cannot be blank', type:1, parentWindow:self, parentForm:targetFormName,dialogId:'id_77'});
		return;
	}
  if(targetFormName.elements.toLocation.value=="" ){
		showDialog({msg:'To Location cannot be blank',  type:1, parentWindow:self, parentForm:targetFormName,dialogId:'id_77'});
		return;
	}
	if(targetFormName.elements.fromLocation.value == targetFormName.elements.toLocation.value){
		showDialog({msg:'Cannot Relocate to same location',  type:1, parentWindow:self, parentForm:targetFormName,dialogId:'id_77'});
		return;
	}
	if(targetFormName.elements.locationType.value=='AGT'){
		showDialog({msg:'Cannot Relocate to Agent Location', type:1, parentWindow:self, parentForm:targetFormName,dialogId:'id_77'});
		return;
	}
	submitForm(frm,'uld.defaults.listuld.saverelocateuld.do');
}

function onScreenLoad(){
 	var flagForCheck=targetFormName.elements.checkFlag.value;
	 if( flagForCheck=='relocateUld') {
	 targetFormName.elements.checkFlag.value="";
	   opener.submitForm(window.opener.targetFormName,"uld.defaults.listuld.do");
	   window.close();
	}
}

function showFacilityCode(){
	if(targetFormName.elements.locationType.value=="" ){
		showDialog({msg:'Location Type cannot be blank', type:1, parentWindow:self, parentForm:targetFormName,dialogId:'id_77'});
		return;
	}
	targetFormName.elements.currentStation.defaultValue=targetFormName.elements.currentStation.value;
	targetFormName.elements.locationType.defaultValue=targetFormName.elements.locationType.value;
	var textfiledDesc="";
	var currentStationForLov=targetFormName.elements.currentStation.value;
	var facilityTypeForLov=targetFormName.elements.locationType.value;
	var strAction="uld.defaults.lov.screenloadfacilitycodelov.do";

	var StrUrl=strAction+"?textfiledObj=toLocation&formNumber=0&textfiledDesc="+textfiledDesc+'&facilityTypeForLov='+facilityTypeForLov+'&currentStationForLov='+currentStationForLov;
	var myWindow = openPopUpWithHeight(StrUrl, "500");
}

function locationChange() {
	if(targetFormName.elements.locationType.defaultValue!=targetFormName.elements.locationType.value)
	{
	targetFormName.elements.toLocation.value="";

	}
}