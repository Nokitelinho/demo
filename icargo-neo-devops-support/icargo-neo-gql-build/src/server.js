'use strict'
/*
* @author Jens
* A node js script to generate java client bindings from graphql schema.
*/
require("dotenv").config();
const stringify = require("json-stringify-pretty-compact");
const path = require("path");
const fs = require("fs");
const fetch = require("node-fetch");
const express = require("express");
const Handlebars = require("handlebars");
const { spawn } = require("child_process");
const crypto = require("crypto");

const {
    getIntrospectionQuery,
    printSchema,
    buildClientSchema,
    TokenKind
} = require("graphql");

// initialization code
const app = express();
app.use(express.json())
const pomTemplate = Handlebars.compile(readConfigFile("pom.xml.hbs"));
const BUILD_DB = {}; // a dictionary to hold the status of the builds

// A compound logger which multiplexes to console as well as http response 
class HttpLogger {
    constructor(service, response) {
        this.response = response;
        this.service = service;
        response.setHeader("Content-Type", "text/plain");
        response.statusCode = 200;
    }

    log(data) {
        this.response.write(data + "\n");
        console.log(this.service + " - " + data);
    }

}

/**
 * Function to export GraphQL schema from a running server to a a file.
 * @param serviceUrl - the graphQL url
 * @param targetDir - the directory in the filesystem where the schema needs to be written
 * @param serviceName - the service name which prefixes the schema dsl and introspection json
 * @returns {Promise<void>}
 */
async function exportGraphQLSchema(serviceUrl, targetDir, serviceName, logger) {
    const introspectionQuery = getIntrospectionQuery();
    const { data } = await fetch(
        serviceUrl,
        {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query: introspectionQuery })
        }
    ).then(response => response.json().catch(err => { logger.log(err); throw err }))
        .catch(err => { logger.log(err); throw err });
    logger.log("Processing schema response ...");
    //const {data} = await response.json();
    const schema = buildClientSchema(data);
    const schemaDsl = printSchema(schema);
    // create a schema hash to prevent duplicate builds, if the schema is not changed
    let oldHash = BUILD_DB[`${serviceName}.schemaHash`];
    let newHash = crypto.createHash('sha1').update(schemaDsl).digest('hex');
    logger.log(`${serviceName} schema hashOld ${oldHash} hashNew ${newHash}`)
    if (oldHash == newHash)
        return false;
    BUILD_DB[`${serviceName}.schemaHash`] = newHash;
    const outputFile = path.join(targetDir, "/" + serviceName + "_schema_dsl.gql");
    logger.log(`Writing schema DSL to location ${outputFile}`)
    await fs.promises.writeFile(outputFile, schemaDsl);
    const outputFileJson = path.join(targetDir, "/" + serviceName + "_schema_introspection.json");
    logger.log(`Writing schema introspection json to ${outputFileJson}`);
    await fs.promises.writeFile(outputFileJson, stringify(data));
    return true;
}

/**
 * Function to generate the maven project structure for the artifact
 * @param artifact
 * @param rootDir
 * @param logger
 */
function generatePomProject(artifact, rootDir, logger) {
    logger.log(`Generating mvn project ${rootDir} ...`)
    fs.mkdirSync(path.join(rootDir, "/", "src/main/java"), { recursive: true }, (err) => {
        if (err) {
            logger.log(err)
            throw err;
        }
    });

    fs.mkdirSync(path.join(rootDir, "/", "src/main/resources/schema"), { recursive: true }, (err) => {
        if (err) {
            logger.log(err)
            throw err;
        }
    });
    artifact.packageName = resolvePackageName(artifact, logger);
    artifact.schemaDslName = `${artifact.artifactId}_schema_dsl.gql`;
    artifact.apiPrefix = resolveTypePrefix(artifact, logger);
    writeClientProperties(artifact, path.join(rootDir, "/", "src/main/resources"));
    let pomXml = pomTemplate(artifact);
    fs.writeFileSync(path.join(rootDir, "pom.xml"), pomXml);
}

/**
 * Stores the property file gql-client.properties into the generated jar. This is to aid tool support.
 * @param artifact
 * @param resDir
 */
function writeClientProperties(artifact, resDir) {
    let prop = "";
    for (let [key, val] of Object.entries(artifact)) {
        prop += `${key}=${val}\n`;
    }
    prop += `query.api=${artifact.packageName}.api.${artifact.apiPrefix}QueryApi\n`;
    prop += `mutation.api=${artifact.packageName}.api.${artifact.apiPrefix}MutationApi\n`;
    fs.writeFileSync(path.join(resDir, "gql-client.properties"), prop);
}

function resolveTypePrefix(artifact, logger) {
    let splts = artifact.description.split(' ');
    for (let tkn of splts) {
        if (tkn.startsWith("name:"))
            return tkn.substring("name:".length);
    }
    logger.log("No name: information found in the artifact description.");
    return artifact.serviceType == "WEB_BFF" ? "Bff" : "Gw";
}

/**
 * artmst domain format module.submodule
 * @param {*} artifact 
 * @param {*} logger 
 * @returns 
 */
function resolvePackageName(artifact, logger) {
    return `com.ibsplc.neoicargo.gql.${artifact.domain}`;
    //let pattern = /[-_.]/; // split the words in - _ .
    //let type = artifact.serviceType.includes('_') ? artifact.serviceType.split('_')[1] : artifact.serviceType;
    //for (let tkn of artifact.artifactId.split(pattern)) {
    //    if (tkn == 'neo' || tkn == 'icargo' || tkn == 'bff' || tkn == 'gw')
    //        continue;
    //    let packageName = `com.ibsplc.neoicargo.gql.${type}.${tkn}`.toLowerCase();
    //    logger.log("Package name : " + packageName);
    //    return packageName;
    //}
    //// artifacts are expected to follow a naming style (icargo|neo)-name-(bff|gw)
    //return `com.ibsplc.neoicargo.gql.${type}.unknown`.toLowerCase();
}

function readConfigFile(fileName) {
    let fileNameAbs = path.join(__dirname, "/data/", fileName);
    return fs.readFileSync(fileNameAbs, { encoding: "utf-8" });
}

async function doBuild(artifactRelease, graphQLHostPort, res, logger) {
    let graphQLUrl = `http://${graphQLHostPort}${artifactRelease.contextPath}`
    let projectDir = path.join(__dirname, "/var/", artifactRelease.artifactId);
    generatePomProject(artifactRelease, projectDir, logger);
    let schemaChanged = await exportGraphQLSchema(graphQLUrl, path.join(projectDir, "/src/main/resources/schema"), artifactRelease.artifactId, logger);
    let previousBuildSuccess = BUILD_DB[`${artifactRelease.artifactId}.status`];
    // build if the schema has changed
    if (previousBuildSuccess && !schemaChanged) {
        res.write("Schema has not been modified, build skipped.");
        res.end();
        return;
    }
    // fireup the maven process
    logger.log("\n |---< Starting Maven Child Process >---| \n");
    let execOptions = { cwd: projectDir, env: process.env };
    let mvn = spawn("mvn", ["--batch-mode", "clean", "deploy"], execOptions);
    mvn.stdout.on("data", (data) => res.write(data));
    mvn.stderr.on("data", (data) => res.write(data));
    mvn.on("close", exitCode => {
        if (exitCode == 0)
            BUILD_DB[`${artifactRelease.artifactId}.status`] = true;
        else
            BUILD_DB[`${artifactRelease.artifactId}.status`] = false;
        logger.log(`maven build process for ${artifactRelease.artifactId} exited with code ${exitCode}`);
        res.end();
    });
}

async function resolveArtifactRelease(artifactId, logger) {
    let getUrl = `http://${process.env.RELEASE_MGR_URL}/release-manager-service/rest/api/artifact/${artifactId}`
    logger.log(`Fetching artifact details from ${getUrl}...`);
    const data = await fetch(
        getUrl, {
        method: "GET",
        headers: { "Accept": "application/json" },
    }
    ).then(response => response.json().catch(err => { logger.log(err); throw err }))
        .catch(err => { logger.log(err); throw err });
    return data;
}

/**
 * Api for the build, need to pass the artifactId string as argument. 
 * The function resolve the artifact details by consulting the release manager.
 */
app.post("/build/:artifactId", async (req, res) => {
    try {
        let artifactId = req.params.artifactId;
        let logger = new HttpLogger(artifactId, res);
        let artifactRelease = await resolveArtifactRelease(artifactId, logger);
        let graphQLServiceUrl = req.query.graphQLServiceUrl;
        if (!graphQLServiceUrl)
            graphQLServiceUrl = process.env.GRAPHQL_SERVICE_URL;
        await doBuild(artifactRelease, graphQLServiceUrl, res, logger)
    } catch (err) {
        console.error(err);
        res.write(err.stack)
        res.end();
    }
});

/**
 * Api for build while running inside kubernetes. The target url of the graphql service
 * is resolved from the namespace and artifactId.
 */
app.post("/k8s-build/:namespace/:artifactId", async (req, res) => {
    try {
        let artifactId = req.params.artifactId;
        let logger = new HttpLogger(artifactId, res);
        let artifactRelease = await resolveArtifactRelease(artifactId, logger);
        let ns = req.params.namespace;
        await doBuild(artifactRelease, `${artifactRelease.artifactId}.${ns}.svc.cluster.local`, res, logger)
    } catch (err) {
        console.error(err);
        res.write(err.stack)
        res.end();
    }
});

/**
 * Status get endpoint, to fetch the status of the build.
 */
app.get("/status/:artifactId", (req, res) => {
    let artifactId = req.params.artifactId;
    let buildOutput = { status: BUILD_DB[`${artifactId}.status`], schemaHash: BUILD_DB[`${artifactId}.schemaHash`] };
    res.statusCode = buildOutput.status ? 200 : 555;
    res.json(buildOutput);
    res.end();
});

/**
 * Health endpoint.
 */
app.get("/health", (req, res) => {
    res.write("UP");
    res.end();
})


// Main block starts here
const PORT = process.env.NODE_PORT || 5080;
app.listen(PORT, () => {
    console.log("Build server started on port " + PORT)
})
