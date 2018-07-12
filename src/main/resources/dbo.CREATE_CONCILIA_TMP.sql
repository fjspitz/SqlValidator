USE PROF
GO

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'CREATE_CONCILIA_TMP' AND u.name = 'dbo' AND o.type = 'P')
  BEGIN
    setuser 'dbo'
    drop procedure CREATE_CONCILIA_TMP
  END
go

CREATE PROCEDURE CREATE_CONCILIA_TMP
AS

  CREATE TABLE tempdb..CONCILIACION_LOG
  (
      codigo varchar(71),
      elemento varchar(24) NOT NULL,
      relacion varchar(50),
      mensaje varchar(29) NOT NULL,
      archivo varchar(50),
      linea int,
      fecha datetime NOT NULL,
      lineacompleta varchar(500)
  )

GO
sp_procxmode 'dbo.CREATE_CONCILIA_TMP', 'Anymode'
GO

GRANT EXECUTE ON CREATE_CONCILIA_TMP TO RolTrnPROF
GO
