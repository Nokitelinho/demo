<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>



    function screenSpecificEventRegister(){
	var frm=targetFormName;

	// initial focus on page load
	if (frm.elements.dsn.disabled == false) {
	    frm.elements.dsn.focus();
	}

	with(targetFormName){
	evtHandler.addEvents("btList","list(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btClear","clear(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btClose","close(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btRateAudit","rateAudit(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btRateAuditDetails","viewRateAudit(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btListprorationException","listprorationException(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btPrint","print(targetFormName)",EVT_CLICK);
	evtHandler.addIDEvents("paLOV","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'PA','1','gpaCode','',0)", EVT_CLICK);
	evtHandler.addIDEvents("subClassLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'OfficeOfExchange','1','subClass','',0)", EVT_CLICK);

	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.rowId)",EVT_CLICK);
	}
	OnScreenload();
}



function OnScreenload(){
	targetFormName.elements.rateauditFlag.value="N";
}
function list(targetFormName){

	targetFormName.elements.lastPageNumber.value=0;

	targetFormName.elements.displayPageNum.value=1;

	submitForm(targetFormName,'mailtracking.mra.defaults.listrateaudit.listrateauditdetails.do');

}

function submitPage1(strLastPageNum,strDisplayPage){

	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPageNum.value = strDisplayPage;
	submitForm(targetFormName, 'mailtracking.mra.defaults.listrateaudit.listrateauditdetails.do');
}



function clear(targetFormName){


	submitForm(targetFormName,'mailtracking.mra.defaults.listrateaudit.clearrateauditdetails.do');
}
function close(targetFormName){


	submitForm(targetFormName,'mailtracking.mra.defaults.listrateaudit.closerateauditdetails.do');
}
function rateAudit(targetFormName){
	var check=0;
	targetFormName.rateauditFlag.value="Y";

	var chkbox = document.getElementsByName("rowId");

	var form=targetFormName;

	var applyAudit=document.getElementsByName("applyAudit");

	var applyAutdits=document.getElementsByName("applyAutdits");

	var dsnStatus=document.getElementsByName("saveDsnStatus");

	var exceptnFlg=document.getElementsByName("excepFlg");

	for (var i =0; i < applyAudit.length; i++){

	if(exceptnFlg[i].value!="Y"){

	if(applyAudit[i].checked==true){

	applyAutdits[i].value="Y";


	}
	else if(applyAudit[i].checked==false){

	 applyAutdits[i].value="N";

	}
	}
	}



	for(var i=0;i<chkbox.length;i++) {
	if(chkbox[i].checked==true){
	check=check+1;
	}
	}
	if(check==0){
		//showDialog("Please select atleast a row",1,self);
		showDialog({msg:'Please select atleast a row',type:1,parentWindow:self});
	}

	var selectedRows="";
	var j=0;
	for(var i=0;i<chkbox.length;i++) {
		if(chkbox[i].checked== true){
			//selectedRows[j++]=chkbox[i].value;

			if(dsnStatus[i].value=='F' && form.rateauditFlag.value=="Y"){

						//showDialog("DSN is already rateaudited",1,self);
						showDialog({msg:'DSN is already rateaudited',type:1,parentWindow:self});
						form.elements.rateauditFlag.value="N";
						return;
				}

			if(exceptnFlg[i].value=="Y"){
				//showDialog('<bean:message bundle="mralistrateaudit" key="mailtracking.mra.defaults.listrateaudit.importexcep" />',1,self);
				showDialog({msg:'<bean:message bundle="mralistrateaudit" key="mailtracking.mra.defaults.listrateaudit.importexcep" />',type:1,parentWindow:self});
		        return;
			}

			if(selectedRows != "")
				selectedRows = selectedRows+","+chkbox[i].value;
			else if(selectedRows == "")
				selectedRows = chkbox[i].value;

			}

		}
	if(check>0){
	targetFormName.elements.selectedRows.value=selectedRows;

	submitForm(targetFormName,'mailtracking.mra.defaults.listrateaudit.changerateauditstatus.do');
	}
}
function viewRateAudit(targetFormName){

	var check=0;
	var chkbox = document.getElementsByName("rowId");

	var exceptnFlg=document.getElementsByName("excepFlg");

	for(var i=0;i<chkbox.length;i++) {
		if(chkbox[i].checked== true){
			check=check+1;
		}
	}
	if(check==0){
		//showDialog("Please select a row",1,self);
		showDialog({msg:'Please select a row',type:1,parentWindow:self});
	}

	else if(check>1){
		//showDialog("Please select only one row",1,self);
		showDialog({msg:'Please select only one row',type:1,parentWindow:self});
	}else{


	var chkbox = document.getElementsByName("rowId");
	for(var i=0;i<chkbox.length;i++) {
		if(chkbox[i].checked== true){
			targetFormName.elements.selectedRowIndex.value=chkbox[i].value;
			var selectedRow = targetFormName.elements.selectedRowIndex.value;
			if(exceptnFlg[i].value=="Y"){
				//showDialog('<bean:message bundle="mralistrateaudit" key="mailtracking.mra.defaults.listrateaudit.importexcep" />',1,self);
				showDialog({msg:'<bean:message bundle="mralistrateaudit" key="mailtracking.mra.defaults.listrateaudit.importexcep" />',type:1,parentWindow:self});
		        return;
			}else{
			submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.list.do?selectedRowIndex='+selectedRow+'&fromScreen=LISTRA');
			}


			}

		}
	}

}

function listprorationException(targetFormName){



	var check=0;
	var chkbox = document.getElementsByName("rowId");

	for(var i=0;i<chkbox.length;i++) {
		if(chkbox[i].checked== true){
			check=check+1;
		}
	}
	if(check==0){
		//showDialog("Please select a row",1,self);
		showDialog({msg:'Please select a row',type:1,parentWindow:self});
		
	}

	else if(check>1){
		//showDialog("Please select only one row",1,self);
		showDialog({msg:'Please select only one row',type:1,parentWindow:self});
	}else{


	var chkbox = document.getElementsByName("rowId");
	for(var i=0;i<chkbox.length;i++) {
		if(chkbox[i].checked== true){
			targetFormName.elements.selectedRowIndex.value=chkbox[i].value;
			var selectedRow = targetFormName.selectedRowIndex.value;

			submitForm(targetFormName,'mailtracking.mra.defaults.listrateaudit.viewproration.do?selectedRowIndex='+selectedRow+'&closeFlag=toListRateAudit'+'&invokingScreen=listrateaudit');



			}

		}
	}

}

function print(targetFormName){

	generateReport(targetFormName,'/mailtracking.mra.defaults.listrateaudit.print.do');

}
