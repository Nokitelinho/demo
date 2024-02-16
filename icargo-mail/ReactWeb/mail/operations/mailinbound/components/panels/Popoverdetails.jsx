import React from 'react'
import { Manager, Target, Popper } from 'react-popper';
import PropTypes from 'prop-types'

export default class PopoverDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isOpen: false
        }
    }
    togglePanel=()=> {
        this.setState({ isOpen: !this.state.isOpen })
    }
    render() {
        return (
            <Manager>
                <Target >
                    <a onClick={this.togglePanel}>{(this.state.isOpen) ? <i className="icon ico-minus-round align-middle"></i> : <i className="icon ico-plus-round align-middle"></i>}</a>
                </Target>
                {(this.state.isOpen) ? <Popper class="animated fadeIn" placement="bottom-start" style={{ width: '100%', top: '10px', zIndex: 1 }}>
                    {this.props.children}
                </Popper> : ''}

            </Manager>

        )
    }
}
PopoverDetails.propTypes = {
    height: PropTypes.string,
    width: PropTypes.string
}