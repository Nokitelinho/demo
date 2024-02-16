package com.ibsplc.neoicargo.devops.utils.ecrsync;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */
public class ECRSyncCli {

    static boolean VERBOSE = false;


    private static void printHelpAndReturn(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ecrsync", options);
        System.exit(1);
    }

    private static void valAndsetOption(Options options, CommandLine cmd, String opt, Consumer<String> setter, boolean optional) {
        if (cmd.hasOption(opt)) {
            setter.accept(cmd.getOptionValue(opt));
        } else {
            if (!optional) {
                printHelpAndReturn(options);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addRequiredOption("s", "source", true, "Source ECR Repository URL. E.g 141807520248.dkr.ecr.ap-south-1.amazonaws.com");
        options.addRequiredOption("t", "target", true, "Target ECR Repository URL. E.g 612067252072.dkr.ecr.ap-southeast-2.amazonaws.com");
        options.addRequiredOption("f", "bom", true, "Bom File yaml. E.g ./bom.yaml");
        options.addOption("sfx", "sourcePfx", true, "Prefix to be added to source Image.Will be appended with a trailing/. E.g neoicargo");
        options.addOption("tfx", "targetPfx", true, "Prefix to be added to target Image.Will be appended with a trailing/. E.g neoicargo");
        options.addOption("tifx", "targetImagePfx", true, "Prefix to be added to target Image Name.");
        options.addOption("v", "verbose", false, "Turn on verbose logging");
        options.addOption("d", "debug", false, "Simulation. Does not do the actual sync");
        options.addOption("n", "no-delete", false, "Never delete target image event if it exists");
        CommandLineParser parser = new DefaultParser();
        int exitc = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("v")) {
                VERBOSE = true;
            }
            CLIArgs cliArgs = new CLIArgs();

            if (cmd.hasOption("d")) {
                cliArgs.setSimulation(true);
            }
            if (cmd.hasOption("n")) {
                cliArgs.setDoNotDelete(true);
            }
            valAndsetOption(options, cmd, "s", cliArgs::setSource, false);
            valAndsetOption(options, cmd, "t", cliArgs::setTarget, false);
            valAndsetOption(options, cmd, "f", cliArgs::setBomFile, false);
            valAndsetOption(options, cmd, "sfx", cliArgs::setSourcePrefix, true);
            valAndsetOption(options, cmd, "tfx", cliArgs::setTargetPrefix, true);
            valAndsetOption(options, cmd, "tifx", cliArgs::setTargetImagePrefix, true);
            DoSync doSync = new DoSync(cliArgs.getBomFile(), new JibEcrSync(cliArgs));
            exitc = doSync.doIt()?0:-1;

        } catch (ParseException | IOException exp) {
            System.err.println(exp.getMessage());
            printHelpAndReturn(options);
            exitc = 1;

        }
        System.exit(exitc);
    }
}
