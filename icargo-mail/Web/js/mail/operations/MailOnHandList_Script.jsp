<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;   
    with(frm)
	{
     //CLICK Events
     	evtHandler.addEvents("btnList","listmailonhandlist(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clearmailonhandlist(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closemailonhandlist(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btnViewContainer","viewcontainers(this.form)",EVT_CLICK);  	
       // evtHandler.addEvents("booking_link","prepareAttributes(event,this,'booking_','bookingDetails')",EVT_CLICK);     
		evtHandler.addEvents("selectmaillist","toggleTableHeaderCheckbox('selectmaillist',targetFormName.mailonhandlist)",EVT_CLICK);
        evtHandler.addIDEvents("airportlov"," displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.airport.value,'Airport','1','airport','',0)", EVT_CLICK);
	var options_arry = new Array();
		options_arry = {
		  "autoOpen" : false,
		  "width" : 350,
		  "height": 50,
		  "draggable" :false,
		  "resizable" :false,
		  
		};
	initDialog('bookingDetails',options_arry);

}
}
function prepareAttributes(event,obj,div,divName){
    var invId=obj.id;
	var divId;
	var indexId=invId.split('_')[2];
	if(indexId != null && indexId != ""){
	 divId=div+indexId;
	}else{
	 divId=div+'';
	}
	IC.util.event. stopPropagation(event);
	showInfoMessage(event,divId,invId,divName,20,30);
}
function submitPage(lastPg,displayPg){
frm=targetFormName;
  frm.elements.lastPageNum.value=lastPg;
  frm.elements.displayPage.value=displayPg;
  submitForm(frm,'mailtracking.defaults.mailonhandlist.listmailhandlist.do');
}

function listmailonhandlist()
{
  frm=targetFormName;
  frm.elements.displayPage.value=1; 
  frm.elements.lastPageNum.value=0;
  submitForm(frm,'mailtracking.defaults.mailonhandlist.listmailhandlist.do');
  
}

function clearmailonhandlist(){
frm=targetFormName;
   submitForm(frm,'mailtracking.defaults.mailonhandlist.clearmailhandlist.do');
   
}

function closemailonhandlist(){

	frm=targetFormName;   
    location.href = appPath + "/home.jsp";
}


function viewcontainers(){	
frm=targetFormName;
	if(validateSelectedCheckBoxes(frm,'selectmaillist','1','1')){
	submitForm(frm,'mailtracking.defaults.mailonhandlist.viewcntrlmailhandlist.do');
	}
 }