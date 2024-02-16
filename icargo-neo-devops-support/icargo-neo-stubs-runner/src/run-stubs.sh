#!/usr/bin/env bash

CURRDIR=`echo $0 | awk '$0 ~ /^\// { print }'`
if [[ ${CURRDIR} != "" ]]; then
  CURRDIR=`dirname $0`
else
  CURRDIR="`pwd``dirname $0 | cut -c2-`"
fi

typeset -r STUBS_CONF="${CURRDIR}/etc/stubs-runner.conf"
# The version of the stubs runner to use @See Dockerfile as well for dependent reference
typeset -r SPRING_BOOT_STUB_RUNNER="org.springframework.cloud:spring-cloud-contract-stub-runner-boot:2.2.6.RELEASE:jar"

# Some temporary files
typeset -r STUBS_LIB="${CURRDIR}/var/stubs"
typeset -r STUBS_OK="${CURRDIR}/var/stubs-success.txt"

# Compiles and add custom classes to the spring boot jar,
# A hack to avoid setting up a full spring boot project for trivial config changes.
buildCustomizationJar(){
  local DIST="${CURRDIR}/var/dist"
  local BLIB="${CURRDIR}/var/build-lib"
  [[ -d ${DIST} ]] && rm -r "${DIST}"
  [[ -d ${BLIB} ]] && rm -r "${BLIB}"
  mkdir -p "${DIST}" "${BLIB}"
  unzip -q -d "${BLIB}" "${CURRDIR}/spring-cloud-contract-stub-runner-boot-*.jar"
  javac --class-path "${BLIB}/BOOT-INF/lib/*" -d "${DIST}" ${CURRDIR}/java/com/ibsplc/neoicargo/stubs/runner/*.java
  mv ${DIST}/* "${BLIB}/BOOT-INF/classes/"
  cd ${BLIB} && jar --create --no-compress --no-manifest --file "${STUBS_LIB}/icargo-neo-stubs-runner.jar" .
  cd "${CURRDIR}"
}

# Download all stubs to the lib folder
downloadAllStubs(){
  # Clean the temp files and folders
  [[ -e ${STUBS_OK} ]] && rm "${STUBS_OK}"
  [[ -d ${STUBS_LIB} ]] && rm -r "${STUBS_LIB}"
  mkdir -p "${STUBS_LIB}"

  while read LINE; do
    # Filter out empty line and lines starting with #
    if [[ -n ${LINE} && ! ${LINE} = \#* ]]; then
      STUB_RES=$( echo ${LINE} | awk 'BEGIN { FS=":" } { print $1":"$2":"$3 }' )
      STUB_CLS=$( echo ${LINE} | awk 'BEGIN { FS=":" } { print $4 }' )
      typeset -i ANS=${?}
      if [[ ${ANS} -eq 0 && -n ${STUB_CLS} ]]; then
        echo "INFO Downloading stub from maven repo ${STUB_RES}"
        mvn --quiet --batch-mode dependency:copy -Dsilent=true -Dtransitive=false -Dartifact="${STUB_RES}:jar:${STUB_CLS}" -DoutputDirectory="${STUBS_LIB}"
        typeset -i ANS=${?}
        [[ ${ANS} -ne 0 ]] && echo "ERROR Unable to download stub ${LINE}" || echo ${LINE} >> ${STUBS_OK}
      else
        echo "ERROR Invalid entry in ${STUBS_CONF} - ${LINE}"
      fi
    fi
  done < ${STUBS_CONF}

  # Download the stub runner
  echo "INFO Downloading stub runner ${SPRING_BOOT_STUB_RUNNER}"
  mvn --batch-mode --quiet dependency:copy -Dsilent=true -Dtransitive=false -Dartifact="${SPRING_BOOT_STUB_RUNNER}" -DoutputDirectory="${CURRDIR}"
}

# Fire up the stub runner
starStubRunner(){
  # setup the classpath
  STUBS_CP=""
  for JAR in $(find ${STUBS_LIB} -type f -name '*.jar' -print ); do
    echo "INFO Adding Jar : ${JAR}"
    [[ -z ${STUBS_CP} ]] && STUBS_CP="${JAR}" || STUBS_CP="${STUBS_CP}:${JAR}"
  done
  # aggregate the subs runner ids
  STUBS_IDS=""
  while read LINE; do
    if [[ -n ${LINE} ]]; then
      [[ -z ${STUBS_IDS} ]] && STUBS_IDS="${LINE}" || STUBS_IDS="${STUBS_IDS},${LINE}"
    fi
  done < ${STUBS_OK}
  java -Xverify:none -cp "${STUBS_CP}" org.springframework.boot.loader.JarLauncher --stubrunner.stubs-mode=CLASSPATH --stubrunner.ids="${STUBS_IDS}" --stubrunner.workOffline=true --stubrunner.http-server-stub-configurer=com.ibsplc.neoicargo.stubs.runner.LeanMeanWiremockConfiguration --server.port=5000 --server.tomcat.threads.max=2 --server.tomcat.threads.min-spare=2 --logging.level.WireMock=WARN --spring.jmx.enabled=false
}

# Tool options are intended for the main process only and should not be picked by other java process like mvn
if [[ -n ${JAVA_TOOL_OPTIONS} ]]; then
  JAVA_TOOL_OPTIONS_BKUP="${JAVA_TOOL_OPTIONS}"
  unset JAVA_TOOL_OPTIONS
fi

# Main Block
downloadAllStubs
buildCustomizationJar
# Reset the tool options
[[ -n ${JAVA_TOOL_OPTIONS_BKUP} ]] && export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS_BKUP}"
starStubRunner

