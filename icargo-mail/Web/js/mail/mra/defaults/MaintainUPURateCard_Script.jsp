<%@ include file="/jsp/includes/js_contenttype.jsp" %>


<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{



	with(targetFormName){

	evtHandler.addEvents("btnList","list()",EVT_CLICK);
	evtHandler.addIDEvents("add","addRateLine()",EVT_CLICK);
	evtHandler.addIDEvents("delete","deleteRateLine()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
	//evtHandler.addEvents("btnChangeStatus","changeStatus()",EVT_CLICK);
	evtHandler.addIDEvents("rateCardIdlov","rateCardLOV()",EVT_CLICK);
	evtHandler.addEvents("btnCopy","copyRateLine()",EVT_CLICK);
	evtHandler.addEvents("btnSave","save()",EVT_CLICK);
	evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',document.forms[1].headChk)",EVT_CLICK);
	evtHandler.addEvents("headChk","updateHeaderCheckBox(targetFormName,targetFormName.elements.headChk,targetFormName.elements.check)",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	evtHandler.addEvents("btnActivate", "onActivate()", EVT_CLICK);
	evtHandler.addEvents("btnInactivate", "onInactivate()", EVT_CLICK);
    evtHandler.addEvents("btnCancel", "onCancel()", EVT_CLICK);
	}
disableButtons();

}
function closeScreen(){
	//alert("close testing");

	//submitFormWithUnsaveCheck(appPath + "/home.jsp");
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.close.do');

}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
			if(targetFormName.rateCardId.disabled == false && targetFormName.elements.rateCardId.readOnly== false){
			targetFormName.elements.rateCardId.focus();
		}
		else{
			document.getElementById('headChk').focus();
		}
	}
}
function rateCardLOV(){
	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
	displayLOV('showRateCardLOV.do','N','Y','showRateCardLOV.do',document.forms[1].rateCardId.value,'Rate Card ID','1','rateCardId','',0,_reqHeight);
}
//Function for displaying rate card details

function list(){



submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.list.do');



}
function addRateLine(){
var rateid=targetFormName.elements.rateCardId.value;
var des=targetFormName.elements.description.value;
var fdat=targetFormName.elements.validFrom.value;
var tdat=targetFormName.elements.validTo.value;
var mdf=removeFormatting(document.forms[1].mialDistFactor.value);
var sv=removeFormatting(document.forms[1].svTkm.value);
var sal=removeFormatting(document.forms[1].salTkm.value);
var airmail=removeFormatting(document.forms[1].airmialTkm.value);
var status=document.forms[1].status.value;


openPopUp("mailtracking.mra.defaults.maintainupuratecard.addratelinepopup.do?mialDistFactor="+mdf+
                    "&svTkm="+sv+"&salTkm="+sal+"&airmialTkm="+airmail+"&status="+status+
                    "&rateCardId="+rateid+"&description="+des+"&validFrom="+fdat+"&validTo="+tdat,640,420);


}
function deleteRateLine()
{


if(validateSelectedCheckBoxes(targetFormName,'check','',1)){
	var chkboxes = document.getElementsByName('check');
		var sta = document.getElementsByName('rateLineStatus');
	var selIndex ='';
	if(chkboxes != null){
			for(var index=0;index<chkboxes.length;index++){
				if(chkboxes[index].checked == true){
					if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
					if(sta[selIndex].value == "N"){
											submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.deleteratelines.do');
					}
					else{
						showDialog({msg:"<common:message bundle='maintainUPURateCard' key='mailtracking.mra.defaults.msg.err.ratelinenewstatuscannotbedeleted' scope='request'/>",type:1,parentWindow:self});
					}
				}
		}
}
}


}
}


function clear(){


submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.clearmaintainscreen.do');

}

function changeStatus(){

var status=document.forms[1].status.value;
var rateid=targetFormName.elements.rateCardId.value;
var des=targetFormName.elements.description.value;
var fdat=targetFormName.elements.validFrom.value;
var tdat=targetFormName.elements.validTo.value;
var mdf=document.forms[1].mialDistFactor.value;
var sv=document.forms[1].svTkm.value;
var sal=document.forms[1].salTkm.value;
var airmail=document.forms[1].airmialTkm.value;
var listStatus=targetFormName.elements.listStatus.value;


var fromPage="changeStatusMaintain";
openPopUp("mailtracking.mra.defaults.maintainupuratecard.changestatuspopup.do?fromPage="+fromPage+"&mialDistFactor="+mdf+
"&svTkm="+sv+"&salTkm="+sal+"&airmialTkm="+airmail+"&status="+status+
                    "&rateCardId="+rateid+"&description="+des+"&validFrom="+fdat+"&validTo="+tdat+"&listStatus="+listStatus,230,110);


}
function copyRateLine(){

var frm =targetFormName;
   var checkboxes=document.getElementsByName('check');
     var  selectedId=new Array();
     if(validateSelectedCheckBoxes(targetFormName,'check',25,1)){
     var j=0;
        for(var i=0;i<checkboxes.length;i++){
            if(checkboxes[i].checked){

              selectedId[j]=checkboxes[i].value;
              j++;

            }
          }
     	    openPopUp("mailtracking.mra.defaults.maintainupuratecard.copyrateline.do?check="+selectedId,460,250);
	}


}
function save(){


var status=document.forms[1].screenStatus.value;

submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.save.do');

}





function disableButtons(){

	//added for screen resolution
	/*var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	document.getElementById('contentdiv').style.height = ((clientHeight*90)/100)+'px';
	document.getElementById('outertable').style.height=((clientHeight*84)/100)+'px';
	var pageTitle=30;
	var filterlegend=80;
	var filterrow=80;
	var bottomrow=30;
	var height=(clientHeight*84)/100;
	var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
    document.getElementById('div1').style.height=tableheight+'px';*/
    //added for screen resolution ends

if(document.forms[1].okFlag.value=="COPY"){
submitForm(targetFormName,'mailtracking.mra.defaults.screenloadcopyrate.do');
}
var status=document.forms[1].screenStatus.value;

if(status=="screenload"){
enableField(targetFormName.elements.rateCardId);
if(document.forms[1].okFlag.value!="COPY")
targetFormName.elements.rateCardId.focus();
var addLink=document.getElementById('add');
disableLink(addLink);
var deleteLink=document.getElementById('delete');
disableLink(deleteLink);
enableField(targetFormName.elements.rateCardIdlov);
//document.forms[1].btnChangeStatus.disabled=true;
disableField(targetFormName.elements.btnActivate);
disableField(targetFormName.elements.btnInactivate);
disableField(targetFormName.elements.btnCancel);
disableField(document.forms[1].btnCopy);
disableField(document.forms[1].btnSave);
disableField(document.forms[1].btnClear);
disableField(document.forms[1].description);
disableField(document.forms[1].validFrom);
disableField(document.forms[1].btn_validFrom);
disableField(document.forms[1].validTo);
disableField(document.forms[1].btn_validTo);
disableField(document.forms[1].mialDistFactor);
disableField(document.forms[1].svTkm);
disableField(document.forms[1].salTkm);
disableField(document.forms[1].airmialTkm);
disableField(document.forms[1].status);

}
if((status=="list")||(status=="new")){
if( targetFormName.elements.rateCardId.disabled == false ){
document.getElementById('headChk').focus();
}
document.forms[1].rateCardId.readOnly=true;
disableField(document.forms[1].rateCardIdlov);
disableField(document.forms[1].btnList);
//enableField(document.forms[1].btnChangeStatus);
enableField(document.forms[1].btnActivate);
enableField(document.forms[1].btnInactivate);
enableField(document.forms[1].btnCancel);
enableField(document.forms[1].btnClear);
enableField(document.forms[1].btnCopy);
enableField(document.forms[1].btnSave);
var ratelineStatus=new Array();

var ratecardStatus1=document.forms[1].status.value;
/*if(ratecardStatus1=="ACTIVE" ||ratecardStatus1=="Active"){
				var addLink=document.getElementById('add');
				disableLink(addLink);
				var deleteLink=document.getElementById('delete');
				disableLink(deleteLink);
}*/
var validFrom1=new Array();
validFrom1=document.getElementsByName("validFromRateLine");

if(validFrom1!=null){
if(validFrom1.length==1){
        if(document.getElementsByName("operationFlag")[0].value!="D"){
		if(document.getElementsByName("rateLineStatus")[validFrom1.length-1].value=='N'){

				if((ratecardStatus1=='Cancelled')||(ratecardStatus1=="CANCELLED")||(ratecardStatus1=="C")
						||(ratecardStatus1=='Inactive')||(ratecardStatus1=='INACTIVE')){
					disableField(targetFormName.elements.validFromRateLine);
					var calendarIconId1 = "btn_validFrom1"+0;
					disableField(document.getElementById(calendarIconId1));
					disableField(targetFormName.elements.validToRateLine);
					var calendarIconId2 = "btn_validTo1"+0;
					disableField(document.getElementById(calendarIconId2));

			}

		}
		else{

						disableField(targetFormName.elements.validFromRateLine);
						var calendarIconId1 = "btn_validFrom1"+0;
						disableField(document.getElementById(calendarIconId1));
						disableField(targetFormName.elements.validToRateLine);
						var calendarIconId2 = "btn_validTo1"+0;
						disableField(document.getElementById(calendarIconId2));
		}
	}
}

	else{

	for(var i=0;i<validFrom1.length;i++){
       if(document.getElementsByName("operationFlag")[i].value!="D"){
		if(document.forms[1].rateLineStatus[i].value=='N'){


			if((ratecardStatus1=='Cancelled')||(ratecardStatus1=="CANCELLED")||(ratecardStatus1=="C")
			||(ratecardStatus1=='Inactive')
			||(ratecardStatus1=='INACTIVE')){

				disableField(targetFormName.elements.validFromRateLine[i]);
				var calendarIconId1 = "btn_validFrom1"+i;
				disableField(document.getElementById(calendarIconId1));
				disableField(targetFormName.elements.validToRateLine[i]);
				var calendarIconId2 = "btn_validTo1"+i;
				disableField(document.getElementById(calendarIconId2));




			}

		}
		else{


				disableField(targetFormName.elements.validFromRateLine[i]);
				var calendarIconId1 = "btn_validFrom1"+i;
				disableField(document.getElementById(calendarIconId1));
				disableField(targetFormName.elements.validToRateLine[i]);
				var calendarIconId2 = "btn_validTo1"+i;
				disableField(document.getElementById(calendarIconId2));

		}
	}
 }
}
}
}


var ratecardStatus=document.forms[1].status.value;


if((ratecardStatus=="Cancelled")||(ratecardStatus=="CANCELLED")||(ratecardStatus=="C")){


disableField(targetFormName.elements.description);
disableField(document.forms[1].validFrom);
disableField(document.forms[1].btn_validFrom);
disableField(document.forms[1].validTo);
disableField(document.forms[1].btn_validTo);
disableField(document.forms[1].mialDistFactor);
disableField(document.forms[1].svTkm);
disableField(document.forms[1].salTkm);
disableField(document.forms[1].airmialTkm);
var addLink=document.getElementById('add');
disableLink(addLink);
var deleteLink=document.getElementById('delete');
disableLink(deleteLink);
//document.forms[1].btnChangeStatus.disabled=true;
disableField(document.forms[1].btnActivate);
disableField(document.forms[1].btnInactivate);
disableField(document.forms[1].btnCancel);
}
if((ratecardStatus=="Inactive")||(ratecardStatus=="INACTIVE")||(ratecardStatus=="I")){
	disableField(targetFormName.elements.description);
	disableField(document.forms[1].validFrom);
	disableField(document.forms[1].btn_validFrom);
	disableField(document.forms[1].validTo);
	disableField(document.forms[1].btn_validTo);
	disableField(document.forms[1].mialDistFactor);
	disableField(document.forms[1].svTkm);
	disableField(document.forms[1].salTkm);
	disableField(document.forms[1].airmialTkm);
	var addLink=document.getElementById('add');
	disableLink(addLink);
	var deleteLink=document.getElementById('delete');
	disableLink(deleteLink);
	//enableField(document.forms[1].btnChangeStatus);
	enableField(document.forms[1].btnActivate);
	enableField(document.forms[1].btnInactivate);
	enableField(document.forms[1].btnCancel);
}
if((ratecardStatus=="New")||(ratecardStatus=="NEW")||(ratecardStatus=="N")){

targetFormName.elements.rateCardId.readOnly=true;
targetFormName.elements.status.readOnly=true;

	if(targetFormName.elements.opFlag.value=="I"){
//	targetFormName.btnChangeStatus.disabled=true;
	disableField(targetFormName.elements.btnActivate);
	disableField(targetFormName.elements.btnInactivate);
	disableField(targetFormName.elements.btnCancel);
	disableField(targetFormName.elements.btnCopy);
	}
enableField(targetFormName.elements.description);
targetFormName.elements.description.focus();
enableField(document.forms[1].validFrom);
enableField(document.forms[1].btn_validFrom);
enableField(document.forms[1].validTo);
enableField(document.forms[1].btn_validTo);

enableField(document.forms[1].mialDistFactor);
enableField(document.forms[1].svTkm);
enableField(document.forms[1].salTkm);
enableField(document.forms[1].airmialTkm);

}
else {
enableField(document.forms[1].rateCardId);
//if(document.forms[1].okFlag.value!="COPY")
//targetFormName.rateCardId.focus();
disableField(document.forms[1].validFrom);
disableField(document.forms[1].btn_validFrom);
disableField(document.forms[1].validTo);
disableField(document.forms[1].btn_validTo);
disableField(document.forms[1].description);
disableField(document.forms[1].mialDistFactor);
disableField(document.forms[1].svTkm);
disableField(document.forms[1].salTkm);
disableField(document.forms[1].airmialTkm);

}

}


function confirmMessage() {
   targetFormName.elements.opFlag.value="I";

	disableButtons();


}

function nonconfirmMessage() {



	submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.clearmaintainscreen.do');

}

function submitPage(lastPg, displayPg) {


    var status=targetFormName.elements.status.value;
	var rateid=targetFormName.elements.rateCardId.value;
	var des=targetFormName.elements.description.value;
	var fdat=targetFormName.elements.validFrom.value;
	var tdat=targetFormName.elements.validTo.value;
	var mdf=document.forms[1].mialDistFactor.value;
	var sv=document.forms[1].svTkm.value;
	var sal=document.forms[1].salTkm.value;
	var airmail=document.forms[1].airmialTkm.value;

	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	targetFormName.action="mailtracking.mra.defaults.maintainupuratecard.listratelines.do?mialDistFactor="+mdf+
    "&svTkm="+sv+"&salTkm="+sal+"&airmialTkm="+airmail+"&status="+status+
    "&rateCardId="+rateid+"&description="+des+"&validFrom="+fdat+"&validTo="+tdat;
	targetFormName.submit();
}
function onActivate(){
	//if(validateSelectedCheckBoxes(targetFormName,'check',1,1)){
	var chkboxes = document.getElementsByName('check');
	var sta = document.getElementsByName("rateLineStatus");
	var selIndex ='';
	var isSelected=false;//Added by a-7871 for ICRD-223130
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
			isSelected=true;
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
					}
					else{
					selIndex=selIndex+"-"+chkboxes[index].value;
				    }
					if(document.getElementsByName("operationFlag")[index].value=="I"){
						disableField(targetFormName.elements.btnActivate);
	disableField(targetFormName.elements.btnInactivate);
return;
				    }
					if(sta[index].value == "C"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.canceltoactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "A"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.activetoactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						showDialog({msg:"<common:message bundle='maintainUPURateCard' key='mailtracking.mra.defaults.msg.err.ratelineexpiredcannotbemodified' scope='request'/>",type:1,parentWindow:self});
						return;
					}

			}
		}
	if(isSelected==false)//Added by a-7871 for ICRD-223130
	{
	for(var index=0;index<chkboxes.length;index++){
	if(sta[index].value == "C"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.canceltoactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "A"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.activetoactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						showDialog({msg:"<common:message bundle='maintainUPURateCard' key='mailtracking.mra.defaults.msg.err.ratelineexpiredcannotbemodified' scope='request'/>",type:1,parentWindow:self});
						return;
					}
					}
	selIndex="ALL";
	}
	}
	//submitFormWithUnsaveCheck('mailtracking.mra.defaults.maintainupuratecard.activate.do?selectedIndexes='+selIndex);
	if(selIndex.length >1) { //Modified by a-7871 for ICRD-223130
	var param={msg:'This will activate all/selected rate lines in the system,Do you want to continue?', 
	onClose:function(result){
                        if(result){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.activate.do?selectedIndexes='+selIndex);
	}
                  }
   };
	showWarningDialog(param);
	}else{
		submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.activate.do?selectedIndexes='+selIndex);
}
	
//}
}


function onInactivate(){
	if(validateSelectedCheckBoxes(targetFormName,'check','',1)){
	var chkboxes = document.getElementsByName('check');
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
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.newtoinactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "I"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.inactivetoinactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "C"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.canceltoinactive" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						showDialog({msg:"<common:message bundle='maintainUPURateCard' key='mailtracking.mra.defaults.msg.err.ratelineexpiredcannotbemodified' scope='request'/>",type:1,parentWindow:self});
						return;
					}

			}
		}
	}
	//submitFormWithUnsaveCheck('mailtracking.mra.defaults.maintainupuratecard.inactivate.do?selectedIndexes='+selIndex);
	
	
	var param={msg:'Do you want to inactivate?', 
	onClose:function(result){
                        if(result){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.inactivate.do?selectedIndexes='+selIndex);
	}
                     
                  }
   };
	showWarningDialog(param);
}
}


function onCancel(){
	if(validateSelectedCheckBoxes(targetFormName,'check','',1)){
	var chkboxes = document.getElementsByName('check');
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
					if(sta[index].value == "C"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.canceltocancel" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "N"){
						showDialog({msg:'<common:message bundle="maintainUPURateCard" key="mailtracking.mra.defaults.maintainupuratecard.status.newtocancel" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					else if(sta[index].value == "E"){
						showDialog({msg:"<common:message bundle='maintainUPURateCard' key='mailtracking.mra.defaults.msg.err.ratelineexpiredcannotbemodified' scope='request'/>",type:1,parentWindow:self});
						return;
					}

			}
		}
	}
	//submitFormWithUnsaveCheck('mailtracking.mra.defaults.maintainupuratecard.cancel.do?selectedIndexes='+selIndex);
	var param={msg:'Do you want to cancel?', 
	onClose:function(result){
                        if(result){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.cancel.do?selectedIndexes='+selIndex);
	}
                  }
   };
	showWarningDialog(param);
}
}