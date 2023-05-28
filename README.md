# **CY-SIAO**
## *Gestion d'un centre d'hébergement en JAVA*


## Table des matières
- [Installation](#installation)
- [Aperçu](#aperçu)
- [Fonctionnalités](#section-1)


### Installation
Pour faire fonctionner notre application JavaFX, suivez les étapes suivantes :

## Étape 1 : Configuration de la base de données
1. Assurez-vous d'avoir installé WampServer (ou un autre serveur de base de données MySQL) sur votre machine.
2. Lancez WampServer et assurez-vous que le service MySQL est en cours d'exécution.
3. Importez notre base de données en exécutant le fichier SQL fourni. Cela créera les tables nécessaires pour notre application.


## Étape 2 : Ajout du JAR externe MySQL
1. Copiez le fichier JAR mySQL dans le répertoire de votre projet JavaFX.
2. Dans votre environnement de développement, ajoutez le JAR externe à votre projet JavaFX. Les étapes exactes peuvent varier en fonction de votre environnement de développement spécifique, mais généralement, vous pouvez faire un clic droit sur le projet, sélectionner "Propriétés" ou "Build Path", puis ajouter le JAR à la configuration du projet.

### Aperçu

[![Capture-d-cran-2023-05-26-232434.png](https://i.postimg.cc/LXbmkTFz/Capture-d-cran-2023-05-26-232434.png)](https://postimg.cc/qh2f42RR)


## Fonctionnalités 

1. Création de la topologie des lits :

- Permettre de définir la structure du centre d'hébergement en créant des chambres et des lits de différentes tailles.
- Offrir une interface conviviale pour ajouter, modifier et supprimer des chambres et des lits.

2. Attribution des lits aux personnes :

- Permettre d'affecter des lits à des personnes en fonction de différents critères tels que le nombre de personnes, les dates, les genres et les âges.
- Gérer la disponibilité des lits et empêcher les affectations de lit en cas de conflit de réservation.

3. Suivi en temps réel de la capacité d'accueil :

- Fournir un état des lieux en temps réel de la capacité d'accueil du centre d'hébergement.
- Afficher des statistiques telles que le taux d'occupation des chambres, le nombre de lits disponibles, etc.
- 
4. Gestion de la base de données :

- Stocker toutes les données relatives aux chambres, aux lits et aux personnes dans une base de données.
- Permettre la création, la modification et la suppression des entrées de la base de données via une interface conviviale.
 
5. Recherche de lits disponibles :

- Offrir une fonctionnalité de recherche pour trouver des lits disponibles en fonction des critères spécifiés (nombre de personnes, dates, genres, etc.).
- Afficher les résultats de recherche de manière claire et intuitive.

6. Interface graphique conviviale :

- Concevoir une interface utilisateur attrayante et conviviale utilisant JavaFX et SceneBuilder.
- Permettre une navigation facile entre les différentes fonctionnalités de l'application.
