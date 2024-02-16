<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){

   var frm=targetFormName;
 	with(frm){
		evtHandler.addEvents("btnList", "onList()", EVT_CLICK);
		evtHandler.addEvents("btnClear", "submitForm(targetFormName,'mailtracking.mra.defaults.listupuratecard.onClear.do')", EVT_CLICK);
		evtHandler.addEvents("btnCopy", "onCopy('mailtracking.mra.defaults.listupuratecard.onCopy.do')", EVT_CLICK);
		evtHandler.addEvents("btnView", "submitAction('mailtracking.mra.defaults.listupuratecard.onView.do')", EVT_CLICK);
		evtHandler.addEvents("btnClose", "submitForm(targetFormName,'mailtracking.mra.defaults.listupuratecard.onClose.do')", EVT_CLICK);
		evtHandler.addEvents("rateCardLov","rateCardLOV()",EVT_CLICK);
		evtHandler.addEvents("headChk","updateHeaderCheckBox(targetFormName, targetFormName.elements.headChk, targetFormName.elements.rowCount)", EVT_CLICK);
		evtHandler.addEvents("rowCount","toggleTableHeaderCheckbox('rowCount',document.forms[1].headChk)",EVT_CLICK);
		callOnScreenLoad();

	}
 applySortOnTable("captureAgtSettlementMemo",new Array("None","String","String","Number","Date","Date","String")); 
}
function rateCardLOV(){
	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
displayLOV('showRateCardLOV.do','N','Y','showRateCardLOV.do',document.forms[1].rateCardID.value,'Rate Card ID','1','rateCardID','',0,_reqHeight);
}


function callOnScreenLoad(action) {
    /*    var clientHeight = document.body.clientHeight;
		var clientWidth = document.body.clientWidth;
		document.getElementById('contentdiv').style.height = ((clientHeight*90)/100)+'px';
	    var pageTitle=30;
		var filterlegend=80;
		var filterrow=80;
		var bottomrow=30;
		var height=(clientHeight*84)/100;
		var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
		document.getElementById('div1').style.height=tableheight+'px';*/
        var frm=targetFormName;
		if(frm.elements.screenStatusFlag.value=="screenload"){
			targetFormName.btnView.disabled = "true";
		}
		if(targetFormName.elements.rateCardID.readOnly==false || targetFormName.elements.rateCardID.disabled==false){
				targetFormName.elements.rateCardID.focus();
		}
		if(frm.elements.changeStatusFlag != null && frm.elements.changeStatusFlag.value == "true"){

			frm.elements.changeStatusFlag.value="false";
			openPopUp("mailtracking.mra.defaults.maintainupuratecard.changestatuspopup.do?fromPage=listupuratecard",230,110);
        }

	return;
}
function onCopy(action){
if(validateSelectedCheckBoxes(frm, 'rowCount', 1, 1)){
	var selectedRows = "";
	var count=0;
	var chkcount = 0;
	var frm=targetFormName;
		var copyCheckBoxes = document.getElementsByName('rowCount');
		chkcount = copyCheckBoxes.length;
			for (j = 0; j < chkcount; j++) {
				if (copyCheckBoxes[j].checked == true) {
					if(count > 0){																				
						selectedRows=selectedRows+",";
					}
					selectedRows=selectedRows+j;
					count = count+1;
				}
				}
		if(selectedRows.length >0){
		
			openPopUp('mailtracking.mra.defaults.listupuratecard.onCopy.do?rowCount='+selectedRows,530,260);
		}else{
			showDialog({msg:"Please select atleast one row",type:1,parentWindow:self});
			return;
		}
}
}
function submitAction(action) {

        frm = targetFormName;

	if(targetFormName.elements.rowCount == null ){
		showDialog({msg:'<common:message bundle="listupuratecardresources" key="mailtracking.mra.defaults.listupuratecard.msg.err.norowselected" scope="request"/>',type: 1, parentWindow:self});
		return;
	}

	var check = validateSelectedCheckBoxes(frm, 'rowCount', 1, 1);

	if(check){
       submitForm(frm,action);
     }else{
       return;
	}
}
function onList() {

	var frm=targetFormName;

	frm.elements.displayPage.value=1;
  	frm.elements.lastPageNum.value=0;
	submitForm(frm,'mailtracking.mra.defaults.listupuratecard.onList.do?navigationMode=FILTER');

}

function submitPage(lastPg, displayPg) {

	targetFormName.lastPageNum.value=lastPg;
	targetFormName.displayPage.value=displayPg;
	targetFormName.action="mailtracking.mra.defaults.listupuratecard.onList.do?navigationMode=NAVIGATION";
	targetFormName.submit();
}

function updateCheck(){

	var chkBoxIds = document.getElementsByName('rowCount');
	var isChecked = 0;
	var length= chkBoxIds.length;

	for(var i=0;i<chkBoxIds.length;i++){

		if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;
		}

	}

	if(isChecked != length){
		document.forms[1].headChk.checked=false;
	}else{
		document.forms[1].headChk.checked=true;
	}

}

function onChangeStatus(action){

	var frm=targetFormName;
	var chkBoxIds = document.getElementsByName('rowCount');
	var isChecked = 0;
	var length= chkBoxIds.length;

	for(var i=0;i<chkBoxIds.length;i++){

		if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;
		}

	}

	if(isChecked == 0){

		showDialog({msg:'<common:message bundle="listupuratecardresources" key="mailtracking.mra.defaults.listupuratecard.msg.err.norowselected" scope="request"/>',type: 1, parentWindow:self});
		return;
	}else{

		submitForm(frm,action);
	}

}