use PROF
go

IF EXISTS (SELECT 1 FROM sysobjects o, sysusers u WHERE o.uid=u.uid AND o.name = 'BATCH_FUN_PA_ID' AND u.name = 'dbo' AND o.type = 'P')
  BEGIN
    setuser 'dbo'
    drop procedure BATCH_FUN_PA_ID
  END
go

CREATE PROCEDURE BATCH_FUN_PA_ID
AS

SELECT p.codigo puesto,a.codigo area,paf.id_funcion,pafa.id_facultad,pafa.importe1,pafa.importe2
INTO #PUESTOAREA
FROM
  PUESTO p,
  AREA a,
  PUESTOAREA pa
  INNER JOIN PUESTOAREA_FUNCION paf ON pa.id = paf.id_puesto_area
  LEFT JOIN PUESTOAREA_FACULTAD pafa ON paf.id = pafa.id_puest_area_fun
WHERE p.id = pa.fk_id_puesto
      AND pa.fk_id_area = a.id

SELECT RF.ambito,RF.plataforma,RF.codigoAPL,RF.descAPL,RF.codFuncion,RF.descFuncion,RF.clase,
  RF.codClase,RF.descClase,RF.tipoAcceso,RF.subClave,P.importe1,P.importe2,RF.codigoTRX,
  RF.descTRX,P.puesto,P.area,RF.cantPermiso,RF.dominio
FROM TMP_CATALOGO_FUNCION RF
  LEFT JOIN #PUESTOAREA P ON P.id_funcion = RF.id_funcion AND P.id_facultad = RF.id_recurso
WHERE (P.puesto <> NULL OR P.area <> NULL) AND RF.id_subclave <> 0
GROUP BY RF.ambito,RF.plataforma,RF.codigoAPL,RF.descAPL,RF.codFuncion,RF.descFuncion,RF.clase,
  RF.codClase,RF.descClase,RF.tipoAcceso,RF.subClave,P.importe1,P.importe2,RF.codigoTRX,
  RF.descTRX,P.puesto,P.area,RF.cantPermiso,RF.dominio

UNION ALL

SELECT RF.ambito,RF.plataforma,RF.codigoAPL,RF.descAPL,RF.codFuncion,RF.descFuncion,RF.clase,
  RF.codClase,RF.descClase,RF.tipoAcceso,RF.subClave,P.importe1,P.importe2,RF.codigoTRX,
  RF.descTRX,P.puesto,P.area,RF.cantPermiso,RF.dominio
FROM TMP_CATALOGO_FUNCION RF
  LEFT JOIN #PUESTOAREA P ON P.id_funcion = RF.id_funcion
WHERE (P.puesto <> NULL OR P.area <> NULL) AND RF.id_subclave = 0
GROUP BY RF.ambito,RF.plataforma,RF.codigoAPL,RF.descAPL,RF.codFuncion,RF.descFuncion,RF.clase,
  RF.codClase,RF.descClase,RF.tipoAcceso,RF.subClave,P.importe1,P.importe2,RF.codigoTRX,
  RF.descTRX,P.puesto,P.area,RF.cantPermiso,RF.dominio

GO
sp_procxmode 'dbo.BATCH_FUN_PA_ID', 'Anymode'
GO

GRANT EXECUTE ON BATCH_FUN_PA_ID TO RolTrnPROF
GO
