package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;

import java.util.Set;


public interface PrideProjectMongoRepositoryCustom {

    /*
    sample_attributes = species
    ptmList = modifications
    */
    //TODO : this function is not used for now. But it might be useful for future
    Page<MongoPrideProject> findByMultipleAttributes(Pageable page, String[] accessions, String[] sample_attributes,
                                                     String[] instruments, String contact, String ptmList,
                                                     String[] publications, String[] keywords);

    Set<String> getAllProjectAccessions();

}
