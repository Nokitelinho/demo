import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import {Key,ComponentId } from '../../constants/constants.js';
import {IMessage } from 'icoreact/lib/ico/framework/html/elements';
const Aux =(props) =>props.children;


export default class ActionButtonPanel extends React.Component {

    constructor(props) {
        super(props);
        this.onCloseFunction = this.onCloseFunction.bind(this);
        this.clearBatchFunction = this.clearBatchFunction.bind(this);
    }


    onCloseFunction=() =>{
       this.props.onCloseFunction(); 
    }
    clearBatchFunction=() =>{
        this.props.clearBatchFunction();
    }

    render() {
        let isListed = this.props.screenMode !=="initial";    
        return (
            <Aux>
               <div class="footer-panel">
                    <IButton category="default" bType="CLEARBATCH" accesskey="O"   disabled={!isListed} componentId={ComponentId.CMP_CLEARBATCH}onClick={this.clearBatchFunction} ><IMessage msgkey={Key.CLEARBATCH_LBL} /></IButton>
                    <IButton category="default" bType="CLOSE" accesskey="O" componentId={ComponentId.CMP_CLS} onClick={this.onCloseFunction} >Close</IButton>
                </div> 
            </Aux>
        );
    }
}
