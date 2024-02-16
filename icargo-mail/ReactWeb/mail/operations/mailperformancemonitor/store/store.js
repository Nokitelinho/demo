import mailperformancemonitorreducer from '../reducers/mailperformancemonitorreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(mailperformancemonitorreducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['commonReducer','filterReducer']
    }
})