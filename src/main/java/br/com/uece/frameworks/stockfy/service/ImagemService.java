package br.com.uece.frameworks.stockfy.service;

import br.com.uece.frameworks.stockfy.model.Imagem;
import br.com.uece.frameworks.stockfy.repository.ImagemRepository;
import br.com.uece.frameworks.stockfy.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Create by Bruno Barbosa - 15/10/2018
 */
@RestController
@RequestMapping("/api/imagem")
public class ImagemService extends GenericService<Imagem> {

    private final ImagemRepository imagemRepository;

    @Autowired
    public ImagemService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }

    @RequestMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public @ResponseBody byte[] getImagem(@PathVariable("id") Long id) {
        Optional<Imagem> imagem = imagemRepository.findById(id);
        return imagem.map(ImageUtil::getImage).orElse(null);
    }
}
