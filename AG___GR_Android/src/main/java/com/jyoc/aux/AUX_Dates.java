package com.jyoc.aux;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AUX_Dates {


/*

#######################################################################################
################                 UTILIDADES DATE                #######################
################               GUIA DE CONTENIDOS               #######################
#######################################################################################
#                                                                                     #
# DATE (JAVA)                                                                         #
# ###########                                                                         #
#    - esBisiesto                  (int anio)                                         #
#    - hoyEnDate                   ()                                                 #
#    - fechaEnDate                 (int dia, int mes, int anio)                       #
#    - dateToString                (Date fechaEnDate, String formato)                 #
#    - stringToDate                (String fechaEnString)                             #
#    - calendarToString            (Calendar fechaEnCalendar, String formato)         #
#    - stringToCalenda             (String fechaEnString)                             #
#    - dateUTILtoSQL               (java.util.Date fechaEnUtil                        #
#    - dateSQLtoUTIL               (java.sql.Date fechaEnSql)                         #
#    - hoyEnStringParaMySQL        ()                                                 #
#    - dateUtilEnStringParaMySQL   (java.util.Date fecha)                             #
#    - diferenciaEnDiasEntreDates  (Date fechaMayor, Date fechaMenor)                 #
#    - diferenciaEnAnosEntreDates  (Date fechaMayor, Date fechaMenor)                 #
#    - diferenciaEnMinutosEntreDates  (Date fechaMayor, Date fechaMenor)              #
#    - milisegundosConFormato         (long milisegundos, String formato)             #
#    - fechaEntreDosFechas    (Date fbuscada, Date finicio, Date ffin)                #
#                                                                                     #
# LOCALDATE (JAVA)                                                                    #
# ################                                                                    #
#    - localdateToString(LocalDate fechaEnlocaldate)                                  #
#    - stringToLocaldate(String fechaEnString)                                        #
#    - fechaEntreDosFechas    (LocalDate fbuscada, LocalDate finicio, LocalDate ffin) #                                                                             #
#    - localDateToDate(LocalDate localDate)                                           #
#    - dateToLocalDate(Date date)                                                     #
#    - diferenciaEnDiasEntreLocalDates(LocalDate fechaMayor, LocalDate fechaMenor)    #
#                                                                                     #
#                                                                                     #
#                                                                                     #
###################################### (fin) ##########################################
*/


/*
#######################################################################################
################                 CON     DATE                   #######################
#######################################################################################
 */


    /**
     * esBisiesto
     *
     * @param anio     año que deseamos comporbar si es bisiesto que
     * @return true si el año indicado es bisisesto, false en caso contrario
     */
    static public boolean esBisiesto(int anio) {
        if ((anio % 4 == 0 && anio % 100 != 0) || (anio % 100 == 0 && anio % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * hoyEnDate
     *
     * @return la fecha actual en formato DATE
     */
    public static Date hoyEnDate() {
        Calendar cc = Calendar.getInstance();
        Date hoyEnDate = cc.getTime();
        return hoyEnDate;
        // return Calendar.getInstance().getTime();

    }

    /**
     * fechaEnDate
     * Devuelve un objeto util.Date con los parámetros indicados
     * @param dia  dia de la fecha
     * @param mes  mes de la fecha (en numero, entre 1 y 12)
     * @param anio anio de la fecha
     * @return objeto Date con el formato indicado en los parámetros
     */
    public static Date fechaEnDate(int dia, int mes, int anio) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(anio, mes - 1, dia, 0, 0, 0);
        Date fecha = calendar.getTime();
        return fecha;
    }

    /**
     * dateToString
     * <p>
     * convertir de un Date de java.util a STRING, con un formato
     *
     * @param fechaEnDate Objeto Date de la fecha a cambiar
     * @param formato     formato, como p.e. "dd/MM/yyyy" Si se idica null, se usa
     *                    por defecto "dd/MM/yyyy"
     * @return fecha en string en dicho formato, por ejemplo, "12/22/2016", o null
     * si la fecha del parametro es null
     */
    public static String dateToString(Date fechaEnDate, String formato) {
        if (fechaEnDate == null) {
            return null;
        }
        if (formato == null) {
            formato = "dd/MM/yyyy";
        }
        SimpleDateFormat miFormato = new SimpleDateFormat(formato);
        String fechaEnString = miFormato.format(fechaEnDate);
        return fechaEnString;
    }

    /**
     * stringToDate
     * <p>
     * Convierte un String en un util.Date
     *
     * @param fechaEnString Objeto String de la fecha a cambiar
     * @param formato       formato, como p.e. "dd/MM/yyyy" Si se idica null, se usa
     *                      por defecto "dd/MM/yyyy"
     * @return fecha en formato java.util o null si la fecha del parametro es null
     */
    public static Date stringToDate(String fechaEnString, String formato) {
        if (fechaEnString == null) {
            return null;
        }
        if (formato == null) {
            formato = "dd/MM/yyyy";
        }
        Date fechaenjava = null;
        SimpleDateFormat miFormato2 = new SimpleDateFormat(formato);
        try {
            fechaenjava = miFormato2.parse(fechaEnString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaenjava;
    }

    /**
     * calendarToString
     * <p>
     * convertir de un Calendar a STRING, con un formato
     *
     * @param fechaEnCalendar Objeto Calendar de la fecha a cambiar
     * @param formato         formato, como p.e. "dd/MM/yyyy" Si se idica null, se usa
     *                        por defecto "dd/MM/yyyy"
     * @return fecha en string en dicho formato, por ejemplo, "12/22/2016", o null
     * si la fecha del parametro es null
     */
    public static String calendarToString(Calendar fechaEnCalendar, String formato) {
        if (fechaEnCalendar == null) {
            return null;
        }
        if (formato == null) {
            formato = "dd/MM/yyyy";
        }

        Date fechaEnDate = fechaEnCalendar.getTime();
        SimpleDateFormat miFormato = new SimpleDateFormat(formato);
        String fechaEnString = miFormato.format(fechaEnDate);
        return fechaEnString;
    }

    /**
     * stringToCalendar
     * <p>
     * Convierte un String en un Calendar
     *
     * @param fechaEnString Objeto String de la fecha a cambiar
     * @param formato       Formato, como p.e. "dd/MM/yyyy" Si se idica null, se usa
     *                      por defecto "dd/MM/yyyy"
     * @return fecha en formato Calendar o null si la fecha del parametro es null
     */
    public static Calendar stringToCalendar(String fechaEnString, String formato) {
        if (fechaEnString == null) {
            return null;
        }
        if (formato == null) {
            formato = "dd/MM/yyyy";
        }

        SimpleDateFormat miFormato2 = new SimpleDateFormat(formato);
        Date fechaenjava = null;
        try {
            fechaenjava = miFormato2.parse(fechaEnString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaenjava);
        return calendar;
    }


    static public int horaDeldia(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY); // en formato 24h format
    }

    static public int minutoDeldia(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    static  public int diaDelMes(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH); // en formato 24h format
    }

    static public int diaDelaSemana(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * dateUTILtoSQL
     * <p>
     * convertir de un Date de java.util a u Date de Sql
     *
     * @param fechaEnUtil Objeto Date de la fecha a cambiar, com oDate de UTIL
     * @return fecha en Date, como Date de SQL, o null si la fecha del parametro es
     * null
     */
    public static java.sql.Date dateUTILtoSQL(Date fechaEnUtil) {
        if (fechaEnUtil == null) {
            return null;
        }
        java.sql.Date fechaEnSql = null;
        fechaEnSql = new java.sql.Date(fechaEnUtil.getTime());
        return fechaEnSql;
    }

    /**
     * dateSQLtoUTIL
     * <p>
     * convertir de un Date de java.util a u Date de Sql
     *
     * @param fechaEnSql Objeto Date de la fecha a cambiar, com oDate de SQL
     * @return fecha en UTIL , COMO Date de UTIL, o null si la fecha del parametro
     * es null
     */
    public static Date dateSQLtoUTIL(java.sql.Date fechaEnSql) {
        if (fechaEnSql == null) {
            return null;
        }
        Date fechaEnUtil = null;
        // opcion 1, la mas correcta
        fechaEnUtil = new Date(fechaEnSql.getTime());
        // opcion 2, la mas simple, con casting implícito, que NO HACE FALTA HACER TRANSFORMACION
        // fechaEnUtil = fechaEnSql;
        return fechaEnUtil;
    }

    /**
     * hoy_enStringParaMySQL
     * <p>
     * Conseguir el String del dia de hoy para una query de MySql
     *
     * @return fecha de hoy com STRING para consulas MYSQL
     */
    public static String hoyEnStringParaMySQL() {
        Calendar cc = Calendar.getInstance();
        Date hoyEnDate = cc.getTime();
        SimpleDateFormat miFormato = new SimpleDateFormat("dd/MM/yyyy");
        String hoyEnString = miFormato.format(hoyEnDate);
        return "STR_TO_DATE('" + hoyEnString + "','%d/%m/%Y')";
    }

    /**
     * dateUtil_enStringParaMySQL
     * <p>
     * Conseguir el String del dia que se pasa por parametro para una query de MySql
     *
     * @return fecha de hoy com STRING para consulas MYSQL, o null si la fecha del
     * parametro es null
     */

    public static String dateUtil_enStringParaMySQL(Date fecha) {
        if (fecha == null) {
            return null;
        }
        String hoyEnStringSql = null;
        SimpleDateFormat miFormato = new SimpleDateFormat("dd/MM/yyyy");
        String hoyEnString = miFormato.format(fecha);
        hoyEnStringSql = "STR_TO_DATE('" + hoyEnString + "','%d/%m/%Y')";
        return hoyEnStringSql;
    }

    /**
     * diferenciaEnDiasEntreDates
     * <p>
     * devuelve el numero de dias existente entre dos date
     *
     * @param fechaMayor
     * @param fechaMenor
     * @return dias de diferencia como un int, o -1 si alguna fecha de los
     * parametros es null o la fecha menor no es menor que la mayor
     */

    public static int diferenciaEnDiasEntreDates(Date fechaMayor, Date fechaMenor) {
        if (fechaMayor == null || fechaMenor == null || fechaMayor.getTime() < fechaMenor.getTime()) {
            return -1;
        }
        long diferenciaEnMilisegs = fechaMayor.getTime() - fechaMenor.getTime();
        long dias = diferenciaEnMilisegs / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

    /**
     * diferenciaEnAnosEntreDates
     * <p>
     * devuelve el numero de años existente entre dos date
     *
     * @param fechaMayor
     * @param fechaMenor
     * @return años de diferencia como un int, o -1 si alguna fecha de los
     * parametros es null o la fecha menor no es menor que la mayor
     */
    public static int diferenciaEnAnosEntreDates(Date fechaMayor, Date fechaMenor) {
        if (fechaMayor == null || fechaMenor == null || fechaMayor.getTime() < fechaMenor.getTime()) {
            return -1;
        }
        long diferenciaEnMilisegs = fechaMayor.getTime() - fechaMenor.getTime();
        long dias = diferenciaEnMilisegs / (1000 * 60 * 60 * 24);
        return (int) (dias / 356) - 1;
    }

    /**
     * diferenciaEnMinutosEntreDates
     * <p>
     * devuelve el numero de minutos existente entre dos date,
     *
     * @param fechaMayor
     * @param fechaMenor
     * @return minutos de diferencia como un int, o -1 si alguna fecha de los
     * parametros es null o la fecha menor no es menor que la mayor
     */
    public static int diferenciaEnMinutosEntreDates(Date fechaMayor, Date fechaMenor) {
        if (fechaMayor == null || fechaMenor == null || fechaMayor.getTime() < fechaMenor.getTime()) {
            return -1;
        }
        long diferenciaEnMilisegs = fechaMayor.getTime() - fechaMenor.getTime();
        long minutos = diferenciaEnMilisegs / (1000 * 60);
        return (int) minutos;
    }

    /*
     * edadEnAnios <p> devuelve un int que representa el numero de años enteros
     * desde una edad dada hasta hoy
     *
     * @param anonacimiento date con la fecha de nacimiento
     *
     * @return int con la edad en anios, o -1 si la fecha del parametro es null
     */
    public static int edadEnAnios(Date anonacimiento) {
        if (anonacimiento == null) {
            return -1;
        }

        Date hoyEnDate = Calendar.getInstance().getTime();

        Calendar a = Calendar.getInstance();
        a.setTime(anonacimiento);
        Calendar b = Calendar.getInstance();
        b.setTime(hoyEnDate);

        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
                && a.get(Calendar.DAY_OF_MONTH) > b.get(Calendar.DAY_OF_MONTH))) {
            diff--;
        }
        return diff;
    }

    /**
     * milisegundosConFormato
     * <p>
     * devuelve un Stroing que representa un numero de milisegundos, segun un
     * formato establecido del orden de las fechas
     *
     * @param milisegundos milisegundos a calcular
     * @param formato      String que representa el formato de salida. Ejepmlos:
     *                     "dd-MM-yy" , "mm:ss"
     * @return String con la fecha formateada
     */
    public static String milisegundosConFormato(long milisegundos, String formato) {
        Date date = new Date(milisegundos);
        DateFormat formatter = new SimpleDateFormat(formato);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    /**
     * fechaEntreDosFechas
     * Devuelve true o false si una fecha dada esta entre otras dos fechas limite
     * Los parametros son objetos Date
     *
     * @param fbuscada Fecha a comparar (formato Date)
     * @param finicio  Fecha inicio del limite a buscar (formato Date)
     * @param ffin     Fecha fin del limite a buscar (formato Date)
     * @return true si la fecha a comparar esta entre las otras dos fechas limites, false en caso contrario
     */
    public static boolean fechaEntreDosFechas(Date fbuscada, Date finicio, Date ffin) {
        if (fbuscada.compareTo(finicio) >= 0 && fbuscada.compareTo(ffin) <= 0) {
            return true;
        }
        return false;
    }



/*
#######################################################################################
################                 CON     LOCALDATE              #######################
#######################################################################################
 */


    /**
     * localdateToString
     * <p>
     * Convierte una fecha localdate en un string, siempre con el formato "dd/MM/yyyy"
     * Si se usa otro formato, que sea siempre de fecha completa
     * Con LocalDate no es optimo hablar solo de un mes o un año, pues existen Month y Year para ello
     *
     * @param fechaEnlocaldate fecha a traducir
     * @return string con la fecha convertida
     */
    public static String localdateToString(LocalDate fechaEnlocaldate) {
        if (fechaEnlocaldate == null) {
            return null;
        }
        return fechaEnlocaldate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * stringToLocaldate
     * <p>
     * Convierte un string, siempre con el formato "dd/MM/yyyy", a una fecha localdate
     * Si se usa otro formato, que sea siempre de fecha completa
     * Con LocalDate no es optimo hablar solo de un mes o un año, pues existen Month y Year para ello
     *
     * @param fechaEnString fecha a transformar
     * @return fecha transformada en localdate
     */
    public static LocalDate stringToLocaldate(String fechaEnString) {
        if (fechaEnString == null) {
            return null;
        }
        return LocalDate.parse(fechaEnString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * fechaEntreDosFechas
     * Devuelve true o false si una fecha dada esta entre otras dos fechas limite
     * Los parametros son objetos LocalDate
     *
     * @param fbuscada Fecha a comparar (formato LocalDate)
     * @param finicio  Fecha inicio del limite a buscar (formato LocalDate)
     * @param ffin     Fecha fin del limite a buscar (formato LocalDate)
     * @return true si la fecha a comparar esta entre las otras dos fechas limites, false en caso contrario
     */
    public static boolean fechaEntreDosFechas(LocalDate fbuscada, LocalDate finicio, LocalDate ffin) {
        if (fbuscada.compareTo(finicio) >= 0 && fbuscada.compareTo(ffin) <= 0) {
            return true;
        }
        return false;
    }


    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }


    public static LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static long diferenciaEnDiasEntreLocalDates(LocalDate fechaMayor, LocalDate fechaMenor) {
        long diasdediferencia = ChronoUnit.DAYS.between(fechaMenor, fechaMayor);
        return diasdediferencia;
    }


    //  // USANDO LA LIBRERIA JODATIME
    //
    //
    //    /**
    //     * convierte un java.time.LocalDate en un org.joda.time.DateTime
    //     */
    //    public static DateTime localDatetoDateTime(LocalDate unLocalDate) {
    //        return new DateTime(unLocalDate.getYear(), unLocalDate.getMonthValue(), unLocalDate.getDayOfMonth(), 0, 0, 0, 0);
    //    }
    //
    //    /**
    //     * convierte un org.joda.time.DateTime en un java.time.LocalDate
    //     */
    //    public static LocalDate dateTimetoLocalDate(DateTime unDateTime) {
    //        return LocalDate.of(unDateTime.getYear(), unDateTime.getMonthOfYear(), unDateTime.getDayOfMonth());
    //    }
    //
    //    /**
    //     * @param unoInicio
    //     * @param unoFinal
    //     * @param dosInicio
    //     * @param dosFinal
    //     * @return
    //     */
    //    public static boolean seSolapan(LocalDate unoInicio, LocalDate unoFinal, LocalDate dosInicio, LocalDate dosFinal) {
    //        unoFinal = unoFinal.plusDays(1);
    //        dosFinal = dosFinal.plusDays(1);
    //        DateTime unoIni = new DateTime(unoInicio.getYear(), unoInicio.getMonthValue(), unoInicio.getDayOfMonth(), 0, 0, 0, 0);
    //        DateTime unoFin = new DateTime(unoFinal.getYear(), unoFinal.getMonthValue(), unoFinal.getDayOfMonth(), 0, 0, 0, 0);
    //        DateTime dosIni = new DateTime(dosInicio.getYear(), dosInicio.getMonthValue(), dosInicio.getDayOfMonth(), 0, 0, 0, 0);
    //        DateTime dosFin = new DateTime(dosFinal.getYear(), dosFinal.getMonthValue(), dosFinal.getDayOfMonth(), 0, 0, 0, 0);
    //        Interval inter1 = new Interval(unoIni, unoFin);
    //        Interval inter2 = new Interval(dosIni, dosFin);
    //        boolean solucion = inter1.overlaps(inter2);
    //        return solucion;
    //    }



}

