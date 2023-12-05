package kz.timka.tacocloud.repositories;

import kz.timka.tacocloud.data.Taco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.util.List;

@Repository
public interface TacoRepository extends JpaRepository<Taco, Long> {

}
