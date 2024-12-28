pipeline {
    agent any
    stages {
        stage('Backend : Build') {
            steps {
                dir('backend') {
                    script {
                        sh 'mvn -B -DskipTests clean package'
                    }
                }
            }
        }

        stage('Backend : Test') {
            steps {
                dir('backend') {
                    script {
                        sh 'mvn clean package'
                    }
                }
            }
        }

        stage('Frontend: Build') {
            steps {
                dir('frontend') {
                    echo "Installing frontend dependencies and building..."
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Docker: Build Images') {
            parallel {
                stage('Backend Docker Build') {
                    steps {
                        dir('backend') {
                            script {
                                echo "Building Docker image for backend..."
                                def pom = readMavenPom file: 'pom.xml'
                                withCredentials([usernamePassword(credentialsId: 'hub.docker.com', passwordVariable: 'HUB_REPO_PASS', usernameVariable: 'HUB_REPO_USER')]) {
                                    def user = env.HUB_REPO_USER
                                    def password = env.HUB_REPO_PASS
                                    sh "docker version"
                                    sh "docker login -u $user -p $password"
                                    sh "docker build -t maliciamrg/${pom.getArtifactId().toLowerCase()}:${pom.getVersion()} . "
                                    sh "docker push maliciamrg/${pom.getArtifactId().toLowerCase()}:${pom.getVersion()}"
                                    sleep 10 // Wait for 10 seconds
                                }
                            }
                        }
                    }
                }
                stage('Frontend Docker Build') {
                    steps {
                        dir('frontend') {
                            script {
                                echo "Building Docker image for frontend..."
                                sh 'docker build -t ${DOCKER_USER}/${FRONTEND_IMAGE} .'
                                def packageJson = readJSON file: 'package.json'
                                withCredentials([usernamePassword(credentialsId: 'hub.docker.com', passwordVariable: 'HUB_REPO_PASS', usernameVariable: 'HUB_REPO_USER')]) {
                                    def user = env.HUB_REPO_USER
                                    def password = env.HUB_REPO_PASS
                                    sh "docker version"
                                    sh "docker login -u $user -p $password"
                                    sh "docker build -t maliciamrg/${packageJson.name.toLowerCase()}:${packageJson.version} . "
                                    sh "docker push maliciamrg/${packageJson.name.toLowerCase()}:${packageJson.version}"
                                    sleep 10 // Wait for 10 seconds
                                }
                            }
                        }
                    }
                }
            }
        }

        stage("assistantPhoto : Deploy to Docker Server") {
            steps {
                script {
                    sh "docker --context remote compose down"
                    sh "docker --context remote compose up -d --force-recreate"
                }
            }
        }

    }
    post {
        always {
            print "always"
        }
        changed {
            print "changed"
        }
        fixed {
            print "fixed"
            discordSend (
                    description: "Jenkins Pipeline Build",
                    footer: "Status fixed",
                    link: env.BUILD_URL,
                    result: currentBuild.currentResult,
                    title: JOB_NAME,
                    webhookURL: "https://discord.com/api/webhooks/1251803129004032030/Ms-4v3aw3MMkIHIECMYMiP48NTV_F1IazsvwQmAqGGFw4OOR9FRX-DwjFG5V1dV-zKg6"
            )
        }
        regression {
            print "regression"
        }
        aborted {
            print "aborted"
        }
        failure {
            print "failure"
            script {
                if (!currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')){
                    discordSend (
                        description: "Jenkins Pipeline Build",
                        footer: "Status failure",
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult,
                        title: JOB_NAME,
                        webhookURL: "https://discord.com/api/webhooks/1251803129004032030/Ms-4v3aw3MMkIHIECMYMiP48NTV_F1IazsvwQmAqGGFw4OOR9FRX-DwjFG5V1dV-zKg6"
                    )
                }
            }
        }
        success {
            print "success"
        }
        unstable {
            print "unstable"
        }
        unsuccessful {
            print "unsuccessful"
        }
        cleanup {
            print "cleanup"
        }
    }
}