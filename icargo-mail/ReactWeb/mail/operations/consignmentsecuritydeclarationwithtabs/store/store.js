import consignmentsecuritydeclarationReducer from "../reducers/consignmentsecuritydeclarationreducer"
import { iCStore } from 'icoreact/lib/ico/framework/component/common/store'
import { warningHandler } from '../actions/commonactions';
export default iCStore(consignmentsecuritydeclarationReducer, {
    warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: []
    }
})