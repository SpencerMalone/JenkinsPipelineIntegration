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

        def lib = new LibraryConfiguration("rsg", new SCMSourceRetriever(new GitSCMSource(null, System.getProperty("user.dir") + "/.copy-of-repo", "", "*", "0", true)))
        lib.setImplicit(true)
        lib.setDefaultVersion("master")
        GlobalLibraries.get().setLibraries(Collections.singletonList(lib));
    }
}
