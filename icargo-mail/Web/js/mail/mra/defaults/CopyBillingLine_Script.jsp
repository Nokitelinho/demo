<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{
	var frm=targetFormName;


	with(targetFormName){

	evtHandler.addEvents("btnList","onList()",EVT_CLICK);
	evtHandler.addEvents("btnClear","onClear()",EVT_CLICK);
	evtHandler.addEvents("btnOk","onOk()",EVT_CLICK);
	evtHandler.addIDEvents("blgMatrixIDLov","displayLOV('showBillingMatrixLOV.do','N','Y','showBillingMatrixLOV.do',document.forms[0].blgMatrixId.value,'Billing Matrix','0','blgMatrixId','',0)", EVT_CLICK);
	evtHandler.addEvents("btClose","onClose()",EVT_CLICK);
	}
	onLoadPopup(targetFormName);


}
function onLoadPopup(frm){

  if(frm.screenMode.value=="capture"){

	  frm.elements.blgMatrixId.disabled="true";
	  //frm.elements.blgMatrixIDLov.disabled="true";
	  document.getElementById("blgMatrixIDLov").disabled=true;
	  frm.elements.validFrom.readOnly=false;
	  frm.elements.validTo.readOnly=false;
	  enableField(frm.elements.btn_validFrom);
	  enableField(frm.elements.btn_validTo);

  }
   if(frm.elements.screenMode.value=="list"){
	   frm.elements.validFrom.disabled="true";
	   frm.elements.validTo.disabled="true";
	   frm.elements.btn_validFrom.disabled="true";
	   frm.elements.btn_validTo.disabled="true";
	   //frm.blgMatrixIDLov.disabled="true";
	   document.getElementById("blgMatrixIDLov").dsiabled=true;

    }
    if(frm.elements.screenMode.value==""){
       frm.elements.blgMatrixId.focus();
       frm.elements.btnOk.disabled=true;
       frm.elements.validFrom.disabled="true";
       frm.elements.validTo.disabled="true";
       frm.elements.btn_validFrom.disabled="true";
       frm.elements.btn_validTo.disabled="true";


    }


  if(frm.screenFlag.value=="open"){

     window.close();
      window.opener.document.forms[1].action="mailtracking.mra.defaults.openmaintainblgmatrixscreen.do?operationFlag="+frm.opFlag.value;
	  window.opener.IC.util.common.childUnloadEventHandler();
     window.opener.document.forms[1].submit();


  }

}

/** List call **/

function onList(){
var frm=targetFormName;

  submitForm(frm,"mailtracking.mra.defaults.listcopybillingline.do");
}

/** clear call **/

function onClear(){
submitForm(targetFormName,"mailtracking.mra.defaults.clearcopybillingline.do");

}


/** OK call **/

function onOk(){
  submitForm(targetFormName,"mailtracking.mra.defaults.okcopybillingline.do");

}

/** Close call **/

function onClose(){
window.close();
}

/** confirm popup message**/
function confirmPopupMessage(){
var frm=targetFormName;
//frm.validFrom.readOnly="false";
//frm.validTo.readOnly="false";
frm.elements.blgMatrixId.disabled="true";
//frm.blgMatrixIDLov.disabled="true";
document.getElementById("blgMatrixIDLov").dsiabled=true;
frm.elements.opFlag.value="I";

}

/** Non Confirm popup message**/
function nonconfirmPopupMessage(){
onClear();

}

