def check() {
    echo "building the application.."
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t rahulkumarpaswan/my-python-project:1.5 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push rahulkumarpaswan/my-python-project:1.5'
    }
} 

def deployApp() {
    echo 'deploying the application...'
    def dockerCmd = 'docker run -d --name ishu-project-2 -p 3000:3000 rahulkumarpaswan/my-python-project:1.5'
    sshagent(['ec2-user-Rahul']) {
    sh "ssh -o StrictHostKeyChecking=no ec2-user@3.109.181.27  ${dockerCmd}"
    }
} 

return this

//

