package br.ciar.domain.ocorrencias;

import org.springframework.beans.factory.annotation.Configurable;
import javax.validation.constraints.Size;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Configurable
public class Conferencia extends Ocorrencia {

    @Size(max = 255)
    private String cliente;
    
    public String getCliente() {
        return this.cliente;
    }
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public static long countConferencias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Conferencia o", Long.class).getSingleResult();
    }

    public static List<Conferencia> findAllConferencias() {
        return entityManager().createQuery("SELECT o FROM Conferencia o", Conferencia.class).getResultList();
    }

    public static Conferencia findConferencia(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Conferencia.class, id);
    }

    public static List<Conferencia> findConferenciaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Conferencia o", Conferencia.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(getCliente()).append(", ");
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
