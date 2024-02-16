import containerenquiryreducer from '../reducers/containerenquiryreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(containerenquiryreducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: [' commonReducer',
  'listcontainerreducer',
  'flightReducer'  ]
    }
})