// used in main/Main.jsx
export const SCREEN_ID = 'mail.mra.generatepassbillingfile';
export const SCREEN_ACTION = 'mail/mra/generatepassbillingfile';

// used in action folder files 
export const SCREENLOAD_ACTION_URL = 'rest/mail/mra/generatepassbillingfile/screenload';
export const LIST_ACTION_URL = 'rest/mail/mra/generatepassbillingfile/list';
export const POPULATE_PERIOD_ACTION_URL = 'rest/mail/mra/gpabilling/generatepassbillingfile/populateperiod';

/* 
** change it to false when going for API testing mode ** 
*/
export let SHOULD_SERVE_MOCKED_RESPONSE = true;


// reducer action type
export const SCREENLOAD_SUCCESS = 'SCREENLOAD_SUCCESS';
export const LIST_SUCCESS = 'LIST_SUCCESS';
export const CLEAR_FILTER = 'CLEAR_FILTER';
export const TOGGLE_SCREEN_MODE = 'TOGGLE_SCREEN_MODE';

// used in panel/*.jsx files
export const SCREEN_MODE = {
    INITIAL: 'INITIAL',
    DISPLAY: 'DISPLAY',
    EDIT: 'EDIT'
}
