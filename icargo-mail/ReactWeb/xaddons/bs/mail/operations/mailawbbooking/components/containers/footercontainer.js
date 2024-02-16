import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {onClose} from '../../actions/commonaction';
import {dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';

import FooterPanel from '../panels/FooterPanel.jsx';

const mapStateToProps=(state)=>{
    return {
    }

}

const mapDispatchToProps=(dispatch, ownProps)=>{
    return {
        onClose: () => {
            dispatch(dispatchAction(onClose)());
        },
    }

}

const FooterContainer= connectContainer(
    mapStateToProps,
    mapDispatchToProps
  )(FooterPanel)

export default FooterContainer