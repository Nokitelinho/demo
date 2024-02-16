import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {attachAll,fetchMailbagsForDsns,detachAll,attachAwbWhenNotSelected} from '../../actions/commonaction';
import { listCarditDsnEnquiry } from '../../actions/filteraction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import {Constants,Errors,Key} from '../../constants/constants.js'
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

import FooterPanel from '../panels/FooterPanel.jsx';

const mapStateToProps=(state)=>{
    return {
            filterValues:  state.filterReducer.filterValues,
    }

}

const mapDispatchToProps=(dispatch, ownProps)=>{
    return {
        onClose: ()=> {
            navigateToScreen('home.jsp',{});
        },
        attachAll: () => {
            dispatch(asyncDispatch(fetchMailbagsForDsns)())
                    .then((response)=>dispatch(asyncDispatch(attachAll)(response)))
                        .then(()=> dispatch(requestWarning([{code:Key.RELIST_WARN,description:Errors.RELIST_WARN}],
                            {functionRecord:listCarditDsnEnquiry})));
        },
        detachAll: () => {
            dispatch(asyncDispatch(fetchMailbagsForDsns)())
                    .then((response)=>dispatch(asyncDispatch(detachAll)(response)))
                        .then(()=>dispatch(asyncDispatch(listCarditDsnEnquiry)({mode:Constants.LIST})))
        },

    }

}

const FooterContainer= connectContainer(
    mapStateToProps,
    mapDispatchToProps
  )(FooterPanel)

export default FooterContainer