import inboundReducer from "../reducers/inboundreducer"
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store'

export default iCStore(inboundReducer,{warningHandler,
    peristanceConfig: {
        enablePersist: true, whitelist: ['filterReducer','commonReducer',
        'flightReducer',
        'awbReducer',
        'listFlightreducer',
        'containerReducer',
        'mailbagReducer']
    }
})