package br.com.uece.frameworks.stockfy.service;

import br.com.uece.frameworks.stockfy.service.exceptions.DataIntegrityException;
import br.com.uece.frameworks.stockfy.service.exceptions.ObjectNotFoundException;
import br.com.uece.frameworks.stockfy.util.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * GenericService
 */
public abstract class GenericService<Entity extends BaseEntity<Long>> {

    @Autowired
    protected JpaRepository<Entity, Long> repository;

    public Entity findById(Long id) {
        Optional<Entity> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id));
    }

    public Entity save(Entity entity) {
        return repository.save(entity);
    }

    public List<Entity> saveAll(List<Entity> entities) {
        return repository.saveAll(entities);
    }

    public void deleteById(Long id) {
        findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não foi possível excluir!");
        }
    }

    public List<Entity> findAll() {
        return repository.findAll();
    }

    public Page<Entity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

}