# Esempi di programmazione Java

## Importazione progetto in eclipse
1. Cliccare su `File` -> `Import` 
1. Comparirà un wizard
   1. **Select**: selezionare `Git` -> `Projects from Git (with smart import)`
   1. **Select repository source**: selezionare `Clone URI`
   1. **Source Git Repository**: nel campo `URI` inserire il repository del progetto `git@gitlab.com:azserve/academy/java.git`, gli altri campi verranno compilati automaticamente
   1. **Branch Selection**: selezionare master
   1. **Local Destination**: dovrebbe proporre `propria-home/git/java`, confermare
   1. **Import Project from File System or Archive**: dovrebbe avere già i valori preimpostati, verificare che tutti i progetti siano selezionati e cliccare su `Finish`
1. Nella tab "Project Explorer" comparirà un progetto `java` e al suo interno una directory `eclipse` e un sotto progetto `jdbc`.
1. Importazione preferenze (formattazione del codice, errori/warning ...)
   1. Cliccare su `Window` -> `Preferences`
   1. Nel pop up selezionare il tasto in basso a sinistra `import`
   1. Selezionare il file `propria-home/git/java/eclipse/preferences.epf`
   1. Selezionare `import all` e confermare
   1. Riavviare eclipse

