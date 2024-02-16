export const ActionType = {
      ERROR_SHOW:'ERROR_SHOW',
      LIST_DSN_SUCCESS:'LIST_DSN_SUCCESS',
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
  };
 
 export const Constants = {
      TOT_BAGS:'Total Mail bags',
      TOT_WGT:'Total Weight',
      ATTACH:'Attach',
      DETACH:'Detach',
      ATTACH_AWB:'Attach AWB',
      DETACH_AWB:'Detach AWB',
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
      TABLE_HEADER:'DSN Details',
      KE_SPECIFIC:'KE_SPECIFIC',
      AWB:'AWB No.',
      WEIGHT:'Weight',
      POPUP_CLOSE_ID:'BTN_MAIL_OPERATIONS_MAILAWBBOOKING_CLOSE'
 };

 export const Urls = {
      SCREEN_ID:'mail.operations.ux.carditdsnenquiry',
      SCREEN_ACTION:'mail/operations/carditdsnenquiry',
      LIST_CARDIT_DSN_ENQUIRY:'rest/mail/operations/carditdsnenquiry/listCarditDsnEnquiry',
      SCREENLOAD_CARDIT_DSN_ENQUIRY:'rest/mail/operations/carditdsnenquiry/screenloadCarditDsnEnquiry',
      LIST_CARDIT_MAILBAGS_DSN:'rest/mail/operations/carditdsnenquiry/listMailbagsforDsns',
      ATTACH_AWB:'mail.operations.ux.mailawbbookingpopup.screenload.do?fromScreen=MTK067',
      DETACH_AWB:'rest/mail/operations/carditdsnenquiry/detachAwb',
      OOELOV:'mailtracking.defaults.ux.oelov.list.do?formCount=1',
      SUBCLASSLOV:'mailtracking.defaults.ux.subclaslov.list.do?formCount=1',
      PACODELOV:'mailtracking.defaults.ux.palov.list.do?formCount=1&mode=Y',
      AIRPORTLOV:'ux.showAirport.do?formCount=1',

 }

 export const Forms = {
      CARDIT_DSN_FILTER:'carditDsnFilter',
      CARDIT_DSN_TABLE_FILTER:'carditDsnTableFilter',

 }

 export const Errors = {
      MANDATORY_FILTER_ERR:'Please enter DSN or Consignment Number or From and To date or Consignment date or AWB number or Flight details',
      ATTACH_NOT_SELECTED_ERR:'Please specify Consignment No or select DSN',
      DETACH_NOT_SELECTED_ERR:'Please provide AWB filter or select DSN',
      ATTACH_WARN:'Attach AWB shall be triggered for all eligible records in the consignment. Do you want to continue?',
      RELIST_WARN:'Listed data may have changed. Do you want to relist the screen?',
      DETACH_WARN:'Detach AWB shall be triggered for all eligible records. Do you want to continue?',
      ATTACH_ACP_ERR:'AWB cannot be attached to an already accepted mail bag',
      ATTACH_NOT_POSSIBLE:'AWB already attached',
      DETACH_NOT_POSSIBLE_COUNT:'AWB detachment not possible as mailbag count is more than',
      COUNT_ERROR:'.Please use other filter criteria for detachment',
      DETACH_ERROR:'Selected mail bag is not attached to any AWB',
      ATTACH_NOT_POSSIBLE_COUNT:'AWB attachment not possible as mailbag count is more than'
 }

 export const ComponentId = {
    DSN_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_DSN',
    OOE_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE',
    DOE_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_DOE',
    CATEGORY_SELECT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY',
    SUBCLASS_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_SUBCLASS',
    YEAR_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_YEAR',
    CONSIGNMENT_NO_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTNO',
    PACODE_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_PACODE',
    CONSIGNMENTDATE_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTDATE',
    RDT_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDT',
    ULDNO_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_ULDNO',
    DATEFROM_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATEFROM',
    DATETO_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATETO',
    MAILSTATUS_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILSTATUS',
    FLIGHTTYPE_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTTYPE',
    AIRPORT_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT',
    RDTTIME_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDTTIME',
    LIST_BTN:'BUT_MAIL_OPERATIONS_CARDITDSNENQUIRY_LIST',
    CLEAR_BTN:'BUT_MAIL_OPERATIONS_CARDITDSNENQUIRY_CLEAR',
    AWBSELECT_TEXT:'CMP_MAIL_OPERATIONS_CARDITENQUIRY_AWBATTACHED',

}

export const Key = {
    MAIL_STATUS:'mailtracking.defaults.mailstatus',
    MAIL_CATEGORY:'mailtracking.defaults.mailcategory',
    FLIGHT_TYPE:'mailtracking.defaults.carditenquiry.flighttype',
    ATTACH_AWB_WARN:'mail.operations.carditdsnenquiry.attachawbwarning',
    RELIST_WARN:'mail.operations.carditdsnenquiry.relistwarning',
    DETACH_AWB_SUCCESS:'mail.operations.carditdsnenquiry.detachsuccess',
    DETACH_AWB_WARN:'mail.operations.carditdsnenquiry.detachawbwarning',
    DSN_LBL:'mail.operations.carditenquiry.lbl.dsn',
    OOE_LBL:'mail.operations.carditenquiry.lbl.ooe',
    DOE_LBL:'mail.operations.carditenquiry.lbl.doe',
    CATEGORY_LBL:'mail.operations.carditenquiry.lbl.category',
    SUBCLASS_LBL:'mail.operations.carditenquiry.lbl.subclass',
    YEAR_LBL:'mail.operations.carditenquiry.lbl.year',
    CONSIGNMENT_NUMBER_LBL:'mail.operations.carditenquiry.lbl.consignmentNo',
    PACODE_LBL:'mail.operations.carditenquiry.lbl.pacode',
    MAILSTATUS_LBL:'mail.operations.carditenquiry.lbl.mailStatus',
    ATTACH_LBL:'mail.operations.carditdsnenquiry.lbl.attach',
    DETACH_LBL:'mail.operations.carditdsnenquiry.lbl.detach',
    ATTACH_ALL_LBL:'mail.operations.carditdsnenquiry.lbl.attachall',
    DETACH_ALL_LBL:'mail.operations.carditdsnenquiry.lbl.detachall',
    CLOSE_LBL:'mail.operations.carditdsnenquiry.lbl.close',
    CONSIGNMENT_DATE_LBL:'mail.operations.carditdsnenquiry.lbl.consignmentdate',
    RDT_LBL:'mail.operations.carditdsnenquiry.lbl.rdt',
    AWB_ATTACHED_LBL:'mail.operations.carditdsnenquiry.lbl.awbattached',
    FLIGHT_TYPE_LBL:'mail.operations.carditdsnenquiry.lbl.flighttype',
    LIST_LBL:'mail.operations.carditdsnenquiry.lbl.list',
    CLEAR_LBL:'mail.operations.carditdsnenquiry.lbl.clear',
    AIRPORT_LBL:'mail.operations.carditenquiry.lbl.upliftAirport',
    FROMDATE_LBL:'mail.operations.carditenquiry.lbl.fromDate',
    TODATE_LBL:'mail.operations.carditenquiry.lbl.toDate',
    RSN_LBL:'mail.operations.carditenquiry.lbl.rsn',
    ULD_LBL:'mail.operations.carditenquiry.lbl.uldNo',
    MISCRPT_LBL:'mail.operations.carditenquiry.lbl.miscreport',





}