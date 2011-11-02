package org.scalaxb.m2e;

import java.io.File;
import java.util.Set;

import org.apache.maven.plugin.MojoExecution;
import org.codehaus.plexus.util.Scanner;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.project.configurator.MojoExecutionBuildParticipant;
import org.sonatype.plexus.build.incremental.BuildContext;

public class ScalaxbBuildParticipant extends MojoExecutionBuildParticipant {

    public ScalaxbBuildParticipant(MojoExecution execution) {
        super(execution, true);
    }

    @Override
    public Set<IProject> build(int kind, IProgressMonitor monitor) throws Exception {
        IMaven maven = MavenPlugin.getMaven();
        BuildContext buildContext = getBuildContext();

        File source = getParameter(maven, "xsdDirectory", File.class);
        Scanner ds = buildContext.newScanner(source);
        ds.scan();
        if (nonEmpty(ds.getIncludedFiles())) {
            return null;
        }

        Set<IProject> result = super.build(kind, monitor);

        refreshOutputDirectory(maven);
        return result;
    }

    private void refreshOutputDirectory(IMaven maven) throws CoreException {
        File generated = getParameter(maven, "outputDirectory", File.class);
        if (generated != null) {
            getBuildContext().refresh(generated);
        }
    }

    <T> T getParameter(IMaven maven, String name, Class<T> type) throws CoreException {
        return maven.getMojoParameterValue(getSession(), getMojoExecution(), name, type);
    }

    private boolean nonEmpty(String[] values) {
        return values != null && values.length > 0;
    }

}
