package br.ciar.domain.ocorrencias;

import org.springframework.beans.factory.annotation.Configurable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "encontropresencial")
@Configurable
public class EncontroPresencial extends Conferencia {

    public static long countEncontroPresencials() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EncontroPresencial o", Long.class).getSingleResult();
    }

    public static List<EncontroPresencial> findAllEncontroPresencials() {
        return entityManager().createQuery("SELECT o FROM EncontroPresencial o", EncontroPresencial.class).getResultList();
    }

    public static EncontroPresencial findEncontroPresencial(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(EncontroPresencial.class, id);
    }

    public static List<EncontroPresencial> findEncontroPresencialEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EncontroPresencial o", EncontroPresencial.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
