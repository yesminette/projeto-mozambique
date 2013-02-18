package br.ciar.domain.informativos;

import org.springframework.beans.factory.annotation.Configurable;
import br.ciar.domain.ocorrencias.Ocorrencia;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Configurable
public class Review extends Noticia {

    @OneToOne(mappedBy="review")
    private Ocorrencia ocorrencia;

    public Ocorrencia getOcorrencia() {
        return this.ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public static long countReviews() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Review o", Long.class).getSingleResult();
    }

    public static List<Review> findAllReviews() {
        return entityManager().createQuery("SELECT o FROM Review o", Review.class).getResultList();
    }

    public static Review findReview(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Review.class, id);
    }

    public static List<Review> findReviewEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Review o", Review.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List getReviews(String[] palavrasChave, int primeiroResultado, int maximoResultados) {
        String query = "SELECT o FROM Review o";

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

        return entityManager().createQuery(query, Review.class).setFirstResult(primeiroResultado).setMaxResults(maximoResultados).getResultList();
    }

    public static long countReviews(String[] palavrasChave) {
        String query = "SELECT COUNT(o) FROM Review o";

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
        sb.append("Ocorrencia: ").append(getOcorrencia()).append(", ");
        sb.append("Titulo: ").append(getTitulo());
        return sb.toString();
    }
}
