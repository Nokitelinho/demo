import gpabillingenquiryreducer from '../reducers/gpabillingenquiryreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(gpabillingenquiryreducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['filterReducer','commonReducer']
    }
})