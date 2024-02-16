import React from 'react'
import { Manager, Target, Popper } from 'react-popper';
import PropTypes from 'prop-types'

export default class PopoverDetails extends React.Component {

    constructor(props) {
        super(props);
       // this.togglePanel = this.togglePanel.bind(this);
        this.state = {
            isOpen: false
        }
    }
    togglePanel=()=> {
        this.setState({ isOpen: !this.state.isOpen })
    }
    render() {
       // const { height, width } = this.props;
        return (
            <Manager>
                <Target >
                    <a onClick={this.togglePanel}>{(this.state.isOpen) ? <i className="icon ico-minus-round align-middle"></i> : <i className="icon ico-plus-round align-middle"></i>}</a>
                </Target>
                {(this.state.isOpen) ? <Popper placement="bottom-right" style={{ zIndex: 9999 }}>
                    {this.props.children}
                </Popper> : ''}

            </Manager>

        )
    }
}
PopoverDetails.propTypes = {
    togglePanel: PropTypes.func
}