<%@ include file="/jsp/includes/js_contenttype.jsp" %>

	window.onload = screenSpecificEventRegister;

function screenSpecificEventRegister(){
		//window.onunload=self.opener.childWindow=null;
		var frm=targetFormName;
		with(frm){
			evtHandler.addEvents("btnFeedBack","submitAction(this.form,'feedback')",EVT_CLICK);
			evtHandler.addEvents("btnCheckStationAvailability","submitAction(this.form,'checkStationAvailability')",EVT_CLICK);
			evtHandler.addEvents("btnEMail","submitAction(this.form,'sendemail')",EVT_CLICK);

			evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
			evtHandler.addEvents("btnPrint","onClickPrint(this.form)",EVT_CLICK);
			

	}

}

function submitAction(frm,str){

   if(str=="checkStationAvailability"){
	   var code=frm.elements.code.value;
	   var name=frm.elements.productName.value;
	   var strAction="products.defaults.screenloadcheckavailability.do";
	   var strUrl=strAction+"?code="+code+"&name="+name;
	   openPopUp(strUrl,700,150);
   }

   if(str=="feedback"){
	      var code=frm.elements.code.value;
	      var strAction="products.defaults.screenloadfeedback.do";
	      var strUrl=strAction+"?code="+code;
	      openPopUp(strUrl,700,350);
   }

   if(str=="sendemail"){
   	      var code=frm.elements.code.value;
   	      var strAction="products.defaults.screenloadsendemail.do";
   	      var strUrl=strAction+"?code="+code;
   	      openPopUp(strUrl,700,350);
   }

 }
 
 
 function onClickPrint(frm){
 
 
		var strAction="/products.defaults.printProductDetails.do?productCode="+targetFormName.elements.code.value+"&hasPreview=true";
		targetFormName.action = strAction;
		//targetFormName.productCode.value = targetFormName.code.value;
		//targetFormName.hasPreview.value = "false";
		generateReport(targetFormName,targetFormName.action);
        				
 }