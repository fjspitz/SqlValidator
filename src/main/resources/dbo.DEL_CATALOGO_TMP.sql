USE PROF
GO

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'DEL_CATALOGO_TMP' AND u.name = 'dbo' AND o.type = 'P')
  BEGIN
    setuser 'dbo'
    drop procedure DEL_CATALOGO_TMP
  END
go

CREATE PROCEDURE DEL_CATALOGO_TMP
AS

  DROP TABLE tempdb..TMP_CATALOGO_FUNCION

GO
sp_procxmode 'dbo.DEL_CATALOGO_TMP', 'Anymode'
GO

GRANT EXECUTE ON DEL_CATALOGO_TMP TO RolTrnPROF
GO
