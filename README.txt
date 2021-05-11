Alkalmazás fejlesztés gyakorlat beadandó.
--------------------------
Rövid leírás a programról:
Ez egy multimodule maven projekt web, adatbázis és gui részre bontva java nyelven.
A gui javaFx, az adatbázis jdbc és sqlite, a web jsp segítségével készült.
Maga az alkalmazás egy sakk, ahol 2 játékos tud játsazni felváltva az egér segítségével.
A start gombra kattintás után meg kell adni a játékosok nevét. Ezután megjelenik a pálya.
A sakk bábúk mozgatása úgy történik, hogy rá kell kattintani egy bábúra és tetszőleges mezőre lehet "vonszolni" az egér mutatójával együtt.
Leütesek működnek. Sáncolás, átváltozás, menet közbeni ütés automatiksan működnek.
A játéknak, akkor van vége, ha valamelyik fél rákattint a neve alatti feladás gombra, ekkor a másik fél automatikusan nyer.
Vagy ha a valamelyik játékos a döntetlenre kattint, ekkor a másik félnek el kell fogadnia azt.
A menüből elérhető egy eddigi lejátszott meccsek menüpont. Ott lehet kereseni névre, kimenetelre stb az eddigi meccsek között.
És egy webes felület is ugyan erre a célra.
(a webes felületen ha vissza kell tölteni az eredeti állapotot csak üres mezőkre kell keresni.)
-----------------------------
Futattás:
Asztali  maven clean install után -> sakk-desktop javafx:run
Webes tomcat indítása után nekem feldobta magától vagy http://localhost:8081/sakk_web_war/
------------------------------
Fix:
Ha kihal a sakk-desktop pom.xml, akkor: File-> Invalidate Caches / Restart
-------------------------------

