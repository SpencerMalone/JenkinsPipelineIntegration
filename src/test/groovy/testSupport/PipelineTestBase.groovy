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
        List<String> gitHeadNameCmd = ['git', 'rev-parse', '--abbrev-ref', 'HEAD']
        def pb = new ProcessBuilder(gitHeadNameCmd).start()
        pb.consumeProcessErrorStream(System.err)
        pb.waitFor();
        if (pb.exitValue() != 0) {
            throw new RuntimeException("Error running "+gitHeadNameCmd.join(" ")+". The test must run from a git repository")
        }
        lib.setImplicit(true)
        lib.setDefaultVersion(pb.text.trim())
        GlobalLibraries.get().setLibraries(Collections.singletonList(lib));
    }
}
