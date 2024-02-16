import mailruleconfigReducer from "../reducers/mailruleconfigreducer"
import { iCStore } from 'icoreact/lib/ico/framework/component/common/store'
import { warningHandler } from '../actions/commonactions';
export default iCStore(mailruleconfigReducer, {
    warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: []
    }
})