import React, { PureComponent } from 'react';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import PropTypes from 'prop-types';

export class MailbagDetailsTabPanel extends PureComponent {
    constructor(props) {
        super(props);

    }
    changeTab = (currentTab) => {
        this.props.changeDetailsTab(currentTab)
    }



    render() {
        let active = 0;
        if (this.props.activeMainTab === 'MAIL_VIEW') {
            active = 0;
        } else {
            active = 1;
        }
        return (
            <div className="tab">
                <IButtonbar active={active}>
                    <IButtonbarItem>
                        <div className="btnbar" data-tab="MAIL_VIEW" onClick={() => this.changeTab('MAIL_VIEW')}>Mailbags</div>
                    </IButtonbarItem>
                    <IButtonbarItem>
                        <div className="btnbar" data-tab="DSN_VIEW" onClick={() => this.changeTab('DSN_VIEW')}>DSN</div>
                    </IButtonbarItem>
                </IButtonbar>
                <div className="mar-r-md">
                    <a href="#" onClick={this.props.allMailbagIconAction}><i className="icon ico-maximize align-middle"></i></a>
                </div>
            </div>
        )
    }
}
MailbagDetailsTabPanel.propTypes = {
    changeDetailsTab:PropTypes.func,
    activeMainTab:PropTypes.string,
    allMailbagIconAction:PropTypes.func
}