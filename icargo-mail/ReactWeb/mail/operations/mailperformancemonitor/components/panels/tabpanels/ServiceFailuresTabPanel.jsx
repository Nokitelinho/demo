import React from 'react';
import { Col } from "reactstrap";
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';


export default class ServiceFailuresTabPanel extends React.Component {
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
                                
                                <li className={"nav-item ui-tabs-tab ui-corner-top ui-state-default ui-tab"+ (this.props.activeTab=='MISSING_ORIGIN_SCAN' && "ui-tabs-active ui-state-active") }
                                 role="tab" tabIndex="0" aria-controls="tabs-a" aria-labelledby="ui-id-1" aria-selected="true" aria-expanded="true">
                                   
                                    <a className="nav-link ui-tabs-anchor" tabIndex="-1" id="ui-id-1" onClick={() => this.changeTab('MISSING_ORIGIN_SCAN')}>
                                    <IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.missingoriginscan"/></a>

                                </li>

                                <li className={"nav-item ui-tabs-tab ui-corner-top ui-state-default ui-tab"+ (this.props.activeTab=='MISSING_DESTINATION_SCAN' && "ui-tabs-active ui-state-active") }
                                 role="tab" tabIndex="-1" aria-controls="tabs-b" aria-labelledby="ui-id-2" aria-selected="false" aria-expanded="false">
                                   
                                    <a className="nav-link ui-tabs-anchor" tabIndex="-1" id="ui-id-2" onClick={() => this.changeTab('MISSING_DESTINATION_SCAN')}>
                                         <IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.missingdestscan"/></a>

                                </li>

                                <li className={"nav-item ui-tabs-tab ui-corner-top ui-state-default ui-tab" + (this.props.activeTab=='MISSING_BOTH_SCAN' && "ui-tabs-active ui-state-active") }
                                role="tab" tabIndex="-1" aria-controls="tabs-c" aria-labelledby="ui-id-3" aria-selected="false" aria-expanded="false">
                                   
                                    <a className="nav-link ui-tabs-anchor" tabIndex="-1" id="ui-id-3" onClick={() => this.changeTab('MISSING_BOTH_SCAN')}>
                                           <IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.missingbothscan"/></a>
                                            
                                </li>

                            </ul> 


                      {/* <ul className="nav nav-tabs bg-white border-0 ui-tabs-nav ui-corner-all ui-helper-reset ui-helper-clearfix ui-widget-header">
                          <IButtonbar active={active}>
                              <IButtonbarItem>
                                  <div className="nav-link ui-tabs-anchor" data-tab="MissingOriginScanTab" onClick={() => this.changeTab('MissingOriginScanTab')}>
                                      Missing Origin Scan
                                </div>
                              </IButtonbarItem>
                              <IButtonbarItem>
                                  <div className="nav-link ui-tabs-anchor" data-tab="MissingDestScanTab" onClick={() => this.changeTab('MissingDestScanTab')}>
                                      Missing Delivery Scan
                                </div>
                              </IButtonbarItem>
                              <IButtonbarItem>
                                  <div className="nav-link ui-tabs-anchor" data-tab="MissingBothScanTab" onClick={() => this.changeTab('MissingBothScanTab')}>
                                      Missing Both Scan
                                </div>
                              </IButtonbarItem>
                          </IButtonbar>
                      </ul>  */}


                        </Col>
                        
 
                   
        );
    }
}
