package com.butlerhq.extdoc;

import extdoc.jsdoc.processor.FileProcessor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal extdoc
 * @phase package
 */
public class ExtDocMojo extends AbstractMojo {

    /**
     * Project XML File
     *
     * @parameter expression="${extdoc.project}"
     */
    private String project;

    /**
     * Directory where documentation should be created
     *
     * @parameter expression="${extdoc.output}" default-value="target/extdoc"
     * @required
     */
    private String output;

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
        if (project != null || source != null && source.length > 0) {
            FileProcessor processor = new FileProcessor();
            if (quiet) {
                processor.setQuiet();
            } else if (verbose) {
                processor.setVerbose();
            }
            processor.process(project, source);
            processor.saveToFolder(output, template);
        } else {
            throw new MojoExecutionException("The extdoc plugin requires project OR source parameters.");
        }
    }
}
