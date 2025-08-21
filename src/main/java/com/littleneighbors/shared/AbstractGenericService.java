package com.littleneighbors.shared;

import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import com.littleneighbors.shared.mapper.GenericMapper;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractGenericService<
        E extends Identifiable<I>, Req, Res, I>
        implements GenericService<Req, Res, I> {

    protected final JpaRepository<E, I> repository;
    protected final GenericMapper<E, Req, Res> mapper;

    protected AbstractGenericService(JpaRepository<E, I> repository,
                                     GenericMapper<E, Req, Res> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Res create(Req request) {
        E entity = mapper.fromRequest(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public Res findById(I id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
        return mapper.toResponse(entity);

    }

    @Override
    public List<Res> findAll() {
        List<E> entities = repository.findAll();
        return mapper.toResponseList(entities);

    }

    @Override
    public Res update(I id, Req request) {
        E existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with i " + id));

        E updated = mapper.fromRequest(request);
        updated.setId(id);
        return mapper.toResponse(repository.save(updated));
    }
    @Override
    public void delete(I id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Entity not found with id " + id);
        }
        repository.deleteById(id);
    }
}
