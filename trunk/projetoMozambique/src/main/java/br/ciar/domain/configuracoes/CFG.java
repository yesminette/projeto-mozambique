/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.domain.configuracoes;

/**
 *
 * @author Lauro
 */
public class CFG {
    /*
     * *********************************************************************************
     */

    private static final TipoConfiguracao tipoConfiguracao = TipoConfiguracao.OFICIAL;
    /*
     * *********************************************************************************
     */
    public static final int ID_CLIPPING = 0;
    public static final String contextPath = tipoConfiguracao == TipoConfiguracao.TESTE ? "/projetoMozambique" : "";

    public enum TipoConfiguracao {

        OFICIAL, TESTE
    }
}
