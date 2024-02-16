import React, { Component } from 'react'
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import PropTypes from 'prop-types';
export class CarditsTableCustomHeaderPanel extends Component {
    constructor(props) {
        super(props)
        this.state = {
            openPopover: false
        }
    }
    changeCarditsTab = (currentTab) => {
        this.props.changeCarditsTab(currentTab)
    }
    onCloseQueueRemarks = () => {
        this.setState(() => {
            return {
                openPopover: false
            }
        })
    }
    onOpenQueueRemarks = () => {
        this.setState(() => {
            return {
                openPopover: true
            }
        })
    }
    onOKQueueRemarks = () => {
        let remarks = document.getElementById('remarks').value;
        this.props.onOKQueueRemarks(remarks);
        this.setState(() => {
            return {
                openPopover: false
            }
        })
    }
    reassignBooking = (event) =>{
        const key = event.target.dataset.key;
        this.props.reassignBooking(key);
    }
    
    render() {
        let active = 0;
        if (this.props.activeCarditTab === 'GROUP') {
            active = 0;
        } else  {
            active = 1;
        } 
        return (
            <div className="row">
                <div className="col col-sm-11">
                    <div className="btnbar-normal">
                        <IButtonbar size="md" active={active} >

                            <IButtonbarItem >
                                <div className="btnbar" onClick={() => this.changeCarditsTab('GROUP')}>
                                    <div className="fs12">Group View </div>
                                </div>
                            </IButtonbarItem>
                            <IButtonbarItem >
                                <div className="btnbar" onClick={() => this.changeCarditsTab('LIST')}>
                                    <div className="fs12">List View</div>
                                </div>
                            </IButtonbarItem>
                            
                        </IButtonbar>
                    </div>
                </div>
              

            </div>
        )
    }
}
CarditsTableCustomHeaderPanel.propTypes = {
    changeCarditsTab: PropTypes.func,
    onOKQueueRemarks: PropTypes.func,
    reassignBooking: PropTypes.func,
    activeCarditTab: PropTypes.string
}
