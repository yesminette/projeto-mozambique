package br.ciar.utils;

/**
 *
 * @author Carlo Rafael Rodovalho Cesar
 */
public class Paginacao {

    public static long calculaPaginas(long registros,long itensPorPagina){
        double paginas =  Math.ceil((double) registros/ (double)itensPorPagina);
        return (long) paginas;
    }

   /**
    * Método que retorna qual será o índice do primeiro item a ser buscado, dado a quantidade
    * de itens disponíveis para mostrar e a quantidade de ítens a ser mostrado em cada página.
    * @param pagina O número da página solicitada.
    * @param itensPorPagina A quantidade de ítens a ser mostrada por página.
    * @return O índice inicial da busca (zero inclusive).
    */
    public static int inicioItens(int pagina,int itensPorPagina){
        return (pagina - 1) * itensPorPagina;
    }
}
