package com.blogapp.services.blog;

import com.blogapp.dtos.blog.*;
import java.util.List;

public interface ArticoloService {

    void aggiungi(AggiuntaArticoloDTO articolo);
    List<VisualizzaArticoloDTO> getAllNotApprovati();
    List<VisualizzaArticoloDTO> getAllApprovati();
    VisualizzaArticoloDTO notApprovedById(Long id);
    void approveArticle(Long id);
    void deleteArticolo(Long id);
    VisualizzaArticoloDTO approvedById(Long id);
    List<VisualizzaArticoloDTO> byCategorie(Long... ids);
    List<VisualizzaArticoloDTO> byTags(String... tags);
    List<VisualizzaArticoloDTO> byContenutoOrTitolo(String keyword);
    void setValidazioniArticolo(ValidazioneDinamicaBlogDTO validazioneDinamicaBlogDTO);
    void setVote(AggiuntaVotoDTO voto);
    void addComment(AggiuntaCommentoDTO comment);
    void addReply(AggiuntaRispostaDTO reply);
}
