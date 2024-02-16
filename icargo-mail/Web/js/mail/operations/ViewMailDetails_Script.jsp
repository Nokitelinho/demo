<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad();
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnOK","closeViewDetails()",EVT_CLICK);
     	
   	}
}


function onScreenLoad(){

   frm=targetFormName;
   
   setupPanes('container1','tab1');
   displayTabPane('container1','tab2');
   
   frm.selectTab.value = "";
   
}



function closeViewDetails(){

   frm=targetFormName;

   window.close();

}