# treasure hunt

Partis pris d'implémentation : 
- Le fichier d'entrée **doit** respecter l'ordre suivant :  
  1. Définition de la taille de la carte 
  2. Montagnes
  3. Trésors
  4. Aventuriers

- L'ordre d'apparition définit la priorité des éléments les uns sur les autres : si un trésor doit être créé sur une même case qu'une montagne, il ne sera pas créé (dans tous les cas il ne serait pas ramassable, cela influerait seulement sur l'output).
- Un aventurier ne ramasse un trésor qu'en traversant une case i.e. : si sa case de départ contient un trésor, il ne le récupéra pas
