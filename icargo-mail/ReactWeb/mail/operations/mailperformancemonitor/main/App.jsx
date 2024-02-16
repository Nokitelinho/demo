import React, { Component, Fragment } from 'react';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad,onCloseFunction} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';

class MailPerformanceMonitor extends Component {
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
                    <DetailsContainer />
                </div>
                
                <footer className="footer-panel">
                      <IButton className="btn btn-default" onClick={this.props.onCloseFunction} componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_CLOSE" >Close</IButton>
                </footer>
            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(MailPerformanceMonitor);

const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted

    };  
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount: () => {
            dispatch(asyncDispatch(screenLoad)());
      },
        onCloseFunction: () => {
            dispatch(dispatchAction(onCloseFunction)());
        }
    }
}

const mailPerformanceMonitorContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default mailPerformanceMonitorContainer;

