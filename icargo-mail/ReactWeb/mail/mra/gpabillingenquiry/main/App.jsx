import React, { Component, Fragment } from 'react';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad } from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import PopupContainer from '../components/containers/popupcontainer';
import {CLEAR_INDEXES} from '../constants/constants'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {listBillingDetails} from '../actions/filteraction.js';

class GPABillingEnquiry extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        return (

            <Fragment>

                <FilterContainer />
                 <DetailsContainer /> 
                <ActionButtonContainer /> 
                 <PopupContainer />
            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(GPABillingEnquiry);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,

    };
}


const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {

            const { dynaProps: { __fromScreen } } = props
            console.log("__fromScreen"+__fromScreen);
            if (__fromScreen && (__fromScreen === 'ROUTING') ){
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                             
                            dispatch(asyncDispatch(listBillingDetails)({'displayPage':'1',mode:'LIST',autoList:true}))
                        } else {
                            return response
                        }
                    });
            }
            else {
               
            dispatch( { type: CLEAR_INDEXES});
            dispatch(asyncDispatch(screenLoad)());
        }
      
      
      }
	  
    }
}

const gpaBillingEnquiryContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default gpaBillingEnquiryContainer;
