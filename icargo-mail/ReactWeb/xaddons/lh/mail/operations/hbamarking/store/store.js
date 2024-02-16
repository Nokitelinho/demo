import hbamarkingreducer from '../reducers/hbamarkingreducer';
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(hbamarkingreducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['commonReducer', 'hbamarkingreducer']
    }
})