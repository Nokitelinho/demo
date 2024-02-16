<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad(frm);
   onloadrelist(frm);
   with(frm){

   	//CLICK Events
     	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btDelete","deleteOe(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClose","window.close()",EVT_CLICK);

		evtHandler.addIDEvents("cityLOV","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.cityCode.value,'city','0','cityCode','',0)", EVT_CLICK);
		evtHandler.addIDEvents("paLOV","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.poaCode.value,'PA','0','poaCode','',0)", EVT_CLICK);
		evtHandler.addIDEvents("mailboxidLOV","displayLOV('mailtracking.defaults.mailboxidlov.list.do','N','Y','mailtracking.defaults.mailboxidlov.list.do',targetFormName.elements.mailboxId.value,'mailboxidLOV','0','mailboxId','',0)", EVT_CLICK);
     //BLUR Events
     	evtHandler.addEvents("code","validateOE(this.form)",EVT_BLUR);

	  	}
}

function onScreenLoad(frm){

	var mode = frm.elements.status.value;

	if("ADD" == mode){
		disableField(frm.elements.btDelete);

		frm.elements.code.readOnly = false;
		frm.elements.cityCode.readOnly = false;
		enableField(document.getElementById("cityLOV"));
	}else {
		enableField(frm.btDelete);

		frm.elements.code.readOnly = true;
		frm.elements.cityCode.readOnly = true;
		disableField(document.getElementById("cityLOV"));
	}


	/*var close = frm.elements.popUpStatus.value;

	if("CLOSE" == close){
		frm.elements.popUpStatus.value = "";
		/*var oeInfo = frm.elements.ooeInfo.value;
		var oeInfoArr = oeInfo.split("-");

		if(oeInfoArr[0] == "D"){
		showDialog({msg:"Data deleted successfully",type:2,parentWindow:self});

		}else{
		showDialog({msg:"Office of exchange code"+oeInfoArr[1]+" saved successfully",type:2,parentWindow:self});

		}

		frm = self.opener.targetFormName;
		var dispPage = frm.elements.displayPage.value;
		if(dispPage == ""){
			dispPage = 1;
		}

		/*frm.action="mailtracking.defaults.oemaster.list.do?displayPage="+dispPage;
		frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler();
		window.close();
		frm.submit();*/
		//return;
	//}
}
//Added by A-8527 for bug IASCB-30982 starts
function onloadrelist(frm){
	var close = frm.elements.popUpStatus.value;
	if("CLOSE" == close){
		frm.elements.popUpStatus.value = "";
		targetFormName.elements.popUpStatus.value = "";
		var officeexchgval = frm.elements.ooexchfltrval.value;
		frm.elements.officeOfExchange.value=officeexchgval;
		frm.elements.displayPage.value=1;
		window.opener.IC.util.common.childUnloadEventHandler();
		window.close();
		window.opener.submitForm(window.opener.targetFormName,'mailtracking.defaults.oemaster.list.do');
       	return;
	}
}
//Added by A-8527 for bug IASCB-30982 ends
function saveDetails(frm){
	window.opener.IC.util.common.childUnloadEventHandler();
	//Added by A-8527 for bug IASCB-30982
	var offexchval=frm.elements.ooexchfltrval.value;
	submitForm(frm,"mailtracking.defaults.oemaster.save.do?ooexchfltrval="+offexchval);
}

function deleteOe(frm){
	submitForm(frm,"mailtracking.defaults.oemaster.save.do?status=DELETE");
}
function validateOE(frm){
	var oe = frm.elements.code.value;
	if(oe.length != 6 ){
	showDialog({msg:"<common:message bundle='officeOfExchangeResources' key='mailtracking.defaults.oemaster.msg.alrt.oelength' />", type:1, parentWindow:self});
		return;
	}
}