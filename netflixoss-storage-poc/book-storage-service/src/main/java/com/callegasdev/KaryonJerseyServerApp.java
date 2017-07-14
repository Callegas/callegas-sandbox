package com.callegasdev;

import com.callegasdev.KaryonJerseyServerApp.KaryonJerseyModuleImpl;
import com.callegasdev.rest.BookService;
import com.netflix.governator.annotations.Modules;

import netflix.adminresources.resources.KaryonWebAdminModule;
import netflix.karyon.KaryonBootstrap;
import netflix.karyon.ShutdownModule;
import netflix.karyon.archaius.ArchaiusBootstrap;
import netflix.karyon.eureka.KaryonEurekaModule;
import netflix.karyon.jersey.blocking.KaryonJerseyModule;
import netflix.karyon.servo.KaryonServoModule;

@ArchaiusBootstrap
@KaryonBootstrap(name = "book-storage-service", healthcheck = HealthcheckResource.class)
@Modules(include = {
        ShutdownModule.class,
        KaryonWebAdminModule.class,
        KaryonServoModule.class,
        KaryonJerseyModuleImpl.class,
        KaryonEurekaModule.class
})
public interface KaryonJerseyServerApp {
    class KaryonJerseyModuleImpl extends KaryonJerseyModule {
        @Override
        protected void configureServer() {
            bind(BookService.class).asEagerSingleton();

            bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
            interceptorSupport().forUri("/*").intercept(LoggingInterceptor.class);
            interceptorSupport().forUri("/storage").interceptIn(AuthInterceptor.class);
            server().port(6001).threadPoolSize(400);
        }
    }
}
