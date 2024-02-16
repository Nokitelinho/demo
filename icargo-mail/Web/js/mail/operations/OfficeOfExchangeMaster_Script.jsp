<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
     	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);

     	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.elements.checkAll,targetFormName.elements.rowId)",EVT_CLICK);

    	if(frm.rowId != null){
			evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',targetFormName.elements.checkAll)",EVT_CLICK);
		}

		evtHandler.addIDEvents("addLink","addSubClass()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteSubClass()",EVT_CLICK);

		evtHandler.addIDEvents("oeLOV","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.officeOfExchange.value,'OE','1','officeOfExchange','',0)", EVT_CLICK);


     //BLUR Events

	  	}
}
function closeScreen(){
location.href = appPath + "/home.jsp";
}
function onScreenLoad(frm){
	var popUp = frm.elements.popUpStatus.value;
	var mode = frm.elements.status.value;

	if((!frm.officeOfExchange.disabled) && (!frm.officeOfExchange.readOnly)){
		frm.officeOfExchange.focus();
	}

	if("SHOW" == popUp){
		frm.popUpStatus.value = "";
		//Added by A-8527 for bug IASCB-30982
		var officeexchge=frm.elements.officeOfExchange.value;
		openPopUp("mailtracking.defaults.addoe.screenload.do?status="+mode+"&ooexchfltrval="+officeexchge,"550","200");
	}
}

function resetFocus(frm){
	 if(!event.shiftKey){
		if((!frm.officeOfExchange.disabled) && (!frm.officeOfExchange.readOnly)){
			frm.officeOfExchange.focus();
		}
	}
}

function listDetails(frm){

	frm.elements.displayPage.value=1;
   	frm.elements.lastPageNum.value=0;

	submitForm(frm,"mailtracking.defaults.oemaster.list.do");
}

function saveDetails(frm){

	submitForm(frm,"mailtracking.defaults.oemaster.save.do");
}

function clearDetails(frm){

	submitForm(frm,"mailtracking.defaults.oemaster.clear.do");
}

function addSubClass(){
	frm = targetFormName;
    if(frm.elements.displayPage.value==""){
		frm.elements.displayPage.value=1;
		frm.elements.lastPageNum.value=0;
	}
	submitForm(frm,"mailtracking.defaults.oemaster.add.do?status=ADD");
}

function deleteSubClass(){

	frm = targetFormName;
	//Added by A-8527 for bug IASCB-30982
	var officeexchge=frm.elements.officeOfExchange.value;
	frm.elements.ooexchfltrval.value=officeexchge
	if(validateSelectedCheckBoxes(frm,'rowId',1,1)){

		submitForm(frm,"mailtracking.defaults.oemaster.add.do?status=UPDATE");
	}
}

function submitPage(lastPg,displayPg){

  var frm=targetFormName;
  frm.elements.lastPageNum.value=lastPg;
  frm.elements.displayPage.value=displayPg;
  submitForm(frm,'mailtracking.defaults.oemaster.list.do');

}