<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad();
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btOk","saveSelectedULDs()",EVT_CLICK);
     	evtHandler.addEvents("btCancel","cancelSelectedULDs()",EVT_CLICK);

		evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.elements.checkAll,targetFormName.elements.selectULDs)",EVT_CLICK);

		if(frm.elements.selectULDs != null){
			evtHandler.addEvents("selectULDs","toggleTableHeaderCheckbox('selectULDs',targetFormName.checkAll)",EVT_CLICK);
		}


   	}
}

function onScreenLoad(){
  frm=targetFormName;
  
  if(frm.elements.uldsPopupCloseFlag.value == "Y"){
  
      var popupflag = "Y";
      if(frm.elements.fromScreen.value == "MANIFEST"){
        frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
	frm.action="mailtracking.defaults.mailmanifest.listmailmanifest.do?uldsPopupCloseFlag="+popupflag;
      
      }else{
      
	var str = "FLIGHT";
	frm = self.opener.targetFormName;
	frm.action="mailtracking.defaults.mailacceptance.listmailacceptance.do?assignToFlight="+str+"&uldsPopupCloseFlag="+popupflag;
		
      }
      frm.elements.method="post";
      frm.submit();
      window.close();
      return;
  }
}

/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

function saveSelectedULDs(){

  frm=targetFormName;
  frm.elements.uldsSelectedFlag.value="Y";
  submitAction(frm,'/mailtracking.defaults.mailacceptance.closeflight.do');

}

function cancelSelectedULDs(){
  frm=targetFormName;
  frm.elements.uldsSelectedFlag.value="Y";
  submitAction(frm,'/mailtracking.defaults.mailacceptance.closeflight.do');
}