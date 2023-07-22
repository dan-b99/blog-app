package com.blogapp.services.blog;

import com.blogapp.dtos.blog.VisualizzaVotoDTO;
import java.util.List;

public interface VotoService {
    List<VisualizzaVotoDTO> getLikesByArticoloId(Long articoloId);
    List<VisualizzaVotoDTO> getDislikesByArticoloid(Long articoloId);
}
