package com.butlerhq.extdoc;

import extdoc.jsdoc.processor.FileProcessor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.reporting.MavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.sink.Sink;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * Copyright 2011 William Butler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @goal extdoc
 * @phase site
 */
public class ExtDocReport extends AbstractMojo implements MavenReport {

    /**
     * Project XML File
     *
     * @parameter expression="${extdoc.project}"
     */
    private String project;

    /**
     * Directory where documentation should be created
     *
     * @parameter expression="${extdoc.output}" default-value="${project.reporting.outputDirectory}/extdoc"
     * @required
     */
    private File output;

    /**
     * XML File containing template information
     *
     * @parameter expression="${extdoc.template}"
     * @required
     */
    private String template;

    /**
     * Source files
     *
     * @parameter expression="${extdoc.source}"
     */
    private String[] source;

    /**
     * Be extra quiet
     *
     * @parameter expression="${extdoc.quiet}"
     */
    private boolean quiet;

    /**
     * Be extra verbose
     *
     * @parameter expression="${extdoc.verbose}"
     */
    private boolean verbose;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            generate(null, null);
        } catch(MavenReportException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public void generate(Sink sink, Locale locale) throws MavenReportException {
        if (project != null || source != null && source.length > 0) {
            Thread currentThread = Thread.currentThread();
            ClassLoader originalClassLoader = currentThread.getContextClassLoader();
            try {
                // workaround for jaxb issue (extdoc uses a call that depends on the thread's classloader)
                currentThread.setContextClassLoader(FileProcessor.class.getClassLoader());
                FileProcessor processor = new FileProcessor();
                if (quiet) {
                    processor.setQuiet();
                } else if (verbose) {
                    processor.setVerbose();
                }
                processor.process(project, source);
                processor.saveToFolder(output.getPath(), template);
            } finally {
                Thread.currentThread().setContextClassLoader(originalClassLoader);
            }
        } else {
            throw new MavenReportException("The extdoc plugin requires project OR source parameters.");
        }
    }

    @Override
    public String getOutputName() {
        return "extdoc/index";
    }

    @Override
    public String getCategoryName() {
        return CATEGORY_PROJECT_REPORTS;
    }

    @Override
    public String getName(Locale locale) {
        return getBundle(locale).getString("extdoc.name");
    }

    @Override
    public String getDescription(Locale locale) {
        return getBundle(locale).getString("extdoc.description");
    }

    @Override
    public void setReportOutputDirectory(File output) {
        this.output = new File(output, "extdoc");
    }

    @Override
    public File getReportOutputDirectory() {
        return output;
    }

    @Override
    public boolean isExternalReport() {
        return true;
    }

    @Override
    public boolean canGenerateReport() {
        return true;
    }

    private ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("extdoc", locale, getClass().getClassLoader());
    }
}
