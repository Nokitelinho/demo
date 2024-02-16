import advancepaymentreducer from '../reducers/advancepaymentreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(advancepaymentreducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['filterReducer','commonReducer']
    }
})