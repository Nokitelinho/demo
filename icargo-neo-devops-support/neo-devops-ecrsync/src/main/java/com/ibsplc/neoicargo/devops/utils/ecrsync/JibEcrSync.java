package com.ibsplc.neoicargo.devops.utils.ecrsync;

import com.google.cloud.tools.jib.api.*;
import com.google.cloud.tools.jib.frontend.CredentialRetrieverFactory;
import com.google.cloud.tools.jib.registry.credentials.CredentialRetrievalException;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */
class JibEcrSync extends ValidatingEcrSync {

    private Optional<Credential> sourceCred;
    private Optional<Credential> targetCred;

    Consumer<LogEvent> logger = (LogEvent logEvent) -> {
        if (ECRSyncCli.VERBOSE) {
            System.out.printf("<%s><%s>%n", logEvent.getLevel(), logEvent.getMessage());
        }
    };

    public JibEcrSync(CLIArgs cliArgs) {
        super(cliArgs);
    }

    @Override
    public boolean sync(ImageEntry imageEntry) {
        var sourceImage = imageEntry.getImage(cliArgs.getSourcePrefix());
        var targetImage = imageEntry.getImage(cliArgs.getTargetPrefix(), cliArgs.getTargetImagePrefix());
        if (super.sync(imageEntry)) {
            try {
                System.out.printf("Image Transfer from %s/%s:%s to %s/%s:%s%n", cliArgs.getSource(), sourceImage, imageEntry.getImageTag(), cliArgs.getTarget(), targetImage, imageEntry.getImageTag());
                if (!cliArgs.isSimulation()) {
                    var fromImage = ImageReference.of(cliArgs.getSource(), sourceImage, imageEntry.getImageTag());
                    var fromImageCredRetriverFactory = CredentialRetrieverFactory.forImage(fromImage, logger);
                    var toImage = ImageReference.of(cliArgs.getTarget(), targetImage, imageEntry.getImageTag());
                    var toImageCredRetriverFactory = CredentialRetrieverFactory.forImage(toImage, logger);
                    boolean fromHarbor = cliArgs.getSource().contains("harbor");
                    boolean toHarbor = cliArgs.getTarget().contains("harbor");
                    boolean targetExists = !toHarbor && targetExists(imageEntry);
                    if(targetExists) {
                        System.out.printf("Image exists at target, skipping sync...%n");
                    } else {
                        Jib.from(RegistryImage.named(fromImage)
                                        .addCredentialRetriever(new CachedCredentialRetriever(fromHarbor ? fromImageCredRetriverFactory.dockerConfig() : fromImageCredRetriverFactory.wellKnownCredentialHelpers(), true)))
                                .containerize(Containerizer.to(RegistryImage.named(toImage)
                                                .addCredentialRetriever(new CachedCredentialRetriever(toHarbor ? toImageCredRetriverFactory.dockerConfig() : toImageCredRetriverFactory.wellKnownCredentialHelpers(), false)))
                                        /*.addCredentialRetriever(toImageCredRetriverFactory.dockerConfig())*/);
                    }
                }

            } catch (Exception e) {
                System.err.printf("Sync of %s failed due to %s%n", imageEntry, e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.printf("Image %s/%s:%s NOT synced to %s/%s:%s - as source does not exist.%n",
                    cliArgs.getSource(), sourceImage, imageEntry.getImageTag(),
                    cliArgs.getTarget(), targetImage, imageEntry.getImageTag());
        }
        return true;
    }

    class CachedCredentialRetriever implements CredentialRetriever {
        private final CredentialRetriever delegate;
        private final boolean source;

        public CachedCredentialRetriever(CredentialRetriever delegate, boolean source) {
            this.delegate = delegate;
            this.source = source;
        }

        @Override
        public Optional<Credential> retrieve() throws CredentialRetrievalException {
            Optional<Credential> answer;
            if(this.source){
                answer = sourceCred == null ? delegate.retrieve() : sourceCred;
                sourceCred = answer;
            } else {
                answer = targetCred == null ? delegate.retrieve() : targetCred;
                targetCred = answer;
            }
            return answer;
        }
    }

}
