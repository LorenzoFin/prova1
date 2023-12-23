# Progetto jdbc
## Inizializzazione Maven
Cliccare con il tasto destro su jdbc, dal menu contestuale selezionare `Maven` -> `Update Project...` e confermare cliccando su `OK`

Questo scaricherà le librerie necessarie al progetto, nel caso specifico il driver per la connessione a PostgreSQL

## Setup database
Il progetto si collega ad un database PostgreSQL installato nel proprio pc (localhost), con porta standard (5432), un utente `test` con password `test` ed un database chiamato `test`

Installare PostgreSQL nel proprio pc con le impostazioni standard. Con l'utente postgres eseguire i comandi:
* `create role test with login password 'test';`
   * output: `CREATE ROLE`
* `create database test with owner test;`
   * output: `CREATE DATABASE`

## Esecuzione
Dal package `com.azserve.academy.java.jdbc` cliccare con il tasto destro sulla classe `Database`, selezionare `Run As` e poi `Java Application`

Nella tab "Console" verranno visualuzzati i comandi, per prima cosa premere "1" e invio per creare le tabelle.

Verranno create due tabelle (vedi classe `Tables`) una `item` e una `movement`. 

La tabella `item` conterrà l'elenco degli articoli con id, codice e descrizione; il campo codice è univoco.

La tabelle `movement` ha una foreign key verso `item` e conterrà i movimenti di magazzino degli articoli. Ogni articolo avrà quindi più movimenti alcuni con quantità positiva, altri con quantità negativa.

Il progetto gestisce solo la tabella `item`, permette di inserire articoli random, cercarli per id o descrizione.

## Esercizio
Seguendo lo stile di `Item` e `ItemService`, creare le classi per la gestione dei movimenti.

* Dato l'id dell'articolo, la quantità e la data inserire un movimento
    * Extra: verificare che quel movimento non porti ad una giacenza negativa
* Dato l'id dell'articolo mostrarne la giacenza (usare la funzione SUM di SQL)
* Dato l'id dell'articolo mostrarne la giacenza (usare la funzione SUM di SQL) ad una certa data
* Cercare tutti gli articoli senza movimenti (tip: usare LEFT JOIN)
* Cercare tutti gli articoli con giacenza inferiore a 10 (tip: usare SUM e LEFT JOIN)
* Cercare tutti gli articoli con movimenti nell'ultimo mese (tip: JOIN)
* Cercare tutti i movimenti dell'ultimo mese mostrando a video codice articolo, descrizione, data movimento e quantità


