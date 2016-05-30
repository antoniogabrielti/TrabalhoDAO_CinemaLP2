package br.com.senacrs.consultorio.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lhries
 */
public class DateUtil {
    
    public static Date stringToDate(String data) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Nao permite adicionar acima do valor restrito da data.
        sdf.setLenient(false);
        return(sdf.parse(data));
    }

    public static Date stringToDateHour(String data) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //Nao permite adicionar acima do valor restrito da data.
        sdf.setLenient(false);
        return(sdf.parse(data));
    }
    
    public static String dateToString(Date data){
        return(new SimpleDateFormat("dd/MM/yyyy").format(data));
    }

    public static String dateHourToString(Date data){
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dataString = formatador.format(data);
        return(dataString);
    }
}
