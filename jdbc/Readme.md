# Progetto jdbc
## Inizializzazione Maven
Cliccare con il tasto destro su jdbc, dal menu contestuale selezionare `Maven` -> `Update Project...` e confermare cliccando su `OK`

Questo scaricherà le librerie necessarie al progetto, nel caso specifico il driver per la connessione a PostgreSQL

## Setup database
Il progetto si collega ad un database PostgreSQL installato nel proprio pc (localhost), con porta standard (5432), un utente `test` con password `test` ed un database chiamato `test`

Installare PostgreSQL nel proprio pc con le impostazioni standard. Con l'utente postgres eseguire i comandi:
* `create role test with login password 'test'`
   * output: `CREATE ROLE`
* `create database test with owner test;`
   * output: `CREATE DATABASE`
