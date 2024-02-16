import generatepassbillingfileReducer from "../reducers/generatepassbillingfilereducer"
import { iCStore } from 'icoreact/lib/ico/framework/component/common/store'
import { warningHandler } from '../actions/commonactions';
export default iCStore(generatepassbillingfileReducer, {
    warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: []
    }
})