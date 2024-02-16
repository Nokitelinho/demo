<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{

   with(targetFormName){

     //CLICK Events
     	evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);


   	}
}

function doClose() {
	close();

}


