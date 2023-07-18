package com.blogapp.services.blog;

import com.blogapp.dtos.blog.AggiuntaCommentoDTO;
import com.blogapp.dtos.blog.AggiuntaRispostaDTO;
import com.blogapp.dtos.blog.VisualizzaCommentoDTO;
import com.blogapp.dtos.blog.VisualizzaRispostaDTO;
import java.util.List;

public interface CommentoService {

    void addComment(AggiuntaCommentoDTO comment);
    List<VisualizzaCommentoDTO> getCommentsByArtId(Long id);
    void addReply(AggiuntaRispostaDTO reply);
    //List<VisualizzaRispostaDTO> getRepliesByArtId(Long id);
    List<VisualizzaRispostaDTO> getRepliesByArtIdAndCommId(Long artId, Long commId);
}
