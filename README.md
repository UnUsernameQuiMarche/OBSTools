# OBSTools

## Prérrequis

Merci de télécharger les JDKs Java pour l'utilisation de OBSTools

Lien de téléchargement : [JDKs Java](https://www.oracle.com/java/technologies/downloads/) 
(Attention : C'est l'onglet linux qui est sélectionné par défaut. Bien choisir l'onglet windows.
Le plus simple est de télécharger le fichier ZIP.)

## Installation

Créez un dossier pour contenir le logiciel

Placez le fichier "OBSTools by William.exe" dans ce dossier

Créez un dossier appelé "jre" et placez-y les fichiers JDKs extraits de l'archive .zip

C'est parti !

```
dossier racine
│   OBStools by William.exe
│
└───jre
    │   bin
    │   conf
    │   include
    │   jmods
    │   legal
    │   lib
    │   COPYRIGHT
    │   LICENSE
    │   README
    │   release
```

### Création d'une template

#### Mise en place

Télécharger le ficher [template.java](https://raw.githubusercontent.com/WB-44/OBSTools/main/exemple/template.java) dans le dossier exemple

Remplacer le nom du fichier par *nom_raccourci_de_la_template*.java

Remplacer le mot *template* aux lignes 6 et 8 par le *nom_raccourci_de_la_template*

Placer le fichier dans le dossier *src/main/java/com/example/javafxmavenobs*

Dans le fichier *NomMacro.java* du dossier *src/main/java/com/example/javafxmavenobs/*, rajouter à la suite de la liste ligne 8 le *nom_complet_de_la_template*

Dans le fichier *MsgBrut.java* du dossier *src/main/java/com/example/javafxmavenobs/*, rajouter à la suite des conditions ligne 53 :


```
else if (this.Macro == NomMacro.nom_complet_de_la_template) {
    f = new nom_raccourci_de_la_template();
}
```

#### Modifications

La fonction *avant* ligne 12 contient les touches pressées avant de coller le texte du bouton

La fonction *après* ligne 19 contient les touches pressées après de coller le texte du bouton

Un exemple d'utilisation est disponible dans la fonction *avant*

Pour maintenir la touche "A" enfoncée
```
robot.keyPress(KeyEvent.VK_A); 
```
Pour relacher la touche "A"
```
robot.keyRelease(KeyEvent.VK_A);
```
Pour faire une pause de 300ms
```
robot.delay(300);
```

#### Liste des commandes disponibles

Pour émuler l'appui sur une touche, utiliser la fonction KeyEvent, avec KeyEvent.VK_*nom_de_la_touche*

Exemple : KeyEvent.VK_A pour la touche "A"

Pour les touches particulières (Tabulation, Backspace, Espace...) voir la documentation [ici](https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html)
