package uk.ac.ebi.pride.mongodb.archive.model.files.idsettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

public class Enzyme {

    private String id;
    private String cTermGain;
    private String nTermGain;
    private Integer missedCleavages;
    private String semiSpecific;

    @JsonProperty("SiteRegexp")
    private String SiteRegexp;

    private MongoCvParam name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcTermGain() {
        return cTermGain;
    }

    public void setcTermGain(String cTermGain) {
        this.cTermGain = cTermGain;
    }

    public String getnTermGain() {
        return nTermGain;
    }

    public void setnTermGain(String nTermGain) {
        this.nTermGain = nTermGain;
    }

    public Integer getMissedCleavages() {
        return missedCleavages;
    }

    public void setMissedCleavages(Integer missedCleavages) {
        this.missedCleavages = missedCleavages;
    }

    public String getSemiSpecific() {
        return semiSpecific;
    }

    public void setSemiSpecific(String semiSpecific) {
        this.semiSpecific = semiSpecific;
    }

    public String getSiteRegexp() {
        return SiteRegexp;
    }

    public void setSiteRegexp(String SiteRegexp) {
        this.SiteRegexp = SiteRegexp;
    }

    public MongoCvParam getName() {
        return name;
    }

    public void setName(MongoCvParam name) {
        this.name = name;
    }
}
