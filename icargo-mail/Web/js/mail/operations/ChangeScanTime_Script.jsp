<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   screenload();
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnOk","scantimeOk()",EVT_CLICK);
	evtHandler.addEvents("btnClose","scantimeCancel()",EVT_CLICK);
   }

}

function screenload(){
   frm = targetFormName;
   frm.elements.scanDate.focus();
     if(frm.elements.scanTimeFromScreen.value == "ACCEPTCLOSE"){
        frm = self.opener.targetFormName;
        frm.action="mailtracking.defaults.mailacceptance.refreshacceptmail.do";
       	frm.method="post";
       	frm.submit();
       	window.closeNow();
       	return;
     }
       
     if(frm.elements.scanTimeFromScreen.value == "ARRIVECLOSE"){
       
         var str = targetFormName.elements.strToDelivery.value;
	   	var paBuiltFlg = str.split("/")[3];
   		  
   		if(paBuiltFlg=="Y"){
	        showDialog({msg:'ULD is SB.Do you want to deliver it along with mailbag ?',type :4, parentWindow:self,parentForm:frm,dialogId:'id_2',
						onClose:function(){
						screenConfirmDialog(frm,'id_2');
						screenNonConfirmDialog(frm,'id_2'); 
						}
						});
	   	}
	   	else{	
	   		var selectMode = frm.elements.strToDelivery.value; 
   	        var selectContainer =selectMode.split("/")[0]; 
  			var childContainer =selectMode.split("/")[1];
   			var selectMainContainer =selectMode.split("/")[2];    		
   		
   			frm = self.opener.targetFormName;
   			frm.elements.selectContainer.value=selectContainer;
   			frm.elements.childContainer.value=childContainer;
   			frm.elements.selectMainContainer.value=selectMainContainer;
			frm.action="mailtracking.defaults.mailarrival.delivermail.do";
		   	frm.method="post";
		   	frm.submit();
		   	window.closeNow();
		   	return;
	   	}		
        
	   	
     }
       
     if(frm.elements.scanTimeFromScreen.value == "ARRIVEPOPUPCLOSE"){
        frm = self.opener.targetFormName;
        frm.action="mailtracking.defaults.mailarrival.refresharrivemail.do";
	   	frm.method="post";
	   	frm.submit();
	   	window.closeNow();
	   	return;
    }
    
    if(frm.elements.scanTimeFromScreen.value == "INVENTORY_LIST_CLOSE"){
        var selectMode = frm.elements.strToDelivery.value;      
        var paBuiltFlg=selectMode.split("/")[2];
        if(paBuiltFlg=="Y"){
	       showDialog({msg:'ULD is SB.Do you want to deliver it along with mailbag ?',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
						}
						});
	   	}
	   	else{
	   		frm = self.opener.targetFormName;
 			//frm.action="mailtracking.defaults.inventorylist.deliverymail.do?selectMode="+selectMode;
			//frm.method="post";
			//frm.submit();
			var strAction="mailtracking.defaults.inventorylist.deliverymail.do?selectMode="+selectMode;
			window.opener.recreateListDetails(strAction,'inventoryListTable','inventoryButtons','carrierText');
			window.closeNow();
			return;
	   	}
        
    }
    
    
    
    
}
    

function scantimeOk(){
   frm = targetFormName;   
window.opener.IC.util.common.childUnloadEventHandler();   
   submitForm(frm,'mailtracking.defaults.mailacceptance.changescantimeok.do');
}

function scantimeCancel(){

    window.closeNow();

}

 
 /**
 *function to Confirm Dialog
 */
 function screenConfirmDialog(frm, dialogId) {
 
 	while(frm.elements.currentDialogId.value == ''){
 
 	} 
 	if(frm.elements.currentDialogOption.value == 'Y') {
 	    if(dialogId == 'id_1'){
 	        var selectMode = frm.elements.strToDelivery.value; 
 	    	frm = self.opener.targetFormName;
 			//frm.action="mailtracking.defaults.inventorylist.deliverymail.do?selectMode="+selectMode;
			//frm.method="post";
			//frm.submit();
			var strAction="mailtracking.defaults.inventorylist.deliverymail.do?selectMode="+selectMode;
			window.opener.recreateListDetails(strAction,'inventoryListTable','inventoryButtons','carrierText');
			window.closeNow();
			return;
 	    }
 	    if(dialogId == 'id_2'){
   	        var selectMode = frm.elements.strToDelivery.value; 
   	        var selectContainer =selectMode.split("/")[0]; 
  			var childContainer =selectMode.split("/")[1];
   			var selectMainContainer =selectMode.split("/")[2];    		
   		
   			frm = self.opener.targetFormName;
   			frm.elements.selectContainer.value=selectContainer;
   			frm.elements.childContainer.value=childContainer;
   			frm.elements.selectMainContainer.value=selectMainContainer;
 	    	frm.action="mailtracking.defaults.mailarrival.delivermail.do";
		   	frm.method="post";
		   	frm.submit();
		   	window.closeNow();
		   	return;
 	    }
 	    
 	}
 }
 
 
 /**
 *function to Non-Confirm Dialog
 */
 function screenNonConfirmDialog(frm, dialogId) {
 
 	while(frm.elements.currentDialogId.value == ''){
 
 	} 
 	if(frm.elements.currentDialogOption.value == 'N') {
 		if(dialogId == 'id_1'){
 		    var selectMode = frm.elements.strToDelivery.value; 
 		    var selected = selectMode.split("/")[1];
 		    var time = selectMode.split("/")[3];
 		    var pa = selectMode.split("/")[2];
 		    selectMode = "M"+"/"+selected+"/"+pa+"/"+time;
 			frm = self.opener.targetFormName;
 			//frm.action="mailtracking.defaults.inventorylist.deliverymail.do?selectMode="+selectMode;
			//frm.method="post";
			//frm.submit();
			var strAction="mailtracking.defaults.inventorylist.deliverymail.do?selectMode="+selectMode;
			window.opener.recreateListDetails(strAction,'inventoryListTable','inventoryButtons','carrierText');
			window.closeNow();
			return;
 		}
 		if(dialogId == 'id_2'){
 			var select = frm.elements.strToDelivery.value; 
 			var selectMainContainer =select.split("/")[2];  			
 		    frm = self.opener.targetFormName;
 		    frm.elements.selectMode.value = "M";
 		    frm.elements.selectMainContainer.value=selectMainContainer;
 	    	frm.action="mailtracking.defaults.mailarrival.delivermail.do";
		   	frm.method="post";
		   	frm.submit();
		   	window.closeNow();
		   	return;
 	    }
 		
 	}
 }