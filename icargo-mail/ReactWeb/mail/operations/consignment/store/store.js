import consignmentReducer from '../reducers/consignmentreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(consignmentReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['']
    }
})