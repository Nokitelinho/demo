import Box from '@material-ui/core/Box';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import Table from '@material-ui/core/Table';
import moment from 'moment';
import React from 'react';


export default function BuildCatalogue(props) {
  const { artifacts,description } = props;

    return (
        <Box margin={1}>
            <Typography variant="h6" gutterBottom component="div">
            {description}
            </Typography>
            <Table size="small" aria-label="purchases">
            <TableHead>
                <TableRow>
                <TableCell>Artefact Id</TableCell>
                <TableCell align="right">Version</TableCell>
                <TableCell align="right">Released Date</TableCell>
                <TableCell align="right">Commit Desc.</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {artifacts.map((artefact) => (
                <TableRow key={artefact.artifactId}>
                    <TableCell component="th" scope="row">
                    {artefact.artifactId}
                    </TableCell>
                    <TableCell align="right">{artefact.artifactVersion}</TableCell>
                    <TableCell align="right">{moment(artefact.releaseTime,moment.ISO_8601).format("LLLL")}</TableCell>
                    <TableCell align="right">{artefact.description}</TableCell>                    
                </TableRow>
                ))}
            </TableBody>
            </Table>
        </Box>
    )

}