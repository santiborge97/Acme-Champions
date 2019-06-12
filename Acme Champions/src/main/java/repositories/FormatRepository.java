
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Format;

@Repository
public interface FormatRepository extends JpaRepository<Format, Integer> {
	
	@Query("select f from Format f where f.federation.id = ?1")
	Collection<Format> findFormatByFederationId(int federationId);

}
