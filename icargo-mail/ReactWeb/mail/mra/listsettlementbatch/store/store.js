import listSettlementBatchreducer from '../reducers/listsettlementbatchreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(listSettlementBatchreducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['filterReducer','commonReducer']
    }
})