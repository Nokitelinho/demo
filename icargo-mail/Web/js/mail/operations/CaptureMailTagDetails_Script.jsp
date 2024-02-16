<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister(){
screenload(targetFormName);
evtHandler.addEvents("mailCarrierLov","invokeLov(this,'mailCarrierLov')",EVT_CLICK);
evtHandler.addEvents("btnClear","clearFn()",EVT_CLICK);  //Added by A-5200 for the ICRD-63164
evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
evtHandler.addEvents("mailSCLov","invokeLov(this,'mailSCLov')",EVT_CLICK);
evtHandler.addEvents("btnOk","okFn()",EVT_CLICK);
evtHandler.addEvents("btnNew","doAdd()",EVT_CLICK);
evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
evtHandler.addEvents("mailDSN","mailDSN()",EVT_BLUR);
evtHandler.addEvents("mailRSN","mailRSN()",EVT_BLUR);
evtHandler.addEvents("mailWt","mailWeight()",EVT_BLUR);
}
function screenload(frm){
	if("YES" == frm.elements.popupCloseFlag.value){
		var frm=targetFormName;
	        frm = self.opener.targetFormName;
	        frm.elements.popupCloseFlag.value = "N";
		frm.action="mailtracking.defaults.mailacceptance.refreshacceptmail.do";
		frm.method="post";
		frm.submit();
		window.closeNow();
		return;

   	}
   	var opFlag =document.getElementsByName("mailOpFlag");
	var disableRequired = true;
	if(frm.elements.modify.value == "Y"){
		disableRequired = false;
	}

	   	if(opFlag[0].value!='I'){
	   	 var ooe =document.getElementsByName("mailOOE");
		 if(disableRequired == true)
		 ooe[0].disabled=true;
		 var doe =document.getElementsByName("mailDOE");
		 if(disableRequired == true)
	   	 doe[0].disabled=true;
	   	 var mailCat =document.getElementsByName("mailCat");
		 if(disableRequired == true)
	   	 mailCat[0].disabled=true;
	   	  var mailSC =document.getElementsByName("mailSC");
		  if(disableRequired == true)
	   	 mailSC[0].disabled=true;
	   	  var mailYr =document.getElementsByName("mailYr");
		  if(disableRequired == true)
	   	 mailYr[0].disabled=true;
	   	  var mailDSN =document.getElementsByName("mailDSN");
		  if(disableRequired == true)
	   	 mailDSN[0].disabled=true;
	   	  var mailRSN =document.getElementsByName("mailRSN");
		  if(disableRequired == true)
	   	 mailRSN[0].disabled=true;
	   	  var mailHNI =document.getElementsByName("mailHNI");
		  if(disableRequired == true)
	   	 mailHNI[0].disabled=true;
	   	  var mailRI =document.getElementsByName("mailRI");
		  if(disableRequired == true)
	   	 mailRI[0].disabled=true;
	   	  var mailWt =document.getElementsByName("mailWt");
		  if(disableRequired == true)
	   	 mailWt[0].disabled=true;
	   	  var mailVolume =document.getElementsByName("mailVolume");
		  if(disableRequired == true)
	   	 mailVolume[0].disabled=true;
	   	var orglov=document.getElementsByName("mailOOELov");
		if(disableRequired == true)
	   	orglov[0].disabled=true;
	   	var dstlov=document.getElementsByName("mailDOELov");
		if(disableRequired == true)
	   	dstlov[0].disabled=true;
	   	var sclov=document.getElementsByName("mailSCLov");
		if(disableRequired == true)
	   	sclov[0].disabled=true;
   	}
   	else{
	   	 var ooe =document.getElementsByName("mailOOE");
			 ooe[0].focus();
   	}
}

function doClose(){
	submitForm(targetFormName, "mailtracking.defaults.mailacceptance.closeMailTagdetails.do");
}


function okFn(){
	var incomplete = "";
	incomplete = validateMailDetails();
      	if(incomplete == "N"){
           return;
      	}
	incomplete = mailWeight();	
	if(incomplete == "N"){
           return;
      	}
	formatMailWeight();
    	var frm = targetFormName;
	submitForm(frm, "mailtracking.defaults.mailacceptance.okMailTagdetails.do");

}

function invokeLov(obj,name){

   var index = obj.id.split(name)[1];

   if(name == "despatchPALov"){
         displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.despatchPA.value,'PA','0','despatchPA','',index);
   }
   if(name == "despatchOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.despatchOOE.value,'OfficeOfExchange','0','despatchOOE','',index);
   }
   if(name == "despatchDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.despatchDOE.value,'OfficeOfExchange','0','despatchDOE','',index);
   }
   if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.mailOOE.value,'OfficeOfExchange','0','mailOOE','',index);
   }
   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.mailDOE.value,'OfficeOfExchange','0','mailDOE','',index);
   }
   if(name == "mailSCLov"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.mailSC.value,'OfficeOfExchange','0','mailSC','',index);
   }
   if(name == "mailCarrierLov"){
         displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.mailCarrier.value,'Airline','0','mailCarrier','',index);
   }
     if(name == "despatchSCLov"){
        displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.despatchSC.value,'OfficeOfExchange','0','despatchSC','',index);
   }
  }

  function validateMailDetails(){

    frm=targetFormName;

    var opFlag =document.getElementsByName("mailOpFlag");

    var mailOOE =document.getElementsByName("mailOOE");
    var mailDOE =document.getElementsByName("mailDOE");
    var mailSC =document.getElementsByName("mailSC");
    var mailYr =document.getElementsByName("mailYr");
    var mailDSN =document.getElementsByName("mailDSN");
    var mailRSN =document.getElementsByName("mailRSN");
    var mailWt =document.getElementsByName("mailWt");
    var mailScanDate =document.getElementsByName("mailScanDate");
    var mailScanTime =document.getElementsByName("mailScanTime");
     var mailVolume =document.getElementsByName("mailVolume");

    for(var i=0;i<mailOOE.length;i++){
      if(opFlag[i].value != "NOOP"){

    	  if(mailOOE[i].value.length == 0){
		    showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooeempty" scope="request"/>',type:1,parentWindow:self});
    	     mailOOE[i].focus();
    	     return "N";
    	  }
    	  if(mailOOE[i].value.length != 6){
  	      showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooelength" scope="request"/>',type:1,parentWindow:self});
  	    // showPane(event,'pane2', frm.tab2);
  	     mailOOE[i].focus();
  	     return "N";
            }

     	  if(mailDOE[i].value.length == 0){
     	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoeempty" scope="request"/>',type:1,parentWindow:self});
     	   //  showPane(event,'pane2', frm.tab2);
     	     mailOOE[i].focus();
     	     return "N";
     	  }
     	  if(mailDOE[i].value.length != 6){
  	      showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoelength" scope="request"/>',type:1,parentWindow:self});
  	   //  showPane(event,'pane2', frm.tab2);
  	     mailDOE[i].focus();
  	     return "N";
            }

     	  if(mailSC[i].value.length == 0){
     	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailscempty" scope="request"/>',type:1,parentWindow:self});
     	   //  showPane(event,'pane2', frm.tab2);
     	     mailSC[i].focus();
     	     return "N";
     	  }
     	  if(mailSC[i].value.length != 2){
  	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailsclength" scope="request"/>',type:1,parentWindow:self});
  	    // showPane(event,'pane2', frm.tab2);
  	     mailSC[i].focus();
  	     return "N";
            }

            if(mailYr[i].value.length == 0){
      	   	  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailyearempty" scope="request"/>',type:1,parentWindow:self});
      	   //  showPane(event,'pane2', frm.tab2);
      	     mailYr[i].focus();
      	     return "N";
      	 }

           if(mailDSN[i].value.length == 0){

      	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildsnempty" scope="request"/>',type:1,parentWindow:self});
      	    // showPane(event,'pane2', frm.tab2);
      	     mailDSN[i].focus();
      	     return "N";
      	 }

           if(mailRSN[i].value.length == 0){
       	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailrsnempty" scope="request"/>',type:1,parentWindow:self});
       	     //showPane(event,'pane2', frm.tab2);
       	     mailRSN[i].focus();
       	     return "N";
       	 }
           if(mailWt[i].value.length == 0){
  	      showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtempty" scope="request"/>',type:1,parentWindow:self});
  	    // showPane(event,'pane2', frm.tab2);
  	     mailWt[i].focus();
  	     return "N";
  	 }

  	 if(mailWt[i].value == 0){
  	    	  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtzero" scope="request"/>',type:1,parentWindow:self});
  	    // showPane(event,'pane2', frm.tab2);
  	     mailWt[i].focus();
  	     return "N";
  	 }
   	/*	 if(mailVolume[i].value.length == 0){
  		      showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailvolumeempty" scope="request"/>',type:1,parentWindow:self});
  		    // showPane(event,'pane2', frm.tab2);
  		     mailVolume[i].focus();
  		     return "N";
  	 	}

  		 if(mailVolume[i].value == 0){
  		     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailvolumezero" scope="request"/>',type:1,parentWindow:self});
  		    // showPane(event,'pane2', frm.tab2);
  		     mailVolume[i].focus();
  		     return "N";
  		 }	 */
          if(mailScanDate[i].value.length == 0){
       	      showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailvolumezero" scope="request"/>',type:1,parentWindow:self});
       	     //showPane(event,'pane2', frm.tab2);
       	     mailScanDate[i].focus();
       	     return "N";
       	 }

          if(mailScanTime[i].value.length == 0){
  	  		  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailtimeempty" scope="request"/>',type:1,parentWindow:self});
  	   //  showPane(event,'pane2', frm.tab2);
  	     mailScanTime[i].focus();
  	     return "N";
  	}

        }

     }



 }

  function selectNextMailTagDetails(strLastPageNum, strDisplayPage) {

   	var inComplete = validateMailDetails();
   	if("N" == inComplete){
   		return;
   	}
   	var frm = targetFormName;
   	frm.elements.lastPageNum.value = strLastPageNum;
   	frm.elements.displayPage.value = strDisplayPage;
   	submitForm(frm, "mailtracking.defaults.mailacceptance.selectnextMailTagdetails.do");
}

function doAdd(){

	var inComplete = validateMailDetails();
	if("N" == inComplete){
		return;
	}
	var frm = targetFormName;
	submitForm(frm, "mailtracking.defaults.mailacceptance.addnewMailTagdetails.do");
}
function mailWeight(){

 frm=targetFormName;
 var weightArr =document.getElementsByName("mailWt");
 var weight =document.getElementsByName("mailWt");
  var weightUnit=document.getElementsByName("weightUnit");
   for(var i=0;i<weightArr.length;i++){
	  
	   var spiltWeight= weight[i].value.split('.');
	   if(spiltWeight.length>1){
	   if (weightUnit[i].value=='K'){
	   if(spiltWeight[1].length>1) {
		showDialog({msg:'Weight in Kilogram can accept only 1 value after  decimal point',type:1,parentWindow:self});
	  targetFormName.elements.mailWt[weightArr.length].focus();
	   return "N";   
	   }
	   }
      else	{
		if(spiltWeight[1]>0) {
		   showDialog({msg:'Weight in pound and hectogram cannot have decimal value',type:1,parentWindow:self});
	       targetFormName.elements.mailWt[weightArr.length].focus();
	       return "N";   
		} 
	  }
	 }	  
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
   populateMailVolume();

}


function mailDSN(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("mailDSN");
 var mailDSN =document.getElementsByName("mailDSN");
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
 var mailRSNArr =document.getElementsByName("mailRSN");
 var mailRSN =document.getElementsByName("mailRSN");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}
function populateMailVolume(){
   var funct_to_overwrite = "refreshMailDetails";
	var strAction = 'mailtracking.defaults.mailacceptance.populatevolume.do';
	 	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}
function refreshMailDetails(_tableInfo){
targetFormName.elements.mailVolume.value=_tableInfo.document.getElementById("_ajax_mailVol").innerHTML;

}

	/*var density = frm.elements.density.value;

	var weight = document.getElementsByName("mailWt");
	var volume = document.getElementsByName("mailVolume");
	for(var i=0;i<weight.length;i++){
			if (density == ''){
				volume[i].value =  0.01;
			}else{
				var w = weight[i].value;

					var dec = w.indexOf(".");
					var prefix = w.substring(0,dec);
					var suffix = w.substring(dec+1,dec+5);
				w=prefix+suffix;
				var wt = w/(10*density);
				var strWt=wt.toString();
				var s = strWt.indexOf(".");
				var prefix = strWt.substring(0,s);
				var suffix = strWt.substring(s,s+5);


				if(wt != 0 && prefix == 0 && suffix < 0.01){
					volume[i].value =  formatNumForLocale(0.01);
				}else{
					var finalVol = Number(prefix+suffix);
					volume[i].value =formatNumForLocale(finalVol);
				}
			}
	}
	var obj=jquery("[name='mailVolume']");
	roundHalfUp(obj[0],"VOL");

}*/
function round(num, decimals) {
	var multiplier = Math.pow(10, decimals);
	if (typeof(num) != "number") {
		num = Number(num);
	}
	if (decimals > 0) {
		return Math.round(num * multiplier) / multiplier;
	} else {
		return Math.round(num);
	}
}
//Added by A-5200 for the ICRD-63164 starts
function clearFn(){
    var frm = targetFormName;
	submitForm(frm, "mailtracking.defaults.mailacceptance.clearMailTagdetails.do");
}
//Added by A-5200 for the ICRD-63164 starts
function formatMailWeight(){
 frm=targetFormName;
 var weightArr =document.getElementsByName("mailWt");
 var weight =document.getElementsByName("mailWt");
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