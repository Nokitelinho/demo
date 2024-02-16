import React, { useState,useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';

import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import Tooltip from '@material-ui/core/Tooltip';
import EditIcon from '@material-ui/icons/Edit';


import Typography from '@material-ui/core/Typography';

import Paper from '@material-ui/core/Paper';
import { getFromBEnd,NoListRow,asWebPath } from '../components/Common'; 
import  commonStyles,{ HeadingCell} from '../components/styles';

import { useHistory } from "react-router-dom";
import { useRecoilState } from 'recoil';
import tenantState from '../store/TenantAtom';

const useRowStyles = makeStyles({
  root: {
    '& > *': {
      borderBottom: 'unset',
    },
  },
});

function Row(props) {
  const { row,tenant } = props;
  const [open, setOpen] = React.useState(false);
  const [catalogoue, setCatalogue] = React.useState([]);
  const classes = useRowStyles();

  const onExpand = appId => {
    setOpen(!open);
    getFromBEnd(`/tenant/${tenant}/applications/${appId}/artefacts`)
      .then(setCatalogue);
  }
  const history = useHistory();
  return (
    <React.Fragment>
      <TableRow className={classes.root}>
        <TableCell>
          <IconButton aria-label="expand row" size="small" onClick={() => onExpand(row.applicationId)}>
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>         
        <TableCell component="th" scope="row">
          <Typography variant="h6" gutterBottom component="div">
                {row.applicationId}
          </Typography>          
        </TableCell>
        <TableCell align="left">{row.applicationDesc}</TableCell>
        <TableCell>
          <Tooltip title="Edit">  
            <IconButton aria-label="expand row" size="small"  onClick={()=>history.push(asWebPath(`/maintain-app/${row.applicationId}`))} >
              <EditIcon/>
            </IconButton>
          </Tooltip>
        </TableCell>
          
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box margin={1}>
              <Typography variant="h6" gutterBottom component="div">
                Artefact Catalogue:
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell>Artefact Id</TableCell>
                    <TableCell align="left">Domain</TableCell>
                    <TableCell align="left">Type</TableCell>
                    <TableCell align="left">Technology</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {catalogoue.map((artefact) => (
                    <TableRow key={artefact.artifactId}>
                      <TableCell component="th" scope="row">
                        {artefact.artifactId}
                      </TableCell>
                      <TableCell align="left">{artefact.domain}</TableCell>
                      <TableCell align="left">{artefact.serviceType}</TableCell>
                      <TableCell align="left">{artefact.serviceTechnology}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}


export default function ApplicationList() {
  const classes = commonStyles();

  const  [appList, setAppList] = useState([]);
  const [tenant, setTenant] = useRecoilState(tenantState);

  useEffect(() =>{
    getFromBEnd(`/tenant/${tenant.value}/applications`)
      .then(setAppList);
  },[]);


  return (
  <div className={classes.margin}>
  <TableContainer component={Paper}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <HeadingCell />
            <HeadingCell>Application Id</HeadingCell>
            <HeadingCell align="left">Description</HeadingCell>
            <HeadingCell />
            <HeadingCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {appList && appList.length>0?appList.map((row) => (
            <Row tenant={tenant.value} key={row.applicationId} row={row} />
          )):<NoListRow message={`No Applications for ${tenant.value}?`} />}
        </TableBody>
      </Table>
    </TableContainer>
    </div>
  );
}