import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import {Key,ComponentId } from '../../constants/constants.js';
import {IMessage } from 'icoreact/lib/ico/framework/html/elements';
const Aux =(props) =>props.children;


export default class ActionButtonPanel extends React.Component {

    constructor(props) {
        super(props);
        this.onCloseFunction = this.onCloseFunction.bind(this);
    }


    onCloseFunction=() =>{
       this.props.onCloseFunction(); 
    }
	navigateToListSettlementBatch=() =>{       
        this.props.navigateToListSettlementBatch();
    }

    render() {
        let isListed = this.props.displayMode !=="initial";    
        return (
            <Aux>
               <div class="footer-panel">
                    <IButton category="default" bType="BATCHDETAILS" accesskey="O" onClick={this.navigateToListSettlementBatch} disabled={!isListed} componentId={ComponentId.CMP_BATCH_DTL} ><IMessage msgkey={Key.BATCH_DTL_LBL}  /></IButton>
                    <IButton category="default" bType="CLOSE" accesskey="O"  onClick={this.onCloseFunction} componentId={ComponentId.CMP_CLS} ><IMessage msgkey={Key.CLS_LBL}  /></IButton>

                </div> 
            </Aux>
        );
    }
}
