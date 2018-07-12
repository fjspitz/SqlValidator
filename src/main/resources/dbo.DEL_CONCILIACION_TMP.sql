USE PROF
GO

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'DEL_CONCILIACION_TMP' AND u.name = 'dbo' AND o.type = 'P')
  BEGIN
    setuser 'dbo'
    drop procedure DEL_CONCILIACION_TMP
  END
go

CREATE PROCEDURE DEL_CONCILIACION_TMP
AS

  DROP TABLE tempdb..CONCILIACION_LOG

GO
sp_procxmode 'dbo.DEL_CONCILIACION_TMP', 'Anymode'
GO

GRANT EXECUTE ON DEL_CONCILIACION_TMP TO RolTrnPROF
GO
