Notes
-----
L'interface est en français et le backend en anglais.

Nous avons choisi d'utiliser Java pour le backend car nous sommes plus à l'aise avec ce langage, après coup il aurait été plus simple d'utiliser php ou javascript pour permettre du server side rendering.

Nous avons choisi html/css/js pour le frontend car nous avons peu d'experience avec les frameworks js et cela permettait à tout le monde de participer.

En terme d'organisation nous nous sommes distribué les tâches sur le fil de l'eau, en faisant spécifs, front et back en parallèle.


Installation
------------
Required versions:
* Java JRE (tested with version 13)
* Apache Maven

Running:
* Install maven dependencies
* Build and run the generated jar, a server will start on port 8080
* Instance files are generated and kept in the `storage/` folder in the running directory.
* On first launch an admin account is created with id `0000001` and password `password`.

Testing:
* Dependencies are in the `lib/` folder, you will need a selenium driver.
* Tests can be run using JUnit.
