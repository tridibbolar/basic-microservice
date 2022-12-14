package pepsico.entry.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PepsicoService<T> {
    Object byId(Long id);
    ResponseEntity<T> create(T t);
    ResponseEntity<T> update(long id, T t);

    ResponseEntity<T> delete(long id);
}
