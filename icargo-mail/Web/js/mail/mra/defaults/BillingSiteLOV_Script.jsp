<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{

	with(targetFormName){

	//CLICK Events

	evtHandler.addEvents("listButton","display()",EVT_CLICK);
	evtHandler.addEvents("clearButton","clearLOVForm()",EVT_CLICK);
	evtHandler.addEvents("closeButton","closeScreen()",EVT_CLICK);
    }
	//onScreenLoad();

}

function display()
{
 submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.showBillingSiteLOVDetails.do');

}

function clearScreen()
{
  submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.clearbillingsitelov.do');

}
function closeScreen()
{
 window.close();

}