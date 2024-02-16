import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import React, { Component, Fragment } from 'react';
import ActionButtonContainer from '../components/containers/ActionButtonContainer.js';
import MarkHBAContainer from '../components/containers/MarkHbaContainer.js';
import {screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import {asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';

class HbaMarking extends Component {
       constructor(props) {
        super(props)
    }

   
    render() {
        return (
            <Fragment>
                <div className="header-panel animated fadeInDown">
                    <MarkHBAContainer />
                </div>
                 <div className="footer-panel">
                    <ActionButtonContainer />
                </div>
            </Fragment>
        );
    }
  
    
}
const decApp = LoadingHOC('relisted', true)(HbaMarking);

const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted
       
    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {
                dispatch(asyncDispatch(screenLoad)(props));
      }
     
    }
}

const HbaMarkingContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default HbaMarkingContainer;
    
