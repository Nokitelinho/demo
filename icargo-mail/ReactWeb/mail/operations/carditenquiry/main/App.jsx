import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import {asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import {listCarditEnquiry} from '../actions/filteraction.js';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import ActionButtonContainer from '../components/containers/ActionButtonContainer.js';


class CarditEnquiry extends Component {
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
                    <ActionButtonContainer />
                </div>
            </Fragment>
        );
    }
  
    
}
const decApp = LoadingHOC('relisted', true)(CarditEnquiry);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
        filterValues:  state.filterReducer.filterValues,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {

            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && (__fromScreen === 'mail.operations.ux.mailoutbound') ){
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(listCarditEnquiry)({'displayPage':'1',mode:'LIST',autoList:true}))
                        } else {
                            return response
                        }
                    });
            }
            else {
                dispatch(asyncDispatch(screenLoad)(props));
            }
      }
     
    }
}

const CarditEnquiryContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default CarditEnquiryContainer;
    
