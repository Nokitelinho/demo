export const ActionType = {
     SCREENLOAD_SUCCESS:'SCREENLOAD_SUCCESS',
     SELECTED_COUNT:'SELECTED_COUNT',
     SAVE_SELECTED_INDEX:'SAVE_SELECTED_INDEX',
     SAVE_FILTERVALUES:'SAVE_FILTERVALUES',
     EXECUTE_SUCCESS:'EXECUTE_SUCCESS'
  };
 
 export const Constants = {
     ROUTEDETAIL_TABLE:'routeDetailsTable',
     SELECT :'SELECT',
     UNSELECT:'UNSELECT',
 };

 export const Urls = {
      SCREEN_ID:'mailtracking.mra.defaults.listmailprorationexceptions',
      SCREEN_ACTION:'mail/mra/defaults/modifyroute',
      SCREEN_LOAD:'rest/mail/mra/defaults/modifyroute/screenload',
      EXECUTE:'rest/mail/mra/defaults/modifyroute/execute',

 }

 export const Forms = {
    

 }

 export const Errors = {
     
      NOROWS_SELECTED:'Please select atleast one row to delete',
      EMPTY_ROUTE_DETAILS:'Please enter  route details ,Transfer PA or Transfer Airline',
      EMPTY_CARRIERCODE:'Please enter flight carrier code',
      EMPTY_FLIGHTNO:'Please enter flight No',
      EMPTY_DEPDATE:'Please select Dep Date',
      EMPTY_POL:'Type or select the POL',
      EMPTY_POU:'Type or select the POU',
      ENTER_ONLYONEVALUE:'Only value for either Transfer PA or Transfer Airline can be specified ',
      
 }

 export const ComponentId = {
    CLOSE_BTN:'BTN_MAIL_MRA_MODIFYROUTE_CLOSE',
    EXECUTE_BTN:'BTN_MAIL_MRA_MODIFYROUTE_EXECUTE',
    ADD_BTN:'BTN_MAIL_MRA_MODIFYROUTE_ADD',
    DELETE_BTN:'BTN_MAIL_MRA_MODIFYROUTE_DELETE',
    CARRIER_CODE:'TXT_MAIL_MRA_MODIFYROUTE_CARRIERCODE',
    FLIGHT_NO:'TXT_MAIL_MRA_MODIFYROUTE_FLIGHTNUMBER',
    DEP_DATE:'CMP_MAIL_MRA_MODIFYROUTE_DEPDATE',
    POL:'TXT_MAIL_MRA_MODIFYROUTE_POL',
    POU:'TXT_MAIL_MRA_MODIFYROUTE_POU',
    BLOCKSPACE:'CMP_MAIL_MRA_MODIFYROUTE_BLOCKSPACE',
    AGREEMENT_TYPE:'CMP_MAIL_MRA_MODIFYROUTE_AGREEMENT_TYPE',


}

export const Key = {
   
 CLOSE_LBL:'mail.mra.defaults.modifyroute.btnlbl.close',
 EXECUTE_LBL:'mail.mra.defaults.modifyroute.btnlbl.execute',
 ADD_LBL:'mail.mra.defaults.modifyroute.btnlbl.add',
 DELETE_LBL:'mail.mra.defaults.modifyroute.btnlbl.delete',
 ADD_TLTP:'mail.mra.defaults.modifyroute.tooltip.add',
 DELETE_TLTP:'mail.mra.defaults.modifyroute.tooltip.delete',
 CLOSE_TLTP:'mail.mra.defaults.modifyroute.tooltip.close' ,
 EXECUTE_TLTP:'mail.mra.defaults.modifyroute.tooltip.execute',
 TITLE_LBL:'ROUTING DETAILS',

}