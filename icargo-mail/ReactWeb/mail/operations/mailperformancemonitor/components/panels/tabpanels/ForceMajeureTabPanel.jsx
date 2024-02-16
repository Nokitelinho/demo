import React from 'react';
import { Col } from "reactstrap";
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

export default class ForceMajeureTabPanel extends React.Component {
    constructor(props) {
        super(props);
    }

   changeTab = (currentTab) => {
        this.props.changeTab(currentTab)
    }

    render() {
     
        return (
        
            <Col className="px-0">


                           <ul className="nav nav-tabs bg-white border-0 ui-tabs-nav ui-corner-all ui-helper-reset ui-helper-clearfix ui-widget-header" role="tablist">
                                
                                <li className={"nav-item ui-tabs-tab ui-corner-top ui-state-default ui-tab"+ (this.props.activeTab=='RAISED_MAILBAGS' && "ui-tabs-active ui-state-active") }
                                 role="tab" tabIndex="0" aria-controls="tabs-a" aria-labelledby="ui-id-1" aria-selected="true" aria-expanded="true">
                                   
                                    <a className="nav-link ui-tabs-anchor" tabIndex="-1" id="ui-id-1" onClick={() => this.changeTab('RAISED_MAILBAGS')}>
                                    <IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.raised"/></a>

                                </li>

                                <li className={"nav-item ui-tabs-tab ui-corner-top ui-state-default ui-tab"+ (this.props.activeTab=='APPROVED_MAILBAGS' && "ui-tabs-active ui-state-active") }
                                 role="tab" tabIndex="-1" aria-controls="tabs-b" aria-labelledby="ui-id-2" aria-selected="false" aria-expanded="false">
                                   
                                    <a className="nav-link ui-tabs-anchor" tabIndex="-1" id="ui-id-2" onClick={() => this.changeTab('APPROVED_MAILBAGS')}>
                                            <IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.approved"/></a>

                                </li>

                                <li className={"nav-item ui-tabs-tab ui-corner-top ui-state-default ui-tab" + (this.props.activeTab=='REJECTED_MAILBAGS' && "ui-tabs-active ui-state-active") }
                                role="tab" tabIndex="-1" aria-controls="tabs-c" aria-labelledby="ui-id-3" aria-selected="false" aria-expanded="false">
                                   
                                    <a className="nav-link ui-tabs-anchor" tabIndex="-1" id="ui-id-3" onClick={() => this.changeTab('REJECTED_MAILBAGS')}>
                                           <IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.rejected"/></a>
                                            
                                </li>

                            </ul> 

            </Col>
          
        );
    }
}
