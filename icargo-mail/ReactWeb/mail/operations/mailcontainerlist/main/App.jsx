import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/FilterContainer.js';
import DetailsContainer from '../components/containers/DetailsContainer.js';
import ActionButtonContainer from '../components/containers/ActionButtonContainer.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import {screenLoad} from '../actions/commonaction.js';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { CommonUtil } from 'icoreact/lib/ico/framework/config/app/commonutil';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {onlistContainerDetails} from '../actions/filteraction.js';

class MailContainerList extends Component {
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


const decApp = LoadingHOC('relisted', true)(MailContainerList);

const mapStateToProps = (state) => {
    return {
        relisted: state.commonreducer.relisted,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount:()=> {
            if(CommonUtil.screenConfig.fromScreen === 'MAINTAINLDPLN' ){
                dispatch(asyncDispatch(screenLoad)())
                .then((response) => {
                    if (isEmpty(response.errors)) {
                        dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1',action:'LIST',autoList:true}));
                    } else {
                        return response
                    }
                });
            }else{
            dispatch(asyncDispatch(screenLoad)())
             }
                }

             }
        }
    




const mailContainerList = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)


export default mailContainerList;

