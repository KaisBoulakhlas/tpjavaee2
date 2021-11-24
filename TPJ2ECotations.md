![](Aspose.Words.c88131fc-5a98-4cb2-a34a-647a3d3c21b9.001.png)

JAVAEE

TP 2 

PANIER DE COTATIONS

Il s'agit de réaliser une application permettant à une personne de gérer un panier de cotations de valeurs boursière et de voir l'évolution de ces cotations. 

Vous avez un exemple fonctionnel à l’adresse[ https://extranet.esimed.fr/StockMarket-web/ ](https://extranet.esimed.fr/StockMarket-web/) . 

**EXIGENCES FONCTIONNELLES**

***FRONT-END UTILISATEUR***

- Création d’un compte utilisateur. 
- Identification par une adresse e-mail et un mot de passe. 
- Une fois connecté l'utilisateur aura la possibilité de : 
  - Ajouter ou supprimer des sociétés cotées à son panier de cotations. 
  - Visualiser les cotations des sociétés de son panier de cotations. Chaque cotation sera indiquée avec les informations suivantes : symbole de la société, nom, cotation actuelle, différence en % par rapport à la cotation de la société lors de son ajout au panier. L’affichage de ce panier de cotations sera mis à jour toutes les 15 secondes. 
  - Retrouver son panier de cotations à chaque connexion. 
  - Editer les informations de son compte. 

***BACK-END***

- L’application récupérera en arrière-plan les mises à jour des cotations référencées dans les paniers des utilisateurs toutes les 5 minutes. Cela devra être optimisé : si deux utilisateurs ont les mêmes cotations elles devront être récupérées qu’une seule fois. 
- L’application devra envoyer à l’utilisateur, toutes les 15 minutes et par e-mail, la liste des cotations de son panier dont la différence en % entre la cotation actuelle et celle du précédent e-mail (ou depuis son ajout dans le panier) est supérieur, inférieure ou égale à 2% (et uniquement s’il y en a). Cela même si l’utilisateur n’est pas connecté (mais il faut qu’il soit actif). 

***FRONT-END ADMINISTRATEUR***

- Un compte pré-créé aura un rôle d’administrateur qui pourra voir la liste des utilisateurs et les activer/désactiver. 
- Un utilisateur connecté désactivé par l’administrateur sera automatiquement déconnecté dans les 10 secondes qui suivent la déconnexion. ![](Aspose.Words.c88131fc-5a98-4cb2-a34a-647a3d3c21b9.002.png)

**EXIGENCES NON-FONCTIONNELLES**

- Utilisez les Timers EJB pour les fonctions en arrière-plan 
- Les sociétés cotées sur le marché et leurs cotations sont données par un serveur distant à l'aide d’une API JSON. Cette API n’est pas à développer et est déjà déployée sur[ https://extranet.esimed.fr/StockMarket/api/stockmarket/ ](https://extranet.esimed.fr/StockMarket/api/stockmarket/). 
- Les classes nécessaires à l’accès à l’API se trouvent dans l’archive StockMarket-client.zip. Utilisez les sources de ce projet comme module de votre projet. 
- La liste des sociétés ne doit pas être stockée par l’application. Les sociétés y seront référencées par leur symbole unique uniquement. ![](Aspose.Words.c88131fc-5a98-4cb2-a34a-647a3d3c21b9.002.png)
PAGE2 ![](Aspose.Words.c88131fc-5a98-4cb2-a34a-647a3d3c21b9.003.png)
