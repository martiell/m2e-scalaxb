package org.scalaxb.m2e;

import org.apache.maven.plugin.MojoExecution;
import org.eclipse.m2e.core.lifecyclemapping.model.IPluginExecutionMetadata;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.AbstractBuildParticipant;
import org.eclipse.m2e.jdt.AbstractJavaProjectConfigurator;
import org.eclipse.m2e.jdt.IJavaProjectConfigurator;

public class ScalaxbProjectConfigurator extends AbstractJavaProjectConfigurator
        implements IJavaProjectConfigurator {

    @Override
    public AbstractBuildParticipant getBuildParticipant(
            IMavenProjectFacade projectFacade,
            MojoExecution execution,
            IPluginExecutionMetadata executionMetadata) {
        return new ScalaxbBuildParticipant(execution);
    }

}
