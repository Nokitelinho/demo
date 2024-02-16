import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import {asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import {listContainersinFlight,listmailbagsinContainers} from '../actions/flightlistactions.js';
import {listDetails} from '../actions/filteraction.js';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';

class MailOutbound extends Component {
    constructor(props) {
        super(props)
    } 
    render() {
        return(

         <Fragment>
                <FilterContainer/>
                <DetailsContainer/>
                <div className="footer-panel">
                    <ActionButtonContainer />
                </div> 
         </Fragment>
        );
    }
  
    
}
const decApp = LoadingHOC('relisted', true)(MailOutbound);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount:(props)=> {
            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && (__fromScreen === 'OFFLOAD') ){
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(listDetails)()).then(()=>{
                                dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                                    dispatch(asyncDispatch(listmailbagsinContainers)());
                                  });
                            });
                            
                            
                        } else {
                            return response
                        }
                    });
            }
            else{
                dispatch(asyncDispatch(screenLoad)(props));
            }
      }
    }
}

const MailOutboundContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default MailOutboundContainer;

