<%@ include file="/jsp/includes/js_contenttype.jsp"%>
<%@ include file="/jsp/includes/tlds.jsp"%>

function screenSpecificEventRegister() {
var frm = targetFormName;
with(targetFormName){
evtHandler.addIDEvents("btOK","closefn()",EVT_CLICK);
evtHandler.addIDEvents("btnOK","remarksfn()",EVT_CLICK);
evtHandler.addIDEvents("btClose","closefn()",EVT_CLICK);
}
onscreenload();
}
function onscreenload(){

var actionFlag =targetFormName.elements.actionFlag.value;
if(actionFlag=="VIEW"){
IC.util.widget.createDataTable("viewhistory",{tableId:"viewhistory",hasChild:false,scrollingY:'55vh'});
jquery('#remarks').hide();
jquery('#view_history').show();
}
else if(actionFlag=="REMARK"){
jquery('#remarks').show();
jquery('#view_history').hide();
}
else if(actionFlag=="REMARK_CLOSE")
{
	closefn();
}
}
function closefn(){
popupUtils.closePopupDialog();
}
function closefn(){
popupUtils.closePopupDialog();
}
function remarksfn(){
	//alert(targetFormName.elements.selectedIndex[0].value);
//alert("RemarksSave1000 success");
var action=targetFormName.actionFlag.value;
var selectedRow = targetFormName.elements.selectedRow.value;
//alert(targetFormName.elements.selectedRow.value);
submitForm(targetFormName,"mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.remarksSave.do?selectedIndex="+selectedRow+"&actionFlag="+action);                                        
//popupUtils.closePopupDialog();
//alert("RemarksSave1 success");
}












