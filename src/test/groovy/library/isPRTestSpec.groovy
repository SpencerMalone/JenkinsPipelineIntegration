package tests.library

import testSupport.PipelineTestBase
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import hudson.model.Result;


class isPRTestSpec extends PipelineTestBase {

    def "isPR returns true when env.BRANCH_NAME starts with PR-"() {

        given:
            WorkflowJob p = j.createProject(WorkflowJob.class, "p");

        when:
            p.setDefinition(new CpsFlowDefinition("""
                node(){ 
                    env.BRANCH_NAME = 'PR-123'; 
                    if(!isPR()) {
                        sh 'exit 1'
                    } 
                }"""
            ));

        then:
            j.assertBuildStatusSuccess(p.scheduleBuild2(0));
    }

    def "isPR returns false when env.BRANCH_NAME starts with a normal branch name"() {

        given:
            WorkflowJob p = j.createProject(WorkflowJob.class, "p");

        when:
            p.setDefinition(new CpsFlowDefinition("""
                node(){ 
                    env.BRANCH_NAME = 'master'; 
                    if(!isPR()) {
                        sh 'exit 1'
                    } 
                }"""
            ));

        then:
            j.assertBuildStatus(Result.FAILURE,p.scheduleBuild2(0));
    }
}