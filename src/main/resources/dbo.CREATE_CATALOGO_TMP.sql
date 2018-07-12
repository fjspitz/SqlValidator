USE PROF
GO

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'CREATE_CATALOGO_TMP' AND u.name = 'dbo' AND o.type = 'P')
  BEGIN
    setuser 'dbo'
    drop procedure CREATE_CATALOGO_TMP
  END
go

CREATE PROCEDURE CREATE_CATALOGO_TMP
AS

  CREATE TABLE tempdb..TMP_CATALOGO_FUNCION
  (
      ambito varchar(50),
      plataforma varchar(50),
      codigoAPL varchar(50),
      descAPL varchar(50),
      codFuncion varchar(50) NOT NULL,
      descFuncion varchar(50),
      clase varchar(50) NOT NULL,
      codClase varchar(50) NOT NULL,
      descClase varchar(50),
      tipoAcceso varchar(50) NOT NULL,
      subClave varchar(50),
      codigoTRX varchar(50),
      descTRX varchar(50),
      cantPermiso varchar(8),
      dominio varchar(50) NOT NULL,
      id_recurso numeric(5) NOT NULL,
      id_funcion numeric(5) NOT NULL,
      id_subclave numeric(10)
  )

GO
sp_procxmode 'dbo.CREATE_CATALOGO_TMP', 'Anymode'
GO

GRANT EXECUTE ON CREATE_CATALOGO_TMP TO RolTrnPROF
GO
