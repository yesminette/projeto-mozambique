package br.ciar.domain.informativos;

import java.util.List;
import javax.persistence.Entity;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
public class Artigo extends Informativo {

    public static long countArtigos() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Artigo o", Long.class).getSingleResult();
    }

    public static List<Artigo> findAllArtigoes() {
        return entityManager().createQuery("SELECT o FROM Artigo o", Artigo.class).getResultList();
    }

    public static Artigo findArtigo(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Artigo.class, id);
    }

    public static List<Artigo> findArtigoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Artigo o", Artigo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List getArtigos(String[] palavrasChave, int primeiroResultado, int maximoResultados) {
        String query = "SELECT o FROM Artigo o";

        if (palavrasChave.length > 0) {
            query += " WHERE ";

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.descricao) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.conteudo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.titulo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            // Remover o último OR que sobrou (ou se não teve nada, remover o Where)
            int aRemover = query.lastIndexOf("OR");
            if(aRemover < 0){
                aRemover = query.lastIndexOf("WHERE");
            }
            query = query.substring(0, aRemover);
        }

        query += " ORDER BY o.dataInformativo DESC";

        return entityManager().createQuery(query, Artigo.class).setFirstResult(primeiroResultado).setMaxResults(maximoResultados).getResultList();
    }

    public static long countArtigos(String[] palavrasChave) {
        String query = "SELECT COUNT(o) FROM Artigo o";

        if (palavrasChave.length > 0) {
            query += " WHERE ";

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.descricao) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.conteudo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.titulo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            // Remover o último OR que sobrou (ou se não teve nada, remover o Where)
            int aRemover = query.lastIndexOf("OR");
            if(aRemover < 0){
                aRemover = query.lastIndexOf("WHERE");
            }
            query = query.substring(0, aRemover);
        }
        return entityManager().createQuery(query, Long.class).getSingleResult();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Conteudo: ").append(getConteudo()).append(", ");
        sb.append("Data_informativo: ").append(getDataInformativo()).append(", ");
        sb.append("Descricao: ").append(getDescricao()).append(", ");
        sb.append("Destaque: ").append(isDestaque()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Titulo: ").append(getTitulo());
        return sb.toString();
    }
}
