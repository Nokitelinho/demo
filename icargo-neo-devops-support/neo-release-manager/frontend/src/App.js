import React from 'react';
import MainApp from './containers/MainApp'
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';
import green from '@material-ui/core/colors/green';
import lightGreen from '@material-ui/core/colors/lightGreen';

import {
  RecoilRoot
} from 'recoil'

const theme = createMuiTheme({
    palette: {
      primary: green,
      secondary: lightGreen,
    },
});

function App() {
  return (
    <RecoilRoot>
    <ThemeProvider theme={theme}>
      <MainApp/>
    </ThemeProvider>  
    </RecoilRoot> 
  );
}

export default App;
