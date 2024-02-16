import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';


const mapStateToProps = (state) => {
    return {
        
       
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        
    }
}

const DetailsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(AddScreeningButtonPanel)

export default DetailsContainer