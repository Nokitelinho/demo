<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

 function screenSpecificEventRegister(){

 var frm=targetFormName;
 with(frm){
  evtHandler.addEvents("btClose","performAction(this.form,'Close')",EVT_CLICK);
 evtHandler.addEvents("btOk","performAction(this.form,'OK')",EVT_CLICK);
 evtHandler.addEvents("mailDistFactor","validateFields(this,-1,'MailDistFactor',2,true,true)",EVT_BLUR);
 evtHandler.addEvents("svTkm","validateFields(this,-1,'SV-TKM',2,true,true)",EVT_BLUR);
 evtHandler.addEvents("salTkm","validateFields(this,-1,'Sal-TKM',2,true,true)",EVT_BLUR);
 evtHandler.addEvents("airmialTkm","validateFields(this,-1,'Airmail-TKM',2,true,true)",EVT_BLUR);
 
 onCopy(frm);
 }
}

function confirmMessage() {
 targetFormName.elements.opFlag.value="I";
	disableButtons();
}
function nonconfirmMessage() {
	window.close();
}
 function performAction(frm, actionType){
 
 if(actionType == 'Close'){

  window.close();
}

if(actionType == 'OK'){

 var frm=targetFormName;

 
 if(frm.elements.rateCardIDNew.value=="" ){
		showDialog({msg:"Rate Card ID is Mandatory",type: 1,parentWindow:self});
		return;
	}
if(frm.elements.mailDistFactor.value=="" ){
		showDialog({msg:"Mail Dist Factor is Mandatory",type: 1, parentWindow:self});
		return;
	}
if(frm.elements.svTkm.value=="" ){
		showDialog({msg:"SV-TKM is Mandatory", type:1,parentWindow:self});
		return;
	}
if(frm.elements.salTkm.value=="" ){
		showDialog({msg:"Sal-TKM is Mandatory", type:1,parentWindow:self});
		return;
	}
if(frm.elements.airmialTkm.value=="" ){
		showDialog({msg:"Airmail-TKM is Mandatory",type:1,parentWindow:self});
		return;
	}
if(frm.elements.validFrom.value=="" ){
		showDialog({msg:"Valid From is Mandatory",type: 1,parentWindow:self});
		return;
	}
if(frm.elements.validTo.value=="" ){
		showDialog({msg:"Valid To is Mandatory",type:1, parentWindow:self});
		return;
	}
 submitForm(frm,'mailtracking.mra.defaults.copyupuratecard.onOk.do');

 }
}
function confirmMessage() {
 submitForm(frm,'mailtracking.mra.defaults.copyupuratecard.onOk.do');
}
function nonconfirmMessage() {
	 targetFormName.elements.opFlag.value="I";
	disableButtons();
}
function onCopy(frm){
if(frm.elements.copyFlag.value == "true"){     
	    frm.elements.copyFlag.value="";		
		showDialog({msg:'Selected Rate Card is successfully copied',type :2, parentWindow:self,parentForm:targetFormName,
						onClose:function(result){
							if(!result) {
        window.close();
						}
						}
						});
	}
}