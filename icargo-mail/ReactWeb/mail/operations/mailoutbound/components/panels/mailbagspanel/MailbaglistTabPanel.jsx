import React, { PureComponent } from 'react';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import PropTypes from 'prop-types';
export class MailbaglistTabPanel extends PureComponent {
    constructor(props) {
        super(props)
    }
    changeTab = (currentTab) => {
        this.props.changeDetailsTab(currentTab)
    }
    render() {
        let active = 0;
        if (this.props.activeMainTab === 'CARDIT_LIST') {
            active = 0;
        } else  {
            active = 1;
        }
        return (
            <div className="segment-summary-table border-bottom">
                <div className="w-100">
                    <IButtonbar size="md" active={active}>
                        <IButtonbarItem>
                            <div className="btnbar" data-tab="CARDIT_LIST" onClick={()=>this.changeTab('CARDIT_LIST')}>
                                Cardit List
                            </div>
                        </IButtonbarItem>
                        <IButtonbarItem>
                            <div className="btnbar" data-tab="LYING_LIST" onClick={()=>this.changeTab('LYING_LIST')}>
                             Lying list
                            </div>
                        </IButtonbarItem>
                      
                    </IButtonbar>
                </div>
            </div>
        );
    }
}
MailbaglistTabPanel.propTypes = {
    changeDetailsTab:PropTypes.func,
    activeMainTab:PropTypes.string
}