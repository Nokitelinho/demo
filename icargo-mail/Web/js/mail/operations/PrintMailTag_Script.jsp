<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   screenload();
   with(frm){

     //CLICK Events
     	
     	evtHandler.addEvents("addLink","addMail()",EVT_CLICK);
     	evtHandler.addEvents("deleteLink","deleteMail()",EVT_CLICK);
     	evtHandler.addEvents("btnPrint","printMailTag()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeMailTag()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
     	
     	//Invoking Lov
	evtHandler.addIDEvents("originOELov","invokeLov(this,'originOELov')",EVT_CLICK);
	evtHandler.addIDEvents("destnOELov","invokeLov(this,'destnOELov')",EVT_CLICK);
	evtHandler.addIDEvents("subClassLov","invokeLov(this,'subClassLov')",EVT_CLICK);

	if(frm.elements.selectMail != null){
		evtHandler.addEvents("selectMail","toggleTableHeaderCheckbox('selectMail',targetFormName.elements.masterMail)",EVT_CLICK);
	}

    //BLUR Events
	evtHandler.addEvents("dsn","mailDSN()",EVT_BLUR);
	evtHandler.addEvents("rsn","mailRSN()",EVT_BLUR);
	evtHandler.addEvents("weight","mailWeight()",EVT_BLUR);
	evtHandler.addEvents("mailbagId","populateMailDetails(this)",EVT_BLUR);
	evtHandler.addEvents("originOE","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("destnOE","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("category","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("subClass","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("year","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("dsn","populateMailbagId(this)",EVT_BLUR);
	evtHandler.addEvents("rsn","populateMailbagId(this)",EVT_BLUR);
	evtHandler.addEvents("hni","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("ri","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("weight","populateMailbagId(this)",EVT_BLUR);	
	
   }

}


function screenload(){

   frm = targetFormName;
   
  
 if(!document.getElementById("addLink").disabled){

		document.getElementById("addLink").focus();
		
	} 
	if(frm.elements.flag.value == "external_print_preview") {
		var selectedMailBagId = frm.elements.selectedMailBagId.value;
		var validPrintRequest = frm.elements.validPrintRequest.value;
		if(validPrintRequest === 'true'){
			frm.elements.flag.value="";
			openPopUp("mailtracking.defaults.printmailtag.externalprinttag.do?selectedMailBagId="+selectedMailBagId,"1150","560");
		}else{
			showDialog({msg:'Please select a single row', type:1, parentWindow:self});
		}
					
		
    }	
  
}

function resetFocus(){
	 if(!event.shiftKey){ 
		if(!document.getElementById("addLink").disabled){
			document.getElementById("addLink").focus();
		}	
	}	
}

function printMailTag(){
    frm=targetFormName;
    
   incomplete = validateMailDetails();
   if(incomplete == "N"){
        return;
   }
   
    var selectMail =document.getElementsByName("selectMail");
   
    /*  for(var i=0;i<selectMail.length;i++){
         if(selectMail[i].checked){
             
         }
      }
     */
   
    generateReport(frm,'/mailtracking.defaults.printmailtag.print.do');
}


function closeMailTag(){
   location.href = appPath + "/home.jsp";
}



function addMail(){

  frm = targetFormName; 
  addTemplateRow('mailTemplateRow','mailTableBody','opFlag');
}

 
function deleteMail(){
 	frm = targetFormName; 
 	deleteTableRow('selectMail','opFlag');
}

function invokeLov(obj,name){

   var index = obj.id.split(name)[1];
   
   if(name == "originOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOE.value,'OfficeOfExchange','1','originOE','',index);    
   }
   if(name == "destnOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destnOE.value,'OfficeOfExchange','1','destnOE','',index);    
   }
   if(name == "subClassLov"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.subClass.value,'OfficeOfExchange','1','subClass','',index);    
   }
   
}
 
function mailWeight(){
 frm=targetFormName;
 var weightArr =document.getElementsByName("weight");
 var weight =document.getElementsByName("weight");

   for(var i=0;i<weightArr.length;i++){
      if(weightArr[i].value.length == 1){
          weight[i].value = "000"+weightArr[i].value;
      }
      if(weightArr[i].value.length == 2){
                weight[i].value = "00"+weightArr[i].value;
      }
      if(weightArr[i].value.length == 3){
                weight[i].value = "0"+weightArr[i].value;
      }
   }

}

function mailDSN(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("dsn");
 var mailDSN =document.getElementsByName("dsn");
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


function mailRSN(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("rsn");
 var mailRSN =document.getElementsByName("rsn");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}


function validateMailDetails(){

  frm=targetFormName;
  
  var opFlag =document.getElementsByName("opFlag");
  
  var mailOOE =document.getElementsByName("originOE");
  var mailDOE =document.getElementsByName("destnOE");
  var mailSC =document.getElementsByName("subClass");
  var mailYr =document.getElementsByName("year");
  var mailDSN =document.getElementsByName("dsn");
  var mailRSN =document.getElementsByName("rsn");
  var mailWt =document.getElementsByName("weight");
  
  for(var i=0;i<mailOOE.length;i++){
    if(opFlag[i].value != "NOOP"){
        
  	  if(mailOOE[i].value.length == 0){
	  showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailooeempty' />", type:1, parentWindow:self});
  	     mailOOE[i].focus();
  	     return "N";
  	  }
  	  if(mailOOE[i].value.length != 6){
	  showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailooelength' />", type:1, parentWindow:self});
	     mailOOE[i].focus();
	     return "N";
          }

   	  if(mailDOE[i].value.length == 0){
	   showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.maildoeempty' />", type:1, parentWindow:self});
   	     mailDOE[i].focus();
   	     return "N";
   	  }
   	  if(mailDOE[i].value.length != 6){
	    showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.maildoelength' />", type:1, parentWindow:self});
	     mailDOE[i].focus();
	     return "N";
          }
         
   	  if(mailSC[i].value.length == 0){
	    showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailscempty' />", type:1, parentWindow:self});
   	     mailSC[i].focus();
   	     return "N";
   	  }
   	  if(mailSC[i].value.length != 2){
	   showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailsclength' />", type:1, parentWindow:self});
	     mailSC[i].focus();
	     return "N";
          }
         
          if(mailYr[i].value.length == 0){
		  showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailyearempty' />", type:1, parentWindow:self});
    	     mailYr[i].focus();
    	     return "N";
    	 }
        
         if(mailDSN[i].value.length == 0){
		  showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.maildsnempty' />", type:1, parentWindow:self});
    	     mailDSN[i].focus();
    	     return "N";
    	 }
        
         if(mailRSN[i].value.length == 0){
		  showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailrsnempty' />", type:1, parentWindow:self});
     	     mailRSN[i].focus();
     	     return "N";
     	 }
         if(mailWt[i].value.length == 0){
		 showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailwgtempty' />", type:1, parentWindow:self});
	     mailWt[i].focus();
	     return "N";
	 }
	 
	 if(mailWt[i].value == 0){
	 showDialog({msg:"<bean:message bundle='printMailTagResources' key='mailtracking.defaults.mailtag.msg.alrt.mailwgtzero' />", type:1, parentWindow:self});
	     mailWt[i].focus();
	     return "N";
	 }
 	  
      } 
	  
   }



 }


 	//-----------------Ajax call for Mail Tag Details -----------------------------------//
function populateMailDetails(obj){
	var  _rowCount =null;
	try{
		_rowCount=obj.getAttribute("rowCount");
 
	}catch(err){
		return;
	}
	globalObj=obj;
	var funct_to_overwrite = "refreshMailDetails";
	var strAction = 'mailtracking.defaults.printmailtag.populatemailtagdetails.do?selectMail='+_rowCount;
 
  	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}

 function refreshMailDetails(_tableInfo){
var _rowCount = globalObj.getAttribute("rowCount");
if(targetFormName.elements.mailbagId.length>0){
targetFormName.elements.originOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailOOE").innerHTML;
targetFormName.elements.destnOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDOE").innerHTML;
targetFormName.elements.category[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailCat").innerHTML;
targetFormName.elements.subClass[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailSC").innerHTML;
targetFormName.elements.year[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailYr").innerHTML;
targetFormName.elements.dsn[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDSN").innerHTML;
targetFormName.elements.rsn[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRSN").innerHTML;
targetFormName.elements.hni[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailHNI").innerHTML;
targetFormName.elements.ri[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRI").innerHTML;
targetFormName.elements.weight[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailWt").innerHTML;
targetFormName.elements.mailbagId[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailbagId").innerHTML;
}
mailWeight();
mailDSN();
mailRSN();
  }
//------------------------------------------------------------------------//
 
 function populateMailbagId(obj){
	var  _rowCount =null;
	try{
		_rowCount=obj.getAttribute("rowCount");
 
	}catch(err){
		return;
	}
	if(targetFormName.elements.originOE[_rowCount].value!=null&&targetFormName.elements.originOE[_rowCount].value!=""&&
	targetFormName.elements.destnOE[_rowCount].value!=null&&targetFormName.elements.destnOE[_rowCount].value!=""&&
	targetFormName.elements.category[_rowCount].value!=null&&targetFormName.elements.category[_rowCount].value!=""&&
	targetFormName.elements.subClass[_rowCount].value!=null&&targetFormName.elements.subClass[_rowCount].value!=""&&
	targetFormName.elements.year[_rowCount].value!=null&&targetFormName.elements.year[_rowCount].value!=""&&
	targetFormName.elements.dsn[_rowCount].value!=null&&targetFormName.elements.dsn[_rowCount].value!=""&&
	targetFormName.elements.rsn[_rowCount].value!=null&&targetFormName.elements.rsn[_rowCount].value!=""&&
	targetFormName.elements.hni[_rowCount].value!=null&&targetFormName.elements.hni[_rowCount].value!=""&&
	targetFormName.elements.ri[_rowCount].value!=null&&targetFormName.elements.ri[_rowCount].value!=""&&
	targetFormName.elements.weight[_rowCount].value!=null&&targetFormName.elements.weight[_rowCount].value!=""){
	targetFormName.elements.mailbagId[_rowCount].value="";
	populateMailDetails(obj);
	}else{
	targetFormName.elements.mailbagId[_rowCount].value="";
	}
	
}