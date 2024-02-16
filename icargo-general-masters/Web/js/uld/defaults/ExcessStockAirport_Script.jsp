<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister()
{
var frm=targetFormName;
	with(frm){
		
		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btFlightDetails","excessStockAirport(this.form)",EVT_CLICK);				
		evtHandler.addEvents("btClose","close(this.form)",EVT_CLICK);


	}
	

}
function list(frm){
	submitForm(frm,'uld.defaults.stock.findlistestimateduldstockcount.do');
}
function clear(frm){
	submitFormWithUnsaveCheck('uld.defaults.stock.clearlistestimateduldstockcount.do');
}
function clear(frm){
	submitFormW(frm,'uld.defaults.stock.findairportswithexcessstock.do');
}
function close(frm){

	submitForm(frm,'uld.defaults.stock.closeexcesstockairportlist.do');
}
function submitPage(lastPg,displayPg){ 
    targetFormName.elements.lastPageNum.value=lastPg;
    targetFormName.elements.displayPage.value=displayPg;
	
   submitForm(targetFormName,'uld.defaults.stock.findairportswithexcessstock.do');
}
function submitFltPage(lastPg,displayPg){ 
    targetFormName.elements.fltlastPageNum.value=lastPg;
    targetFormName.elements.fltDisplayPage.value=displayPg;	
	submitForm(targetFormName,'uld.defaults.stock.showFlightDetails.do');	
}
function excessStockAirport(frm){
	var val ="";
	var selectedRows = document.getElementsByName('selectFlag');
	var selecteduldTypeRows = document.getElementsByName('uldTypeCode');
	

	for(var i=0;i<selectedRows.length;i++) {
		
			if(selectedRows[i].checked)	{
				val = selecteduldTypeRows[i].value;
			}
		}
		if(val!=""){
				submitForm(frm,'uld.defaults.stock.showFlightDetails.do?destination='+val);		
		}
		else{
		//showDialog('Please select a row', 1, self);
		showDialog({msg:"Please select a row",
						type:1,
						parentWindow:self,                                       
										
                });
		}

}