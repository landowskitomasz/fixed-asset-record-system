# Ewidencja œrodków trwa³ych

Aplikacja do ewidencji œrodków trwa³ych umo¿liwia wprowadzanie oraz edycjê œrodków trwa³ych oraz mo¿liwoœæ przypisania sprzêtu do pracowników oraz lokalizacji co u³atwia zarz¹dzanie tym sprzêtem.
System sk³ada siê z dwych modu³ow www oraz aplikacji mobilnej. Interfejs www umo¿liwia wprowadzanie danych do bazy oraz zarz¹danie nimi natomiast aplikacja mobilna s³u¿y do tworzenia powi¹zañ pracownika z pomieszczeniami i sprzêtem.
Dane pomiêdzy tymi 2 modu³ami wymieniane s¹ za pomoca synchronizatora danych Mobeelizer.

![Model systemu.](EwidencjaSrodkowTrwalych/raw/master/model.png)

#Œrodowisko pracy

* baza danych MySql
* serwer aplikacyjny jBoss 6.1.0
* eclipse indigo
* android sdk 2.2 

# Uruchomienie aplikacji www

1. Utwórz bazê danych na serwerze MySql (np. CREATE DATABASE ewidencja;)
2. Uruchom skrypty SQL w utworzonej bazie danych
 * www/ddl.sql.txt
 * www/reports_ddl.sql.txt
3. Zmieñ ustawienia pliku dostêpu do Ÿród³a danych - www/EwidServerAdmin-ear/resources/EwidServerAdmin-ds.xml tak aby wskazywa³ na Twoj¹ bazê danych.
4. Skopiuj skonfigurowany plik do folderu deploy serwera jBoss.
5. Zaimportuj projekty z katalogu www do œrodowiska pracy eclips'a.
6. Uruchom serwer jBosss u¿ywaj¹c perspektywy Java EE i okna Servers. 
7. Uruchom projekty na serwerze klikaj¹c prawym przyciskiem i wybieraj¹æ Run>Run on Server lub dodaj¹c je do serwera w oknie Servers.
8. Aplikacja zostanie uruchomiona na porcie 8080.

# Uruchomienie aplikacji android

1. Zaimportuj projekt z katalogu AndroidApp do eclipsa.
2. Uruchom projekt w emulatorze lub zbuduj go i zainstaluj na telefonie. (Pamiêtaj ¿e aplikacja wymaga sdk w wersji minimum 2.2 oraz ¿e emulator musi byœ skonfigurowany tak aby mia³ dostêp do internetu oraz zewnetrznej pamiêci)

# Uruchomienie synchronizacji

Do synchronizacji danych serwera z Mobeelizerem stworzyliœmy osobn¹ aplikacjê konsolow¹ www/Synchronizator, powodem by³y konflikty na serwerze jBoss. 


# Dokumentacja projektu 

Pe³n¹ dokumentacje projektu znajdziesz pod adresem: http://ai.ia.agh.edu.pl/wiki/pl:dydaktyka:ztb:2012:projekty:srodki_trwale

# Copyright

Copyright 2012 - Marcin Hajduczek(mhajduczek@wp.pl), Tomasz Landowski(landowskitomasz@gmail.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.