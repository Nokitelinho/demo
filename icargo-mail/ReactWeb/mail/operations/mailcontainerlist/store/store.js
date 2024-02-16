import mailcontainerlistreducer from '../reducers/mailcontainerlistreducer';

import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(mailcontainerlistreducer,{peristanceConfig: {
        enablePersist: true, whitelist: [' commonReducer','filterreducer','mailcontainerreducer' ]
    }
})