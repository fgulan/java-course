<property name="hibernate.hbm2ddl.auto" value="create-drop" />

Tek kad sam postavio value na create-drop tomcat je prestao zezati. Inače ako se stavi
update za value onda nekad tomcat javlja grešku da nije uspio dodati constraintove u tablice 
u bazi pa onda moram svaki put dropat sve tablice iz baze pa onda ponovno deployat war file, nakon
toga radi bez problema.