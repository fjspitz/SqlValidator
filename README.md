# SqlValidator

![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_SqlValidator&metric=alert_status)
[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/2276/badge)](https://bestpractices.coreinfrastructure.org/projects/2276)
![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_SqlValidator&metric=sqale_index)
![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_SqlValidator&metric=coverage)
![Lines of code](https://sonarcloud.io/api/project_badges/measure?project=fjspitz_SqlValidator&metric=ncloc)

##### v1.0

##### lunes, 06. agosto 2018 06:24 

Pequeña aplicación para realizar validaciones simples sobre script de base de datos.

Esta primera versión apunta al DBMS **Sybase ASE versión 15.7**.

La idea es tomar un script como archivo de entrada, por ejemplo un script DDL de Stored Procedure y validar que dentro del script se encuentren ciertas instrucciones en un cierto orden.

Esta utilidad viene a partir de conflictos que ocurren cuando un externo, como una Software Factory, hace entrega de estos scripts de base de datos.

Si no son lo suficientemente cuidadosos, se pueden producir inconsistencias difíciles de encontrar.

Un ejemplo:

Una validación requerida es que la primer orden o cláusula del script realice un posicionamiento sobre la base de datos a trabajar.

Si no se controla esto, puede ocurrir que al intentar ejecutar el script desde una consola o herramienta del DBMS, la primer conexión se hace sobre la base master. Al ejecutar el script sobre dicha base, se produce un error (ya sea porque las tablas referenciadas no existen, etc.)

Otra validación de ejemplo:

Se requiere que el nombre del archivo de script se corresponda con el objeto a crear/modificar y responsa a un patrón determinado. Por ejemplo, si el script refiere al stored procedure llamado **sp_ActualizacionDeIndiceDePrecios**, entonces el script debe llamarse *DDL_sp_ActualizacionDeIndiceDePrecios.sql*.

### Premisas

Cada elemento u objeto de base de datos que es creado, lo es a partir de un archivo de script que puede ejecutarse en el DBMS.


 
