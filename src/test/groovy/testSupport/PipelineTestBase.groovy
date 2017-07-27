package testSupport

import org.jvnet.hudson.test.JenkinsRule;
import hudson.model.*;
import org.junit.Rule

import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import spock.lang.Specification
import jenkins.plugins.git.GitSCMSource;
import org.jenkinsci.plugins.workflow.libs.*
/**
 * A base class for Spock testing using the pipeline helper
 */
class PipelineTestBase extends Specification {

    @Rule JenkinsRule j = new JenkinsRule()

    /**
     * Do the common setup
     */
    def setup() {

        def lib = new LibraryConfiguration("rsg", new SCMSourceRetriever(new GitSCMSource(null, System.getProperty("user.dir"), "", "*", "0", true)))
        def proc = Runtime.getRuntime().exec("git branch -m test-branch");
        proc.waitFor();
        lib.setImplicit(true)
        lib.setDefaultVersion('test-branch')
        GlobalLibraries.get().setLibraries(Collections.singletonList(lib));
    }
}