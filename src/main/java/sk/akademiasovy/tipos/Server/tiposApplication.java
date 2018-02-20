package sk.akademiasovy.tipos.Server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import sk.akademiasovy.tipos.Server.resources.Login;

public class tiposApplication extends Application<tiposConfiguration> {

    public static void main(final String[] args) throws Exception {
        new tiposApplication().run(args);
    }

    @Override
    public String getName() {
        return "tipos";
    }

    @Override
    public void initialize(final Bootstrap<tiposConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final tiposConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(
                new Login()
        );
    }

}
