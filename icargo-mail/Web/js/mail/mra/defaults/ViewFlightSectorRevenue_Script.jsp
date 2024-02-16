<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){


	with(targetFormName){
		screenload();
		evtHandler.addEvents("btnList","doList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","doClear()",EVT_CLICK);
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btnRevdtl","doRevenueDtls()",EVT_CLICK);
		evtHandler.addEvents("btnAccDetails","showAccDetails()",EVT_CLICK);
		evtHandler.addEvents("btnViewExceptions","showExceptions()",EVT_CLICK);


	}

}
/**
*Screen Load
*/
function screenload(){
	disableField(targetFormName.elements.carrierCode);
	// initial focus on page load
	if(targetFormName.elements.flightNo.disabled == false) {
	   targetFormName.elements.flightNo.focus();
	}
	if(targetFormName.elements.sectorCtrlFlg.value == "Y") {
		diableField(targetFormName.elements.sector);
		targetFormName.elements.sectorCtrlFlg.value = "N";

	}
}
/**
*Function for listing the sectors
*/
function doList(){
	submitForm(targetFormName,'mailtracking.mra.defaults.viewflightsectorrevenue.list.do');
}
/**
*Function for Close
*/
function doClose(){
	submitForm(targetFormName,'mailtracking.mra.defaults.sectorrevenue.close.do');
	//location.href = appPath+"/home.jsp";

}
/**
*Function for Listing the Revenue Details
*/
function doRevenueDtls(){
	submitForm(targetFormName,'mailtracking.mra.defaults.viewflightsectorrevenue.revenuedtls.do');

}
/**
*Function for Clearing Details
*/
function doClear(){
	submitForm(targetFormName,'mailtracking.mra.defaults.viewflightsectorrevenue.clear.do');

}

function showAccDetails(){
var chkbox = document.getElementsByName("selectedRows");
if(chkbox.length > 0){
	if(validateSelectedCheckBoxes(targetFormName,'selectedRows','1','1')){
		for(var i=0;i<chkbox.length;i++) {
			if(chkbox[i].checked){
				submitForm(targetFormName,'mailtracking.mra.defaults.sectorrevenue.viewaccountingentry.do?selectedDsn='+chkbox[i].value);

				}
			}
		}
	}
}

function showExceptions(){
	frm = targetFormName;
		var checkBox = document.getElementsByName('selectedRows');
		var count=0;
		if(validateSelectedCheckBoxes(targetFormName,'selectedRows',1,1)){
		for(var i=0;i<checkBox.length;i++) {
						if(checkBox[i].checked)	{
							count=i;
						}
					}
			//frm.selectedRow.value=count;
		submitForm(targetFormName,'mailtracking.mra.defaults.sectorrevenue.viewexceptions.do?count='+count);
}
}