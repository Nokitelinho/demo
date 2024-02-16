import invoicenquiryreducer from '../reducers/invoicenquiryreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(invoicenquiryreducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['commonReducer','filterReducer']
    }
})