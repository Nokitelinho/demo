<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister() {
	var frm = targetFormName;
	with(frm){
		evtHandler.addEvents("btList","List()",EVT_CLICK);
		evtHandler.addEvents("btPrint","onPrint(this.form)",EVT_CLICK);
		evtHandler.addEvents("btView","onView(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clear()",EVT_CLICK);
		evtHandler.addEvents("btViewRequest","viewRequest()",EVT_CLICK);
		evtHandler.addEvents("btCreateRequest","createRequest()",EVT_CLICK);
		evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("btStockManager","onStockManager()",EVT_CLICK);
		evtHandler.addEvents("rowId","singleSelectRow(this)",EVT_CLICK);
		//evtHandler.addEvents("airlineValue","selectAll(this)",EVT_CLICK);

	}
	onScreenLoading();
	
	applySortOnTable("stocklisttable",new Array("None","String", "String","String","Number", "Number"));
	
	
}

function selectAll(obj){
	updateHeaderCheckBox(targetFormName,targetFormName.elements.airlineValue, targetFormName.elements.rowId);
}

function onPrint(frm){
   	frm.elements.preview.value="false";
	//frm.action = "/stockcontrol.defaults.cto.stocklistreport.do";
	//generateReport(frm,frm.action);
	generateReport(frm,"/stockcontrol.defaults.cto.stocklistreport.do");
}

function onView(frm){
	frm.elements.preview.value="true";
	frm.action = "/stockcontrol.defaults.cto.stocklistreport.do";
	generateReport(frm,frm.action);
}

function closeScreen() {
	submitForm(targetFormName,'stockcontrol.defaults.cto.home.do');
}

function clear() {
	submitForm(targetFormName,'stockcontrol.defaults.clearliststocks.do');
}

function List() {
	targetFormName.elements.isButtonClicked.value = "N";
	targetFormName.elements.fromStockList.value="N";
	submitForm(targetFormName,'stockcontrol.defaults.liststocks.do?countTotalFlag=YES');
}

function createRequest() {
	openPopUp("stockcontrol.defaults.cto.createstockrequestscreenload.do",'650','250');
}

function viewRequest() {
	if(validateSelectedCheckBoxes(targetFormName,'rowId',1,1)){
		var pos = 0;
		var status;
		var key;
		frm=targetFormName;

		for(var i=0; i<frm.elements.length; i++) {
			if(frm.elements[i].type == "checkbox") {
				if(frm.elements[i].checked == true) {
				    key = frm.elements[i].value;
				}
			}
		}
		submitForm(targetFormName,'stockcontrol.defaults.cto.reloadstockrequest.do');
	}

}

function onScreenLoading(){
	targetFormName.elements.airline.focus();
	if(targetFormName.elements.afterReload.value=="Y"){
		targetFormName.elements.afterReload.value="N";
		openPopUp("stockcontrol.defaults.cto.viewstockrequest.do",'650','305');
	}
	if(targetFormName.elements.forMessage.value=="Y" && targetFormName.elements.fromStockList.value=="Y"){
		targetFormName.elements.forMessage.value ="N";
		submitForm(targetFormName,'stockcontrol.defaults.cto.liststockmanager.do');

	}
}

function submitPage(lastPg,displayPg){

	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	targetFormName.elements.isButtonClicked.value = "Y";
	submitForm(targetFormName,'stockcontrol.defaults.liststocks.do');
}

function onStockManager(){
	if(validateSelectedCheckBoxes(targetFormName,'rowId',1,1)){
		submitForm(targetFormName,'stockcontrol.defaults.cto.showstockmanager.do');
	}
}

function singleSelectRow(obj){
	var checkVal = obj.value;

	var flag=0;//if nothing is checked
	for(var i=0;i<targetFormName.elements.length;i++){
		if(targetFormName.elements[i].type =='checkbox'){
			if(targetFormName.elements[i].checked == true){
				flag=1;
				if(targetFormName.elements[i].value != checkVal){
					targetFormName.elements[i].checked = false;
				}
			}
		}
	}
	
	if(flag==0){
		checkVal="";
	}
	
	toggleTableHeaderCheckbox('rowId', targetFormName.elements.airlineValue);

}