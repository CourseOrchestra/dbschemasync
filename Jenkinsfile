node {
    def server = Artifactory.server 'ART'
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo
    def oldWarnings

    stage ('Clone') {
        checkout scm
    }

    stage ('Artifactory configuration') {
        rtMaven.tool = 'M3' 
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        buildInfo = Artifactory.newBuildInfo()
        buildInfo.env.capture = true
    }

    try{
        stage ('Exec Maven') {
            rtMaven.run pom: 'pom.xml', goals: 'clean install', buildInfo: buildInfo
        }
    } finally {
        junit 'target/surefire-reports/**/*.xml'
        recordIssues(
           enabledForFailure: true,
           tools: [java(), 
                   checkStyle(pattern: '**/target/checkstyle-result.xml', reportEncoding: 'UTF-8'),
                   spotBugs(pattern: '**/target/spotbugsXml.xml')
                  ],
           qualityGates: [[threshold: 1, type: 'TOTAL']]
        )
    }

    if (env.BRANCH_NAME == 'dev') {
        stage ('Publish build info') {
            server.publishBuildInfo buildInfo
        }
    }
}
