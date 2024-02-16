import billingschedulemasterReducer from "../reducers/billingschedulemasterreducer"
import { iCStore } from 'icoreact/lib/ico/framework/component/common/store'
import { warningHandler } from '../actions/commonactions';
export default iCStore(billingschedulemasterReducer, {
    warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: []
    }
})