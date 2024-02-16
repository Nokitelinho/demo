<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){

	var frm=targetFormName;

	onScreenLoad();

	with(frm){

	
    evtHandler.addEvents("btnOk","okButton()",EVT_CLICK);
    evtHandler.addEvents("btClose","window.close()",EVT_CLICK);

		}
	



}

function onScreenLoad(){
  if(targetFormName.elements.popUpFlag.value == "N"){
			frm = self.opener.targetFormName;
			frm.action="mailtracking.defaults.mailsearch.listload.do?navigationMode=list";
			window.opener.IC.util.common.childUnloadEventHandler(); 
			frm.submit();
			window.close();	
          	}
}

function okButton()
{
//added by A-8061 as part of ICRD-229572 begin
var frm = targetFormName;	
  if(validateSelectedCheckBoxes(targetFormName,'selectedMailbagId',25,1)){
	var chkboxes = document.getElementsByName("selectedMailbagId");
	var bookingFlightNumber = "";
	if(chkboxes != null){
	for(var i=0;i<(chkboxes.length);i++) {
	   if(chkboxes[i].checked==true) {
				if(bookingFlightNumber==""){
			//	bookingFlightNumber=targetFormName.elements.bookingFlightNumber[i+1].value+" "+targetFormName.elements.bookingCarrierCode[i+1].value+" "+targetFormName.elements.bookingFlightSequenceNumber[i+1].value;
				bookingFlightNumber=i+"";
				}
				else{
				//bookingFlightNumber=bookingFlightNumber+","+targetFormName.elements.bookingFlightNumber[i+1].value+" "+targetFormName.elements.bookingCarrierCode[i+1].value+" "+targetFormName.elements.bookingFlightSequenceNumber[i+1].value;
				bookingFlightNumber=bookingFlightNumber+","+i;
				}
			}	
	}
	}
}
else{
return false;
}

submitForm(targetFormName,'mailtracking.defaults.listconsignment.bookedflight.ok.attachMailAWB.do?bookingFlightNumber='+bookingFlightNumber);
}