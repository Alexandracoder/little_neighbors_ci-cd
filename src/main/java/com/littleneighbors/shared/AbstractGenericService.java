package com.littleneighbors.shared;

import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import com.littleneighbors.shared.mapper.GenericMapper;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractGenericService<
        E extends Identifiable<I>, Req, Res, I>
        implements GenericService<Req, Res, I> {

    protected JpaRepository<E, I> repository;
    protected GenericMapper<Req, Res, E> mapper;

    protected AbstractGenericService() {
    }

      protected AbstractGenericService(JpaRepository<E, I> repository, GenericMapper<Req, Res, E> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected AbstractGenericService(GenericMapper<Req, Res, E> mapper) {
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
    public Page<Res> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Res update(I id, Req request) {
        E existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));

        updateEntityFromRequest(request, existing);
        return mapper.toResponse(repository.save(existing));
    }
    protected  abstract void updateEntityFromRequest(Req request, E existing);

    @Override
    public void delete(I id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Entity not found with id " + id);
        }
        repository.deleteById(id);
    }
}
