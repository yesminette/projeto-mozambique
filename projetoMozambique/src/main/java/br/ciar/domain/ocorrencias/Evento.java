package br.ciar.domain.ocorrencias;

import org.springframework.beans.factory.annotation.Configurable;
import java.util.List;
import javax.persistence.Entity;

@Entity
@Configurable
public class Evento extends Ocorrencia {
    public static long countEventos() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Evento o", Long.class).getSingleResult();
    }

    public static List<Evento> findAllEventos() {
        return entityManager().createQuery("SELECT o FROM Evento o", Evento.class).getResultList();
    }

    public static Evento findEvento(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Evento.class, id);
    }

    public static List<Evento> findEventoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Evento o", Evento.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comentarios: ").append(getComentarios() == null ? "null" : getComentarios().size()).append(", ");
        sb.append("Descricao: ").append(getDescricao()).append(", ");
        sb.append("Fim: ").append(getFim()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Inicio: ").append(getInicio()).append(", ");
        sb.append("Local_ocorrencia: ").append(getLocalOcorrencia()).append(", ");
        sb.append("Mapa: ").append(getMapa()).append(", ");
        sb.append("Nome: ").append(getNome()).append(", ");
        sb.append("Review: ").append(getReview());
        return sb.toString();
    }
}