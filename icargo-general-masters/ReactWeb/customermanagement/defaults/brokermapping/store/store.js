import brokermappingReducer from "../reducers/brokermappingreducer";
import { iCStore } from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(brokermappingReducer, {
   handleWarning : null ,
    peristanceConfig: {
        enablePersist: true, whitelist: ['']
    }
})