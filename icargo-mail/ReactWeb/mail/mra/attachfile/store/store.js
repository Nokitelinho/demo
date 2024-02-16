import attachfilereducer from '../reducers/attachfilereducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(attachfilereducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['filterReducer','commonReducer']
    }
})