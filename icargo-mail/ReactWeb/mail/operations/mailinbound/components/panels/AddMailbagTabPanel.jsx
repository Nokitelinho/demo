import React, { Component, Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import { Col } from 'reactstrap';
import PropTypes from 'prop-types';

export default class AddMailbagTabPanel extends Component {
    constructor(props) {
        super(props);
    }
    changeTab = (currentTab) => {
        this.props.changeAddMailbagTab(currentTab)
    }
  
    render() {
        let active = 0;
        if (this.props.activeMailbagAddTab === 'EXCEL_VIEW') {
            active = 0;
        } else  {
            active = 1;
        }
        return (
            <Fragment>
                <div className="card-header card-header-action">
                    <Col><h4>Mailbag Details</h4></Col>
                    {this.props.activeMailbagAddTab != 'EXCEL_VIEW' &&
                        <Col xs="auto">
                            <IButton category="primary" onClick={this.props.addRow}>Add</IButton>
                            <IButton category="primary" onClick={this.props.onDeleteRow}>Delete</IButton>
                        </Col>
                    }
                    <Col xs="auto">
                        <div className="tab tab-icon">
                    <IButtonbar active={active}>
                        <IButtonbarItem>
                            <div className="btnbar" data-tab="EXCEL_VIEW"  onClick={() => this.changeTab('EXCEL_VIEW')}>
                                        <i className="icon ico-list-blue align-middle" />
                            </div>
                        </IButtonbarItem>
                        <IButtonbarItem>
                            <div className="btnbar" data-tab="NORMAL_VIEW"  onClick={() => this.changeTab('NORMAL_VIEW')}>
                                        <i className="icon ico-list-item align-middle" />
                            </div>
                        </IButtonbarItem>

                    </IButtonbar>
                   
                    
                    
            </div>
                    </Col>
                </div>
            </Fragment>
        )
    }
}
AddMailbagTabPanel.propTypes = {
    activeMailbagAddTab: PropTypes.string,
    changeAddMailbagTab: PropTypes.func,
    onDeleteRow: PropTypes.func,
    addRow: PropTypes.func
}