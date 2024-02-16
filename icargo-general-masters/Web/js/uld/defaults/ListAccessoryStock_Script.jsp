<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>



function screenSpecificEventRegister() {

	var frm=targetFormName;
	//changed by a-3045 for bug20361 starts
	if(frm.elements.accessoryCode.disabled == false){
		frm.elements.accessoryCode.focus();
	}
	//changed by a-3045 for bug20361 ends
	onScreenLoad();
	with(frm){
		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btDetails","getDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btDelete","deleteAcc()",EVT_CLICK);
		evtHandler.addEvents("btClose","close(this.form)",EVT_CLICK);

		evtHandler.addEvents("stationlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.station.value,'Station','1','station','',0)",EVT_CLICK);
		evtHandler.addEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);

		evtHandler.addEvents("accessoryCode","validateFields(this,-1,'Accessory Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("airlineCode","validateFields(this,-1,'Airline Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("station","validateFields(this,-1,'Station',0,true,true)",EVT_BLUR);
		if(frm.elements.select!=null){
			evtHandler.addEvents("select","select(this.form)",EVT_CLICK);
		}
		evtHandler.addEvents("selectAll","selectAll(this.form)",EVT_CLICK);


	}
	 applySortOnTable("listaccessorytable",new Array("None","String","String","String","String","Number","Number","String"));

}

function onScreenLoad(){
	if(targetFormName.elements.selectFlag!=null && targetFormName.elements.selectFlag.value==0){
		disableField(targetFormName.elements.btDetails);
		disableField(targetFormName.elements.btDelete);
	}
	
	//Commented By A-6841 for ICRD-185395
	/*if(targetFormName.elements.listDisableStatus.value=="airline"){
		targetFormName.elements.airlineCode.readOnly = true;
		targetFormName.elements.airlinelov.readOnly = true;
	}*/
	if(targetFormName.elements.listDisableStatus.value=="GHA"){
		targetFormName.elements.station.readOnly = true;
		targetFormName.elements.stationlov.readOnly = true;
	}
}

function select(frm){

	toggleTableHeaderCheckbox('select', frm.elements.selectAll);
}

function selectAll(frm){

	updateHeaderCheckBox(frm, frm.elements.selectAll, frm.elements.select);
}
function list(frm){
	frm.elements.selectAll.checked=false;
	frm.elements.displayPage.value="1";
	frm.elements.lastPageNum.value="0";
	submitForm(frm,'uld.defaults.stock.findaccessoriesstocklist.do?navigationMode=LIST');
}
function clear(frm){

	//submitForm(frm,'uld.defaults.stock.clearaccessoriesstocklist.do');
	submitFormWithUnsaveCheck('uld.defaults.stock.clearaccessoriesstocklist.do');
}
function getDetails(frm){

	var frm=targetFormName;

	var chkbox = document.getElementsByName("select");
	var chkcount = chkbox.length+1;
	var acccode = "";
	var airline = "";
	var station = "";
	var acccodes = document.getElementsByName('accCodesSelected');
	var airlines = document.getElementsByName('airCodesSelected');
	var stations = document.getElementsByName('stationsSelected');

	 if(validateSelectedCheckBoxes(frm,'select',chkcount,'1')){
	      for(var i=0;i<chkbox.length;i++) {

	   	if(chkbox[i].checked){

			if(acccode != "") {

			acccode = acccode+","+acccodes[i].value;
			airline = airline+","+airlines[i].value;
			station = station+","+stations[i].value;
			}

			else if(acccode == ""){
			acccode = acccodes[i].value;
			airline = airlines[i].value;
			station = stations[i].value;
			}
      		}
      	}

        submitForm(frm,'uld.defaults.stock.detailslistaccessories.do?accCodesSelected='+acccode+'&airCodesSelected='+airline+'&stationsSelected='+station);

	}
}


function close(frm){

	submitForm(frm,'uld.defaults.stock.closeaccessoriesstocklist.do');
}
function submitPage(lastPg,displayPg){

  	targetFormName.elements.lastPageNum.value=lastPg;
  	targetFormName.elements.displayPage.value=displayPg;
  	submitForm(targetFormName,'uld.defaults.stock.findaccessoriesstocklist.do?navigationMode=NAVIGATION');
}

function deleteAcc(){
var frm=targetFormName;
 var chkbox = document.getElementsByName("select");
 var loaned = document.getElementsByName("loanedAcc");
 var isEligible ="true";
 var chkcount= chkbox.length+1;

  if(validateSelectedCheckBoxes(frm,'select',chkcount,'1')){
       for(var i=0; i<chkbox.length; i++) {
   	     if(chkbox[i].checked) {
   	               if(loaned[i].value > 0) {
   	        	isEligible ="false";
   	        	break;
   	        }


   	  }

       }
       if(isEligible=="true")
       {
        //showDialog('<bean:message bundle="listaccessoriesstockResources" key="uld.defaults.listaccessories.msg.warn.delete" />', 4, self, frm, 'id_1');
          showDialog({
								msg:'<bean:message bundle="listaccessoriesstockResources" key="uld.defaults.listaccessories.msg.warn.delete" />',
								type:4,
								parentWindow:self,
								parentForm:frm,
								dialogId:'id_1',
								onClose:function(){
                                screenConfirmDialog(frm,'id_1');
                                screenNonConfirmDialog(frm,'id_1');
								}
							});
       	
       }
       else{
      // showDialog('Selected rows contain loaned accessories', 1, self);
	     showDialog({msg :'<bean:message bundle="listaccessoriesstockResources" key="uld.defaults.listaccessories.msg.warn.loanexist" />',
			            	type:1,
				            parentWindow:self,                                       
				            parentForm:targetFormName,
                         }); 

       }
   }

}


function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			frm.elements.selectAll.checked=false;
			submitForm(frm,'uld.defaults.stock.deletelistaccessoriess.do');
		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}