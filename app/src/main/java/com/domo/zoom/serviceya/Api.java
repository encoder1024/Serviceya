package com.domo.zoom.serviceya;

public class Api {
    private static final String ROOT_URL = "http://www.prontoservice.com.ar/pspia/v1/Api.php?apicall=";
    static final String ROOT_URL_IMAGES = "http://www.prontoservice.com.ar/pspia/imagenes/";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";
    public static final String URL_READ_USER = ROOT_URL + "readUser&email=";
    public static final String URL_READ_USER_TEST = ROOT_URL + "readUser&email=";
    public static final String URL_READ_MARCAS_POR_TIPO_VEHI = ROOT_URL + "readMarcas&tipoVehiculo=";
    public static final String URL_READ_MODELOS_POR_MARCA_VEHI = ROOT_URL + "readModelos&id=";
    public static final String URL_WRITE_ACT_USER = ROOT_URL + "writeDataUser";
    public static final String URL_WRITE_ADD_USER = ROOT_URL + "createUser";
    public static final String URL_WRITE_REG_CALL = ROOT_URL + "regCall";
    public static final String URL_LOGIN_PRES = ROOT_URL + "getPrestadorEmail";

    static final String URL_READ_SITIOS_FULL = ROOT_URL + "getsitiofull&idSitio=";
    static final String URL_READ_CATEGORIAS = ROOT_URL + "getCategorias&grupoName=";
    static final String URL_READ_PRESTADORES = ROOT_URL + "getProCategoria&categName=";
    static final String URL_READ_PRESTADORES_SEARCH_INICIAL = ROOT_URL + "getProSearchIni&que=";
    static final String URL_READ_PRESTADORES_SEARCH = ROOT_URL + "getProSearch&provincia=";
    static final String URL_READ_PROVINCIAS = ROOT_URL + "getProvincias&queBusco=";
    static final String URL_READ_LOCALIDADES = ROOT_URL + "getLocalidades&provincia=";
    static final String URL_READ_GRUPOS = ROOT_URL + "getGrupos&queBusco=";
    static final String URL_READ_SERVICE_CAT = ROOT_URL + "getServicioCat";
    static final String URL_READ_SERVICE_BUS = ROOT_URL + "getServicioBus";

    public static final String URL_UPDATE_USER_TOKEN_FB = ROOT_URL + "settokenfb&idUser=";
    public static final String URL_WRITE_ADD_PRO = ROOT_URL + "writeCreatePro";
    public static final String URL_WRITE_ACT_PRO = ROOT_URL + "writeDataPro";
    public static final String URL_READ_PRO = ROOT_URL + "readPro&email=";
    public static final String URL_READ_SITIOS_POR_MARCATIPO = ROOT_URL + "readSitioMarca&idMarca=";
}
