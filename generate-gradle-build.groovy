// Use this script in the jenkins web console to export your jenkins instance's verion + plugins into a build.gradle

println """
apply plugin:   'groovy'

ext {
"""
println "    jenkinsVersion = '" + Jenkins.instance.VERSION + "'"
println """
}

repositories {
    jcenter()
    maven {
        url 'https://repo.jenkins-ci.org/public/'
    }
}

task setupRepoCopy(type:Exec) {
    def stdout = new ByteArrayOutputStream()

    commandLine "./script/test.sh", "setupCopyOfRepo"
    standardOutput = stdout;
    doLast {
        println "Output:\n$stdout";
    }
}

tasks.test.dependsOn(setupRepoCopy)

dependencies {
    compile 'org.codehaus.groovy:groovy-all:2.4.11'

    /**
     * JenkinsPipelineUnit for testing pipelines from:
     * https://github.com/lesfurets/JenkinsPipelineUnit
     */
    //testCompile 'com.lesfurets:jenkins-pipeline-unit:0.12'

    /**
     * Test framwork stuff
     */
    testCompile 'org.spockframework:spock-core:1.1-groovy-2.4'
    testCompile 'cglib:cglib-nodep:3.2.2'
    testCompile 'org.objenesis:objenesis:1.2'
    testCompile 'org.assertj:assertj-core:3.7.0'

    testCompile 'org.jenkins-ci.main:jenkins-test-harness:2.20'
    testCompile "org.jenkins-ci.main:jenkins-war:\${jenkinsVersion}"
    testCompile "org.jenkins-ci.main:jenkins-war:\${jenkinsVersion}:war-for-test@jar"
"""

Jenkins.instance.pluginManager.plugins.each {
def attributes = it.getManifest().getMainAttributes()
if(attributes.getValue('Short-Name') != 'metrics' && attributes.getValue('Short-Name') != 'jobConfigHistory' && attributes.getValue('Short-Name') != 'jacoco' ) {
println("    testCompile ('" + attributes.getValue('Group-Id') + ":" + attributes.getValue('Short-Name') + ":" + attributes.getValue('Plugin-Version') + "') {  force = true; artifact {name='"+ attributes.getValue('Short-Name') +"';type='jar'}; artifact {name='"+ attributes.getValue('Short-Name') +"';type='hpi' } }")
}
}

println """
}

sourceSets {
    test {
        groovy {
            srcDirs= ['src/test/groovy']
            
        }
    }
}

"""
""