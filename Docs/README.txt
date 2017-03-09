Webservices RESTful avec Python :
    Bibliothèque web.py
        pip install web.py

    Faire le webservice en PHP m'obligait à uiliser un troisième langage et donc à ajouter un niveau de
    complexité au système. En plus, après chaque modification du code, il aurait fallu deployer les
    fichiers PHP dans un serveur local.

    J'ai donc décidé d'implémenter le webservice en Python. En faisant ainsi, tout le côté serveur est fait
    dans un même langage et il est donc plus facile à maintenir.

    En outre, pour tester dans les PCs de la fac le webservice PHP aurait été un problème puisque Ice n'est
    pas installé dans le serveur pedago.


Struct Commande :
    - Pendant le développement je me suis rendu compte qu'il fallait ajouter un attribut pour indiquer
    le retour des commandes. Par exemple, pour obtenir la liste de chansons disponibles.
    - Le retour est implemente comme un string, dans le cas d'objets complexes on va retourner sa representation JSON.
    - Pour permettre la gestion de commandes plus variées, j'ai supprimé l'attribut chanson et ajouté
    une liste de parametres pour la commande à exécuter. en faisant ainsi, une commande peut recevoir
    un nombre indefini de paramètres.
