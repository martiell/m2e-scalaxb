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
        File xsd = getParameter(maven, "xsdDirectory", File.class);
        File wsdl = getParameter(maven, "wsdlDirectory", File.class);
        if (!hasChanged(xsd) && !hasChanged(wsdl)) {
            return null;
        }

        Set<IProject> result = super.build(kind, monitor);
        refreshOutputDirectory(maven);
        return result;
    }

    private boolean hasChanged(File directory) throws CoreException {
        BuildContext buildContext = getBuildContext();
        Scanner ds = buildContext.newScanner(directory);
        ds.scan();
        String[] files = ds.getIncludedFiles();
        return files != null && files.length > 0;
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

}
