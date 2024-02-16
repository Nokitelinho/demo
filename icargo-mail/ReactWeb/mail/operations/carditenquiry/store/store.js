import carditEnquiryReducer from '../reducers/carditenquiryreducer';
import { warningHandler,navigationHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(carditEnquiryReducer,{warningHandler,navigationHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['awbReducer', 'carditEnquiryReducer', 'commonReducer', 'filterReducer']
    }
})