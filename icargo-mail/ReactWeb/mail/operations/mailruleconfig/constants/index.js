// used in main/Main.jsx
export const SCREEN_ID = 'mail.operations.ux.mailruleconfig';
export const SCREEN_ACTION = 'mail/operations/mailruleconfig';

// used in action folder files 
export const SCREENLOAD_ACTION_URL = 'rest/mail/operations/mailruleconfig/screenload';
export const LIST_ACTION_URL = 'rest/mail/operations/mailruleconfig/list';
export const SAVE_ACTION_URL = 'rest/mail/operations/mailruleconfig/save';
export const VALIDATE_URL = 'rest/mail/operations/mailruleconfig/validatemailrule';

/* 
** change it to false when going for API testing mode ** 
*/
export let SHOULD_SERVE_MOCKED_RESPONSE = true;


// reducer action type
export const SCREENLOAD_SUCCESS = 'SCREENLOAD_SUCCESS';
export const LIST_SUCCESS = 'LIST_SUCCESS';
export const CLEAR_FILTER = 'CLEAR_FILTER';
export const TOGGLE_SCREEN_MODE = 'TOGGLE_SCREEN_MODE';
export const LOAD_ADD_POPUP = 'LOAD_ADD_POPUP';
export const CLOSE_ADD_POPUP = 'CLOSE_ADD_POPUP';
export const SAVE_SELECTED_INDEX = 'SAVE_SELECTED_INDEX';
export const SAVE_SUCCESS = 'SAVE_SUCCESS';
export const ADD_NEW_MAILRULE = 'ADD_NEW_MAILRULE';
export const LOAD_MODIFY_POPUP='LOAD_MODIFY_POPUP';

// used in panel/*.jsx files
export const SCREEN_MODE = {
    INITIAL: 'INITIAL',
    DISPLAY: 'DISPLAY',
    EDIT: 'EDIT'
}
