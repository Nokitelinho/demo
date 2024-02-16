<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
 
   var frm=targetFormName;

   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnAdd","Add()",EVT_CLICK);
	evtHandler.addEvents("btnNew","New()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeMailPopup()",EVT_CLICK);
	evtHandler.addIDEvents("mailOOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.orginOfficeOfExchange.value,'OfficeOfExchange','1','orginOfficeOfExchange','',0)", EVT_CLICK);
	evtHandler.addIDEvents("mailDOELov", "displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destOfficeOfExchange.value,'OfficeOfExchange','1','destOfficeOfExchange','',0)",EVT_CLICK);
	evtHandler.addIDEvents("SCLov", "displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.mailSubClass.value,'OfficeOfExchange','1','mailSubClass','',0)",EVT_CLICK);
    evtHandler.addEvents("mailDsn","dsnPadding()",EVT_BLUR);
	evtHandler.addEvents("rsnRangeFrom","strtRsnPadding()",EVT_BLUR);
	evtHandler.addEvents("rsnRangeTo","endRsnPadding()",EVT_BLUR);
	evtHandler.addEvents("rsnRangeFrom","calculteTotalBags()",EVT_BLUR);
    evtHandler.addEvents("rsnRangeTo","calculteTotalBags()",EVT_BLUR);

   	}
	onScreenLoad(frm);
}


function onScreenLoad(frm){

 frm.elements.rsnRangeTo.disabled = false;//Modified by a-7871 for ICRD-220439
 if(frm.elements.actionName.value== 'NEW')//added by A-7371 for ICRD-221978
{
 frm.elements.rsnRangeFrom.value="";//Modified by a-7871 for ICRD-223492
 frm.elements.rsnRangeTo.value="";//Modified by a-7871 for ICRD-223492

}
}

  
function Add(){
var incomplete = validateMailDetails();
   if(incomplete == "N"){
        return;
   }
  
var action=targetFormName.actionName.value;
action="ADD";
debugger;
var origin=targetFormName.elements.orginOfficeOfExchange.value;
var destination=targetFormName.elements.destOfficeOfExchange.value;
var mailCat=targetFormName.elements.mailCategory.value;
var mailClass=targetFormName.elements.mailSubClass.value;
var mailType=targetFormName.elements.mailClassType.value;
var hni=targetFormName.elements.highestNumberIndicator.value;
var ri=targetFormName.elements.registeredIndicator.value;
var dsn=targetFormName.elements.mailDsn.value;
var year=targetFormName.elements.mailYear.value;
var strtRsn=targetFormName.elements.rsnRangeFrom.value;
var endRsn=targetFormName.elements.rsnRangeTo.value;



window.opener.refreshTableDetails("mailtracking.defaults.consignment.addmultiplemailbags.do?orginOfficeOfExchange="+origin+"&destOfficeOfExchange="+destination+"&mailCategory="+mailCat+"&mailSubClass="+mailClass+"&mailClassType="
                                 +mailType+"&highestNumberIndicator="+hni+"&registeredIndicator="+ri+"&mailDsn="+dsn+"&mailYear="+year+"&rsnRangeFrom="+strtRsn+"&rsnRangeTo="+endRsn+"&actionName="+action,"mailDiv");

}

function validateMailDetails()
{
frm=targetFormName;

   var opFlag =document.getElementsByName("mailOpFlag");

   var originOE =document.getElementsByName("orginOfficeOfExchange");
   var destinationOE =document.getElementsByName("destOfficeOfExchange");
   var subClassDSN =document.getElementsByName("mailSubClass");
   var year =document.getElementsByName("mailYear");
   var dsn =document.getElementsByName("mailDsn");
   var strtRsn=document.getElementsByName("rsnRangeFrom");  
   var highestNumberIndicator=document.getElementsByName("highestNumberIndicator"); //Added by a-7871 for ICRD-220330
   var registeredIndicator=document.getElementsByName("registeredIndicator"); //Added by a-7871 for ICRD-220330
   
	   for(var i=0;i<originOE.length;i++){
	   if(opFlag[i].value != "NOOP"){//Added by a-7371 for ICRD-219894
	   if(originOE[i].value.length == 0 && destinationOE[i].value.length == 0 && subClassDSN[i].value.length == 0 && year[i].value.length == 0 && dsn[i].value.length == 0 && strtRsn[i].value.length == 0 && highestNumberIndicator[i].value.length == 0 && registeredIndicator[i].value.length == 0){
	   showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.nodataentered" />',type:1,parentWindow:self});
           originOE[i].focus();
           return "N";
	   
	   }
	   
	    if(originOE[i].value.length == 0){
           showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.ooeempty" />',type:1,parentWindow:self});
           originOE[i].focus();
           return "N";
       }
	 if(originOE[i].value.length != 6){
	  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.ooelength" />',type:1,parentWindow:self});
	  originOE[i].focus();
	  return "N";
       }

       if(destinationOE[i].value.length == 0){
                 showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.doeempty" />',type:1,parentWindow:self});
                 destinationOE[i].focus();
                 return "N";
             }
             if(destinationOE[i].value.length != 6){
     	    showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.doelength" />',type:1,parentWindow:self});
     	    destinationOE[i].focus();
     	    return "N";
       }

       if(subClassDSN[i].value.length != 0){
     		if(subClassDSN[i].value.length != 2){
     		  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.sclength" />',type:1,parentWindow:self});
     		  subClassDSN[i].focus();
     		  return "N";
     		}
          }

          if(year[i].value.length == 0){
	               showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.yearempty" />',type:1,parentWindow:self});
	               year[i].focus();
	               return "N";
         }

      	 if(dsn[i].value.length == 0){
      	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.dsnempty" />',type:1,parentWindow:self});
      	     dsn[i].focus();
      	     return "N";
      
        }	
		//Added by a-7871 for ICRD-220330 starts
		if(highestNumberIndicator[i].value.length == 0){
             showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.hiempty" />',type:1,parentWindow:self});
             highestNumberIndicator[i].focus();
             return "N";
       }
	    if(registeredIndicator[i].value.length == 0){
             showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.riempty" />',type:1,parentWindow:self});
             registeredIndicator[i].focus();
             return "N";
       }
	   //Added by a-7871 for ICRD-220330 ends
		if(strtRsn[i].value.length == 0){
      	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.rsnmandatory" />',type:1,parentWindow:self});
      	     strtRsn[i].focus();
      	     return "N";
      
        }	
}
}
}

function dsnPadding(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("mailDsn");
 var mailDSN =document.getElementsByName("mailDsn");

   for(var i=0;i<mailDSNArr.length;i++){

      if(mailDSNArr[i].value.length == 1){
          mailDSN[i].value = "000"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 2){
                mailDSN[i].value = "00"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 3){
                mailDSN[i].value = "0"+mailDSNArr[i].value;
      }

   }

}

function strtRsnPadding(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("rsnRangeFrom");
 var mailRSN =document.getElementsByName("rsnRangeFrom");

   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }

   }
   
}


function endRsnPadding(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("rsnRangeTo");
 var mailRSN =document.getElementsByName("rsnRangeTo");

   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }

   }
}


function New()
{
var incomplete = validateMailDetails();//added by a-7871 for ICRD-220432
   if(incomplete == "N"){
        return;
      } 
 var frm=targetFormName;
 frm.elements.actionName.value="NEW";
 
 submitForm(targetFormName,"mailtracking.defaults.consignment.maildetailsnew.do");
}

function displayNextConsignment(lastPg,displayPg){
	
	

	var frm = targetFormName;
	frm.elements.actionName.value="NEXT";

	document.forms[0].elements.lastPopupPageNum.value=lastPg;
	document.forms[0].elements.displayPopupPage.value=displayPg;
	document.forms[0].action='mailtracking.defaults.consignment.maildetailsnew.do';
	document.forms[0].submit();


} 

function calculteTotalBags(){
var frm = targetFormName;
if(targetFormName.elements.rsnRangeTo.disabled==true){
debugger;
targetFormName.elements.rsnRangeTo.disabled = false;
targetFormName.elements.rsnRangeTo.focus();
}
 var totalCount = "";
var strtRsn=targetFormName.elements.rsnRangeFrom.value;
var endRsn=targetFormName.elements.rsnRangeTo.value;

	if (strtRsn.length!=0 && endRsn.length==0)
	{
   totalCount = 1;
   
	}
	else if(endRsn.length == 3)
	{
	if(strtRsn < endRsn){
	totalCount=(endRsn-strtRsn)+1;
	}
	else
	{

	 showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.checkRsn" />',type:1,parentWindow:self});
	}
	}
	document.getElementById('Count').innerHTML="<b>"+totalCount+"</b>";
	//targetFormName.elements.totalReceptacles.value = totalCount;
	
}
function closeMailPopup(){
    var frm=targetFormName;
	submitForm(targetFormName,"mailtracking.defaults.consignment.mailpopupclose.do");
   window.close();
}
