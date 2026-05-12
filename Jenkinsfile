pipeline {
    agent any

    /* Aquí definimos las herramientas. 
       Asegúrate de que en 'Manage Jenkins' -> 'Tools', 
       el nombre de Maven coincida con el que pongas aquí (ej. 'M3')
    */
    tools {
        maven 'M3' 
    }

    stages {
        stage('Carga de Código') {
            steps {
                // Jenkins descarga automáticamente el código de GitHub
                echo 'Descargando código de GitHub...'
            }
        }

        stage('Compilación y Empaquetado') {
            steps {
                echo 'Compilando el proyecto Spring Boot...'
                // Usamos 'bat' porque Jenkins corre sobre Windows
                // -DskipTests es opcional para que la primera vez sea más rápido
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Pruebas') {
            steps {
                echo 'Ejecutando pruebas unitarias...'
                bat 'mvn test'
            }
        }

        stage('Resumen de Artefatos') {
            steps {
                echo 'El archivo .jar se ha generado con éxito en la carpeta target.'
                // Esto guarda el archivo generado dentro de Jenkins para que lo puedas descargar
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
		stage('Construir Imagen Docker') {
            steps {
                script {
                    // Nombre de la imagen con el número de build
                    def imageName = "mi-api-s3:${env.BUILD_NUMBER}"
                    
                    // Construir la imagen usando el Dockerfile
					bat "docker build -t ${imageName} -t mi-api-s3:latest ."
                    // Opcional: subirla a un registry
                    // sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"
                    // sh "docker tag ${imageName} myrepo/mi-api-s3:${env.BUILD_NUMBER}"
                    // sh "docker push myrepo/mi-api-s3:${env.BUILD_NUMBER}"
                }
            }
        }
		
		
		stage('Clean container') {
			steps {
				bat '''
					REM Si ya existe el contenedor mi-api, bajar el stack
					for /f %%i in ('docker ps -q -f name=mi-api') do (
						docker-compose down
					)
				'''
			}
		}
		stage('Run container') {
			steps {
				bat '''
					REM Levantar todo el stack con la nueva imagen
					docker-compose up -d
				'''
			}
		}

		
    }

    post {
        success {
            echo '¡Felicidades! El pipeline terminó correctamente2. VERSION2 '
        }
        failure {
            echo 'Hubo un error en la construcción. Revisa el Console Output.'
        }
    }
}