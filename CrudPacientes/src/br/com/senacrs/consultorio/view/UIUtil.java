/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senacrs.consultorio.view;

import br.com.senacrs.consultorio.util.Console;

/**
 *
 * @author lhries
 */
public class UIUtil {

    public static final String SIM = "sim";
    public static final String NAO = "nao";
    
    public static void mostrarErro(Object msgErro) {
        System.err.println(msgErro);
    }
    
    public static boolean getConfirmacao(Object msg) {
        String confirmacao = "NAO";
        do {
            confirmacao = Console.scanString(msg+"(sim/nao)");
            if (confirmacao.equalsIgnoreCase(SIM)) {
                return true;
            }
            else if(confirmacao.equalsIgnoreCase(NAO)){
                return false;
            }
            else{
                System.out.println("Opcao invalida!");
            }                
        }while(confirmacao.equalsIgnoreCase(SIM) || 
                confirmacao.equalsIgnoreCase(NAO));
        return false;
    } 
    
}
