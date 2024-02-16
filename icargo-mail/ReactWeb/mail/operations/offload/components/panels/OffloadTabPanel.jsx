
import React from 'react';
import { Row, Col,Container } from "reactstrap";
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
const Aux =(props) =>props.children;
export default class OffloadTabPanel extends React.Component {
    constructor(props) {
        super(props);
        this.toggleFilter = this.toggleFilter.bind(this);


    }

    changeTab = (currentTab) => {
        this.props.changeOffloadTab(currentTab,this.props.containerdetails);   
    }
    toggleFilter(){
        this.props.toggleFilter((this.props.screenMode === 'edit')?'display':'edit');
    }
    render() {
        let active = 0;
        if (this.props.activeOffloadTab === 'CONTAINER_VIEW') {
            active = 0;
        }else if (this.props.activeOffloadTab === 'MAIL_VIEW') {
            active = 1;
        } 
        else if (this.props.activeOffloadTab === 'DSN_VIEW') {
            active = 2;
        } 
        else  {
            active = 0;
        }
        return (
         

        
            <div className="header-filter-panel flippane">
               

               
            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
                <div>
            {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
             </div>
             ) : (
            <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleFilter}></i>)}
            

                
                <div className="tab tab-sm">
                    <IButtonbar active={active} manualActiveIndex>
                    {console.log('active=',active)}
                        <IButtonbarItem>
                            <div className="btnbar" data-tab="CONTAINER_VIEW" onClick={() => this.changeTab('CONTAINER_VIEW')}>Container</div>
                        </IButtonbarItem>
                        <IButtonbarItem>
                            <div className="btnbar" data-tab="MAIL_VIEW" onClick={() => this.changeTab('MAIL_VIEW')}>Mail</div>
                        </IButtonbarItem>
                        <IButtonbarItem>
                            <div className="btnbar" data-tab="DSN_VIEW" onClick={() => this.changeTab('DSN_VIEW')}>DSN</div>
                        </IButtonbarItem>

                    </IButtonbar>
                  
                    
                    
                    </div>
                    </div>
                 
                   
         
        )
    }
}