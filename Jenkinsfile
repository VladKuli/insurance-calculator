pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Make Gradle executable') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew bootJar -x test'
            }
        }

        stage('Deploy to Render') {
            when {
                branch 'master'
            }
            steps {
                withCredentials([string(credentialsId: 'render-deploy-hook', variable: 'RENDER_DEPLOY_HOOK')]) {
                    sh 'curl -X POST "$RENDER_DEPLOY_HOOK"'
                }
            }
        }
    }
}