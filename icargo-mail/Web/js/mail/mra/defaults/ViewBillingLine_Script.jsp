<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){

   var frm=targetFormName;

 	with(frm){
   		evtHandler.addEvents("btnSurcharge", "onSurcharge()", EVT_CLICK);
		evtHandler.addEvents("btnList", "onList()", EVT_CLICK);
		evtHandler.addEvents("btnClear", "onClear()", EVT_CLICK);
		evtHandler.addEvents("btnClose", "onClose()", EVT_CLICK);
		//evtHandler.addEvents("btnSave", "submitForm(targetFormName,'mailtracking.mra.defaults.viewbillingline.save.do')", EVT_CLICK);
		//evtHandler.addEvents("btnchangeStatus", "onChangeStatus()", EVT_CLICK);
		evtHandler.addEvents("btnCopy","copyBillingLine()",EVT_CLICK);
		evtHandler.addIDEvents("blgMatrixIDLov","billingMatrixLOV()", EVT_CLICK);
		evtHandler.addIDEvents("airlineLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airline.value,'Airline','1','airline','',0)", EVT_CLICK);
		evtHandler.addIDEvents("postalAdminLov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.postalAdmin.value,'Postal Admin','1','postalAdmin','',0)", EVT_CLICK);
		//evtHandler.addEvents("originLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.origin.value,'Airport','1','origin','',0)", EVT_CLICK);
		//evtHandler.addEvents("destinationLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destination.value,'Airport','1','destination','',0)", EVT_CLICK);
		evtHandler.addIDEvents("categoryLov","displayOneTimeLovCategory()", EVT_CLICK);
		evtHandler.addIDEvents("classLov","displayOneTimeLovClass()", EVT_CLICK);
		evtHandler.addIDEvents("subClassLov","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.subClass.value,'Subclass','1','subClass','',0)", EVT_CLICK);
		evtHandler.addIDEvents("uldTypeLov","displayLOV('showUld.do','Y','Y','showUld.do',targetFormName.elements.uldType.value,'ULD Type','1','uldType','',0)", EVT_CLICK);
		evtHandler.addEvents("checkboxes","toggleTableHeaderCheckbox('checkboxes',document.forms[1].headChk)",EVT_CLICK);
		evtHandler.addEvents("headChk","updateHeaderCheckBox(targetFormName,targetFormName.elements.headChk,targetFormName.elements.checkboxes)",EVT_CLICK);
		evtHandler.addEvents("btnActivate", "onActivate()", EVT_CLICK);
		evtHandler.addEvents("btnInactivate", "onInactivate()", EVT_CLICK);
		evtHandler.addEvents("btnCancel", "onCancel()", EVT_CLICK);
		evtHandler.addIDEvents("originLov","originFilter()", EVT_CLICK);
		evtHandler.addIDEvents("destinationLov","destinationFilter()", EVT_CLICK);
		evtHandler.addIDEvents("upliftLov","upliftFilter()", EVT_CLICK);
		evtHandler.addIDEvents("dischargeLov","dischargeFilter()", EVT_CLICK);
	}
	frm.billingMatrixID.focus();
	changeBtnStatus();
}

function onSurcharge() {
if(validateSelectedCheckBoxes(targetFormName,'checkboxes',1,1) ){
	var chkboxes = document.getElementsByName('checkboxes');
	var surchargeIndicators = document.getElementsByName('surchargeIndicator');
	var selIndex ='';
	if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
			selIndex=chkboxes[index].value;

			}
		}
	}
	if("Y"!=surchargeIndicators[selIndex].value)
	{
		showDialog({msg:"Surcharges do not exist for the selected rate line",type:1,parentWindow:self,onClose:function(result){}});

		return;
	}
	openPopUp("mailtracking.mra.defaults.viewbillingline.surchargedetails.do?selectedIndex="+selIndex,430,255);

}
}
function billingMatrixLOV(){
	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
displayLOV('showBillingMatrixLOV.do','N','Y','showBillingMatrixLOV.do',targetFormName.elements.billingMatrixID.value,'Billing Matrix','1','billingMatrixID','',0);
}
function changeBtnStatus(){
	//added for screen resolution
					  		      	//var clientHeight = document.body.clientHeight;
					  		  		//var clientWidth = document.body.clientWidth;
					  		  		//document.getElementById('contentdiv').style.height = ((clientHeight*90)/100)+'px';
					  		  		//document.getElementById('outertable').style.height=((clientHeight*84)/100)+'px';
					  		  		//var pageTitle=30;
					  		  		//var filterlegend=80;
					  		  		//var filterrow=90;
					  		  		//var bottomrow=40;
					  		  		//var height=(clientHeight*84)/100;
					  		  		//var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
					  		  		//document.getElementById('div1').style.height=tableheight+'px';
	//added for screen resolution ends


var frm = targetFormName;
if(document.forms[1].copyFlag.value=="COPY"){
alert(document.forms[1].copyFlag.value);
	submitForm(targetFormName,'mailtracking.mra.defaults.screenloadcopybillingline.do');
}
var chks = document.getElementsByName("checkboxes");
	if(chks.length == 0){
		//frm.btnChangeStatus.disabled = true;
		frm.btnActivate.disabled = true;
		frm.btnInactivate.disabled = true;
		frm.btnCancel.disabled = true;
		//frm.btnSave.disabled = true;
		frm.btnCopy.disabled = true;
		frm.btnSurcharge.disabled = true;
	}
	else{
		//frm.btnChangeStatus.disabled = false;

		if(frm.elements.btnActivate.getAttribute("privileged")== 'Y'){
			frm.btnActivate.disabled=false;
			}
		if(frm.elements.btnInactivate.getAttribute("privileged") == 'Y'){
			frm.btnInactivate.disabled=false;
			}
		if(frm.elements.btnCancel.getAttribute("privileged") == 'Y'){
			frm.btnCancel.disabled=false;
			}
		if(frm.elements.btnCopy.getAttribute("privileged") == 'Y'){
			frm.btnCopy.disabled=false;
			}
		//Added by A-8527 for ICRD-345683	
		if(frm.elements.btnSurcharge.getAttribute("privileged") == 'Y'){	
		frm.btnSurcharge.disabled = false;
		}
		//frm.btnActivate.disabled = false;
	        //frm.btnInactivate.disabled = false;
		//frm.btnCancel.disabled = false;
		//frm.btnSave.disabled = false;
		//frm.btnCopy.disabled = false;
	}
}



function onClear(){

	var frm = targetFormName;

	submitForm(frm,"mailtracking.mra.defaults.viewbillingline.clear.do");

}

function onClose(){

	var frm = targetFormName;
	if(frm.fromPage.value=="PARTNER_CARRIER"){

	  	var airprt = frm.origin.value;
		var strAction="mailtracking.defaults.partnercarrier.refresh.do";
		var strURL=strAction+"?airport="+airprt;
		submitForm(targetFormName,strAction);
	}else
	submitForm(targetFormName,'mailtracking.mra.defaults.viewbillingline.close.do')

}

function onList(){

	var frm = targetFormName;
	frm.displayPage.value=1;
	frm.lastPageNum.value=0;
	submitForm(targetFormName,"mailtracking.mra.defaults.viewbillingline.list.do");

}

function submitPage(lastPg, displayPg) {

	targetFormName.lastPageNum.value=lastPg;
	targetFormName.displayPage.value=displayPg;
	targetFormName.action="mailtracking.mra.defaults.viewbillingline.list.do";
	targetFormName.submit();
}

function onChangeStatus(){

var frm = targetFormName;
var chkboxes = document.getElementsByName("checkboxes");
var selectedIndexes="";
for(var i = 0;i<chkboxes.length;i++){

	if(chkboxes[i].checked == true){
		if(selectedIndexes!=""){
		selectedIndexes = selectedIndexes+",";
		}
		selectedIndexes = selectedIndexes+chkboxes[i].value;
		//alert(chkboxes[i].value+"kkkk");
	}

}
targetFormName.elements.selectedIndexes.value=selectedIndexes;
var size;
if(chkboxes != null){
size = chkboxes.length;
if(validateSelectedCheckBoxes(frm,"checkboxes",size,1))
	openPopUp("mailtracking.mra.defaults.viewbillingline.changestatuspopup.do",230,110);
}

}
function displayOneTimeLovCategory(){

var mainAction  = 'showOneTime.do';
var strAction = 'showOneTime.do';

var indexval="";
var parentAction="";
var strUrl = mainAction+"?multiselect=Y&pagination=Y&lovaction="+strAction+"&code="+targetFormName.elements.category.value+"&title=Category&formCount=1&lovTxtFieldName=catCode&lovDescriptionTxtFieldName=category&fieldType=mailtracking.defaults.mailcategory&index="+indexval+"&parentAction="+parentAction;
//var myWindow = window.open(strUrl, "LOV", 'status,scrollbars,width=430,height=320,screenX=800,screenY=600,left=250,top=100')
//changed by A-5222 as part of ICRD-41909
//changed by A-7938 as part of ICRD-245316
var myWindow = openPopUp(strUrl,'430','380');

}

function displayOneTimeLovClass(){

var mainAction  = 'showOneTime.do';
var strAction = 'showOneTime.do';

var indexval="";
var parentAction="";
var strUrl = mainAction+"?multiselect=Y&pagination=Y&lovaction="+strAction+"&code="+targetFormName.elements.billingClass.value+"&title=BillingClass&formCount=1&lovTxtFieldName=classCode&lovDescriptionTxtFieldName=billingClass&fieldType=mailtracking.defaults.mailclass&index="+indexval+"&parentAction="+parentAction;
//var myWindow = window.open(strUrl, "LOV", 'status,scrollbars,width=430,height=320,screenX=800,screenY=600,left=250,top=100')
//changed by A-5222 as part of ICRD-41909
//changed by A-7938 as part of ICRD-245316
var myWindow = openPopUp(strUrl,'450','500');
}
//Function to copyBillingLine
function copyBillingLine(){
var frm =targetFormName;
   var checkboxes=document.getElementsByName('checkboxes');
     var  selectedId=new Array();
     if(validateSelectedCheckBoxes(targetFormName,'checkboxes',25,1)){
     var j=0;
        for(var i=0;i<checkboxes.length;i++){
            if(checkboxes[i].checked){

              selectedId[j]=checkboxes[i].value;
              j++;

            }
          }
     	    openPopUp("mailtracking.mra.defaults.viewbillingline.copybillingline.do?checkboxes="+selectedId,460,250);
	}
}
function onActivate(){
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes',1,1)){
	var chkboxes = document.getElementsByName('checkboxes');
	var sta = document.getElementsByName('statusValue');
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
							showDialog({msg:"Status of BillingLines cannot be changed from Cancelled to Active",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}
						else if(sta[index].value == "A"){
							showDialog({msg:"BillingLines is already in Active status",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}
						else if(sta[index].value == "E"){
							showDialog({msg:"BillingLines with Expired status cannot be modified",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}

				}
			}
	}
	submitForm(targetFormName,'mailtracking.mra.defaults.viewbillingline.activate.do?selectedIndexes='+selIndex);
}
}

function onInactivate(){
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes','',1)){
	var chkboxes = document.getElementsByName('checkboxes');
	var sta = document.getElementsByName('statusValue');
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
							showDialog({msg:"Status of BillingLines  cannot be changed from New to Inactive",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}
						else if(sta[index].value == "I"){
							showDialog({msg:"BillingLines is already in Inactive status",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}
						else if(sta[index].value == "C"){
							showDialog({msg:"Status of BillingLines cannot be changed from Cancelled to Inactive",type:1,parentWindow:self,onClose:function(result){}});

						 return;
						}
						else if(sta[index].value == "E"){
							showDialog({msg:"BillingLines with Expired status cannot be modified",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}

				}
			}
	}
	submitForm(targetFormName,'mailtracking.mra.defaults.viewbillingline.inactivate.do?selectedIndexes='+selIndex);
}
}

function onCancel(){
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes','',1)){
	var chkboxes = document.getElementsByName('checkboxes');
	var sta = document.getElementsByName('statusValue');
	var selIndex ='';

	if(chkboxes != null){

			for(var index=0;index<chkboxes.length;index++){
				if(chkboxes[index].checked == true){
					//alert('entered');
					if(selIndex.trim().length == 0){
						selIndex=chkboxes[index].value;

						//alert(sta[index].value);
					   }
				    else{
						//alert(sta[index].value);
						selIndex=selIndex+"-"+chkboxes[index].value;

						}

						if(sta[index].value == "C"){
							showDialog({msg:"BillingLines is already in Cancelled status",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}
						if(sta[index].value == "N"){
							showDialog({msg:"Status of BillingLines cannot be changed from New to Cancelled",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}
					    if(sta[index].value == "E"){
							showDialog({msg:"BillingLines with Expired status cannot be modified",type:1,parentWindow:self,onClose:function(result){}});

							return;
						}


				}
			}
	}

	submitForm(targetFormName,'mailtracking.mra.defaults.viewbillingline.cancel.do?selectedIndexes='+selIndex);
}
}
function checkZero(e){
if (e.value==0){
     e.value='';
   }
}
//Added by A-7929 for ICRD - 224586 starts 
function callbackViewBillingLine (collapse,collapseFilterOrginalHeight,mainContainerHeight){   
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               //IC.util.widget.updateTableContainerHeight(jquery("div1"),+collapseFilterOrginalHeight);
               
}

//Added by A-7929 for ICRD - 224586 ends


function originFilter(){
	var frm=targetFormName;
 if(frm.originLevel.value=="CNT"){
           
         displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.origin.value,'Country Code','','origin','',0);;

}else if(frm.originLevel.value=="CTY")
{

       displayLOV('showCity.do','N','Y','showCity.do',targetFormName.origin.value,'Airport','1','origin','',0);
}
else if(frm.originLevel.value == "ARP"){
		 		displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.origin.value,'origin','1','origin','','0');
}
else if(frm.originLevel.value == "REG"){
		 		displayLOV('showRegion.do','N','Y','showRegion.do',targetFormName.origin.value,'origin','1','origin','','0');
}
}


function destinationFilter(){
	var frm=targetFormName;
 if(frm.destinationLevel.value=="CNT"){
           
         displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.destination.value,'Country Code','','destination','',0);;

}else if(frm.destinationLevel.value=="CTY")
{

       displayLOV('showCity.do','N','Y','showCity.do',targetFormName.destination.value,'Airport','1','destination','',0);
}
else if(frm.destinationLevel.value == "ARP"){
		 		displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'destination','1','destination','','0');
}
else if(frm.destinationLevel.value == "REG"){
		 		displayLOV('showRegion.do','N','Y','showRegion.do',targetFormName.destination.value,'destination','1','destination','','0');
}
}


function upliftFilter(){
	var frm=targetFormName;
 if(frm.upliftLevel.value=="CNT"){
           
         displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.uplift.value,'Country Code','','uplift','',0);;

}else if(frm.upliftLevel.value=="CTY")
{

       displayLOV('showCity.do','N','Y','showCity.do',targetFormName.uplift.value,'Airport','1','uplift','',0);
}
else if(frm.upliftLevel.value == "ARP"){
		 		displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.uplift.value,'uplift','1','uplift','','0');
}
else if(frm.upliftLevel.value == "REG"){
		 		displayLOV('showRegion.do','N','Y','showRegion.do',targetFormName.uplift.value,'uplift','1','uplift','','0');
}
}

function dischargeFilter(){
	var frm=targetFormName;
 if(frm.dischargeLevel.value=="CNT"){
           
         displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.discharge.value,'Country Code','','discharge','',0);;

}else if(frm.dischargeLevel.value=="CTY")
{

       displayLOV('showCity.do','N','Y','showCity.do',targetFormName.discharge.value,'Airport','1','discharge','',0);
}
else if(frm.dischargeLevel.value == "ARP"){
		 		displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.discharge.value,'discharge','1','discharge','','0');
}
else if(frm.dischargeLevel.value == "REG"){
		 		displayLOV('showRegion.do','N','Y','showRegion.do',targetFormName.discharge.value,'discharge','1','discharge','','0');
}
}