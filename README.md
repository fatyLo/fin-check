L’application est un projet springboot avec comme gestionnaire de packages Maven.
Elle se lance donc par sa classe main: FinCheckApplication
ou avec la commande : mvn spring-boot:run


l’API est accessible via swagger qui permet de tester tous les endpoint: http://localhost:8080/swagger-ui/index.html ( le port 8080 est le port par défaut et peut être modifié dans applications.properties (property server.port) s’il est occupé par un autre process)
La base de données est une base H2, consultable via la console H2 : 

http://localhost:8080/h2-console (le port doit être le même que celui de l’application)

Pour la connection, renseigner dans le champ JDBC URL :jdbc:h2:mem:fin-check-db (configurable dans application.properties)

Les tables sont pré-remplies avec des valeurs par défaut réinitialisées à chaque démarrage de l’application (pour faciliter les tests)

Les 6 Endpoint décrites dans le contrat ont été implémentées.


Concernant les améliorations que j’aurai aimé apportées, il s’agirait de créer des mapper entre les DTOs et les Entities, d’ajouter une base de données et d’ajouter certains uses cases tels: 

La suppression et la mise à jour des différentes entités
La suppression d’un bénéficiaire d’une entreprise
la récupération de la liste des personnes physiques
Ajouter des test unitaires

