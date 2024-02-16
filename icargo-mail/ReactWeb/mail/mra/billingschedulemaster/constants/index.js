// used in main/Main.jsx
export const SCREEN_ID = 'mail.mra.ux.mailbillingschedulemaster';
export const SCREEN_ACTION = 'mail/mra/billingschedulemaster';

// used in action folder files 
export const SCREENLOAD_ACTION_URL = 'rest/mail/mra/defaults/billingschedulemaster/screenload';
export const LIST_ACTION_URL = 'rest/mail/mra/defaults/billingschedulemaster/list';
export const SAVE_ACTION_URL = 'rest/mail/mra/defaults/billingschedulemaster/save';
export const VALIDATE_ACTION_URL = 'rest/mail/mra/defaults/billingschedulemaster/validate';

/* 
** change it to false when going for API testing mode ** 
*/
export let SHOULD_SERVE_MOCKED_RESPONSE = true;


// reducer action type
export const SCREENLOAD_SUCCESS = 'SCREENLOAD_SUCCESS';
export const LIST_SUCCESS = 'LIST_SUCCESS';
export const CLEAR_FILTER = 'CLEAR_FILTER';
export const CLEAR_TABLE = 'CLEAR_TABLE';
export const TOGGLE_SCREEN_MODE = 'TOGGLE_SCREEN_MODE';
export const LOAD_ADD_POPUP = 'LOAD_ADD_POPUP';
export const CLOSE_ADD_POPUP = 'CLOSE_ADD_POPUP';
export const PARAMETER_OK ='PARAMETER_OK';
export const PARAMETER_EXISTS = 'PARAMETER_EXISTS';
export const DISABLE_FILTER='DISABLE_FILTER';
export const Errors = {
  MANDATORY_FROM_DATE:'Please enter BIlling FromDate',
  MANDATORY_TO_DATE:'Please enter BIlling ToDate'
}


// used in panel/*.jsx files
export const SCREEN_MODE = {
    INITIAL: 'INITIAL',
    DISPLAY: 'DISPLAY',
    EDIT: 'EDIT'
}
