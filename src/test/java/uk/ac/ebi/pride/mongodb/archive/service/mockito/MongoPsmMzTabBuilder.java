package uk.ac.ebi.pride.mongodb.archive.service.mockito;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import uk.ac.ebi.pride.archive.dataprovider.data.database.DefaultDatabase;
import uk.ac.ebi.pride.archive.utils.spectrum.SpectrumIDGenerator;
import uk.ac.ebi.pride.archive.utils.spectrum.SpectrumIdGeneratorPrideArchive;
import uk.ac.ebi.pride.jmztab.model.*;
import uk.ac.ebi.pride.mongodb.archive.model.psms.PrideMongoPSM;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MongoPsmMzTabBuilder {

  private static Logger logger = LoggerFactory.getLogger(MongoPsmMzTabBuilder.class.getName());

  /**
   * The map between the assay accession and the file need to be provided externally from the
   * database
   *
   * @return A map of assay accessions to PSMs
   */
  public static List<PrideMongoPSM> readPsmsFromMzTabFile(String projectAccession, String assayAccession, MZTabFile mzTabFile) {
    List<PrideMongoPSM> result = new LinkedList<>();
    if (mzTabFile != null) {
      result = convertFromMzTabPsmsToPrideArchivePsms(mzTabFile.getPSMs(), mzTabFile.getMetadata(), projectAccession, assayAccession);
      logger.debug("Found " + result.size() + " psms for Assay " + assayAccession + " in file " + mzTabFile);
    }
    return result;
  }

  /**
   * Converts from mzTab-PSMs to Archive-compatible PSMs.
   *
   * @param mzTabPsms maTab PSMs
   * @param metadata PSM metadata
   * @param projectAccession the Archive project accession number
   * @param assayAccession the Archive assay accession number
   * @return list of Archive-compatible PSMs.
   */
  private static LinkedList<PrideMongoPSM> convertFromMzTabPsmsToPrideArchivePsms(
      Collection<PSM> mzTabPsms,
      Metadata metadata,
      String projectAccession,
      String assayAccession) {
    LinkedList<PrideMongoPSM> result = new LinkedList<>();
    for (PSM mzTabPsm : mzTabPsms) {
      PrideMongoPSM newPsm = PrideMongoPSM.builder()
              .accessionInReportedFile(mzTabPsm.getPSM_ID())
              .reportedFileID(metadata.getMZTabID())
              .spectrumAccession(createSpectrumId(mzTabPsm, projectAccession))
              .peptideSequence(mzTabPsm.getSequence())
              .projectAccession(projectAccession)
              .database(new DefaultDatabase(mzTabPsm.getDatabase(), mzTabPsm.getDatabaseVersion()))
              .projectAccession(projectAccession)
              .build();
      result.add(newPsm);
      // To be compatible with the project index we don't clean the protein accession
    }
    return result;
  }

  /**
   * Creates a spectrum Id compatible with PRIDE Archive
   *
   * @param psm original mzTab psm
   * @param projectAccession PRIDE Archive accession
   * @return the spectrum Id or "unknown_id" if the conversion fails
   */
  private static String createSpectrumId(PSM psm, String projectAccession) {
    String msRunFileName;
    String identifierInMsRunFile;
    String spectrumId = "unknown_id";
    SpectraRef spectrum;
    SplitList<SpectraRef> spectra = psm.getSpectraRef();
    if (!CollectionUtils.isEmpty(spectra)) {
      // If the psm was discovered as a combination of several spectra, we will
      // simplify the case choosing only the first spectrum
      if (logger.isDebugEnabled()) {
        logger.debug("Found " + spectra.size() + " spectra for PSM " + psm.getPSM_ID());
      }
      spectrum = spectra.get(0);
      if (spectrum != null) {
        msRunFileName = extractFileName(spectrum.getMsRun().getLocation().getFile());
        identifierInMsRunFile = spectrum.getReference();
        if (msRunFileName != null
            && !msRunFileName.isEmpty()
            && identifierInMsRunFile != null
            && !identifierInMsRunFile.isEmpty()) {
          SpectrumIDGenerator spectrumIDGenerator = new SpectrumIdGeneratorPrideArchive();
          spectrumId =
              spectrumIDGenerator.generate(projectAccession, msRunFileName, identifierInMsRunFile);
        }
      }
    }
    return spectrumId;
  }

  private static String extractFileName(String filePath) {
    return FilenameUtils.getName(filePath);
  }
}
