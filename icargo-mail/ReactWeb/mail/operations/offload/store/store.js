import offloadreducer from '../reducers/offloadreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(offloadreducer,{
    warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['commonReducer','filterReducer']
    }
})