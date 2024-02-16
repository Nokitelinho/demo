import outboundReducer from '../reducers/outboundreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(outboundReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['awbReducer', 'carditReducer', 'commonReducer', 'containerReducer', 'filterReducer',
    'listFlightReducer', 'lyingListReducer', 'mailbagReducer']
    },
    searchConfig: [
        {
            tableId:'deviationlisttable',
            resourceName:'deviationListReducer.deviationlistMailbagsArray',
            resourceIndexes:['mailbagId','consignmentNumber','mailorigin','mailDestination']
        }                            

]
})