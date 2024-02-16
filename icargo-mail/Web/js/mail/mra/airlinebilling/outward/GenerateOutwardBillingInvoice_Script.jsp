<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){


   var frm=targetFormName;

	with(frm){

		evtHandler.addEvents("btnGenInv", "onGenInv()", EVT_CLICK);
		evtHandler.addEvents("btnClose", "onClose(targetFormName)", EVT_CLICK);
		evtHandler.addEvents("btnClose", "resetFocus()", EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addIDEvents("clrprdLov","clearanceLOV()",EVT_CLICK);
		evtHandler.addIDEvents("airlineCodeLov","displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].elements.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);

		callOnScreenLoad();
	}
}

function clearanceLOV(){

	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
	var clearHse = targetFormName.elements.clearingHouse.value;
	
displayLOV('showClearancePeriods.do','N','N','showClearancePeriods.do',document.forms[1].elements.clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0,_reqHeight,'',clearHse);
//displayLOV('showUPUClearancePeriods.do','N','Y','showUPUClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period','1','clearancePeriod','',0,_reqHeight);
}
function callOnScreenLoad(){
	frm = targetFormName;


	if(targetFormName.elements.clearancePeriod.readOnly==false || targetFormName.elements.clearancePeriod.disabled==false){
					targetFormName.elements.clearancePeriod.focus();
	}

	//if(targetFormName.hasGenerated.value=='true'){
			//showDialog('<common:message bundle="generateoutwardbillinginvoice" key="mailtracking.mra.airlinebilling.generateinvoice.msg.info.invgen" scope="request"/>', 2, self);
	//}


}


function onGenInv() {

	frm = targetFormName;

	if(frm.clearancePeriod.value == ''){

		//showDialog('<common:message bundle="generateoutwardbillinginvoice" key="mailtracking.mra.airlinebilling.generateinvoice.msg.err.mnddatefields" scope="request"/>', 1, self);
		showDialog({msg:"<common:message bundle='generateoutwardbillinginvoice' key='mailtracking.mra.airlinebilling.generateinvoice.msg.err.mnddatefields' scope='request'/>",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
		return;
	}
	if(frm.clearingHouse.value == ''){

		//showDialog('<common:message bundle="generateoutwardbillinginvoice" key="mailtracking.mra.airlinebilling.generateinvoice.msg.err.mndclrhousefields" scope="request"/>', 1, self);
		showDialog({msg:"<common:message bundle='generateoutwardbillinginvoice' key='mailtracking.mra.airlinebilling.generateinvoice.msg.err.mndclrhousefields' scope='request'/>",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
		return;
	}

	if(frm.ownAirline.value == frm.airlineCode.value){

			//showDialog('<common:message bundle="generateoutwardbillinginvoice" key="mailtracking.mra.airlinebilling.generateinvoice.msg.err.sameairlinecodes" scope="request"/>', 1, self);
			showDialog({msg:"<common:message bundle='generateoutwardbillinginvoice' key='mailtracking.mra.airlinebilling.generateinvoice.msg.err.sameairlinecodes' scope='request'/>",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
			return;
	}

	submitForm(targetFormName,'mailtracking.mra.airlinebilling.outward.generateinvoice.geninv.do');
}


function onClose(frm) {

	location.href = appPath + "/home.jsp";
}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.clearancePeriod.disabled == false && targetFormName.elements.clearancePeriod.readOnly== false){
			targetFormName.elements.clearancePeriod.focus();
		}
	}
}