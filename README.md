# Backend Projet DevOps MIAGE

[![Build & Test](https://github.com/GreenTreep/Backend/actions/workflows/gradle-build.yml/badge.svg?branch=main)](https://github.com/GreenTreep/Backend/actions/workflows/gradle-build.yml)
[![Java](https://img.shields.io/badge/java-21-blue.svg)](https://www.oracle.com/java/technologies/javase-jdk21.html)
[![Docker](https://img.shields.io/badge/docker-20.10.7-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/license-Apache-yellow.svg)](LICENSE)
[![Stars](https://img.shields.io/github/stars/GreenTreep/Backend.svg?style=social&label=Stars)](https://github.com/GreenTreep/Backend/stargazers)
[![SonarCloud Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=GreenTreep_Backend&metric=alert_status)](https://sonarcloud.io/dashboard?id=GreenTreep_Backend)

## Table des Matières
- [Introduction](#introduction)
- [Prérequis](#prérequis)
- [Installation](#installation)
  - [Cloner le Repository](#cloner-le-repository)
  - [Configurer le Projet](#configurer-le-projet)
- [Utilisation](#utilisation)
  - [Lancer l'Application](#lancer-lapplication)
  - [Utiliser Docker](#utiliser-docker)
- [Contribuer](#contribuer)
- [Licence](#licence)
- [Contact](#contact)

## Introduction
<p align="center">
  <a href="https://github.com/GreenTreep/Backend">
    <img src="https://avatars.githubusercontent.com/u/188393733?s=200&v=4" alt="Logo" width="150"/>
  </a>
</p>
Bienvenue dans le **Backend Projet DevOps MIAGE** ! Ce projet est conçu pour offrir une solution robuste et scalable pour vos besoins en backend. Basé sur Java 21 et orchestré avec Docker, il s'intègre parfaitement dans des environnements DevOps modernes.

## Prérequis

- **Java 21** : [Télécharger Java 21](https://www.oracle.com/java/technologies/javase-jdk21.html)
- **Docker Desktop** : [Télécharger Docker](https://www.docker.com/products/docker-desktop)
- **Git** : [Installer Git](https://git-scm.com/downloads)
- **Gradle** : [Documentation Gradle](https://gradle.org/install/)

## Installation

### Cloner le Repository

```bash
git clone https://github.com/GreenTreep/Backend.git
cd Backend
```

### Configurer le Projet

1. **Créer une nouvelle branche** pour développer une fonctionnalité :
    ```bash
    git checkout -b feature_incroyable
    ```

2. **Basculer vers une branche existante** :
    ```bash
    git checkout branche_existante
    ```

## Utilisation

### Lancer l'Application

Pour exécuter l'application localement, utilisez la commande suivante :

```bash
./gradlew run
```

### Utiliser Docker

1. **Construire le projet avec Gradle** :
    ```bash
    ./gradlew build
    ```

2. **Ouvrir Docker Desktop**.

3. **Lancer les services Docker** avec Docker Compose :
    ```bash
    docker compose up --build
    ```

## Contribuer

Nous accueillons les contributions de la communauté ! Pour contribuer :

1. **Fork** le repository.
2. **Créer une branche** pour votre fonctionnalité (`git checkout -b feature_nom`).
3. **Commit** vos changements (`git commit -m 'Ajout de la fonctionnalité incroyable'`).
4. **Pousser** la branche (`git push origin feature_nom`).
5. **Ouvrir une Pull Request**.

_N'oubliez pas de synchroniser votre branche locale avec le dépôt principal à chaque lancement de VSCode :_

```bash
git pull
```

## Licence

Ce projet est sous licence **APACHE**. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## Contact

Pour toute question ou suggestion, n'hésitez pas à [ouvrir une issue](https://github.com/GreenTreep/Backend/issues) ou à contacter [greentrip1@proton.me](mailto:greentrip1@proton.me).

---

<p align="center">
  <a href="https://github.com/GreenTreep/Backend">
    <img src="https://avatars.githubusercontent.com/u/188393733?s=200&v=4" alt="Logo" width="150"/>
  </a>
</p>
