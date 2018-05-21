package uk.ac.ebi.pride.mongodb.archive.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.PrideMongoPSM;

import java.util.Optional;

/**
 *
 * @author ypriverol
 */
public interface PridePSMMongoRepository extends MongoRepository<PrideMongoPSM, ObjectId>, PridePSMMongoRepositoryCustom {

    @Query("{'"+ PrideArchiveField.ACCESSION + "' : ?0}")
    Optional<PrideMongoPSM> findByAccession(String accession);


}
