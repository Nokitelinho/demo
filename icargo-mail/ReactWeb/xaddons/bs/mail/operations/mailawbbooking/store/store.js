import mailAwbBookingReducer from '../reducers/mailawbbookingreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(mailAwbBookingReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['']
    }
})