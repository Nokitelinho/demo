<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister()
    {
	var frm=targetFormName;
	with(frm){

	evtHandler.addEvents("btnClear","clearScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	evtHandler.addEvents("btnList","listScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btViewProration","viewProration(this.form)",EVT_CLICK);
	evtHandler.addEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.dsn.value,'DSN No','1','dsn','',0)",EVT_CLICK);
	}
	
onScreenLoad(frm);
}

/**
* Function on Screen Load
*/
function onScreenLoad(frm){
	if(frm.elements.prorateFlag.value=="Y"){
	frm.elements.prorateFlag.value="N";
	}
	if(targetFormName.elements.dsn.value==""){
	frm.elements.btViewProration.disabled="true";
	}
	
	//initial focus on page load
	if(targetFormName.elements.dsn.disabled == false) {
	   targetFormName.elements.dsn.focus();
	}
	
	if(targetFormName.elements.showDsnPopUp.value=="TRUE"){
			targetFormName.elements.showDsnPopUp.value="FALSE";
	    	openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do",725,450);
    }
}
 /**
*Function for close
*/ 
  
function closeScreen(frm)
{
submitForm(frm,'mailtracking.mra.defaults.prorationlog.closescreen.do');
}

function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.dsn.disabled == false&&targetFormName.elements.dsn.readOnly == false){
			targetFormName.elements.dsn.focus();
		}
		else{
			if(document.getElementById("checkBoxLog").checked == false){
				document.getElementById("checkBoxLog").focus();
			}
		}
	}
}

/**
*Function for clear
*/

function clearScreen(frm)
{
submitForm(frm,'mailtracking.mra.defaults.prorationlog.clearscreen.do');
}


/**
*Function for list
*/
function listScreen(frm){
	submitForm(frm,'mailtracking.mra.defaults.prorationlog.listdsndetail.do');


}

/**
*  To restrict single select of checkbox
*/
function singleSelect(checkVal){
	singleSelectCb(targetFormName,checkVal.value,'checkBoxLog');
}

/**
*function for viewProration
*/
function viewProration(frm){

var frm = targetFormName;
		var selectedRows=0;
		var rows=document.getElementsByName("checkBoxLog");

		var norows=document.getElementById("prorationlogtable").rows;

		var flag="n";
		for(var i=0;i<rows.length;i++){
		if(rows[i].checked==true){
		var details=norows[i].cells;

		selectedRows=selectedRows+1;
		flag="y";
	    }
	    }
	    if(selectedRows){
		submitForm(frm,"mailtracking.mra.defaults.prorationlog.viewproration.do");
		}else
	    if(flag=="n"){
			showDialog({msg:'Atleast one row should be selected',type:1,parentWindow:self});
	    }
}