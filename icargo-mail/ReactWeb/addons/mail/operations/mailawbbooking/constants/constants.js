export const ActionType = {
      ERROR_SHOW:'ERROR_SHOW',
      LIST_AWB_SUCCESS:'LIST_AWB_SUCCESS',
      CLEAR_FILTER:'CLEAR_FILTER',
      SCREENLOAD_SUCCESS:'SCREENLOAD_SUCCESS',
      TOGGLE_FILTER:'TOGGLE_FILTER',
      UPDATE_SORT_VARIABLE:'UPDATE_SORT_VARIABLE',
      SAVE_FILTER:'SAVE_FILTER',
      CLEAR_TABLE_FILTER:'CLEAR_TABLE_FILTER',
      SAVE_SELECTED_INDEXES:'SAVE_SELECTED_INDEXES',
      CLEAR_DSN_TABLE:'CLEAR_DSN_TABLE',
      DETACH_SUCCESS:'DETACH_SUCCESS',
      SAVE_SELECTED_MAILBAGVOS:'SAVE_SELECTED_MAILBAGVOS',
      ATTACH_AWB:'ATTACH_AWB',
      FILTER_FROM_CARDIT_ENQUIRY:'FILTER_FROM_CARDIT_ENQUIRY'
  };
 
 export const Constants = {
      CONFIRM_CODE:'C',
      QUEUED_CODE:'Q',
      CONFIRM:'Confirmed',
      QUEUED:'Queued',
      TOT_BAGS:'Total Mail bags',
      TOT_WGT:'Total Weight',
      ATTACH:'Attach',
      DETACH:'Detach',
      ACCPTD:'Accptd',
      NOT_ACPTD:'CAPTD NOT. ACCPTD',
      EXPORT:'EXPORT',
      LIST_BOOKING_POPUP_TITLE:'List Mail Bookings',
      DETACH_SUCCESS:'AWB detached successfully',
      __DELEGATE_WARNING_ONOK:'__DELEGATE_WARNING_ONOK',
      __DELEGATE_WARNING_ONCANCEL:'__DELEGATE_WARNING_ONCANCEL',
      LIST:'LIST',
      ACP:'ACP',
      CAP:'CAP',
      ACCEPTED:'Accepted',
      CAP_NOT_ACP:'Captured but not accpeted',
      TABLE_HEADER:'Mailbag Details',
      KE_SPECIFIC:'KE_SPECIFIC',
      AWB:'AWB No.',
      WEIGHT:'Weight',
 };

 export const Urls = {
      SCREEN_ID:'addons.mail.operations.mailawbbooking',
      SCREEN_ACTION:'addons/mail/operations/mailawbbooking',
      LIST_MAIL_AWB_BOOKING:'rest/addons/mail/operations/mailawbbooking/listMailAwbBooking',
      SCREENLOAD_MAIL_AWB_BOOKING:'rest/addons/mail/operations/mailawbbooking/screenloadMailAwbBooking',
      ATTACH_AWB:'rest/addons/mail/operations/mailawbbooking/attachAwb',
      STATIONLOV:'ux.showStation.do?formCount=1&multiselect=N',
      PRODUCTLOV:'products.defaults.ux.screenloadProductLov.do?productObject=productName&formNumber=1&activeProduct=Y&rowIndex=0',
      CUSTOMERLOV:'ux.showCustomer.do?formCount=1&mode=Y',
      AGENTLOV:'shared.defaults.ux.agent.screenloadagentlov.do?multiselect=N&pagination=Y&textfiledObj=agentCode&formNumber=1&textfiledDesc=&rowCount=0&agentCode="',
      SCCLOV:'ux.showScc.do?formCount=1&multiselect=Y',
      AIRPORTLOV:'ux.showAirport.do?formCount=1',

 }

 export const Forms = {
      MAIL_AWBBOOKING_FILTER:'mailAwbBookingFilter',

 }

 export const Errors = {
      

 }

 export const ComponentId = {
    LIST_BTN:'BTN_MAIL_OPERATIONS_MAILAWBBOOKING_LIST',
    CLEAR_BTN:'BTN_MAIL_OPERATIONS_MAILAWBBOOKING_CLEAR',
    CLOSE_BTN:'BTN_MAIL_OPERATIONS_MAILAWBBOOKING_CLOSE',
    BOOKING_FROM:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_BOOKINGFROM',
    BOOKING_TO:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_BOOKINGTO',
    SCC:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_SCC',
    PRODUCT:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_PRODUCT',
    ORIGIN:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_ORIGIN',
    VIA_POINT:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_VIAPOINT',
    DESTINATION:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_DEST',
    STATION:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_STATION',
    SHIPPING_DATE:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_SHIPPINGDATE',
    FLIGHT_FROM:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_FLIGHTFROM',
    FLIGHT_TO:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_FLIGHTTO',
    AGENT_CODE:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_AGENTCODE',
    CUSTOMER_CODE:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_CUSTCODE',
    USER_ID:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_USERID',
    SHIPMENT_STATUS_LBL:'CMP_MAIL_OPERATIONS_MAILAWBBOOKING_SHIPMENTSTATUS',
    ATTACH_AWB:'BTN_MAIL_OPERATIONS_MAILAWBBOOKING_ATTACH',

}

export const Key = {
    SHIPMENT_STATUS:'capacity.booking.shipmentstatus',
    SHIPMENT_STATUS_LIST:'operations.shipment.shipmentstatus',
    BOOKING_FROM:'mailtracking.defaults.carditenquiry.lbl.bookingFrom',
    BOOKING_TO:'mailtracking.defaults.carditenquiry.lbl.bookingTo',
    LIST_LBL:'mailtracking.defaults.carditenquiry.btn.list',
    CLEAR_LBL:'mailtracking.defaults.carditenquiry.btn.clear',
    CLOSE_LBL:'mailtracking.defaults.carditenquiry.btn.close',
    SCC:'mailtracking.defaults.carditenquiry.lbl.scc',
    PRODUCT:'mailtracking.defaults.carditenquiry.lbl.product',
    VIA_POINT:'mailtracking.defaults.carditenquiry.lbl.viaPoint',
    STATION:'mailtracking.defaults.carditenquiry.lbl.station',
    SHIPPING_DATE:'mailtracking.defaults.carditenquiry.lbl.shippingDate',
    FLIGHT_FROM:'mailtracking.defaults.carditenquiry.lbl.flightFrom',
    FLIGHT_TO:'mailtracking.defaults.carditenquiry.lbl.flightTo',
    AGENT_CODE:'mailtracking.defaults.carditenquiry.lbl.agentCode',
    CUSTOMER_CODE:'mailtracking.defaults.carditenquiry.lbl.customerCode',
    USER_ID:'mailtracking.defaults.carditenquiry.lbl.userid',
    SHIPMENT_STATUS_LBL:'mailtracking.defaults.carditenquiry.lbl.shipmentStatus',
    DESTINATION:'mailtracking.defaults.carditenquiry.lbl.destination',
    ORIGIN:'mailtracking.defaults.carditenquiry.lbl.origin',
    DEST_AWB_WARN:'addons.mail.operations.searchconsignment.attach.awbdestinationmismatch.warning'


}