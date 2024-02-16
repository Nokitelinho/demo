 import modifyRouteReducer from '../reducers/modifyroutereducer';
 import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';
 import { warningHandler } from '../actions/commonaction';
 export default iCStore(modifyRouteReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['']
    }
 })
