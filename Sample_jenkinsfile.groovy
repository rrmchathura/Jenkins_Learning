pipeline {
    agent any


    environment{
        // Define environment variabless here
        My_ENV_VAR = 'custom value'
    }

    stages {
        stage('Checkout') {
            steps {
                script{
                    //Clone the git repository master branch
                    def gitRepoUrl = 'https://github.com/rrmchathura/Jenkins_Learning.git'

                    Checkout([$class: 'GitSCM',
                        branches: [[name: '*/main']],
                        userRemoteConfigs: [[url: gitRepoUrl]],
                        extensions: [[$class: 'CleanBeforeCheckout'], [$class:'CloneOption', noTags: false, shallow: true, depth: 1]]
                        ])
                }
                
            }
        }

        stage('Build') {
            steps {
                //Build your application here 
                //execute the shell
                sh '''
                ls
                echo "In build step"
                '''
            }
        }

        stage('Test') {
            steps {
                //Run your tests
                sh 'echo "In test step"'
            }
        }

        stage('Deploy') {
            steps {
                //Deploy your application to a target environment
                  sh 'echo "Value of ENV Variable is "$My_ENV_VAR""'
            }
        }
    }

    post{
        success {
            //actions to perform when the pipeline succeeds
            echo 'pipeline succeeded!'
        }

        failure{
                  //actions to perform when the pipeline fails
            echo 'pipeline failed!'
        }
    }
}