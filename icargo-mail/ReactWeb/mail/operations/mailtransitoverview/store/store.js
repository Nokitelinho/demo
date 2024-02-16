
import { warningHandler } from '../actions/commonaction';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';
import mailTransitReducer from '../reducers/mailtransitoverviewreducer';


export default iCStore(mailTransitReducer,{warningHandler,

    peristanceConfig: {

        enablePersist: true, whitelist: ['commonReducer','filterReducer']

    }

})
