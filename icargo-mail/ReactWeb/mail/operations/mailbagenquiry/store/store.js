import mailbagEnquiryReducer from '../reducers/mailbagenquiryreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(mailbagEnquiryReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['filterReducer','filterReducer',
  'commonReducer',
  'listmailbagsreducer',
  'flightReducer']
    }
})