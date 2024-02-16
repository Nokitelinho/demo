<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister()
{
var frm=targetFormName;
	with(frm){
		onScreenLoad();
		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btDetails","excessStockAirport(this.form)",EVT_CLICK);				
		evtHandler.addEvents("btClose","close(this.form)",EVT_CLICK);


	}
	

}
function onScreenLoad(){
var frm=targetFormName;
	var val=targetFormName.elements.listStatus.value;
	if(val=="list_success"){
		//alert(val);
		frm.elements.airport.disabled=true;
	}
	else{
		frm.elements.airport.disabled=false;
	}
	//if(frm.selectFlag!=null && frm.selectFlag)
}
function list(frm){
	submitForm(frm,'uld.defaults.stock.findlistestimateduldstockcount.do');
}
function clear(frm){
	submitFormWithUnsaveCheck('uld.defaults.stock.clearlistestimateduldstockcount.do');
}
function close(frm){

	submitForm(frm,'uld.defaults.stock.closeestimatedULDstock.do');
}

function submitPage(lastPg,displayPg){ 
   
	targetFormName.elements.stockLastPageNum.value=lastPg;
    targetFormName.elements.stockdisplayPage.value=displayPg;
    submitForm(targetFormName,'uld.defaults.stock.findlistestimateduldstockcount.do');
}
function excessStockAirport(frm){
	var val ="";
	var selectedRows = document.getElementsByName('select');
	var selecteduldTypeRows = document.getElementsByName('uldTypeCode');
	

	for(var i=0;i<selectedRows.length;i++) {
	
			if(selectedRows[i].checked)	{
				val = selecteduldTypeRows[i].value;
			
			}
		}
		var action="List";
		if(val!=""){
			submitForm(frm,"uld.defaults.stock.findairportswithexcessstock.do?uldType="+val+"&screenInvokeActionStatus="+action);	
		}
		else{
		//showDialog('Please select a row', 1, self);
		showDialog({msg:"Please select a row",
						type:1,
						parentWindow:self,                                       
										
                });
		}

}