# Ewidencja œrodków trwa³ych

Aplikacja do ewidencji œrodków trwa³ych umo¿liwia wprowadzanie oraz edycjê œrodków trwa³ych oraz mo¿liwoœæ przypisania sprzêtu do pracowników oraz lokalizacji co u³atwia zarz¹dzanie tym sprzêtem.
System sk³ada siê z dwych modu³ow www oraz aplikacji mobilnej. Interfejs www umo¿liwia wprowadzanie danych do bazy oraz zarz¹danie nimi natomiast aplikacja mobilna s³u¿y do tworzenia powi¹zañ pracownika z pomieszczeniami i sprzêtem.
Dane pomiêdzy tymi 2 modu³ami wymieniane s¹ za pomoca synchronizatora danych Mobeelizer.

![Model systemu.](landowskitomasz.github.com/widencjaSrodkowTrwalych/model.png)

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
4. Skopiuj skonfigurowany o do folderu deploy serwera jBoss.
5. Zaimportuj projekty z katalogu www do œrodowiska pracy eclips'a i uruchom je na serwerze jBoss

# Uruchomienie aplikacji android

1. Zaimportuj projekt z katalogu AndroidApp do eclipsa.
2. Uruchom projekt w emulatorze lub zbuduj go i zainstaluj na telefonie. 

# Copyright

Copyright 2012 - Marcin Hajduczek, Tomasz Landowski

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.