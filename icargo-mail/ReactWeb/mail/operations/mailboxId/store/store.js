 import mailboxidReducer from '../reducers/mailboxidreducer';
 import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';
 import { warningHandler } from '../actions/commonaction';
 export default iCStore(mailboxidReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['']
    }
 })
