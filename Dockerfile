# Utiliser une image de base avec Java 21
FROM openjdk:21-jdk-slim

# Copier le fichier JAR dans le répertoire de travail
ADD app/build/libs/app-0.0.1.jar app.jar

# Commande pour lancer le JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
