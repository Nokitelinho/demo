import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { CarditsTableCustomHeaderPanel } from '../panels/carditlyinglistpanel/CarditsTableCustomHeaderPanel.jsx';


const mapStateToProps = (state) => {
    return {
        activeCarditTab: state.carditReducer.activeCarditTab

    }
}

const mapDispatchToProps = (dispatch) => {
    return {
      
        changeCarditsTab: (currentTab) => {
            dispatch({ type: 'CHANGE_CARDITS_TAB', data: currentTab })
        }
       
    }
}

const CarditsCustomHeaderContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(CarditsTableCustomHeaderPanel)

export default CarditsCustomHeaderContainer