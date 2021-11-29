package com.felipepossari.jersey.configuration;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.DuplicatePostProcessor;

import java.io.IOException;
import java.util.logging.Logger;

public class AutoScanFeatureConfig implements Feature {

    private static final Logger log = Logger.getLogger(AutoScanFeatureConfig.class.getName());

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
            log.severe("Auto scan feature populate failed. " + e.getMessage());
        }

        return true;
    }
}
