<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad(frm);
   with(frm){

   	//CLICK Events

     	evtHandler.addEvents("btOk","window.close()",EVT_CLICK);

	  	}
}

function onScreenLoad(frm){


}