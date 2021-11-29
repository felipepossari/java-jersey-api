package com.felipepossari.jersey.configuration;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.DuplicatePostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AutoScanFeatureConfig implements Feature {

    private static final Logger log = LoggerFactory.getLogger(AutoScanFeatureConfig.class);

    private final ServiceLocator serviceLocator;

    @Inject
    public AutoScanFeatureConfig(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @Override
    public boolean configure(FeatureContext featureContext) {
        log.info("Configuring auto scan feature");
        DynamicConfigurationService dynamicConfigurationService =
                serviceLocator.getService(DynamicConfigurationService.class);
        Populator populator = dynamicConfigurationService.getPopulator();

        try {
            populator.populate(
                    new ClasspathDescriptorFileFinder(this.getClass().getClassLoader()),
                    new DuplicatePostProcessor());
        } catch (IOException e) {
            log.error("Auto scan feature populate failed", e);
        }

        return true;
    }
}
