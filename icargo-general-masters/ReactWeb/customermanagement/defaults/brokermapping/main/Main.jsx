import React from 'react';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config';
import store from '../store/store';

class MainPage extends React.Component {


    render() {
        return  <App/>;
 }
}

export default initapp({store,screenId: 'customermanagement.defaults.ux.brokermapping', screenAction: 'customermanagement/defaults/brokermapping', appConfig: config })(MainPage)


