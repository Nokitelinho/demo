import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad,onCloseFunction} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import {onlistContainerDetails} from '../actions/filteraction.js';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class ListContainer extends Component {
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
                <div className="footer-panel">
                    <button className="btn btn-default" bType="CLOSE" accesskey="O" onClick={this.props.onCloseFunction}>Close</button>
                </div>
            </Fragment>
        );
    }    
}
const decApp = LoadingHOC('relisted', true)(ListContainer);

const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {
			//Added by A-8164 for mailinbound 
            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen &&  (__fromScreen === 'MailInbound' || __fromScreen === 'MailOutbound'  || __fromScreen === 'OFFLOAD')){
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1',action:'LIST',autoList: true }))
                        } else {
                            return response
                        }
                    });
            }
            else {
          dispatch(asyncDispatch(screenLoad)());
            }
        },
        onCloseFunction: () => {
         dispatch(dispatchAction(onCloseFunction)());
      }
    }
}

const listContainerContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default listContainerContainer;
    
