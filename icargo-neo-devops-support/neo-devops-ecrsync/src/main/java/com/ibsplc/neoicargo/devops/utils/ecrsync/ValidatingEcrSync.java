package com.ibsplc.neoicargo.devops.utils.ecrsync;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ecr.EcrClient;
import software.amazon.awssdk.services.ecr.model.*;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 08, 2021 	  Binu K			First draft
 */
public abstract class ValidatingEcrSync implements EcrSync {

    private static final Pattern ECR_REGISTRY_FQ_NAME_PATTERN = Pattern.compile("(?<registry>[0-9]{12})\\.\\p{ASCII}{3}\\.\\p{ASCII}{3}.(?<region>\\p{ASCII}+)\\.\\p{ASCII}+\\.\\p{ASCII}+");
    protected CLIArgs cliArgs;
    protected EcrClient ecrClientSource;
    protected EcrClient ecrClientTarget;
    private String sourceRegistryId;
    private String targetRegistryId;

    public ValidatingEcrSync(CLIArgs cliArgs) {
        this.cliArgs = cliArgs;
        var matcher = ECR_REGISTRY_FQ_NAME_PATTERN.matcher(cliArgs.getSource());
        if (matcher.matches()) {
            this.ecrClientSource = EcrClient.builder().region(Region.of(matcher.group("region"))).build();
            this.sourceRegistryId = matcher.group("registry");
        }
        matcher = ECR_REGISTRY_FQ_NAME_PATTERN.matcher(cliArgs.getTarget());
        if (matcher.matches()) {
            this.ecrClientTarget = EcrClient.builder().region(Region.of(matcher.group("region"))).build();
            this.targetRegistryId = matcher.group("registry");
        }
    }

    static <T> Supplier<T> executeIgnoringECRErrors(Supplier<T> function, Supplier<T> defautlVal) {
        return () -> {
            try {
                return function.get();
            } catch (EcrException ecrException) {
                System.err.printf("Could not invoke SNS APIs %s%n", ecrException.getMessage());
            }
            return defautlVal.get();
        };
    }

    protected boolean imageExistsAtRepoWithTag(EcrClient ecrClient, String registryId, String repository, String imageTag) {
        var describeImageRequest = DescribeImagesRequest.builder().registryId(registryId).repositoryName(repository).imageIds(ImageIdentifier.builder().imageTag(imageTag).build()).build();
        return executeIgnoringECRErrors(() -> ecrClient.describeImages(describeImageRequest).hasImageDetails(), () -> false).get();
    }

    protected boolean sourceExists(ImageEntry imageEntry) {
        return imageExistsAtRepoWithTag(this.ecrClientSource, sourceRegistryId, imageEntry.getImage(cliArgs.getSourcePrefix()), imageEntry.getImageTag());
    }

    protected boolean targetExists(ImageEntry imageEntry) {
        return imageExistsAtRepoWithTag(this.ecrClientTarget, targetRegistryId, imageEntry.getImage(cliArgs.getTargetPrefix(), cliArgs.getTargetImagePrefix()), imageEntry.getImageTag());
    }

    protected boolean deleteImageAtRepoWithTag(EcrClient ecrClient, String registryId, String repository, String imageTag) {
        var deleteImageRequest = BatchDeleteImageRequest.builder().registryId(registryId)
                .repositoryName(repository).imageIds(ImageIdentifier.builder().imageTag(imageTag).build()).build();
        return (boolean) executeIgnoringECRErrors(() -> {
            var response = ecrClient.batchDeleteImage(deleteImageRequest);
            var images = response.imageIds();
            return images != null && images.size() > 0;
        }, Boolean.FALSE::booleanValue).get();
    }


    @Override
    public boolean sync(ImageEntry imageEntry) {
        //Source exists check don't work when this is run on the AWS Code-pipeline with IAM roles.Not sure why.
        //Hence commented out
        //Optional<Image> source = sourceExists(imageEntry);
        Optional<Image> target = Optional.empty() ;//targetExists(imageEntry);
        boolean targetDeleted = false;
        if (target.isPresent() && !cliArgs.isSimulation()) {
            targetDeleted = !cliArgs.isDoNotDelete() && deleteImageAtRepoWithTag(this.ecrClientTarget, targetRegistryId, imageEntry.getImage(cliArgs.getTargetPrefix()), imageEntry.getImageTag());
        }
        System.out.printf("Image %s with tag %s exists : at target (%s): target deleted(%s) %n", imageEntry.getImage(), imageEntry.getImageTag(), target.isPresent(), targetDeleted);
        return true;
    }

}
