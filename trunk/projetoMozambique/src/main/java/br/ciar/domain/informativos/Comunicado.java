package br.ciar.domain.informativos;

import java.util.List;
import javax.persistence.Entity;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
public class Comunicado extends Informativo {

    public static long countComunicados() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Comunicado o", Long.class).getSingleResult();
    }

    public static List<Comunicado> findAllComunicadoes() {
        return entityManager().createQuery("SELECT o FROM Comunicado o", Comunicado.class).getResultList();
    }

    public static Comunicado findComunicado(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Comunicado.class, id);
    }

    public static List<Comunicado> findComunicadoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Comunicado o", Comunicado.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List getComunicados(String[] palavrasChave, int primeiroResultado, int maximoResultados) {
        String query = "SELECT o FROM Comunicado o";

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

        return entityManager().createQuery(query, Comunicado.class).setFirstResult(primeiroResultado).setMaxResults(maximoResultados).getResultList();
    }

    public static long countComunicados(String[] palavrasChave) {
        String query = "SELECT COUNT(o) FROM Comunicado o";

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
