<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
    function screenSpecificEventRegister()
    {
	var frm=targetFormName;
	//if(frm.clearanceFrom.disabled==false){
	//frm.clearanceFrom.focus();}


	with(frm){

	evtHandler.addEvents("btnClose","onClose()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
        evtHandler.addEvents("btnClear","clearScreen(targetFormName)",EVT_CLICK);
        evtHandler.addEvents("rateCardLov","rateCardLOV()",EVT_CLICK);
	//evtHandler.addEvents("rateCardLov","displayLOV('showRateCardLOV.do','N','Y','showRateCardLOV.do',document.forms[1].rateCardID.value,'Rate Card ID','1','rateCardID','','',0)",EVT_CLICK);
	evtHandler.addEvents("btnList","listScreen(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btnClear","submitForm(targetFormName,'mailtracking.mra.defaults.viewupurate.onClear.do')", EVT_CLICK);
	evtHandler.addEvents("headChk","updateHeaderCheckBox(targetFormName, targetFormName.elements.headChk, targetFormName.elements.rowId)", EVT_CLICK);
	evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',document.forms[1].headChk)",EVT_CLICK);
	//evtHandler.addEvents("rowId","updateCheck()", EVT_CLICK);
	/*evtHandler.addEvents("stationlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.origin.value,'Origin','1','origin','','0')",EVT_CLICK);
	evtHandler.addEvents("stationCodelov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.destination.value,'Destination','1','destination','','0')",EVT_CLICK);*/
	evtHandler.addEvents("stationlov","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.origin.value,'Origin','1','origin','','0')",EVT_CLICK);
	
	evtHandler.addEvents("stationCodelov","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.destination.value,'Destination','1','destination','','0')",EVT_CLICK);	
	//evtHandler.addEvents("btnChangeStatus","changeStatus()",EVT_CLICK);
	evtHandler.addEvents("btnCopy","onCopyRate()",EVT_CLICK);
	//evtHandler.addEvents("btnChangeStatus","onChange(targetFormName,'mailtracking.mra.defaults.viewupurate.onChangeStatus.do')", EVT_CLICK);
	evtHandler.addEvents("btnActivate", "onActivate()", EVT_CLICK);
	evtHandler.addEvents("btnInactivate", "onInactivate()", EVT_CLICK);
    evtHandler.addEvents("btnCancel", "onCancel()", EVT_CLICK);
	callOnScreenLoad();
	onLoad();
	}
      }
	function rateCardLOV(){
		var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
	displayLOV('showRateCardLOV.do','N','Y','showRateCardLOV.do',document.forms[1].rateCardID.value,'Rate Card ID','1','rateCardID','',0,_reqHeight);
	}

      function onLoad(){

		    //added for screen resolution
		  		    /*  	var clientHeight = document.body.clientHeight;
		  		  		var clientWidth = document.body.clientWidth;
		  		  		document.getElementById('contentdiv').style.height = ((clientHeight*90)/100)+'px';
		  		  		document.getElementById('outertable').style.height=((clientHeight*84)/100)+'px';
		  		  		var pageTitle=30;
		  		  		var filterlegend=80;
		  		  		var filterrow=90;
		  		  		var bottomrow=40;
		  		  		var height=(clientHeight*84)/100;
		  		  		var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
		  		  		document.getElementById('div1').style.height=tableheight+'px';*/
		  	//added for screen resolution ends

         //alert(targetFormName.elements.newStatus.value);
         if(targetFormName.elements.newStatus.value == "OSL"){
	           	  // targetFormName.elements.btnChangeStatus.disabled = "true";
	           	  targetFormName.elements.btnActivate.disabled = "true";
				  targetFormName.elements.btnInactivate.disabled = "true";
	           	  targetFormName.elements.btnCancel.disabled = "true";
	           	  targetFormName.elements.btnCopy.disabled = "true";
          	}
      }

      function callOnScreenLoad(action) {


          	var frm=targetFormName;
          	if(targetFormName.elements.rateCardID.readOnly==false || targetFormName.elements.rateCardID.disabled==false){
      	    			targetFormName.elements.rateCardID.focus();
          	}


          	if(frm.elements.changeStatusFlag != null && frm.elements.changeStatusFlag.value == "true"){

          	   frm.elements.changeStatusFlag.value="false";
          	   openPopUp("mailtracking.mra.defaults.maintainupuratecard.changestatuspopup.do?fromPage=viewupurate",230,110);

          	}

          	return;

}

    function clearScreen()
   {
    submitForm(targetFormName,'mailtracking.mra.defaults.viewupurate.onClear.do');

	}


     function onClose(){
            frm = targetFormName;
    	submitForm(frm,'mailtracking.mra.defaults.viewupurate.onClose.do');

        }
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
			if(targetFormName.elements.rateCardID.disabled == false && targetFormName.elements.rateCardID.readOnly== false){
			targetFormName.elements.rateCardID.focus();
		}
	}
}

    function submitPage(lastPg, displayPg) {

    	targetFormName.elements.lastPageNum.value=lastPg;
    	targetFormName.elements.displayPage.value=displayPg;
    	targetFormName.action="mailtracking.mra.defaults.listviewupurate.do";
    	targetFormName.submit();
	}


    function submitPage1(lastPg,displayPg){
    	targetFormName.elements.lastPageNum.value=lastPg;
    	targetFormName.elements.displayPage.value=displayPg;
    	submitForm(targetFormName,'mailtracking.mra.defaults.listviewupurate.do');

    }

    function onDetails(){
    	var frm = targetFormName;
    	frm.elements.displayPage.value = "1";
        frm.elements.lastPageNum.value = "0";

        if(targetFormName.elements.fromDate.value!="" && targetFormName.elements.toDate.value != ""){

		if(!compDatesCheckFocus(targetFormName, targetFormName.elements.fromDate, targetFormName.elements.toDate)){
			return;
		}
    	}

    	submitForm(targetFormName,'mailtracking.mra.defaults.listviewupurate.do');
}

function onCopyRate(){
  var frm =targetFormName;
   var checkboxes=document.getElementsByName('rowId');
     var  selectedId=new Array();
     if(validateSelectedCheckBoxes(targetFormName,'rowId',25,1)){
     var j=0;
        for(var i=0;i<checkboxes.length;i++){
            if(checkboxes[i].checked){

              selectedId[j]=checkboxes[i].value;
              j++;
              //alert("selected checkbox-->"+selectedId[i]);

            }
          }
            // alert("selected checkbox length-->"+selectedId[0]);

     	    openPopUp("mailtracking.mra.defaults.copyrateviewupurate.do?rowId="+selectedId,420,225);
	}
}










       function listScreen(frm){

       	frm.elements.displayPage.value="1";


       	    submitForm(targetFormName,'mailtracking.mra.defaults.listviewupurate.do?invokingScreen=listrateline');

       }



function changeStatus(){

var fromPage="changeStatusMaintain";
openPopUp("mailtracking.mra.defaults.maintainupuratecard.changestatuspopup.do?fromPage="+fromPage,700,400);


}

function updateCheck(){

	var chkBoxIds = document.getElementsByName('rowId');
	var isChecked = 0;
	var length= chkBoxIds.length;

	for(var i=0;i<chkBoxIds.length;i++){

		if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;
		}

	}

	if(isChecked != length){
		document.forms[1].headChk.checked=false;
	}else{
		document.forms[1].headChk.checked=true;
	}

}

 function onChange(frm, action){


	var check = validateSelectedCheckBoxes(frm, 'rowId', '', 1);
	if(check){
		submitForm(frm, action);

	}else{
		return;
	}

}


function onActivate(){
	if(validateSelectedCheckBoxes(targetFormName,'rowId',1,1)){
	var chkboxes = document.getElementsByName('rowId');
	var sta = document.getElementsByName("rateLineStatus");
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
					}
					else{
					selIndex=selIndex+"-"+chkboxes[index].value;
				    }
					if(sta[index].value == "C"){
						showDialog({msg:'Status of RateLine cannot be changed from Cancelled to Active',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "A"){
						showDialog({msg:'RateLines is already in Active status',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						showDialog({msg:'RateLine with Expired status cannot be modified',type:1,parentWindow:self});
						return;
					}

			}
		}
	}
	submitForm(targetFormName,'mailtracking.mra.defaults.viewupurate.activate.do?selectedIndexes='+selIndex);
}
}


function onInactivate(){
	if(validateSelectedCheckBoxes(targetFormName,'rowId','',1)){
	var chkboxes = document.getElementsByName('rowId');
	var sta = document.getElementsByName('rateLineStatus');
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
					}
					else{
					selIndex=selIndex+"-"+chkboxes[index].value;
				    }
					if(sta[index].value == "N"){
						showDialog({msg:'Status of RateLines  cannot be changed from New to Inactive',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "I"){
						showDialog({msg:'RateLines is already in Inactive status',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "C"){
						showDialog({msg:'Status of RateLines cannot be changed from Cancelled to Inactive',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						showDialog({msg:'RateLine with Expired status cannot be modified',type:1,parentWindow:self});
						return;
					}

			}
		}
	}
	submitForm(targetFormName,'mailtracking.mra.defaults.viewupurate.inactivate.do?selectedIndexes='+selIndex);
}
}


function onCancel(){
	if(validateSelectedCheckBoxes(targetFormName,'rowId','',1)){
	var chkboxes = document.getElementsByName('rowId');
	var sta = document.getElementsByName('rateLineStatus');
	var selIndex ='';
	if(chkboxes != null){

		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
				//alert('entered');
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;


				   }
			    else{

					selIndex=selIndex+"-"+chkboxes[index].value;

					}

					if(sta[index].value == "C"){
						showDialog({msg:'RateLines is already in Cancelled status',type:1,parentWindow:self});
						return;
					}
					if(sta[index].value == "N"){
						showDialog({msg:'Status of RateLines cannot be changed from New to Cancelled',type:1,parentWindow:self});
						return;
					}
				    if(sta[index].value == "E"){
						showDialog({msg:'RateLine with Expired status cannot be modified',type:1,parentWindow:self});
						return;
					}


			}
		}
	}
	submitForm(targetFormName,'mailtracking.mra.defaults.viewupurate.cancel.do?selectedIndexes='+selIndex);
}
}






