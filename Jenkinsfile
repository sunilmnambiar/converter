node {
	stage('Build Docker Image') {
	
		echo "Building docker image"
		git branch: 'main', url: 'https://github.com/sunilmnambiar/converter'
      	sh "docker build -t sunilmnambiar/converter ."
      	
    }
   
    stage('Deploy Docker Image'){
    
      echo "Deploying docker image"
	  
	  sh "docker stop converter || true"
	  
	  sh "docker rm converter || true"
	  
	  sh "docker run -d --name converter -v vol1:/data -p 80:8080 sunilmnambiar/converter || true"
      
    }
}