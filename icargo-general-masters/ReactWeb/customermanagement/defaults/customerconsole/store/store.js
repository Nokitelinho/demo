import customerConsoleReducer from '../reducers/customerconsolereducer';

import { iCStore } from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(customerConsoleReducer, {
   handleWarning : null ,
    peristanceConfig: {
        enablePersist: true, whitelist: ["commonReducer","filterReducer","invoiceReducer"]
    }
})
