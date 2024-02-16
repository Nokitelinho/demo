import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import MailBoxDetailsContainer from '../components/containers/mailboxdetailscontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import {asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad,onCloseFunction} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';

class MailboxID extends Component {
       constructor(props) {
        super(props)
    }
    render() {
        return (
            <Fragment>
                <div className="header-panel animated fadeInDown">
                    <FilterContainer />
                </div>
                <div className="section-panel animated fadeInUp">
                    <MailBoxDetailsContainer />
                </div>
                <div className="footer-panel">
                    <ActionButtonContainer/>
                </div> 

            </Fragment>
        );
    }
  
    
}
const decApp = LoadingHOC('relisted', true)(MailboxID);
const mapStateToProps = (state) => {
    return {
        relisted:true,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount:()=> {
          dispatch(asyncDispatch(screenLoad)());
      },
    //   onClose: () => {
    //     dispatch(dispatchAction(onCloseFunction)());
    //   }
    }
}

const MailboxIDContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default MailboxIDContainer;