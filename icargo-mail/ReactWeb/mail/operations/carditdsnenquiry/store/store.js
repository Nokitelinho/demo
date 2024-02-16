import carditDsnEnquiryReducer from '../reducers/carditdsnenquiryreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(carditDsnEnquiryReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['']
    }
})