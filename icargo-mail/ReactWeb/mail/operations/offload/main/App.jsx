
import React, { Component,Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import ActionButtonContainer from  '../components/containers/actionbuttoncontainer.js';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad } from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { offloadOnNavigation, offloadOnNavigationFromMailbagEnquiry, offloadMailOnNavigationFromMailbagOutbound,
    offloadContainerOnNavigationFromMailbagOutbound } from '../actions/filteraction.js';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
const Aux = (props) => props.children;

class Offload extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        return (

            <Fragment>

                <div className="header-panel animated fadeInDown"><FilterContainer/></div>
                <div className="section-panel animated fadeInUp"><DetailsContainer/></div>
                <footer className="footer-panel">
                  { /* <button className="btn btn-primary" onClick={this.props.onOffloadFunction} enabled={activeOffloadTab=='DSN_VIEW'?false:true}>Offload</button>
                    <button className="btn btn-default" onClick={this.props.onCloseFunction} >Close</button>*/}
                    <ActionButtonContainer/>
                </footer>
            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(Offload);

const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,

    };
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onMount: (props) => {
             const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && __fromScreen === 'mail.operations.ux.containerenquiry') {
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(offloadOnNavigation)({ autoList: true }))
                        } else {
                            return response
                        }
                    });
            }
            if (__fromScreen && __fromScreen === 'mail.operations.ux.mailbagenquiry') {
            dispatch(asyncDispatch(screenLoad)())
                .then((response) => {
                    if (isEmpty(response.errors)) {
                        dispatch(asyncDispatch(offloadOnNavigationFromMailbagEnquiry)({ autoList: true }))
                    } else {
                        return response
                    }
                });
            }
            if (__fromScreen && __fromScreen === 'mail.operations.ux.mailoutbound') {
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(offloadMailOnNavigationFromMailbagOutbound)({ autoList: true }))
                        } else {
                            return response
                        }
                    });
                }
            if (__fromScreen && __fromScreen === 'mail.operations.ux.mailoutbound.container') {
                    dispatch(asyncDispatch(screenLoad)())
                        .then((response) => {
                            if (isEmpty(response.errors)) {
                                dispatch(asyncDispatch(offloadContainerOnNavigationFromMailbagOutbound)({ autoList: true }))
                            } else {
                                return response
                            }
                        });
                }
            else {
            dispatch(asyncDispatch(screenLoad)());
            }
        },
		/*   onCloseFunction: () => {
            dispatch(dispatchAction(onCloseFunction)());
        },
        onOffloadFunction:() =>{
            dispatch(asyncDispatch(onOffloadFunction)())
        }*/
    }
}


const OffloadContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default OffloadContainer;

